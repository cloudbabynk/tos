package net.huadong.tech.shipbill.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Random;

import org.springframework.ui.Model;
import net.huadong.tech.shipbill.entity.TallySplit;
import net.huadong.tech.shipbill.service.TallySplitService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.ShipDispatchLog;

import org.springframework.stereotype.Controller;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.VWorkCommand;

/**
 * @author yuliang
 * @date 2020-07-14
 */
@Controller
@RequestMapping("webresources/login/shipbill/TallySplit")  //注意路径
public class TallySplitController  {
	
	@Autowired
	private TallySplitService tallySplitService; 
	
	@RequestMapping(value = "/tallysplit.htm")
	public String pageTh(Model model, String Type) {
		Random random = new Random();
		model.addAttribute("Type", Type);
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/tallysplit";
	}
	@RequestMapping(value = "/tallysplitSingle.htm")
	public String tallysplitSingle(Model model, String Type) {
		Random random = new Random();
		model.addAttribute("Type", Type);
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/tallysplitSingle";
	}
	
	@RequestMapping(value = "/tallysplithy.htm")
	public String pageHy(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/tallysplithy";
	}
	
	@RequestMapping(value = "/tallysplithySingle.htm")
	public String pageHySingle(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/tallysplithySingle";
	}
	
	@RequestMapping(value = "/tallysplitcurd.htm")
	public String pageThx(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/tallysplitcurd";
	}
	
	
	@RequestMapping(value = "/zysj.htm")
	public String pageTg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/zysj";
	}
	@RequestMapping(value = "/zysjcurd.htm")
	public String pageTgCurd(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/zysjcurd";
	}
	@RequestMapping(value = "/zysjSingle.htm")
	public String zysjSingle(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/shipbill/zysjSingle";
	}
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/find", method={RequestMethod.POST})
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return tallySplitService.find(hdQuery);
	}
	public HdEzuiDatagridData findExcel( HdQuery hdQuery,String ids) {
	    return tallySplitService.findExcel(hdQuery,ids);
	}
	@RequestMapping(value = "/dealS")
	@ResponseBody
	public HdMessageCode dealS(String shipNo,String workTyp) {
	    return tallySplitService.dealS(shipNo,workTyp);
	}
	@RequestMapping(value = "/dealT")
	@ResponseBody
	public HdMessageCode dealT(String contractNo,String workTyp) {
	    return tallySplitService.dealT(contractNo,workTyp);
	}
	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findone", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public TallySplit findone(String id) {
		if (HdUtils.strIsNull(id)) {// 增加时默认初值
			TallySplit entity = new TallySplit();
			return entity;
		}
		return tallySplitService.findone(id);
	}
	
	 /**
     * 单条删除
     */
    @RequestMapping(value = "/remove", method={RequestMethod.POST})
    @ResponseBody
    public HdMessageCode remove(@RequestBody TallySplit tallysplit) {
        tallySplitService.remove(tallysplit.getId());
        return HdUtils.genMsg();
    }

	
	/**
	 * 计费数据审核批量删除
	 */
	@RequestMapping(value = "/removeAllHY", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode removeAllHY(String ids) {
		tallySplitService.removeAllHY(ids);
		return HdUtils.genMsg();
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		tallySplitService.removeAll(ids);
		return HdUtils.genMsg();
	}
	
	/**
	 * 批量提交和回退
	 */
	@RequestMapping(value = "/submitAll", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode submitAll(String ids, String type) {
		tallySplitService.submitAll(ids, type);
		return HdUtils.genMsg();
	}
	/**
     * 保存资源信息
     *
     * @param TallySplit
     * @return
     */
	@RequestMapping(value = "/saveone", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode saveone(@RequestBody TallySplit tallysplit) {
		return tallySplitService.saveone(tallysplit);
	}
	/**
     * 保存资源信息
     *
     * @param TallySplit
     * @return
     */
	@RequestMapping(value = "/save", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode saveAll(@RequestBody HdEzuiSaveDatagridData<TallySplit> gridData) {
		return tallySplitService.save(gridData);
	}
	
	/**
     * 计费数据审核保存资源信息
     *
     * @param TallySplit
     * @return
     */
	@RequestMapping(value = "/saveHY", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode saveAllHY(@RequestBody HdEzuiSaveDatagridData<TallySplit> gridData) {
		return tallySplitService.saveHY(gridData);
	}
	
	
	/**
	 * 提单校验
	 * @param type
	 * @param shipNo
	 * @return
	 */
	@RequestMapping(value = "/billCheck", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode billCheck(String type, String shipNo) {
		
		return tallySplitService.billCheck(type, shipNo);
	}
	
	/**
	 * 提单校验
	 * @param type
	 * @param shipNo
	 * @return
	 */
	@RequestMapping(value = "/billCheckCurd", method={RequestMethod.POST})
	@ResponseBody
	public HdMessageCode billCheckCurd(String type, String shipNo) {
		
		return tallySplitService.billCheckCurd(type, shipNo);
	}
	/**
	 * 导出Excel
	 * 
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @param response
	 */
	/*@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others,String ids, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.find(params);
	    List<TallySplit> list = data.getRows();
		//数据处理
		for(TallySplit t : list){
			t.setCheckId(this.getYesOrNo(t.getCheckId()));
			t.setOverLengthId(this.getYesOrNo(t.getOverLengthId()));
			t.setUsefreightmac(this.getYesOrNo(t.getUsefreightmac()));
			t.setUseshipworkmac(this.getYesOrNo(t.getUseshipworkmac()));
			t.setUseshipworkperson(this.getYesOrNo(t.getUseshipworkperson()));
			t.setIsnight(this.getYesOrNo(t.getIsnight()));
			t.setIsholiday(this.getYesOrNo(t.getIsholiday()));
			if(HdUtils.strNotNull(t.getUpId())){
				if("1".equals(t.getUpId())){
					t.setUpId("已提交");
				}else{
					t.setUpId("驳回");
				}
			}else{
				t.setUpId("未提交");
			}
			if(HdUtils.strNotNull(t.getYwTyp())){
				if("1".equals(t.getYwTyp())){
					t.setYwTyp("陆运");
				}else if("0".equals(t.getYwTyp())){
					t.setYwTyp("航运");
				}
			}
			if(null!=t.getInDte() && null!= t.getOutDte()){
				long storeday = (t.getOutDte().getTime() - t.getInDte().getTime())/(24*60*60*1000);
				t.setStoreDays(String.valueOf(storeday));
			}
		}
		
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}*/
	/**
	 * 导出Excel
	 * 
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others,String ids, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		//HdEzuiDatagridData data = this.find(params);
		HdEzuiDatagridData data = this.findExcel(params,ids);//修改成按照选择的行进行导出
		tallySplitService.exportExcelJF(data,response);//新增加方法excel中添加行，显示合计数据
		
	}
	
	public String getYesOrNo (String flag){
		if(HdUtils.strNotNull(flag) && "1".equals(flag)){
			return "√";
		}else{
			return "×";
		}
	}
	/*流向下拉
	@RequestMapping(value = "/findFlow", method={RequestMethod.POST})
	@ResponseBody
	public List<EzDropBean> findFlow() {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		String jpql= " select a  from  TallySplit a  where 1=1 and a.flow  IS NOT NULL ";
		QueryParamLs params=new QueryParamLs();
		List<TallySplit>  ls=JpaUtils.findAll(jpql, params);
		for(TallySplit ts:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(ts.getFlow());
			drop.setLabel(ts.getFlowNam());
			list.add(drop);
		}
		return list;
	}*/
}

