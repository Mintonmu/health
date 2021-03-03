package top.beansprout.health.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.beansprout.health.model.entity.TUser;

/**
  * <p> Title: TUserMapper </p>
  * <p> Description: 用户数据操作</p>
  * 
  * @Auther: cyy
  * @Date: 2020年4月23日 下午9:24:05
  * @Version: 1.0.0
  */
public interface TUserMapper {

	@Select("SELECT * FROM t_user WHERE username = #{username}")
	TUser selectByusername(@Param("username") String username);

	@Select("SELECT * FROM t_user WHERE id = #{userId}")
	TUser selectById(@Param("userId") int id);

	@Insert("INSERT INTO t_user (creator, createtime, updater, updatetime, nickname, username, password) "
			+ "VALUE (0, NOW(), 0, NOW(), #{username}, #{username}, #{password})")
	int insertByOne(TUser tUser);

	@Update("UPDATE t_user SET updater = #{id}, updatetime = NOW(), password = #{password} WHERE id = #{id}")
	int updateByOne(TUser tUser);

	@Update("UPDATE t_user SET updater = #{id}, updatetime = NOW(), nickname = #{nickname}, email = #{email}, headurl = #{headurl} WHERE id = #{id}")
	int updateByUserInfoOne(TUser tUser);

}