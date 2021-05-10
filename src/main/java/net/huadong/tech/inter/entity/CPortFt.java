package net.huadong.tech.inter.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="C_PORT_FT")
@NamedQuery(name="CPortFt.findAll", query="SELECT c FROM CPortFt c")
public class CPortFt extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="PORT_FT_ID")
	private String portFtId;
	
	@Column(name="VC_PORT_NAME")
	private String vcPortName;
	
	@Column(name="VC_PORT_ID")
	private String vcPortId;
	
	@Column(name="PORT_ID")
	private String portId;
	
	public String getcPortNam() {
		return cPortNam;
	}

	public void setcPortNam(String cPortNam) {
		this.cPortNam = cPortNam;
	}

	@Transient
	private String cPortNam;
	
	public CPortFt() {
	}

	public String getPortFtId() {
		return portFtId;
	}

	public void setPortFtId(String portFtId) {
		this.portFtId = portFtId;
	}

	public String getVcPortName() {
		return vcPortName;
	}

	public void setVcPortName(String vcPortName) {
		this.vcPortName = vcPortName;
	}

	public String getVcPortId() {
		return vcPortId;
	}

	public void setVcPortId(String vcPortId) {
		this.vcPortId = vcPortId;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}

}