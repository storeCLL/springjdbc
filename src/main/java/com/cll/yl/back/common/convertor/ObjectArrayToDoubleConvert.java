package com.cll.yl.back.common.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述信息:
 * 将对象数组转换成double数组转换器
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 10:34
 */
public class ObjectArrayToDoubleConvert implements Convert<Object[], double[]> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectArrayToPackingDoubleConvert.class);

    @Override
    public double[] convert(Object[] source) {
        if (null == source) {
            return null;
        }
        logger.info("------当前转换器被使用。元数据类型 = {}, 目标数据类型 = {}", source.getClass().getName(), double[].class.getName());
        int sourceLength = source.length;
        if (sourceLength == 0) {
            return new double[0];
        }
        double[] target = new double[sourceLength];
        for (int i = 0; i < sourceLength; i++) {
            try {
                target[i] = Double.parseDouble(source[i].toString());
            } catch (NullPointerException npe) {
                logger.error("******原数组中下标为 = {}的元素为空，抛出空指针异常" + npe.getMessage(), i, npe);
                return null;
            } catch (NumberFormatException nfe) {
                logger.error("******对象数组转换成double类型数组抛出异常" + nfe.getMessage(), nfe);
                return null;
            }
        }
        return target;
    }
}
