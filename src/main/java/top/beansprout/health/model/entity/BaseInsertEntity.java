package top.beansprout.health.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: BaseUpdateEntity</p>
 * <p>Description: 实体类插入对象的父类-公共字段</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/23 23:52
 */
@Setter
@Getter
public abstract class BaseInsertEntity extends BaseEntity {

    private static final long serialVersionUID = -7314584145079382702L;

    private Long creator;// 创建人id
    private Date createtime;// 创建时间

    public void init(long userId) {
        this.creator = userId;
        this.createtime = new Date();
    }

}