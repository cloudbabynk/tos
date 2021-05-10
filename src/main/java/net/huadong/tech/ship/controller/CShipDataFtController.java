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
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataFt;
import net.huadong.tech.ship.service.CShipDataFtService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;

@Controller
@RequestMapping("webresources/login/cargo/CShipDataFt")
public class CShipDataFtController {
	
	@Autowired
	private CShipDataFtService cShipDataFtService;
	//菜单进入
		@RequestMapping(value = "/cShipDataFT.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/cshipdataft";
		}
		
		@RequestMapping(value = "/find")
		@ResponseBody
		public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
		    return cShipDataFtService.find(hdQuery);
		}
		
		/**
		 * 系统船舶对应
		 */
		@RequestMapping(value = "/findCShipData")
		@ResponseBody
		public List<EzDropBean> getCClientCodDropHd() {
			List<EzDropBean> list = new ArrayList<EzDropBean>();
			String jpql = " SELECT c FROM CShipData c ";
			QueryParamLs params = new QueryParamLs();
			List<CShipData> ls = JpaUtils.findAll(jpql, params);
			for (CShipData cp : ls) {
				EzDropBean drop = new EzDropBean();
				drop.setValue(cp.getShipCodId());
				drop.setLabel(cp.getShipShort() + "-" + cp.getcShipNam());
				list.add(drop);
			}
			return list;
		}
		
		/**
		 * 保存saveone
		 */
		@RequestMapping("/saveone")
		@ResponseBody
		public HdMessageCode saveone(@RequestBody CShipDataFt cShipDataFt) {
			return cShipDataFtService.saveone(cShipDataFt);
		}
		
		/**
		 * 批量删除
		 */
		@RequestMapping(value = "/removeAll")
		@ResponseBody
		public HdMessageCode removeAll(String shipDataFtId) {
			cShipDataFtService.removeAll(shipDataFtId);
			return HdUtils.genMsg();
		}
		

}
