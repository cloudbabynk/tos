package net.huadong.tech.work.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.service.WorkCommandRollbackService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author wangsw
 */
@Controller
@RequestMapping("webresources/login/work/WorkCommandRollback")
public class WorkCommandRollbackController  {
	
	@Autowired
	private WorkCommandRollbackService workCommandRollbackService; 
	
	//菜单进入
		@RequestMapping(value = "/workcommandrollback.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/work/workcommandrollback";
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
	    return workCommandRollbackService.find(hdQuery);
	}
	/**
	 * 卸船回退
	 */
	@RequestMapping(value = "/unloadBack")
	@ResponseBody
	public HdMessageCode unloadBack(String portCarNo) {
		workCommandRollbackService.unloadBack(portCarNo);
		return HdUtils.genMsg();
	}
	/**
	 * 装船回退
	 */
	@RequestMapping(value = "/loadBack")
	@ResponseBody
	public HdMessageCode loadBack(String portCarNo) {
		workCommandRollbackService.loadBack(portCarNo);
		return HdUtils.genMsg();
	}
	/**
	 * 存栈装船回退
	 */
	@RequestMapping(value = "/loadBackCz")
	@ResponseBody
	public HdMessageCode loadBackCz(String portCarNo) {
		workCommandRollbackService.loadBackCz(portCarNo);
		return HdUtils.genMsg();
	}
	/**
	 * 集港回退
	 */
	@RequestMapping(value = "/jgBack")
	@ResponseBody
	public HdMessageCode jgBack(String portCarNo) {
		workCommandRollbackService.jgBack(portCarNo);
		return HdUtils.genMsg();
	}
	/**
	 * 疏港回退
	 */
	@RequestMapping(value = "/sgBack")
	@ResponseBody
	public HdMessageCode sgBack(String portCarNo) {
		workCommandRollbackService.sgBack(portCarNo);
		return HdUtils.genMsg();
	}
	
	/**
	 * 转栈回退
	 */
	@RequestMapping(value = "/tzBack")
	@ResponseBody
	public HdMessageCode tzBack(String portCarNo) {
		workCommandRollbackService.tzBack(portCarNo);
		return HdUtils.genMsg();
	}
	
	@RequestMapping(value = "/getCCarTyp")
	@ResponseBody
	public List<EzDropBean> getCCarTyp(String q,String brandCod) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarTyp a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(q)){
			jpql += "and a.carTypNam like :carTypNam";
			params.addParam("carTypNam", "%" + q + "%");
		}
		if (HdUtils.strNotNull(brandCod)){
			jpql += "and a.brandCod =:brandCod";
			params.addParam("brandCod", brandCod);
		}
		List<CCarTyp> ls = JpaUtils.findAll(jpql, params);
		for (CCarTyp cct : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cct.getCarTyp());
			drop.setLabel(cct.getCarTypNam());
			list.add(drop);
		}
		return list;
	}
	
	@RequestMapping(value = "/getCBrandDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandDrop(String q) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CBrand a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(q)){
			jpql += "and a.brandNam like :brandNam";
			params.addParam("brandNam", "%" + q + "%");
		}
		List<CBrand> ls = JpaUtils.findAll(jpql, params);
		for (CBrand cct : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cct.getBrandCod());
			drop.setLabel(cct.getBrandNam());
			list.add(drop);
		}
		return list;
	}
	
	
	/*
	 * 批量理货回退
	 */
	@RequestMapping("/cargoBack")
	@ResponseBody
	public HdMessageCode cargoBack(@RequestBody CargoInfo cargoInfo, String type) {

		return workCommandRollbackService.cargoBack(cargoInfo,type);
	}
}
