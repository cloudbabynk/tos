package net.huadong.tech.ship.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.ship.entity.CShipData;
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
@RequestMapping({ "webresources/login/work/FileUpload" })
public class WorkFileUploadController {
	private static Logger LOG = Logger.getLogger(WorkFileUploadController.class);

	@Autowired
	private FileUploadService fileUploadService;
	private String ingateId;
	private String contractNo;
	private String inCyNam;

	@Value("${file.ShipReport-upload-path}")
	private String uploadPath;

	@RequestMapping({ "" })
	public String page(String nouse, String ingateId, String contractNo, String inCyNam) {
		this.ingateId = ingateId;
		this.contractNo = contractNo;
		this.inCyNam = inCyNam;
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
		String ingateId = this.ingateId;
		String contractNo = this.contractNo;
		String inCyNam = this.inCyNam;
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
		List<ShipReport> shipReportList = new ArrayList();
		Sheet sheet = wb.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String vinNo = row.getCell(0).getStringCellValue();
			String jpql = "select a from PortCar a where a.currentStat = '2' and a.vinNo =:vinNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("vinNo", vinNo);
			List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
			if (portCarList.size() < 1) {
				result.setCode("0");
				result.setMessage("车架号：" + vinNo + "不在场");
				return result;
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String vinNo = row.getCell(0).getStringCellValue();
			String jpql = "select a from PortCar a where a.currentStat = '2' and a.vinNo =:vinNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("vinNo", vinNo);
			List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
			PortCar portCar = portCarList.get(0);
			if (portCar != null) {
				GateTruck gateTruck1 = JpaUtils.findById(GateTruck.class, ingateId);
				WorkCommand workCommand = new WorkCommand();
				workCommand.setQueueId(HdUtils.genUuid());
				GateTruck gateTruck = JpaUtils.findById(GateTruck.class, ingateId);
				String jpql1 = "select a from WorkQueue a where a.contractNo =:contractNo and a.workTyp='TO' and a.truckNo=:truckNo";
				QueryParamLs paramLs1 = new QueryParamLs();
				paramLs1.addParam("contractNo", contractNo);
				paramLs1.addParam("truckNo", gateTruck.getTruckNo());
				List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
				if (workQueueList.size() > 0) {
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				}
				workCommand.setRfidCardNo(portCar.getRfidCardNo().toString());
				BeanUtils.copyProperties(portCar, workCommand);
				workCommand.setWorkTyp("TO");
				workCommand.setTruckNo(gateTruck1.getTruckNo());
				workCommand.setUseMachId("0");
				workCommand.setOutCyNam(inCyNam);
				JpaUtils.save(workCommand);

				TruckWork truckWork = new TruckWork();
				BeanUtils.copyProperties(portCar, truckWork);
				truckWork.setIngateId(ingateId);
				truckWork.setContractNo(contractNo);
				truckWork.setTruckNo(gateTruck1.getTruckNo());
				truckWork.setInGateNo(gateTruck1.getInGatNo());
				truckWork.setInRecNam(inCyNam);
				truckWork.setWorkNam(inCyNam);
				try {
					truckWork.setWorkTim(new Timestamp(format.parse(row.getCell(1).getStringCellValue()).getTime()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				truckWork.setInGatTim(HdUtils.getDateTime());
				truckWork.setCarryId("T");
				truckWork.setCarryWay("0");
				truckWork.setVinNo(portCar.getVinNo());
				JpaUtils.save(truckWork);

				portCar.setCurrentStat("0");
				portCar.setCyPlac(null);
				portCar.setOutCyTim(truckWork.getWorkTim());
				portCar.setOutToolNo(gateTruck.getPlatNo());
				JpaUtils.update(portCar);

				String jpql2 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("ingateId", ingateId);
				paramLs2.addParam("contractNo", contractNo);
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
				for (GateTruckContract gateTruckContract : gateTruckContractList) {
					gateTruckContract.setWorkNum(gateTruckContract.getWorkNum().add(new BigDecimal("1")));
					JpaUtils.update(gateTruckContract);
				}

			}
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