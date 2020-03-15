package org.ainy.deepmind;

import org.ainy.deepmind.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;

/**
 * @author AINY_uan
 * @description 日期测试
 * @date 2020-03-15 19:22
 */
public class DateTest {

    /**
     * 测试
     */
    @Test
    public void ex1() throws ParseException {

        // 验证日期是否合法
        System.out.println(DateUtil.isValidDate("2020-12-32", "yyyy-MM-dd"));

        // 按固定格式获取日期时间
        System.out.println(DateUtil.getCurrentDateTime());

        // 按指定格式获取日期时间
        System.out.println(DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

        // 获取任一日期按指定日期加后的日期
        System.out.println(DateUtil.getAnyDay("yyyy-MM-dd", "yyyy-MM-dd", "2012-12-12", 5));

        // 获取任一日期按指定日期加后的日期
        System.out.println(DateUtil.getAnyDay("yyyy-MM-dd", 5));

        // 获取指定日期后几个月的日期
        System.out.println(DateUtil.getAfterMonth("20200501", 5));

        // 计算月份差
        System.out.println(DateUtil.getMonthSpace("20200501", "20201212"));

        // 获取两个日期相隔的天数
        System.out.println(DateUtil.getDateSpace("20200501", "20201212"));

        // 获取两个日期之间的每个日期，包含首尾日期
        System.out.println(DateUtil.findDates("20200501", "20200510"));


        System.out.println(DateUtil.dateToLocalDateTime());


        System.out.println(DateUtil.dateToLocalDate());


        System.out.println(DateUtil.dateToLocalTime());
    }
}
