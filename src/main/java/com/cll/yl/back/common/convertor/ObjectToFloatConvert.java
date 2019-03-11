package com.cll.yl.back.common.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述信息:
 * 对象类型转换成Float类型转换器
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 17:10
 */
public class ObjectToFloatConvert implements Convert<Object, Float> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToFloatConvert.class);

    @Override
    public Float convert(Object source) {
        if (null == source) {
            logger.error("*****对象类型转换成Float类型，参数为空");
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), Float.class.getName());
        try {
            return Float.parseFloat(source.toString());
        } catch (NumberFormatException nfe) {
            logger.error("******对象类型转换成Float类型，抛出数字格式化异常" + nfe.getMessage(), nfe);
            return null;
        }
    }
}
