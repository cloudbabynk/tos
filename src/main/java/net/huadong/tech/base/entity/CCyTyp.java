package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the C_CY_TYP database table.
 * 
 */
@Entity
@Table(name="C_CY_TYP")
@NamedQuery(name="CCyTyp.findAll", query="SELECT c FROM CCyTyp c")
public class CCyTyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CY_TYP")
	private String cyTyp;

	@Column(name="CY_TYP_NAM")
	private String cyTypNam;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	private String remarks;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	public CCyTyp() {
	}

	public String getCyTyp() {
		return this.cyTyp;
	}

	public void setCyTyp(String cyTyp) {
		this.cyTyp = cyTyp;
	}

	public String getCyTypNam() {
		return this.cyTypNam;
	}

	public void setCyTypNam(String cyTypNam) {
		this.cyTypNam = cyTypNam;
	}

	public String getRecNam() {
		return this.recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return this.recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUpdNam() {
		return this.updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return this.updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

}