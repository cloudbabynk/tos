package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillTallyerClass;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillTallyerClassService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyerClass> gridData,String workbillNo);
	void removeAll(String classCods,String workbillNo);
	WorkBillTallyerClass findone(String damCod);
	HdMessageCode saveone(WorkBillTallyerClass workBillTallyerClass);
}
