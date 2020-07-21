package org.ainy.deepmind;

import org.ainy.deepmind.util.XmlUtil;
import org.dom4j.DocumentException;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 阿拉丁省油的灯
 * @description Xml工具类测试
 * @date 2020-07-04 17:26
 */
public class XmlUtilTest {

    @Test
    public void ex1() throws TransformerException, ParserConfigurationException {

        Map<String, String> map = new HashMap<>(16);

        map.put("name", "阿拉丁省油的灯");
        map.put("sex", "男");
        map.put("address", "中国上海");

        System.out.println(XmlUtil.mapToXml("info", map));
    }

    @Test
    public void ex2() {

        Map<String, Object> map = new HashMap<>(16);

        map.put("name", "阿拉丁省油的灯");
        map.put("sex", "男");
        map.put("address", "中国上海");

        Map<String, String> info = new HashMap<>(16);
        info.put("email", "ainy_uan@126.com");
        info.put("telePhone", "18114043944");

        map.put("info", info);

        System.out.println(XmlUtil.mapToXml("idea", map, false));
    }

    @Test
    public void ex3() throws UnsupportedEncodingException, DocumentException {

        System.out.println(XmlUtil.xmlToMap("<ainy><address>中国上海</address><sex>男</sex><name>阿拉丁省油的灯</name><info><email>ainy_uan@126.com</email><telePhone>18114043944</telePhone></info></ainy>", "UTF-8"));
    }

    @Test
    public void ex4() {

        System.out.println(XmlUtil.xmlToMap("<ainy><address>中国上海</address><sex>男</sex><name>阿拉丁省油的灯</name><info><email>ainy_uan@126.com</email><telePhone>18114043944</telePhone></info></ainy>"));
    }

    @Test
    public void ex5() {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("@root", "2.999.1.96");
        map1.put("@extension", "b943f116-5cbd-4036-9314-f7be62306ee1");
        map.put("id", map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("@value", "20190114163412");
        map.put("creationTime", map2);

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("@root", "2.16.840.1.113883.1.6");
        map3.put("@extension", "PRPA_IN000001UV03");
        map.put("interactionId", map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("@typeCode", "RCV");
        HashMap<String, String> map4_1 = new HashMap<>();
        map4_1.put("@value", "");
        map4.put("telecom", map4_1);
        HashMap<String, Object> map4_2 = new HashMap<>();
        map4_2.put("@classCode", "DEV");
        map4_2.put("@determinerCode", "INSTANCE");
        HashMap<String, String> map4_2_1 = new HashMap<>();
        map4_2_1.put("@root", "2.999.1.97");
        map4_2_1.put("@extension", "DZ");
        map4_2.put("id", map4_2_1);

        HashMap<String, String> map4_2_2 = new HashMap<>();
        map4_2_2.put("@value", "设备编号");
        map4_2.put("telecom", map4_2_2);

        HashMap<String, String> map4_2_3 = new HashMap<>();
        map4_2_3.put("@code", "DZ");
        map4_2_3.put("@displayName", "导诊系统");
        map4_2_3.put("@codeSystem", "2.999.2.3.2.84");
        map4_2_3.put("@codeSystemName", "医院信息平台系统域代码表");
        map4_2.put("softwareName", map4_2_3);

        map4.put("device", map4_2);
        map.put("receiver", map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("@typeCode", "SND");
        HashMap<String, String> map5_1 = new HashMap<>();
        map5_1.put("@value", "192.168.27.41");
        map5.put("telecom", map5_1);

        HashMap<String, Object> map5_2 = new HashMap<>();
        map5_2.put("@classCode", "DEV");
        map5_2.put("@determinerCode", "INSTANCE");
        HashMap<String, String> map5_2_1 = new HashMap<>();
        map5_2_1.put("@root", "2.999.1.98");
        map5_2_1.put("@extension", "DZ");
        map5_2.put("id", map5_2_1);

        HashMap<String, String> map5_2_2 = new HashMap<>();
        map5_2_2.put("@value", "设备编号");
        map5_2.put("telecom", map5_2_2);

        HashMap<String, String> map5_2_3 = new HashMap<>();
        map5_2_3.put("@code", "DZ");
        map5_2_3.put("@displayName", "导诊系统");
        map5_2_3.put("@codeSystem", "2.999.2.3.2.84");
        map5_2_3.put("@codeSystemName", "医院信息平台系统域代码表");
        map5_2.put("softwareName", map5_2_3);

        map5.put("device", map5_2);

        map.put("sender", map5);

        HashMap<String, Object> map6 = new HashMap<>();
        map6.put("@classCode", "CACT");
        map6.put("@moodCode", "APT");

        HashMap<String, Object> map6_1 = new HashMap<>();
        map6_1.put("@typeCode", "SUBJ");

        HashMap<String, Object> map6_1_1 = new HashMap<>();

        HashMap<String, Object> map6_1_1_1 = new HashMap<>();

        map6_1_1_1.put("funName", "appointment");
        map6_1_1_1.put("xml", "xxx");

        map6_1_1.put("Common", map6_1_1_1);

        map6_1.put("request", map6_1_1);

        map6.put("subject", map6_1);

        map.put("controlActProcess", map6);

        System.out.println(XmlUtil.mapToXml(map, "PRPA_IN000001UV03", false));
    }

    @Test
    public void ex6() {

        Map<String, Object> map = new HashMap<>(16);

        map.put("name", "阿拉丁省油的灯");
        map.put("sex", "男");
        map.put("address", "中国上海");

        Map<String, String> info = new HashMap<>(16);
        info.put("email", "ainy_uan@126.com");
        info.put("telePhone", "18114043944");

        map.put("info", info);

        System.out.println(XmlUtil.mapToXml(map, "ainy", false));
    }
}
