package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataHis;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CShipDataService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findHisData(HdQuery hdQuery);
	HdEzuiDatagridData findJt(HdQuery hdQuery);
	HdEzuiDatagridData findNewJt(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CShipData> gridData);
	HdMessageCode saveData(HdEzuiSaveDatagridData<CShipData> gridData,String shipCodId);
	void removeAll(String shipCodIds);
	CShipData findone(String shipCodId);
	CShipDataHis findHis(String id);
	CShipData findData(String eShipNam, String shipImo, String cShipNam);
	HdMessageCode saveone(CShipData cShipData);
}
