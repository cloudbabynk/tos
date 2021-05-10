package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_DAMG_AREA database table.
 * 
 */
@Entity
@Table(name="C_DAMG_AREA")
@NamedQuery(name="CDamgArea.findAll", query="SELECT c FROM CDamgArea c")
public class CDamgArea extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DAMG_AREA_COD")
	private String damgAreaCod;

	@Column(name="DAMG_AREA")
	private String damgArea;


	@Column(name="REMARK_TXT")
	private String remarkTxt;

	public CDamgArea() {
	}

	public String getDamgAreaCod() {
		return this.damgAreaCod;
	}

	public void setDamgAreaCod(String damgAreaCod) {
		this.damgAreaCod = damgAreaCod;
	}

	public String getDamgArea() {
		return this.damgArea;
	}

	public void setDamgArea(String damgArea) {
		this.damgArea = damgArea;
	}

	public String getRemarkTxt() {
		return remarkTxt;
	}

	public void setRemarkTxt(String remarkTxt) {
		this.remarkTxt = remarkTxt;
	}






}