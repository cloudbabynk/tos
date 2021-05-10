package net.huadong.tech.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ShipTrendExcelTest {
	static String[] excelHeader = { "码头", "泊位", "船名", "总吨", "国籍", "日期", "时间", "日期", "时间" };
	/**
	 * 导出xls文件(合并单元格)
	 * 
	 * @param list
	 * @param xlsFileName
	 * @param sheetName
	 */
	public static void reportMergeXls(HttpServletResponse response, List<Map<String, String>> list, String filename,
			String sheetName) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);// 创建一个sheet-test1
		
		sheet.setColumnWidth(2,256*13+184);
		sheet.setColumnWidth(4,256*11+184);
		// 设置单元格风格，居中对齐.
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		cs.setAlignment(HorizontalAlignment.CENTER);
		cs.setVerticalAlignment(VerticalAlignment.CENTER);

		// 设置字体:
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示

		cs.setFont(font);// 要用到的字体格式
		
		// sheet.setColumnWidth(0, 3766); //第一个参数代表列下标(从0开始),第2个参数代表宽度值
		// cs.setWrapText(true);//设置字体超出宽度自动换行

		// 设置背景颜色
		// cs.setFillBackgroundColor(HSSFColor.BLUE.index);
		// cs.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		HSSFCellStyle cs1 = wb.createCellStyle();
		cs1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cs1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cs1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cs1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		cs1.setAlignment(HorizontalAlignment.CENTER);
		cs1.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置字体:
		HSSFFont font1 = wb.createFont();
		font1.setFontName("宋体");
		font1.setFontHeightInPoints((short) 11);// 设置字体大小
		cs1.setFont(font1);// 要用到的字体格式
		// 创建第一行

		for (int j = 0; j < 2; j++) {
			HSSFRow row = sheet.createRow((short) j);
			HSSFCell cell;
			for (int i = 0; i < excelHeader.length; i++) {// 设置表头-标题
				cell = row.createCell(i);
				cell.setCellStyle(cs1);
				if (i == 3 || i == 4){
					cell.setCellStyle(cs);
				}
				cell.setCellValue(excelHeader[i]);
				if(j==0&&i==5){
				cell.setCellValue("靠泊");
				}
				if(j==0&&i==7){
				cell.setCellValue("离泊");
				}
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));// 横向：合并第一行的第6列到第7列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));// 横向：合并第一行的第8列到第9列
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));// 纵向：合并第一列的第1行,第2行
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));// 纵向：合并第二列的第1行,第2行
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));// 纵向：合并第三列的第1行,第2行
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));// 纵向：合并第四列的第1行,第2行
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));// 纵向：合并第五列的第1行,第2行

		
		// 设置列值-内容
		for (int i = 0; i < list.size(); i++) {
			HSSFRow row = sheet.createRow(i+2);
			Map map = list.get(i);
			row.createCell(0).setCellStyle(cs1);
			row.getCell(0).setCellValue((String) map.get("码头"));
			row.createCell(1).setCellStyle(cs1);
			row.getCell(1).setCellValue((String) map.get("泊位"));
			row.createCell(2).setCellStyle(cs1);
			row.getCell(2).setCellValue((String) map.get("船名"));
			row.createCell(3).setCellStyle(cs1);
			row.getCell(3).setCellValue((String) map.get("总吨").toString());
			row.createCell(4).setCellStyle(cs1);
			row.getCell(4).setCellValue((String) map.get("国籍"));
			row.createCell(5).setCellStyle(cs1);
			row.getCell(5).setCellValue((String) map.get("到港日期"));
			row.createCell(6).setCellStyle(cs1);
			row.getCell(6).setCellValue((String) map.get("到港时间"));
			row.createCell(7).setCellStyle(cs1);
			row.getCell(7).setCellValue((String) map.get("离港日期"));
			row.createCell(8).setCellStyle(cs1);
			row.getCell(8).setCellValue((String) map.get("离港时间"));
			
		}
		HSSFRow row;
		row = sheet.createRow(2+list.size());
		// addMergedRegion(起始行,结束行,起始列,结束列 );
		// 设置合并的标题头(注意：横向合并的时候，标题头单元格必须长度和内容单元格一致否则合并时会把其他标题头单元格内容挤掉)
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			if (hashmap.containsKey(map.get("码头"))) {
				hashmap.put((String) map.get("码头"), hashmap.get((String) map.get("码头")) + 1);
			} else {
				hashmap.put((String) map.get("码头"), 1);
			}
			if (hashmap.containsKey(map.get("泊位"))) {
				hashmap.put((String) map.get("泊位"), hashmap.get((String) map.get("泊位")) + 1);
			} else {
				hashmap.put((String) map.get("泊位"), 1);
			}
		}
		// 统计和滚装和环球，N1,N2,N13,N14各出现的次数
		int gzcs = 0;
		int hqcs = 0;
		int n1cs = 0;
		int n2cs = 0;
		int n13cs = 0;
		int n14cs = 0;
		if (hashmap.containsKey("滚装")) {
			gzcs = hashmap.get("滚装");
		}
		if (hashmap.containsKey("环球滚装")) {
			hqcs = hashmap.get("环球滚装");
		}
		if (hashmap.containsKey("N1")) {
			n1cs = hashmap.get("N1");
		}
		if (hashmap.containsKey("N2")) {
			n2cs = hashmap.get("N2");
		}
		if (hashmap.containsKey("N13")) {
			n13cs = hashmap.get("N13");
		}
		if (hashmap.containsKey("N14")) {
			n14cs = hashmap.get("N14");
		}
		if (!hashmap.containsKey("滚装")) {
			if (!hashmap.containsKey("N13")) {
				if(n14cs>1){
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n14cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n14cs, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
				cs.setWrapText(true);
				AppendToLastRow(row,cs);
				}
			}
			if (!hashmap.containsKey("N14")) {
				if(n13cs>1){
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n13cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n13cs, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
				cs.setWrapText(true);
				AppendToLastRow(row,cs);
				}
			} 
			if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
				if(n13cs>1&&n14cs>1){
				sheet.addMergedRegion(new CellRangeAddress(n13cs, 1 + n13cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2 + n13cs, 1 + n13cs + n14cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n13cs + n14cs, 0, 0));	
				sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
				cs.setWrapText(true);
				AppendToLastRow(row,cs);
				}
				if(n13cs>1&&n14cs<=1){
				sheet.addMergedRegion(new CellRangeAddress(2, 1+ n13cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 2+ n13cs, 0, 0));	
				sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
				cs.setWrapText(true);
				AppendToLastRow(row,cs);
				}
				if(n13cs<=1&&n14cs>1){
					sheet.addMergedRegion(new CellRangeAddress(2+ n13cs, 1 + n13cs + n14cs, 1, 1));	
					sheet.addMergedRegion(new CellRangeAddress(2, 1 + n13cs + n14cs, 0, 0));
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);
				}
				if(n13cs==1&&n14cs==1){
					sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);
				}
			}
		} else {
			if (!hashmap.containsKey("N1")) {
				if(n2cs>1){
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n2cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n2cs, 0,0));
				if (hashmap.containsKey("环球滚装")) {
					if (!hashmap.containsKey("N13")) {
						if(n14cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}else{
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);	
						}
					}
					if (!hashmap.containsKey("N14")) {
						if(n13cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}else{
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);	
						}
					} 
					if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
						if(n13cs>1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	//N13，N14都存在且大于1
							sheet.addMergedRegion(new CellRangeAddress(2+ gzcs + n13cs, 1 + gzcs + n13cs + n14cs, 1, 1));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+hqcs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
                         if(n13cs>1&&n14cs<=1){
                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
                        	 sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
 							cs.setWrapText(true);
 							AppendToLastRow(row,cs);
                         }
                         if(n13cs<=1&&n14cs>1){
                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs+n13cs, 1 + gzcs + n14cs+n13cs, 1, 1));
                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
                        	 sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
 							cs.setWrapText(true);
 							AppendToLastRow(row,cs);
                         }
                         if(n13cs==1&&n14cs==1){
                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
                        	 sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
 							cs.setWrapText(true);
 							AppendToLastRow(row,cs);
                         }
						}
					}else{
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);	
					}
				}else{
					if (hashmap.containsKey("环球滚装")) {
						if (!hashmap.containsKey("N13")) {
							if(n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 1, 1));
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);	
							}
						}
						if (!hashmap.containsKey("N14")) {
							if(n13cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);	
							}
						} 
						if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
							if(n13cs>1&&n14cs>1){
								sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
								sheet.addMergedRegion(new CellRangeAddress(2+ gzcs + n13cs, 1+ gzcs + n13cs + n14cs, 1, 1));
								sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+hqcs, 0, 0));
								sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
								cs.setWrapText(true);
								AppendToLastRow(row,cs);	
							}
	                         if(n13cs>1&&n14cs<=1){
	                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
	                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
	                        		sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
	        						cs.setWrapText(true);
	        						AppendToLastRow(row,cs);	
	                         }
	                         if(n13cs<=1&&n14cs>1){
	                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs+n13cs, 1 + gzcs + n14cs+n13cs, 1, 1));
	                        	 sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
	                        		sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
	        						cs.setWrapText(true);
	        						AppendToLastRow(row,cs);	
	                         }
	                         if(n13cs==1&&n14cs==1){
	                        	    sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
	                        		sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
	        						cs.setWrapText(true);
	        						AppendToLastRow(row,cs);		 
	                         }
							}
						
						}else{
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);		
						}
				}
				}else if (!hashmap.containsKey("N2")) {
				if(n1cs>1){
				sheet.addMergedRegion(new CellRangeAddress(2, 1+ n1cs, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(2, 1 + n1cs, 0,0));
				if (hashmap.containsKey("环球滚装")) {
					if (!hashmap.containsKey("N13")) {
						if(n14cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}
					}
					if (!hashmap.containsKey("N14")) {
						if(n13cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}
					} 
					if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
						if(n13cs>1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs + n13cs, 1 + gzcs + n13cs + n14cs, 1, 1));
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs,  1+gzcs + n13cs+n14cs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
						if(n13cs>1&&n14cs<=1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));		
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));	
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
						if(n13cs<=1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs+n13cs, 1 + gzcs + n14cs+ n13cs, 1, 1));		
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));		
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}if(n13cs==1&&n14cs==1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
					}
				}else{
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);
					}	
				}else{
					if (hashmap.containsKey("环球滚装")) {
					if (!hashmap.containsKey("N13")) {
						if(n14cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}
					}
					if (!hashmap.containsKey("N14")) {
						if(n13cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
						cs.setWrapText(true);
						AppendToLastRow(row,cs);
						}
					} 
					if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
						if(n13cs>1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs + n13cs, 1 + gzcs + n13cs + n14cs, 1, 1));
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs,  1+gzcs + n13cs+n14cs, 0, 0));
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
						if(n13cs>1&&n14cs<=1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));		
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));	
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
						if(n13cs<=1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs+n13cs, 1 + gzcs + n14cs+ n13cs, 1, 1));		
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));		
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);
						}
						if(n13cs==1&&n14cs==1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs+n14cs, 0, 0));		
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
							cs.setWrapText(true);
							AppendToLastRow(row,cs);	
						}
					}
				}else{
					sheet.addMergedRegion(new CellRangeAddress(2,1+gzcs, 0, 0));
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);	
				}
				}
				}else  if (hashmap.containsKey("N1") && hashmap.containsKey("N2")) {
				if(n1cs>1&&n2cs>1){
					sheet.addMergedRegion(new CellRangeAddress(2,n1cs+1, 1, 1));
					sheet.addMergedRegion(new CellRangeAddress(2+ n1cs, 1 + n1cs + n2cs, 1, 1));	
					sheet.addMergedRegion(new CellRangeAddress(2, 1 + n1cs + n2cs, 0, 0));	
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);	
				}
				if(n1cs>1&&n2cs<=1){
					sheet.addMergedRegion(new CellRangeAddress(2, 1+ n1cs, 1, 1));
					sheet.addMergedRegion(new CellRangeAddress(2, 2+ n1cs, 0, 0));	
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);	
				}
				if(n1cs<=1&&n2cs>1){
					sheet.addMergedRegion(new CellRangeAddress(2+ n1cs, 1 + n1cs + n2cs, 1, 1));	
					sheet.addMergedRegion(new CellRangeAddress(2, 1 + n1cs + n2cs, 0, 0));	
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);	
				}
				if(n1cs==1&&n2cs==1){
					sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));	
					sheet.addMergedRegion(new CellRangeAddress(2+gzcs+hqcs, 6+ gzcs+hqcs, 0, 8));
					cs.setWrapText(true);
					AppendToLastRow(row,cs);	
				}
				if (hashmap.containsKey("环球滚装")) {
					if (!hashmap.containsKey("N13")) {
						if(n14cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n14cs, 0, 0));
						}
					}
					if (!hashmap.containsKey("N14")) {
						if(n13cs>1){
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));
						sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 0, 0));
						}
					} 
					if (hashmap.containsKey("N13") && hashmap.containsKey("N14")) {
						if(n13cs>1&&n14cs>1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs + n13cs, 1 + gzcs + n13cs + n14cs, 1, 1));	
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+hqcs, 0, 0));
						}
						if(n13cs>1&&n14cs<=1){
							sheet.addMergedRegion(new CellRangeAddress(2 + gzcs, 1 + gzcs + n13cs, 1, 1));	
							sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+hqcs, 0, 0));	
						}
                        if(n13cs<=1&&n14cs>1){
                        	sheet.addMergedRegion(new CellRangeAddress(2 + gzcs + n13cs, 1 + gzcs + n13cs + n14cs, 1, 1));	
                        	sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+hqcs, 0, 0));
						}
                        if(n13cs==1&&n14cs==1){
                        	sheet.addMergedRegion(new CellRangeAddress(2+gzcs, 1 + gzcs+2, 0, 0));
                        	
                        }
						}
					}
				}
			}
			
		try {
			// FileOutputStream fileOut = new
			// FileOutputStream("C:/tjroro/excel/"+xlsFileName+".xls");
			// wb.write(fileOut);
			// fileOut.close();
			// System.out.println("输出完成");
			// 页面弹出下载或保存

			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("GBK");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((filename + ".xlsx").getBytes(), "iso-8859-1"));
			// response.setCharacterEncoding("utf-8");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();

		} catch (Exception e) {
			System.out.println("文件输出失败!");
			e.printStackTrace();
		}

	}
	public static void AppendToLastRow(HSSFRow row,HSSFCellStyle cs){
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(cs);
		String LastRowText="填写说明：1、每日动态的时间节点为16:00-次日16:00,内容为该时间段内的所有船舶动态；"
          +"2、请各单位于每日14:00之前将该表格发送至我局邮箱；";
		cell.setCellValue(LastRowText);	
	}
}
