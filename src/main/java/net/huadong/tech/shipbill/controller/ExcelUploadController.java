package net.huadong.tech.shipbill.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.util.FileUtil;
import net.huadong.tech.util.HdUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFDataFormat;  
import org.apache.poi.hssf.usermodel.HSSFFont;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"webresources/login/shipbill/FileUpload"})
public class ExcelUploadController
{
  private static Logger LOG = Logger.getLogger(ExcelUploadController.class);
  public static final String JG = "集港";// 作业方式
  public static final String SG = "疏港";// 作业方式
  public static final String GZ = "滚装";// 码头
  public static final String HQ = "环球";// 码头
  @Autowired
  private FileUploadService fileUploadService;

  @Value("${file.ContractIeDoc-upload-path}")
  private String uploadPath;

  @RequestMapping({""})
  public String page(String nouse) { return "com/fileupload"; } 
  @ResponseBody
  @RequestMapping({"/find"})
  public List<ComFileUpload> find(String entityName, String entityId) {
    return this.fileUploadService.find(entityName, entityId);
  }
  @ResponseBody
  @RequestMapping({"upload"})
  public HdMessageCode upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String uuid = HdUtils.genUuid();
    FileUtil.upload(this.uploadPath, uuid, file);
    String oriName = file.getOriginalFilename();
    ComFileUpload upload = new ComFileUpload();
    upload.setFileSize(file.getSize() + "");
    upload.setFileName(oriName);
    upload.setUploadId(uuid);
    upload.setFilePath(this.uploadPath);
    upload.setFileUuid(uuid);
    this.fileUploadService.save(upload);
    
    //excel导入
   
    Workbook wb = null;
	String ext = oriName.substring(oriName.lastIndexOf("."));
	try {
		if(".xls".equals(ext)){
			wb = new HSSFWorkbook(file.getInputStream());
		}else if(".xlsx".equals(ext)){
			wb = new XSSFWorkbook(file.getInputStream());
		}
	} catch (FileNotFoundException e) {
		throw new HdRunTimeException("文件不存在！");//
	} catch (IOException e) {
		throw new HdRunTimeException("输出异常！");//
	}
    Sheet sheet = wb.getSheetAt(0);
    for (int i = 1; i <= sheet.getLastRowNum(); i++)
    {
        Row row = sheet.getRow(i);//获取索引为i的行，以0开始
        String dockNam = "";
        String contractTyp= "";
        String cShipNam= "";
        String voyage= "";
        String billNo = "";
        String brandNam = "";
        String carKindNam = "";
        BigDecimal carNum = null;
        String contractDte = "";
        String machcondition = "";
        String personcondition = "";
        String consignNam = "";
        if(row.getCell(0) != null){//获取第i行的索引为0的单元格数据
        	dockNam = row.getCell(0).getStringCellValue();
        }
        if(row.getCell(1) != null){
        	contractTyp = row.getCell(1).getStringCellValue();
        }
        if(row.getCell(2) != null){
        	cShipNam = row.getCell(2).getStringCellValue();
        }
        if(row.getCell(3) != null){
        	voyage = row.getCell(3).getStringCellValue();
        }
        if(row.getCell(4) != null){
        	billNo = row.getCell(4).getStringCellValue();
        }
        if(row.getCell(5) != null){
        	brandNam = row.getCell(5).getStringCellValue();
        }
        if(row.getCell(6) != null){
        	carKindNam = row.getCell(6).getStringCellValue();
        }
        if(row.getCell(7) != null){
        	carNum = new BigDecimal(Double.toString(row.getCell(7).getNumericCellValue()));
        }
        if(row.getCell(8) != null){
        	contractDte = row.getCell(8).getStringCellValue();
        }
        if(row.getCell(9) != null){
        	machcondition = row.getCell(9).getStringCellValue();
        }
        if(row.getCell(10) != null){
        	personcondition = row.getCell(10).getStringCellValue();
        }
        if(row.getCell(11) != null){
        	consignNam = row.getCell(11).getStringCellValue();
        }
        ContractIeDoc contractIeDoc = new ContractIeDoc();
        String jpqlm="select max(cast(a.contractNo as int)) as maxContractNo from ContractIeDoc a";
        List<BigDecimal> contractList=JpaUtils.findAll(jpqlm, null);
        if(contractList.size()>0){
        	Integer maxContractNo=contractList.get(0).intValue()+1;
        	String maxN = addZeroForNum(maxContractNo.toString(), 8);
        	contractIeDoc.setContractNo(maxN);
        }else{
        	contractIeDoc.setContractNo("00000001");	
        }
        contractIeDoc.setShipNam(cShipNam);
        if(JG.equals(contractTyp)){
        contractIeDoc.setiEId("E");
        String jpqln="select a from Ship a where a.cShipNam=:cShipNam  and a.evoyage=:voyage ";
        QueryParamLs paramLsn = new QueryParamLs();
        paramLsn.addParam("cShipNam",cShipNam);
        paramLsn.addParam("voyage",voyage);
        contractIeDoc.setContractTyp("1");
        contractIeDoc.setWorkWay("1");
        List<Ship> shipList=JpaUtils.findAll(jpqln, paramLsn);
        if(shipList.size()>0){
        	contractIeDoc.setTradeId(shipList.get(0).getTradeId());
        	contractIeDoc.setShipNo(shipList.get(0).getShipNo());
        	contractIeDoc.setVoyage(shipList.get(0).getEvoyage());
         } 
        }
        if(SG.equals(contractTyp)){
        	   contractIeDoc.setiEId("I");
               String jpqln="select a from Ship a where a.cShipNam=:cShipNam and a.ivoyage=:voyage ";
               QueryParamLs paramLsn = new QueryParamLs();
               paramLsn.addParam("cShipNam",cShipNam);
               paramLsn.addParam("voyage",voyage);
               contractIeDoc.setContractTyp("2");
               contractIeDoc.setWorkWay("2");
               List<Ship> shipList=JpaUtils.findAll(jpqln, paramLsn);
               if(shipList.size()>0){
               	contractIeDoc.setTradeId(shipList.get(0).getTradeId());
               	contractIeDoc.setShipNo(shipList.get(0).getShipNo());
               	contractIeDoc.setVoyage(shipList.get(0).getIvoyage());
                } 	
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
        if(HdUtils.strNotNull(contractDte)){
        	contractIeDoc.setContractDte(sdf.parse(contractDte));
        	Calendar   calendar = new GregorianCalendar(); 
        	calendar.setTime(sdf.parse(contractDte)); 
        	calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动 
        	contractIeDoc.setValidatDte(calendar.getTime()); //这个时间就是日期往后推一天的结果 
        }
        if(HdUtils.strNotNull(consignNam)){
        String jqpl1 = "select a from CClientCod a where a.cClientNam =:cClientNam";
        QueryParamLs paramLs1 = new QueryParamLs();
        paramLs1.addParam("cClientNam", consignNam);
        List<CClientCod> cClientCodList = JpaUtils.findAll(jqpl1, paramLs1);
        if(cClientCodList.size()>0){
        	contractIeDoc.setConsignCod(cClientCodList.get(0).getClientCod());
        }
        } 
        if(HdUtils.strNotNull(consignNam)){
            String jqpl1 = "select a from CClientCod a where a.cClientNam =:cClientNam";
            QueryParamLs paramLs1 = new QueryParamLs();
            paramLs1.addParam("cClientNam", consignNam);
            List<CClientCod> cClientCodList = JpaUtils.findAll(jqpl1, paramLs1);
            if(cClientCodList.size()>0){
            	contractIeDoc.setConsignCod(cClientCodList.get(0).getClientCod());
            	contractIeDoc.setConsignNam(cClientCodList.get(0).getcClientShort());
            }
            } 
       if(HdUtils.strNotNull(brandNam)){
    	   String jpqlb="select a from CBrand a where a.brandNam=:brandNam ";
    	   QueryParamLs paramLsb = new QueryParamLs();
           paramLsb.addParam("brandNam", brandNam);
           List<CBrand> cbList=JpaUtils.findAll(jpqlb, paramLsb);
           if(cbList.size()>0){
           contractIeDoc.setBrand(cbList.get(0).getBrandCod());   
           }
        }
       if(HdUtils.strNotNull(carKindNam)){
    	   String jpqlk="select a from CCarKind a where a.carKindNam=:carKindNam ";
    	   QueryParamLs paramLsk = new QueryParamLs();
           paramLsk.addParam("carKindNam", carKindNam);
           List<CCarKind> cckist=JpaUtils.findAll(jpqlk, paramLsk);
           if(cckist.size()>0){
           contractIeDoc.setCarKind(cckist.get(0).getCarKind());   
           }
        }
       if(HdUtils.strNotNull(billNo)){
    	contractIeDoc.setBillNo(billNo);  
       }
       if(HdUtils.strNotNull(machcondition)){
       contractIeDoc.setOutMac(machcondition); 
       }
        if(HdUtils.strNotNull(personcondition)){
       contractIeDoc.setOutPerson(personcondition);  
       }
       contractIeDoc.setCarNum(carNum);
       contractIeDoc.setRecTim(HdUtils.getDateTime());
       contractIeDoc.setRecNam(HdUtils.getCurUser().getAccount());
       if(GZ.equals(dockNam)){
       contractIeDoc.setDockCod("03406500"); 
       }
       if(HQ.equals(dockNam)){
        contractIeDoc.setDockCod("03409000"); 
        }
       JpaUtils.save(contractIeDoc);
    }
    HdMessageCode result = new HdMessageCode();
    result.setCode("1");
    result.setMessage(uuid);
    return result;
  }
	public String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}
  @ResponseBody
  @RequestMapping({"delete"})
  public HdMessageCode delete(String uploadId) { LOG.debug("del:" + uploadId);
    this.fileUploadService.remove(uploadId);
    HdMessageCode result = new HdMessageCode();
    result.setCode("1");
    result.setMessage(uploadId);
    return result; }

  @RequestMapping({"download"})
  public void download(String uploadId, HttpServletResponse response) throws Exception {
    ComFileUpload upload = this.fileUploadService.findById(uploadId);
    String oriName = upload.getFileName();
    String fileFullpath = upload.getFilePath() + upload.getUploadId() + ".upload";

    String mimeType = "application/octet-stream";

    response.setContentType(mimeType);
    response.setContentLength(Integer.valueOf(upload.getFileSize()).intValue());

    String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"%s\"", new Object[] { oriName });
    response.setHeader(headerKey, headerValue);
    FileUtil.downLoad(fileFullpath, response.getOutputStream());
  }
}