package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WORK_BILL_TALLYER database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_TALLYER")
@IdClass(WorkBillTallyerPK.class)
@NamedQuery(name="WorkBillTallyer.findAll", query="SELECT w FROM WorkBillTallyer w")
public class WorkBillTallyer extends AuditEntityBean implements Serializable {
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

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Column(name="WORK_TON_NUM")
	private BigDecimal workTonNum;

	@Transient
	private String classCodNam;
	
	public WorkBillTallyer() {
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



	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	public BigDecimal getWorkTonNum() {
		return this.workTonNum;
	}

	public void setWorkTonNum(BigDecimal workTonNum) {
		this.workTonNum = workTonNum;
	}



	public String getClassCodNam() {
		return classCodNam;
	}



	public void setClassCodNam(String classCodNam) {
		this.classCodNam = classCodNam;
	}
	
	

}