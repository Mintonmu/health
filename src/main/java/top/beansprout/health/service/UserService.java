package top.beansprout.health.service;

import javax.servlet.http.HttpServletRequest;

import top.beansprout.health.model.dto.UserLoginDto;
import top.beansprout.health.model.dto.UserRegisterDto;
import top.beansprout.health.model.dto.UserUpdateInfoDto;
import top.beansprout.health.model.dto.UserUpdatePasswordDto;
import top.beansprout.health.model.vo.UserInfoVo;
import top.beansprout.health.model.vo.UserLoginVo;

/**
  * <p> Title: UserService </p>
  * <p> Description: 用户业务接口</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月25日 下午8:01:14
  * @Version: 1.0.0
  */
public interface UserService {

	/** 登录逻辑 **/
	UserLoginVo login(UserLoginDto userLoginDto);

	/** 注册逻辑 **/
	void register(UserRegisterDto userRegisterDto);

	/** 登出 **/
	void logout(HttpServletRequest request);

	/** 修改密码 **/
	void updatePassword(HttpServletRequest request, int userId, UserUpdatePasswordDto updatePasswordDto);

	/** 修改用户信息  **/
	UserInfoVo updateUserInfo(int userId, UserUpdateInfoDto userUpdateInfoDto);

}