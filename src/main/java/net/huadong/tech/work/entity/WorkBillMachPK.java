package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_MACH database table.
 * 
 */
public class WorkBillMachPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WORKBILL_NO", insertable=false, updatable=false)
	private String workbillNo;

	@Column(name="MACH_TYP_COD")
	private String machTypCod;

	public WorkBillMachPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkBillMachPK)) {
			return false;
		}
		WorkBillMachPK castOther = (WorkBillMachPK)other;
		return 
			this.workbillNo.equals(castOther.workbillNo)
			&& this.machTypCod.equals(castOther.machTypCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workbillNo.hashCode();
		hash = hash * prime + this.machTypCod.hashCode();
		
		return hash;
	}
}