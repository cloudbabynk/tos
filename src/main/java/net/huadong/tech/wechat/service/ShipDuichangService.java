package net.huadong.tech.wechat.service;

import java.util.List;

import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.work.entity.WorkCommand;

public interface ShipDuichangService {

	List<PortCar> checkLoadRfid(String rfid);

	String checkShipNoAndVinNo(String shipNo, String vinNo);

	String checkLoadVin(String vinNo, String shipNo, String billNo, String planPlac);

	StringBuffer loaddcbill(String shipNo);
	
	StringBuffer shipCarCount(String shipNo);

	String shiploadoutplace(String req);

	String shipUnloaderdc(String req);

	String wmshipload(String req);

	StringBuffer billcount(String shipNo, String billNo);

	String checkDRIVER(String shipNo, String billNo, String vinNo);

	List<WorkCommand> checkVinRfid(String vin, String rfid);

	String checkcyPlac(String cyAreaNo, String cyRowNo, String cyBayNo, String shipNo);

	String checkBayNo(String cyAreaNo, String cyRowNo, String cyBayNo, String vinNo);

	String nmshipUnloaderdc(String req);

	String checkLoadVinFt(String vinNo, String shipNo, String workTyp, String directId, String account, String port);

	String checkLoadport(String port, String vinNo);

	String checkLoadVinFtXC(String vinNo, String shipNo);

	String checkVinNoShipNo(String vinNo, String shipNo);

	String countCar(String shipNo, String workTyp, String vcPortID);

}
