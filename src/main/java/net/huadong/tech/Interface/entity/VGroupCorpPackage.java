package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_GROUP_CORP_PACKAGE database table.
 * 
 */
@Entity
@Table(name="V_GROUP_CORP_PACKAGE")
@NamedQuery(name="VGroupCorpPackage.findAll", query="SELECT v FROM VGroupCorpPackage v")
public class VGroupCorpPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PACKAGE_CODE")
	private String packageCode;

	@Column(name="PACKAGE_NAME")
	private String packageName;

	public VGroupCorpPackage() {
	}

	public String getPackageCode() {
		return this.packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}