package net.huadong.tech.damage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MOverlenghConfirm;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.damage.service.MOverlenghConfirmService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/damage/MOverlenghConfirm")
public class MOverlengthConfirmController  {
	
	@Autowired
	private MOverlenghConfirmService mOverlenghConfirmService; 
	
	//菜单进入
		@RequestMapping(value = "/moverlenghconfirm.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/damage/moverlenghconfirm";
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
	    return mOverlenghConfirmService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MOverlenghConfirm> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return mOverlenghConfirmService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String confirmids) {
		mOverlenghConfirmService.removeAll(confirmids);
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
	public MOverlenghConfirm findone(String confirmid) {
		if (HdUtils.strIsNull(confirmid)) {
			MOverlenghConfirm mOverlenghConfirm = new MOverlenghConfirm();
			return mOverlenghConfirm;
		}
		return mOverlenghConfirmService.findone(confirmid);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MOverlenghConfirm mOverlenghConfirm) {

		return mOverlenghConfirmService.saveone(mOverlenghConfirm);
	}
	
	@RequestMapping(value = "/getCarTypDrop")
	@ResponseBody
	public List<EzDropBean> getCarTypDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CCarTyp a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CCarTyp>  ls=JpaUtils.findAll(jpql, params);
			for(CCarTyp sb:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(sb.getCarTyp());
				drop.setLabel(sb.getCarTypNam());
				list.add(drop);
			}
			return list;
		}
	@RequestMapping(value = "/getShipBillDrop")
	@ResponseBody
	public List<EzDropBean> getShipBillDrop(String shipNo) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  ShipBill a  where 1=1 and a.shipNo=:shipNo";
			QueryParamLs params=new QueryParamLs();
			params.addParam("shipNo",shipNo);
			List<ShipBill>  ls=JpaUtils.findAll(jpql, params);
			for(ShipBill sb:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(sb.getBillNo());
				drop.setLabel(sb.getBillNo());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 所属品牌下拉
	 */
	@RequestMapping(value = "/getCBrandDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CBrand a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CBrand> ls = JpaUtils.findAll(jpql, params);
		for (CBrand cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getBrandCod());
			drop.setLabel(cc.getBrandNam());
			list.add(drop);
		}
		return list;
	}




	@RequestMapping(value = "/getCCarKind")
	@ResponseBody
	public List<EzDropBean> getCCarKind() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarKind a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CCarKind> ls = JpaUtils.findAll(jpql, params);
		for (CCarKind cck : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cck.getCarKind());
			drop.setLabel(cck.getCarKindNam());
			list.add(drop);
		}
		return list;
	}

	@RequestMapping(value = "/getCCarTypByKindBrand")
	@ResponseBody
	public List<EzDropBean> getCCarTypByKind(String brand ,String kind ) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarTyp a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		if(CommonUtil.strNotNull(kind)){
			jpql+=" and a.carKind =:carKind";
			params.addParam("carKind",kind);
		}
		if(CommonUtil.strNotNull(brand)){
			jpql+=" and a.brandCod =:brandCod";
			params.addParam("brandCod",brand);
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

	@RequestMapping(value = "/getCCarTyp")
	@ResponseBody
	public List<EzDropBean> getCCarTyp() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarTyp a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CCarTyp> ls = JpaUtils.findAll(jpql, params);
		for (CCarTyp cct : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cct.getCarTyp());
			drop.setLabel(cct.getCarTypNam());
			list.add(drop);
		}
		return list;
	}
	@RequestMapping(value = "/getCCyArea")
	@ResponseBody
	public List<EzDropBean> getCCyArea() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCyArea a  where 1=1 order by a.cyAreaNo  asc";
		QueryParamLs params = new QueryParamLs();
		List<CCyArea> ls = JpaUtils.findAll(jpql, params);
		for (CCyArea cck : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cck.getCyAreaNo());
			drop.setLabel(cck.getCyAreaNam());
			list.add(drop);
		}
		return list;
	}
}
