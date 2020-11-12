package com.xc.common.utils.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.xc.common.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.io.Writer;

/**
 * Xstream模式,bean 与 XML 互转处理类
 * @author Administrator
 */
@Slf4j
public class XstreamUtil {
    /**
     * bean -> xml
     * 格式化显示
     * @return
     */
    public static String toPrettyXML(Object obj, String xmlHead) {
        xmlHead = StringUtils.isBlank(xmlHead) ? SysConstant.XML_HEAD.UTF8 : xmlHead;

        XStream xs = new XStream(new DomDriver(SysConstant.ENCODING.UTF8, new NoNameCoder()));
        xs.autodetectAnnotations(true);
        Writer writer = new StringWriter();

        xs.toXML(obj, writer);

        log.debug("Dom4JXmlWriter: {}", writer);

        return xmlHead + SysConstant.MARK.NEWLINE + writer.toString();
    }

    /**
     * bean -> xml
     *
     * @return
     */
    public static String toXML(Object obj, String xmlHead) {
        xmlHead = StringUtils.isBlank(xmlHead) ? SysConstant.XML_HEAD.UTF8 : xmlHead;

        XStream xs = new XStream(new DomDriver(SysConstant.ENCODING.UTF8, new NoNameCoder()));
        xs.autodetectAnnotations(true);
        Writer writer = new StringWriter();
        xs.marshal(obj, new CompactWriter(writer, new NoNameCoder()));

        log.debug("Dom4JXmlWriter: {}", writer);

        return xmlHead + writer.toString();
    }

    /**
     * xml -> bean
     * @param xmlStr
     * @return
     */
    public static Object toBean(String xmlStr, Object obj){
        log.debug("ResXML: {}", xmlStr);

        XStream xs = new XStream(new DomDriver(SysConstant.ENCODING.UTF8, new NoNameCoder()));
        xs.autodetectAnnotations(true);
        xs.registerConverter(new XstreamDateConverter());
        Writer writer = new StringWriter();
        xs.marshal(obj, new CompactWriter(writer, new NoNameCoder()));

        return xs.fromXML(xmlStr, obj);
    }

    /**
     * xml -> bean
     * @param xmlStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T  toBean(String xmlStr, Class<T> cls){
        log.debug("ResXML: {}", xmlStr);

        XStream xstream=new XStream(new DomDriver(SysConstant.ENCODING.UTF8, new NoNameCoder()));
        xstream.registerConverter(new XstreamDateConverter());
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;
    }
}
