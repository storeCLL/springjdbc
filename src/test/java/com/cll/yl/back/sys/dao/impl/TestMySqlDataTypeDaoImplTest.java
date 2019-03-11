package com.cll.yl.back.sys.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cll.yl.back.sys.dao.TestMySqlDataTypeDao;
import com.cll.yl.back.sys.entity.TestMySqlDataTypeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/10 8:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-context.xml"})
public class TestMySqlDataTypeDaoImplTest {

    private static final Logger logger = LoggerFactory.getLogger(TestMySqlDataTypeDaoImplTest.class);

    @Resource
    private TestMySqlDataTypeDao testMySqlDataTypeDao;

    @Test
    public void queryById() throws Exception {
        TestMySqlDataTypeEntity testMySqlDataTypeEntity = testMySqlDataTypeDao.queryById(1);
        logger.info("result = {}", testMySqlDataTypeEntity);
    }

    @Test
    public void selectById() throws Exception {
        Map<String, Object> map = testMySqlDataTypeDao.selectById(3);
        logger.info("------result = {}", JSONObject.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void listEntities() throws Exception {
        List<TestMySqlDataTypeEntity> testMySqlDataTypeEntities = testMySqlDataTypeDao.listEntities();
        logger.info("------result = {}", JSONArray.toJSONStringWithDateFormat(testMySqlDataTypeEntities, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void insertEntity() throws Exception {
        TestMySqlDataTypeEntity entity = new TestMySqlDataTypeEntity();
        entity.setUsername("yangL");
        entity.setAge(24);
        entity.setAmount(new BigDecimal(4500.45D));
        entity.setLock(false);
        entity.setScore(99.99D);
        entity.setWeight(55.55F);
        entity.setGmtCreate(new Date());
        Boolean insertFlag = testMySqlDataTypeDao.insertEntity(entity);
        logger.info("------result = {}", insertFlag);
    }

    @Test
    public void updateEntity() throws Exception {
        TestMySqlDataTypeEntity entity = new TestMySqlDataTypeEntity();
        entity.setId(36L);
        entity.setUsername("yangLiu");
        entity.setAge(24);
        entity.setAmount(new BigDecimal(4500.68D));
        entity.setScore(99.99D);
        entity.setWeight(50.50F);
        entity.setGmtModefied(new Date());
        entity.setLock(true);
        Boolean updateFlag = testMySqlDataTypeDao.updateEntity(entity);
        logger.info("------result = {}", updateFlag);
    }

    @Test
    public void deleteById() throws Exception {
        Boolean deleteFlag = testMySqlDataTypeDao.deleteById(36L);
        logger.info("------result = {}", deleteFlag);
    }

}