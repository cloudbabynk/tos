package net.huadong.tech.shipbill.service.impl;
import net.huadong.tech.shipbill.entity.TallySplit;
import net.huadong.tech.shipbill.service.TallySplitService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.ship.entity.ShipDispatchLog;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.VWorkCommand;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 
 */
@Component
public class TallySplitServiceImpl implements TallySplitService {

	/*@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {       
      	String jpql = "select a from TallySplit a where 1=1 ";
      	String type = hdQuery.getStr("Type");
      	QueryParamLs paramLs = new QueryParamLs();
      	if ("HY".equals(type)) {
      		jpql += "and a.upId =:upId";
      		paramLs.addParam("upId", "1");//1代表已提交
      	}
      	HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
      	List<TallySplit> list = result.getRows();
      	for (TallySplit bean : list) {
      		if (HdUtils.strNotNull(bean.getCarTypCod())) {
      			CCarTyp data = JpaUtils.findById(CCarTyp.class, bean.getCarTypCod());
      			if (data != null) {
      				bean.setCarTypNam(data.getCarTypNam());
      			}
      		}
      		if (HdUtils.strNotNull(bean.getBrandCod())) {
      			CBrand data = JpaUtils.findById(CBrand.class, bean.getBrandCod());
      			if (data != null) {
      				bean.setBrandNam(data.getBrandNam());
      			}
      		}
      	}
        return result;
	}*/
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String shipNo = hdQuery.getStr("shipNo");
      	String workTyp=hdQuery.getStr("workTyp");
      	String type = hdQuery.getStr("Type");
      	String contractNo=hdQuery.getStr("contractNo");
      	
      	String billNo = hdQuery.getStr("billNo");
      	String brandCod = hdQuery.getStr("brandCod");
      	String workDteStart = hdQuery.getStr("workDteStart");//日期开始 
		String workDteEnd= hdQuery.getStr("workDteEnd"); //日期结束
		String storeDaysStart= hdQuery.getStr("storeDaysStart");//堆存天数开始
		String storeDaysEnd= hdQuery.getStr("storeDaysEnd");//堆存天数结束
		String ywTyp= hdQuery.getStr("ywTyp");//陆运/航运
      	
      	QueryParamLs paramLs = new QueryParamLs();
        String jpql = "select a from TallySplit a where 1=1 ";
        if("TI".equals(workTyp)||"TO".equals(workTyp)){//作业类型集疏港的不根据船号查询
        	jpql+=" ";
        }else{
        	jpql+=" and a.shipNo  =:shipNo";
        	paramLs.addParam("shipNo",shipNo);
        }
        if(workTyp!=null){
      		jpql+=" and a.workTyp like :workTyp";
      		paramLs.addParam("workTyp","%" + workTyp + "%");
      	}
        if ("HY".equals(type)) {
      		jpql += " and a.upId =:upId";
      		paramLs.addParam("upId", "1");//1代表已提交
      	}
        if(contractNo!=null&&!"".equals(contractNo)){
        	jpql += " and a.contractNo like :contractNo";
      		paramLs.addParam("contractNo", "%" + contractNo + "%");
        }
        if(HdUtils.strNotNull(billNo)){
        	jpql += " and a.billNo = :billNo";
      		paramLs.addParam("billNo", billNo);
        }
        if(HdUtils.strNotNull(brandCod)){
        	jpql += " and a.brandCod = :brandCod";
      		paramLs.addParam("brandCod", brandCod);
        }
        
        if(HdUtils.strNotNull(workDteStart)){
        	jpql += " and a.workTim >= :workDteStart";
      		paramLs.addParam("workDteStart", HdUtils.strToDate(workDteStart));
        }
        if(HdUtils.strNotNull(workDteEnd)){
        	jpql += " and a.workTim <= :workDteEnd";
      		paramLs.addParam("workDteEnd", HdUtils.strToDate(workDteEnd));
        }
        if(HdUtils.strNotNull(storeDaysStart)){
        	jpql += " and (a.outDte - a.inDte)>=:storeDaysStart";
      		paramLs.addParam("storeDaysStart", storeDaysStart);
        }
        
        if(HdUtils.strNotNull(storeDaysEnd)){
        	jpql += " and (a.outDte - a.inDte)<=:storeDaysEnd";
      		paramLs.addParam("storeDaysEnd", storeDaysEnd);
        }
        if(HdUtils.strNotNull(ywTyp)){
        	jpql += " and a.ywTyp = :ywTyp";
      		paramLs.addParam("ywTyp", ywTyp);
        }
      	HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
      	List<TallySplit> list = result.getRows();
      	for (TallySplit bean : list) {//在后台对总重字段进行四舍五入取整操作
      	/*	if(bean.getAllWgt()!=null){
      		    BigDecimal AllWgt = bean.getAllWgt();
           		BigDecimal result1 = AllWgt.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
           	    bean.setAllWgt(result1);
           	   
      		}
      		if(bean.getAllValumes()!=null){//在后台对总体积字段进行四舍五入取整操作
      			BigDecimal allValumes = bean.getAllValumes();
           		BigDecimal result1 = allValumes.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
           	    bean.setAllValumes(result1);
      		}*/
      		if (HdUtils.strNotNull(bean.getCarTypCod())) {
      			CCarTyp data = JpaUtils.findById(CCarTyp.class, bean.getCarTypCod());
      			if (data != null) {
      				bean.setCarTypNam(data.getCarTypNam());
      			}
      		}
      		if (HdUtils.strNotNull(bean.getBrandCod())) {
      			CBrand data = JpaUtils.findById(CBrand.class, bean.getBrandCod());
      			if (data != null) {
      				bean.setBrandNam(data.getBrandNam());
      			}
      		}
      	}
        //return result;
      	HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
		ezuiDatagridData.setRows(list);
		ezuiDatagridData.setTotal(result.getTotal());
		return ezuiDatagridData;
	}
	@Override
	public HdEzuiDatagridData findExcel(HdQuery hdQuery,String ids) {//新增加的根据选择的行进行导出
		String shipNo = hdQuery.getStr("shipNo");
      	String workTyp=hdQuery.getStr("workTyp");
      	String type = hdQuery.getStr("Type");
      	String contractNo=hdQuery.getStr("contractNo");
      	
      	String billNo = hdQuery.getStr("billNo");
      	String brandCod = hdQuery.getStr("brandCod");
      	String workDteStart = hdQuery.getStr("workDteStart");//日期开始 
		String workDteEnd= hdQuery.getStr("workDteEnd"); //日期结束
		String storeDaysStart= hdQuery.getStr("storeDaysStart");//堆存天数开始
		String storeDaysEnd= hdQuery.getStr("storeDaysEnd");//堆存天数结束
		String ywTyp= hdQuery.getStr("ywTyp");//陆运/航运
      	
      	QueryParamLs paramLs = new QueryParamLs();
        String jpql = "select a from TallySplit a where 1=1 ";
        if(!"".equals(ids)&&ids!=null){
        	String[] ids2=ids.split(",");
        	String endIds="";
        	for(int i=0;i<ids2.length;i++){
        		String ids3 = "'" + ids2[i] + "'";  
        		if (i < ids2.length-1)  
	            {
	            	ids3 += ",";
	            }
	            endIds += ids3;
        	}
        	//jpql+=" and a.id  in (:id)";
        	//paramLs.addParam("id",endIds);
        	jpql+="and a.id in(" + endIds + ")";
        }
        if("TI".equals(workTyp)||"TO".equals(workTyp)){//作业类型集疏港的不根据船号查询
        	jpql+=" ";
        }else{
        	jpql+=" and a.shipNo  =:shipNo";
        	paramLs.addParam("shipNo",shipNo);
        }
        if(workTyp!=null){
      		jpql+=" and a.workTyp like :workTyp";
      		paramLs.addParam("workTyp","%" + workTyp + "%");
      	}
        if ("HY".equals(type)) {
      		jpql += " and a.upId =:upId";
      		paramLs.addParam("upId", "1");//1代表已提交
      	}
        if(contractNo!=null&&!"".equals(contractNo)){
        	jpql += " and a.contractNo like :contractNo";
      		paramLs.addParam("contractNo", "%" + contractNo + "%");
        }
        if(HdUtils.strNotNull(billNo)){
        	jpql += " and a.billNo = :billNo";
      		paramLs.addParam("billNo", billNo);
        }
        if(HdUtils.strNotNull(brandCod)){
        	jpql += " and a.brandCod = :brandCod";
      		paramLs.addParam("brandCod", brandCod);
        }
        
        if(HdUtils.strNotNull(workDteStart)){
        	jpql += " and a.workTim >= :workDteStart";
      		paramLs.addParam("workDteStart", HdUtils.strToDate(workDteStart));
        }
        if(HdUtils.strNotNull(workDteEnd)){
        	jpql += " and a.workTim <= :workDteEnd";
      		paramLs.addParam("workDteEnd", HdUtils.strToDate(workDteEnd));
        }
        if(HdUtils.strNotNull(storeDaysStart)){
        	jpql += " and (a.outDte - a.inDte)>=:storeDaysStart";
      		paramLs.addParam("storeDaysStart", storeDaysStart);
        }
        
        if(HdUtils.strNotNull(storeDaysEnd)){
        	jpql += " and (a.outDte - a.inDte)<=:storeDaysEnd";
      		paramLs.addParam("storeDaysEnd", storeDaysEnd);
        }
        if(HdUtils.strNotNull(ywTyp)){
        	jpql += " and a.ywTyp = :ywTyp";
      		paramLs.addParam("ywTyp", ywTyp);
        }
      	HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
      	List<TallySplit> list = result.getRows();
      	for (TallySplit bean : list) {//在后台对总重字段进行四舍五入取整操作
      		if(bean.getAllWgt()!=null){
      		    BigDecimal AllWgt = bean.getAllWgt();
           		BigDecimal result1 = AllWgt.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
           	    bean.setAllWgt(result1);
           	   
      		}
      		if(bean.getAllValumes()!=null){//在后台对总体积字段进行四舍五入取整操作
      			BigDecimal allValumes = bean.getAllValumes();
           		BigDecimal result1 = allValumes.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
           	    bean.setAllValumes(result1);
      		}
      		if (HdUtils.strNotNull(bean.getCarTypCod())) {
      			CCarTyp data = JpaUtils.findById(CCarTyp.class, bean.getCarTypCod());
      			if (data != null) {
      				bean.setCarTypNam(data.getCarTypNam());
      			}
      		}
      		if (HdUtils.strNotNull(bean.getBrandCod())) {
      			CBrand data = JpaUtils.findById(CBrand.class, bean.getBrandCod());
      			if (data != null) {
      				bean.setBrandNam(data.getBrandNam());
      			}
      		}
      	}
        //return result;
      	HdEzuiDatagridData ezuiDatagridData=new HdEzuiDatagridData();
		ezuiDatagridData.setRows(list);
		ezuiDatagridData.setTotal(result.getTotal());
		return ezuiDatagridData;
		
	}
	
	@Override
	@Transactional
    public void exportExcelJF(HdEzuiDatagridData data,HttpServletResponse response){
		List<TallySplit> TallySplitList = data.getRows();
		HSSFWorkbook ex = new HSSFWorkbook();
		HSSFCellStyle style = ex.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Sheet sheet1 = ex.createSheet();
        sheet1.setColumnWidth(3, 15 * 256);//设置列宽
        sheet1.setColumnWidth(7, 20 * 256);
        sheet1.setColumnWidth(8, 20 * 256);
        sheet1.setColumnWidth(9, 20 * 256);
        sheet1.setColumnWidth(11, 20 * 256);
        sheet1.setColumnWidth(13,  20 * 256);
        Row row = sheet1.createRow(0);
        Row row1=sheet1.createRow(TallySplitList.size()+2);
        HSSFCellStyle fCellStyle = (HSSFCellStyle)ex.createCellStyle();//设置单元格中字体的颜色和加粗
        HSSFFont ffont = (HSSFFont)ex.createFont();
        ffont.setColor(HSSFColor.BLACK.index);
        ffont.setBold(true);
        fCellStyle.setFont(ffont);
         for (int i=0;i<=27;i++) {
			Cell cell = row.createCell(i);
			Cell cell2=row1.createCell(i);
        }
      
	    row.getCell(0).setCellValue("提交");
        row.getCell(1).setCellValue("审核");
        row.getCell(2).setCellValue("陆运/航运");
        row.getCell(3).setCellValue("船名");
        row.getCell(4).setCellValue("航次");
        row.getCell(5).setCellValue("作业项目");
        row.getCell(6).setCellValue("件数");
        row.getCell(7).setCellValue("提单号");
        row.getCell(8).setCellValue("进厂日期");
        row.getCell(9).setCellValue("出厂日期");
        row.getCell(10).setCellValue("堆存天数");
        row.getCell(11).setCellValue("作业时间");
        row.getCell(12).setCellValue("车型");
        row.getCell(13).setCellValue("品牌");
        row.getCell(14).setCellValue("总重");
        row.getCell(15).setCellValue("单车重量");
        row.getCell(16).setCellValue("长度");
        row.getCell(17).setCellValue("超长");
        row.getCell(18).setCellValue("流向");
        row.getCell(19).setCellValue("总体积");
        row.getCell(20).setCellValue("件体积");
        row.getCell(21).setCellValue("陆运机力");
        row.getCell(22).setCellValue("港方机力");
        row.getCell(23).setCellValue("港方人力");
        row.getCell(24).setCellValue("夜班作业");
        row.getCell(25).setCellValue("节假日作业");
        row.getCell(26).setCellValue("备注");
        for(int i=0;i<=26;i++){
        row.getCell(i).setCellStyle(fCellStyle);
        }
        int result1 =0;
        BigDecimal result2 = null;
        BigDecimal result3 = null;
        int result4 = 0;
        int result5 =0;
        for (int i=0;i<TallySplitList.size();i++) {//在后台对总重字段进行四舍五入取整操作
        	if(TallySplitList.get(i).getPieces()!=null){
      		    BigDecimal Pieces = TallySplitList.get(i).getPieces();
      		    int a = Pieces.intValue();
      		    result1+=a;
           		 }
      		if(TallySplitList.get(i).getAllWgt()!=null){
      		     BigDecimal AllWgt = TallySplitList.get(i).getAllWgt();
      		     result2 = AllWgt.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
      		     int a = result2.intValue();
      		     result4+=a;
               }
      		if(TallySplitList.get(i).getAllValumes()!=null){
      		    BigDecimal AllValumes = TallySplitList.get(i).getAllValumes();
           		 result3 = AllValumes.setScale( 0,BigDecimal.ROUND_HALF_UP).abs(); 
           		 int a = result3.intValue();
     		     result5+=a;
               }
      		}
        row1.getCell(0).setCellValue("合计:");
        row1.getCell(6).setCellValue(result1);
        row1.getCell(14).setCellValue(result4);
        row1.getCell(19).setCellValue(result5);
        row1.getCell(0).setCellStyle(fCellStyle);
        row1.getCell(6).setCellStyle(fCellStyle);
        row1.getCell(14).setCellStyle(fCellStyle);
        row1.getCell(19).setCellStyle(fCellStyle);
        for(int j=0;j<TallySplitList.size();j++){
        	Row bean = sheet1.createRow(j+1);
        	for (int k=0;k<=27;k++) {
    			Cell cell = bean.createCell(k);
            }
        	if(TallySplitList.get(j).getUpId()==null){
        		bean.getCell(0).setCellValue("未提交");
			}else{
				if("1".equals(TallySplitList.get(j).getUpId())){
					bean.getCell(0).setCellValue("已提交");
				}else{
					bean.getCell(0).setCellValue("驳回");
				}
			}
            bean.getCell(1).setCellValue(this.getYesOrNo(TallySplitList.get(j).getCheckId()));	
        	if(TallySplitList.get(j).getYwTyp()==null){
        		bean.getCell(2).setCellValue("");
        	}else{
        		if("1".equals(TallySplitList.get(j).getYwTyp())){
        			bean.getCell(2).setCellValue("陆运");
        		}else{
        	        bean.getCell(2).setCellValue("航运");
        		}
        	}
        	if(TallySplitList.get(j).getShipNam()==null){
        		bean.getCell(3).setCellValue("");
        	}else{
    		    bean.getCell(3).setCellValue(TallySplitList.get(j).getShipNam());
        	}
        	if(TallySplitList.get(j).getVoyage()==null){
        		bean.getCell(4).setCellValue("");
        	}else{
    		    bean.getCell(4).setCellValue(TallySplitList.get(j).getVoyage());
        	}
        	if(TallySplitList.get(j).getWorkTyp()==null){
        		bean.getCell(5).setCellValue("");
        	}else{
        		//bean.getCell(5).setCellValue(TallySplitList.get(j).getWorkTyp());
        		if("MV".equals(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("捣场");
        		}else if("SI".equals(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("卸船");
        		}else if("SO".equals(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("装船");
        		}else if("TI".equals(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("集港");
        		}else if("TO".endsWith(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("疏港");
        		}else if("UI".equals(TallySplitList.get(j).getWorkTyp())){
        			bean.getCell(5).setCellValue("拆箱");
        		}
        	}
    	    if(TallySplitList.get(j).getPieces()==null){
    			bean.getCell(6).setCellValue("");
    		}else{
    		    bean.getCell(6).setCellValue(TallySplitList.get(j).getPieces().toString());
    		}
    	    if(TallySplitList.get(j).getBillNo()==null){
    	    	bean.getCell(7).setCellValue("");
    	    }else{
    		    bean.getCell(7).setCellValue(TallySplitList.get(j).getBillNo());
    	    }
    		if(TallySplitList.get(j).getInDte()==null){
    			bean.getCell(8).setCellValue(" ");
    		}else{
    		    bean.getCell(8).setCellValue(TallySplitList.get(j).getInDte().toString());
    		}
    		if(TallySplitList.get(j).getOutDte()==null){
    			bean.getCell(9).setCellValue(" ");
    		}else{
    		    bean.getCell(9).setCellValue(TallySplitList.get(j).getOutDte().toString());
    		}
    		
    		if(null!=TallySplitList.get(j).getInDte() && null!= TallySplitList.get(j).getOutDte()){
    		long storeday = (TallySplitList.get(j).getOutDte().getTime() - TallySplitList.get(j).getInDte().getTime())/(24*60*60*1000);
    		bean.getCell(10).setCellValue(storeday);
    		}
    		if(TallySplitList.get(j).getWorkTim()==null){
    			bean.getCell(11).setCellValue("");
    		}else{
    		    bean.getCell(11).setCellValue(TallySplitList.get(j).getWorkTim().toString());
    		}
    		if(TallySplitList.get(j).getCarTypCod()==null){
    			bean.getCell(12).setCellValue("");
    		}else{
    		    bean.getCell(12).setCellValue(TallySplitList.get(j).getCarTypCod());
    		}
    		if(TallySplitList.get(j).getBrandCod()==null){
    			bean.getCell(13).setCellValue("");
    		}else{
    		    bean.getCell(13).setCellValue(TallySplitList.get(j).getBrandCod());
    		}
    		if(TallySplitList.get(j).getAllWgt()==null){
    			bean.getCell(14).setCellValue("");
    		}else{
    		    bean.getCell(14).setCellValue(TallySplitList.get(j).getAllWgt().toString());
    		}
    		if(TallySplitList.get(j).getPieceWgt()==null){
    			bean.getCell(15).setCellValue("");
    		}else{
    		    bean.getCell(15).setCellValue(TallySplitList.get(j).getPieceWgt().toString());
    		}
    		if(TallySplitList.get(j).getLength()==null){
    			bean.getCell(16).setCellValue(" ");
    		}else{
    		    bean.getCell(16).setCellValue(TallySplitList.get(j).getLength().toString());
    		}
    		bean.getCell(17).setCellValue(this.getYesOrNo(TallySplitList.get(j).getOverLengthId()));
    		if(TallySplitList.get(j).getFlow()==null){
    			bean.getCell(18).setCellValue(" ");
    		}else{
    		    bean.getCell(18).setCellValue(TallySplitList.get(j).getFlow());
    		}
    		if(TallySplitList.get(j).getAllValumes()==null){
    			bean.getCell(19).setCellValue("");
    		}else{
    		    bean.getCell(19).setCellValue(TallySplitList.get(j).getAllValumes().toString());
    		}
    		if(TallySplitList.get(j).getPieceValume()==null){
    			bean.getCell(20).setCellValue("");
    		}else{
    		    bean.getCell(20).setCellValue(TallySplitList.get(j).getPieceValume().toString());
    		}
    		
    		bean.getCell(21).setCellValue(this.getYesOrNo(TallySplitList.get(j).getUsefreightmac()));
    		bean.getCell(22).setCellValue(this.getYesOrNo(TallySplitList.get(j).getUseshipworkmac()));
    		bean.getCell(23).setCellValue(this.getYesOrNo(TallySplitList.get(j).getUseshipworkperson()));
    		bean.getCell(24).setCellValue(this.getYesOrNo(TallySplitList.get(j).getIsnight()));
    		bean.getCell(25).setCellValue(this.getYesOrNo(TallySplitList.get(j).getIsholiday()));
    		if(TallySplitList.get(j).getRemarkTxt()==null){
    			bean.getCell(26).setCellValue(" ");
    		}else{
    		    bean.getCell(26).setCellValue(TallySplitList.get(j).getRemarkTxt());
    		}
    		
        }
        OutputStream output;
		try {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode("计费数据审核汇总", "UTF-8") + ".xls"); 
			ServletOutputStream arg35 = response.getOutputStream();
			ex.write(arg35);
			arg35.flush();
			arg35.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getYesOrNo (String flag){
		if(HdUtils.strNotNull(flag) && "1".equals(flag)){
			return "√";
		}else{
			return "×";
		}
	}
	@Override
	@Transactional
	public HdMessageCode dealS(String shipNo,String workTyp) {
		List<Object> list = new ArrayList<Object>();
		list.add(shipNo);
		list.add(workTyp);
		List<String> list1 = new ArrayList<>();
		String outInfo = "";
		JpaUtils.executeOracleProcWithResult("P_TALLY_DEAL", list, list1, list.size()+1);
		outInfo = list1.get(0);
		System.out.println(outInfo);
		/*if (!"ok".equals(outInfo)){
			throw new HdRunTimeException(outInfo);
		}*/
		return HdUtils.genMsg(outInfo);
		
		
	}
	@Override
	@Transactional
	public HdMessageCode dealT(String contractNo,String workTyp) {
		List<Object> list = new ArrayList<Object>();
		list.add(contractNo);
		list.add(workTyp);
		List<String> list1 = new ArrayList<>();
		String outInfo = "";
		JpaUtils.executeOracleProcWithResult("P_TALLY_DEAL", list, list1, list.size()+1);
		outInfo = list1.get(0);
		System.out.println(outInfo);
		/*if (!"ok".equals(outInfo)){
			throw new HdRunTimeException(outInfo);
		}*/
		return HdUtils.genMsg(outInfo);
		
		
	}
	@Override
	public TallySplit findone(String id) {		
        TallySplit tallysplit= JpaUtils.findById( TallySplit.class, id);
        return tallysplit;
	}

    @Override
	@Transactional
	public HdMessageCode removeAll(String ids) {
		List<String> shipNoList = HdUtils.paraseStrs(ids);
		for (String shipNo : shipNoList) {
			TallySplit tallysplit=JpaUtils.findById(TallySplit.class, shipNo);
			if("1".equals(tallysplit.getUpId())){
				throw new HdRunTimeException("已经提交的数据不能删除");
			}
			JpaUtils.remove(TallySplit.class, shipNo);
		}
		return HdUtils.genMsg();
	} 
    
    @Override
   	@Transactional
   	public HdMessageCode removeAllHY(String ids) {
   		List<String> shipNoList = HdUtils.paraseStrs(ids);
   		for (String shipNo : shipNoList) {
   			JpaUtils.remove(TallySplit.class, shipNo);
   		}
   		return HdUtils.genMsg();
   	} 
    
    @Override
	@Transactional
	public HdMessageCode submitAll(String ids, String type) {
		List<String> idList = HdUtils.paraseStrs(ids);
		for (String id : idList) {
			if ("UP".equals(type)){
				TallySplit bean = JpaUtils.findById(TallySplit.class, id);
				if (bean != null) {
					bean.setUpId("1");//提交标志 为1代表已提交
					JpaUtils.update(bean);
				}
			}
			if ("UNDOUP".equals(type)){
				TallySplit bean = JpaUtils.findById(TallySplit.class, id);
				if (bean != null) {
					if("1".equals(bean.getCheckId())){
						throw new HdRunTimeException("已经审核的数据不能取消提交");
					}
					bean.setUpId("0");//提交标志 为0代表未提交，即取消了提交
					JpaUtils.update(bean);
				}
			}
			/*if ("DOWN".equals(type)){
				TallySplit bean = JpaUtils.findById(TallySplit.class, id);
				if (bean != null) {
					bean.setUpId("2");//提交标志 为2代表已驳回
					JpaUtils.update(bean);
				}
			}*/
			if ("DOWN".equals(type)){
				TallySplit bean = JpaUtils.findById(TallySplit.class, id);
				if (bean != null) {
					bean.setCheckId("0");//驳回功能，审核标志改为0
					bean.setUpId("2");//提交标志 为2代表已驳回
					JpaUtils.update(bean);
				}
			}
			if ("CHECK".equals(type)){
				TallySplit bean = JpaUtils.findById(TallySplit.class, id);
				if (bean != null) {
					bean.setCheckId("1");//审核标志 为1代表审核通过
					JpaUtils.update(bean);
				}
			}
			
		}
		return HdUtils.genMsg();
	} 
	
	@Override
	@Transactional
	public void remove(String id) {
		JpaUtils.remove(TallySplit.class,id);
	}

	@Override
	@Transactional
	public HdMessageCode saveone(TallySplit tallysplit) {		
		if(HdUtils.strIsNull(tallysplit.getId())){
			tallysplit.setId(HdUtils.genUuid());
			JpaUtils.save(tallysplit);}
        else
			JpaUtils.update(tallysplit);
		return HdUtils.genMsg(tallysplit);
	
	}
	@Override
	@Transactional
	public HdMessageCode save(HdEzuiSaveDatagridData<TallySplit> hdEzuiSaveDatagridData) {
		List<TallySplit> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		List<TallySplit> updateList=hdEzuiSaveDatagridData.getUpdatedRows();
		for(TallySplit bean : insertList){
			bean.setId(HdUtils.generateUUID());
			bean.setUpId("0");
			bean.setCheckId("0");
		}
		for(TallySplit bean : updateList){//已经提交的数据不能编辑
			if("1".equals(bean.getUpId())){
				throw new HdRunTimeException("已经提交的数据不能编辑");
			}
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Override
	@Transactional
	public HdMessageCode saveHY(HdEzuiSaveDatagridData<TallySplit> hdEzuiSaveDatagridData) {
		List<TallySplit> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		List<TallySplit> updateList=hdEzuiSaveDatagridData.getUpdatedRows();
		for(TallySplit bean : insertList){
			bean.setId(HdUtils.generateUUID());
			//bean.setUpId("0");
			bean.setCheckId("0");
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Override
	@Transactional
	public HdMessageCode billCheck(String type, String shipNo) {
		List<Object> list = new ArrayList<Object>();
		list.add(shipNo);
		list.add(type);
		List<String> list1 = new ArrayList<>();
		String outInfo = "";
		JpaUtils.executeOracleProcWithResult("P_TALLYSPLIT_BILL_CHECK", list, list1, list.size()+1);
		
		outInfo = list1.get(0);
		if(HdUtils.strNotNull(outInfo)&&"ok".equals(outInfo)){
			return HdUtils.genMsg();
		}else{
			HdMessageCode code = new HdMessageCode();
			code.setCode("-1");
			code.setMessage(outInfo);
			return code;
		}
	} 
	
	@Override
	@Transactional
	public HdMessageCode billCheckCurd(String type, String shipNo) {
		List<Object> list = new ArrayList<Object>();
		list.add(shipNo);
		list.add(type);
		List<String> list1 = new ArrayList<>();
		String outInfo = "";
		JpaUtils.executeOracleProcWithResult("P_TALLYSPLIT_LYBILL_CHECK", list, list1, list.size()+1);
		outInfo = list1.get(0);
		if(HdUtils.strNotNull(outInfo)&&"ok".equals(outInfo)){
			return HdUtils.genMsg();
		}else{
			HdMessageCode code = new HdMessageCode();
			code.setCode("-1");
			code.setMessage(outInfo);
			return code;
		}
		 
	} 
	
}

