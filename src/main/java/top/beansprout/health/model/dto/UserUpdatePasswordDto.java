package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

/**
  * <p> Title: UserUpdatePasswordDto </p>
  * <p> Description: 用户修改密码</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月27日 上午12:32:39
  * @Version: 1.0.0
  */
@Setter
@Getter
public class UserUpdatePasswordDto implements Serializable {

	private static final long serialVersionUID = 8674837059264557849L;

	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 10, message = "密码必须在6~10位之间")
	private String passWord;
	@NotBlank(message = "确认密码不能为空")
	@Length(min = 6, max = 10, message = "确认密码必须在6~10位之间")
	private String confirmPassWord;

}