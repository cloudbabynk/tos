package net.huadong.tech.ship.entity;
import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.huadong.tech.base.bean.AuditEntityBean;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
 * The persistent class for the SHIP database table.
 * 
 */
@Entity
@NamedQuery(name="Ship.findAll", query="SELECT s FROM Ship s")
public class Ship extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//船状态
	public static final String STATUS_1 = "E";// 预报船
	public static final String STATUS_2 = "A";// 锚地船
	public static final String STATUS_3 = "Y";// 在港船
	public static final String STATUS_4 = "L";// 离港船
	
	public static final String NM = "1";// 内贸
	public static final String WM = "2";// 外贸
	
	public static final String JK = "I";// 进口
	public static final String CK = "E";// 出口
	
	public static final String GK = "AA00"; //默认泊位为沽口
	
	public static final String HQ = "03409000"; //环球
	public static final String GZ = "03406500"; //滚装
	
	public static final String CZNO = "20190311082013"; //存栈船的shipNo
	
	@Id
	@Column(name="SHIP_NO")
	private String shipNo;
	@Column(name="BEG_CABLE_NO")
	private String begCableNo;
	@Column(name="BERTH_COD")
	private String berthCod;
	@Column(name="BERTH_WAY")
	private String berthWay;
	@Column(name="CON_ARRV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp conArrvTim;
	@Column(name="DISC_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp discBegTim;
	@Column(name="DISC_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp discEndTim;
	@Column(name="DOCK_COD")
	private String dockCod;
	@Column(name="E_CARGO_NAM")
	private String eCargoNam;
	@Column(name="E_CARGO_PIECES")
	private BigDecimal eCargoPieces;
	@Column(name="E_TON_NUM")
	private BigDecimal eTonNum;
	@Column(name="END_CABLE_NO")
	private String endCableNo;
	@Column(name="ETD_ARRV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")  
	private Timestamp etdArrvTim;
	@Column(name="ETD_LEAV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")  
	private Timestamp etdLeavTim;
	private String evoyage;
	@Column(name="F_LINE_COD")
	private String fLineCod;
	@Column(name="GROUP_SHIP")
	private String groupShip;
	@Column(name="GROUP_SHIP_NO")
	private String groupShipNo;
	@Column(name="I_CARGO_NAM")
	private String iCargoNam;
	@Column(name="I_CARGO_PIECES")
	private BigDecimal iCargoPieces;
	@Column(name="I_LINE_COD")
	private String iLineCod;
	@Column(name="I_TON_NUM")
	private BigDecimal iTonNum;
	private String ivoyage;
	@Column(name="LAST_PORT_COD")
	private String lastPortCod;
	@Column(name="LEAV_PORT_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp leavPortTim;
	@Column(name="LOAD_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp loadBegTim;
	@Column(name="LOAD_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp loadEndTim;
	@Column(name="LOCK_ID")
	private String lockId;
	@Column(name="NEXT_PORT_COD")
	private String nextPortCod;
	@Column(name="PLAN_DISC_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp planDiscBegTim;
	@Column(name="PLAN_DISC_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp planDiscEndTim;
	@Column(name="PLAN_LOAD_BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp planLoadBegTim;
	@Column(name="PLAN_LOAD_END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp planLoadEndTim;
	private String remarks;
	@Column(name="SHIP_AGENT_COD")
	private String shipAgentCod;
	@Column(name="SHIP_COD")
	private String shipCod;
	@Column(name="SHIP_COD_ID")
	private String shipCodId;
	@Column(name="SHIP_CORP_COD")
	private String shipCorpCod;


	@Column(name="C_SHIP_NAM")
	private String cShipNam;
	
	@Column(name="E_SHIP_NAM")
	private String eShipNam;
	
	
	@Column(name="IPOD")
	private String ipod;
	
	@Column(name="EPOD")
	private String epod;
	
	@Column(name="IPOC")
	private String ipoc;
	
	@Column(name="EPOC")
	private String epoc;
	
	@Column(name="I_SHIP_LINE")
	private String iShipLine;
	
	@Column(name="E_SHIP_LINE")
	private String eShipLine;


	@Column(name="SHIP_STAT")
	private String shipStat;
	@Column(name="SHIP_TYP")
	private String shipTyp;
	@Column(name="TO_PORT_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp toPortTim;
	@Column(name="TRADE_ID")
	private String tradeId;
	
	@Column(name="DRAFT_FRONT")
	private BigDecimal draftFront;
	
	@Column(name="DRAFT_BACK")
	private BigDecimal draftBack;
	
	@Column(name="NEW_GROUP_SHIP_NO")
	private String newGroupShipNo;
	
	@Column(name="NEW_I_SHIP_ID")
	private String newIShipId;
	
	@Column(name="NEW_E_SHIP_ID")
	private String newEShipId;
	
	@Column(name="IN_CONSIGN_COD")
	private String inConsignCod;
	
	@Column(name="OUT_CONSIGN_COD")
	private String outConsignCod;
	
	@Transient
	private String shipStatNam;
	
	@Transient
	private String berthCodNam;
	
	@Transient
	private BigDecimal shipLongNum;
	
	@Transient
	private BigDecimal shipGrossWgt;
	
	@Transient
	private BigDecimal shipNetWgt;
	
	@Transient
	private String dkrq;
	
	@Transient
	private String dksj;
	
	@Transient
	private String shipShort;
	
	public String getNewGroupShipNo() {
		return newGroupShipNo;
	}

	public void setNewGroupShipNo(String newGroupShipNo) {
		this.newGroupShipNo = newGroupShipNo;
	}

	public String getNewIShipId() {
		return newIShipId;
	}

	public void setNewIShipId(String newIShipId) {
		this.newIShipId = newIShipId;
	}

	public String getNewEShipId() {
		return newEShipId;
	}

	public void setNewEShipId(String newEShipId) {
		this.newEShipId = newEShipId;
	}

	public BigDecimal getShipNetWgt() {
		return shipNetWgt;
	}

	public void setShipNetWgt(BigDecimal shipNetWgt) {
		this.shipNetWgt = shipNetWgt;
	}

	public String getDkrq() {
		return dkrq;
	}

	public void setDkrq(String dkrq) {
		this.dkrq = dkrq;
	}

	public String getInConsignCod() {
		return inConsignCod;
	}

	public void setInConsignCod(String inConsignCod) {
		this.inConsignCod = inConsignCod;
	}

	public String getOutConsignCod() {
		return outConsignCod;
	}

	public void setOutConsignCod(String outConsignCod) {
		this.outConsignCod = outConsignCod;
	}

	public String getDksj() {
		return dksj;
	}

	public void setDksj(String dksj) {
		this.dksj = dksj;
	}

	public BigDecimal getShipGrossWgt() {
		return shipGrossWgt;
	}

	public void setShipGrossWgt(BigDecimal shipGrossWgt) {
		this.shipGrossWgt = shipGrossWgt;
	}

	public BigDecimal getShipLongNum() {
		return shipLongNum;
	}

	public void setShipLongNum(BigDecimal shipLongNum) {
		this.shipLongNum = shipLongNum;
	}

	public BigDecimal getDraftFront() {
		return draftFront;
	}

	public void setDraftFront(BigDecimal draftFront) {
		this.draftFront = draftFront;
	}

	public BigDecimal getDraftBack() {
		return draftBack;
	}

	public void setDraftBack(BigDecimal draftBack) {
		this.draftBack = draftBack;
	}

	public String getBerthCodNam() {
		return berthCodNam;
	}

	public void setBerthCodNam(String berthCodNam) {
		this.berthCodNam = berthCodNam;
	}

	public Ship() {
	}
	
	public String getShipStatNam() {
		return shipStatNam;
	}
	public void setShipStatNam(String shipStatNam) {
		this.shipStatNam = shipStatNam;
	}
	public String getShipNo() {
		return this.shipNo;
	}


	public String getfLineCod() {
		return fLineCod;
	}

	public void setfLineCod(String fLineCod) {
		this.fLineCod = fLineCod;
	}


	public BigDecimal getiCargoPieces() {
		return iCargoPieces;
	}

	public void setiCargoPieces(BigDecimal iCargoPieces) {
		this.iCargoPieces = iCargoPieces;
	}

	public BigDecimal getiTonNum() {
		return iTonNum;
	}

	public void setiTonNum(BigDecimal iTonNum) {
		this.iTonNum = iTonNum;
	}

	public String getcShipNam() {
		return cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}

	public String geteShipNam() {
		return eShipNam;
	}

	public void seteShipNam(String eShipNam) {
		this.eShipNam = eShipNam;
	}


	public String getIpod() {
		return ipod;
	}

	public void setIpod(String ipod) {
		this.ipod = ipod;
	}

	public String getEpod() {
		return epod;
	}

	public void setEpod(String epod) {
		this.epod = epod;
	}

	public String getIpoc() {
		return ipoc;
	}

	public void setIpoc(String ipoc) {
		this.ipoc = ipoc;
	}

	public String getEpoc() {
		return epoc;
	}

	public void setEpoc(String epoc) {
		this.epoc = epoc;
	}

	public String getiShipLine() {
		return iShipLine;
	}

	public void setiShipLine(String iShipLine) {
		this.iShipLine = iShipLine;
	}

	public String geteShipLine() {
		return eShipLine;
	}

	public void seteShipLine(String eShipLine) {
		this.eShipLine = eShipLine;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setLeavPortTim(Timestamp leavPortTim) {
		this.leavPortTim = leavPortTim;
	}

	public void setLoadBegTim(Timestamp loadBegTim) {
		this.loadBegTim = loadBegTim;
	}

	public void setLoadEndTim(Timestamp loadEndTim) {
		this.loadEndTim = loadEndTim;
	}

	public void setToPortTim(Timestamp toPortTim) {
		this.toPortTim = toPortTim;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getBegCableNo() {
		return this.begCableNo;
	}
	public void setBegCableNo(String begCableNo) {
		this.begCableNo = begCableNo;
	}
	public String getBerthCod() {
		return this.berthCod;
	}
	public void setBerthCod(String berthCod) {
		this.berthCod = berthCod;
	}
	public String getBerthWay() {
		return this.berthWay;
	}
	public void setBerthWay(String berthWay) {
		this.berthWay = berthWay;
	}
	public Timestamp getConArrvTim() {
		return conArrvTim;
	}
	public Timestamp getDiscBegTim() {
		return discBegTim;
	}
	public Timestamp getDiscEndTim() {
		return discEndTim;
	}
	public String getDockCod() {
		return this.dockCod;
	}
	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}
	public String getECargoNam() {
		return this.eCargoNam;
	}
	public void setECargoNam(String eCargoNam) {
		this.eCargoNam = eCargoNam;
	}
	public BigDecimal getECargoPieces() {
		return this.eCargoPieces;
	}
	public void setECargoPieces(BigDecimal eCargoPieces) {
		this.eCargoPieces = eCargoPieces;
	}
	public String getEndCableNo() {
		return this.endCableNo;
	}
	public void setEndCableNo(String endCableNo) {
		this.endCableNo = endCableNo;
	}
	public Timestamp getEtdArrvTim() {
		return etdArrvTim;
	}
	public void setEtdArrvTim(Timestamp etdArrvTim) {
		this.etdArrvTim = etdArrvTim;
	}
	public Timestamp getEtdLeavTim() {
		return this.etdLeavTim;
	}
	public String getEvoyage() {
		return this.evoyage;
	}
	public void setEvoyage(String evoyage) {
		this.evoyage = evoyage;
	}
	public String getFLineCod() {
		return this.fLineCod;
	}
	public void setFLineCod(String fLineCod) {
		this.fLineCod = fLineCod;
	}
	public String getGroupShip() {
		return this.groupShip;
	}
	public void setGroupShip(String groupShip) {
		this.groupShip = groupShip;
	}
	public String getGroupShipNo() {
		return this.groupShipNo;
	}
	public void setGroupShipNo(String groupShipNo) {
		this.groupShipNo = groupShipNo;
	}
	public String getIvoyage() {
		return this.ivoyage;
	}
	public void setIvoyage(String ivoyage) {
		this.ivoyage = ivoyage;
	}
	public String getLastPortCod() {
		return this.lastPortCod;
	}
	public void setLastPortCod(String lastPortCod) {
		this.lastPortCod = lastPortCod;
	}
	public Timestamp getLeavPortTim() {
		return this.leavPortTim;
	}
	public Timestamp getLoadBegTim() {
		return this.loadBegTim;
	}
	public Timestamp getLoadEndTim() {
		return this.loadEndTim;
	}
	public String getLockId() {
		return this.lockId;
	}
	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
	public String getNextPortCod() {
		return this.nextPortCod;
	}
	public void setNextPortCod(String nextPortCod) {
		this.nextPortCod = nextPortCod;
	}
	public Timestamp getPlanDiscBegTim() {
		return this.planDiscBegTim;
	}
	public Timestamp getPlanDiscEndTim() {
		return this.planDiscEndTim;
	}
	public Timestamp getPlanLoadBegTim() {
		return this.planLoadBegTim;
	}
	public Timestamp getPlanLoadEndTim() {
		return this.planLoadEndTim;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getShipAgentCod() {
		return this.shipAgentCod;
	}
	public void setShipAgentCod(String shipAgentCod) {
		this.shipAgentCod = shipAgentCod;
	}
	public String getShipCod() {
		return this.shipCod;
	}
	public void setShipCod(String shipCod) {
		this.shipCod = shipCod;
	}
	public String getShipCodId() {
		return this.shipCodId;
	}
	public void setShipCodId(String shipCodId) {
		this.shipCodId = shipCodId;
	}
	public String getShipCorpCod() {
		return this.shipCorpCod;
	}
	public void setShipCorpCod(String shipCorpCod) {
		this.shipCorpCod = shipCorpCod;
	}


	public String getShipStat() {
		return this.shipStat;
	}
	public void setShipStat(String shipStat) {
		this.shipStat = shipStat;
	}
	public String getShipTyp() {
		return this.shipTyp;
	}
	public void setShipTyp(String shipTyp) {
		this.shipTyp = shipTyp;
	}
	public Timestamp getToPortTim() {
		return this.toPortTim;
	}
	public String getTradeId() {
		return this.tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String geteCargoNam() {
		return eCargoNam;
	}
	public void seteCargoNam(String eCargoNam) {
		this.eCargoNam = eCargoNam;
	}
	public BigDecimal geteCargoPieces() {
		return eCargoPieces;
	}
	public void seteCargoPieces(BigDecimal eCargoPieces) {
		this.eCargoPieces = eCargoPieces;
	}
	public BigDecimal geteTonNum() {
		return eTonNum;
	}
	public void seteTonNum(BigDecimal eTonNum) {
		this.eTonNum = eTonNum;
	}
	public void setConArrvTim(Timestamp conArrvTim) {
		this.conArrvTim = conArrvTim;
	}
	public void setDiscBegTim(Timestamp discBegTim) {
		this.discBegTim = discBegTim;
	}
	public void setDiscEndTim(Timestamp discEndTim) {
		this.discEndTim = discEndTim;
	}
	public void setEtdLeavTim(Timestamp etdLeavTim) {
		this.etdLeavTim = etdLeavTim;
	}
	public void setPlanDiscBegTim(Timestamp planDiscBegTim) {
		this.planDiscBegTim = planDiscBegTim;
	}
	public void setPlanDiscEndTim(Timestamp planDiscEndTim) {
		this.planDiscEndTim = planDiscEndTim;
	}
	public void setPlanLoadBegTim(Timestamp planLoadBegTim) {
		this.planLoadBegTim = planLoadBegTim;
	}
	public void setPlanLoadEndTim(Timestamp planLoadEndTim) {
		this.planLoadEndTim = planLoadEndTim;
	}

	public String getiCargoNam() {
		return iCargoNam;
	}

	public void setiCargoNam(String iCargoNam) {
		this.iCargoNam = iCargoNam;
	}

	public String getShipShort() {
		return shipShort;
	}

	public void setShipShort(String shipShort) {
		this.shipShort = shipShort;
	}
	
	
}