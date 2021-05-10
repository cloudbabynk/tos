package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DAY_NIGHT_TRENDS database table.
 * 
 */
@Entity
@Table(name="DAY_NIGHT_TRENDS")
@NamedQuery(name="DayNightTrend.findAll", query="SELECT d FROM DayNightTrend d")
public class DayNightTrend extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_ID")
	private String planId;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DAYS\"")
	private Date days;

	@Column(name="IN_AREA_NEED")
	private BigDecimal inAreaNeed;

	@Column(name="IN_AREA_PLAN")
	private String inAreaPlan;

	@Column(name="IN_CARGO")
	private String inCargo;

	@Column(name="IN_NUM")
	private BigDecimal inNum;

	@Column(name="IN_TOTAL_NUM")
	private BigDecimal inTotalNum;

	@Column(name="OUT_AREA_NEED")
	private BigDecimal outAreaNeed;

	@Column(name="OUT_AREA_PLAN")
	private String outAreaPlan;

	@Column(name="OUT_CARGO")
	private String outCargo;

	@Column(name="OUT_NUM")
	private BigDecimal outNum;

	@Column(name="OUT_TOTAL_NUM")
	private BigDecimal outTotalNum;


	@Column(name="SHIP_TRENDS_ID")
	private String shipTrendsId;
	
	@Transient
	private String jcgdt;
	
	@Transient
	private String kx;
	
	@Transient
	private String bwlz;
	
	@Transient
	private String cshipnam;
	
	@Transient
	private String hc;


	public String getJcgdt() {
		return jcgdt;
	}

	public void setJcgdt(String jcgdt) {
		this.jcgdt = jcgdt;
	}

	public String getKx() {
		return kx;
	}

	public void setKx(String kx) {
		this.kx = kx;
	}

	public String getBwlz() {
		return bwlz;
	}

	public void setBwlz(String bwlz) {
		this.bwlz = bwlz;
	}


	public String getCshipnam() {
		return cshipnam;
	}

	public void setCshipnam(String cshipnam) {
		this.cshipnam = cshipnam;
	}

	public String getHc() {
		return hc;
	}

	public void setHc(String hc) {
		this.hc = hc;
	}

	public DayNightTrend() {
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public BigDecimal getInAreaNeed() {
		return this.inAreaNeed;
	}

	public void setInAreaNeed(BigDecimal inAreaNeed) {
		this.inAreaNeed = inAreaNeed;
	}

	public String getInAreaPlan() {
		return this.inAreaPlan;
	}

	public void setInAreaPlan(String inAreaPlan) {
		this.inAreaPlan = inAreaPlan;
	}

	public String getInCargo() {
		return this.inCargo;
	}

	public void setInCargo(String inCargo) {
		this.inCargo = inCargo;
	}

	public BigDecimal getInNum() {
		return this.inNum;
	}

	public void setInNum(BigDecimal inNum) {
		this.inNum = inNum;
	}

	public BigDecimal getInTotalNum() {
		return this.inTotalNum;
	}

	public void setInTotalNum(BigDecimal inTotalNum) {
		this.inTotalNum = inTotalNum;
	}

	public BigDecimal getOutAreaNeed() {
		return this.outAreaNeed;
	}

	public void setOutAreaNeed(BigDecimal outAreaNeed) {
		this.outAreaNeed = outAreaNeed;
	}

	public String getOutAreaPlan() {
		return this.outAreaPlan;
	}

	public void setOutAreaPlan(String outAreaPlan) {
		this.outAreaPlan = outAreaPlan;
	}

	public String getOutCargo() {
		return this.outCargo;
	}

	public void setOutCargo(String outCargo) {
		this.outCargo = outCargo;
	}

	public BigDecimal getOutNum() {
		return this.outNum;
	}

	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}

	public BigDecimal getOutTotalNum() {
		return this.outTotalNum;
	}

	public void setOutTotalNum(BigDecimal outTotalNum) {
		this.outTotalNum = outTotalNum;
	}

	public String getShipTrendsId() {
		return shipTrendsId;
	}

	public void setShipTrendsId(String shipTrendsId) {
		this.shipTrendsId = shipTrendsId;
	}


}