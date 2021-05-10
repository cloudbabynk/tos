package net.huadong.tech.map.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.map.entity.CGisWbill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CGisWbillService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CGisWbill> gridData);
	void removeAll(String ids);
	CGisWbill findone(String id);
	HdMessageCode saveone(CGisWbill cgiswbill);
}
