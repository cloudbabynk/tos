package net.huadong.tech.work.service;

import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkQueueService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findZclh(HdQuery hdQuery);
	HdEzuiDatagridData findZcnmlh(HdQuery hdQuery);
	HdEzuiDatagridData findPl(HdQuery hdQuery);
	HdMessageCode findWorkCharts(Map mapPam);
	HdMessageCode findWorkCharts1(Map mapPam);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkQueue> gridData);
	HdEzuiDatagridData findWorkPlan(HdQuery hdQuery);
	void generatewq(WorkQueue workQueue);
	void generatewq2(WorkQueue workQueue);
	void generatewq3(WorkQueue workQueue);
}
