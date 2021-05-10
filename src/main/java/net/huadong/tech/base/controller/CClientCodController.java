package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.service.CClientCodService;
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
@RequestMapping("webresources/login/base/CClientCod")
public class CClientCodController  {
	
	@Autowired
	private CClientCodService cClientCodService; 
	
	//菜单进入
	@RequestMapping(value = "/cclientcod.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cclientcod";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String clientCods) {
		cClientCodService.removeAll(clientCods);
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
	    return cClientCodService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CClientCod findone(String clientCod) {
		if (HdUtils.strIsNull(clientCod)) {
			CClientCod cClientCod = new CClientCod();
			return cClientCod;
		}
		return cClientCodService.findone(clientCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CClientCod> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cClientCodService.save(gridData);
	}
	
	/**
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CClientCod cClientCod) {

		return cClientCodService.saveone(cClientCod);
	}
	
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCClientCod")
	@ResponseBody
	public HdMessageCode findCClientCod(String clientCod) {
		return cClientCodService.findCClientCod(clientCod);
	}
	/**
	 * 缴费单位、货主、代理方下拉
	 */
	@RequestMapping(value = "/getCClientCodDrop")
	@ResponseBody
	public List<EzDropBean> getCClientCodDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CClientCod a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CClientCod>  ls=JpaUtils.findAll(jpql, params);
			for(CClientCod cp:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cp.getClientCod());
				drop.setLabel(cp.getcClientNam());
				list.add(drop);
			}
			return list;
		}
}
