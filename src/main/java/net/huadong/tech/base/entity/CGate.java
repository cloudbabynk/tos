package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_GATE database table.
 * 
 */
@Entity
@Table(name="C_GATE")
@NamedQuery(name="CGate.findAll", query="SELECT c FROM CGate c")
public class CGate extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GATE_NO")
	private String gateNo;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="GATE_NAM")
	private String gateNam;

	@Column(name="GATE_TYP")
	private String gateTyp;

	@Column(name="MACH_IP")
	private String machIp;

	
	private String remarks;

	private BigDecimal x;

	private BigDecimal y;
	
	@Transient
	private String dockCodNam;

	public CGate() {
	}

	public String getGateNo() {
		return this.gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getGateNam() {
		return this.gateNam;
	}

	public void setGateNam(String gateNam) {
		this.gateNam = gateNam;
	}

	public String getGateTyp() {
		return this.gateTyp;
	}

	public void setGateTyp(String gateTyp) {
		this.gateTyp = gateTyp;
	}

	public String getMachIp() {
		return this.machIp;
	}

	public void setMachIp(String machIp) {
		this.machIp = machIp;
	}



	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public BigDecimal getX() {
		return this.x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return this.y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public String getDockCodNam() {
		return dockCodNam;
	}

	public void setDockCodNam(String dockCodNam) {
		this.dockCodNam = dockCodNam;
	}

}