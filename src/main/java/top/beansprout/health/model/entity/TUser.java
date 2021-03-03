package top.beansprout.health.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: TUser</p>
 * <p>Description: 用户表</p>
 * 
 * @author beansprout
 * @date 2020年4月22日
 */
@Setter
@Getter
public class TUser extends BaseUpdateEntity {

	private static final long serialVersionUID = 7849174171033853904L;

	// 用户名
	private String nickname;
	// 账户
	private String username;
	// 密码
	private String password;
	// 邮箱
	private String email;
	// 头像
	private String headurl;

}
