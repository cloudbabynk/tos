package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the C_CAR_VIN database table.
 * 
 */
@Entity
@Table(name="C_CAR_VIN")
@NamedQuery(name="CCarVin.findAll", query="SELECT c FROM CCarVin c")
public class CCarVin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="VIN_ID")
	private String vinId;

	@Column(name="CAR_TYP")
	private String carTyp;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="TYP_COD")
	private String typCod;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	@Column(name="VIN_NO")
	private String vinNo;

	public CCarVin() {
	}

	public String getVinId() {
		return this.vinId;
	}

	public void setVinId(String vinId) {
		this.vinId = vinId;
	}

	public String getCarTyp() {
		return this.carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
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

	public String getTypCod() {
		return this.typCod;
	}

	public void setTypCod(String typCod) {
		this.typCod = typCod;
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

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

}