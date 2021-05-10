package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the C_CARGO database table.
 * 
 */
@Entity
@Table(name="C_CARGO")
@NamedQuery(name="CCargo.findAll", query="SELECT c FROM CCargo c")
public class CCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CARGO_ID")
	private String cargoId;

	@Column(name="C_CARGO_NAM")
	private String cCargoNam;

	@Column(name="CARGO_COD")
	private String cargoCod;

	@Column(name="CARGO_KIND_COD")
	private String cargoKindCod;

	@Column(name="CARGO_SHORT")
	private String cargoShort;

	@Column(name="E_CARGO_NAM")
	private String eCargoNam;

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

	public CCargo() {
	}

	public String getCargoId() {
		return this.cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public String getCCargoNam() {
		return this.cCargoNam;
	}

	public void setCCargoNam(String cCargoNam) {
		this.cCargoNam = cCargoNam;
	}

	public String getCargoCod() {
		return this.cargoCod;
	}

	public void setCargoCod(String cargoCod) {
		this.cargoCod = cargoCod;
	}

	public String getCargoKindCod() {
		return this.cargoKindCod;
	}

	public void setCargoKindCod(String cargoKindCod) {
		this.cargoKindCod = cargoKindCod;
	}

	public String getCargoShort() {
		return this.cargoShort;
	}

	public void setCargoShort(String cargoShort) {
		this.cargoShort = cargoShort;
	}

	public String getECargoNam() {
		return this.eCargoNam;
	}

	public void setECargoNam(String eCargoNam) {
		this.eCargoNam = eCargoNam;
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