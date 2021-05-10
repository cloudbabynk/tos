package net.huadong.tech.map.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import net.huadong.tech.base.entity.CCamera;

import java.util.Date;


/**
 * The persistent class for the C_CY_TYP database table.
 * 
 */
@Entity
@Table(name="C_GIS_WBILL_CAMERA")
public class CGisWbillCamera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String Id;
	@Column(name="WB_ID")
	private String wbId;
	@Column(name="CAM_ID")
	private String camId;
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
	@Column(name="SORT_NUM")
	private BigDecimal sortNum;
	

	public BigDecimal getSortNum() {
		return sortNum;
	}

	public void setSortNum(BigDecimal sortNum) {
		this.sortNum = sortNum;
	}

	@JoinColumn(name = "CAM_ID", referencedColumnName = "ID", updatable = false, insertable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private CCamera  cCamera;
	
	
	
	public CGisWbillCamera() {
	}

	/**
	 * @return the wbId
	 */
	public String getWbId() {
		return wbId;
	}

	/**
	 * @param wbId the wbId to set
	 */
	public void setWbId(String wbId) {
		this.wbId = wbId;
	}

	/**
	 * @return the recNam
	 */
	public String getRecNam() {
		return recNam;
	}

	/**
	 * @param recNam the recNam to set
	 */
	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	/**
	 * @return the recTim
	 */
	public Date getRecTim() {
		return recTim;
	}

	/**
	 * @param recTim the recTim to set
	 */
	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	/**
	 * @return the updNam
	 */
	public String getUpdNam() {
		return updNam;
	}

	/**
	 * @param updNam the updNam to set
	 */
	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	/**
	 * @return the updTim
	 */
	public Date getUpdTim() {
		return updTim;
	}

	/**
	 * @param updTim the updTim to set
	 */
	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the camId
	 */
	public String getCamId() {
		return camId;
	}

	/**
	 * @param camId the camId to set
	 */
	public void setCamId(String camId) {
		this.camId = camId;
	}

	/**
	 * @return the cCamera
	 */
	public CCamera getcCamera() {
		return cCamera;
	}

	/**
	 * @param cCamera the cCamera to set
	 */
	public void setcCamera(CCamera cCamera) {
		this.cCamera = cCamera;
	}

	
	

}