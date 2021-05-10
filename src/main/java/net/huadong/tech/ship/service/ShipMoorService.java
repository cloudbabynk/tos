package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipMoor;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface ShipMoorService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipMoor> gridData);
	void removeAll(String shipMoorIds);
	ShipMoor findone(String shipMoorId);
	HdMessageCode saveone(ShipMoor shipMoor);
}
