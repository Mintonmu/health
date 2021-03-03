package top.beansprout.health.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.beansprout.health.mapper.TBodyInfoMapper;
import top.beansprout.health.model.dto.BodyInfoQuery;
import top.beansprout.health.model.dto.BodyInfoSaveDto;
import top.beansprout.health.model.entity.TBodyInfo;
import top.beansprout.health.model.vo.BodyInfoDetailVo;
import top.beansprout.health.model.vo.BodyInfoStatisticsVo;
import top.beansprout.health.model.vo.BodyInfoStatisticsVo.BodyInfoVo;
import top.beansprout.health.model.vo.BusinessException;
import top.beansprout.health.model.vo.PageVo;
import top.beansprout.health.service.HealthService;
import top.beansprout.health.util.DateUtils;
import top.beansprout.health.util.PublicUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: HealthServiceImpl</p>
 * <p>Description: 健康业务逻辑操作</p>
 *
 * @author cyy
 * @date 2020年4月27日
 */
@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private TBodyInfoMapper bodyInfoMapper;

    @Override
    @Transactional
    public void saveBodyInfo(int userId, BodyInfoSaveDto bodyInfoSaveDto) {
        // 判断今天是否已经录入过
        final int countByOneAsToDay = bodyInfoMapper.countByOneAsToDay(userId);
        if (countByOneAsToDay > 0)
            throw new BusinessException("今天已经打过卡了");

        final TBodyInfo tBodyInfo = new TBodyInfo();
        PublicUtils.copyBean(bodyInfoSaveDto, tBodyInfo);
        tBodyInfo.init(userId);
        bodyInfoMapper.insertByOne(tBodyInfo);
    }

    @Override
    public PageVo<TBodyInfo> bodyInfoList(int userId, BodyInfoQuery bodyInfoQuery) {
        if ((bodyInfoQuery.getMinDate() != null) && (bodyInfoQuery.getMaxDate() != null)) {
            if (bodyInfoQuery.getMinDate().after(bodyInfoQuery.getMaxDate()))
                throw new BusinessException("最小时间不能大于最大时间");
        }
        PageHelper.startPage(bodyInfoQuery.getPage(), bodyInfoQuery.getPageSize());
        final PageInfo<TBodyInfo> pageInfo = new PageInfo<>(
                bodyInfoMapper.selectByUserId(userId, bodyInfoQuery.getMinDate(), bodyInfoQuery.getMaxDate()));
        for (var i :
                pageInfo.getList()) {
            System.out.println(i.getId());
        }
        return PageVo.of(pageInfo);
    }

    @Override
    @Transactional
    public void deleteBodyInfo(int userId, int id) {
        bodyInfoMapper.deleteByOne(id, userId);
    }

    @Override
    public BodyInfoDetailVo getBodyInfo(int userId, int id) {
        final TBodyInfo bodyInfo = bodyInfoMapper.selectByUserOne(id, userId);
        if (bodyInfo == null)
            throw new BusinessException("该身体信息不存在");

        BodyInfoDetailVo bodyInfoDetailVo = new BodyInfoDetailVo();
        PublicUtils.copyBean(bodyInfo, bodyInfoDetailVo);
        bodyInfoDetailVo = bodyInfoCompare(bodyInfoDetailVo);
        return bodyInfoDetailVo;
    }

    /**
     * 健康信息比较
     **/
    private BodyInfoDetailVo bodyInfoCompare(BodyInfoDetailVo bodyInfoDetailVo) {
        // TODO
        // 血压
        bodyInfoDetailVo = bloodPressureCompare(bodyInfoDetailVo);
        // 心率
        final int heartrate = bodyInfoDetailVo.getHeartrate();
        bodyInfoDetailVo.setHeartratestatus(heartratestatus(heartrate));
        bodyInfoDetailVo.setHeartratedesc(statusDesc(bodyInfoDetailVo.getHeartratestatus()));
        // 体温
        final double temperature = bodyInfoDetailVo.getTemperature();
        bodyInfoDetailVo.setTemperaturestatus(temperaturestatus(temperature));
        bodyInfoDetailVo.setTemperaturedesc(statusDesc(bodyInfoDetailVo.getTemperaturestatus()));
        return bodyInfoDetailVo;
    }

    /**
     * 血压判断
     **/
    private BodyInfoDetailVo bloodPressureCompare(BodyInfoDetailVo bodyInfoDetailVo) {
        // 收缩压
        // 正常范围收缩压90～139mmHg
        final int minhighbloodpressure = 90;
        final int maxhighbloodpressure = 139;
        // 舒张压
        // 舒张压60～89mmHg
        final int minlowbloodpressure = 60;
        final int maxlowbloodpressure = 89;
        // 高血压：成人收缩压≥140mmHg和（或）舒张压≥90mmHg
        // 低血压：血压低于90/60mmHg

        if (bodyInfoDetailVo.getLowbloodpressure() > maxlowbloodpressure) {
            bodyInfoDetailVo.setLowbloodpressurestatus(1);
            bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.getLowbloodpressurestatus()));
        } else if (bodyInfoDetailVo.getLowbloodpressure() < minlowbloodpressure) {
            bodyInfoDetailVo.setLowbloodpressurestatus(-1);
            bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.getLowbloodpressurestatus()));
        } else {
            bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.getLowbloodpressurestatus()));
        }

        if (bodyInfoDetailVo.getHighbloodpressure() > maxhighbloodpressure) {
            bodyInfoDetailVo.setHighbloodpressurestatus(1);
            bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.getHighbloodpressurestatus()));
        } else if (bodyInfoDetailVo.getHighbloodpressure() < minhighbloodpressure) {
            bodyInfoDetailVo.setHighbloodpressurestatus(-1);
            bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.getHighbloodpressurestatus()));
        } else {
            bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.getHighbloodpressurestatus()));
        }

        if ((bodyInfoDetailVo.getLowbloodpressurestatus() == 1)
                && (bodyInfoDetailVo.getHighbloodpressurestatus() == 1)) {
            bodyInfoDetailVo.setBloodpressuredesc("该用户为高血压");
        } else if ((bodyInfoDetailVo.getLowbloodpressurestatus() == -1)
                && (bodyInfoDetailVo.getHighbloodpressurestatus() == -1)) {
            bodyInfoDetailVo.setBloodpressuredesc("该用户为低血压");
        } else if ((bodyInfoDetailVo.getLowbloodpressurestatus() == 0)
                && (bodyInfoDetailVo.getHighbloodpressurestatus() == 0)) {
            bodyInfoDetailVo.setBloodpressuredesc("该用户血压正常");
        }

        /*
         * if ((bodyInfoDetailVo.getLowbloodpressure() > maxlowbloodpressure) ||
         * (bodyInfoDetailVo.getHighbloodpressure() > maxhighbloodpressure)) {
         * bodyInfoDetailVo.setLowbloodpressurestatus(1);
         * bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.
         * getLowbloodpressurestatus()));
         * bodyInfoDetailVo.setHighbloodpressurestatus(1);
         * bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.
         * getHighbloodpressurestatus())); } else if
         * ((bodyInfoDetailVo.getLowbloodpressure() < minlowbloodpressure) ||
         * (bodyInfoDetailVo.getHighbloodpressure() < minhighbloodpressure)) {
         * bodyInfoDetailVo.setLowbloodpressurestatus(-1);
         * bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.
         * getLowbloodpressurestatus()));
         * bodyInfoDetailVo.setHighbloodpressurestatus(-1);
         * bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.
         * getHighbloodpressurestatus())); } else {
         * bodyInfoDetailVo.setLowbloodpressuredesc(statusDesc(bodyInfoDetailVo.
         * getLowbloodpressurestatus()));
         * bodyInfoDetailVo.setHighbloodpressuresesc(statusDesc(bodyInfoDetailVo.
         * getHighbloodpressurestatus())); }
         */
        return bodyInfoDetailVo;
    }

    /**
     * 心率判断
     **/
    private int heartratestatus(int heartrate) {
        final int min = 60;
        final int max = 80;
        final int max1 = 100;
        final int max2 = 120;
        if (heartrate < min)
            return -1;
        else if ((heartrate >= min) && (heartrate <= max))
            return 0;
        else if ((heartrate > max) && (heartrate < max1))
            return 1;
        else if ((heartrate > max1) && (heartrate < max2))
            return 2;
        else
            return 4;
    }

    /**
     * 体温判断
     **/
    private int temperaturestatus(double temperature) {
        final Double min = 36.1;
        final Double max = 37.3;
        final Double max1 = 38.1;
        final Double max2 = 39.1;
        final Double max3 = 41.1;
        if (temperature < min)
            return -1;
        else if ((temperature >= min) && (temperature <= max))
            return 0;
        else if ((temperature > max) && (temperature < max1))
            return 1;
        else if ((temperature > max1) && (temperature < max2))
            return 2;
        else if ((temperature > max2) && (temperature < max3))
            return 3;
        else
            return 4;
    }

    /**
     * 状态描述语
     **/
    private String statusDesc(int status) {
        switch (status) {
            case -1:
                return "低";
            case 1:
                return "高";
            case 2:
                return "很高";
            case 3:
                return "严重";
            case 4:
                return "非常严重";
            default:
                return "正常";
        }
    }

    @Override
    public BodyInfoStatisticsVo getBodyStatistics(int userId, Date date) {
        LocalDateTime now = null;
        if (date == null) {
            now = LocalDateTime.now();
        } else {
            now = DateUtils.convertDateToLDT(date);
        }
        final LocalDateTime startWeekDateTime = DateUtils.getDayStart(DateUtils.getWeekBegin(now));
        System.out.println(startWeekDateTime);
        final Date startWeek = DateUtils.convertLDTToDate(startWeekDateTime);
        final Date endWeek = DateUtils.convertLDTToDate(DateUtils.getDayEnd(DateUtils.getWeekEnd(now)));
        final List<TBodyInfo> bodyInfos = bodyInfoMapper.selectByUserId(userId, startWeek, endWeek);
        bodyInfos.forEach(System.out::println);

        final List<BodyInfoVo> bodyInfoVos = fillBodyInfoVo(startWeekDateTime, bodyInfos);

        return new BodyInfoStatisticsVo(bodyInfoVos.stream().map(BodyInfoVo::getTypeName).collect(Collectors.toList()),
                bodyInfoVos);
    }

    /**
     * 填充数据
     **/
    private List<BodyInfoVo> fillBodyInfoVo(LocalDateTime startWeekDateTime, List<TBodyInfo> bodyInfos) {
        final List<BodyInfoVo> bodyInfoVos = new ArrayList<>();
        final List<Object> lowbloodpressures = new ArrayList<>();
        final List<Object> highbloodpressures = new ArrayList<>();
        final List<Object> heartrates = new ArrayList<>();
        final List<Object> temperatures = new ArrayList<>();
        final List<Object> numberOfSetps = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            final Long targetTime = DateUtils.getMilliByTime(DateUtils.plus(startWeekDateTime, i, ChronoUnit.DAYS));
            boolean isSign = false;
            for (final TBodyInfo tBodyInfo : bodyInfos) {
                if (targetTime.equals(DateUtils.getMilliByTime(
                        DateUtils.getDayStart(DateUtils.convertDateToLDT(tBodyInfo.getCreatetime()))))) {
                    // 已打卡
                    isSign = true;
                    lowbloodpressures.add(tBodyInfo.getLowbloodpressure());
                    highbloodpressures.add(tBodyInfo.getHighbloodpressure());
                    heartrates.add(tBodyInfo.getHeartrate());
                    temperatures.add(tBodyInfo.getTemperature());
                    numberOfSetps.add(tBodyInfo.getNumberofstep());
                    break;
                }
            }
            if (!isSign) {
                // 未打卡
                lowbloodpressures.add(0);
                highbloodpressures.add(0);
                heartrates.add(0);
                temperatures.add(0);
                numberOfSetps.add(0);
            }
        }
        bodyInfoVos.add(new BodyInfoVo("舒张压", lowbloodpressures));
        bodyInfoVos.add(new BodyInfoVo("收缩压", highbloodpressures));
        bodyInfoVos.add(new BodyInfoVo("心率", heartrates));
        bodyInfoVos.add(new BodyInfoVo("体温", temperatures));
        bodyInfoVos.add(new BodyInfoVo("步数", numberOfSetps));

        return bodyInfoVos;
    }

}