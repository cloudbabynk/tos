package net.huadong.tech.cargo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CTruck;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.cargo.service.TruckWorkService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.CargoInfo;

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
@RequestMapping("webresources/login/cargo/TruckWork")
public class TruckWorkController  {
	
	@Autowired
	private TruckWorkService truckWorkService; 
	
	//菜单进入
	@RequestMapping(value = "/truckwork.htm")
	public String  pageTh(String Type,Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		model.addAttribute("Type", Type);
		return "system/cargo/truckwork";
	}
	
	
	//菜单进入
	@RequestMapping(value = "/nmsg.htm")
	public String  pageSg(String Type,Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		model.addAttribute("Type", Type);
		return "system/cargo/nmsg";
	}
	
	//菜单进入
	@RequestMapping(value = "/truckworkall.htm")
	public String  pageTruckAll(String Type,Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		model.addAttribute("Type", Type);
		return "system/ship/truckworkall";
	}
	/**
	 * 理货员下拉
	 */
	@RequestMapping(value = "/getAuthUserDrop")
	@ResponseBody
	public List<EzDropBean> getAuthUserDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  AuthUser a  where a.orgnId =:orgnId ";
			QueryParamLs params=new QueryParamLs();
			params.addParam("orgnId", "0e9a117162d3468388e09b35fc3b5a7f");
			List<AuthUser>  ls=JpaUtils.findAll(jpql, params);
			for(AuthUser cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getUserId());
				drop.setLabel(cc.getName());
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
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String ids) {
		truckWorkService.removeAll(ids);
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
	    return truckWorkService.find(hdQuery);
	}
	
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findJglh")
	@ResponseBody
	public HdEzuiDatagridData findJglh(@RequestBody HdQuery hdQuery) {
	    return truckWorkService.findJglh(hdQuery);
	}
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findHycz")
	@ResponseBody
	public HdEzuiDatagridData findHycz(@RequestBody HdQuery hdQuery) {
	    return truckWorkService.findHycz(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public TruckWork findone(String id) {
		if (HdUtils.strIsNull(id)) {
			TruckWork truckWork = new TruckWork();
			return truckWork;
		}
		return truckWorkService.findone(id);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<TruckWork> gridData,String ingateId,String gateNo) {
	 	 //CommonUtil.initEntity(gridData);
		return truckWorkService.save(gridData,ingateId,gateNo);
	}
	
	
	/**
     * 疏港批量理货 货运出闸
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/saveHycz")
	@ResponseBody	
	public HdMessageCode saveHycz(String ingateId,String gateNo) {
	 	 //CommonUtil.initEntity(gridData);
		return truckWorkService.saveHycz(ingateId,gateNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody TruckWork truckWork,String driver,String lengthOverId,String widthOverId,String useMachId,String useWorkerId) {
		return truckWorkService.saveone(truckWork,driver,lengthOverId,widthOverId,useMachId,useWorkerId);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveJglh")
	@ResponseBody
	public HdMessageCode saveJglh(@RequestBody CargoInfo cargoInfo, String type) {
		return truckWorkService.saveJglh(cargoInfo,type);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveJgsclh")
	@ResponseBody
	public HdMessageCode saveJgsclh(@RequestBody CargoInfo cargoInfo) {
		return truckWorkService.saveJgsclh(cargoInfo);
	}
	
	/*
	 * 转配
	 */
	@RequestMapping("/updateJgsclh")
	@ResponseBody
	public HdMessageCode updateJgsclh(String shipNo, String billNos, String newBillNo) {
		return truckWorkService.updateJgsclh(shipNo,billNos,newBillNo);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/updateJgsclhBill")
	@ResponseBody
	public HdMessageCode updateJgsclhBill(@RequestBody CargoInfo cargoInfo) {
		return truckWorkService.updateJgsclhBill(cargoInfo);
	}
	
	/*
	 * 改提单号
	 */
	@RequestMapping("/updateBillNo")
	@ResponseBody
	public HdMessageCode updateBillNo(@RequestBody CargoInfo cargoInfo) {
		return truckWorkService.updateBillNo(cargoInfo);
	}
	/**
	 * 批量保存
	 */
	@RequestMapping(value = "/saveAll")
	@ResponseBody
	public HdMessageCode saveAll(String portCarNos,@RequestBody CargoInfo cargoInfo) {
		return truckWorkService.saveAll(portCarNos,cargoInfo);
	}
	
	
	/**
	 * 批量保存
	 */
	@RequestMapping(value = "/saveAllSglh")
	@ResponseBody
	public HdMessageCode saveAllSglh(@RequestBody CargoInfo cargoInfo) {
		return truckWorkService.saveAllSglh(cargoInfo);
	}
}
