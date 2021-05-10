package net.huadong.tech.work.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.PageInfo;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.his.entity.PortCarBak;
import net.huadong.tech.plan.entity.PlanGroup;
import net.huadong.tech.plan.entity.PlanPlacVin;
import net.huadong.tech.plan.entity.PlanRange;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipLoadPlan;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.entity.ShipBillRecord;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.MoveCarPlan;
import net.huadong.tech.work.entity.VWorkCommand;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.work.service.WorkCommandService;

/**
 * @author
 */
@Component
public class WorkCommandServiceImpl implements WorkCommandService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql = "select a.billNo bno, a.carTyp ctp, count(a.queueId) num  from WorkCommand a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		} else {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", "123456789##");
		}
		if ("XC".equals(type) || "PLXC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SI");
		} else if ("ZC".equals(type) || "PLZC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SO");
		} else if ("PLTZ".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TZ");
		}
		jpql += "group by a.billNo,a.carTyp";
		List<Object[]> objlist = JpaUtils.findAll(jpql, paramLs);
		List<WorkCommand> workCommandListAll = new ArrayList();
		for (Object[] obj : objlist) {
			String jpql1 = "select a from WorkCommand a where a.billNo =:billNo and a.carTyp =:carTyp ";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("billNo", obj[0]);
			paramLs1.addParam("carTyp", obj[1]);
			if ("XC".equals(type) || "PLXC".equals(type)) {
				jpql1 += "and a.workTyp =:workTyp ";
				paramLs1.addParam("workTyp", "SI");
			} else if ("ZC".equals(type) || "PLZC".equals(type)) {
				jpql1 += "and a.workTyp =:workTyp ";
				paramLs1.addParam("workTyp", "SO");
			} else if ("PLTZ".equals(type)) {
				jpql1 += "and a.workTyp =:workTyp ";
				paramLs1.addParam("workTyp", "TZ");
			}
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
			if (workCommandList.size() > 0) {
				WorkCommand workCommand = workCommandList.get(0);
				workCommand.setRksl(String.valueOf(obj[2]));
				workCommandListAll.add(workCommand);
			}
		}
		for (WorkCommand bc : workCommandListAll) {
			if (HdUtils.strNotNull(bc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if (ccartyp != null) {
					bc.setCarTypNam(ccartyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				if (carkind != null) {
					bc.setCarKindNam(carkind.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
				if (ship != null) {
					bc.setShipNam(ship.getcShipNam());
					bc.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			if (HdUtils.strNotNull(bc.getInCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getInCyNam());
				if (cEmployee != null) {
					bc.setInCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(bc.getOutCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getOutCyNam());
				if (cEmployee != null) {
					bc.setOutCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (bc.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bc.getPortCarNo());
				if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
					CCyArea cCyArea = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
					if (cCyArea != null) {
						bc.setCyArea(cCyArea.getCyAreaNam());
					}
				}
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(workCommandListAll);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see net.huadong.tech.work.service.WorkCommandService#exportExcel(net.huadong.tech.springboot.core.HdEzuiDatagridData, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void exportExcel(HdEzuiDatagridData data,HttpServletResponse response,HdQuery hdQuery){
		List<VWorkCommand> vWorkCommandList = data.getRows();
		HSSFWorkbook ex = new HSSFWorkbook();
		HSSFCellStyle style = ex.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//自适应宽度
		
        Sheet sheet1 = ex.createSheet();
        Row row = sheet1.createRow(0);
        Row row1 = sheet1.createRow(1);
        for (int i=0;i<10;i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			Cell cell1 = row1.createCell(i);
			cell1.setCellStyle(style);
			if (i!=3 && i!=4 && i!=7 && i!=8) {
			 CellRangeAddress region = new CellRangeAddress(0, 1, i, i);// 起始行号，终止行号， 起始列号，终止列号
			 //在sheet里增加合并单元格  
			 sheet1.addMergedRegion(region);
			}
        }
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 3, 4);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region1);
		CellRangeAddress region2 = new CellRangeAddress(0, 0, 7, 8);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region2);
        row.getCell(0).setCellValue("船名");
        row.getCell(1).setCellValue("提单号");
        row.getCell(2).setCellValue("货物名称");
        row.getCell(3).setCellValue("货物总数");
        row.getCell(5).setCellValue("货代");
        row.getCell(6).setCellValue("联系电话");
        row.getCell(7).setCellValue("集港情况");
        row.getCell(9).setCellValue("场地位置");
        
        row1.getCell(3).setCellValue("车");
        row1.getCell(4).setCellValue("件");
        row1.getCell(7).setCellValue("数量");
        row1.getCell(8).setCellValue("齐");
        
        for(int j=2;j<=vWorkCommandList.size()+1;j++){
        	Row bean = sheet1.createRow(j);
        	for (int k=0;k<10;k++) {
    			Cell cell = bean.createCell(k);
    			cell.setCellStyle(style);
            }
        	bean.getCell(0).setCellValue(vWorkCommandList.get(j-2).getShipNam());
        	bean.getCell(1).setCellValue(vWorkCommandList.get(j-2).getBillNo());
        	bean.getCell(2).setCellValue(vWorkCommandList.get(j-2).getCarTypNam());
        	if (vWorkCommandList.get(j-2).getCarTypNam().indexOf("设备") != -1){
        		bean.getCell(3).setCellValue("");
            	bean.getCell(4).setCellValue(vWorkCommandList.get(j-2).getRksl());
        	} else {
        		bean.getCell(3).setCellValue(vWorkCommandList.get(j-2).getRksl());
            	bean.getCell(4).setCellValue("");
        	}
    		if (HdUtils.strNotNull(vWorkCommandList.get(j-2).getConsignCod())){
    			CClientCod cClientCod = JpaUtils.findById(CClientCod.class, vWorkCommandList.get(j-2).getConsignCod());
    			if (cClientCod != null) {
    				bean.getCell(5).setCellValue(cClientCod.getcClientShort());
    			}
    		}
//        	if (vWorkCommandList.get(j-2).getPortCarNo() != null) {
//        		PortCar portCar = JpaUtils.findById(PortCar.class, vWorkCommandList.get(j-2).getPortCarNo());
//        		if (portCar != null){
//        			bean.getCell(6).setCellValue(portCar.getContactInfo());
//        		}
//        	}
        	bean.getCell(6).setCellValue(vWorkCommandList.get(j-2).getContactInfo());
        	
        	String jpql = "select a from ShipBillRecord a where 1=1 ";
        	QueryParamLs paramLs = new QueryParamLs();
        	if (HdUtils.strNotNull(hdQuery.getStr("shipNo"))){
        		jpql += "and a.shipNo =:shipNo ";
        		paramLs.addParam("shipNo", hdQuery.getStr("shipNo"));
        	}
        	if (HdUtils.strNotNull(vWorkCommandList.get(j-2).getBillNo())){
        		jpql += "and a.billNo =:billNo ";
        		paramLs.addParam("billNo", vWorkCommandList.get(j-2).getBillNo());
        	}
        	List<ShipBillRecord> list = JpaUtils.findAll(jpql, paramLs);
        	if (list.size() > 0){
            	bean.getCell(7).setCellValue(list.get(0).getPieces().doubleValue());
        	}
        	if(HdUtils.strNotNull(vWorkCommandList.get(j-2).getQueueId())){
        		WorkCommand workCommand = JpaUtils.findById(WorkCommand.class, vWorkCommandList.get(j-2).getQueueId());
        		if (workCommand != null){
        			if(workCommand.getPortCarNo() != null){
        				PortCar portCar = JpaUtils.findById(PortCar.class, workCommand.getPortCarNo());
        				if (portCar != null){
        					if ("1".equals(portCar.getIsTiComplete())){
            					bean.getCell(8).setCellValue("齐");
            				}else{
            					bean.getCell(8).setCellValue("");
            				}
        				}
        				 
        			}
        		}
        	}else {
        		bean.getCell(8).setCellValue("");
        	}
        	if (HdUtils.strNotNull(vWorkCommandList.get(j-2).getCyAreaNo())){
        		CCyArea cCyArea = JpaUtils.findById(CCyArea.class, vWorkCommandList.get(j-2).getCyAreaNo());
        		if (cCyArea != null){
        			if (HdUtils.strNotNull(vWorkCommandList.get(j-2).getCyRemarks())){
        				bean.getCell(9).setCellValue(cCyArea.getCyAreaNam()+vWorkCommandList.get(j-2).getCyRemarks());
            		} else {
            			bean.getCell(9).setCellValue(cCyArea.getCyAreaNam());
            		}
        		}
        	}
        	
        }
        //合并船名
        CellRangeAddress cShipNam = new CellRangeAddress(2, vWorkCommandList.size()+1, 0, 0);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(cShipNam);
		
		
		try {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode("外贸集港明细"+CommonUtil.getId(), "UTF-8") + ".xls"); 
			ServletOutputStream arg35 = response.getOutputStream();
			ex.write(arg35);
			arg35.flush();
			arg35.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportZxzy(HdEzuiDatagridData data,HttpServletResponse response){
		List<VWorkCommand> vWorkCommandList = data.getRows();
		HSSFWorkbook ex = new HSSFWorkbook();
		HSSFCellStyle style = ex.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Sheet sheet1 = ex.createSheet();
        Row row = sheet1.createRow(0);
        Row row1 = sheet1.createRow(1);
        Row row2 = sheet1.createRow(2);
        for (int i=0;i<9;i++) {
			Cell cell = row.createCell(i);
			Cell cell1 = row1.createCell(i);
			Cell cell2 = row2.createCell(i);
        }
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 8);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region1);
		CellRangeAddress region2 = new CellRangeAddress(1, 1, 0, 2);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region2);
		CellRangeAddress region3 = new CellRangeAddress(1, 1, 3, 5);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region3);
		CellRangeAddress region4 = new CellRangeAddress(1, 1, 6, 8);// 起始行号，终止行号， 起始列号，终止列号
		 //在sheet里增加合并单元格  
		sheet1.addMergedRegion(region4);
		row.getCell(0).setCellValue("装卸作业情况说明");
		row1.getCell(0).setCellValue("船名："+vWorkCommandList.get(0).getShipNam());
		String voyage = vWorkCommandList.get(0).getVoyage();
		if ("卸船".equals(vWorkCommandList.get(0).getWorkTypNam())){
			row1.getCell(3).setCellValue("航次："+voyage.substring(0, voyage.indexOf("/")));
		} else if ("装船".equals(vWorkCommandList.get(0).getWorkTyp())){
			row1.getCell(3).setCellValue("航次："+voyage.substring(voyage.indexOf("/")));
		}
		
		row1.getCell(6).setCellValue("装/卸日期："+vWorkCommandList.get(0).getInCyTim().toString().substring(0, 10));
        row2.getCell(0).setCellValue("作业项目");
        row2.getCell(1).setCellValue("提单号/装货单号");
        row2.getCell(2).setCellValue("货名");
        row2.getCell(3).setCellValue("数量");
        row2.getCell(4).setCellValue("使用港方动力");
        row2.getCell(5).setCellValue("使用港方人力");
        row2.getCell(6).setCellValue("夜班作业");
        row2.getCell(7).setCellValue("节假日作业");
        row2.getCell(8).setCellValue("备注");
        
        for(int j=0;j<vWorkCommandList.size();j++){
        	Row bean = sheet1.createRow(j+3);
        	for (int k=0;k<9;k++) {
    			Cell cell = bean.createCell(k);
            }
        	bean.getCell(0).setCellValue(vWorkCommandList.get(j).getWorkTypNam());
        	bean.getCell(1).setCellValue(vWorkCommandList.get(j).getBillNo());
        	bean.getCell(2).setCellValue(vWorkCommandList.get(j).getCarTypNam());
    		bean.getCell(3).setCellValue(vWorkCommandList.get(j).getRksl());
    		if ("1".equals(vWorkCommandList.get(j).getUseMachId()))
        	bean.getCell(4).setCellValue("√");
    		if ("1".equals(vWorkCommandList.get(j).getUseWorkerId()))
        	bean.getCell(5).setCellValue("√");
    		if ("1".equals(vWorkCommandList.get(j).getNightId()))
        	bean.getCell(6).setCellValue("√");
    		if ("1".equals(vWorkCommandList.get(j).getHolidayId()))
        	bean.getCell(7).setCellValue("√");
        	//备注要加判断
        	if ("1".equals(vWorkCommandList.get(j).getUseMachId())){
        		bean.getCell(8).setCellValue("马菲 拖头");
        	} else {
        		bean.getCell(8).setCellValue("滚卸");
        	}
        	
        }
        OutputStream output;
		try {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode("外贸装卸理货", "UTF-8") + ".xls"); 
			ServletOutputStream arg35 = response.getOutputStream();
			ex.write(arg35);
			arg35.flush();
			arg35.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public HdEzuiDatagridData findCyAreaUpdate(HdQuery hdQuery) {
		List<WorkCommand> allList = new ArrayList();
		String shipNo = hdQuery.getStr("shipNo");
		if (HdUtils.strIsNull(shipNo)) {
			throw new HdRunTimeException("船舶信息为空！");
		}
		String jpql = "select a.brandCod brd, count(a.queueId) cnt from WorkCommand a, PortCar b where a.workTyp =:workTyp and a.shipNo =:shipNo and a.portCarNo = b.portCarNo and b.cyAreaNo is null and b.brandCod is not null group by a.brandCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("workTyp", "SI");
		paramLs.addParam("shipNo", shipNo);
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		String jpql1 = "select a from WorkCommand a where a.workTyp =:workTyp and a.shipNo =:shipNo and a.brandCod =:brandCod";
		QueryParamLs paramLs1 = new QueryParamLs();
		paramLs1.addParam("workTyp", "SI");
		paramLs1.addParam("shipNo", shipNo);
		for (Object[] obj : objList) {
			paramLs1.addParam("brandCod", obj[0]);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql1, paramLs1);
			if (workCommandList.size() > 0) {
				WorkCommand data = workCommandList.get(0);
				data.setRksl(obj[1].toString());
				JpaUtils.update(data);
				allList.add(data);
			}
		}
		for (WorkCommand bc : allList) {
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
				if (ship != null) {
					bc.setShipNam(ship.getcShipNam());
					bc.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(allList);
		return result;
	}

	@Override
	public HdEzuiDatagridData findDclh(HdQuery hdQuery) {
		String jpql = "select a from WorkCommand a where 1=1 and (a.remarks = '手持录入' or a.qzId = '1')";
		String shipNo = hdQuery.getStr("shipNo");
		String type = hdQuery.getStr("type");
		String vinNo = hdQuery.getStr("vinNo");
		String qzId = hdQuery.getStr("qzId");
		String contractNo = hdQuery.getStr("contractNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(qzId)) {
			jpql += "and a.qzId=:qzId ";
			paramLs.addParam("qzId", qzId);
		}
		if (HdUtils.strNotNull(vinNo)) {
			jpql += "and a.vinNo like :vinNo ";
			paramLs.addParam("vinNo", "%" + vinNo + "%");
		}
		if (HdUtils.strNotNull(contractNo)) {
			jpql += "and a.contractNo=:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		if ("XC".equals(type) || "NMPLXC".equals(type) || "WMPLXC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SI");
		} else if ("ZC".equals(type) || "NMPLZC".equals(type) || "WMPLZC".equals(type) || "CZZC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SO");
		} else if ("PLTZ".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TZ");
		} else if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TI");
		}
		jpql += "order by a.inCyTim desc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkCommand> workCommandListAll = result.getRows();
		for (WorkCommand bc : workCommandListAll) {
			
			if (HdUtils.strNotNull(bc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if (ccartyp != null) {
					bc.setCarTypNam(ccartyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				if (carkind != null) {
					bc.setCarKindNam(carkind.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
				if (ship != null) {
					bc.setShipNam(ship.getcShipNam());
					bc.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			if (HdUtils.strNotNull(bc.getInCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getInCyNam());
				if (cEmployee != null) {
					bc.setInCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(bc.getOutCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getOutCyNam());
				if (cEmployee != null) {
					bc.setOutCyNamNam(cEmployee.getEmpNam());
				}
			}
			if(CommonUtil.strIsNull(bc.getInCyNam())){
				
			}else{
				bc.setOutCyNamNam(bc.getInCyNamNam());
				bc.setOutCyTim(bc.getInCyTim());
			}  
			if (bc.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bc.getPortCarNo());
				if (portCar != null) {
					if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
						CCyArea cCyArea = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
						if (cCyArea != null) {
							bc.setCyArea(cCyArea.getCyAreaNam());
						}
						
					}
					if(HdUtils.strNotNull(portCar.getTranPortCod()) || HdUtils.strNotNull(portCar.getLoadPortCod())){//若卸货港代码么有则换为装货港。
						String portCodTemp = "";
						if(HdUtils.strNotNull(portCar.getTranPortCod())){
							portCodTemp = portCar.getTranPortCod();
						}else{
							portCodTemp = portCar.getLoadPortCod();
						}
						String sql = "select a from CPort a where a.portShort =:portShort";
						QueryParamLs param = new QueryParamLs();
						param.addParam("portShort", portCodTemp);
						List<CPort> cPortList = JpaUtils.findAll(sql, param);
						if (cPortList.size() > 0) {
							bc.setTranPortCodNam(cPortList.get(0).getcPortNam());
						}
					}
				}
			}
		}
		return result;
	}
	
	
	@Override
	public HdEzuiDatagridData findFcd(HdQuery hdQuery) {
		String jpql = "select a from WorkCommand a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String carTyp = hdQuery.getStr("carTyp");
		String iEId = hdQuery.getStr("iEId");
		String type = hdQuery.getStr("type");
		String vinNo = hdQuery.getStr("vinNo");
		String qzId = hdQuery.getStr("qzId");
		String contractNo = hdQuery.getStr("contractNo");
		//String consignCod=hdQuery.getStr("consignCod");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(qzId)) {
			jpql += "and a.qzId=:qzId ";
			paramLs.addParam("qzId", qzId);
		}
		if (HdUtils.strNotNull(iEId)) {
			if ("I".equals(iEId)){
				jpql += "and a.workTyp=:workTyp ";
				paramLs.addParam("workTyp", "SI");
			} else if ("E".equals(iEId)){
				jpql += "and a.workTyp=:workTyp ";
				paramLs.addParam("workTyp", "TI");
			}
		} else {
			jpql += "and a.workTyp in ('SI','TI') ";
		}
		if (HdUtils.strNotNull(billNo)) {
			jpql += "and a.billNo=:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jpql += "and a.carTyp=:carTyp ";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(vinNo)) {
			jpql += "and a.vinNo like :vinNo ";
			paramLs.addParam("vinNo", "%" + vinNo + "%");
		}
		if (HdUtils.strNotNull(contractNo)) {
			jpql += "and a.contractNo=:contractNo ";
			paramLs.addParam("contractNo", contractNo);
		}
		/*if (HdUtils.strNotNull(consignCod)) {
			jpql += "and a.consignCod=:consignCod ";
			paramLs.addParam("consignCod", consignCod);
		}*/
		if ("XC".equals(type) || "NMPLXC".equals(type) || "WMPLXC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SI");
		} else if ("ZC".equals(type) || "NMPLZC".equals(type) || "WMPLZC".equals(type) || "CZZC".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "SO");
		} else if ("PLTZ".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TZ");
		} else if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			jpql += "and a.workTyp =:workTyp ";
			paramLs.addParam("workTyp", "TI");
		}
		jpql += "order by a.inCyTim desc,a.widthOverId asc";
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<WorkCommand> workCommandListAll = result.getRows();
		for (WorkCommand bc : workCommandListAll) {
			if (HdUtils.strNotNull(bc.getCarTyp())) {
				CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
				if (ccartyp != null) {
					bc.setCarTypNam(ccartyp.getCarTypNam());
				}
			}
			if (HdUtils.strNotNull(bc.getCarKind())) {
				CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
				if (carkind != null) {
					bc.setCarKindNam(carkind.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(bc.getBrandCod())) {
				CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
				if (cbrand != null) {
					bc.setBrandNam(cbrand.getBrandNam());
				}
			}
			if (HdUtils.strNotNull(bc.getShipNo())) {
				Ship ship = JpaUtils.findById(Ship.class, bc.getShipNo());
				if (ship != null) {
					bc.setShipNam(ship.getcShipNam());
					bc.setVoyage(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			if (HdUtils.strNotNull(bc.getInCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getInCyNam());
				if (cEmployee != null) {
					bc.setInCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (HdUtils.strNotNull(bc.getOutCyNam())) {
				CEmployee cEmployee = JpaUtils.findById(CEmployee.class, bc.getOutCyNam());
				if (cEmployee != null) {
					bc.setOutCyNamNam(cEmployee.getEmpNam());
				}
			}
			if (bc.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bc.getPortCarNo());
				if (portCar != null) {
					if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
						CCyArea cCyArea = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
						if (cCyArea != null) {
							bc.setCyArea(cCyArea.getCyAreaNam());
						}
						
					}
					if(HdUtils.strNotNull(portCar.getTranPortCod())){
						String sql = "select a from CPort a where a.portShort =:portShort";
						QueryParamLs param = new QueryParamLs();
						param.addParam("portShort", portCar.getTranPortCod());
						List<CPort> cPortList = JpaUtils.findAll(sql, param);
						if (cPortList.size() > 0) {
							bc.setTranPortCodNam(cPortList.get(0).getcPortNam());
						}
					}
				}
			}
		}
		return result;
	}

	@Transactional
	public WorkCommand findOne(String queueId) {
		WorkCommand bean = new WorkCommand();
		if (HdUtils.strNotNull(queueId)) {
			bean = JpaUtils.findById(WorkCommand.class, queueId);
			if (bean.getPortCarNo() != null) {
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				if (portCar != null) {
					bean.setCyArea(portCar.getCyAreaNo());
					if (HdUtils.strNotNull(portCar.getTranPortCod())) {
						String sql = "select a from CPort a where a.portShort =:portShort";
						QueryParamLs param = new QueryParamLs();
						param.addParam("portShort", portCar.getTranPortCod());
						List<CPort> cPortList = JpaUtils.findAll(sql, param);
						if (cPortList.size() > 0) {
							String portNam = cPortList.get(0).getcPortNam();
							String jpql = "select a from SysCode a where a.fieldCod =:fieldCod and a.name =:name";
							QueryParamLs paramLs = new QueryParamLs();
							paramLs.addParam("fieldCod", "FLOW_AREA");
							paramLs.addParam("name", portNam);
							List<SysCode> list = JpaUtils.findAll(jpql, paramLs);
							if (list.size() > 0) {
								bean.setTranPortCodNam(list.get(0).getCode());
							}
						}
					}
				}

			}
		}
		return bean;
	}

	// 更新单车理货数据
	@Transactional
	public HdMessageCode updateLhData(CargoInfo data, String type) {
		if (HdUtils.strIsNull(data.getQueueId())) {
			throw new HdRunTimeException("信息有误，请刷新！");
		}
		WorkCommand bean = JpaUtils.findById(WorkCommand.class, data.getQueueId());
		if (HdUtils.strNotNull(data.getCarTyp())) {
			bean.setCarTyp(data.getCarTyp());
		}
		if (HdUtils.strNotNull(data.getCarKind())) {
			bean.setCarKind(data.getCarKind());
		}
		if (HdUtils.strNotNull(data.getBrandCod())) {
			bean.setBrandCod(data.getBrandCod());
		}
		if (HdUtils.strNotNull(data.getBillNo())) {
			bean.setBillNo(data.getBillNo());
		}
		if (HdUtils.strNotNull(data.getVinNo())) {
			bean.setVinNo(data.getVinNo());
		}
		if (data.getInCyTim() != null) {
			bean.setInCyTim(data.getInCyTim());
		}
		bean.setUseMachId(data.getUseMachId());
		if ("1".equals(data.getLengthOverId())) {
			bean.setLengthOverId(data.getLengthOverId());
			bean.setLength(data.getLength());
		}
		if (HdUtils.strNotNull(data.getVinNo())) {
			bean.setVinNo(data.getVinNo());
		}
		if (HdUtils.strNotNull(data.getUseWorkerId())) {
			bean.setUseWorkerId(data.getUseWorkerId());
		}
		if (HdUtils.strNotNull(data.getNightId())) {
			bean.setNightId(data.getNightId());
		}
		if (HdUtils.strNotNull(data.getHolidayId())) {
			bean.setHolidayId(data.getHolidayId());
		}
		JpaUtils.update(bean);
		PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
		portCar.setCarTyp(bean.getCarTyp());
		portCar.setCarKind(bean.getCarKind());
		portCar.setBrandCod(bean.getBrandCod());
		portCar.setVinNo(bean.getVinNo());
		portCar.setBillNo(bean.getBillNo());
		portCar.setInCyTim(bean.getInCyTim());
		portCar.setCyAreaNo(data.getCyAreaNo());
		String flowNam = HdUtils.getSysCodeName("FLOW_AREA", data.getFlow());
		if (HdUtils.strNotNull(flowNam)) {
			String sql = "select a from CPort a where a.cPortNam =:cPortNam";
			QueryParamLs queryCport = new QueryParamLs();
			queryCport.addParam("cPortNam", flowNam);
			List<CPort> cPortList = JpaUtils.findAll(sql, queryCport);
			if (cPortList.size() > 0) {
				portCar.setTranPortCod(cPortList.get(0).getPortCod());
			}
		}
		JpaUtils.update(portCar);
		if ("NMPLJG".equals(type) || "WMPLJG".equals(type)) {
			String jpql = "select a from TruckWork a where a.portCarNo =:portCarNo and a.contractNo =:contractNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("portCarNo", bean.getPortCarNo());
			paramLs.addParam("contractNo", bean.getContractNo());
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql, paramLs);
			if (truckWorkList.size() > 0) {
				TruckWork truckWork = truckWorkList.get(0);
				truckWork.setCarTyp(bean.getCarTyp());
				truckWork.setCarKind(bean.getCarKind());
				truckWork.setBrandCod(bean.getBrandCod());
				truckWork.setVinNo(bean.getVinNo());
				truckWork.setBillNo(bean.getBillNo());
				truckWork.setTranPortCod(portCar.getTranPortCod());
				JpaUtils.update(truckWork);
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	public void saveZpzclh(CargoInfo cargoInfo, String type, String billNos) {
		List<String> billNoList = HdUtils.paraseStrs(billNos);
		String portCarNos = "";
		for (String billNo : billNoList) {
			String jpql = "select a from PortCar a where a.currentStat = '2' and a.billNo ='" + billNo + "'";
			QueryParamLs paramLs = new QueryParamLs();
			List<PortCar> portCarList = JpaUtils.findAll(jpql, paramLs);
			for (PortCar bean : portCarList) {
				portCarNos += bean.getPortCarNo() + ",";
			}
		}
		if (HdUtils.strNotNull(portCarNos)) {
			saveZclh(cargoInfo, type, portCarNos);
		}
	}
	
	// 集港收车查询
	@Override
	public HdEzuiDatagridData findJgcsWm(HdQuery hdQuery) {
		List<VWorkCommand> allList = new ArrayList();
		List<Object[]> objList = new ArrayList();
		String shipNo = hdQuery.getStr("shipNo");
		String sql=" SELECT max(m.QUEUE_ID) QUEUE_ID,m.bill_No, " +
				"         m.car_Typ, " +
				"         m.consign_cod, " +
				"         max( m.C_SHIP_NAM) C_SHIP_NAM, "+
				"         MAX (m.car_Typ_Nam) car_typ_nam, " +
				"         CAST (COUNT (1) AS VARCHAR (12)) rksl, " +
				"         m.cy_remarks, " +
				"         m.contact_info," +
				"         m.cy_area_no" +
				"    FROM V_Work_Command m" +
				"   WHERE m.work_Typ='TI' " ;
		if (HdUtils.strNotNull(shipNo)) {
			sql += "and m.ship_No='"+shipNo+"' ";
		}
		sql+=" GROUP BY m.bill_No, " +
		"         m.car_Typ,m.consign_cod,m.contact_info,m.cy_area_no,m.cy_remarks " +
		"ORDER BY m.consign_cod ASC,m.bill_no asc";
		Query query=JpaUtils.getEntityManager().createNativeQuery(sql,VWorkCommand.class);
		List<VWorkCommand> workCommandList = query.getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(workCommandList);
		return result;
	}

	// 集港收车查询
	@Override
	public HdEzuiDatagridData findJgcs(HdQuery hdQuery,String Type) {
		String shipNo = hdQuery.getStr("shipNo");
		String brandCod = hdQuery.getStr("brandCod");
		String carKind = hdQuery.getStr("carKind");
		String carTyp = hdQuery.getStr("carTyp");
		String cyAreaNo = hdQuery.getStr("cyAreaNo");
		String inCyTim = hdQuery.getStr("inCyTim");
		String consignCod = hdQuery.getStr("consignCod");
		String portCarSql = "SELECT A.CAR_TYP,\r\n" + "A.BILL_NO,\r\n" + "COUNT(A.PORT_CAR_NO),\r\n"
				+ "       CAST(trunc(A.IN_CY_TIM, 'dd') AS date),\r\n" +"A.CY_AREA_NO,\r\n" + "A.CURRENT_STAT\r\n" + "  FROM PORT_CAR A, WORK_COMMAND B\r\n"
				+ " WHERE A.IN_CY_TIM IS NOT NULL AND B.WORK_TYP = 'TI' AND A.PORT_CAR_NO = B.PORT_CAR_NO\r\n";
		if ("PL".equals(Type)){
			portCarSql += " AND B.REMARKS IS NULL\r\n";
		} else if ("DC".equals(Type)){
			portCarSql += " AND B.REMARKS = '手持录入'\r\n";
		}
		if (HdUtils.strNotNull(shipNo)) {
			portCarSql += "   AND A.SHIP_NO = '" + shipNo + "'\r\n";
		} else {
			portCarSql += "   AND A.SHIP_NO = '不选船不展示'\r\n";
		}
		if (HdUtils.strNotNull(brandCod)) {
			portCarSql += "   AND A.BRAND_COD = '" + brandCod + "'\r\n";
		}
		if (HdUtils.strNotNull(inCyTim)) {
			portCarSql += "   AND to_char(A.IN_CY_TIM, 'yyyy-MM-dd') ='" + inCyTim +"'";
		}
		if (HdUtils.strNotNull(carKind)) {
			portCarSql += "   AND A.CAR_KIND = '" + carKind + "'\r\n";
		}
		if (HdUtils.strNotNull(carTyp)) {
			portCarSql += "   AND A.CAR_TYP = '" + carTyp + "'\r\n";
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			portCarSql += "   AND A.CY_AREA_NO = '" + cyAreaNo + "'\r\n";
		}
		if (HdUtils.strNotNull(consignCod)) {
			portCarSql += "   AND A.CONSIGN_COD = '" + consignCod + "'\r\n";
		}
		portCarSql += " GROUP BY A.BILL_NO, A.CAR_TYP,A.CY_AREA_NO,A.CURRENT_STAT, CAST(trunc(A.IN_CY_TIM, 'dd') AS date)\r\n"
				+ " ORDER BY A.BILL_NO ASC, CAST(trunc(A.IN_CY_TIM, 'dd') AS date) DESC";
		List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(portCarSql).getResultList();
		List<PortCar> allList = new ArrayList();
		for (Object[] obj : objList) {
			String jqpl1 = "select a from PortCar a where 1=1";
			QueryParamLs paramLs1 = new QueryParamLs();
			if (obj[1] != null) {
				jqpl1 += " and a.billNo =:billNo";
				paramLs1.addParam("billNo", obj[1]);
			}
			if (obj[0] != null) {
				jqpl1 += " and a.carTyp =:carTyp";
				paramLs1.addParam("carTyp", obj[0]);
			}
			if (obj[3] != null) {
				jqpl1 += " and a.inCyTim >=:begTim and a.inCyTim <:endTim";
				paramLs1.addParam("begTim", HdUtils.strToDate(String.valueOf(obj[3])));
				paramLs1.addParam("endTim", HdUtils.addDay(HdUtils.strToDate(String.valueOf(obj[3])), 1));
			}
			if (obj[4] != null) {
				jqpl1 += " and a.cyAreaNo =:cyAreaNo";
				paramLs1.addParam("cyAreaNo", obj[4]);
			}
			if (obj[5] != null) {
				jqpl1 += " and a.currentStat =:currentStat";
				paramLs1.addParam("currentStat", obj[5]);
			}
			List<PortCar> portCarList = JpaUtils.findAll(jqpl1, paramLs1);
			if (portCarList.size() > 0) {
				PortCar portCar = portCarList.get(0);
				portCar.setRksl(String.valueOf(obj[2]));
				// portCar.setInCyTim(Timestamp.valueOf(String.valueOf(obj[3])));
				if (HdUtils.strNotNull(portCar.getBrandCod())) {
					CBrand cbrand = JpaUtils.findById(CBrand.class, portCar.getBrandCod());
					if (cbrand != null) {
						portCar.setBrandNam(cbrand.getBrandNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getCarKind())) {
					CCarKind carkind = JpaUtils.findById(CCarKind.class, portCar.getCarKind());
					if (carkind != null) {
						portCar.setCarKindNam(carkind.getCarKindNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getCarTyp())) {
					CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, portCar.getCarTyp());
					if (ccartyp != null) {
						portCar.setCarTypNam(ccartyp.getCarTypNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, portCar.getShipNo());
					if (ship != null) {
						portCar.setcShipNam(ship.getcShipNam());
						portCar.setVoyage(ship.getEvoyage());
					}
				}
				if (HdUtils.strNotNull(portCar.getConsignCod())) {
					CClientCod cClientCod = JpaUtils.findById(CClientCod.class, portCar.getConsignCod());
					if (cClientCod != null) {
						portCar.setConsignNam(cClientCod.getcClientNam());
					}
				}
				if (HdUtils.strNotNull(portCar.getCyAreaNo())) {
					CCyArea bean = JpaUtils.findById(CCyArea.class, portCar.getCyAreaNo());
					if (bean != null){
						portCar.setCyArea(bean.getCyAreaNam());
					}
				}
				allList.add(portCar);
			}
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(allList);
		return result;
	}

	// 装卸作业情况查询
	@Override
	public HdEzuiDatagridData findZxzy(HdQuery hdQuery) {
		List<VWorkCommand> allList = new ArrayList();
		List<Object[]> objList = new ArrayList();
		String workTyp = hdQuery.getStr("workTyp");
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		String brandCod = hdQuery.getStr("brandCod");
		String carKind = hdQuery.getStr("carKind");
		String carTyp = hdQuery.getStr("carTyp");
		String inCyTim = hdQuery.getStr("inCyTim");
		String contractNo=hdQuery.getStr("contractNo");
		
		String workDteStart = hdQuery.getStr("workDteStart");//日期开始 
		String workDteEnd= hdQuery.getStr("workDteEnd"); //日期结束
		String storeDaysStart= hdQuery.getStr("storeDaysStart");//堆存天数开始
		String storeDaysEnd= hdQuery.getStr("storeDaysEnd");//堆存天数结束
		
		
		String sql=" SELECT max(m.QUEUE_ID) QUEUE_ID,m.bill_No, " +
				"         m.car_Typ, " +
				"         m.holiday_Id, " +
				"         m.night_Id, " +
				"         m.ship_No, " +
				"         m.use_Mach_Id, " +
				"         m.use_Worker_Id, " +
				"         m.direct_Id,"+
				"		  max(M.REMARKS) REMARKS,"+
				"         (CASE WHEN max(m.work_Typ) in ('SI','TI') THEN trunc(m.in_cy_tim,'dd') ELSE trunc(m.out_cy_tim,'dd') END) in_cy_tim,"+	
				"         max( m.C_SHIP_NAM) C_SHIP_NAM, "+
			    "     	  max( m.VOYAGE )VOYAGE, "+
			    "     	  max( m.work_typ_nam) work_typ_nam, "+
				"         MAX (m.car_Typ_Nam) car_typ_nam, " +
				"         MAX (m.car_Kind_Nam) car_Kind_Nam, " +
				"         MAX (m.brand_Nam) brand_Nam, " +
				"         MAX (m.TRAN_PORT_NAM) TRAN_PORT_NAM, " +
				"         count(m.port_car_no) rksl" +
				"    FROM V_Work_Command m " +
				"   WHERE    1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNo)) {
			if("TI".equals(workTyp)||"TO".equals(workTyp)){//类型是集港或疏港且委托号不为空时，不根据船号进行查询
			   if(contractNo!=null&&!"".equals(contractNo)){
				   sql +=" ";
			   }
			}else{
				sql += "and m.ship_No='"+shipNo+"' ";
			}
		}
        if (HdUtils.strNotNull(workTyp)) {
			sql += "and m.work_Typ='"+workTyp+"' ";
		}
		if(HdUtils.strNotNull(billNo)){
			sql += "   AND m.bill_No = '"+billNo+"'";
		}
		if (HdUtils.strNotNull(brandCod)) {
			sql += "   AND m.brand_Cod ='"+brandCod+"' ";
		}
		if (HdUtils.strNotNull(carKind)) {
			sql +="    AND m.car_Kind ='"+carKind+"' ";
		}
		if (HdUtils.strNotNull(carTyp)) {
			sql +="    AND m.car_Typ ='"+carTyp+"' ";
		}
		/*sql += "and TRUNC(m.IN_CY_TIM) -"+
			      " TRUNC(to_date(to_char(m.IN_CY_TIM - 1, 'yyyy-MM-dd') || ' 08:00',"+
			                    " 'yyyy-MM-dd HH:ss')) >= 0 "+
			   "and TRUNC(to_date(to_char(m.IN_CY_TIM, 'yyyy-MM-dd') || ' 08:00',"+
			                    " 'yyyy-MM-dd HH:ss')) - TRUNC(m.IN_CY_TIM) >= 0";*/
		String cytimCol="in_Cy_Tim";
		if ("SI".equals(workTyp)) {
			if(HdUtils.strNotNull(inCyTim)){
				sql +=" AND to_char(m.in_Cy_Tim,'yyyy-MM-dd') = '"+inCyTim+"' ";
			}
		} else if ("SO".equals(workTyp) || "TZ".equals(workTyp)) {
			if(HdUtils.strNotNull(inCyTim)){
				sql +=" AND to_char(m.out_Cy_Tim,'yyyy-MM-dd') = '"+inCyTim+"' ";
			}
			cytimCol="out_Cy_Tim";
		}else if("TI".equals(workTyp)||"TO".equals(workTyp)){
			    sql +="AND contract_no like '%"+contractNo+"%'";
		}
		if(HdUtils.strNotNull(workDteStart)){
			sql += " and to_char(m.in_cy_tim,'yyyy-MM-dd') >= '"+workDteStart+"' ";
		}
		
		if(HdUtils.strNotNull(workDteEnd)){
			sql += " and to_char(m.in_cy_tim,'yyyy-MM-dd') <= '"+workDteEnd+"' ";
		}
		
		
		sql+=" GROUP BY m.bill_No, " +
		"         m.car_Typ, " +
		"         trunc(m.in_cy_tim,'dd'), " +
		"         trunc(m.out_cy_tim,'dd')," +
		"         m.use_Mach_Id, " +
		"         m.use_Worker_Id, " +
		"         m.night_Id, " +
		"         m.holiday_Id, " +
		"         m.ship_No, " +
		"         m.direct_Id "+
		"ORDER BY trunc(m.in_cy_tim,'dd'),car_typ ";
		/*Query query=JpaUtils.getEntityManager().createNativeQuery(sql,VWorkCommand.class);
		List<VWorkCommand> workCommandList = query.getResultList();
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		result.setRows(workCommandList);
		result.setTotal(workCommandList.size());
		return result;*/
		Query query=JpaUtils.getEntityManager().createNativeQuery(sql,VWorkCommand.class);
		List<VWorkCommand> workCommandList = query.getResultList();
		Integer sumCarNum=0;
		for(VWorkCommand workCommand:workCommandList){
			//sumCarNum+=workCommand.get
		}
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List listRows = new ArrayList<>();
		if(hdQuery.getPage()!=null){
			int curPage=hdQuery.getPage();
	    	int pageSize=hdQuery.getRows();
			int max = workCommandList.size();
			for (int i = (curPage-1)*pageSize ; i < (curPage*pageSize) ; i++) {
				if(i<max){
					listRows.add(workCommandList.get(i));
				}else{
					break;
				}
			}
		}
		if(hdQuery.getPage()!=null){
			result.setRows(listRows);
		}else{
			result.setRows(workCommandList);
		}
		result.setTotal(workCommandList.size());
		return result;
	}

	public List findWorking(String shipNo) {
		List l = new ArrayList(10);
		for (int i = 0; i < 10; i++) {
			l.add(i, 0);
		}
		String workTyp = "";
		String jpql8 = "select a from WorkCommand a where a.shipNo=:shipNo";
		QueryParamLs paramLs8 = new QueryParamLs();
		paramLs8.addParam("shipNo", shipNo);
		List<WorkCommand> wcList = JpaUtils.findAll(jpql8, paramLs8);
		if (wcList.size() > 0) {
			for (WorkCommand wc : wcList) {
				workTyp = wc.getWorkTyp();
			}
		}
		BigDecimal total = new BigDecimal(0);
		BigDecimal xctotoal = new BigDecimal(0);
		int cbzy = 0;
		int dczy = 0;
		int xczy = 0;
		int xccbzy = 0;
		float loadRate;
		float unloadRate;
		if (workTyp.equals("SO")) {
			// 装船作业监控
			String jpql = "select a from ShipLoadPlan a where 1=1 ";
			QueryParamLs paramLs = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql += "and a.shipNo=:shipNo and a.iEId='E' ";
				paramLs.addParam("shipNo", shipNo);
			}
			List<ShipLoadPlan> shipLoadPlanList = JpaUtils.findAll(jpql, paramLs);
			if (shipLoadPlanList.size() > 0) {
				for (ShipLoadPlan s : shipLoadPlanList) {
					total = total.add(s.getCarNum());
				}
			}
			l.add(0, total);

			String jpql2 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs2 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql2 += "and a.shipNo=:shipNo and a.workTyp='SO' and a.shipWorkTim is not null";
				paramLs2.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql2, paramLs2);
			if (workCommandList.size() > 0) {
				cbzy = workCommandList.size();
			}
			l.add(1, cbzy);

			String jpql3 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs3 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql3 += "and a.shipNo=:shipNo and a.workTyp='SO' and a.outCyTim is not null";
				paramLs3.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList2 = JpaUtils.findAll(jpql3, paramLs3);
			if (workCommandList2.size() > 0) {
				dczy = workCommandList2.size();
			}
			l.add(2, dczy);
			BigDecimal dczy2 = new BigDecimal(dczy);
			total.subtract(dczy2);
			l.add(3, total.subtract(dczy2));
			// 取WorkCommand中最大最小时间
			String jpql33 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs33 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql33 += "and a.shipNo=:shipNo and a.workTyp='SO' and a.outCyTim is not null";
				paramLs33.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList33 = JpaUtils.findAll(jpql33, paramLs33);
			List timeList = new ArrayList();
			if (workCommandList33.size() > 0) {
				for (int i = 0; i < workCommandList33.size(); i++) {
					timeList.add(i, workCommandList33.get(i).getShipWorkTim());
				}
			}
			Collections.sort(timeList);
			Timestamp maxDte = (Timestamp) timeList.get(0);
			Timestamp minDte = (Timestamp) timeList.get(timeList.size() - 1);
			long max = maxDte.getTime();
			long min = minDte.getTime();
			loadRate = cbzy / (max - min);
			l.add(4, loadRate);
		}
		// 卸船作业监控
		if (workTyp.equals("SI")) {
			String jpql4 = "select a from ShipBill a where 1=1 ";
			QueryParamLs paramLs4 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql4 += "and a.shipNo=:shipNo and a.iEId='I'";
				paramLs4.addParam("shipNo", shipNo);
			}
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql4, paramLs4);
			if (shipBillList.size() > 0) {
				for (ShipBill s : shipBillList) {
					xctotoal = xctotoal.add(s.getCarNum());
				}
			}
			l.add(5, xctotoal);

			String jpql5 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs5 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql5 += "and a.shipNo=:shipNo and a.workTyp='SI' and a.shipWorkTim is not null";
				paramLs5.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList3 = JpaUtils.findAll(jpql5, paramLs5);
			if (workCommandList3.size() > 0) {
				xczy = workCommandList3.size();
			}
			l.add(6, xczy);

			String jpql6 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs6 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql6 += "and a.shipNo=:shipNo and a.workTyp='SI' and a.inCyTim is not null";
				paramLs6.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList4 = JpaUtils.findAll(jpql6, paramLs6);
			if (workCommandList4.size() > 0) {
				xccbzy = workCommandList4.size();
			}
			l.add(7, xccbzy);
			BigDecimal xczy2 = new BigDecimal(xczy);
			xctotoal.subtract(xczy2);
			l.add(8, xctotoal.subtract(xczy2));
			// 取WorkCommand中最大最小时间
			String jpql55 = "select a from WorkCommand a where 1=1 ";
			QueryParamLs paramLs55 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jpql55 += "and a.shipNo=:shipNo and a.workTyp='SI' and a.inCyTim is not null";
				paramLs55.addParam("shipNo", shipNo);
			}
			List<WorkCommand> workCommandList55 = JpaUtils.findAll(jpql55, paramLs55);
			List timeList2 = new ArrayList();
			if (workCommandList55.size() > 0) {
				for (int i = 0; i < workCommandList55.size(); i++) {
					timeList2.add(i, workCommandList55.get(i).getShipWorkTim());
				}
			}
			// 求卸船效率
			Collections.sort(timeList2);
			Timestamp maxDte2 = (Timestamp) timeList2.get(0);
			Timestamp minDte2 = (Timestamp) timeList2.get(timeList2.size() - 1);
			long max2 = maxDte2.getTime();
			long min2 = minDte2.getTime();
			if (min2 == max2) {
				unloadRate = 0;
			} else {
				unloadRate = xccbzy / (max2 - min2);
			}
			l.add(9, unloadRate);
		}
		return l;

	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<WorkCommand> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	public HdMessageCode updateCyArea(String cyArealist, String shipNo, String brandCod) {
		String list[] = cyArealist.split(",");
		String jpql = "select a from WorkCommand a,PortCar b where a.portCarNo = b.portCarNo and b.cyAreaNo is null and a.shipNo =:shipNo and a.workTyp = 'SI' and a.brandCod =:brandCod";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		paramLs.addParam("brandCod", brandCod);
		List<WorkCommand> workCommandlist = JpaUtils.findAll(jpql, paramLs);
		int cs1 = Integer.valueOf(list[1]);
		for (int i = 0; i < cs1; i++) {
			WorkCommand bean = workCommandlist.get(i);
			PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
			if (portCar == null) {
				throw new HdRunTimeException("在场车信息有误！");
			}
			portCar.setCyAreaNo(list[0]);
			JpaUtils.update(portCar);
		}
		int size = list.length;
		if (size > 3) {
			List<WorkCommand> workCommandlist1 = JpaUtils.findAll(jpql, paramLs);
			int cs2 = Integer.valueOf(list[3]);
			for (int i = 0; i < cs2; i++) {
				WorkCommand bean = workCommandlist1.get(i);
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				if (portCar == null) {
					throw new HdRunTimeException("在场车信息有误！");
				}
				portCar.setCyAreaNo(list[2]);
				JpaUtils.update(portCar);
			}
		}
		if (size > 5) {
			List<WorkCommand> workCommandlist1 = JpaUtils.findAll(jpql, paramLs);
			int cs3 = Integer.valueOf(list[5]);
			for (int i = 0; i < cs3; i++) {
				WorkCommand bean = workCommandlist1.get(i);
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				if (portCar == null) {
					throw new HdRunTimeException("在场车信息有误！");
				}
				portCar.setCyAreaNo(list[4]);
				JpaUtils.update(portCar);
			}
		}
		if (size > 7) {
			List<WorkCommand> workCommandlist1 = JpaUtils.findAll(jpql, paramLs);
			int cs4 = Integer.valueOf(list[7]);
			for (int i = 0; i < cs4; i++) {
				WorkCommand bean = workCommandlist1.get(i);
				PortCar portCar = JpaUtils.findById(PortCar.class, bean.getPortCarNo());
				if (portCar == null) {
					throw new HdRunTimeException("在场车信息有误！");
				}
				portCar.setCyAreaNo(list[6]);
				JpaUtils.update(portCar);
			}
		}

		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode saveone(@RequestBody WorkCommand workCommand, String type) {
		if ("XC".equals(type)) {
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", workCommand.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("保存失败");
			}
			if (HdUtils.strNotNull(workCommand.getBillNo())) {
				String jpql2 = "select a from BillCar a where a.billNo =:billNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("billNo", workCommand.getBillNo());
				List<BillCar> billCarList = JpaUtils.findAll(jpql2, paramLs2);
				if (billCarList.size() <= 0) {
					throw new HdRunTimeException("出错了！");
				}
				BillCar billCar = billCarList.get(0);
				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setPortCarNo(billCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				workCommand.setBrandCod(billCar.getBrandCod());
				workCommand.setCarTyp(billCar.getCarTyp());
				workCommand.setCarKind(billCar.getCarKind());
				workCommand.setLength(billCar.getLength());
				workCommand.setWidth(billCar.getWidth());
				workCommand.setInCyTim(HdUtils.getDateTime());
				workCommand.setFinishedId("0");
				workCommand.setNightId("0");
				workCommand.setHolidayId("0");
				workCommand.setShipWorkTim(HdUtils.getDateTime());
				workCommand.setQueueId(HdUtils.genUuid());
				JpaUtils.save(workCommand);

				PortCar portCar = JpaUtils.findById(PortCar.class, workCommand.getPortCarNo());
				portCar.setCurrentStat("2");
				portCar.setCyPlac(workCommand.getCyPlac());
				portCar.setInCyTim(HdUtils.getDateTime());
				portCar.setInToolNo(workCommand.getShipNo());

				JpaUtils.save(portCar);
			} else {
				Ship ship = JpaUtils.findById(Ship.class, workCommand.getShipNo());

				PortCar portCar = new PortCar();
				BeanUtils.copyProperties(ship, portCar);
				JpaUtils.save(portCar);

				BillCar billCar = new BillCar();
				BeanUtils.copyProperties(ship, billCar);
				billCar.setPortCarNo(portCar.getPortCarNo());
				JpaUtils.save(billCar);

				workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
				workCommand.setPortCarNo(portCar.getPortCarNo());
				workCommand.setWorkTyp("SI");
				JpaUtils.save(workCommand);

			}
		} else if ("ZC".equals(type)) {
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", workCommand.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("保存失败");
			}
			BillCar billCar = JpaUtils.findById(BillCar.class, workCommand.getBillNo());
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(billCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(billCar.getBrandCod());
			workCommand.setCarTyp(billCar.getCarTyp());
			workCommand.setCarKind(billCar.getCarKind());
			workCommand.setLength(billCar.getLength());
			workCommand.setWidth(billCar.getWidth());
			workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setFinishedId("0");
			workCommand.setNightId("0");
			workCommand.setHolidayId("0");
			JpaUtils.save(workCommand);

			PortCar portCar = JpaUtils.findById(PortCar.class, workCommand.getPortCarNo());
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(HdUtils.getDateTime());
			portCar.setOutPortNo(workCommand.getShipNo());
			JpaUtils.save(portCar);

		}

		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode saveZclh(CargoInfo cargoInfo, String type, String portCarNos) {
		List<String> portCarNoList = HdUtils.paraseStrs(portCarNos);
		for (String portCarNo : portCarNoList) {

			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String jpql = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", workQueueList.get(0).getShipNo());
			paramLs2.addParam("billNo", JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo)).getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs2);
			if (shipBillList.size() == 0) {
				throw new HdRunTimeException("请先完善该船出口舱单！");
			}
			PortCar portCar = JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo));
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setRemarks(cargoInfo.getRemarks());
			JpaUtils.update(portCar);

			WorkCommand workCommand = new WorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			// workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setShipWorkTim(cargoInfo.getOutCyTim());
			workCommand.setFinishedId("0");
			workCommand.setNightId(cargoInfo.getNightId());
			workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			workCommand.setRemarks(cargoInfo.getRemarks());
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setInCyNam(cargoInfo.getInCyNam());
			workCommand.setInCyTim(cargoInfo.getOutCyTim());
			JpaUtils.save(workCommand);

			BillCar billCar = new BillCar();
			BeanUtils.copyProperties(portCar, billCar);
			billCar.setBillcarId(HdUtils.genUuid());
			billCar.setShipbillId(shipBillList.get(0).getShipbillId());
			JpaUtils.save(billCar);
		}
		return HdUtils.genMsg();
	}

	// 批量装船理货
	@Override
	public HdMessageCode saveNmzclh(CargoInfo cargoInfo, String type) {
		String jqpl2 = "select a from PortCar a where a.currentStat = '2'";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = cargoInfo.getShipNo();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		String tranPortCod = cargoInfo.getTranPortCod();
		if (HdUtils.strNotNull(shipNo) && !"CZZC".equals(type)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", shipNo);
		} else if ("CZZC".equals(type)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", "20190311082013");
		}
		if (HdUtils.strNotNull(carTyp)) {
			jqpl2 += " and a.carTyp =:carTyp";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(billNo)) {
			jqpl2 += " and a.billNo =:billNo";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jqpl2 += " and a.cyAreaNo =:cyAreaNo";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		if (HdUtils.strNotNull(tranPortCod)) {
			jqpl2 += " and a.tranPortCod =:tranPortCod";
			paramLs.addParam("tranPortCod", tranPortCod);
		}
		if ("NMPLZC".equals(type) || "CZZC".equals(type)) {
			jqpl2 += " and a.iEId = 'E' and a.tradeId = '1'";
		} else if ("WMPLZC".equals(type)) {
			jqpl2 += " and a.iEId = 'E' and a.tradeId = '2'";
		}
		List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs);
		if (portCarList.size() <= 0) {
			throw new HdRunTimeException("场地车辆信息有误，请确认提单选择正确！");
		}
		for (int i = 0; i < cargoInfo.getRcsl().intValue(); i++) {
			PortCar portCar = portCarList.get(i);
			String portCarNo = portCar.getPortCarNo().toString();
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'SO' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String jpql = "select a from ShipBill a where a.shipNo =:shipNo and a.billNo =:billNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("shipNo", workQueueList.get(0).getShipNo());
			paramLs2.addParam("billNo", JpaUtils.findById(PortCar.class, new BigDecimal(portCarNo)).getBillNo());
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs2);
			if (shipBillList.size() == 0 && !"CZZC".equals(type) && !"XCPZ".equals(type)) {
				throw new HdRunTimeException("请先完善该船出口舱单！");
			}
			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setRemarks(cargoInfo.getRemarks());
			if ("CZZC".equals(type)) {
				portCar.setShipNo(cargoInfo.getShipNo());
			}
			if ("XCPZ".equals(type)) {
				portCar.setShipNo(cargoInfo.getNewShipNo());
			}
			JpaUtils.update(portCar);

			WorkCommand workCommand = new WorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("SO");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			if ("XCPZ".equals(type)){
				workCommand.setShipNo(cargoInfo.getNewShipNo());
			}
			// workCommand.setInCyTim(HdUtils.getDateTime());
			workCommand.setShipWorkTim(cargoInfo.getOutCyTim());
			workCommand.setFinishedId("0");
			workCommand.setNightId(cargoInfo.getNightId());
			workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			workCommand.setRemarks(cargoInfo.getRemarks());
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setOutCyTim(cargoInfo.getOutCyTim());
			if (portCar.getInCyTim() != null) {
				workCommand.setInCyTim(portCar.getInCyTim());
			}
			JpaUtils.save(workCommand);

			if (!"CZZC".equals(type) && !"XCPZ".equals(type)) {
				BillCar billCar = new BillCar();
				BeanUtils.copyProperties(portCar, billCar);
				billCar.setBillcarId(HdUtils.genUuid());
				billCar.setShipbillId(shipBillList.get(0).getShipbillId());
				JpaUtils.save(billCar);
			}
		}
		return HdUtils.genMsg();
	}

	// 批量转栈理货
	@Override
	@Transactional
	public HdMessageCode saveZzlh(CargoInfo cargoInfo, String type) {
		String jqpl2 = "select a from PortCar a where a.currentStat = '2' and a.iEId = 'I' and a.tradeId = '2'";
		QueryParamLs paramLs = new QueryParamLs();
		String shipNo = cargoInfo.getShipNo();
		String cyAreaNo = cargoInfo.getCyAreaNo();
		String billNo = cargoInfo.getBillNo();
		String carTyp = cargoInfo.getCarTyp();
		if (HdUtils.strNotNull(shipNo)) {
			jqpl2 += " and a.shipNo =:shipNo";
			paramLs.addParam("shipNo", shipNo);
		}
		if (HdUtils.strNotNull(carTyp)) {
			jqpl2 += " and a.carTyp =:carTyp";
			paramLs.addParam("carTyp", carTyp);
		}
		if (HdUtils.strNotNull(billNo)) {
			jqpl2 += " and a.billNo =:billNo";
			paramLs.addParam("billNo", billNo);
		}
		if (HdUtils.strNotNull(cyAreaNo)) {
			jqpl2 += " and a.cyAreaNo =:cyAreaNo";
			paramLs.addParam("cyAreaNo", cyAreaNo);
		}
		List<PortCar> portCarList = JpaUtils.findAll(jqpl2, paramLs);
		if (portCarList.size() <= 0) {
			throw new HdRunTimeException("场地车辆信息有误，请确认提单选择正确！");
		}
		for (int i = 0; i < cargoInfo.getRcsl().intValue(); i++) {
			PortCar portCar = portCarList.get(i);
			String jpql1 = "select a from WorkQueue a where a.workTyp = 'TZ' and a.shipNo =:shipNo";
			QueryParamLs paramLs1 = new QueryParamLs();
			paramLs1.addParam("shipNo", cargoInfo.getShipNo());
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql1, paramLs1);
			if (workQueueList.size() < 1) {
				throw new HdRunTimeException("该作业队列存在问题！");
			}
			String truckNo = workQueueList.get(0).getTruckNo();
			String jpql = "select a from GateTruck a where a.truckNo =:truckNo";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("truckNo", truckNo);
			List<GateTruck> gateTruckList = JpaUtils.findAll(jpql, paramLs2);
			if (gateTruckList.size() < 1) {
				throw new HdRunTimeException("该作业进出闸存在问题！");
			}
			GateTruck gateTruck = gateTruckList.get(0);

			portCar.setCurrentStat("5");
			portCar.setCyPlac(null);
			portCar.setOutCyTim(cargoInfo.getOutCyTim());
			portCar.setOutPortNo(cargoInfo.getShipNo());
			portCar.setTransId("1");
			portCar.setTransCorp(cargoInfo.getTransCorp());
			JpaUtils.update(portCar);

			WorkCommand workCommand = new WorkCommand();
			BeanUtils.copyProperties(cargoInfo, workCommand);
			workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
			workCommand.setPortCarNo(portCar.getPortCarNo());
			workCommand.setWorkTyp("TZ");
			workCommand.setBrandCod(portCar.getBrandCod());
			workCommand.setCarTyp(portCar.getCarTyp());
			workCommand.setCarKind(portCar.getCarKind());
			workCommand.setLength(portCar.getLength());
			workCommand.setWidth(portCar.getWidth());
			workCommand.setFinishedId("0");
			// workCommand.setNightId(cargoInfo.getNightId());
			// workCommand.setHolidayId(cargoInfo.getHolidayId());
			workCommand.setQueueId(HdUtils.genUuid());
			workCommand.setRfidCardNo(portCar.getRfidCardNo());
			workCommand.setVinNo(portCar.getVinNo());
			// workCommand.setRemarks(cargoInfo.getRemarks());\
			workCommand.setOutCyNam(cargoInfo.getInCyNam());
			workCommand.setOutCyTim(cargoInfo.getOutCyTim());
			workCommand.setInCyTim(cargoInfo.getOutCyTim());
			workCommand.setTruckNo(gateTruck.getTruckNo());
			JpaUtils.save(workCommand);

			// TruckWork truckWork = new TruckWork();
			// BeanUtils.copyProperties(portCar, truckWork);
			// truckWork.setContractNo(workQueueList.get(0).getContractNo());
			// truckWork.setTruckNo(gateTruck.getTruckNo());
			// truckWork.setInGateNo(gateTruck.getInGatNo());
			// truckWork.setInRecNam(gateTruck.getInRecNam());
			// truckWork.setWorkNam(cargoInfo.getInCyNam());
			// truckWork.setWorkTim(cargoInfo.getOutCyTim());
			// truckWork.setInGatTim(HdUtils.getDateTime());
			// truckWork.setCarryId("T");
			// if(TruckWork.BC.equals(cargoInfo.getTransCorp())){
			// truckWork.setCarryWay("1");
			// }else{
			// truckWork.setCarryWay("0");
			// }
			// truckWork.setVinNo(portCar.getVinNo());
			// truckWork.setIngateId(gateTruck.getIngateId());
			// JpaUtils.save(truckWork);

			// BillCar billCar = new BillCar();
			// BeanUtils.copyProperties(portCar, billCar);
			// billCar.setBillcarId(HdUtils.genUuid());
			// billCar.setShipbillId(shipBillList.get(0).getShipbillId());
			// JpaUtils.save(billCar);
		}
		return HdUtils.genMsg();
	}

	// 批量卸船理货
	@Override
	public HdMessageCode saveNmXclh(CargoInfo cargoInfo) {
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", cargoInfo.getShipNo());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("不存在卸船作业队列！");
		}
		if (cargoInfo.getRcsl() != null) {
			int rcsl = cargoInfo.getRcsl().intValue();
			String dockCod = "";
			String billNo = "";
			Ship ship = JpaUtils.findById(Ship.class, cargoInfo.getShipNo());
			if (HdUtils.strNotNull(ship.getShipCodId())) {
				CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
				if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
					CBrand cBrand = JpaUtils.findById(CBrand.class, cargoInfo.getBrandCod());
					if (HdUtils.strIsNull(cBrand.getBrandShort())) {
						throw new HdRunTimeException("品牌简称为空 请先维护品牌简称信息！");
					}
					billNo = cShipData.getShipShort() + " " + ship.getIvoyage() + cBrand.getBrandShort();
				} else {
					throw new HdRunTimeException("请输入品牌信息！");
				}

			}
			if (HdUtils.strNotNull(cargoInfo.getInCyNam())) {
				CEmployee bean = JpaUtils.findById(CEmployee.class, cargoInfo.getInCyNam());
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
				for (int i = 0; i < rcsl; i++) {
					PortCar portCar = new PortCar();
					portCar.setiEId(Ship.JK);
					portCar.setTradeId(Ship.NM);
					portCar.setInPortNo(" ");
					portCar.setCurrentStat("2");
					portCar.setBillNo(billNo);
					portCar.setShipNo(cargoInfo.getShipNo());
					portCar.setInCyTim(cargoInfo.getInCyTim());
					portCar.setRfidCardNo("vepc" + new Date().getTime());
					portCar.setVinNo(portCar.getRfidCardNo());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						portCar.setBrandCod(cargoInfo.getBrandCod());
					}
					portCar.setLengthOverId(cargoInfo.getLengthOverId());
					portCar.setCarTyp(cargoInfo.getCarTyp());
					portCar.setCarKind(cargoInfo.getCarKind());
					portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
					portCar.setCyRowNo("###");
					portCar.setCyBayNo("###");
					portCar.setCyPlac("###");
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						portCar.setCarLevel(cargoInfo.getCarLevel());
					}
					portCar.setDockCod(dockCod);
					portCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.save(portCar);

					WorkCommand workCommand = new WorkCommand();
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
					workCommand.setRfidCardNo(portCar.getRfidCardNo());
					workCommand.setVinNo(portCar.getVinNo());
					workCommand.setPortCarNo(portCar.getPortCarNo());
					workCommand.setWorkTyp("SI");
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						workCommand.setBrandCod(cargoInfo.getBrandCod());
					}
					workCommand.setCarTyp(cargoInfo.getCarTyp());
					workCommand.setLength(cargoInfo.getLength());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setShipWorkTim(cargoInfo.getInCyTim());
					workCommand.setFinishedId("0");
					workCommand.setLengthOverId(cargoInfo.getLengthOverId());
					workCommand.setUseMachId(cargoInfo.getUseMachId());
					workCommand.setUseWorkerId(cargoInfo.getUseWorkerId());
					workCommand.setNightId(cargoInfo.getNightId());
					workCommand.setHolidayId(cargoInfo.getHolidayId());
					workCommand.setQueueId(HdUtils.genUuid());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setInCyNam(cargoInfo.getInCyNam());
					workCommand.setShipNo(cargoInfo.getShipNo());
					workCommand.setBillNo(billNo);
					workCommand.setCyPlac(portCar.getCyPlac());
					workCommand.setCarKind(cargoInfo.getCarKind());
					workCommand.setRemarks(cargoInfo.getRemarks());
					workCommand.setDirectId(cargoInfo.getDirectId());//新增加的直提标志
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						workCommand.setCarLevel(cargoInfo.getCarLevel());
					}
					JpaUtils.save(workCommand);
				}
			}
		}
		return HdUtils.genMsg();
	}

	// 批量卸船理货
	@Override
	public HdMessageCode saveXclh(CargoInfo cargoInfo) {
		String jpql = "select a from WorkQueue a where a.workTyp = 'SI' and a.shipNo =:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", cargoInfo.getShipNo());
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
		if (workQueueList.size() < 1) {
			throw new HdRunTimeException("不存在卸船作业队列！");
		}
		if (cargoInfo.getRcsl() != null) {
			int rcsl = cargoInfo.getRcsl().intValue();
			for (int i = 0; i < rcsl; i++) {
				if (HdUtils.strNotNull(cargoInfo.getBillNo())) {
					String jpql1 = "select a from BillCar a where a.billNo =:billNo and a.shipNo =:shipNo and a.lhFlag = '0' order by a.recTim asc";
					QueryParamLs paramLs1 = new QueryParamLs();
					paramLs1.addParam("billNo", cargoInfo.getBillNo());
					paramLs1.addParam("shipNo", cargoInfo.getShipNo());
					List<BillCar> billCarList = JpaUtils.findAll(jpql1, paramLs1);
					if (billCarList.size() <= 0) {
						throw new HdRunTimeException("舱单明细出错了！");
					}
					BillCar billCar = billCarList.get(0);
					billCar.setCarTyp(cargoInfo.getCarTyp());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						billCar.setBrandCod(cargoInfo.getBrandCod());
					}
					billCar.setLengthOverId(cargoInfo.getLengthOverId());
					// 超长标志 1
					if ("1".equals(cargoInfo.getLengthOverId())) {
						billCar.setLength(cargoInfo.getLength());
					}
					billCar.setLhFlag("1");// 理货标志 1代表理完
					billCar.setRfidCardNo("vepc" + new Date().getTime());
					billCar.setVinNo(billCar.getRfidCardNo());
					billCar.setCarKind(cargoInfo.getCarKind());
					billCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.update(billCar);

					PortCar portCar = JpaUtils.findById(PortCar.class, billCar.getPortCarNo());
					portCar.setCurrentStat("2");
					portCar.setInCyTim(cargoInfo.getInCyTim());
					portCar.setRfidCardNo(billCar.getRfidCardNo());
					portCar.setVinNo(billCar.getVinNo());
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						portCar.setBrandCod(cargoInfo.getBrandCod());
					}
					portCar.setLengthOverId(cargoInfo.getLengthOverId());
					portCar.setCarTyp(cargoInfo.getCarTyp());
					portCar.setCarKind(cargoInfo.getCarKind());
					portCar.setBrandCod(cargoInfo.getBrandCod());
					portCar.setCyAreaNo(cargoInfo.getCyAreaNo());
					portCar.setCyRowNo("###");
					portCar.setCyBayNo("###");
					portCar.setCyPlac("###");
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						portCar.setCarLevel(cargoInfo.getCarLevel());
					}
					portCar.setRemarks(cargoInfo.getRemarks());
					JpaUtils.update(portCar);

					WorkCommand workCommand = new WorkCommand();
					workCommand.setWorkQueueNo(workQueueList.get(0).getWorkQueueNo());
					workCommand.setRfidCardNo(billCar.getRfidCardNo());
					workCommand.setVinNo(billCar.getVinNo());
					workCommand.setPortCarNo(billCar.getPortCarNo());
					workCommand.setWorkTyp("SI");
					if (HdUtils.strNotNull(cargoInfo.getBrandCod())) {
						workCommand.setBrandCod(cargoInfo.getBrandCod());
					}
					workCommand.setCarTyp(cargoInfo.getCarTyp());
					workCommand.setLength(cargoInfo.getLength());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setShipWorkTim(cargoInfo.getInCyTim());
					workCommand.setFinishedId("0");
					workCommand.setLengthOverId(cargoInfo.getLengthOverId());
					workCommand.setUseMachId(cargoInfo.getUseMachId());
					workCommand.setUseWorkerId(cargoInfo.getUseWorkerId());
					workCommand.setNightId(cargoInfo.getNightId());
					workCommand.setHolidayId(cargoInfo.getHolidayId());
					workCommand.setQueueId(HdUtils.genUuid());
					workCommand.setInCyTim(cargoInfo.getInCyTim());
					workCommand.setInCyNam(cargoInfo.getInCyNam());
					workCommand.setShipNo(cargoInfo.getShipNo());
					workCommand.setBillNo(cargoInfo.getBillNo());
					workCommand.setCyPlac(portCar.getCyPlac());
					workCommand.setCarKind(cargoInfo.getCarKind());
					workCommand.setRemarks(cargoInfo.getRemarks());
					workCommand.setDirectId(cargoInfo.getDirectId());//新增加的直提标志
					if (HdUtils.strNotNull(cargoInfo.getCarLevel())) {
						workCommand.setCarLevel(cargoInfo.getCarLevel());
					}
					JpaUtils.save(workCommand);
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Transactional
	@Override
	public void ckWanChuan(String shipNo) {
		String jpql = "select a from PlanRange a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql, paramLs);
		if (planRangeList.size() > 0) {
			for (PlanRange p : planRangeList) {
				JpaUtils.remove(p);
			}
		}

		String jpql2 = "select a from PlanPlacVin a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("shipNo", shipNo);
		List<PlanPlacVin> planPlacVinList = JpaUtils.findAll(jpql2, paramLs2);
		if (planPlacVinList.size() > 0) {
			for (PlanPlacVin p : planPlacVinList) {
				JpaUtils.remove(p);
			}
		}

		String jpql3 = "select a from PlanGroup a where a.planTyp='SO' and a.shipNo=:shipNo  ";
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("shipNo", shipNo);
		List<PlanGroup> planGroupList = JpaUtils.findAll(jpql3, paramLs3);
		if (planGroupList.size() > 0) {
			for (PlanGroup p : planGroupList) {
				JpaUtils.remove(p);
			}
		}
		// 根据作业数据生成舱单
		ShipBill shipBill = new ShipBill();
		String shipbillId = HdUtils.genUuid();
		shipBill.setShipbillId(shipbillId);
		shipBill.setShipNo(shipNo);
		String jpql4 = "select a from PortCar a where a.portCarNo in (select b.portCarNo from WorkCommand  b where b.workTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs4 = new QueryParamLs();
		paramLs4.addParam("shipNo", shipNo);
		List<PortCar> portCarList = JpaUtils.findAll(jpql4, paramLs4);
		if (portCarList.size() > 0) {
			for (PortCar wc : portCarList) {
				shipBill.setBillNo(wc.getBillNo());
				shipBill.setShipNo(wc.getShipNo());
				shipBill.setiEId("E");
				shipBill.setTradeId(wc.getTradeId());
				shipBill.setBrandCod(wc.getBrandCod());
				shipBill.setCarTyp(wc.getCarTyp());
				shipBill.setMarks(wc.getMarks());
				shipBill.setPieces(null);
				shipBill.setWeights(null);
				shipBill.setValumes(null);
				shipBill.setCarNum(null);
				shipBill.setFittings(null);
				shipBill.setLoadPortCod(null);
				shipBill.setDiscPortCod(null);
				shipBill.setConsignCod(null);
				shipBill.setConsignNam(null);
				shipBill.setCustomBillNo(null);
				shipBill.setReceiveCod(null);
				shipBill.setReceiveNam(null);
				shipBill.setContactPerson(null);
				shipBill.setContactPhone(null);
				shipBill.setSplitBillNo(null);
				shipBill.setRemarks(null);
				shipBill.setRecNam(HdUtils.getCurUser().getAccount());
				shipBill.setRecTim(HdUtils.getDateTime());
			}
		}
		// 删除捣场计划
		String jpql5 = "select a from MoveCarPlan a where a.portCarNo in (select b.portCarNo from WorkCommand  b where b.workTyp='SO' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs5 = new QueryParamLs();
		paramLs5.addParam("shipNo", shipNo);
		List<MoveCarPlan> moveCarPlanList = JpaUtils.findAll(jpql5, paramLs5);
		if (moveCarPlanList.size() > 0) {
			for (MoveCarPlan p : moveCarPlanList) {
				JpaUtils.remove(p);
			}
		}
		// 删除作业指令信息
		String jpql6 = "select a from WorkCommand a where a.workTyp='SO' and a.shipNo=:shipNo ";
		QueryParamLs paramLs6 = new QueryParamLs();
		paramLs6.addParam("shipNo", shipNo);
		List<WorkCommand> workCommandList = JpaUtils.findAll(jpql6, paramLs6);
		ArrayList<WorkCommandBak> workCommandBakList = new ArrayList<WorkCommandBak>();
		if (workCommandList.size() > 0) {
			for (int i = 0; i < workCommandList.size(); i++) {
				WorkCommandBak wcb = new WorkCommandBak();
				wcb.setQueueId(workCommandList.get(i).getQueueId());
				wcb.setWorkQueueNo(workCommandList.get(i).getWorkQueueNo());
				wcb.setPortCarNo(workCommandList.get(i).getPortCarNo());
				wcb.setRfidCardNo(workCommandList.get(i).getRfidCardNo());
				wcb.setVinNo(workCommandList.get(i).getVinNo());
				wcb.setShipNo(workCommandList.get(i).getShipNo());
				wcb.setBillNo(workCommandList.get(i).getBillNo());
				wcb.setContractNo(workCommandList.get(i).getContractNo());
				wcb.setWorkTyp(workCommandList.get(i).getWorkTyp());
				wcb.setBrandCod(workCommandList.get(i).getBrandCod());
				wcb.setCarTyp(workCommandList.get(i).getCarTyp());
				wcb.setCarKind(workCommandList.get(i).getCarKind());
				wcb.setLength(workCommandList.get(i).getLength());
				wcb.setWidth(workCommandList.get(i).getWidth());
				wcb.setTruckNo(workCommandList.get(i).getTruckNo());
				wcb.setDirectId(workCommandList.get(i).getDirectId());
				wcb.setOldPlac(workCommandList.get(i).getOldPlac());
				wcb.setPlanPlac(workCommandList.get(i).getPlanPlac());
				wcb.setCyPlac(workCommandList.get(i).getCyPlac());
				wcb.setSendTim(workCommandList.get(i).getSendTim());
				wcb.setSendNam(workCommandList.get(i).getSendNam());
				wcb.setShipWorkTim(workCommandList.get(i).getShipWorkTim());
				wcb.setShipWorkNam(workCommandList.get(i).getShipWorkNam());
				wcb.setInCyNam(workCommandList.get(i).getInCyNam());
				wcb.setInCyTim(workCommandList.get(i).getInCyTim());
				wcb.setOutCyNam(workCommandList.get(i).getOutCyNam());
				wcb.setOutCyTim(workCommandList.get(i).getOutCyTim());
				wcb.setFinishedId(workCommandList.get(i).getFinishedId());
				wcb.setNightId(workCommandList.get(i).getNightId());
				wcb.setHolidayId(workCommandList.get(i).getHolidayId());
				wcb.setUseMachId(workCommandList.get(i).getUseMachId());
				wcb.setUseWorkerId(workCommandList.get(i).getUseWorkerId());
				wcb.setDriver(workCommandList.get(i).getDriver());
				wcb.setLengthOverId(workCommandList.get(i).getLengthOverId());
				wcb.setRemarks(workCommandList.get(i).getRemarks());
				wcb.setWidthOverId(workCommandList.get(i).getWidthOverId());
				workCommandBakList.add(wcb);
				JpaUtils.remove(workCommandList.get(i));
			}
		}
		// 删除装船的车辆信息
		String jpql7 = "select a from PortCar a where a.iEId='E' and a.currentStat='5' and  a.shipNo=:shipNo ";
		QueryParamLs paramLs7 = new QueryParamLs();
		paramLs7.addParam("shipNo", shipNo);
		List<PortCar> portcarList = JpaUtils.findAll(jpql7, paramLs7);
		ArrayList<PortCarBak> portCarBakList = new ArrayList<PortCarBak>();
		if (portcarList.size() > 0) {
			for (int i = 0; i < portcarList.size(); i++) {
				PortCarBak pcb = new PortCarBak();
				pcb.setPortCarNo(portcarList.get(i).getPortCarNo());
				pcb.setRfidCardNo(portcarList.get(i).getRfidCardNo());
				pcb.setVinNo(portcarList.get(i).getVinNo());
				pcb.setCurrentStat(portcarList.get(i).getCurrentStat());
				pcb.setShipNo(portcarList.get(i).getShipNo());
				pcb.setBillNo(portcarList.get(i).getBillNo());
				pcb.setiEId(portcarList.get(i).getiEId());
				pcb.setTradeId(portcarList.get(i).getTradeId());
				pcb.setConsignCod(portcarList.get(i).getConsignCod());
				pcb.setConsignNam(portcarList.get(i).getConsignNam());
				pcb.setReceiveCod(portcarList.get(i).getReceiveCod());
				pcb.setReceiveNam(portcarList.get(i).getReceiveNam());
				pcb.setCarTyp(portcarList.get(i).getCarTyp());
				pcb.setBrandCod(portcarList.get(i).getBrandCod());
				pcb.setCarKind(portcarList.get(i).getCarKind());
				pcb.setMarks(portcarList.get(i).getMarks());
				pcb.setColorCod(portcarList.get(i).getColorCod());
				pcb.setWeights(portcarList.get(i).getWeights());
				pcb.setVolumes(portcarList.get(i).getVolumes());
				pcb.setLength(portcarList.get(i).getLength());
				pcb.setWidth(portcarList.get(i).getWidth());
				pcb.setHeight(portcarList.get(i).getHeight());
				pcb.setLengthOverId(portcarList.get(i).getLengthOverId());
				pcb.setWidthOverId(portcarList.get(i).getWidthOverId());
				pcb.setCyPlac(portcarList.get(i).getCyPlac());
				pcb.setCyAreaNo(portcarList.get(i).getCyAreaNo());
				pcb.setCyRowNo(portcarList.get(i).getCyRowNo());
				pcb.setCyBayNo(portcarList.get(i).getCyBayNo());
				pcb.setExitCustomId(portcarList.get(i).getExitCustomId());
				pcb.setSpecId(portcarList.get(i).getSpecId());
				pcb.setCustomId(portcarList.get(i).getCustomId());
				pcb.setInspectionId(portcarList.get(i).getInspectionId());
				pcb.setInspOkId(portcarList.get(i).getInspOkId());
				pcb.setLockId(portcarList.get(i).getLockId());
				pcb.setDamId(portcarList.get(i).getDamId());
				pcb.setDamCod(portcarList.get(i).getDamCod());
				pcb.setDamLevel(portcarList.get(i).getDamLevel());
				pcb.setDamArea(portcarList.get(i).getDamArea());
				pcb.setLoadPortCod(portcarList.get(i).getLoadPortCod());
				pcb.setTranPortCod(portcarList.get(i).getTranPortCod());
				pcb.setDiscPortCod(portcarList.get(i).getDiscPortCod());
				pcb.setContractNo(portcarList.get(i).getContractNo());
				pcb.setInToolNo(portcarList.get(i).getInToolNo());
				pcb.setInPortNo(portcarList.get(i).getInPortNo());
				pcb.setToPortTim(portcarList.get(i).getToPortTim());
				pcb.setDiscShipTim(portcarList.get(i).getDiscShipTim());
				pcb.setInCyTim(portcarList.get(i).getInCyTim());
				pcb.setOutToolNo(portcarList.get(i).getOutToolNo());
				pcb.setOutPortNo(portcarList.get(i).getOutPortNo());
				pcb.setOutCyTim(portcarList.get(i).getOutCyTim());
				pcb.setLoadShipTim(portcarList.get(i).getLoadShipTim());
				pcb.setCustomBillNo(portcarList.get(i).getCustomBillNo());
				pcb.setLeavPortTim(portcarList.get(i).getLeavPortTim());
				pcb.setContractNo(portcarList.get(i).getContractNo());
				pcb.setTransId(portcarList.get(i).getTransId());
				pcb.setDockCod(portcarList.get(i).getDockCod());
				pcb.setRemarks(portcarList.get(i).getRemarks());
				pcb.setRecNam(HdUtils.getCurUser().getAccount());
				pcb.setRecTim(HdUtils.getDateTime());
				pcb.setUpdNam(null);
				pcb.setUpdTim(null);
				portCarBakList.add(pcb);
				JpaUtils.remove(portcarList.get(i));
			}
		}
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setLoadEndTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
	}

	@Transactional
	@Override
	public void jkWanChuan(String shipNo) {
		String jpql = "select a from PlanRange a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SI' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<PlanRange> planRangeList = JpaUtils.findAll(jpql, paramLs);
		if (planRangeList.size() > 0) {
			for (PlanRange p : planRangeList) {
				JpaUtils.remove(p);
			}
		}

		String jpql2 = "select a from PlanPlacVin a where a.planGroupNo in (select b.planGroupNo from PlanGroup b where b.planTyp='SI' and b.shipNo=:shipNo ) ";
		QueryParamLs paramLs2 = new QueryParamLs();
		paramLs2.addParam("shipNo", shipNo);
		List<PlanPlacVin> planPlacVinList = JpaUtils.findAll(jpql2, paramLs2);
		if (planPlacVinList.size() > 0) {
			for (PlanPlacVin p : planPlacVinList) {
				JpaUtils.remove(p);
			}
		}

		String jpql3 = "select a from PlanGroup a where a.planTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs3 = new QueryParamLs();
		paramLs3.addParam("shipNo", shipNo);
		List<PlanGroup> planGroupList = JpaUtils.findAll(jpql3, paramLs3);
		if (planGroupList.size() > 0) {
			for (PlanGroup p : planGroupList) {
				JpaUtils.remove(p);
			}
		}
		Timestamp discEndTim;
		String jpql4 = "select a from WorkCommand a where a.workTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs4 = new QueryParamLs();
		paramLs4.addParam("shipNo", shipNo);
		List<WorkCommand> workCommandList = JpaUtils.findAll(jpql4, paramLs4);
		ArrayList<WorkCommandBak> workCommandBakList = new ArrayList<WorkCommandBak>();
		if (workCommandList.size() > 0) {
			for (int i = 0; i < workCommandList.size(); i++) {
				WorkCommandBak wcb = new WorkCommandBak();
				wcb.setQueueId(workCommandList.get(i).getQueueId());
				wcb.setWorkQueueNo(workCommandList.get(i).getWorkQueueNo());
				wcb.setPortCarNo(workCommandList.get(i).getPortCarNo());
				wcb.setRfidCardNo(workCommandList.get(i).getRfidCardNo());
				wcb.setVinNo(workCommandList.get(i).getVinNo());
				wcb.setShipNo(workCommandList.get(i).getShipNo());
				wcb.setBillNo(workCommandList.get(i).getBillNo());
				wcb.setContractNo(workCommandList.get(i).getContractNo());
				wcb.setWorkTyp(workCommandList.get(i).getWorkTyp());
				wcb.setBrandCod(workCommandList.get(i).getBrandCod());
				wcb.setCarTyp(workCommandList.get(i).getCarTyp());
				wcb.setCarKind(workCommandList.get(i).getCarKind());
				wcb.setLength(workCommandList.get(i).getLength());
				wcb.setWidth(workCommandList.get(i).getWidth());
				wcb.setTruckNo(workCommandList.get(i).getTruckNo());
				wcb.setDirectId(workCommandList.get(i).getDirectId());
				wcb.setOldPlac(workCommandList.get(i).getOldPlac());
				wcb.setPlanPlac(workCommandList.get(i).getPlanPlac());
				wcb.setCyPlac(workCommandList.get(i).getCyPlac());
				wcb.setSendTim(workCommandList.get(i).getSendTim());
				wcb.setSendNam(workCommandList.get(i).getSendNam());
				wcb.setShipWorkTim(workCommandList.get(i).getShipWorkTim());
				wcb.setShipWorkNam(workCommandList.get(i).getShipWorkNam());
				wcb.setInCyNam(workCommandList.get(i).getInCyNam());
				wcb.setInCyTim(workCommandList.get(i).getInCyTim());
				wcb.setOutCyNam(workCommandList.get(i).getOutCyNam());
				wcb.setOutCyTim(workCommandList.get(i).getOutCyTim());
				wcb.setFinishedId(workCommandList.get(i).getFinishedId());
				wcb.setNightId(workCommandList.get(i).getNightId());
				wcb.setHolidayId(workCommandList.get(i).getHolidayId());
				wcb.setUseMachId(workCommandList.get(i).getUseMachId());
				wcb.setUseWorkerId(workCommandList.get(i).getUseWorkerId());
				wcb.setDriver(workCommandList.get(i).getDriver());
				wcb.setLengthOverId(workCommandList.get(i).getLengthOverId());
				wcb.setRemarks(workCommandList.get(i).getRemarks());
				wcb.setWidthOverId(workCommandList.get(i).getWidthOverId());
				workCommandBakList.add(wcb);
				JpaUtils.remove(workCommandList.get(i));
				// 记录末卸船时间
				if (i == workCommandList.size() - 1) {
					discEndTim = workCommandList.get(i).getInCyTim();
					// 修改卸船完工时间
					String jpql6 = " update Ship a set a.discEndTim=:discEndTim where a.shipNo=:shipNo ";
					QueryParamLs paramLs6 = new QueryParamLs();
					paramLs6.addParam("shipNo", shipNo);
					paramLs6.addParam("discEndTim", discEndTim);
					JpaUtils.execUpdate(jpql6, paramLs6);
				}
			}
		}
		String jpql5 = "select a from WorkQueue a where a.workTyp='SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs5 = new QueryParamLs();
		paramLs5.addParam("shipNo", shipNo);
		List<WorkQueue> workQueueList = JpaUtils.findAll(jpql5, paramLs5);
		if (workQueueList.size() > 0) {
			for (int i = 0; i < workQueueList.size(); i++) {
				JpaUtils.remove(workQueueList.get(i));
			}
		}
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		ship.setDiscEndTim(HdUtils.getDateTime());
		JpaUtils.update(ship);
	}

	@Override
	public Result chooseShipNo(HdEzuiQueryParams hdEzuiQueryParams) {
		// TODO Auto-generated method stub
		String jpql = "select a from WorkCommand a where a.workTyp = 'SI' and a.shipNo=:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", hdEzuiQueryParams.getOthers().get("shipNo"));
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPageNum(1);
		pageInfo.setRowOfPage(20);
		List mList = JpaUtils.findAll(jpql, paramLs, pageInfo);
		Result result = new Result();
		result.setData(mList);
		return result;
	}

	@Override
	public WorkCommand getShipNoInfo(String shipNo) {
		String jpql = "select a from WorkCommand a where 1=1 and a.workTyp = 'SI' and a.shipNo=:shipNo ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("shipNo", shipNo);
		List<WorkCommand> shipNoList = JpaUtils.findAll(jpql, paramLs);
		WorkCommand s = new WorkCommand();
		if (shipNoList.size() > 0) {
			for (WorkCommand workCommand : shipNoList) {
				s.setShipNo(workCommand.getShipNo());
				s.setBillNo(workCommand.getBillNo());
				s.setDriver(workCommand.getDriver());
				s.setPlanPlac(workCommand.getPlanPlac());
			}
		}
		return s;
	}

	@Override
	public HdEzuiDatagridData findShipingReport(HdQuery hdQuery) {
		String jpql = "select a.brandCod,a.inCyTim,count(a.carTyp),a.carTyp from WorkCommand a where a.workTyp='SO' ";
		// String brandCod=hdQuery.getStr("brandCod");
		String shipNo = hdQuery.getStr("shipNo");
		QueryParamLs paramLs = new QueryParamLs();
		// if(HdUtils.strNotNull(brandCod)){
		// jpql +=" and a.brandCod=:brandCod ";
		// paramLs.addParam("brandCod",brandCod);
		// }
		if (HdUtils.strNotNull(shipNo)) {
			jpql += "  and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		jpql += " group by a.carTyp,a.brandCod,a.inCyTim ";
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		List<WorkCommand> wcList = new ArrayList();
		for (Object[] obj : objList) {
			String jqpl1 = "select a from WorkCommand a where 1=1 and a.workTyp='SO'";
			QueryParamLs paramLs1 = new QueryParamLs();
			if (HdUtils.strNotNull(shipNo)) {
				jqpl1 += "  and a.shipNo=:shipNo ";
				paramLs1.addParam("shipNo", shipNo);
			}
			if (obj[0] != null) {
				jqpl1 += " and a.brandCod =:brandCod";
				paramLs1.addParam("brandCod", obj[0]);
			}
			if (obj[1] != null) {
				jqpl1 += " and a.inCyTim =:inCyTim";
				paramLs1.addParam("inCyTim", obj[1]);
			}
			if (obj[3] != null) {
				jqpl1 += " and a.carTyp =:carTyp";
				paramLs1.addParam("carTyp", obj[3]);
			}
			List<WorkCommand> workCommandList = JpaUtils.findAll(jqpl1, paramLs1);
			if (workCommandList.size() > 0) {
				WorkCommand wc = workCommandList.get(0);
				if (HdUtils.strNotNull(wc.getBrandCod())) {
					CBrand cb = JpaUtils.findById(CBrand.class, wc.getBrandCod());
					wc.setBrandNam(cb.getBrandNam());
				}
				if (HdUtils.strNotNull(wc.getCarTyp())) {
					CCarTyp cct = JpaUtils.findById(CCarTyp.class, wc.getCarTyp());
					wc.setCarTypNam(cct.getCarTypNam());
				}
				wc.setCksl(String.valueOf(obj[2]));
				wcList.add(wc);
			}
		}
		HdEzuiDatagridData results = new HdEzuiDatagridData();
		results.setRows(wcList);
		return results;
	}

	@Override
	public List<EzDropBean> getBillNoByShipNo(String shipNo, String workTyp) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		
		String sql = "select bill_no  from v_select_bill where  ship_no = '"+shipNo+"' and work_typ = '"+workTyp+"' and bill_no is not null";
		List<Map<String,String>> res =
				JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		
		for(Map<String,String> map : res){
			EzDropBean  drop=new EzDropBean();
			String code = map.get("BILL_NO")==null?"":map.get("BILL_NO").toString();
			drop.setValue(code);
			drop.setLabel(code);
			list.add(drop);
		}
		return list;
	}

}
