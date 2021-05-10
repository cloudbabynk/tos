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
import net.huadong.tech.work.service.WorkBillDriverSecondService;
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
@RequestMapping("webresources/login/work/WorkBillDriverSecond")
public class WorkBillDriverSecondController  {
	
	@Autowired
	private WorkBillDriverSecondService workBillDriverSecondService; 
	
	//菜单进入
	@RequestMapping(value = "/workbilldriversecond.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/work/workbilldriversecond";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String driverCods,String workbillNo,String classCod) {
		workBillDriverSecondService.removeAll(driverCods,workbillNo,classCod);
		return HdUtils.genMsg();
	}
	
	
	/*
	 * 根据主键删除
	 */
	@RequestMapping(value = "/removeone")
	@ResponseBody
	public HdMessageCode removeone(@RequestBody WorkBillDriverSecond workBillDriverSecond) {
		workBillDriverSecondService.removeone(workBillDriverSecond);
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
	    return workBillDriverSecondService.find(hdQuery);
	}
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findDriver")
	@ResponseBody
	public HdEzuiDatagridData findDriver(@RequestBody HdQuery hdQuery) {
	    return workBillDriverSecondService.findDriver(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public WorkBillDriverSecond findone(String damCod) {
		if (HdUtils.strIsNull(damCod)) {
			WorkBillDriverSecond workBillDriverSecond = new WorkBillDriverSecond();
			return workBillDriverSecond;
		}
		return workBillDriverSecondService.findone(damCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkBillDriverSecond> gridData,String workbillNo,String classCod) {
	 	 //CommonUtil.initEntity(gridData);
		return workBillDriverSecondService.save(gridData,workbillNo,classCod);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody WorkBillDriverSecond workBillDriverSecond) {

		return workBillDriverSecondService.saveone(workBillDriverSecond);
	}
}
