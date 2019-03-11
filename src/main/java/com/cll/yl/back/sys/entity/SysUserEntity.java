package com.cll.yl.back.sys.entity;

import com.cll.yl.back.common.dao.CustomColumn;

import java.util.Date;

/**
 * 描述信息:
 * 后台用户
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 16:53
 */
public class SysUserEntity {

    private String id;

    private String username;

    private String salt;

    private String password;

    @CustomColumn(name = "gmt_create")
    private Date gmtCreate;

    @CustomColumn(name = "gmt_modified")
    private Date gmtModified;

    @CustomColumn(name = "is_lock")
    private Boolean lock;

    public SysUserEntity() {
    }

    @Override
    public String toString() {
        return "SysUserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", lock=" + lock +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}