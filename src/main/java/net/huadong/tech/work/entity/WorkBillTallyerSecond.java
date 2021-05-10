package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the WORK_BILL_TALLYER_SECOND database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_TALLYER_SECOND")
@IdClass(WorkBillTallyerSecondPK.class)
@NamedQuery(name="WorkBillTallyerSecond.findAll", query="SELECT w FROM WorkBillTallyerSecond w")
public class WorkBillTallyerSecond extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Id
	@Column(name="CLASS_COD")
	private String classCod;

	@Id
	@Column(name="TALLY_COD")
	private String tallyCod;

	//bi-directional many-to-one association to WorkBillTallyerClass
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CLASS_COD", referencedColumnName="CLASS_COD", insertable=false, updatable=false),
		@JoinColumn(name="WORKBILL_NO", referencedColumnName="WORKBILL_NO", insertable=false, updatable=false)
		})
	private WorkBillTallyerClass workBillTallyerClass;

	public WorkBillTallyerSecond() {
	}


	public String getWorkbillNo() {
		return workbillNo;
	}


	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}


	public String getClassCod() {
		return classCod;
	}


	public void setClassCod(String classCod) {
		this.classCod = classCod;
	}


	public String getTallyCod() {
		return tallyCod;
	}


	public void setTallyCod(String tallyCod) {
		this.tallyCod = tallyCod;
	}


	@JsonIgnore
	public WorkBillTallyerClass getWorkBillTallyerClass() {
		return this.workBillTallyerClass;
	}

	public void setWorkBillTallyerClass(WorkBillTallyerClass workBillTallyerClass) {
		this.workBillTallyerClass = workBillTallyerClass;
	}

}