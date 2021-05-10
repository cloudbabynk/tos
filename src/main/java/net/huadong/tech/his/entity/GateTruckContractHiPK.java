package net.huadong.tech.his.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GATE_TRUCK_CONTRACT database table.
 * 
 */
public class GateTruckContractHiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private String ingateId;
	
	private String contractNo;

	public GateTruckContractHiPK() {
	}
	public String getIngateId() {
		return this.ingateId;
	}
	public void setIngateId(String ingateId) {
		this.ingateId = ingateId;
	}
	public String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GateTruckContractHiPK)) {
			return false;
		}
		GateTruckContractHiPK castOther = (GateTruckContractHiPK)other;
		return 
			this.ingateId.equals(castOther.ingateId)
			&& this.contractNo.equals(castOther.contractNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ingateId.hashCode();
		hash = hash * prime + this.contractNo.hashCode();
		
		return hash;
	}
}