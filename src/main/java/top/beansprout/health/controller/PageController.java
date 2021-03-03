package top.beansprout.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
  * <p> Title: PageController </p>
  * <p> Description: 页面管理</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月23日 下午11:15:18
  * @Version: 1.0.0
  */
@Controller
public class PageController {

	// 根页面
	@GetMapping("/")
	public String rootView() {
		return "redirect:/login";
	}

	// 登录页面
	@GetMapping("/login")
	public String loginView() {
		return "../../index";
	}

	// 注册页面
	@GetMapping("/register")
	public String registerView() {
		return "register";
	}

	// 主页面
	@GetMapping("/home")
	public String homeView() {
		return "home";
	}

	// 用户信息页面
	@GetMapping("/userInfo")
	public String userInfoView() {
		return "userInfo";
	}

	// 用户信息页面
	@GetMapping("/updatePassword")
	public String updatePasswordView() {
		return "updatePassword";
	}

	// 用户身体信息录入页面
	@GetMapping("/bodyInfoInput")
	public String bodyInfoInputView() {
		return "bodyInfoInput";
	}

	// 用户身体信息列表页面
	@GetMapping("/bodyInofList")
	public String bodyInofListView() {
		return "bodyInofList";
	}

	// 用户身体信息统计页面
	@GetMapping("/bodyInofStatistics")
	public String bodyInofStatisticsView() {
		return "bodyInofStatistics";
	}

}