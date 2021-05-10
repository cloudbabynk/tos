package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the WORK_BILL_DRIVER_SECOND database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_DRIVER_SECOND")
@IdClass(WorkBillDriverSecondPK.class)
@NamedQuery(name="WorkBillDriverSecond.findAll", query="SELECT w FROM WorkBillDriverSecond w")
public class WorkBillDriverSecond extends AuditEntityBean implements Serializable {
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


	//bi-directional many-to-one association to WorkBillDriverClass
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CLASS_COD", referencedColumnName="CLASS_COD", insertable=false, updatable=false),
		@JoinColumn(name="WORKBILL_NO", referencedColumnName="WORKBILL_NO", insertable=false, updatable=false)
		})
	private WorkBillDriverClass workBillDriverClass;

	public WorkBillDriverSecond() {
	}


	public WorkBillDriverClass getWorkBillDriverClass() {
		return this.workBillDriverClass;
	}

	public void setWorkBillDriverClass(WorkBillDriverClass workBillDriverClass) {
		this.workBillDriverClass = workBillDriverClass;
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

}