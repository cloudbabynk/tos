package net.huadong.tech.base.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CHoliday;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CHolidayService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CHoliday> hdEzuiSaveDatagridData);
	void removeAll(String hIds);
	void initHoliday(String year);
	CHoliday findone(String hId);
	HdMessageCode saveone(CHoliday cHoliday);
}
