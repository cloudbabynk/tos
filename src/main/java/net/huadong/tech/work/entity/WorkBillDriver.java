package net.huadong.tech.work.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the WORK_BILL_DRIVER database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_DRIVER")
@IdClass(WorkBillDriverPK.class)
@NamedQuery(name="WorkBillDriver.findAll", query="SELECT w FROM WorkBillDriver w")
public class WorkBillDriver extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Id
	@Column(name="CLASS_COD")
	private String classCod;

	@Id
	@Column(name="DRIVER_COD")
	private String driverCod;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Transient
	private String classCodNam;
	
	public WorkBillDriver() {
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


	public String getDriverCod() {
		return driverCod;
	}


	public void setDriverCod(String driverCod) {
		this.driverCod = driverCod;
	}


	public BigDecimal getWorkNum() {
		return workNum;
	}


	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}


	public String getClassCodNam() {
		return classCodNam;
	}


	public void setClassCodNam(String classCodNam) {
		this.classCodNam = classCodNam;
	}
	

}