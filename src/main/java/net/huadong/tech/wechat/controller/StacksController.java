package net.huadong.tech.wechat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.wechat.service.StacksService;

/**
 * 转栈出场
 * @author hdwork
 *
 */
@Controller
@RequestMapping("webresources/Stacks")
public class StacksController {
	
	@Autowired
	StacksService stacksService;
	
	/**
	 * 转栈出场RFID
	 */
	@RequestMapping(value = "/checkshgRfid")
	@ResponseBody
	public Map<String, Object> checkshgRfid(String rfid, String billNo) {
		return stacksService.checkshgRfid(rfid, billNo);
	}
	/**
	 * 验证新车位疏港 
	 */
	@RequestMapping(value = "/checkcyPlacshg")
	@ResponseBody
	public String checkcyPlacshg(String cyAreaNo, String cyRowNo, String cyBayNo, String contractNo) {
		return stacksService.checkcyPlacshg(cyAreaNo, cyRowNo, cyBayNo, contractNo);
	}
	/**
	 * 疏港出场vinNo校验
	 */
	@RequestMapping(value = "/checkVinsg")
	@ResponseBody
	public String checkVinsg(String vinNo, String billNo) {
		return stacksService.checkVinsg(vinNo,billNo);
	}
	/**
	 * 转栈确认
	 */
	@RequestMapping(value = "/shiploaderzhz")
	@ResponseBody
	public String shiploaderzhz(String req) {
		return stacksService.shiploaderzhz(req);
	}
	/**
	 * 集港出场提单号
	 */
	@RequestMapping(value = "/billNum")
	@ResponseBody
	public List<ContractIeDoc> billNum(String contractNo) {
		return stacksService.billNum(contractNo);
	}

}
