package net.huadong.tech.work.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.work.service.StatisticCountService;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/work/StatisticCount")
public class StatisticCountController {

	@Autowired
	private StatisticCountService statisticCountService;

	// 菜单进入
	@RequestMapping(value = "/classbill.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/classbill";
	}




	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findSc")
	@ResponseBody
	public HdEzuiDatagridData findSc(@RequestBody HdQuery hdQuery) {
		return statisticCountService.findSc(hdQuery);
	}
	
	/**
	 * 出入库中间表 
	 */
	@RequestMapping(value = "/findScJs")
	@ResponseBody
	public HdEzuiDatagridData findScJs(@RequestBody HdQuery hdQuery) {
		return statisticCountService.findScJs(hdQuery);
	}

}
