package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.Interface.entity.ShipClassAct;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipClassActService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipClassAct> gridData);
	void removeAll(String scactIds);
	void uploadAll(String scactIds);
	ShipClassAct findone(String scactId);
	HdMessageCode saveone(ShipClassAct bean);
}
