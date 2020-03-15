package org.ainy.deepmind.model;

/**
 * @author AINY_uan
 * @description 子类
 * @date 2020-03-15 00:20
 */
public class Son extends Father {

    static {
        System.out.println("子类的静态代码块");
    }

    {
        System.out.println("子类的构造块");
    }

    public Son() {
        System.out.println("子类的构造函数");
    }
}
