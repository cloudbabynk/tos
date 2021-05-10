package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DAY_NIGHT_PLAN database table.
 * 
 */
@Entity
@Table(name="DAY_NIGHT_PLAN")
@NamedQuery(name="DayNightPlan.findAll", query="SELECT d FROM DayNightPlan d")
public class DayNightPlan extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="CLASS_EFFI")
	private BigDecimal classEffi;

	@Column(name="CLASS20_LINES")
	private BigDecimal class20Lines;

	@Column(name="CLASS20_MACH_WORKCAR")
	private BigDecimal class20MachWorkcar;

	@Column(name="CLASS20_TONS")
	private BigDecimal class20Tons;

	@Column(name="CLASS20_LOAD_WORK_NUM")
	private BigDecimal class20LoadWorkNum;
	
	@Column(name="CLASS20_UNLOAD_WORK_NUM")
	private BigDecimal class20UnloadWorkNum;

	@Column(name="CLASS20_WORKER")
	private BigDecimal class20Worker;

	@Column(name="CLASS08_LINES")
	private BigDecimal class08Lines;

	@Column(name="CLASS08_MACH_WORKCAR")
	private BigDecimal class08MachWorkcar;

	@Column(name="CLASS08_TONS")
	private BigDecimal class08Tons;

	@Column(name="CLASS08_LOAD_WORK_NUM")
	private BigDecimal class08LoadWorkNum;
	
	@Column(name="CLASS08_UNLOAD_WORK_NUM")
	private BigDecimal class08UnloadWorkNum;
	
	@Column(name="CLASS20_WORK_NUM")
	private BigDecimal class20WorkNum;
	
	@Column(name="CLASS08_WORK_NUM")
	private BigDecimal class08WorkNum;

	@Column(name="CLASS08_WORKER")
	private BigDecimal class08Worker;


	@Temporal(TemporalType.DATE)
	@Column(name="DAYS")
	private Date days;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="PLAN_WORK_TIM")
	private String planWorkTim;

	@Column(name="SHIP_EFFI")
	private BigDecimal shipEffi;

	@Column(name="SHIP_NAM_VOYAGE")
	private String shipNamVoyage;
	
	@Column(name="SHIP_VOYAGE")
	private String shipVoyage;

	@Column(name="SHIP_NO")
	private String shipNo;

	@Column(name="WORK_NUM")
	private BigDecimal workNum;

	@Column(name="WORK_TIM")
	private BigDecimal workTim;
	
	@Column(name="CLASS08_LOAD_TYP")
	private BigDecimal class08LoadTyp;
	
	@Column(name="CLASS20_LOAD_TYP")
	private BigDecimal class20LoadTyp;
	
	@Column(name="CLASS20_MACH_TRAILER")
	private BigDecimal class20MachTrailer;
	
	@Column(name="CLASS20_MACH_FORK")
	private BigDecimal class20MachFork;
	
	@Column(name="CLASS08_MACH_TRAILER")
	private BigDecimal class08MachTrailer;
	
	@Column(name="CLASS08_MACH_FORK")
	private BigDecimal class08MachFork;
	
	@Column(name="CLASS08_TONS_X")
	private BigDecimal class08TonsX;
	
	@Column(name="CLASS20_TONS_X")
	private BigDecimal class20TonsX;
	
	public DayNightPlan() {
	}
	

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public BigDecimal getClassEffi() {
		return this.classEffi;
	}

	public void setClassEffi(BigDecimal classEffi) {
		this.classEffi = classEffi;
	}


	public BigDecimal getClass08LoadTyp() {
		return class08LoadTyp;
	}


	public void setClass08LoadTyp(BigDecimal class08LoadTyp) {
		this.class08LoadTyp = class08LoadTyp;
	}


	public BigDecimal getClass08UnloadWorkNum() {
		return class08UnloadWorkNum;
	}


	public void setClass08UnloadWorkNum(BigDecimal class08UnloadWorkNum) {
		this.class08UnloadWorkNum = class08UnloadWorkNum;
	}


	public BigDecimal getClass20LoadTyp() {
		return class20LoadTyp;
	}


	public void setClass20LoadTyp(BigDecimal class20LoadTyp) {
		this.class20LoadTyp = class20LoadTyp;
	}


	public BigDecimal getClass20Lines() {
		return class20Lines;
	}


	public void setClass20Lines(BigDecimal class20Lines) {
		this.class20Lines = class20Lines;
	}


	public BigDecimal getClass20Tons() {
		return class20Tons;
	}


	public void setClass20Tons(BigDecimal class20Tons) {
		this.class20Tons = class20Tons;
	}

	public BigDecimal getClass20Worker() {
		return class20Worker;
	}

	public void setClass20Worker(BigDecimal class20Worker) {
		this.class20Worker = class20Worker;
	}

	public BigDecimal getClass08Lines() {
		return this.class08Lines;
	}

	public void setClass08Lines(BigDecimal class08Lines) {
		this.class08Lines = class08Lines;
	}


	public BigDecimal getClass08Tons() {
		return this.class08Tons;
	}

	public void setClass08Tons(BigDecimal class08Tons) {
		this.class08Tons = class08Tons;
	}


	public BigDecimal getClass08Worker() {
		return this.class08Worker;
	}

	public void setClass08Worker(BigDecimal class08Worker) {
		this.class08Worker = class08Worker;
	}


	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getPlanWorkTim() {
		return this.planWorkTim;
	}

	public void setPlanWorkTim(String planWorkTim) {
		this.planWorkTim = planWorkTim;
	}


	public BigDecimal getShipEffi() {
		return this.shipEffi;
	}

	public void setShipEffi(BigDecimal shipEffi) {
		this.shipEffi = shipEffi;
	}

	public String getShipNamVoyage() {
		return this.shipNamVoyage;
	}

	public void setShipNamVoyage(String shipNamVoyage) {
		this.shipNamVoyage = shipNamVoyage;
	}

	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}


	public BigDecimal getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(BigDecimal workNum) {
		this.workNum = workNum;
	}

	public BigDecimal getWorkTim() {
		return this.workTim;
	}

	public void setWorkTim(BigDecimal workTim) {
		this.workTim = workTim;
	}


	public BigDecimal getClass20MachWorkcar() {
		return class20MachWorkcar;
	}


	public void setClass20MachWorkcar(BigDecimal class20MachWorkcar) {
		this.class20MachWorkcar = class20MachWorkcar;
	}


	public BigDecimal getClass08MachWorkcar() {
		return class08MachWorkcar;
	}


	public void setClass08MachWorkcar(BigDecimal class08MachWorkcar) {
		this.class08MachWorkcar = class08MachWorkcar;
	}


	public BigDecimal getClass20MachTrailer() {
		return class20MachTrailer;
	}


	public void setClass20MachTrailer(BigDecimal class20MachTrailer) {
		this.class20MachTrailer = class20MachTrailer;
	}


	public BigDecimal getClass20MachFork() {
		return class20MachFork;
	}


	public void setClass20MachFork(BigDecimal class20MachFork) {
		this.class20MachFork = class20MachFork;
	}


	public BigDecimal getClass08MachTrailer() {
		return class08MachTrailer;
	}


	public void setClass08MachTrailer(BigDecimal class08MachTrailer) {
		this.class08MachTrailer = class08MachTrailer;
	}


	public BigDecimal getClass08MachFork() {
		return class08MachFork;
	}


	public void setClass08MachFork(BigDecimal class08MachFork) {
		this.class08MachFork = class08MachFork;
	}


	public String getShipVoyage() {
		return shipVoyage;
	}


	public void setShipVoyage(String shipVoyage) {
		this.shipVoyage = shipVoyage;
	}


	public BigDecimal getClass20LoadWorkNum() {
		return class20LoadWorkNum;
	}


	public void setClass20LoadWorkNum(BigDecimal class20LoadWorkNum) {
		this.class20LoadWorkNum = class20LoadWorkNum;
	}


	public BigDecimal getClass20UnloadWorkNum() {
		return class20UnloadWorkNum;
	}


	public void setClass20UnloadWorkNum(BigDecimal class20UnloadWorkNum) {
		this.class20UnloadWorkNum = class20UnloadWorkNum;
	}


	public BigDecimal getClass08LoadWorkNum() {
		return class08LoadWorkNum;
	}


	public void setClass08LoadWorkNum(BigDecimal class08LoadWorkNum) {
		this.class08LoadWorkNum = class08LoadWorkNum;
	}


	public BigDecimal getClass20WorkNum() {
		return class20WorkNum;
	}


	public void setClass20WorkNum(BigDecimal class20WorkNum) {
		this.class20WorkNum = class20WorkNum;
	}


	public BigDecimal getClass08WorkNum() {
		return class08WorkNum;
	}


	public void setClass08WorkNum(BigDecimal class08WorkNum) {
		this.class08WorkNum = class08WorkNum;
	}


	public BigDecimal getClass08TonsX() {
		return class08TonsX;
	}


	public void setClass08TonsX(BigDecimal class08TonsX) {
		this.class08TonsX = class08TonsX;
	}


	public BigDecimal getClass20TonsX() {
		return class20TonsX;
	}


	public void setClass20TonsX(BigDecimal class20TonsX) {
		this.class20TonsX = class20TonsX;
	}
	
	

}