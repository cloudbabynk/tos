package net.huadong.tech.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.wechat.service.ShipAnbianService;

@Controller
@RequestMapping("webresources/ShipAnbian")
public class ShipAnbianController {
	
	@Autowired
	ShipAnbianService shipAnbianService;
	
	/**
	 * 船作业数、船计划数
	 */
	@RequestMapping(value = "/loadBillData")
	@ResponseBody
	public StringBuffer loadBillData(String shipNo) {
		return shipAnbianService.loadBillData(shipNo);
	}
	/**
	 * 装船确认
	 */
	@RequestMapping(value = "/shiploader")
	@ResponseBody
	public String shiploader(String req) {
		return shipAnbianService.shiploader(req);
	}
	
	/**
	 * 手持卸船确认
	 */
	@RequestMapping(value = "/shipUnloader")
	@ResponseBody
	public String shipUnloader(String req) {
		return shipAnbianService.shipUnloader(req);
	}
	/**
	 * 提单作业数、提单计划数
	 */
	@RequestMapping(value = "/billData")
	@ResponseBody
	public StringBuffer shipBillData(String shipNo, String billNo) {
		return shipAnbianService.shipBillData(shipNo, billNo);
	}
	/**
	 * 验证rfid信息卸船
	 */
	@RequestMapping(value = "/checkRFID")
	@ResponseBody
	public String checkVinRfid(String rfid) {
		return shipAnbianService.checkRFID(rfid);
	}
	/**
	 * 验证VIN信息卸船
	 */
	@RequestMapping(value = "/checkVIN", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String checkVINNm(String vin, String shipNo) {
		return shipAnbianService.checkVIN(vin, shipNo);
	}
	/**
	 * 验证VIN信息卸船 
	 * 目前用于外贸的           卸船
	 * 		内外贸的    装卸船
	 */
	@RequestMapping(value = "/checkVINHandling", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkVINHandling(String vin, String shipNo,String tradeId,String directId,String ieId) {
		String rtStr=null;
		//1 内贸 2外贸
		if(tradeId.equals("2")) {
			rtStr=shipAnbianService.checkVINHandling(vin, shipNo,tradeId,directId,ieId);
		}
		return rtStr;
	}
	
	/**
	 * 验证VIN信息装船
	 */
	@RequestMapping(value = "/checkVINTrade", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkVINTrade(String vin, String shipNo,String tradeId,String directId) {
		String ss=shipAnbianService.checkVIN(vin, shipNo,tradeId,directId);
		return ss;
	}
	/**
	 * VIN验证billCar
	 */
	@RequestMapping(value = "/checkVINbillCar")
	@ResponseBody
	public String checkVINbillCar(String vin, String shipNo) {
		return shipAnbianService.checkVINbillCar(vin, shipNo);
	}
	/**
	 * 验证入场位置有效性cyPlac
	 */
	@RequestMapping(value = "/checkcyPlac")
	@ResponseBody
	public String checkcyPlac(String cyPlac, String shipNo) {
		return shipAnbianService.checkcyPlac(cyPlac,shipNo);
	}
	/**
	 * 验证流向 
	 */
	@RequestMapping(value = "/checkFlow", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkFlow(String vin, String flow, String directid) {
		return shipAnbianService.checkFlow(vin, flow, directid);
	}	
	/**
	 * 获取：品牌、车型、车类     
	 */
	@RequestMapping(value = "/getCarInfo", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getCarInfo(String vin) {
		String aa= shipAnbianService.getCarInfo(vin);
		return aa;
	}
	
	/**
	 * 统计：作业数/计划数    
	 */
	@RequestMapping(value = "/countCar")
	@ResponseBody
	public String countCar(String shipNo, String workTyp, String brandcod, String cartyp) {
		return shipAnbianService.countCar(shipNo,  workTyp,  brandcod,  cartyp);
	}	
}
