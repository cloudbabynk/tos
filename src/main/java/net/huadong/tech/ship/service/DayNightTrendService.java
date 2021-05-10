package net.huadong.tech.ship.service;

import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.DayNightTrend;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface DayNightTrendService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<DayNightTrend> gridData,String days);
	void removeAll(String planIds);
	Map<String,Object> getDockCodByShipNo(String shipNo); 
	DayNightTrend findone(String planId);
	DayNightTrend findWorkNum(String days, String shipNo);
	HdMessageCode saveone(DayNightTrend dayNightTrend);
}
