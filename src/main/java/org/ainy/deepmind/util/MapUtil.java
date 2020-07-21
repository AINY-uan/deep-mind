package org.ainy.deepmind.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author 阿拉丁省油的灯
 * @description Map工具类
 * @date 2020-06-29 19:28
 */
public class MapUtil {

    /**
     * 集合M内非空参数值的参数按照参数名ASCII码从小到大排序
     *
     * @param map 待排序的集合
     * @return 集合M内非空参数值的参数按照参数名ASCII码从小到大排序
     */
    public static String sortMapByKey(HashMap<String, String> map) throws Exception {

        if (map.size() == 0) {
            throw new Exception("params size is more than 0");
        }

        Set<String> keySet = map.keySet();

        String[] keyArray = keySet.toArray(new String[0]);

        Arrays.sort(keyArray);

        StringBuilder sb = new StringBuilder();

        for (String k : keyArray) {

            if (StringUtils.isNotEmpty(map.get(k))) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(map.get(k).trim()).append("&");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * JDK8
     * 按照Key排序
     *
     * @param map map数据
     * @param asc 是否升序
     * @param <K> Key
     * @param <V> Value
     * @return Map<K, V>
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, boolean asc) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> stream = map.entrySet().stream();
        if (asc) {
            stream.sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            stream.sorted(Map.Entry.<K, V>comparingByKey().reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    /**
     * JDK8
     * 按照Value排序
     *
     * @param map map数据
     * @param asc 是否升序
     * @param <K> Key
     * @param <V> Value
     * @return Map<K, V>
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean asc) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> stream = map.entrySet().stream();
        if (asc) {
            //升序
            stream.sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            //降序
            stream.sorted(Map.Entry.<K, V>comparingByValue().reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }
}
