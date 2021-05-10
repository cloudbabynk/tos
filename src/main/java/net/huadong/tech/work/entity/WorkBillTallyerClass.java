package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the WORK_BILL_TALLYER_CLASS database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_TALLYER_CLASS")
@IdClass(WorkBillTallyerClassPK.class)
@NamedQuery(name="WorkBillTallyerClass.findAll", query="SELECT w FROM WorkBillTallyerClass w")
public class WorkBillTallyerClass extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Id
	@Column(name="CLASS_COD")
	private String classCod;
	
	@Transient
	private String classCodNam;


	public WorkBillTallyerClass() {
	}


	public String getClassCodNam() {
		return classCodNam;
	}


	public void setClassCodNam(String classCodNam) {
		this.classCodNam = classCodNam;
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



}