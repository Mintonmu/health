package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: UserUpdateInfoDto</p>
 * <p>Description: 用户信息修改</p>
 * 
 * @author cyy
 * @date 2020年4月30日
 */
@Setter
@Getter
public class UserUpdateInfoDto implements Serializable {

	private static final long serialVersionUID = 7815632813092576575L;

	// 昵称
	@NotBlank(message = "昵称不能为空")
	@Length(min = 2, max = 10, message = "昵称只能为2~10个字符")
	private String nickname;
	// 邮件
	@Email(message = "邮箱格式不正确")
	private String email;
	// 头像
	@NotNull(message = "头像不能为空")
	private MultipartFile headurl;

}