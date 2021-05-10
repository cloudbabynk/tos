package net.huadong.tech.thirdparty.service;

import java.io.Serializable;
import java.util.List;

/**
 * 第三方接口命令实体。
 * Created by jason on 3/25/17.
 */
public class Command implements Serializable{
    // 命令ID。
    private int id;
    // 命令参数。
    private List params;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }
}
