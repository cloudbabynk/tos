package net.huadong.tech.map.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="C_GIS_WBILL")
public class CGisWbill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WB_ID")
	private String wbId;
	@Column(name="WB_NAM")
	private String wbNam;
	@Column(name="POS")
	private String pos;	
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
	
    @JoinColumn(name = "WB_ID", referencedColumnName = "WB_ID", updatable = false, insertable = false)
	@OneToMany(fetch = FetchType.LAZY)
	private List<CGisWbillCamera> cGisWbillCamera;
    
    

	public CGisWbill() {
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
	 * @return the wbNam
	 */
	public String getWbNam() {
		return wbNam;
	}

	/**
	 * @param wbNam the wbNam to set
	 */
	public void setWbNam(String wbNam) {
		this.wbNam = wbNam;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
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
	 * @return the cGisWbillCamera
	 */
	public List<CGisWbillCamera> getcGisWbillCamera() {
		return cGisWbillCamera;
	}

	/**
	 * @param cGisWbillCamera the cGisWbillCamera to set
	 */
	public void setcGisWbillCamera(List<CGisWbillCamera> cGisWbillCamera) {
		this.cGisWbillCamera = cGisWbillCamera;
	}

	
	

}