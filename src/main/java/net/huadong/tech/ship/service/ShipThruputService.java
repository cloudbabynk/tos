package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipThruput;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ShipThruputService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipThruput> gridData);
	void removeAll(String shipThruputIds);
	ShipThruput findone(String shipThruputId);
	HdMessageCode saveone(ShipThruput shipThruput);
	void sendData(String shipNo);
	void upload(String shipNo);
	ShipThruput findData(String shipNo);
	void removeByshipNo(String shipNo);
}
