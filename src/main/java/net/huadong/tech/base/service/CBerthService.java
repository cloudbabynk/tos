package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CBerthService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findGs(HdQuery hdQuery);
	HdEzuiDatagridData findShipstat(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CBerth> gridData);
	void removeAll(String damCods);
	CBerth findone(String damCod);
	HdMessageCode saveone(CBerth cBerth);
	HdMessageCode findCBerth(String berthCod);
}
