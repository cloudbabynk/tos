package net.huadong.tech.damage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.LockCarDoc;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.damage.service.LockCarDocService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
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
@RequestMapping("webresources/login/damage/LockCarDoc")
public class LockCarDocController  {
	
	@Autowired
	private LockCarDocService lockCarDocService; 
	
	//菜单进入
		@RequestMapping(value = "/lockcardoc.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/damage/lockcardoc";
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
	    return lockCarDocService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<LockCarDoc> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return lockCarDocService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String lockcarIds) {
		lockCarDocService.removeAll(lockcarIds);
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
	public LockCarDoc findone(String lockcarId) {
		if (HdUtils.strIsNull(lockcarId)) {
			LockCarDoc lockCarDoc = new LockCarDoc();
			return lockCarDoc;
		}
		return lockCarDocService.findone(lockcarId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody LockCarDoc lockCarDoc) {
		return lockCarDocService.saveone(lockCarDoc);
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

}
