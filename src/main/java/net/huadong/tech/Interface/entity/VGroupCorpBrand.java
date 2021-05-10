package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_BRAND database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_BRAND")
@NamedQuery(name="VGroupCorpBrand.findAll", query="SELECT v FROM VGroupCorpBrand v")
public class VGroupCorpBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BRAND_CODE")
	private String brandCode;

	@Column(name="BRAND_NAME")
	private String brandName;

	public VGroupCorpBrand() {
	}

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}