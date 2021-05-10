package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.VGroupCorpClient;
import net.huadong.tech.Interface.entity.VGroupCorpFlow;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CBrandDetail;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.service.CBrandDetailService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Controller
@RequestMapping("webresources/login/base/CBrandDetail")
public class CBrandDetailController {
	
	@Autowired
	private CBrandDetailService cBrandDetailService;
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return cBrandDetailService.find(hdQuery);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CBrandDetail> gridData) {
		return cBrandDetailService.ezuiSaveSysCode(gridData);
	}
	
	/**
	 * 品牌下拉
	 */
	@RequestMapping(value = "/getCBrandDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandDrop() {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		String jpql= " select a  from  CBrand a  where 1=1 and a.checkFlag='1' ";
		QueryParamLs params=new QueryParamLs();
		List<CBrand>  ls=JpaUtils.findAll(jpql, params);
		for(CBrand cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getBrandCod());
			drop.setLabel(cc.getBrandNam());
			list.add(drop);
		}
		return list;
	}
	
	/**
	 * 总调 货主、货代、收货人、发货人
	 */
	@RequestMapping(value = "/getVCClientCodDrop")
	@ResponseBody
	public List<EzDropBean> getVCClientCodDrop(String q) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		String jpql= " select a  from  VGroupCorpClient a where 1=1 ";
		QueryParamLs params=new QueryParamLs();
		if (HdUtils.strNotNull(q)){
			jpql += "and a.clientName like :clientName";
			params.addParam("clientName", "%" + q + "%");
		}
		List<VGroupCorpClient>  ls=JpaUtils.findAll(jpql, params);
		for(VGroupCorpClient cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getClientCode());
			drop.setLabel(cc.getClientName());
			list.add(drop);
		}
		return list;
	}
	
	/*
	 * 货主对应 getGroupCClientCodDrop
	 */
	@RequestMapping(value = "/getGroupCClientCodDrop")
	@ResponseBody
	public List<EzDropBean> getGroupCClientCodDrop(String q) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		String jpql= "SELECT c FROM CClientCod c where c.groupClientCod is not null ";
		QueryParamLs params=new QueryParamLs();
		if (HdUtils.strNotNull(q)){
			jpql += "and c.cClientNam like :cClientNam";
			params.addParam("cClientNam", "%" + q + "%");
		}
		List<CClientCod>  ls=JpaUtils.findAll(jpql, params);
		for(CClientCod cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getGroupClientCod());
			drop.setLabel(cc.getcClientNam());
			list.add(drop);
		}
		return list;
	}
	
	/**
	 * 产地、流向
	 */
	@RequestMapping(value = "/getVGroupCorpFlow")
	@ResponseBody
	public List<EzDropBean> getVGroupCorpFlow() {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		String jpql= " select a  from  VGroupCorpFlow a  ";
		QueryParamLs params=new QueryParamLs();
		List<VGroupCorpFlow>  ls=JpaUtils.findAll(jpql, params);
		for(VGroupCorpFlow cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getOriginCode());
			drop.setLabel(cc.getOriginName());
			list.add(drop);
		}
		return list;
	}
	
	/**
	 * 产地、流向展示
	 */
	@RequestMapping(value = "/findVGroupCorpFlow")
	@ResponseBody
	public String findVGroupCorpFlow(String originCode) {
		String originName = "";
		if(HdUtils.strNotNull(originCode)) {
			String jpql = "SELECT v FROM VGroupCorpFlow v where v.originCode=:originCode";
			QueryParamLs params = new QueryParamLs();
			params.addParam("originCode", originCode);
			List<VGroupCorpFlow> vGroupCorpFlowList = JpaUtils.findAll(jpql, params);
			VGroupCorpFlow vGroupCorpFlow = vGroupCorpFlowList.get(0);
			if(vGroupCorpFlow != null) {
				originName = vGroupCorpFlow.getOriginName();
			}
		}
		if(HdUtils.strIsNull(originName)) {
			originName = originCode;
		}
		return originName;
	}
	
	/**
	 * 品牌展示
	 */
	@RequestMapping(value = "/findCBrand")
	@ResponseBody
	public String findCBrand(String brandCod) {
		String brandNam = "";
		if(HdUtils.strNotNull(brandCod)) {
			CBrand cBrand = JpaUtils.findById(CBrand.class, brandCod);
			if(cBrand != null) {
				brandNam = cBrand.getBrandNam();
			}
		}
		if(HdUtils.strIsNull(brandNam)) {
			brandNam = brandCod;
		}
		return brandNam;
	}
	
	/**
	 * 货主货代展示  
	 */
	@RequestMapping(value = "/findVCClientCod")
	@ResponseBody
	public String findVCClientCod(String clientCode) {
		String clientName = "";
		if(HdUtils.strNotNull(clientCode)) {
			String jpql = "SELECT v FROM VGroupCorpClient v where v.clientCode=:clientCode";
			QueryParamLs params = new QueryParamLs();
			params.addParam("clientCode", clientCode);
			List<VGroupCorpClient> vGroupCorpClientList = JpaUtils.findAll(jpql, params);
			VGroupCorpClient vGroupCorpClient = vGroupCorpClientList.get(0);
			if(vGroupCorpClient != null) {
				clientName = vGroupCorpClient.getClientName();
			}
		}
		if(HdUtils.strIsNull(clientName)) {
			clientName = clientCode;
		}
		return clientName;
	}
	
	/**
	 * 货主货代对应展示   
	 */
	@RequestMapping(value = "/findGroupCClientCod")
	@ResponseBody
	public String findGroupCClientCod(String gruopClientCode) {
		String clientName = "";
		if(HdUtils.strNotNull(gruopClientCode)) {
			String jpql = "SELECT c FROM CClientCod c where c.groupClientCod is not null and c.groupClientCod=:groupClientCod";
			QueryParamLs params = new QueryParamLs();
			params.addParam("groupClientCod", gruopClientCode);
			List<CClientCod> vGroupCorpClientList = JpaUtils.findAll(jpql, params);
			CClientCod cClientCodList = vGroupCorpClientList.get(0);
			if(cClientCodList != null) {
				clientName = cClientCodList.getcClientNam();
			}
		}
		if(HdUtils.strIsNull(clientName)) {
			clientName = gruopClientCode;
		}
		return clientName;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		cBrandDetailService.removeAll(ids);
		return HdUtils.genMsg();
	}

}
