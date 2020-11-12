package com.xc.common.utils.xstream;

import com.xc.common.utils.XmlUtil;

/**
 * 测试xml
 */
public class TestXml {
    public static void main(String[] args){
        TestModel t=new TestModel();
        System.out.println("测试1");
        XmlUtil.xstream.alias("xml",t.getClass());
        //必须要把两个下划线替换为一个
        String xmlData = XmlUtil.xstream.toXML(t).replace("__", "_");
        System.out.println(xmlData);

        System.out.println("测试2");
        System.out.println(XstreamUtil.toXML(t,null));
        System.out.println("测试3");
        XstreamUtil.toPrettyXML(t,null);
    }


}
