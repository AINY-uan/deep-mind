package org.ainy.deepmind;

import org.ainy.deepmind.util.LocalDateTimeUtil;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author AINY_uan
 * @description LocalDateTime测试
 * @date 2020-03-15 15:25
 */
public class LocalDateTimeTest {

    /**
     * 按指定格式获取当前系统日期时间
     */
    @Test
    public void ex1() {

        System.out.println(LocalDateTimeUtil.getCurrentDateTimeStr("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void ex2() {

        String patternInput = "yyyy-MM-dd";

        String patternOutput = "yyyy-MM-dd";

        // 按天
        System.out.println(LocalDateTimeUtil.getAnyDayStr(
                "2012-12-12",
                patternInput, patternOutput,
                LocalDateTimeUtil.TYPE_DAY,
                5));

        // 按周
        System.out.println(LocalDateTimeUtil.getAnyDayStr(
                "2012-12-12",
                patternInput,
                patternOutput,
                LocalDateTimeUtil.TYPE_WEEK,
                5));

        // 按月
        System.out.println(LocalDateTimeUtil.getAnyDayStr(
                "2012-12-12",
                patternInput,
                patternOutput,
                LocalDateTimeUtil.TYPE_MONTH,
                5));

        // 按年
        System.out.println(LocalDateTimeUtil.getAnyDayStr(
                "2012-12-12",
                patternInput,
                patternOutput,
                LocalDateTimeUtil.TYPE_YEAR,
                5));
    }

    @Test
    public void ex3() {

        LocalDate startDate = LocalDate.of(2012, 12, 12);

        LocalDate endDate = LocalDate.of(2020, 11, 11);

        // 按天
        System.out.println(LocalDateTimeUtil.getAnyDaySpace(startDate, endDate, LocalDateTimeUtil.TYPE_DAY));

        // 按天
        System.out.println(LocalDateTimeUtil.getAnyDaySpace(startDate, endDate, LocalDateTimeUtil.TYPE_WEEK));

        // 按天
        System.out.println(LocalDateTimeUtil.getAnyDaySpace(startDate, endDate, LocalDateTimeUtil.TYPE_MONTH));

        // 按天
        System.out.println(LocalDateTimeUtil.getAnyDaySpace(startDate, endDate, LocalDateTimeUtil.TYPE_YEAR));
    }

    @Test
    public void ex4() {

        LocalDate date = LocalDate.of(2016, 5, 1);

        System.out.println(LocalDateTimeUtil.getFirstDayOfAnyMonth(date, 5));

        System.out.println(LocalDateTimeUtil.getLastDayOfAnyMonth(date, 5));
    }

    @Test
    public void ex5() {

        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(LocalDateTimeUtil.localDateTimeToUdate(dateTime));

        LocalDate date = LocalDate.now();

        System.out.println(LocalDateTimeUtil.localDateToUdate(date));

        System.out.println(LocalDateTimeUtil.localTimeToUdate());
    }
}
