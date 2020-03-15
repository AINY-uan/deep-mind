package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-03-02 12:52
 * @description 日期操作类
 */
@Slf4j
public class DateUtil {

    /**
     * 验证日期是否合法
     *
     * @param dateStr 需要校验的日期时间字符串
     * @param format  需要校验的日期时间字符串格式
     * @return true: 合法; false: 非法
     */
    public static boolean isValidDate(String dateStr, String format) {

        boolean isValid = true;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("[ERROR]", e);
            isValid = false;
        }
        return isValid;
    }

    /**
     * 获取当前系统时间
     *
     * @return 返回 [yyyyMMddHHmmss] 格式的日期时间字符串
     */
    public static String getCurrentDateTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 按指定格式获取当前系统时间
     *
     * @param format 传入的指定日期格式
     * @return 返回指定格式的日期时间字符串
     */
    public static String getCurrentDateTime(String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取任一日期按指定日期加后的日期
     *
     * @param formatIn  传入的日期格式
     * @param formatOut 传出的日期格式
     * @param dateStr   传入的日期字符串
     * @param num       相加或相减的天数
     * @return 指定格式的日期字符串
     * @throws ParseException ParseException
     */
    public static String getAnyDay(String formatIn, String formatOut, String dateStr, int num) throws ParseException {

        SimpleDateFormat sdfIn = new SimpleDateFormat(formatIn);
        Date date = sdfIn.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        SimpleDateFormat dfOut = new SimpleDateFormat(formatOut);
        return dfOut.format(date);
    }

    /**
     * @param format 传入的日期格式
     * @param num    相加或相减的天数
     * @return 指定格式的日期字符串
     */
    public static String getAnyDay(String format, int num) {

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取指定日期后几个月的日期
     *
     * @param startDate 传入的开始日期
     * @param number    相加或相减的月份数
     * @return 指定格式的日期字符串
     * @throws ParseException ParseException
     */
    public static String getAfterMonth(String startDate, int number) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        Date date = sdf.parse(startDate);
        c.setTime(date);
        c.add(Calendar.MONTH, number);
        return sdf.format(c.getTime());
    }

    /**
     * 计算月份差
     *
     * @param startDate 传入的开始日期
     * @param endDate   传入的结束日期
     * @return 月份差值
     * @throws ParseException ParseException
     */
    public static int getMonthSpace(String startDate, String endDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(sdf.parse(startDate));
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(sdf.parse(endDate));

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    /**
     * 获取两个日期相隔的天数
     *
     * @param startDate 传入的开始日期
     * @param endDate   传入的结束日期
     * @return 两个日期的天数差值
     * @throws ParseException ParseException
     */
    public static int getDateSpace(String startDate, String endDate) throws ParseException {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calst.setTime(sdf.parse(startDate));
        caled.setTime(sdf.parse(endDate));

        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        // 得到两个日期相差的天数

        return ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
    }

    /**
     * 获取两个日期之间的每个日期，包含首尾日期
     *
     * @param dateStart 传入的开始日期
     * @param dateEnd   传入的结束日期
     * @return 传入的开始日期与结束日期之间的每一个日期
     */
    public static List<String> findDates(String dateStart, String dateEnd) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dBegin = sdf.parse(dateStart);
            Date dEnd = sdf.parse(dateEnd);
            List<String> lDate = new ArrayList<>();
            lDate.add(dateStart);
            Calendar calBegin = Calendar.getInstance();
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(dEnd);
            while (dEnd.after(calBegin.getTime())) {
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(sdf.format(calBegin.getTime()));
            }
            return lDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * java.util.Date --> java.time.LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime() {

        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * java.util.Date --> java.time.LocalDate
     */
    public static LocalDate dateToLocalDate() {

        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * java.util.Date --> java.time.LocalTime
     */
    public static LocalTime dateToLocalTime() {

        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }
}
