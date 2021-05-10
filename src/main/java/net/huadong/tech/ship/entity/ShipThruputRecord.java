package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.util.HdUtils;

/**
 * 
 * @ClassName ShipThruput
 * @Description 吞吐量过录Model
 * @author WSW
 * @date 2018年4月20日
 *
 */
@Entity
@Table(name = "SHIP_THRUPUT_RECORD")
public class ShipThruputRecord extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 公司上报标志
	 */
	@Column(name = "SUBMIT_FLAG")
	private String submitFlag = "0";

	/**
	 * 公司上报人
	 */
	@Column(name = "SUBMIT_NAME")
	private String submitName;

	/**
	 * 公司上报时间
	 */
	@Column(name = "SUBMIT_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp submitTime;

	/**
	 * 危险品种类（共9级）
	 */
	@Column(name = "DGR_KIND")
	private String dgrKind;

	/**
	 * 危品重量
	 */
	@Column(name = "DGR_CARGO_WGT")
	private BigDecimal dgrCargoWgt;

	/**
	 * 发货人，集疏运报表用
	 */
	@Column(name = "CNOR_CODE")
	private String cnorCode;

	/**
	 * 货名代码
	 */
	@Column(name = "CARGO_CODE")
	private String cargoCode;

	/**
	 * 包装
	 */
	@Column(name = "PACKAGE_CODE")
	private String packageCode;

	/**
	 * 规格
	 */
	@Column(name = "FORMAT")
	private String format;

	/**
	 * 车辆品牌代码
	 */
	@Column(name = "BRAND_CODE")
	private String brandCode;

	/**
	 * 收货人，集疏运报表用
	 */
	@Column(name = "CNEE_CODE")
	private String cneeCode;

	/**
	 * 标车数
	 */
	@Column(name = "STDCAR_NUM")
	private BigDecimal stdcarNum;

	/**
	 * 危险品货类（1、集装箱，2、液散、件杂）
	 */
	@Column(name = "DGR_TYPE")
	private String dgrType;

	/**
	 * 件数或teu，危险品日报用
	 */
	@Column(name = "CARGO_NUM")
	private BigDecimal cargoNum;

	/**
	 * 集团确认标志
	 */
	@Column(name = "CHECK_FLAG")
	private String checkFlag = "0";

	/**
	 * 集团确认人
	 */
	@Column(name = "CHECK_NAME")
	private String checkName;

	/**
	 * 集团确认时间
	 */
	@Column(name = "CHECK_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp checkTime;

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "STHRUPUT_ID")
	private String sthruputId;

	/**
	 * 公司组织id
	 */
	@Column(name = "DOCK_COD")
	private String dockCod;

	/**
	 * 吞吐量日期
	 */
	@Column(name = "STAT_DATE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Timestamp statDate;

	/**
	 * 船号
	 */
	@Column(name = "SHIP_NO")
	private String shipNo;

	/**
	 * 船名
	 */
	@Column(name = "SHIP_NAME")
	private String shipName;

	/**
	 * 实际完工时间
	 */
	@Column(name = "END_TIME")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp endTime;

	/**
	 * 内外贸
	 */
	@Column(name = "TRADE_ID")
	private String tradeId;

	/**
	 * 进出口标志
	 */
	@Column(name = "I_E_ID")
	private String iEId;

	/**
	 * 提单号
	 */
	@Column(name = "BILL_NO")
	private String billNo;

	/**
	 * 货单号
	 */
	@Column(name = "CARGO_BILL_NO")
	private String cargoBillNo;

	/**
	 * 派船方
	 */
	@Column(name = "SEND_SHIP_FLAG")
	private String sendShipFlag;

	/**
	 * 是否水转水
	 */

	/**
	 * 泊位号
	 */
	@Column(name = "BERTH_CODE")
	private String berthCode;

	/**
	 * 往来港代码
	 */
	@Column(name = "PORT_CODE")
	private String portCode;

	/**
	 * 船籍代码
	 */
	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	/**
	 * 船主代码，船舶性质（外轮、外贸国轮、海轮等）
	 */
	@Column(name = "SHIP_OWNER_COD")
	private String shipOwnerCod;
	@Column(name = "CONSIGN_COD")
	private String consignCod;
	/**
	 * 船公司代码
	 */
	@Column(name = "SHIP_CORP_CODE")
	private String shipCorpCode;

	/**
	 * 船型代码
	 */
	@Column(name = "SHIP_TYP_COD")
	private String shipTypCod;

	/**
	 * 航线代码
	 */
	@Column(name = "SHIP_LINE_CODE")
	private String shipLineCode;

	/**
	 * 净吨
	 */
	@Column(name = "SNT")
	private BigDecimal snt;

	/**
	 * 总载重量
	 */
	@Column(name = "SGT")
	private BigDecimal sgt;
	
	@Column(name = "PIECES_WGT")
	private BigDecimal piecesWGt;

	/**
	 * 出口指产地，外贸进口国家，用于集疏运报表
	 */
	@Column(name = "ORIGIN_PLACE_COD")
	private String originPlaceCod;

	/**
	 * 货物代理公司代码
	 */
	@Column(name = "CARGO_AGENT_COD")
	private String cargoAgentCod;

	/**
	 * 吞吐量货主代码，钢厂、货主等，进口为收货人，出口为发货人
	 */
	@Column(name = "SHIPPER_COD")
	private String shipperCod;

	/**
	 * 车数
	 */
	@Column(name = "CAR_NUM")
	private BigDecimal carNum;

	/**
	 * 重量
	 */
	@Column(name = "CARGO_WGT")
	private BigDecimal cargoWgt;

	/**
	 * 体积
	 */
	@Column(name = "CARGO_VOL")
	private BigDecimal cargoVol;

	/**
	 * 箱数
	 */
	@Column(name = "CNTR_NUM")
	private BigDecimal cntrNum;

	/**
	 * 标箱数
	 */
	@Column(name = "TEU_NUM")
	private BigDecimal teuNum;

	@Column(name = "THRUPUT_TYPE")
	private String thruputType;
	@Column(name = "VOYAGE")
	private String voyage;
	
	@Column(name = "PIECES")
	private BigDecimal pieces;
	
	
	
	/**
	 * 危险品种类
	 */
	@Transient
	private String dgrKindStr;
	@Transient
	private String dgrTypeStr;
	//抵口时间
	@Transient
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Timestamp conArrvTim;
	/**
	 * 发货人
	 */
	@Transient
	private String cnorCodeStr;

	/**
	 * 货名代码
	 */
	@Transient
	private String cargoCodeStr;

	/**
	 * 包装代码
	 */
	@Transient
	private String packageCodeStr;

	/**
	 * 车辆品牌代码
	 */
	@Transient
	private String brandCodeStr;

	/**
	 * 收货人代码
	 */
	@Transient
	private String cneeCodeStr;

	/**
	 * 公司组织
	 */
	@Transient
	private String dockCodStr;

	/**
	 * 内外贸
	 */
	@Transient
	private String tradeIdStr;

	/**
	 * 进出口
	 */
	@Transient
	private String iEIdStr;

	/**
	 * 派船方
	 */
	@Transient
	private String sentShipFlagStr;

	/**
	 * 泊位
	 */
	@Transient
	private String berthCodeStr;

	/**
	 * 往来港
	 */
	@Transient
	private String portCodeStr;

	/**
	 * 船籍代码
	 */
	@Transient
	private String countryCodeStr;

	/**
	 * 船主代码
	 */
	@Transient
	private String shipOwnerCodStr;

	/**
	 * 船公司代码
	 */
	@Transient
	private String shipCorpCodeStr;

	/**
	 * 船型代码
	 */
	@Transient
	private String shipTypCodStr;

	/**
	 * 航线代码
	 */
	@Transient
	private String shipLineCodeStr;

	/**
	 * 出口指产地，外贸进口国家，用于集疏运报表(原产地)
	 */
	@Transient
	private String originPlaceCodStr;

	/**
	 * 货物代理公司
	 */
	@Transient
	private String cargoAgentCodStr;

	/**
	 * 吞吐量货主代码
	 */
	@Transient
	private String shipperCodStr;

	@Transient
	private String consignCodStr;
	public String getSubmitFlag() {
		return this.submitFlag;
	}

	public void setSubmitFlag(String submitFlag) {
		this.submitFlag = submitFlag;
	}

	public String getSubmitName() {
		return this.submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public Timestamp getConArrvTim() {
		return conArrvTim;
	}

	public void setConArrvTim(Timestamp conArrvTim) {
		this.conArrvTim = conArrvTim;
	}

	public String getDgrKind() {
		return this.dgrKind;
	}

	public void setDgrKind(String dgrKind) {
		this.dgrKind = dgrKind;
	}

	public BigDecimal getDgrCargoWgt() {
		return this.dgrCargoWgt;
	}

	public void setDgrCargoWgt(BigDecimal dgrCargoWgt) {
		this.dgrCargoWgt = dgrCargoWgt;
	}

	public String getCnorCode() {
		return this.cnorCode;
	}

	public void setCnorCode(String cnorCode) {
		this.cnorCode = cnorCode;
	}

	public String getCargoCode() {
		return this.cargoCode;
	}

	public void setCargoCode(String cargoCode) {
		this.cargoCode = cargoCode;
	}

	public String getPackageCode() {
		return this.packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getCneeCode() {
		return this.cneeCode;
	}

	public void setCneeCode(String cneeCode) {
		this.cneeCode = cneeCode;
	}

	public BigDecimal getStdcarNum() {
		return this.stdcarNum;
	}

	public void setStdcarNum(BigDecimal stdcarNum) {
		this.stdcarNum = stdcarNum;
	}

	public String getDgrType() {
		return this.dgrType;
	}

	public void setDgrType(String dgrType) {
		this.dgrType = dgrType;
	}

	public BigDecimal getCargoNum() {
		return this.cargoNum;
	}

	public void setCargoNum(BigDecimal cargoNum) {
		this.cargoNum = cargoNum;
	}

	public String getCheckFlag() {
		return this.checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getCheckName() {
		return this.checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getSthruputId() {
		return this.sthruputId;
	}

	public void setSthruputId(String sthruputId) {
		this.sthruputId = sthruputId;
	}

	
	public String getDockCod() {
		return dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getiEId() {
		return iEId;
	}

	public void setiEId(String iEId) {
		this.iEId = iEId;
	}

	public Timestamp getStatDate() {
		return this.statDate;
	}

	public void setStatDate(Timestamp statDate) {
		this.statDate = statDate;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}


	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCargoBillNo() {
		return this.cargoBillNo;
	}

	public void setCargoBillNo(String cargoBillNo) {
		this.cargoBillNo = cargoBillNo;
	}

	public String getSendShipFlag() {
		return this.sendShipFlag;
	}

	public void setSendShipFlag(String sendShipFlag) {
		this.sendShipFlag = sendShipFlag;
	}

	public String getBerthCode() {
		return this.berthCode;
	}

	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}

	public String getPortCode() {
		return this.portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getShipOwnerCod() {
		return this.shipOwnerCod;
	}

	public void setShipOwnerCod(String shipOwnerCod) {
		this.shipOwnerCod = shipOwnerCod;
	}

	public String getShipCorpCode() {
		return this.shipCorpCode;
	}

	public void setShipCorpCode(String shipCorpCode) {
		this.shipCorpCode = shipCorpCode;
	}

	public String getShipTypCod() {
		return this.shipTypCod;
	}

	public void setShipTypCod(String shipTypCod) {
		this.shipTypCod = shipTypCod;
	}

	public String getShipLineCode() {
		return this.shipLineCode;
	}

	public void setShipLineCode(String shipLineCode) {
		this.shipLineCode = shipLineCode;
	}

	public BigDecimal getSnt() {
		return this.snt;
	}

	public void setSnt(BigDecimal snt) {
		this.snt = snt;
	}

	public BigDecimal getSgt() {
		return this.sgt;
	}

	public void setSgt(BigDecimal sgt) {
		this.sgt = sgt;
	}

	public String getOriginPlaceCod() {
		return this.originPlaceCod;
	}

	public void setOriginPlaceCod(String originPlaceCod) {
		this.originPlaceCod = originPlaceCod;
	}

	public String getCargoAgentCod() {
		return this.cargoAgentCod;
	}

	public void setCargoAgentCod(String cargoAgentCod) {
		this.cargoAgentCod = cargoAgentCod;
	}

	public String getShipperCod() {
		return this.shipperCod;
	}

	public void setShipperCod(String shipperCod) {
		this.shipperCod = shipperCod;
	}

	public BigDecimal getCarNum() {
		return this.carNum;
	}

	public void setCarNum(BigDecimal carNum) {
		this.carNum = carNum;
	}

	public BigDecimal getCargoWgt() {
		return this.cargoWgt;
	}

	public void setCargoWgt(BigDecimal cargoWgt) {
		this.cargoWgt = cargoWgt;
	}

	public BigDecimal getCargoVol() {
		return this.cargoVol;
	}

	public void setCargoVol(BigDecimal cargoVol) {
		this.cargoVol = cargoVol;
	}

	public BigDecimal getCntrNum() {
		return this.cntrNum;
	}

	public void setCntrNum(BigDecimal cntrNum) {
		this.cntrNum = cntrNum;
	}

	public BigDecimal getTeuNum() {
		return this.teuNum;
	}

	public void setTeuNum(BigDecimal teuNum) {
		this.teuNum = teuNum;
	}

	public String getDgrKindStr() {
		return HdUtils.getSysCodeName("DANGER_KIND", getDgrKind());
	}

	public void setDgrKindStr(String dgrKindStr) {
		this.dgrKindStr = dgrKindStr;
	}

	public String getDgrTypeStr() {
		return HdUtils.getSysCodeName("DGR_TYPE", getDgrType());
	}

	public void setDgrTypeStr(String dgrTypeStr) {
		this.dgrTypeStr = dgrTypeStr;
	}

	public String getCnorCodeStr() {
		return cnorCodeStr;
	}

	public void setCnorCodeStr(String cnorCodeStr) {
		this.cnorCodeStr = cnorCodeStr;
	}

	public String getCargoCodeStr() {
		return cargoCodeStr;
	}

	public void setCargoCodeStr(String cargoCodeStr) {
		this.cargoCodeStr = cargoCodeStr;
	}

	public String getPackageCodeStr() {
		return packageCodeStr;
	}

	public void setPackageCodeStr(String packageCodeStr) {
		this.packageCodeStr = packageCodeStr;
	}

	public String getBrandCodeStr() {
		return brandCodeStr;
	}

	public void setBrandCodeStr(String brandCodeStr) {
		this.brandCodeStr = brandCodeStr;
	}

	public String getCneeCodeStr() {
		return cneeCodeStr;
	}

	public void setCneeCodeStr(String cneeCodeStr) {
		this.cneeCodeStr = cneeCodeStr;
	}

	
	public String getDockCodStr() {
		return dockCodStr;
	}

	public void setDockCodStr(String dockCodStr) {
		this.dockCodStr = dockCodStr;
	}

	public String getTradeIdStr() {
		return tradeIdStr;
	}

	public void setTradeIdStr(String tradeIdStr) {
		this.tradeIdStr = tradeIdStr;
	}

	public String getiEIdStr() {
		return iEIdStr;
	}

	public void setiEIdStr(String iEIdStr) {
		this.iEIdStr = iEIdStr;
	}

	public String getSentShipFlagStr() {
		return sentShipFlagStr;
	}

	public void setSentShipFlagStr(String sentShipFlagStr) {
		this.sentShipFlagStr = sentShipFlagStr;
	}

	public String getBerthCodeStr() {
		return berthCodeStr;
	}

	public void setBerthCodeStr(String berthCodeStr) {
		this.berthCodeStr = berthCodeStr;
	}

	public String getPortCodeStr() {
		return portCodeStr;
	}

	public void setPortCodeStr(String portCodeStr) {
		this.portCodeStr = portCodeStr;
	}

	public String getCountryCodeStr() {
		return countryCodeStr;
	}

	public void setCountryCodeStr(String countryCodeStr) {
		this.countryCodeStr = countryCodeStr;
	}

	public String getShipOwnerCodStr() {
		return shipOwnerCodStr;
	}

	public void setShipOwnerCodStr(String shipOwnerCodStr) {
		this.shipOwnerCodStr = shipOwnerCodStr;
	}

	public String getShipCorpCodeStr() {
		return shipCorpCodeStr;
	}

	public void setShipCorpCodeStr(String shipCorpCodeStr) {
		this.shipCorpCodeStr = shipCorpCodeStr;
	}

	public String getShipTypCodStr() {
		return HdUtils.getSysCodeName("SHIP_TYPE", getShipTypCod());
	}

	public void setShipTypCodStr(String shipTypCodStr) {
		this.shipTypCodStr = shipTypCodStr;
	}

	public String getShipLineCodeStr() {
		return shipLineCodeStr;
	}

	public void setShipLineCodeStr(String shipLineCodeStr) {
		this.shipLineCodeStr = shipLineCodeStr;
	}

	public String getOriginPlaceCodStr() {
		return originPlaceCodStr;
	}

	public void setOriginPlaceCodStr(String originPlaceCodStr) {
		this.originPlaceCodStr = originPlaceCodStr;
	}

	public String getCargoAgentCodStr() {
		return cargoAgentCodStr;
	}

	public void setCargoAgentCodStr(String cargoAgentCodStr) {
		this.cargoAgentCodStr = cargoAgentCodStr;
	}

	public String getShipperCodStr() {
		return shipperCodStr;
	}

	public void setShipperCodStr(String shipperCodStr) {
		this.shipperCodStr = shipperCodStr;
	}

	public String getConsignCod() {
		return consignCod;
	}

	public void setConsignCod(String consignCod) {
		this.consignCod = consignCod;
	}

	public String getConsignCodStr() {
		return consignCodStr;
	}

	public void setConsignCodStr(String consignCodStr) {
		this.consignCodStr = consignCodStr;
	}

	public String getThruputType() {
		return thruputType;
	}

	public void setThruputType(String thruputType) {
		this.thruputType = thruputType;
	}


	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public BigDecimal getPieces() {
		return pieces;
	}

	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}

	public BigDecimal getPiecesWGt() {
		return piecesWGt;
	}

	public void setPiecesWGt(BigDecimal piecesWGt) {
		this.piecesWGt = piecesWGt;
	}

	
	
}
