package com.cll.yl.back.common.util;

import java.util.UUID;

/**
 * UUID生成随机数工具类
 * @author  陈林林
 * @date 2019-03-08
 */
public class UUIDUtil {
    /**
     * 去除中间的横线
     * @return  去除中间的横线的随机字符串
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
