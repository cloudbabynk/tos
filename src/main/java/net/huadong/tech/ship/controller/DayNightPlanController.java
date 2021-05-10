package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.DayNightPlan;
import net.huadong.tech.ship.service.DayNightPlanService;
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
@RequestMapping("webresources/login/ship/DayNightPlan")
public class DayNightPlanController  {
	
	@Autowired
	private DayNightPlanService dayNightPlanService; 
	
	//菜单进入
	@RequestMapping(value = "/daynightplan.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/daynightplan";
	}
	
	//上昼夜导入
		@RequestMapping(value = "/exdaynightplan.htm")
		public String  pageExth(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/exdaynightplan";
		}
	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/getCCShipDataDrop")
	@ResponseBody
	public List<EzDropBean> getCCShipDataDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CShipData a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CShipData>  ls=JpaUtils.findAll(jpql, params);
			for(CShipData cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getShipCod());
				drop.setLabel(cc.getcShipNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planIds) {
		dayNightPlanService.removeAll(planIds);
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
	    return dayNightPlanService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public DayNightPlan findone(String planId) {
		if (HdUtils.strIsNull(planId)) {
			DayNightPlan dayNightPlan = new DayNightPlan();
			return dayNightPlan;
		}
		return dayNightPlanService.findone(planId);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<DayNightPlan> gridData,String days,String shipNo,String dockCod) {
	 	 //CommonUtil.initEntity(gridData);
		return dayNightPlanService.save(gridData,days,shipNo,dockCod);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody DayNightPlan dayNightPlan) {

		return dayNightPlanService.saveone(dayNightPlan);
	}
	
	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/uploadDayNightPlans")
	@ResponseBody
	public HdMessageCode uploadDayNightPlans(String planIds) {
		dayNightPlanService.uploadDayNightPlans(planIds);
		return HdUtils.genMsg();
	}
}
