package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.huadong.tech.base.bean.AuditEntityBean;


/**
 * The persistent class for the C_HOLIDAY database table.
 * 
 */
@Entity
@Table(name="C_HOLIDAY")
@NamedQuery(name="CHoliday.findAll", query="SELECT c FROM CHoliday c")
public class CHoliday extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	/*@Basic(optional = false)
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
	@NotNull*/
	@Column(name="H_ID")
	private String hId;

	@Temporal(TemporalType.DATE)
	@Column(name="ACT_DATE")
	private Date actDate;

	@Column(name="HOLIDAY_DAY")
	private String holidayDay;

	@Column(name="HOLIDAY_ID")
	private String holidayId;

	@Column(name="HOLIDAY_MONTH")
	private String holidayMonth;

	@Column(name="HOLIDAY_WEEK")
	private String holidayWeek;

	@Column(name="HOLIDAY_YEAR")
	private String holidayYear;

	

	@Column(name="WEEKEND_ID")
	private String weekendId;

	public CHoliday() {
	}
    public CHoliday(String hId,String holidayYear,String holidayMonth,String holidayDay,String holidayWeek,String holidayId,String weekendId,Date actDate,String recNam,Date recTim){
    super();
    this.hId=hId;
    this.holidayYear=holidayYear;
    this.holidayMonth=holidayMonth;
    this.holidayDay=holidayDay;
    this.holidayWeek=holidayWeek;
    this.holidayId=holidayId;
    this.weekendId=weekendId;
    this.actDate=actDate;
   
    }


	public String gethId() {
		return hId;
	}



	public void sethId(String hId) {
		this.hId = hId;
	}


	public Date getActDate() {
		return this.actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}

	public String getHolidayDay() {
		return this.holidayDay;
	}

	public void setHolidayDay(String holidayDay) {
		this.holidayDay = holidayDay;
	}

	public String getHolidayId() {
		return this.holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

	public String getHolidayMonth() {
		return this.holidayMonth;
	}

	public void setHolidayMonth(String holidayMonth) {
		this.holidayMonth = holidayMonth;
	}

	public String getHolidayWeek() {
		return this.holidayWeek;
	}

	public void setHolidayWeek(String holidayWeek) {
		this.holidayWeek = holidayWeek;
	}

	public String getHolidayYear() {
		return this.holidayYear;
	}

	public void setHolidayYear(String holidayYear) {
		this.holidayYear = holidayYear;
	}

	public String getWeekendId() {
		return this.weekendId;
	}

	public void setWeekendId(String weekendId) {
		this.weekendId = weekendId;
	}

}