package net.huadong.tech.shipbill.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Contract {
	//码头、流向、船名、航次、内外贸、作业方式、提单号、品牌、车类、数量、货代、场地、起止时间、外方机力、外方人力、港方机力
	private String dockNam;
	private String flow;
	private String shipNam;
	private String voyage;
	private String tradeNam;
	private String contractTyp;
	private String billNo;
	private String brandNam;
	private String carKindNam;
	private BigDecimal carNum;
	private String consignNam;
	private String planArea;
	private String fromTo;
	private String outMac;
	private String outPerson;
	private String portMac;
	private String remarks;
	public String getDockNam() {
		return dockNam;
	}
	public void setDockNam(String dockNam) {
		this.dockNam = dockNam;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
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
	public String getTradeNam() {
		return tradeNam;
	}
	public void setTradeNam(String tradeNam) {
		this.tradeNam = tradeNam;
	}
	public String getContractTyp() {
		return contractTyp;
	}
	public void setContractTyp(String contractTyp) {
		this.contractTyp = contractTyp;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBrandNam() {
		return brandNam;
	}
	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}
	public String getCarKindNam() {
		return carKindNam;
	}
	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}
	public BigDecimal getCarNum() {
		return carNum;
	}
	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}
	public String getConsignNam() {
		return consignNam;
	}
	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}
	public String getPlanArea() {
		return planArea;
	}
	public void setPlanArea(String planArea) {
		this.planArea = planArea;
	}
	public String getFromTo() {
		return fromTo;
	}
	public void setFromTo(String fromTo) {
		this.fromTo = fromTo;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
