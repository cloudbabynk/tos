package net.huadong.tech.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.wechat.service.ShipDuichangService;
import net.huadong.tech.work.entity.WorkCommand;

@Controller
@RequestMapping("webresources/ShipDuichang")
public class ShipDuichangController {
	
	@Autowired
	ShipDuichangService ShipDuichangService;
	
	/**
	 * 验证rfid信息装船
	 */
	@RequestMapping(value = "/checkLoadRfid")
	@ResponseBody
	public List<PortCar> checkLoadRfid(String rfid) {
		return ShipDuichangService.checkLoadRfid(rfid);
	}
	/**
	 * 校验车辆是否在港
	 */
	@RequestMapping(value = "/checkShipNoAndVinNo")
	@ResponseBody
	public String checkShipNoAndVinNo(String shipNo,String vinNo) {
		return ShipDuichangService.checkShipNoAndVinNo(shipNo,vinNo);
	}
	
	/*
	 * 验证在场
	 */
	@RequestMapping(value = "/checkVinNoShipNo")
	@ResponseBody
	public String checkVinNoShipNo(String vinNo, String shipNo) {
		return ShipDuichangService.checkVinNoShipNo(vinNo, shipNo);
	}
	
	/**
	 * 验证vinNo信息装船 checkLoadVin
	 */
	@RequestMapping(value = "/checkLoadVin")
	@ResponseBody
	public String checkLoadVin(String vinNo, String shipNo, String billNo, String planPlac) {
		return ShipDuichangService.checkLoadVin(vinNo,shipNo,billNo,planPlac);
	}
	/**
	 * 装船进场 船作业数、船计划数  
	 */
	@RequestMapping(value = "/loaddcbill")
	@ResponseBody
	public StringBuffer loaddcbill(String shipNo) {
		return ShipDuichangService.loaddcbill(shipNo);
	}
	/**
	 * 确认出场
	 */
	@RequestMapping(value = "/shiploadoutplace")  
	@ResponseBody
	public String shiploadoutplace(String req) {
		return ShipDuichangService.shiploadoutplace(req);

	}
	
	/**
	 * 手持卸船堆场确认
	 */
	@RequestMapping(value = "/shipUnloaderdc")
	@ResponseBody
	public String shipUnloaderdc(String req) {
		return ShipDuichangService.shipUnloaderdc(req);
	}
	/**
	 * @param 外贸装船
	 * @return
	 */
	@RequestMapping(value = "/wmshipload", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String wmshiploaderdc(String req) {
		return ShipDuichangService.wmshipload(req);
	}
	//内贸无条形码卸船理货
	@RequestMapping(value = "/nmshipUnloaderdc")
	@ResponseBody
	public String nmshipUnloaderdc(String req) {
		return ShipDuichangService.nmshipUnloaderdc(req);
	}
	
	/**
	 * 卸船堆场作业数计划数
	 */
	@RequestMapping(value = "/billcount")
	@ResponseBody
	public StringBuffer billcount(String shipNo, String billNo) {
		return ShipDuichangService.billcount(shipNo, billNo);
	}
	
	/**
	 * 卸船堆场作业数计划数
	 */
	@RequestMapping(value = "/shipCarCount")
	@ResponseBody
	public StringBuffer shipCarCount(String shipNo) {
		return ShipDuichangService.shipCarCount(shipNo);
	}
	
	/**
	 * 手持司机验证
	 */
	@RequestMapping(value = "/checkDRIVER")
	@ResponseBody
	public String checkDRIVER(String shipNo, String billNo, String vinNo) {
		return ShipDuichangService.checkDRIVER(shipNo, billNo, vinNo);
	}
	/**
	 * 验证rfid、vin信息
	 */
	@RequestMapping(value = "/checkVinRfid")
	@ResponseBody
	public List<WorkCommand> checkVinRfid(String vin, String rfid) {
		return ShipDuichangService.checkVinRfid(vin, rfid);
	}
	/**
	 * 验证入场位置有效性cyPlac
	 */
	@RequestMapping(value = "/checkcyPlac")
	@ResponseBody
	public String checkcyPlac(String cyAreaNo, String cyRowNo, String cyBayNo, String shipNo) {
		return ShipDuichangService.checkcyPlac(cyAreaNo,cyRowNo,cyBayNo,shipNo);
	}
	/**
	 * 验证场位
	 */
	@RequestMapping(value = "/checkBayNo")
	@ResponseBody
	public String checkBayNo(String cyAreaNo, String cyRowNo, String cyBayNo, String vinNo) {
		return ShipDuichangService.checkBayNo(cyAreaNo,cyRowNo,cyBayNo,vinNo);
	}
	
	//内贸装船 丰田车架号校验  
	@RequestMapping(value = "/checkLoadVinFt")
	@ResponseBody
	public String checkLoadVinFt(String vinNo, String shipNo, String workTyp, String directId, String account, String port) {
		return ShipDuichangService.checkLoadVinFt(vinNo, shipNo, workTyp, directId, account, port);
	}
	
	//内贸装船 丰田 流向校验
	@RequestMapping(value = "/checkLoadport")
	@ResponseBody
	public String checkLoadport(String port, String vinNo) {
		return ShipDuichangService.checkLoadport(port, vinNo);
	}
	
	//作业数计划书
	@RequestMapping(value = "/countCar")
	@ResponseBody
	public String countCar(String shipNo, String workTyp,String vcPortID) {
		return ShipDuichangService.countCar(shipNo, workTyp,vcPortID);
	}
	
	//内贸装船 丰田车架号校验  
	@RequestMapping(value = "/checkLoadVinFtXC")
	@ResponseBody
	public String checkLoadVinFtXC(String vinNo, String shipNo) {
		return ShipDuichangService.checkLoadVinFtXC(vinNo,shipNo);
	}

}
