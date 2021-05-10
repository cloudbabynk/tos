package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CHoliday;
import net.huadong.tech.base.service.CHolidayService;
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
@RequestMapping("webresources/login/base/CHoliday")
public class CHolidayController  {
	
	@Autowired
	private CHolidayService cHolidayService; 
	
	//菜单进入
	@RequestMapping(value = "/choliday.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/choliday";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String hIds) {
		HdMessageCode hdMessageCode = new HdMessageCode();
		try{
		cHolidayService.removeAll(hIds);
		return HdUtils.genMsg();
	}catch (Exception e){
		hdMessageCode.setCode("-1");
		hdMessageCode.setMessage(e.getMessage());
		return  hdMessageCode;
	}
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
	    return cHolidayService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CHoliday findone(String hId) {
		return cHolidayService.findone(hId);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CHoliday> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cHolidayService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CHoliday cHoliday) {

		return cHolidayService.saveone(cHoliday);
	}
	/**
	 * 初始化年度节假日
	 * @param cHoliday
	 * @return
	 */
	@RequestMapping("/initHoliday")
	@ResponseBody
	public HdMessageCode initHoliday(String year) {

		cHolidayService.initHoliday(year);
		return HdUtils.genMsg();
	}
}
