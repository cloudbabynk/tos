package net.huadong.tech.work.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.SCode;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.WorkBillDriverSecond;
import net.huadong.tech.work.entity.WorkBillTallyerSecond;
import net.huadong.tech.work.service.WorkBillTallyerSecondService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/work/WorkBillTallyerSecond")
public class WorkBillTallyerSecondController  {
	
	@Autowired
	private WorkBillTallyerSecondService workBillTallyerSecondService; 
	
	//菜单进入
	@RequestMapping(value = "/workbilltallyersecond.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/work/workbilltallyersecond";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String classCods,String workbillNo) {
		workBillTallyerSecondService.removeAll(classCods,workbillNo);
		return HdUtils.genMsg();
	}
	
	
	/*
	 * 根据主键删除
	 */
	@RequestMapping(value = "/removeone")
	@ResponseBody
	public HdMessageCode removeone(@RequestBody WorkBillTallyerSecond workBillTallyerSecond) {
		workBillTallyerSecondService.removeone(workBillTallyerSecond);
		return HdUtils.genMsg();
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
	    return workBillTallyerSecondService.find(hdQuery);
	}
	
	
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findTallyer")
	@ResponseBody
	public HdEzuiDatagridData findTallyer(@RequestBody HdQuery hdQuery) {
	    return workBillTallyerSecondService.findTallyer(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public WorkBillTallyerSecond findone(String damCod) {
		if (HdUtils.strIsNull(damCod)) {
			WorkBillTallyerSecond workBillTallyerSecond = new WorkBillTallyerSecond();
			return workBillTallyerSecond;
		}
		return workBillTallyerSecondService.findone(damCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkBillTallyerSecond> gridData,String workbillNo,String classCod) {
	 	 //CommonUtil.initEntity(gridData);
		return workBillTallyerSecondService.save(gridData,workbillNo,classCod);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody WorkBillTallyerSecond workBillTallyerSecond) {

		return workBillTallyerSecondService.saveone(workBillTallyerSecond);
	}
}
