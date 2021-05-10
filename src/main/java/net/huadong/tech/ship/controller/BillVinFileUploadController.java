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
@RequestMapping({ "webresources/login/billvin/FileUpload" })
public class BillVinFileUploadController {
	private static Logger LOG = Logger.getLogger(BillVinFileUploadController.class);

	@Autowired
	private FileUploadService fileUploadService;
	private String shipNo;
	private String iEId;

	@Value("${file.ShipReport-upload-path}")
	private String uploadPath;

	@RequestMapping({ "" })
	public String page(String nouse, String shipNo, String iEId) {
		this.shipNo = shipNo;
		this.iEId = iEId;
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
		String shipNo = this.shipNo;
		String iEId = this.iEId;
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
		Sheet sheet = wb.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String vinNo = row.getCell(1).getStringCellValue();
			String billNo = row.getCell(0).getStringCellValue();

			MBillVin bean = new MBillVin();
			bean.setBillVinId(HdUtils.genUuid());
			bean.setShipNo(shipNo);
			bean.setiEId(iEId);
			bean.setBillNo(billNo.trim());
			bean.setVinNo(vinNo);
			JpaUtils.save(bean);
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