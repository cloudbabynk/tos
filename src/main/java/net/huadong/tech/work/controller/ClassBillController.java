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
import net.huadong.tech.work.entity.ClassBill;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.WorkBill;
import net.huadong.tech.work.service.ClassBillService;
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
@RequestMapping("webresources/login/work/ClassBill")
public class ClassBillController {

	@Autowired
	private ClassBillService classBillService;

	// 菜单进入
	@RequestMapping(value = "/classbill.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/classbill";
	}


	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String classbillNos) {
		classBillService.removeAll(classbillNos);
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
		return classBillService.find(hdQuery);
	}
	
	
	/**
	 * 装卸工班票查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findZx")
	@ResponseBody
	public HdMessageCode findZx(String shipNo,String workDte,String workRunCod) {
		return classBillService.findZx(shipNo,workDte,workRunCod);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ClassBill findone(String classbillNo) {
		if (HdUtils.strIsNull(classbillNo)) {
			ClassBill classBill = new ClassBill();
			return classBill;
		}
		return classBillService.findone(classbillNo);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ClassBill> gridData) {
		// CommonUtil.initEntity(gridData);
		return classBillService.save(gridData);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ClassBill classBill) {

		return classBillService.saveone(classBill);
	}
}
