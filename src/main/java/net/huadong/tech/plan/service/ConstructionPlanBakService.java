package net.huadong.tech.plan.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.plan.entity.ConstructionPlanBak;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ConstructionPlanBakService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ConstructionPlanBak> gridData);
	void removeAll(String planIds);
	ConstructionPlanBak findone(String planId);
	HdMessageCode saveone(ConstructionPlanBak constructionPlan);
}
