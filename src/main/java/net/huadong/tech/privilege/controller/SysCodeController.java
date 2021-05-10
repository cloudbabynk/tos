package net.huadong.tech.privilege.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.privilege.service.SysCodeService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author zhangdh 修改：qidd
 */

@Controller
@RequestMapping("webresources/login/privilege/SysCode")
public class SysCodeController {

	@Autowired
	private SysCodeService sysCodeService;

	@RequestMapping(value = "/syscode.htm")
	public String privilege(String userId) {
		return "system/privilege/syscode";
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
		return sysCodeService.ezuiFindSysCode(hdQuery);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode ezuiSave(@RequestBody HdEzuiSaveDatagridData<SysCode> hdEzuiSaveDatagridData) {
		return sysCodeService.ezuiSaveSysCode(hdEzuiSaveDatagridData);
	}

	/**
	 * 分类查找代码
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/findAll")
	@ResponseBody
	public List<SysCode> findAll(@QueryParam("fieldCod") String fieldCod) {
		List<SysCode> listSysCode = sysCodeService.findAll(fieldCod);
		return listSysCode;
	}

	// 列表用
	@RequestMapping(value = "/findsyscodeByfieldCod")
	@ResponseBody
	public HdEzuiDatagridData findsyscodeByfieldCod(@RequestBody HdQuery params) {
		return sysCodeService.findbyfieldCod(params, true);

	}

	// 通用列表下拉用
	@RequestMapping(value = "/findForGrid")
	@ResponseBody
	public HdEzuiDatagridData findForGrid(@RequestBody HdQuery params) {
		return sysCodeService.findbyfieldCod(params, false);

	}
	
	//未完成原因
	@RequestMapping(value = "/findUnReason")
	@ResponseBody
	public HdEzuiDatagridData findUnReason(@RequestBody HdQuery params) {
		return sysCodeService.findUnReason(params);

	}
	
	//未完成原因
	@RequestMapping(value = "/cPort")
	@ResponseBody
	public HdEzuiDatagridData cPort(@RequestBody HdQuery params) {
		return sysCodeService.cPort(params);

	}

}
