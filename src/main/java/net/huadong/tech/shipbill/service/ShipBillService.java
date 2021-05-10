package net.huadong.tech.shipbill.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.entity.ShipBillRecord;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.work.entity.CargoInfo;
/**
 * @author 
 */
public interface ShipBillService {
	HdEzuiDatagridData findShipVoyage(HdQuery hdQuery);
	HdEzuiDatagridData findShipBillCar(HdQuery hdQuery);
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode saveone(ShipBill shipBill);
	void removeAll(String shipbillId);
	List<EzTreeBean> findTree(String iEId,String tradeId);
	HdMessageCode save(HdEzuiSaveDatagridData<ShipBill> gridData);
	HdEzuiDatagridData findSBQuery(HdQuery hdQuery);
	ShipBill findone(String shipbillId);
	ShipBill findShipBill(CargoInfo cargoInfo);
	ShipBillRecord findShipBillRecord(CargoInfo cargoInfo);
	ShipBill getShipBillInfo(String billNo);
	List findTreec(String iEId);
	void genbillcar(String billNo);
	void createData(String shipNo,String iEId,String tradeId);
	void async(String shipbillId,String dockCod);
	void exitCustom(String shipbillId);
	void generatebcpc(String billNo);
	ShipBill searchShipBill(String billNo);
	HdMessageCode copy(ShipBill shipBill);
	List<EzTreeBean> gentreerep();
	String uploadshipbill(String shipbillId,String dockCod);
	/**
	 * 
	 * @param shipNo
	 * @param iEId
	 * @return
	 */
	List<ShipBill> getShipBillByShip(String shipNo,String iEId);

	HdMessageCode paperBind(String shipNos, String billNos,String carNums);
	HdMessageCode genPaper(String shipNos, String billNos,String carNums);
	HdMessageCode dealForce(String shipNos, String billNos);
}
