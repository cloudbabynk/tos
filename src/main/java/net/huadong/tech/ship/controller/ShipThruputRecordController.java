package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipThruputRecord;
import net.huadong.tech.ship.service.ShipThruputRecordService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/ShipThruputRecord")
public class ShipThruputRecordController {

	@Autowired
	private ShipThruputRecordService shipThruputRecordService;

	// 菜单进入
	@RequestMapping(value = "/shipthruputrecord.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipthruputrecord";
	}

	// 菜单进入
	@RequestMapping(value = "/tuntuliang.htm")
	public String pageTTL(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/tuntuliang";
	}

	// 菜单进入
	@RequestMapping(value = "/shipthruputrecordquery.htm")
	public String pageSQ(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipthruputrecordquery";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String sthruputIds) {
		shipThruputRecordService.removeAll(sthruputIds);
		return HdUtils.genMsg();
	}

	/**
	 * 批量审核
	 */
	@RequestMapping(value = "/checkAll")
	@ResponseBody
	public HdMessageCode checkAll(String sthruputIds) {
		shipThruputRecordService.checkAll(sthruputIds);
		return HdUtils.genMsg();
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
		return shipThruputRecordService.find(hdQuery);
	}

	// 吞吐量查询
	@RequestMapping(value = "/findQ")
	@ResponseBody
	public HdEzuiDatagridData findQ(@RequestBody HdQuery hdQuery) {
		return shipThruputRecordService.findQ(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipThruputRecord findone(String sthruputId) {
		return shipThruputRecordService.findone(sthruputId);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipThruputRecord> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipThruputRecordService.save(gridData);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipThruputRecord shipThruputRecord) {

		return shipThruputRecordService.saveone(shipThruputRecord);
	}

	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findShipThruputRecord")
	@ResponseBody
	public HdMessageCode findShipThruputRecord(String sthruputId) {
		return shipThruputRecordService.findShipThruputRecord(sthruputId);
	}

	@RequestMapping(value = "/gentree", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentree() {
		return shipThruputRecordService.findTree();
	}
	
	@RequestMapping(value = "/getShipTueInfo")
	@ResponseBody

	public HdMessageCode getShipTueInfo(@RequestBody Map map) {
		return shipThruputRecordService.getShipTueInfo( map);
	}
	
	

	@RequestMapping(value = "/getCDock")
	@ResponseBody
	public List<EzDropBean> getCDockDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CDock a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CDock> ls = JpaUtils.findAll(jpql, params);
		for (CDock cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getDockCod());
			drop.setLabel(cc.getDockNam());
			list.add(drop);
		}
		return list;
	}
	/**
	 * 道闸编号下拉
	 * @return
	 */
	@RequestMapping(value = "/getLane")
	@ResponseBody
	public List<Map<String, Object>> getLane(String unit) {
		String sql="select t.LANE_NO,t.LANE_NAM from vic_lane t where t.LANE_NAM<>'X' ";
		if (HdUtils.strNotNull(unit)) {
			sql += " and t.LANE_NAM like '%"+unit+"%' ";
		}
		sql += " order by LANE_NO asc ";
		List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		return list;
	}
	/**
	 * 吞吐量上报集团集团接口
	 * @param shipNo
	 * @return
	 */
	@RequestMapping(value = "/sendjt")
	@ResponseBody
	public String sendjt(String sthruputIds) {
		return	shipThruputRecordService.sendjt(sthruputIds);
	}
	
	/**
	 * 车类下拉
	 */
	@RequestMapping(value = "/getUnitCargo")
	@ResponseBody
	public List<EzDropBean> getUnitCargo(String q,String iEId,String tradeId) {
			HdMessageCode HdMessageCode=shipThruputRecordService.getUnitCargo(null);
			List<EzDropBean> lirtLst=new ArrayList<EzDropBean>();
			List<Map> list=(List) HdMessageCode.getData();
			for(Map cc:list){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.get("TTL_TYP")+"_"+cc.get("CARGO_CODE"));
				drop.setLabel(cc.get("CARGO_NAME")+"");
				lirtLst.add(drop);
			}
			return lirtLst;
	}
	
}
