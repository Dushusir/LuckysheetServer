package com.xc.common.utils.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;


/**
 * xstream 转换日期时间
 */
public class XstreamDateConverter implements Converter {
    private static Logger log = LoggerFactory.getLogger(XstreamDateConverter.class);

    // 支持转换的日期格式
    private static final DateTimeFormatter[] ACCEPT_DATE_FORMATS = {
            DateTimeFormat.forPattern("yyyy-MM-dd"),
            DateTimeFormat.forPattern("yyyy-MM"),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormat.forPattern("yyyy/MM/dd")
    };

    /* (non-Javadoc)
     * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class type) {
        return Date.class == type;
    }

    /* (non-Javadoc)
     * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object, com.thoughtworks.xstream.io.HierarchicalStreamWriter, com.thoughtworks.xstream.converters.MarshallingContext)
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

    }

    /* (non-Javadoc)
     * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.UnmarshallingContext)
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        if (reader.getValue() == null || "".equals(reader.getValue())) {
            return null;
        }
        DateTime dt = null;

        for (DateTimeFormatter format : ACCEPT_DATE_FORMATS) {
            try {
                // 遍历日期支持格式，进行转换
                dt = format.parseDateTime(reader.getValue());
                break;
            } catch (Exception e) {
                log.debug("unmarshal: {}", e.getMessage());
                continue;
            }
        }

        return dt == null ? null : dt.getMillis();
    }
}
