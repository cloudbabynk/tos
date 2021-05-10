package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.DayNightPlan;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface DayNightPlanService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<DayNightPlan> gridData,String days,String shipNo,String dockCod);
	void removeAll(String planIds);
	DayNightPlan findone(String planId);
	HdMessageCode saveone(DayNightPlan dayNightPlan);
	void uploadDayNightPlans(String planIds);
}
