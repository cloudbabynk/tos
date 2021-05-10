package net.huadong.tech.ship.controller;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.ship.entity.ShipTimWork;
import net.huadong.tech.ship.service.ShipTimWorkService;
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
@RequestMapping("webresources/login/ship/ShipTimWork")
public class ShipTimWorkController  {
	
	@Autowired
	private ShipTimWorkService shipTimWorkService; 
	
	//菜单进入
		@RequestMapping(value = "/shiptimwork.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/ship/shiptimwork";
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
	    return shipTimWorkService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipTimWork> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipTimWorkService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipTimworkIds) {
		shipTimWorkService.removeAll(shipTimworkIds);
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
	public ShipTimWork findone(String shipTimworkId) {
		if (HdUtils.strIsNull(shipTimworkId)) {
			ShipTimWork ShipTimWork = new ShipTimWork();
			return ShipTimWork;
		}
		return shipTimWorkService.findone(shipTimworkId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipTimWork ShipTimWork) {

		return shipTimWorkService.saveone(ShipTimWork);
	}
	
}
