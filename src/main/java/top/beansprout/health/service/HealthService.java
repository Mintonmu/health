package top.beansprout.health.service;

import java.util.Date;

import top.beansprout.health.model.dto.BodyInfoQuery;
import top.beansprout.health.model.dto.BodyInfoSaveDto;
import top.beansprout.health.model.entity.TBodyInfo;
import top.beansprout.health.model.vo.BodyInfoDetailVo;
import top.beansprout.health.model.vo.BodyInfoStatisticsVo;
import top.beansprout.health.model.vo.PageVo;

/**
 * <p>Title: HealthService</p>
 * <p>Description: 健康业务接口</p>
 * 
 * @author cyy
 * @date 2020年4月27日
 */
public interface HealthService {

	/** 保存身体信息 **/
	void saveBodyInfo(int userId, BodyInfoSaveDto bodyInfoSaveDto);

	/** 获取身体信息列表 **/
	PageVo<TBodyInfo> bodyInfoList(int userId, BodyInfoQuery bodyInfoQuery);

	/** 删除身体信息 **/
	void deleteBodyInfo(int userId, int id);

	/** 获取身体信息  **/
	BodyInfoDetailVo getBodyInfo(int userId, int id);

	/** 获取身体信息统计 **/
	BodyInfoStatisticsVo getBodyStatistics(int userId, Date date);

}