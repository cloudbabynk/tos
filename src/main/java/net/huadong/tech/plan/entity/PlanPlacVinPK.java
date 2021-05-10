package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PLAN_PLAC_VIN database table.
 * 
 */
public class PlanPlacVinPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PLAN_GROUP_NO")
	private long planGroupNo;

	@Column(name="VIN_NO")
	private String vinNo;

	public PlanPlacVinPK() {
	}
	public long getPlanGroupNo() {
		return this.planGroupNo;
	}
	public void setPlanGroupNo(long planGroupNo) {
		this.planGroupNo = planGroupNo;
	}
	public String getVinNo() {
		return this.vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlanPlacVinPK)) {
			return false;
		}
		PlanPlacVinPK castOther = (PlanPlacVinPK)other;
		return 
			(this.planGroupNo == castOther.planGroupNo)
			&& this.vinNo.equals(castOther.vinNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.planGroupNo ^ (this.planGroupNo >>> 32)));
		hash = hash * prime + this.vinNo.hashCode();
		
		return hash;
	}
}