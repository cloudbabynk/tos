package net.huadong.tech.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.wechat.service.DaoFieldService;

/**
 * 捣场
 * @author hdwork
 *
 */
@Controller
@RequestMapping("webresources/daofile")
public class DaoFieldController {
	
	@Autowired
	DaoFieldService daoFieldService;
	
	/**
	 * 捣场RFID
	 */
	@RequestMapping(value = "/daofield")
	@ResponseBody
	public String checkdaofrfid(String rfid) {
		return daoFieldService.checkdaofrfid(rfid);
	}
	/**
	 * 捣场出场
	 */
	@RequestMapping(value = "/outfield")
	@ResponseBody
	public String outfield(String req) {
		return daoFieldService.outfield(req);
	}

}
