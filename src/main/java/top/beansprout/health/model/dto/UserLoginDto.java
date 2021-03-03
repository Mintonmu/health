package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

/**
  * <p> Title: UserLoginDto </p>
  * <p> Description: 用户登录</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月25日 下午8:11:06
  * @Version: 1.0.0
  */
@Setter
@Getter
public class UserLoginDto implements Serializable {

	private static final long serialVersionUID = 6865880222397602631L;

	@NotBlank(message = "账户不能为空")
	@Length(max = 20, message = "账户不能超过20位")
	private String username;
	@NotBlank(message = "密码不能为空")
	@Length(max = 20, message = "密码不能超过20位")
	private String passWord;

}