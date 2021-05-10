package net.huadong.tech.ship.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.entity.CShipLine;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.ShipTrendExcel;
import net.huadong.tech.ship.service.ShipTrendService;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.List2Excel;
import net.huadong.tech.util.ShipTrendExcelTest;
import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/ShipTrend")
public class ShipTrendController {

	@Autowired
	private ShipTrendService shipTrendService;

	// 菜单进入
	@RequestMapping(value = "/shiptrend.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shiptrend";
	}

	// 菜单进入
	@RequestMapping(value = "/shiptrendjt.htm")
	public String pageJtTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shiptrendjt";
	}

	// 菜单进入
	@RequestMapping(value = "/shiptrendselect.htm")
	public String pageXz(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shiptrendselect";
	}

	// 菜单进入
	@RequestMapping(value = "/shiptrendgs.htm")
	public String pageGs(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/shiptrendgs";
	}

	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/getCCShipDataDrop")
	@ResponseBody
	public List<EzDropBean> getCCShipDataDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CShipData a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CShipData> ls = JpaUtils.findAll(jpql, params);
		for (CShipData cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getShipCod());
			drop.setLabel(cc.getcShipNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipTrendsIds) {
		shipTrendService.removeAll(shipTrendsIds);
		return HdUtils.genMsg();
	}

	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/importShipTrend")
	@ResponseBody
	public HdMessageCode importShipTrend(String shipTrendsId) {
		shipTrendService.importShipTrend(shipTrendsId);
		return HdUtils.genMsg();
	}

	/**
	 * 上报集团
	 */
	@RequestMapping(value = "/importAll")
	@ResponseBody
	public HdMessageCode importAll(String shipTrendsIds) {
		shipTrendService.importAll(shipTrendsIds);
		return HdUtils.genMsg();
	}

	/**
	 * 上报集团新调度
	 */
	@RequestMapping(value = "/uploadAll")
	@ResponseBody
	public HdMessageCode uploadAll(String shipTrendsIds) {
		shipTrendService.uploadAll(shipTrendsIds);
		return HdUtils.genMsg();
	}

	/**
	 * 变更删除
	 */
	@RequestMapping(value = "/dtdelete")
	@ResponseBody
	public HdMessageCode dtdelete(String shipTrendsId) {
		shipTrendService.dtdelete(shipTrendsId);
		return HdUtils.genMsg();
	}

	/**
	 * 撤销申请
	 */
	@RequestMapping(value = "/dtcancle")
	@ResponseBody
	public HdMessageCode dtcancle(String shipTrendsId) {
		shipTrendService.dtcancle(shipTrendsId);
		return HdUtils.genMsg();
	}

	/**
	 * 变更编辑
	 */
	@RequestMapping(value = "/dtchange")
	@ResponseBody
	public HdMessageCode dtchange(String shipTrendsId) {
		shipTrendService.dtchange(shipTrendsId);
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
		return shipTrendService.find(hdQuery);
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShipTrendJt")
	@ResponseBody
	public HdEzuiDatagridData findShipTrendJt(@RequestBody HdQuery hdQuery) {
		return shipTrendService.findShipTrendJt(hdQuery);
	}

	/**
	 * 集团查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/updateShipTrendJt")
	@ResponseBody
	public HdMessageCode updateShipTrendJt() {
		shipTrendService.updateShipTrendJt();
		return HdUtils.genMsg();
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipTrend findone(String shipTrendsId) {
		if (HdUtils.strIsNull(shipTrendsId)) {
			ShipTrend shipTrend = new ShipTrend();
			return shipTrend;
		}
		return shipTrendService.findone(shipTrendsId);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipTrend> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipTrendService.save(gridData);
	}

	/**
	 * 动态变更保存
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/saveDtedit")
	@ResponseBody
	public HdMessageCode saveDtedit(@RequestBody HdEzuiSaveDatagridData<ShipTrend> gridData) {
		// CommonUtil.initEntity(gridData);
		shipTrendService.saveDtedit(gridData);
		return HdUtils.genMsg();
	}

	/**
	 * 集团导入的保存
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/savejt")
	@ResponseBody
	public HdMessageCode savejt(@RequestBody HdEzuiSaveDatagridData<ShipTrend> gridData) {
		// CommonUtil.initEntity(gridData);
		return shipTrendService.savejt(gridData);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipTrend shipTrend) {

		return shipTrendService.saveone(shipTrend);
	}

	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	@ResponseBody
	public void exportExcel(String shipTrendsIds, String filename, HttpServletResponse response) {
		List<String> shipTrendsIdList = HdUtils.paraseStrs(shipTrendsIds);
		List<ShipTrendExcel> trendexcelList = new ArrayList<ShipTrendExcel>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		if (hashMap.containsKey("6510")) {
			hashMap.put("6510", hashMap.get("6510") + 1);
		} else {
			hashMap.put("6510", 1);
		}
		if (hashMap.containsKey("6511")) {
			hashMap.put("6511", hashMap.get("6511") + 1);
		} else {
			hashMap.put("6511", 1);
		}
		if (hashMap.containsKey("6512")) {
			hashMap.put("6512", hashMap.get("6512") + 1);
		} else {
			hashMap.put("6512", 1);
		}
		if (hashMap.containsKey("6520")) {
			hashMap.put("6520", hashMap.get("6520") + 1);
		} else {
			hashMap.put("6520", 1);
		}
		if (hashMap.containsKey("6521")) {
			hashMap.put("6521", hashMap.get("6521") + 1);
		} else {
			hashMap.put("6521", 1);
		}
		if (hashMap.containsKey("6522")) {
			hashMap.put("6522", hashMap.get("6522") + 1);
		} else {
			hashMap.put("6522", 1);
		}
		if (hashMap.containsKey("7410")) {
			hashMap.put("7410", hashMap.get("7410") + 1);
		} else {
			hashMap.put("7410", 1);
		}
		if (hashMap.containsKey("7411")) {
			hashMap.put("7411", hashMap.get("7411") + 1);
		} else {
			hashMap.put("7411", 1);
		}
		if (hashMap.containsKey("7412")) {
			hashMap.put("7412", hashMap.get("7412") + 1);
		} else {
			hashMap.put("7412", 1);
		}
		if (hashMap.containsKey("7420")) {
			hashMap.put("7420", hashMap.get("7420") + 1);
		} else {
			hashMap.put("7420", 1);
		}
		if (hashMap.containsKey("7421")) {
			hashMap.put("7421", hashMap.get("7421") + 1);
		} else {
			hashMap.put("7421", 1);
		}
		if (hashMap.containsKey("7422")) {
			hashMap.put("7422", hashMap.get("7422") + 1);
		} else {
			hashMap.put("7422", 1);
		}
		ArrayList<String> arr1 = new ArrayList<String>();
		for (String shipTrendsId : shipTrendsIdList) {
			ArrayList<String> allberth = new ArrayList<String>() {
				{
					add("6510");
					add("6511");
					add("6512");
					add("6520");
					add("6521");
					add("6522");
					add("7410");
					add("7411");
					add("7412");
					add("7420");
					add("7421");
					add("7422");
				}
			};
			ArrayList<String> arrayL = new ArrayList<String>() {
				{
					add("6510");
					add("6511");
					add("6512");
					add("6520");
					add("6521");
					add("6522");
				}
			};
			ArrayList<String> arrayL2 = new ArrayList<String>() {
				{
					add("7410");
					add("7411");
					add("7412");
					add("7420");
					add("7421");
					add("7422");
				}
			};
			ArrayList<String> arrayN1 = new ArrayList<String>() {
				{
					add("6510");
					add("6511");
					add("6512");
				}
			};
			ArrayList<String> arrayN2 = new ArrayList<String>() {
				{
					add("6520");
					add("6521");
					add("6522");
				}
			};
			ArrayList<String> arrayN13 = new ArrayList<String>() {
				{
					add("7410");
					add("7411");
					add("7412");
				}
			};
			ArrayList<String> arrayN14 = new ArrayList<String>() {
				{
					add("7420");
					add("7421");
					add("7422");
				}
			};
			//进左靠 进右靠 进
			ArrayList<String> jzjyj = new ArrayList<String>() {
				{
					add("1");
					add("11");
					add("12");
				}
			};
			
			//移左靠 移右靠 移
			ArrayList<String> yzyyy = new ArrayList<String>() {
				{
					add("2");
					add("21");
					add("22");
				}
			};
			ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, shipTrendsId);
			Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
			
			if (shipTrend.getTrendsBegArea().equals("6510")) {
				arr1.add("6510");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6510")) {
				arr1.add("6510");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("6511")) {
				arr1.add("6511");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6511")) {
				arr1.add("6511");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("6512")) {
				arr1.add("6512");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6512")) {
				arr1.add("6512");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("6520")) {
				arr1.add("6520");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6520")) {
				arr1.add("6520");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("6521")) {
				arr1.add("6521");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6521")) {
				arr1.add("6521");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("6522")) {
				arr1.add("6522");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("6522")) {
				arr1.add("6522");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7410")) {
				arr1.add("7410");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7410")) {
				arr1.add("7410");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7411")) {
				arr1.add("7411");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7411")) {
				arr1.add("7411");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7412")) {
				arr1.add("7412");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7412")) {
				arr1.add("7412");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7420")) {
				arr1.add("7420");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7420")) {
				arr1.add("7420");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7421")) {
				arr1.add("7421");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7421")) {
				arr1.add("7421");// 滚装
			}
			if (shipTrend.getTrendsBegArea().equals("7422")) {
				arr1.add("7422");// 滚装
			}
			if (shipTrend.getTrendsEndArea().equals("7422")) {
				arr1.add("7422");// 滚装
			}
			
			
			ShipTrendExcel shipTrendExcel = new ShipTrendExcel();

			CDock cdock = new CDock();

			if (arrayL.contains(shipTrend.getTrendsBegArea()) || arrayL.contains(shipTrend.getTrendsEndArea())) {

				cdock = JpaUtils.findById(CDock.class, "03406500");
				if (arrayN1.contains(shipTrend.getTrendsBegArea()) || arrayN1.contains(shipTrend.getTrendsEndArea())) {
					shipTrendExcel.setBerthNam("N1");
				}
				if (arrayN2.contains(shipTrend.getTrendsBegArea()) || arrayN2.contains(shipTrend.getTrendsEndArea())) {
					shipTrendExcel.setBerthNam("N2");
				}
			}
			if (arrayL2.contains(shipTrend.getTrendsEndArea()) || arrayL2.contains(shipTrend.getTrendsBegArea())) {
				cdock = JpaUtils.findById(CDock.class, "03409000");
				if (arrayN13.contains(shipTrend.getTrendsBegArea())
						|| arrayN13.contains(shipTrend.getTrendsEndArea())) {
					shipTrendExcel.setBerthNam("N13");
				}
				if (arrayN14.contains(shipTrend.getTrendsBegArea())
						|| arrayN14.contains(shipTrend.getTrendsEndArea())) {
					shipTrendExcel.setBerthNam("N14");
				}
			}
			shipTrendExcel.setDockNam(cdock.getDockNam());
			if ("环球".equals(cdock.getDockNam())){
				shipTrendExcel.setDockNam("环球滚装");
			}
			shipTrendExcel.setcShipNam(ship.getcShipNam());
			CShipData cshipdata = JpaUtils.findById(CShipData.class, ship.getShipCodId());
			if (cshipdata.getShipGrossWgt() != null) {
				shipTrendExcel.setShipGrossWGt(cshipdata.getShipGrossWgt());
			} else {
				shipTrendExcel.setShipGrossWGt(new BigDecimal("0"));
			}
			if (HdUtils.strNotNull(cshipdata.getCountryCod())) {
				CCountry ccountry = JpaUtils.findById(CCountry.class, cshipdata.getCountryCod());
				if (ccountry != null)
				shipTrendExcel.setCountryNam(ccountry.getcCountryNam());
			} else {
				shipTrendExcel.setCountryNam("");
			}
			if (jzjyj.contains(shipTrend.getTrendsTermini()) || (yzyyy.contains(shipTrend.getTrendsTermini()) && allberth.contains(shipTrend.getTrendsEndArea()))){
				Date toPortDate = new Date(shipTrend.getTrendsPlanTim().getTime());
				Date toPortTim = new Date(shipTrend.getTrendsPlanTim().getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
				String toportdate = sdf.format(toPortDate);
				String toporttime = sdf2.format(toPortTim);
				shipTrendExcel.setToPortDate(toportdate);
				shipTrendExcel.setToPortTime(toporttime);
			} else {
				shipTrendExcel.setToPortDate("");
				shipTrendExcel.setToPortTime("");
			}
			//开为3
			if ("3".equals(shipTrend.getTrendsTermini()) || (yzyyy.contains(shipTrend.getTrendsTermini()) && !allberth.contains(shipTrend.getTrendsEndArea()))) {
				Date leavePortDate = new Date(shipTrend.getTrendsPlanTim().getTime());
				Date leavePortTim = new Date(shipTrend.getTrendsPlanTim().getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
				String leaveportdate = sdf.format(leavePortDate);
				String leaveporttime = sdf2.format(leavePortTim);
				shipTrendExcel.setLeavePortDate(leaveportdate);
				shipTrendExcel.setLeavePortTime(leaveporttime);
			} else {
				shipTrendExcel.setLeavePortDate("");
				shipTrendExcel.setLeavePortTime("");
			}
			trendexcelList.add(shipTrendExcel);
		}
		Collections.sort(trendexcelList, new Comparator<ShipTrendExcel>() {

			@Override
			public int compare(ShipTrendExcel o1, ShipTrendExcel o2) {
				// 按照泊位排序
				int a1 = Integer.parseInt(o1.getBerthNam().substring(1, o1.getBerthNam().length()));
				int a2 = Integer.parseInt(o2.getBerthNam().substring(1, o2.getBerthNam().length()));
				if (a1 > a2) {
					return 1;
				}
				if (a1 == a2) {
					return 0;
				}
				return -1;
			}
		});
		getListmap(trendexcelList);
		ShipTrendExcelTest shipTrendExcelTest = new ShipTrendExcelTest();
		// shipTrendExcelTest.reportXls(trendexcelList);
		try {
			shipTrendExcelTest.reportMergeXls(response, getListmap(trendexcelList), filename, "sheet1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Map<String, String>> getListmap(List<ShipTrendExcel> trendexcelList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < trendexcelList.size(); i++) {
			Map<String, String> map1 = new HashMap();
			map1.put("码头", trendexcelList.get(i).getDockNam());
			map1.put("泊位", trendexcelList.get(i).getBerthNam());
			map1.put("船名", trendexcelList.get(i).getcShipNam());
			map1.put("总吨", trendexcelList.get(i).getShipGrossWGt().toString());
			map1.put("国籍", trendexcelList.get(i).getCountryNam());
			map1.put("到港日期", trendexcelList.get(i).getToPortDate());
			map1.put("到港时间", trendexcelList.get(i).getToPortTime());
			map1.put("离港日期", trendexcelList.get(i).getLeavePortDate());
			map1.put("离港时间", trendexcelList.get(i).getLeavePortTime());
			list.add(map1);
		}
		return list;

	}
}
