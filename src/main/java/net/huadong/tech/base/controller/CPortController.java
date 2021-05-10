package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CPort;
import net.huadong.tech.base.service.CPortService;
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
@RequestMapping("webresources/login/base/CPort")
public class CPortController  {
	
	@Autowired
	private CPortService cPortService; 
	
	//菜单进入
	@RequestMapping(value = "/cport.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cport";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String portIds) {
		cPortService.removeAll(portIds);
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
	    return cPortService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CPort findone(String portId) {
		if (HdUtils.strIsNull(portId)) {
			CPort cPort = new CPort();
			return cPort;
		}
		return cPortService.findone(portId);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CPort> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cPortService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CPort cPort) {

		return cPortService.saveone(cPort);
	}
	/**
	 * 港口下拉
	 */
	@RequestMapping(value = "/getCPortDrop")
	@ResponseBody
	public List<EzDropBean> getCPortDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CPort a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CPort>  ls=JpaUtils.findAll(jpql, params);
			for(CPort cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getPortCod());
				drop.setLabel(cc.getcPortNam());
				list.add(drop);
			}
			return list;
		}
	
	/**
	 * 流向下拉
	 */
	@RequestMapping(value = "/getCPortFlowDrop")
	@ResponseBody
	public List<EzDropBean> getCPortFlowDrop(String q) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CPort a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			if (HdUtils.strNotNull(q)){
				jpql += "and a.cPortNam like :cPortNam";
				params.addParam("cPortNam", "%" + q + "%");
			}
			List<CPort>  ls=JpaUtils.findAll(jpql, params);
			for(CPort cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getPortCod());
				drop.setLabel(cc.getcPortNam());
				list.add(drop);
			}
			return list;
		}
	
}
