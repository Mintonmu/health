package top.beansprout.health.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import top.beansprout.health.constant.SysConstant;
import top.beansprout.health.model.vo.BusinessException;
import top.beansprout.health.model.vo.UserLoginVo;
import top.beansprout.health.util.PublicUtils;

/**
  * <p> Title: BaseController </p>
  * <p> Description: 基本信息处理</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月27日 上午12:36:51
  * @Version: 1.0.0
  */
public class BaseController {

	@Autowired
	private HttpServletRequest request;

	public UserLoginVo getUserInfo() {
		final Object userObject = request.getSession().getAttribute(SysConstant.INIT_FIELD_USER_VO);
		if (PublicUtils.isNotBlank(userObject))
			return (UserLoginVo) userObject;
		throw new BusinessException("login", "身份信息已过期，请重新登录");
	}

	public int getUserId() {
		return getUserInfo().getId();
	}

}