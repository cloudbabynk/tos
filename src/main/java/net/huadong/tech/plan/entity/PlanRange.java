package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PLAN_RANGE database table.
 * 
 */
@Entity
@Table(name="PLAN_RANGE")
@NamedQuery(name="PlanRange.findAll", query="SELECT p FROM PlanRange p")
public class PlanRange  extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_RANGE_NO")
	private String planRangeNo;

	@Column(name="ACTIVE_ID")
	private String activeId;

	@Column(name="CY_AREA_NO")
	private String cyAreaNo;

	@Column(name="PLAN_GROUP_NO")
	private BigDecimal planGroupNo;

	@Column(name="PLAN_NUM")
	private BigDecimal planNum;

	@Column(name="SEQ_NO")
	private BigDecimal seqNo;

	public PlanRange() {
	}

	public String getPlanRangeNo() {
		return this.planRangeNo;
	}

	public void setPlanRangeNo(String planRangeNo) {
		this.planRangeNo = planRangeNo;
	}

	public String getActiveId() {
		return this.activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getCyAreaNo() {
		return this.cyAreaNo;
	}

	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}

	public BigDecimal getPlanGroupNo() {
		return this.planGroupNo;
	}

	public void setPlanGroupNo(BigDecimal planGroupNo) {
		this.planGroupNo = planGroupNo;
	}

	public BigDecimal getPlanNum() {
		return this.planNum;
	}

	public void setPlanNum(BigDecimal planNum) {
		this.planNum = planNum;
	}

	public BigDecimal getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}


}