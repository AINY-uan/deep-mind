package org.ainy.deepmind.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * @author 阿拉丁省油的灯
 * @date 2019-06-16 15:14
 * @description Xml与Map互转工具类
 */
public class XmlMap {

    /**
     * 根据Map创建Xml
     *
     * @param params Map数据
     * @return xml字符串
     */
    public static String createXmlByMap(Map<String, Object> params) {

        String parentName = "Document";
        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = iteratorXml(doc.getRootElement(), parentName, params, false);
        return formatXml(xml);
    }

    /**
     * 根据Map创建Xml
     *
     * @param parentName 根节点名称
     * @param params     Map数据
     * @return xml字符串
     */
    public static String createXmlByMap(String parentName, Map<String, Object> params) {

        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = iteratorXml(doc.getRootElement(), parentName, params, false);
        return formatXml(xml);
    }

    /**
     * 根据Map创建Xml
     *
     * @param parentName 根节点名称
     * @param params     Map数据
     * @param isCdata    CDATA标签用于说明数据不被XML解析器解析
     * @return xml字符串
     */
    public static String createXmlByMap(String parentName, Map<String, Object> params, boolean isCdata) {

        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = iteratorXml(doc.getRootElement(), parentName, params, isCdata);
        return formatXml(xml);
    }

    @SuppressWarnings("unchecked")
    private static String iteratorXml(Element element, String parentName, Map<String, Object> params, boolean isCdata) {

        Element e = element.addElement(parentName);
        Set<String> set = params.keySet();
        for (String key : set) {
            if (params.get(key) instanceof Map) {
                iteratorXml(e, key, (Map<String, Object>) params.get(key), isCdata);
            } else {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                if (isCdata) {
                    e.addElement(key).addCDATA(value);
                } else {
                    e.addElement(key).addText(value);
                }
            }
        }
        return e.asXML();
    }

    /**
     * 格式化xml,显示为容易看的XML格式
     *
     * @param inputXml xml字符串
     * @return xml字符串
     */
    private static String formatXml(String inputXml) {

        String requestXml = null;
        XMLWriter writer = null;
        Document document;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new StringReader(inputXml));
            if (document != null) {
                StringWriter stringWriter = new StringWriter();
                // 格式化，每一级前的空格
                OutputFormat format = new OutputFormat("	", true);
                // xml声明与内容是否添加空行
                format.setNewLineAfterDeclaration(false);
                // 是否设置xml声明头部
                format.setSuppressDeclaration(false);
                // 设置分行
                format.setNewlines(true);
                writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                requestXml = stringWriter.getBuffer().toString();
            }
            return requestXml;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过XML转换为Map<String,Object>
     *
     * @param xml xml字符串
     * @return 第一个为Root节点，Root节点之后为Root的元素，如果为多层，可以通过key获取下一层Map
     */
    public static Map<String, Object> createMapByXml(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>(16);
        if (doc == null) {
            return map;
        }
        Element rootElement = doc.getRootElement();
        elementTomap(rootElement, map);
        return map;
    }

    /**
     * XmlToMap核心方法，里面有递归调用
     *
     * @param outele 根节点
     * @param outmap 输出Map
     */
    @SuppressWarnings("unchecked")
    private static void elementTomap(Element outele, Map<String, Object> outmap) {
        List<Element> list = outele.elements();
        int size = list.size();
        if (size == 0) {
            outmap.put(outele.getName(), outele.getTextTrim());
        } else {
            Map<String, Object> innermap = new HashMap<>();
            for (Element ele1 : list) {
                String eleName = ele1.getName();
                Object obj = innermap.get(eleName);
                if (obj == null) {
                    elementTomap(ele1, innermap);
                } else {
                    if (obj instanceof Map) {
                        List<Map<String, Object>> list1 = new ArrayList<>();
                        list1.add((Map<String, Object>) innermap.remove(eleName));
                        elementTomap(ele1, innermap);
                        list1.add((Map<String, Object>) innermap.remove(eleName));
                        innermap.put(eleName, list1);
                    } else {
                        elementTomap(ele1, innermap);
                        ((List<Map<String, Object>>) obj).add(innermap);
                    }
                }
            }
            outmap.put(outele.getName(), innermap);
        }
    }
}
