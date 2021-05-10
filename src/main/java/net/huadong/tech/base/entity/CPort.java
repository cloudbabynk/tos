package net.huadong.tech.base.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the C_PORT database table.
 * 
 */
@Entity
@Table(name="C_PORT")
@NamedQuery(name="CPort.findAll", query="SELECT c FROM CPort c")
public class CPort extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)

	@NotNull
	@Column(name="PORT_ID")
	private String portId;

	@Column(name="C_PORT_NAM")
	private String cPortNam;

	@Column(name="COUNTRY_COD")
	private String countryCod;

	@Column(name="E_PORT_NAM")
	private String ePortNam;

	@Column(name="PORT_CLASS")
	private String portClass;

	@Column(name="PORT_COD")
	private String portCod;

	@Column(name="PORT_SHORT")
	private String portShort;

	@Column(name="STAT_PORT_COD")
	private String statPortCod;


	public CPort() {
	}

	public String getPortId() {
		return this.portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}


	public String getCountryCod() {
		return this.countryCod;
	}

	public void setCountryCod(String countryCod) {
		this.countryCod = countryCod;
	}

	public String getcPortNam() {
		return cPortNam;
	}

	public void setcPortNam(String cPortNam) {
		this.cPortNam = cPortNam;
	}

	public String getePortNam() {
		return ePortNam;
	}

	public void setePortNam(String ePortNam) {
		this.ePortNam = ePortNam;
	}

	public String getPortClass() {
		return this.portClass;
	}

	public void setPortClass(String portClass) {
		this.portClass = portClass;
	}

	public String getPortCod() {
		return this.portCod;
	}

	public void setPortCod(String portCod) {
		this.portCod = portCod;
	}

	public String getPortShort() {
		return this.portShort;
	}

	public void setPortShort(String portShort) {
		this.portShort = portShort;
	}

	public String getStatPortCod() {
		return this.statPortCod;
	}

	public void setStatPortCod(String statPortCod) {
		this.statPortCod = statPortCod;
	}

}