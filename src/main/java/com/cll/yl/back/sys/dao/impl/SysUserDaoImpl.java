package com.cll.yl.back.sys.dao.impl;

import com.cll.yl.back.common.dao.BaseDao;
import com.cll.yl.back.common.entity.PageData;
import com.cll.yl.back.sys.dao.SysUserDao;
import com.cll.yl.back.sys.entity.SysUserEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 17:05
 */
@Repository
public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    @Override
    public Boolean insert(SysUserEntity sysUserEntity) {
        if (null == sysUserEntity) {
            return null;
        }
//        String sql = "insert into back_sys_user(id, username, salt, password, gmt_create) values(?,?,?,?,?)";
        String sql = getXmlSql();
        return jdbcTemplate.update(sql, sysUserEntity.getId(), sysUserEntity.getUsername(), sysUserEntity.getSalt(), sysUserEntity.getPassword(), sysUserEntity.getGmtCreate()) == 1;
    }

    @Override
    public Boolean insertByEntity(SysUserEntity sysUserEntity) {
        if (null == sysUserEntity) {
            return null;
        }
//        String sql = "insert into back_sys_user(id, username, salt, password, gmt_create, gmt_modified, is_lock) values(?,?,?,?,?,?,?)";
        String sql = getXmlSql();
        return insertEntity(sql, sysUserEntity);
    }

    @Override
    public Boolean insertByPreparedStatement(SysUserEntity sysUserEntity) {
        if (null == sysUserEntity) {
            return null;
        }
//        String sql = "insert into back_sys_user(id, username, salt, password, gmt_create) values(?,?,?,?,?)";
        String sql = getXmlSqlById("insert");
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, sysUserEntity.getId());
                ps.setString(2, sysUserEntity.getUsername());
                ps.setString(3, sysUserEntity.getSalt());
                ps.setString(4, sysUserEntity.getPassword());
                ps.setTimestamp(5, new Timestamp(sysUserEntity.getGmtCreate().getTime()));
            }
        }) == 1;
    }

    @Override
    public Boolean update(SysUserEntity sysUserEntity) {
        if (null == sysUserEntity) {
            return null;
        }
//        String sql = "update back_sys_user set username = ?, salt = ?, password = ?, gmt_modified = ? where id = ?";
        String sql = getXmlSql();
        return jdbcTemplate.update(sql, sysUserEntity.getUsername(), sysUserEntity.getSalt(), sysUserEntity.getPassword(), sysUserEntity.getGmtModified(), sysUserEntity.getId()) == 1;
    }

    @Override
    public Boolean updateByEntity(SysUserEntity sysUserEntity) {
        if (null == sysUserEntity) {
            return null;
        }
//        String sql = "update back_sys_user set username = ?, salt = ?, password = ?, gmt_modified = ?, is_lock = ? where id = ?";
        String sql = getXmlSql();
        return updateEntity(sql, sysUserEntity);
    }

    @Override
    public SysUserEntity selectForEntityById(String id) {
//        String sql = "select * from back_sys_user where id = ? ";
        String sql = getXmlSqlById("selectById");
        return selectForEntity(sql, new Object[]{id}, SysUserEntity.class);
    }

    @Override
    public List<SysUserEntity> listAllEntity() {
//        String sql = "select * from back_sys_user ";
        String sql = getXmlSqlById("selectAll");
        return selectForEntityList(sql, SysUserEntity.class);
    }

    @Override
    public Map<String, Object> selectForMapById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
//        String sql = "SELECT * FROM back_sys_user WHERE id = ?";
        String sql = getXmlSqlById("selectById");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
//        String sql = "DELETE FROM back_sys_user WHERE id = ?";
        String sql = getXmlSqlById("deleteById");
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public PageData<SysUserEntity> selectForPage(Map<String, Object> params, int pageNo, int pageSize) {
        String sql = getXmlSql();
        return selectForPage(sql, null, pageNo, pageSize, SysUserEntity.class);
    }
}
