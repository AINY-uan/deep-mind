package org.ainy.deepmind.util;

import java.lang.reflect.Field;

/**
 * @author 阿拉丁省油的灯
 * @description JavaBean操作工具类
 * @date 2018-11-12 15:12
 */
public class BeanUtil {

    /**
     * 复制名称相同类型相同字段的值
     */
    public static <T1, T> T copyData(T1 srcObj, Class<T> targetClass) {

        // 获取源数据的类
        Class<?> srcClass = srcObj.getClass();
        // 创建一个目标数据实例
        final T targetObj = getInstance(targetClass);

        // 获取clazz1和clazz2中的属性
        Field[] srcFields = srcClass.getDeclaredFields();
        Field[] targetFlelds = targetClass.getDeclaredFields();

        // 遍历srcFields
        for (Field f1 : srcFields) {
            // 遍历targetFlelds，逐字段匹配
            for (Field f2 : targetFlelds) {
                // 复制字段
                copyField(srcObj, targetObj, f1, f2);
            }
        }
        return targetObj;
    }

    /**
     * 按照字段表复制相同名称相同类型的字段的值
     */
    public static <T1, T> T copyData(T1 obj, Class<T> clazz2, String[] fieldNames) {

        // 获取源数据的类
        Class<?> clazz1 = obj.getClass();

        // 创建一个目标数据实例
        final T obj2 = getInstance(clazz2);

        // 获取clazz1和clazz2中的属性
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();

        // 遍历字段列表
        for (String fieldName : fieldNames) {

            // 遍历fields1
            for (Field f1 : fields1) {

                // 找到这个字段（找不到就不用遍历fields2）
                if (fieldName.equals(f1.getName())) {

                    // 遍历fields2，逐字段匹配
                    for (Field f2 : fields2) {

                        // 在fields2中也要有这个字段

                        if (fieldName.equals(f2.getName())) {
                            // 复制字段
                            copyField(obj, obj2, f1, f2);
                        }
                    }
                }
            }
        }
        return obj2;
    }

    /**
     * 复制相同名称相同类型的字段的值
     */
    private static <T1, T> void copyField(T1 obj, T obj2, Field f1, Field f2) {

        try {
            // 字段名和字段类型都必须一致
            if (f1.getName().equals(f2.getName()) & f1.getType().getName().equals(f2.getType().getName())) {
                // 属性可访问
                f1.setAccessible(true);
                // 获取obj这个字段的值
                Object val = f1.get(obj);
                f2.setAccessible(true);
                // 把这个值赋给obj2这个字段
                f2.set(obj2, val);
                // 访问权限还原
                f2.setAccessible(false);
                f1.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tClass 泛型类
     * @param <T>    泛型
     * @return 返回泛型类的实例对象
     */
    private static <T> T getInstance(Class<T> tClass) {

        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
