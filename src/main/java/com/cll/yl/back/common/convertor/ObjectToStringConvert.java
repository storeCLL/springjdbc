package com.cll.yl.back.common.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 21:10
 */
public class ObjectToStringConvert implements Convert<Object, String> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToStringConvert.class);

    @Override
    public String convert(Object source) {
        if (null == source) {
            logger.error("******将对象类型转换成String类型，原始数据为空");
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), String.class.getName());
        try {
            return source.toString().trim();
        } catch (Exception e) {
            logger.error("******将对象类型转换成String类型，抛出异常" + e.getMessage(), e);
            return null;
        }
    }
}
