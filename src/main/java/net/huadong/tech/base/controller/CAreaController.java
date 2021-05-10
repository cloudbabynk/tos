package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CAreaService;
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
@RequestMapping("webresources/login/base/CArea")
public class CAreaController  {
	
	@Autowired
	private CAreaService cAreaService; 
	
	//菜单进入
		@RequestMapping(value = "/carea.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/carea";
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
	    return cAreaService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CArea> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cAreaService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String areaCods) {
		HdMessageCode hdMessageCode = new HdMessageCode();
		try {
			cAreaService.removeAll(areaCods);
			return HdUtils.genMsg();
		}catch (Exception e)
		{
			hdMessageCode.setMessage(e.getMessage());
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CArea findone(String areaCod) {
		if (HdUtils.strIsNull(areaCod)) {
			CArea cArea = new CArea();
			return cArea;
		}
		return cAreaService.findone(areaCod);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CArea cArea) {

		return cAreaService.saveone(cArea);
	}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCArea")
	@ResponseBody
	public HdMessageCode findCArea(String areaCod) {
		return cAreaService.findCArea(areaCod);
	}
}
