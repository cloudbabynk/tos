package net.huadong.tech.shipbill.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.shipbill.entity.ShipBill;
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
@RequestMapping({"webresources/login/shipbill/ShipBillUpload"})
public class ShipBillUploadController
{
  private static Logger LOG = Logger.getLogger(ShipBillUploadController.class);

  @Autowired
  private FileUploadService fileUploadService;

  @Value("${file.ShipBill-upload-path}")
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
  public HdMessageCode upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response,@RequestParam String shipNo) throws Exception {
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
    Sheet sheet = wb.getSheetAt(0);
    for (int i = 9; i < sheet.getLastRowNum(); i++)
    {
        Row row = sheet.getRow(i);//获取索引为i的行，以0开始
        String carTypNam = "";
        String line = "";
        String carNum = "";
        String weight = "";
        String receiveNam = "";
        String payNam = "";
        if(row.getCell(0) != null){//获取第i行的索引为0的单元格数据
        	carTypNam = row.getCell(0).getStringCellValue();
        }
        if(row.getCell(1) != null){
        	line = row.getCell(1).getStringCellValue();
        }
        if(row.getCell(2) != null){
        	carNum = String.valueOf(row.getCell(2).getNumericCellValue());
        }
        if(row.getCell(3) != null){
        	weight =  String.valueOf(row.getCell(3).getNumericCellValue());
        }
        if(row.getCell(4) != null){
        	receiveNam = row.getCell(4).getStringCellValue();
        }
        if(row.getCell(5) != null){
        	payNam = row.getCell(5).getStringCellValue();
        }

        ShipBill shipbill = new ShipBill();
        shipbill.setShipbillId(HdUtils.generateUUID());
        shipbill.setShipNo(shipNo);
        shipbill.setTradeId("1");
        shipbill.setiEId("I");
        shipbill.setCarNum(new BigDecimal(carNum));
        shipbill.setWeights(new BigDecimal(weight));
        String jpql = "select a from CCarTyp a where a.carTypNam =:carTypNam";
        QueryParamLs paramLs = new QueryParamLs();
        if(HdUtils.strNotNull(carTypNam)){
        	paramLs.addParam("carTypNam", carTypNam);
        }else{
        	continue;
        }
        List<CCarTyp> cCarTypList = JpaUtils.findAll(jpql, paramLs);
        if(cCarTypList.size()>0){
        	if(HdUtils.strNotNull(cCarTypList.get(0).getBrandCod())){
        		shipbill.setBrandCod(cCarTypList.get(0).getBrandCod());	
        	}
        	if(HdUtils.strNotNull(cCarTypList.get(0).getCarKind())){
        		shipbill.setCarKind(cCarTypList.get(0).getCarKind());	
        	}
        }else{
        	continue;
        }
      
        JpaUtils.save(shipbill);
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