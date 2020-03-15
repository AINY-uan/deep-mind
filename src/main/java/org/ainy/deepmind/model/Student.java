package org.ainy.deepmind.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-03-30 17:26
 * @description 学生类
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Student {
    /**
     * 学生id
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 班级
     */
    private String grade;
    /**
     * 扩展
     */
    private String ext;
    /**
     * 余额
     */
    private BigDecimal balance;
}
