package net.huadong.tech.shipbill.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the BILL_CAR database table.
 * 
 */
@Entity
@Table(name="M_FEE_INTERFACE_TALLY")
@NamedQuery(name="MFeeInterfaceTally.findAll", query="SELECT b FROM MFeeInterfaceTally b")
public class MFeeInterfaceTally implements Serializable {
	private static final long serialVersionUID = 1L;

	
	 /**
     *进出口,进口=I,出口=E，取值：I/E
     */
     @Column(name = "IMPEXPID")
     private String impexpid;

     /**
     *出库工班班次，取值：白班/夜班
     */
     @Column(name = "OUTSHIFTID")
     private String outshiftid;

     /**
     *工班作业班次
     */
     @Column(name = "WORKSHIFTID")
     private String workshiftid;
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
     *航次
     */
     @Column(name = "VOYAGE")
     private String voyage;
     
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
     *实际作业件数
     */
     @Column(name = "WORKCARGOPKG")
     private BigDecimal workcargopkg;

     /**
     *MAFI拖头数量
     */
     @Column(name = "MAFINUMBER")
     private BigDecimal mafinumber;

     /**
     *计费系统接收删除报文成功 回执标记 1=已接收；0=未接收
     */
     @Column(name = "JF_DEL_OK_ID")
     private String jfDelOkId;

     /**
     *单件体积    ？？？ 立方米
     */
     @Column(name = "WORKCARGOVOLUMN")
     private BigDecimal workcargovolumn;

     /**
     *-- 计费单位 重量=W 件数=P 体积=M 择大=W/M
     */
     @Column(name = "CARGOCHARGEUNITID")
     private String cargochargeunitid;

     /**
     *存储时长 堆存或停泊需要填写，如100天
     */
     @Column(name = "STORAGEQUANTITY")
     private BigDecimal storagequantity;

     /**
     *出库工班日期
     */
     @Column(name = "OUTWARHOUSEDATE")
     private Timestamp outwarhousedate;

     /**
     *报文发送标记 0=未发送 1=已发送
     */
     @Column(name = "BW_SEND_ID")
     private String bwSendId;

     /**
     *英文船名
     */
     @Column(name = "VESSELNAMEEN")
     private String vesselnameen;

     /**
     *货类-汉语
     */
     @Column(name = "CHARGECARGOTYPEID")
     private String chargecargotypeid;

     /**
     *交货条款 取值范围 调用基础数据接口，columnName='DELIVERY_TERM_ID'  ？？？？？？？？？？？
     */
     @Column(name = "DELIVERYTERM")
     private String deliveryterm;

     /**
     *货主-汉语
     */
     @Column(name = "CARGOOWNERID")
     private String cargoownerid;

     /**
     *过境标记 取值0/1     -- ？？ 怎么区分过境 ？？？
     */
     @Column(name = "GJID")
     private String gjid;

     /**
     *
     */
     @Column(name = "SENDNAME")
     private String sendname;

     /**
     *船代 - 汉语
     */
     @Column(name = "VESSELAGENTID")
     private String vesselagentid;

     /**
     *船公司
     */
     @Column(name = "CARRIERID")
     private String carrierid;

     /**
     *货代-汉语
     */
     @Column(name = "CARGOAGENTID")
     private String cargoagentid;

     /**
     *
     */
     @Column(name = "SENDTIME")
     @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
     private Timestamp sendtime;

     /**
     *发票货名，默认和计费货名一致，如果不一致则需要填写
     */
     @Column(name = "INVOICECARGONAME")
     private String invoicecargoname;

     /**
     *库场类型  取值：库/场
     */
     @Column(name = "STORAGETYPEID")
     private String storagetypeid;

     /**
     *是否使用港方机械，0=不使用、1=使用
     */
     @Column(name = "PORTMECHANICALID")
     private String portmechanicalid;

     /**
     *是否使用港方工人，0=不使用、1=使用
     */
     @Column(name = "PORTWOKERID")
     private String portwokerid;

     /**
     *舱单重量
     */
     @Column(name = "MANIFESTWEIGHT")
     private BigDecimal manifestweight;

     /**
     *危品标志 ，取值0/1
     */
     @Column(name = "HAZARDID")
     private String hazardid;

     /**
     *生产系统船期ID
     */
     @Column(name = "VESSELVISITID")
     private String vesselvisitid;

     /**
     *舱单号
     */
     @Column(name = "MANIFESTNO")
     private String manifestno;

     /**
     *原舱单号。如果没有分单，该字段不用填，只填舱单号
     */
     @Column(name = "ORGMANIFESTNO")
     private String orgmanifestno;

     /**
     *随车标记 ，1=随车 0=不随车 默认为1
     */
     @Column(name = "TOOLBOXID")
     private String toolboxid;

     /**
     *计费开始时间
     */
     @Column(name = "CHARGEBEGINTIME")
     private Timestamp chargebegintime;

     /**
     *海洋油标记 ，1=是、0=不是，默认填0
     */
     @Column(name = "OCEANOILID")
     private String oceanoilid;

     /**
     *人力机力 0=都不用、1=人力 2=机力 3=人力机力
     */
     @Column(name = "WORKERID")
     private String workerid;

     /**
     *生产系统该船期对应的表主键（集团表）
     */
     @Column(name = "VOYAGEINTERFACEID")
     private String voyageinterfaceid;

     /**
     * 航陆运  ******* 取值范围：航运/陆运 *****
     */
     @Column(name = "TRANSPORTTYPEID")
     private String transporttypeid;

     /**
     *作业字体主键 ？？？
     */
     @Column(name = "CARGOMOVEWORKID")
     private String cargomoveworkid;

     /**
     *货名 - 汉语
     */
     @Column(name = "CHARGECARGONAME")
     private String chargecargoname;

     /**
     *货物长度/单车长度
     */
     @Column(name = "CARGOLENGTH")
     private BigDecimal cargolength;

     /**
     *直提标志 取值：0/1
     */
     @Column(name = "DIRECTID")
     private String directid;

     /**
     *入库工班班次，取值：白班/夜班
     */
     @Column(name = "INVSHIFTID")
     private String invshiftid;

     /**
     *删除标记  1=已删除；0=未删除/默认
     */
     @Column(name = "DEL_ID")
     private String delId;

     /**
     *工班作业日期
     */
     @Column(name = "WORKDATE")
     private Timestamp workdate;

     /**
     *货方使用港方机械，0=否，1=是，默认为0
     */
     @Column(name = "CARGOMECHANICALID")
     private String cargomechanicalid;

     /**
     *舱单件数
     */
     @Column(name = "MANIFESTPKGS")
     private BigDecimal manifestpkgs;

     /**
     *计费系统接收报文成功 回执标记 1=已接收；0=未接收
     */
     @Column(name = "JF_OK_ID")
     private String jfOkId;

     /**
     *删除报文 发送完毕 1=已发送 0=未发送
     */
     @Column(name = "DEL_SEND_ID")
     private String delSendId;

     /**
     *
     */
     @Column(name = "USER_ID")
     private String userId;

     /**
     *
     */
     @Column(name = "USERNAME")
     private String username;

     /**
     *生产系统用户IP
     */
     @Column(name = "USERIP")
     private String userip;

     /**
     *运输工具  -- 汉语 --  调用基础数据接口 columnName=' MOVE_TOOL_ID'
     */
     @Column(name = "MOVETOOLID")
     private String movetoolid;

     /**
     *船方机械，0=否，1=是，默认为0
     */
     @Column(name = "SHIPMECHANICALID")
     private String shipmechanicalid;

     /**
     *贸易条款 取值：船方/货方
     */
     @Column(name = "CHARGETERM")
     private String chargeterm;

     /**
     *生成系统该船期对应的表主键（码头公司表）
     */
     @Column(name = "VOYAGEFACILITYINTERFACEID")
     private String voyagefacilityinterfaceid;

     /**
     *入库工班日期
     */
     @Column(name = "INVWARHOUSEDATE")
     private Timestamp invwarhousedate;

     /**
     *????????
     */
     @Column(name = "MANIFESTTRADEID")
     private String manifesttradeid;

     /**
     *码头公司代码，滚装=GZ,环球滚装=HQGZ,固定
     */
     @Column(name = "FACILITYID")
     private String facilityid;

     /**
     *货名，调货名接口
     */
     @Id
     @Column(name = "ID")
     private String id;

     /**
     *船名
     */
     @Column(name = "VESSELNAME")
     private String vesselname;

     /**
     *实际作业重量 单件重量   ？？？？单位 ：Kg or 吨
     */
     @Column(name = "WORKCARGOWEIGHT")
     private BigDecimal workcargoweight;

     /**
     *停泊类型 取值: 滚装非生产性停泊/滚装锚地停泊/滚装生产性停泊
     */
     @Column(name = "MOVETYPEID")
     private String movetypeid;

     /**
     *计费结束时间
     */
     @Column(name = "CHARGEENDTIME")
     @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
     private Timestamp chargeendtime;

     /**
     *码头类型 固定值=RORO
     */
     @Column(name = "COMPLEXID")
     private String complexid;

     /**
     *军品标记 取值0/1
     */
     @Column(name = "JPID")
     private String jpid;

     /**
     *舱单体积
     */
     @Column(name = "MANIFESTVOLUMN")
     private BigDecimal manifestvolumn;

     /**
     *工班作业人数
     */
     @Column(name = "WORKERNUMBER")
     private BigDecimal workernumber;

     /**
     *场地名称
     */
     @Column(name = "YARDTYPEID")
     private String yardtypeid;
     
     
     @Column(name = "OVERLENGTHID")
     private String overlengthid;
     
     @Column(name = "TALLY_SPLIT_ID")
     private String tallySplitId;
     
     @Transient
     private String usefreightmac;
     
     
     public MFeeInterfaceTally(){
    	 
     }
     
     public MFeeInterfaceTally(MFeeInterfaceTally a, String usefreightmac){
    	this.setId(a.getId());
    	//TODO 在这把其他的字段set一下，跟那个setId一样
    	this.setImpexpid(a.getImpexpid());
        this.setOutshiftid(a.getOutshiftid()); 
        this.setWorkshiftid(a.getWorkshiftid()); 
        this.setNightid(a.getNightid());
        this.setHolidayid(a.getHolidayid()); 
        this.setVoyage(a.getVoyage()); 
        this.setBrandid(a.getBrandid()); 
        this.setModelid(a.getModelid());
        this.setWorkcargopkg(a.getWorkcargopkg()); 
        this.setMafinumber(a.getMafinumber());
        this.setJfDelOkId(a.getJfDelOkId());
        this.setWorkcargovolumn(a.getWorkcargovolumn());
        this.setCargochargeunitid(a.getCargochargeunitid()); 
        this.setStoragequantity(a.getStoragequantity()); 
        this.setOutwarhousedate(a.getOutwarhousedate());
        this.setBwSendId(a.getBwSendId()); 
        this.setVesselnameen(a.getVesselnameen());
        this.setChargecargotypeid(a.getChargecargotypeid()); 
        this.setDeliveryterm(a.getDeliveryterm());
        this.setCargoownerid(a.getCargoownerid()); 
        this.setGjid(a.getGjid()); 
        this.setSendname(a.getSendname());
        this.setVesselagentid(a.getVesselagentid()); 
        this.setCarrierid(a.getCarrierid()); 
        this.setCargoagentid(a.getCargoagentid());
        this.setSendtime(a.getSendtime());
        this.setInvoicecargoname(a.getInvoicecargoname());
        this.setStoragetypeid(a.getStoragetypeid());
        this.setPortmechanicalid(a.getPortmechanicalid());
        this.setPortwokerid(a.getPortwokerid());
        this.setManifestweight(a.getManifestweight());
        this.setHazardid(a.getHazardid());
        this.setVesselvisitid(a.getVesselvisitid());
        this.setManifestno(a.getManifestno());
        this.setOrgmanifestno(a.getOrgmanifestno());
        this.setToolboxid(a.getToolboxid());
        this.setChargebegintime(a.getChargebegintime());
        this.setOceanoilid(a.getOceanoilid());
        this.setWorkerid(a.getWorkerid());
        this.setVoyageinterfaceid(a.getVoyageinterfaceid());
        this.setTransporttypeid(a.getTransporttypeid());
        this.setCargomoveworkid(a.getCargomoveworkid());
        this.setChargecargoname(a.getChargecargoname());
        this.setCargolength(a.getCargolength());
        this.setDirectid(a.getDirectid());
        this.setInvshiftid(a.getInvshiftid());
        this.setDelId(a.getDelId());
        this.setWorkdate(a.getWorkdate());
        this.setCargomechanicalid(a.getCargomechanicalid());
        this.setManifestpkgs(a.getManifestpkgs());
        this.setJfOkId(a.getJfOkId());
        this.setDelSendId(a.getDelSendId());
        this.setUserId(a.getUserId());
        this.setUsername(a.getUsername());
        this.setUserip(a.getUserip());
        this.setMovetoolid(a.getMovetoolid());
        this.setShipmechanicalid(a.getShipmechanicalid());
        this.setChargeterm(a.getChargeterm());
        this.setVoyagefacilityinterfaceid(a.getVoyagefacilityinterfaceid());
        this.setInvwarhousedate(a.getInvwarhousedate());
        this.setManifesttradeid(a.getManifesttradeid());
        this.setFacilityid(a.getFacilityid());
        //this.setId(a.getId());
        this.setVesselname(a.getVesselname());
        this.setWorkcargoweight(a.getWorkcargoweight());
        this.setMovetypeid(a.getMovetypeid());
        this.setChargeendtime(a.getChargeendtime());
        this.setComplexid(a.getComplexid());
        this.setJpid(a.getJpid());
        this.setManifestvolumn(a.getManifestvolumn());
        this.setWorkernumber(a.getWorkernumber());
        this.setYardtypeid(a.getYardtypeid());
        this.setOverlengthid(a.getOverlengthid());
        this.setTallySplitId(a.getTallySplitId()); 
        this.setUsefreightmac(usefreightmac);
     }

	
	public String getUsefreightmac() {
		return usefreightmac;
	}

	public void setUsefreightmac(String usefreightmac) {
		this.usefreightmac = usefreightmac;
	}

	public String getTallySplitId() {
		return tallySplitId;
	}

	public void setTallySplitId(String tallySplitId) {
		this.tallySplitId = tallySplitId;
	}

	public String getOverlengthid() {
		return overlengthid;
	}

	public void setOverlengthid(String overlengthid) {
		this.overlengthid = overlengthid;
	}

	public String getImpexpid() {
		return impexpid;
	}

	public void setImpexpid(String impexpid) {
		this.impexpid = impexpid;
	}

	public String getOutshiftid() {
		return outshiftid;
	}

	public void setOutshiftid(String outshiftid) {
		this.outshiftid = outshiftid;
	}

	public String getWorkshiftid() {
		return workshiftid;
	}

	public void setWorkshiftid(String workshiftid) {
		this.workshiftid = workshiftid;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public BigDecimal getWorkcargopkg() {
		return workcargopkg;
	}

	public void setWorkcargopkg(BigDecimal workcargopkg) {
		this.workcargopkg = workcargopkg;
	}

	public BigDecimal getMafinumber() {
		return mafinumber;
	}

	public void setMafinumber(BigDecimal mafinumber) {
		this.mafinumber = mafinumber;
	}

	public String getJfDelOkId() {
		return jfDelOkId;
	}

	public void setJfDelOkId(String jfDelOkId) {
		this.jfDelOkId = jfDelOkId;
	}

	public BigDecimal getWorkcargovolumn() {
		return workcargovolumn;
	}

	public void setWorkcargovolumn(BigDecimal workcargovolumn) {
		this.workcargovolumn = workcargovolumn;
	}

	public String getCargochargeunitid() {
		return cargochargeunitid;
	}

	public void setCargochargeunitid(String cargochargeunitid) {
		this.cargochargeunitid = cargochargeunitid;
	}

	public BigDecimal getStoragequantity() {
		return storagequantity;
	}

	public void setStoragequantity(BigDecimal storagequantity) {
		this.storagequantity = storagequantity;
	}

	public Timestamp getOutwarhousedate() {
		return outwarhousedate;
	}

	public void setOutwarhousedate(Timestamp outwarhousedate) {
		this.outwarhousedate = outwarhousedate;
	}

	public String getBwSendId() {
		return bwSendId;
	}

	public void setBwSendId(String bwSendId) {
		this.bwSendId = bwSendId;
	}

	public String getVesselnameen() {
		return vesselnameen;
	}

	public void setVesselnameen(String vesselnameen) {
		this.vesselnameen = vesselnameen;
	}

	public String getChargecargotypeid() {
		return chargecargotypeid;
	}

	public void setChargecargotypeid(String chargecargotypeid) {
		this.chargecargotypeid = chargecargotypeid;
	}

	public String getDeliveryterm() {
		return deliveryterm;
	}

	public void setDeliveryterm(String deliveryterm) {
		this.deliveryterm = deliveryterm;
	}

	public String getCargoownerid() {
		return cargoownerid;
	}

	public void setCargoownerid(String cargoownerid) {
		this.cargoownerid = cargoownerid;
	}

	public String getGjid() {
		return gjid;
	}

	public void setGjid(String gjid) {
		this.gjid = gjid;
	}

	public String getSendname() {
		return sendname;
	}

	public void setSendname(String sendname) {
		this.sendname = sendname;
	}

	public String getVesselagentid() {
		return vesselagentid;
	}

	public void setVesselagentid(String vesselagentid) {
		this.vesselagentid = vesselagentid;
	}

	public String getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(String carrierid) {
		this.carrierid = carrierid;
	}

	public String getCargoagentid() {
		return cargoagentid;
	}

	public void setCargoagentid(String cargoagentid) {
		this.cargoagentid = cargoagentid;
	}

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public String getInvoicecargoname() {
		return invoicecargoname;
	}

	public void setInvoicecargoname(String invoicecargoname) {
		this.invoicecargoname = invoicecargoname;
	}

	public String getStoragetypeid() {
		return storagetypeid;
	}

	public void setStoragetypeid(String storagetypeid) {
		this.storagetypeid = storagetypeid;
	}

	public String getPortmechanicalid() {
		return portmechanicalid;
	}

	public void setPortmechanicalid(String portmechanicalid) {
		this.portmechanicalid = portmechanicalid;
	}

	public String getPortwokerid() {
		return portwokerid;
	}

	public void setPortwokerid(String portwokerid) {
		this.portwokerid = portwokerid;
	}

	public BigDecimal getManifestweight() {
		return manifestweight;
	}

	public void setManifestweight(BigDecimal manifestweight) {
		this.manifestweight = manifestweight;
	}

	public String getHazardid() {
		return hazardid;
	}

	public void setHazardid(String hazardid) {
		this.hazardid = hazardid;
	}

	public String getVesselvisitid() {
		return vesselvisitid;
	}

	public void setVesselvisitid(String vesselvisitid) {
		this.vesselvisitid = vesselvisitid;
	}

	public String getManifestno() {
		return manifestno;
	}

	public void setManifestno(String manifestno) {
		this.manifestno = manifestno;
	}

	public String getOrgmanifestno() {
		return orgmanifestno;
	}

	public void setOrgmanifestno(String orgmanifestno) {
		this.orgmanifestno = orgmanifestno;
	}

	public String getToolboxid() {
		return toolboxid;
	}

	public void setToolboxid(String toolboxid) {
		this.toolboxid = toolboxid;
	}

	public Timestamp getChargebegintime() {
		return chargebegintime;
	}

	public void setChargebegintime(Timestamp chargebegintime) {
		this.chargebegintime = chargebegintime;
	}

	public String getOceanoilid() {
		return oceanoilid;
	}

	public void setOceanoilid(String oceanoilid) {
		this.oceanoilid = oceanoilid;
	}

	public String getWorkerid() {
		return workerid;
	}

	public void setWorkerid(String workerid) {
		this.workerid = workerid;
	}

	public String getVoyageinterfaceid() {
		return voyageinterfaceid;
	}

	public void setVoyageinterfaceid(String voyageinterfaceid) {
		this.voyageinterfaceid = voyageinterfaceid;
	}

	public String getTransporttypeid() {
		return transporttypeid;
	}

	public void setTransporttypeid(String transporttypeid) {
		this.transporttypeid = transporttypeid;
	}

	public String getCargomoveworkid() {
		return cargomoveworkid;
	}

	public void setCargomoveworkid(String cargomoveworkid) {
		this.cargomoveworkid = cargomoveworkid;
	}

	public String getChargecargoname() {
		return chargecargoname;
	}

	public void setChargecargoname(String chargecargoname) {
		this.chargecargoname = chargecargoname;
	}

	public BigDecimal getCargolength() {
		return cargolength;
	}

	public void setCargolength(BigDecimal cargolength) {
		this.cargolength = cargolength;
	}

	public String getDirectid() {
		return directid;
	}

	public void setDirectid(String directid) {
		this.directid = directid;
	}

	public String getInvshiftid() {
		return invshiftid;
	}

	public void setInvshiftid(String invshiftid) {
		this.invshiftid = invshiftid;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public Timestamp getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Timestamp workdate) {
		this.workdate = workdate;
	}

	public String getCargomechanicalid() {
		return cargomechanicalid;
	}

	public void setCargomechanicalid(String cargomechanicalid) {
		this.cargomechanicalid = cargomechanicalid;
	}

	public BigDecimal getManifestpkgs() {
		return manifestpkgs;
	}

	public void setManifestpkgs(BigDecimal manifestpkgs) {
		this.manifestpkgs = manifestpkgs;
	}

	public String getJfOkId() {
		return jfOkId;
	}

	public void setJfOkId(String jfOkId) {
		this.jfOkId = jfOkId;
	}

	public String getDelSendId() {
		return delSendId;
	}

	public void setDelSendId(String delSendId) {
		this.delSendId = delSendId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getMovetoolid() {
		return movetoolid;
	}

	public void setMovetoolid(String movetoolid) {
		this.movetoolid = movetoolid;
	}

	public String getShipmechanicalid() {
		return shipmechanicalid;
	}

	public void setShipmechanicalid(String shipmechanicalid) {
		this.shipmechanicalid = shipmechanicalid;
	}

	public String getChargeterm() {
		return chargeterm;
	}

	public void setChargeterm(String chargeterm) {
		this.chargeterm = chargeterm;
	}

	public String getVoyagefacilityinterfaceid() {
		return voyagefacilityinterfaceid;
	}

	public void setVoyagefacilityinterfaceid(String voyagefacilityinterfaceid) {
		this.voyagefacilityinterfaceid = voyagefacilityinterfaceid;
	}

	public Timestamp getInvwarhousedate() {
		return invwarhousedate;
	}

	public void setInvwarhousedate(Timestamp invwarhousedate) {
		this.invwarhousedate = invwarhousedate;
	}

	public String getManifesttradeid() {
		return manifesttradeid;
	}

	public void setManifesttradeid(String manifesttradeid) {
		this.manifesttradeid = manifesttradeid;
	}

	public String getFacilityid() {
		return facilityid;
	}

	public void setFacilityid(String facilityid) {
		this.facilityid = facilityid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVesselname() {
		return vesselname;
	}

	public void setVesselname(String vesselname) {
		this.vesselname = vesselname;
	}

	public BigDecimal getWorkcargoweight() {
		return workcargoweight;
	}

	public void setWorkcargoweight(BigDecimal workcargoweight) {
		this.workcargoweight = workcargoweight;
	}

	public String getMovetypeid() {
		return movetypeid;
	}

	public void setMovetypeid(String movetypeid) {
		this.movetypeid = movetypeid;
	}

	public Timestamp getChargeendtime() {
		return chargeendtime;
	}

	public void setChargeendtime(Timestamp chargeendtime) {
		this.chargeendtime = chargeendtime;
	}

	public String getComplexid() {
		return complexid;
	}

	public void setComplexid(String complexid) {
		this.complexid = complexid;
	}

	public String getJpid() {
		return jpid;
	}

	public void setJpid(String jpid) {
		this.jpid = jpid;
	}

	public BigDecimal getManifestvolumn() {
		return manifestvolumn;
	}

	public void setManifestvolumn(BigDecimal manifestvolumn) {
		this.manifestvolumn = manifestvolumn;
	}

	public BigDecimal getWorkernumber() {
		return workernumber;
	}

	public void setWorkernumber(BigDecimal workernumber) {
		this.workernumber = workernumber;
	}

	public String getYardtypeid() {
		return yardtypeid;
	}

	public void setYardtypeid(String yardtypeid) {
		this.yardtypeid = yardtypeid;
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