package net.huadong.tech.ship.entity;

import java.sql.Timestamp;

public class ShipTim {
	private String tim;// 几点几分
	//private String date;// 日期 (年月日)
	private double zb;
    private Timestamp date;
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public String getTim() {
		return tim;
	}

	public void setTim(String tim) {
		this.tim = tim;
	}

	public double getZb() {
		return zb;
	}

	public void setZb(double zb) {
		this.zb = zb;
	}

}
