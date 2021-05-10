package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_TALLYER_SECOND database table.
 * 
 */
public class WorkBillTallyerSecondPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WORKBILL_NO", insertable=false, updatable=false)
	private String workbillNo;

	@Column(name="CLASS_COD", insertable=false, updatable=false)
	private String classCod;

	@Column(name="TALLY_COD")
	private String tallyCod;

	public WorkBillTallyerSecondPK() {
	}
	public String getWorkbillNo() {
		return this.workbillNo;
	}
	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}
	public String getClassCod() {
		return this.classCod;
	}
	public void setClassCod(String classCod) {
		this.classCod = classCod;
	}
	public String getTallyCod() {
		return this.tallyCod;
	}
	public void setTallyCod(String tallyCod) {
		this.tallyCod = tallyCod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkBillTallyerSecondPK)) {
			return false;
		}
		WorkBillTallyerSecondPK castOther = (WorkBillTallyerSecondPK)other;
		return 
			this.workbillNo.equals(castOther.workbillNo)
			&& this.classCod.equals(castOther.classCod)
			&& this.tallyCod.equals(castOther.tallyCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workbillNo.hashCode();
		hash = hash * prime + this.classCod.hashCode();
		hash = hash * prime + this.tallyCod.hashCode();
		
		return hash;
	}
}