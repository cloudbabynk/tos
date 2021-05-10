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
@RequestMapping({ "webresources/login/nmxc/FileUpload" })
public class NmxcFileUploadController {
	private static Logger LOG = Logger.getLogger(NmxcFileUploadController.class);

	@Autowired
	private FileUploadService fileUploadService;
	private String shipNo;
	private String inCyNam;

	@Value("${file.ShipReport-upload-path}")
	private String uploadPath;

	@RequestMapping({ "" })
	public String page(String nouse, String shipNo, String inCyNam) {
		this.shipNo = shipNo;
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
		String shipNo = this.shipNo;
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
		Sheet sheet = wb.getSheetAt(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("不存在卸船作业队列！");
		}
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String carTyp = "";
			String brandCod = "";
			String carKind = "";
			String dockCod = "";
			String billNo = "";
			Timestamp time = new Timestamp(System.currentTimeMillis());
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String vinNo = row.getCell(0).getStringCellValue();
			try {
				time = new Timestamp(format.parse(row.getCell(1).getStringCellValue()).getTime());//获取卸船时间
			} catch (Exception e) {
				e.printStackTrace();
			}
			String vinNoStr = "";
			if (HdUtils.strNotNull(vinNo)) {// 获取车架号的前八位
				vinNoStr = vinNo.substring(0, 8);
			} else {
				continue;
			}

			String sql = "select a from CCarVin a where a.vinNo =:vinNo";
			QueryParamLs param = new QueryParamLs();
			param.addParam("vinNo", vinNoStr);
			List<CCarVin> cCarVinList = JpaUtils.findAll(sql, param);
			if (cCarVinList.size() > 0) {// 获取车架号对应的车型、品牌、车类信息
				carTyp = cCarVinList.get(0).getCarTyp();
				CCarTyp bean = JpaUtils.findById(CCarTyp.class, carTyp);
				if (bean != null) {
					brandCod = bean.getBrandCod();
					carKind = bean.getCarKind();
				}
			}
			Ship ship = JpaUtils.findById(Ship.class, shipNo);
			if (HdUtils.strNotNull(ship.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(brandCod)) {
					CBrand cBrand = JpaUtils.findById(CBrand.class, brandCod);
					if (HdUtils.strIsNull(cBrand.getBrandShort())) {
						throw new HdRunTimeException("品牌简称为空 请先维护品牌简称信息！");
					}
					billNo = cShipData.getShipShort() + " " + ship.getIvoyage() + cBrand.getBrandShort();
				} else {
					throw new HdRunTimeException("请输入品牌信息！");
				}

			}
			if (HdUtils.strNotNull(inCyNam)) {
				CEmployee bean = JpaUtils.findById(CEmployee.class, inCyNam);
				if (bean != null) {
					if (CEmployee.TYP_GZ_01.equals(bean.getClassNo()) || CEmployee.TYP_GZ_02.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_03.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_04.equals(bean.getClassNo())
							|| CEmployee.TYP_GZ_05.equals(bean.getClassNo())) {
						dockCod = Ship.GZ;
					} else {
						dockCod = Ship.HQ;
					}
				} else {
					throw new HdRunTimeException("理货员信息不完善！");
				}
			}
			if (HdUtils.strNotNull(billNo)) {
				PortCar portCar = new PortCar();
				portCar.setiEId(Ship.JK);
				portCar.setTradeId(Ship.NM);
				portCar.setInPortNo(" ");
				portCar.setCurrentStat("2");
				portCar.setBillNo(billNo);
				portCar.setShipNo(shipNo);
				portCar.setInCyTim(time);
				portCar.setRfidCardNo("vepc" + new Date().getTime());
				portCar.setVinNo(portCar.getRfidCardNo());
				if (HdUtils.strNotNull(brandCod)) {
					portCar.setBrandCod(brandCod);
				}
				portCar.setLengthOverId("0");
				portCar.setCarTyp(carTyp);
				portCar.setCarKind(carKind);
				portCar.setCyAreaNo("HQC1");
				portCar.setCyRowNo("###");
				portCar.setCyBayNo("###");
				portCar.setCyPlac("###");
				portCar.setDockCod(dockCod);
				JpaUtils.save(portCar);

				WorkCommand workCommand = new WorkCommand();
				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setRfidCardNo(portCar.getRfidCardNo());
				workCommand.setVinNo(portCar.getVinNo());
				workCommand.setPortCarNo(portCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				if (HdUtils.strNotNull(brandCod)) {
					workCommand.setBrandCod(brandCod);
				}
				workCommand.setCarTyp(carTyp);
				workCommand.setInCyTim(time);
				workCommand.setShipWorkTim(time);
				workCommand.setFinishedId("0");
				workCommand.setLengthOverId("0");
				workCommand.setUseMachId("0");
				workCommand.setUseWorkerId("0");
				workCommand.setNightId("0");
				workCommand.setHolidayId("0");
				workCommand.setQueueId(HdUtils.genUuid());
				workCommand.setInCyTim(time);
				workCommand.setInCyNam(inCyNam);
				workCommand.setShipNo(shipNo);
				workCommand.setBillNo(billNo);
				workCommand.setCyPlac(portCar.getCyPlac());
				workCommand.setCarKind(carKind);
				JpaUtils.save(workCommand);
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