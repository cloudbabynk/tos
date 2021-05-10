package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_DAMG_LEVEL database table.
 * 
 */
@Entity
@Table(name="C_DAMG_LEVEL")
@NamedQuery(name="CDamgLevel.findAll", query="SELECT c FROM CDamgLevel c")
public class CDamgLevel extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DAMG_LEVEL_COD")
	private String damgLevelCod;

	@Column(name="DAMG_LEVEL")
	private String damgLevel;

	@Column(name="REMARK_TXT")
	private String remarkTxt;

	public CDamgLevel() {
	}

	public String getDamgLevelCod() {
		return this.damgLevelCod;
	}

	public void setDamgLevelCod(String damgLevelCod) {
		this.damgLevelCod = damgLevelCod;
	}

	public String getDamgLevel() {
		return this.damgLevel;
	}

	public void setDamgLevel(String damgLevel) {
		this.damgLevel = damgLevel;
	}

	public String getRemarkTxt() {
		return this.remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}


}