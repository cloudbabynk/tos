package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.base.service.CMachTypService;
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
@RequestMapping("webresources/login/base/CMachTyp")
public class CMachTypController  {
	
	@Autowired
	private CMachTypService cMachTypService; 
	
	//菜单进入
	@RequestMapping(value = "/cmachtyp.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cmachtyp";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String machTypCods) {
		cMachTypService.removeAll(machTypCods);
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
	    return cMachTypService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CMachTyp findone(String machTypCod) {
		if (HdUtils.strIsNull(machTypCod)) {
			CMachTyp cMachTyp = new CMachTyp();
			return cMachTyp;
		}
		return cMachTypService.findone(machTypCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CMachTyp> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cMachTypService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CMachTyp cMachTyp) {

		return cMachTypService.saveone(cMachTyp);
	}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCMachTyp")
	@ResponseBody
	public HdMessageCode findCMachTyp(String machTypCod) {
		return cMachTypService.findCMachTyp(machTypCod);
	}
}
