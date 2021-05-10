package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CGateService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
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
@RequestMapping("webresources/login/base/CGate")
public class CGateController  {
	
	@Autowired
	private CGateService cGateService; 
	
	//菜单进入
		@RequestMapping(value = "/cgate.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/cgate";
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
	    return cGateService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CGate> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cGateService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String gateNos) {
		cGateService.removeAll(gateNos);
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
	public CGate findone(String gateNo) {
		if (HdUtils.strIsNull(gateNo)) {
			CGate cGate = new CGate();
			return cGate;
		}
		return cGateService.findone(gateNo);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CGate cGate) {

		return cGateService.saveone(cGate);
	}
	/**
	 * 班组下拉
	 */
	@RequestMapping(value = "/getCGateDrop")
	@ResponseBody
	public List<EzDropBean> getCGateDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CDock a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CDock>  ls=JpaUtils.findAll(jpql, params);
			for(CDock cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getDockCod());
				drop.setLabel(cc.getDockNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCGate")
	@ResponseBody
	public HdMessageCode findCGate(String gateNo) {
		return cGateService.findCGate(gateNo);
	}
}
