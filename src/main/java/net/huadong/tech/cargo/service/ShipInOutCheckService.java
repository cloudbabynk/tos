package net.huadong.tech.cargo.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ShipInOutCheckService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipInOutCheck> gridData);
	HdMessageCode saveone(ShipInOutCheck shipInOutCheck);
	ShipInOutCheck findone(String checkId);
	HdEzuiDatagridData findYardIn(HdQuery hdQuery);
	HdMessageCode removeAll(String shipNo, String workTyp);
	HdEzuiDatagridData findShipOut(HdQuery hdQuery);
	HdMessageCode createDB(String shipNo, String workTyp);
	HdEzuiDatagridData findShipIn(HdQuery hdQuery);
	HdMessageCode removeshipInOutCheck(String checkIds);
	HdMessageCode checkShip(String shipNo);
}
