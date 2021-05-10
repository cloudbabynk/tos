package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.service.CCountryService;
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
@RequestMapping("webresources/login/base/CCountry")
public class CCountryController  {
	
	@Autowired
	private CCountryService cCountryService; 
	
	//菜单进入
	@RequestMapping(value = "/ccountry.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/ccountry";
	}
	
	/*
	 * 获取泊位下拉列表
	 */	
	@RequestMapping(value = "/getCAreaDrop")
	@ResponseBody
	public List<EzDropBean> getDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a from CArea a where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CArea>  ls=JpaUtils.findAll(jpql, params);
			for(CArea cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getAreaCod());
				drop.setLabel(cc.getAreaNam());
				list.add(drop);
			}
			return list;
		}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String countryCods) {
		cCountryService.removeAll(countryCods);
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
	    return cCountryService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CCountry findone(String countryCod) {
		if (HdUtils.strIsNull(countryCod)) {
			CCountry cCountry = new CCountry();
			return cCountry;
		}
		return cCountryService.findone(countryCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCountry> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cCountryService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CCountry cCountry) {

		return cCountryService.saveone(cCountry);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCCountry")
	@ResponseBody
	public HdMessageCode findCCountry(String countryCod) {
		return cCountryService.findCCountry(countryCod);
	}
}
