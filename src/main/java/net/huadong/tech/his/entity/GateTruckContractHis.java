package net.huadong.tech.his.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


/**
 * The persistent class for the GATE_TRUCK_CONTRACT database table.
 * 
 */
@Entity
@Table(name="GATE_TRUCK_CONTRACT_HIS")
@IdClass(GateTruckContractHiPK.class)
@NamedQuery(name="GateTruckContractHis.findAll", query="SELECT g FROM GateTruckContractHis g")
public class GateTruckContractHis implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String singleId_false = "0";


	@Column(name="BILL_NO")
	private String billNo;
	
	@Id
	@Column(name="INGATE_ID")
	private String ingateId;
	
	@Id
	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="CARRY_ID")
	private String carryId;

	@Column(name="PLAN_NUM")
	private BigDecimal planNum;

	@Column(name="TRUCK_NO")
	private String truckNo;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	//bi-directional many-to-one association to GateTruck
	@ManyToOne
	@JoinColumn(name="INGATE_ID", insertable=false, updatable=false)
	private GateTruckHis gateTruck;
	
	

	public GateTruckContractHis() {
	}


	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCarryId() {
		return this.carryId;
	}

	public void setCarryId(String carryId) {
		this.carryId = carryId;
	}

	public BigDecimal getPlanNum() {
		return this.planNum;
	}

	public void setPlanNum(BigDecimal planNum) {
		this.planNum = planNum;
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	@JsonIgnore
	public GateTruckHis getGateTruck() {
		return this.gateTruck;
	}

	public void setGateTruck(GateTruckHis gateTruck) {
		this.gateTruck = gateTruck;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getIngateId() {
		return ingateId;
	}


	public void setIngateId(String ingateId) {
		this.ingateId = ingateId;
	}
	
	
	

}