package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.ShipTimWork;
import net.huadong.tech.ship.entity.ShipWorkman;
import net.huadong.tech.ship.service.ShipTimWorkService;
import net.huadong.tech.ship.service.ShipWorkManService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
/**
 * @author  wsw
 */
@Controller
@RequestMapping("webresources/login/ship/ShipWorkman")
public class ShipWorkManController {
	
	@Autowired
	private ShipWorkManService shipWorkManService; 
	//菜单进入
	@RequestMapping(value = "/shipworkman.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/shipworkman";
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
	    return shipWorkManService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipWorkman> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipWorkManService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipWorkmanIds) {
		shipWorkManService.removeAll(shipWorkmanIds);
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
	public ShipWorkman findone(String shipWorkmanId) {
		if (HdUtils.strIsNull(shipWorkmanId)) {
			ShipWorkman shipWorkman = new ShipWorkman();
			return shipWorkman;
		}
		return shipWorkManService.findone(shipWorkmanId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipWorkman ShipWorkman) {

		return shipWorkManService.saveone(ShipWorkman);
	}

	/**
	 * 班次下拉
	 */
	@RequestMapping(value = "/getWorkRunCodDrop")
	@ResponseBody
	public List<EzDropBean> getWorkRunCodDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CWorkRun a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CWorkRun> ls = JpaUtils.findAll(jpql, params);
		for (CWorkRun cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getWorkRun());
			drop.setLabel(cc.getWorkRunNam());
			list.add(drop);
		}
		return list;
	}
	@RequestMapping("/genExecutionByship")
	@ResponseBody
	public HdEzuiDatagridData genExecutionByship(@RequestBody Map map) {
		return shipWorkManService.genExecutionByship(map);
	}
}
