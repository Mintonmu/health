package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

/**
  * <p> Title: UserRegisterDto </p>
  * <p> Description: 用户注册</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月26日 上午12:54:09
  * @Version: 1.0.0
  */
@Setter
@Getter
public class UserRegisterDto implements Serializable {

	private static final long serialVersionUID = -4003225859721099921L;

	@NotBlank(message = "用户名或姓名不能为空")
	@Length(min = 2, max = 10, message = "用户名或姓名必须在2~10位之间")
	private String username;
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 10, message = "密码必须在6~10位之间")
	private String passWord;
	@NotBlank(message = "确认密码不能为空")
	@Length(min = 6, max = 10, message = "确认密码必须在6~10位之间")
	private String confirmPassWord;

}