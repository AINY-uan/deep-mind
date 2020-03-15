package org.ainy.deepmind;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.ainy.deepmind.util.XmlMap.createMapByXml;
import static org.ainy.deepmind.util.XmlMap.createXmlByMap;

/**
 * @author AINY_uan
 * @description XmlMap测试
 * @date 2020-03-15 14:14
 */
public class XmlMapTest {

    @Test
    public void ex1() {

        Map<String, Object> result = new HashMap<>();
        result.put("Request", "getData");
        Map<String, Object> map = new HashMap<>();
        map.put("data", "2019-12-12");
        map.put("name", "阿拉丁省油的灯");
        result.put("Data", map);
        System.out.println(createXmlByMap(result));
        System.out.println(createXmlByMap("request", result));
        System.out.println(createXmlByMap("Parent", result, true));
        System.out.println(createMapByXml(createXmlByMap(result)));
    }
}
