package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the WORK_COMMAND database table.
 * 
 */
@Entity
@Table(name="WORK_COMMAND")
@NamedQuery(name="WorkCommand.findAll", query="SELECT w FROM WorkCommand w")
public class WorkCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="QUEUE_ID")
	private String queueId;
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

	@Column(name="DIRECT_ID")
	private String directId;

	private String driver;

	@Column(name="FINISHED_ID")
	private String finishedId;

	@Column(name="HOLIDAY_ID")
	private String holidayId;

	@Column(name="IN_CY_NAM")
	private String inCyNam;

	@Column(name="IN_CY_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp inCyTim;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	@Column(name="LENGTH_OVER_ID")
	private String lengthOverId;

	@Column(name="NIGHT_ID")
	private String nightId;

	@Column(name="OLD_PLAC")
	private String oldPlac;

	@Column(name="OUT_CY_NAM")
	private String outCyNam;

	@Column(name="OUT_CY_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp outCyTim;

	@Column(name="PLAN_PLAC")
	private String planPlac;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SEND_NAM")
	private String sendNam;

	@Column(name="SEND_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp sendTim;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="SHIP_WORK_NAM")
	private String shipWorkNam;

	@Column(name="SHIP_WORK_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp shipWorkTim;

	@Column(name="TRUCK_NO")
	private String truckNo;

	@Column(name="USE_MACH_ID")
	private String useMachId;

	@Column(name="USE_WORKER_ID")
	private String useWorkerId;

	@Column(name="VIN_NO")
	private String vinNo;
	
	@Column(name="WORK_QUEUE_NO")
	private String workQueueNo;

	private BigDecimal width;

	@Column(name="WIDTH_OVER_ID")
	private String widthOverId;

	@Column(name="WORK_TYP")
	private String workTyp;
	
	@Column(name="CAR_LEVEL")
	private String carLevel;
	
	@Column(name="CY_REMARKS")
	private String cyRemarks;

	@Column(name="VCEXCEPTION")
	private String vcexception;
	
	@Column(name="QZ_ID")
	private String qzId;
	

	@Column(name="LX_PDA_ID")
	private String lxPdaId;
	
	@Column(name="NO_BILL_ID")
	private String noBillId;
	
	@Transient
	private String carTypNam;
	
	@Transient
	private String carKindNam;
	
	@Transient
	private String brandNam;
	
	@Transient
	private String workTypNam;
	
	@Transient
	private String inCyNamNam;
	
	@Transient
	private String outCyNamNam;
	
	@Transient
	private String tranPortCodNam;
	
	@Transient
	private String discPortCodNam;
	
	@Transient
	private String cyArea;
	
	//集港收车入库数量
	@Transient
	private String rksl;
	
	//装船出库数量
	@Transient
	private String cksl;
	
	@Transient
	private String shipNam;
	
	@Transient
	private String consignNam;
	
	@Transient
	private String contactInfo;
	
	

	//bi-directional many-to-one association to WorkQueue
	@ManyToOne
	@JoinColumn(name="WORK_QUEUE_NO", insertable=false, updatable=false)
	private WorkQueue workQueue;

	public WorkCommand() {
	}

	public String getConsignNam() {
		return consignNam;
	}

	public void setConsignNam(String consignNam) {
		this.consignNam = consignNam;
	}

	public String getCyRemarks() {
		return cyRemarks;
	}

	public void setCyRemarks(String cyRemarks) {
		this.cyRemarks = cyRemarks;
	}

	public String getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}

	public String getVcexception() {
		return vcexception;
	}

	public void setVcexception(String vcexception) {
		this.vcexception = vcexception;
	}

	public String getQzId() {
		return qzId;
	}

	public void setQzId(String qzId) {
		this.qzId = qzId;
	}

	public String getNoBillId() {
		return noBillId;
	}

	public void setNoBillId(String noBillId) {
		this.noBillId = noBillId;
	}

	public String getLxPdaId() {
		return lxPdaId;
	}

	public void setLxPdaId(String lxPdaId) {
		this.lxPdaId = lxPdaId;
	}

	public String getOutCyNamNam() {
		return outCyNamNam;
	}

	public void setOutCyNamNam(String outCyNamNam) {
		this.outCyNamNam = outCyNamNam;
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
	public String getWorkTypNam() {
		return workTypNam;
	}

	public void setWorkTypNam(String workTypNam) {
		this.workTypNam = workTypNam;
	}
	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getInCyNamNam() {
		return inCyNamNam;
	}

	public void setInCyNamNam(String inCyNamNam) {
		this.inCyNamNam = inCyNamNam;
	}

	public String getTranPortCodNam() {
		return tranPortCodNam;
	}

	public void setTranPortCodNam(String tranPortCodNam) {
		this.tranPortCodNam = tranPortCodNam;
	}

	public String getDiscPortCodNam() {
		return discPortCodNam;
	}

	public void setDiscPortCodNam(String discPortCodNam) {
		this.discPortCodNam = discPortCodNam;
	}

	public String getWorkQueueNo() {
		return workQueueNo;
	}

	public void setWorkQueueNo(String workQueueNo) {
		this.workQueueNo = workQueueNo;
	}

	public String getQueueId() {
		return this.queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
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

	public String getHolidayId() {
		return this.holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

	public String getInCyNam() {
		return this.inCyNam;
	}

	public void setInCyNam(String inCyNam) {
		this.inCyNam = inCyNam;
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

	public String getNightId() {
		return this.nightId;
	}

	public void setNightId(String nightId) {
		this.nightId = nightId;
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

	public String getSendNam() {
		return this.sendNam;
	}

	public void setSendNam(String sendNam) {
		this.sendNam = sendNam;
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


	public Timestamp getInCyTim() {
		return inCyTim;
	}

	public void setInCyTim(Timestamp inCyTim) {
		this.inCyTim = inCyTim;
	}

	public Timestamp getOutCyTim() {
		return outCyTim;
	}

	public void setOutCyTim(Timestamp outCyTim) {
		this.outCyTim = outCyTim;
	}

	public Timestamp getSendTim() {
		return sendTim;
	}

	public void setSendTim(Timestamp sendTim) {
		this.sendTim = sendTim;
	}

	public Timestamp getShipWorkTim() {
		return shipWorkTim;
	}

	public void setShipWorkTim(Timestamp shipWorkTim) {
		this.shipWorkTim = shipWorkTim;
	}

	public String getTruckNo() {
		return this.truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public String getUseMachId() {
		return this.useMachId;
	}

	public void setUseMachId(String useMachId) {
		this.useMachId = useMachId;
	}

	public String getUseWorkerId() {
		return this.useWorkerId;
	}

	public void setUseWorkerId(String useWorkerId) {
		this.useWorkerId = useWorkerId;
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

	public String getWorkTyp() {
		return this.workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	@JsonIgnore
	public WorkQueue getWorkQueue() {
		return this.workQueue;
	}

	public void setWorkQueue(WorkQueue workQueue) {
		this.workQueue = workQueue;
	}

	public String getCyArea() {
		return cyArea;
	}

	public void setCyArea(String cyArea) {
		this.cyArea = cyArea;
	}

	public String getRksl() {
		return rksl;
	}

	public void setRksl(String rksl) {
		this.rksl = rksl;
	}

	public String getShipNam() {
		return shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getCksl() {
		return cksl;
	}

	public void setCksl(String cksl) {
		this.cksl = cksl;
	}
	

}