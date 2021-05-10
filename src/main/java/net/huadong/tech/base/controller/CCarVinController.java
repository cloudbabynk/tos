package net.huadong.tech.base.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CAreaService;
import net.huadong.tech.base.service.CCarVinService;
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
@RequestMapping("webresources/login/base/CCarVin")
public class CCarVinController  {
	
	@Autowired
	private CCarVinService cCarVinService; 
	
	//菜单进入
		@RequestMapping(value = "/ccarvin.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/ccarvin";
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
	    return cCarVinService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCarVin> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		
		return cCarVinService.ezuiSaveSysCode(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String vinIds) {
		cCarVinService.removeAll(vinIds);
		return HdUtils.genMsg();
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CCarVin findone(String vinId) {
		if (HdUtils.strIsNull(vinId)) {
			CCarVin cCarVin = new CCarVin();
			return cCarVin;
		}
		return cCarVinService.findone(vinId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CCarVin cCarVin) {

		return cCarVinService.saveone(cCarVin);
	}
	
	@RequestMapping({"/findvinNoByCarTyp"})
	@ResponseBody
	public HdEzuiDatagridData findvinNoByCarTyp(@RequestBody HdQuery params) {
		return this.cCarVinService.findvinNoByCarTyp(params, true);
	}

    /*
	 * 更新库存
	 */
	@RequestMapping("/updatePortCarTyp")
	@ResponseBody
	public HdMessageCode updatePortCarTyp(String carTyp,String vinNo) {
		return cCarVinService.updatePortCarTyp(carTyp,vinNo);
	}
	
}
