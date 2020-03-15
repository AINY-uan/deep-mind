package org.ainy.deepmind;

import org.ainy.deepmind.model.Father;
import org.ainy.deepmind.model.Son;
import org.ainy.deepmind.util.GetIpAddress;
import org.ainy.deepmind.util.PropertyUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author AINY_uan
 * @description 测试类
 * @date 2020-03-14 22:28
 */
public class DeepMind {

    private synchronized void printNumber1(int i) {
        try {
            this.notify();
            System.out.println(Thread.currentThread().getName() + " " + i);
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多线程交替打印
     */
    @Test
    public void ex1() {

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                printNumber1(i);
            }
        });

        t1.setName("Thread t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                printNumber1(i);
            }
        });

        t2.setName("Thread t2");
        t2.start();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void printNumber2(int i) {
        try {
            synchronized (this) {
                this.notify();
                System.out.println(Thread.currentThread().getName() + " " + i);
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多线程交替打印
     */
    @Test
    public void ex2() {

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                printNumber2(i);
            }
        });

        t1.setName("Thread t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                printNumber2(i);
            }
        });

        t2.setName("Thread t2");
        t2.start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ReentrantLock rl = new ReentrantLock();
    private Condition condition = rl.newCondition();

    public void printNumber3(int i) {
        try {
            rl.lock();
            condition.signal();
            System.out.println(Thread.currentThread().getName() + " " + i);
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rl.unlock();
        }
    }

    /**
     * 多线程交替打印
     */
    @Test
    public void ex3() {

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                printNumber3(i);
            }
        });

        t1.setName("Thread t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                printNumber3(i);
            }
        });

        t2.setName("Thread t2");
        t2.start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * BigDecimal测试
     */
    @Test
    public void ex4() {

        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(3);
        BigDecimal divide = a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        System.out.println(numberFormat.format(divide.doubleValue()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 格式化百分比
     */
    @Test
    public void ex5() {

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        int a = 12;
        long b = 111L;
        System.out.println(nf.format((double) a / b));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int plus(int i) {

        try {
            System.out.println("try");
            ++i;
        } finally {
            System.out.println("finally");
            ++i;
        }
        System.out.println("return");
        return i;
    }

    /**
     * 测试 try finally
     */
    @Test
    public void ex6() {

        System.out.println(plus(5));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 测试static关键字
     */
    @Test
    public void ex7() {

        Father a = new Son();
        System.out.println("-----------------------------");
        a = new Son();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ip地址测试
     */
    @Test
    public void ex8() {

        System.out.println(GetIpAddress.getInetAddress());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * properties测试
     */
    @Test
    public void ex9() {

        System.out.println(PropertyUtil.getProperty("s0"));

        System.out.println(PropertyUtil.getProperty("s2", "123"));
    }
}
