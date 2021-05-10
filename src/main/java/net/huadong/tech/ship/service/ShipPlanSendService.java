package net.huadong.tech.ship.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.MFeeInterfaceBerth;
import net.huadong.tech.Interface.entity.MFeeInterfaceVoyage;
import net.huadong.tech.Interface.entity.ShipPlanSend;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @yl
 */
public interface ShipPlanSendService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findVoyage(HdQuery hdQuery);
	HdEzuiDatagridData findBerth(HdQuery hdQuery);
	public HdMessageCode procVoyage(String shipNo, String iEId) ;
	public HdMessageCode procYundi(String ydId) ;
	public HdMessageCode procBerth(String shipNo, String iEId) ;
	HdMessageCode save(HdEzuiSaveDatagridData<ShipPlanSend> gridData);
	HdMessageCode saveVoyage(HdEzuiSaveDatagridData<MFeeInterfaceVoyage> gridData);
	HdMessageCode saveBerth(HdEzuiSaveDatagridData<MFeeInterfaceBerth> gridData);
	void removeAll(String spsendIds);
	void uploadAll(String spsendIds);
	ShipPlanSend findone(String spsendId);
	HdMessageCode saveone(ShipPlanSend bean);
}
