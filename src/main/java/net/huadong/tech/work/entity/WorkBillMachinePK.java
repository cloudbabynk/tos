package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_MACHINE database table.
 * 
 */
public class WorkBillMachinePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Column(name="MACH_TYP_COD")
	private String machTypCod;

	@Column(name="MACH_NO")
	private String machNo;

	public WorkBillMachinePK() {
	}
	public String getWorkbillNo() {
		return this.workbillNo;
	}
	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}
	public String getMachTypCod() {
		return this.machTypCod;
	}
	public void setMachTypCod(String machTypCod) {
		this.machTypCod = machTypCod;
	}
	public String getMachNo() {
		return this.machNo;
	}
	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkBillMachinePK)) {
			return false;
		}
		WorkBillMachinePK castOther = (WorkBillMachinePK)other;
		return 
			this.workbillNo.equals(castOther.workbillNo)
			&& this.machTypCod.equals(castOther.machTypCod)
			&& this.machNo.equals(castOther.machNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workbillNo.hashCode();
		hash = hash * prime + this.machTypCod.hashCode();
		hash = hash * prime + this.machNo.hashCode();
		
		return hash;
	}
}