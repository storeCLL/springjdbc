package com.cll.yl.back.common.entity;

import com.cll.yl.back.common.dao.CustomColumn;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 描述信息:
 * 根实体父类
 * @author CLL
 * @version 1.0
 * @date 2019/3/10 8:33
 */
public class BaseEntity {

    /**
     * 主键，自增，bigint(20)
     */
    protected Long id;

    /**
     * 创建时间，timestamp, default:currentTimestamp
     */
    @CustomColumn(name = "gmt_create")
    protected Date gmtCreate;
    /**
     * 修改时间, datetime
     */
    @CustomColumn(name = "gmt_modified")
    protected Date gmtModified;


    public BaseEntity() {
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

    /**
     * 获取类实体对象的所有字段，包含父类中声明的字段
     * @param cs    实体类的Class对象
     * @param fieldArray    字段数组
     * @return  所有字段的字段数组
     */
    protected Field[] getBeanFieldArray(Class<?> cs, Field[] fieldArray) {
        // 获取当前类中声明的字段属性，不包含父类中定义的属性
        fieldArray = ArrayUtils.addAll(fieldArray, cs.getDeclaredFields());
        if (null != cs.getSuperclass()) {
            Class<?> superclass = cs.getSuperclass();
            fieldArray = getBeanFieldArray(superclass, fieldArray);
        }
        return fieldArray;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModefied(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}