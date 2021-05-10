package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONTRACT_IE_DOC database table.
 * 
 */
@Entity
@Table(name="CONTRACT_IE_DOC")
@NamedQuery(name="ContractIeDoc.findAll", query="SELECT c FROM ContractIeDoc c")
public class ContractIeDoc extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String jigang = "1"; //集港
	public static final String huanqiu = "03409000";
	public static final String gunzhuang = "03406500";//码头
	@Id
    @Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="ACTIVE_ID")
	private String activeId;

	@Column(name="AGENT_COD")
	private String agentCod;

	@Column(name="BILL_NO")
	private String billNo;

	private String brand;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_NUM")
	private BigDecimal carNum;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CONSIGN_COD")
	private String consignCod;

	@Column(name="CONSIGN_NAM")
	private String consignNam;

	@Temporal(TemporalType.DATE)
	@Column(name="CONTRACT_DTE")
	private Date contractDte;

	@Column(name="CONTRACT_TYP")
	private String contractTyp;

	@Column(name="DISC_PORT_COD")
	private String discPortCod;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="I_E_ID")
	private String iEId;

	@Column(name="QRCODE_PATH")
	private String qrcodePath;

	@Column(name="IS_TRUCK")
	private String isTruck;
	
	@Column(name="CONFIRM_ID")
	private String confirmId;
	
	@Column(name="PAY_UNIT_COD")
	private String payUnitCod;
	
	private String remarks;

	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	@Temporal(TemporalType.DATE)
	@Column(name="VALIDAT_DTE")
	private Date validatDte;

	private String voyage;

	@Column(name="WORK_WAY")
	private String workWay;
	
	@Column(name="PLAN_AREA")
	private String planArea;
	
	@Column(name="FLOW")
	private String flow;

	@Column(name="OUT_MAC")
	private String outMac;
	
	@Column(name="OUT_PERSON")
	private String outPerson;
	
	@Column(name="PORT_MAC")
	private String portMac;
	
	@Column(name="START_DTE")
	private String startDte;
	
	@Column(name="END_DTE")
	private String endDte;
	
	@Column(name="FACTORY_COD")
	private String factoryCod;
	
	@Column(name="CZ_ID")
	private String czId;

	public String getCzId() {
		return czId;
	}

	public void setCzId(String czId) {
		this.czId = czId;
	}

	@Transient
	private String carTypNam;
	
	@Transient
	private String carKindNam;
	
	@Transient
	private String tradeNam;
	
	@Transient
	private String ieNam;
	
	@Transient
	private String brandNam;
	
	@Transient
	private String workNam;
	
	@Transient
	private String flowNam;
	
	@Transient
	private String dockNam;
	
	@Transient
	private String fromTo;
	
	@Transient
	private String resultNum;

	public ContractIeDoc() {
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getResultNum() {
		return resultNum;
	}

	public void setResultNum(String resultNum) {
		this.resultNum = resultNum;
	}

	public String getActiveId() {
		return this.activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getAgentCod() {
		return this.agentCod;
	}

	public void setAgentCod(String agentCod) {
		this.agentCod = agentCod;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getIsTruck() {
		return isTruck;
	}

	public void setIsTruck(String isTruck) {
		this.isTruck = isTruck;
	}

	public String getStartDte() {
		return startDte;
	}

	public void setStartDte(String startDte) {
		this.startDte = startDte;
	}

	public String getEndDte() {
		return endDte;
	}

	public void setEndDte(String endDte) {
		this.endDte = endDte;
	}

	public String getFactoryCod() {
		return factoryCod;
	}

	public void setFactoryCod(String factoryCod) {
		this.factoryCod = factoryCod;
	}

	public String getCarTypNam() {
		return carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getCarKindNam() {
		return carKindNam;
	}

	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getTradeNam() {
		return tradeNam;
	}

	public void setTradeNam(String tradeNam) {
		this.tradeNam = tradeNam;
	}

	public String getIeNam() {
		return ieNam;
	}

	public void setIeNam(String ieNam) {
		this.ieNam = ieNam;
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getWorkNam() {
		return workNam;
	}

	public void setWorkNam(String workNam) {
		this.workNam = workNam;
	}

	public String getFlowNam() {
		return flowNam;
	}

	public void setFlowNam(String flowNam) {
		this.flowNam = flowNam;
	}

	public String getDockNam() {
		return dockNam;
	}

	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}

	public String getFromTo() {
		return fromTo;
	}

	public void setFromTo(String fromTo) {
		this.fromTo = fromTo;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getConsignCod() {
		return this.consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getConsignNam() {
		return this.consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}

	public Date getContractDte() {
		return this.contractDte;
	}

	public void setContractDte(Date contractDte) {
		this.contractDte = contractDte;
	}

	public String getContractTyp() {
		return this.contractTyp;
	}

	public void setContractTyp(String contractTyp) {
		this.contractTyp = contractTyp;
	}

	public String getDiscPortCod() {
		return this.discPortCod;
	}

	public void setDiscPortCod(String discPortCod) {
		this.discPortCod = discPortCod;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}


	public String getPayUnitCod() {
		return this.payUnitCod;
	}

	public void setPayUnitCod(String payUnitCod) {
		this.payUnitCod = payUnitCod;
	}

	

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTranPortCod() {
		return this.tranPortCod;
	}

	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
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

	public String getWorkWay() {
		return this.workWay;
	}

	public void setWorkWay(String workWay) {
		this.workWay = workWay;
	}

	public String getPlanArea() {
		return planArea;
	}

	public void setPlanArea(String planArea) {
		this.planArea = planArea;
	}
	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getOutMac() {
		return outMac;
	}

	public void setOutMac(String outMac) {
		this.outMac = outMac;
	}

	public String getOutPerson() {
		return outPerson;
	}

	public void setOutPerson(String outPerson) {
		this.outPerson = outPerson;
	}

	public String getPortMac() {
		return portMac;
	}

	public void setPortMac(String portMac) {
		this.portMac = portMac;
	}

	public String getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

}