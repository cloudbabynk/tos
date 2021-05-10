package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.base.service.CDamgLevelService;
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
@RequestMapping("webresources/login/base/CDamgLevel")
public class CDamgLevelController  {
	
	@Autowired
	private CDamgLevelService cDamgLevelService; 
	
	//菜单进入
	@RequestMapping(value = "/cdamglevel.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cdamglevel";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String damgLevelCods) {
		cDamgLevelService.removeAll(damgLevelCods);
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
	    return cDamgLevelService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CDamgLevel findone(String damgLevelCod) {
		if (HdUtils.strIsNull(damgLevelCod)) {
			CDamgLevel cDamgLevel = new CDamgLevel();
			return cDamgLevel;
		}
		return cDamgLevelService.findone(damgLevelCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CDamgLevel> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cDamgLevelService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CDamgLevel cDamgLevel) {

		return cDamgLevelService.saveone(cDamgLevel);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCDamgLevel")
	@ResponseBody
	public HdMessageCode findCDamgLevel(String damgLevelCod) {
		return cDamgLevelService.findCDamgLevel(damgLevelCod);
	}
}
