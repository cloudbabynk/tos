package net.huadong.tech.work.entity;

import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.HdShipPicSbcBerth;
import net.huadong.tech.base.entity.HdShipPicSbcBollard;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CargoInfo {
	//卸船理货
    private String billNo;
    private String carLevel;
    private String shipNo;
    private String newShipNo;
    private String brandCod;
    private String carTyp;
    private String cyAreaNo;
    private BigDecimal sycw;
    private BigDecimal rcsl;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Timestamp inCyTim;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Timestamp outCyTim;
    private BigDecimal length;
    private String inCyNam;
    private String carKind;
    private String contactInfo;
    private String consignCod;
    //每行所停的车数
    private BigDecimal mlcs;
    //提单总件数
    private BigDecimal pieces;
    
    private String widthOverId;
    private String lengthOverId;
    private String nightId;
    private String holidayId;
    private String useMachId;
    private String useWorkerId;
    private String transId;
    private String iEId;
    private String transCorp;
    private String remarks;
    private String cyRemarks;
    private String directId;//新增加的直提标志
    //集港理货
    private String ingateId;
    private String contractNo;
    private String flow;
    private String tranPortCod;
    private String queueId;
    private String vinNo;
    private String currentStat;
    
	public String getCurrentStat() {
		return currentStat;
	}
	public void setCurrentStat(String currentStat) {
		this.currentStat = currentStat;
	}
	public String getVinNo() {
		return vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}
	
	public String getNewShipNo() {
		return newShipNo;
	}
	public void setNewShipNo(String newShipNo) {
		this.newShipNo = newShipNo;
	}
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public String getiEId() {
		return iEId;
	}
	public void setiEId(String iEId) {
		this.iEId = iEId;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getTranPortCod() {
		return tranPortCod;
	}
	public void setTranPortCod(String tranPortCod) {
		this.tranPortCod = tranPortCod;
	}
	public String getCarLevel() {
		return carLevel;
	}
	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}
	public BigDecimal getPieces() {
		return pieces;
	}
	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}
	public BigDecimal getMlcs() {
		return mlcs;
	}
	public void setMlcs(BigDecimal mlcs) {
		this.mlcs = mlcs;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBrandCod() {
		return brandCod;
	}
	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}
	public String getCarTyp() {
		return carTyp;
	}
	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	public String getCyAreaNo() {
		return cyAreaNo;
	}
	public void setCyAreaNo(String cyAreaNo) {
		this.cyAreaNo = cyAreaNo;
	}
	public BigDecimal getSycw() {
		return sycw;
	}
	public void setSycw(BigDecimal sycw) {
		this.sycw = sycw;
	}
	public BigDecimal getRcsl() {
		return rcsl;
	}
	public void setRcsl(BigDecimal rcsl) {
		this.rcsl = rcsl;
	}
	public Timestamp getInCyTim() {
		return inCyTim;
	}
	public void setInCyTim(Timestamp inCyTim) {
		this.inCyTim = inCyTim;
	}
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public String getLengthOverId() {
		return lengthOverId;
	}
	public void setLengthOverId(String lengthOverId) {
		this.lengthOverId = lengthOverId;
	}
	public String getNightId() {
		return nightId;
	}
	public void setNightId(String nightId) {
		this.nightId = nightId;
	}
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public String getUseMachId() {
		return useMachId;
	}
	public void setUseMachId(String useMachId) {
		this.useMachId = useMachId;
	}
	public String getUseWorkerId() {
		return useWorkerId;
	}
	public void setUseWorkerId(String useWorkerId) {
		this.useWorkerId = useWorkerId;
	}
	public String getInCyNam() {
		return inCyNam;
	}
	public void setInCyNam(String inCyNam) {
		this.inCyNam = inCyNam;
	}
	public String getIngateId() {
		return ingateId;
	}
	public void setIngateId(String ingateId) {
		this.ingateId = ingateId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getWidthOverId() {
		return widthOverId;
	}
	public void setWidthOverId(String widthOverId) {
		this.widthOverId = widthOverId;
	}
	public String getCarKind() {
		return carKind;
	}
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getConsignCod() {
		return consignCod;
	}
	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Timestamp getOutCyTim() {
		return outCyTim;
	}
	public void setOutCyTim(Timestamp outCyTim) {
		this.outCyTim = outCyTim;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransCorp() {
		return transCorp;
	}
	public void setTransCorp(String transCorp) {
		this.transCorp = transCorp;
	}
	public String getCyRemarks() {
		return cyRemarks;
	}
	public void setCyRemarks(String cyRemarks) {
		this.cyRemarks = cyRemarks;
	}
	public String getDirectId() {
		return directId;
	}
	public void setDirectId(String directId) {
		this.directId = directId;
	}
	


}
