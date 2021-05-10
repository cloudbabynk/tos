package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CShipStat;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CShipStatService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CShipStat> gridData);
	void removeAll(String shipStatCods);
	CShipStat findone(String shipStatCod);
	HdMessageCode saveone(CShipStat cShipStat);
	HdMessageCode findCShipStat(String shipStatCod);
}
