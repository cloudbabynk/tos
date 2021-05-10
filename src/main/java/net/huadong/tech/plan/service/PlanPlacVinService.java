package net.huadong.tech.plan.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface PlanPlacVinService {

	HdEzuiDatagridData find(HdQuery hdQuery);

	HdMessageCode save(HdEzuiSaveDatagridData<PlanPlacVin> gridData);

	HdMessageCode saveone(PlanPlacVin planPlacVin);

	void removeAll(String vinNo);

}
