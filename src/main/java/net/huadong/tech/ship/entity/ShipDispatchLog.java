package net.huadong.tech.ship.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the SHIP_DISPATCH_LOG database table.
 * 
 */
@Entity
@Table(name="SHIP_DISPATCH_LOG")
@NamedQuery(name="ShipDispatchLog.findAll", query="SELECT s FROM ShipDispatchLog s")
public class ShipDispatchLog extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DISPATCH_ID")
	private String dispatchId;

	@Temporal(TemporalType.DATE)
	@Column(name="DISPATCH_DTE")
	private Date dispatchDte;

	@Column(name="WORK_CONTENT")
	private String workContent;

	@Column(name="WORK_RUN")
	private String workRun;
	
	@Column(name="JB_NAM1")
	private String jbNam1;
	
	@Column(name="JB_NAM2")
	private String jbNam2;
	
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
	@Column(name="JB_TIM1")
	private Timestamp jbTim1;
	
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name="JB_TIM2")
    private Timestamp jbTim2;

	public ShipDispatchLog() {
	}

	public String getDispatchId() {
		return this.dispatchId;
	}

	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}

	public Date getDispatchDte() {
		return this.dispatchDte;
	}

	public void setDispatchDte(Date dispatchDte) {
		this.dispatchDte = dispatchDte;
	}


	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getWorkRun() {
		return this.workRun;
	}

	public void setWorkRun(String workRun) {
		this.workRun = workRun;
	}

	public String getJbNam1() {
		return jbNam1;
	}

	public void setJbNam1(String jbNam1) {
		this.jbNam1 = jbNam1;
	}

	public String getJbNam2() {
		return jbNam2;
	}

	public void setJbNam2(String jbNam2) {
		this.jbNam2 = jbNam2;
	}

    public Timestamp getJbTim1() {
        return jbTim1;
    }

    public void setJbTim1(Timestamp jbTim1) {
        this.jbTim1 = jbTim1;
    }

    public Timestamp getJbTim2() {
        return jbTim2;
    }

    public void setJbTim2(Timestamp jbTim2) {
        this.jbTim2 = jbTim2;
    }

	

}