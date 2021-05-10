package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the C_CAR_TYP database table.
 * 
 */
@Entity
@Table(name="C_CAR_TYP")
@NamedQuery(name="CCarTyp.findAll", query="SELECT c FROM CCarTyp c")
public class CCarTyp extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String Y = "1"; //审核状态是
	public static final String N = "0"; //审核状态否
	@Id
	@Column(name="CAR_TYP")
	private String carTyp;

	public String getCarTyp() {
		return carTyp;
	}

	public void setCarTyp(String carTyp) {
		this.carTyp = carTyp;
	}
	@Transient
	private String brandNam;
	
	@Transient
	private String carKindNam;
	@Transient
	private String carFeeTypNamNam;

	@Transient
	private String factoryNam;
	//CAR_FEE_TYP_NAM
	@Column(name="CAR_FEE_TYP_NAM")
	private String carFeeTypNam;
	

	@Column(name="BRAND_COD")
	private String brandCod;

	@Column(name="CAR_KIND")
	private String carKind;
	
	@Column(name="CAR_LEVEL")
	private String carLevel;

	@Column(name="CAR_TYP_NAM")
	private String carTypNam;
	
	@Column(name="CAR_TYP_E_NAM")
	private String carTypENam;

	@Column(name="FACTORY_COD")
	private String factoryCod;

	private BigDecimal height;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;
	
	@Column(name="CHECK_FLAG")
	private String checkFlag;
	
	@Column(name="CAR_FEE_TYP")
	private String carFeeTyp;
	
	@Column(name="SWEPT_VOLUME")
	private BigDecimal sweptVolume;

	@Column(name="\"VOLUMES\"")
	private BigDecimal volumes;

	private BigDecimal weights;

	private BigDecimal width;

	public String getCarFeeTypNamNam() {
		return carFeeTypNamNam;
	}

	public void setCarFeeTypNamNam(String carFeeTypNamNam) {
		this.carFeeTypNamNam = carFeeTypNamNam;
	}

	public CCarTyp() {
	}
	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getCarFeeTyp() {
		return carFeeTyp;
	}

	public void setCarFeeTyp(String carFeeTyp) {
		this.carFeeTyp = carFeeTyp;
	}
	private String remarks;

	public String getBrandNam() {
		return brandNam;
	}

	public String getCarTypENam() {
		return carTypENam;
	}

	public void setCarTypENam(String carTypENam) {
		this.carTypENam = carTypENam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getCarKindNam() {
		return carKindNam;
	}

	public void setCarKindNam(String carKindNam) {
		this.carKindNam = carKindNam;
	}

	public String getFactoryNam() {
		return factoryNam;
	}

	public void setFactoryNam(String factoryNam) {
		this.factoryNam = factoryNam;
	}
	public String getBrandCod() {
		return this.brandCod;
	}

	public void setBrandCod(String brandCod) {
		this.brandCod = brandCod;
	}

	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	public String getCarTypNam() {
		return this.carTypNam;
	}

	public void setCarTypNam(String carTypNam) {
		this.carTypNam = carTypNam;
	}

	public String getFactoryCod() {
		return this.factoryCod;
	}

	public void setFactoryCod(String factoryCod) {
		this.factoryCod = factoryCod;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}



	
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSweptVolume() {
		return this.sweptVolume;
	}

	public void setSweptVolume(BigDecimal sweptVolume) {
		this.sweptVolume = sweptVolume;
	}


	public BigDecimal getVolumes() {
		return this.volumes;
	}

	public void setVolumes(BigDecimal volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getWeights() {
		return this.weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public String getCarFeeTypNam() {
		return carFeeTypNam;
	}

	public void setCarFeeTypNam(String carFeeTypNam) {
		this.carFeeTypNam = carFeeTypNam;
	}

	public String getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}

}