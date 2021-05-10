package net.huadong.tech.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ShipBill;

public class Axis2Util {
	public static final String RESULT_SUCCESS = "SUCCESS";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_NOPREVILGE = "NOSUCCESS";
	public static final String RESULT_NODATA = "NOROW";
	public static final String GZMT = "03406500";
	public static final String HQMT = "03409000";
	@Transactional
	public void getCustomRelease(String shipNo,String billNo) throws Exception {
		Ship ship=JpaUtils.findById(Ship.class, shipNo);
//		String serviceUrl = "http://10.128.141.21:9090/axis2/services/NewNzxPassDate?wsdl";
		String serviceUrl = "http://10.163.204.253:9090/axis2/services/NewNzxPassDate?wsdl";
	    RPCServiceClient serviceClient = null;
	    String resultString = "";
		// 服务器端WebService的WSDL连接串
		serviceClient = getServiceClient(serviceUrl);
		// 服务器端开放的方法名
		String wsFunction = "getNewNzxPass";
		System.out.println(wsFunction);
		if(GZMT.equals(ship.getDockCod())){
			String[] params = { "GGZMT", "Gg03406500$zmt", "NEWNZXPASS", billNo };
			resultString = login(serviceUrl, serviceClient, params, wsFunction);	
		}
	   if(HQMT.equals(ship.getDockCod())){
			// 要传给服务器开放方法的参数.
			String[] params = { "GHQGZ", "Gh03409000#qgz", "NEWNZXPASS", billNo };
			resultString = login(serviceUrl, serviceClient, params, wsFunction);	
		}
		if (RESULT_SUCCESS.equals(resultString)) {
			//throw new HdRunTimeException("可以获取数据！");
		}
		if (RESULT_ERROR.equals(resultString)) {
			throw new HdRunTimeException("调用接口失败！不能获取数据！");
		}
		if (RESULT_NOPREVILGE.equals(resultString)) {
			throw new HdRunTimeException("没有查询海关放行的权限！");
		}
		if (RESULT_NODATA.equals(resultString)) {
			throw new HdRunTimeException("没有对应的数据！");
		}
		System.out.println("resultString=" + resultString);
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
		QName opLogin = new QName("http://ws.apache.org/axis2", wsFunction);
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
		Class[] returnTypes = new Class[] { CustomR.class };
		Object[] o = serviceClient.invokeBlocking(opLogin, inputArgs, returnTypes);
		CustomR customr = (CustomR) o[0];
		//现阶段先更新ShipBill的放行时间
		String jpql="select a from ShipBill a  where a.billNo=:billNo ";
		QueryParamLs paramLs=new QueryParamLs();
		paramLs.addParam("billNo",customr.getBill_no());
		List<ShipBill> shipBillList=JpaUtils.findAll(jpql, paramLs);
		if(shipBillList.size()>0){
		for(ShipBill sb:shipBillList){
	    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = null;
		try {
			date = sd.parse(customr.getCreate_time().toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}  
        String str=sdf.format(date);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
        try {
			Date dat = sf.parse(str);
			Timestamp tt = new Timestamp(dat.getTime());  
			sb.setReleaseTim(tt);
			JpaUtils.update(sb);
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		}	
		}
		return customr.getResult();
	}
}
