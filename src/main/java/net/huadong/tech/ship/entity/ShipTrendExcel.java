package net.huadong.tech.ship.entity;

import java.math.BigDecimal;

public class ShipTrendExcel {
private String dockNam;
private String berthNam;
private String cShipNam;
private BigDecimal shipGrossWGt;
private String countryNam;
private String toPortDate;
private String toPortTime;
private String leavePortDate;
private String leavePortTime;
public String getDockNam() {
	return dockNam;
}
public void setDockNam(String dockNam) {
	this.dockNam = dockNam;
}
public String getBerthNam() {
	return berthNam;
}
public void setBerthNam(String berthNam) {
	this.berthNam = berthNam;
}
public String getcShipNam() {
	return cShipNam;
}
public void setcShipNam(String cShipNam) {
	this.cShipNam = cShipNam;
}
public BigDecimal getShipGrossWGt() {
	return shipGrossWGt;
}
public void setShipGrossWGt(BigDecimal shipGrossWGt) {
	this.shipGrossWGt = shipGrossWGt;
}
public String getCountryNam() {
	return countryNam;
}
public void setCountryNam(String countryNam) {
	this.countryNam = countryNam;
}
public String getToPortDate() {
	return toPortDate;
}
public void setToPortDate(String toPortDate) {
	this.toPortDate = toPortDate;
}
public String getToPortTime() {
	return toPortTime;
}
public void setToPortTime(String toPortTime) {
	this.toPortTime = toPortTime;
}
public String getLeavePortDate() {
	return leavePortDate;
}
public void setLeavePortDate(String leavePortDate) {
	this.leavePortDate = leavePortDate;
}
public String getLeavePortTime() {
	return leavePortTime;
}
public void setLeavePortTime(String leavePortTime) {
	this.leavePortTime = leavePortTime;
}

}
