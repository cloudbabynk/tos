package net.huadong.tech.wechat.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.entity.Crfid;
import net.huadong.tech.wechat.service.CrfidService;


@Controller
@RequestMapping("webresources/wx/rfid")
public class WechatRfidController {

	@Autowired
	CrfidService crfidService;

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public	HdEzuiDatagridData find(String rfidCod) {
		HdQuery hdQuery=new HdQuery();
		HashMap map=new HashMap();
		map.put("rfidCod", rfidCod);
		hdQuery.setOthers(map);
		return crfidService.find(hdQuery);
	}
	
			
			
	@RequestMapping(value = "/saveone", method = RequestMethod.POST)
	@ResponseBody
	public	HdMessageCode saveone(@RequestBody Crfid cPortFt){
		return crfidService.saveone(cPortFt);
	}
	@RequestMapping(value = "/removeAll", method = RequestMethod.POST)
	@ResponseBody
	public	HdMessageCode removeAll(String rfid) {
		crfidService.removeAll(rfid);
		return HdUtils.genMsg();
	}		
}
