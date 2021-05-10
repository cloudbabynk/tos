package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_MACHINE database table.
 * 
 */
@Entity
@Table(name="C_MACHINE")
@NamedQuery(name="CMachine.findAll", query="SELECT c FROM CMachine c")
public class CMachine extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MACH_NO")
	private String machNo;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="MACH_NAM")
	private String machNam;

	@Column(name="MACH_TYP")
	private String machTyp;
	
	@Transient
	private boolean checked;
	
	@Transient
	private String driverCod;


	public CMachine() {
	}

	public String getMachNo() {
		return this.machNo;
	}

	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getMachNam() {
		return this.machNam;
	}

	public void setMachNam(String machNam) {
		this.machNam = machNam;
	}

	public String getMachTyp() {
		return this.machTyp;
	}

	public void setMachTyp(String machTyp) {
		this.machTyp = machTyp;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getDriverCod() {
		return driverCod;
	}

	public void setDriverCod(String driverCod) {
		this.driverCod = driverCod;
	}

 
}