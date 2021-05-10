package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ANNUAL_WORK_PLAN database table.
 * 
 */
@Entity
@Table(name="ANNUAL_WORK_PLAN")
@NamedQuery(name="AnnualWorkPlan.findAll", query="SELECT a FROM AnnualWorkPlan a")
public class AnnualWorkPlan extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="ANNUAL_PLAN_NUM")
	private BigDecimal annualPlanNum;

	@Column(name="YEAR_SET")
	private String yearSet;

	public AnnualWorkPlan() {
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public BigDecimal getAnnualPlanNum() {
		return this.annualPlanNum;
	}

	public void setAnnualPlanNum(BigDecimal annualPlanNum) {
		this.annualPlanNum = annualPlanNum;
	}

	public String getYearSet() {
		return this.yearSet;
	}

	public void setYearSet(String yearSet) {
		this.yearSet = yearSet;
	}

}