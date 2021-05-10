package net.huadong.tech.plan.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface PlanRangeService {

	HdEzuiDatagridData find(HdQuery hdQuery);

	HdMessageCode save(HdEzuiSaveDatagridData<PlanRange> gridData);

	HdMessageCode saveone(PlanRange planRange);
	void removeAll(String ids); 
	HdMessageCode update(PlanRange planRange);

}
