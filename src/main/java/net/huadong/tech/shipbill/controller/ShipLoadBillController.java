package net.huadong.tech.shipbill.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.ShipLoadBill;
import net.huadong.tech.shipbill.service.ShipLoadBillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/ShipLoadBill")
public class ShipLoadBillController {

	@Autowired
	private ShipLoadBillService shipLoadBillService;

	// 菜单进入

	@RequestMapping(value = "/shiploadbill.htm")
	public String pageTh(String iEId, String tradeId, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("iEId", iEId);
		model.addAttribute("tradeId", tradeId);
		return "system/shipbill/shiploadbill";
	}

	@RequestMapping(value = "/shiploadbillnew.htm")
	public String pageThnew(String iEId, String tradeId, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("iEId", iEId);
		model.addAttribute("tradeId", tradeId);
		return "system/shipbill/shiploadbillnew";
	}


	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
		return shipLoadBillService.find(hdQuery);
	}

	

	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipLoadBill shipLoadBill) {

		return shipLoadBillService.saveone(shipLoadBill);
	}

	// 行编辑保存
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipLoadBill> gridData) {
		List<ShipLoadBill> insertlst=gridData.getInsertedRows();
		for (ShipLoadBill shipLoadBill : insertlst) {
			shipLoadBill.setId(HdUtils.generateUUID());
		}
		return shipLoadBillService.save(gridData);
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipbillId) {
		shipLoadBillService.removeAll(shipbillId);
		return HdUtils.genMsg();
	}

	@RequestMapping("/savePortCarBillNo")
	@ResponseBody
	public HdMessageCode savePortCarBillNo(@RequestBody Map map) {
		return shipLoadBillService.savePortCarBillNo(map);
				
	}

	@RequestMapping("/jiqi")
	@ResponseBody
	public String jiqi(@RequestBody Map map) {
		return shipLoadBillService.jiqi(map);
				
	}
	
	@RequestMapping(value ="/deljiqi", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String deljiqi(@RequestBody Map map) {
		return shipLoadBillService.deljiqi(map);
				
	}
	
	@RequestMapping("/shipjiqcheck")
	@ResponseBody
	public String shipjiqcheck(@RequestBody Map map) {
		return shipLoadBillService.shipjiqcheck(map);
				
	}
	
}
