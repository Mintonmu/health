package top.beansprout.health.model.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
  * <p> Title: UserLoginVo </p>
  * <p> Description: </p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月25日 下午8:49:19
  * @Version: 1.0.0
  */
@Setter
@Getter
public class UserLoginVo implements Serializable {

	private static final long serialVersionUID = -7027379021523096696L;

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