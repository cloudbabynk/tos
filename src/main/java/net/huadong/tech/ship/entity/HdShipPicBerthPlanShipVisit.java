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
@Table(name="SHIP_PLAN") 
public class HdShipPicBerthPlanShipVisit extends AuditEntityBean implements Serializable {
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
	
	@Id
	@Column(name="PLAN_ID")
	private String shipVisitId;
	@Column(name="SHIP_NO")
	private String shipNo;
	@Column(name="BEG_CABLE_NO")
	private String planBeginBollardCode;
	@Column(name="BERTH_COD")
	private String planBerthCode;
	@Column(name="BERTH_WAY")
	private String planBerthingMode;
	@Column(name="CON_ARRV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm") 
	private Timestamp conArrvTim;
	@Column(name="DOCK_COD")
	private String dockCod;
	@Column(name="E_CARGO_NAM")
	private String eCargoNam;
	@Column(name="E_CARGO_PIECES")
	private BigDecimal eCargoPieces;
	@Column(name="E_TON_NUM")
	private BigDecimal eTonNum;
	@Column(name="END_CABLE_NO")
	private String planEndBollardCode;
	@Column(name="ETD_ARRV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")  
	private Timestamp etb;
	@Column(name="ETD_LEAV_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")  
	private Timestamp etu;
	@Column(name="EVOYAGE")
	private String exVoyage;
	@Column(name="I_CARGO_NAM")
	private String iCargoNam;
	@Column(name="I_CARGO_PIECES")
	private BigDecimal iCargoPieces;
	@Column(name="I_TON_NUM")
	private BigDecimal iTonNum;
	@Column(name="IVOYAGE")
	private String imVoyage;
	private String remarks;
	@Column(name="SHIP_COD")
	private String shipCod;
	@Column(name="SHIP_COD_ID")
	private String shipCodId;
	@Column(name="C_SHIP_NAM")
	private String shipName;
	@Column(name="E_SHIP_NAM")
	private String eShipNam;
	@Transient
	private BigDecimal shipLongNum;
	@Transient
	private BigDecimal shipLength;
	@Transient
	private String planBerthingModeName;
	@Transient
	private String cShipNam;
	
	
	public String getcShipNam() {
		return cShipNam;
	}

	public void setcShipNam(String cShipNam) {
		this.cShipNam = cShipNam;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipVisitId() {
		return shipVisitId;
	}

	public void setShipVisitId(String shipVisitId) {
		this.shipVisitId = shipVisitId;
	}

	public String getPlanBeginBollardCode() {
		return planBeginBollardCode;
	}

	public void setPlanBeginBollardCode(String planBeginBollardCode) {
		this.planBeginBollardCode = planBeginBollardCode;
	}

	public String getPlanBerthCode() {
		return planBerthCode;
	}

	public void setPlanBerthCode(String planBerthCode) {
		this.planBerthCode = planBerthCode;
	}

	public String getPlanBerthingMode() {
		return planBerthingMode;
	}

	public void setPlanBerthingMode(String planBerthingMode) {
		this.planBerthingMode = planBerthingMode;
	}

	public String getPlanEndBollardCode() {
		return planEndBollardCode;
	}

	public void setPlanEndBollardCode(String planEndBollardCode) {
		this.planEndBollardCode = planEndBollardCode;
	}

	public Timestamp getEtb() {
		return etb;
	}

	public void setEtb(Timestamp etb) {
		this.etb = etb;
	}


	public Timestamp getEtu() {
		return etu;
	}

	public void setEtu(Timestamp etu) {
		this.etu = etu;
	}

	public String getExVoyage() {
		return exVoyage;
	}

	public void setExVoyage(String exVoyage) {
		this.exVoyage = exVoyage;
	}

	public String getImVoyage() {
		return imVoyage;
	}

	public void setImVoyage(String imVoyage) {
		this.imVoyage = imVoyage;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}


	public BigDecimal getShipLongNum() {
		return shipLongNum;
	}

	public void setShipLongNum(BigDecimal shipLongNum) {
		this.shipLongNum = shipLongNum;
	}

	public BigDecimal getShipLength() {
		return shipLength;
	}

	public void setShipLength(BigDecimal shipLength) {
		this.shipLength = shipLength;
	}

	public HdShipPicBerthPlanShipVisit() {
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

	public String geteShipNam() {
		return eShipNam;
	}

	public void seteShipNam(String eShipNam) {
		this.eShipNam = eShipNam;
	}



	public String getRemarks() {
		return remarks;
	}


	public Timestamp getConArrvTim() {
		return conArrvTim;
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
	public BigDecimal getETonNum() {
		return this.eTonNum;
	}
	public void setETonNum(BigDecimal eTonNum) {
		this.eTonNum = eTonNum;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getiCargoNam() {
		return iCargoNam;
	}

	public void setiCargoNam(String iCargoNam) {
		this.iCargoNam = iCargoNam;
	}

	public String getPlanBerthingModeName() {
		return planBerthingModeName;
	}

	public void setPlanBerthingModeName(String planBerthingModeName) {
		this.planBerthingModeName = planBerthingModeName;
	}

	
	
}