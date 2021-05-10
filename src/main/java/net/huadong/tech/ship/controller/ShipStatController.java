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
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.service.ShipStatService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.Axis2Util;
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
@RequestMapping("webresources/login/ship/ShipStat")
public class ShipStatController  {
	
	@Autowired
	private ShipStatService shipStatService; 
	
	//菜单进入
	@RequestMapping(value = "/shipstat.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/shipstat";
	}
	
	//船舶动态菜单进入
		@RequestMapping(value = "/shipdt.htm")
		public String  pageDt(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/shipdt";
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
	public HdMessageCode removeAll(String shipStatIds) {
		shipStatService.removeAll(shipStatIds);
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
	    return shipStatService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipStat findone(String shipStatId) {
		if (HdUtils.strIsNull(shipStatId)) {
			ShipStat shipStat = new ShipStat();
			return shipStat;
		}
		return shipStatService.findone(shipStatId);
	}
	
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findJgsj")
	@ResponseBody
	public ShipTrend findJgsj(String shipNo) {
		return shipStatService.findJgsj(shipNo);
	}
	
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findHl")
	@ResponseBody
	public ShipStat findHl(String shipNo, String iEId) {
		return shipStatService.findHl(shipNo,iEId);
	}
	
	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/importShipStats")
	@ResponseBody
	public HdMessageCode importShipStats(String shipStatIds) {
		shipStatService.importShipStats(shipStatIds);
		return HdUtils.genMsg();
	}
	
	/**
	 * 上报新集团
	 */
	@RequestMapping(value = "/importNewShipStats")
	@ResponseBody
	public HdMessageCode importNewShipStats(String shipStatIds) {
		shipStatService.importNewShipStats(shipStatIds);
		return HdUtils.genMsg();
	}
	
	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/cancelShipStat")
	@ResponseBody
	public HdMessageCode cancelShipStat(String shipStatId) {
		shipStatService.cancelShipStat(shipStatId);
		return HdUtils.genMsg();
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipStat> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipStatService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipStat shipStat) {

		return shipStatService.saveone(shipStat);
	}
	//获取停时听取
	@RequestMapping(value = "/getshipstat")
	@ResponseBody
	public HdMessageCode getshipstat(String groupShipNo) {
		shipStatService.getshipstat(groupShipNo);
		return HdUtils.genMsg();
	}
}
