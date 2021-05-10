package net.huadong.tech.base.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.service.CCyBayService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
/**
 * @author 
 */
@Controller
@RequestMapping("webresources/login/base/CCyBay")
public class CCyBayController  {
	
	@Autowired
	private CCyBayService cCyBayService; 
	
	//菜单进入
		@RequestMapping(value = "/ccybay.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/ccybay";
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
	    return cCyBayService.find(hdQuery);
	}
	
	/**
     * 通用列表查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findLocked")
	@ResponseBody
	public String findLocked(String cyAreaNo) {
	    return cCyBayService.findLocked(cyAreaNo);
	}


	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCyBay> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cCyBayService.save(gridData);
	}

	@RequestMapping("/add")
	@ResponseBody	
	public String  add(String cyPlac) {
		QueryParamLs paramLs = new QueryParamLs();
		String sql="update CCyBay a set a.lockId='1' where a.cyPlac=:cyPlac";
		paramLs.addParam("cyPlac", cyPlac);
		JpaUtils.execUpdate(sql, paramLs);
		return "1";
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody	
	public String  delete(String cyPlac) {
		QueryParamLs paramLs = new QueryParamLs();
		String sql="update CCyBay a set a.lockId='0' where a.cyPlac=:cyPlac";
		paramLs.addParam("cyPlac", cyPlac);
		JpaUtils.execUpdate(sql, paramLs);
		return "1";
	}	
}
