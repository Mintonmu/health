package top.beansprout.health.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: TBodyInfo</p>
 * <p>Description: 身体信息表</p>
 * @author cyy
 * @date 2020年4月27日
 */
@Setter
@Getter
public class TBodyInfo extends BaseUpdateEntity {

	private static final long serialVersionUID = -7707228772072459981L;

	// 舒张压
	private int lowbloodpressure;
	// 收缩压
	private int highbloodpressure;
	// 心率
	private int heartrate;
	// 体温
	private double temperature;
	// 食欲
	private int appetite;
	// 体重
	private double weight;
	// 步数
	private int numberofstep;

}