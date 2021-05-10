package net.huadong.tech.ship.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipWorkman;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ShipWorkManService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipWorkman> gridData);
	void removeAll(String shipWorkmanIds);
	ShipWorkman findone(String shipWorkmanId);
	HdMessageCode saveone(ShipWorkman shipWorkman);
	HdEzuiDatagridData genExecutionByship(Map map);
}
