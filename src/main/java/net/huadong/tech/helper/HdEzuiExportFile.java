package net.huadong.tech.helper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.persistence.queries.ReportQueryResult;

public class HdEzuiExportFile {
	public static void exportExcelEx(HdExportExcel hdExportExcel, HttpServletResponse response,
			HdEzuiDatagridData data) {
		exportExcelFile(hdExportExcel.getColumnTitles(), hdExportExcel.getColumnNames(), data.getRows(),
				hdExportExcel.getExportFileName(), response);
	}

	public static void exportExcelFile(String columnTitles, String columnNames, List data, String exportFileName,
			HttpServletResponse response) {
		try {
			HSSFWorkbook ex = new HSSFWorkbook();
			Sheet sheet1 = ex.createSheet(exportFileName);
			sheet1.setDefaultColumnWidth(20);
			byte rowIndex = 0;
			int cellIndex = 0;
			int arg31 = rowIndex + 1;
			Row row = sheet1.createRow(rowIndex);
			CellStyle cellStyle = ex.createCellStyle();
			cellStyle.setAlignment((short) 2);
			Font font = ex.createFont();
			font.setBoldweight((short) 700);
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern((short) 1);
			String[] arg14;
			int os = (arg14 = columnTitles.split(",")).length;

			for (int dateCellStyle = 0; dateCellStyle < os; ++dateCellStyle) {
				String createHelper = arg14[dateCellStyle];
				Cell dataCellIndex = row.createCell(cellIndex++);
				if (createHelper.contains("<font")) {
					createHelper = createHelper.replace("<font color=\"red\">", "").replace("</font>", "");
					font.setColor((short) 10);
					cellStyle.setFont(font);
				} else {
					font.setColor((short) 8);
					cellStyle.setFont(font);
				}

				dataCellIndex.setCellStyle(cellStyle);
				dataCellIndex.setCellValue(createHelper);
			}

			CreationHelper arg32 = ex.getCreationHelper();
			CellStyle arg33 = ex.createCellStyle();
			Iterator arg36 = data.iterator();

			while (true) {
				while (arg36.hasNext()) {
					Object arg34 = arg36.next();
					int arg18;
					if (arg34 instanceof Map) {
						Map arg38 = (Map) arg34;
						row = sheet1.createRow(arg31++);
						int arg40 = 0;
						String[] arg43;
						int arg42 = (arg43 = columnNames.split(",")).length;

						for (arg18 = 0; arg18 < arg42; ++arg18) {
							String arg41 = arg43[arg18];

							try {
								Cell arg44 = row.createCell(arg40++);
								Object o3 = arg38.get(arg41);
								if (o3 != null) {
									if (o3 instanceof Date) {
										arg44.setCellValue((Date) o3);
										arg33.setDataFormat(arg32.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
										arg44.setCellStyle(arg33);
									} else {
										arg44.setCellValue(o3.toString());
									}
								}
							} catch (SecurityException arg28) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg28);
							} catch (IllegalArgumentException arg29) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg29);
							}
						}
					} else {
						row = sheet1.createRow(arg31++);
						int arg37 = 0;
						String[] arg19;
						arg18 = (arg19 = columnNames.split(",")).length;

						for (int columnName1 = 0; columnName1 < arg18; ++columnName1) {
							String columnName = arg19[columnName1];

							try {
								Cell ex1 = row.createCell(arg37++);
								Object o2 = null;
								if (arg34 instanceof ReportQueryResult) {
									o2 = ((ReportQueryResult) arg34).get(columnName);
								} else {
									o2 = arg34.getClass()
											.getMethod("get" + columnName.substring(0, 1).toUpperCase()
													+ columnName.substring(1), new Class[0])
											.invoke(arg34, new Object[0]);
								}

								if (o2 != null) {
									if (o2 instanceof Date) {
										ex1.setCellValue((Date) o2);
										arg33.setDataFormat(arg32.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
										ex1.setCellStyle(arg33);
									} else {
										ex1.setCellValue(o2.toString());
									}
								}
							} catch (NoSuchMethodException arg23) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg23);
							} catch (SecurityException arg24) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg24);
							} catch (IllegalAccessException arg25) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg25);
							} catch (IllegalArgumentException arg26) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg26);
							} catch (InvocationTargetException arg27) {
								Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null,
										arg27);
							}
						}
					}
				}

				response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(exportFileName, "UTF-8") + ".xls");
				ServletOutputStream arg35 = response.getOutputStream();
				ex.write(arg35);
				arg35.close();
				break;
			}
		} catch (IOException arg30) {
			Logger.getLogger(HdEzuiExportFile.class.getName()).log(Level.SEVERE, (String) null, arg30);
		}

	}
}