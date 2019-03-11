package com.cll.yl.back.sys.dao;

import com.cll.yl.back.sys.entity.TestMySqlDataTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 21:41
 */
public interface TestMySqlDataTypeDao {

    /**
     * 根据主键查询实体
     * @param id    主键
     * @return  实体
     */
    TestMySqlDataTypeEntity queryById(long id);

    /**
     * 根据ID查询Map对象
     * @param id    主键
     * @return Map对象
     */
    Map<String, Object> selectById(long id);

    /**
     * 查询所有
     * @return  所有记录
     */
    List<TestMySqlDataTypeEntity> listEntities();

    /**
     * 新增实体对象
     * @param testMySqlDataTypeEntity    实体对象
     * @return  新增结果
     */
    Boolean insertEntity(TestMySqlDataTypeEntity testMySqlDataTypeEntity);

    /**
     * 修改实体对象
     * @param testMySqlDataTypeEntity    实体对象
     * @return  修改结果
     */
    Boolean updateEntity(TestMySqlDataTypeEntity testMySqlDataTypeEntity);

    /**
     * 根据主键删除对象
     * @param id    对象主键
     * @return  删除结果
     */
    Boolean deleteById(Long id);
}