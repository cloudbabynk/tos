package net.huadong.tech.ship.controller;

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
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.ShipReportService;
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
@RequestMapping("webresources/login/ship/ShipReport")
public class ShipReportController  {
	
	@Autowired
	private ShipReportService shipReportService; 
	
	//菜单进入
	@RequestMapping(value = "/shipreport.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/shipreport";
	}
	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/getCCShipDataDrop")
	@ResponseBody
	public List<EzDropBean> getCCShipDataDrop(String shipCod) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		QueryParamLs params=new QueryParamLs();
		List<CShipData>  ls=null;
		if(HdUtils.strNotNull(shipCod)){
			String jpql= " select a  from  CShipData a  where a.shipCod=:shipCod";
			params.addParam("shipCod", shipCod);
			ls=JpaUtils.findAll(jpql, params);
		}else{
			String jpql= " select a  from  CShipData a  where 1=1 ";
			ls=JpaUtils.findAll(jpql, params);
		}
		for(CShipData cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getShipCod());
			drop.setLabel(cc.getcShipNam());
			list.add(drop);
		}
		return list;
	}
	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/geteShipNamDrop")
	@ResponseBody
	public List<EzDropBean> geteShipNamDrop(String shipCod) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		QueryParamLs params=new QueryParamLs();
		List<CShipData>  ls=null;
		if(HdUtils.strNotNull(shipCod)){
			String jpql= " select a  from  CShipData a  where a.shipCod=:shipCod";
			params.addParam("shipCod", shipCod);
			ls=JpaUtils.findAll(jpql, params);
		}else{
			String jpql= " select a  from  CShipData a  where 1=1 ";
			ls=JpaUtils.findAll(jpql, params);
		}
		for(CShipData cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getShipCod());
			drop.setLabel(cc.geteShipNam());
			list.add(drop);
		}
		return list;
	}
	
	/**
	 * 上报集团新调度
	 */
	@RequestMapping(value = "/uploadAll")
	@ResponseBody
	public HdMessageCode uploadAll(String shipRptIds) {
		shipReportService.uploadAll(shipRptIds);
		return HdUtils.genMsg();
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipRptIds) {
		shipReportService.removeAll(shipRptIds);
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
	    return shipReportService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipReport findone(String shipRptId) {
		if (HdUtils.strIsNull(shipRptId)) {
			ShipReport shipReport = new ShipReport();
			return shipReport;
		}
		return shipReportService.findone(shipRptId);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipReport> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipReportService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipReport shipReport) {

		return shipReportService.saveone(shipReport);
	}
}
