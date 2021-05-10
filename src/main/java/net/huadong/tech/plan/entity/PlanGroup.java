package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PLAN_GROUP database table.
 * 
 */
@Entity
@Table(name="PLAN_GROUP")
@NamedQuery(name="PlanGroup.findAll", query="SELECT p FROM PlanGroup p")
public class PlanGroup extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_PLAN_GROUP_NO", sequenceName = "SEQ_PLAN_GROUP_NO",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_PLAN_GROUP_NO")
	@Column(name="PLAN_GROUP_NO")
	private long planGroupNo;

	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="PLAN_TYP")
	private String planTyp;


	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TOTAL_NUM")
	private BigDecimal totalNum;

	@Column(name="TOYOTO_ID")
	private String toyotoId;

	@Temporal(TemporalType.DATE)
	@Column(name="VALIDAT_DTE")
	private Date validatDte;

	private String voyage;

	public PlanGroup() {
	}

	public long getPlanGroupNo() {
		return this.planGroupNo;
	}

	public void setPlanGroupNo(long planGroupNo) {
		this.planGroupNo = planGroupNo;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getPlanTyp() {
		return this.planTyp;
	}

	public void setPlanTyp(String planTyp) {
		this.planTyp = planTyp;
	}

	

	public String getShipNam() {
		return this.shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public BigDecimal getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public String getToyotoId() {
		return this.toyotoId;
	}

	public void setToyotoId(String toyotoId) {
		this.toyotoId = toyotoId;
	}

	public Date getValidatDte() {
		return this.validatDte;
	}

	public void setValidatDte(Date validatDte) {
		this.validatDte = validatDte;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

}