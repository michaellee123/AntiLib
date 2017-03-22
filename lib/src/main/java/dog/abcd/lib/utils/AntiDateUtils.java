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

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat yyyyMMddForChina = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat yyyyMMddHHmmssForChina = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    private AntiDateUtils() {

    }

    /**
     * 获取系统时间
     *
     * @return Long类型
     */
    public static long getCurrentDate() {
        return new Date().getTime();
    }

    /**
     * string转long
     *
     * @param dateString 时间字符串
     * @param dateFormat SimpleDateFormat
     * @return 如果出错则返回当前日期的long，并打印错误信息
     */
    public static long convertToLong(String dateString, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return getCurrentDate();
        }
    }

    /**
     * string转date
     *
     * @param dateString 时间字符串
     * @param dateFormat SimpleDateFormat
     * @return 如果出错则返回当前日期，并打印错误信息
     */
    public static Date convertToDate(String dateString, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * long转String
     *
     * @param dateLong   时间戳
     * @param dateFormat SimpleDateFormat
     * @return
     */
    public static String convertToString(long dateLong, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(dateLong));
    }

    /**
     * date转String
     *
     * @param date       时间
     * @param dateFormat SimpleDateFormat
     * @return
     */
    public static String convertToString(Date date, SimpleDateFormat dateFormat) {
        return dateFormat.format(date);
    }

    /**
     * 得到本周周一
     *
     * @return
     */
    public static String getMondayOfThisWeek(SimpleDateFormat dateFormat) {
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
        return convertToString(c.getTime(), dateFormat);
    }

    /**
     * 得到本周周日
     *
     * @return
     */
    public static String getSundayOfThisWeek(SimpleDateFormat dateFormat) {
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
        return convertToString(c.getTime(), dateFormat);
    }

    /**
     * 获取当前一周的开头和结尾
     *
     * @return
     */
    public static String getThisWeek(SimpleDateFormat dateFormat) {
        return getMondayOfThisWeek(dateFormat) + "~" + getSundayOfThisWeek(dateFormat);
    }

    /**
     * 传入一天的值，获取昨天
     *
     * @param today      传入的时间需要和dateformat格式相同，返回相同格式
     * @param dateFormat
     * @return
     */
    public static String getYestoday(String today, SimpleDateFormat dateFormat) {
        long time = convertToLong(today, dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(new java.sql.Date(time));
        c.add(Calendar.DAY_OF_YEAR, -1);
        return convertToString(c.getTime(), dateFormat);
    }


    /**
     * 传入一天的值，获取明天
     *
     * @param today      传入的时间需要和dateformat格式相同，返回相同格式
     * @param dateFormat
     * @return
     */
    public static String getTomorrow(String today, SimpleDateFormat dateFormat) {
        long time = convertToLong(today, dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(new java.sql.Date(time));
        c.add(Calendar.DAY_OF_YEAR, 1);
        return convertToString(c.getTime(), dateFormat);
    }

    /**
     * 判断是否时间前后
     *
     * @param big
     * @param small
     * @param dateFormat
     * @return 返回TRUE则代表前面那一个时间更晚
     */
    public static boolean isBigger(String big, String small, SimpleDateFormat dateFormat) {
        long bigTime = convertToLong(big, dateFormat);
        long smallTime = convertToLong(small, dateFormat);
        return bigTime > smallTime;
    }

}