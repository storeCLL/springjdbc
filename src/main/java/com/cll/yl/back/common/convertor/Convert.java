package com.cll.yl.back.common.convertor;

/**
 * 描述信息:
 * 类型转换接口
 * @param <S> 原类型
 * @param <T> 目标类型
 * @author CLL
 * @version 1.0
 * @date 2019/3/7 9:30
 */
public interface Convert<S, T> {

    /**
     * 将原类型数据转换成目标类型数据
     * @param source    原类型数据
     * @return  目标类型数据
     */
    T convert(S source);
}
