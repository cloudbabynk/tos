package net.huadong.tech.damage.service;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface CarDamageService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findPortCar(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<CarDamage> gridData);
	void removeAll(String cardamagIds);
	CarDamage findone(String areaCod);
	HdMessageCode saveone(CarDamage carDamage);
	HdMessageCode findCarDamage(String cardamagId);
}
