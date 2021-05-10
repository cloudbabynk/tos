/**
  * Copyright 2017 bejson.com 
  */
package net.huadong.tech.thirdparty.service;

import java.io.Serializable;

public class RequestData implements Serializable {

	private int id;// 接口命令id，如1001
	private Object data;// 传递的参数
	private String coId;// 第三方调用id
	private String coKey;// 第三方调用者接口对应秘钥

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoId() {
		return coId;
	}

	public void setCoId(String coId) {
		this.coId = coId;
	}

	public String getCoKey() {
		return coKey;
	}

	public void setCoKey(String coKey) {
		this.coKey = coKey;
	}

}