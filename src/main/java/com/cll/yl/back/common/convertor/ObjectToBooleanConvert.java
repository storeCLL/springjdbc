package com.cll.yl.back.common.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 21:14
 */
public class ObjectToBooleanConvert implements Convert<Object, Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToBooleanConvert.class);

    /**
     * true的标志
     */
    private static final String[] TRUE_ARRAY = {"yes", "true", "1"};
    /**
     * false标志
     */
    private static final String[] FALSE_ARRAY = {"no", "false", "0"};

    @Override
    public Boolean convert(Object source) {
        if (null == source) {
            logger.error("******将对象类型转换成Boolean类型，原始数据为空");
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), Boolean.class.getName());
        if (source instanceof Integer) {
            if (1 == ((Integer)source)) {
                return true;
            }
            if (0 == ((Integer) source)) {
                return false;
            }
        }
        if (source instanceof String) {
            for (int i = 0; i < 3; i++) {
                if (TRUE_ARRAY[i].equalsIgnoreCase(source.toString())) {
                    return true;
                }
                if (FALSE_ARRAY[i].equalsIgnoreCase(source.toString())) {
                    return false;
                }
            }
        }
        logger.error("*****将对象类型转换成Boolean类型，出现不可转换的数据类型");
        try {
            return Boolean.parseBoolean(source.toString().trim());
        } catch (Exception e) {
            logger.error("******将对象类型转换成Boolean类型,抛出异常" + e.getMessage(), e);
            return null;
        }
    }
}
