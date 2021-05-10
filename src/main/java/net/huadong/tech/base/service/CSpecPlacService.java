package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CSpecPlac;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CSpecPlacService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CSpecPlac> gridData);
	void removeAll(String specPlacs);
	CSpecPlac findone(String specPlac);
	HdMessageCode saveone(CSpecPlac cSpecPlac);
	HdMessageCode findCSpecPlac(String specPlac);
}
