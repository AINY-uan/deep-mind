package org.ainy.deepmind.model;

/**
 * @author AINY_uan
 * @description 子类
 * @date 2020-03-15 00:20
 */
public class Son extends Father {

    private String name;

    static {
        System.out.println("子类的静态代码块");
    }

    {
        System.out.println("子类的构造块");
    }

    public Son() {
        System.out.println("子类的构造函数");
    }

    @Override
    public void function() {

        System.out.println("我是你爹");
    }
}
