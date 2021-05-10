package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
//import net.huadong.tech.ship.entity.ShipQuery;

/**
 *
 * @author wangbin
 */
@Entity
@Table(name = "M_NOBILL_WORK_COMMAND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MNobillWorkCommand.findAll", query = "SELECT m FROM MNobillWorkCommand m")
    , @NamedQuery(name = "MNobillWorkCommand.findByQueueId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.queueId = :queueId")
    , @NamedQuery(name = "MNobillWorkCommand.findByWorkQueueNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.workQueueNo = :workQueueNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByPortCarNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.portCarNo = :portCarNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByRfidCardNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.rfidCardNo = :rfidCardNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByVinNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.vinNo = :vinNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByShipNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.shipNo = :shipNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByBillNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.billNo = :billNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByContractNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.contractNo = :contractNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByWorkTyp", query = "SELECT m FROM MNobillWorkCommand m WHERE m.workTyp = :workTyp")
    , @NamedQuery(name = "MNobillWorkCommand.findByBrandCod", query = "SELECT m FROM MNobillWorkCommand m WHERE m.brandCod = :brandCod")
    , @NamedQuery(name = "MNobillWorkCommand.findByCarTyp", query = "SELECT m FROM MNobillWorkCommand m WHERE m.carTyp = :carTyp")
    , @NamedQuery(name = "MNobillWorkCommand.findByCarKind", query = "SELECT m FROM MNobillWorkCommand m WHERE m.carKind = :carKind")
    , @NamedQuery(name = "MNobillWorkCommand.findByLength", query = "SELECT m FROM MNobillWorkCommand m WHERE m.length = :length")
    , @NamedQuery(name = "MNobillWorkCommand.findByWidth", query = "SELECT m FROM MNobillWorkCommand m WHERE m.width = :width")
    , @NamedQuery(name = "MNobillWorkCommand.findByTruckNo", query = "SELECT m FROM MNobillWorkCommand m WHERE m.truckNo = :truckNo")
    , @NamedQuery(name = "MNobillWorkCommand.findByDirectId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.directId = :directId")
    , @NamedQuery(name = "MNobillWorkCommand.findByOldPlac", query = "SELECT m FROM MNobillWorkCommand m WHERE m.oldPlac = :oldPlac")
    , @NamedQuery(name = "MNobillWorkCommand.findByPlanPlac", query = "SELECT m FROM MNobillWorkCommand m WHERE m.planPlac = :planPlac")
    , @NamedQuery(name = "MNobillWorkCommand.findByCyPlac", query = "SELECT m FROM MNobillWorkCommand m WHERE m.cyPlac = :cyPlac")
    , @NamedQuery(name = "MNobillWorkCommand.findBySendTim", query = "SELECT m FROM MNobillWorkCommand m WHERE m.sendTim = :sendTim")
    , @NamedQuery(name = "MNobillWorkCommand.findBySendNam", query = "SELECT m FROM MNobillWorkCommand m WHERE m.sendNam = :sendNam")
    , @NamedQuery(name = "MNobillWorkCommand.findByShipWorkNam", query = "SELECT m FROM MNobillWorkCommand m WHERE m.shipWorkNam = :shipWorkNam")
    , @NamedQuery(name = "MNobillWorkCommand.findByShipWorkTim", query = "SELECT m FROM MNobillWorkCommand m WHERE m.shipWorkTim = :shipWorkTim")
    , @NamedQuery(name = "MNobillWorkCommand.findByInCyTim", query = "SELECT m FROM MNobillWorkCommand m WHERE m.inCyTim = :inCyTim")
    , @NamedQuery(name = "MNobillWorkCommand.findByInCyNam", query = "SELECT m FROM MNobillWorkCommand m WHERE m.inCyNam = :inCyNam")
    , @NamedQuery(name = "MNobillWorkCommand.findByOutCyTim", query = "SELECT m FROM MNobillWorkCommand m WHERE m.outCyTim = :outCyTim")
    , @NamedQuery(name = "MNobillWorkCommand.findByOutCyNam", query = "SELECT m FROM MNobillWorkCommand m WHERE m.outCyNam = :outCyNam")
    , @NamedQuery(name = "MNobillWorkCommand.findByFinishedId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.finishedId = :finishedId")
    , @NamedQuery(name = "MNobillWorkCommand.findByDriver", query = "SELECT m FROM MNobillWorkCommand m WHERE m.driver = :driver")
    , @NamedQuery(name = "MNobillWorkCommand.findByNightId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.nightId = :nightId")
    , @NamedQuery(name = "MNobillWorkCommand.findByHolidayId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.holidayId = :holidayId")
    , @NamedQuery(name = "MNobillWorkCommand.findByUseMachId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.useMachId = :useMachId")
    , @NamedQuery(name = "MNobillWorkCommand.findByUseWorkerId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.useWorkerId = :useWorkerId")
    , @NamedQuery(name = "MNobillWorkCommand.findByRemarks", query = "SELECT m FROM MNobillWorkCommand m WHERE m.remarks = :remarks")
    , @NamedQuery(name = "MNobillWorkCommand.findByLengthOverId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.lengthOverId = :lengthOverId")
    , @NamedQuery(name = "MNobillWorkCommand.findByWidthOverId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.widthOverId = :widthOverId")
    , @NamedQuery(name = "MNobillWorkCommand.findByCarLevel", query = "SELECT m FROM MNobillWorkCommand m WHERE m.carLevel = :carLevel")
    , @NamedQuery(name = "MNobillWorkCommand.findByVcexception", query = "SELECT m FROM MNobillWorkCommand m WHERE m.vcexception = :vcexception")
    , @NamedQuery(name = "MNobillWorkCommand.findByLxPdaId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.lxPdaId = :lxPdaId")
    , @NamedQuery(name = "MNobillWorkCommand.findByConfirmId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.confirmId = :confirmId")
    , @NamedQuery(name = "MNobillWorkCommand.findByConfirmTim", query = "SELECT m FROM MNobillWorkCommand m WHERE m.confirmTim = :confirmTim")
    , @NamedQuery(name = "MNobillWorkCommand.findByConfirmNam", query = "SELECT m FROM MNobillWorkCommand m WHERE m.confirmNam = :confirmNam")
    , @NamedQuery(name = "MNobillWorkCommand.findByQzId", query = "SELECT m FROM MNobillWorkCommand m WHERE m.qzId = :qzId")
    , @NamedQuery(name = "MNobillWorkCommand.findByCyArea", query = "SELECT m FROM MNobillWorkCommand m WHERE m.cyArea = :cyArea")
    , @NamedQuery(name = "MNobillWorkCommand.findByCyRow", query = "SELECT m FROM MNobillWorkCommand m WHERE m.cyRow = :cyRow")
    , @NamedQuery(name = "MNobillWorkCommand.findByCyBay", query = "SELECT m FROM MNobillWorkCommand m WHERE m.cyBay = :cyBay")})
public class MNobillWorkCommand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "QUEUE_ID")
    private String queueId;
    @Basic(optional = false)
    @Column(name = "WORK_QUEUE_NO")
    private String workQueueNo;
    @Column(name = "PORT_CAR_NO")
    private Long portCarNo;
    @Column(name = "RFID_CARD_NO")
    private String rfidCardNo;
    @Column(name = "VIN_NO")
    private String vinNo;
    @Column(name = "SHIP_NO")
    private String shipNo;
    @Column(name = "BILL_NO")
    private String billNo;
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    @Basic(optional = false)
    @Column(name = "WORK_TYP")
    private String workTyp;
    @Column(name = "BRAND_COD")
    private String brandCod;
    @Column(name = "CAR_TYP")
    private String carTyp;
    @Column(name = "CAR_KIND")
    private String carKind;
    @Column(name = "LENGTH")
    private Integer length;
    @Column(name = "WIDTH")
    private Integer width;
    @Column(name = "TRUCK_NO")
    private String truckNo;
    @Column(name = "DIRECT_ID")
    private Character directId;
    @Column(name = "OLD_PLAC")
    private String oldPlac;
    @Column(name = "PLAN_PLAC")
    private String planPlac;
    @Column(name = "CY_PLAC")
    private String cyPlac;
    @Column(name = "SEND_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTim;
    @Column(name = "SEND_NAM")
    private String sendNam;
    @Column(name = "SHIP_WORK_NAM")
    private String shipWorkNam;
    @Column(name = "SHIP_WORK_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipWorkTim;
    @Column(name = "IN_CY_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inCyTim;
    @Column(name = "IN_CY_NAM")
    private String inCyNam;
    @Column(name = "OUT_CY_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date outCyTim;
    @Column(name = "OUT_CY_NAM")
    private String outCyNam;
    @Column(name = "FINISHED_ID")
    private Character finishedId;
    @Column(name = "DRIVER")
    private String driver;
    @Column(name = "NIGHT_ID")
    private Character nightId;
    @Column(name = "HOLIDAY_ID")
    private Character holidayId;
    @Column(name = "USE_MACH_ID")
    private Character useMachId;
    @Column(name = "USE_WORKER_ID")
    private Character useWorkerId;
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "LENGTH_OVER_ID")
    private Character lengthOverId;
    @Column(name = "WIDTH_OVER_ID")
    private Character widthOverId;
    @Column(name = "CAR_LEVEL")
    private String carLevel;
    @Column(name = "VCEXCEPTION")
    private String vcexception;
    @Column(name = "LX_PDA_ID")
    private String lxPdaId;
    @Column(name = "CONFIRM_ID")
    private String confirmId;
    @Column(name = "CONFIRM_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmTim;
    @Column(name = "CONFIRM_NAM")
    private String confirmNam;
    @Column(name = "QZ_ID")
    private String qzId;
    @Column(name = "CY_AREA")
    private String cyArea;
    @Column(name = "CY_ROW")
    private String cyRow;
    @Column(name = "CY_BAY")
    private String cyBay;
    @Column(name = "NAM_NO")
    private String namNo;
    
    
   /* @JoinColumn(name = "SHIP_NO", referencedColumnName = "SHIP_NO", insertable = false, updatable = false)
   	@OneToOne(fetch = FetchType.EAGER)
   	private ShipQuery shipQuery;
    
    
    @Transient
    private String namNo1;
    
    @Transient
    private String num1;
    
    @Transient
    private String num2;
    
    public String getNum1() {
		if(shipQuery!=null){
			return shipQuery.getCShipNam();
		}else{
		return num1;}
	}
    public void setNum1(String num1) {
		this.num1 = num1;
	}
    
    public String getNum2() {
		if(shipQuery!=null){
			return shipQuery.getIvoyage();
		}else{
		return num2;}
	}
    public void setNum2(String num2) {
		this.num2 = num2;
	}
    
    
    public String getNamNo() {
		if(shipQuery!=null){
			namNo=shipQuery.getCShipNam()+"-"+shipQuery.getIvoyage();
			return namNo;
		}else{
		return namNo;}
	}
    public void setNamNo(String namNo) {
		this.namNo = namNo;
	}
    
    public String getNamNo1() {
		return num1 + "（" + num2 + "）";
	}

	public void setNamNo1(String namNo1) {
		this.namNo1 = namNo1;
	}*/

	public MNobillWorkCommand() {
    }

    public String getNamNo() {
		return namNo;
	}

	public void setNamNo(String namNo) {
		this.namNo = namNo;
	}

	public MNobillWorkCommand(String queueId) {
        this.queueId = queueId;
    }

    public MNobillWorkCommand(String queueId, String workQueueNo, String workTyp) {
        this.queueId = queueId;
        this.workQueueNo = workQueueNo;
        this.workTyp = workTyp;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getWorkQueueNo() {
        return workQueueNo;
    }

    public void setWorkQueueNo(String workQueueNo) {
        this.workQueueNo = workQueueNo;
    }

    public Long getPortCarNo() {
        return portCarNo;
    }

    public void setPortCarNo(Long portCarNo) {
        this.portCarNo = portCarNo;
    }

    public String getRfidCardNo() {
        return rfidCardNo;
    }

    public void setRfidCardNo(String rfidCardNo) {
        this.rfidCardNo = rfidCardNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getWorkTyp() {
        return workTyp;
    }

    public void setWorkTyp(String workTyp) {
        this.workTyp = workTyp;
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

    public String getCarKind() {
        return carKind;
    }

    public void setCarKind(String carKind) {
        this.carKind = carKind;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public Character getDirectId() {
        return directId;
    }

    public void setDirectId(Character directId) {
        this.directId = directId;
    }

    public String getOldPlac() {
        return oldPlac;
    }

    public void setOldPlac(String oldPlac) {
        this.oldPlac = oldPlac;
    }

    public String getPlanPlac() {
        return planPlac;
    }

    public void setPlanPlac(String planPlac) {
        this.planPlac = planPlac;
    }

    public String getCyPlac() {
        return cyPlac;
    }

    public void setCyPlac(String cyPlac) {
        this.cyPlac = cyPlac;
    }

    public Date getSendTim() {
        return sendTim;
    }

    public void setSendTim(Date sendTim) {
        this.sendTim = sendTim;
    }

    public String getSendNam() {
        return sendNam;
    }

    public void setSendNam(String sendNam) {
        this.sendNam = sendNam;
    }

    public String getShipWorkNam() {
        return shipWorkNam;
    }

    public void setShipWorkNam(String shipWorkNam) {
        this.shipWorkNam = shipWorkNam;
    }

    public Date getShipWorkTim() {
        return shipWorkTim;
    }

    public void setShipWorkTim(Date shipWorkTim) {
        this.shipWorkTim = shipWorkTim;
    }

    public Date getInCyTim() {
        return inCyTim;
    }

    public void setInCyTim(Date inCyTim) {
        this.inCyTim = inCyTim;
    }

    public String getInCyNam() {
        return inCyNam;
    }

    public void setInCyNam(String inCyNam) {
        this.inCyNam = inCyNam;
    }

    public Date getOutCyTim() {
        return outCyTim;
    }

    public void setOutCyTim(Date outCyTim) {
        this.outCyTim = outCyTim;
    }

    public String getOutCyNam() {
        return outCyNam;
    }

    public void setOutCyNam(String outCyNam) {
        this.outCyNam = outCyNam;
    }

    public Character getFinishedId() {
        return finishedId;
    }

    public void setFinishedId(Character finishedId) {
        this.finishedId = finishedId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Character getNightId() {
        return nightId;
    }

    public void setNightId(Character nightId) {
        this.nightId = nightId;
    }

    public Character getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Character holidayId) {
        this.holidayId = holidayId;
    }

    public Character getUseMachId() {
        return useMachId;
    }

    public void setUseMachId(Character useMachId) {
        this.useMachId = useMachId;
    }

    public Character getUseWorkerId() {
        return useWorkerId;
    }

    public void setUseWorkerId(Character useWorkerId) {
        this.useWorkerId = useWorkerId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Character getLengthOverId() {
        return lengthOverId;
    }

    public void setLengthOverId(Character lengthOverId) {
        this.lengthOverId = lengthOverId;
    }

    public Character getWidthOverId() {
        return widthOverId;
    }

    public void setWidthOverId(Character widthOverId) {
        this.widthOverId = widthOverId;
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

    public String getLxPdaId() {
        return lxPdaId;
    }

    public void setLxPdaId(String lxPdaId) {
        this.lxPdaId = lxPdaId;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public Date getConfirmTim() {
        return confirmTim;
    }

    public void setConfirmTim(Date confirmTim) {
        this.confirmTim = confirmTim;
    }

    public String getConfirmNam() {
        return confirmNam;
    }

    public void setConfirmNam(String confirmNam) {
        this.confirmNam = confirmNam;
    }

    public String getQzId() {
        return qzId;
    }

    public void setQzId(String qzId) {
        this.qzId = qzId;
    }

    public String getCyArea() {
        return cyArea;
    }

    public void setCyArea(String cyArea) {
        this.cyArea = cyArea;
    }

    public String getCyRow() {
        return cyRow;
    }

    public void setCyRow(String cyRow) {
        this.cyRow = cyRow;
    }

    public String getCyBay() {
        return cyBay;
    }

    public void setCyBay(String cyBay) {
        this.cyBay = cyBay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (queueId != null ? queueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MNobillWorkCommand)) {
            return false;
        }
        MNobillWorkCommand other = (MNobillWorkCommand) object;
        if ((this.queueId == null && other.queueId != null) || (this.queueId != null && !this.queueId.equals(other.queueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.MNobillWorkCommand[ queueId=" + queueId + " ]";
    }
    
}
