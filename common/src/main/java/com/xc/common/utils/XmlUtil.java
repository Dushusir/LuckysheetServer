package com.xc.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * xml处理
 * @author Administrator
 */
@Slf4j
public class XmlUtil {

    /**
     * 解析成json格式
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> obtainResultForH5PAY(String xmlStr) {
        return obtainXMLElement(xmlStr);
    }

    public static Map<String, String> obtainXMLElement(String xmlStr) {
        Map<String, String> map = new TreeMap<String, String>();

        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);

            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText().replace("![CDATA[", "").replace("]]", ""));
            }
        } catch (DocumentException e) {
            log.error("解析xml数据错误：" + e.getMessage());
        }
        return map;
    }

    public static XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_", "")) {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                @SuppressWarnings("unchecked")
                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name);
                }
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    writer.write(text);
                }
            };
        }
    });


}
