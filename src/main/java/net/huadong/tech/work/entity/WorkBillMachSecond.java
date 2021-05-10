package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.gate.entity.GateTruckContractPK;

import java.util.Date;


/**
 * The persistent class for the WORK_BILL_MACH_SECOND database table.
 * 
 */
@Entity
@Table(name="WORK_BILL_MACH_SECOND")
@IdClass(WorkBillMachSecondPK.class)
@NamedQuery(name="WorkBillMachSecond.findAll", query="SELECT w FROM WorkBillMachSecond w")
public class WorkBillMachSecond extends AuditEntityBean implements Serializable {
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
	
	


	//bi-directional many-to-one association to WorkBillMach
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="MACH_TYP_COD", referencedColumnName="MACH_TYP_COD", insertable=false, updatable=false),
		@JoinColumn(name="WORKBILL_NO", referencedColumnName="WORKBILL_NO", insertable=false, updatable=false)
		})
	private WorkBillMach workBillMach;

	public WorkBillMachSecond() {
	}


	public String getDriverCod() {
		return this.driverCod;
	}

	public void setDriverCod(String driverCod) {
		this.driverCod = driverCod;
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


	@JsonIgnore
	public WorkBillMach getWorkBillMach() {
		return this.workBillMach;
	}

	public void setWorkBillMach(WorkBillMach workBillMach) {
		this.workBillMach = workBillMach;
	}

}