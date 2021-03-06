package net.huadong.tech.cargo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.cargo.service.PortCarService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.ListToExcel;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/cargo/PortCar")
public class PortCarController {

	@Autowired
	private PortCarService portCarService;

	// ????????????
	@RequestMapping(value = "/truckwork.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/cargo/truckwork";
	}
	// ????????????
	@RequestMapping(value = "/cargoCarLayout.htm")
	public String pageCarLayout(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/cargo/cargoCarLayout";
	}

	// ????????????
	@RequestMapping(value = "/portcar.htm")
	public String pagePortTh(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/portcar";
	}

	// ????????????
	@RequestMapping(value = "/portcarqueryandedit.htm")
	public String pagedc(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/portcarqueryandedit";
	}

	// ????????????
	@RequestMapping(value = "/portcarquery.htm")
	public String pageqr(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/cargo/portcarquery";
	}
	
	@RequestMapping(value = "/portcarquerypl.htm")
	public String pagePs(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/cargo/portcarquerypl";
	}

	@RequestMapping(value = "/design.htm")
	public String pageDs(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/design";
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/getAuthUserDrop")
	@ResponseBody
	public List<EzDropBean> getAuthUserDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  AuthUser a  where a.orgnId =:orgnId ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("orgnId", "0e9a117162d3468388e09b35fc3b5a7f");
		List<AuthUser> ls = JpaUtils.findAll(jpql, params);
		for (AuthUser cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getUserId());
			drop.setLabel(cc.getName());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getCEmployeeDrop")
	@ResponseBody
	public List<EzDropBean> getCEmployeeDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CEmployee a  where a.empTypCod =:empTypCod ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("empTypCod", "08");
		List<CEmployee> ls = JpaUtils.findAll(jpql, params);
		for (CEmployee cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getEmpNo());
			drop.setLabel(cc.getEmpNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		portCarService.removeAll(ids);
		return HdUtils.genMsg();
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/changeAll")
	@ResponseBody
	public HdMessageCode changeAll(String portCarNos) {
		portCarService.changeAll(portCarNos);
		return HdUtils.genMsg();
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/confrimJq")
	@ResponseBody
	public HdMessageCode confrimJq(String portCarNo,String type) {
		portCarService.confrimJq(portCarNo,type);
		return HdUtils.genMsg();
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/confrimAuto")
	@ResponseBody
	public HdMessageCode confrimAuto(String shipNo) {
		portCarService.confrimAuto(shipNo);
		return HdUtils.genMsg();
	}

	/**
	 * ?????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findHwcrk")
	@ResponseBody
	public HdEzuiDatagridData findHwcrk(@RequestBody HdQuery hdQuery) {
		return portCarService.findHwcrk(hdQuery);
	}

	@RequestMapping(value = "/sendHwcrkDatatoJT")
	@ResponseBody
	public String sendHwcrkDatatoJT(String shipNo, String iEId) {
		return portCarService.sendHwcrkDatatoJT(shipNo, iEId);
	}
	
	/**?????????????????????
	 * ??????????????????????????????????????????statistic_count
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/statisticCount")
	@ResponseBody
	public HdMessageCode statisticCount(String inoutcytim) {
		portCarService.statisticCount(inoutcytim);
		return HdUtils.genMsg();
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
		return portCarService.find(hdQuery);
	}
	
	
	/**
	 * ?????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDchz")
	@ResponseBody
	public String findDchz(String cyAreaNo) {
		return portCarService.findDchz(cyAreaNo);
	}
	
	/**
	 * ????????????????????????????????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDchzcl")
	@ResponseBody
	public HashMap<String, String> findDchzcl(String cyAreaNo) {
		return portCarService.findDchzcl(cyAreaNo);
	}
	
	
	/**
	 * ??????????????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public HdEzuiDatagridData findAll(@RequestBody HdQuery hdQuery) {
		return portCarService.findAll(hdQuery);
	}

	/**
	 * ????????????????????????????????????????????????
	 *
	 * @param hdQuery
	 * @return HdEzuiDatagridData
	 */
	@RequestMapping(value = "/findNull")
	@ResponseBody
	public HdEzuiDatagridData findNull(@RequestBody HdQuery hdQuery) {
		return portCarService.findNull(hdQuery);
	}

	/**
	 * ????????????????????????????????????????????????
	 *
	 * @param vimNo
	 * @return HdEzuiDatagridData
	 */
	@RequestMapping(value = "/updateInfo")
	@ResponseBody
	public HdMessageCode updateInfo(String vimNo,String isAll) {
		return portCarService.updateInfo(vimNo,isAll);
	}
	
	@RequestMapping(value = "/findVinExcel")
	@ResponseBody
	public HdEzuiDatagridData findVinExcel(@RequestBody HdQuery hdQuery) {
		return portCarService.findVinExcel(hdQuery);
	}
	
	  @ResponseBody
	  @RequestMapping({"upload"})
	  public HdMessageCode portCarVinUpload(@RequestParam MultipartFile file, HttpServletRequest request,	HttpServletResponse response) {
	    Workbook wb = null;
	    String oriName = file.getOriginalFilename();
		String ext = oriName.substring(oriName.lastIndexOf("."));
		try {
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(file.getInputStream());
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(file.getInputStream());
			}
		} catch (FileNotFoundException e) {
			throw new HdRunTimeException("??????????????????");//
		} catch (IOException e) {
			throw new HdRunTimeException("???????????????");//
		}
	    Sheet sheet = wb.getSheetAt(0);
	    
	    List<CellRangeAddress> cellRangeAddressLst= sheet.getMergedRegions();
	    Map hMap=null;
	    List  mergesLst=new ArrayList();
	    for (CellRangeAddress cellRangeAddress : cellRangeAddressLst) {
	    	Cell firstCell=sheet.getRow(cellRangeAddress.getFirstRow()).getCell(cellRangeAddress.getFirstColumn());
	    	firstCell.setCellType(CellType.STRING);
	    	int rowNum=cellRangeAddress.getLastRow()-cellRangeAddress.getFirstRow();
	    	for (int i=1;i<=rowNum;i++) {
	    		Cell curCell=sheet.getRow(cellRangeAddress.getFirstRow()+i).getCell(cellRangeAddress.getFirstColumn());
//	    		curCell.setCellType(CellType.STRING);
	    		curCell.setCellValue(firstCell.getStringCellValue());
			}
	    	hMap=new HashMap();
	    	hMap.put("index",cellRangeAddress.getFirstRow()-1);
	    	hMap.put("rowspan",cellRangeAddress.getLastRow()- cellRangeAddress.getFirstRow()+1);
	    	mergesLst.add(hMap);
		} 
	    Row thRow=sheet.getRow(sheet.getFirstRowNum());
	    List thLst=new ArrayList();
	    List thtleLst=new ArrayList();
	    for (Cell cell : thRow) {
	    	cell.setCellType(CellType.STRING);
	    	thLst.add(cell.getStringCellValue().replaceAll("[./]", ""));
	    	thtleLst.add(cell.getStringCellValue());
		}
	    List rowList=new ArrayList();
	    for (int i = 1; i <= sheet.getLastRowNum(); i++)
	    {
	    	  Row row = sheet.getRow(i);//???????????????i????????????0??????
	    	  if(row!=null) {
		          Map mapItem=new HashMap();
				  for (Cell cell : row) {
						cell.setCellType(CellType.STRING);
						mapItem.put(thLst.get(cell.getColumnIndex()), cell.getStringCellValue());
				  }
				  rowList.add(mapItem);
	    	  }
	    }
	    
	    Map rtMap=new HashMap();
	    HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
	    ezuiDatagridData.setRows(rowList);

	    rtMap.put("column",thLst);
	    rtMap.put("columntielts",thtleLst);
	    rtMap.put("row",ezuiDatagridData);
	    rtMap.put("mergesLst",mergesLst);
	    
		HdMessageCode message=HdUtils.genMsg();
		message.setData(rtMap);
		return message;
	}

	// ???????????????
	@RequestMapping(value = "/findZC")
	@ResponseBody
	public HdEzuiDatagridData findZC(@RequestBody HdQuery hdQuery) {
		return portCarService.findZC(hdQuery);
	}
	
	// ???????????????   ??????500??????????????????
	@RequestMapping(value = "/findZCGroup")
	@ResponseBody
	public Map<String,Object> findZCGroup() {
		return portCarService.findZCGroup();
	}
	
	//??????????????????????????????????????????Group by???
	@RequestMapping(value = "/findZCPL")
	@ResponseBody
	public HdEzuiDatagridData findZCPL(@RequestBody HdQuery hdQuery) {
		return portCarService.findZCPL(hdQuery);
	}

	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findHwcrk(params);
		List<PortCar> portcarList = data.getRows();
		try {
			String dte = new java.text.SimpleDateFormat("yyyy-MM-dd").format(portcarList.get(0).getInCyTim());
			ListToExcel.writeToXls(ListToExcel.createlist(portcarList), response, dte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public PortCar findone(String portCarNo) {
		if (HdUtils.strIsNull(portCarNo)) {
			PortCar portCar = new PortCar();
			return portCar;
		}
		return portCarService.findone(portCarNo);
	}

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<PortCar> gridData, String ingateId, String gateNo) {
		// CommonUtil.initEntity(gridData);
		return portCarService.save(gridData, ingateId, gateNo);
	}

	/*
	 * form??????
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody PortCar portCar) {

		return portCarService.saveone(portCar);
	}

	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findZclh")
	@ResponseBody
	public HdEzuiDatagridData findZclh(@RequestBody HdQuery hdQuery) {
		return portCarService.findZclh(hdQuery);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getCBrandDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CBrand a ";
		List<CBrand> ls = JpaUtils.findAll(jpql, null);
		for (CBrand cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getBrandCod());
			drop.setLabel(cc.getBrandNam());
			list.add(drop);
		}
		return list;
	}

	// ??????????????????????????????
	@RequestMapping(value = "/getDetail")
	@ResponseBody
	public HashMap<String, String> getDetail(String date,String dockCod,String tradeId) {
		return portCarService.getDetail(date,dockCod,tradeId);
	}

	// ??????????????????????????????????????????
	@RequestMapping(value = "/getJsg")
	@ResponseBody
	public HashMap<String, String> getJsg(String date) {
		return portCarService.getJsg(date);
	}

	// ????????????????????????
	@RequestMapping(value = "/getDuiChangDetail")
	@ResponseBody
	public HashMap<String, String> getDuiChangDetail(String date) {
		return portCarService.getDuiChangDetail(date);
	}
	

	// ?????????????????????????????????????????????????????????
	@RequestMapping(value = "/getFtJsg")
	@ResponseBody
	public HashMap<String, Long> getFtJsg(String date) {
		return portCarService.getFtJsg(date);
	}
	
	// ???????????????????????????????????????
	@RequestMapping(value = "/getFtDcqk")
	@ResponseBody
	public HashMap<String, Long> getFtDcqk(String date) {
		return portCarService.getFtDcqk(date);
	}
	
	/*
	 * ????????????  
	 */
	@RequestMapping(value = "/statisticCountSave")
	@ResponseBody
	public HdMessageCode statisticCountSave(String shipNo, String iEId) {
		return portCarService.statisticCountSave(shipNo, iEId);
	}
	/**
	 * ???????????????  
	 */
	@RequestMapping(value = "/statisticCountJs")
	@ResponseBody
	public HdMessageCode statisticCountJs(String inoutcytim, String portTyp) {
		return portCarService.statisticCountJs(inoutcytim, portTyp);
	}
	
	
	
	

}
