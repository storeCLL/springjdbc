package com.cll.yl.back.common.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述信息:
 * 对象类型转换成Integer类型
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 21:01
 */
public class ObjectToIntegerConvert implements Convert<Object, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToIntegerConvert.class);

    @Override
    public Integer convert(Object source) {
        if (null == source) {
            logger.error("******对象类型转换成Integer类型，原始数据为空");
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), Integer.class.getName());
        try {
            return Integer.parseInt(source.toString().trim());
        } catch (NumberFormatException nfe) {
            logger.error("******将对象 = {} 类型转换成Integer类型抛出数字格式化异常" + nfe.getMessage(), source.toString().trim(), nfe);
            return null;
        }
    }
}
