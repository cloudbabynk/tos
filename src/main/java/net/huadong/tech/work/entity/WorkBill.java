package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the WORK_BILL database table.
 * 
 */
@Entity
@Table(name="WORK_BILL")
@NamedQuery(name="WorkBill.findAll", query="SELECT w FROM WorkBill w")
public class WorkBill extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WORKBILL_NO")
	private String workbillNo;

	@Column(name="BEG_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp begTim;

	@Column(name="CHECK_ID")
	private String checkId;

	@Column(name="CHECK_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp checkTim;

	@Column(name="CHECKER_MAN")
	private String checkerMan;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="DRIVER_BILL_CONF")
	private String driverBillConf;

	@Column(name="DRIVER_NUM")
	private BigDecimal driverNum;

	@Column(name="END_TIM")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Timestamp endTim;

	@Column(name="F_DIRECTOR")
	private String fDirector;

	@Column(name="F_DIRECTOR_COD")
	private String fDirectorCod;

	@Column(name="MACH_BILL_CONF")
	private String machBillConf;

	@Column(name="MACH_TXT")
	private String machTxt;
	
	@Column(name="S_DIRECTOR")
	private String sDirector;

	@Column(name="S_DIRECTOR_COD")
	private String sDirectorCod;

	@Column(name="SHIP_NAM")
	private String shipNam;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="TALLY_BILL_CONF")
	private String tallyBillConf;

	@Column(name="TALLY_NUM")
	private BigDecimal tallyNum;

	private String voyage;

	@Column(name="WORK_DTE")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") 
	private Timestamp workDte;

	@Column(name="WORK_RUN_COD")
	private String workRunCod;

	@Column(name="WORK_TYP")
	private String workTyp;

	@Column(name="WORKTIME_NUM")
	private BigDecimal worktimeNum;
	
	@Column(name="PLAN_NUM")
	private BigDecimal planNum;
	
	@Column(name="PLAN_WEIGHT")
	private BigDecimal planWeight;
	
	@Column(name="WORK_CAR_NUM")
	private BigDecimal workCarNum;
	
	@Column(name="WORK_TRUCK_NUM")
	private BigDecimal workTruckNum;
	
	@Column(name="WORK_BULK_NUM")
	private BigDecimal workBulkNum;
	
	@Column(name="WORK_BULK_WEIGHT")
	private BigDecimal workBulkWeight;
	
	@Column(name="TOTAL_NUM")
	private BigDecimal totalNum;
	
	@Column(name="TOTAL_WEIGHT")
	private BigDecimal totalWeight;
	
	@Transient
	private String workRunCodNam;
	
	@Transient
	private String jtcNum;
	
	@Transient
	private String ccNum;
	
	@Transient
	private String qycNum;
	
	//bi-directional many-to-one association to WorkBillMach

	public WorkBill() {
	}

	public String getWorkbillNo() {
		return this.workbillNo;
	}

	public void setWorkbillNo(String workbillNo) {
		this.workbillNo = workbillNo;
	}

	public Timestamp getBegTim() {
		return begTim;
	}

	public void setBegTim(Timestamp begTim) {
		this.begTim = begTim;
	}

	public Timestamp getCheckTim() {
		return checkTim;
	}

	public void setCheckTim(Timestamp checkTim) {
		this.checkTim = checkTim;
	}

	public Timestamp getEndTim() {
		return endTim;
	}

	public void setEndTim(Timestamp endTim) {
		this.endTim = endTim;
	}



	public String getfDirector() {
		return fDirector;
	}

	public void setfDirector(String fDirector) {
		this.fDirector = fDirector;
	}

	public String getfDirectorCod() {
		return fDirectorCod;
	}

	public void setfDirectorCod(String fDirectorCod) {
		this.fDirectorCod = fDirectorCod;
	}

	public String getsDirector() {
		return sDirector;
	}

	public void setsDirector(String sDirector) {
		this.sDirector = sDirector;
	}

	public String getsDirectorCod() {
		return sDirectorCod;
	}

	public void setsDirectorCod(String sDirectorCod) {
		this.sDirectorCod = sDirectorCod;
	}

	public String getWorkRunCodNam() {
		return workRunCodNam;
	}

	public void setWorkRunCodNam(String workRunCodNam) {
		this.workRunCodNam = workRunCodNam;
	}

	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckerMan() {
		return this.checkerMan;
	}

	public void setCheckerMan(String checkerMan) {
		this.checkerMan = checkerMan;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDriverBillConf() {
		return this.driverBillConf;
	}

	public void setDriverBillConf(String driverBillConf) {
		this.driverBillConf = driverBillConf;
	}

	public BigDecimal getDriverNum() {
		return this.driverNum;
	}

	public void setDriverNum(BigDecimal driverNum) {
		this.driverNum = driverNum;
	}



	public String getMachBillConf() {
		return this.machBillConf;
	}

	public void setMachBillConf(String machBillConf) {
		this.machBillConf = machBillConf;
	}

	public String getMachTxt() {
		return this.machTxt;
	}

	public void setMachTxt(String machTxt) {
		this.machTxt = machTxt;
	}



	public String getShipNam() {
		return this.shipNam;
	}

	public void setShipNam(String shipNam) {
		this.shipNam = shipNam;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTallyBillConf() {
		return this.tallyBillConf;
	}

	public void setTallyBillConf(String tallyBillConf) {
		this.tallyBillConf = tallyBillConf;
	}

	public BigDecimal getTallyNum() {
		return this.tallyNum;
	}

	public void setTallyNum(BigDecimal tallyNum) {
		this.tallyNum = tallyNum;
	}


	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}


	public String getWorkRunCod() {
		return this.workRunCod;
	}

	public void setWorkRunCod(String workRunCod) {
		this.workRunCod = workRunCod;
	}

	public String getWorkTyp() {
		return this.workTyp;
	}

	public void setWorkTyp(String workTyp) {
		this.workTyp = workTyp;
	}

	public BigDecimal getWorktimeNum() {
		return this.worktimeNum;
	}

	public void setWorktimeNum(BigDecimal worktimeNum) {
		this.worktimeNum = worktimeNum;
	}



	public String getJtcNum() {
		return jtcNum;
	}

	public void setJtcNum(String jtcNum) {
		this.jtcNum = jtcNum;
	}

	public String getCcNum() {
		return ccNum;
	}

	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}

	public String getQycNum() {
		return qycNum;
	}

	public void setQycNum(String qycNum) {
		this.qycNum = qycNum;
	}

	public BigDecimal getPlanNum() {
		return planNum;
	}

	public void setPlanNum(BigDecimal planNum) {
		this.planNum = planNum;
	}

	public BigDecimal getPlanWeight() {
		return planWeight;
	}

	public void setPlanWeight(BigDecimal planWeight) {
		this.planWeight = planWeight;
	}

	public BigDecimal getWorkCarNum() {
		return workCarNum;
	}

	public void setWorkCarNum(BigDecimal workCarNum) {
		this.workCarNum = workCarNum;
	}

	public BigDecimal getWorkTruckNum() {
		return workTruckNum;
	}

	public void setWorkTruckNum(BigDecimal workTruckNum) {
		this.workTruckNum = workTruckNum;
	}

	public BigDecimal getWorkBulkNum() {
		return workBulkNum;
	}

	public void setWorkBulkNum(BigDecimal workBulkNum) {
		this.workBulkNum = workBulkNum;
	}

	public BigDecimal getWorkBulkWeight() {
		return workBulkWeight;
	}

	public void setWorkBulkWeight(BigDecimal workBulkWeight) {
		this.workBulkWeight = workBulkWeight;
	}

	public Timestamp getWorkDte() {
		return workDte;
	}

	public void setWorkDte(Timestamp workDte) {
		this.workDte = workDte;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}




}