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

    Student a1 = Student.builder().id(1).name("1号").age(22).grade("高一四班").balance(new BigDecimal("598")).build();
    Student a2 = Student.builder().id(2).name("2号").age(20).grade("高一四班").balance(new BigDecimal("666")).build();
    Student a3 = Student.builder().id(3).name("3号").age(25).grade("高一四班").balance(new BigDecimal("404")).ext("特例").build();
    Student a4 = Student.builder().id(4).name("4号").age(24).grade("高一四班").balance(new BigDecimal("200")).build();
    Student a5 = Student.builder().id(5).name("5号").age(22).grade("高一四班").balance(new BigDecimal("500")).build();
    Student a6 = Student.builder().id(6).name("6号").age(23).grade("高一四班").balance(new BigDecimal("999")).ext("特例").build();

    Student b1 = Student.builder().id(1).name("1号").age(21).grade("高一五班").balance(new BigDecimal("444")).build();
    Student b2 = Student.builder().id(2).name("2号").age(23).grade("高一五班").balance(new BigDecimal("361")).build();
    Student b3 = Student.builder().id(3).name("3号").age(20).grade("高一五班").balance(new BigDecimal("502")).ext("特例").build();
    Student b4 = Student.builder().id(4).name("4号").age(24).grade("高一五班").balance(new BigDecimal("403")).build();
    Student b5 = Student.builder().id(5).name("5号").age(25).grade("高一五班").balance(new BigDecimal("800")).build();
    Student b6 = Student.builder().id(6).name("6号").age(19).grade("高一五班").balance(new BigDecimal("198")).ext("特例").build();

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

    @Test
    public void forEach() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        list.forEach(o -> System.out.println(o.toString()));
    }

    @Test
    public void filter() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        List<Student> r1 = list.stream().filter(o -> !(o.getExt() == null || "".equals(o.getExt()))).collect(Collectors.toList());
        r1.forEach(o -> System.out.println(o.toString()));
    }

    @Test
    public void sortByAge() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        System.out.println("按年龄从小到大------------------------------------------------------------------------------");
        List<Student> r1 = list.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        r1.forEach(o -> System.out.println(o.toString()));

        System.out.println("按年龄从大到小------------------------------------------------------------------------------");
        List<Student> r2 = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        r2.forEach(o -> System.out.println(o.toString()));

        System.out.println("按年龄从大到小------------------------------------------------------------------------------");
        List<Student> r3 = r1.stream().sorted(Comparator.comparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r3.forEach(o -> System.out.println(o.toString()));
    }

    @Test
    public void sortByIdThenAge() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        System.out.println("按id从小到大、年龄从小到大--------------------------------------------------------------------");
        List<Student> r1 = list.stream().sorted(Comparator.comparing(Student::getId).thenComparing(Student::getAge)).collect(Collectors.toList());
        r1.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从大到小、年龄从小到大--------------------------------------------------------------------");
        // 先以属性一升序,升序结果进行属性一降序,再进行属性二升序
        List<Student> r2 = list.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge)).collect(Collectors.toList());
        r2.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从大到小、年龄从小到大--------------------------------------------------------------------");
        // 先以属性一降序,再进行属性二升序
        List<Student> r3 = r1.stream().sorted(Comparator.comparing(Student::getId, Comparator.reverseOrder()).thenComparing(Student::getAge)).collect(Collectors.toList());
        r3.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从大到小、年龄从大到小--------------------------------------------------------------------");
        // 先以属性一升序,升序结果进行属性一降序, 再进行属性二降序
        List<Student> r4 = r1.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r4.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从大到小、年龄从大到小--------------------------------------------------------------------");
        // 先以属性一降序,再进行属性二降序
        List<Student> r5 = r1.stream().sorted(Comparator.comparing(Student::getId, Comparator.reverseOrder()).thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r5.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从小到大、年龄从大到小--------------------------------------------------------------------");
        // 先以属性一升序,升序结果进行属性一降序,再进行属性二升序,结果进行属性一降序属性二降序
        List<Student> r6 = r1.stream().sorted(Comparator.comparing(Student::getId).reversed().thenComparing(Student::getAge).reversed()).collect(Collectors.toList());
        r6.forEach(o -> System.out.println(o.toString()));

        System.out.println("按id从小到大、年龄从大到小--------------------------------------------------------------------");
        // 先以属性一升序,再进行属性二降序
        List<Student> r7 = r1.stream().sorted(Comparator.comparing(Student::getId).thenComparing(Student::getAge, Comparator.reverseOrder())).collect(Collectors.toList());
        r7.forEach(o -> System.out.println(o.toString()));

        // 1. Comparator.comparing(类::属性一).reversed();
        // 2. Comparator.comparing(类::属性一,Comparator.reverseOrder());
        // 两种排序是完全不一样的,一定要区分开来 1 是得到排序结果后再排序,2是直接进行排序,很多人会混淆导致理解出错,2更好理解,建议使用2
    }

    @Test
    public void count() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        System.out.println("以下为取最大最小求和平均值--------------------------------------------------------------------");
        System.out.println(list.stream().mapToInt(Student::getAge).max().orElse(0));
        System.out.println(list.stream().mapToInt(Student::getAge).min().orElse(0));
        System.out.println(list.stream().mapToInt(Student::getAge).sum());
        System.out.println(list.stream().mapToInt(Student::getAge).average().orElse(0));
        System.out.println(df.format(list.stream().map(Student::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add))); // 无法过滤BigDecimal为空，需要先过滤掉null，或者重写方法
        System.out.println(list.stream().map(Student::getBalance).reduce(BigDecimal.ZERO, StreamTest::sum)); // 使用重写的求和方法
    }

    @Test
    public void group() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        System.out.println("以下为分组---------------------------------------------------------------------------------");

        Map<String, List<Student>> mapList = list.stream().collect(Collectors.groupingBy(Student::getGrade));

        // 第一个参数：分组按照什么分类
        //
        // 第二个参数：分组最后用什么容器保存返回
        //
        // 第三个参数：按照第一个参数分类后，对应的分类的结果如何收集
        // LinkedHashMap<String, List<Student>> listLinkedHashMap = list1.stream().collect(Collectors.groupingBy(Student::getGrade, LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<String, List<Student>> entry : mapList.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    @Test
    public void getRandomKey() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        System.out.println("以下为获取list对象的某个字段组装成新list-------------------------------------------------------");
        List<Integer> ageList = list.stream().map(Student::getAge).collect(Collectors.toList());
        System.out.println(ageList);
    }

    @Test
    public void distinct() {

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("A");
        list.add("C");
        list.add("D");

        System.out.println(list.stream().distinct().collect(Collectors.toList()));
    }

    @Test
    public void list2Map() {

        List<Student> list = new ArrayList<>(12);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);

        /*
         * List -> Map
         * 需要注意的是：
         * toMap 如果集合对象有重复的key，会报错Duplicate key ....
         *  user1,user2的id都为1。
         *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
         */
        Map<Integer, Student> studentMap = list.stream().collect(Collectors.toMap(Student::getId, a -> a, (k1, k2) -> k1));

        System.out.println(studentMap);
    }
}
