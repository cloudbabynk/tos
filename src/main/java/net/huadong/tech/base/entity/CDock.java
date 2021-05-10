package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_DOCK database table.
 * 
 */
@Entity
@Table(name="C_DOCK")
@NamedQuery(name="CDock.findAll", query="SELECT c FROM CDock c")
public class CDock extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="C_DOCK_NAM")
	private String cDockNam;

	@Column(name="DOCK_NAM")
	private String dockNam;

	@Column(name="E_DOCK_NAM")
	private String eDockNam;

	@Column(name="REMARKS")
	private String remarks;


	public CDock() {
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getcDockNam() {
		return cDockNam;
	}

	public void setcDockNam(String cDockNam) {
		this.cDockNam = cDockNam;
	}

	public String getDockNam() {
		return this.dockNam;
	}

	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}

	public String geteDockNam() {
		return this.eDockNam;
	}

	public void seteDockNam(String eDockNam) {
		this.eDockNam = eDockNam;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

}