package com.cll.yl.back.sys.entity;

import com.cll.yl.back.common.dao.CustomColumn;
import com.cll.yl.back.common.entity.BaseEntity;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 描述信息:
 * 测试MYSQL中常用数据类型的实体类
 * @author CLL
 * @version 1.0
 * @date 2019/3/10 8:30
 */
public class TestMySqlDataTypeEntity extends BaseEntity {

    /**
     * 用户名，varchar(255)
     */
    private String username;
    /**
     * 年龄，int(11)
     */
    private Integer age;
    /**
     * 账户余额，decimal(11, 2)
     */
    private BigDecimal amount;
    /**
     * 账户是否锁定，tinyint(2)
     */
    @CustomColumn(name = "is_lock")
    private Boolean lock;
    /**
     * 考试成绩
     */
    private Double score;
    /**
     * 体重
     */
    private Float weight;

    public TestMySqlDataTypeEntity() {
    }

    @Override
    public String toString() {
        Class<? extends TestMySqlDataTypeEntity> aClass = this.getClass();
        Field[] beanFieldArray = getBeanFieldArray(aClass, null);
        if (null != beanFieldArray && beanFieldArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(aClass.getName()).append(" ").append("{").append("\n");
            for (Field field : beanFieldArray) {
                try {
                    stringBuilder.append("\t").append(field.getName()).append("=").append(field.get(this)).append("\n");
                } catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                } catch (IllegalAccessException ia) {
                    System.out.println("\n******非法访问异常");
                    ia.printStackTrace();
                }
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return null;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
