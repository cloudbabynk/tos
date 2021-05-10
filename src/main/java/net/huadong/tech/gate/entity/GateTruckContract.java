package net.huadong.tech.gate.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


/**
 * The persistent class for the GATE_TRUCK_CONTRACT database table.
 * 
 */
@Entity
@Table(name="GATE_TRUCK_CONTRACT")
@IdClass(GateTruckContractPK.class)
@NamedQuery(name="GateTruckContract.findAll", query="SELECT g FROM GateTruckContract g")
public class GateTruckContract implements Serializable {
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
	private GateTruck gateTruck;
	
	@Transient
	private String shipNam;
	
	@Transient
	private String voyage;
	
	@Transient
	private String shipNo;
	
	@Transient
	private String carTypNam;
	
	@Transient
	private String carKindNam;
	
	@Transient
	private String brandNam;
	
	@Transient
	private String consignNam;
	
	@Transient
	private String flow;
	
	@Transient
	private String tranPortCod;
	
	@Transient
	private String carTyp;
	
	@Transient
	private String carKind;
	
	@Transient
	private String dockCod;
	
	@Transient
	private String brandCod;
	
	@Transient
	private String factoryCod;
	
	@Transient
	private String factoryNam;

	public GateTruckContract() {
	}


	public String getBrandCod() {
		return brandCod;
	}


	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getFactoryNam() {
		return factoryNam;
	}


	public void setFactoryNam(String factoryNam) {
		this.factoryNam = factoryNam;
	}


	public String getFactoryCod() {
		return factoryCod;
	}


	public void setFactoryCod(String factoryCod) {
		this.factoryCod = factoryCod;
	}


	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCarKindNam() {
		return carKindNam;
	}


	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}


	public String getCarTypNam() {
		return carTypNam;
	}


	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}


	public String getCarKind() {
		return carKind;
	}


	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}


	public String getBrandNam() {
		return brandNam;
	}


	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}


	public String getConsignNam() {
		return consignNam;
	}


	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}


	public String getFlow() {
		return flow;
	}


	public void setFlow(String flow) {
		this.flow = flow;
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
	public GateTruck getGateTruck() {
		return this.gateTruck;
	}

	public void setGateTruck(GateTruck gateTruck) {
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


	public String getShipNam() {
		return shipNam;
	}


	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}


	public String getVoyage() {
		return voyage;
	}


	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}


	public String getShipNo() {
		return shipNo;
	}


	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}


	public String getCarTyp() {
		return carTyp;
	}


	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}


	public String getTranPortCod() {
		return tranPortCod;
	}


	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
	}


	public String getDockCod() {
		return dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}
	

}