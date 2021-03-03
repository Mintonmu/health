package top.beansprout.health.model.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BodyInfoDetailVo</p>
 * <p>Description: 身体信息详情</p>
 * 
 * @author cyy
 * @date 2020年4月28日
 */
@Setter
@Getter
public class BodyInfoDetailVo implements Serializable {

	private static final long serialVersionUID = -2831120593213278473L;

	// id
	private int id;
	// 打卡时间
	private Date createTime;

	// 舒张压
	private int lowbloodpressure;
	// 舒张压状态
	private int lowbloodpressurestatus;
	// 舒张压描述
	private String lowbloodpressuredesc;

	// 收缩压
	private int highbloodpressure;
	// 收缩压状态
	private int highbloodpressurestatus;
	// 收缩压描述
	private String highbloodpressuresesc;

	// 血压
	private String bloodpressuredesc;

	// 心率
	private int heartrate;
	// 心率状态
	private int heartratestatus;
	// 心率描述
	private String heartratedesc;

	// 体温
	private double temperature;
	// 体温状态
	private int temperaturestatus;
	// 体温描述
	private String temperaturedesc;

	// 食欲
	private int appetite;
	// 体重
	private double weight;
	// 步数
	private int numberofstep;

}