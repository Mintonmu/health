package top.beansprout.health.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: PageDto</p>
 * <p>Description: 列表请求</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 22:32
 */
@Setter
@Getter
public class PageDto implements Serializable {

	private static final long serialVersionUID = -1796187511693230421L;

	@Min(value = 1, message = "当前页最小页码为1")
	private int page = 1;

	@Min(value = 1, message = "每页展示条数最小为1")
	@Max(value = 100, message = "每页展示条数最大为100")
	private int pageSize = 10;

}