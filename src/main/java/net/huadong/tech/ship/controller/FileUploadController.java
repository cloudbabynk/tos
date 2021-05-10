package net.huadong.tech.ship.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.FileUploadService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"webresources/login/com/FileUpload"})
public class FileUploadController
{
  private static Logger LOG = Logger.getLogger(FileUploadController.class);

  @Autowired
  private FileUploadService fileUploadService;

  @Value("${file.ShipReport-upload-path}")
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
    try{
    	if(true){
    		wb = new XSSFWorkbook(file.getInputStream());
    	}else{
    		wb = new HSSFWorkbook(file.getInputStream());
    	}
    }catch (IOException e)
    {
        e.printStackTrace();
        return null;
    }
    List<ShipReport> shipReportList = new ArrayList();
    Sheet sheet = wb.getSheetAt(0);
    for (int i = 0; i < sheet.getLastRowNum(); i++)
    {
        Row row = sheet.getRow(i);//获取索引为i的行，以0开始
        String shipNam = "";
        String eShipNam = "";
        String shipCorp = "";//船公司
        String tradeId = "";
        
        String iCargoNam = "";
        String iCargoWgt = "";
        String iConsignNam = "";
        String iLine = "";
        
        String eCargoNam = "";
        String eCargoWgt = "";
        String eConsignNam = "";
        String eLine = "";
        
        String etaTim = "";
        String tenDays = "";
        
        String shipLength = "";
        String shipWidth = "";
        String shipCountry = "";
        String shipWgt = "";
        
        String shipAgent = "";
        String telephon = "";
        if(row.getCell(1) != null){//获取第i行的索引为0的单元格数据
        	shipNam = row.getCell(1).getStringCellValue();
        }
        if(row.getCell(2) != null){
        	eShipNam = row.getCell(2).getStringCellValue();
        }
        if(row.getCell(3) != null){
        	shipCorp = row.getCell(3).getStringCellValue();
        }
        if(row.getCell(4) != null){
        	tradeId = row.getCell(4).getStringCellValue();
        }
        if(row.getCell(5) != null){
        	iCargoNam = row.getCell(5).getStringCellValue();
        }
        if(row.getCell(6) != null){
        	iCargoWgt = row.getCell(6).getStringCellValue();
        }
        if(row.getCell(7) != null){
        	iConsignNam = row.getCell(7).getStringCellValue();
        }
        if(row.getCell(8) != null){
        	iLine = row.getCell(8).getStringCellValue();
        }
        if(row.getCell(9) != null){
        	eCargoNam = row.getCell(9).getStringCellValue();
        }
        if(row.getCell(10) != null){
        	eCargoWgt = row.getCell(10).getStringCellValue();
        }
        if(row.getCell(11) != null){
        	eConsignNam = row.getCell(11).getStringCellValue();
        }
        if(row.getCell(12) != null){
        	eLine = row.getCell(12).getStringCellValue();
        }
        if(row.getCell(13) != null){
        	etaTim = row.getCell(13).getStringCellValue();
        }
        if(row.getCell(14) != null){
        	tenDays = row.getCell(14).getStringCellValue();
        }
        
        if(row.getCell(15) != null){
        	shipLength = row.getCell(15).getStringCellValue();
        }
        if(row.getCell(16) != null){
        	shipWidth = row.getCell(16).getStringCellValue();
        }
        if(row.getCell(17) != null){
        	shipCountry = row.getCell(17).getStringCellValue();
        }
        if(row.getCell(18) != null){
        	shipWgt = row.getCell(18).getStringCellValue();
        }
        
        
        
        
        if(row.getCell(19) != null){
        	shipAgent = row.getCell(19).getStringCellValue();
        }
        if(row.getCell(20) != null){
        	telephon = row.getCell(20).getStringCellValue();
        }
        ShipReport shipReport = new ShipReport();//进口
        shipReport.setShipRptId(HdUtils.generateUUID());
        shipReport.setShipNam(shipNam);
        shipReport.seteShipNam(eShipNam);
        String jpql = "select a from CShipData a where a.eShipNam =:eShipNam";
        QueryParamLs paramLs = new QueryParamLs();
        if(HdUtils.strNotNull(eShipNam)){
        	paramLs.addParam("eShipNam", eShipNam);
        }
        List<CShipData> cShipDataList = JpaUtils.findAll(jpql, paramLs);
        if(cShipDataList.size()>0){
        	shipReport.setShipCodId(cShipDataList.get(0).getShipCodId());
        }
        String jqpl1 = "select a from CClientCod a where a.cClientNam =:cClientNam";
        QueryParamLs paramLs1 = new QueryParamLs();
        if(HdUtils.strNotNull(shipCorp)){
        	paramLs1.addParam("cClientNam", shipCorp);
        }
        List<CClientCod> cClientCodList = JpaUtils.findAll(jqpl1, paramLs1);
        if(cClientCodList.size()>0){
        	shipReport.setShipCorpCod(cClientCodList.get(0).getShipCorpId());
        }
        shipReport.setTradeId(tradeId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
        	shipReport.setEtaTim(new Timestamp(sdf.parse(etaTim).getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
        shipReport.setTenDays(tenDays);
        QueryParamLs paramLs2 = new QueryParamLs();
        if(HdUtils.strNotNull(shipCorp)){
        	paramLs2.addParam("cClientNam", shipAgent);
        }
        List<CClientCod> cClientCodList1 = JpaUtils.findAll(jqpl1, paramLs2);
        if(cClientCodList1.size()>0){
        	shipReport.setShipAgentCod(cClientCodList1.get(0).getShipCorpId());
        }
        shipReport.setTelephon(telephon);
        ShipReport bean = new ShipReport();//出口
        BeanUtils.copyProperties(shipReport, bean);
        bean.setShipRptId(HdUtils.genUuid());
        JpaUtils.save(shipReport);
    }
    HdMessageCode result = new HdMessageCode();
    result.setCode("1");
    result.setMessage(uuid);
    return result;
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