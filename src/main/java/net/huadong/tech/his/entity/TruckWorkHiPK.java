package net.huadong.tech.his.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the TRUCK_WORK_HIS database table.
 * 
 */
@Embeddable
public class TruckWorkHiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String contractNo;

	private BigDecimal portCarNo;

	public TruckWorkHiPK() {
	}
	public String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public BigDecimal getPortCarNo() {
		return portCarNo;
	}
	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractNo == null) ? 0 : contractNo.hashCode());
		result = prime * result + ((portCarNo == null) ? 0 : portCarNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TruckWorkHiPK other = (TruckWorkHiPK) obj;
		if (contractNo == null) {
			if (other.contractNo != null)
				return false;
		} else if (!contractNo.equals(other.contractNo))
			return false;
		if (portCarNo == null) {
			if (other.portCarNo != null)
				return false;
		} else if (!portCarNo.equals(other.portCarNo))
			return false;
		return true;
	}
	
	

}