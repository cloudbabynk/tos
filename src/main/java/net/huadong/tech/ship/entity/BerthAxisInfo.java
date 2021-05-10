package net.huadong.tech.ship.entity;

import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.HdShipPicSbcBerth;
import net.huadong.tech.base.entity.HdShipPicSbcBollard;

import java.util.List;

public class BerthAxisInfo {
    private HdShipPicSbcBerth berth;
    private String waterDepth;
    private List<HdShipPicSbcBollard> bollardList;
    
	public HdShipPicSbcBerth getBerth() {
		return berth;
	}
	public void setBerth(HdShipPicSbcBerth berth) {
		this.berth = berth;
	}
	public String getWaterDepth() {
		return waterDepth;
	}
	public void setWaterDepth(String waterDepth) {
		this.waterDepth = waterDepth;
	}
	public List<HdShipPicSbcBollard> getBollardList() {
		return bollardList;
	}
	public void setBollardList(List<HdShipPicSbcBollard> bollardList) {
		this.bollardList = bollardList;
	}

}
