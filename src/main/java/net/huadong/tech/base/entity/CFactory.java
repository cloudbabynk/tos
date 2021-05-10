package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_FACTORY database table.
 * 
 */
@Entity
@Table(name="C_FACTORY")
@NamedQuery(name="CFactory.findAll", query="SELECT c FROM CFactory c")
public class CFactory extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FACTORY_COD")
	private String factoryCod;

	@Column(name="FACTORY_NAM")
	private String factoryNam;

	
	private String remarks;

	
	public CFactory() {
	}

	public String getFactoryCod() {
		return this.factoryCod;
	}

	public void setFactoryCod(String factoryCod) {
		this.factoryCod = factoryCod;
	}

	public String getFactoryNam() {
		return this.factoryNam;
	}

	public void setFactoryNam(String factoryNam) {
		this.factoryNam = factoryNam;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

}