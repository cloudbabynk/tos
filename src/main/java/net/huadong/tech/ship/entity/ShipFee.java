package net.huadong.tech.ship.entity;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ShipFee {
	//卸船理货
    private String jkhc;
    private String ckhc;
    private String jkmt;
    private String ckmt;
    private String shipNo;
    private BigDecimal shipNetWgt;
	public String getJkhc() {
		return jkhc;
	}
	public void setJkhc(String jkhc) {
		this.jkhc = jkhc;
	}
	public String getCkhc() {
		return ckhc;
	}
	public void setCkhc(String ckhc) {
		this.ckhc = ckhc;
	}
	public String getJkmt() {
		return jkmt;
	}
	public void setJkmt(String jkmt) {
		this.jkmt = jkmt;
	}
	public String getCkmt() {
		return ckmt;
	}
	public void setCkmt(String ckmt) {
		this.ckmt = ckmt;
	}
	public BigDecimal getShipNetWgt() {
		return shipNetWgt;
	}
	public void setShipNetWgt(BigDecimal shipNetWgt) {
		this.shipNetWgt = shipNetWgt;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

}
