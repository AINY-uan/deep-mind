package org.ainy.deepmind;

import org.ainy.deepmind.model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.ainy.deepmind.util.ListUtil.convertBean2Map;
import static org.ainy.deepmind.util.ListUtil.convertListBean2ListMap;

/**
 * @author AINY_uan
 * @description List测试
 * @date 2020-03-15 14:22
 */
public class ListTest {

    /**
     * 差集
     * 求List1中有的但是List2中没有的元素
     */
    @Test
    public void differenceSet() {

        List<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        List<String> list2 = new ArrayList<>();
        list2.add("C");
        list2.add("D");
        list2.add("E");

        list1.removeAll(list2);
        System.out.println(list1);
    }

    /**
     * 并集
     * 不去重
     */
    @Test
    public void union() {

        List<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        List<String> list2 = new ArrayList<>();
        list2.add("C");
        list2.add("D");
        list2.add("E");

        list1.addAll(list2);
        System.out.println(list1);
    }

    /**
     * 并集
     * 去重
     */
    @Test
    public void unionAndDistinct() {

        List<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        List<String> list2 = new ArrayList<>();
        list2.add("C");
        list2.add("D");
        list2.add("E");

        list1.removeAll(list2);
        list1.addAll(list2);
        System.out.println(list1);
    }

    /**
     * 交集
     */
    @Test
    public void intersection() {

        List<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        List<String> list2 = new ArrayList<>();
        list2.add("C");
        list2.add("D");
        list2.add("E");

        list1.retainAll(list2);
        System.out.println(list1);
    }

    @Test
    public void ex1() {

        Student s1 = new Student();
        s1.setName("小米");
        s1.setSex("女");
        s1.setAge(20);
        Student s2 = new Student();
        s2.setName("大米");
        s2.setSex("男");
        s2.setAge(22);
        List<Object> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        try {
            System.out.println(convertBean2Map(s1));
            System.out.println("----------------------------------------");
            System.out.println(convertBean2Map(s2));
            System.out.println("----------------------------------------");
            System.out.println(convertListBean2ListMap(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
