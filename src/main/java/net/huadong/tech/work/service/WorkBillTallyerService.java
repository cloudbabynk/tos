package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillTallyer;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillTallyerService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyer> gridData,String workbillNo);
	void removeAll(String classCods,String workbillNo);
	WorkBillTallyer findone(String damCod);
	HdMessageCode saveone(WorkBillTallyer workBillTallyerClass);
}
