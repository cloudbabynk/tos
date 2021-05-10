package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WORK_COMMAND_ROLLBACK database table.
 * 
 */
@Entity
@Table(name="WORK_COMMAND_ROLLBACK")
@NamedQuery(name="WorkCommandRollback.findAll", query="SELECT w FROM WorkCommandRollback w")
public class WorkCommandRollback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLLBACK_ID")
	private String rollbackId;
    @Transient
    private String cShipNam;
    
	@Transient 
    private String voyage;
    
	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CAR_KIND")
	private String carKind;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="CY_PLAC")
	private String cyPlac;
	
	@Transient
	private String cyAreaNam;

	@Column(name="CY_WORK_NAM")
	private String cyWorkNam;

	@Column(name="DIRECT_ID")
	private String directId;

	private String driver;

	@Column(name="FINISHED_ID")
	private String finishedId;

	@Temporal(TemporalType.DATE)
	@Column(name="IN_CY_TIM")
	private Date inCyTim;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LENGTH_OVER_ID")
	private String lengthOverId;

	@Column(name="OLD_PLAC")
	private String oldPlac;

	@Column(name="OUT_CY_NAM")
	private String outCyNam;

	@Temporal(TemporalType.DATE)
	@Column(name="OUT_CY_TIM")
	private Date outCyTim;

	@Column(name="PLAN_PLAC")
	private String planPlac;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="ROLLBACK_OPER")
	private String rollbackOper;

	@Temporal(TemporalType.DATE)
	@Column(name="ROLLBACK_TIM")
	private Date rollbackTim;

	@Column(name="SEND_NAM")
	private String sendNam;

	@Temporal(TemporalType.DATE)
	@Column(name="SEND_TIM")
	private Date sendTim;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_WORK_NAM")
	private String shipWorkNam;

	@Temporal(TemporalType.DATE)
	@Column(name="SHIP_WORK_TIM")
	private Date shipWorkTim;

	@Column(name="TRUCK_NO")
	private String truckNo;

	@Column(name="VIN_NO")
	private String vinNo;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;

	@Column(name="WORK_QUEUE_NO")
	private String workQueueNo;

	@Column(name="WORK_TYP")
	private String workTyp;
	
	@Transient
	private String tranPortCodNam;

	public WorkCommandRollback() {
	}

	public String getRollbackId() {
		return this.rollbackId;
	}

	public void setRollbackId(String rollbackId) {
		this.rollbackId = rollbackId;
	}
	  public String getcShipNam() {
			return cShipNam;
		}

		public void setcShipNam(String cShipNam) {
			this.cShipNam = cShipNam;
		}

		public String getVoyage() {
			return voyage;
		}

		public void setVoyage(String voyage) {
			this.voyage = voyage;
		}
	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCyPlac() {
		return this.cyPlac;
	}

	public void setCyPlac(String cyPlac) {
		this.cyPlac = cyPlac;
	}

	public String getCyWorkNam() {
		return this.cyWorkNam;
	}

	public void setCyWorkNam(String cyWorkNam) {
		this.cyWorkNam = cyWorkNam;
	}

	public String getDirectId() {
		return this.directId;
	}

	public void setDirectId(String directId) {
		this.directId = directId;
	}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getFinishedId() {
		return this.finishedId;
	}

	public void setFinishedId(String finishedId) {
		this.finishedId = finishedId;
	}

	public Date getInCyTim() {
		return this.inCyTim;
	}

	public void setInCyTim(Date inCyTim) {
		this.inCyTim = inCyTim;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLengthOverId() {
		return this.lengthOverId;
	}

	public void setLengthOverId(String lengthOverId) {
		this.lengthOverId = lengthOverId;
	}

	public String getOldPlac() {
		return this.oldPlac;
	}

	public void setOldPlac(String oldPlac) {
		this.oldPlac = oldPlac;
	}

	public String getOutCyNam() {
		return this.outCyNam;
	}

	public void setOutCyNam(String outCyNam) {
		this.outCyNam = outCyNam;
	}

	public Date getOutCyTim() {
		return this.outCyTim;
	}

	public void setOutCyTim(Date outCyTim) {
		this.outCyTim = outCyTim;
	}

	public String getPlanPlac() {
		return this.planPlac;
	}

	public void setPlanPlac(String planPlac) {
		this.planPlac = planPlac;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRfidCardNo() {
		return this.rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	public String getRollbackOper() {
		return this.rollbackOper;
	}

	public void setRollbackOper(String rollbackOper) {
		this.rollbackOper = rollbackOper;
	}

	public Date getRollbackTim() {
		return this.rollbackTim;
	}

	public void setRollbackTim(Date rollbackTim) {
		this.rollbackTim = rollbackTim;
	}

	public String getSendNam() {
		return this.sendNam;
	}

	public void setSendNam(String sendNam) {
		this.sendNam = sendNam;
	}

	public Date getSendTim() {
		return this.sendTim;
	}

	public void setSendTim(Date sendTim) {
		this.sendTim = sendTim;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipWorkNam() {
		return this.shipWorkNam;
	}

	public void setShipWorkNam(String shipWorkNam) {
		this.shipWorkNam = shipWorkNam;
	}

	public Date getShipWorkTim() {
		return this.shipWorkTim;
	}

	public void setShipWorkTim(Date shipWorkTim) {
		this.shipWorkTim = shipWorkTim;
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public String getWidthOverId() {
		return this.widthOverId;
	}

	public void setWidthOverId(String widthOverId) {
		this.widthOverId = widthOverId;
	}

	public String getWorkQueueNo() {
		return this.workQueueNo;
	}

	public void setWorkQueueNo(String workQueueNo) {
		this.workQueueNo = workQueueNo;
	}

	public String getWorkTyp() {
		return this.workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	public String getCyAreaNam() {
		return cyAreaNam;
	}

	public void setCyAreaNam(String cyAreaNam) {
		this.cyAreaNam = cyAreaNam;
	}

	public String getTranPortCodNam() {
		return tranPortCodNam;
	}

	public void setTranPortCodNam(String tranPortCodNam) {
		this.tranPortCodNam = tranPortCodNam;
	}

}