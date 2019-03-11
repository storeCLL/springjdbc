package com.cll.yl.back.sys.dao.impl;

import com.cll.yl.back.common.dao.BaseDao;
import com.cll.yl.back.sys.dao.TestMySqlDataTypeDao;
import com.cll.yl.back.sys.entity.TestMySqlDataTypeEntity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 21:55
 */
@Repository
public class TestMySqlDataTypeDaoImpl extends BaseDao implements TestMySqlDataTypeDao {

    @Override
    public TestMySqlDataTypeEntity queryById(long id) {
        String sql = getXmlSqlById("selectById");
        return selectForEntity(sql, new Object[]{id}, TestMySqlDataTypeEntity.class);
    }

    @Override
    public Map<String, Object> selectById(long id) {
        String sql = getXmlSql();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TestMySqlDataTypeEntity> listEntities() {
        String sql = getXmlSql();
        return selectForEntityList(sql, TestMySqlDataTypeEntity.class);
    }

    @Override
    public Boolean insertEntity(TestMySqlDataTypeEntity testMySqlDataTypeEntity) {
        String sql = getXmlSql();
        return insertEntity(sql, testMySqlDataTypeEntity);
    }

    @Override
    public Boolean updateEntity(TestMySqlDataTypeEntity testMySqlDataTypeEntity) {
        String sql = getXmlSql();
        return updateEntity(sql, testMySqlDataTypeEntity);
    }

    @Override
    public Boolean deleteById(Long id) {
        String sql = getXmlSql();
        return jdbcTemplate.update(sql, id) == 1;
    }
}
