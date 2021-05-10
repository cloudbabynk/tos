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
import net.huadong.tech.work.entity.WorkBill;
import net.huadong.tech.work.service.WorkBillService;
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
@RequestMapping("webresources/login/work/WorkBill")
public class WorkBillController {

	@Autowired
	private WorkBillService workBillService;

	// 菜单进入
	@RequestMapping(value = "/workbill.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workbill";
	}

	// 菜单进入
	@RequestMapping(value = "/workbillsjpg.htm")
	public String pageThSjpg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workbillsjpg";
	}
	
	// 菜单进入
	@RequestMapping(value = "/ttltjb.htm")
	public String pageTtltjb(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/ttltjb";
	}
	
	// 菜单进入
	@RequestMapping(value = "/ttlydzb.htm")
	public String pageTtlydzb(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/ttlydzb";
	}

	// 菜单进入
	@RequestMapping(value = "/workbilllhpg.htm")
	public String pageThLhpg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workbilllhpg";
	}

	// 菜单进入
	@RequestMapping(value = "/workbilljxpg.htm")
	public String pageThJxpg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workbilljxpg";
	}
	
	// 菜单进入
	@RequestMapping(value = "/workbillzypqr.htm")
	public String pageThZypqr(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workbillzypqr";
	}

	/**
	 * 指导员下拉
	 */
	@RequestMapping(value = "/getCEmployeeDrop")
	@ResponseBody
	public List<EzDropBean> getCEmployeeDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CEmployee a  where a.empTypCod =:empTypCod ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("empTypCod", "10");
		List<CEmployee> ls = JpaUtils.findAll(jpql, params);
		for (CEmployee cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getEmpNo());
			drop.setLabel(cc.getEmpNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 指导员下拉
	 */
	@RequestMapping(value = "/getSyscodeDrop")
	@ResponseBody
	public List<EzDropBean> getSyscodeDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  SCode a  where a.fldEng =:fldEng ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("fldEng", "WORK_TYP");
		List<SCode> ls = JpaUtils.findAll(jpql, params);
		for (SCode cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getCode());
			drop.setLabel(cc.getName());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 委托号下拉
	 */
	@RequestMapping(value = "/getContractNoDrop")
	@ResponseBody
	public List<EzDropBean> getContractNoDrop(String contractTyp) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ContractIeDoc a  where a.contractTyp =:contractTyp ";
		QueryParamLs params = new QueryParamLs();
		if ("TI".equals(contractTyp)) {
			params.addParam("contractTyp", "1");
		} else if ("TO".equals(contractTyp)) {
			params.addParam("contractTyp", "2");
		}
		List<ContractIeDoc> ls = JpaUtils.findAll(jpql, params);
		for (ContractIeDoc cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getContractNo());
			drop.setLabel(cc.getContractNo());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 委托号下拉
	 */
	@RequestMapping(value = "/getMoveCarPlanDrop")
	@ResponseBody
	public List<EzDropBean> getMoveCarPlanDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select distinct a  from  MoveCarPlan a";
		QueryParamLs params = new QueryParamLs();
		List<MoveCarPlan> ls = JpaUtils.findAll(jpql, params);
		for (MoveCarPlan cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getMovePlanNo());
			drop.setLabel(cc.getMovePlanNo());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String workbillNos) {
		workBillService.removeAll(workbillNos);
		return HdUtils.genMsg();
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/generate")
	@ResponseBody
	public HdMessageCode generate(String workbillNo) {
		workBillService.generate(workbillNo);
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
		return workBillService.find(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public WorkBill findone(String workbillNo) {
		if (HdUtils.strIsNull(workbillNo)) {
			WorkBill workBill = new WorkBill();
			return workBill;
		}
		return workBillService.findone(workbillNo);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkBill> gridData) {
		// CommonUtil.initEntity(gridData);
		return workBillService.save(gridData);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody WorkBill workBill) {

		return workBillService.saveone(workBill);
	}
	
	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/uploadWorkBills")
	@ResponseBody
	public HdMessageCode uploadWorkBills(String workbillNos) {
		workBillService.uploadWorkBills(workbillNos);
		return HdUtils.genMsg();
	}
}
