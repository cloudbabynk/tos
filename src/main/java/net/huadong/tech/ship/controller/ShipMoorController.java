package net.huadong.tech.ship.controller;

import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipMoor;
import net.huadong.tech.ship.service.ShipMoorService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/ship/ShipMoor")
public class ShipMoorController  {
	
	@Autowired
	private ShipMoorService shipMoorService; 
	
	//菜单进入
		@RequestMapping(value = "/shipmoor.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/shipmoor";
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
	    return shipMoorService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipMoor> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipMoorService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipMoorIds) {
		shipMoorService.removeAll(shipMoorIds);
		return HdUtils.genMsg();
	}
	//获取当前用户名字
	@RequestMapping(value = "/getCurrentAccountName")
	@ResponseBody
	public String getCurrentAccountName() {
		String accountName=HdUtils.getCurUser().getName();
		return accountName;
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipMoor findone(String shipMoorId) {
		if (HdUtils.strIsNull(shipMoorId)) {
			ShipMoor shipMoor = new ShipMoor();
			return shipMoor;
		}
		return shipMoorService.findone(shipMoorId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipMoor shipMoor) {

		return shipMoorService.saveone(shipMoor);
	}
}
