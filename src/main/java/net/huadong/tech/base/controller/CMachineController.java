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
import net.huadong.tech.base.entity.CMachTyp;
import net.huadong.tech.base.entity.CMachine;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.service.CMachineService;
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
@RequestMapping("webresources/login/base/CMachine")
public class CMachineController  {
	
	@Autowired
	private CMachineService cMachineService; 
	
	//菜单进入
	@RequestMapping(value = "/cmachine.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cmachine";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String machNos) {
		cMachineService.removeAll(machNos);
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
	    return cMachineService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CMachine findone(String machNo) {
		if (HdUtils.strIsNull(machNo)) {
			CMachine cMachine = new CMachine();
			return cMachine;
		}
		return cMachineService.findone(machNo);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CMachine> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cMachineService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CMachine cMachine) {

		return cMachineService.saveone(cMachine);
	}
	
	/**
	 * 机械类型下拉
	 */
	@RequestMapping(value = "/getCMachTypDrop")
	@ResponseBody
	public List<EzDropBean> getCMachTypDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CMachTyp a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CMachTyp>  ls=JpaUtils.findAll(jpql, params);
			for(CMachTyp cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getMachTypCod());
				drop.setLabel(cc.getMachTyp());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 所属码头下拉
	 */
	@RequestMapping(value = "/getCDockDrop")
	@ResponseBody
	public List<EzDropBean> getCDockDrop() {
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
	@RequestMapping(value = "/findCMachine")
	@ResponseBody
	public HdMessageCode findCMachine(String machNo) {
		return cMachineService.findCMachine(machNo);
	}
}
