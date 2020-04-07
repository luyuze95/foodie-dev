package com.luyuze.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyJsonResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    @JsonIgnore
    private String ok;	// 不使用

    public static MyJsonResult build(Integer status, String msg, Object data) {
        return new MyJsonResult(status, msg, data);
    }

    public static MyJsonResult build(Integer status, String msg, Object data, String ok) {
        return new MyJsonResult(status, msg, data, ok);
    }

    public static MyJsonResult ok(Object data) {
        return new MyJsonResult(data);
    }

    public static MyJsonResult ok() {
        return new MyJsonResult(null);
    }

    public static MyJsonResult errorMsg(String msg) {
        return new MyJsonResult(500, msg, null);
    }

    public static MyJsonResult errorMap(Object data) {
        return new MyJsonResult(501, "error", data);
    }

    public static MyJsonResult errorTokenMsg(String msg) {
        return new MyJsonResult(502, msg, null);
    }

    public static MyJsonResult errorException(String msg) {
        return new MyJsonResult(555, msg, null);
    }

    public static MyJsonResult errorUserQQ(String msg) {
        return new MyJsonResult(556, msg, null);
    }

    public MyJsonResult() {

    }

    public MyJsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public MyJsonResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public MyJsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
