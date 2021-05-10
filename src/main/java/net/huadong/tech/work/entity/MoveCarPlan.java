package net.huadong.tech.work.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MOVE_CAR_PLAN database table.
 * 
 */
@Entity
@Table(name="MOVE_CAR_PLAN")
@NamedQuery(name="MoveCarPlan.findAll", query="SELECT m FROM MoveCarPlan m")
public class MoveCarPlan extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MOVEPLAN_ID")
	private String moveplanId;

	@Column(name="DOCK_COD")
	private String dockCod;

	@Column(name="FINISHED_ID")
	private String finishedId;

	@Column(name="MOVE_PLAN_NO")
	private String movePlanNo;

	@Column(name="MOVE_WAY")
	private String moveWay;

	@Column(name="OLD_PLAC")
	private String oldPlac;

	@Column(name="PLAN_PLAC")
	private String planPlac;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;

	private String remarks;

	@Column(name="RFID_CARD_NO")
	private String rfidCardNo;

	@Column(name="SEND_ID")
	private String sendId;

	public MoveCarPlan() {
	}

	public String getMoveplanId() {
		return this.moveplanId;
	}

	public void setMoveplanId(String moveplanId) {
		this.moveplanId = moveplanId;
	}

	public String getDockCod() {
		return this.dockCod;
	}

	public void setDockCod(String dockCod) {
		this.dockCod = dockCod;
	}

	public String getFinishedId() {
		return this.finishedId;
	}

	public void setFinishedId(String finishedId) {
		this.finishedId = finishedId;
	}

	public String getMovePlanNo() {
		return this.movePlanNo;
	}

	public void setMovePlanNo(String movePlanNo) {
		this.movePlanNo = movePlanNo;
	}

	public String getMoveWay() {
		return this.moveWay;
	}

	public void setMoveWay(String moveWay) {
		this.moveWay = moveWay;
	}

	public String getOldPlac() {
		return this.oldPlac;
	}

	public void setOldPlac(String oldPlac) {
		this.oldPlac = oldPlac;
	}

	public String getPlanPlac() {
		return this.planPlac;
	}

	public void setPlanPlac(String planPlac) {
		this.planPlac = planPlac;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRfidCardNo() {
		return this.rfidCardNo;
	}

	public void setRfidCardNo(String rfidCardNo) {
		this.rfidCardNo = rfidCardNo;
	}

	public String getSendId() {
		return this.sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

}