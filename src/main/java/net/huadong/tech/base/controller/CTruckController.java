package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.base.service.CTruckService;
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
 * @yl 
 */
@Controller
@RequestMapping("webresources/login/base/CTruck")
public class CTruckController  {
	
	@Autowired
	private CTruckService cTruckService; 
	
	//菜单进入
	@RequestMapping(value = "/ctruck.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/ctruck";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String truckNos) {
		cTruckService.removeAll(truckNos);
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
	    return cTruckService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CTruck findone(String truckNo) {
		if (HdUtils.strIsNull(truckNo)) {
			CTruck cTruck = new CTruck();
			return cTruck;
		}
		return cTruckService.findone(truckNo);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CTruck> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cTruckService.save(gridData);
	}
	
	/**
     * 是否禁提
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/ifForbid")
	@ResponseBody	
	public HdMessageCode ifForbid(String truckNo) {
		return cTruckService.ifForbid(truckNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CTruck cTruck) {

		return cTruckService.saveone(cTruck);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCTruck")
	@ResponseBody
	public HdMessageCode findCTruck(String truckNo) {
		return cTruckService.findCTruck(truckNo);
	}
}
