package top.beansprout.health.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: THealthConfig</p>
 * <p>Description: 健康信息配置表</p>
 * @author cyy
 * @date 2020年4月27日
 */
@Setter
@Getter
public class THealthConfig extends BaseUpdateEntity {

	private static final long serialVersionUID = 6928075281678415808L;

	// 最小低血压
	private int minlowbloodpressure;
	// 最大低血压
	private int maxlowbloodpressure;
	// 最小高血压
	private int minhighbloodpressure;
	// 最大高血压
	private int maxhighbloodpressure;
	// 最小心率
	private int minheartrate;
	// 最大心率
	private int maxheartrate;
	// 最小体温
	private double mintemperature;
	// 最大体温
	private double maxtemperature;

}