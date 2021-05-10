package net.huadong.tech.ship.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.Interface.entity.ShipTeam;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipTeamService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findTeamJt(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipTeam> gridData);
	void removeAll(String steamIds);
	void uploadAll(String steamIds);
	ShipTeam findone(String steamId);
	HdMessageCode saveone(ShipTeam bean);
}
