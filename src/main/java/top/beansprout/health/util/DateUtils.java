package top.beansprout.health.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: DateUtils</p>
 * <p>Description: 时间工具类</p>
 *
 * @author beansprout
 * @version 1.0
 * @date 2020/3/19 23:14
 */
@Slf4j
public class DateUtils {

	/** HH:mm **/
	public static final String HHmm = "HH:mm";
	/** HH:mm:ss **/
	public static final String HHmmss = "HH:mm:ss";
	/** HH:mm:ss a **/
	public static final String HHmmssa = "HH:mm:ss a";

	/** yyyyMM **/
	public static final String yyyyMM = "yyyyMM";
	/** yyyyMMdd **/
	public static final String yyyyMMdd = "yyyyMMdd";
	/** yyyyMMddHH **/
	public static final String yyyyMMddHH = "yyyyMMddHH";
	/** yyyyMMddHHmm **/
	public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
	/** yyyyMMddHHmmss **/
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	/** yyyyMMddHHmmss.SSS **/
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmss.SSS";
	/** yyyyMMddTHHmmss.SSSZ **/
	public static final String yyyyMMddTHHmmssSSSZ = "yyyyMMdd'T'HHmmss.SSS'Z'";

	/** MM-dd HH:mm **/
	public static final String MMddHHmm_LINE = "MM-dd HH:mm";
	/** yyyy-MM **/
	public static final String yyyyMM_LINE = "yyyy-MM";
	/** yyyy-MM-dd **/
	public static final String yyyyMMdd_LINE = "yyyy-MM-dd";
	/** yyyy-MM-dd HH **/
	public static final String yyyyMMddHH_LINE = "yyyy-MM-dd HH";
	/** yyyy-MM-dd HH:mm **/
	public static final String yyyyMMddHHmm_LINE = "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mm:ss **/
	public static final String yyyyMMddHHmmss_LINE = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-ddTHH:mm:ss **/
	public static final String yyyyMMddTHHmmss_LINE = "yyyy-MM-dd'T'HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.SSS **/
	public static final String yyyyMMddHHmmssSSS_LINE = "yyyy-MM-dd HH:mm:ss.SSS";
	/** yyyy-MM-ddTHH:mm:ss.SSSZ **/
	public static final String yyyyMMddTHHmmssSSSZ_LINE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/** yyyy.MM **/
	public static final String yyyyMM_POINT = "yyyy.MM";
	/** yyyy.MM.dd **/
	public static final String yyyyMMdd_POINT = "yyyy.MM.dd";
	/** yyyy.MM.dd HH **/
	public static final String yyyyMMddHH_POINT = "yyyy.MM.dd HH";
	/** yyyy.MM.dd HH:mm **/
	public static final String yyyyMMddHHmm_POINT = "yyyy.MM.dd HH:mm";
	/** yyyy.MM.dd HH:mm:ss **/
	public static final String yyyyMMddHHmmss_POINT = "yyyy.MM.dd HH:mm:ss";
	/** yyyy.MM.dd HH:mm:ss.SSS **/
	public static final String yyyyMMddHHmmssSSS_POINT = "yyyy.MM.dd HH:mm:ss.SSS";
	/** yyyy.MM.ddTHH:mm:ss.SSSZ **/
	public static final String yyyyMMddTHHmmssSSSZ_POINT = "yyyy.MM.dd'T'HH:mm:ss.SSS'Z'";

	/** MM/dd/yyyy, HH:mm:ss a **/
	public static final String MMddyyyyHHmmssa_SLASH = "MM/dd/yyyy, HH:mm:ss a";
	/** MM月MM **/
	public static final String yyyyMM_SLASH = "yyyy/MM";
	/** yyyy/MM/dd **/
	public static final String yyyyMMdd_SLASH = "yyyy/MM/dd";
	/** yyyy/MM/dd HH **/
	public static final String yyyyMMddHH_SLASH = "yyyy/MM/dd HH";
	/** yyyy/MM/dd HH:mm **/
	public static final String yyyyMMddHHmm_SLASH = "yyyy/MM/dd HH:mm";
	/** yyyy/MM/dd HH:mm:ss **/
	public static final String yyyyMMddHHmmss_SLASH = "yyyy/MM/dd HH:mm:ss";
	/** yyyy/MM/dd HH:mm:ss.SSS **/
	public static final String yyyyMMddHHmmssSSS_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";
	/** yyyy/MM/ddTHH:mm:ss.SSSZ **/
	public static final String yyyyMMddTHHmmssSSSZ_SLASH = "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'";

	/** yyyy年MM月 **/
	public static final String yyyyMM_UNIT = "yyyy年MM月";
	/** yyyy年MM月dd日 **/
	public static final String yyyyMMdd_UNIT = "yyyy年MM月dd日";
	/** yyyy年MM月dd日 HH **/
	public static final String yyyyMMddHH_UNIT = "yyyy年MM月dd日 HH";
	/** yyyy年MM月dd日 HH:mm **/
	public static final String yyyyMMddHHmm_UNIT = "yyyy年MM月dd日 HH:mm";
	/** yyyy年MM月dd日 HH:mm:ss **/
	public static final String yyyyMMddHHmmss_UNIT = "yyyy年MM月dd日 HH:mm:ss";
	/** yyyy年MM月dd日 HH:mm:ss.SSS **/
	public static final String yyyyMMddHHmmssSSS_UNIT = "yyyy年MM月dd日 HH:mm:ss.SSS";
	/** yyyy年MM月dd日THH:mm:ss.SSSZ **/
	public static final String yyyyMMddTHHmmssSSSZ_UNIT = "yyyy年MM月dd日'T'HH:mm:ss.SSS'Z'";

	public static final long SECONDS_TO_MINUTE = 60;
	public static final long MINUTES_TO_HOUR = 60;
	public static final long HOUR_TO_DAY = 24;
	public static final long DAYS_TO_WEEK = 7;
	public static final long DAYS_TO_MONTH = 31;
	public static final long DAYS_TO_YEAR = 365;
	public static final long MONTHES_TO_YEAR = 12;

	public static final long SECONDS_TO_HOUR = SECONDS_TO_MINUTE * MINUTES_TO_HOUR;
	public static final long SECONDS_TO_DAY = SECONDS_TO_MINUTE * MINUTES_TO_HOUR * HOUR_TO_DAY;
	public static final long SECONDS_TO_WEEK = SECONDS_TO_MINUTE * MINUTES_TO_HOUR * HOUR_TO_DAY * DAYS_TO_WEEK;
	public static final long SECONDS_TO_MONTH = SECONDS_TO_MINUTE * MINUTES_TO_HOUR * HOUR_TO_DAY * DAYS_TO_MONTH;// 暂定义位每月31天
	public static final long SECONDS_TO_YEAR = SECONDS_TO_MINUTE * MINUTES_TO_HOUR * HOUR_TO_DAY * DAYS_TO_YEAR;

	private static final String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private static final String[] weekDaysName_en = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday" };
	private static final String[] monthName = { "一月份", "二月份", "三月份", "四月份", "五月份", "六月份", "七月份", "八月份", "九月份", "十月份",
			"十一月份", "十二月份" };

	/**
	 * 格式化时间为LocalDateTime
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static LocalDateTime parse(String str, String pattern) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(str, formatter);
	}

	/**
	 * 格式化时间为Date
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String str, String pattern) {
		return convertLDTToDate(parse(str, pattern));
	}

	/**
	 * Date转换为LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime convertDateToLDT(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * LocalDateTime转换为Date
	 *
	 * @param time
	 * @return
	 */
	public static Date convertLDTToDate(LocalDateTime time) {
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取指定日期的毫秒
	 * @param time
	 * @return
	 */
	public static Long getMilliByTime(LocalDateTime time) {
		return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 获取指定日期的秒
	 * @param time
	 * @return
	 */
	public static Long getSecondsByTime(LocalDateTime time) {
		return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

	/**
	 * 获取指定时间的指定格式
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatTime(LocalDateTime time, String pattern) {
		return time.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 获取当前时间的指定格式
	 * @param pattern
	 * @return
	 */
	public static String formatNow(String pattern) {
		return formatTime(LocalDateTime.now(), pattern);
	}

	/**
	 * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
	 * @param time
	 * @param number
	 * @param field
	 * @return
	 */
	public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
		return time.plus(number, field);
	}

	/**
	 * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
	 * @param time
	 * @param number
	 * @param field
	 * @return
	 */
	public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
		return time.minus(number, field);
	}

	/**
	 * 获取两个日期的差  field参数为ChronoUnit.*
	 * @param startTime
	 * @param endTime
	 * @param field     单位(年月日时分秒)
	 * @return
	 */
	public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
		final Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
		if (field == ChronoUnit.YEARS)
			return period.getYears();
		if (field == ChronoUnit.MONTHS)
			return period.getYears() * 12 + period.getMonths();
		return field.between(startTime, endTime);
	}

	/**
	 * 获取一天的开始时间，2017,7,22 00:00
	 * @param time
	 * @return
	 */
	public static LocalDateTime getDayStart(LocalDateTime time) {
		return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	/**
	 * 获取一天的结束时间，2017,7,22 23:59:59.999999999
	 * @param time
	 * @return
	 */
	public static LocalDateTime getDayEnd(LocalDateTime time) {
		return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
	}

	/**
	 * 获得指定时间的周一日期
	 * @param time
	 * @return
	 */
	public static LocalDateTime getWeekBegin(LocalDateTime time) {
		return time.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
	}

	/**
	 * 获得指定时间的周日日期
	 * @param time
	 * @return
	 */
	public static LocalDateTime getWeekEnd(LocalDateTime time) {
		return time.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
	}

}