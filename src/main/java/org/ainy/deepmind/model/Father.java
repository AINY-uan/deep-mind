package org.ainy.deepmind.model;

/**
 * @author AINY_uan
 * @description 父类
 * @date 2020-03-15 00:18
 */
public class Father {

    private String name;

    static {
        System.out.println("父类的静态代码块");
    }

    {
        System.out.println("父类的构造块");
    }

    public Father() {
        System.out.println("父类的构造函数");
    }

    public void function() {

        System.out.println("我是你爹");
    }
}
