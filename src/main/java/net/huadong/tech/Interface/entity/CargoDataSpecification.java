package net.huadong.tech.Interface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.commons.net.ntp.TimeStamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.util.Date;


/**
 * The persistent class for the C_BRAND database table.
 * 
 */
@Entity
@Table(name="CARGO_DATA_SPECIFICATION")
@NamedQuery(name="CargoDataSpecification.findAll", query="SELECT c FROM CargoDataSpecification c")
public class CargoDataSpecification implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="X_MD_CARGOID")
	private String xMdCargoid;
	
	@Column(name="X_FIRST_CODE")
	private String xFirstCode;
	
	@Column(name="X_FIRST_NAME")
	private String xFirstName;
	
	@Column(name="X_SECOND_CODE")
	private String xSecondCode;
	
	@Column(name="X_SECOND_NAME")
	private String xSecondName;
	
	@Column(name="X_THIRD_CODE")
	private String xThirdCode;
	
	@Column(name="X_THIRD_NAME")
	private String xThirdName;
	
	@Column(name="X_FOURTH_CODE")
	private String xFourthCode;
	
	@Column(name="X_FOURTH_NAME")
	private String xFourthName;
	
	@Column(name="X_TTL_CARGO_KIND")
	private String xTtlCargoKind;
	
	@Column(name="X_STOP_KIND_CODE")
	private String xStopKindCode;
	
	@Column(name="X_CARGO_TYPE_CODE")
	private String xCargoTypeCode;
	
	@Column(name="X_M_CARGO_KIND_CODE")
	private String xMCargoKindCode;
	
	@Column(name="X_SOURCE_KIND_CODE")
	private String xSourceKindCode;
	
	@Column(name="X_STORE_CARGO_KIND")
	private String xStoreCargoKind;
	
	@Column(name="X_IOYARD_CARGO_KIND")
	private String xIoyardCargoKind;
	
	@Column(name="X_CARGO_KIND_CODE")
	private String xCargoKindCode;
	
	@Column(name="X_INSERT_TIME")
	private Date xInsertTime;
	
	@Column(name="X_UPDATE_TIME")
	private Date xUpdateTime;
	
	@Column(name="X_USED_FLAG")
	private String xUsedFlag;
	
	@Column(name="X_ERROR_FFAG")
	private String xErrorFfag;
	

	public CargoDataSpecification() {
	}


	public String getxMdCargoid() {
		return xMdCargoid;
	}


	public void setxMdCargoid(String xMdCargoid) {
		this.xMdCargoid = xMdCargoid;
	}


	public String getxFirstCode() {
		return xFirstCode;
	}


	public void setxFirstCode(String xFirstCode) {
		this.xFirstCode = xFirstCode;
	}


	public String getxFirstName() {
		return xFirstName;
	}


	public void setxFirstName(String xFirstName) {
		this.xFirstName = xFirstName;
	}


	public String getxSecondCode() {
		return xSecondCode;
	}


	public void setxSecondCode(String xSecondCode) {
		this.xSecondCode = xSecondCode;
	}


	public String getxSecondName() {
		return xSecondName;
	}


	public void setxSecondName(String xSecondName) {
		this.xSecondName = xSecondName;
	}


	public String getxThirdCode() {
		return xThirdCode;
	}


	public void setxThirdCode(String xThirdCode) {
		this.xThirdCode = xThirdCode;
	}


	public String getxThirdName() {
		return xThirdName;
	}


	public void setxThirdName(String xThirdName) {
		this.xThirdName = xThirdName;
	}


	public String getxFourthCode() {
		return xFourthCode;
	}


	public void setxFourthCode(String xFourthCode) {
		this.xFourthCode = xFourthCode;
	}


	public String getxFourthName() {
		return xFourthName;
	}


	public void setxFourthName(String xFourthName) {
		this.xFourthName = xFourthName;
	}


	public String getxTtlCargoKind() {
		return xTtlCargoKind;
	}


	public void setxTtlCargoKind(String xTtlCargoKind) {
		this.xTtlCargoKind = xTtlCargoKind;
	}


	public String getxStopKindCode() {
		return xStopKindCode;
	}


	public void setxStopKindCode(String xStopKindCode) {
		this.xStopKindCode = xStopKindCode;
	}


	public String getxCargoTypeCode() {
		return xCargoTypeCode;
	}


	public void setxCargoTypeCode(String xCargoTypeCode) {
		this.xCargoTypeCode = xCargoTypeCode;
	}


	public String getxMCargoKindCode() {
		return xMCargoKindCode;
	}


	public void setxMCargoKindCode(String xMCargoKindCode) {
		this.xMCargoKindCode = xMCargoKindCode;
	}


	public String getxSourceKindCode() {
		return xSourceKindCode;
	}


	public void setxSourceKindCode(String xSourceKindCode) {
		this.xSourceKindCode = xSourceKindCode;
	}


	public String getxStoreCargoKind() {
		return xStoreCargoKind;
	}


	public void setxStoreCargoKind(String xStoreCargoKind) {
		this.xStoreCargoKind = xStoreCargoKind;
	}


	public String getxIoyardCargoKind() {
		return xIoyardCargoKind;
	}


	public void setxIoyardCargoKind(String xIoyardCargoKind) {
		this.xIoyardCargoKind = xIoyardCargoKind;
	}


	public String getxCargoKindCode() {
		return xCargoKindCode;
	}


	public void setxCargoKindCode(String xCargoKindCode) {
		this.xCargoKindCode = xCargoKindCode;
	}


	public Date getxInsertTime() {
		return xInsertTime;
	}


	public void setxInsertTime(Date xInsertTime) {
		this.xInsertTime = xInsertTime;
	}


	public Date getxUpdateTime() {
		return xUpdateTime;
	}


	public void setxUpdateTime(Date xUpdateTime) {
		this.xUpdateTime = xUpdateTime;
	}


	public String getxUsedFlag() {
		return xUsedFlag;
	}


	public void setxUsedFlag(String xUsedFlag) {
		this.xUsedFlag = xUsedFlag;
	}


	public String getxErrorFfag() {
		return xErrorFfag;
	}


	public void setxErrorFfag(String xErrorFfag) {
		this.xErrorFfag = xErrorFfag;
	}


	public CargoDataSpecification(String xMdCargoid, String xFirstCode, String xFirstName, String xSecondCode,
			String xSecondName, String xThirdCode, String xThirdName, String xFourthCode, String xFourthName,
			String xTtlCargoKind, String xStopKindCode, String xCargoTypeCode, String xMCargoKindCode,
			String xSourceKindCode, String xStoreCargoKind, String xIoyardCargoKind, String xCargoKindCode,
			Timestamp xInsertTime, Timestamp xUpdateTime, String xUsedFlag, String xErrorFfag) {
		super();
		this.xMdCargoid = xMdCargoid;
		this.xFirstCode = xFirstCode;
		this.xFirstName = xFirstName;
		this.xSecondCode = xSecondCode;
		this.xSecondName = xSecondName;
		this.xThirdCode = xThirdCode;
		this.xThirdName = xThirdName;
		this.xFourthCode = xFourthCode;
		this.xFourthName = xFourthName;
		this.xTtlCargoKind = xTtlCargoKind;
		this.xStopKindCode = xStopKindCode;
		this.xCargoTypeCode = xCargoTypeCode;
		this.xMCargoKindCode = xMCargoKindCode;
		this.xSourceKindCode = xSourceKindCode;
		this.xStoreCargoKind = xStoreCargoKind;
		this.xIoyardCargoKind = xIoyardCargoKind;
		this.xCargoKindCode = xCargoKindCode;
		this.xInsertTime = xInsertTime;
		this.xUpdateTime = xUpdateTime;
		this.xUsedFlag = xUsedFlag;
		this.xErrorFfag = xErrorFfag;
	}
	
	



}