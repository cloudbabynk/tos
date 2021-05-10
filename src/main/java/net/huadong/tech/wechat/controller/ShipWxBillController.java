package net.huadong.tech.wechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.ShipLoadBill;
import net.huadong.tech.shipbill.service.ShipLoadBillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.springboot.core.criterialquery.HdConditions;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/wx/shipweb/ShipLoadBill")
public class ShipWxBillController {

	@Autowired
	private ShipLoadBillService shipLoadBillService;


	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(String shipNo,String ieid) {
		HdQuery hdQuery =new HdQuery();
		
		Map map=hdQuery.getOthers();
		if(map==null ) map=new HashMap();
	    map.put("shipNo", shipNo);
	    map.put("iEId", ieid);
	    hdQuery.setOthers((HashMap) map);
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


	
}
