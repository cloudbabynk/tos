package net.huadong.tech.work.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.huadong.tech.base.service.CEmployeeService;

import jxl.Sheet;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.controller.CEmployeeController;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.privilege.entity.AuthUser;
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
import net.huadong.tech.work.entity.VWorkCommand;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.service.WorkCommandService;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/work/WorkCommand")
public class WorkCommandController {

	@Autowired
	private WorkCommandService workCommandService;
	
	@Autowired
	private CEmployeeService cEmployeeService; 

	// ????????????
	@RequestMapping(value = "/workcommand.htm")
	public String pageTh(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/workcommand";
	}
	@RequestMapping(value = "/xchtozhch.htm")
	public String xchtozhch(String Type, Model model) {
		Random random = new Random();
		AuthUser user = HdUtils.getCurUser();
		String empNo = cEmployeeService.findCemployee(user.getAccount()).getEmpNo();
		String classNo = cEmployeeService.findCemployee(user.getAccount()).getClassNo();
		model.addAttribute("empNo", empNo);
		model.addAttribute("classNo", classNo);
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/xchtozhch";
	}
	
	// ????????????
	@RequestMapping(value = "/workcommandjs.htm")
	public String pageThDc(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/workcommandjs";
	}
	
	// ????????????
	@RequestMapping(value = "/cyareaupdate.htm")
	public String pageThCyUp(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/cyareaupdate";
	}
	
	// ????????????
	@RequestMapping(value = "/workcommandzx.htm")
	public String pageThNmxc(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/workcommandzx";
	}
	
	
	// ????????????
	@RequestMapping(value = "/workcommandfcd.htm")
	public String pageFcd(String ShipNo, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("ShipNo", ShipNo);
		return "system/work/workcommandfcd";
	}

	// ????????????
	@RequestMapping(value = "/shipworkmonitor.htm")
	public String pageSw(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/shipworkmonitor";
	}

	// ????????????
	@RequestMapping(value = "/cargo.htm")
	public String pageLH(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/cargo";
	}
	
	// ????????????
	@RequestMapping(value = "/nmxclh.htm")
	public String pageNm(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/nmxclh";
	}
	
	// ????????????
	@RequestMapping(value = "/czzclh.htm")
	public String pageCzzc(String Type, Model model) {
		Random random = new Random();
		AuthUser user = HdUtils.getCurUser();
		String empNo = cEmployeeService.findCemployee(user.getAccount()).getEmpNo();
		String classNo = cEmployeeService.findCemployee(user.getAccount()).getClassNo();
		model.addAttribute("empNo", empNo);
		model.addAttribute("classNo", classNo);
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/czzclh";
	}
		
    // ??????????????????????????????
	@RequestMapping(value = "/jgscjl.htm")
	public String pageJg(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/jgscjl";
	}
	
	// ??????????????????????????????
	@RequestMapping(value = "/jgscjlsingle.htm")
	public String pageJgSingle(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/jgscjlsingle";
	}
	// ????????????
	@RequestMapping(value = "/zxzyqk.htm")
	public String pageZx(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/zxzyqk";
	}
	// ????????????
	@RequestMapping(value = "/hwcrkjl.htm")
	public String pageCrk(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/hwcrkjl";
	}
	//
	@RequestMapping(value = "/shipingreport.htm")
	public String pageSp(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/shippingreport";
	}
	/**
	 * ??????????????????
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
	 * ??????????????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findCyAreaUpdate")
	@ResponseBody
	public HdEzuiDatagridData findCyAreaUpdate(@RequestBody HdQuery hdQuery) {
		return workCommandService.findCyAreaUpdate(hdQuery);
	}
	
	
	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findOne")
	@ResponseBody
	public WorkCommand findOne(String queueId) {
		return workCommandService.findOne(queueId);
	}
	
	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDclh")
	@ResponseBody
	public HdEzuiDatagridData findDclh(@RequestBody HdQuery hdQuery) {
		return workCommandService.findDclh(hdQuery);
	}
	
	/**
	 * ???????????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findFcd")
	@ResponseBody
	public HdEzuiDatagridData findFcd(@RequestBody HdQuery hdQuery) {
		return workCommandService.findFcd(hdQuery);
	}
	
	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findJgcs")
	@ResponseBody
	public HdEzuiDatagridData findJgcs(@RequestBody HdQuery hdQuery,String Type) {
		return workCommandService.findJgcs(hdQuery,Type);
	}
	
	/**
	 * ????????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findJgcsWm")
	@ResponseBody
	public HdEzuiDatagridData findJgcsWm(@RequestBody HdQuery hdQuery) {
		return workCommandService.findJgcsWm(hdQuery);
	}
	
	@RequestMapping(value = "/exportZxzy", method = RequestMethod.POST)
	@ResponseBody
	public void exportZxzy(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findZxzy(params);
		workCommandService.exportZxzy(data,response);

	}
	
	@RequestMapping(value = "/nmzcExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void nmzcExportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findDclh(params);
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}
	
	/**
	 * ??????Excel
	 * 
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findJgcs(params,"PL");
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}
	
	/**
	 * ??????Excel
	 * 
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelWm", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcelWm(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findJgcsWm(params);
		//??????list??????
        workCommandService.exportExcel(data, response,params);
        
	}
	
	
	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findZxzy")
	@ResponseBody
	public HdEzuiDatagridData findZxzy(@RequestBody HdQuery hdQuery) {
		return workCommandService.findZxzy(hdQuery);
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/saveZpzclh")
	@ResponseBody
	public HdMessageCode saveZpzclh(@RequestBody CargoInfo cargoInfo, String type,String billNos) {
		workCommandService.saveZpzclh(cargoInfo,type,billNos);
		return HdUtils.genMsg();
	}
	
	/**
	 * ??????shipno?????? 
	 */
	@RequestMapping(value = "/findAllByShipNo", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseDamCod(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return workCommandService.chooseShipNo(hdEzuiQueryParams);
	}
	
	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkCommand> gridData) {
		// CommonUtil.initEntity(gridData);
		return workCommandService.save(gridData);
	}
	
	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/updateLhData")
	@ResponseBody
	public HdMessageCode updateLhData(@RequestBody CargoInfo data, String type) {
		// CommonUtil.initEntity(gridData);
		return workCommandService.updateLhData(data,type);
	}

	

	
	/*
	 * form??????
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody WorkCommand workCommand, String type) {
		return workCommandService.saveone(workCommand, type);
	}
	
	/*
	 * form??????
	 */
	@RequestMapping("/updateCyArea")
	@ResponseBody
	public HdMessageCode updateCyArea(String cyArealist, String shipNo, String brandCod) {
		return workCommandService.updateCyArea(cyArealist,shipNo,brandCod);
	}
	
	
	/*
	 * ??????????????????
	 */
	@RequestMapping("/saveZclh")
	@ResponseBody
	public HdMessageCode saveZclh(@RequestBody CargoInfo cargoInfo, String type, String portCarNos) {

		return workCommandService.saveZclh(cargoInfo, type, portCarNos);
	}
	
	
	/*
	 * ??????????????????
	 */
	@RequestMapping("/saveNmzclh")
	@ResponseBody
	public HdMessageCode saveNmzclh(@RequestBody CargoInfo cargoInfo,String type) {

		return workCommandService.saveNmzclh(cargoInfo,type);
	}
	
	/*
	 * ??????????????????
	 */
	@RequestMapping("/saveZzlh")
	@ResponseBody
	public HdMessageCode saveZzlh(@RequestBody CargoInfo cargoInfo, String type) {

		return workCommandService.saveZzlh(cargoInfo, type);
	}
	
	
	/*
	 * ??????????????????
	 */
	@RequestMapping("/saveXclh")
	@ResponseBody
	public HdMessageCode saveXclh(@RequestBody CargoInfo cargoInfo) {

		return workCommandService.saveXclh(cargoInfo);
	}
	
	/*
	 * ????????????????????????
	 */
	@RequestMapping("/saveNmXclh")
	@ResponseBody
	public HdMessageCode saveNmXclh(@RequestBody CargoInfo cargoInfo) {

		return workCommandService.saveNmXclh(cargoInfo);
	}

	@RequestMapping(value = "/findWorking")
	@ResponseBody
	public List findWorking(String shipNo) {
		return workCommandService.findWorking(shipNo);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/jkWanChuan")
	@ResponseBody
	public HdMessageCode jkWanChuan(String shipNo) {
		workCommandService.jkWanChuan(shipNo);
		return HdUtils.genMsg();
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/ckWanChuan")
	@ResponseBody
	public HdMessageCode ckWanChuan(String shipNo) {
		workCommandService.ckWanChuan(shipNo);
		return HdUtils.genMsg();
	}
	
	/**
	 * ???ship_no????????????
	 */
	@RequestMapping(value = "/findByShipNo")
	@ResponseBody
	public WorkCommand getShipBillInfo(String shipNo) {
		return workCommandService.getShipNoInfo(shipNo);
	}
	
	@RequestMapping(value = "/findShipingReport")
	@ResponseBody
	public HdEzuiDatagridData findShipingReport(@RequestBody HdQuery hdQuery) {
		return workCommandService.findShipingReport(hdQuery);
	}
	
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/getBillNoByShipNo")
	@ResponseBody
	public List<EzDropBean> getBillNoByShipNo(String shipNo,String workTyp) {
		
		return workCommandService.getBillNoByShipNo(shipNo,workTyp);
	}

}
