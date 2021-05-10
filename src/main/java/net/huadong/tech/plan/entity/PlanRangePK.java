package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

public class PlanRangePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PLAN_GROUP_NO")
	private long planGroupNo;

	@Column(name="SEQ_NO")
	private long seqNo;

	public PlanRangePK() {
	}
	public long getPlanGroupNo() {
		return this.planGroupNo;
	}
	public void setPlanGroupNo(long planGroupNo) {
		this.planGroupNo = planGroupNo;
	}
	public long getSeqNo() {
		return this.seqNo;
	}
	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlanRangePK)) {
			return false;
		}
		PlanRangePK castOther = (PlanRangePK)other;
		return 
			(this.planGroupNo == castOther.planGroupNo)
			&& (this.seqNo == castOther.seqNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.planGroupNo ^ (this.planGroupNo >>> 32)));
		hash = hash * prime + ((int) (this.seqNo ^ (this.seqNo >>> 32)));
		
		return hash;
	}
}