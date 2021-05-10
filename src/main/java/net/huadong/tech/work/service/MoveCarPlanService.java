package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MoveCarPlanService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MoveCarPlan> gridData);
	void removeAll(String moveplanIds);
	MoveCarPlan findone(String damCod);
	HdMessageCode saveone(MoveCarPlan moveCarPlan);
	HdMessageCode saveAll(String moveplanIds,String workNam,String driver,String planPlac);
	HdEzuiDatagridData findB(HdQuery hdQuery);
}
