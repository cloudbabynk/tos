package net.huadong.tech.work.service;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;

/**
 * @author 
 */
public interface StatisticCountService {
	HdEzuiDatagridData findSc(HdQuery hdQuery);
	HdEzuiDatagridData findScJs(HdQuery hdQuery);
	
}
