package net.huadong.tech.damage.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CAR_DAMAGE database table.
 * 
 */
@Entity
@Table(name="CAR_DAMAGE")
@NamedQuery(name="CarDamage.findAll", query="SELECT c FROM CarDamage c")
public class CarDamage  extends AuditEntityBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CARDAMAG_ID")
	private String cardamagId;

	@Column(name="DAM_AREA")
	private String damArea;

	@Column(name="DAM_COD")
	private String damCod;

	@Column(name="DAM_LEVEL")
	private String damLevel;

	private String incharge;

	@Column(name="PORT_CAR_NO")
	private BigDecimal portCarNo;


	@Column(name="REG_POST")
	private String regPost;

	@Column(name="VIN_NO")
	private String vinNo;

	public CarDamage() {
	}

	public String getCardamagId() {
		return this.cardamagId;
	}

	public void setCardamagId(String cardamagId) {
		this.cardamagId = cardamagId;
	}

	public String getDamArea() {
		return this.damArea;
	}

	public void setDamArea(String damArea) {
		this.damArea = damArea;
	}

	public String getDamCod() {
		return this.damCod;
	}

	public void setDamCod(String damCod) {
		this.damCod = damCod;
	}

	public String getDamLevel() {
		return this.damLevel;
	}

	public void setDamLevel(String damLevel) {
		this.damLevel = damLevel;
	}

	public String getIncharge() {
		return this.incharge;
	}

	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}

	public BigDecimal getPortCarNo() {
		return this.portCarNo;
	}

	public void setPortCarNo(BigDecimal portCarNo) {
		this.portCarNo = portCarNo;
	}


	public String getRegPost() {
		return this.regPost;
	}

	public void setRegPost(String regPost) {
		this.regPost = regPost;
	}


	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

}