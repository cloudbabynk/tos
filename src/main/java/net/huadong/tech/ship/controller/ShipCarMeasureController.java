package net.huadong.tech.ship.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.ship.entity.ShipCarMeasure;
import net.huadong.tech.ship.service.ShipCarMeasureService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.springboot.core.criterialquery.HdExportExcel;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/ship/ShipCarMeasure")
public class ShipCarMeasureController  {
	
	
	@Autowired
	private ShipCarMeasureService shipCarMeasureService; 
	
	@RequestMapping(value = "/shipcarmeasure.htm")
    public String page(Model model) {
        return "system/ship/shipcarmeasure";
    }
	
	@RequestMapping(value = "/shipcarmeasureck.htm")
    public String pageCk(Model model) {
        return "system/ship/shipcarmeasureck";
    }
	
	@RequestMapping(value = "/shipcarmeasurea.htm")
    public String pageA(Model model) {
        return "system/ship/shipcarmeasurea";
    }
	
	@RequestMapping(value = "/shipcarmeasureb.htm")
    public String pageB(Model model) {
        return "system/ship/shipcarmeasureb";
    }
    
    @RequestMapping(value = "/shipcarmeasureform.htm")
    public String form(String id, Model model) {
    	model.addAttribute("id", id);
        return "system/ship/shipcarmeasureform";
    }
    
    @RequestMapping(value = "/shipcarmeasureckform.htm")
    public String Ckform(String id, Model model) {
    	model.addAttribute("id", id);
        return "system/ship/shipcarmeasureckform";
    }
    
    @RequestMapping(value = "/shipcarmeasureformpl.htm")
    public String Plform(String ids, Model model) {
    	model.addAttribute("ids", ids);
        return "system/ship/shipcarmeasureformpl";
    }
    @RequestMapping(value = "/shipcarmeasurecka.htm")
    public String pageckA(Model model) {
        return "system/ship/shipcarmeasurecka";
    }
    @RequestMapping(value = "/shipcarmeasureckb.htm")
    public String pageckB(Model model) {
        return "system/ship/shipcarmeasureckb";
    }

	/**
     * ??????????????????
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
	    return shipCarMeasureService.find(hdQuery);
	}
	
	/**
     * ??????????????????
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findFcd")
	@ResponseBody
	public HdEzuiDatagridData findFcd(@RequestBody HdQuery hdQuery) {
	    return shipCarMeasureService.findFcd(hdQuery);
	}
	/**
     * ??????????????????
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findFcdck")
	@ResponseBody
	public HdEzuiDatagridData findFcdck(@RequestBody HdQuery hdQuery) {
	    return shipCarMeasureService.findFcdck(hdQuery);
	}
	
	
	
		/**
	 * ??????
	 * 
	 * @param params
	 *            ??????
	 * @return ????????????
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipCarMeasure findone(String id) {
		if (HdUtils.strIsNull(id)) {// ?????????????????????
			ShipCarMeasure entity = new ShipCarMeasure();
			return entity;
		}
		return shipCarMeasureService.findone(id);
	}

	/**
     * ??????????????????
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/saveone")
	@ResponseBody	
	public HdMessageCode saveone(@RequestBody ShipCarMeasure entity) {
		return shipCarMeasureService.saveone(entity);
	}
	
	
	/**
     * ?????????????????????????????????
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/savepl")
	@ResponseBody	
	public HdMessageCode savepl(@RequestBody ShipCarMeasure entity, String ids) {
		return shipCarMeasureService.savepl(entity, ids);
	}
	
	
	/**
     * ??????????????????
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/updateAll")
	@ResponseBody	
	public HdMessageCode updateAll(String queueIds) {
		return shipCarMeasureService.updateAll(queueIds);
	}
    
    
    /**
     * ??????????????????
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody   
    public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipCarMeasure> gridData) {
        return shipCarMeasureService.save(gridData);
    }
    
   /* @RequestMapping(value = "/exportExcelJk", method = RequestMethod.POST)
	@ResponseBody 
	public void exportExcelJk(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others,HttpServletResponse response) {
    	if(hdConditions==null) {}
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.findFcd(params);
		HdExportExcel hdExportExcel=params.getHdConditions().getHdExportExcel();
		hdExportExcel.setColumnTitles("??????,??????,?????????,??????,?????????,??????,?????????,??????,??????");
		hdExportExcel.setColumnNames("shipNam,voyage,billNo,carTypNam,carVin,carNum,carSize,carVol,carWeight");
		HdEzuiExportFile.exportExcelEx(hdExportExcel, response, data); 
	}*/
    //??????????????????????????????excel?????????
    @RequestMapping(value = "/exportExcelJk", method = RequestMethod.POST)
	@ResponseBody 
	public void exportExcelJk(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others,String ids,HttpServletResponse response) {
    	if(hdConditions==null) {}
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.exportExcel(params,ids);
		HdExportExcel hdExportExcel=params.getHdConditions().getHdExportExcel();
		hdExportExcel.setColumnTitles("??????,??????,?????????,??????,?????????,??????,?????????,??????,??????");
		//hdExportExcel.setColumnNames("shipNam,voyage,billNo,carTypNam,carVin,carNum,carSize,carVol,carWeight");
		hdExportExcel.setColumnNames("SHIP_NAM,VOYAGE,BILL_NO,CAR_TYP_NAM,CAR_VIN,CAR_NUM,CAR_SIZE,CAR_VOL,CAR_WEIGHT");
		HdEzuiExportFile.exportExcelEx(hdExportExcel, response, data); 
	}
   private HdEzuiDatagridData exportExcel(HdQuery params, String ids) {
	   return shipCarMeasureService.exportExcel(params,ids);
    }


    
    @RequestMapping(value = "/exportExcelCk", method = RequestMethod.POST)
	@ResponseBody 
	public void exportExcelCk(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others,String ids,HttpServletResponse response) {
    	if(hdConditions==null) {}
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		//HdEzuiDatagridData data = this.find(params);
		HdEzuiDatagridData data = this.exportExcelCk(params,ids);
		HdExportExcel hdExportExcel=params.getHdConditions().getHdExportExcel();
		//hdExportExcel.setColumnTitles("??????,??????,??????,??????,?????????,??????,?????????,??????,??????");
		//hdExportExcel.setColumnNames("shipNam,voyage,carTypVersion,carTypNam,oldCarSize,oldCarVol,carSize,carVol,consignNam");
		hdExportExcel.setColumnTitles("??????,??????,??????,??????,?????????,??????,??????,?????????,??????");
		hdExportExcel.setColumnNames("SHIP_NAM,VOYAGE,CAR_TYP_VERSION,CAR_TYP_NAM,CAR_VIN,CAR_NUM,OLD_CAR_VOL,CAR_SIZE,CAR_VOL");
		
		HdEzuiExportFile.exportExcelEx(hdExportExcel, response, data); 
	}
    private HdEzuiDatagridData exportExcelCk(HdQuery params, String ids) {
 	   return shipCarMeasureService.exportExcelCk(params,ids);
     }
}
