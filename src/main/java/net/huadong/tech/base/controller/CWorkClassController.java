package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.service.CWorkClassService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.CShipData;
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
@RequestMapping("webresources/login/base/CWorkClass")
public class CWorkClassController  {
	
	@Autowired
	private CWorkClassService cWorkClassService; 
	
	//菜单进入
	@RequestMapping(value = "/cworkclass.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cworkclass";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String classCods) {
		cWorkClassService.removeAll(classCods);
		return HdUtils.genMsg();
	}

	
	/**
	 * 理货班组下拉
	 */
	@RequestMapping(value = "/getCWorkClass")
	@ResponseBody
	public List<EzDropBean> getCWorkClass() {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		QueryParamLs params = new QueryParamLs();
		List<CWorkClass> ls = null;
		String jpql = " select a  from  CWorkClass a  where 1=1 ";
		ls = JpaUtils.findAll(jpql, params);
		for (CWorkClass cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getClassCod());
			drop.setLabel(cc.getClassNam());
			list.add(drop);
		}
		return list;
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
	    return cWorkClassService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CWorkClass findone(String classCod) {
		if (HdUtils.strIsNull(classCod)) {
			CWorkClass cWorkClass = new CWorkClass();
			return cWorkClass;
		}
		return cWorkClassService.findone(classCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CWorkClass> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cWorkClassService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CWorkClass cWorkClass) {

		return cWorkClassService.saveone(cWorkClass);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCWorkClass")
	@ResponseBody
	public HdMessageCode findCWorkClass(String classCod) {
		return cWorkClassService.findCWorkClass(classCod);
	}
}
