package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CBizcar;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CBizcar;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CBizcarService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.helper.HdEzuiExportFile;
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
@RequestMapping("webresources/login/base/CBizcar")
public class CBizcarController {

	@Autowired
	private CBizcarService cBizcarService;

	// 菜单进入
	@RequestMapping(value = "/cbizcar.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/cbizcar";
	}

	@RequestMapping(value = "/portcarvinexcel.htm")
	public String pageTh2(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/portcarvinexcel";
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
		return cBizcarService.find(hdQuery);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CBizcar> gridData) {
		// CommonUtil.initEntity(gridData);
		return cBizcarService.save(gridData);
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String bizcarNos) {
		cBizcarService.removeAll(bizcarNos);
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
	public CBizcar findone(String bizcarNo) {
		if (HdUtils.strIsNull(bizcarNo)) {
			CBizcar cBizcar = new CBizcar();
			return cBizcar;
		}
		return cBizcarService.findone(bizcarNo);
	}

	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CBizcar cBizcar) {

		return cBizcarService.saveone(cBizcar);
	}

	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCBizcar")
	@ResponseBody
	public HdMessageCode findCBizcar(String bizcarNo) {
		return cBizcarService.findCBizcar(bizcarNo);
	}

	@RequestMapping(value = "/exportCbiz", method = RequestMethod.POST)
	@ResponseBody
	public void exportCbiz(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);
		HdEzuiDatagridData data = this.find(params);
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}

	


}
