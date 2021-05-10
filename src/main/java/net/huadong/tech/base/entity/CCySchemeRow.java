package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the C_CY_SCHEME_ROW database table.
 * 
 */
@Entity
@Table(name="C_CY_SCHEME_ROW")
@NamedQuery(name="CCySchemeRow.findAll", query="SELECT c FROM CCySchemeRow c")
public class CCySchemeRow implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CCySchemeRowPK id;

	@Column(name="DISP_ROW_NO")
	private String dispRowNo;

	//bi-directional many-to-one association to CCyScheme
	@ManyToOne
	@JoinColumn(name="SCHEME")
	private CCyScheme CCyScheme;

	public CCySchemeRow() {
	}

	public CCySchemeRowPK getId() {
		return this.id;
	}

	public void setId(CCySchemeRowPK id) {
		this.id = id;
	}

	public String getDispRowNo() {
		return this.dispRowNo;
	}

	public void setDispRowNo(String dispRowNo) {
		this.dispRowNo = dispRowNo;
	}

	public CCyScheme getCCyScheme() {
		return this.CCyScheme;
	}

	public void setCCyScheme(CCyScheme CCyScheme) {
		this.CCyScheme = CCyScheme;
	}

}