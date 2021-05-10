package net.huadong.tech.wechat.service;

import java.util.List;

import net.huadong.tech.shipbill.entity.PortCar;

public interface DamageRegistrationService {

	List<PortCar> rfidcheck(String rfid);

	StringBuffer cardamagels(String portCarNo);

	String damageloader(String req);

}
