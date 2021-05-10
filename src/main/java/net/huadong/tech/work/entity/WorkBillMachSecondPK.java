package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_MACH_SECOND database table.
 * 
 */
public class WorkBillMachSecondPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WORKBILL_NO", insertable=false, updatable=false)
	private String workbillNo;

	@Column(name="MACH_TYP_COD", insertable=false, updatable=false)
	private String machTypCod;

	@Column(name="MACH_NO")
	private String machNo;

	public WorkBillMachSecondPK() {
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
		if (!(other instanceof WorkBillMachSecondPK)) {
			return false;
		}
		WorkBillMachSecondPK castOther = (WorkBillMachSecondPK)other;
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