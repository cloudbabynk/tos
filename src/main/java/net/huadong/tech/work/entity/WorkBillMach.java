package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.his.entity.GateTruckContractHiPK;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WORK_BILL_MACH database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_MACH")
@IdClass(WorkBillMachPK.class)
@NamedQuery(name="WorkBillMach.findAll", query="SELECT w FROM WorkBillMach w")
public class WorkBillMach extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String JTC = "01";//交通车
	public static final String CC = "02";//叉车
	public static final String QYC = "03";//牵引车

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;
	
	@Id
	@Column(name="MACH_TYP_COD")
	private String machTypCod;
	
	@Column(name="MAN_NUM")
	private BigDecimal manNum;
	
	@Transient
	private String machTypCodNam;


	//bi-directional many-to-one association to WorkBill
	@ManyToOne
	@JoinColumn(name="WORKBILL_NO", insertable=false, updatable=false)
	private WorkBill workBill;

	public WorkBillMach() {
	}


	public BigDecimal getManNum() {
		return this.manNum;
	}

	public void setManNum(BigDecimal manNum) {
		this.manNum = manNum;
	}

	@JsonIgnore
	public WorkBill getWorkBill() {
		return this.workBill;
	}

	public void setWorkBill(WorkBill workBill) {
		this.workBill = workBill;
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


	public String getMachTypCodNam() {
		return machTypCodNam;
	}


	public void setMachTypCodNam(String machTypCodNam) {
		this.machTypCodNam = machTypCodNam;
	}

	
}