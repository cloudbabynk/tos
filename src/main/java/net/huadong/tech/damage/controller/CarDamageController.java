package net.huadong.tech.damage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CDamgArea;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.damage.service.CarDamageService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/damage/CarDamage")
public class CarDamageController  {
	
	@Autowired
	private CarDamageService carDamageService; 
	
	//菜单进入
		@RequestMapping(value = "/cardamage.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/damage/cardamage";
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
	    return carDamageService.find(hdQuery);
	}

	@RequestMapping(value = "/findPortCar")
	@ResponseBody
	public HdEzuiDatagridData findPortCar(@RequestBody HdQuery hdQuery) {
	    return carDamageService.findPortCar(hdQuery);
	}
	
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CarDamage> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return carDamageService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String cardamagIds) {
		carDamageService.removeAll(cardamagIds);
		return HdUtils.genMsg();
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CarDamage findone(String cardamagId) {
		if (HdUtils.strIsNull(cardamagId)) {
			CarDamage carDamage = new CarDamage();
			return carDamage;
		}
		return carDamageService.findone(cardamagId);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CarDamage carDamage) {

		return carDamageService.saveone(carDamage);
	}
	//残损代码下拉
	@RequestMapping(value = "/getCDamageCodDrop")
	@ResponseBody
	public List<EzDropBean> getCDamageCodDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CDamage a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CDamage>  ls=JpaUtils.findAll(jpql, params);
			for(CDamage cd:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cd.getDamCod());
				drop.setLabel(cd.getDamNam());
				list.add(drop);
			}
			return list;
		}
	//残损等级下拉
	@RequestMapping(value = "/getCDamgLevelDrop")
	@ResponseBody
	public List<EzDropBean> getCDamgLevelDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CDamgLevel a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CDamgLevel>  ls=JpaUtils.findAll(jpql, params);
			for(CDamgLevel cd:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cd.getDamgLevelCod());
				drop.setLabel(cd.getDamgLevel());
				list.add(drop);
			}
			return list;
		}
	//残损范围下拉
		@RequestMapping(value = "/getCDamgAreaDrop")
		@ResponseBody
		public List<EzDropBean> getCDamgAreaDrop() {
				List<EzDropBean> list=new ArrayList<EzDropBean>();
				String jpql= " select a  from  CDamgArea a  where 1=1 ";
				QueryParamLs params=new QueryParamLs();
				List<CDamgArea>  ls=JpaUtils.findAll(jpql, params);
				for(CDamgArea cd:ls){
					EzDropBean  drop=new EzDropBean();
					drop.setValue(cd.getDamgAreaCod());
					drop.setLabel(cd.getDamgArea());
					list.add(drop);
				}
				return list;
			}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCarDamage")
	@ResponseBody
	public HdMessageCode findCarDamage(String cardamagId) {
		return carDamageService.findCarDamage(cardamagId);
	}
}
