package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.ship.service.CPortFtService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;

@Controller
@RequestMapping("webresources/login/ship/CPortFt")
public class CPortFtController {
	
	@Autowired
	private CPortFtService cPortFtService;
	//菜单进入
	@RequestMapping(value = "/cPortFt.htm")
	public String pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cportft";
	}
	
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
		return cPortFtService.find(hdQuery);
	}
	
	/*
	 * 港口对应
	 */
	@RequestMapping(value = "/findCPortNam")
	@ResponseBody
	public List<EzDropBean> getCClientCodDropHd() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " SELECT c FROM CPort c ";
		QueryParamLs params = new QueryParamLs();
		List<CPort> ls = JpaUtils.findAll(jpql, params);
		for (CPort cp : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cp.getPortId());
			drop.setLabel(cp.getcPortNam());
			list.add(drop);
		}
		return list;
	}
	
	/*
	 * 保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CPortFt cPortFt) {
		return cPortFtService.saveone(cPortFt);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String portFtId) {
		cPortFtService.removeAll(portFtId);
		return HdUtils.genMsg();
	}

}
