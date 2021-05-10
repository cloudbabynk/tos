package net.huadong.tech.gate.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.service.GateTruckService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/gate/GateTruck")
public class GateTruckController  {
	
	@Autowired
	private GateTruckService gateTruckService; 
	
	//菜单进入
	@RequestMapping(value = "/gatetruck.htm")
	public String  pageTh(String Type,Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		model.addAttribute("Type", Type);
		return "system/gate/gatetruck";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		gateTruckService.removeAll(ids);
		return HdUtils.genMsg();
	}
	

	/**
     * 通用列表查询--内外贸集疏港的队列信息通用
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return gateTruckService.find(hdQuery);
	}
	
	
	/**
     * 选车下拉查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findCar")
	@ResponseBody
	public HdEzuiDatagridData findCar(@RequestBody HdQuery hdQuery) {
	    return gateTruckService.findCar(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public GateTruck findone(String id) {
		if (HdUtils.strIsNull(id)) {
			GateTruck gateTruck = new GateTruck();
			return gateTruck;
		}
		return gateTruckService.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<GateTruck> gridData,String contractNo,String singleId,String truckNo,String planNum,String inGatTim,String gateNo) {
	 	 //CommonUtil.initEntity(gridData);
		return gateTruckService.save(gridData,contractNo,singleId,truckNo,planNum,inGatTim,gateNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody GateTruck gateTruck) {

		return gateTruckService.saveone(gateTruck);
	}
}
