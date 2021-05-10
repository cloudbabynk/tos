package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CSpecPlac;
import net.huadong.tech.base.service.CSpecPlacService;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
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
@RequestMapping("webresources/login/base/CSpecPlac")
public class CSpecPlacController  {
	
	@Autowired
	private CSpecPlacService cSpecPlacService; 
	
	//菜单进入
	@RequestMapping(value = "/cspecplac.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cspecplac";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String specPlacs) {
		cSpecPlacService.removeAll(specPlacs);
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
	    return cSpecPlacService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CSpecPlac findone(String specPlac) {
		if (HdUtils.strIsNull(specPlac)) {
			CSpecPlac cSpacPlac = new CSpecPlac();
			return cSpacPlac;
		}
		return cSpecPlacService.findone(specPlac);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CSpecPlac> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cSpecPlacService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CSpecPlac cSpecPlac) {

		return cSpecPlacService.saveone(cSpecPlac);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCSpecPlac")
	@ResponseBody
	public HdMessageCode findCSpecPlac(String specPlac) {
		return cSpecPlacService.findCSpecPlac(specPlac);
	}
}
