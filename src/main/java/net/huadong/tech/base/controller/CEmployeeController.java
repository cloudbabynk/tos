package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CEmpTyp;
import net.huadong.tech.base.entity.CEmployee;
import net.huadong.tech.base.entity.CWorkClass;
import net.huadong.tech.base.service.CEmployeeService;
import net.huadong.tech.com.entity.AuthUserExtra;
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
@RequestMapping("webresources/login/base/CEmployee")
public class CEmployeeController  {
	
	@Autowired
	private CEmployeeService cEmployeeService; 
	
	//菜单进入
	@RequestMapping(value = "/cemployee.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cemployee";
	}
	
	/**
	 * 工种下拉
	 */
	@RequestMapping(value = "/getDrop")
	@ResponseBody
	public List<EzDropBean> getDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CEmpTyp a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CEmpTyp>  ls=JpaUtils.findAll(jpql, params);
			for(CEmpTyp cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getEmpTypCod());
				drop.setLabel(cc.getEmpTypNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 用户下拉
	 */
	@RequestMapping(value = "/getAuthUserDrop")
	@ResponseBody
	public List<EzDropBean> getAuthUserDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  AuthUserExtra a where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<AuthUserExtra>  ls=JpaUtils.findAll(jpql, params);
			for(AuthUserExtra cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getUserId());
				drop.setLabel(cc.getName());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 班组下拉
	 */
	@RequestMapping(value = "/getCEmployee")
	@ResponseBody
	public List<EzDropBean> getCEmployee(String classNo) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CEmployee a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			if(HdUtils.strNotNull(classNo)){
				jpql += "and a.classNo =:classNo";
				params.addParam("classNo", classNo);
			}
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
	 * 理货员下拉
	 */
	@RequestMapping(value = "/getDrop1")
	@ResponseBody
	public List<EzDropBean> getDrop1() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CWorkClass a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CWorkClass>  ls=JpaUtils.findAll(jpql, params);
			for(CWorkClass cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getClassCod());
				drop.setLabel(cc.getClassNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String empNos) {
		cEmployeeService.removeAll(empNos);
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
	    return cEmployeeService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CEmployee findone(String empNo) {
		if (HdUtils.strIsNull(empNo)) {
			CEmployee cEmployee = new CEmployee();
			return cEmployee;
		}
		return cEmployeeService.findone(empNo);
	}
	
	
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findCemployee")
	@ResponseBody
	public CEmployee findCemployee(String sysUserNam) {
		return cEmployeeService.findCemployee(sysUserNam);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CEmployee> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cEmployeeService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CEmployee cEmployee) {

		return cEmployeeService.saveone(cEmployee);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCEmployee")
	@ResponseBody
	public HdMessageCode findCEmployee(String empNo) {
		return cEmployeeService.findCEmployee(empNo);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findSj")
	@ResponseBody
	public HdEzuiDatagridData findSj(@RequestBody HdQuery hdQuery) {
		return cEmployeeService.findSj(hdQuery);
	}
}
