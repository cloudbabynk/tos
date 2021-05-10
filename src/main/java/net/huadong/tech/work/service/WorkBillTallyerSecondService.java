package net.huadong.tech.work.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillTallyerSecond;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface WorkBillTallyerSecondService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findTallyer(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkBillTallyerSecond> gridData,String workbillNo,String classCod);
	void removeAll(String classCods,String workbillNo);
	WorkBillTallyerSecond findone(String damCod);
	HdMessageCode removeone(WorkBillTallyerSecond workBillTallyerSecond);
	HdMessageCode saveone(WorkBillTallyerSecond workBillTallyerSecond);
}
