package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipReportService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipReport> gridData);
	void removeAll(String shipRptIds);
	void uploadAll(String shipRptIds);
	ShipReport findone(String shipRptId);
	HdMessageCode saveone(ShipReport shipReport);
}
