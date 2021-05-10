package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import net.huadong.tech.base.bean.AuditEntityBean;
/**
 * 
 * @ClassName IntfQueqeExport
 * @author zhaochao
 * @Description Tos数据接口表
 * @Date 2017年9月20日
 */
@Entity
@Table(name="INTF_QUEUE_EXPORT")
public class IntfQueqeExport implements Serializable{

	private static final long serialVersionUID = -2414458957700851742L;
	
	@Id
	@NotNull
	@UuidGenerator(name="UUID")
	@GeneratedValue(generator = "UUID")
	@Size(min = 1,max = 36)
	@Column(name = "ID")
	private String id;
	
	@Column(name="QUEUE_ID")
	private String queueId;
	
	@Column(name="SEQ_NO")
	private BigDecimal seqNo;
	
	/*@Column(name="CORREL_ID")
	private String correlId;*/
	
	@Column(name="TYP")
	private String typ;
	
	@Column(name="SUB_TYP")
	private String subTyp;
	
	@Column(name="ACTION")
	private String action;
	
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	
	@Column(name="APP")
	private String app;
	
	@Column(name="OPERATOR_ID")
	private String operatorId;
	
	/*@Column(name="STATE")
	private String state;*/
	
	/*@Column(name="PROPERTY")
	private String porperty;*/
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="CREATED_ON")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp createdOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	/*public String getCorrelId() {
		return correlId;
	}

	public void setCorrelId(String correlId) {
		this.correlId = correlId;
	}*/

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getSubTyp() {
		return subTyp;
	}

	public void setSubTyp(String subTyp) {
		this.subTyp = subTyp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/*public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}*/

	/*public String getPorperty() {
		return porperty;
	}

	public void setPorperty(String porperty) {
		this.porperty = porperty;
	}*/

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "IntfQueqeExport [id=" + id + ", queueId=" + queueId + ", seqNo=" + seqNo + ", typ=" + typ + ", subTyp=" + subTyp + ", action=" + action + ", ipAddress=" + ipAddress + ", app="
				+ app + ", operatorId=" + operatorId + ", content="
				+ content + ", createdOn=" + createdOn + "]";
	}

	
}
