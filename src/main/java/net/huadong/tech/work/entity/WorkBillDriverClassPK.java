package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_DRIVER_CLASS database table.
 * 
 */
public class WorkBillDriverClassPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String workbillNo;

	private String classCod;

	public WorkBillDriverClassPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkBillDriverClassPK)) {
			return false;
		}
		WorkBillDriverClassPK castOther = (WorkBillDriverClassPK)other;
		return 
			this.workbillNo.equals(castOther.workbillNo)
			&& this.classCod.equals(castOther.classCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workbillNo.hashCode();
		hash = hash * prime + this.classCod.hashCode();
		
		return hash;
	}
}