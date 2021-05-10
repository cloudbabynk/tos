package net.huadong.tech.cargo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.cargo.service.PortCarService;
import net.huadong.tech.com.entity.ComFileUpload;
import net.huadong.tech.ship.service.FileUploadService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/cargo/FileUpload")
public class PortCarFileController {

	
	  @Autowired
	  private FileUploadService fileUploadService;
		@Autowired
		private PortCarService portCarService;
		
		
	  
	  @RequestMapping({""})
	  public String page(String nouse) { return "com/fileupload"; } 
	  
	  @ResponseBody
	  @RequestMapping({"/find"})
	  public List<ComFileUpload> find(String entityName, String entityId) {
		  List<ComFileUpload> aa= this.fileUploadService.find(entityName, entityId);
		    return aa;
	  }

		@RequestMapping("/syncPortCatPic")
		@ResponseBody
	  public HdMessageCode syncPortCatPic(@RequestBody Map map){
			
		    return portCarService.syncPortCatPic(map);
	  }
		@RequestMapping(value = "/exportPortCatPic", method = RequestMethod.POST)
		@ResponseBody
		public void exportPortCatPic(@RequestBody Map map, HttpServletResponse response) {
			String exportFileName=map.get("exportFileName")+"";	
			Map dataMap=(Map) map.get("gradeData");
			List thtleLst=(List) dataMap.get("columntielts");

			Map mapData=(Map) dataMap.get("row");
			List<Map> rowLst=(List) mapData.get("rows");
			List<Map> mergesLst=(List) dataMap.get("mergesLst");
			
			HSSFWorkbook ex = new HSSFWorkbook();
			Sheet sheet1 = ex.createSheet(exportFileName);
			sheet1.setAutobreaks(true);
			int  idxRow=0,idxCol=0;
			Row tRow=sheet1.createRow(idxRow);
			Map<String,Object> tMap=new HashMap<>();
			for (int i=0;i<thtleLst.size();i++) {
				Cell cell=tRow.createCell(i);
				cell.setCellValue(thtleLst.get(i)+"");
				tMap.put(thtleLst.get(i)+"", i);
			}
			idxRow=1;
			for (int i=0;i<rowLst.size();i++) {
				Map<String,Object> mapItem=rowLst.get(i);
				Row row=sheet1.createRow(idxRow+i);
			    for (Map.Entry<String, Object> m : tMap.entrySet()) {
			    	Cell cell=row.createCell(Integer.parseInt( m.getValue()+""));
			    	cell.setCellValue(mapItem.get(m.getKey().replaceAll("[./]", ""))==null?"":mapItem.get(m.getKey().replaceAll("[./]", ""))+"");
			    }
			}
			
			for (int i=0;i<mergesLst.size();i++) {
				Map<String,Object> mapItem=mergesLst.get(i);
				int rowIndex=Integer.parseInt( mapItem.get("index")+"");
				int rowSpan=Integer.parseInt( mapItem.get("rowspan")+"");
				int colNum=Integer.parseInt(tMap.get("联系方式")+"");
				 CellRangeAddress region = new CellRangeAddress(rowIndex+1, rowIndex+rowSpan, colNum, colNum);// 起始行号，终止行号， 起始列号，终止列号
				 //在sheet里增加合并单元格  
				 sheet1.addMergedRegion(region);
				
			}
			
			OutputStream output;
			try {
				response.setContentType("application/octet-stream;charset=utf-8");
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(exportFileName, "UTF-8") + ".xls"); 
				ServletOutputStream arg35 = response.getOutputStream();
				ex.write(arg35);
				arg35.flush();
				arg35.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  
	  
	  @ResponseBody
	  @RequestMapping({"upload"})
	  public HdMessageCode carCargoUpload(@RequestParam MultipartFile file, HttpServletRequest request,	HttpServletResponse response) {
	    Workbook wb = null;
	    String oriName = file.getOriginalFilename();
		String ext = oriName.substring(oriName.lastIndexOf("."));
		try {
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(file.getInputStream());
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(file.getInputStream());
			}
		} catch (FileNotFoundException e) {
			throw new HdRunTimeException("文件不存在！");//
		} catch (IOException e) {
			throw new HdRunTimeException("输出异常！");//
		}
	    Sheet sheet = wb.getSheetAt(0);
	    
	    List<CellRangeAddress> cellRangeAddressLst= sheet.getMergedRegions();
	    Map hMap=null;
	    List  mergesLst=new ArrayList();
	    for (CellRangeAddress cellRangeAddress : cellRangeAddressLst) {
	    	Cell firstCell=sheet.getRow(cellRangeAddress.getFirstRow()).getCell(cellRangeAddress.getFirstColumn());
	    	firstCell.setCellType(CellType.STRING);
	    	int rowNum=cellRangeAddress.getLastRow()-cellRangeAddress.getFirstRow();
	    	for (int i=1;i<=rowNum;i++) {
	    		Cell curCell=sheet.getRow(cellRangeAddress.getFirstRow()+i).getCell(cellRangeAddress.getFirstColumn());
//	    		curCell.setCellType(CellType.STRING);
	    		curCell.setCellValue(firstCell.getStringCellValue());
			}
	    	hMap=new HashMap();
	    	hMap.put("index",cellRangeAddress.getFirstRow()-1);
	    	hMap.put("rowspan",cellRangeAddress.getLastRow()- cellRangeAddress.getFirstRow()+1);
	    	mergesLst.add(hMap);
		}
	    
	    
	    
	    Row thRow=sheet.getRow(sheet.getFirstRowNum());
	    List thLst=new ArrayList();
	    List thtleLst=new ArrayList();
	    for (Cell cell : thRow) {
	    	cell.setCellType(CellType.STRING);
	    	thLst.add(cell.getStringCellValue().replaceAll("[./]", ""));
	    	thtleLst.add(cell.getStringCellValue());
		}
	    List rowList=new ArrayList();
	    for (int i = 1; i <= sheet.getLastRowNum(); i++)
	    {
	    	  Row row = sheet.getRow(i);//获取索引为i的行，以0开始
	    	  if(row!=null) {
		          Map mapItem=new HashMap();
				  for (Cell cell : row) {
						cell.setCellType(CellType.STRING);
						mapItem.put(thLst.get(cell.getColumnIndex()), cell.getStringCellValue());
				  }
				  rowList.add(mapItem);
	    	  }
	    }
	    
	    Map rtMap=new HashMap();
	    HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
	    ezuiDatagridData.setRows(rowList);

	    rtMap.put("column",thLst);
	    rtMap.put("columntielts",thtleLst);
	    rtMap.put("row",ezuiDatagridData);
	    rtMap.put("mergesLst",mergesLst);
	    
		HdMessageCode message=HdUtils.genMsg();
		message.setData(rtMap);
		return message;
	}
}
