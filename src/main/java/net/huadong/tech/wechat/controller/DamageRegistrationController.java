package net.huadong.tech.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.wechat.service.DamageRegistrationService;

/**
 * 残损登记
 * @author hdwork
 *
 */
@Controller
@RequestMapping("webresources/damageRegistration")
public class DamageRegistrationController {
	
	@Autowired
	DamageRegistrationService damageRegistrationService;
	
	/**
	 * 捣场RFID
	 */
	@RequestMapping(value = "/rfidcheck")
	@ResponseBody
	public List<PortCar> rfidcheck(String rfid) {
		return damageRegistrationService.rfidcheck(rfid);
	}
	
	/**
	 * 残损历史记录
	 */
	@RequestMapping(value = "/cardamagels")
	@ResponseBody
	public StringBuffer cardamagels(String portCarNo) {
		return damageRegistrationService.cardamagels(portCarNo);
	}
	/**
	 * 残损确认
	 */
	@RequestMapping(value = "/damageloader")
	@ResponseBody
	public String damageloader(String req) {
		return damageRegistrationService.damageloader(req);
	}

}
