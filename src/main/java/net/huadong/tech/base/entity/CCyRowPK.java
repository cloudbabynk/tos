package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the C_CY_ROW database table.
 * 
 */
public class CCyRowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String cyAreaNo;

	private String cyRowNo;

	public CCyRowPK() {
	}
	public String getCyAreaNo() {
		return this.cyAreaNo;
	}
	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}
	public String getCyRowNo() {
		return this.cyRowNo;
	}
	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CCyRowPK)) {
			return false;
		}
		CCyRowPK castOther = (CCyRowPK)other;
		return 
			this.cyAreaNo.equals(castOther.cyAreaNo)
			&& this.cyRowNo.equals(castOther.cyRowNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cyAreaNo.hashCode();
		hash = hash * prime + this.cyRowNo.hashCode();
		
		return hash;
	}
}