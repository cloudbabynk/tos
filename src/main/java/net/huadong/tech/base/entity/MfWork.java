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
@Table(name="MF_WORK")
@NamedQuery(name="MfWork.findAll", query="SELECT c FROM MfWork c")
public class MfWork extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="C_SHIP_NAM")
	private String cShipNam;

	@Column(name="WORK_DATE")
	private Date workDate;
	
	@Column(name="MAFEI_NO")
	private String mafeiNo;
	
	@Column(name="WORK_TYP")
	private String workTyp;
	
	@Column(name="WORK_SIZE")
	private String workSize;
	
	@Column(name="CARGO_NUM")
	private String cargoNum;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="TEAM_NO")
	private String teamNo;

	public MfWork() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getWorkTyp() {
		return workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	public String getcShipNam() {
		return cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}


	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getMafeiNo() {
		return mafeiNo;
	}

	public void setMafeiNo(String mafeiNo) {
		this.mafeiNo = mafeiNo;
	}

	public String getWorkSize() {
		return workSize;
	}

	public void setWorkSize(String workSize) {
		this.workSize = workSize;
	}

	public String getCargoNum() {
		return cargoNum;
	}

	public void setCargoNum(String cargoNum) {
		this.cargoNum = cargoNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}



}