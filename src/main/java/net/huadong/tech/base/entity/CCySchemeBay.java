package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the C_CY_SCHEME_BAY database table.
 * 
 */
@Entity
@Table(name="C_CY_SCHEME_BAY")
@NamedQuery(name="CCySchemeBay.findAll", query="SELECT c FROM CCySchemeBay c")
public class CCySchemeBay implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CCySchemeBayPK id;

	@Column(name="DISP_BAY_NO")
	private String dispBayNo;

	//bi-directional many-to-one association to CCyScheme
	@ManyToOne
	@JoinColumn(name="SCHEME")
	private CCyScheme CCyScheme;

	public CCySchemeBay() {
	}

	public CCySchemeBayPK getId() {
		return this.id;
	}

	public void setId(CCySchemeBayPK id) {
		this.id = id;
	}

	public String getDispBayNo() {
		return this.dispBayNo;
	}

	public void setDispBayNo(String dispBayNo) {
		this.dispBayNo = dispBayNo;
	}

	public CCyScheme getCCyScheme() {
		return this.CCyScheme;
	}

	public void setCCyScheme(CCyScheme CCyScheme) {
		this.CCyScheme = CCyScheme;
	}

}