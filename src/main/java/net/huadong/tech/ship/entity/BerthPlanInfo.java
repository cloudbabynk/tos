package net.huadong.tech.ship.entity;

import net.huadong.tech.ship.entity.BerthAxisInfo;
import net.huadong.tech.ship.entity.TimeAxisInfo;

import java.util.List;

public class BerthPlanInfo {
    private TimeAxisInfo timeAxisInfo;
    private List<BerthAxisInfo> berthAxisInfoList;
    private List<HdShipPicBerthPlanShipVisit> shipList;

    public TimeAxisInfo getTimeAxisInfo() {
        return timeAxisInfo;
    }

    public void setTimeAxisInfo(TimeAxisInfo timeAxisInfo) {
        this.timeAxisInfo = timeAxisInfo;
    }

    public List<BerthAxisInfo> getBerthAxisInfoList() {
        return berthAxisInfoList;
    }

    public void setBerthAxisInfoList(List<BerthAxisInfo> berthAxisInfoList) {
        this.berthAxisInfoList = berthAxisInfoList;
    }

	public List<HdShipPicBerthPlanShipVisit> getShipList() {
		return shipList;
	}

	public void setShipList(List<HdShipPicBerthPlanShipVisit> shipList) {
		this.shipList = shipList;
	}

}
