package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MBillVinService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MBillVin> gridData);
	void removeAll(String billVinIds);
	HdMessageCode importAll(String shipNo);
	MBillVin findone(String billVinId);
//	HdMessageCode saveone(MBillVin mBillVin);
	HdMessageCode antiBill(String shipNo);
}
