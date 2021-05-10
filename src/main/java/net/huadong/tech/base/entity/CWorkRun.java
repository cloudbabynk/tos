package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_WORK_RUN database table.
 * 
 */
@Entity
@Table(name="C_WORK_RUN")
@NamedQuery(name="CWorkRun.findAll", query="SELECT c FROM CWorkRun c")
public class CWorkRun extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="WORK_RUN")
	private String workRun;

	@Column(name="BEG_TIM")
	private String begTim;

	@Column(name="END_TIM")
	private String endTim;

	@Column(name="NIGHT_ID")
	private String nightId;

	@Column(name="WORK_RUN_NAM")
	private String workRunNam;

	public CWorkRun() {
	}

	public String getWorkRun() {
		return this.workRun;
	}

	public void setWorkRun(String workRun) {
		this.workRun = workRun;
	}

	public String getBegTim() {
		return this.begTim;
	}

	public void setBegTim(String begTim) {
		this.begTim = begTim;
	}

	public String getEndTim() {
		return this.endTim;
	}

	public void setEndTim(String endTim) {
		this.endTim = endTim;
	}

	public String getNightId() {
		return this.nightId;
	}

	public void setNightId(String nightId) {
		this.nightId = nightId;
	}

	public String getWorkRunNam() {
		return this.workRunNam;
	}

	public void setWorkRunNam(String workRunNam) {
		this.workRunNam = workRunNam;
	}

}