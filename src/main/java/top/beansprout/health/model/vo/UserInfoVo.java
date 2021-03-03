package top.beansprout.health.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Title: UserInfoVo</p>
 * <p>Description: 用户信息</p>
 * 
 * @author cyy
 * @date 2020年4月30日
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo implements Serializable {

	private static final long serialVersionUID = 8389631895704876733L;

	// 用户id
	private int id;
	// 用户名
	private String nickname;
	// 账户
	private String username;
	// 邮箱
	private String email;
	// 头像
	private String headurl;

}