package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the C_COLOR database table.
 * 
 */
@Entity
@Table(name="C_COLOR")
@NamedQuery(name="CColor.findAll", query="SELECT c FROM CColor c")
public class CColor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COLOR_COD")
	private String colorCod;

	@Column(name="COLOR_NAM")
	private String colorNam;

	@Column(name="REC_NAM")
	private String recNam;

	@Temporal(TemporalType.DATE)
	@Column(name="REC_TIM")
	private Date recTim;

	@Column(name="UPD_NAM")
	private String updNam;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_TIM")
	private Date updTim;

	public CColor() {
	}

	public String getColorCod() {
		return this.colorCod;
	}

	public void setColorCod(String colorCod) {
		this.colorCod = colorCod;
	}

	public String getColorNam() {
		return this.colorNam;
	}

	public void setColorNam(String colorNam) {
		this.colorNam = colorNam;
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