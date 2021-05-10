package net.huadong.tech.ship.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipThruput;
import net.huadong.tech.ship.service.ShipThruputService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
@Controller
@RequestMapping("webresources/login/ship/ShipThruput")
public class ShipThruputController {
	@Autowired
	private ShipThruputService shipThruputService; 
	
	//菜单进入
		@RequestMapping(value = "/shipthruputform.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/shipthruputform";
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
	    return shipThruputService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipThruput> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipThruputService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipThruputIds) {
		shipThruputService.removeAll(shipThruputIds);
		return HdUtils.genMsg();
	}
	
	/**
	 * 根据shipNo删除外签吨
	 */
	@RequestMapping(value = "/removeByshipNo")
	@ResponseBody
	public HdMessageCode removeByshipNo(String shipNo) {
		shipThruputService.removeByshipNo(shipNo);
		return HdUtils.genMsg();
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipThruput findone(String shipThruputId) {
		if (HdUtils.strIsNull(shipThruputId)) {
			ShipThruput shipThruput = new ShipThruput();
			return shipThruput;
		}
		return shipThruputService.findone(shipThruputId);
	}
	/**
	 * Form形式展现外签吨
	 * @param shipNo
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public ShipThruput findData(String shipNo) {
		if (HdUtils.strIsNull(shipNo)) {
			ShipThruput shipThruput = new ShipThruput();
			return shipThruput;
		}
		return shipThruputService.findData(shipNo);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipThruput shipThruput) {

		return shipThruputService.saveone(shipThruput);
	}
	
	/**
	 * 上报外签吨
	 * @param 
	 * @return
	 */
//	@RequestMapping(value = "/sendData")
//	@ResponseBody
//	public HdMessageCode sendData(String shipThruputId) {
//		shipThruputService.sendData(shipThruputId);
//		return HdUtils.genMsg();
//	}
	/**
	 * 根据shipNo上传外签吨
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/sendData")
	@ResponseBody
	public HdMessageCode sendData(String shipNo) {
		shipThruputService.sendData(shipNo);
		return HdUtils.genMsg();
	}
	
	/**
	 * 根据shipNo上传外签吨
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public HdMessageCode upload(String shipNo) {
		shipThruputService.upload(shipNo);
		return HdUtils.genMsg();
	}
}
