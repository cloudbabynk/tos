
package net.huadong.tech.privilege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.SysField;
import net.huadong.tech.privilege.service.SysFieldService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author WangHZ 2017年7月27日
 *
 */
@Controller
// @RequestMapping("login/SysField")
@RequestMapping("webresources/login/privilege/SysField")
public class SysFieldController {
	@Autowired
	private SysFieldService sysFieldService;

	@RequestMapping(value = "/sysfield.htm")
	public String privilege(String userId) { 
		return "system/privilege/syscode";
	}

	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery query) {
		return sysFieldService.find(query);
	}
	
	@RequestMapping(value = "/remove")
	@ResponseBody
	public HdMessageCode remove(String idLs) {
		return sysFieldService.remove(idLs);
	}
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode ezuiSave(@RequestBody HdEzuiSaveDatagridData<SysField> menu) {
        return sysFieldService.save(menu);
    }

}
