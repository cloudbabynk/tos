package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the MESSAGE_INFO database table.
 * 
 */
@Entity
@Table(name="MESSAGE_INFO")
@NamedQuery(name="MessageInfo.findAll", query="SELECT m FROM MessageInfo m")
public class MessageInfo extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MID")
	private String mid;
    
	@Column(name="MESSAGE")
	private String message;
	
	@Transient
	private String time;

	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public MessageInfo() {
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}