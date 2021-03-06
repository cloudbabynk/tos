package net.huadong.tech.shipbill.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.ship.entity.CShipDataGroup;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipTrendExcel;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.entity.ShipBillRecord;
import net.huadong.tech.shipbill.service.ContractIeDocService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.List2Excel;
import net.sf.json.JSONArray;
import oracle.net.aso.b;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/contract/ContractIeDoc")
public class ContractIeDocController {

	@Autowired
	private ContractIeDocService contractIeDocService;

	// ????????????
	@RequestMapping(value = "/contractiedoc.htm")
	public String page(String contractTyp, Model model) {
		// SG?????? JG??????
		if ("JG".equals(contractTyp)) {
			model.addAttribute("contractTyp", contractTyp);
		}
		if ("SG".equals(contractTyp)) {
			model.addAttribute("contractTyp", contractTyp);
		}
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/contract/contractiedoc";
	}

	// ???????????????????????????
	@RequestMapping(value = "/contractiedocprint.htm")
	public String pagePrint(String contractTyp, Model model) {
		// SG?????? JG??????
//		if ("JG".equals(contractTyp)) {
//			model.addAttribute("contractTyp", contractTyp);
//		}
//		if ("SG".equals(contractTyp)) {
//			model.addAttribute("contractTyp", contractTyp);
//		}
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/contract/contractiedocprint";
	}

	// ????????????
	@RequestMapping(value = "/contractiedocquery.htm")
	public String pageContractQuery(String contractTyp, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/contract/contractiedocquery";
	}

	// ????????????
	@RequestMapping(value = "/gatecontractiedoc.htm")
	public String pageGateTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/gate/gatecontractiedoc";
	}

	// ????????????
	@RequestMapping(value = "/hycz.htm")
	public String pageHycz(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/gate/hycz";
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
		return contractIeDocService.find(hdQuery);
	}
	
	/**
	 * ????????????  ???????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findContract")
	@ResponseBody
	public List<ContractIeDoc> findContract() {
		return contractIeDocService.findContract();
	}

	/**
	 * ??????Excel
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
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.find(params);
		List<ContractIeDoc> contractiedocList = data.getRows();
		for (int i = 0; i < contractiedocList.size(); i++) {
			if (HdUtils.strNotNull(contractiedocList.get(i).getBrand())) {
				CBrand cb = JpaUtils.findById(CBrand.class, contractiedocList.get(i).getBrand());
				contractiedocList.get(i)
						.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractiedocList.get(i).getTradeId()));
				contractiedocList.get(i)
						.setIeNam(HdUtils.getSysCodeName("I_E_ID", contractiedocList.get(i).getiEId()));
				contractiedocList.get(i)
						.setFlowNam(HdUtils.getSysCodeName("FLOW_AREA", contractiedocList.get(i).getFlow()));
				contractiedocList.get(i)
						.setWorkNam(HdUtils.getSysCodeName("WORK_WAY", contractiedocList.get(i).getWorkWay()));
				contractiedocList.get(i).setBrandNam(cb.getBrandNam());
				contractiedocList.get(i)
				.setActiveId(HdUtils.getSysCodeName("ACTIVE_ID", contractiedocList.get(i).getActiveId()));
		        contractiedocList.get(i).setBrandNam(cb.getBrandNam());
			} else {
				contractiedocList.get(i)
						.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractiedocList.get(i).getTradeId()));
				contractiedocList.get(i)
						.setIeNam(HdUtils.getSysCodeName("I_E_ID", contractiedocList.get(i).getiEId()));
				contractiedocList.get(i)
						.setFlowNam(HdUtils.getSysCodeName("FlOW_AREA", contractiedocList.get(i).getFlow()));
				contractiedocList.get(i)
						.setWorkNam(HdUtils.getSysCodeName("WORK_WAY", contractiedocList.get(i).getWorkWay()));
				contractiedocList.get(i)
				.setActiveId(HdUtils.getSysCodeName("ACTIVE_ID", contractiedocList.get(i).getActiveId()));
			}
		}
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}
	@RequestMapping(value = "/exportExcel2", method = RequestMethod.POST)
	@ResponseBody public void exportExcel2(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, String contractNos, HttpServletResponse response) {
//		JSONArray myJsonArray = JSONArray.fromObject(bodyData);
//		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		 //String jsonString = gson.toJson(resourceInfo,ResourceGeoInfo.class);
//			List<ContractIeDoc> contractiedocList= gson.fromJson(bodyData, new TypeToken<List<ContractIeDoc>>() {
//			}.getType());
		List<ContractIeDoc> contractiedocList = new ArrayList<ContractIeDoc>();
		if (HdUtils.strIsNull(contractNos)){
			HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
					hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
			HdEzuiDatagridData data = this.find(params);
			contractiedocList = data.getRows();
		} else {
			List<String> contractNoString = HdUtils.paraseStrs(contractNos);
			for (String contractNo : contractNoString) {
				ContractIeDoc bean = JpaUtils.findById(ContractIeDoc.class, contractNo);
				if (bean != null){
					contractiedocList.add(bean);
				}
			}
		}
		for (int i = 0; i < contractiedocList.size(); i++) {
			if (HdUtils.strNotNull(contractiedocList.get(i).getBrand())) {
				CBrand cb = JpaUtils.findById(CBrand.class, contractiedocList.get(i).getBrand());
				contractiedocList.get(i)
						.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractiedocList.get(i).getTradeId()));
				contractiedocList.get(i)
						.setIeNam(HdUtils.getSysCodeName("I_E_ID", contractiedocList.get(i).getiEId()));
				contractiedocList.get(i)
						.setFlowNam(HdUtils.getSysCodeName("FLOW_AREA", contractiedocList.get(i).getFlow()));
				contractiedocList.get(i)
						.setWorkNam(HdUtils.getSysCodeName("WORK_WAY", contractiedocList.get(i).getWorkWay()));
				contractiedocList.get(i).setBrandNam(cb.getBrandNam());
				contractiedocList.get(i)
				.setActiveId(HdUtils.getSysCodeName("ACTIVE_ID", contractiedocList.get(i).getActiveId()));
		        contractiedocList.get(i).setBrandNam(cb.getBrandNam());
			} else {
				contractiedocList.get(i)
						.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractiedocList.get(i).getTradeId()));
				contractiedocList.get(i)
						.setIeNam(HdUtils.getSysCodeName("I_E_ID", contractiedocList.get(i).getiEId()));
				contractiedocList.get(i)
						.setFlowNam(HdUtils.getSysCodeName("FlOW_AREA", contractiedocList.get(i).getFlow()));
				contractiedocList.get(i)
						.setWorkNam(HdUtils.getSysCodeName("WORK_WAY", contractiedocList.get(i).getWorkWay()));
				contractiedocList.get(i)
				.setActiveId(HdUtils.getSysCodeName("ACTIVE_ID", contractiedocList.get(i).getActiveId()));
			}
		}
		Collections.sort(contractiedocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// ??????????????????
				String a1 = o1.getTradeId()+o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId()+o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if(a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		try {
			String dte=new java.text.SimpleDateFormat("yyyy-MM-dd").format(contractiedocList.get(0).getContractDte());
			List2Excel.writeToXls(List2Excel.createlist(contractiedocList),response,dte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	/**
	 * ??????????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShip")
	@ResponseBody
	public HdEzuiDatagridData findShip(@RequestBody HdQuery hdQuery) {
		return contractIeDocService.findShip(hdQuery);
	}

	/**
	 * ??????????????????
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ContractIeDoc> gridData) {
		// CommonUtil.initEntity(gridData);
		return contractIeDocService.save(gridData);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String contractNos) {
		contractIeDocService.removeAll(contractNos);
		return HdUtils.genMsg();
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public HdMessageCode sendEmail(String fromWho, String password, String toWho, String attach) {
		contractIeDocService.sendEmail(fromWho, password, toWho, attach);
		return HdUtils.genMsg();
	}

	/**
	 * ????????????
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ContractIeDoc findone(String contractNo) {
		if (HdUtils.strIsNull(contractNo)) {
			ContractIeDoc ContractIeDoc = new ContractIeDoc();
			return ContractIeDoc;
		}
		return contractIeDocService.findone(contractNo);
	}

	/*
	 * form??????
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ContractIeDoc ContractIeDoc) {

		return contractIeDocService.saveone(ContractIeDoc);
	}
	
	@RequestMapping("/hasWorkCommand")
	@ResponseBody
	public String hasWorkCommand(@RequestBody String contractNo) {

		return contractIeDocService.hasWorkCommand(contractNo);
	}
	
	/**
	 * ???????????? ??????????????????
	 */
	@RequestMapping(value = "/findContractIeDoc")
	@ResponseBody
	public HdMessageCode findContractIeDoc(String contractNo) {
		return contractIeDocService.findContractIeDoc(contractNo);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/checkBefDel")
	@ResponseBody
	public HdMessageCode checkBefDel(String contractNo) {
		return contractIeDocService.checkBefDel(contractNo);
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getShipBillDrop")
	@ResponseBody
	public List<EzDropBean> getShipBillDrop(String shipNo,String q) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ShipBill a  where 1=1 and a.shipNo=:shipNo ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("shipNo", shipNo);
		if (HdUtils.strNotNull(q)){
			jpql += "and a.billNo like :billNo";
			params.addParam("billNo", "%" + q + "%");
		}
		List<ShipBill> ls = JpaUtils.findAll(jpql, params);
		if (ls.size() > 0) {
			for (ShipBill sb : ls) {
				EzDropBean drop = new EzDropBean();
				drop.setValue(sb.getBillNo());
				drop.setLabel(sb.getBillNo());
				list.add(drop);
			}
		} else {
			EzDropBean drop = new EzDropBean();
			drop.setValue("--");
			drop.setLabel("--");
			list.add(drop);
		}
		return list;
	}
	
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getShipBillRecordDrop")
	@ResponseBody
	public List<EzDropBean> getShipBillRecordDrop(String shipNo,String q) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ShipBillRecord a  where 1=1 and a.shipNo=:shipNo ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("shipNo", shipNo);
		if (HdUtils.strNotNull(q)){
			jpql += "and a.billNo like :billNo";
			params.addParam("billNo", "%" + q + "%");
		}
		List<ShipBillRecord> ls = JpaUtils.findAll(jpql, params);
		if (ls.size() > 0) {
			for (ShipBillRecord sb : ls) {
				EzDropBean drop = new EzDropBean();
				drop.setValue(sb.getBillNo());
				drop.setLabel(sb.getBillNo());
				list.add(drop);
			}
		}
		return list;
	}
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getShipBillWmckDrop")
	@ResponseBody
	public List<EzDropBean> getShipBillWmckDrop(String shipNo,String q) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ShipBill a  where a.iEId = 'E' and a.shipNo=:shipNo ";
		QueryParamLs params = new QueryParamLs();
		params.addParam("shipNo", shipNo);
		if (HdUtils.strNotNull(q)){
			jpql += "and a.billNo like :billNo";
			params.addParam("billNo", "%" + q + "%");
		}
		List<ShipBill> ls = JpaUtils.findAll(jpql, params);
		if (ls.size() > 0) {
			for (ShipBill sb : ls) {
				EzDropBean drop = new EzDropBean();
				drop.setValue(sb.getBillNo());
				drop.setLabel(sb.getBillNo());
				list.add(drop);
			}
		} else {
			EzDropBean drop = new EzDropBean();
			drop.setValue("--");
			drop.setLabel("--");
			list.add(drop);
		}
		return list;
	}
	/*
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/getAllShipBillDrop")
	@ResponseBody
	public List<EzDropBean> getAllShipBillDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ShipBill a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<ShipBill> ls = JpaUtils.findAll(jpql, null);
		if (ls.size() > 0) {
			for (ShipBill sb : ls) {
				EzDropBean drop = new EzDropBean();
				drop.setValue(sb.getBillNo());
				drop.setLabel(sb.getBillNo());
				list.add(drop);
			}
		} else {
			EzDropBean drop = new EzDropBean();
			drop.setValue("--");
			drop.setLabel("--");
			list.add(drop);
		}
		return list;
	}
	// ??????????????????
	@RequestMapping(value = "/getshipBillDrop")
	@ResponseBody
	public List<EzDropBean> getshipBillDrop(String shipNo) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ShipBill a  where 1=1 and a.shipNo=:shipNo  and a.brandCod='TOYOTA'";
		QueryParamLs params = new QueryParamLs();
		params.addParam("shipNo", shipNo);
		List<ShipBill> ls = JpaUtils.findAll(jpql, params);
		for (ShipBill sb : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(sb.getBillNo());
			drop.setLabel(sb.getBillNo());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getContractIeDocDrop")
	@ResponseBody
	public List<EzDropBean> getContractIeDocDrop(String shipNo) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ContractIeDoc a  where 1=1 and a.shipNo=:shipNo";
		QueryParamLs params = new QueryParamLs();
		params.addParam("shipNo", shipNo);
		List<ContractIeDoc> ls = JpaUtils.findAll(jpql, params);
		for (ContractIeDoc sb : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(sb.getContractNo());
			drop.setLabel(sb.getContractNo());
			list.add(drop);
		}
		return list;
	}
	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/getAllContractIeDocDrop")
	@ResponseBody
	public List<EzDropBean> getAllContractIeDocDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  ContractIeDoc a  where 1=1";
		QueryParamLs params = new QueryParamLs();
		List<ContractIeDoc> ls = JpaUtils.findAll(jpql, null);
		for (ContractIeDoc sb : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(sb.getContractNo());
			drop.setLabel(sb.getContractNo());
			list.add(drop);
		}
		return list;
	}
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/getCBrandDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CBrand a  where 1=1 and a.checkFlag='1' ";
		QueryParamLs params = new QueryParamLs();
		List<CBrand> ls = JpaUtils.findAll(jpql, params);
		for (CBrand cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getBrandCod());
			drop.setLabel(cc.getBrandNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
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
	 * ??????????????????
	 */
	@RequestMapping(value = "/getCPortDrop")
	@ResponseBody
	public List<EzDropBean> getCPortDrop() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CPort a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CPort> ls = JpaUtils.findAll(jpql, params);
		for (CPort cp : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cp.getPortCod());
			drop.setLabel(cp.getcPortNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ???????????????????????????????????????
	 */
	@RequestMapping(value = "/getCClientCodDrop")
	@ResponseBody
	public List<EzDropBean> getCClientCodDrop(String q) {
 		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CClientCod a ";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(q)){
			jpql += "where (a.crgAgentId='1' or a.consignId='1') and a.cClientNam like :cClientNam";
			params.addParam("cClientNam", "%" + q + "%");
		}else{
			jpql += "where a.crgAgentId='1' or a.consignId='1'";
		}
		List<CClientCod> ls = JpaUtils.findAll(jpql, params);
		for (CClientCod cp : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cp.getClientCod());
			drop.setLabel(cp.getcClientNam());
			list.add(drop);
		}
		return list;
	}
	
	/*
	 * ?????????  ??????
	 */
	@RequestMapping(value = "/getCClientCodDropHd")
	@ResponseBody
	public List<EzDropBean> getCClientCodDropHd() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CClientCod a  where a.crgAgentId='1' or a.consignId='1' ";
		QueryParamLs params = new QueryParamLs();
		List<CClientCod> ls = JpaUtils.findAll(jpql, params);
		for (CClientCod cp : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cp.getClientCod());
			drop.setLabel(cp.getcClientShort());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ???????????????????????????????????????
	 */
	@RequestMapping(value = "/getCCarTyp")
	@ResponseBody
	public List<EzDropBean> getCCarTyp() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarTyp a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CCarTyp> ls = JpaUtils.findAll(jpql, params);
		for (CCarTyp cct : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cct.getCarTyp());
			drop.setLabel(cct.getCarTypNam());
			list.add(drop);
		}
		return list;
	}
	//??????????????????????????????
	@RequestMapping(value = "/findBrandKind")
	@ResponseBody
	public CCarTyp findBrandKind(String carTyp) {
		if (HdUtils.strIsNull(carTyp)) {
			CCarTyp cCarTyp = new CCarTyp();
			return cCarTyp;
		}
		return contractIeDocService.findBrandKind(carTyp);
	}
	
	/**
	 * ???????????????????????????????????????
	 */
	@RequestMapping(value = "/getCCarKind")
	@ResponseBody
	public List<EzDropBean> getCCarKind() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCarKind a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		List<CCarKind> ls = JpaUtils.findAll(jpql, params);
		for (CCarKind cck : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cck.getCarKind());
			drop.setLabel(cck.getCarKindNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/getCCyArea")
	@ResponseBody
	public List<EzDropBean> getCCyArea(String dockCod) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCyArea a  where 1=1 and a.dockCod=:dockCod order by a.cyAreaNo  asc";
		QueryParamLs params = new QueryParamLs();
		params.addParam("dockCod",dockCod);
		List<CCyArea> ls = JpaUtils.findAll(jpql, params);
		for (CCyArea cck : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cck.getCyAreaNo());
			drop.setLabel(cck.getCyAreaNam());
			list.add(drop);
		}
		return list;
	}

	@RequestMapping(value = "/savename")
	@ResponseBody
	public List<Ship> savename(@RequestBody HdQuery hdQuery) {
		return contractIeDocService.savename(hdQuery);
	}

	/**
	 * ?????????????????????????????????????????????
	 * 
	 * @param imgName
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/IoReadImage/{imgName}", method = RequestMethod.GET)
	public String IoReadImage(@PathVariable String imgName, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ServletOutputStream out = null;
		FileInputStream ips = null;
		try {
			// ????????????????????????
			String imgPath = "c:/tjroro/qrcodepng/" + imgName + ".png";
			ips = new FileInputStream(new File(imgPath));
			response.setContentType("multipart/form-data");
			out = response.getOutputStream();
			// ???????????????
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			ips.close();
		}
		return null;
	}
	
	/*
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/getShipBillInfo")
	@ResponseBody
	public ContractIeDoc getShipBillInfo(String contractNo) {
		return contractIeDocService.getShipBillInfo(contractNo);
	}
	
	/**
	 * copy??????
	 */
	@RequestMapping("/copy")
	@ResponseBody
	public HdMessageCode copy(@RequestBody ContractIeDoc contractIeDoc) {

		return contractIeDocService.copy(contractIeDoc);
	}	
	@RequestMapping(value = "/getShipDockCod")
	@ResponseBody
	public Ship getShipDockCod(String shipNo) {
		return contractIeDocService.getShipDockCod(shipNo);
	}
	
	@RequestMapping(value = "/getTrueCarNum")
	@ResponseBody
	public List<Map<String,Object>> getTrueCarNum(String shipNo) {
		return contractIeDocService.getTrueCarNum(shipNo);
	}
	
//	@RequestMapping(value = "/confirmingate")
//	@ResponseBody
//	public HdMessageCode confirmingate(GateTruckContract gateTruckContract,String singleId,String truckNo,String planNum,String inGatTim,String gateNo) {
//	 contractIeDocService.confirmingate(gateTruckContract,singleId,planNum,inGatTim,gateNo);
//	 return HdUtils.genMsg();
//	}
	@RequestMapping(value = "/confirmingate")
	@ResponseBody
	public HdMessageCode confirmingate(String contractNos) {
	 contractIeDocService.confirmingate(contractNos);
	 return HdUtils.genMsg();
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param billspId
	 * @return
	 */
	@RequestMapping(value = "/sendData")
	@ResponseBody
	public String sendData(String contractNos) {
		return contractIeDocService.sendData(contractNos);
	}
	
	
	/**
	 * ?????????????????????????????????
	 * 
	 * @param billspId
	 * @return
	 */
	@RequestMapping(value = "/sendDailyContractPlan")
	@ResponseBody
	public String sendDailyContractPlan(String contractNos) {
		return contractIeDocService.sendDailyContractPlan(contractNos);
	}
	
	/*
	 * ??????????????? ???????????? ???????????????
	 */
	@RequestMapping(value = "/getSysCodeJiGang")
	@ResponseBody
	public List<EzDropBean> getSysCodeJiGang() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  SysCode a  where a.fieldCod='WORK_WAY' and (a.code='1' or a.code='3')";
		QueryParamLs params = new QueryParamLs();
		List<SysCode> ls = JpaUtils.findAll(jpql, params);
		for (SysCode cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getCode());
			drop.setLabel(cc.getName());
			list.add(drop);
		}
		return list;
	}
}
