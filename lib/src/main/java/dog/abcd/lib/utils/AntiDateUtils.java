package dog.abcd.lib.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>时间操作</b><br>
 * 包括时间的获取和转换
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午11:18:21
 */
@SuppressLint("SimpleDateFormat")
public class AntiDateUtils {

	private static final String TAG = "AntiDateUtils";

	private AntiDateUtils() {

	}

	private static SimpleDateFormat sf = null;

	/**
	 * 获取系统时间
	 * 
	 * @return Long类型
	 */
	public static long getCurrentDate() {
		Date d = new Date();
		return d.getTime();
	}

	/**
	 * 时间转字符串
	 * 
	 * @param time
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToString(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(d);
	}

	/**
	 * 时间转字符串
	 * 
	 * @param time
	 * @return yyyy-MM-dd
	 */
	public static String dateToStringSmall(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}

	/**
	 * 时间转字符串
	 * 
	 * @param time
	 * @return yyyy年MM月dd日
	 */
	public static String dateToStringChina(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy年MM月dd日");
		return sf.format(d);
	}

	/**
	 * 字符串转Long
	 * 
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long stringToDate(String time) {
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			AntiLog.e(TAG, e.toString());
		}
		return date.getTime();
	}

	/**
	 * 字符串转Long
	 * 
	 * @param time
	 *            yyyy-MM-dd
	 * @return
	 */
	public static long stringToDateSmall(String time) {
		sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			AntiLog.e(TAG, e.toString());
		}
		return date.getTime();
	}

	/**
	 * 字符串转Long
	 * 
	 * @param time
	 *            yyyy年MM月dd日
	 * @return
	 */
	public static long stringToDateSmallChina(String time) {
		sf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			AntiLog.e(TAG, e.toString());
		}
		return date.getTime();
	}


	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		int weekend = 1 - weekDay;
		c.add(Calendar.DATE, weekend);
		return AntiDateUtils.dateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		int weekend = 7 - weekDay;
		c.add(Calendar.DATE, weekend);
		return AntiDateUtils.dateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 获取当前一周的开头和结尾
	 *
	 * @return yyyy-MM-dd~yyyy-MM-dd
	 */
	public static String getThisWeek() {
		return getMondayOfThisWeek() + "~" + getSundayOfThisWeek();
	}

	/**
	 * 传入一天的值，获取昨天YYYY-MM-DD
	 *
	 * @param today
	 * @return
	 */
	public static String getYestoday(String today) {
		long time = AntiDateUtils.stringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new java.sql.Date(time));
		c.add(Calendar.DAY_OF_YEAR, -1);
		return AntiDateUtils.dateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取昨天年月日
	 *
	 * @param today
	 * @return
	 */
	public static String getYestodayChina(String today) {
		long time = AntiDateUtils.stringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new java.sql.Date(time));
		c.add(Calendar.DAY_OF_YEAR, -1);
		return AntiDateUtils.dateToStringChina(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取明天YYYY-MM-DD
	 *
	 * @param today
	 * @return
	 */
	public static String getTomorrow(String today) {
		long time = AntiDateUtils.stringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new java.sql.Date(time));
		c.add(Calendar.DAY_OF_YEAR, 1);
		return AntiDateUtils.dateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 判断是否时间前后
	 *
	 * @param big
	 * @param small
	 * @return
	 */
	public static boolean isBigger(String big, String small) {
		long bigTime = AntiDateUtils.stringToDateSmall(big);
		long smallTime = AntiDateUtils.stringToDateSmall(small);
		return bigTime > smallTime;
	}

	/**
	 * 判断是否时间前后
	 *
	 * @param big
	 * @param small
	 * @return
	 */
	public static boolean isBiggerChina(String big, String small) {
		long bigTime = AntiDateUtils.stringToDateSmallChina(big);
		long smallTime = AntiDateUtils.stringToDateSmallChina(small);
		return bigTime > smallTime;
	}
}