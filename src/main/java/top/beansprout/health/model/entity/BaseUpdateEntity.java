package top.beansprout.health.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BaseUpdateEntity</p>
 * <p>Description: 实体类用户对象的父类-公共字段</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/23 23:52
 */
@Setter
@Getter
public abstract class BaseUpdateEntity extends BaseEntity {

	private static final long serialVersionUID = 1620758104430484420L;

	private int creator;// 创建人id
	private Date createtime;// 创建时间
	private int updater;// 更新者id
	private Date updatetime;// 更新时间

	public void init(int userId) {
		this.creator = userId;
		this.createtime = new Date();
		this.update(userId);
	}

	public void update(int userId) {
		this.updater = userId;
		this.updatetime = new Date();
	}

}