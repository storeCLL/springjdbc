package com.cll.yl.back.common.entity;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述信息:
 * 分页封装对象
 * @author CLL
 * @version 1.0
 * @date 2019/3/10 10:56
 */
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 6114445305150795643L;

    /**
     * 当前查询数据库总记录数
     */
    private int totalSize;
    /**
     * 一共分页数
     */
    private int totalPage;
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 分页数据
     */
    private List<T> data;

    public PageData() {
    }

    public PageData(int totalSize, int totalPage, int currentPage, List<T> data) {
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "totalSize=" + totalSize +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", data=" + JSONArray.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss") +
                '}';
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        this.data = data;
    }
}