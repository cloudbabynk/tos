package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CCyRowService;
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
@RequestMapping("webresources/login/base/CCyRow")
public class CCyRowController  {
	
	@Autowired
	private CCyRowService cCyRowService; 
	
	//菜单进入
		@RequestMapping(value = "/ccyrow.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/ccyrow";
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
	    return cCyRowService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCyRow> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cCyRowService.save(gridData);
	}
	/**
	 *场地策划ccyrow 空车位
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findcdch")
	@ResponseBody
	public HdEzuiDatagridData findcdch(@RequestBody HdQuery hdQuery) {
	    return cCyRowService.findcdch(hdQuery);
	}
}
