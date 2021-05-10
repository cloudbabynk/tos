package net.huadong.tech.ship.controller;

import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.ship.entity.ShipExecution;
import net.huadong.tech.ship.service.ShipExecutionService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;


@Controller
@RequestMapping("webresources/login/ship/ShipExecution")
public class ShipExecutionController {
	@Autowired
	private ShipExecutionService shipExecutionService; 
	
	//菜单进入
	@RequestMapping(value = "/shipexecution.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/shipexecution";
	}		
	/**
     * 通用列表查询
     * @param params
     * @return
     */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return shipExecutionService.find(hdQuery);
	}
	@RequestMapping("/genExecutionByship")
	@ResponseBody
	public HdEzuiDatagridData genExecutionByship(@RequestBody Map map) {
		return shipExecutionService.genExecutionByship(map);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipExecution> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipExecutionService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipExecutionIds) {
		shipExecutionService.removeAll(shipExecutionIds);
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
	public ShipExecution findone(String shipExecutionId) {
		if (HdUtils.strIsNull(shipExecutionId)) {
			ShipExecution shipExecution = new ShipExecution();
			return shipExecution;
		}
		return shipExecutionService.findone(shipExecutionId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipExecution shipExecution) {

		return shipExecutionService.saveone(shipExecution);
	}	
}
