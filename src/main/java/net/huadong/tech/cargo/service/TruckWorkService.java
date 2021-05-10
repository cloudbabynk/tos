package net.huadong.tech.cargo.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.work.entity.CargoInfo;

/**
 * @author 
 */
public interface TruckWorkService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findHycz(HdQuery hdQuery);
	HdEzuiDatagridData findJglh(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<TruckWork> gridData,String ingateId,String gateNo);
	HdMessageCode saveHycz(String ingateId,String gateNo);
	void removeAll(String ids);
	TruckWork findone(String id);
	HdMessageCode saveJglh(CargoInfo cargoInfo,String type);
	HdMessageCode saveJgsclh(CargoInfo cargoInfo);
	HdMessageCode updateJgsclh(String shipNo, String billNos, String newBillNo);
	HdMessageCode updateBillNo(CargoInfo cargoInfo);
	HdMessageCode updateJgsclhBill(CargoInfo cargoInfo);
	HdMessageCode saveAll(String portCarNos,CargoInfo cargoInfo);
	HdMessageCode saveAllSglh(CargoInfo cargoInfo);
	HdMessageCode saveone(TruckWork truckWork,String driver,String lengthOverId,String widthOverId,String useMachId,String useWorkerId);
}
