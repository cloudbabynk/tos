package net.huadong.tech.shipbill.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.shipbill.entity.AnnualWorkPlan;
import net.huadong.tech.shipbill.service.AnnualWorkPlanService;
import net.huadong.tech.ship.service.ShipDispatchLogService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
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
@RequestMapping("webresources/login/ship/AnnualWorkPlan")
public class AnnualWorkPlanController {

	@Autowired
	private AnnualWorkPlanService annualWorkPlanService;

	// 菜单进入
	@RequestMapping(value = "/annualworkplan.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/annualworkplan";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planIds) {
		annualWorkPlanService.removeAll(planIds);
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
		return annualWorkPlanService.find(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public AnnualWorkPlan findone(String planId) {
		if (HdUtils.strIsNull(planId)) {
			AnnualWorkPlan annualWorkPlan = new AnnualWorkPlan();
			return annualWorkPlan;
		}
		return annualWorkPlanService.findone(planId);
	}

	/**
	 * 船公司下拉
	 */
	@RequestMapping(value = "/getYearDrop")
	@ResponseBody
	public List<EzDropBean> getYearDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = "select a from AnnualWorkPlan a where 1=1 order by a.recTim desc";
		QueryParamLs params = new QueryParamLs();
		List<AnnualWorkPlan> ls = JpaUtils.findAll(jpql, params);
		for (AnnualWorkPlan cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getYearSet());
			drop.setLabel(cc.getYearSet());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<AnnualWorkPlan> gridData) {
		// CommonUtil.initEntity(gridData);
		return annualWorkPlanService.save(gridData);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody AnnualWorkPlan annualWorkPlan) {

		return annualWorkPlanService.saveone(annualWorkPlan);
	}
}
