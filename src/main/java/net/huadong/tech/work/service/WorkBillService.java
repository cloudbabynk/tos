package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBill> gridData);
	void removeAll(String workbillNos);
	WorkBill findone(String workbillNo);
	HdMessageCode saveone(WorkBill workBill);
	void generate(String workbillNo);
	void uploadWorkBills(String workbillNos);
}
