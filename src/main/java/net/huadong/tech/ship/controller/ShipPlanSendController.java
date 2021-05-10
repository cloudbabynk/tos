package net.huadong.tech.ship.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.MFeeInterfaceBerth;
import net.huadong.tech.Interface.entity.MFeeInterfaceVoyage;
import net.huadong.tech.Interface.entity.ShipPlanSend;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.service.ShipPlanSendService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CargoR;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONObject;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/ship/ShipPlanSend")
public class ShipPlanSendController  {
	
	@Autowired
	private ShipPlanSendService shipPlanSendService; 
	
	//菜单进入
	@RequestMapping(value = "/shipplansend.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/ship/shipplansend";
	}
	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/getCCShipDataDrop")
	@ResponseBody
	public List<EzDropBean> getCCShipDataDrop(String shipCod) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		QueryParamLs params=new QueryParamLs();
		List<CShipData>  ls=null;
		if(HdUtils.strNotNull(shipCod)){
			String jpql= " select a  from  CShipData a  where a.shipCod=:shipCod";
			params.addParam("shipCod", shipCod);
			ls=JpaUtils.findAll(jpql, params);
		}else{
			String jpql= " select a  from  CShipData a  where 1=1 ";
			ls=JpaUtils.findAll(jpql, params);
		}
		for(CShipData cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getShipCod());
			drop.setLabel(cc.getcShipNam());
			list.add(drop);
		}
		return list;
	}
	/**
	 * 船名下拉
	 */
	@RequestMapping(value = "/geteShipNamDrop")
	@ResponseBody
	public List<EzDropBean> geteShipNamDrop(String shipCod) {
		List<EzDropBean> list=new ArrayList<EzDropBean>();
		QueryParamLs params=new QueryParamLs();
		List<CShipData>  ls=null;
		if(HdUtils.strNotNull(shipCod)){
			String jpql= " select a  from  CShipData a  where a.shipCod=:shipCod";
			params.addParam("shipCod", shipCod);
			ls=JpaUtils.findAll(jpql, params);
		}else{
			String jpql= " select a  from  CShipData a  where 1=1 ";
			ls=JpaUtils.findAll(jpql, params);
		}
		for(CShipData cc:ls){
			EzDropBean  drop=new EzDropBean();
			drop.setValue(cc.getShipCod());
			drop.setLabel(cc.geteShipNam());
			list.add(drop);
		}
		return list;
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String spsendIds) {
		shipPlanSendService.removeAll(spsendIds);
		return HdUtils.genMsg();
	}
	
	/**
	 * 上报集团新调度
	 */
	@RequestMapping(value = "/uploadAll")
	@ResponseBody
	public HdMessageCode uploadAll(String spsendIds) {
		shipPlanSendService.uploadAll(spsendIds);
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
	    return shipPlanSendService.find(hdQuery);
	}
	/**
	 * 航次计费接口查询
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findVoyage")
	@ResponseBody
	public HdEzuiDatagridData findVoyage(@RequestBody HdQuery hdQuery) {
		return shipPlanSendService.findVoyage(hdQuery);
	}
	/**
	 * 停泊计费接口查询
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findBerth")
	@ResponseBody
	public HdEzuiDatagridData findBerth(@RequestBody HdQuery hdQuery) {
		return shipPlanSendService.findBerth(hdQuery);
	}
	//处理航次计费接口
		@RequestMapping("/procVoyage")
		@ResponseBody
		public HdMessageCode genmovershort(String shipNo,String tradeId) {
			return shipPlanSendService.procVoyage(shipNo,tradeId);
		}
		//处理运抵标记
		@RequestMapping("/procYunDi")
		@ResponseBody
		public HdMessageCode procYunDi(String ydId) {
			return shipPlanSendService.procYundi(ydId);
		}
		//处理停泊计费接口
		@RequestMapping("/procBerth")
		@ResponseBody
		public HdMessageCode procBerth(String shipNo,String tradeId) {
			return shipPlanSendService.procBerth(shipNo,tradeId);
		}
		/*
		 * 上传数据
		 */
		@RequestMapping("/uploadData")
		@ResponseBody
		public HdMessageCode uploadData(String id) {
			QueryParamLs paramLs = new QueryParamLs();
 	   		String sql="update MFeeInterfaceVoyage a set a.bwSendId='1' where a.id=:id";
 	   		paramLs.addParam("id", id);
 	   		JpaUtils.execUpdate(sql, paramLs);
			List<Map> mFeeInterfaceTallyBakList = new ArrayList<Map>();
			MFeeInterfaceVoyage bean = JpaUtils.findById(MFeeInterfaceVoyage.class, id);
			if (bean != null) {
				MFeeInterfaceVoyage m = new MFeeInterfaceVoyage();
				BeanUtils.copyProperties(bean, m);
				Map map= new HashMap();
				map.put("VesselName", m.getVesselname());
				map.put("VesselNameEn", m.getVesselnameen());
				map.put("Voyage", m.getVoyage());
				map.put("ImpExpId", m.getImpexpid());
				map.put("VesselTradeId", m.getVesseltradeid());
				map.put("VesselWeight", m.getVesselweight());
				map.put("VesselAgentId", m.getVesselagentid());
				map.put("CarrierId", m.getCarrierid());
				map.put("Imo", m.getImo());
				map.put("VesselVisitId", m.getVesselvisitid());
				map.put("VoyageInterfaceId", m.getVoyageinterfaceid());
				map.put("VoyageFacilityInterfaceId", m.getVoyagefacilityinterfaceid());
				map.put("FacilityId", m.getFacilityid());
				map.put("ComplexId", m.getComplexid());
				map.put("SendName", HdUtils.getCurUser().getName());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("SendTime",  sdf.format(HdUtils.getDateTime()));
				mFeeInterfaceTallyBakList.add(map);
			}
			if (mFeeInterfaceTallyBakList.size() > 0) {
				try {
					JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				    //Client client = dcf.createClient("http://114.116.100.75:8082/interfaceservice.svc?wsdl");
					Client client = dcf.createClient("http://10.128.141.66/interface/interfaceservice.svc?wsdl");
					
				    ObjectMapper json = new ObjectMapper();
				    System.out.println("start-----------------xin ji fei ----hang ci---------     ++++1");
				    Object[] objects = new Object[0]; 
				    String params= null; //接口第一个参数 json数据
				    String params1= "Vessel"; //接口第二个参数 固定值 参数
				    JSONObject jsonObj = new JSONObject();
				    jsonObj.put("UserId", HdUtils.getCurUser().getUserId());
				    jsonObj.put("UserName", HdUtils.getCurUser().getName());
				    jsonObj.put("Ip", "XX");
				    jsonObj.put("InterfaceList", json.writeValueAsString(mFeeInterfaceTallyBakList));
				    JSONObject jsonObj1 = new JSONObject();
				    jsonObj1.put("datas", jsonObj.toString());
				    params = jsonObj1.toString();
					objects = client.invoke("SendDataToInterface", params,params1);
				    
					String a = objects[0].toString();
		 	        if ("0".equals(a.substring(a.length()-2, a.length()-1))) {
		 	        	HdMessageCode hdMessageCode = new HdMessageCode();
						hdMessageCode.setCode("-1");
						try {
							CargoR cargoR = JSON.parse(a, CargoR.class);
							hdMessageCode.setMessage(cargoR.getResultMessage());
						} catch(Exception e) {
							e.printStackTrace();
						}
						return hdMessageCode;
		 	        }else{
			 	   		sql="update MFeeInterfaceVoyage a set a.jfOkId='1',a.sendname=:name,a.sendtime=:sendtime where a.id=:id";
			 	   		paramLs.addParam("name", HdUtils.getCurUser().getName());
			 	   		paramLs.addParam("sendtime", HdUtils.getDateTime());
			 	   		JpaUtils.execUpdate(sql, paramLs);
		 	        }
				} catch (Exception e) {
					throw new HdRunTimeException("操作失败！");
				}
			}else {
				throw new HdRunTimeException("数据不能为空！");
			}
			
			return HdUtils.genMsg();
		}
		/*
		 * 上传数据
		 */
		@RequestMapping("/uploadData11")
		@ResponseBody
		public HdMessageCode uploadData11(String ids) {
			List<String> idList = HdUtils.paraseStrs(ids);
			List<Map> mFeeInterfaceTallyBakList = new ArrayList<Map>();
			for (int i = 0; i < idList.size(); i++) {
				QueryParamLs paramLs = new QueryParamLs();
	 	   		String sql="update MFeeInterfaceBerth a set a.bwSendId='1' where a.id=:id";
	 	   		String id =idList.get(i);
	 	   		paramLs.addParam("id", id);
	 	   		JpaUtils.execUpdate(sql, paramLs);
				MFeeInterfaceBerth bean = JpaUtils.findById(MFeeInterfaceBerth.class, id);
				if (bean != null) {
					MFeeInterfaceBerth m = new MFeeInterfaceBerth();
					BeanUtils.copyProperties(bean, m);
					Map map= new HashMap();
					map.put("VesselName", m.getVesselname());
					map.put("VesselNameEn", m.getVesselnameen());
					map.put("Voyage", m.getVoyage());
					map.put("ImpExpId", m.getImpexpid());
					map.put("VesselTradeId", m.getVesseltradeid());
					map.put("VesselWeight", m.getVesselweight());
					map.put("VesselAgentId", m.getVesselagentid());
					map.put("CarrierId", m.getCarrierid());
					map.put("Imo", m.getImo());
					map.put("VesselVisitId", m.getVesselvisitid());
					map.put("BerthCode", m.getBerthcode());
					map.put("BerthName", m.getBerthname());
					map.put("BerthCategoryId", m.getBerthcategoryid());
					map.put("VoyageInterfaceId", m.getVoyageinterfaceid());
					map.put("VoyageFacilityInterfaceId", m.getVoyagefacilityinterfaceid());
					map.put("FacilityId", m.getFacilityid());
					map.put("ComplexId", m.getComplexid());
					map.put("NightId", m.getNightid());
					map.put("HolidayId", m.getHolidayid());
					map.put("OrderNo", m.getOrderno());
					map.put("SendName", HdUtils.getCurUser().getName());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("SendTime",  sdf.format(HdUtils.getDateTime()));
					map.put("BeginDate",  sdf.format(m.getBegindate()));
					map.put("EndDate",  sdf.format(m.getEnddate()));
					map.put("MoveTypeId",  m.getMovetypeid());
					map.put("InterfaceBerthNo",  m.getInterfaceberthno());
					mFeeInterfaceTallyBakList.add(map);
				}
			}
			
			if (mFeeInterfaceTallyBakList.size() > 0) {
				try {
					JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				    //Client client = dcf.createClient("http://114.116.100.75:8082/interfaceservice.svc?wsdl");
					Client client = dcf.createClient("http://10.128.141.66/interface/interfaceservice.svc?wsdl");
					
				    ObjectMapper json = new ObjectMapper();
				    System.out.println("start-----------------xin ji fei ----ting bo---------     ++++1");
				    Object[] objects = new Object[0]; 
				    String params= null; //接口第一个参数 json数据
				    String params1= "Berth"; //接口第二个参数 固定值 参数
				    JSONObject jsonObj = new JSONObject();
				    jsonObj.put("UserId", HdUtils.getCurUser().getUserId());
				    jsonObj.put("UserName", HdUtils.getCurUser().getName());
				    jsonObj.put("Ip", "XX");
				    jsonObj.put("InterfaceList", json.writeValueAsString(mFeeInterfaceTallyBakList));
				    JSONObject jsonObj1 = new JSONObject();
				    jsonObj1.put("datas", jsonObj.toString());
				    params = jsonObj1.toString();
					objects = client.invoke("SendDataToInterface", params,params1);
				    
					String a = objects[0].toString();
		 	        if ("0".equals(a.substring(a.length()-2, a.length()-1))) {
		 	        	HdMessageCode hdMessageCode = new HdMessageCode();
						hdMessageCode.setCode("-1");
						try {
							CargoR cargoR = JSON.parse(a, CargoR.class);
							hdMessageCode.setMessage(cargoR.getResultMessage());
						} catch(Exception e) {
							e.printStackTrace();
						}
						return hdMessageCode;
		 	        }else{
		 	        	for (int i = 0; i < idList.size(); i++) {
		 	        		String id =idList.get(i);
		 					QueryParamLs paramLs = new QueryParamLs();
				 	   		String sql="update MFeeInterfaceBerth a set a.jfOkId='1',a.sendname=:name,a.sendtime=:sendtime where a.id=:id";
				 	   		paramLs.addParam("id", id);
				 	   		paramLs.addParam("name", HdUtils.getCurUser().getName());
				 	   		paramLs.addParam("sendtime", HdUtils.getDateTime());
				 	   		JpaUtils.execUpdate(sql, paramLs);
		 	        	}
		 	        }
				} catch (Exception e) {
					throw new HdRunTimeException("操作失败！");
				}
			} else {
				throw new HdRunTimeException("数据不能为空！");
			}
			
			return HdUtils.genMsg();
		}
		/*
		 * 发送删除报文
		 */
		@RequestMapping("/deleteData")
		@ResponseBody
		public HdMessageCode deleteData(String ids) {
			String message= "";
			List<String> idList = HdUtils.paraseStrs(ids);
			for (int i = 0; i < idList.size(); i++) {
				QueryParamLs paramLs = new QueryParamLs();
				String sql="update MFeeInterfaceBerth a set a.delSendId='1' where a.id=:id";
				String id =idList.get(i);
				paramLs.addParam("id", id);
				JpaUtils.execUpdate(sql, paramLs);
				MFeeInterfaceBerth bean = JpaUtils.findById(MFeeInterfaceBerth.class, id);
				if (bean != null) {
					try {
						JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
						//Client client = dcf.createClient("http://114.116.100.75:8082/interfaceservice.svc?wsdl");
						Client client = dcf.createClient("http://10.128.141.66/interface/interfaceservice.svc?wsdl");
						Object[] objects = new Object[0];

						objects = client.invoke("DeleteInterfaceDataByBerthNo", bean.getInterfaceberthno());

						String a = objects[0].toString();
						// CargoR cargoR = JSON.parse(a, CargoR.class);
						// String resultCode = cargoR.getResultCode();
						if ("0".equals(a.substring(a.length() - 2, a.length() - 1))) {
							message="接口发送失败或已经重复发送!";
							throw new HdRunTimeException("接口数据删除失败！");
						}else{
			 	        	message="发送成功!";
					 	   		sql="update MFeeInterfaceBerth a set a.jfDelOkId='1' where a.id=:id";
					 	   		JpaUtils.execUpdate(sql, paramLs);
			 	        }
					} catch (java.lang.Exception e) {
						throw new HdRunTimeException("删除失败！");
					}
				}
			}			
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
	public ShipPlanSend findone(String spsendId) {
		if (HdUtils.strIsNull(spsendId)) {
			ShipPlanSend shipPlanSend = new ShipPlanSend();
			return shipPlanSend;
		}
		return shipPlanSendService.findone(spsendId);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipPlanSend> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return shipPlanSendService.save(gridData);
	}
	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/saveVoyage")
	@ResponseBody	
	public HdMessageCode saveVoyage(@RequestBody HdEzuiSaveDatagridData<MFeeInterfaceVoyage> gridData) {
		//CommonUtil.initEntity(gridData);
		return shipPlanSendService.saveVoyage(gridData);
	}
	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/saveBerth")
	@ResponseBody	
	public HdMessageCode saveBerth(@RequestBody HdEzuiSaveDatagridData<MFeeInterfaceBerth> gridData) {
		//CommonUtil.initEntity(gridData);
		return shipPlanSendService.saveBerth(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipPlanSend shipPlanSend) {

		return shipPlanSendService.saveone(shipPlanSend);
	}
}
