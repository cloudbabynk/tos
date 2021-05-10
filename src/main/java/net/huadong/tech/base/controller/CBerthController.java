package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBerth;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.service.CBerthService;
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
@RequestMapping("webresources/login/base/CBerth")
public class CBerthController  {
	
	@Autowired
	private CBerthService cBerthService; 
	
	//菜单进入
	@RequestMapping(value = "/cberth.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cberth";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String berthCods) {
		HdMessageCode hdMessageCode= new HdMessageCode();
		try {
			cBerthService.removeAll(berthCods);
			return HdUtils.genMsg();
		}catch (Exception e)
		{
			hdMessageCode.setMessage(e.getMessage());
			hdMessageCode.setCode("-1");
			return  hdMessageCode;
		}
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
	    return cBerthService.find(hdQuery);
	}
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findGs")
	@ResponseBody
	public HdEzuiDatagridData findGs(@RequestBody HdQuery hdQuery) {
	    return cBerthService.findGs(hdQuery);
	}
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findShipstat")
	@ResponseBody
	public HdEzuiDatagridData findShipstat(@RequestBody HdQuery hdQuery) {
	    return cBerthService.findShipstat(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CBerth findone(String berthCod) {
		if (HdUtils.strIsNull(berthCod)) {
			CBerth cBerth = new CBerth();
			return cBerth;
		}
		return cBerthService.findone(berthCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CBerth> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cBerthService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CBerth cBerth) {

		return cBerthService.saveone(cBerth);
	}
	//所属码头下拉
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
	@RequestMapping(value = "/findCBerth")
	@ResponseBody
	public HdMessageCode findCBerth(String berthCod) {
		return cBerthService.findCBerth(berthCod);
	}
	
	//泊位代码下拉
		@RequestMapping(value = "/getCBerthDrop")
		@ResponseBody
		public List<EzDropBean> getCBerthDrop() {
				List<EzDropBean> list=new ArrayList<EzDropBean>();
				String jpql= " select a  from  CBerth a  where 1=1 ";
				QueryParamLs params=new QueryParamLs();
				List<CBerth>  ls=JpaUtils.findAll(jpql, params);
				for(CBerth cb:ls){
					EzDropBean  drop=new EzDropBean();
					drop.setValue(cb.getBerthCod());
					drop.setLabel(cb.getBerthNam());
					list.add(drop);
				}
				return list;
			}
}
