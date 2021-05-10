package net.huadong.tech.wechat.service;

import java.util.List;
import java.util.Map;

import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
//import net.huadong.tech.wechat.serviceimpl.string;

public interface SparseSetService {

	List<PortCar> checkLoadRfid(String rfid);

	String checkcyPlacIn(String cyPlac, String contractNo, String cyBayNo, String contractNo2);

	String checkVinIn(String vinNo);
	
	String checkVinNum();
	
	String countAreaNo(String contractNo);
	
	String checkBillVin(String vinNo, String contractNo);

	List<ContractIeDoc> billNum(String contractNo);

	String portloadoutplace(String req);

	String finished(String req);

	String checkshgRfid(String rfid, String billNo);

	String checkcyPlacshg(String cyPlac, String contractNo);

	String checkVinsg(String vinNo, String billNo);

	String shiploadershg(String req);

	String finishedworkshg(String ingateId,String account);

	Map<String, Object> defaultAreaRowBay(String billNo);

	GateTruckContract billCount(String ingateId, String contractNo);
	
	String sgworkCount(String contractNo); //

	String checkVIN(String vin, String billNo, String contractNo, String brandCod);

	String wmshiploadershg(String req);
	
	Map<String, Object> checkBc(String vinNo);
	
	Map<String, Object> getBrandCar(String vinNo);
	
	String checkShipInOutCheck(String vinNo);
	
	String jgCheck(String contractNo,String vinNo);
	
	String countCarInPort(String contractNo);
	
	String countCarZc(String billNo);
	
	String countCarXhz(String shipNo, String billNo);

}
