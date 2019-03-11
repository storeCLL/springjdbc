package com.cll.yl.back.common.convertor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 描述信息:
 * 对象类型数据转为时间类型数据转换器
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 14:23
 */
public class ObjectToDateConvert implements Convert<Object, Date> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToDateConvert.class);

    private static Object[] [] patterns = {
            {Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"), "yyyy-MM-dd"},
            {Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$"), "yyyy-MM-dd HH:mm"},
            {Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"), "yyyy-MM-dd HH:mm:ss"},
            {Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$"), "yyyy/MM/dd"},
            {Pattern.compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}$"), "yyyy/MM/dd HH:mm"},
            {Pattern.compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}$"), "yyyy/MM/dd HH:mm:ss"},
    };

    @Override
    public Date convert(Object source) {
        if (null == source) {
            logger.error("******对象类型数据转为时间类型数据，原始数据为空");
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), Date.class.getName());
        String sourceValue = source.toString().trim();
        String formatStr = null;
        Pattern pattern;
        for (Object[] obj : patterns) {
            pattern = (Pattern) obj[0];
            if (pattern.matcher(sourceValue).matches()) {
                formatStr = obj[1].toString();
                break;
            }
        }
        if (StringUtils.isEmpty(formatStr)) {
            logger.error("******不存在当前数据格式的转换器。source = {}", sourceValue);
            return null;
        }
        try {
            return new SimpleDateFormat(formatStr).parse(sourceValue);
        } catch (ParseException pe) {
            logger.error("******强制将数据类型转为日期类型，抛出异常" + pe.getMessage(), pe);
            return null;
        }
    }
}
