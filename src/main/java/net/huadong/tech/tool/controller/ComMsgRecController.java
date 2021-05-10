package net.huadong.tech.tool.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.com.entity.ComMsgRec;
import net.huadong.tech.tool.service.ComMsgRecService;
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
@RequestMapping("webresources/login/tool/ComMsgRec")
public class ComMsgRecController  {
	
	@Autowired
	private ComMsgRecService comMsgRecService; 
	
	//菜单进入
	@RequestMapping(value = "/commsgrec.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/tool/commsgrec";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String recIds) {
		comMsgRecService.removeAll(recIds);
		return HdUtils.genMsg();
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public HdMessageCode update() {
		comMsgRecService.update();
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
	    return comMsgRecService.find(hdQuery);
	}
	
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findNum")
	@ResponseBody
	public ComMsgRec findNum() {
	    return comMsgRecService.findNum();
	}
	
}
