package net.huadong.tech.plan.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ConstructionPlanService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ConstructionPlan> gridData);
	void removeAll(String planIds);
	ConstructionPlan findone(String planId);
	HdMessageCode saveone(ConstructionPlan constructionPlan);
}
