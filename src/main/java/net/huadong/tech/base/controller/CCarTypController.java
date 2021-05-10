package net.huadong.tech.base.controller;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.*;
import net.huadong.tech.base.service.CCarTypService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.AuthUserController;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/base/CCarTyp")
public class CCarTypController  {
	
	@Autowired
	private CCarTypService cCarTypService; 
	

	//菜单进入
		@RequestMapping(value = "/ccartyp.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/ccartyp";
		}

	//菜单进入
	@RequestMapping(value = "/ccartypinsert.htm")
	public String  pageTs(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/ccartypinsert";
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
	    return cCarTypService.find(hdQuery);
	}

	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CCarTyp findone(String carTyp) {
		if (HdUtils.strIsNull(carTyp)) {
			CCarTyp cCarTyp = new CCarTyp();
			return cCarTyp;
		}
		return cCarTypService.findone(carTyp);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCarTyp> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cCarTypService.save(gridData);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CCarTyp cCarTyp) {

		return cCarTypService.saveone(cCarTyp);
	}

	/**
	 * saveoneByCarTyp
	 * @return
	 */
	@RequestMapping("/saveoneByCarTyp")
	@ResponseBody
	public HdMessageCode saveoneByCarTyp(String carTyp,String carFeeTypNam,String carFeeTyp ) {

		return cCarTypService.saveoneByCarTyp(carTyp,carFeeTypNam,carFeeTyp);
	}

//查询货物带出计费
@RequestMapping(value = "/getCarFeeTypNam")
@ResponseBody
public List<EzDropBean> getCarFeeTypNam() {
	List<EzDropBean> list = new ArrayList<EzDropBean>();

	String jpql = " select a  from  CCarTypFeeName a  where 1=1 ";
	QueryParamLs params = new QueryParamLs();
	List<CCarTypFeeName> ls = JpaUtils.findAll(jpql, params);
	for (CCarTypFeeName cc : ls) {
		EzDropBean drop = new EzDropBean();
		drop.setValue(cc.getId());
		drop.setLabel(cc.getCarTypName());
		list.add(drop);
	}
	return list;
}



	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String carTyps) {
		HdMessageCode hdMessageCode =new HdMessageCode();
		try{
		cCarTypService.removeAll(carTyps);
		return HdUtils.genMsg();
	}catch (Exception e){
		hdMessageCode.setCode("-1");
		hdMessageCode.setMessage(e.getMessage());
		return  hdMessageCode;
	}
	}
	
	/**
	 * 批量审核
	 */
	@RequestMapping(value = "/checkAll")
	@ResponseBody
	public HdMessageCode checkAll(String carTyps) {
		cCarTypService.checkAll(carTyps);
		return HdUtils.genMsg();
	}
	/**
	 * 所属品牌下拉
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
	 * 车属类别下拉
	 */
	@RequestMapping(value = "/getCCarKindDrop")
	@ResponseBody
	public List<EzDropBean> getCCarKindDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CCarKind a  where 1=1 and a.checkFlag='1'";
			QueryParamLs params=new QueryParamLs();
			List<CCarKind>  ls=JpaUtils.findAll(jpql, params);
			for(CCarKind cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getCarKind());
				drop.setLabel(cc.getCarKindNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 生产厂家下拉
	 */
	@RequestMapping(value = "/getCFactoryDrop")
	@ResponseBody
	public List<EzDropBean> getCFactoryDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CFactory a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CFactory>  ls=JpaUtils.findAll(jpql, params);
			for(CFactory cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getFactoryCod());
				drop.setLabel(cc.getFactoryNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCCarTyp")
	@ResponseBody
	public HdMessageCode findCCarTyp(String carTyp) {
		return cCarTypService.findCCarTyp(carTyp);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findOne")
	@ResponseBody
	public CCarTyp findOne(String brandCod, String carKind) {
		return cCarTypService.findOne(brandCod,carKind);
	}
	
}
