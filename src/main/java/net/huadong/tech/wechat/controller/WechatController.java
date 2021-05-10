package net.huadong.tech.wechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.utils.HdCipher;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.wechat.service.WechatService;

/**
 * 
 * @Description: 微信服务
 * @author yurh
 * @date 2017年10月23日 上午10:29:13 修改：zhangK 修改内容：
 */
@Controller
@RequestMapping("webresources/wx")
public class WechatController {

	@Autowired
	WechatService wechatService;

	/************************* 用户基本信息处理 ***********************************************************/

	/**
	 * 
	 * @Description: 登录判断
	 */
	@RequestMapping(value = "/isLogin", method = RequestMethod.GET)
	@ResponseBody
	public Result isLogin(@RequestParam("userCod") String account, @RequestParam("password") String password,
			HttpServletRequest request) {
		return wechatService.isLogin(account, HdCipher.getMD(password, "MD5"), request);
	}

	// 选装外贸卸船
	@RequestMapping(value = "/chooseShip/findShipwmxc", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseShipzx(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipwmxc(hdEzuiQueryParams);
	}

	// 选装船
	@RequestMapping(value = "/chooseShip/findShipzc", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseShipzc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipzc(hdEzuiQueryParams);
	}	
	
	// 选装内贸装船
	@RequestMapping(value = "/chooseShip/findShipnmzc", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseShipnmzc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipnmzc(hdEzuiQueryParams);
	}

	// 选装内贸装船
	@RequestMapping(value = "/chooseShip/findShipwmzc", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseShipwmzc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipwmzc(hdEzuiQueryParams);
	}

	// 选装内贸卸船
	@RequestMapping(value = "/chooseShip/findShipnmxc", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseShipnmxc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipnmxc(hdEzuiQueryParams);
	}

	// 集疏港
	@RequestMapping(value = "/chooseShip/findSparseset", method = RequestMethod.POST)
	@ResponseBody
	public Result findSparseset(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findSparseset(hdEzuiQueryParams);
	}

	// 外贸疏港
	@RequestMapping(value = "/chooseShip/findWmsg", method = RequestMethod.POST)
	@ResponseBody
	public Result findWmsg(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findWmsg(hdEzuiQueryParams);
	}

	// 外贸集港
	@RequestMapping(value = "/chooseShip/findWmjg", method = RequestMethod.POST)
	@ResponseBody
	public Result findWmjg(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findWmjg(hdEzuiQueryParams);
	}

	// 内贸疏港
	@RequestMapping(value = "/chooseShip/findNmsg", method = RequestMethod.POST)
	@ResponseBody
	public Result findNmsg(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findNmsg(hdEzuiQueryParams);
	}

	// 内贸集港
	@RequestMapping(value = "/chooseShip/findNmjg", method = RequestMethod.POST)
	@ResponseBody
	public Result findNmjg(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findNmjg(hdEzuiQueryParams);
	}

	// 转栈出场
	@RequestMapping(value = "/chooseBill/findStacks", method = RequestMethod.POST)
	@ResponseBody
	public Result findStacks(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findStacks(hdEzuiQueryParams);
	}

	// 选择残损类型
	@RequestMapping(value = "/chooseDamCod/find", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseDamCod(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.chooseDamCod(hdEzuiQueryParams);
	}

	// 选择残损类型
	@RequestMapping(value = "/chooseDamArea/find", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseDamArea(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.chooseDamArea(hdEzuiQueryParams);
	}

	// 选择残损类型
	@RequestMapping(value = "/chooseDamLevel/find", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseDamLevel(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.chooseDamLevel(hdEzuiQueryParams);
	}

	// 选择责任方
	@RequestMapping(value = "/chooseInCharge/find", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseInCharge(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.chooseInCharge(hdEzuiQueryParams);
	}

	/**
	 * 贸易下拉
	 */
	@RequestMapping(value = "/findtradeId")
	@ResponseBody
	public Result findtradeId(HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findtradeId(hdEzuiQueryParams);
	}

	/**
	 * 进出口
	 */
	@RequestMapping(value = "/findiEId")
	@ResponseBody
	public Result findiEId(HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findiEId(hdEzuiQueryParams);
	}

	/**
	 * 车型
	 */
	@RequestMapping(value = "/findcarTyp")
	@ResponseBody
	public Result findcarTyp(@RequestBody HdQuery hdQuery) {
		return wechatService.findcarTyp(hdQuery);
	}

	/**
	 * 品牌
	 */
	@RequestMapping(value = "/findbrandCod")
	@ResponseBody
	public Result findbrandCod(@RequestBody HdQuery hdQuery) {
		return wechatService.findbrandCod(hdQuery);
	}

	/**
	 * 类别
	 */
	@RequestMapping(value = "/findcarKind")
	@ResponseBody
	public Result findcarKind(@RequestBody HdQuery hdQuery) {
		return wechatService.findcarKind(hdQuery);
	}

	/**
	 * 港口
	 */
	@RequestMapping(value = "/findport")
	@ResponseBody
	public Result findport(HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findport(hdEzuiQueryParams);
	}

	/**
	 * 早夜班
	 */
	@RequestMapping(value = "/findworkRunNam")
	@ResponseBody
	public Result findworkRunNam(HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findworkRunNam(hdEzuiQueryParams);
	}

	/**
	 * 提单号
	 */
	@RequestMapping(value = "/chooseShipBill")
	@ResponseBody
	public Result chooseShipBill(String shipNo) {
		return wechatService.chooseShipBill(shipNo);
	}
	@RequestMapping(value = "/chooseLoadShipBill")
	@ResponseBody
	public Result chooseLoadShipBill(String shipNo) {
		return wechatService.chooseLoadShipBill(shipNo);
	}

	/**
	 * 场号下拉
	 */
	@RequestMapping(value = "/choosecyAreaNo")
	@ResponseBody
	public Result choosecyAreaNo(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.choosecyAreaNo(hdEzuiQueryParams);
	}
	
	/**
	 * 行号下拉
	 */
	@RequestMapping(value = "/choosecyRowNo")
	@ResponseBody
	public Result choosecyRowNo(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.choosecyRowNo(hdEzuiQueryParams);
	}

	/**
	 * 车位号下拉
	 */
	@RequestMapping(value = "/choosecyBayNo")
	@ResponseBody
	public Result choosecyBayNo(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.choosecyBayNo(hdEzuiQueryParams);
	}

	@RequestMapping(value = "/chooseWorkRun")
	@ResponseBody
	public Result chooseWorkRun(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.chooseWorkRun(hdEzuiQueryParams);
	}
	

	// 丰田装船
	@RequestMapping(value = "/chooseShip/findShipftnmzc", method = RequestMethod.POST)
	@ResponseBody
	public Result findShipftnmzc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipftnmzc(hdEzuiQueryParams);
	}

	// 丰田卸船
	@RequestMapping(value = "/chooseShip/findShipftnmxc", method = RequestMethod.POST)
	@ResponseBody
	public Result findShipftnmxc(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findShipftnmxc(hdEzuiQueryParams);
	}
	
	//丰田流向 
	@RequestMapping(value = "/chooseVcPortID/findVcPortID", method = RequestMethod.POST)
	@ResponseBody
	public Result findVcPortID(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findVcPortID(hdEzuiQueryParams);
	}
	
	//全流向 
	@RequestMapping(value = "/chooseAllPortID/findAllPortID", method = RequestMethod.POST)
	@ResponseBody
	public Result chooseAllPortID(@RequestBody HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findAllPortID(hdEzuiQueryParams);
	}
	
	// 离线模式下载船列表
	@RequestMapping(value = "/findShipList", method = RequestMethod.POST)
	@ResponseBody
	public Result findShipList(HttpServletRequest request) {
		String shipNo=request.getParameter("shipNo");
		String workTyp=request.getParameter("workTyp");
		return wechatService.findShipList(shipNo);
	}
	
	// 离线模式下载用户列表
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	@ResponseBody
	public Result findUserList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findUserList(hdEzuiQueryParams);
	}
	
	// 离线模式下载丰田流向列表
	@RequestMapping(value = "/findPortFTList", method = RequestMethod.POST)
	@ResponseBody
	public Result findPortFTList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findPortFTList(hdEzuiQueryParams);
	}
	
	// 离线模式下载装卸船计划明细列表
	@RequestMapping(value = "/findShipInOutCheckList", method = RequestMethod.POST)
	@ResponseBody
	public Result findShipInOutCheckList(HttpServletRequest request) {
		String shipNo=request.getParameter("shipNo");
		String workTyp=request.getParameter("workTyp");
		return wechatService.findShipInOutCheckList(shipNo,workTyp);
	}
	
	// 离线模式下载
	@RequestMapping(value = "/findYardInList", method = RequestMethod.POST)
	@ResponseBody
	public Result findYardInList(HttpServletRequest request) {
		String shipNo=request.getParameter("shipNo");
		return wechatService.findYardInList(shipNo);
	}
	
	// 离线模式下载在港车辆列表 版本1 升级后可去掉 2019/8-12
	@RequestMapping(value = "/findPortCarList", method = RequestMethod.POST)
	@ResponseBody
	public Result findPortCarList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findPortCarList(hdEzuiQueryParams);
	}
	// 离线模式下载在港车辆列表 版本2
	@RequestMapping(value = "/findPortCarStateList", method = RequestMethod.POST)
	@ResponseBody
	public Result findPortCarStateList(HttpServletRequest request) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("sign",request.getParameter("sign"));
		return wechatService.findPortCarStateList(map);
	}
	
	
	// 离线模式下载品牌列表
	@RequestMapping(value = "/findBrandList", method = RequestMethod.POST)
	@ResponseBody
	public Result findBrandList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findBrandList(hdEzuiQueryParams);
	}
	
	// 离线模式下载车类列表
	@RequestMapping(value = "/findCCarKindList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCCarKindList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCCarKindList(hdEzuiQueryParams);
	}
	
	// 离线模式下载车型列表
	@RequestMapping(value = "/findCCarTypList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCCarTypList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCCarTypList(hdEzuiQueryParams);
	}
	
	// 离线模式下载车架号对应关系列表
	@RequestMapping(value = "/findCCarVinList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCCarVinList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCCarVinList(hdEzuiQueryParams);
	}
	
	// 离线模式下载车架号对应关系列表
	@RequestMapping(value = "/findCyAreaNoList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCyAreaNoList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCyAreaNoList(hdEzuiQueryParams);
	}
	// 离线模式下载行号
	@RequestMapping(value = "/findCyRowNoList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCyRowNoList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCyRowNoList(hdEzuiQueryParams);
	}
	// 离线模式下载列号
	@RequestMapping(value = "/findCyBayNoList", method = RequestMethod.POST)
	@ResponseBody
	public Result findCyBayNoList(@RequestBody(required = false) HdEzuiQueryParams hdEzuiQueryParams) {
		return wechatService.findCyBayNoList(hdEzuiQueryParams);
	}
	//丰田装船上传至中间表
	@RequestMapping(value = "/ftnmLoadShip")
	@ResponseBody
	public String ftnmLoadShip(String req) {
		return wechatService.ftnmLoadShip(req);
	}
	
	//丰田卸船上传至中间表
	@RequestMapping(value = "/ftnmUnLoadShip")
	@ResponseBody
	public void ftnmUnLoadShip(String req) {
		wechatService.ftnmUnLoadShip(req);
	}
	
	/**
	 * 集港 or 卸船 系统创建1个车架号
	 * @param account 登录账号
	 * @return
	 */
	@RequestMapping(value = "/createRfidVinno")
	@ResponseBody
	public String createRfidVinno(String account,String rfidNo) {
		return wechatService.createRfidVinno(account,rfidNo);
	}
	/**
	 * 外贸卸船 系统创建1个车架号
	 * @param rfidNo rfid编号
	 * @return
	 */
	@RequestMapping(value = "/createMbvRfidVin")
	@ResponseBody
	public String createMbvRfidVin(String rfidNo) {
		return wechatService.createMbvRfidVin(rfidNo);
	}	
	
	/**
	 * 装船 or 提货  根据rfid_no 找vin_no
	 * @param account 登录账号
	 * @return
	 */
	@RequestMapping(value = "/getRfidVinno")
	@ResponseBody
	public String getRfidVinno(String rifdNo) {
		return wechatService.getRfidVinno(rifdNo);
	}
	
	/**
	 * 外贸卸船 根据rfid_no 找vin_no
	 * @param account 登录账号
	 * @return
	 */
	@RequestMapping(value = "/getMbvRfidVinno")
	@ResponseBody
	public String getMbvRfidVinno(String shipNo,String rifdNo) {
		return wechatService.getMbvRfidVinno(shipNo, rifdNo);
	}	
	
	// 复尺单
	@RequestMapping(value = "/findFcd", method = RequestMethod.POST)
	@ResponseBody
	public Result findFcd(String vinNo) {
		return wechatService.findFcd(vinNo);
	}
	// 复尺单保存
	@RequestMapping(value = "/saveFcd", method = RequestMethod.POST)
	@ResponseBody
	public String saveFcd(String req) {
		return wechatService.saveFcd(req);
	}
	// 下货纸保存
	@RequestMapping(value = "/saveShipBillPortCar", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String saveShipBillPortCar(String req) {
		return wechatService.saveShipBillPortCar(req);
	}
	
	@RequestMapping(value = "/findMbillVin", method = RequestMethod.POST)
	@ResponseBody
	public Result findMbillVin(String shipNo) {
		return wechatService.findMbillVin(shipNo);
	}
	@RequestMapping(value = "/findCrfid", method = RequestMethod.POST)
	@ResponseBody
	public Result findCrfid(String shipNo) {
		return wechatService.findCrfid(shipNo);
	}
	@RequestMapping(value = "/findBillCarBytShipNo", method = RequestMethod.POST)
	@ResponseBody
	public Result findBillCarBytShipNo(String shipNo) {
		return wechatService.findBillCarBytShipNo(shipNo);
	}
	
}
