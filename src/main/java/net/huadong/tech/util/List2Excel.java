package net.huadong.tech.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.huadong.tech.ship.entity.ShipTrendExcel;
import net.huadong.tech.shipbill.entity.ContractIeDoc;

public class List2Excel {
	// public static void main(String[] args) {
	// List workbookList=createlist();
	// try {
	// writeToXls(workbookList);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	public static List createlist(List<ContractIeDoc> contarctiedocList) {
		List biaotou = new ArrayList();
		List resultList = new ArrayList();

		biaotou.add("船名");
		biaotou.add("航次");
		biaotou.add("作业性质");
		biaotou.add("装货单号/唛头");
		biaotou.add("货名");
		biaotou.add("数量");
		biaotou.add("货代");
		biaotou.add("集港");
		biaotou.add("疏港");
		biaotou.add("物流部");
		biaotou.add("集疏港场地");
		biaotou.add("集疏港时间");
		biaotou.add("备注(机力)");
		resultList.add(biaotou);
		for (int i = 0; i < contarctiedocList.size(); i++) {
			List neirong = new ArrayList();
			neirong.add(contarctiedocList.get(i).getShipNam());
			neirong.add(contarctiedocList.get(i).getVoyage());
			String zyxz = "";
			if ("内贸".equals(contarctiedocList.get(i).getTradeNam())) {
				zyxz += "内";
			}
			if ("外贸".equals(contarctiedocList.get(i).getTradeNam())) {
				zyxz += "外";
			}
			if ("进口".equals(contarctiedocList.get(i).getIeNam())) {
				zyxz += "进";
			}
			if ("出口".equals(contarctiedocList.get(i).getIeNam())) {
				zyxz += "出";
			}
			neirong.add(zyxz);
			neirong.add(contarctiedocList.get(i).getBillNo());
			if(HdUtils.strNotNull(contarctiedocList.get(i).getBrand())){
				if(HdUtils.strNotNull(contarctiedocList.get(i).getCarKind())){
					neirong.add(contarctiedocList.get(i).getBrandNam()+contarctiedocList.get(i).getCarKindNam());
				}else{
					neirong.add(contarctiedocList.get(i).getBrandNam());
				}
			}else{
				if(HdUtils.strNotNull(contarctiedocList.get(i).getCarKind())){
					neirong.add(contarctiedocList.get(i).getCarKindNam());
				}else{
					neirong.add("");
				}
			}
			String carNum = String.valueOf(contarctiedocList.get(i).getCarNum());
			if(HdUtils.strIsNull(carNum)) {
				carNum = "0";
			}
			neirong.add(carNum);
			neirong.add(contarctiedocList.get(i).getConsignNam());
			// 集疏港挑钩
			String jg = "√";
			String sg = "√";
			String wl = "√";

			if ("1".equals(contarctiedocList.get(i).getContractTyp())) {
				sg = "";
				wl = "";
				neirong.add(jg);
				neirong.add(sg);
				neirong.add(wl);
			}
			if ("2".equals(contarctiedocList.get(i).getContractTyp())) {
				jg = "";
				wl = "";
				neirong.add(jg);
				neirong.add(sg);
				neirong.add(wl);
			}
			if ("4".equals(contarctiedocList.get(i).getContractTyp())) {
				jg = "";
				sg = "";
				neirong.add(jg);
				neirong.add(sg);
				neirong.add(wl);
			}
			neirong.add(contarctiedocList.get(i).getPlanArea());
			if ("00:00-00:00".equals(contarctiedocList.get(i).getFromTo())) {
				neirong.add("全天");
			} else {
				neirong.add(contarctiedocList.get(i).getFromTo());
			}
			// 机力人力
			String jlrl = "";
			if (HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())
					&& HdUtils.strNotNull(contarctiedocList.get(i).getPortMac())) {
				jlrl = contarctiedocList.get(i).getOutMac() +","+contarctiedocList.get(i).getPortMac();
				neirong.add(jlrl);
			} else if (HdUtils.strNotNull(contarctiedocList.get(i).getOutMac())) {
				jlrl = contarctiedocList.get(i).getOutMac();
				neirong.add(jlrl);
			} else if (HdUtils.strNotNull(contarctiedocList.get(i).getPortMac())) {
				jlrl = contarctiedocList.get(i).getPortMac();
				neirong.add(jlrl);
			} else {
				neirong.add("");
			}
			resultList.add(neirong);
		}
		//System.out.print(resultList);
		return resultList;
	}
	public static void writeToXls(List resultList,HttpServletResponse response,String dte) throws Exception {
		// 创建一个EXCEL
		Workbook wb = new HSSFWorkbook();
		HSSFCellStyle setBorder = (HSSFCellStyle) wb.createCellStyle();
		// 创建一个SHEET
		Sheet sheet1 = wb.createSheet("MySheet");
//		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				// 创建一行
				Row row = sheet1.createRow(i);
				List rowList = (List) resultList.get(i);
				for (int j = 0; j < rowList.size(); j++) {
					if(i!=0&&j==5){
				    Cell cell = row.createCell(j);
				    setBorder.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
				    String num = (String)rowList.get(j);
				    if(HdUtils.strIsNull(num) || num.equals("null")) {
				    	num = "0";
				    }
//				    System.out.println("***\n" + num);
				    int cellLiString = Integer.parseInt(num);
//				    System.out.println("***\n" + num);
					cell.setCellValue(cellLiString);
					cell.setCellStyle(setBorder);
					}else{
					Cell cell = row.createCell(j);
					String cellLiString = (String) rowList.get(j);
					cell.setCellValue(cellLiString);
					}
				}
			}
		}
//		   response.setContentType("application/vnd.ms-excel;charset=utf-8");
//           response.setCharacterEncoding("GBK");
//           response.setHeader("Content-Disposition", "attachment;filename="+ new String(("test" + ".xlsx").getBytes(), "iso-8859-1"));
//           //response.setCharacterEncoding("utf-8");
//           OutputStream os=response.getOutputStream();
//           wb.write(os);
//           os.close();
		
		response.setContentType("application/vnd.ms-excel;charset=utf-8");//下面三行是关键代码，处理乱码问题 
		response.setCharacterEncoding("GBK");
		String exportFileName="集疏港计划"+"-"+dte;
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String((exportFileName+".xls").getBytes(), "iso-8859-1"));
		OutputStream outstream = response.getOutputStream();
		wb.write(outstream);
		outstream.close();
//		FileOutputStream fileOut = new FileOutputStream("c:\\tjroro\\excel\\集疏港计划.xls");
//		wb.write(fileOut);
//		fileOut.close();
	}
	
}