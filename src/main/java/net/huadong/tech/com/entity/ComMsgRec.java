package net.huadong.tech.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="COM_MSG_REC")
@NamedQuery(name="ComMsgRec.findAll", query="SELECT c FROM ComMsgRec c")
public class ComMsgRec implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="REC_ID")
	private String recId;
	
	@Column(name="SEND_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp sendTim;
	
	@Column(name="SEND_NAM")
	private String sendNam;
	
	@Column(name="SEND_CONTENT")
	private String sendContent;
	
	@Column(name="SEND_MSG_TYP")
	private String sendMsgTyp;
	
	@Column(name="READ_ID")
	private String readId;
	
	@Column(name="READ_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp readTim;
	
	@Transient
	private String unReadNum;
	

	public ComMsgRec() {
	}


	public String getUnReadNum() {
		return unReadNum;
	}


	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}


	public String getRecId() {
		return recId;
	}


	public void setRecId(String recId) {
		this.recId = recId;
	}




	public String getSendNam() {
		return sendNam;
	}


	public void setSendNam(String sendNam) {
		this.sendNam = sendNam;
	}


	public String getSendContent() {
		return sendContent;
	}


	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}


	public String getSendMsgTyp() {
		return sendMsgTyp;
	}


	public void setSendMsgTyp(String sendMsgTyp) {
		this.sendMsgTyp = sendMsgTyp;
	}


	public String getReadId() {
		return readId;
	}


	public void setReadId(String readId) {
		this.readId = readId;
	}


	public Timestamp getSendTim() {
		return sendTim;
	}


	public void setSendTim(Timestamp sendTim) {
		this.sendTim = sendTim;
	}


	public Timestamp getReadTim() {
		return readTim;
	}


	public void setReadTim(Timestamp readTim) {
		this.readTim = readTim;
	}



}