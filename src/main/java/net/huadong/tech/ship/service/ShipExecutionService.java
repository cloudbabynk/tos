package net.huadong.tech.ship.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipExecution;
import net.huadong.tech.ship.entity.ShipThruput;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ShipExecutionService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipExecution> gridData);
	void removeAll(String shipExecutionIds);
	ShipExecution findone(String shipExecutionId);
	HdMessageCode saveone(ShipExecution shipExecution);
	/**
	 * 导入业务数
	 * @param map
	 * @return
	 */
	HdEzuiDatagridData genExecutionByship(Map map);
}
