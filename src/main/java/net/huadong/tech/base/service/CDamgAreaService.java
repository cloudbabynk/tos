package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDamgArea;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CDamgAreaService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CDamgArea> gridData);
	void removeAll(String damgAreaCods);
	CDamgArea findone(String damgAreaCod);
	HdMessageCode saveone(CDamgArea cDamgArea);
	HdMessageCode findCDamgArea(String damgAreaCod);
}
