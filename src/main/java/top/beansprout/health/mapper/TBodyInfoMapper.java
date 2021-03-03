package top.beansprout.health.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.beansprout.health.model.entity.TBodyInfo;

/**
 * <p>Title: TBodyInfoMapper</p>
 * <p>Description: 身体健康操作</p>
 * 
 * @author cyy
 * @date 2020年4月27日
 */
public interface TBodyInfoMapper {

	@Insert("INSERT INTO t_body_info (creator, createtime, updater, updatetime, lowbloodpressure, "
			+ "highbloodpressure, heartrate, temperature, appetite, weight, numberofstep) VALUE "
			+ "( #{creator},NOW(),#{updater},NOW(),#{lowbloodpressure},#{highbloodpressure},"
			+ "#{heartrate},#{temperature},#{appetite},#{weight},#{numberofstep} )")
	int insertByOne(TBodyInfo bodyInfo);

	@Select("SELECT COUNT(*) FROM t_body_info WHERE creator = #{creator} AND createtime >= CURDATE() AND createtime <= CONCAT(CURDATE(), '23:59:59')")
	int countByOneAsToDay(@Param("creator") int userId);

	List<TBodyInfo> selectByUserId(@Param("creator") int userId, @Param("minDate") Date minDate,
			@Param("maxDate") Date maxDate);

	@Delete("DELETE FROM t_body_info WHERE id = #{id} AND creator = #{creator}")
	int deleteByOne(@Param("id") int id, @Param("creator") int userId);

	@Select("SELECT * FROM t_body_info WHERE id = #{id} AND creator = #{creator}")
	TBodyInfo selectByUserOne(@Param("id") int id, @Param("creator") int userId);

}