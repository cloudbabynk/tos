package net.huadong.tech.ship.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.HdShipPicBerthPlanShipVisit;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipFee;
import net.huadong.tech.ship.entity.ShipStat;
import net.huadong.tech.ship.entity.Shipcount;
import net.huadong.tech.ship.service.ShipService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/Ship")
public class ShipController {

	@Autowired
	private ShipService shipService;
	
	// ????????????
		@RequestMapping(value = "/waiqiandun.htm")
	public String pageWQD(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/waiqiandun";
	}
	// ????????????
	@RequestMapping(value = "/ship.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/ship";
	}
	
	// ????????????
	@RequestMapping(value = "/zcqrs.htm")
	public String pageZcqrs(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/zcqrs";
	}
	
	// ????????????
	@RequestMapping(value = "/ckhwcqmx.htm")
	public String pageCkhwcq(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/ckhwcqmx";
	}
	
	// ????????????
	@RequestMapping(value = "/hybg.htm")
	public String pageHybg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/hybg";
	}

	// ????????????
	@RequestMapping(value = "/dashboard.htm")
	public String pageDash(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/dashboard";
	}

	// ????????????
	@RequestMapping(value = "/qianzhengdan.htm")
	public String pageQz(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/qianzhengdan";
	}

	// ????????????
	@RequestMapping(value = "/shipzong.htm")
	public String pageZong(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipzong";
	}
	
	// ????????????
	@RequestMapping(value = "/shipzongnew.htm")
	public String pageZongNew(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipzongnew";
	}

	// ????????????
	@RequestMapping(value = "/shipjt.htm")
	public String pageJt(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipjt";
	}
	
	// ????????????
	@RequestMapping(value = "/shipjtnew.htm")
	public String pageJtNew(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipjtnew";
	}

	// ????????????
	@RequestMapping(value = "/shipgs.htm")
	public String pageGs(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipgs";
	}
	
	// ????????????
	@RequestMapping(value = "/shipgsnew.htm")
	public String pageGsNew(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shipgsnew";
	}

	// ????????????
	@RequestMapping(value = "/nyk.htm")
	public String pageNy(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/nyk";
	}

	// ????????????
	@RequestMapping(value = "/overdate.htm")
	public String pageOd(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/overdate";
	}

	// ????????????
	@RequestMapping(value = "/workdata.htm")
	public String pageWk(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/workdata";
	}

	// ????????????
	@RequestMapping(value = "/workdatadaily.htm")
	public String pageWdl(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/workdatadaily";
	}

	// ????????????
	@RequestMapping(value = "/dangerliquid.htm")
	public String pageDl(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/dangerliquid";
	}

	// ????????????
	@RequestMapping(value = "/carnum.htm")
	public String pageCn(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/carnum";
	}

	// ????????????
	@RequestMapping(value = "/tuntuliang.htm")
	public String pageTt(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/tuntuliang";
	}

	// ????????????
	@RequestMapping(value = "/intloaddaily.htm")
	public String pageInl(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/intloaddaily";
	}

	// ????????????
	@RequestMapping(value = "/domloaddaily.htm")
	public String pageDol(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/domloaddaily";
	}

	// ????????????
	@RequestMapping(value = "/intdischdaily.htm")
	public String pageIdd(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/intdischdaily";
	}

	// ????????????
	@RequestMapping(value = "/domdischdaily.htm")
	public String pageDdd(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/report/domdischdaily";
	}
	// ????????????
		@RequestMapping(value = "/shipdatamonth.htm")
		public String pageSdm(Model model) {
			Random random = new Random();
			model.addAttribute("radi", random.nextInt() * 1000);
			return "system/report/shipdatamonth";
		}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/gentree", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentree() {
		ShipTask task=new ShipTask();
		return task.getTree();
	}
	
	
	/**
	 * ?????????????????????
	 */
	@RequestMapping(value = "/uploadAll")
	@ResponseBody
	public String uploadAll(String shipNos) {
		return shipService.uploadAll(shipNos);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getCCShipDataDrop")
	@ResponseBody
	public List<EzDropBean> getCCShipDataDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		QueryParamLs params = new QueryParamLs();
		List<CShipData> ls = null;
		String jpql = " select a from CShipData a where 1=1";
		ls = JpaUtils.findAll(jpql, params);
		for (CShipData cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getShipCodId());
			drop.setLabel(cc.getShipShort() + "-" + cc.getcShipNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipNos) {
		shipService.removeAll(shipNos);
		return HdUtils.genMsg();
	}

	@RequestMapping(value = "/findAxis")
	@ResponseBody
	public String findAxis(String width, String height, String daySum, String startdate) {

		return shipService.findAxis(width, height, daySum, startdate);
	}

	@RequestMapping(value = "/getBerthTim")
	@ResponseBody
	public String getBerthTim(String ptim, String startdate, String enddate, String height) {
		return shipService.getBerthTim(ptim, startdate, enddate, height);
	}

	@RequestMapping(value = "/showBerths")
	@ResponseBody
	public String showBerths(String startdate, String enddate, String width, String height, String daySum) {
		return shipService.showBerths(startdate, enddate, width, height, daySum);
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
		return shipService.find(hdQuery);
	}
	
	/**
	 * ???????????? ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDashShip")
	@ResponseBody
	public String findDashShip() {
		return shipService.findDashShip();
	}
	
	/**
	 * ???????????? ??????????????????(table??????)
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDashShipTable")
	@ResponseBody
	public List<Ship> findDashShipTable() {
		return shipService.findDashShipTable();
	}

	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShipJt")
	@ResponseBody
	public HdEzuiDatagridData findShipJt(@RequestBody HdQuery hdQuery) {
		return shipService.findShipJt(hdQuery);
	}
	
	/**
	 * ?????????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShipJtNew")
	@ResponseBody
	public HdEzuiDatagridData findShipJtNew(@RequestBody HdQuery hdQuery) {
		return shipService.findShipJtNew(hdQuery);
	}

	@RequestMapping(value = "/showDetail")
	@ResponseBody
	public String showDetail(String type, String width, String height, String berth1, String cable1, String berth2,
			String cable2, String tim1, String tim2, String date1, String date2) {
		return shipService.showDetail(type, width, height, berth1, cable1, berth2, cable2, tim1, tim2, date1, date2);
	}

	@RequestMapping(value = "/findMsg")
	@ResponseBody
	public HdMessageCode findMsg(String start, String end, String interval) {
		return shipService.findMsg(start, end, interval);
	}
	
	@RequestMapping(value = "/findExpandMsg")
	@ResponseBody
	public HdMessageCode findExpandMsg(String start, String end, String interval) {
		return shipService.findExpandMsg(start, end, interval);
	}
	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public Ship findone(String shipNo) {
		if (HdUtils.strIsNull(shipNo)) {
			Ship ship = new Ship();
			ship.setShipNo(CommonUtil.getId());
			// ???????????????????????????
			ship.setShipStat(ship.STATUS_1);
			ship.setBerthCod(ship.GK);
			return ship;
		}
		return shipService.findone(shipNo);
	}

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<Ship> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipService.save(gridData);
	}

	/**
	 * ????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/saveSbjf")
	@ResponseBody
	public HdMessageCode saveSbjf(@RequestBody ShipFee shipFee) {
		// CommonUtil.initEntity(gridData);
		return shipService.saveSbjf(shipFee);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/importBilling")
	@ResponseBody
	public HdMessageCode importBilling(String shipNo, String ieId, String dockCod, String feeTon) {
		shipService.importBilling(shipNo, ieId, dockCod, feeTon);
		return HdUtils.genMsg();
	}

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/savezong")
	@ResponseBody
	public HdMessageCode savezong(@RequestBody HdEzuiSaveDatagridData<Ship> gridData, String shipNo) {
		// CommonUtil.initEntity(gridData);
		return shipService.savezong(gridData, shipNo);
	}
	
	
	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/saveNewZong")
	@ResponseBody
	public HdMessageCode saveNewZong(@RequestBody HdEzuiSaveDatagridData<Ship> gridData, String shipNo) {
		// CommonUtil.initEntity(gridData);
		return shipService.saveNewZong(gridData, shipNo);
	}

	/*
	 * ??????????????????
	 */
	@RequestMapping("/saveBerth")
	@ResponseBody
	public HdMessageCode saveBerth(@RequestBody HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		return shipService.saveBerth(hdShipPicBerthPlanShipVisit);
	}
	
	
	/*
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping("/saveBerthAndShip")
	@ResponseBody
	public HdMessageCode saveBerthAndShip(@RequestBody HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		return shipService.saveBerthAndShip(hdShipPicBerthPlanShipVisit);
	}

	/*
	 * form??????
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody Ship ship) {
		return shipService.saveone(ship);
	}

	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findBerth")
	@ResponseBody
	public HdShipPicBerthPlanShipVisit findBerth(String shipNo) {
		if (HdUtils.strIsNull(shipNo)) {
			throw new HdRunTimeException("???????????????");
		}
		return shipService.findBerth(shipNo);
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/removeShipPlan")
	@ResponseBody
	public HdMessageCode removeShipPlan(@RequestBody HdShipPicBerthPlanShipVisit hdShipPicBerthPlanShipVisit) {
		shipService.removeShipPlan(hdShipPicBerthPlanShipVisit);
		return HdUtils.genMsg();
	}
	/**
	 * ?????????????????????
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getBerthPlanShip")
	@ResponseBody
	public HdMessageCode getBerthPlanShip(@RequestBody Map map) {
		return shipService.getBerthPlanShip(map);
	}
}
