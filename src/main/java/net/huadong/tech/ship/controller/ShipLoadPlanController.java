package net.huadong.tech.ship.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.ship.service.ShipLoadPlanService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.springboot.core.utils.HdEzuiExportFile;
import net.huadong.tech.util.FileUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.utils.ImportExportUtil;
import net.huadong.tech.work.entity.WorkQueue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/ShipLoadPlan")
public class ShipLoadPlanController {

	@Autowired
	private ShipLoadPlanService shipLoadPlanService;
	
    @Autowired
	private FileUploadService fileUploadService;
	
	@Value("${file.ShipLoadPlan-upload-path}")
	private String uploadPath;

	// ????????????
	@RequestMapping(value = "/shiploadplan.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shiploadplan";
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
		return shipLoadPlanService.find(hdQuery);
	}

	/**
	 * ??????????????????
	 * 
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findShip")
	@ResponseBody
	public HdEzuiDatagridData findShip(@RequestBody HdQuery hdQuery) {
		return shipLoadPlanService.findShip(hdQuery);
	}

	/**
	 * ?????????????????????
	 * 
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findShipBill")
	@ResponseBody
	public HdEzuiDatagridData findShipBill(@RequestBody HdQuery hdQuery) {
		return shipLoadPlanService.findShipBill(hdQuery);
	}

	/**
	 * ??????????????????
	 * 
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findPortCar")
	@ResponseBody
	public HdEzuiDatagridData findPortCar(@RequestBody HdQuery hdQuery) {
		return shipLoadPlanService.findPortCar(hdQuery);
	}

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipLoadPlan> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipLoadPlanService.save(gridData);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String planNos) {
		shipLoadPlanService.removeAll(planNos);
		return HdUtils.genMsg();
	}

	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipLoadPlan findone(String planNo) {
		if (HdUtils.strIsNull(planNo)) {
			ShipLoadPlan ShipLoadPlan = new ShipLoadPlan();
			return ShipLoadPlan;
		}
		return shipLoadPlanService.findone(planNo);
	}

	/*
	 * form??????
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipLoadPlan shipLoadPlan) {
		return shipLoadPlanService.saveone(shipLoadPlan);
	}

	/**
	 * ???????????? ??????????????????
	 */
	@RequestMapping(value = "/findShipLoadPlan")
	@ResponseBody
	public HdMessageCode findShipLoadPlan(String planNo) {
		return shipLoadPlanService.findShipLoadPlan(planNo);
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
		HdEzuiDatagridData data = this.find(params);
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}

	@RequestMapping(value = "/impShipLoadPlanEUKOU", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode impShipLoadPlanEUKOU(@RequestParam("file") MultipartFile file,@RequestParam("shipNo456") String shipNo456) {
		    String uuid = HdUtils.genUuid();
		    try {
				FileUtil.upload(this.uploadPath, uuid, file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    String oriName = file.getOriginalFilename();
		    ComFileUpload upload = new ComFileUpload();
		    upload.setFileSize(file.getSize() + "");
		    upload.setFileName(oriName);
		    upload.setUploadId(uuid);
		    upload.setFilePath(this.uploadPath);
		    upload.setFileUuid(uuid);
		    this.fileUploadService.save(upload);
		
		    
		    Workbook wb = null;
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
		    List<ShipLoadPlan> shipLoadPlanList=new ArrayList();
		    for (int i = 2; i <=sheet.getLastRowNum()-1; i++)
		    {
		    	//EUKOU????????????????????????G.TTL?????????????????????TTL????????????????????????????????????????????????????????????????????????
              
		        Row row = sheet.getRow(i);//???????????????i????????????0??????
		        
                if(row.getCell(2)!=null){
                if(row.getCell(2).getStringCellValue().equals("TTL")){
                continue;
                }
                
                //?????????
		        String POL = "";
		      
		        //?????????
		        String POD = "";
		        
		        //?????????
		        String BookingNo = "";
		        
		        //?????????
		        String Shipper = "";
		        //????????????
		        String commodity = "";
		        
		        
		        String seqNo = "";//????????????????????????ITEM
		        //??????
		        double Qty = 0;
		        
		        double Length = 0  ;
		        double Width = 0  ;
		        double Height= 0 ;
		        double Weight = 0;
		        String tele="";
		        if(row.getCell(8) != null){//?????????i???????????????8??????????????????
		        	seqNo = row.getCell(8).getStringCellValue();
		        }
		        if(row.getCell(2) != null){
		        	POD = row.getCell(2).getStringCellValue();
		        }
		        if(row.getCell(7) != null){
		        	//????????????????????????java??????????????????????????????????????? ????????????????????????????????????????????????
		        	BookingNo = row.getCell(7).getStringCellValue().split(" +")[1];
		        }
		        if(row.getCell(9) != null){
		        	commodity = row.getCell(9).getStringCellValue();
		        }
		        if(row.getCell(11) != null){
		        	Qty = row.getCell(11).getNumericCellValue();
		        }
		      
		        if(row.getCell(16) != null){
		        	Length = row.getCell(16).getNumericCellValue();
		        }
		        if(row.getCell(17) != null){
		        	Width =  row.getCell(17).getNumericCellValue();
		        }
		        if(row.getCell(18) != null){
		        	Height = row.getCell(18).getNumericCellValue();
		        }
		        if(row.getCell(14) != null){
		        	Weight = row.getCell(14).getNumericCellValue();
		        }
		      
		        ShipLoadPlan shiploadplan = new ShipLoadPlan();
		        shiploadplan.setPlanNo(HdUtils.genUuid());
		        shiploadplan.setWorkSeqNo(new BigDecimal(seqNo));
		        String jpql="select a from ShipBill a where a.billNo=:billNo and a.iEId='E' and a.tradeId='2' and a.shipNo=:shipNo ";
		        QueryParamLs paramLs = new QueryParamLs();
		        paramLs.addParam("billNo",BookingNo);
		        paramLs.addParam("shipNo",shipNo456);
		        List<ShipBill> shipBillList=JpaUtils.findAll(jpql, paramLs);
		        if(shipBillList.size()>0){
		            Ship ship=JpaUtils.findById(Ship.class,shipBillList.get(0).getShipNo());
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setShipNo(shipBillList.get(0).getShipNo());
		            shiploadplan.setShipNam(ship.getcShipNam());
		            shiploadplan.setVoyage(ship.getEvoyage());
		            shiploadplan.setiEId("E");
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setBillNo(shipBillList.get(0).getBillNo());
		            String jpqlz="select a from CCarTyp a where a.carTypNam=:carTypNam ";
		            QueryParamLs paramLsz = new QueryParamLs();
		            paramLsz.addParam("carTypNam",commodity);
		            List<CCarTyp> cctList=JpaUtils.findAll(jpqlz, paramLsz);
		            if(cctList.size()>0){
		            	shiploadplan.setCarTyp(cctList.get(0).getCarTyp()); 
		            	shiploadplan.setBrandCod(cctList.get(0).getBrandCod());
		            	shiploadplan.setBrandCod(cctList.get(0).getCarKind());
		            	String jpqls="select a from PortCar a where a.billNo=:billNo and a.carTyp=:carTyp ";
		            	QueryParamLs paramLss = new QueryParamLs();
		 		        paramLss.addParam("billNo",BookingNo);
		 		        paramLss.addParam("carTyp",cctList.get(0).getCarTyp());
		 		        List<PortCar> pcList=JpaUtils.findAll(jpqls, paramLss);
		 		        if(pcList.size()>0){
		 		        if(HdUtils.strNotNull(pcList.get(0).getConsignCod())){
		 		        shiploadplan.setConsignCod(pcList.get(0).getConsignCod());
		 		        CClientCod ccc=JpaUtils.findById(CClientCod.class, shiploadplan.getConsignCod());
		 		        shiploadplan.setConsignNam(ccc.getcClientShort());
		 		        }
		 		        //?????????????????????????????????????????????
		 		        shiploadplan.setPlac(pcList.get(0).getCyAreaNo());
		 		        }
		            }else{
		            	//System.out.println("??????????????????");
		            	throw new HdRunTimeException("??????????????????");//	
		            }
		            shiploadplan.setLoadPortCod("CNTSN");
		            shiploadplan.setTranPortCod(POD);
		            if(HdUtils.strNotNull(shipBillList.get(0).getDiscPortCod())){
		           	 shiploadplan.setDiscPortCod(shipBillList.get(0).getDiscPortCod());	
		           }else{
		           	shiploadplan.setDiscPortCod("*");
		           }
		            shiploadplan.setWeights(new BigDecimal(String.valueOf(Weight)));
		            shiploadplan.setHeight(new BigDecimal(String.valueOf(Height)));
		            shiploadplan.setWidth(new BigDecimal(String.valueOf(Width)));
		            shiploadplan.setLength(new BigDecimal(String.valueOf(Length)));
		            shiploadplan.setCarNum(new BigDecimal(String.valueOf(Qty)));
		            //shiploadplan.setPlac(cyAreaNo);
		            shiploadplan.setDockCod(ship.getDockCod());
		            shiploadplan.setRecTim(HdUtils.getDateTime());
		            shiploadplan.setRecNam(HdUtils.getCurUser().getAccount());
		            shiploadplan.setTele(tele);
		            shipLoadPlanList.add(shiploadplan);
		        }else{
		        	throw new HdRunTimeException("????????????"+BookingNo+"?????????????????????");
		        }
                
                }
		    }
		    for(int j=0;j<shipLoadPlanList.size();j++){
		    	if(shipLoadPlanList.get(j)!=null){
		    	JpaUtils.save(shipLoadPlanList.get(j));
		    	}
		    }
		    HdMessageCode hs=HdUtils.genMsg();
		    return hs;
	}
	
	
	
	@RequestMapping(value = "/impShipLoadPlanWL", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode impShipLoadPlanWL(@RequestParam("file") MultipartFile file,@RequestParam("shipNo123") String shipNo123) {
		    String uuid = HdUtils.genUuid();
		    try {
				FileUtil.upload(this.uploadPath, uuid, file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    String oriName = file.getOriginalFilename();
		    ComFileUpload upload = new ComFileUpload();
		    upload.setFileSize(file.getSize() + "");
		    upload.setFileName(oriName);
		    upload.setUploadId(uuid);
		    upload.setFilePath(this.uploadPath);
		    upload.setFileUuid(uuid);
		    this.fileUploadService.save(upload);
		
		    
		    Workbook wb = null;
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
		    HdMessageCode result = new HdMessageCode();
		    List<ShipLoadPlan> shipLoadPlanList=new ArrayList();
		    for (int i = 3; i <=sheet.getLastRowNum(); i++)
		    {
		        Row row = sheet.getRow(i);//???????????????i????????????0??????
		        //?????????
		        String POL = "";
		      
		        //?????????
		        String POD = "";
		        
		        //?????????
		        String BookingNo = "";
		        
		        //?????????
		        String Shipper = "";
		        //????????????
		        String commodity = "";
		        
		        
		        double seqNo = 0;//????????????????????????ITEM
		        //??????
		        double Qty = 0;
		        
		        String cyAreaNo = "";//??????????????????????????????????????????
		        String Unit = null;
		        double Length = 0  ;
		        double Width = 0  ;
		        double Height= 0 ;
		        double Weight = 0;
		        String tele="";
		        if(row.getCell(8) != null){//?????????i???????????????8??????????????????
		        	seqNo = row.getCell(8).getNumericCellValue();
		        }
		        if(row.getCell(1) != null){
		        	POD = row.getCell(1).getStringCellValue();
		        }
		        if(row.getCell(3) != null){
		        	BookingNo = row.getCell(3).getStringCellValue();
		        }
		        if(row.getCell(7) != null){
		        	commodity = row.getCell(7).getStringCellValue();
		        }
		        if(row.getCell(9) != null){
		        	Qty = row.getCell(9).getNumericCellValue();
		        }
		      
		        if(row.getCell(10) != null){
		        	Length = row.getCell(10).getNumericCellValue();
		        }
		        if(row.getCell(11) != null){
		        	Width =  row.getCell(11).getNumericCellValue();
		        }
		        if(row.getCell(12) != null){
		        	Height = row.getCell(12).getNumericCellValue();
		        }
		        if(row.getCell(13) != null){
		        	Weight = row.getCell(13).getNumericCellValue();
		        }
		      
		        ShipLoadPlan shiploadplan = new ShipLoadPlan();
		        shiploadplan.setPlanNo(HdUtils.genUuid());
		        shiploadplan.setWorkSeqNo(new BigDecimal(String.valueOf(seqNo)));
		        String jpql="select a from ShipBill a where a.billNo=:billNo and a.iEId='E' and a.tradeId='2' and a.shipNo=:shipNo ";
		        QueryParamLs paramLs = new QueryParamLs();
		        paramLs.addParam("billNo",BookingNo);
		        paramLs.addParam("shipNo",shipNo123);
		        List<ShipBill> shipBillList=JpaUtils.findAll(jpql, paramLs);
		        if(shipBillList.size()>0){
		            Ship ship=JpaUtils.findById(Ship.class,shipBillList.get(0).getShipNo());
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setShipNo(shipBillList.get(0).getShipNo());
		            shiploadplan.setShipNam(ship.getcShipNam());
		            shiploadplan.setVoyage(ship.getEvoyage());
		            shiploadplan.setiEId("E");
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setBillNo(shipBillList.get(0).getBillNo());
		            String jpqlz="select a from CCarTyp a where a.carTypNam=:carTypNam ";
		            QueryParamLs paramLsz = new QueryParamLs();
		            paramLsz.addParam("carTypNam",commodity);
		            List<CCarTyp> cctList=JpaUtils.findAll(jpqlz, paramLsz);
		            if(cctList.size()>0){
		            	shiploadplan.setCarTyp(cctList.get(0).getCarTyp()); 
		            	shiploadplan.setBrandCod(cctList.get(0).getBrandCod());
		            	shiploadplan.setBrandCod(cctList.get(0).getCarKind());
		            	String jpqls="select a from PortCar a where a.billNo=:billNo and a.carTyp=:carTyp ";
		            	QueryParamLs paramLss = new QueryParamLs();
		 		        paramLss.addParam("billNo",BookingNo);
		 		        paramLss.addParam("carTyp",cctList.get(0).getCarTyp());
		 		        List<PortCar> pcList=JpaUtils.findAll(jpqls, paramLss);
		 		        if(pcList.size()>0){
		 		        if(HdUtils.strNotNull(pcList.get(0).getConsignCod())){
		 		        shiploadplan.setConsignCod(pcList.get(0).getConsignCod());
		 		        CClientCod ccc=JpaUtils.findById(CClientCod.class, shiploadplan.getConsignCod());
		 		        shiploadplan.setConsignNam(ccc.getcClientShort());
		 		        }
		 		        //?????????????????????????????????????????????
		 		        shiploadplan.setPlac(pcList.get(0).getCyAreaNo());
		 		        }
		            }else{
		            	//System.out.println("??????????????????");
		            	throw new HdRunTimeException("??????????????????");//	
		            }
		            if(HdUtils.strNotNull(shipBillList.get(0).getLoadPortCod())){
		            	 shiploadplan.setLoadPortCod(shipBillList.get(0).getLoadPortCod());	
		            }else{
		            	shiploadplan.setLoadPortCod("CNTSN");
		            }
		            shiploadplan.setTranPortCod(POD);
		            if(HdUtils.strNotNull(shipBillList.get(0).getDiscPortCod())){
		           	 shiploadplan.setDiscPortCod(shipBillList.get(0).getDiscPortCod());	
		           }else{
		           	shiploadplan.setDiscPortCod("*");
		           }
		            shiploadplan.setWeights(new BigDecimal(String.valueOf(Weight)));
		            shiploadplan.setHeight(new BigDecimal(String.valueOf(Height)));
		            shiploadplan.setWidth(new BigDecimal(String.valueOf(Width)));
		            shiploadplan.setLength(new BigDecimal(String.valueOf(Length)));
		            shiploadplan.setCarNum(new BigDecimal(String.valueOf(Qty)));
		            //shiploadplan.setPlac(cyAreaNo);
		            shiploadplan.setDockCod(ship.getDockCod());
		            shiploadplan.setRecTim(HdUtils.getDateTime());
		            shiploadplan.setRecNam(HdUtils.getCurUser().getAccount());
		            shiploadplan.setTele(tele);
		            shipLoadPlanList.add(shiploadplan);
		        }else{
		        	throw new HdRunTimeException("????????????"+BookingNo+"?????????????????????");
		        }
		    }
		    for(int j=0;j<shipLoadPlanList.size();j++){
		    	if(shipLoadPlanList.get(j)!=null){
		    	JpaUtils.save(shipLoadPlanList.get(j));
		    	}
		    }
		    HdMessageCode hs=HdUtils.genMsg();
		    return hs;
	}
	
	
	@RequestMapping(value = "/impShipLoadPlan", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode impShipLoadPlan(@RequestParam("file") MultipartFile file,@RequestParam("shipNo") String shipNo) {
		    String uuid = HdUtils.genUuid();
		    try {
				FileUtil.upload(this.uploadPath, uuid, file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    String oriName = file.getOriginalFilename();
		    ComFileUpload upload = new ComFileUpload();
		    upload.setFileSize(file.getSize() + "");
		    upload.setFileName(oriName);
		    upload.setUploadId(uuid);
		    upload.setFilePath(this.uploadPath);
		    upload.setFileUuid(uuid);
		    this.fileUploadService.save(upload);
		
		    
		    Workbook wb = null;
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
		    HdMessageCode result = new HdMessageCode();
		    for (int i = 1; i <=sheet.getLastRowNum(); i++)
		    {
		        Row row = sheet.getRow(i);//???????????????i????????????0??????
		        String seqNo = "";//????????????????????????ITEM
		        String POD = "";
		        String BookingNo = "";
		        String commodity = "";
		        String cyAreaNo = "";//??????????????????????????????????????????
		        String Unit = null;
		        String Length = null ;
		        String Width = null ;
		        String Height = null;
		        String Weight = null;
		        String tele="";
		        if(row.getCell(0) != null){//?????????i???????????????0??????????????????
		        	seqNo = row.getCell(0).getStringCellValue();
		        }
		        if(row.getCell(1) != null){
		        	POD = row.getCell(1).getStringCellValue();
		        }
		        if(row.getCell(2) != null){
		        	BookingNo = row.getCell(2).getStringCellValue();
		        }
		        if(row.getCell(3) != null){
		        	commodity = row.getCell(3).getStringCellValue();
		        }
		        if(row.getCell(4) != null){
		        	cyAreaNo = row.getCell(4).getStringCellValue();
		        }
		        if(row.getCell(5) != null){
		        	Unit = row.getCell(5).getStringCellValue();
		        }
		        if(row.getCell(6) != null){
		        	Length = row.getCell(6).getStringCellValue();
		        }
		        if(row.getCell(7) != null){
		        	Width =  row.getCell(7).getStringCellValue();
		        }
		        if(row.getCell(8) != null){
		        	Height = row.getCell(8).getStringCellValue();
		        }
		        if(row.getCell(9) != null){
		        	Weight = row.getCell(9).getStringCellValue();
		        }
		        if(row.getCell(10) != null){
		        	tele = row.getCell(10).getStringCellValue();
		        }
		        ShipLoadPlan shiploadplan = new ShipLoadPlan();
		        shiploadplan.setPlanNo(HdUtils.genUuid());
		        shiploadplan.setWorkSeqNo(new BigDecimal(seqNo));
		        String jpql="select a from ShipBill a where a.billNo=:billNo and a.iEId='E' and a.shipNo=:shipNo ";
		        QueryParamLs paramLs = new QueryParamLs();
		        paramLs.addParam("billNo",BookingNo);
		        paramLs.addParam("shipNo",shipNo);
		        List<ShipBill> shipBillList=JpaUtils.findAll(jpql, paramLs);
		        if(shipBillList.size()>0){
		            Ship ship=JpaUtils.findById(Ship.class,shipBillList.get(0).getShipNo());
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setShipNo(shipBillList.get(0).getShipNo());
		            shiploadplan.setShipNam(ship.getcShipNam());
		            shiploadplan.setVoyage(ship.getEvoyage());
		            shiploadplan.setiEId("E");
		            shiploadplan.setTradeId(ship.getTradeId());
		            shiploadplan.setBillNo(shipBillList.get(0).getBillNo());
		            String jpqlz="select a from CCarTyp a where a.carTypNam=:carTypNam ";
		            QueryParamLs paramLsz = new QueryParamLs();
		            paramLsz.addParam("carTypNam",commodity);
		            List<CCarTyp> cctList=JpaUtils.findAll(jpqlz, paramLsz);
		            if(cctList.size()>0){
		            	shiploadplan.setCarTyp(cctList.get(0).getCarTyp()); 
		            	shiploadplan.setBrandCod(cctList.get(0).getBrandCod());
		            	shiploadplan.setBrandCod(cctList.get(0).getCarKind());
		            	String jpqls="select a from PortCar a where a.billNo=:billNo and a.carTyp=:carTyp ";
		            	QueryParamLs paramLss = new QueryParamLs();
		 		        paramLss.addParam("billNo",BookingNo);
		 		        paramLss.addParam("carTyp",cctList.get(0).getCarTyp());
		 		        List<PortCar> pcList=JpaUtils.findAll(jpqls, paramLss);
		 		        if(pcList.size()>0){
		 		        if(HdUtils.strNotNull(pcList.get(0).getConsignCod())){
		 		        shiploadplan.setConsignCod(pcList.get(0).getConsignCod());
		 		        CClientCod ccc=JpaUtils.findById(CClientCod.class, shiploadplan.getConsignCod());
		 		        shiploadplan.setConsignNam(ccc.getcClientShort());
		 		        }
		 		        //?????????????????????????????????????????????
		 		        shiploadplan.setPlac(pcList.get(0).getCyAreaNo());
		 		        }
		            }else{
		            	//System.out.println("??????????????????");
		            	throw new HdRunTimeException("??????????????????");//	
		            }
		            if(HdUtils.strNotNull(shipBillList.get(0).getLoadPortCod())){
		            	 shiploadplan.setLoadPortCod(shipBillList.get(0).getLoadPortCod());	
		            }else{
		            	shiploadplan.setLoadPortCod("CNTXG");
		            }
		            shiploadplan.setTranPortCod(POD);
		            if(HdUtils.strNotNull(shipBillList.get(0).getDiscPortCod())){
		           	 shiploadplan.setDiscPortCod(shipBillList.get(0).getDiscPortCod());	
		           }else{
		           	shiploadplan.setDiscPortCod("*");
		           }
		            shiploadplan.setWeights(new BigDecimal(Weight));
		            shiploadplan.setHeight(new BigDecimal(Height));
		            shiploadplan.setWidth(new BigDecimal(Width));
		            shiploadplan.setLength(new BigDecimal(Length));
		            shiploadplan.setCarNum(new BigDecimal(Unit));
		            //shiploadplan.setPlac(cyAreaNo);
		            shiploadplan.setDockCod(ship.getDockCod());
		            shiploadplan.setRecTim(HdUtils.getDateTime());
		            shiploadplan.setRecNam(HdUtils.getCurUser().getAccount());
		            shiploadplan.setTele(tele);
		            JpaUtils.save(shiploadplan);
		        }else{
		        	throw new HdRunTimeException("????????????"+BookingNo+"?????????????????????");
		        }
		    }
			return result;
		
	}
	
	
	/*
	 * ??????????????????
	 */
	@RequestMapping("/genWorkQueue")
	@ResponseBody
	public HdMessageCode genWorkQueue(String planNos) {
		shipLoadPlanService.genWorkQueue(planNos);
		return HdUtils.genMsg();
	}
	//?????????????????????
	@RequestMapping(value = "/gentreeslp", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentreeslp() {
		return shipLoadPlanService.findTreeslp();
	}
}
