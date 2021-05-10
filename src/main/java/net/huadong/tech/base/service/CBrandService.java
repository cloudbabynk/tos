package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CBrandService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CBrand> gridData);
	void removeAll(String brandCods);
	void checkAll(String brandCods);
	CBrand findone(String brandCod);
	HdMessageCode saveone(CBrand cBrand);
	HdMessageCode generate(String shipworkTim);
	HdMessageCode findCBrand(String brandEname);
}
