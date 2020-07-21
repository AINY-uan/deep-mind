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
    public static String createXmlByMap(String parentName, Map<?, ?> params, boolean isCdata) {

        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = iteratorXml(doc.getRootElement(), parentName, params, isCdata);
        return formatXml(xml);
    }

    private static String iteratorXml(Element element, String parentName, Map<?, ?> params, boolean isCdata) {

        Element e = element.addElement(parentName);
        Set<?> set = params.keySet();
        for (Object key : set) {
            if (params.get(key) instanceof Map) {
                iteratorXml(e, (String) key, (Map<?, ?>) params.get(key), isCdata);
            } else {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                if (isCdata) {
                    e.addElement((String) key).addCDATA(value);
                } else {
                    e.addElement((String) key).addText(value);
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
        elementToMap(rootElement, map);
        return map;
    }

    /**
     * XmlToMap核心方法，里面有递归调用
     *
     * @param outElement 根节点
     * @param outMap     输出Map
     */
    private static void elementToMap(Element outElement, Map<String, Object> outMap) {

        List<?> list = outElement.elements();
        int size = list.size();
        if (size == 0) {
            outMap.put(outElement.getName(), outElement.getTextTrim());
        } else {
            Map<String, Object> innerMap = new HashMap<>(16);
            for (Object ele1 : list) {
                Element e = (Element) ele1;
                String eleName = e.getName();
                Object obj = innerMap.get(eleName);
                if (obj == null) {
                    elementToMap(e, innerMap);
                } else {
                    if (obj instanceof Map<?, ?>) {
                        List<Object> list1 = new ArrayList<>();
                        list1.add(innerMap.remove(eleName));
                        elementToMap(e, innerMap);
                        list1.add(innerMap.remove(eleName));
                        innerMap.put(eleName, list1);
                    }
                }
            }
            outMap.put(outElement.getName(), innerMap);
        }
    }
}
