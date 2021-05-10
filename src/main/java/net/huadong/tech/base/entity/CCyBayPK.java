package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the C_CY_BAY database table.
 * 
 */
public class CCyBayPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String cyAreaNo;

	private String cyBayNo;

	private String cyRowNo;

	public CCyBayPK() {
	}
	public String getCyAreaNo() {
		return this.cyAreaNo;
	}
	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}
	public String getCyBayNo() {
		return this.cyBayNo;
	}
	public void setCyBayNo(String cyBayNo) {
		this.cyBayNo = cyBayNo;
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
		if (!(other instanceof CCyBayPK)) {
			return false;
		}
		CCyBayPK castOther = (CCyBayPK)other;
		return 
			this.cyAreaNo.equals(castOther.cyAreaNo)
			&& this.cyBayNo.equals(castOther.cyBayNo)
			&& this.cyRowNo.equals(castOther.cyRowNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cyAreaNo.hashCode();
		hash = hash * prime + this.cyBayNo.hashCode();
		hash = hash * prime + this.cyRowNo.hashCode();
		
		return hash;
	}
}