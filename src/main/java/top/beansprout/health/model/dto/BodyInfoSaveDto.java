package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BodyInfoSaveDto</p>
 * <p>Description: 身体信息保存</p>
 * 
 * @author cyy
 * @date 2020年4月27日
 */
@Setter
@Getter
public class BodyInfoSaveDto implements Serializable {
	
	private static final long serialVersionUID = 80577054311531170L;

	@NotNull(message = "低血压不能为空")
	@Range(min = 0, max = 200, message = "低血压只能在0到200之间")
	private Integer lowbloodpressure;
	@NotNull(message = "高血压不能为空")
	@Range(min = 0, max = 200, message = "高血压只能在0到200之间")
	private Integer highbloodpressure;
	@NotNull(message = "心率不能为空")
	@Range(min = 40, max = 200, message = "心率只能在40到200之间")
	private Integer heartrate;
	@NotNull(message = "体温不能为空")
	@Range(min = 33, max = 45, message = "体温只能在33到45之间")
	private Double temperature;
	@NotNull(message = "食欲不能为空")
	@Range(min = 1, max = 6, message = "食欲只能在1到6之间")
	private Integer appetite;
	@NotNull(message = "体重不能为空")
	@Range(min = 0, max = 200, message = "体重只能在33到40之间")
	private Double weight;
	// 步数
	private int numberofstep;

}