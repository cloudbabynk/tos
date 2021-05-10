package net.huadong.tech.wechat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.wechat.service.SparseSetService;

/**
 * 集疏港
 * @author hdwork
 *
 */
@Controller
@RequestMapping("webresources/SparseSet")
public class SparseSetController {
	
	@Autowired
	SparseSetService sparseSetService;
	
	/**
	 * 验证rfid信息装船
	 */
	@RequestMapping(value = "/checkLoadRfid")
	@ResponseBody
	public List<PortCar> checkLoadRfid(String rfid) {
		return sparseSetService.checkLoadRfid(rfid);
	}
	/**
	 * 验证新车位cyplac集港
	 */
	@RequestMapping(value = "/checkcyPlacIn")
	@ResponseBody
	public String checkcyPlacIn(String cyAreaNo, String cyRowNo, String cyBayNo, String contractNo) {
		return sparseSetService.checkcyPlacIn(cyAreaNo, cyRowNo, cyBayNo, contractNo);
	}
	/**
	 * 验证船名、航次、提单号
	 */
	@RequestMapping(value = "/checkBillVin")
	@ResponseBody
	public String checkBillVin(String vinNo, String contractNo) {
		return sparseSetService.checkBillVin(vinNo,contractNo);
	}
	
	/**
	 * 获取条形码验证长度
	 */
	@RequestMapping(value = "/checkVinNum")
	@ResponseBody
	public String checkVinNum() {
		return sparseSetService.checkVinNum();
	}
	/**
	 * 验证集疏港计划
	 */
	@RequestMapping(value = "/countAreaNo")
	@ResponseBody
	public String countAreaNo(String contractNo) {
		return sparseSetService.countAreaNo(contractNo);
	}
	/**
	 * 验证VIN信息卸船集港
	 */
	@RequestMapping(value = "/checkVinIn")
	@ResponseBody
	public String checkVinIn(String vinNo) {
		return sparseSetService.checkVinIn(vinNo);
	}
	/**
	 * 集港出场提单号
	 */
	@RequestMapping(value = "/billNum")
	@ResponseBody
	public List<ContractIeDoc> billNum(String contractNo) {
		return sparseSetService.billNum(contractNo);
	}
	/**
	 * 集港确认入场
	 */
	@RequestMapping(value = "/portloadoutplace")
	@ResponseBody
	public String portloadoutplace(String req) {
		return sparseSetService.portloadoutplace(req);
	}
	
	/**
	 * 集港完工确认
	 */
	@RequestMapping(value = "/finished")
	@ResponseBody
	public String finished(String req) {
		return sparseSetService.finished(req);
	}
	/**
	 * 转栈出场RFID
	 */
	@RequestMapping(value = "/checkshgRfid")
	@ResponseBody
	public String checkshgRfid(String rfid, String billNo) {
		return sparseSetService.checkshgRfid(rfid, billNo);
	}
	/**
	 * 验证新车位疏港 
	 */
	@RequestMapping(value = "/checkcyPlacshg")
	@ResponseBody
	public String checkcyPlacshg(String cyPlac, String contractNo) {
		return sparseSetService.checkcyPlacshg(cyPlac, contractNo);
	}
	/**
	 * 疏港出场vinNo校验
	 */
	@RequestMapping(value = "/checkVinsg")
	@ResponseBody
	public String checkVinsg(String vinNo, String billNo) {
		return sparseSetService.checkVinsg(vinNo,billNo);
	}
	/**
	 * 疏港确认出场
	 */
	@RequestMapping(value = "/shiploadershg")
	@ResponseBody
	public String shiploadershg(String req) {
		return sparseSetService.shiploadershg(req);
	}
	/**
	 * 外贸疏港确认出场
	 */
	@RequestMapping(value = "/wmshiploadershg")
	@ResponseBody
	public String wmshiploadershg(String req) {
		return sparseSetService.wmshiploadershg(req);
	}
	/**
	 * 疏港 完工确认
	 */
	@RequestMapping(value = "/finishedworkshg")
	@ResponseBody
	public String finishedworkshg(String ingateId,String account) {
		return sparseSetService.finishedworkshg(ingateId,account);
	}
	/**
	 * 场、道、位 默认值  defaultAreaRowBay
	 */
	@RequestMapping(value = "/defaultAreaRowBay")
	@ResponseBody
	public Map<String, Object> defaultAreaRowBay(String billNo) {
		return sparseSetService.defaultAreaRowBay(billNo);
	}
	
	/**
	 * 疏港计划数
	 */
	@RequestMapping(value = "/billCount")
	@ResponseBody
	public GateTruckContract billCount(String ingateId, String contractNo) {
		return sparseSetService.billCount(ingateId, contractNo);
	}
	/**
	 * 疏港作业数
	 */
	@RequestMapping(value = "/sgworkCount")
	@ResponseBody
	public String sgworkCount(String contractNo) {
		return sparseSetService.sgworkCount(contractNo);
	}
    //外贸疏港根据车架号带入相关信息
	@RequestMapping(value = "/checkVIN")
	@ResponseBody
	public String checkVIN(String vin, String billNo, String contractNo, String brandCod) {
		return sparseSetService.checkVIN(vin, billNo, contractNo, brandCod);
	}
	
	//品牌、车类带出规则 
	@RequestMapping(value = "/checkBc")
	@ResponseBody
	public Map<String, Object> checkBc(String vinNo) {
		return sparseSetService.checkBc(vinNo);
	}
	
	//外贸疏港品牌、车类 getBrandCar
	@RequestMapping(value = "/getBrandCar")
	@ResponseBody
	public Map<String, Object> getBrandCar(String vinNo) {
		return sparseSetService.getBrandCar(vinNo);
	}
	
	//丰田集港车架号校验   checkShipInOutCheck
	@RequestMapping(value = "/checkShipInOutCheck")
	@ResponseBody
	public String checkShipInOutCheck(String vinNo) {
		return sparseSetService.checkShipInOutCheck(vinNo);
	}
	
	//集港车架号校验   jgCheck
	@RequestMapping(value = "/jgCheck")
	@ResponseBody
	public String jgCheck(String contractNo, String vinNo) {
		return sparseSetService.jgCheck(contractNo, vinNo);
	}	
	
	//作业数计划数
	@RequestMapping(value = "/countCarInPort")
	@ResponseBody
	public String countCarInPort(String contractNo) {
		return sparseSetService.countCarInPort(contractNo);
	}
	
	//装船作业数
	@RequestMapping(value = "/countCarZc")
	@ResponseBody
	public String countCarZc(String billNo) {
		return sparseSetService.countCarZc(billNo);
	}
	
	//作业数计划数
	@RequestMapping(value = "/countCarXhz")
	@ResponseBody
	public String countCarXhz(String shipNo, String billNo) {
		return sparseSetService.countCarXhz(shipNo, billNo);
	}	
	
}
