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
import net.huadong.tech.base.entity.MAreaInfo;
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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({ "webresources/login/base/MAreaInfoUpload" })
public class MAreaInfoUploadController {
	private static Logger LOG = Logger.getLogger(MAreaInfoUploadController.class);

	@Autowired
	private FileUploadService fileUploadService;
	private String workDte;

	@Value("${file.ShipLoadPlan-upload-path}")
	private String uploadPath;

	@RequestMapping({ "" })
	public String page(String nouse, String workDte) {
		this.workDte = workDte;
		return "com/fileupload";
	}

	@ResponseBody
	@RequestMapping({ "/find" })
	public List<ComFileUpload> find(String entityName, String entityId) {

		return this.fileUploadService.find(entityName, entityId);
	}

	@ResponseBody
	@Transactional
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

		// excel导入

		Workbook wb = null;
		String ext = oriName.substring(oriName.lastIndexOf("."));
		try {
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(file.getInputStream());
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(file.getInputStream());
			}
		} catch (FileNotFoundException e) {
			throw new HdRunTimeException("文件不存在！");//
		} catch (IOException e) {
			throw new HdRunTimeException("输出异常！");//
		}
		Sheet sheet = wb.getSheetAt(0);
		HdMessageCode result = new HdMessageCode();
		String workDte = this.workDte;
		String brandNam = sheet.getRow(2).getCell(0).getStringCellValue();
		String jpql = "select a FROM MAreaInfo a WHERE a.workDte =:workDte AND A.brandNam =:brandNam";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workDte", HdUtils.strToDate(workDte));
		paramLs.addParam("brandNam", brandNam);
		JpaUtils.removeAll(JpaUtils.findAll(jpql, paramLs));
//		JpaUtils.getEntityManager().createNativeQuery(sql).executeUpdate();
		for (int i = 2; i <= sheet.getLastRowNum() - 2; i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			brandNam = row.getCell(0).getStringCellValue();
			for (int j = 1; j <= 2; j++){
				row.getCell(j).setCellType(CellType.STRING);
			}
			String areaNum = row.getCell(1).getStringCellValue();
			String areaInfo = row.getCell(2).getStringCellValue();
			Double pieceNum = row.getCell(3).getNumericCellValue();
			MAreaInfo bean = new MAreaInfo();
			bean.setId(HdUtils.genUuid());
			bean.setBrandNam(brandNam);
			bean.setWorkDte(HdUtils.strToDate(workDte));
			bean.setAreaNum(areaNum);
			bean.setAreaInfo(areaInfo);
			bean.setPieceNum(new BigDecimal(pieceNum));
			JpaUtils.save(bean);
		}
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