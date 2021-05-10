package net.huadong.tech.base.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCable;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.service.CCableService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
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
@RequestMapping("webresources/login/base/CCable")
public class CCableController  {
	
	@Autowired
	private CCableService cCableService; 
	
	//菜单进入
	@RequestMapping(value = "/ccable.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/ccable";
	}
	
	/*
	 * 获取泊位下拉列表
	 */	
	@RequestMapping(value = "/getCBerthDrop")
	@ResponseBody
	public List<EzDropBean> getDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a from  CBerth a where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CBerth>  ls=JpaUtils.findAll(jpql, params);
			for(CBerth cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getBerthCod());
				drop.setLabel(cc.getBerthNam());
				list.add(drop);
			}
			return list;
		}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String cableCods) {
		cCableService.removeAll(cableCods);
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
	    return cCableService.find(hdQuery);
	}
	
	
	
	/**
     * 揽桩下拉
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findLz")
	@ResponseBody
	public HdEzuiDatagridData findLz(@RequestBody HdQuery hdQuery) {
		String jpql= " select a  from  CCable a ";
		QueryParamLs paramLs=new QueryParamLs();
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<CCable> cCableList = result.getRows();
		for(CCable cCable : cCableList){
			if(HdUtils.strNotNull(cCable.getBerthCod())){
				CBerth cBerth = JpaUtils.findById(CBerth.class, cCable.getBerthCod());
				cCable.setCableNo(cCable.getCableNo()+"/"+cBerth.getBerthNam());
			}
		}
		return result;
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CCable findone(String cableCod) {
		if (HdUtils.strIsNull(cableCod)) {
			CCable cCable = new CCable();
			return cCable;
		}
		return cCableService.findone(cableCod);
	}
	
	/**
     * 泊位查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findberth")
	@ResponseBody
	public CBerth findberth(String cableCod) {
		CCable cCable = JpaUtils.findById(CCable.class, cableCod);
		CBerth cBerth = null;
		if(cCable != null){
			cBerth = JpaUtils.findById(CBerth.class, cCable.getBerthCod());
		}
		return cBerth;
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCable> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cCableService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CCable cCable) {

		return cCableService.saveone(cCable);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCCable")
	@ResponseBody
	public HdMessageCode findCCarKind(String cableCod) {
		return cCableService.findCCable(cableCod);
	}
}
