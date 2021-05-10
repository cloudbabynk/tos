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
import net.huadong.tech.base.entity.MfWork;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.service.MfWorkService;
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
@RequestMapping("webresources/login/ship/MfWork")
public class MfWorkController  {
	
	@Autowired
	private MfWorkService mfWorkService; 
	
	//菜单进入
	@RequestMapping(value = "/mfwork.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/mfwork";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		mfWorkService.removeAll(ids);
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
	    return mfWorkService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public MfWork findone(String id) {
		if (HdUtils.strIsNull(id)) {
			MfWork bean = new MfWork();
			return bean;
		}
		return mfWorkService.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MfWork> gridData,String shipNo) {
	 	 //CommonUtil.initEntity(gridData);
		return mfWorkService.save(gridData,shipNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MfWork mfWork) {
		return mfWorkService.saveone(mfWork);
	}
}
