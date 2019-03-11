package com.cll.yl.back.sys.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cll.yl.back.common.entity.PageData;
import com.cll.yl.back.common.util.UUIDUtil;
import com.cll.yl.back.sys.dao.SysUserDao;
import com.cll.yl.back.sys.entity.SysUserEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 17:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-context.xml"})
public class SysUserDaoImplTest {

    private static final Logger logger = LoggerFactory.getLogger(SysUserDaoImplTest.class);

    @Resource
    private SysUserDao sysUserDao;

    private SysUserEntity sysUserEntity;

    @Before
    public void init() {
        sysUserEntity = new SysUserEntity();
        String uuid = UUIDUtil.getUUID();
        logger.info("------execute init().uuid = {}", uuid);
        sysUserEntity.setId(uuid);
        sysUserEntity.setUsername(uuid);
        sysUserEntity.setSalt(uuid);
        sysUserEntity.setPassword(uuid);
        sysUserEntity.setGmtCreate(new Date());

    }

    @Test
    public void insert() throws Exception {
        Boolean insert = sysUserDao.insert(sysUserEntity);
        logger.info("insert = {}", insert);
    }

    @Test
    public void insertByEntity() throws Exception {
        sysUserEntity.setGmtModified(new Date());
        sysUserEntity.setLock(true);
        Boolean insertByEntity = sysUserDao.insertByEntity(sysUserEntity);
        logger.info("insertByEntity = {}", insertByEntity);
    }

    @Test
    public void insertByPreparedStatement() throws Exception {
        Boolean insertByPreparedStatement = sysUserDao.insertByPreparedStatement(sysUserEntity);
        logger.info("insertByPreparedStatement = {}", insertByPreparedStatement);
    }

    @Test
    public void update() throws Exception {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId("ba23b70bc6bb4722871dcf9464388f1a");
        sysUserEntity.setGmtModified(new Date());
        sysUserEntity.setLock(true);
        Boolean update = sysUserDao.update(sysUserEntity);
        logger.info("update = {}", update);
    }

    @Test
    public void updateByEntity() throws Exception {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId("ba23b70bc6bb4722871dcf9464388f1a");
        sysUserEntity.setUsername("ba23b70bc6bb4722871dcf9464388f1a");
        sysUserEntity.setSalt("ba23b70bc6bb4722871dcf9464388f1a");
        sysUserEntity.setPassword("ba23b70bc6bb4722871dcf9464388f1a");
        sysUserEntity.setGmtModified(new Date());
        sysUserEntity.setLock(true);
        Boolean updateByEntity = sysUserDao.updateByEntity(sysUserEntity);
        logger.info("updateByEntity = {}", updateByEntity);
    }

    @Test
    public void selectForEntityById() throws Exception {
        SysUserEntity sysUserEntity = sysUserDao.selectForEntityById("f5882daa59044b2fb9daf9ceceec9ca3");
        logger.info("sysUserEntity = {}", sysUserEntity);
    }

    @Test
    public void listAllEntity() throws Exception {
        List<SysUserEntity> sysUserEntities = sysUserDao.listAllEntity();
        logger.info("list = {}", JSONArray.toJSONStringWithDateFormat(sysUserEntities, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void selectForMapById() throws Exception {
        String id = "f5882daa59044b2fb9daf9ceceec9ca3";
        Map<String, Object> map = sysUserDao.selectForMapById(id);
        logger.info("map = {}", JSONObject.toJSONString(map));
    }

    @Test
    public void delete() throws Exception {
        String id = "271653093869423c819e21ed894ed388";
        Boolean delete = sysUserDao.delete(id);
        logger.info("delete = {}", delete);
    }

    @Test
    public void selectForPage() throws Exception {
        PageData<SysUserEntity> sysUserEntityPageData = sysUserDao.selectForPage(null, 2, 4);

        logger.info("sysUserEntityPageData = {}", JSONObject.toJSONStringWithDateFormat(sysUserEntityPageData, "yyyy-MM-dd HH:mm:ss"));
    }

}