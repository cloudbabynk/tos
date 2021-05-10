package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the C_CY_SCHEME_ROW database table.
 * 
 */
@Embeddable
public class CCySchemeRowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String scheme;

	@Column(name="ROW_NO")
	private String rowNo;

	public CCySchemeRowPK() {
	}
	public String getScheme() {
		return this.scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getRowNo() {
		return this.rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CCySchemeRowPK)) {
			return false;
		}
		CCySchemeRowPK castOther = (CCySchemeRowPK)other;
		return 
			this.scheme.equals(castOther.scheme)
			&& this.rowNo.equals(castOther.rowNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.scheme.hashCode();
		hash = hash * prime + this.rowNo.hashCode();
		
		return hash;
	}
}