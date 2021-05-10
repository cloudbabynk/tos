package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipTrendService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findShipTrendJt(HdQuery hdQuery);
	void updateShipTrendJt();
	HdMessageCode save(HdEzuiSaveDatagridData<ShipTrend> gridData);
	void saveDtedit(HdEzuiSaveDatagridData<ShipTrend> gridData);
	HdMessageCode savejt(HdEzuiSaveDatagridData<ShipTrend> gridData);
	void removeAll(String shipTrendsIds);
	void importShipTrend(String shipTrendsId);
	void importAll(String shipTrendsIds);
	void uploadAll(String shipTrendsIds);
	void dtdelete(String saveDtedit);
	void dtcancle(String shipTrendsId);
	void dtchange(String shipTrendsId);
	ShipTrend findone(String shipTrendsId);
	HdMessageCode saveone(ShipTrend shipTrend);
}
