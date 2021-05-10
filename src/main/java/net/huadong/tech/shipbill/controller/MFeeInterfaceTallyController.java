package net.huadong.tech.shipbill.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.ui.Model;

import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTally;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTallyBak;
import net.huadong.tech.shipbill.service.MFeeInterfaceTallyService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import org.springframework.stereotype.Controller;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.util.Axis2CargoUtil;
import net.huadong.tech.util.Axis2Util;
import net.huadong.tech.util.CargoR;
import net.huadong.tech.util.DateJsonValueProcessor;
import net.huadong.tech.util.HdUtils;

/**
 * @author yuliang
 * @date 2020-05-13
 */
@Controller
@RequestMapping("webresources/login/shipbill/MFeeInterfaceTally") // 注意路径
public class MFeeInterfaceTallyController {

	@Autowired
	private MFeeInterfaceTallyService mFeeInterfaceTallyService;

	@RequestMapping(value = "/mfeeinterfacetally.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/mfeeinterfacetally";
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
		return mFeeInterfaceTallyService.find(hdQuery);
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public MFeeInterfaceTally findone(String noPkName) {
		if (HdUtils.strIsNull(noPkName)) {// 增加时默认初值
			MFeeInterfaceTally entity = new MFeeInterfaceTally();
			return entity;
		}
		return mFeeInterfaceTallyService.findone(noPkName);
	}

	/**
	 * 单条删除
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public HdMessageCode remove(@RequestBody MFeeInterfaceTally mfeeinterfacetally) {
		mFeeInterfaceTallyService.remove(mfeeinterfacetally.getId());
		return HdUtils.genMsg();
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		mFeeInterfaceTallyService.removeAll(ids);
		return HdUtils.genMsg();
	}

	/**
	 * 保存资源信息
	 *
	 * @param MFeeInterfaceTally
	 * @return
	 */
	@RequestMapping(value = "/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MFeeInterfaceTally mfeeinterfacetally) {
		return mFeeInterfaceTallyService.saveone(mfeeinterfacetally);
	}

	/**
	 * 保存资源信息
	 *
	 * @param MFeeInterfaceTally
	 * @return
	 */
	@RequestMapping(value = "/saveAll")
	@ResponseBody
	public HdMessageCode saveAll(@RequestBody HdEzuiSaveDatagridData<MFeeInterfaceTally> gridData) {
		return mFeeInterfaceTallyService.saveAll(gridData);
	}

	/*
	 * 更新数据
	 */
	@RequestMapping("/updateData")
	@ResponseBody
	public HdMessageCode updateData(String shipNo, String iEId, String transPortTypeId, String moveTyp) {
		return mFeeInterfaceTallyService.updateData(shipNo, iEId, transPortTypeId, moveTyp);
	}

	/*
	 * 删除数据
	 */
	@RequestMapping("/deleteData")
	@ResponseBody
	public HdMessageCode deleteData(String shipNo, String billNo) {
		String str = "";
		String jpql = "select a from MFeeInterfaceTally a where a.manifestno =:manifestno and a.voyagefacilityinterfaceid =:voyagefacilityinterfaceid";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("manifestno", billNo);
		paramLs.addParam("voyagefacilityinterfaceid", shipNo);
		List<MFeeInterfaceTally> list = JpaUtils.findAll(jpql, paramLs);
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			//Client client = dcf.createClient("http://114.116.100.75:8082/interfaceservice.svc?wsdl");
			Client client = dcf.createClient("http://10.128.141.66/interface/interfaceservice.svc?wsdl");
			
			ObjectMapper json = new ObjectMapper();
			System.out.println("start-----------------xin ji fei ----lu yun---------     ++++1");
			Object[] objects = new Object[0];

			objects = client.invoke("DeleteSendDataByManifestNo", billNo);

			String a = objects[0].toString();
			// CargoR cargoR = JSON.parse(a, CargoR.class);
			// String resultCode = cargoR.getResultCode();
			if ("0".equals(a.substring(a.length() - 2, a.length() - 1))) {
				HdMessageCode hdMessageCode = new HdMessageCode();
				hdMessageCode.setCode("-1");
				try {
					CargoR cargoR = JSON.parse(a, CargoR.class);
					hdMessageCode.setMessage(cargoR.getResultMessage());
				} catch(Exception e) {
					e.printStackTrace();
				}
				return hdMessageCode;
			} else if ("1".equals(a.substring(a.length() - 2, a.length() - 1))) {
				for (MFeeInterfaceTally data : list) {
					data.setDelId("1");
					data.setDelSendId("1");
					data.setJfDelOkId("1");
					JpaUtils.update(data);
				}
			}
		} catch (java.lang.Exception e) {
			throw new HdRunTimeException("删除失败！");
		}
		return HdUtils.genMsg();
	}

	/*
	 * 上传数据
	 */
	@RequestMapping("/uploadData")
	@ResponseBody
	public HdMessageCode uploadData(String ids) {
		List<String> idList = HdUtils.paraseStrs(ids);
		List<MFeeInterfaceTally> mFeeInterfaceTallyList = new ArrayList<MFeeInterfaceTally>();
		String str = "";
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		for (String id : idList) {
			MFeeInterfaceTally bean = JpaUtils.findById(MFeeInterfaceTally.class, id);
			bean.setSendname(HdUtils.getCurUser().getName());
			bean.setSendtime(HdUtils.getDateTime());
			if (bean != null) {
				mFeeInterfaceTallyList.add(bean);
				MFeeInterfaceTallyBak mFeeInterfaceTallyBak = new MFeeInterfaceTallyBak();
				mFeeInterfaceTallyBak = mFeeInterfaceTallyService.copyData(bean);
				
				if (HdUtils.strNotNull(str)){
					str += "," + JSONObject.fromObject(mFeeInterfaceTallyBak, config).toString();
				} else {
					str += JSONObject.fromObject(mFeeInterfaceTallyBak, config).toString();
				}
			}
		}
		if (mFeeInterfaceTallyList.size() > 0) {
			try {
				JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
				//Client client = dcf.createClient("http://114.116.100.75:8082/interfaceservice.svc?wsdl");
				Client client = dcf.createClient("http://10.128.141.66/interface/interfaceservice.svc?wsdl");

				ObjectMapper json = new ObjectMapper();
				System.out.println("start-----------------xin ji fei ----lu yun---------     ++++1");
				Object[] objects = new Object[0];
				String params = null; // 接口第一个参数 json数据
				String params1 = "CargoMove"; // 接口第二个参数 固定值 参数
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("UserId", HdUtils.getCurUser().getUserId());
				jsonObj.put("UserName", HdUtils.getCurUser().getName());
				jsonObj.put("Ip", "XX");
				jsonObj.put("InterfaceList", "[" + str + "]");
				JSONObject jsonObj1 = new JSONObject();
				jsonObj1.put("datas", jsonObj.toString());
				params = jsonObj1.toString();
				objects = client.invoke("SendDataToInterface", params, params1);

				String a = objects[0].toString();
				if ("0".equals(a.substring(a.length() - 2, a.length() - 1))) {
					HdMessageCode hdMessageCode = new HdMessageCode();
					hdMessageCode.setCode("-1");
					try {
						CargoR cargoR = JSON.parse(a, CargoR.class);
						hdMessageCode.setMessage(cargoR.getResultMessage());
					} catch(Exception e) {
						e.printStackTrace();
					}
					return hdMessageCode;
				} else if ("1".equals(a.substring(a.length() - 2, a.length() - 1))) {
					for (MFeeInterfaceTally data : mFeeInterfaceTallyList) {
						data.setBwSendId("1");
						data.setJfOkId("1");
						JpaUtils.update(data);
					}
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();
				//throw new HdRunTimeException("操作失败！");
				throw new HdRunTimeException("接口异常！");
			}
		} else {
			throw new HdRunTimeException("数据不能为空！");
		}
		return HdUtils.genMsg();
	}

}
