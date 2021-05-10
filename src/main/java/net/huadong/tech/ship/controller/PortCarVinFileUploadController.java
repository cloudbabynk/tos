package net.huadong.tech.ship.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.FileUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkQueue;

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
@RequestMapping({ "webresources/login/portCarVin/FileUpload" })
public class PortCarVinFileUploadController {
    private static Logger LOG = Logger.getLogger(BillVinFileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;
    private String tradeId;
    private String billNo;
    private String cyAreaNo;
    private String brandCod;
    private String tranPortCod;
    private String iEId;
    private  String portCarNos;

    @Value("${file.ShipReport-upload-path}")
    private String uploadPath;

    @RequestMapping({ "" })
    public String page(String tradeId, String iEId,String billNo,String cyAreaNo,String brandCod,String tranPortCod,String portCarNos) {

        this.tradeId=tradeId;
        this.iEId = iEId;
        this.billNo = billNo;
        this.cyAreaNo = cyAreaNo;
        this.brandCod = brandCod;
        this.tranPortCod = tranPortCod;
        this.portCarNos=portCarNos;
        return "com/fileupload";
    }

    @ResponseBody
    @RequestMapping({ "/find" })
    public List<ComFileUpload> find(String entityName, String entityId) {
        return this.fileUploadService.find(entityName, entityId);
    }

    @ResponseBody
    @RequestMapping({ "upload" })
    public HdMessageCode upload(@RequestParam MultipartFile file, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
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
        HdMessageCode result = new HdMessageCode();
        // excel导入


        Workbook wb = null;
        String ext = oriName.substring(oriName.lastIndexOf("."));
        try {
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(file.getInputStream());
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try{
            String portCarNosArr[]=this.portCarNos.split(",");

            Sheet sheet = wb.getSheetAt(0);
            Integer limit =0;
            if((sheet.getLastRowNum())<=portCarNosArr.length)
            {
                limit=sheet.getLastRowNum();
            }else {
                limit=portCarNosArr.length;
            }
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {//第一行不准有数据了!!!!!
                Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
                String vinNo = row.getCell(0).getStringCellValue();
                BigDecimal bigDecimal = new BigDecimal(Integer.parseInt(portCarNosArr[i]));
                PortCar portCar = JpaUtils.findById(PortCar.class, bigDecimal);
//			portCar.setBillNo(billNo);
//			portCar.setBrandCod(brandCod);
                portCar.setVinNo(vinNo);
//			portCar.setiEId(iEId);
//			portCar.setCyAreaNo(cyAreaNo);
//			portCar.setTranPortCod(tranPortCod);
                JpaUtils.update(portCar);
            }
        }catch (Exception e){
            result.setCode("-1");
            result.setMessage(e.getMessage());
            return  result;
        }

        result.setCode("1");
        result.setMessage(uuid);
        return result;
    }

    @ResponseBody
    @RequestMapping({ "delete" })
    public HdMessageCode delete(String uploadId) {
        LOG.debug("del:" + uploadId);
        this.fileUploadService.remove(uploadId);
        HdMessageCode result = new HdMessageCode();
        result.setCode("1");
        result.setMessage(uploadId);
        return result;
    }

    @RequestMapping({ "download" })
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