package net.huadong.tech.cargo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.cargo.entity.MChangeShip;
import net.huadong.tech.cargo.service.MChangeShipService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
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
@RequestMapping("webresources/login/cargo/MChangeShip")
public class MChangeShipController  {
	
	@Autowired
	private MChangeShipService mChangeShipService; 
	
	//菜单进入
	@RequestMapping(value = "/mchangeship.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/cargo/mchangeship";
	}
	//菜单进入
	@RequestMapping(value = "/mchangeshippl.htm")
	public String  pagePl(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/cargo/mchangeshippl";
	}
	/**
	 * 提单号下拉
	 */
	@RequestMapping(value = "/getShipBillkDrop")
	@ResponseBody
	public List<EzDropBean> getShipBillkDrop(String shipNo) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  ShipBill a  where a.shipNo =:shipNo ";
			QueryParamLs params=new QueryParamLs();
			params.addParam("shipNo", shipNo);
			List<ShipBill>  ls=JpaUtils.findAll(jpql, params);
			for(ShipBill cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getShipbillId());
				drop.setLabel(cc.getBillNo());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 车辆品牌下拉
	 */
	@RequestMapping(value = "/getCBrandkDrop")
	@ResponseBody
	public List<EzDropBean> getCBrandkDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CBrand a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CBrand>  ls=JpaUtils.findAll(jpql, params);
			for(CBrand cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBrandCod());
				drop.setLabel(cc.getBrandNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 司机下拉
	 */
	@RequestMapping(value = "/getCEmployeeDrop")
	@ResponseBody
	public List<EzDropBean> getCEmployeeDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CEmployee a  where a.empTypCod =:empTypCod ";
			QueryParamLs params=new QueryParamLs();
			params.addParam("empTypCod", "08");
			List<CEmployee>  ls=JpaUtils.findAll(jpql, params);
			for(CEmployee cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getEmpNo());
				drop.setLabel(cc.getEmpNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 批量保存
	 */
	@RequestMapping(value = "/saveAll")
	@ResponseBody
	public HdMessageCode saveAll(String portCarNos,String shipbillId,String newTranPortCod,String newDiscPortCod) {
		return mChangeShipService.saveAll(portCarNos,shipbillId,newTranPortCod,newDiscPortCod);
	}
	
	/**
	 * 批量保存
	 */
	@RequestMapping(value = "/savePL")
	@ResponseBody
	public HdMessageCode savePL(String shipNos,String billNos,String brandCods,String carTyps,String cyAreaNos,String countNum,String newTranPortCod,String newDiscPortCod,String newShipNo,String newBillNo,int num,String flag,String dockCod) {
		return mChangeShipService.savePL(shipNos,billNos,brandCods,carTyps,cyAreaNos,countNum,newTranPortCod,newDiscPortCod,newShipNo,newBillNo,num,flag,dockCod);
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
	    return mChangeShipService.find(hdQuery);
	}
	//
	@RequestMapping(value = "/findPL")
	@ResponseBody
	public HdEzuiDatagridData findPL(@RequestBody HdQuery hdQuery) {
	    return mChangeShipService.findPL(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public MChangeShip findone(String id) {
		if (HdUtils.strIsNull(id)) {
			MChangeShip mChangeShip = new MChangeShip();
			return mChangeShip;
		}
		return mChangeShipService.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MChangeShip> gridData,String ingateId,String gateNo) {
	 	 //CommonUtil.initEntity(gridData);
		return mChangeShipService.save(gridData,ingateId,gateNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MChangeShip mChangeShip) {

		return mChangeShipService.saveone(mChangeShip);
	}
	
	/**
	 * 提单号下拉
	 */
	@RequestMapping(value = "/getShipBillkDropz")
	@ResponseBody
	public List<EzDropBean> getShipBillkDropz(String shipNo) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  ShipBill a  where a.shipNo =:shipNo ";
			QueryParamLs params=new QueryParamLs();
			params.addParam("shipNo", shipNo);
			List<ShipBill>  ls=JpaUtils.findAll(jpql, params);
			for(ShipBill cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBillNo());
				drop.setLabel(cc.getBillNo());
				list.add(drop);
			}
			return list;
		}
	
}
