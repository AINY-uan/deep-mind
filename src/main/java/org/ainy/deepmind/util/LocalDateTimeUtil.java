/*
 * 使用Duration进行 day,hour,minute,second等的计算
 * 使用Period进行Year,Month的计算
 */
package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-05-12 17:51
 * @description 适用于JDK8及其以上版本的日期时间工具类
 */
@Slf4j
public class LocalDateTimeUtil {

    /**
     * 按天
     */
    public static final int TYPE_DAY = 0;
    /**
     * 按周
     */
    public static final int TYPE_WEEK = 1;
    /**
     * 按月
     */
    public static final int TYPE_MONTH = 2;
    /**
     * 按年
     */
    public static final int TYPE_YEAR = 3;

    /**
     * 按指定格式获取当前系统日期时间
     *
     * @param pattern 日期时间格式
     * @return 当前系统日期时间
     */
    public static String getCurrentDateTimeStr(String pattern) {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取任意日期按指定天数相加后的日期
     *
     * @param dateTimeInputStr 传入的日期字符串，可为空，为空时默认当前系统日期
     * @param patternInput     传入的日期字符串格式
     * @param patternOutput    传出的日期字符串格式
     * @param type             0-按天加，1-按周加，2-按月加，3-按年加；为空时默认按天加
     * @param num              指定天数
     * @return 指定格式的日期字符串
     */
    public static String getAnyDayStr(String dateTimeInputStr,
                                      String patternInput,
                                      String patternOutput,
                                      Integer type,
                                      long num) {

        LocalDate localDate;
        if (StringUtils.isEmpty(dateTimeInputStr)) {
            localDate = LocalDate.now();
        } else {
            localDate = LocalDate.parse(dateTimeInputStr, DateTimeFormatter.ofPattern(patternInput));
        }

        LocalDate result;

        switch (type) {
            case TYPE_WEEK:
                result = localDate.plusWeeks(num);
                break;
            case TYPE_MONTH:
                result = localDate.plusMonths(num);
                break;
            case TYPE_YEAR:
                result = localDate.plusYears(num);
                break;
            default:
                result = localDate.plusDays(num);
        }
        return result.format(DateTimeFormatter.ofPattern(patternOutput));
    }

    /**
     * 天/月/年数差值
     *
     * @param start 开始日期
     * @param end   结束日期
     * @param type  1-计算两个LocalDate的周数差，2-计算两个LocalDate的月份差，3-计算两个日期的年份查，其他-计算两个LocalDate的天数差
     * @return 天数差值（月份差值）
     */
    public static long getAnyDaySpace(LocalDate start, LocalDate end, Integer type) {

        long space;

        switch (type) {
            case TYPE_WEEK:
                space = ChronoUnit.WEEKS.between(start, end);
                break;
            case TYPE_MONTH:
                space = ChronoUnit.MONTHS.between(start, end);
                break;
            case TYPE_YEAR:
                space = ChronoUnit.YEARS.between(start, end);
                break;
            default:
                /*end.toEpochDay() - start.toEpochDay();*/
                space = ChronoUnit.DAYS.between(start, end);
        }

        return space;
    }

    /**
     * 获取任意月的第一天
     *
     * @param localDate 日期
     * @param num       月份
     * @return 传入的LocalDate相差月份数的第一天
     */
    public static LocalDate getFirstDayOfAnyMonth(LocalDate localDate, long num) {

        return localDate.plusMonths(num).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取任意月的最后一天
     *
     * @param localDate 日期
     * @param num       月份
     * @return 传入的LocalDate相差月份数的最后一天
     */
    public static LocalDate getLastDayOfAnyMonth(LocalDate localDate, long num) {

        return localDate.plusMonths(num).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * java.time.LocalDateTime --> java.util.Date
     */
    public static Date localDateTimeToUdate(LocalDateTime localDateTime) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();

        return Date.from(instant);
    }


    /**
     * java.time.LocalDate --> java.util.Date
     */
    public static Date localDateToUdate(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * java.time.LocalTime --> java.util.Date
     */
    public static Date localTimeToUdate() {

        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();

        return Date.from(instant);
    }
}
