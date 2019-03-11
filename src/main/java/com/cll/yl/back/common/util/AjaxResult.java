package com.cll.yl.back.common.util;

/**
 * @author 陈林林
 * @date 2017/9/6
 * 异步刷新返回结果工具类
 */
public class AjaxResult {

    /**
     * 操作是否成功
     */
    private boolean success;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 操作结果描述信息
     */
    private String info;

    public AjaxResult() {
    }

    private AjaxResult(boolean success, String info) {
        this.success = success;
        this.info = info;
    }

    private AjaxResult(boolean success, String info, Object data) {
        this.success = success;
        this.info = info;
        this.data = data;
    }

    public static AjaxResult customError(String info) {
        return new AjaxResult(false, info);
    }

    public static AjaxResult error() {
        return new AjaxResult(false, "操作失败");
    }

    public static AjaxResult success() {
        return new AjaxResult(true, "操作成功");
    }

    public static AjaxResult successWithInfo(String info) {
        return new AjaxResult(true, info);
    }

    public static AjaxResult successWithData(String info, Object data) {
        return new AjaxResult(true, info, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
