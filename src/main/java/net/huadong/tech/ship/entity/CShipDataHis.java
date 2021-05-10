package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_SHIP_DATA database table.
 * 
 */
@Entity
@Table(name="C_SHIP_DATA_HIS")
@NamedQuery(name="CShipDataHis.findAll", query="SELECT c FROM CShipDataHis c")
public class CShipDataHis extends AuditEntityBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="SHIP_COD_ID")
	private String shipCodId;

	@Column(name="BACK_BOARD_BEARING")
	private BigDecimal backBoardBearing;

	@Column(name="BACK_BOARD_ID")
	private String backBoardId;

	@Column(name="BACK_BOARD_LENGTH")
	private BigDecimal backBoardLength;

	@Column(name="BACK_BOARD_WIDTH")
	private BigDecimal backBoardWidth;

	@Column(name="BACK_BOTTOM_DISTANCE")
	private BigDecimal backBottomDistance;

	@Column(name="BACK_DECK_NO")
	private BigDecimal backDeckNo;

	@Column(name="BACK_HATCH_HIGHT")
	private BigDecimal backHatchHight;

	@Column(name="BACK_HATCH_WIDTH")
	private BigDecimal backHatchWidth;

	@Column(name="BUILD_DTE")
	private String buildDte;

	@Column(name="C_SHIP_NAM")
	private String cShipNam;

	@Column(name="CAB_WIDTH")
	private BigDecimal cabWidth;

	@Column(name="COUNTRY_COD")
	private String countryCod;

	@Column(name="DECK_NUM")
	private BigDecimal deckNum;

	@Column(name="E_SHIP_NAM")
	private String eShipNam;

	@Column(name="EMPTY_DRAFT_BACK")
	private BigDecimal emptyDraftBack;

	@Column(name="EMPTY_DRAFT_FRONT")
	private BigDecimal emptyDraftFront;

	@Column(name="FRONT_BOARD_BEARING")
	private BigDecimal frontBoardBearing;

	@Column(name="FRONT_BOARD_ID")
	private String frontBoardId;

	@Column(name="FRONT_BOARD_LENGTH")
	private BigDecimal frontBoardLength;

	@Column(name="FRONT_BOARD_WIDTH")
	private BigDecimal frontBoardWidth;

	@Column(name="FRONT_BOTTOM_DISTANCE")
	private BigDecimal frontBottomDistance;

	@Column(name="FRONT_DECK_NO")
	private BigDecimal frontDeckNo;

	@Column(name="FRONT_HATCH_HIGHT")
	private BigDecimal frontHatchHight;

	@Column(name="FRONT_HATCH_WIDTH")
	private BigDecimal frontHatchWidth;

	@Column(name="FULL_DRAFT_BACK")
	private BigDecimal fullDraftBack;

	@Column(name="FULL_DRAFT_FRONT")
	private BigDecimal fullDraftFront;

	@Column(name="LAST_PORT_COD")
	private String lastPortCod;

	@Column(name="LCG_DIR")
	private String lcgDir;

	@Column(name="LCG_ID")
	private String lcgId;

	@Column(name="LCG_REF_PT")
	private String lcgRefPt;

	@Column(name="LIFT_DESCRIPTION")
	private String liftDescription;

	@Column(name="LIFT_ID")
	private String liftId;

	@Column(name="LINER_ID")
	private String linerId;

	@Column(name="MIDDLE_BOARD_BEARING")
	private BigDecimal middleBoardBearing;

	@Column(name="MIDDLE_BOARD_ID")
	private String middleBoardId;

	@Column(name="MIDDLE_BOARD_LENGTH")
	private BigDecimal middleBoardLength;

	@Column(name="MIDDLE_BOARD_WIDTH")
	private BigDecimal middleBoardWidth;

	@Column(name="MIDDLE_BOTTOM_DISTANCE")
	private BigDecimal middleBottomDistance;

	@Column(name="MIDDLE_DECK_NO")
	private BigDecimal middleDeckNo;

	@Column(name="MIDDLE_HATCH_HIGHT")
	private BigDecimal middleHatchHight;

	@Column(name="MIDDLE_HATCH_WIDTH")
	private BigDecimal middleHatchWidth;

	@Column(name="MMSI_SHIP_COD")
	private String mmsiShipCod;

	@Column(name="NEXT_PORT_COD")
	private String nextPortCod;

	private String ownercode;

	@Column(name="PUSH_ID")
	private String pushId;

	@Column(name="PUSH_POWER")
	private String pushPower;


	@Column(name="SHIP_AGENT_COD")
	private String shipAgentCod;

	@Column(name="SHIP_CALL")
	private String shipCall;

	@Column(name="SHIP_COD")
	private String shipCod;

	@Column(name="SHIP_CORP_COD")
	private String shipCorpCod;

	@Column(name="SHIP_DEAD_WT")
	private BigDecimal shipDeadWt;

	@Column(name="SHIP_GROSS_WGT")
	private BigDecimal shipGrossWgt;

	@Column(name="SHIP_HIGH")
	private BigDecimal shipHigh;

	@Column(name="SHIP_IMO")
	private String shipImo;

	@Column(name="SHIP_LCG")
	private BigDecimal shipLcg;
	
	@Column(name="SHIP_PROPERTY")
	private String shipProperty;

	@Column(name="SHIP_LINE_COD")
	private String shipLineCod;

	@Column(name="SHIP_LONG_NUM")
	private BigDecimal shipLongNum;

	@Column(name="SHIP_NET_WGT")
	private BigDecimal shipNetWgt;

	@Column(name="SHIP_SHORT")
	private String shipShort;

	@Column(name="SHIP_SPEED")
	private BigDecimal shipSpeed;

	@Column(name="SHIP_TCG")
	private BigDecimal shipTcg;

	@Column(name="SHIP_TYP")
	private String shipTyp;

	@Column(name="SHIP_VCG")
	private BigDecimal shipVcg;

	@Column(name="SHIP_WIDTH_NUM")
	private BigDecimal shipWidthNum;

	@Column(name="STOW_REQU")
	private String stowRequ;

	@Column(name="TCG_DIR")
	private String tcgDir;

	@Column(name="TCG_ID")
	private String tcgId;

	@Column(name="TOTAL_CAPACITY")
	private BigDecimal totalCapacity;

	@Column(name="TRADE_ID")
	private String tradeId;

	@Column(name="TYPE_DEEP")
	private BigDecimal typeDeep;

	@Column(name="VCG_ID")
	private String vcgId;

	@Column(name="WORK_REQU")
	private String workRequ;
	
	@Transient
	private String countryCodNam;
	
	@Transient
	private String shipCorpCodNam;
	


	public String getBuildDte() {
		return buildDte;
	}

	public void setBuildDte(String buildDte) {
		this.buildDte = buildDte;
	}

	public String getShipCorpCodNam() {
		return shipCorpCodNam;
	}

	public void setShipCorpCodNam(String shipCorpCodNam) {
		this.shipCorpCodNam = shipCorpCodNam;
	}

	public String getShipProperty() {
		return shipProperty;
	}

	public void setShipProperty(String shipProperty) {
		this.shipProperty = shipProperty;
	}

	public String getCountryCodNam() {
		return countryCodNam;
	}

	public void setCountryCodNam(String countryCodNam) {
		this.countryCodNam = countryCodNam;
	}

	public CShipDataHis() {
	}

	public String getShipCodId() {
		return this.shipCodId;
	}

	public void setShipCodId(String shipCodId) {
		this.shipCodId = shipCodId;
	}

	public BigDecimal getBackBoardBearing() {
		return this.backBoardBearing;
	}

	public void setBackBoardBearing(BigDecimal backBoardBearing) {
		this.backBoardBearing = backBoardBearing;
	}

	public String getBackBoardId() {
		return this.backBoardId;
	}

	public void setBackBoardId(String backBoardId) {
		this.backBoardId = backBoardId;
	}

	public BigDecimal getBackBoardLength() {
		return this.backBoardLength;
	}

	public void setBackBoardLength(BigDecimal backBoardLength) {
		this.backBoardLength = backBoardLength;
	}

	public BigDecimal getBackBoardWidth() {
		return this.backBoardWidth;
	}

	public void setBackBoardWidth(BigDecimal backBoardWidth) {
		this.backBoardWidth = backBoardWidth;
	}

	public BigDecimal getBackBottomDistance() {
		return this.backBottomDistance;
	}

	public void setBackBottomDistance(BigDecimal backBottomDistance) {
		this.backBottomDistance = backBottomDistance;
	}

	public BigDecimal getBackDeckNo() {
		return this.backDeckNo;
	}

	public void setBackDeckNo(BigDecimal backDeckNo) {
		this.backDeckNo = backDeckNo;
	}

	public BigDecimal getBackHatchHight() {
		return this.backHatchHight;
	}

	public void setBackHatchHight(BigDecimal backHatchHight) {
		this.backHatchHight = backHatchHight;
	}

	public BigDecimal getBackHatchWidth() {
		return this.backHatchWidth;
	}

	public void setBackHatchWidth(BigDecimal backHatchWidth) {
		this.backHatchWidth = backHatchWidth;
	}



	public String getcShipNam() {
		return this.cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}

	public BigDecimal getCabWidth() {
		return this.cabWidth;
	}

	public void setCabWidth(BigDecimal cabWidth) {
		this.cabWidth = cabWidth;
	}

	public String getCountryCod() {
		return this.countryCod;
	}

	public void setCountryCod(String countryCod) {
		this.countryCod = countryCod;
	}

	public BigDecimal getDeckNum() {
		return this.deckNum;
	}

	public void setDeckNum(BigDecimal deckNum) {
		this.deckNum = deckNum;
	}

	public String geteShipNam() {
		return this.eShipNam;
	}

	public void seteShipNam(String eShipNam) {
		this.eShipNam = eShipNam;
	}


	public BigDecimal getEmptyDraftBack() {
		return emptyDraftBack;
	}

	public void setEmptyDraftBack(BigDecimal emptyDraftBack) {
		this.emptyDraftBack = emptyDraftBack;
	}

	public BigDecimal getEmptyDraftFront() {
		return this.emptyDraftFront;
	}

	public void setEmptyDraftFront(BigDecimal emptyDraftFront) {
		this.emptyDraftFront = emptyDraftFront;
	}

	public BigDecimal getFrontBoardBearing() {
		return this.frontBoardBearing;
	}

	public void setFrontBoardBearing(BigDecimal frontBoardBearing) {
		this.frontBoardBearing = frontBoardBearing;
	}

	public String getFrontBoardId() {
		return this.frontBoardId;
	}

	public void setFrontBoardId(String frontBoardId) {
		this.frontBoardId = frontBoardId;
	}

	public BigDecimal getFrontBoardLength() {
		return this.frontBoardLength;
	}

	public void setFrontBoardLength(BigDecimal frontBoardLength) {
		this.frontBoardLength = frontBoardLength;
	}

	public BigDecimal getFrontBoardWidth() {
		return this.frontBoardWidth;
	}

	public void setFrontBoardWidth(BigDecimal frontBoardWidth) {
		this.frontBoardWidth = frontBoardWidth;
	}

	public BigDecimal getFrontBottomDistance() {
		return this.frontBottomDistance;
	}

	public void setFrontBottomDistance(BigDecimal frontBottomDistance) {
		this.frontBottomDistance = frontBottomDistance;
	}

	public BigDecimal getFrontDeckNo() {
		return this.frontDeckNo;
	}

	public void setFrontDeckNo(BigDecimal frontDeckNo) {
		this.frontDeckNo = frontDeckNo;
	}

	public BigDecimal getFrontHatchHight() {
		return this.frontHatchHight;
	}

	public void setFrontHatchHight(BigDecimal frontHatchHight) {
		this.frontHatchHight = frontHatchHight;
	}

	public BigDecimal getFrontHatchWidth() {
		return this.frontHatchWidth;
	}

	public void setFrontHatchWidth(BigDecimal frontHatchWidth) {
		this.frontHatchWidth = frontHatchWidth;
	}


	public BigDecimal getFullDraftBack() {
		return fullDraftBack;
	}

	public void setFullDraftBack(BigDecimal fullDraftBack) {
		this.fullDraftBack = fullDraftBack;
	}

	public BigDecimal getFullDraftFront() {
		return this.fullDraftFront;
	}

	public void setFullDraftFront(BigDecimal fullDraftFront) {
		this.fullDraftFront = fullDraftFront;
	}

	public String getLastPortCod() {
		return this.lastPortCod;
	}

	public void setLastPortCod(String lastPortCod) {
		this.lastPortCod = lastPortCod;
	}

	public String getLcgDir() {
		return this.lcgDir;
	}

	public void setLcgDir(String lcgDir) {
		this.lcgDir = lcgDir;
	}

	public String getLcgId() {
		return this.lcgId;
	}

	public void setLcgId(String lcgId) {
		this.lcgId = lcgId;
	}

	public String getLcgRefPt() {
		return this.lcgRefPt;
	}

	public void setLcgRefPt(String lcgRefPt) {
		this.lcgRefPt = lcgRefPt;
	}

	public String getLiftDescription() {
		return this.liftDescription;
	}

	public void setLiftDescription(String liftDescription) {
		this.liftDescription = liftDescription;
	}

	public String getLiftId() {
		return this.liftId;
	}

	public void setLiftId(String liftId) {
		this.liftId = liftId;
	}

	public String getLinerId() {
		return this.linerId;
	}

	public void setLinerId(String linerId) {
		this.linerId = linerId;
	}

	public BigDecimal getMiddleBoardBearing() {
		return this.middleBoardBearing;
	}

	public void setMiddleBoardBearing(BigDecimal middleBoardBearing) {
		this.middleBoardBearing = middleBoardBearing;
	}

	public String getMiddleBoardId() {
		return this.middleBoardId;
	}

	public void setMiddleBoardId(String middleBoardId) {
		this.middleBoardId = middleBoardId;
	}

	public BigDecimal getMiddleBoardLength() {
		return this.middleBoardLength;
	}

	public void setMiddleBoardLength(BigDecimal middleBoardLength) {
		this.middleBoardLength = middleBoardLength;
	}

	public BigDecimal getMiddleBoardWidth() {
		return this.middleBoardWidth;
	}

	public void setMiddleBoardWidth(BigDecimal middleBoardWidth) {
		this.middleBoardWidth = middleBoardWidth;
	}

	public BigDecimal getMiddleBottomDistance() {
		return this.middleBottomDistance;
	}

	public void setMiddleBottomDistance(BigDecimal middleBottomDistance) {
		this.middleBottomDistance = middleBottomDistance;
	}

	public BigDecimal getMiddleDeckNo() {
		return this.middleDeckNo;
	}

	public void setMiddleDeckNo(BigDecimal middleDeckNo) {
		this.middleDeckNo = middleDeckNo;
	}

	public BigDecimal getMiddleHatchHight() {
		return this.middleHatchHight;
	}

	public void setMiddleHatchHight(BigDecimal middleHatchHight) {
		this.middleHatchHight = middleHatchHight;
	}

	public BigDecimal getMiddleHatchWidth() {
		return this.middleHatchWidth;
	}

	public void setMiddleHatchWidth(BigDecimal middleHatchWidth) {
		this.middleHatchWidth = middleHatchWidth;
	}

	public String getMmsiShipCod() {
		return this.mmsiShipCod;
	}

	public void setMmsiShipCod(String mmsiShipCod) {
		this.mmsiShipCod = mmsiShipCod;
	}

	public String getNextPortCod() {
		return this.nextPortCod;
	}

	public void setNextPortCod(String nextPortCod) {
		this.nextPortCod = nextPortCod;
	}

	public String getOwnercode() {
		return this.ownercode;
	}

	public void setOwnercode(String ownercode) {
		this.ownercode = ownercode;
	}

	public String getPushId() {
		return this.pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getPushPower() {
		return this.pushPower;
	}

	public void setPushPower(String pushPower) {
		this.pushPower = pushPower;
	}


	public String getShipAgentCod() {
		return this.shipAgentCod;
	}

	public void setShipAgentCod(String shipAgentCod) {
		this.shipAgentCod = shipAgentCod;
	}

	public String getShipCall() {
		return this.shipCall;
	}

	public void setShipCall(String shipCall) {
		this.shipCall = shipCall;
	}

	public String getShipCod() {
		return this.shipCod;
	}

	public void setShipCod(String shipCod) {
		this.shipCod = shipCod;
	}

	public String getShipCorpCod() {
		return this.shipCorpCod;
	}

	public void setShipCorpCod(String shipCorpCod) {
		this.shipCorpCod = shipCorpCod;
	}

	public BigDecimal getShipDeadWt() {
		return this.shipDeadWt;
	}

	public void setShipDeadWt(BigDecimal shipDeadWt) {
		this.shipDeadWt = shipDeadWt;
	}

	public BigDecimal getShipGrossWgt() {
		return this.shipGrossWgt;
	}

	public void setShipGrossWgt(BigDecimal shipGrossWgt) {
		this.shipGrossWgt = shipGrossWgt;
	}

	public BigDecimal getShipHigh() {
		return this.shipHigh;
	}

	public void setShipHigh(BigDecimal shipHigh) {
		this.shipHigh = shipHigh;
	}

	public String getShipImo() {
		return this.shipImo;
	}

	public void setShipImo(String shipImo) {
		this.shipImo = shipImo;
	}

	public BigDecimal getShipLcg() {
		return this.shipLcg;
	}

	public void setShipLcg(BigDecimal shipLcg) {
		this.shipLcg = shipLcg;
	}

	public String getShipLineCod() {
		return this.shipLineCod;
	}

	public void setShipLineCod(String shipLineCod) {
		this.shipLineCod = shipLineCod;
	}

	public BigDecimal getShipLongNum() {
		return this.shipLongNum;
	}

	public void setShipLongNum(BigDecimal shipLongNum) {
		this.shipLongNum = shipLongNum;
	}

	public BigDecimal getShipNetWgt() {
		return this.shipNetWgt;
	}

	public void setShipNetWgt(BigDecimal shipNetWgt) {
		this.shipNetWgt = shipNetWgt;
	}

	public String getShipShort() {
		return this.shipShort;
	}

	public void setShipShort(String shipShort) {
		this.shipShort = shipShort;
	}

	public BigDecimal getShipSpeed() {
		return this.shipSpeed;
	}

	public void setShipSpeed(BigDecimal shipSpeed) {
		this.shipSpeed = shipSpeed;
	}

	public BigDecimal getShipTcg() {
		return this.shipTcg;
	}

	public void setShipTcg(BigDecimal shipTcg) {
		this.shipTcg = shipTcg;
	}

	public String getShipTyp() {
		return this.shipTyp;
	}

	public void setShipTyp(String shipTyp) {
		this.shipTyp = shipTyp;
	}

	public BigDecimal getShipVcg() {
		return this.shipVcg;
	}

	public void setShipVcg(BigDecimal shipVcg) {
		this.shipVcg = shipVcg;
	}

	public BigDecimal getShipWidthNum() {
		return this.shipWidthNum;
	}

	public void setShipWidthNum(BigDecimal shipWidthNum) {
		this.shipWidthNum = shipWidthNum;
	}

	public String getStowRequ() {
		return this.stowRequ;
	}

	public void setStowRequ(String stowRequ) {
		this.stowRequ = stowRequ;
	}

	public String getTcgDir() {
		return this.tcgDir;
	}

	public void setTcgDir(String tcgDir) {
		this.tcgDir = tcgDir;
	}

	public String getTcgId() {
		return this.tcgId;
	}

	public void setTcgId(String tcgId) {
		this.tcgId = tcgId;
	}

	public BigDecimal getTotalCapacity() {
		return this.totalCapacity;
	}

	public void setTotalCapacity(BigDecimal totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public BigDecimal getTypeDeep() {
		return this.typeDeep;
	}

	public void setTypeDeep(BigDecimal typeDeep) {
		this.typeDeep = typeDeep;
	}


	public String getVcgId() {
		return this.vcgId;
	}

	public void setVcgId(String vcgId) {
		this.vcgId = vcgId;
	}

	public String getWorkRequ() {
		return this.workRequ;
	}

	public void setWorkRequ(String workRequ) {
		this.workRequ = workRequ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}