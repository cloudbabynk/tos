package net.huadong.tech.shipbill.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.CargoDataSpecification;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.plan.entity.ConstructionPlan;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.BerthShape;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.DayNightAttention;
import net.huadong.tech.ship.entity.DayNightTrend;
import net.huadong.tech.ship.entity.HdShipPicBerthPlanShipVisit;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.ship.entity.ShipImage;
import net.huadong.tech.ship.entity.ShipLine;
import net.huadong.tech.ship.entity.ShipTim;
import net.huadong.tech.ship.entity.ShipTrend;
import net.huadong.tech.ship.entity.Shipcount;
import net.huadong.tech.shipbill.entity.Contract;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.service.ContractIeDocService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.task.ShipTask;
import net.huadong.tech.util.ConstructionPlanInter;
import net.huadong.tech.util.ContractIeDocInter;
import net.huadong.tech.util.DayNightTrendInter;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONArray;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/nologin/contract/ContractIeDoc")
public class InterfaceController {

	@Autowired
	private ContractIeDocService contractIeDocService;

	// 菜单进入
	@RequestMapping(value = "/berth.html")
	public String pageTh(Model model) {
		return "system/ship/berth";
	}

	// 菜单进入
	@RequestMapping(value = "/berthPlan.html")
	public String pageBT(Model model) {
		return "system/ship/berthPlan";
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findJgContract")
	@ResponseBody
	public List findContractList(@RequestParam("contractDte") String contractDte) {
		String jpql = "select a from ContractIeDoc a where a.contractTyp='1' and a.validatDte=:contractDte";
		QueryParamLs paramLs = new QueryParamLs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) sdf.parse(contractDte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paramLs.addParam("contractDte", date);
		List<ContractIeDoc> contractIeDocList = JpaUtils.findAll(jpql, paramLs);
		Collections.sort(contractIeDocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// 按照泊位排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		List<Contract> contractList = new ArrayList();
		if (contractIeDocList.size() > 0) {
			for (ContractIeDoc contractIeDoc : contractIeDocList) {
				Contract contract = new Contract();
				if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
					if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
						contract.setDockNam("环球");
					}
					if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
						contract.setDockNam("滚装");
					}
				}
				if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
					contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
				} else {
					contract.setFlow("");
				}
				contract.setShipNam(contractIeDoc.getShipNam());
				contract.setVoyage(contractIeDoc.getVoyage());
				contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
				// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
				// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
				// contractIeDoc.getContractTyp()));
				// }
				contract.setContractTyp("集港");
				contract.setBillNo(contractIeDoc.getBillNo());
				if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
					CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
					contract.setBrandNam(cbrand.getBrandNam());
				} else {
					contract.setBrandNam("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
					CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
					contract.setCarKindNam(ccarkind.getCarKindNam());
				} else {
					contract.setCarKindNam("");
				}
				contract.setCarNum(contractIeDoc.getCarNum());
				if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
					CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
					contract.setConsignNam(cclient.getcClientNam());
				} else {
					contract.setConsignNam("");
				}
				contract.setPlanArea(contractIeDoc.getPlanArea());
				if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
					if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
						contract.setFromTo("全天");
					} else {
						contract.setFromTo(contractIeDoc.getFromTo());
					}
				} else {
					contract.setFromTo("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
					contract.setOutMac(contractIeDoc.getOutMac());
				} else {
					contract.setOutMac("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
					contract.setOutPerson(contractIeDoc.getOutPerson());
				} else {
					contract.setOutPerson("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
					contract.setPortMac(contractIeDoc.getPortMac());
				} else {
					contract.setPortMac("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
					contract.setRemarks(contractIeDoc.getRemarks());
				} else {
					contract.setRemarks("");
				}
				contractList.add(contract);
			}
		}
		return contractList;

	}

	@RequestMapping(value = "/findSgContract")
	@ResponseBody
	public List findSgContractList(@RequestParam("contractDte") String contractDte) {
		String jpql = "select a from ContractIeDoc a where a.contractTyp='2' and a.validatDte=:contractDte";
		QueryParamLs paramLs = new QueryParamLs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) sdf.parse(contractDte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paramLs.addParam("contractDte", date);
		List<ContractIeDoc> contractIeDocList = JpaUtils.findAll(jpql, paramLs);
		Collections.sort(contractIeDocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// 按照船名作业性质排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		List<Contract> contractList = new ArrayList();
		if (contractIeDocList.size() > 0) {
			for (ContractIeDoc contractIeDoc : contractIeDocList) {
				Contract contract = new Contract();
				if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
					if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
						contract.setDockNam("环球");
					}
					if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
						contract.setDockNam("滚装");
					}
				}
				if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
					contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
				} else {
					contract.setFlow("");
				}
				contract.setShipNam(contractIeDoc.getShipNam());
				contract.setVoyage(contractIeDoc.getVoyage());
				contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
				// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
				// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
				// contractIeDoc.getContractTyp()));
				// }
				contract.setContractTyp("疏港");
				contract.setBillNo(contractIeDoc.getBillNo());
				if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
					CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
					contract.setBrandNam(cbrand.getBrandNam());
				} else {
					contract.setBrandNam("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
					CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
					contract.setCarKindNam(ccarkind.getCarKindNam());
				} else {
					contract.setCarKindNam("");
				}
				contract.setCarNum(contractIeDoc.getCarNum());
				if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
					CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
					contract.setConsignNam(cclient.getcClientNam());
				} else {
					contract.setConsignNam("");
				}
				contract.setPlanArea(contractIeDoc.getPlanArea());
				if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
					if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
						contract.setFromTo("全天");
					} else {
						contract.setFromTo(contractIeDoc.getFromTo());
					}
				} else {
					contract.setFromTo("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
					contract.setOutMac(contractIeDoc.getOutMac());
				} else {
					contract.setOutMac("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
					contract.setOutPerson(contractIeDoc.getOutPerson());
				} else {
					contract.setOutPerson("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
					contract.setPortMac(contractIeDoc.getPortMac());
				} else {
					contract.setPortMac("");
				}
				if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
					contract.setRemarks(contractIeDoc.getRemarks());
				} else {
					contract.setRemarks("");
				}
				contractList.add(contract);
			}
		}

		return contractList;

	}

	@RequestMapping(value = "/saveOrUpdateCargoData", method = RequestMethod.POST, produces = "application/json;utf-8")
	@ResponseBody
	public String saveOrUpdateCargoData(HttpServletRequest request) {
		JSONArray jsonArray = null;
		try { // 获取输入流
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8")); // 写入数据到Stringbuilder
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = streamReader.readLine()) != null) {
				sb.append(line);
			}
			jsonArray = JSONArray.fromObject(sb.toString()); // 直接将json信息打印出来
		} catch (Exception e) {
			e.printStackTrace();
			throw new HdRunTimeException("JSON串解析有误！");
		}
		List<Map<String,Object>> mapListJson = (List)jsonArray;
		for(Map<String,Object> map :mapListJson){
//			for (String s : map.keySet()) {
//                System.out.print(map.get(s) + "  ");
//            }
			CargoDataSpecification bean = new CargoDataSpecification();
			bean.setxMdCargoid(map.get("X_MD_CARGOID").toString());
			bean.setxFirstCode(map.get("X_FIRST_CODE").toString());
			bean.setxFirstName(map.get("X_FIRST_NAME").toString());
			bean.setxSecondCode(map.get("X_SECOND_CODE").toString());
			bean.setxSecondName(map.get("X_SECOND_NAME").toString());
			bean.setxThirdCode(map.get("X_THIRD_CODE").toString());
			bean.setxThirdName(map.get("X_THIRD_NAME").toString());
			bean.setxFourthCode(map.get("X_FOURTH_CODE").toString());
			bean.setxFourthName(map.get("X_FOURTH_NAME").toString());
			bean.setxTtlCargoKind(map.get("X_TTL_CARGO_KIND").toString());
			bean.setxStopKindCode(map.get("X_STOP_KIND_CODE").toString());
			bean.setxCargoTypeCode(map.get("X_CARGO_TYPE_CODE").toString());
			bean.setxMCargoKindCode(map.get("X_M_CARGO_KIND_CODE").toString());
			bean.setxSourceKindCode(map.get("X_SOURCE_KIND_CODE").toString());
			bean.setxStoreCargoKind(map.get("X_STORE_CARGO_KIND").toString());
			bean.setxIoyardCargoKind(map.get("X_IOYARD_CARGO_KIND").toString());
			bean.setxCargoKindCode(map.get("X_CARGO_KIND_CODE").toString());
			bean.setxInsertTime(new Date(Long.valueOf(map.get("X_INSERT_TIME").toString())));
			bean.setxUpdateTime(new Date(Long.valueOf(map.get("X_UPDATE_TIME").toString())));
			bean.setxUsedFlag(map.get("X_USED_FLAG").toString());
			bean.setxErrorFfag(map.get("X_ERROR_FFAG").toString());
			
			CargoDataSpecification cargoDataSpecification = JpaUtils.findById(CargoDataSpecification.class, bean.getxMdCargoid());
			if (cargoDataSpecification != null){
				org.springframework.beans.BeanUtils.copyProperties(bean, cargoDataSpecification);
				JpaUtils.update(cargoDataSpecification);
			} else {
				JpaUtils.save(bean);
			}
		}
		return "操作成功";
	}

	@RequestMapping(value = "/findTotal")
	@ResponseBody
	public List findTotal(@RequestParam("contractDte") String contractDte) {
		String jpql = "select a from ContractIeDoc a where  a.validatDte=:contractDte";
		QueryParamLs paramLs = new QueryParamLs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) sdf.parse(contractDte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paramLs.addParam("contractDte", date);

		List<ContractIeDoc> contractIeDocList = JpaUtils.findAll(jpql, paramLs);
		Collections.sort(contractIeDocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// 按照船名作业性质排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		List<Contract> contractList = new ArrayList();
		BigDecimal jgcar = new BigDecimal(0);
		BigDecimal jgshebei = new BigDecimal(0);
		BigDecimal sgcar = new BigDecimal(0);
		BigDecimal sgshebei = new BigDecimal(0);
		if (contractIeDocList.size() > 0) {
			for (ContractIeDoc contractIeDoc : contractIeDocList) {
				Contract contract = new Contract();
				if (contractIeDoc.getContractTyp().equals("1")) {
					if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
						if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("环球");
						}
						if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("滚装");
						}
					}
					if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
						contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
					} else {
						contract.setFlow("");
					}
					contract.setShipNam(contractIeDoc.getShipNam());
					contract.setVoyage(contractIeDoc.getVoyage());
					contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
					// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
					// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
					// contractIeDoc.getContractTyp()));
					// }
					contract.setContractTyp("集港");
					contract.setBillNo(contractIeDoc.getBillNo());
					if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
						contract.setBrandNam(cbrand.getBrandNam());
					} else {
						contract.setBrandNam("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
						CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
						contract.setCarKindNam(ccarkind.getCarKindNam());
					} else {
						contract.setCarKindNam("");
					}
					contract.setCarNum(contractIeDoc.getCarNum());
					if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
						CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
						contract.setConsignNam(cclient.getcClientNam());
					} else {
						contract.setConsignNam("");
					}
					contract.setPlanArea(contractIeDoc.getPlanArea());
					if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
						if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
							contract.setFromTo("全天");
						} else {
							contract.setFromTo(contractIeDoc.getFromTo());
						}
					} else {
						contract.setFromTo("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
						contract.setOutMac(contractIeDoc.getOutMac());
					} else {
						contract.setOutMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
						contract.setOutPerson(contractIeDoc.getOutPerson());
					} else {
						contract.setOutPerson("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
						contract.setPortMac(contractIeDoc.getPortMac());
					} else {
						contract.setPortMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
						contract.setRemarks(contractIeDoc.getRemarks());
					} else {
						contract.setRemarks("");
					}
					// 统计设备数
					if ("04".equals(contractIeDoc.getCarKind()) && contractIeDoc.getCarNum() != null) {
						jgshebei = jgshebei.add(contractIeDoc.getCarNum());
					} else if(contractIeDoc.getCarNum() != null){
						jgcar = jgcar.add(contractIeDoc.getCarNum());
					}
					// contractList.add(contract);
				} else if (contractIeDoc.getContractTyp().equals("2")) {
					if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
						if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("环球");
						}
						if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("滚装");
						}
					}
					if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
						contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
					} else {
						contract.setFlow("");
					}
					contract.setShipNam(contractIeDoc.getShipNam());
					contract.setVoyage(contractIeDoc.getVoyage());
					contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
					// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
					// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
					// contractIeDoc.getContractTyp()));
					// }
					contract.setContractTyp("疏港");
					contract.setBillNo(contractIeDoc.getBillNo());
					if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
						contract.setBrandNam(cbrand.getBrandNam());
					} else {
						contract.setBrandNam("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
						CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
						contract.setCarKindNam(ccarkind.getCarKindNam());
					} else {
						contract.setCarKindNam("");
					}
					contract.setCarNum(contractIeDoc.getCarNum());
					if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
						CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
						contract.setConsignNam(cclient.getcClientNam());
					} else {
						contract.setConsignNam("");
					}
					contract.setPlanArea(contractIeDoc.getPlanArea());
					if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
						if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
							contract.setFromTo("全天");
						} else {
							contract.setFromTo(contractIeDoc.getFromTo());
						}
					} else {
						contract.setFromTo("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
						contract.setOutMac(contractIeDoc.getOutMac());
					} else {
						contract.setOutMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
						contract.setOutPerson(contractIeDoc.getOutPerson());
					} else {
						contract.setOutPerson("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
						contract.setPortMac(contractIeDoc.getPortMac());
					} else {
						contract.setPortMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
						contract.setRemarks(contractIeDoc.getRemarks());
					} else {
						contract.setRemarks("");
					}
					// 统计设备数
					if ("04".equals(contractIeDoc.getCarKind())) {
						sgshebei = sgshebei.add(contractIeDoc.getCarNum());
					} else {
						sgcar = sgcar.add(contractIeDoc.getCarNum());
					}
					// contractList.add(contract);
				}
			}
		}
		List totalList = new ArrayList();
		totalList.add(jgcar);
		totalList.add(jgshebei);
		totalList.add(sgcar);
		totalList.add(sgshebei);
		return totalList;

	}

	@RequestMapping(value = "/findLYTotal")
	@ResponseBody
	public Map<String, BigDecimal> findLYTotal(@RequestParam("contractDte") String contractDte,
			HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");

		String jpql = "select a from ContractIeDoc a where  a.validatDte=:contractDte";
		QueryParamLs paramLs = new QueryParamLs();
		contractDte = contractDte.replace('/', '-');
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) sdf.parse(contractDte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paramLs.addParam("contractDte", date);

		List<ContractIeDoc> contractIeDocList = JpaUtils.findAll(jpql, paramLs);
		Collections.sort(contractIeDocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// 按照船名作业性质排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		List<Contract> contractList = new ArrayList();
		BigDecimal jgcar = new BigDecimal(0);
		BigDecimal jgshebei = new BigDecimal(0);
		BigDecimal sgcar = new BigDecimal(0);
		BigDecimal sgshebei = new BigDecimal(0);
		if (contractIeDocList.size() > 0) {
			for (ContractIeDoc contractIeDoc : contractIeDocList) {
				Contract contract = new Contract();
				if (contractIeDoc.getContractTyp().equals("1")) {
					if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
						if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("环球");
						}
						if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("滚装");
						}
					}
					if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
						contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
					} else {
						contract.setFlow("");
					}
					contract.setShipNam(contractIeDoc.getShipNam());
					contract.setVoyage(contractIeDoc.getVoyage());
					contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
					// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
					// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
					// contractIeDoc.getContractTyp()));
					// }
					contract.setContractTyp("集港");
					contract.setBillNo(contractIeDoc.getBillNo());
					if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
						contract.setBrandNam(cbrand.getBrandNam());
					} else {
						contract.setBrandNam("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
						CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
						contract.setCarKindNam(ccarkind.getCarKindNam());
					} else {
						contract.setCarKindNam("");
					}
					contract.setCarNum(contractIeDoc.getCarNum());
					if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
						CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
						contract.setConsignNam(cclient.getcClientNam());
					} else {
						contract.setConsignNam("");
					}
					contract.setPlanArea(contractIeDoc.getPlanArea());
					if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
						if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
							contract.setFromTo("全天");
						} else {
							contract.setFromTo(contractIeDoc.getFromTo());
						}
					} else {
						contract.setFromTo("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
						contract.setOutMac(contractIeDoc.getOutMac());
					} else {
						contract.setOutMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
						contract.setOutPerson(contractIeDoc.getOutPerson());
					} else {
						contract.setOutPerson("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
						contract.setPortMac(contractIeDoc.getPortMac());
					} else {
						contract.setPortMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
						contract.setRemarks(contractIeDoc.getRemarks());
					} else {
						contract.setRemarks("");
					}
					// 统计设备数
						if ("04".equals(contractIeDoc.getCarKind())) {
							jgshebei = jgshebei.add(contractIeDoc.getCarNum());
						} else {
							jgcar = jgcar.add(contractIeDoc.getCarNum());
						}
					// contractList.add(contract);
				} else if (contractIeDoc.getContractTyp().equals("2")) {
					if (HdUtils.strNotNull(contractIeDoc.getDockCod())) {
						if (contractIeDoc.huanqiu.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("环球");
						}
						if (contractIeDoc.gunzhuang.equals(contractIeDoc.getDockCod())) {
							contract.setDockNam("滚装");
						}
					}
					if (HdUtils.strNotNull(contractIeDoc.getFlow())) {
						contract.setFlow(HdUtils.getSysCodeName("FLOW_AREA", contractIeDoc.getFlow()));
					} else {
						contract.setFlow("");
					}
					contract.setShipNam(contractIeDoc.getShipNam());
					contract.setVoyage(contractIeDoc.getVoyage());
					contract.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractIeDoc.getTradeId()));
					// if (HdUtils.strNotNull(contractIeDoc.getContractTyp())) {
					// contract.setContractTyp(HdUtils.getSysCodeName("CONTRACT_TYP",
					// contractIeDoc.getContractTyp()));
					// }
					contract.setContractTyp("疏港");
					contract.setBillNo(contractIeDoc.getBillNo());
					if (HdUtils.strNotNull(contractIeDoc.getBrand())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, contractIeDoc.getBrand());
						contract.setBrandNam(cbrand.getBrandNam());
					} else {
						contract.setBrandNam("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getCarKind())) {
						CCarKind ccarkind = JpaUtils.findById(CCarKind.class, contractIeDoc.getCarKind());
						contract.setCarKindNam(ccarkind.getCarKindNam());
					} else {
						contract.setCarKindNam("");
					}
					contract.setCarNum(contractIeDoc.getCarNum());
					if (HdUtils.strNotNull(contractIeDoc.getConsignCod())) {
						CClientCod cclient = JpaUtils.findById(CClientCod.class, contractIeDoc.getConsignCod());
						contract.setConsignNam(cclient.getcClientNam());
					} else {
						contract.setConsignNam("");
					}
					contract.setPlanArea(contractIeDoc.getPlanArea());
					if (HdUtils.strNotNull(contractIeDoc.getFromTo())) {
						if ("00:00-00:00".equals(contractIeDoc.getFromTo())) {
							contract.setFromTo("全天");
						} else {
							contract.setFromTo(contractIeDoc.getFromTo());
						}
					} else {
						contract.setFromTo("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutMac())) {
						contract.setOutMac(contractIeDoc.getOutMac());
					} else {
						contract.setOutMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getOutPerson())) {
						contract.setOutPerson(contractIeDoc.getOutPerson());
					} else {
						contract.setOutPerson("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getPortMac())) {
						contract.setPortMac(contractIeDoc.getPortMac());
					} else {
						contract.setPortMac("");
					}
					if (HdUtils.strNotNull(contractIeDoc.getRemarks())) {
						contract.setRemarks(contractIeDoc.getRemarks());
					} else {
						contract.setRemarks("");
					}
					// 统计设备数
					if ("04".equals(contractIeDoc.getCarKind())) {
						sgshebei = sgshebei.add(contractIeDoc.getCarNum());
					} else {
						sgcar = sgcar.add(contractIeDoc.getCarNum());
					}
					// contractList.add(contract);
				}
			}
		}
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		map.put("jgcar", jgcar);
		map.put("jgshebei", jgshebei);
		map.put("sgcar", sgcar);
		map.put("sgshebei", sgshebei);
		return map;

	}

	/**
	 * 船名结构下拉
	 */
	@RequestMapping(value = "/gentree", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentree() {
		ShipTask task=new ShipTask();
		List<EzTreeBean> lst=task.getTree();
		return lst;
	}


	@RequestMapping(value = "/findAxis")
	@ResponseBody
	public String findAxis(String width, String height, String daySum, String startdate) {
		int w = Integer.parseInt(width);// 画布的宽
		int h = Integer.parseInt(height);// 画布的高 zr.getHeight
		double kd = getKd(h, daySum);
		// 此处是查询时间
		List<ShipTim> timlist = getShipTim(h, kd, daySum, startdate);
		// 此处是泊位和揽桩
		List<Shipcount> lta = getBerthCable(w);
		JSONObject json = new JSONObject();
		json.put("result", lta);
		json.put("times", timlist);

		return json.toString();
	}

	public double getKd(int height, String daySum) {
		double kd = 0;
		double sum = Double.valueOf(daySum);
		BigDecimal a1 = new BigDecimal(Double.valueOf(height));
		BigDecimal a2 = new BigDecimal(sum * 24);// 除法必须得到精确的值
		kd = a1.divide(a2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();// 画布的高度除以48得到每一个刻度的高度
		return kd;
	}

	public List<ShipTim> getShipTim(double height, double kd, String daySum, String startdate) {
		List<ShipTim> timlist = new ArrayList<ShipTim>();// 放时间;
		Timestamp sp1 = null;
		if (HdUtils.strNotNull(startdate)) {
			sp1 = HdUtils.strToDate(startdate);
		}
		int ptim = 17;
		int days = Integer.valueOf(daySum);// 天数
		int hours = days * 12;
		for (int i = 0; i <= hours; i++) {
			String tim = String.format("%02d", ptim);
			ShipTim shipTim = new ShipTim();
			shipTim.setTim(tim);
			shipTim.setDate(sp1);
			timlist.add(shipTim);
			if (ptim < 23) {
				ptim = ptim + 2;
			} else {
				ptim = 1;
			}
		}

		for (int j = 0; j < timlist.size(); j++) {
			ShipTim st = timlist.get(j);
			double b = j * kd;
			st.setZb(b);
		}

		return timlist;
	}

	// 此方法是得到泊位和揽桩的总的集合
	public List<Shipcount> getBerthCable(double width) {
		List<Shipcount> shipcountbwList = new ArrayList<Shipcount>();// 泊位集合
		String jpql = " select  a  from  CBerth  a  where  a.berthTyp=:berthTyp  order by  a.dispSeq  desc ";// 按照序号排序
		QueryParamLs params = new QueryParamLs();
		params.addParam("berthTyp", "01");
		List<CBerth> cBerthList = JpaUtils.findAll(jpql, params);
		for (CBerth cberth : cBerthList) {
			Shipcount st = new Shipcount();
			st.setBerthId(cberth.getBerthCod());
			st.setSeqNo(cberth.getDispSeq().doubleValue());
			st.setLength(cberth.getBerthLong().doubleValue());
			st.setName(cberth.getBerthNam());
			shipcountbwList.add(st);
		}
		List<Shipcount> shipcountlzList = new ArrayList<Shipcount>();// 揽桩集合
		String jpqls = " select  a  from  CCable  a  where  a.berthCod in ('6510','6520','7410','7420')  order by  a.cableSeq  desc ";// 按照序号排序
		QueryParamLs pa = new QueryParamLs();
		List<CCable> cCableList = JpaUtils.findAll(jpqls, pa);
		for (CCable cable : cCableList) {
			Shipcount st = new Shipcount();
			st.setBerthId(cable.getBerthCod());
			st.setCableId(cable.getCableCod());
			st.setSeqNo(cable.getCableSeq());
			st.setLength(1L);
			st.setName(cable.getCableNo());
			shipcountlzList.add(st);
		}
		List<Shipcount> shipcountList = new ArrayList<Shipcount>();// 泊位+揽桩
		for (Shipcount st : shipcountbwList) {
			String berthId = st.getBerthId();
			for (Shipcount stt : shipcountlzList) {
				if (berthId.equals(stt.getBerthId())) {
					stt.setFullNam(st.getName() + "-" + stt.getName());
					shipcountList.add(stt);
				}
			}
		}
		for (Shipcount st : shipcountList) {
			BigDecimal b1 = new BigDecimal(Double.valueOf("1"));
			BigDecimal b2 = new BigDecimal(Double.valueOf(shipcountlzList.size()));
			double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
			st.setRate(b3);
		}
		double rate = 0;
		for (int i = 0; i < shipcountList.size(); i++) {
			Shipcount st = shipcountList.get(i);
			double dd = st.getRate();
			rate += dd;
			st.setZbx(rate * width);
		}
		return shipcountList;
	}

	@RequestMapping(value = "/showBerths")
	@ResponseBody
	public String showBerths(String startdate, String enddate, String width, String height, String daySum) {

		int w = Integer.parseInt(width);// 画布的宽
		int h = Integer.parseInt(height);// 画布的高 zr.getHeight
		double kd = getKd(h, daySum);
		List<BerthShape> ls = new ArrayList<BerthShape>();
		// 此处是查询时间
		List<ShipTim> timlist = getShipTim(h, kd, daySum, startdate);
		// 此处是泊位和揽桩
		List<Shipcount> lta = getBerthCable(w);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String jpql = " select  a  from  HdShipPicBerthPlanShipVisit a where 1=1 ";
		String date3 = startdate + " 17:00:00";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(startdate)) {
			jpql += " and a.etb>=:date1 and a.etu<:date2 and a.etu>:date3";
			params.addParam("date1", HdUtils.strToDate(startdate));
			params.addParam("date2", HdUtils.strToDate(enddate));
			try {
				params.addParam("date3", new Timestamp(sf.parse(date3).getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<HdShipPicBerthPlanShipVisit> list = JpaUtils.findAll(jpql, params);

		int span = Integer.valueOf(daySum);

		for (HdShipPicBerthPlanShipVisit berth : list) {
			BerthShape bs = getBerthSahpes(berth, lta, timlist, kd, span);
			bs.setShipNo(berth.getShipNo());
			bs.setBerthWay(berth.getPlanBerthingMode());
			ls.add(bs);
		}
		JSONObject json = new JSONObject();
		json.put("list", ls);

		return json.toString();
	}

	public BerthShape getBerthSahpes(HdShipPicBerthPlanShipVisit ship, List<Shipcount> lta, List<ShipTim> timlist,
			double kd, int span) {
		double x1 = 0;// 起始位置
		double x2 = 0;// 终止位置
		double y1 = 0;// 开始时间
		double y2 = 0;// 结束时间
		BerthShape bs = new BerthShape();
		Timestamp sp1 = ship.getEtb();
		Timestamp sp2 = ship.getEtu();
		if (HdUtils.strNotNull(ship.getPlanBerthCode())) {
			if (HdUtils.strNotNull(ship.getPlanBeginBollardCode())
					&& HdUtils.strNotNull(ship.getPlanEndBollardCode())) {
				for (Shipcount sp : lta) {
					if (ship.getPlanBeginBollardCode().equals(sp.getCableId())) {
						x1 = sp.getZbx();
					} else if (ship.getPlanEndBollardCode().equals(sp.getCableId())) {
						x2 = sp.getZbx();
					}
				}
			}
		}
		if (sp1 != null && sp2 != null) {// 泊位 离泊（有开始时间和结束时间）
			y1 = getZby(sp1, timlist, kd, span);
			y2 = getZby(sp2, timlist, kd, span);
			List<List<Object>> list = new ArrayList<List<Object>>();// 泊位 离泊
																	// 这两种在图上显示矩形
			List<Object> lst1 = new ArrayList<Object>();
			lst1.add(x1);
			lst1.add(y1);
			list.add(lst1);
			List<Object> lst2 = new ArrayList<Object>();
			lst2.add(x2);
			lst2.add(y1);
			list.add(lst2);
			List<Object> lst3 = new ArrayList<Object>();
			lst3.add(x2);
			lst3.add(y2);
			list.add(lst3);
			List<Object> lst4 = new ArrayList<Object>();
			lst4.add(x1);
			lst4.add(y2);
			list.add(lst4);
			bs.setShape(list);
			ShipImage image = new ShipImage();
			image.setX(x2);
			image.setY(y1);
			double w = (x1 - x2);
			double h = (y2 - y1);
			image.setWidth(w);
			image.setHeight(h);
			bs.setImage(image);
		} else if (sp1 != null && sp2 == null) {// 预报 沽口 离港 （只有开始时间）
			int hour1 = getHour(sp1);
			String min1 = String.valueOf(getMinute(sp1));
			y1 = getY(hour1, min1, timlist, kd);
			ShipLine sl = new ShipLine();
			sl.setX1(x1);
			sl.setX2(x2);
			sl.setY(y1);
			bs.setShape(sl);
			bs.setFlag("2");// flag=2 表示是预报 沽口 离港 船 一条线
			ShipImage image = new ShipImage();
			image.setX(x1);
			image.setY(y1 - 50);
			double w = (x2 - x1);
			image.setWidth(w);
			image.setHeight(50);
			bs.setImage(image);
		}
		if (HdUtils.strNotNull(ship.getShipCodId())) {
			CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
			if (HdUtils.strNotNull(ship.getRemarks())) {
				bs.setRemarks(ship.getRemarks());
			} else {
				bs.setRemarks("");
			}
			bs.setShipLongNum(cShipData.getShipLongNum().toString());
			bs.setFlag(cShipData.getShipTyp());
			if (HdUtils.strNotNull(ship.getShipNo())) {
				Ship bean = JpaUtils.findById(Ship.class, ship.getShipNo());
				if (bean != null) {
					bs.setText(bean.getcShipNam() + " 船长为：" + cShipData.getShipLongNum() + "("
							+ HdUtils.getSysCodeName("BERTH_WAY", ship.getPlanBerthingMode()) + ")");
				}
				if (HdUtils.strNotNull(bean.getShipStat())) {
					bs.setShipStat(bean.getShipStat());
				} else {
					bs.setShipStat("E");
				}
			}
		}

		// flag代表船型 展示时是不同的颜色

		return bs;
	}

	public double getZby(Timestamp sp, List<ShipTim> timlist, double kd, int span) {
		double y = 0;
		// double hours=(span*24)/48;//求出 每个刻度表示几个小时（1天的是0.5小时，5天的是2.5小时）

		BigDecimal a1 = new BigDecimal(span * 24);
		BigDecimal a2 = new BigDecimal(span * 24);
		double hours = a1.divide(a2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();

		double second = hours * 3600;// 求出秒数

		BigDecimal b1 = new BigDecimal(kd);
		BigDecimal b2 = new BigDecimal(Double.valueOf(second));
		double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();

		double z = 0;
		double b = 0;
		double aaa;
		Timestamp date = timlist.get(0).getDate();// 获取日期
		if (sp.getTime() > date.getTime()) {
			// b=(sp.getTime()-date.getTime())/(1000*3600);//获取小时数
			aaa = sp.getTime() - date.getTime() - 17 * 3600 * 1000;
			BigDecimal t1 = new BigDecimal(aaa);
			BigDecimal t2 = new BigDecimal(1000);
			b = t1.divide(t2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (b > 0) {// 证明是同一天
				y = b * b3;
			}
		}

		return y;
	}

	// 此方法是从纵轴的时间集合里找到对应的时间的纵坐标
	public double getY(int hour, String minute, List<ShipTim> timlist, double kd) {
		double y = 0;
		for (ShipTim shipTim : timlist) {
			String[] str = shipTim.getTim().split(":");
			int h = Integer.parseInt(str[0]);
			int min = Integer.parseInt(str[1]);
			if (h == hour && min == 0) {
				y = shipTim.getZb();
				if (HdUtils.strNotNull(minute)) {
					double d = Double.parseDouble(minute);
					BigDecimal b1 = new BigDecimal(d);
					BigDecimal b2 = new BigDecimal(Double.valueOf(30));
					double b3 = b1.divide(b2, 15, BigDecimal.ROUND_HALF_UP).doubleValue();
					double z = b3 * kd;
					y -= z;

				}
			}
		}
		return y;
	}

	public static int getHour(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	@RequestMapping(value = "/getBerthTim")
	@ResponseBody
	public String getBerthTim(String ptim, String startdate, String enddate, String height) {
		List<ShipTim> timlist = getTimes(ptim, startdate, enddate, height);

		JSONObject json = new JSONObject();
		json.put("times", timlist);
		// json.put("days", span);
		return json.toString();
	}

	public List<ShipTim> getTimes(String ptim, String startdate, String enddate, String height) {
		int h = Integer.parseInt(height);
		double kd = getKd(h, "");
		List<ShipTim> timlist = new ArrayList<ShipTim>(49);// 放时间;
		ptim = "18";// 默认是18点
		Timestamp sp1 = HdUtils.strToDate(startdate);
		Timestamp sp2 = HdUtils.strToDate(enddate);
		long between = 0;
		if (sp2 != null && sp1 != null) {// 多日
			between = (sp2.getTime() - sp1.getTime()) / (1000 * 3600 * 24);
		} else if (sp1 != null && sp2 == null) {// 单日
			between = 1;
		}
		int span = Integer.parseInt(String.valueOf(between)); // 跨度为天
		String now = ptim + ":00";
		int flg = span % 2;// 取余
		int fa = Integer.valueOf(span / 2);// 取商
		int fb = 0;
		if (flg == 1) {// 奇数
			fb = fa + 1;
		}
		Timestamp date = sp1;

		for (int i = 0; i < 49; i++) {
			int end = now.indexOf(":");
			double f = i % 2;
			if (flg == 1) {// 奇数天
				if (f == 1) {
					now = Integer.valueOf(now.substring(0, end)) + fa + ":30";
				} else if (f == 0 && i != 0) {
					now = Integer.valueOf(now.substring(0, end)) + fb + ":00";
				}
			} else if (flg == 0) {// 偶数天
				if (i != 0) {
					now = Integer.valueOf(now.substring(0, end)) + fa + ":00";
				}
			}
			if (Integer.valueOf(now.substring(0, end)) > 24) {
				date = HdUtils.addDay(date, 1);
				String en = now.substring(end);
				now = Integer.valueOf(now.substring(0, end)) - 24 + en;
				end = now.indexOf(":");
			}
			if (Integer.valueOf(now.substring(0, end)) == 24) {
				date = HdUtils.addDay(date, 1);
				now = "0" + now.substring(end);
			}

			String nownew = "";
			String[] str = now.split(":");
			int tt = Integer.parseInt(str[0]);
			if (tt < 10) {
				nownew = "0" + now;
			} else {
				nownew = now;
			}
			ShipTim shipTim = new ShipTim();
			shipTim.setTim(nownew);
			shipTim.setDate(date);
			timlist.add(shipTim);
		}

		for (int j = 0; j < timlist.size(); j++) {
			ShipTim st = timlist.get(j);
			double b = h - j * kd;
			st.setZb(b);
		}

		return timlist;
	}

	@RequestMapping(value = "/findDayNightTrend")
	@ResponseBody
	public Map<String, Object> findDayNightTrend(@RequestParam("planDte") String planDte,
			HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		planDte = planDte.replace('/', '-');
		String jpql = "select a from DayNightTrend a where 1=1 ";
		// String trendsId = hdQuery.getStr("trendsId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(planDte)) {
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(planDte));
		}
		jpql += "order by a.recTim desc";
		List<DayNightTrend> dayNightTrendList = JpaUtils.findAll(jpql, paramLs);
		List<DayNightTrendInter> dayNightTrendInterList = new ArrayList<DayNightTrendInter>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		for (DayNightTrend dayNightTrend : dayNightTrendList) {
			DayNightTrendInter dayNightTrendInter = new DayNightTrendInter();
			if (HdUtils.strNotNull(dayNightTrend.getShipTrendsId())) {
				ShipTrend shipTrend = JpaUtils.findById(ShipTrend.class, dayNightTrend.getShipTrendsId());
				String berthName = "";
				CBerth cBerth = new CBerth();
				ArrayList dx = new ArrayList();
				dx.add("1");
				dx.add("11");
				dx.add("12");
				dx.add("2");
				dx.add("21");
				dx.add("22");
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
				if (dx.contains(shipTrend.getTrendsTermini())) {
					if (arrayN1.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N1");
					}
					if (arrayN2.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N2");
					}
					if (arrayN13.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N13");
					}
					if (arrayN14.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N14");
					}
					cBerth = JpaUtils.findById(CBerth.class, shipTrend.getTrendsEndArea());
					berthName = cBerth.getBerthNam();
				} else if ("3".equals(shipTrend.getTrendsEndArea())) {
					if (arrayN1.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N1");
					}
					if (arrayN2.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N2");
					}
					if (arrayN13.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N13");
					}
					if (arrayN14.contains(shipTrend.getTrendsEndArea())) {
						dayNightTrendInter.setBerthNam("N14");
					}
				} else {
					if (arrayN1.contains(shipTrend.getTrendsBegArea())) {
						dayNightTrendInter.setBerthNam("N1");
					}
					if (arrayN2.contains(shipTrend.getTrendsBegArea())) {
						dayNightTrendInter.setBerthNam("N2");
					}
					if (arrayN13.contains(shipTrend.getTrendsBegArea())) {
						dayNightTrendInter.setBerthNam("N13");
					}
					if (arrayN14.contains(shipTrend.getTrendsBegArea())) {
						dayNightTrendInter.setBerthNam("N14");
					}
					cBerth = JpaUtils.findById(CBerth.class, shipTrend.getTrendsBegArea());
					berthName = cBerth.getBerthNam();
				}
				if (HdUtils.strNotNull(shipTrend.getBegCableNo()) && HdUtils.strNotNull(shipTrend.getEndCableNo())
						&& HdUtils.strNotNull(shipTrend.getTrendsEndArea())) {
					CCable cCable = JpaUtils.findById(CCable.class, shipTrend.getBegCableNo());
					CCable cCable1 = JpaUtils.findById(CCable.class, shipTrend.getEndCableNo());
					if (cBerth != null && cCable1 != null && cCable != null) {
						if (cCable.getCableNo().endsWith("#")) {
							if (new BigDecimal(cCable.getCableSeq())
									.compareTo(new BigDecimal(cCable1.getCableSeq())) == -1) {
								dayNightTrend.setBwlz(cBerth.getBerthNam() + "#" + cCable.getCableSeq() + "-"
										+ cCable1.getCableSeq() + "桩");
							} else if (new BigDecimal(cCable.getCableSeq())
									.compareTo(new BigDecimal(cCable1.getCableSeq())) == 1) {
								dayNightTrend.setBwlz(cBerth.getBerthNam() + "#" + cCable1.getCableSeq() + "-"
										+ cCable.getCableSeq() + "桩");
							}
						} else {
							if (new BigDecimal(cCable.getCableNo())
									.compareTo(new BigDecimal(cCable1.getCableNo())) == -1) {
								dayNightTrend.setBwlz(cBerth.getBerthNam() + "#" + cCable.getCableNo() + "-"
										+ cCable1.getCableNo() + "桩");
							} else if (new BigDecimal(cCable.getCableNo())
									.compareTo(new BigDecimal(cCable1.getCableNo())) == 1) {
								dayNightTrend.setBwlz(cBerth.getBerthNam() + "#" + cCable1.getCableNo() + "-"
										+ cCable.getCableNo() + "桩");
							}
						}
					}
				}
				if (shipTrend.getTrendsPlanTim() != null) {
					String datetime = shipTrend.getTrendsPlanTim().toString();
					String day = String.format("%02d", Integer.valueOf(datetime.substring(8, 10)));
					String hours = String.format("%02d", Integer.valueOf(datetime.substring(11, 13)));
					String minutes = String.format("%02d", Integer.valueOf(datetime.substring(14, 16)));
					if (ShipTrend.JZK.equals(shipTrend.getTrendsTermini())
							|| ShipTrend.YZK.equals(shipTrend.getTrendsTermini())) {
						dayNightTrend.setKx("左靠");
						if (HdUtils.strNotNull(berthName)) {
							if (ShipTrend.JZK.equals(shipTrend.getTrendsTermini())) {
								dayNightTrend.setJcgdt(hours + minutes + "/" + day + "进" + berthName);
							} else {
								dayNightTrend.setJcgdt(hours + minutes + "/" + day + "移" + berthName);
							}
						}
					} else if (ShipTrend.JYK.equals(shipTrend.getTrendsTermini())
							|| ShipTrend.YYK.equals(shipTrend.getTrendsTermini())) {
						dayNightTrend.setKx("右靠");
						if (HdUtils.strNotNull(berthName)) {
							if (ShipTrend.JYK.equals(shipTrend.getTrendsTermini())) {
								dayNightTrend.setJcgdt(hours + minutes + "/" + day + "进" + berthName);
							} else {
								dayNightTrend.setJcgdt(hours + minutes + "/" + day + "移" + berthName);
							}

						}
					} else if (ShipTrend.Y.equals(shipTrend.getTrendsTermini())
							|| ShipTrend.J.equals(shipTrend.getTrendsTermini())) {
						if (HdUtils.strNotNull(berthName)) {
							dayNightTrend.setJcgdt(hours + minutes + "/" + day
									+ HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini()) + berthName);
						}
					} else if (ShipTrend.K.equals(shipTrend.getTrendsTermini())) {
						dayNightTrend.setJcgdt(hours + minutes + "/" + day
								+ HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini()) + "船");
					} else {
						dayNightTrend.setJcgdt(hours + minutes + "/" + day
								+ HdUtils.getSysCodeName("DX_ID", shipTrend.getTrendsTermini()));
					}
				}
				if (HdUtils.strNotNull(shipTrend.getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, shipTrend.getShipNo());
					dayNightTrend.setCshipnam(ship.getcShipNam());
					dayNightTrend.setHc(ship.getIvoyage() + "/" + ship.getEvoyage());
				}
			}
			dayNightTrendInter.setPlanId(dayNightTrend.getPlanId());
			dayNightTrendInter.setBwlz(dayNightTrend.getBwlz());
			dayNightTrendInter.setCshipnam(dayNightTrend.getCshipnam());
			dayNightTrendInter.setDays(format2.format(dayNightTrend.getDays()));
			dayNightTrendInter.setHc(dayNightTrend.getHc());
			dayNightTrendInter.setInAreaNeed(dayNightTrend.getInAreaNeed());
			dayNightTrendInter.setInAreaPlan(dayNightTrend.getInAreaPlan());
			dayNightTrendInter.setInCargo(dayNightTrend.getInCargo());
			dayNightTrendInter.setInNum(dayNightTrend.getInNum());
			dayNightTrendInter.setInTotalNum(dayNightTrend.getInTotalNum());
			dayNightTrendInter.setJcgdt(dayNightTrend.getJcgdt());
			dayNightTrendInter.setKx(dayNightTrend.getKx());
			dayNightTrendInter.setOutAreaNeed(dayNightTrend.getOutAreaNeed());
			dayNightTrendInter.setOutAreaPlan(dayNightTrend.getOutAreaPlan());
			dayNightTrendInter.setOutCargo(dayNightTrend.getOutCargo());
			dayNightTrendInter.setOutNum(dayNightTrend.getOutNum());
			dayNightTrendInter.setOutTotalNum(dayNightTrend.getOutTotalNum());
			dayNightTrendInter.setRecNam(dayNightTrend.getRecNam());
			dayNightTrendInter.setRecTim(format.format(dayNightTrend.getRecTim().getTime()));
			dayNightTrendInter.setUpdNam(dayNightTrend.getUpdNam());
			dayNightTrendInter.setUpdTim(format.format(dayNightTrend.getUpdTim().getTime()));
			dayNightTrendInterList.add(dayNightTrendInter);
		}
		JSONArray listArray = JSONArray.fromObject(dayNightTrendInterList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dayNightTrendInter", listArray);
		return map;
	}

	@RequestMapping(value = "/findSafetyMatters")
	@ResponseBody
	public Map<String, String> findSafetyMatters(@RequestParam("planDte") String planDte,
			HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		String jpql = "select a from DayNightAttention a where 1=1 ";
		// String trendsId = hdQuery.getStr("trendsId");
		QueryParamLs paramLs = new QueryParamLs();
		planDte = planDte.replace('/', '-');
		if (HdUtils.strNotNull(planDte)) {
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(planDte));
		}
		jpql += "order by a.recTim desc";
		List<DayNightAttention> dayNightAttentionList = JpaUtils.findAll(jpql, paramLs);
		String safetymatters = "";
		if (dayNightAttentionList.size() > 0) {
			safetymatters = dayNightAttentionList.get(0).getSafetyMatters();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("safetymatters", safetymatters);
		return map;
	}

	// 陆运情况
	@RequestMapping(value = "/findLY")
	@ResponseBody
	public Map<String, Object> findLY(@RequestParam("contractDte") String contractDte, HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		String jpql = "select a from ContractIeDoc a where  1=1 and a.validatDte=:contractDte";
		QueryParamLs paramLs = new QueryParamLs();
		contractDte = contractDte.replace('/', '-');
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) sdf.parse(contractDte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paramLs.addParam("contractDte", date);
		List<ContractIeDoc> contractIeDocList = JpaUtils.findAll(jpql, paramLs);
		Collections.sort(contractIeDocList, new Comparator<ContractIeDoc>() {

			public int compare(ContractIeDoc o1, ContractIeDoc o2) {
				// 按照船名作业性质排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});
		List<ContractIeDocInter> contractIeDocInterList = new ArrayList<ContractIeDocInter>();
		if (contractIeDocList.size() > 0) {
			for (int i = 0; i < contractIeDocList.size(); i++) {
				ContractIeDocInter contractIeDocInter = new ContractIeDocInter();
				String zyxz = "";
				if ("1".equals(contractIeDocList.get(i).getTradeId())) {
					zyxz += "内";
				}
				if ("2".equals(contractIeDocList.get(i).getTradeId())) {
					zyxz += "外";
				}
				if ("I".equals(contractIeDocList.get(i).getiEId())) {
					zyxz += "进";
				}
				if ("E".equals(contractIeDocList.get(i).getiEId())) {
					zyxz += "出";
				}
				if (HdUtils.strNotNull(contractIeDocList.get(i).getShipNo())) {
					Ship ship = JpaUtils.findById(Ship.class, contractIeDocList.get(i).getShipNo());
					contractIeDocInter.setShipNam(ship.getcShipNam());
				} else {
					contractIeDocInter.setShipNam("");
				}
				contractIeDocInter.setTradeNam(zyxz);
				if (HdUtils.strNotNull(contractIeDocList.get(i).getBrand())) {
					if (HdUtils.strNotNull(contractIeDocList.get(i).getCarKind())) {
						CBrand cb = JpaUtils.findById(CBrand.class, contractIeDocList.get(i).getBrand());
						CCarKind cck = JpaUtils.findById(CCarKind.class, contractIeDocList.get(i).getCarKind());
						contractIeDocInter.setCargoNam((cb.getBrandNam() + cck.getCarKindNam()));
					} else {
						CBrand cb = JpaUtils.findById(CBrand.class, contractIeDocList.get(i).getBrand());
						contractIeDocInter.setCargoNam((cb.getBrandNam()));
					}
				} else {
					if (HdUtils.strNotNull(contractIeDocList.get(i).getCarKind())) {
						CCarKind cck = JpaUtils.findById(CCarKind.class, contractIeDocList.get(i).getCarKind());
						contractIeDocInter.setCargoNam((cck.getCarKindNam()));
					} else {
						contractIeDocInter.setCargoNam((""));
					}
				}
				contractIeDocInter.setCarNum(contractIeDocList.get(i).getCarNum());
				contractIeDocInterList.add(contractIeDocInter);
			}

		}
		JSONArray listArray2 = JSONArray.fromObject(contractIeDocInterList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractIeDocInter", listArray2);
		return map;
	}

	@RequestMapping(value = "/findConstructionPlan")
	@ResponseBody
	public Map<String, Map<String, Object>> findConstructionPlan(@RequestParam("planDte") String planDte,
			HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		planDte = planDte.replace('/', '-');
		String jpql = "select c.orgnId oid from ConstructionPlan a,AuthUser b,AuthOrgn c where a.recNam = b.account and b.orgnId = c.orgnId ";
		// String trendsId = hdQuery.getStr("trendsId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(planDte)) {
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(planDte));
		}
		jpql += "group by c.orgnId";
		List<Object[]> objList = JpaUtils.findAll(jpql, paramLs);
		Map<String, Map<String, Object>> map2 = new HashMap();
		for (Object obj : objList) {
			String jpql1 = "select a from ConstructionPlan a,AuthUser b,AuthOrgn c where a.recNam = b.account and b.orgnId = c.orgnId ";
			QueryParamLs paramLs1 = new QueryParamLs();
			if (obj != null) {
				jpql1 += "and c.orgnId =:orgnId ";
				paramLs1.addParam("orgnId", obj);
			}
			if (HdUtils.strNotNull(planDte)) {
				jpql1 += "and a.days =:days ";
				paramLs1.addParam("days", HdUtils.strToDate(planDte));
			}
			jpql += "order by a.recTim desc";
			List<ConstructionPlan> constructionPlanList = JpaUtils.findAll(jpql1, paramLs1);
			List<ConstructionPlanInter> constructionPlanInterList = new ArrayList<ConstructionPlanInter>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			ArrayList array = new ArrayList();
			for (ConstructionPlan constructionPlan : constructionPlanList) {
				ConstructionPlanInter constructionPlanInter = new ConstructionPlanInter();
				constructionPlanInter.setPlanId(constructionPlan.getPlanId());
				constructionPlanInter.setDays(format2.format(constructionPlan.getDays()));
				constructionPlanInter.setWorkItem(constructionPlan.getWorkItem());
				constructionPlanInter.setWorkArea(constructionPlan.getWorkArea());
				constructionPlanInter.setStartTim(constructionPlan.getStartTim());
				constructionPlanInter.setEndTim(constructionPlan.getEndTim());
				constructionPlanInter.setIsAffect(constructionPlan.getIsAffect());
				if (HdUtils.strNotNull(constructionPlan.getConsCorpCod())) {
					CClientCod cclientCod = JpaUtils.findById(CClientCod.class, constructionPlan.getConsCorpCod());
					constructionPlanInter.setConsCorpNam(cclientCod.getcClientNam());
				} else {
					constructionPlanInter.setConsCorpNam("");
				}
				// constructionPlanInter.setResponseMan(constructionPlan.getResponseMan());
				constructionPlanInter.setRelationNam(constructionPlan.getRelationNam());
				constructionPlanInter.setRelationTele(constructionPlan.getRelationTele());
				constructionPlanInter.setOutPerson(constructionPlan.getOutPerson());
				constructionPlanInter.setOutMach(constructionPlan.getOutMach());
				constructionPlanInter.setOutMachNum(constructionPlan.getOutMachNum());
				if (HdUtils.strNotNull(constructionPlan.getDanWorkItems())) {
					constructionPlanInter.setDanWorkItems(constructionPlan.getDanWorkItems());
				} else {
					constructionPlanInter.setDanWorkItems("");
				}
				String jpqla = "select a from AuthUser a where a.account=:account ";
				QueryParamLs paramLsa = new QueryParamLs();
				paramLsa.addParam("account", constructionPlan.getRecNam());
				List<AuthUser> authUserList = JpaUtils.findAll(jpqla, paramLsa);
				if (authUserList.size() > 0) {
					AuthOrgn authOrgn = JpaUtils.findById(AuthOrgn.class, authUserList.get(0).getOrgnId());
					constructionPlanInter.setDeptNam(authOrgn.getName());
				}
				constructionPlanInter.setRecNam(constructionPlan.getRecNam());
				constructionPlanInter.setRecTim(format.format(constructionPlan.getRecTim().getTime()));
				constructionPlanInter.setUpdNam(constructionPlan.getUpdNam());
				constructionPlanInter.setUpdTim(format.format(constructionPlan.getRecTim().getTime()));
				constructionPlanInter.setRemarks(constructionPlan.getReamarks());
				array.add(constructionPlanInter);
				Map<String, Object> map = new HashMap();
				map.put("xm", array);
				AuthOrgn authOrgn = JpaUtils.findById(AuthOrgn.class, String.valueOf(obj));
				if (HdUtils.strNotNull(authOrgn.getName())) {
					map2.put(authOrgn.getName(), map);
				}
			}
		}
		return map2;

	}

	@RequestMapping(value = "/findConstructionPlanc")
	@ResponseBody
	public List findConstructionPlanc(@RequestParam("planDte") String planDte, HttpServletResponse response) {
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		String jpql = "select a from ConstructionPlan a where 1=1 ";
		// String trendsId = hdQuery.getStr("trendsId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(planDte)) {
			jpql += "and a.days =:days ";
			paramLs.addParam("days", HdUtils.strToDate(planDte));
		}
		jpql += "order by a.recTim desc";
		List<ConstructionPlan> constructionPlanList = JpaUtils.findAll(jpql, paramLs);
		List<ConstructionPlanInter> constructionPlanInterList = new ArrayList<ConstructionPlanInter>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		for (ConstructionPlan constructionPlan : constructionPlanList) {
			ConstructionPlanInter constructionPlanInter = new ConstructionPlanInter();
			constructionPlanInter.setPlanId(constructionPlan.getPlanId());
			constructionPlanInter.setDays(format2.format(constructionPlan.getDays()));
			constructionPlanInter.setWorkItem(constructionPlan.getWorkItem());
			constructionPlanInter.setWorkArea(constructionPlan.getWorkArea());
			constructionPlanInter.setStartTim(constructionPlan.getStartTim());
			constructionPlanInter.setEndTim(constructionPlan.getEndTim());
			constructionPlanInter.setIsAffect(constructionPlan.getIsAffect());
			if (HdUtils.strNotNull(constructionPlan.getConsCorpCod())) {
				CClientCod cclientCod = JpaUtils.findById(CClientCod.class, constructionPlan.getConsCorpCod());
				constructionPlanInter.setConsCorpNam(cclientCod.getcClientNam());
			} else {
				constructionPlanInter.setConsCorpNam("");
			}
			// constructionPlanInter.setResponseMan(constructionPlan.getResponseMan());
			constructionPlanInter.setRelationNam(constructionPlan.getRelationNam());
			constructionPlanInter.setRelationTele(constructionPlan.getRelationTele());
			constructionPlanInter.setOutPerson(constructionPlan.getOutPerson());
			constructionPlanInter.setOutMach(constructionPlan.getOutMach());
			constructionPlanInter.setOutMachNum(constructionPlan.getOutMachNum());
			if (HdUtils.strNotNull(constructionPlan.getDanWorkItems())) {
				constructionPlanInter.setDanWorkItems(constructionPlan.getDanWorkItems());
			} else {
				constructionPlanInter.setDanWorkItems("");
			}
			String jpqla = "select a from AuthUser a where a.account=:account ";
			QueryParamLs paramLsa = new QueryParamLs();
			paramLsa.addParam("account", constructionPlan.getRecNam());
			List<AuthUser> authUserList = JpaUtils.findAll(jpqla, paramLsa);
			if (authUserList.size() > 0) {
				AuthOrgn authOrgn = JpaUtils.findById(AuthOrgn.class, authUserList.get(0).getOrgnId());
				constructionPlanInter.setDeptNam(authOrgn.getName());
			}
			constructionPlanInter.setRecNam(constructionPlan.getRecNam());
			constructionPlanInter.setRecTim(format.format(constructionPlan.getRecTim().getTime()));
			constructionPlanInter.setUpdNam(constructionPlan.getUpdNam());
			constructionPlanInter.setUpdTim(format.format(constructionPlan.getRecTim().getTime()));
			constructionPlanInter.setRemarks(constructionPlan.getReamarks());
			constructionPlanInterList.add(constructionPlanInter);
		}

		return constructionPlanInterList;
	}
}
