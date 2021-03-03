package top.beansprout.health.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.beansprout.health.constant.SysConstant;
import top.beansprout.health.model.vo.RequestVo;
import top.beansprout.health.model.vo.RequestVo.RequestVoBuilder;
import top.beansprout.health.model.vo.UserLoginVo;
import top.beansprout.health.util.JsonUtils;
import top.beansprout.health.util.PublicUtils;

/**
 * <p>Title: CrossXssFilter</p>
 * <p>Description: 对用户输入的表单信息进行检测过滤</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 22:47
 */
@Slf4j
public class CrossXssFilter implements Filter {

	/** 忽略资源地址 **/
	private final String[] ignores = new String[] { "/druid", "/static" };
	/** 忽略页面地址 **/
	private final String[] filtrationViews = new String[] { "/login", "/user/login", "/register", "/user/register",
			"/logout", "/user/logout" };

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = init(servletRequest);
		if (!isRequestValid(request)) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setContentType("text/html;charset=utf-8");
		// sql,xss过滤
		log.info("URI:{}", request.getRequestURI());
		log.info("METHOD:{}", request.getMethod());
		log.info("ParameterMap:{}", JsonUtils.toJson(request.getParameterMap()));
		if (isOauthValid(request)) {
			// 获取身份
			final Object user = request.getSession().getAttribute(SysConstant.INIT_FIELD_USER_VO);
			if (PublicUtils.isBlank(user)) {
				final HttpServletResponse response = (HttpServletResponse) servletResponse;
				if ((request.getHeader("x-requested-with") != null)
						&& "XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
					// ajax请求
					response.setHeader("sessionstatus", "timeout");
					response.setStatus(403);
					response.addHeader("loginPath", "login");
					chain.doFilter(request, response);
					return;
				}
				// 页面请求
				response.setHeader("X-Frame-Options", "DENY");
				response.sendRedirect("login");
				return;
			}
		}
		final XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(request);
		chain.doFilter(xssHttpServletRequestWrapper, servletResponse);
	}

	/** 初始化 **/
	private HttpServletRequest init(ServletRequest servletRequest) {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;

		final String basePath = PublicUtils.join(request.getScheme(), "://", request.getServerName(),
				":" + request.getServerPort(), request.getContextPath(), "/");

		final RequestVoBuilder requestVo = RequestVo.builder().basePath(basePath);
		final Object userStr = request.getSession().getAttribute(SysConstant.INIT_FIELD_USER_VO);
		if (PublicUtils.isNotBlank(userStr)) {
			requestVo.user((UserLoginVo) userStr);
		}
		request.setAttribute(SysConstant.INIT_FIELD_REQUEST_VO, JsonUtils.toJson(requestVo.build()));
		return request;
	}

	/** 校验合法地址请求 **/
	private boolean isRequestValid(HttpServletRequest request) {
		URI uri;
		try {
			uri = new URI(request.getRequestURL().toString());
		} catch (final URISyntaxException ex) {
			return false;
		}

		if (uri.getHost() == null)
			return false;
		if (!uri.getScheme().equalsIgnoreCase("http") && !uri.getScheme().equalsIgnoreCase("https"))
			return false;
		// 忽略指定地址
		final String path = StringUtils.removeStart(uri.getPath(), request.getContextPath());
		for (final String ignore : ignores) {
			if (path.startsWith(ignore))
				return false;
		}
		// 忽略swagger的根路径
		if (path.equalsIgnoreCase("/"))
			return false;
		return true;
	}

	/** 是否校验合法身份请求 **/
	private boolean isOauthValid(HttpServletRequest request) {
		// 忽略指定地址
		final String path = StringUtils.removeStart(request.getRequestURI(), request.getContextPath());
		for (final String filtrationView : filtrationViews) {
			if (path.startsWith(filtrationView))
				return false;
		}
		return true;
	}

	@Override
	public void destroy() {
	}

}