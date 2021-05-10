package net.huadong.tech.his.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DAY_NIGHT_PLAN_HIS database table.
 * 
 */
@Entity
@Table(name="DAY_NIGHT_PLAN_HIS")
@NamedQuery(name="DayNightPlanHis.findAll", query="SELECT d FROM DayNightPlanHis d")
public class DayNightPlanHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="CLASS_EFFI")
	private BigDecimal classEffi;

	@Column(name="CLASS00_LINES")
	private BigDecimal class00Lines;

	@Column(name="CLASS00_MACH")
	private BigDecimal class00Mach;

	@Column(name="CLASS00_TONS")
	private BigDecimal class00Tons;

	@Column(name="CLASS00_WORK_NUM")
	private BigDecimal class00WorkNum;

	@Column(name="CLASS00_WORKER")
	private BigDecimal class00Worker;

	@Column(name="CLASS08_LINES")
	private BigDecimal class08Lines;

	@Column(name="CLASS08_MACH")
	private BigDecimal class08Mach;

	@Column(name="CLASS08_TONS")
	private BigDecimal class08Tons;

	@Column(name="CLASS08_WORK_NUM")
	private BigDecimal class08WorkNum;

	@Column(name="CLASS08_WORKER")
	private BigDecimal class08Worker;

	@Column(name="CLASS16_LINES")
	private BigDecimal class16Lines;

	@Column(name="CLASS16_MACH")
	private BigDecimal class16Mach;

	@Column(name="CLASS16_TONS")
	private BigDecimal class16Tons;

	@Column(name="CLASS16_WORK_NUM")
	private BigDecimal class16WorkNum;

	@Column(name="CLASS16_WORKER")
	private BigDecimal class16Worker;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DAYS\"")
	private Date days;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="PLAN_WORK_TIM")
	private String planWorkTim;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="SHIP_EFFI")
	private BigDecimal shipEffi;

	@Column(name="SHIP_NAM_VOYAGE")
	private String shipNamVoyage;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Column(name="WORK_TIM")
	private BigDecimal workTim;

	public DayNightPlanHis() {
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public BigDecimal getClassEffi() {
		return this.classEffi;
	}

	public void setClassEffi(BigDecimal classEffi) {
		this.classEffi = classEffi;
	}

	public BigDecimal getClass00Lines() {
		return this.class00Lines;
	}

	public void setClass00Lines(BigDecimal class00Lines) {
		this.class00Lines = class00Lines;
	}

	public BigDecimal getClass00Mach() {
		return this.class00Mach;
	}

	public void setClass00Mach(BigDecimal class00Mach) {
		this.class00Mach = class00Mach;
	}

	public BigDecimal getClass00Tons() {
		return this.class00Tons;
	}

	public void setClass00Tons(BigDecimal class00Tons) {
		this.class00Tons = class00Tons;
	}

	public BigDecimal getClass00WorkNum() {
		return this.class00WorkNum;
	}

	public void setClass00WorkNum(BigDecimal class00WorkNum) {
		this.class00WorkNum = class00WorkNum;
	}

	public BigDecimal getClass00Worker() {
		return this.class00Worker;
	}

	public void setClass00Worker(BigDecimal class00Worker) {
		this.class00Worker = class00Worker;
	}

	public BigDecimal getClass08Lines() {
		return this.class08Lines;
	}

	public void setClass08Lines(BigDecimal class08Lines) {
		this.class08Lines = class08Lines;
	}

	public BigDecimal getClass08Mach() {
		return this.class08Mach;
	}

	public void setClass08Mach(BigDecimal class08Mach) {
		this.class08Mach = class08Mach;
	}

	public BigDecimal getClass08Tons() {
		return this.class08Tons;
	}

	public void setClass08Tons(BigDecimal class08Tons) {
		this.class08Tons = class08Tons;
	}

	public BigDecimal getClass08WorkNum() {
		return this.class08WorkNum;
	}

	public void setClass08WorkNum(BigDecimal class08WorkNum) {
		this.class08WorkNum = class08WorkNum;
	}

	public BigDecimal getClass08Worker() {
		return this.class08Worker;
	}

	public void setClass08Worker(BigDecimal class08Worker) {
		this.class08Worker = class08Worker;
	}

	public BigDecimal getClass16Lines() {
		return this.class16Lines;
	}

	public void setClass16Lines(BigDecimal class16Lines) {
		this.class16Lines = class16Lines;
	}

	public BigDecimal getClass16Mach() {
		return this.class16Mach;
	}

	public void setClass16Mach(BigDecimal class16Mach) {
		this.class16Mach = class16Mach;
	}

	public BigDecimal getClass16Tons() {
		return this.class16Tons;
	}

	public void setClass16Tons(BigDecimal class16Tons) {
		this.class16Tons = class16Tons;
	}

	public BigDecimal getClass16WorkNum() {
		return this.class16WorkNum;
	}

	public void setClass16WorkNum(BigDecimal class16WorkNum) {
		this.class16WorkNum = class16WorkNum;
	}

	public BigDecimal getClass16Worker() {
		return this.class16Worker;
	}

	public void setClass16Worker(BigDecimal class16Worker) {
		this.class16Worker = class16Worker;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getPlanWorkTim() {
		return this.planWorkTim;
	}

	public void setPlanWorkTim(String planWorkTim) {
		this.planWorkTim = planWorkTim;
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

	public BigDecimal getShipEffi() {
		return this.shipEffi;
	}

	public void setShipEffi(BigDecimal shipEffi) {
		this.shipEffi = shipEffi;
	}

	public String getShipNamVoyage() {
		return this.shipNamVoyage;
	}

	public void setShipNamVoyage(String shipNamVoyage) {
		this.shipNamVoyage = shipNamVoyage;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	public BigDecimal getWorkTim() {
		return this.workTim;
	}

	public void setWorkTim(BigDecimal workTim) {
		this.workTim = workTim;
	}

}