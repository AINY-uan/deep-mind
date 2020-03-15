package org.ainy.deepmind;

import org.ainy.deepmind.model.Student;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author AINY_uan
 * @description JDK8之Stream流测试
 * @date 2020-03-15 00:25
 */
public class StreamTest {

    /**
     * 保留两位小数点
     */
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Test
    public void ex1() {

        List<Student> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student s = new Student();
            s.setId(i);
            s.setName(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            s.setAge((int) (Math.random() * 100));
            s.setGrade("高一四班");
            if (i % 3 == 0) {
                s.setExt("特例");
            }
            s.setBalance(BigDecimal.valueOf(new Random().nextDouble() * 100 + 100).setScale(BigDecimal.ROUND_HALF_UP, 2));
            list1.add(s);
        }

        List<Student> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student s = new Student();
            s.setId(i);
            s.setName(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            s.setAge((int) (Math.random() * 100));
            s.setGrade("高一五班");
            if (i % 3 == 0) {
                s.setExt("特例");
            }
            s.setBalance(BigDecimal.valueOf(new Random().nextDouble() * 100 + 100).setScale(BigDecimal.ROUND_HALF_UP, 2));
            list2.add(s);
        }

        list1.addAll(list2);

        System.out.println("---------------------------以下为List.forEach直接输出-----------------------------");
        list1.forEach(o -> System.out.println(o.toString())); // 直接输出
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为去除EXT字段为Null或者空字符串----------------------------");
        List<Student> r1 = list1.stream().filter(o -> !(o.getExt() == null || "".equals(o.getExt()))).collect(Collectors.toList());
        r1.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为按年龄从小到大排序----------------------------");
        List<Student> r2 = r1.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        r2.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为按年龄从大到小排序----------------------------");
        // 先以年龄升序，得到结果后再以年龄降序
        List<Student> r3 = r1.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        r3.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n");
        // 以年龄降序
        List<Student> r4 = r1.stream().sorted(Comparator.comparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r4.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为按id，年龄从小到大排序----------------------------");
        // 以id升序、年龄升序
        List<Student> r5 = r1.stream().sorted(Comparator.comparing(Student::getId).thenComparing(Student::getAge)).collect(Collectors.toList());
        r5.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为按id从大到小，年龄从小到大排序----------------------------");
        // 先以属性一升序,升序结果进行属性一降序,再进行属性二升序
        List<Student> r6 = r1.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge)).collect(Collectors.toList());
        r6.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n");
        // 先以属性一降序,再进行属性二升序
        List<Student> r7 = r1.stream().sorted(Comparator.comparing(Student::getId, Comparator.reverseOrder()).thenComparing(Student::getAge)).collect(Collectors.toList());
        r7.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        System.out.println("---------------------------以下为按id从大到小，年龄从大到小排序----------------------------");
        // 先以属性一升序,升序结果进行属性一降序, 再进行属性二降序
        List<Student> r8 = r1.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r8.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n");
        // 先以属性一降序,再进行属性二降序
        List<Student> r9 = r1.stream().sorted(Comparator.comparing(Student::getId, Comparator.reverseOrder()).thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r9.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");


        System.out.println("---------------------------以下为按id从小到大，年龄从大到小排序----------------------------");
        // 先以属性一升序,升序结果进行属性一降序,再进行属性二升序,结果进行属性一降序属性二降序
        List<Student> r10 = r1.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge).reversed()).collect(Collectors.toList());
        r10.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n");
        // 先以属性一升序,再进行属性二降序
        List<Student> r11 = r1.stream().sorted(Comparator.comparing(Student::getId).thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r11.forEach(o -> System.out.println(o.toString()));
        System.out.println("\n\n\n");

        // 1. Comparator.comparing(类::属性一).reversed();
        // 2. Comparator.comparing(类::属性一,Comparator.reverseOrder());
        // 两种排序是完全不一样的,一定要区分开来 1 是得到排序结果后再排序,2是直接进行排序,很多人会混淆导致理解出错,2更好理解,建议使用2


        System.out.println("---------------------------以下为取最大最小求和平均值------------------------------");
        System.out.println(r2.stream().mapToInt(Student::getAge).max().orElse(0));
        System.out.println(r2.stream().mapToInt(Student::getAge).min().orElse(0));
        System.out.println(r2.stream().mapToInt(Student::getAge).sum());
        System.out.println(r2.stream().mapToInt(Student::getAge).average().orElse(0));
        System.out.println(df.format(r2.stream().map(Student::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add))); // 无法过滤BigDecimal为空，需要先过滤掉null，或者重写方法
        System.out.println(r2.stream().map(Student::getBalance).reduce(BigDecimal.ZERO, StreamTest::sum)); // 使用重写的求和方法

        System.out.println("---------------------------以下为分组------------------------------");
        Map<String, List<Student>> mapList = list1.stream().collect(Collectors.groupingBy(Student::getGrade));
        // 第一个参数：分组按照什么分类
        //
        // 第二个参数：分组最后用什么容器保存返回
        //
        // 第三个参数：按照第一个参数分类后，对应的分类的结果如何收集
        // LinkedHashMap<String, List<Student>> listLinkedHashMap = list1.stream().collect(Collectors.groupingBy(Student::getGrade, LinkedHashMap::new, Collectors.toList()));
        for (Map.Entry<String, List<Student>> entry : mapList.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println("----------------以下为获取list对象的某个字段组装成新list--------------------");
        List<Integer> ageList = r2.stream().map(Student::getAge).collect(Collectors.toList());
        System.out.println(ageList);

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("A");
        list.add("C");
        list.add("D");
        System.out.println("---------------------------以下为去重------------------------------");
        System.out.println(list.stream().distinct().collect(Collectors.toList()));

        /*
         * List -> Map
         * 需要注意的是：
         * toMap 如果集合对象有重复的key，会报错Duplicate key ....
         *  user1,user2的id都为1。
         *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
         */
//        Map<Integer, Student> studentMap = r2.stream().collect(Collectors.toMap(Student::getId, a -> a, (k1, k2) -> k1));
    }


    private static BigDecimal ifNullSet0(BigDecimal in) {

        if (in != null) {
            return in;
        }
        return BigDecimal.ZERO;
    }

    private static BigDecimal sum(BigDecimal... in) {

        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal bigDecimal : in) {
            result = result.add(ifNullSet0(bigDecimal));
        }
        return result;
    }
}
