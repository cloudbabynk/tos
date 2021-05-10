package net.huadong.tech.ship.service;

import java.util.List;
import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.ship.entity.ShipThruputRecord;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ShipThruputRecordService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipThruputRecord> gridData);
	void removeAll(String sthruputIds);
	void checkAll(String sthruputIds);
	ShipThruputRecord findone(String sthruputId);
	HdMessageCode saveone(ShipThruputRecord sthruputId);
	HdMessageCode findShipThruputRecord(String sthruputId);
	List<EzTreeBean> findTree();
	HdEzuiDatagridData findQ(HdQuery hdQuery);
	HdMessageCode getShipTueInfo(Map map);
	HdMessageCode getUnitCargo(Map map);
	String sendjt(String sthruputIds);
}
