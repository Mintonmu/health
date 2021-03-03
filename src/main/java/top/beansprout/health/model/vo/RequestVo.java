package top.beansprout.health.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Title: RequestVo</p>
 * <p>Description: 请求数据</p>
 * @author cyy
 * @date 2020年4月23日
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestVo implements Serializable {

	private static final long serialVersionUID = -2460516900904070899L;

	// 基本项目地址
	private String basePath;
	// 用户信息
	private UserLoginVo user;

}