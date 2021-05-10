package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipStatService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipStat> gridData);
	void removeAll(String shipStatIds);
	ShipStat findone(String shipStatId);
	ShipTrend findJgsj(String shipNo);
	ShipStat findHl(String shipNo, String iEId);
	HdMessageCode saveone(ShipStat shipStat);
	void importShipStats(String shipStatIds);
	void importNewShipStats(String shipStatIds);
	void cancelShipStat(String shipStatId);
	void getshipstat(String groupShipNo);
}
