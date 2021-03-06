package net.huadong.tech.cargo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.inter.entity.CPortFt;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.cargo.service.ShipInOutCheckService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.FileUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.IFCSUMTOJAVA;
import net.huadong.tech.util.SqlLiteDbTool;

import net.huadong.tech.cargo.service.impl.DBUtil;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/cargo/ShipInOutCheck")
public class ShipInOutCheckController {

	@Autowired
	private ShipInOutCheckService shipInOutCheckService;

	// ????????????
	@RequestMapping(value = "/shipinoutcheck.htm")
	public String pageTh(Model model, String Type) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/cargo/shipinoutcheck";
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
		return shipInOutCheckService.find(hdQuery);
	}
	@RequestMapping(value = "/findYardIn")
	@ResponseBody
	public HdEzuiDatagridData findYardIn(@RequestBody HdQuery hdQuery) {
		return shipInOutCheckService.findYardIn(hdQuery);
	}
	
	/**
	 * YARD_IN??????
	 */
	

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipInOutCheck> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipInOutCheckService.save(gridData);
	}

	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipInOutCheck shipInOutCheck) {
		return shipInOutCheckService.saveone(shipInOutCheck);
	}
	
	/*
	 * ??????????????????
	 */
	@RequestMapping(value = "/getCPortFt")
	@ResponseBody
	public List<EzDropBean> getCGateDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= "SELECT c FROM CPortFt c  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CPortFt>  ls=JpaUtils.findAll(jpql, params);
			for(CPortFt cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getVcPortId());
				drop.setLabel(cc.getVcPortName());
				list.add(drop);
			}
			return list;
		}
	
	
	/**
	 * ????????????DB??????
	 */
	@RequestMapping(value = "/import")
	@ResponseBody
	public HdMessageCode importDb(String filenam, String type,String shipNo) {
		List<String> filenamList = HdUtils.paraseStrs(filenam);
		for (String fnam : filenamList) {
			SqlLiteDbTool bean = new SqlLiteDbTool();
			bean.selectAll(type, fnam, shipNo);
		}
		return HdUtils.genMsg();
	}
	
	/*
	 * ???????????????????????? 
	 */
	@RequestMapping(value = "/checkShip")
	@ResponseBody
	public HdMessageCode checkShip(String shipNo) {
		return shipInOutCheckService.checkShip(shipNo);
	}

	/**
	 * 
	 * 
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipInOutCheck findone(String checkId) {
		return shipInOutCheckService.findone(checkId);
	}
	
	/*
	 * ??????
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipNo, String workTyp) {
		return shipInOutCheckService.removeAll(shipNo, workTyp);
	}
	
	/*
	 * ????????????
	 */
	@RequestMapping(value = "/removeshipInOutCheck")
	@ResponseBody
	public HdMessageCode removeshipInOutCheck(String checkIds) {
		return shipInOutCheckService.removeshipInOutCheck(checkIds);
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/findShipOut")
	@ResponseBody
	public HdEzuiDatagridData findShipOut(@RequestBody HdQuery hdQuery) {
		return shipInOutCheckService.findShipOut(hdQuery);
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/findShipIn")
	@ResponseBody
	public HdEzuiDatagridData findShipIn(@RequestBody HdQuery hdQuery) {
		return shipInOutCheckService.findShipIn(hdQuery);
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/shipInFT.htm")
	public String shipIn(String workTyp, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("workTyp", workTyp);
		return "system/ship/shipInDataGrid";
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/shipOutFT.htm")
	public String shipOut(String shipNo, String workTyp, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("shipNo", shipNo);
		model.addAttribute("workTyp", workTyp);
		return "system/ship/shipOutDataGrid";
	}
	
	/**
	 * ??????db??????
	 */
	@RequestMapping(value = "/createDB")
	@ResponseBody
	public HdMessageCode createDB(String shipNo, String workTyp) {
		return shipInOutCheckService.createDB(shipNo, workTyp);
	}
	
	/**
	 * ??????DB??????
	 */
	@RequestMapping(value = "/downLoadDB", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadDB(String workTyp,HttpServletResponse response) throws IOException {
		String filename = "";
		if(workTyp.equals("SI")) {
//			filename = "shipOut.db";
			filename = "info.db";
		}
		if(workTyp.equals("SO")) {
//			filename = "shipIn.db";
			filename = "info.db";
		}
		try {
			String resultFileName = filename;
			String url1 = ".//upload//ShipInOutDb//" + filename;
            response.setHeader("Content-Disposition","attachment; filename=" + resultFileName);// ?????????????????????
            response.setContentType("application/octet-stream");// ??????????????????
            //????????????
            IOUtils.copy(new FileInputStream(url1), response.getOutputStream());
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/downLoadDBCheck")
	@ResponseBody
	public HdMessageCode downLoadDBCheck(String workTyp) {
		String filename = "";
		String path = ".//upload//ShipInOutDB//";
		if(workTyp.equals("SI")) {
			filename = "info.db";
			File file = new File(path + filename);
	        if(!file.exists()) {
	        	throw new HdRunTimeException("?????????DB?????????");
	        }
		}
		if(workTyp.equals("SO")) {
			filename = "info.db";
			File file = new File(path + filename);
	        if(!file.exists()) {
	        	throw new HdRunTimeException("?????????DB?????????");
	        }
		}
		return HdUtils.genMsg();
	}
	
}
