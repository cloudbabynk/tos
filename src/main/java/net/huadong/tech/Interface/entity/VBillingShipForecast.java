package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_BILLING_SHIP_FORECAST database table.
 * 
 */
@Entity
@Table(name="V_BILLING_SHIP_FORECAST")
@NamedQuery(name="VBillingShipForecast.findAll", query="SELECT v FROM VBillingShipForecast v")
public class VBillingShipForecast implements Serializable {
	private static final long serialVersionUID = 1L;

	private String agent;

	@Column(name="CHECK_IN_LOAD_FLAG")
	private String checkInLoadFlag;

	@Column(name="CHECK_IN_SHIP_FLAG")
	private String checkInShipFlag;

	@Column(name="CHECK_OUT_LOAD_FLAG")
	private String checkOutLoadFlag;

	@Column(name="CHECK_OUT_SHIP_FLAG")
	private String checkOutShipFlag;

	@Column(name="CODE_CORP_DOCK")
	private String codeCorpDock;

	@Column(name="CONFIRM_VSS_ID")
	private String confirmVssId;

	@Column(name="CUSTOM_VESSEL_CODE")
	private String customVesselCode;

	@Column(name="FLAG_APPLY")
	private String flagApply;

	@Column(name="FLAG_APPLY_IN")
	private String flagApplyIn;

	@Column(name="FLAG_APPLY_OUT")
	private String flagApplyOut;

	@Column(name="FLAG_CHARGE")
	private String flagCharge;

	@Column(name="FLAG_CHARGE_IN")
	private String flagChargeIn;

	@Column(name="FLAG_CHARGE_OUT")
	private String flagChargeOut;

	@Column(name="FLAG_IN_APPLY_LOAD")
	private String flagInApplyLoad;

	@Column(name="FLAG_IN_APPLY_SHIP")
	private String flagInApplyShip;

	@Column(name="FLAG_IN_CHARGE_LOAD")
	private String flagInChargeLoad;

	@Column(name="FLAG_IN_CHARGE_SHIP")
	private String flagInChargeShip;

	@Column(name="FLAG_OUT_APPLY_LOAD")
	private String flagOutApplyLoad;

	@Column(name="FLAG_OUT_APPLY_SHIP")
	private String flagOutApplyShip;

	@Column(name="FLAG_OUT_CHARGE_LOAD")
	private String flagOutChargeLoad;

	@Column(name="FLAG_OUT_CHARGE_SHIP")
	private String flagOutChargeShip;

	@Id
	private String id;

	@Column(name="IN_FLAG")
	private byte[] inFlag;

	@Column(name="IN_GOODS_INFO")
	private byte[] inGoodsInfo;

	@Column(name="IN_GOODS_NAME")
	private String inGoodsName;

	@Column(name="IN_LINE")
	private String inLine;

	@Column(name="IN_NUM")
	private BigDecimal inNum;

	@Column(name="IN_WEIGHT")
	private BigDecimal inWeight;

	@Column(name="IS_KEY_SERVICE")
	private String isKeyService;

	@Temporal(TemporalType.DATE)
	@Column(name="OP_TIME")
	private Date opTime;

	@Column(name="OPERATE_MAN")
	private String operateMan;

	@Column(name="OUT_FLAG")
	private byte[] outFlag;

	@Column(name="OUT_GOODS_INFO")
	private byte[] outGoodsInfo;

	@Column(name="OUT_GOODS_NAM")
	private String outGoodsNam;

	@Column(name="OUT_LINE")
	private String outLine;

	@Column(name="OUT_NUM")
	private BigDecimal outNum;

	@Column(name="OUT_WEIGHT")
	private BigDecimal outWeight;

	@Temporal(TemporalType.DATE)
	@Column(name="PRE_IN_TIME")
	private Date preInTime;

	@Temporal(TemporalType.DATE)
	@Column(name="PRE_OFF_TIME")
	private Date preOffTime;

	@Temporal(TemporalType.DATE)
	@Column(name="PRE_UP_TIME")
	private Date preUpTime;

	private String remark;

	@Column(name="SHIP_CORP")
	private String shipCorp;

	private String tel;

	@Column(name="VOY_IN")
	private String voyIn;

	@Column(name="VOY_OUT")
	private String voyOut;

	@Column(name="VSS_CODE")
	private String vssCode;

	@Column(name="VSS_COMPLETE_DEPT")
	private String vssCompleteDept;

	@Column(name="VSS_COMPLETE_FLAG")
	private String vssCompleteFlag;

	@Column(name="VSS_COMPLETE_MAN")
	private String vssCompleteMan;

	@Column(name="VSS_COMPLETE_REMARK")
	private String vssCompleteRemark;

	@Temporal(TemporalType.DATE)
	@Column(name="VSS_COMPLETE_TIME")
	private Date vssCompleteTime;

	@Column(name="VSS_ENAME")
	private String vssEname;

	@Temporal(TemporalType.DATE)
	@Column(name="VSS_IN_COMPLETE_TIME")
	private Date vssInCompleteTime;

	@Column(name="VSS_INFO")
	private String vssInfo;

	@Column(name="VSS_NAME")
	private byte[] vssName;

	@Column(name="VSS_NO")
	private String vssNo;

	@Temporal(TemporalType.DATE)
	@Column(name="VSS_OUT_COMPLETE_TIME")
	private Date vssOutCompleteTime;

	public VBillingShipForecast() {
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCheckInLoadFlag() {
		return this.checkInLoadFlag;
	}

	public void setCheckInLoadFlag(String checkInLoadFlag) {
		this.checkInLoadFlag = checkInLoadFlag;
	}

	public String getCheckInShipFlag() {
		return this.checkInShipFlag;
	}

	public void setCheckInShipFlag(String checkInShipFlag) {
		this.checkInShipFlag = checkInShipFlag;
	}

	public String getCheckOutLoadFlag() {
		return this.checkOutLoadFlag;
	}

	public void setCheckOutLoadFlag(String checkOutLoadFlag) {
		this.checkOutLoadFlag = checkOutLoadFlag;
	}

	public String getCheckOutShipFlag() {
		return this.checkOutShipFlag;
	}

	public void setCheckOutShipFlag(String checkOutShipFlag) {
		this.checkOutShipFlag = checkOutShipFlag;
	}

	public String getCodeCorpDock() {
		return this.codeCorpDock;
	}

	public void setCodeCorpDock(String codeCorpDock) {
		this.codeCorpDock = codeCorpDock;
	}

	public String getConfirmVssId() {
		return this.confirmVssId;
	}

	public void setConfirmVssId(String confirmVssId) {
		this.confirmVssId = confirmVssId;
	}

	public String getCustomVesselCode() {
		return this.customVesselCode;
	}

	public void setCustomVesselCode(String customVesselCode) {
		this.customVesselCode = customVesselCode;
	}

	public String getFlagApply() {
		return this.flagApply;
	}

	public void setFlagApply(String flagApply) {
		this.flagApply = flagApply;
	}

	public String getFlagApplyIn() {
		return this.flagApplyIn;
	}

	public void setFlagApplyIn(String flagApplyIn) {
		this.flagApplyIn = flagApplyIn;
	}

	public String getFlagApplyOut() {
		return this.flagApplyOut;
	}

	public void setFlagApplyOut(String flagApplyOut) {
		this.flagApplyOut = flagApplyOut;
	}

	public String getFlagCharge() {
		return this.flagCharge;
	}

	public void setFlagCharge(String flagCharge) {
		this.flagCharge = flagCharge;
	}

	public String getFlagChargeIn() {
		return this.flagChargeIn;
	}

	public void setFlagChargeIn(String flagChargeIn) {
		this.flagChargeIn = flagChargeIn;
	}

	public String getFlagChargeOut() {
		return this.flagChargeOut;
	}

	public void setFlagChargeOut(String flagChargeOut) {
		this.flagChargeOut = flagChargeOut;
	}

	public String getFlagInApplyLoad() {
		return this.flagInApplyLoad;
	}

	public void setFlagInApplyLoad(String flagInApplyLoad) {
		this.flagInApplyLoad = flagInApplyLoad;
	}

	public String getFlagInApplyShip() {
		return this.flagInApplyShip;
	}

	public void setFlagInApplyShip(String flagInApplyShip) {
		this.flagInApplyShip = flagInApplyShip;
	}

	public String getFlagInChargeLoad() {
		return this.flagInChargeLoad;
	}

	public void setFlagInChargeLoad(String flagInChargeLoad) {
		this.flagInChargeLoad = flagInChargeLoad;
	}

	public String getFlagInChargeShip() {
		return this.flagInChargeShip;
	}

	public void setFlagInChargeShip(String flagInChargeShip) {
		this.flagInChargeShip = flagInChargeShip;
	}

	public String getFlagOutApplyLoad() {
		return this.flagOutApplyLoad;
	}

	public void setFlagOutApplyLoad(String flagOutApplyLoad) {
		this.flagOutApplyLoad = flagOutApplyLoad;
	}

	public String getFlagOutApplyShip() {
		return this.flagOutApplyShip;
	}

	public void setFlagOutApplyShip(String flagOutApplyShip) {
		this.flagOutApplyShip = flagOutApplyShip;
	}

	public String getFlagOutChargeLoad() {
		return this.flagOutChargeLoad;
	}

	public void setFlagOutChargeLoad(String flagOutChargeLoad) {
		this.flagOutChargeLoad = flagOutChargeLoad;
	}

	public String getFlagOutChargeShip() {
		return this.flagOutChargeShip;
	}

	public void setFlagOutChargeShip(String flagOutChargeShip) {
		this.flagOutChargeShip = flagOutChargeShip;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getInFlag() {
		return this.inFlag;
	}

	public void setInFlag(byte[] inFlag) {
		this.inFlag = inFlag;
	}

	public byte[] getInGoodsInfo() {
		return this.inGoodsInfo;
	}

	public void setInGoodsInfo(byte[] inGoodsInfo) {
		this.inGoodsInfo = inGoodsInfo;
	}

	public String getInGoodsName() {
		return this.inGoodsName;
	}

	public void setInGoodsName(String inGoodsName) {
		this.inGoodsName = inGoodsName;
	}

	public String getInLine() {
		return this.inLine;
	}

	public void setInLine(String inLine) {
		this.inLine = inLine;
	}

	public BigDecimal getInNum() {
		return this.inNum;
	}

	public void setInNum(BigDecimal inNum) {
		this.inNum = inNum;
	}

	public BigDecimal getInWeight() {
		return this.inWeight;
	}

	public void setInWeight(BigDecimal inWeight) {
		this.inWeight = inWeight;
	}

	public String getIsKeyService() {
		return this.isKeyService;
	}

	public void setIsKeyService(String isKeyService) {
		this.isKeyService = isKeyService;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getOperateMan() {
		return this.operateMan;
	}

	public void setOperateMan(String operateMan) {
		this.operateMan = operateMan;
	}

	public byte[] getOutFlag() {
		return this.outFlag;
	}

	public void setOutFlag(byte[] outFlag) {
		this.outFlag = outFlag;
	}

	public byte[] getOutGoodsInfo() {
		return this.outGoodsInfo;
	}

	public void setOutGoodsInfo(byte[] outGoodsInfo) {
		this.outGoodsInfo = outGoodsInfo;
	}

	public String getOutGoodsNam() {
		return this.outGoodsNam;
	}

	public void setOutGoodsNam(String outGoodsNam) {
		this.outGoodsNam = outGoodsNam;
	}

	public String getOutLine() {
		return this.outLine;
	}

	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}

	public BigDecimal getOutNum() {
		return this.outNum;
	}

	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}

	public BigDecimal getOutWeight() {
		return this.outWeight;
	}

	public void setOutWeight(BigDecimal outWeight) {
		this.outWeight = outWeight;
	}

	public Date getPreInTime() {
		return this.preInTime;
	}

	public void setPreInTime(Date preInTime) {
		this.preInTime = preInTime;
	}

	public Date getPreOffTime() {
		return this.preOffTime;
	}

	public void setPreOffTime(Date preOffTime) {
		this.preOffTime = preOffTime;
	}

	public Date getPreUpTime() {
		return this.preUpTime;
	}

	public void setPreUpTime(Date preUpTime) {
		this.preUpTime = preUpTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShipCorp() {
		return this.shipCorp;
	}

	public void setShipCorp(String shipCorp) {
		this.shipCorp = shipCorp;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getVoyIn() {
		return this.voyIn;
	}

	public void setVoyIn(String voyIn) {
		this.voyIn = voyIn;
	}

	public String getVoyOut() {
		return this.voyOut;
	}

	public void setVoyOut(String voyOut) {
		this.voyOut = voyOut;
	}

	public String getVssCode() {
		return this.vssCode;
	}

	public void setVssCode(String vssCode) {
		this.vssCode = vssCode;
	}

	public String getVssCompleteDept() {
		return this.vssCompleteDept;
	}

	public void setVssCompleteDept(String vssCompleteDept) {
		this.vssCompleteDept = vssCompleteDept;
	}

	public String getVssCompleteFlag() {
		return this.vssCompleteFlag;
	}

	public void setVssCompleteFlag(String vssCompleteFlag) {
		this.vssCompleteFlag = vssCompleteFlag;
	}

	public String getVssCompleteMan() {
		return this.vssCompleteMan;
	}

	public void setVssCompleteMan(String vssCompleteMan) {
		this.vssCompleteMan = vssCompleteMan;
	}

	public String getVssCompleteRemark() {
		return this.vssCompleteRemark;
	}

	public void setVssCompleteRemark(String vssCompleteRemark) {
		this.vssCompleteRemark = vssCompleteRemark;
	}

	public Date getVssCompleteTime() {
		return this.vssCompleteTime;
	}

	public void setVssCompleteTime(Date vssCompleteTime) {
		this.vssCompleteTime = vssCompleteTime;
	}

	public String getVssEname() {
		return this.vssEname;
	}

	public void setVssEname(String vssEname) {
		this.vssEname = vssEname;
	}

	public Date getVssInCompleteTime() {
		return this.vssInCompleteTime;
	}

	public void setVssInCompleteTime(Date vssInCompleteTime) {
		this.vssInCompleteTime = vssInCompleteTime;
	}

	public String getVssInfo() {
		return this.vssInfo;
	}

	public void setVssInfo(String vssInfo) {
		this.vssInfo = vssInfo;
	}

	public byte[] getVssName() {
		return this.vssName;
	}

	public void setVssName(byte[] vssName) {
		this.vssName = vssName;
	}

	public String getVssNo() {
		return this.vssNo;
	}

	public void setVssNo(String vssNo) {
		this.vssNo = vssNo;
	}

	public Date getVssOutCompleteTime() {
		return this.vssOutCompleteTime;
	}

	public void setVssOutCompleteTime(Date vssOutCompleteTime) {
		this.vssOutCompleteTime = vssOutCompleteTime;
	}

}