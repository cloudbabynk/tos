package net.huadong.tech.damage.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the M_DELIVERY_RECORD database table.
 * 
 */
@Entity
@Table(name="M_DELIVERY_RECORD")
@NamedQuery(name="MDeliveryRecord.findAll", query="SELECT m FROM MDeliveryRecord m")
public class MDeliveryRecord extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String deliveryid;

	@Column(name="CLASS_RUN")
	private String classRun;

	@Column(name="DELIVERY_CONTENT")
	private String deliveryContent;

	@Column(name="WORK_DTE")
	private  Timestamp workDte;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Timestamp getWorkDte() {
		return workDte;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setWorkDte(Timestamp workDte) {
		this.workDte = workDte;
	}

	public MDeliveryRecord() {
	}

	public String getDeliveryid() {
		return this.deliveryid;
	}

	public void setDeliveryid(String deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getClassRun() {
		return this.classRun;
	}

	public void setClassRun(String classRun) {
		this.classRun = classRun;
	}

	public String getDeliveryContent() {
		return this.deliveryContent;
	}

	public void setDeliveryContent(String deliveryContent) {
		this.deliveryContent = deliveryContent;
	}


}