package org.ainy.deepmind.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author 阿拉丁省油的灯
 * @description Xml工具类
 * @date 2020-06-29 17:09
 */
public class XmlUtil {

    /**
     * 将Map对象转换为Xml格式的字符串
     *
     * @param parentName 根节点
     * @param data       Map数据
     * @return Xml格式的字符串
     * @throws ParserConfigurationException 解析配置异常
     * @throws TransformerException         转换异常
     */
    public static String mapToXml(String parentName, Map<String, String> data)
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement(parentName);
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    /**
     * 多层Map转Xml
     *
     * @param parentName 根结点
     * @param data       map数据
     * @param isCdata    是否CDATA
     * @return Xml格式的字符串
     */
    public static String mapToXml(String parentName, Map<String, Object> data, boolean isCdata) {

        Document doc = DocumentHelper.createDocument();

        doc.addElement(parentName);

        return recursionMapToXml(doc.getRootElement(), parentName, data, isCdata);
    }

    /**
     * 递归实现Map转Xml
     *
     * @param element    节点
     * @param parentName 节点名称
     * @param map        map数据
     * @param isCdata    是否需要CDATA
     * @return Xml格式的字符串
     */
    private static String recursionMapToXml(Element element, String parentName, Map<?, ?> map, boolean isCdata) {

        Element xmlElement = element.addElement(parentName);

        map.keySet().forEach(key -> {

            if (map.get(key) instanceof Map<?, ?>) {
                recursionMapToXml(xmlElement, (String) key, (Map<?, ?>) map.get(key), isCdata);
            } else {
                String value = map.get(key) == null ? "" : map.get(key).toString();
                if (isCdata) {
                    xmlElement.addElement((String) key).addCDATA(value);
                } else {
                    xmlElement.addElement((String) key).addText(value);
                }
            }
        });
        return xmlElement.asXML();
    }

    public static String mapToXml(Map<String, Object> map, String rootName, boolean isCdata) {

        Document doc = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement(rootName);
        doc.add(root);
        mapToXml(map, root, isCdata);
        return doc.asXML();
    }

    private static void mapToXml(Map<?, ?> map, Element body, boolean isCdata) {

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (key.startsWith("@")) {
                // 属性
                body.addAttribute(key.substring(1), value.toString());
            } else if ("#text".equals(key)) {
                // 有属性时的文本
                body.setText(value.toString());
            } else {
                if (value instanceof List) {
                    List<?> list = (List<?>) value;
                    for (Object o : list) {
                        if (o instanceof Map<?, ?>) {
                            Element subElement = body.addElement(key);
                            mapToXml((Map<?, ?>) o, subElement, isCdata);
                        } else {
                            body.addElement(key).setText((String) o);
                        }
                    }
                } else if (value instanceof Map<?, ?>) {
                    Element subElement = body.addElement(key);
                    mapToXml((Map<?, ?>) value, subElement, isCdata);
                } else {
                    if (isCdata) {
                        body.addElement(key).addCDATA(value.toString());
                    } else {
                        body.addElement(key).setText(value.toString());
                    }
                }
            }
        }
    }

    /**
     * Xml转Map
     *
     * @param xml     Xml数据
     * @param charset 字符编码
     * @return Map
     * @throws UnsupportedEncodingException 未知编码异常
     * @throws DocumentException            文档异常
     */
    public static Map<String, String> xmlToMap(String xml, String charset) throws UnsupportedEncodingException, DocumentException {

        Map<String, String> map = new HashMap<>(16);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(charset)));
        Element root = doc.getRootElement();

        return xmlToMap(root, map);
    }

    /**
     * Xml转Map
     *
     * @param tmpElement 临时Xml节点
     * @param map        Map
     * @return Map
     */
    private static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> map) {

        if (tmpElement.isTextOnly()) {
            map.put(tmpElement.getName(), tmpElement.getText());
            return map;
        }

        Iterator<?> elementIterator = tmpElement.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = (Element) elementIterator.next();
            xmlToMap(element, map);
        }
        return map;
    }

    /**
     * 通过XML转换为Map<String,Object>
     *
     * @param xml xml字符串
     * @return 第一个为Root节点，Root节点之后为Root的元素，如果为多层，可以通过key获取下一层Map
     */
    public static Map<String, Object> xmlToMap(String xml) {

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

    public static Object convertToEntityInfo(String xml, Class<?> clazz) throws JAXBException {

        StringReader reader = new StringReader(xml);
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return jaxbUnmarshaller.unmarshal(reader);
    }
}
