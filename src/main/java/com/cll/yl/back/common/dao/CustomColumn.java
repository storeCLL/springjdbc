package com.cll.yl.back.common.dao;

import java.lang.annotation.*;

/**
 * 描述信息:
 * 自定义实体类与数据库映射的字段
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 21:44
 */
@Documented
// 作用于为运行期
@Retention(RetentionPolicy.RUNTIME)
// 只能作用于字段（属性）上
@Target(ElementType.FIELD)
public @interface CustomColumn {
    /**
     * 自定义映射名称
     * @return  映射的名称
     */
    String name() default "";
}
