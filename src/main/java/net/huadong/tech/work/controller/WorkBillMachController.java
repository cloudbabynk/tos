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
import net.huadong.tech.work.entity.WorkBillMach;
import net.huadong.tech.work.service.WorkBillMachService;
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
@RequestMapping("webresources/login/work/WorkBillMach")
public class WorkBillMachController  {
	
	@Autowired
	private WorkBillMachService workBillMachService; 
	
	//菜单进入
	@RequestMapping(value = "/workbillmach.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/work/workbillmach";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String machTypCods,String workbillNo) {
		workBillMachService.removeAll(machTypCods,workbillNo);
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
	    return workBillMachService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public WorkBillMach findone(String damCod) {
		if (HdUtils.strIsNull(damCod)) {
			WorkBillMach workBillMach = new WorkBillMach();
			return workBillMach;
		}
		return workBillMachService.findone(damCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkBillMach> gridData,String workbillNo) {
	 	 //CommonUtil.initEntity(gridData);
		return workBillMachService.save(gridData,workbillNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody WorkBillMach workBillMach) {

		return workBillMachService.saveone(workBillMach);
	}
}
