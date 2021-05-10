package net.huadong.tech.wechat.service;

import java.util.List;
import java.util.Map;

import net.huadong.tech.shipbill.entity.PortCar;

public interface FindCarInfoService {

	List<PortCar> findByrfid(String rfid);

	List<PortCar> findByvinNo(String vinNo);

	Map<String, Object> findaAllData(String dateTime, String workRunNam);

}
