package net.huadong.tech.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.criterialquery.HdExportExcel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.persistence.queries.ReportQueryResult;


import net.huadong.tech.ship.entity.ShipTrendExcel;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;

public class ListToExcel {
	// public static void main(String[] args) {
	// List workbookList=createlist();
	// try {
	// writeToXls(workbookList);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	public static List createlist(List<PortCar> portcarList) {
		List biaotou = new ArrayList();
		List resultList = new ArrayList();

		biaotou.add("序号");
		biaotou.add("性质");
		biaotou.add("装货/提单号");
		biaotou.add("入库日期");
		biaotou.add("入库数量");
		biaotou.add("货物种类");
		biaotou.add("出库日期");
		biaotou.add("出库数量");
		biaotou.add("堆存天数");
		biaotou.add("备注");
		resultList.add(biaotou);
		for (int i = 0; i < portcarList.size(); i++) {
			List neirong = new ArrayList();
			neirong.add(String.valueOf(i+1));
			String zyxz = "";
			if ("1".equals(portcarList.get(i).getTradeId())) {
				zyxz += "内";
			}
			if ("2".equals(portcarList.get(i).getTradeId())) {
				zyxz += "外";
			}
			if ("I".equals(portcarList.get(i).getiEId())) {
				zyxz += "进";
			}
			if ("E".equals(portcarList.get(i).getiEId())) {
				zyxz += "出";
			}
			neirong.add(zyxz);
			neirong.add(portcarList.get(i).getBillNo());
			neirong.add(portcarList.get(i).getInCyTim().toString().substring(0, 10));
			neirong.add(portcarList.get(i).getRksl());
			if(HdUtils.strNotNull(portcarList.get(i).getBrandNam())){
				neirong.add(portcarList.get(i).getBrandNam()+portcarList.get(i).getCarKindNam());
			}else{
				neirong.add(portcarList.get(i).getCarKindNam());
			}
			
			if(portcarList.get(i).getOutCyTim() == null){
				neirong.add("");
			}else{
				neirong.add(portcarList.get(i).getOutCyTim().toString().substring(0, 10));
			}
			
			neirong.add(portcarList.get(i).getCksl());
			neirong.add(portcarList.get(i).getDcts());
			neirong.add(portcarList.get(i).getConsignNam());
			resultList.add(neirong);
		}
		System.out.print(resultList);
		return resultList;
	}
	//生成动态excel
	public static List createDTlist(List<ShipTrendExcel> trendexcelList) {
		List biaotou = new ArrayList();
		List resultList = new ArrayList();

		biaotou.add("码头");
		biaotou.add("泊位");
		biaotou.add("船名");
		biaotou.add("总表");
		biaotou.add("国籍");
		biaotou.add("日期");
		biaotou.add("时间");
		biaotou.add("日期");
		biaotou.add("时间");
		resultList.add(biaotou);
		for (int i = 0; i < trendexcelList.size(); i++) {
			List neirong = new ArrayList();
			neirong.add(trendexcelList.get(i).getDockNam());
			neirong.add(trendexcelList.get(i).getBerthNam());
			neirong.add(trendexcelList.get(i).getBerthNam());
//			
//			if ("外贸".equals(contarctiedocList.get(i).getTradeNam())) {
//				zyxz += "外";
//			}
//			if ("进口".equals(contarctiedocList.get(i).getIeNam())) {
//				zyxz += "进";
//			}
//			if ("出口".equals(contarctiedocList.get(i).getIeNam())) {
//				zyxz += "出";
//			}
//			neirong.add(zyxz);
//			neirong.add(contarctiedocList.get(i).getBillNo());
//			neirong.add(contarctiedocList.get(i).getBrandNam());
//			neirong.add(contarctiedocList.get(i).getCarNum().toString());
//			neirong.add(contarctiedocList.get(i).getConsignNam());
//			// 集疏港挑钩
//			String jg = "√";
//			String sg = "√";
//
//			if ("1".equals(contarctiedocList.get(i).getContractTyp())) {
//				sg = "";
//				neirong.add(jg);
//				neirong.add(sg);
//			}
//			if ("2".equals(contarctiedocList.get(i).getContractTyp())) {
//				jg = "";
//				neirong.add(jg);
//				neirong.add(sg);
//			}
//			neirong.add(contarctiedocList.get(i).getPlanArea());
//			if ("00:00-00:00".equals(contarctiedocList.get(i).getFromTo())) {
//				neirong.add("全天");
//			} else {
//				neirong.add(contarctiedocList.get(i).getFromTo());
//			}
//			// 机力人力
//			String jlrl = "";
//			if (HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())
//					&& HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())) {
//				jlrl = contarctiedocList.get(i).getOutMac() + contarctiedocList.get(i).getPortMac();
//				neirong.add(jlrl);
//			} else if (HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())) {
//				jlrl = contarctiedocList.get(i).getOutMac();
//				neirong.add(jlrl);
//			} else if (HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())) {
//				jlrl = contarctiedocList.get(i).getPortMac();
//				neirong.add(jlrl);
//			} else {
//				neirong.add("");
//			}
//			resultList.add(neirong);
		}
		//System.out.print(resultList);
		//return resultList;
		return trendexcelList;
		
	}
	public static void writeToXls(List resultList,HttpServletResponse response,String dte) throws Exception {
		// 创建一个EXCEL
		Workbook wb = new HSSFWorkbook();
		HSSFCellStyle setBorder = (HSSFCellStyle) wb.createCellStyle();
		// 创建一个SHEET
		Sheet sheet1 = wb.createSheet("MySheet");
//		Row row = sheet1.createRow((int) 0);
//		for (int i = 0; i < excelHeader.length; i++) {//设置表头-标题
//            Cell cell = row.createCell(i);
//            cell.setCellValue(excelHeader[i]);
//            sheet1.autoSizeColumn(i);//自动设宽
//        }
//		sheet1.addMergedRegion(new CellRangeAddress(0,0,0,9));//横向：合并第一行的第6列到第7列
//		row.createCell(0).setCellValue("天津港环球滚装码头单船货物出入库记录");
//		Row row1 = sheet1.createRow((int) 1);
//		Cell cell1 = row.createCell(0);
//		cell1.setCellValue("船名：");
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				// 创建一行
				Row row = sheet1.createRow(i);
				List rowList = (List) resultList.get(i);
				for (int j = 0; j < rowList.size(); j++) {
					Cell cell = row.createCell(j);
					String cellLiString = (String) rowList.get(j);
					cell.setCellValue(cellLiString);
				}
			}
		}
		
//        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("11"+CommonUtil.getDateStr()).getBytes("UTF-8"), "iso8859-1") + ".xls");
//        response.setContentType("application/x-msdownload;charset=UTF-8");   
//        ServletOutputStream outstream = response.getOutputStream();
//		wb.write(outstream);
//		outstream.close();
//		FileOutputStream fileOut = new FileOutputStream("c:\\tjroro\\1.xls");
//		wb.write(fileOut);
//		fileOut.close();
//		response.setHeader("Content-Disposition",
//				"attachment; filename=" + URLEncoder.encode("sasdfas", "UTF-8") + ".xls");
//		ServletOutputStream arg35 = response.getOutputStream();
//		wb.write(arg35);
//		arg35.close();

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("GBK");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("货物出入库记录"+CommonUtil.getDateStr()).getBytes("UTF-8"), "iso8859-1") + ".xls");
        //response.setCharacterEncoding("utf-8");
        OutputStream os=response.getOutputStream();
        wb.write(os);
        os.close();
	}
	public static void writeToDTXls(List resultList,HttpServletResponse response) throws Exception {
		// 创建一个EXCEL
		Workbook wb = new HSSFWorkbook();
		HSSFCellStyle setBorder = (HSSFCellStyle) wb.createCellStyle();
		// 创建一个SHEET
		Sheet sheet1 = wb.createSheet("MySheet");
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				// 创建一行
				Row row = sheet1.createRow(i);
				List rowList = (List) resultList.get(i);
				for (int j = 0; j < rowList.size(); j++) {
					Cell cell = row.createCell(j);
					String cellLiString = (String) rowList.get(j);
					cell.setCellValue(cellLiString);
				}
			}
		}
//		response.setContentType("msexcel");//下面三行是关键代码，处理乱码问题 
//		response.setCharacterEncoding("utf-8");
//		String exportFileName="jsg"+"-"+dte;
//		response.setHeader("Content-Disposition",
//				"attachment; filename=" + new String(exportFileName.getBytes("gbk"), "iso8859-1") + ".xls");
//		OutputStream outstream = response.getOutputStream();
//		wb.write(outstream);
//		outstream.close();
		FileOutputStream fileOut = new FileOutputStream("c:\\tjroro\\excel\\滚装码头船舶动态表.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}