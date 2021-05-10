package net.huadong.tech.plan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.plan.service.ConstructionPlanService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.entity.SysCode;
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
@RequestMapping("webresources/login/plan/ConstructionPlan")
public class ConstructionPlanController  {
	
	@Autowired
	private ConstructionPlanService constructionPlanService; 
	
	//菜单进入
		@RequestMapping(value = "/constructionplan.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/constructionplan";
		}
		@RequestMapping(value = "/relatedpartyplan.htm")
		public String  construplan(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/relatedpartyplan";
		}	
		@RequestMapping(value = "/borderInspection.htm")
		public String  borderIns(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/plan/borderIns";
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
	    return constructionPlanService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ConstructionPlan> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return constructionPlanService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planIds) {
		constructionPlanService.removeAll(planIds);
		return HdUtils.genMsg();
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ConstructionPlan findone(String planId) {
		if (HdUtils.strIsNull(planId)) {
			ConstructionPlan constructionPlan = new ConstructionPlan();
			return constructionPlan;
		}
		return constructionPlanService.findone(planId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ConstructionPlan constructionPlan) {

		return constructionPlanService.saveone(constructionPlan);
	}
	/**
	 * 场号下拉
	 */
	@RequestMapping(value = "/getDanWorkItems")
	@ResponseBody
	public List<EzDropBean> getDanWorkItems() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  SysCode a  where a.fieldCod='DAN_WORK_ITEMS' order by a.code  asc";
		QueryParamLs params = new QueryParamLs();
		List<SysCode> ls = JpaUtils.findAll(jpql, params);
		for (SysCode sc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(sc.getCode());
			drop.setLabel(sc.getName());
			list.add(drop);
		}
		return list;
	}
	/**
	 * 相关方代码
	 */
	@RequestMapping(value = "/getCClientCodDrop")
	@ResponseBody
	public List<EzDropBean> getCClientCodDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CClientCod a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CClientCod> ls = JpaUtils.findAll(jpql, params);
		for (CClientCod cp : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cp.getClientCod());
			drop.setLabel(cp.getcClientNam());
			list.add(drop);
		}
		return list;
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.find(params);
		List<ConstructionPlan> constructionPlanList = data.getRows();
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}
}
