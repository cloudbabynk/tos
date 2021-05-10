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
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.ShipReport;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
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
@RequestMapping({ "webresources/login/nmjg/FileUpload" })
public class NmjgFileUploadController {
	private static Logger LOG = Logger.getLogger(NmjgFileUploadController.class);

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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);// 获取索引为i的行，以0开始
			String vinNo = row.getCell(5).getStringCellValue();
			String jpql1 = "select a from PortCar a where a.vinNo =:vinNo and a.currentStat =:currentStat";
			QueryParamLs currentParam = new QueryParamLs();
			currentParam.addParam("vinNo", vinNo);
			currentParam.addParam("currentStat", "2");
			List<PortCar> list = JpaUtils.findAll(jpql1, currentParam);
			if (list.size() > 0){
				continue;
			}
			String flowNam = row.getCell(7).getStringCellValue();
			if (HdUtils.strIsNull(flowNam)){
				continue;
			} else {
				flowNam = flowNam.substring(0, 2);
			}
			String carTyp = "";
			String brandCod = "";
			String carKind = "";
			String vinNoStr = "";
			String flowCod = "";
			String cyAreaNo = "";
			String tranPortCod = "";
			//处理时间
			Timestamp time = new Timestamp(System.currentTimeMillis());
			try {
				time = new Timestamp(format.parse(row.getCell(17).getStringCellValue()).getTime());//获取卸船时间
			} catch (Exception e) {
				e.printStackTrace();
			}
			//解析品牌车型
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
			//获取流向和堆场
			String sql1 = "select a from CPort a where a.cPortNam =:cPortNam";
			QueryParamLs queryCport = new QueryParamLs();
			queryCport.addParam("cPortNam", flowNam);
			List<CPort> cPortList = JpaUtils.findAll(sql1, queryCport);
			if (cPortList.size() > 0) {
				tranPortCod = cPortList.get(0).getPortCod();
			}
			if ("上海".equals(flowNam)){
				cyAreaNo = "HQD1";
			} else if ("东莞".equals(flowNam)){
				cyAreaNo = "HQD2";
			} else if ("南沙".equals(flowNam)){
				cyAreaNo = "HQD3";
			}
			PortCar portCar = new PortCar();
			portCar.setRfidCardNo("vepc" + new Date().getTime());
			portCar.setVinNo(vinNo);
			portCar.setCurrentStat("2");
			portCar.setLengthOverId("0");
			portCar.setCarTyp(carTyp);
			portCar.setCarKind(carKind);
			portCar.setBrandCod(brandCod);
            portCar.setTranPortCod(tranPortCod);
			portCar.setBillNo("--");
			portCar.setCyAreaNo(cyAreaNo);
			portCar.setContractNo(contractNo);
			ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
			portCar.setiEId(contractIeDoc.getiEId());
			portCar.setTradeId(contractIeDoc.getTradeId());
			portCar.setDockCod(contractIeDoc.getDockCod());
			portCar.setInPortNo(contractIeDoc.getContractNo());
			// updatePortCar(portCar, cargoInfo); //自动理货计算车位
			portCar.setCyRowNo("###");
			portCar.setCyBayNo("###");
			portCar.setCyPlac("###");
			portCar.setInCyTim(time);
			portCar.setRemarks("手持录入");
			if (HdUtils.strNotNull(contractIeDoc.getShipNo())) {
				portCar.setShipNo(contractIeDoc.getShipNo());
			}
			JpaUtils.save(portCar);

			TruckWork truckWork = new TruckWork();
			BeanUtils.copyProperties(portCar, truckWork);
			truckWork.setPortCarNo(portCar.getPortCarNo());
			truckWork.setIngateId(ingateId);
			String jpql2 = "select a from GateTruckContract a where a.ingateId=:ingateId and a.contractNo=:contractNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("ingateId", truckWork.getIngateId());
			paramLs2.addParam("contractNo", truckWork.getContractNo());
			List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpql2, paramLs2);
			for (GateTruckContract gateTruckContract : gateTruckContractList) {
				truckWork.setCarryId(gateTruckContract.getCarryId());
				truckWork.setTruckNo(gateTruckContract.getTruckNo());
			}
			GateTruck gateTruck = JpaUtils.findById(GateTruck.class, truckWork.getIngateId());
			if (gateTruck != null) {
				truckWork.setInGatTim(gateTruck.getInGatTim());
				truckWork.setCarryWay(gateTruck.getSingleId());
				truckWork.setInGateNo(gateTruck.getInGatNo());
				truckWork.setInRecNam(gateTruck.getInRecNam());
			}
			truckWork.setBillNo("--");
			truckWork.setCarTyp(carTyp);
			truckWork.setBrandCod(brandCod);
			truckWork.setCarKind(carKind);
			truckWork.setTradeId(portCar.getTradeId());
			truckWork.setDiscPortCod(contractIeDoc.getDiscPortCod());
			truckWork.setTranPortCod(contractIeDoc.getTranPortCod());
			truckWork.setWorkTim(time);
			truckWork.setWorkNam(inCyNam);
			truckWork.setVinNo(vinNo);
			truckWork.setRfidCardNo(vinNo);
			truckWork.setCyPlac(portCar.getCyPlac());
			truckWork.setiEId(portCar.getiEId());
			if (HdUtils.strNotNull(contractIeDoc.getShipNo())) {
				truckWork.setShipNo(contractIeDoc.getShipNo());
			}
			JpaUtils.save(truckWork);

			WorkCommand workCommand = new WorkCommand();
			String jpql3 = "select a from WorkQueue a where a.workTyp = 'TI' and a.contractNo=:contractNo and a.truckNo=:truckNo";
			QueryParamLs paramLs3 = new QueryParamLs();
			paramLs3.addParam("contractNo", truckWork.getContractNo());
			paramLs3.addParam("truckNo", truckWork.getTruckNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql3, paramLs3);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("作业指令保存失败");
			}
			for (WorkQueue workQueue : workQueueList) {

				workCommand.setWorkQueueNo(workQueue.getWorkQueueNo());
			}
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setRfidCardNo(truckWork.getRfidCardNo());
			workCommand.setWorkTyp("TI");
			workCommand.setVinNo(truckWork.getVinNo());
			workCommand.setCyPlac(portCar.getCyPlac());
			workCommand.setContractNo(truckWork.getContractNo());
			if (HdUtils.strNotNull(truckWork.getCarKind())) {
				workCommand.setCarKind(truckWork.getCarKind());
			}

			if (HdUtils.strNotNull(truckWork.getCarTyp())) {
				workCommand.setCarTyp(truckWork.getCarTyp());
			}

			if (HdUtils.strNotNull(truckWork.getBrandCod())) {
				workCommand.setBrandCod(truckWork.getBrandCod());
			}
			if (HdUtils.strNotNull(truckWork.getBillNo())) {
				workCommand.setBillNo(truckWork.getBillNo());
			}
			if (HdUtils.strNotNull(contractIeDoc.getShipNo())) {
				workCommand.setShipNo(contractIeDoc.getShipNo());
			}
			workCommand.setInCyTim(time);
			workCommand.setInCyNam(inCyNam);
			workCommand.setRemarks("手持录入");
			JpaUtils.save(workCommand);

			GateTruckContract gateTruckContract = gateTruckContractList.get(0);
			gateTruckContract.setWorkNum(gateTruckContract.getWorkNum().add(new BigDecimal("1")));
			JpaUtils.update(gateTruckContract);

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