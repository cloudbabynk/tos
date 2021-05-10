package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WORK_BILL_MACHINE database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_MACHINE")
@IdClass(WorkBillMachinePK.class)
@NamedQuery(name="WorkBillMachine.findAll", query="SELECT w FROM WorkBillMachine w")
public class WorkBillMachine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Id
	@Column(name="MACH_TYP_COD")
	private String machTypCod;

	@Id
	@Column(name="MACH_NO")
	private String machNo;
	
	@Column(name="DRIVER_COD")
	private String driverCod;


	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Column(name="WORK_TON_NUM")
	private BigDecimal workTonNum;
	
	@Transient
	private String machTypCodNam;

	public WorkBillMachine() {
	}

	public String getDriverCod() {
		return this.driverCod;
	}

	public void setDriverCod(String driverCod) {
		this.driverCod = driverCod;
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

	public String getWorkbillNo() {
		return workbillNo;
	}

	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}

	public String getMachTypCod() {
		return machTypCod;
	}

	public void setMachTypCod(String machTypCod) {
		this.machTypCod = machTypCod;
	}

	public String getMachNo() {
		return machNo;
	}

	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

	public String getMachTypCodNam() {
		return machTypCodNam;
	}

	public void setMachTypCodNam(String machTypCodNam) {
		this.machTypCodNam = machTypCodNam;
	}

}