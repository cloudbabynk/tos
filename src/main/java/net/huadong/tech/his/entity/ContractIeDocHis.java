package net.huadong.tech.his.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONTRACT_IE_DOC_HIS database table.
 * 
 */
@Entity
@Table(name="CONTRACT_IE_DOC_HIS")
@NamedQuery(name="ContractIeDocHis.findAll", query="SELECT c FROM ContractIeDocHis c")
public class ContractIeDocHis implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@Column(name="PAY_UNIT_COD")
	private String payUnitCod;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	private String remarks;

	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TRAN_PORT_COD")
	private String tranPortCod;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Temporal(TemporalType.DATE)
	@Column(name="VALIDAT_DTE")
	private Date validatDte;

	private String voyage;

	@Column(name="WORK_WAY")
	private String workWay;

	public ContractIeDocHis() {
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getIEId() {
		return this.iEId;
	}

	public void setIEId(String iEId) {
		this.iEId = iEId;
	}

	public String getPayUnitCod() {
		return this.payUnitCod;
	}

	public void setPayUnitCod(String payUnitCod) {
		this.payUnitCod = payUnitCod;
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

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
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

}