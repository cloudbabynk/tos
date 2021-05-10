package net.huadong.tech.cargo.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the STATISTIC_COUNT database table.
 * 
 */
@Entity
@Table(name="STATISTIC_COUNT")
@NamedQuery(name="StatisticCount.findAll", query="SELECT s FROM StatisticCount s")
public class StatisticCount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ioyardid;

	private String brandcode;
	
	@Transient
	private String brandNam;

	private String cargocode;
	
	@Transient
	private String cargoNam;
	
	@Transient
	private String brandName;
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	private String cargoid;

	private String cargomark;

	private BigDecimal cargonum;

	private BigDecimal cargowgt;

	private String cneecode;

	private String cnorcode;

	private String consigncode;

	private String consignorcode;

	private String description;

	private String format;
    //货代名称
	private String forwardercode;
	@Transient
	private String forwarderNam;

	private String ieflag;
	@Transient
	private String ieNam;
	
	private String informid;

	private String ioyarddate;

	private String ioyardflag;
	@Transient
	private String ioyardflagNam;

	private String ioyardway;
	@Transient
	private String ioyardwayNam;
	
	private String jobid;
	
	private String flowDir;

	private String locationcode;

	private String matacode;

	private String origincode;

	private String packagecode;

	private String piecesno;

	private String pilecode;

	private String shipid;

	private String shipname;

	private String sublocationcode;

	private String submitflag;

	private String submitname;

	private String submittime;

	private String svoyageid;

	private String teamorgnid;
	@Transient
	private String teamOrgnNam;

	private String tradeflag;
	@Transient
	private String tradeFlagNam;
	
	private String voyage;

	private String yardcode;
	
	@Column(name="SHIP_NO")
	private String shipNo;
	
	@Column(name="BILL_NO")
	private String billNo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public StatisticCount() {
	}

	public String getIoyardid() {
		return this.ioyardid;
	}

	public void setIoyardid(String ioyardid) {
		this.ioyardid = ioyardid;
	}

	public String getFlowDir() {
		return flowDir;
	}

	public void setFlowDir(String flowDir) {
		this.flowDir = flowDir;
	}

	public String getBrandcode() {
		return this.brandcode;
	}

	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

	public String getCargocode() {
		return this.cargocode;
	}

	public void setCargocode(String cargocode) {
		this.cargocode = cargocode;
	}

	public String getCargoid() {
		return this.cargoid;
	}

	public void setCargoid(String cargoid) {
		this.cargoid = cargoid;
	}

	public String getCargomark() {
		return this.cargomark;
	}

	public void setCargomark(String cargomark) {
		this.cargomark = cargomark;
	}

	public BigDecimal getCargonum() {
		return this.cargonum;
	}

	public void setCargonum(BigDecimal cargonum) {
		this.cargonum = cargonum;
	}

	public BigDecimal getCargowgt() {
		return this.cargowgt;
	}

	public void setCargowgt(BigDecimal cargowgt) {
		this.cargowgt = cargowgt;
	}

	public String getCneecode() {
		return this.cneecode;
	}

	public void setCneecode(String cneecode) {
		this.cneecode = cneecode;
	}

	public String getCnorcode() {
		return this.cnorcode;
	}

	public void setCnorcode(String cnorcode) {
		this.cnorcode = cnorcode;
	}

	public String getConsigncode() {
		return this.consigncode;
	}

	public void setConsigncode(String consigncode) {
		this.consigncode = consigncode;
	}

	public String getConsignorcode() {
		return this.consignorcode;
	}

	public void setConsignorcode(String consignorcode) {
		this.consignorcode = consignorcode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getForwardercode() {
		return this.forwardercode;
	}

	public void setForwardercode(String forwardercode) {
		this.forwardercode = forwardercode;
	}

	public String getIeflag() {
		return this.ieflag;
	}

	public void setIeflag(String ieflag) {
		this.ieflag = ieflag;
	}

	public String getInformid() {
		return this.informid;
	}

	public void setInformid(String informid) {
		this.informid = informid;
	}

	public String getIoyarddate() {
		return this.ioyarddate;
	}

	public void setIoyarddate(String ioyarddate) {
		this.ioyarddate = ioyarddate;
	}

	public String getIoyardflag() {
		return this.ioyardflag;
	}

	public void setIoyardflag(String ioyardflag) {
		this.ioyardflag = ioyardflag;
	}

	public String getIoyardway() {
		return this.ioyardway;
	}

	public void setIoyardway(String ioyardway) {
		this.ioyardway = ioyardway;
	}

	public String getJobid() {
		return this.jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getLocationcode() {
		return this.locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getMatacode() {
		return this.matacode;
	}

	public void setMatacode(String matacode) {
		this.matacode = matacode;
	}

	public String getOrigincode() {
		return this.origincode;
	}

	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}

	public String getPackagecode() {
		return this.packagecode;
	}

	public void setPackagecode(String packagecode) {
		this.packagecode = packagecode;
	}

	public String getPiecesno() {
		return this.piecesno;
	}

	public void setPiecesno(String piecesno) {
		this.piecesno = piecesno;
	}

	public String getPilecode() {
		return this.pilecode;
	}

	public void setPilecode(String pilecode) {
		this.pilecode = pilecode;
	}

	public String getShipid() {
		return this.shipid;
	}

	public void setShipid(String shipid) {
		this.shipid = shipid;
	}

	public String getShipname() {
		return this.shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public String getSublocationcode() {
		return this.sublocationcode;
	}

	public void setSublocationcode(String sublocationcode) {
		this.sublocationcode = sublocationcode;
	}

	public String getSubmitflag() {
		return this.submitflag;
	}

	public void setSubmitflag(String submitflag) {
		this.submitflag = submitflag;
	}

	public String getSubmitname() {
		return this.submitname;
	}

	public void setSubmitname(String submitname) {
		this.submitname = submitname;
	}

	public String getSubmittime() {
		return this.submittime;
	}

	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}

	public String getSvoyageid() {
		return this.svoyageid;
	}

	public void setSvoyageid(String svoyageid) {
		this.svoyageid = svoyageid;
	}

	public String getTeamorgnid() {
		return this.teamorgnid;
	}

	public void setTeamorgnid(String teamorgnid) {
		this.teamorgnid = teamorgnid;
	}

	public String getTradeflag() {
		return this.tradeflag;
	}

	public void setTradeflag(String tradeflag) {
		this.tradeflag = tradeflag;
	}

	public String getVoyage() {
		return this.voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getYardcode() {
		return this.yardcode;
	}

	public void setYardcode(String yardcode) {
		this.yardcode = yardcode;
	}

	public String getBrandNam() {
		return brandNam;
	}

	public void setBrandNam(String brandNam) {
		this.brandNam = brandNam;
	}

	public String getCargoNam() {
		return cargoNam;
	}

	public void setCargoNam(String cargoNam) {
		this.cargoNam = cargoNam;
	}

	public String getForwarderNam() {
		return forwarderNam;
	}

	public void setForwarderNam(String forwarderNam) {
		this.forwarderNam = forwarderNam;
	}

	public String getIeNam() {
		return ieNam;
	}

	public void setIeNam(String ieNam) {
		this.ieNam = ieNam;
	}


	public String getIoyardflagNam() {
		return ioyardflagNam;
	}

	public void setIoyardflagNam(String ioyardflagNam) {
		this.ioyardflagNam = ioyardflagNam;
	}

	public String getIoyardwayNam() {
		return ioyardwayNam;
	}

	public void setIoyardwayNam(String ioyardwayNam) {
		this.ioyardwayNam = ioyardwayNam;
	}

	public String getTeamOrgnNam() {
		return teamOrgnNam;
	}

	public void setTeamOrgnNam(String teamOrgnNam) {
		this.teamOrgnNam = teamOrgnNam;
	}

	public String getTradeFlagNam() {
		return tradeFlagNam;
	}

	public void setTradeFlagNam(String tradeFlagNam) {
		this.tradeFlagNam = tradeFlagNam;
	}

}