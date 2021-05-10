package net.huadong.tech.plan.controller;

import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.plan.service.PlanPlacVinService;
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
@RequestMapping("webresources/login/plan/PlanPlacVin")
public class PlanPlacVinController  {
	
	@Autowired
	private PlanPlacVinService planPlacVinService; 
	
	//菜单进入
		@RequestMapping(value = "/planplacvin.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/planplacvin";
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
	    return planPlacVinService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<PlanPlacVin> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return planPlacVinService.save(gridData);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody PlanPlacVin planPlacVin) {

		return planPlacVinService.saveone(planPlacVin);
	}
	/**
	 * 删除
	 * @param vinNo
	 * @return
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String vinNo) {
		planPlacVinService.removeAll(vinNo);
		return HdUtils.genMsg();
	}
}
