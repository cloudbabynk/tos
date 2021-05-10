package net.huadong.tech.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTally;
import net.huadong.tech.shipbill.entity.MFeeInterfaceTallyBak;
import net.huadong.tech.shipbill.entity.ShipBill;

public class Axis2CargoUtil {
	public static final String RESULT_SUCCESS = "SUCCESS";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_NOPREVILGE = "NOSUCCESS";
	public static final String RESULT_NODATA = "NOROW";
	public static final String GZMT = "03406500";
	public static final String HQMT = "03409000";
	@Transactional
	public void uploadData(String ids) throws Exception {
		List<String> idList = HdUtils.paraseStrs(ids);
		List<MFeeInterfaceTallyBak> mFeeInterfaceTallyBakList = new ArrayList<MFeeInterfaceTallyBak>();
		for (String id : idList) {
			MFeeInterfaceTally bean = JpaUtils.findById(MFeeInterfaceTally.class, id);
			if (bean != null) {
				MFeeInterfaceTallyBak mFeeInterfaceTallyBak = new MFeeInterfaceTallyBak();
				BeanUtils.copyProperties(bean, mFeeInterfaceTallyBak);
				mFeeInterfaceTallyBakList.add(mFeeInterfaceTallyBak);
			}
		}
		String serviceUrl = "http://114.116.100.75:8082/interfaceservice.svc?wsdl";
	    RPCServiceClient serviceClient = null;
	    String resultString = "";
		// 服务器端WebService的WSDL连接串
		serviceClient = getServiceClient(serviceUrl);
		// 服务器端开放的方法名
		String wsFunction = "SendDataToInterface";
		System.out.println(wsFunction);
		ObjectMapper json = new ObjectMapper();
	    System.out.println("start-----------------xin ji fei ----lu yun---------     ++++1");
	    Object[] objects = new Object[0]; 
	    String params= null; //接口第一个参数 json数据
	    String params1= "CargoMove"; //接口第二个参数 固定值 参数
	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("UserId", HdUtils.getCurUser().getUserId());
	    jsonObj.put("UserName", HdUtils.getCurUser().getName());
	    jsonObj.put("Ip", "XX");
	    jsonObj.put("InterfaceList", "["+json.writeValueAsString(mFeeInterfaceTallyBakList)+"]");
	    JSONObject jsonObj1 = new JSONObject();
	    jsonObj1.put("datas", jsonObj.toString());
	    params = jsonObj1.toString();
			// 要传给服务器开放方法的参数.
		String[] paramData = { params, params1 };
		resultString = login(serviceUrl, serviceClient, paramData, wsFunction);	
	}

	public RPCServiceClient getServiceClient(String wsdlUrl) {
		RPCServiceClient serviceClient = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(wsdlUrl);
			options.setTo(targetEPR);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return serviceClient;
	}

	public String login(String serviceUrl, RPCServiceClient serviceClient, String[] params, String wsFunction)
			throws AxisFault {
		// 在创建QName对象时,QName类的构造方法的第一个参数表示WSDL文件的命名空间名，也就是<wsdl:definitions>元素的targetNamespace属性值
		QName opLogin = new QName("http://tempuri.org/", wsFunction);
		// 参数,如果有多个,继续往后面增加即可,不用指定参数的名称
		Object[] inputArgs = new Object[] {};
		if (params.length > 0) {
			inputArgs = new Object[] { params };
		}
		/*
		 * 返回参数类型，这个和axis1有点区别
		 * invokeBlocking方法有三个参数，其中第一个参数的类型是QName对象，表示要调用的方法名；
		 * 第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；
		 * 第三个参数表示WebService方法的返回值类型的Class对象，参数类型为Class[]。
		 * 当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}
		 * 如果被调用的WebService方法没有返回值，应使用RPCServiceClient类的invokeRobust方法，
		 * 该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同
		 */
		Class[] returnTypes = new Class[] { Object.class };
		Object[] o = serviceClient.invokeBlocking(opLogin, inputArgs, returnTypes);
		try{
			Map map = (Map)JSON.parse(o[0].toString());  
		    String flag = map.get("resultCode").toString();
		} catch (Exception e) {
			
		}
		
		return "suc";
	}
}
