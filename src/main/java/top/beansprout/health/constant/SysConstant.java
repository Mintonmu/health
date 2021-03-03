package top.beansprout.health.constant;

import javax.servlet.http.HttpServletRequest;

import top.beansprout.health.model.vo.RequestVo;
import top.beansprout.health.util.JsonUtils;
import top.beansprout.health.util.PublicUtils;

/**
 * <p>Title: SysConstant</p>
 * <p>Description: 系统常量</p>
 * @author cyy
 * @date 2020年4月23日
 */
public class SysConstant {

	public static final String CHARACHER = "UTF-8";

	public static final String INIT_FIELD_REQUEST_IP = "ip";
	public static final String INIT_FIELD_REQUEST_REQUEST_ID = "requestId";
	public static final String INIT_FIELD_REQUEST_USER_AGENT = "userAgent";
	public static final String INIT_FIELD_REQUEST_BASE_PATH = "basePath";
	public static final String INIT_FIELD_REQUEST_VO = "requestVo";
	public static final String INIT_FIELD_USER_VO = "user";

	/** 获取初始化数据 **/
	public static RequestVo getRequestVo(HttpServletRequest request) {
		return JsonUtils.toObj(PublicUtils.getAttribute(request, INIT_FIELD_REQUEST_VO), RequestVo.class);
	}

}