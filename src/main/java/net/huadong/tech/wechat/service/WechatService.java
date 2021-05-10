package net.huadong.tech.wechat.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.thirdparty.service.Result;

/**
 * 
 * @Description: 微信服务
 * @author yurh
 * @date 2017年5月2日 上午10:11:47
 *
 */
public interface WechatService {

	
	
	// 登录
	Result isLogin(String account, String password,HttpServletRequest request);


	Result findShipwmxc(HdEzuiQueryParams hdEzuiQueryParams);
   //选择残损类型
	Result chooseDamCod(HdEzuiQueryParams hdEzuiQueryParams);

	Result chooseDamArea(HdEzuiQueryParams hdEzuiQueryParams);

	Result chooseDamLevel(HdEzuiQueryParams hdEzuiQueryParams);

	Result chooseInCharge(HdEzuiQueryParams hdEzuiQueryParams);



	//集疏港
	Result findSparseset(HdEzuiQueryParams hdEzuiQueryParams);

	//转栈出场
	Result findStacks(HdEzuiQueryParams hdEzuiQueryParams);

	Result findtradeId(HdEzuiQueryParams hdEzuiQueryParams);

	Result findiEId(HdEzuiQueryParams hdEzuiQueryParams);

	Result findcarTyp(HdQuery hdQuery);

	Result findbrandCod(HdQuery hdQuery);

	Result findcarKind(HdQuery hdQuery);

	Result findport(HdEzuiQueryParams hdEzuiQueryParams);

	Result findworkRunNam(HdEzuiQueryParams hdEzuiQueryParams);

	Result chooseShipBill(String shipNo);
	Result chooseLoadShipBill(String shipNo);

	Result choosecyAreaNo(HdEzuiQueryParams hdEzuiQueryParams);

	Result choosecyRowNo(HdEzuiQueryParams hdEzuiQueryParams);

	Result choosecyBayNo(HdEzuiQueryParams hdEzuiQueryParams);
	
	String getCarLocation(String cyAreaNo); 



	Result chooseWorkRun(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findShipList(String hdEzuiQueryParams);

	Result findShipftnmzc(HdEzuiQueryParams hdEzuiQueryParams);

	Result findShipnmxc(HdEzuiQueryParams hdEzuiQueryParams);

	Result findWmsg(HdEzuiQueryParams hdEzuiQueryParams);

	Result findWmjg(HdEzuiQueryParams hdEzuiQueryParams);

	Result findNmsg(HdEzuiQueryParams hdEzuiQueryParams);

	Result findNmjg(HdEzuiQueryParams hdEzuiQueryParams);

	Result findShipzc(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findShipnmzc(HdEzuiQueryParams hdEzuiQueryParams);

	Result findShipwmzc(HdEzuiQueryParams hdEzuiQueryParams);

	Result findShipftnmxc(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findVcPortID(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findAllPortID(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findUserList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findPortFTList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findShipInOutCheckList(String  shipNo,String workTyp);
	
	Result findYardInList(String shipNo);
	
	Result findPortCarList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findPortCarStateList(Map map);
	
	Result findBrandList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCCarKindList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCCarTypList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCCarVinList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCyAreaNoList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCyRowNoList(HdEzuiQueryParams hdEzuiQueryParams);
	
	Result findCyBayNoList(HdEzuiQueryParams hdEzuiQueryParams);
	Result findMbillVin(String shipNo);
	Result findCrfid(String shipNo);
	Result findBillCarBytShipNo(String shipNo);
	
	
	String ftnmLoadShip(String req);
	
	void ftnmUnLoadShip(String req);
	
	/**
	 * 集港 or 卸船 系统创建1个车架号
	 * @param account 登录账号
	 * @return
	 */
    String createRfidVinno(String account,String rfidNo);  
    
	/**
	 * 外贸卸船 系统创建1个车架号
	 * @param rfidNo rfid编号
	 * @return
	 */
    String createMbvRfidVin(String rfidNo);     
    
    /**
	 * 装船 or 提货  根据rfid_no 找vin_no
	 * @param account 登录账号
	 * @return
	 */
    String getRfidVinno(String rifdNo);
 
    /**
	 * 外贸卸船  根据rfid_no 找vin_no
	 * @return
	 */
    String getMbvRfidVinno(String shipNo,String rifdNo);
    
    /**
     * 复尺单查询
     * @param hdEzuiQueryParams
     * @return
     */    
    Result findFcd(String vinNo);
    
    /**
     * 复尺单保存
     * @param req
     * @return
     */
    String saveFcd(String req);
    //保存提单
    String saveShipBillPortCar(String req);
    
    //装卸船车数
    String findCarNumByShip(HdQuery hdQuery);
   //装卸船车数
    String findCarNumByShipAndBill(HdQuery hdQuery);
    
}
