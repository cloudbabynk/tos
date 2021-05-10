package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WORK_BILL_DRIVER_SECOND database table.
 * 
 */
public class WorkBillDriverSecondPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="WORKBILL_NO", insertable=false, updatable=false)
	private String workbillNo;

	@Column(name="CLASS_COD", insertable=false, updatable=false)
	private String classCod;

	@Column(name="DRIVER_COD")
	private String driverCod;

	public WorkBillDriverSecondPK() {
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
	public String getDriverCod() {
		return this.driverCod;
	}
	public void setDriverCod(String driverCod) {
		this.driverCod = driverCod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WorkBillDriverSecondPK)) {
			return false;
		}
		WorkBillDriverSecondPK castOther = (WorkBillDriverSecondPK)other;
		return 
			this.workbillNo.equals(castOther.workbillNo)
			&& this.classCod.equals(castOther.classCod)
			&& this.driverCod.equals(castOther.driverCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.workbillNo.hashCode();
		hash = hash * prime + this.classCod.hashCode();
		hash = hash * prime + this.driverCod.hashCode();
		
		return hash;
	}
}