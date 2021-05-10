package net.huadong.tech.shipbill.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.AnnualWorkPlan;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface AnnualWorkPlanService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<AnnualWorkPlan> gridData);
	void removeAll(String planIds);
	AnnualWorkPlan findone(String planId);
	HdMessageCode saveone(AnnualWorkPlan annualWorkPlan);
}
