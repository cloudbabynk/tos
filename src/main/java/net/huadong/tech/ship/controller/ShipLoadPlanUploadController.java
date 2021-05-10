package net.huadong.tech.ship.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
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
@RequestMapping({"webresources/login/ship/ShipLoadPlanUpload"})
public class ShipLoadPlanUploadController
{
  private static Logger LOG = Logger.getLogger(ShipLoadPlanUploadController.class);

  @Autowired
  private FileUploadService fileUploadService;

  @Value("${file.ShipLoadPlan-upload-path}")
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
    HdMessageCode result = new HdMessageCode();
    for (int i = 1; i <=sheet.getLastRowNum(); i++)
    {
        Row row = sheet.getRow(i);//获取索引为i的行，以0开始
        String seqNo = "";
        String POD = "";
        String BookingNo = "";
        String commodity = "";
        String cyAreaNo = "";
        String Unit = null;
        String Length = null ;
        String Width = null ;
        String Height = null;
        String Weight = null;
        String tele="";
        if(row.getCell(0) != null){//获取第i行的索引为0的单元格数据
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
        String jpqlm="select max(cast(a.planNo as int)) as maxPlanNo from ShipLoadPlan a";
        List<BigDecimal> contractList=JpaUtils.findAll(jpqlm, null);
        if(contractList.get(0)==null){
        	shiploadplan.setPlanNo("1");
        }else{
        	Integer maxPlanNo=contractList.get(0).intValue()+1;
        	shiploadplan.setPlanNo(maxPlanNo.toString());	
        }
        String jpql="select a from ShipBill a where a.billNo=:billNo and a.iEId='E' ";
        QueryParamLs paramLs = new QueryParamLs();
        paramLs.addParam("billNo",BookingNo);
        List<ShipBill> shipBillList=JpaUtils.findAll(jpql, paramLs);
        if(shipBillList.size()>0){
            Ship ship=JpaUtils.findById(Ship.class,shipBillList.get(0).getShipNo());
            shiploadplan.setTradeId(ship.getTradeId());
            shiploadplan.setShipNo(shipBillList.get(0).getShipNo());
            if(shipBillList.get(0).getConsignCod()!=null){
            shiploadplan.setConsignCod(shipBillList.get(0).getConsignCod());
            CClientCod cct=JpaUtils.findById(CClientCod.class,shipBillList.get(0).getConsignCod());
            shiploadplan.setConsignNam(cct.getcClientShort());
        	shiploadplan.setConsignCod(shipBillList.get(0).getConsignCod());
            }else{
            shiploadplan.setConsignNam("*");
            shiploadplan.setConsignCod("*");
            }
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
            }else{
            	//System.out.println("车型不存在！");
            	throw new HdRunTimeException("车型不存在！");//	
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
            shiploadplan.setPlac(cyAreaNo);
            shiploadplan.setDockCod(ship.getDockCod());
            shiploadplan.setWorkSeqNo(new BigDecimal(shiploadplan.getPlanNo()));
            shiploadplan.setRecTim(HdUtils.getDateTime());
            shiploadplan.setRecNam(HdUtils.getCurUser().getAccount());
            shiploadplan.setTele(tele);
            JpaUtils.save(shiploadplan);
        }else{
        	 result.setCode("1");
        	 result.setMessage("提单号："+BookingNo+"不存在！");
        	 break;
        	//throw new HdRunTimeException("提单号："+BookingNo+"不存在！");
        }
    }
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