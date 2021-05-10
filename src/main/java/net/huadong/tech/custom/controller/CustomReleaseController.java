package net.huadong.tech.custom.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.custom.entity.CustomRelease;
import net.huadong.tech.custom.service.CustomReleaseService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/custom/CustomRelease")
public class CustomReleaseController  {
	
	@Autowired
	private CustomReleaseService customReleaseService; 
	
	//菜单进入
		@RequestMapping(value = "/custom.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/custom/custom";
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
	    return customReleaseService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CustomRelease> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return customReleaseService.save(gridData);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CustomRelease customRelease) {

		return customReleaseService.saveone(customRelease);
	}
	
	/**
	 * 提单下拉
	 */
	@RequestMapping(value = "/getShipBillDrop")
	@ResponseBody
	public List<ShipBill> getShipBillDrop(String shipNo,String iEId) {
		List<ShipBill> list=new ArrayList<ShipBill>();
		String jpql= " select a  from  ShipBill a  where 1=1 and a.shipNo=:shipNo and a.iEId=:iEId ";
		QueryParamLs params=new QueryParamLs();
		params.addParam("shipNo",shipNo);
		params.addParam("iEId",iEId);
		List<ShipBill>  ls=JpaUtils.findAll(jpql, params);
		for(ShipBill sb:ls){
			ShipBill  shipBill=new ShipBill();
			shipBill.setBillNo(sb.getBillNo());
			shipBill.setConsignCod(sb.getConsignCod());
			shipBill.setCarNum(sb.getCarNum());
			shipBill.setCustomBillNo(sb.getCustomBillNo());
			shipBill.setWeights(sb.getWeights());
			shipBill.setValumes(sb.getValumes());
			shipBill.setShipNo(sb.getShipNo());
			shipBill.setPieces(sb.getPieces());
			shipBill.setiEId(sb.getiEId());
			list.add(shipBill);
		}
		return list;
	}
	@RequestMapping(value = "/getShipBillDrop2")
	@ResponseBody
	public List<PortCar> getShipBillDrop2(String shipNo,String iEId) {
		List<PortCar> list=new ArrayList<PortCar>();
		String jpql= " select a  from  PortCar a  where 1=1 and a.shipNo=:shipNo and a.iEId=:iEId ";
		QueryParamLs params=new QueryParamLs();
		params.addParam("shipNo",shipNo);
		params.addParam("iEId",iEId);
		List<PortCar>  ls=JpaUtils.findAll(jpql, params);
		for(PortCar sb:ls){
			PortCar  portCar=new PortCar();
			portCar.setBillNo(sb.getBillNo());
			portCar.setConsignCod(sb.getConsignCod());
			portCar.setCustomBillNo(sb.getCustomBillNo());
			portCar.setWeights(sb.getWeights());
			portCar.setVolumes(sb.getVolumes());
			portCar.setShipNo(sb.getShipNo());
			portCar.setiEId(sb.getiEId());
			list.add(portCar);
		}
		return list;
	}
}
