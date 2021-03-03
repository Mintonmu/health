package top.beansprout.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.beansprout.health.model.dto.UserLoginDto;
import top.beansprout.health.model.dto.UserRegisterDto;
import top.beansprout.health.model.dto.UserUpdateInfoDto;
import top.beansprout.health.model.dto.UserUpdatePasswordDto;
import top.beansprout.health.model.vo.R;
import top.beansprout.health.service.UserService;

/**
 * <p>Title: UserController</p>
 * <p>Description: 用户管理接口</p>
 * 
 * @author cyy
 * @date 2020年4月23日
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	// 登录
	@ResponseBody
	@PostMapping("/login")
	public R login(@Valid UserLoginDto userLoginDto) {
		return R.okAsAjax(userService.login(userLoginDto));
	}

	// 注册
	@ResponseBody
	@PostMapping("/register")
	public R register(@Valid UserRegisterDto userRegisterDto) {
		userService.register(userRegisterDto);
		return R.okAsAjax();
	}

	// 登出
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		userService.logout(request);
		return "redirect:/login?target=redirect";
	}

	// 修改密码
	@ResponseBody
	@PutMapping("/updatePassword")
	public R updatePassword(HttpServletRequest request, @RequestBody @Valid UserUpdatePasswordDto updatePasswordDto) {
		userService.updatePassword(request, getUserId(), updatePasswordDto);
		return R.okAsAjax();
	}

	// 修改用户信息
	@ResponseBody
	@PostMapping(value = "/updateUserInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public R updateUserInfo(@Valid UserUpdateInfoDto userUpdateInfoDto) {
		return R.okAsAjax(userService.updateUserInfo(getUserId(), userUpdateInfoDto));
	}

}