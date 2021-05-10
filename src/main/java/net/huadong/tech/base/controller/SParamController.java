package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.SParamService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.SParam;

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
@RequestMapping("webresources/login/base/SParam")
public class SParamController  {
	
	@Autowired
	private SParamService sParamService; 
	

	
	//菜单进入
		@RequestMapping(value = "/sparam.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/sparam";
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
	    return sParamService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public SParam findone(String paramId) {
		if (HdUtils.strIsNull(paramId)) {
			SParam sParam = new SParam();
			return sParam;
		}
		return sParamService.findone(paramId);
	}
	
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<SParam> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return sParamService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody SParam sParam) {

		return sParamService.saveone(sParam);
	}
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String paramId) {
		sParamService.removeAll(paramId);
		return HdUtils.genMsg();
	}
	@RequestMapping(value = "/removeById")
	@ResponseBody
	public HdMessageCode removeById(String paramId) {
		sParamService.removeById(paramId);
		return HdUtils.genMsg();
	}
	
}
