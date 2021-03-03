package top.beansprout.health.model.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
  * <p> Title: BodyInfoStatisticsVo </p>
  * <p> Description: 健康信息统计</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月28日 下午11:32:05
  * @Version: 1.0.0
  */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoStatisticsVo implements Serializable {

	private static final long serialVersionUID = 8627681890953284164L;

	// 类别名字
	private List<String> typeNames;
	// 类别数据
	private List<BodyInfoVo> bodyInfoVos;

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BodyInfoVo {
		// 类别名
		private String typeName;
		// 数据
		private List<Object> datas;
	}

}