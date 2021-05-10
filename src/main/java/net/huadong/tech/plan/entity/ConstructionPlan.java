package net.huadong.tech.plan.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONSTRUCTION_PLAN database table.
 * 
 */
@Entity
@Table(name="CONSTRUCTION_PLAN")
@NamedQuery(name="ConstructionPlan.findAll", query="SELECT c FROM ConstructionPlan c")
public class ConstructionPlan extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="CONS_CORP_COD")
	private String consCorpCod;
    @Transient
	private String consCorpNam;
	
	@Column(name="DAN_WORK_ITEMS")
	private String danWorkItems;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DAYS\"")
	private Date days;

	@Column(name="END_TIM")
	private String endTim;

	@Column(name="IS_AFFECT")
	private String isAffect;

	@Column(name="OUT_MACH")
	private String outMach;

	@Column(name="OUT_PERSON")
	private BigDecimal outPerson;


	@Column(name="REC_TELE")
	private String recTele;

	@Column(name="RELATION_NAM")
	private String relationNam;
	
	@Column(name="RESPONSE_MAN")
	private String responseMan;

	@Column(name="RELATION_TELE")
	private String relationTele;

	@Column(name="START_TIM")
	private String startTim;

	@Column(name="WORK_AREA")
	private String workArea;

	@Column(name="WORK_ITEM")
	private String workItem;

	@Column(name="OUT_MACH_NUM")
	private BigDecimal outMachNum;
	
	@Column(name="REAMARKS")
	private String reamarks;
	
	@Transient
	private String danWorkItemsName;
	
	public ConstructionPlan() {
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	

	public String getDanWorkItemsName() {
		return danWorkItemsName;
	}

	public void setDanWorkItemsName(String danWorkItemsName) {
		this.danWorkItemsName = danWorkItemsName;
	}

	public BigDecimal getOutMachNum() {
		return outMachNum;
	}

	public void setOutMachNum(BigDecimal outMachNum) {
		this.outMachNum = outMachNum;
	}

	public String getReamarks() {
		return reamarks;
	}
 
	public void setReamarks(String reamarks) {
		this.reamarks = reamarks;
	}

	public String getConsCorpCod() {
		return this.consCorpCod;
	}

	public void setConsCorpCod(String consCorpCod) {
		this.consCorpCod = consCorpCod;
	}

	public String getConsCorpNam() {
		return consCorpNam;
	}

	public void setConsCorpNam(String consCorpNam) {
		this.consCorpNam = consCorpNam;
	}

	public String getDanWorkItems() {
		return this.danWorkItems;
	}

	public void setDanWorkItems(String danWorkItems) {
		this.danWorkItems = danWorkItems;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getEndTim() {
		return this.endTim;
	}

	public void setEndTim(String endTim) {
		this.endTim = endTim;
	}

	public String getIsAffect() {
		return this.isAffect;
	}

	public void setIsAffect(String isAffect) {
		this.isAffect = isAffect;
	}

	public String getOutMach() {
		return this.outMach;
	}

	public void setOutMach(String outMach) {
		this.outMach = outMach;
	}

	public BigDecimal getOutPerson() {
		return this.outPerson;
	}

	public void setOutPerson(BigDecimal outPerson) {
		this.outPerson = outPerson;
	}

		public String getRecTele() {
		return this.recTele;
	}

	public void setRecTele(String recTele) {
		this.recTele = recTele;
	}


	public String getRelationNam() {
		return this.relationNam;
	}

	public void setRelationNam(String relationNam) {
		this.relationNam = relationNam;
	}

	public String getRelationTele() {
		return this.relationTele;
	}

	public void setRelationTele(String relationTele) {
		this.relationTele = relationTele;
	}

	public String getStartTim() {
		return this.startTim;
	}

	public void setStartTim(String startTim) {
		this.startTim = startTim;
	}

	
	public String getWorkArea() {
		return this.workArea;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public String getWorkItem() {
		return this.workItem;
	}

	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}

	public String getResponseMan() {
		return responseMan;
	}

	public void setResponseMan(String responseMan) {
		this.responseMan = responseMan;
	}

	

}