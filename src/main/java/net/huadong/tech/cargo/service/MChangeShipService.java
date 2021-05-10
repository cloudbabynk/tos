package net.huadong.tech.cargo.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.cargo.entity.MChangeShip;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface MChangeShipService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MChangeShip> gridData,String ingateId,String gateNo);
	HdMessageCode saveAll(String portCarNos,String shipbillId,String newTranPortCod,String newDiscPortCod);
	MChangeShip findone(String id);
	HdMessageCode saveone(MChangeShip mChangeShip);
	HdEzuiDatagridData findPL(HdQuery hdQuery);
	HdMessageCode savePL(String shipNos,String billNos, String brandCods, String carTyps,  String cyAreaNos,
			String countNum,String newTranPortCod,String newDiscPortCod,String newShipNo,String newBillNo,int num,String flag,String dockCod);
}
