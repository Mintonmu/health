package top.beansprout.health.model.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BaseEntity</p>
 * <p>Description: 实体类对象的父类-公共字段</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/22 16:50
 */
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4402927657355642335L;

	private int id;

}