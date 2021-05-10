package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BILL_CAR database table.
 * 
 */
@Entity
@Table(name="M_FEE_INTERFACE_TALLY")
@NamedQuery(name="MFeeInterfaceTally.findAll", query="SELECT b FROM MFeeInterfaceTally b")
public class MFeeInterfaceTallyBak implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MANIFESTTRADEID")
    private String ManifestTradeId;

    /**
    *货物长度/单车长度
    */
    @Column(name = "CARGOLENGTH")
    private BigDecimal CargoLength;

    /**
    *码头类型 固定值=RORO
    */
    @Column(name = "COMPLEXID")
    private String ComplexId;
    
    /**
     *品牌
     */
	 @Column(name = "BRANDID")
	 private String brandid;
	 
	 /**
     *品牌
     */
	 @Column(name = "MODELID")
	 private String modelid;

    /**
    *船名
    */
    @Column(name = "VESSELNAME")
    private String VesselName;

    /**
    *舱单体积
    */
    @Column(name = "MANIFESTVOLUMN")
    private BigDecimal ManifestVolumn;


    /**
    *单件体积    ？？？ 立方米
    */
    @Column(name = "WORKCARGOVOLUMN")
    private BigDecimal WorkCargoVolumn;

    /**
    *舱单号
    */
    @Column(name = "MANIFESTNO")
    private String ManifestNo;

    /**
    *货主-汉语
    */
    @Column(name = "CARGOOWNERID")
    private String CargoOwnerId;

    /**
    *存储时长 堆存或停泊需要填写，如100天
    */
    @Column(name = "STORAGEQUANTITY")
    private BigDecimal StorageQuantity;

    /**
    *
    */
    @Column(name = "SENDTIME")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date SendTime;

    /**
    *生产系统船期ID
    */
    @Column(name = "VESSELVISITID")
    private String VesselVisitId;


    /**
    *航次
    */
    @Column(name = "VOYAGE")
    private String Voyage;

    /**
    *货类-汉语
    */
    @Column(name = "CHARGECARGOTYPEID")
    private String ChargeCargotypeId;

    /**
    *停泊类型 取值: 滚装非生产性停泊/滚装锚地停泊/滚装生产性停泊
    */
    @Column(name = "MOVETYPEID")
    private String MoveTypeId;
    /**
    *
    */
    @Column(name = "NIGHT_ID")
    private String nightid;

    /**
    *
    */
    @Column(name = "HOLIDAY_ID")
    private String holidayid;
    /**
    *计费结束时间
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHARGEENDTIME")
    private Date ChargeEndTime;

    /**
    *生成系统该船期对应的表主键（码头公司表）
    */
    @Column(name = "VOYAGEFACILITYINTERFACEID")
    private String VoyageFacilityInterfaceId;

    /**
    *货方使用港方机械，0=否，1=是，默认为0
    */
    @Column(name = "CARGOMECHANICALID")
    private String CargoMechanicalId;

    /**
    *MAFI拖头数量
    */
    @Column(name = "MAFINUMBER")
    private BigDecimal MafiNumber;

    /**
    *船代 - 汉语
    */
    @Column(name = "VESSELAGENTID")
    private String VesselAgentId;


    /**
    *实际作业重量 单件重量   ？？？？单位 ：Kg or 吨
    */
    @Column(name = "WORKCARGOWEIGHT")
    private BigDecimal WorkCargoWeight;

    /**
    *舱单重量
    */
    @Column(name = "MANIFESTWEIGHT")
    private BigDecimal ManifestWeight;

    /**
    *入库工班班次，取值：白班/夜班
    */
    @Column(name = "INVSHIFTID")
    private String InvShiftId;

    /**
    *库场类型  取值：库/场
    */
    @Column(name = "STORAGETYPEID")
    private String StorageTypeId;

    /**
    *舱单件数
    */
    @Column(name = "MANIFESTPKGS")
    private BigDecimal ManifestPkgs;

    /**
    *工班作业日期
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WORKDATE")
    private Date WorkDate;

    /**
    *工班作业人数
    */
    @Column(name = "WORKERNUMBER")
    private BigDecimal WorkerNumber;

    /**
    *进出口,进口=I,出口=E，取值：I/E
    */
    @Column(name = "IMPEXPID")
    private String ImpExpId;

    /**
    *出库工班班次，取值：白班/夜班
    */
    @Column(name = "OUTSHIFTID")
    private String OutShiftId;

    /**
    *军品标记 取值0/1
    */
    @Column(name = "JPID")
    private String JpId;

    /**
    *随车标记 ，1=随车 0=不随车 默认为1
    */
    @Column(name = "TOOLBOXID")
    private String ToolBoxId;

    /**
    * 航陆运  ******* 取值范围：航运/陆运 *****
    */
    @Column(name = "TRANSPORTTYPEID")
    private String TransportTypeId;


    /**
    *贸易条款 取值：船方/货方
    */
    @Column(name = "CHARGETERM")
    private String ChargeTerm;

    /**
    *运输工具  -- 汉语 --  调用基础数据接口 columnName=' MOVE_TOOL_ID'
    */
    @Column(name = "MOVETOOLID")
    private String MoveToolId;

    /**
    *码头公司代码，滚装=GZ,环球滚装=HQGZ,固定
    */
    @Column(name = "FACILITYID")
    private String FacilityId;

    /**
    *入库工班日期
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INVWARHOUSEDATE")
    private Date InvWarhouseDate;

    /**
    *货名 - 汉语
    */
    @Column(name = "CHARGECARGONAME")
    private String ChargeCargoName;

    /**
    *实际作业件数
    */
    @Column(name = "WORKCARGOPKG")
    private BigDecimal WorkCargoPkg;

    /**
    *原舱单号。如果没有分单，该字段不用填，只填舱单号
    */
    @Column(name = "ORGMANIFESTNO")
    private String OrgManifestNo;

    /**
    *场地名称
    */
    @Column(name = "YARDTYPEID")
    private String YardTypeId;

    /**
    *是否使用港方工人，0=不使用、1=使用
    */
    @Column(name = "PORTWOKERID")
    private String PortWokerId;

    /**
    *
    */
    @Column(name = "SENDNAME")
    private String SendName;

    /**
    *海洋油标记 ，1=是、0=不是，默认填0
    */
    @Column(name = "OCEANOILID")
    private String OceanOilId;

    /**
    *发票货名，默认和计费货名一致，如果不一致则需要填写
    */
    @Column(name = "INVOICECARGONAME")
    private String InvoiceCargoName;

    /**
    *作业字体主键 ？？？
    */
    @Column(name = "CARGOMOVEWORKID")
    private String CargoMoveWorkId;

    /**
    *货代-汉语
    */
    @Column(name = "CARGOAGENTID")
    private String CargoAgentId;

    /**
    *计费开始时间
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHARGEBEGINTIME")
    private Date ChargeBeginTime;

    /**
    *过境标记 取值0/1     -- ？？ 怎么区分过境 ？？？
    */
    @Column(name = "GJID")
    private String GjId;

    /**
    *生产系统该船期对应的表主键（集团表）
    */
    @Column(name = "VOYAGEINTERFACEID")
    private String VoyageInterfaceId;

    /**
    *工班作业班次
    */
    @Column(name = "WORKSHIFTID")
    private String WorkShiftId;

    /**
    *-- 计费单位 重量=W 件数=P 体积=M 择大=W/M
    */
    @Column(name = "CARGOCHARGEUNITID")
    private String CargoChargeUnitId;

    /**
    *直提标志 取值：0/1
    */
    @Column(name = "DIRECTID")
    private String DirectId;

    /**
    *人力机力 0=都不用、1=人力 2=机力 3=人力机力
    */
    @Column(name = "WORKERID")
    private String WorkerId;

    /**
    *是否使用港方机械，0=不使用、1=使用
    */
    @Column(name = "PORTMECHANICALID")
    private String PortMechanicalId;

    /**
    *船方机械，0=否，1=是，默认为0
    */
    @Column(name = "SHIPMECHANICALID")
    private String ShipMechanicalId;

    /**
    *船公司
    */
    @Column(name = "CARRIERID")
    private String CarrierId;

    /**
    *英文船名
    */
    @Column(name = "VESSELNAMEEN")
    private String VesselNameEn;

    /**
    *交货条款 取值范围 调用基础数据接口，columnName='DELIVERY_TERM_ID'  ？？？？？？？？？？？
    */
    @Column(name = "DELIVERYTERM")
    private String DeliveryTerm;

    /**
    *危品标志 ，取值0/1
    */
    @Column(name = "HAZARDID")
    private String HazardId;

    /**
    *出库工班日期
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OUTWARHOUSEDATE")
    private Date OutWarhouseDate;

	public String getManifestTradeId() {
		return ManifestTradeId;
	}

	public void setManifestTradeId(String manifestTradeId) {
		ManifestTradeId = manifestTradeId;
	}

	public String getComplexId() {
		return ComplexId;
	}

	public void setComplexId(String complexId) {
		ComplexId = complexId;
	}

	public String getVesselName() {
		return VesselName;
	}

	public void setVesselName(String vesselName) {
		VesselName = vesselName;
	}

	public String getManifestNo() {
		return ManifestNo;
	}

	public void setManifestNo(String manifestNo) {
		ManifestNo = manifestNo;
	}

	public String getCargoOwnerId() {
		return CargoOwnerId;
	}

	public void setCargoOwnerId(String cargoOwnerId) {
		CargoOwnerId = cargoOwnerId;
	}

	public Date getSendTime() {
		return SendTime;
	}

	public void setSendTime(Date sendTime) {
		SendTime = sendTime;
	}

	public String getVesselVisitId() {
		return VesselVisitId;
	}

	public void setVesselVisitId(String vesselVisitId) {
		VesselVisitId = vesselVisitId;
	}

	public String getVoyage() {
		return Voyage;
	}

	public void setVoyage(String voyage) {
		Voyage = voyage;
	}

	public String getChargeCargotypeId() {
		return ChargeCargotypeId;
	}

	public void setChargeCargotypeId(String chargeCargotypeId) {
		ChargeCargotypeId = chargeCargotypeId;
	}

	public String getMoveTypeId() {
		return MoveTypeId;
	}

	public void setMoveTypeId(String moveTypeId) {
		MoveTypeId = moveTypeId;
	}

	public Date getChargeEndTime() {
		return ChargeEndTime;
	}

	public void setChargeEndTime(Date chargeEndTime) {
		ChargeEndTime = chargeEndTime;
	}

	public String getVoyageFacilityInterfaceId() {
		return VoyageFacilityInterfaceId;
	}

	public void setVoyageFacilityInterfaceId(String voyageFacilityInterfaceId) {
		VoyageFacilityInterfaceId = voyageFacilityInterfaceId;
	}

	public String getCargoMechanicalId() {
		return CargoMechanicalId;
	}

	public void setCargoMechanicalId(String cargoMechanicalId) {
		CargoMechanicalId = cargoMechanicalId;
	}

	public String getVesselAgentId() {
		return VesselAgentId;
	}

	public void setVesselAgentId(String vesselAgentId) {
		VesselAgentId = vesselAgentId;
	}

	public String getInvShiftId() {
		return InvShiftId;
	}

	public void setInvShiftId(String invShiftId) {
		InvShiftId = invShiftId;
	}

	public String getStorageTypeId() {
		return StorageTypeId;
	}

	public void setStorageTypeId(String storageTypeId) {
		StorageTypeId = storageTypeId;
	}

	public Date getWorkDate() {
		return WorkDate;
	}

	public void setWorkDate(Date workDate) {
		WorkDate = workDate;
	}

	public String getImpExpId() {
		return ImpExpId;
	}

	public void setImpExpId(String impExpId) {
		ImpExpId = impExpId;
	}

	public String getOutShiftId() {
		return OutShiftId;
	}

	public void setOutShiftId(String outShiftId) {
		OutShiftId = outShiftId;
	}

	public String getJpId() {
		return JpId;
	}

	public void setJpId(String jpId) {
		JpId = jpId;
	}

	public String getToolBoxId() {
		return ToolBoxId;
	}

	public void setToolBoxId(String toolBoxId) {
		ToolBoxId = toolBoxId;
	}

	public String getTransportTypeId() {
		return TransportTypeId;
	}

	public void setTransportTypeId(String transportTypeId) {
		TransportTypeId = transportTypeId;
	}

	public String getChargeTerm() {
		return ChargeTerm;
	}

	public void setChargeTerm(String chargeTerm) {
		ChargeTerm = chargeTerm;
	}

	public String getMoveToolId() {
		return MoveToolId;
	}

	public void setMoveToolId(String moveToolId) {
		MoveToolId = moveToolId;
	}

	public String getFacilityId() {
		return FacilityId;
	}

	public void setFacilityId(String facilityId) {
		FacilityId = facilityId;
	}

	public Date getInvWarhouseDate() {
		return InvWarhouseDate;
	}

	public void setInvWarhouseDate(Date invWarhouseDate) {
		InvWarhouseDate = invWarhouseDate;
	}

	public String getChargeCargoName() {
		return ChargeCargoName;
	}

	public void setChargeCargoName(String chargeCargoName) {
		ChargeCargoName = chargeCargoName;
	}

	public String getOrgManifestNo() {
		return OrgManifestNo;
	}

	public void setOrgManifestNo(String orgManifestNo) {
		OrgManifestNo = orgManifestNo;
	}

	public String getNightid() {
		return nightid;
	}

	public void setNightid(String nightid) {
		this.nightid = nightid;
	}

	public String getHolidayid() {
		return holidayid;
	}

	public void setHolidayid(String holidayid) {
		this.holidayid = holidayid;
	}

	public String getYardTypeId() {
		return YardTypeId;
	}

	public void setYardTypeId(String yardTypeId) {
		YardTypeId = yardTypeId;
	}

	public String getPortWokerId() {
		return PortWokerId;
	}

	public void setPortWokerId(String portWokerId) {
		PortWokerId = portWokerId;
	}

	public String getSendName() {
		return SendName;
	}

	public void setSendName(String sendName) {
		SendName = sendName;
	}

	public String getOceanOilId() {
		return OceanOilId;
	}

	public void setOceanOilId(String oceanOilId) {
		OceanOilId = oceanOilId;
	}

	public String getInvoiceCargoName() {
		return InvoiceCargoName;
	}

	public void setInvoiceCargoName(String invoiceCargoName) {
		InvoiceCargoName = invoiceCargoName;
	}

	public String getCargoMoveWorkId() {
		return CargoMoveWorkId;
	}

	public void setCargoMoveWorkId(String cargoMoveWorkId) {
		CargoMoveWorkId = cargoMoveWorkId;
	}

	public String getCargoAgentId() {
		return CargoAgentId;
	}

	public void setCargoAgentId(String cargoAgentId) {
		CargoAgentId = cargoAgentId;
	}

	public Date getChargeBeginTime() {
		return ChargeBeginTime;
	}

	public void setChargeBeginTime(Date chargeBeginTime) {
		ChargeBeginTime = chargeBeginTime;
	}

	public String getGjId() {
		return GjId;
	}

	public void setGjId(String gjId) {
		GjId = gjId;
	}

	public String getVoyageInterfaceId() {
		return VoyageInterfaceId;
	}

	public void setVoyageInterfaceId(String voyageInterfaceId) {
		VoyageInterfaceId = voyageInterfaceId;
	}

	public String getWorkShiftId() {
		return WorkShiftId;
	}

	public void setWorkShiftId(String workShiftId) {
		WorkShiftId = workShiftId;
	}

	public String getCargoChargeUnitId() {
		return CargoChargeUnitId;
	}

	public void setCargoChargeUnitId(String cargoChargeUnitId) {
		CargoChargeUnitId = cargoChargeUnitId;
	}

	public String getDirectId() {
		return DirectId;
	}

	public void setDirectId(String directId) {
		DirectId = directId;
	}

	public String getWorkerId() {
		return WorkerId;
	}

	public void setWorkerId(String workerId) {
		WorkerId = workerId;
	}

	public String getPortMechanicalId() {
		return PortMechanicalId;
	}

	public void setPortMechanicalId(String portMechanicalId) {
		PortMechanicalId = portMechanicalId;
	}

	public String getShipMechanicalId() {
		return ShipMechanicalId;
	}

	public void setShipMechanicalId(String shipMechanicalId) {
		ShipMechanicalId = shipMechanicalId;
	}

	public String getCarrierId() {
		return CarrierId;
	}

	public void setCarrierId(String carrierId) {
		CarrierId = carrierId;
	}

	public String getVesselNameEn() {
		return VesselNameEn;
	}

	public void setVesselNameEn(String vesselNameEn) {
		VesselNameEn = vesselNameEn;
	}

	public String getDeliveryTerm() {
		return DeliveryTerm;
	}

	public void setDeliveryTerm(String deliveryTerm) {
		DeliveryTerm = deliveryTerm;
	}

	public String getHazardId() {
		return HazardId;
	}

	public void setHazardId(String hazardId) {
		HazardId = hazardId;
	}

	public Date getOutWarhouseDate() {
		return OutWarhouseDate;
	}

	public void setOutWarhouseDate(Date outWarhouseDate) {
		OutWarhouseDate = outWarhouseDate;
	}

	public BigDecimal getCargoLength() {
		return CargoLength;
	}

	public void setCargoLength(BigDecimal cargoLength) {
		CargoLength = cargoLength;
	}

	public BigDecimal getManifestVolumn() {
		return ManifestVolumn;
	}

	public void setManifestVolumn(BigDecimal manifestVolumn) {
		ManifestVolumn = manifestVolumn;
	}

	public BigDecimal getWorkCargoVolumn() {
		return WorkCargoVolumn;
	}

	public void setWorkCargoVolumn(BigDecimal workCargoVolumn) {
		WorkCargoVolumn = workCargoVolumn;
	}

	public BigDecimal getStorageQuantity() {
		return StorageQuantity;
	}

	public void setStorageQuantity(BigDecimal storageQuantity) {
		StorageQuantity = storageQuantity;
	}

	public BigDecimal getMafiNumber() {
		return MafiNumber;
	}

	public void setMafiNumber(BigDecimal mafiNumber) {
		MafiNumber = mafiNumber;
	}

	public BigDecimal getWorkCargoWeight() {
		return WorkCargoWeight;
	}

	public void setWorkCargoWeight(BigDecimal workCargoWeight) {
		WorkCargoWeight = workCargoWeight;
	}

	public BigDecimal getManifestWeight() {
		return ManifestWeight;
	}

	public void setManifestWeight(BigDecimal manifestWeight) {
		ManifestWeight = manifestWeight;
	}

	public BigDecimal getManifestPkgs() {
		return ManifestPkgs;
	}

	public void setManifestPkgs(BigDecimal manifestPkgs) {
		ManifestPkgs = manifestPkgs;
	}

	public BigDecimal getWorkerNumber() {
		return WorkerNumber;
	}

	public void setWorkerNumber(BigDecimal workerNumber) {
		WorkerNumber = workerNumber;
	}

	public BigDecimal getWorkCargoPkg() {
		return WorkCargoPkg;
	}

	public void setWorkCargoPkg(BigDecimal workCargoPkg) {
		WorkCargoPkg = workCargoPkg;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}


    

    
}