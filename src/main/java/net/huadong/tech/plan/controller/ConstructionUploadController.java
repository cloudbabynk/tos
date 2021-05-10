package net.huadong.tech.plan.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.util.FileUtil;
import net.huadong.tech.util.HdUtils;
@Controller
@RequestMapping({"webresources/login/plan/FileUpload"})
public class ConstructionUploadController
{
  private static Logger LOG = Logger.getLogger(ConstructionUploadController.class);
  @Autowired
  private FileUploadService fileUploadService;
  @Value("${file.Construction-upload-path}")
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
        String days = "";
        String workItems= "";
        String workArea= "";
        String startTim= "";
        String endTim = "";
        String isAffect = "";
        String consCorpNam = "";
        String relationNam = "";
        String responseMan = "";
        String relationTel = "";
        String outPerson = "";
        String outMach = "";
        String outMachNum = "";
        String danWorkItems = "";
        String recTele="";
        String remarks="";
        if(row.getCell(0) != null){//获取第i行的索引为0的单元格数据
        	days = row.getCell(0).getStringCellValue();
        }
        if(row.getCell(1) != null){
        	workItems = row.getCell(1).getStringCellValue();
        }
        if(row.getCell(2) != null){
        	workArea = row.getCell(2).getStringCellValue();
        }
        if(row.getCell(3) != null){
        	startTim = row.getCell(3).getStringCellValue();
        }
        if(row.getCell(4) != null){
        	endTim = row.getCell(4).getStringCellValue();
        }
        if(row.getCell(5) != null){
        	isAffect = row.getCell(5).getStringCellValue();
        }
        if(row.getCell(6) != null){
        	consCorpNam = row.getCell(6).getStringCellValue();
        }
        if(row.getCell(7) != null){
        	relationNam = row.getCell(7).getStringCellValue();
        }
        if(row.getCell(8) != null){
        	responseMan = row.getCell(8).getStringCellValue();
        }
        if(row.getCell(9) != null){
        	relationTel = row.getCell(9).getStringCellValue();
        }
        if(row.getCell(10) != null){
        	outPerson = row.getCell(10).getStringCellValue();
        }
        if(row.getCell(11) != null){
        	outMach = row.getCell(11).getStringCellValue();
        }
        if(row.getCell(12) != null){
        	outMachNum = row.getCell(12).getStringCellValue();
        }
        if(row.getCell(13) != null){
        	danWorkItems = row.getCell(13).getStringCellValue();
        }
        if(row.getCell(14) != null){
        	recTele = row.getCell(14).getStringCellValue();
        }
        if(row.getCell(15) != null){
        	remarks = row.getCell(15).getStringCellValue();
        }
        ConstructionPlan constructionPlan = new ConstructionPlan();
        constructionPlan.setPlanId(HdUtils.generateUUID());
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/MM/dd" ); 
        constructionPlan.setDays(sdf.parse(days));
        constructionPlan.setWorkItem(workItems);
        constructionPlan.setWorkArea(workArea);
        constructionPlan.setStartTim(startTim);
        constructionPlan.setEndTim(endTim);
        constructionPlan.setIsAffect(isAffect);
        if(HdUtils.strNotNull(consCorpNam)){
        String jpql="select a from CClientCod a where a.cClientNam=:cClientNam ";
    	QueryParamLs paramLsa = new QueryParamLs();
		paramLsa.addParam("cClientNam",consCorpNam);
		List <CClientCod> ccL=JpaUtils.findAll(jpql, paramLsa);
        if(ccL.size()>0){
        constructionPlan.setConsCorpCod(ccL.get(0).getClientCod()); 	
        }else{
        	throw new HdRunTimeException("系统无该相关方资料!");	
        }
        }else{
         constructionPlan.setConsCorpCod("");	
        }
        constructionPlan.setRelationNam(relationNam);
        constructionPlan.setResponseMan(responseMan);
        constructionPlan.setRelationTele(relationTel);
        constructionPlan.setOutPerson(new BigDecimal(outPerson));
        constructionPlan.setOutMach(outMach);
        constructionPlan.setOutMachNum(new BigDecimal(outMachNum));
        constructionPlan.setDanWorkItems(danWorkItems);
        constructionPlan.setRecTele(recTele);
        constructionPlan.setReamarks(remarks);
        constructionPlan.setRecNam(HdUtils.getCurUser().getAccount());
        constructionPlan.setRecTim(HdUtils.getDateTime());
       JpaUtils.save(constructionPlan);
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
