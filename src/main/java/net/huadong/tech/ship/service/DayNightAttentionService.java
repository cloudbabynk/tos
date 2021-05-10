package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.DayNightAttention;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface DayNightAttentionService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<DayNightAttention> gridData);
	void removeAll(String planIds);
	DayNightAttention findone(String days);
	DayNightAttention findAttention(String days);
	HdMessageCode saveone(DayNightAttention dayNightAttention);
}
