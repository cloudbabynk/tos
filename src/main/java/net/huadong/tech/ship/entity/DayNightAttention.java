package net.huadong.tech.ship.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the DAY_NIGHT_ATTENTION database table.
 * 
 */
@Entity
@Table(name="DAY_NIGHT_ATTENTION")
@NamedQuery(name="DayNightAttention.findAll", query="SELECT d FROM DayNightAttention d")
public class DayNightAttention extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ATTENTION_ID")
	private String attentionId;

	@Column(name="CHANNEL_PLAN")
	private String channelPlan;

	@Temporal(TemporalType.DATE)
	@Column(name="DAYS")
	private Date days;

	@Column(name="DRIVER_PLAN")
	private String driverPlan;

	@Column(name="SAFETY_MATTERS")
	private String safetyMatters;

	@Column(name="STAFF_PLAN")
	private String staffPlan;

	@Column(name="STRESS_MATTERS")
	private String stressMatters;

	private String tide;

	private String weather;

	@Column(name="WORK_PLAN")
	private String workPlan;

	public DayNightAttention() {
	}

	public String getAttentionId() {
		return this.attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public String getChannelPlan() {
		return this.channelPlan;
	}

	public void setChannelPlan(String channelPlan) {
		this.channelPlan = channelPlan;
	}


	public Date getDays() {
		return days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getDriverPlan() {
		return this.driverPlan;
	}

	public void setDriverPlan(String driverPlan) {
		this.driverPlan = driverPlan;
	}



	public String getSafetyMatters() {
		return this.safetyMatters;
	}

	public void setSafetyMatters(String safetyMatters) {
		this.safetyMatters = safetyMatters;
	}

	public String getStaffPlan() {
		return this.staffPlan;
	}

	public void setStaffPlan(String staffPlan) {
		this.staffPlan = staffPlan;
	}

	public String getStressMatters() {
		return this.stressMatters;
	}

	public void setStressMatters(String stressMatters) {
		this.stressMatters = stressMatters;
	}

	public String getTide() {
		return this.tide;
	}

	public void setTide(String tide) {
		this.tide = tide;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWorkPlan() {
		return this.workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

}