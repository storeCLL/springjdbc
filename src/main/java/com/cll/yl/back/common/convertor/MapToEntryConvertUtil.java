package com.cll.yl.back.common.convertor;

import com.cll.yl.back.common.dao.CustomColumn;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *  将Map自动映射到实体类的工具
 * @author CLL
 * @version 1.0
 * @date 2019/3/6 15:00
 */
public class MapToEntryConvertUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapToEntryConvertUtil.class);

    private static Map<String, ConvertEntryItem> convertEntryItemCache = new HashMap<>();

    /**
     * 将map对象，映射成实体类对象
     * @param valueMap  map对象
     * @param entityClass   实体类的类对象
     * @param prefix    map前缀
     * @param <T>   实体泛型
     * @return  实体对象
     */
    public static <T> T convert(Map<String, Object> valueMap, Class<T> entityClass, String prefix) {
        ConvertEntryItem convertEntryItem = convertEntryItemCache.get(entityClass.getName());
        if (null == convertEntryItem) {
            convertEntryItem = ConvertEntryItem.getInstance(entityClass);
            convertEntryItemCache.put(entityClass.getName(), convertEntryItem);
        }
        List<String> fieldNameList = convertEntryItem.getFieldNameList();
        Map<String, Method> fieldSetMethodMap = convertEntryItem.getFieldSetMethodMap();

        T entity;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException ie) {
            logger.error("******不能实例化当前类 = {}", entityClass.getName());
            return null;
        } catch (IllegalAccessException iae) {
            logger.error("******非法访问异常。类 = {}", entityClass.getName());
            return null;
        }

        if (StringUtils.isEmpty(prefix)) {
            prefix = "";
        }
        Object fieldValue;
        Object targetValue;
        Method fieldSetMethod;
        Class<?>[] parameterTypes;
        // 遍历字段名称，给对象实例赋值
        for (String fieldName : fieldNameList) {
            fieldValue = MapUtils.getObject(valueMap, prefix + fieldName);
            if (null == fieldValue) {
                logger.info("------获取字段为空.类名称 = {},field = {}", entityClass.getName(),  fieldName);
                continue;
            }
            fieldSetMethod = fieldSetMethodMap.get(fieldName);
            if (null == fieldSetMethod) {
                logger.error("******获取set方法为空.类名称 = {},field = {}", entityClass.getName(),  fieldName);
                continue;
            }
            // 只支持单个参数的set方法
            int parameterCount = fieldSetMethod.getParameterCount();
            if (parameterCount != 1) {
                logger.error("******set方法参数个数不为1.类名称 = {},field = {}", entityClass.getName(),  fieldName);
                continue;
            }
            Parameter[] parameters = fieldSetMethod.getParameters();
            if (null == parameters || parameters.length != 1) {
                logger.error("******set方法参数个数不为1.类名称 = {},field = {}", entityClass.getName(),  fieldName);
                continue;
            }
            parameterTypes = fieldSetMethod.getParameterTypes();
            if (null == parameterTypes || parameterTypes.length != 1) {
                logger.error("******set方法参数个数不为1.类名称 = {},field = {}", entityClass.getName(),  fieldName);
                continue;
            }
            // valueMap中参数值的类型和fieldSetMethodMap中需要的类型一致
            if (parameterTypes[0].isAssignableFrom(fieldValue.getClass())) {
                targetValue = fieldValue;
            } else {
                // 进行目标类型的转换，将valueMap中值的类型，转换成set方法中需要的类型
                targetValue = ConvertFactory.convert(parameterTypes[0], fieldValue);
            }

            if (null != targetValue) {
                try {
                    fieldSetMethod.invoke(entity, targetValue);
                } catch (IllegalAccessException iae) {
                    logger.error("******执行set方法抛出非法访问异常" + iae.getMessage(), iae);
                } catch (InvocationTargetException ite) {
                    logger.error("******执行set方法，抛出调用执行目标方法异常" + ite.getMessage(), ite);
                }
            } else {
                logger.error("******未能获取当前类 = {}, 当前字段 = {}的值，使用set方法设值失败", entityClass.getName(), fieldName);
            }
        }
        return entity;
    }

    /**
     * 实体转换静态内部类
     * 对象映射内容的对象
     */
    static class ConvertEntryItem {
        /**
         * set方法前缀
         */
        private static final String SET_METHOD_PREFIX = "set";
        /**
         * 实体类的字段属性名称的集合
         */
        private List<String> fieldNameList = new ArrayList<>();
        /**
         * 实体类中字段属性对象的setMethod.赋值（设值）方法
         */
        private Map<String, Method> fieldSetMethodMap = new HashMap<>();

        /**
         * 公开的构造方法
         * @param cs    实体类对象的类对象
         * @return  字段映射对象
         */
        private static ConvertEntryItem getInstance(Class<?> cs) {
            ConvertEntryItem convertEntryItem = new ConvertEntryItem();
            convertEntryItem.parseEntity(cs);
            return convertEntryItem;
        }

        /**
         * 转换实体对象的字段属性以及属性的setMethod
         * @param cs    描述实体类的对象
         */
        private void parseEntity(Class<?> cs) {
            if (null == cs) {
                logger.error("******创建实体映射参数错误");
            } else {
                // 所有字段的声明，不一定是最终需要映射的字段，用来获取setMethod
                Field[] allFieldArray = getBeanFieldArray(cs, null);
                if (null != allFieldArray && allFieldArray.length > 0) {
                    Method method;
                    String fieldName;
                    String setMethodName;
                    for (Field field : allFieldArray) {
                        fieldName = field.getName();
                        if (StringUtils.isEmpty(fieldName)) {
                            logger.error("******获取字段属性名称为空 field = {}", field);
                        } else {
                            if (fieldName.length() == 1) {
                                setMethodName = SET_METHOD_PREFIX + fieldName.toUpperCase();
                            } else {
                                setMethodName = SET_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            }
                            try {
                                // 只返回和当前字段类型相符的set方法，不支持多参数以及不同类型的set方法
                                method = cs.getMethod(setMethodName, field.getType());
                                if (null != method) {
                                    CustomColumn annotation = field.getAnnotation(CustomColumn.class);
                                    if (null == annotation) {
                                        fieldNameList.add(fieldName);
                                        fieldSetMethodMap.put(fieldName, method);
                                    } else {
                                        fieldNameList.add(annotation.name());
                                        fieldSetMethodMap.put(annotation.name(), method);
                                    }
                                }
                            } catch (NoSuchMethodException nsme) {
                                logger.error("******类名称 = {},方法名 = {}，未找到方法。", cs.getName(), setMethodName);
                            } catch (SecurityException se) {
                                logger.error("******转换实体类安全异常" + se.getMessage(), se);
                            }
                        }
                    }
                }

            }
        }

        /**
         * 获取类实体对象的所有字段，包含父类中声明的字段
         * @param cs    实体类的Class对象
         * @param fieldArray    字段数组
         * @return  所有字段的字段数组
         */
        private Field[] getBeanFieldArray(Class<?> cs, Field[] fieldArray) {
            // 获取当前类中声明的字段属性，不包含父类中定义的属性
            fieldArray = ArrayUtils.addAll(fieldArray, cs.getDeclaredFields());
            if (null != cs.getSuperclass()) {
                Class<?> superclass = cs.getSuperclass();
                fieldArray = getBeanFieldArray(superclass, fieldArray);
            }
            return fieldArray;
        }

        /**
         * 私有构造方法
         */
        private ConvertEntryItem() {
        }

        private List<String> getFieldNameList() {
            return fieldNameList;
        }

        private Map<String, Method> getFieldSetMethodMap() {
            return fieldSetMethodMap;
        }
    }
}