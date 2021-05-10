package net.huadong.tech.plan.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface PlanGroupService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findft(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<PlanGroup> gridData);

	HdMessageCode saveone(PlanGroup planGroup);
	HdMessageCode ftsaveone(PlanGroup planGroup);
	HdEzuiDatagridData findCCyArea(HdQuery hdQuery);

	void removeAll(String planGroupNo);
	List getUnlockedBay(String cyAreaNo);
	List getRest(String planGroupNo);
	HdEzuiDatagridData findBill(HdQuery hdQuery);
	HdMessageCode impWrokPlan( Map map);
}
