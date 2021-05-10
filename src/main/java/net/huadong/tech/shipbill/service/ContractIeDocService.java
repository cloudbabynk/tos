package net.huadong.tech.shipbill.service;

import java.util.List;
import java.util.Map;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

public interface ContractIeDocService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	List<ContractIeDoc> findContract();
	HdMessageCode save(HdEzuiSaveDatagridData<ContractIeDoc> gridData);
	void removeAll(String contractNos);
	ContractIeDoc findone(String contractNo);
	HdMessageCode saveone(ContractIeDoc ContractIeDoc);
	String hasWorkCommand(String contractNo);
	HdMessageCode findContractIeDoc(String contractNo);
	HdEzuiDatagridData findShip(HdQuery hdQuery);
	HdMessageCode checkBefDel(String contractNo);
	List<Ship> savename(HdQuery hdQuery);
	void sendEmail(String fromWho, String password, String toWho, String attach);
	ContractIeDoc getShipBillInfo(String contractNo);
	HdMessageCode copy(ContractIeDoc contractIeDoc);
	Ship getShipDockCod(String shipNo);
	void confirmingate(String contractNos);
	CCarTyp findBrandKind(String carTyp);
	String sendData(String contractNos);
	String sendDailyContractPlan(String contractNos);
	List<Map<String,Object>> getTrueCarNum(String shipNo);
}
