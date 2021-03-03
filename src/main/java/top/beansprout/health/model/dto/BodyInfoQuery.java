package top.beansprout.health.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BodyInfoQuery</p>
 * <p>Description: 身体信息查询</p>
 * 
 * @author cyy
 * @date 2020年4月27日
 */
@Setter
@Getter
public class BodyInfoQuery extends PageDto {

	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date minDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date maxDate;

}