package top.beansprout.health.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import top.beansprout.health.model.vo.BusinessException;
import top.beansprout.health.model.vo.R;
import top.beansprout.health.util.JsonUtils;
import top.beansprout.health.util.PublicUtils;

/**
 * <p>Title: MySimpleMappingExceptionResolver</p>
 * <p>Description: 异常处理</p>
 * @author cyy
 * @date 2020年4月23日
 */
public class MySimpleMappingExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		// 判断是否ajax请求
		if (!((request.getHeader("accept").indexOf("application/json") > -1)
				|| ((request.getHeader("X-Requested-With") != null)
						&& (request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)))) {
			// 这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
			exception.printStackTrace();
			if (exception instanceof BusinessException) {
				final BusinessException be = (BusinessException) exception;
				String path = be.getR().getPath();
				if (PublicUtils.isBlank(path)) {
					path = request.getHeader("Referer");
					path = StringUtils.removeStart(path, request.getHeader("Origin"));
					path = StringUtils.removeStart(path, request.getContextPath());
				}
				return R.failed(path, be.getR());
			} else
				// 对于非ajax请求，我们都统一跳转到error.jsp页面
				return R.failed(R.budil().result(false).message("系统异常！"));
		} else {
			// 如果是ajax请求，JSON格式返回
			if (exception instanceof BusinessException) {
				final BusinessException be = (BusinessException) exception;
				response(response, be.getR());
			} else {
				response(response, R.budil().result(false).message("系统异常！"));
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private void response(HttpServletResponse response, Object data) {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			response.setStatus(HttpStatus.OK.value());
			writer = response.getWriter();
			writer.write(JsonUtils.toJson(data));
			writer.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

}