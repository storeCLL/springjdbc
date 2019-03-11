package com.cll.yl.back.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 描述信息:
 * 自定义字符串工具类
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 11:09
 */
public class StringUtil {

    /**
     * 将字符串的首字母编程大写
     * @param sourceStr 原始字符串
     * @return  首字母大写之后的字符串
     */
    public static String firstCharToUpperCase(String sourceStr) {
        if (StringUtils.isEmpty(sourceStr)) {
            return sourceStr;
        }
        String firstCharUpperCase = sourceStr.substring(0, 1).toUpperCase();
        if (sourceStr.length() == 1) {
            return firstCharUpperCase;
        }
        return firstCharUpperCase + sourceStr.substring(1);
    }
}