package net.huadong.tech.work.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.huadong.tech.util.HdUtils;


/**
 * The persistent class for the WORK_QUEUE database table.
 * 
 */
@Entity
@Table(name="WORK_QUEUE")
@NamedQuery(name="WorkQueue.findAll", query="SELECT w FROM WorkQueue w")
public class WorkQueue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORK_QUEUE_NO")
	private String workQueueNo;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="REMARKS")
	private String remarks;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TRUCK_NO")
	private String truckNo;

	@Column(name="WORK_TYP")
	private String workTyp;


	@Transient
	private String workTypName;
	@Transient
	private String consignNam;
	
	@Transient
	private Date contractDte;
	@Transient
	private Date validatDte;
	
	
    public String getWorkTypName() {
    	if(HdUtils.strNotNull(workTyp)) {
    		return HdUtils.getSysCodeName("WORK_TYP", workTyp);
    	}else {
    		return "";
    	}

	}

	public void setWorkTypName(String workTypName) {
		this.workTypName = workTypName;
	}

	@Transient
	private BigDecimal planNum;
    
    public BigDecimal getPlanNum() {
		return planNum;
	}

	public void setPlanNum(BigDecimal planNum) {
		this.planNum = planNum;
	}

	public BigDecimal getWorkNum() {
		return workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	@Transient
	private BigDecimal workNum;
	
	
	public WorkQueue() {
	}

	public String getWorkQueueNo() {
		return this.workQueueNo;
	}

	public void setWorkQueueNo(String workQueueNo) {
		this.workQueueNo = workQueueNo;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public String getWorkTyp() {
		return this.workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	public String getConsignNam() {
		return consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}
	

	public Date getContractDte() {
		return contractDte;
	}

	public void setContractDte(Date contractDte) {
		this.contractDte = contractDte;
	}

	public Date getValidatDte() {
		return validatDte;
	}

	public void setValidatDte(Date validatDte) {
		this.validatDte = validatDte;
	}

	public WorkQueue(String workQueueNo, String contractNo, String recNam, Date recTim, String remarks, String shipNo,
			String truckNo, String workTyp, BigDecimal planNum, BigDecimal workNum,String consignNam,Date validatDte,Date contractDte) {
		super();
		this.workQueueNo = workQueueNo;
		this.contractNo = contractNo;
		this.recNam = recNam;
		this.recTim = recTim;
		this.remarks = remarks;
		this.shipNo = shipNo;
		this.truckNo = truckNo;
		this.workTyp = workTyp;
		this.planNum = planNum;
		this.workNum = workNum;
		this.consignNam=consignNam;
		this.contractDte=contractDte;
		this.validatDte=validatDte;
	}


}