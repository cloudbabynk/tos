package net.huadong.tech.gate.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.gate.service.GateTruckContractService;
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
@RequestMapping("webresources/login/gate/GateTruckContract")
public class GateTruckContractController  {
	
	@Autowired
	private GateTruckContractService gateTruckContractService; 
	
	//菜单进入
	@RequestMapping(value = "/gatetruckcontract.htm")
	public String  pageTh(String Type,Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		model.addAttribute("Type", Type);
		return "system/gate/gatetruckcontract";
	}
	/**
	 * 车牌号下拉
	 */
	@RequestMapping(value = "/getCGateDrop")
	@ResponseBody
	public List<EzDropBean> getCGateDrop(String gateTyp) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CGate a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			if(HdUtils.strNotNull(gateTyp)){
				jpql += "and a.gateTyp =:gateTyp";
				params.addParam("gateTyp", gateTyp);
			}
			List<CGate>  ls=JpaUtils.findAll(jpql, params);
			for(CGate cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getGateNo());
				drop.setLabel(cc.getGateNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 车牌号下拉
	 */
	@RequestMapping(value = "/getCTruckDrop")
	@ResponseBody
	public List<EzDropBean> getCTruckDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CTruck a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CTruck>  ls=JpaUtils.findAll(jpql, params);
			for(CTruck cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getTruckNo());
				drop.setLabel(cc.getPlatNo());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		gateTruckContractService.removeAll(ids);
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
	    return gateTruckContractService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public GateTruckContract findone(String id) {
		if (HdUtils.strIsNull(id)) {
			GateTruckContract gateTruckContract = new GateTruckContract();
			return gateTruckContract;
		}
		return gateTruckContractService.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<GateTruckContract> gridData,String singleId,String truckNo,String planNum,String inGatTim,String gateNo) {
	 	 //CommonUtil.initEntity(gridData);
		return gateTruckContractService.save(gridData,singleId,truckNo,planNum,inGatTim,gateNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody GateTruckContract gateTruckContract) {

		return gateTruckContractService.saveone(gateTruckContract);
	}
	
}
