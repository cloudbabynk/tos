package net.huadong.tech.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.util.HdUtils;
import net.sf.json.JSONObject;

@Component
@Configurable
@EnableScheduling
public class ShipTask {

	//在场车辆信息
	public static List<EzTreeBean> allShiplst=null;
	
	//每5分钟执行一次
    @Scheduled(cron = "0 */5 *  * * * ")
	    public void loadAllShip(){	
		allShiplst=findTree();
	}
    public  List<EzTreeBean> getTree() {
    	if(allShiplst==null) allShiplst=this.findTree();

    	List cpList=new ArrayList<>();
    	
		for (EzTreeBean ezTreeBean : allShiplst) {
			EzTreeBean cpEzTreeBean=new EzTreeBean();
			BeanUtils.copyProperties(ezTreeBean, cpEzTreeBean);
			if(cpEzTreeBean.getChildren().size()>0) {
				List<EzTreeBean> chLst=cpEzTreeBean.getChildren();
				List<EzTreeBean> rtChlst=new ArrayList<>();
				for (EzTreeBean chTreeBean : chLst) {
					EzTreeBean cpEzTreeBeanItem=new EzTreeBean();
					BeanUtils.copyProperties(chTreeBean, cpEzTreeBeanItem);
					rtChlst.add(cpEzTreeBeanItem);
				}
				cpEzTreeBean.setChildren(rtChlst);
			}
			cpList.add(cpEzTreeBean);
		}


		return cpList;
    }

    //和shipServceImpl功能一样
    public List<EzTreeBean> findTree() {// yl.生成
		List<EzTreeBean> result = new ArrayList<>();
		HdUtils.addDay(HdUtils.getDateTime(), -90);
		EzTreeBean one = new EzTreeBean();
		one.setText("预报");
		one.setState(EzTreeBean.OPEN);
		EzTreeBean two = new EzTreeBean();
		two.setText("沽口");
		EzTreeBean five = new EzTreeBean();
		five.setText("存栈");
		EzTreeBean third = new EzTreeBean();
		third.setText("到港");
		EzTreeBean fous = new EzTreeBean();
		fous.setText("离港");
		result.add(five);
		result.add(one);
		result.add(two);
		result.add(third);
		result.add(fous);
		String jpql = " select  a  from  Ship a  where  a.shipStat=:shipStat";
		QueryParamLs param1 = new QueryParamLs();
		QueryParamLs param2 = new QueryParamLs();
		QueryParamLs param3 = new QueryParamLs();
		QueryParamLs param4 = new QueryParamLs();
		param1.addParam("shipStat", Ship.STATUS_1);
		param2.addParam("shipStat", Ship.STATUS_2);
		param3.addParam("shipStat", Ship.STATUS_3);
		param4.addParam("shipStat", Ship.STATUS_4);
		String jpql1 = " select  a  from  CShipData a  where a.shipCodId=:shipCodId  order by  a.updTim";
		List<Ship> list1 = JpaUtils.findAll(jpql, param1);
		if (list1.size() > 0) {
			for (Ship ship : list1) {
				QueryParamLs p1 = new QueryParamLs();
				p1.addParam("shipCodId", ship.getShipCodId());
				List<CShipData> list = JpaUtils.findAll(jpql1, p1);
				for (CShipData cShipData : list) {
					EzTreeBean child1 = new EzTreeBean();
					JSONObject objShip=JSONObject.fromObject(ship);
					child1.setAttributes(objShip.toString());
					String str1 = namestr(ship, Ship.STATUS_1, cShipData);
					child1.setText(str1);
					child1.setType(cShipData.getcShipNam() + "," + cShipData.geteShipNam());
					one.getChildren().add(child1);
					child1.setId(ship.getShipNo());
					child1.setUserId(ship.getShipStat());
					
					ship.setcShipNam(cShipData.getcShipNam());
					child1.setObj(ship);
				}
			}
		}
		List<Ship> list2 = JpaUtils.findAll(jpql, param2);
		if (list2.size() > 0) {
			for (Ship ship : list2) {
				QueryParamLs p1 = new QueryParamLs();
				p1.addParam("shipCodId", ship.getShipCodId());
				List<CShipData> list = JpaUtils.findAll(jpql1, p1);
				for (CShipData cShipData : list) {
					EzTreeBean child2 = new EzTreeBean();
					JSONObject objShip=JSONObject.fromObject(ship);
					child2.setAttributes(objShip.toString());
					String str2 = namestr(ship, Ship.STATUS_2, cShipData);
					child2.setText(str2);
					child2.setType(cShipData.getcShipNam() + "," + cShipData.geteShipNam());
					two.getChildren().add(child2);
					child2.setId(ship.getShipNo());
					child2.setUserId(ship.getShipStat());
					ship.setcShipNam(cShipData.getcShipNam());
					child2.setObj(ship);
				}
			}
		}
		//存栈船
		EzTreeBean child5 = new EzTreeBean();
		Ship bean = JpaUtils.findById(Ship.class, "20190311082013");
		child5.setType("存栈" + "," + "cunzhan");
		child5.setText("存栈船");
		five.getChildren().add(child5);
		child5.setId(bean.getShipNo());
		child5.setUserId(Ship.STATUS_1);
		child5.setObj(bean);
		
		List<Ship> list3 = JpaUtils.findAll(jpql, param3);
		if (list3.size() > 0) {
			for (Ship ship : list3) {
				QueryParamLs p1 = new QueryParamLs();
				p1.addParam("shipCodId", ship.getShipCodId());
				List<CShipData> list = JpaUtils.findAll(jpql1, p1);
				for (CShipData cShipData : list) {
					EzTreeBean child3 = new EzTreeBean();
					JSONObject objShip=JSONObject.fromObject(ship);
					child3.setAttributes(objShip.toString());
					String str3 = namestr(ship, Ship.STATUS_3, cShipData);
					child3.setText(str3);
					child3.setType(cShipData.getcShipNam() + "," + cShipData.geteShipNam());
					third.getChildren().add(child3);
					child3.setId(ship.getShipNo());
					child3.setUserId(ship.getShipStat());
					ship.setcShipNam(cShipData.getcShipNam());
					child3.setObj(ship);
				}
			}
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.DATE, -3);
		jpql += " order by a.recTim desc";
		List<Ship> list4 = JpaUtils.findAll(jpql, param4);
		if (list4.size() > 0) {
			for (Ship ship : list4) {
				QueryParamLs p1 = new QueryParamLs();
				p1.addParam("shipCodId", ship.getShipCodId());
				List<CShipData> list = JpaUtils.findAll(jpql1, p1);
				for (CShipData cShipData : list) {
					EzTreeBean child4 = new EzTreeBean();
					JSONObject objShip=JSONObject.fromObject(ship);
					child4.setAttributes(objShip.toString());
					String str4 = namestr(ship, Ship.STATUS_4, cShipData);
					child4.setText(str4);
					child4.setType(cShipData.getcShipNam() + "," + cShipData.geteShipNam());
					fous.getChildren().add(child4);
					child4.setId(ship.getShipNo());
					child4.setUserId(ship.getShipStat());
					ship.setcShipNam(cShipData.getcShipNam());
					child4.setObj(ship);
				}
			}
		}
		return result;
	}
	public static final String JK = "I";// 进口
	public static final String CK = "E";// 出口
    public static String namestr(Ship sp, String type,  String iEId) {
		String str1 = "";
		if (JK.equals(iEId)) {
			if (HdUtils.strNotNull(sp.getcShipNam())) {// 船名
				str1 += sp.getcShipNam();
			}

			if (HdUtils.strNotNull(sp.getIvoyage())) {
				str1 += "(" + sp.getIvoyage() + ")";
			}
			if (HdUtils.strNotNull(sp.getTradeId())) {// 内外贸
				// str1+=sp.getTradeFlg();
				if ("2".equals(sp.getTradeId())) {
					str1 += "外";
				} else if ("1".equals(sp.getTradeId())) {
					str1 += "内";
				}
			}
			if (Ship.STATUS_1.equals(type)) {
				if (sp.getEtdArrvTim() != null) {// 预抵港时间
					str1 += strnew(HdUtils.getDateTimeStr(sp.getEtdArrvTim())) + "";
				}
			} else if (Ship.STATUS_2.equals(type)) {
				if (sp.getConArrvTim() != null) {// 抵锚时间
					str1 += strnew(HdUtils.getDateTimeStr(sp.getConArrvTim())) + "";
				}
			} else if (Ship.STATUS_3.equals(type)) {// 靠泊时间
				if (sp.getToPortTim() != null) {
					str1 += strnew(HdUtils.getDateTimeStr(sp.getToPortTim())) + "";
				}
			} else if (Ship.STATUS_4.equals(type)) {// 离港时间
				if (sp.getLeavPortTim() != null) {
					str1 += strnew(HdUtils.getDateTimeStr(sp.getLeavPortTim())) + "";
				}
			}
		}
		if (CK.equals(iEId)) {
			if (HdUtils.strNotNull(sp.getcShipNam())) {// 船名
				str1 += sp.getcShipNam();
			}
			if (HdUtils.strNotNull(sp.getEvoyage())) {
				str1 += "(" + sp.getEvoyage() + ")";
			}
			if (HdUtils.strNotNull(sp.getTradeId())) {// 内外贸
				// str1+=sp.getTradeFlg();
				if ("2".equals(sp.getTradeId())) {
					str1 += "外";
				} else if ("1".equals(sp.getTradeId())) {
					str1 += "内";
				}
			}
			if (Ship.STATUS_1.equals(type)) {
				if (sp.getEtdArrvTim() != null) {// 预抵港时间
					str1 += strnew(HdUtils.getDateTimeStr(sp.getEtdArrvTim())) + "";
				}
			} else if (Ship.STATUS_2.equals(type)) {
				if (sp.getConArrvTim() != null) {// 抵锚时间
					str1 += strnew(HdUtils.getDateTimeStr(sp.getConArrvTim())) + "";
				}
			} else if (Ship.STATUS_3.equals(type)) {// 靠泊时间
				if (sp.getToPortTim() != null) {
					str1 += strnew(HdUtils.getDateTimeStr(sp.getToPortTim())) + "";
				}
			} else if (Ship.STATUS_4.equals(type)) {// 离港时间
				if (sp.getLeavPortTim() != null) {
					str1 += strnew(HdUtils.getDateTimeStr(sp.getLeavPortTim())) + "";
				}
			}
		}
		return str1;
	}
    public static String namestr(Ship sp, String type, CShipData cShipData) {
		String str1 = "";
		if (HdUtils.strNotNull(cShipData.getcShipNam())) {// 船名
			str1 += cShipData.getcShipNam();
		}
		if (HdUtils.strNotNull(sp.getIvoyage())) {// 航次
			str1 += "（" + sp.getIvoyage() + "）";
		}
		if (HdUtils.strNotNull(sp.getTradeId())) {// 内外贸
			// str1+=sp.getTradeFlg();
			if (Ship.NM.equals(sp.getTradeId())) {
				str1 += "内";
			} else if (Ship.WM.equals(sp.getTradeId())) {
				str1 += "外";
			}
		}
		if (Ship.STATUS_1.equals(type) || Ship.STATUS_2.equals(type)) {
			if (sp.getConArrvTim() != null) {// 抵锚时间
				str1 += strnew(HdUtils.getDateTimeStr(sp.getConArrvTim())) + "";
			}
		} else if (Ship.STATUS_3.equals(type)) {// 靠泊时间
			if (sp.getToPortTim() != null) {
				str1 += strnew(HdUtils.getDateTimeStr(sp.getToPortTim())) + "";
			}
		} else if (Ship.STATUS_4.equals(type)) {// 离港时间
			if (sp.getLeavPortTim() != null) {
				str1 += strnew(HdUtils.getDateTimeStr(sp.getLeavPortTim())) + "";
			}
		}
		return str1;
	}
	public static String strnew(String str) {
		String s = "";
		s = str.replace("2017", "");
		s = s.substring(1);
		s = s.replace("-", "/");
		s = "-" + s;
		return s;
	}
}
