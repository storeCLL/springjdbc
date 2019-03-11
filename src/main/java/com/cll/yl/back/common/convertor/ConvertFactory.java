package com.cll.yl.back.common.convertor;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述信息:
 * 类型转换器工厂
 * @author CLL
 * @version 1.0
 * @date 2019/3/6 17:33
 */
public class ConvertFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConvertFactory.class);

    /**
     * 转换器中元数据类型与目标数据类型拼接符
     */
    private static final String CONVERT_HANDLER_SPLICING_CHAR = "To";

    /**
     * 转换器处理器中心
     */
    private static Map<String, Convert<?, ?>> convertHandlers = new HashMap<>();

    static {
        // 注册对象数组转换成Double和double数组
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectArrayToPackingDoubleConvert());
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectArrayToDoubleConvert());
        // 注册对象数组转换成Float和float数组
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Float.class.getName(), new ObjectArrayToPackingFloatConvert());
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + float.class.getName(), new ObjectArrayToFloatConvert());
        // 注册对象数组转换成Integer和int数组
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Integer.class.getName(), new ObjectArrayToIntegerConvert());
        convertHandlers.put(String[].class.getName() + CONVERT_HANDLER_SPLICING_CHAR + int.class.getName(), new ObjectArrayToIntConvert());
        // 注册将对象类型转换成日期类型的转换器
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Date.class.getName(), new ObjectToDateConvert());
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Date.class.getName(), new ObjectToDateConvert());
        convertHandlers.put(long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Date.class.getName(), new ObjectToDateConvert());
        // 注册将对象类型转换成Double类型的转换器
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(Float.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        // 新增开始
        convertHandlers.put(Float.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(int.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(int.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + double.class.getName(), new ObjectToDoubleConvert());
        // 新增结束

        // 注册将对象类型转换成Float类型的转换器
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + float.class.getName(), new ObjectToFloatConvert());
        // 新增开始
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(int.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(int.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + float.class.getName(), new ObjectToFloatConvert());
        // 新增结束
        // 注册将对象类型转换成Integer类型的转换器
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Integer.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + int.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Integer.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + int.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Integer.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + int.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + int.class.getName(), new ObjectToIntegerConvert());

        // 注册将对象类型转换成Boolean类型的转换器
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Boolean.class.getName(), new ObjectToBooleanConvert());
        convertHandlers.put(String.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + boolean.class.getName(), new ObjectToBooleanConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + Boolean.class.getName(), new ObjectToBooleanConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + boolean.class.getName(), new ObjectToBooleanConvert());

        // 注册将对象类型转换成String类型
        convertHandlers.put(Long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(long.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Double.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(double.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Float.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(float.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Character.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(char.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Byte.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(byte.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Boolean.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(boolean.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Integer.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(int.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(Short.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(short.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());
        convertHandlers.put(BigDecimal.class.getName() + CONVERT_HANDLER_SPLICING_CHAR + String.class.getName(), new ObjectToStringConvert());


        Set<Map.Entry<String, Convert<?, ?>>> entrySet = convertHandlers.entrySet();
        if (CollectionUtils.isNotEmpty(entrySet)) {
            logger.info("======当前系统注册的转换器开始");
            for (Map.Entry<String, Convert<?, ?>> entry : entrySet) {
                logger.info("------转换器名称 = {}, 转换器类型 = {}", entry.getKey(), entry.getValue());
            }
            logger.info("======当前系统注册的转换器结束");
        } else {
            logger.error("******当前系统未配置转换器");
        }

    }

    /**
     * 将元数据转换成目标类型的数据
     * @param cs    目标类型的Class对象
     * @param sourceValue   原类型数据
     * @param <T>   目标类型泛型支持
     * @return  目标类型数据
     */
    @SuppressWarnings({"unchecked"})
    static <T> T convert(Class<T> cs, Object sourceValue) {
        String convertHandlerName = sourceValue.getClass().getName() + CONVERT_HANDLER_SPLICING_CHAR + cs.getName();
        Convert convert = convertHandlers.get(convertHandlerName);
        if (null == convert) {
            logger.error("******暂不支持的数据类型转换器 = {}", convertHandlerName);
            return null;
        }
        return (T) convert.convert(sourceValue);
    }
}