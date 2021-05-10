package net.huadong.tech.work.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipReportExcel;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.ShipTrendExcel;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.ListToExcel;
import net.huadong.tech.util.ShipTrendExcelTest;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.MPdaWorkCommand;
import net.huadong.tech.work.service.MPdaWorkCommandService;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/work/MPdaWorkCommand")
public class MPdaWorkCommandController {

	@Autowired
	private MPdaWorkCommandService workCommandService;

	// 菜单进入
	@RequestMapping(value = "/mpdaworkcommand.htm")
	public String pageTh(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/mpdaworkcommand";
	}
	
	// 菜单进入
	@RequestMapping(value = "/mpdaworkcommandall.htm")
	public String pageAll(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/mpdaworkcommandall";
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
		return workCommandService.find(hdQuery);
	}
	
	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MPdaWorkCommand> gridData) {
		// CommonUtil.initEntity(gridData);
		return workCommandService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MPdaWorkCommand bean, String type) {
		return workCommandService.saveone(bean, type);
	}
	
	
	/*
	 * 转栈理货批量
	 */
	@RequestMapping("/saveLxlh")
	@ResponseBody
	public HdMessageCode saveLxlh(String shipNo, String type) {
		return workCommandService.saveLxlh(shipNo, type);
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String queueIds) {
		workCommandService.removeAll(queueIds);
		return HdUtils.genMsg();
	}

}
