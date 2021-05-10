package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.CShipDataHis;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.service.CShipDataService;
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
@RequestMapping("webresources/login/ship/CShipData")
public class CShipDataController  {
	
	@Autowired
	private CShipDataService cShipDataService; 
	
	//菜单进入
	@RequestMapping(value = "/cshipdata.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdata";
	}
	//菜单进入
	@RequestMapping(value = "/cshipdatahis.htm")
	public String  pageHis(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdatahis";
	}
	@RequestMapping(value = "/cshipdatags.htm")
	public String  pageGs(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdatags";
	}
	@RequestMapping(value = "/cshipdatar.htm")
	public String  pageDb(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdatar";
	}
	
	@RequestMapping(value = "/cshipdatazong.htm")
	public String  pageZo(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdatazong";
	}
	//菜单进入
	@RequestMapping(value = "/cshipdatajt.htm")
	public String  pageThJt(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/cshipdatajt";
	}
	/**
	 * 国籍下拉
	 */
	@RequestMapping(value = "/getCCountryDrop")
	@ResponseBody
	public List<EzDropBean> getCCountryDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CCountry a where 1=1";
			QueryParamLs params=new QueryParamLs();
			List<CCountry>  ls=JpaUtils.findAll(jpql, params);
			for(CCountry cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getCountryCod());
				drop.setLabel(cc.getcCountryNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 船公司下拉
	 */
	@RequestMapping(value = "/getshipCorpCodDrop")
	@ResponseBody
	public List<EzDropBean> getshipCorpCodDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CClientCod a where a.shipCorpId = '1'";
			QueryParamLs params=new QueryParamLs();
			List<CClientCod>  ls=JpaUtils.findAll(jpql, params);
			for(CClientCod cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getClientCod());
				drop.setLabel(cc.getcClientNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 船代理下拉
	 */
	@RequestMapping(value = "/getshipAgentCodDrop")
	@ResponseBody
	public List<EzDropBean> getshipAgentCodDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CClientCod a where a.shipAgentId = '1'";
			QueryParamLs params=new QueryParamLs();
			List<CClientCod>  ls=JpaUtils.findAll(jpql, params);
			for(CClientCod cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getClientCod());
				drop.setLabel(cc.getcClientShort());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 货主下拉
	 */
	@RequestMapping(value = "/getshipConsignCodDrop")
	@ResponseBody
	public List<EzDropBean> getshipConsignCodDrop(String q) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CClientCod a where a.shipConsignId = '1'";
			QueryParamLs params=new QueryParamLs();
			if (HdUtils.strNotNull(q)){
				jpql += " and a.cClientShort like :cClientShort";
				params.addParam("cClientShort", q);
			}
			List<CClientCod>  ls=JpaUtils.findAll(jpql, params);
			for(CClientCod cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getClientCod());
				drop.setLabel(cc.getcClientShort());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 港口下拉
	 */
	@RequestMapping(value = "/getCPortDrop")
	@ResponseBody
	public List<EzDropBean> getCPortDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CPort a where 1=1";
			QueryParamLs params=new QueryParamLs();
			List<CPort>  ls=JpaUtils.findAll(jpql, params);
			for(CPort cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getPortCod());
				drop.setLabel(cc.getcPortNam());
				list.add(drop);
			}
			return list;
		}
	
	/**
	 * 航线下拉
	 */
	@RequestMapping(value = "/getCShipLineDrop")
	@ResponseBody
	public List<EzDropBean> getCShipLineDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CShipLine a where 1=1";
			QueryParamLs params=new QueryParamLs();
			List<CShipLine>  ls=JpaUtils.findAll(jpql, params);
			for(CShipLine cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getShipLineCod());
				drop.setLabel(cc.getCShipLineNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 所有泊位下拉
	 */
	@RequestMapping(value = "/getCBerthDrop")
	@ResponseBody
	public List<EzDropBean> getCBerthDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CBerth a where 1=1";
			QueryParamLs params=new QueryParamLs();
			List<CBerth>  ls=JpaUtils.findAll(jpql, params);
			for(CBerth cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBerthCod());
				drop.setLabel(cc.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 船期预报泊位下拉
	 */
	@RequestMapping(value = "/getShipCBerthDrop")
	@ResponseBody
	public List<EzDropBean> getShipCBerthDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CBerth a where a.dockCod in ('03406500','03409000')";
			QueryParamLs params=new QueryParamLs();
			List<CBerth>  ls = JpaUtils.findAll(jpql, params);
			for(CBerth cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBerthCod());
				drop.setLabel(cc.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 泊位计划下拉
	 */
	@RequestMapping(value = "/getGsCBerthDrop")
	@ResponseBody
	public List<EzDropBean> getGsCBerthDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CBerth a where a.berthTyp =:berthTyp";
			QueryParamLs params=new QueryParamLs();
			params.addParam("berthTyp", "01");
			List<CBerth>  ls = JpaUtils.findAll(jpql, params);
			for(CBerth cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBerthCod());
				drop.setLabel(cc.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 全部缆桩下拉
	 */
	@RequestMapping(value = "/getCCableDrop")
	@ResponseBody
	public List<EzDropBean> getCCableDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CCable a order by a.berthCod asc,a.cableSeq asc";
			QueryParamLs params=new QueryParamLs();
			List<CCable>  ls=JpaUtils.findAll(jpql, params);
			for(CCable cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getCableCod());
				CBerth cBerth = JpaUtils.findById(CBerth.class, cc.getBerthCod());
				drop.setLabel(cc.getCableNo()+"/"+cBerth.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 公司缆桩下拉
	 */
	@RequestMapping(value = "/getGsCCableDrop")
	@ResponseBody
	public List<EzDropBean> getGsCCableDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CCable a join fetch a.CBerth m where m.berthTyp=:berthTyp order by a.berthCod asc,a.cableSeq asc";
			QueryParamLs params=new QueryParamLs();
			params.addParam("berthTyp", "01");
			List<CCable>  ls=JpaUtils.findAll(jpql, params);
			for(CCable cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getCableCod());
				CBerth cBerth = JpaUtils.findById(CBerth.class, cc.getBerthCod());
				drop.setLabel(cc.getCableNo()+"/"+cBerth.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 作业码头下拉
	 */
	@RequestMapping(value = "/getCDockDrop")
	@ResponseBody
	public List<EzDropBean> getCDockDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "select a from CDock a";
			QueryParamLs params=new QueryParamLs();
			List<CDock>  ls=JpaUtils.findAll(jpql, params);
			for(CDock cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getDockCod());
				drop.setLabel(cc.getDockNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipCodIds) {
		cShipDataService.removeAll(shipCodIds);
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
	    return cShipDataService.find(hdQuery);
	}
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findHisData")
	@ResponseBody
	public HdEzuiDatagridData findHisData(@RequestBody HdQuery hdQuery) {
	    return cShipDataService.findHisData(hdQuery);
	}
	/**
     * 集团查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findJt")
	@ResponseBody
	public HdEzuiDatagridData findJt(@RequestBody HdQuery hdQuery) {
	    return cShipDataService.findJt(hdQuery);
	}
	
	/**
     * 集团查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findNewJt")
	@ResponseBody
	public HdEzuiDatagridData findNewJt(@RequestBody HdQuery hdQuery) {
	    return cShipDataService.findNewJt(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CShipData findone(String shipCodId) {
		if (HdUtils.strIsNull(shipCodId)) {
			CShipData cShipData = new CShipData();
			return cShipData;
		}
		return cShipDataService.findone(shipCodId);
	}
	
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findHis")
	@ResponseBody
	public CShipDataHis findHis(String id) {
		return cShipDataService.findHis(id);
	}
	
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public CShipData findData(String eShipNam, String shipImo, String cShipNam) {
		return cShipDataService.findData(eShipNam, shipImo, cShipNam);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CShipData> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cShipDataService.save(gridData);
	}
	
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/saveData")
	@ResponseBody	
	public HdMessageCode saveData(@RequestBody HdEzuiSaveDatagridData<CShipData> gridData, String shipCodId) {
	 	 //CommonUtil.initEntity(gridData);
		return cShipDataService.saveData(gridData,shipCodId);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CShipData cShipData) {

		return cShipDataService.saveone(cShipData);
	}
}
