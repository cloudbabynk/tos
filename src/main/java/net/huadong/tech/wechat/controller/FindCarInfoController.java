package net.huadong.tech.wechat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.wechat.service.FindCarInfoService;

/**
 * 车辆信息查询
 * @author hdwork
 *
 */
@Controller
@RequestMapping("webresources/findCarInfo")
public class FindCarInfoController {
	
	@Autowired
	FindCarInfoService findCarInfoService;
	
	/**
	 * 车辆信息查询RFID
	 */
	@RequestMapping(value = "/findByrfid")
	@ResponseBody
	public List<PortCar> findByrfid(String rfid) {
		return findCarInfoService.findByrfid(rfid);
	}
	
	/**
	 * 车辆信息查询RFID
	 */
	@RequestMapping(value = "/findByvinNo")
	@ResponseBody
	public List<PortCar> findByvinNo(String vinNo) {
		return findCarInfoService.findByvinNo(vinNo);
	}
	
	/**
	 * 理货统计
	 */
	@RequestMapping(value = "/findaAllData")
	@ResponseBody
	public Map<String, Object> findaAllData(String dateTime, String workRunNam) {
		return findCarInfoService.findaAllData(dateTime, workRunNam);
	}
}
