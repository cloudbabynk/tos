package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_MACH_TYP database table.
 * 
 */
@Entity
@Table(name="C_MACH_TYP")
@NamedQuery(name="CMachTyp.findAll", query="SELECT c FROM CMachTyp c")
public class CMachTyp extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MACH_TYP_COD")
	private String machTypCod;

	@Column(name="MACH_TYP")
	private String machTyp;

	
	
	public CMachTyp() {
	}

	public String getMachTypCod() {
		return this.machTypCod;
	}

	public void setMachTypCod(String machTypCod) {
		this.machTypCod = machTypCod;
	}

	public String getMachTyp() {
		return this.machTyp;
	}

	public void setMachTyp(String machTyp) {
		this.machTyp = machTyp;
	}



}