package com.cll.yl.back.sys.dao;

import com.cll.yl.back.common.entity.PageData;
import com.cll.yl.back.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 17:00
 */
public interface SysUserDao {

    /**
     * 新增对象
     * @param sysUserEntity 新增的对象
     * @return 新增结果
     */
    Boolean insert(SysUserEntity sysUserEntity);

    /**
     * 新增对象
     * @param sysUserEntity 新增的对象
     * @return 新增结果
     */
    Boolean insertByEntity(SysUserEntity sysUserEntity);

    /**
     * 新增对象
     * @param sysUserEntity 新增的对象
     * @return 新增结果
     */
    Boolean insertByPreparedStatement(SysUserEntity sysUserEntity);

    /**
     * 修改对象
     * @param sysUserEntity 修改之后的对象
     * @return  修改结果
     */
    Boolean update(SysUserEntity sysUserEntity);

    /**
     * 修改对象
     * @param sysUserEntity 修改之后的对象
     * @return  修改结果
     */
    Boolean updateByEntity(SysUserEntity sysUserEntity);


    /**
     * 根据主键查询实体
     * @param id    主键
     * @return  实体对象
     */
    SysUserEntity selectForEntityById(String id);

    /**
     * 获取所有的对象
     * @return  对象列表
     */
    List<SysUserEntity> listAllEntity();

    /**
     * 根据主键查询Map集
     * @param id    主键
     * @return  Map集
     */
    Map<String, Object> selectForMapById(String id);

    /**
     * 根据主键删除对象
     * @param id    主键
     * @return  删除结果
     */
    Boolean delete(String id);

    /**
     * 分页查询数据
     * @param params    查询参数
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @return  分页数据
     */
    PageData<SysUserEntity> selectForPage(Map<String, Object> params, int pageNo, int pageSize);
}
