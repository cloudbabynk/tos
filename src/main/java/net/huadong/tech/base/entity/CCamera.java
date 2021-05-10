package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.UuidGenerator;


/**
 * The persistent class for the C_CAMERA database table.
 * 
 */
@Entity
@Table(name="C_CAMERA")
@NamedQuery(name="CCamera.findAll", query="SELECT c FROM CCamera c")
public class CCamera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
	@Column(name="ID")
	private String id;

	@Column(name="CAMERA_NAM")
	private String cameraNam;

	@Column(name="CAMERA_TYP")
	private String cameraTyp;

	@Column(name="CHANNEL_NO")
	private BigDecimal channelNo;

	@Column(name="COMPANY_COD")
	private String companyCod;

	@Column(name="OVER_LAP")
	private String overLap;
	
	@Column(name="URL")
	private String url;

	@Column(name="STATUS_ID")
	private String statusId;
	
	@Column(name="XPOS")
	private String xpos;
	
	@Column(name="YPOS")
	private String ypos;

	public CCamera() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCameraNam() {
		return this.cameraNam;
	}

	public void setCameraNam(String cameraNam) {
		this.cameraNam = cameraNam;
	}

	public String getCameraTyp() {
		return this.cameraTyp;
	}

	public void setCameraTyp(String cameraTyp) {
		this.cameraTyp = cameraTyp;
	}

	public BigDecimal getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(BigDecimal channelNo) {
		this.channelNo = channelNo;
	}


	public String getCompanyCod() {
		return companyCod;
	}

	public void setCompanyCod(String companyCod) {
		this.companyCod = companyCod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOverLap() {
		return this.overLap;
	}

	public void setOverLap(String overLap) {
		this.overLap = overLap;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getXpos() {
		return this.xpos;
	}

	public void setXpos(String xpos) {
		this.xpos = xpos;
	}

	public String getYpos() {
		return this.ypos;
	}

	public void setYpos(String ypos) {
		this.ypos = ypos;
	}

}