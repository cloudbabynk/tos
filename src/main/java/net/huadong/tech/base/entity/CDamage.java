package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_DAMAGE database table.
 * 
 */
@Entity
@Table(name="C_DAMAGE")
@NamedQuery(name="CDamage.findAll", query="SELECT c FROM CDamage c")
public class CDamage extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DAM_COD")
	private String damCod;

	@Column(name="DAM_NAM")
	private String damNam;

	@Column(name="E_DAM_NAM")
	private String eDamNam;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	public CDamage() {
	}

	public String getDamCod() {
		return this.damCod;
	}

	public void setDamCod(String damCod) {
		this.damCod = damCod;
	}

	public String getDamNam() {
		return this.damNam;
	}

	public void setDamNam(String damNam) {
		this.damNam = damNam;
	}

	public String geteDamNam() {
		return eDamNam;
	}

	public void seteDamNam(String eDamNam) {
		this.eDamNam = eDamNam;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}


}