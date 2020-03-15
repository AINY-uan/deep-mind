package org.ainy.deepmind.other;

/**
 * @author AINY_uan
 * @description JDK8新特性
 * @date 2020-03-15 00:48
 */
public interface CustomInterface {

    /**
     * 测试方法
     */
    void test();

    /**
     * 默认方法
     */
    default void xxx() {
        System.out.println("我是JDK8的接口新特性");
    }
}
