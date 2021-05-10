package net.huadong.tech.plan.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.plan.service.PlanGroupService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
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
@RequestMapping("webresources/login/plan/PlanGroup")
public class PlanGroupController  {
	@Autowired
	private PlanGroupService planGroupService; 
	
	//菜单进入
		@RequestMapping(value = "/plangroup.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/plangroup";
		}
		
	//菜单进入
	   @RequestMapping(value = "/ftplangroup.htm")
				public String  page(Model model){
					Random random = new Random();
					model.addAttribute("radi", random.nextInt()*1000);
					return "system/plan/ftplangroup";
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
	    return planGroupService.find(hdQuery);
	}
	/**
     * 丰田查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findft")
	@ResponseBody
	public HdEzuiDatagridData findft(@RequestBody HdQuery hdQuery) {
	    return planGroupService.findft(hdQuery);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<PlanGroup> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return planGroupService.save(gridData);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody PlanGroup planGroup) {

		return planGroupService.saveone(planGroup);
	}
	/*
	 * 丰田form保存
	 */
	@RequestMapping("/ftsaveone")
	@ResponseBody
	public HdMessageCode ftsaveone(@RequestBody PlanGroup planGroup) {

		return planGroupService.ftsaveone(planGroup);
	}
	@RequestMapping(value = "/findCCyArea")
	@ResponseBody
	public HdEzuiDatagridData findCCyArea(@RequestBody HdQuery hdQuery) {
	    return planGroupService.findCCyArea(hdQuery);
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planGroupNo) {
		planGroupService.removeAll(planGroupNo);
		return HdUtils.genMsg();
	}
	
	@RequestMapping(value = "/getUnlockedBay")
	@ResponseBody
	public List getUnlockdedBay(String cyAreaNo) {
		return planGroupService.getUnlockedBay(cyAreaNo);
	}
	/*
	 * 单个场地没策划完，剩余的计划车数
	 */
	@RequestMapping(value = "/getRest")
	@ResponseBody
	public List getRest(String planGroupNo) {
		return planGroupService.getRest(planGroupNo);
	}
	
	@RequestMapping(value = "/findBill")
	@ResponseBody
	public HdEzuiDatagridData findBill(@RequestBody HdQuery hdQuery) {
		return planGroupService.findBill(hdQuery);
	}
	@RequestMapping("/impWrokPlan")
	@ResponseBody
	public HdMessageCode impWrokPlan(@RequestBody Map map) throws Exception {
		return planGroupService.impWrokPlan(map);
	}
}
