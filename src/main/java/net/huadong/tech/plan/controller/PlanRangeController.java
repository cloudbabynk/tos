package net.huadong.tech.plan.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.plan.service.PlanRangeService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.beanUtil;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/plan/PlanRange")
public class PlanRangeController  {
	
	@Autowired
	private PlanRangeService planRangeService; 
	
	//菜单进入
		@RequestMapping(value = "/planrange.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/planrange";
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
	    return planRangeService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<PlanRange> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return planRangeService.save(gridData);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody PlanRange planRange) {
		return planRangeService.saveone(planRange);
	}
	@RequestMapping("/saveRangLst")
	@ResponseBody
	public HdMessageCode saveRangLst(@RequestBody Map map) {
		
	    List<Map> lst=(List) map.get("yards");
	    for (Map mapItem : lst) {
	    	
			PlanRange planRange=beanUtil.convertMap((Map)map.get("formdata"), PlanRange.class);
			planRange.setCyAreaNo(mapItem.get("cyAreaNo")+"");
		    planRangeService.saveone(planRange);
		}
		return HdUtils.genMsg();
	}
	
	
	/**
	 * 激活场地
	 * @param planRange
	 * @return
	 */
	@RequestMapping("/active")
	@ResponseBody
	public HdMessageCode active(@RequestBody PlanRange planRange) {
		return planRangeService.update(planRange);
	}
	/**
	 * 不激活场地
	 * @param planRange
	 * @return
	 */
	@RequestMapping("/inactive")
	@ResponseBody
	public HdMessageCode inactive(@RequestBody PlanRange planRange) {

		return planRangeService.update(planRange);
	}
	@RequestMapping("/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planIds) {
		planRangeService.removeAll(planIds);
		return HdUtils.genMsg();
	}
	
}
