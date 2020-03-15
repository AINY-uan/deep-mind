package org.ainy.deepmind.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 将Javabean转为Map
 * @author demo
 */
public class ListUtil {

    /**
     * 将List<JavaBean>转为Map<bean>
     *
     * @param beanList beanList
     * @return List
     * @throws IllegalAccessException    非法访问异常
     * @throws IntrospectionException    中断异常
     * @throws InvocationTargetException 反射异常
     */
    public static List<List<Object>> convertListBean2ListMap(List<Object> beanList)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        List<Object> rowList;
        List<List<Object>> list = new ArrayList<>();
        for (Object bean : beanList) {
            Map<Object, Object> map = convertBean2Map(bean);
            rowList = new ArrayList<>(map.values());
            list.add(rowList);
        }
        return list;
    }

    /**
     * 将JavaBean的实例化对象转成Map
     *
     * @param bean JavaBean
     * @return Map
     * @throws IntrospectionException    非法访问异常
     * @throws InvocationTargetException 中断异常
     * @throws IllegalAccessException    反射异常
     */
    public static Map<Object, Object> convertBean2Map(Object bean)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        Class<?> type = bean.getClass();
        Map<Object, Object> returnMap = new HashMap<>(16);
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}