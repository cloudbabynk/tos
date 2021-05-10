package net.huadong.tech.thirdparty.service;

import java.io.Serializable;

/**
 * 返回结果。
 * Created by jason on 3/25/17.
 */
public class Result implements Serializable {
    // 状态。 1：为成功；-1：为失败！
    private int status;
    // 返回结果描述。
    private String info;
    // 返回的数据。
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
