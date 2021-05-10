package net.huadong.tech.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.sf.json.JSONObject;

public class XStreamUtil {
	public static String filenam;
	private static XStream xstream;
	@Value("${api.service.ip}")
	private static String apiServiceIp;
	// dom解析驱动
	public static final XStreamNameCoder nameCoder = new XStreamNameCoder();

	// 编码格式
	private static final String ENCODING = "UTF-8";
	private static final DomDriver fixDriver = new DomDriver(ENCODING, nameCoder);
	// 通用解析器
	public static final XStream XSTREAM = new XStream(fixDriver);
	private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	// xml转java对象
	public static void xmlToBean(String iEId, String eShipnam, String Voyage) {
		// xstream = new XStream();
		xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.alias("Head", head.class);
		xstream.alias("RepresentativePerson", representativePerson.class);
		xstream.alias("ExitCustomsOffice", exitCustomsOffice.class);
		xstream.alias("Agent", agent.class);
		xstream.alias("Carrier", carrier.class);
		xstream.alias("Declaration", declaration.class);
		xstream.alias("Consignment", consignment.class);
		xstream.addImplicitCollection(declaration.class, "consignmentList");
		xstream.alias("Contact", contact.class);
		xstream.addImplicitCollection(contact.class, "communicationList");
		xstream.alias("Communication", communication.class);
        xstream.addImplicitCollection(consignor.class, "communicationList");
		xstream.alias("Communication", communication.class);
		xstream.alias("NotifyParty", notifyParty.class);
		xstream.addImplicitCollection(notifyParty.class, "communicationList");
		xstream.alias("Consignee", consignee.class);
        xstream.addImplicitCollection(consignee.class, "communicationList");
		xstream.alias("RoutingCountryCode", String.class);
		xstream.addImplicitCollection(consignment.class, "routingCountryCodeList");
		xstream.alias("Manifest", manifest.class);
		// String xmlurl=filenam.replaceAll("\\/", "//");
		String xml = filenam;
		try {
			FileInputStream fis = new FileInputStream(xml);
		    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//		    BufferedReader br = new BufferedReader(isr);
//		    System.out.println(br);
//			manifest M = (manifest) xstream.fromXML(new FileReader(new File(xml)));
		    manifest M = (manifest) xstream.fromXML(isr);
			int csize = M.getDeclaration().consignmentList.size();
			String voyage = M.getDeclaration().getBorderTransportMeans().getJourneyID();
			String eShipNam=M.getDeclaration().getBorderTransportMeans().getName();
			if (!(Voyage.equals(voyage)&&eShipnam.replaceAll(" ", "").equals(eShipNam.replaceAll(" ", "")))){
				throw new HdRunTimeException("该舱单不属于该航次！");// 舱单与该航次不匹配
			}
			String sql = "SELECT SHIP_NO,TRADE_ID FROM SHIP WHERE REPLACE(E_SHIP_NAM,' ','') = '" + eShipNam.replaceAll(" ", "") + "'\r\n";
			if (Ship.JK.equals(iEId)){
				sql += "AND IVOYAGE = '" + voyage + "'\r\n";
			} else if (Ship.CK.equals(iEId)){
				sql += "AND EVOYAGE = '" + voyage + "'\r\n";
			}
			List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
			if (objList.size() == 0) {
				throw new HdRunTimeException("该舱单不属于该航次！");// 改舱单与该航次不匹配
			}
//			else{
//				//覆盖外贸集港理货生成的提单信息
//				String jpqlaa="select a from ShipBill a where a.shipNo=:shipNo and a.iEId='E' and a.tradeId='2'";
//				QueryParamLs param1a = new QueryParamLs();
//				param1a.addParam("shipNo", shipList.get(0).getShipNo());
//				List<ShipBill> sbL=JpaUtils.findAll(jpqlaa, param1a);
//				if(sbL.size()>0){
//				for(ShipBill s:sbL){
//					JpaUtils.remove(s);
//				}	
//				}
//			}
			for (int i = 0; i < csize; i++) {
				ShipBill shipBill = new ShipBill();
				for (Object[] s : objList) {
					if("I".equals(iEId)){
						shipBill.setiEId("I");	
					}
					if("E".equals(iEId)){
						shipBill.setiEId("E");	
					}
					shipBill.setTradeId(s[1].toString());
					shipBill.setShipNo(s[0].toString());
				}
				shipBill.setBillNo(M.getDeclaration().consignmentList.get(i).getTransportContractDocument().getID());
				shipBill.setLoadPortCod(
						M.getDeclaration().consignmentList.get(i).getLoadingLocation().getID().substring(0, 5));
				shipBill.setTranPortCod(
						M.getDeclaration().consignmentList.get(i).getUnloadingLocation().getID().substring(0, 5));
				//System.out.println(M.getDeclaration().consignmentList.get(i).getGrossVolumeMeasure()+"****");
				if(M.getDeclaration().consignmentList.get(i).getGrossVolumeMeasure()!=null){
				shipBill.setValumes(
						(new BigDecimal(M.getDeclaration().consignmentList.get(i).getGrossVolumeMeasure())));
				}
				shipBill.setCarNum(new BigDecimal(
						M.getDeclaration().consignmentList.get(i).getConsignmentPackaging().getQuantityQuantity()));

				String carTypENam = M.getDeclaration().consignmentList.get(i).getConsignmentItem().getCommodity().getCargoDescription();
				shipBill.setCargoNam(carTypENam);
				
				//dockcode
				String jpqlShip = "select a from Ship a where a.shipNo=:shipNo ";
				QueryParamLs paramLShip = new QueryParamLs();
				paramLShip.addParam("shipNo", shipBill.getShipNo());
				List<Ship> dockCodelist = JpaUtils.findAll(jpqlShip, paramLShip);
				if(dockCodelist.size() > 0) {
					shipBill.setDockCod(dockCodelist.get(0).getDockCod());					
				}
				
				String jpql3 = "select a from CBrand a where 1=1";
				List<CBrand> cbrandList = JpaUtils.findAll(jpql3, null);
				for (CBrand cb : cbrandList) {
					if (carTypENam.contains(cb.getBrandEname())) {
						shipBill.setBrandCod(cb.getBrandCod());
					}
				}
				shipBill.setConsignNam(M.getDeclaration().consignmentList.get(i).getConsignor().getName());
				shipBill.setReceiveNam(M.getDeclaration().consignmentList.get(i).getConsignee().getName());
				int c = M.getDeclaration().consignmentList.get(i).getConsignmentItem().getConsignmentItemPackaging()
						.getMarksNumbers().length();
				if (c > 16) {
					shipBill.setMarks(M.getDeclaration().consignmentList.get(i).getConsignmentItem()
							.getConsignmentItemPackaging().getMarksNumbers().substring(0, 15));
				} else {
					shipBill.setMarks(M.getDeclaration().consignmentList.get(i).getConsignmentItem()
							.getConsignmentItemPackaging().getMarksNumbers());
				}
				shipBill.setPieces(new BigDecimal(
						M.getDeclaration().consignmentList.get(i).getConsignmentPackaging().getQuantityQuantity()));
				shipBill.setWeights(new BigDecimal(M.getDeclaration().consignmentList.get(i).getConsignmentItem()
						.getGoodsMeasure().getGrossMassMeasure()));
				String uuid = UUID.randomUUID().toString();
				ShipBill sbill = JpaUtils.findById(ShipBill.class, uuid);
				if (sbill != null) {
					throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
				} else {
					shipBill.setShipbillId(uuid);
					shipBill.setConfirmId("0");
					shipBill.setExitCustomId("0");
					JpaUtils.save(shipBill);
					//改为手动向综合查询系统同步数据
					//insertIntoZongheCha(shipBill);
					BillSplit billSplit = new BillSplit();
					String uid = HdUtils.genUuid();
					billSplit.setBillspId(uid);
					billSplit.setShipbillId(shipBill.getShipbillId());
					String jpqla = "select a from Ship a where a.shipNo=:shipNo ";
					QueryParamLs paramLs = new QueryParamLs();
					paramLs.addParam("shipNo", shipBill.getShipNo());
					List<Ship> shiplist = JpaUtils.findAll(jpqla, paramLs);
					if (shiplist.size() > 0) {
						for (Ship s : shiplist) {
							billSplit.setcShipNam(s.getcShipNam());
							billSplit.setVoyage(s.getIvoyage() + '/' + s.getEvoyage());
							billSplit.setInPortTim(s.getToPortTim());
							billSplit.setOutPortTim(s.getLeavPortTim());
						}
					}
					billSplit.setTradeId(shipBill.getTradeId());
					billSplit.setiEId(shipBill.getiEId());
					billSplit.setShipNo(shipBill.getShipNo());
					billSplit.setBillNo(shipBill.getBillNo());
					billSplit.setBrandCod(shipBill.getBrandCod());
					billSplit.setCarTyp(shipBill.getCarTyp());
					billSplit.setCargoNam(shipBill.getCargoNam());
					billSplit.setPieces(shipBill.getPieces());
					billSplit.setWeights(shipBill.getWeights());
					billSplit.setVolumes(shipBill.getValumes());
					billSplit.setRecNam(HdUtils.getCurUser().getAccount());
					billSplit.setRecTim(HdUtils.getDateTime());
					billSplit.setUseShipworkPerson("1");
					JpaUtils.save(billSplit);
					HdUtils.genMsg();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static {
		xstream = new XStream(fixDriver);
		xstream.alias("PassInfoAck", PassInfoAck.class);
	}

	// java对象转xml
	public static String beanToXml(Object obj) {
		XStream xstream = XSTREAM;
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(obj);
	}
	public static void insertIntoZongheCha(ShipBill shipBill) {
		JSONObject jsonObj = new JSONObject();
		String uuid=HdUtils.genUuid().substring(0, 8);
		jsonObj.put("id", uuid);
		String vss_no=_10_to_62(Long.parseLong(shipBill.getShipNo()), 5);
		jsonObj.put("vss_no", vss_no);
	     Ship ship=JpaUtils.findById(Ship.class, shipBill.getShipNo());
		jsonObj.put("vss_name", ship.getcShipNam());
		jsonObj.put("voyage", shipBill.getVoyage());
		jsonObj.put("in_out_flag",shipBill.getiEId());
		jsonObj.put("b_l", shipBill.getBillNo());
		jsonObj.put("quantity", shipBill.getPieces().longValue());
		jsonObj.put("true_quantity",  shipBill.getPieces().longValue());
		jsonObj.put("vol", shipBill.getValumes());
		jsonObj.put("single_weight",shipBill.getWeights().divide(shipBill.getPieces(), 3, BigDecimal.ROUND_HALF_UP));
		jsonObj.put("charge_weight", shipBill.getWeights().divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP));
		jsonObj.put("charge_vol",shipBill.getValumes());
		jsonObj.put("charge_quantity",shipBill.getPieces().longValue());
		jsonObj.put("lh_check_people", HdUtils.getCurUser().getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		jsonObj.put("lh_check_tim",format.format(HdUtils.getDateTime()));
		jsonObj.put("hy_check_people", HdUtils.getCurUser().getName());
		jsonObj.put("hy_check_tim", format.format(HdUtils.getDateTime()));
		jsonObj.put("check_flag", "0");
		jsonObj.put("flag_charge", "0");
		jsonObj.put("flag_apply", "0");
		jsonObj.put("check_time",format.format(HdUtils.getDateTime()));
		jsonObj.put("check_person", HdUtils.getCurUser().getName());
		if ("03406500".equals(ship.getDockCod())) {
			jsonObj.put("token", "roroBilling");
		}
		if ("03409000".equals(ship.getDockCod())) {
			jsonObj.put("token", "globalBilling");
		}
		String url = apiServiceIp+"8081/RoroBillingSysWebApi/setShipBill";
		String query = jsonObj.toString();

		String response = "";
		try {
			URL httpUrl = null; // HTTP URL类 用这个类来创建连接
			// 创建URL
			httpUrl = new URL(url);
			// 建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST请求
			try (OutputStream os = conn.getOutputStream()) {
				os.write(query.getBytes("UTF-8"));
			}
			// out.flush();
			// 读取响应
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				response = sbf.toString();
				String str = '{' + "\"code\"" + ":0" + '}';
				String str2 = '{' + "\"code\"" + ":1" + '}';
				if (str.equals(response)) {
					throw new HdRunTimeException("上报舱单数据失败！");
				}
				if (str2.equals(response)) {
					throw new HdRunTimeException("上报舱单数据成功！");
				}
				// JSONObject j = JSON.parseObject(response);
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			/**
			 * 计费连接接口问题导致上报失败！
			 */
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常！");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			// try {
			// if (os != null) {
			// out.close();
			// }
			// if (reader != null) {
			// reader.close();
			// }
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// }
		}
		}
	public static String _10_to_62(long number, int length){  
	    Long rest=number;  
	    Stack<Character> stack=new Stack<Character>();  
	    StringBuilder result=new StringBuilder(0);  
	    while(rest!=0){  
	        stack.add(charSet[new Long((rest-(rest/62)*62)).intValue()]);  
	        rest=rest/62;  
	    }  
	    for(;!stack.isEmpty();){  
	        result.append(stack.pop());  
	    }  
	    int result_length = result.length();  
	    StringBuilder temp0 = new StringBuilder();  
	    for(int i = 0; i < length - result_length; i++){  
	        temp0.append('0');  
	    }  
	      
	    return temp0.toString() + result.toString();  

	} 
}
