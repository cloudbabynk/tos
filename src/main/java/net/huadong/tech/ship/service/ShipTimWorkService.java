package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.ship.entity.ShipTimWork;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ShipTimWorkService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipTimWork> gridData);
	void removeAll(String shipTimWorkIds);
	ShipTimWork findone(String areaCod);
	HdMessageCode saveone(ShipTimWork cArea);
}
