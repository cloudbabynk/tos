package net.huadong.tech.ship.service;
import net.huadong.tech.ship.entity.ShipLoadPlan;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.work.entity.WorkQueue;

/**
 * @author 
 */
public interface ShipLoadPlanService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findShip(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipLoadPlan> gridData);
	void removeAll(String planNos);
	ShipLoadPlan findone(String planNo);
	HdMessageCode saveone(ShipLoadPlan ShipLoadPlan);
	HdMessageCode findShipLoadPlan(String planNo);
	HdEzuiDatagridData findPortCar(HdQuery hdQuery);
	HdEzuiDatagridData findShipBill(HdQuery hdQuery);
	HdMessageCode genWorkQueue(WorkQueue workQueue);
	List<EzTreeBean> findTreeslp();
	void genWorkQueue(String planNos);
}
