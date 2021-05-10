package net.huadong.tech.ship.entity;

import net.huadong.idev.hdmessagecode.HdMessageCode;

public class ReturnObject extends HdMessageCode {
    public ReturnObject() {
        super();
        super.setCode("1");
        super.setMessage("操作成功！");
    }

    public void setFailMsg(String msg) {
        super.setCode("-1");
        super.setMessage(msg);
    }
}
