package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the C_CY_SCHEME_BAY database table.
 * 
 */
@Embeddable
public class CCySchemeBayPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String scheme;

	@Column(name="BAY_NO")
	private String bayNo;

	public CCySchemeBayPK() {
	}
	public String getScheme() {
		return this.scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getBayNo() {
		return this.bayNo;
	}
	public void setBayNo(String bayNo) {
		this.bayNo = bayNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CCySchemeBayPK)) {
			return false;
		}
		CCySchemeBayPK castOther = (CCySchemeBayPK)other;
		return 
			this.scheme.equals(castOther.scheme)
			&& this.bayNo.equals(castOther.bayNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.scheme.hashCode();
		hash = hash * prime + this.bayNo.hashCode();
		
		return hash;
	}
}