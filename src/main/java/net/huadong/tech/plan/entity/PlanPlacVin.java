package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the PLAN_PLAC_VIN database table.
 * 
 */
@Entity
@Table(name = "PLAN_PLAC_VIN")
@NamedQuery(name = "PlanPlacVin.findAll", query = "SELECT p FROM PlanPlacVin p")
public class PlanPlacVin implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PLAN_VIN_NO")
	private long planVinNo;

	@Column(name = "PLAN_GROUP_NO")
	private long planGroupNo;

	@Column(name = "VIN_NO")
	private String vinNo;

	@Column(name = "CY_AREA_NO")
	private String cyAreaNo;

	@Column(name = "CY_ROW_NO")
	private String cyRowNo;

	@Column(name = "REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name = "REC_TIM")
	private Date recTim;

	public PlanPlacVin() {
	}
	public long getPlanVinNo() {
		return planVinNo;
	}

	public void setPlanVinNo(long planVinNo) {
		this.planVinNo = planVinNo;
	}

	public String getCyAreaNo() {
		return this.cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public String getCyRowNo() {
		return this.cyRowNo;
	}

	public void setCyRowNo(String cyRowNo) {
		this.cyRowNo = cyRowNo;
	}

	public String getRecNam() {
		return this.recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return this.recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public long getPlanGroupNo() {
		return planGroupNo;
	}

	public void setPlanGroupNo(long planGroupNo) {
		this.planGroupNo = planGroupNo;
	}

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

}