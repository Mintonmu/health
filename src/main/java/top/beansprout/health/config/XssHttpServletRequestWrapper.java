package top.beansprout.health.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import top.beansprout.health.util.PublicUtils;

/**
 * <p>Title: XssHttpServletRequestWrapper</p>
 * <p>Description: XSS过滤处理</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 22:51
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest orgRequest;
    private final String requestUri;
    private static HTMLWrapper htmlWrapper;
    @Getter
    private String requestBody;

    private static String key = "and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+";
    private static Set<String> notAllowedKeyWords = new HashSet<>(0);
    private static String replacedString = "INVALID";
    // 指定uri需要进行html过滤
    private static String []htmlUri = {  };
    private static List<String> ignoreParams = new ArrayList<>();

    static {
        final String keyStr[] = key.split("\\|");
        for (final String str : keyStr) {
            notAllowedKeyWords.add(str);
        }
        htmlWrapper = new HTMLWrapper(true);
    }

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        orgRequest = request;
        if (request instanceof XssHttpServletRequestWrapper) {
            orgRequest = ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        requestUri = orgRequest.getRequestURI();
        requestBody = getAnalysisRequestBody();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 为空，直接返回
        if (PublicUtils.isBlank(requestBody))
			return super.getInputStream();

        // xss过滤
        requestBody = xssEncode(requestBody);
		final ByteArrayInputStream bis = new ByteArrayInputStream(requestBody.getBytes("UTF-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        final String value = super.getParameter(name);
        if (value == null)
			return null;
        return xssEncode(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        final String[] parameters = super.getParameterValues(name);
        if ((parameters == null) || (parameters.length == 0))
			return null;
        final int count = parameters.length;
        final String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = xssEncode(parameters[i]);
        }
        return encodedValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> parameters = super.getParameterMap();
        if (parameters == null)
			return null;
        final Map<String, String[]> result = new HashMap<>();
        for (final String key : parameters.keySet()) {
            final String encodedKey = xssEncode(key);
            final int count = parameters.get(key).length;
            final String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = xssEncode(parameters.get(key)[i]);
            }
            result.put(encodedKey, encodedValues);
        }
        return result;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        final String value = super.getHeader(name);
        if (value == null)
			return null;
        // 忽略指定字段不进行xss
        if (ignoreParams.contains(name)) return value;
        return xssEncode(value);
    }

    /** 获取请求body json 内容 **/
    private String getAnalysisRequestBody() throws IOException {
		final String contentType = super.getHeader("Content-Type");
        if (PublicUtils.isBlank(contentType) || !MediaType.APPLICATION_JSON_VALUE.contentEquals(contentType))
			return null;

		return IOUtils.toString(super.getInputStream(), "UTF-8");
    }

    /** 获取最原始request **/
    private HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /** 过滤 */
    private String xssEncode(String value) {
        // You'll need to remove the spaces from the html entities below
        String result = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        result = result.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        result = result.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        result = result.replaceAll("'", "& #39;");
        result = result.replaceAll("eval\\((.*)\\)", "");
        result = result.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        result = result.replaceAll("script", "");
        result = sqlKeyFilterWords(result);
        for (int i = 0; i < htmlUri.length; i ++) {
            if (requestUri.contains(htmlUri[i])) {
                result = htmlWrapper.filter(result);
            }
        }
        return result;
    }

    /** sql注入过滤 **/
    private String sqlKeyFilterWords(String value) {
        String paramValue = value;
        for (final String keyword : notAllowedKeyWords) {
            if ((paramValue.length() > (keyword.length() + 1))
                    && (paramValue.contains(" " + keyword) || paramValue.contains(keyword + " ") || paramValue.contains(" " + keyword + " "))) {
                paramValue = StringUtils.replace(paramValue, keyword, replacedString);
				log.warn("CrosXssFilter 请求参数中包含不允许sql的关键词({});参数：{};过滤后的参数：{}", keyword, value, paramValue);
            }
        }
        return paramValue;
    }

}