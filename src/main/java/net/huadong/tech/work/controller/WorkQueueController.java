package net.huadong.tech.work.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkQueue;
import net.huadong.tech.work.service.WorkQueueService;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/work/WorkQueue")
public class WorkQueueController {

	@Autowired
	private WorkQueueService workQueueService;

	// 菜单进入
	@RequestMapping(value = "/workqueue.htm")
	public String pageTh(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/workqueue";
	}

	// 菜单进入
	@RequestMapping(value = "/workmonitor.htm")
	public String pageWp(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/work/workmonitor";
	}

	// 批量理货菜单进入
	@RequestMapping(value = "/workqueuepl.htm")
	public String pagePL(String Type, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("Type", Type);
		return "system/work/workqueuepl";
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
		return workQueueService.find(hdQuery);
	}

	/**
	 * 外贸装船理货查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findZclh")
	@ResponseBody
	public HdEzuiDatagridData findZclh(@RequestBody HdQuery hdQuery) {
		return workQueueService.findZclh(hdQuery);
	}
	/**
	 * 内贸装船理货查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findZcnmlh")
	@ResponseBody
	public HdEzuiDatagridData findZcnmlh(@RequestBody HdQuery hdQuery) {
		return workQueueService.findZcnmlh(hdQuery);
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findPl")
	@ResponseBody
	public HdEzuiDatagridData findPl(@RequestBody HdQuery hdQuery) {
		return workQueueService.findPl(hdQuery);
	}

	/**
	 * 堆场作业监控查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findWorkPlan")
	@ResponseBody
	public HdEzuiDatagridData findWorkPlan(@RequestBody HdQuery hdQuery) {
		return workQueueService.findWorkPlan(hdQuery);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<WorkQueue> gridData) {
		// CommonUtil.initEntity(gridData);
		return workQueueService.save(gridData);
	}

	/**
	 * 生成卸船作业队列
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping("/generatewq")
	@ResponseBody
	public HdMessageCode generatewq(@RequestBody WorkQueue workQueue) {
		workQueueService.generatewq(workQueue);
		return HdUtils.genMsg();
	}

	/**
	 * 生成装船作业队列
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/generatewq2")
	@ResponseBody
	public HdMessageCode generatewq2(@RequestBody WorkQueue workQueue) {
		workQueueService.generatewq2(workQueue);
		return HdUtils.genMsg();
	}

	/**
	 * 生成转栈作业队列
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/generatewq3")
	@ResponseBody
	public HdMessageCode generatewq3(@RequestBody WorkQueue workQueue) {
		workQueueService.generatewq3(workQueue);
		return HdUtils.genMsg();
	}
	@RequestMapping("/findWorkCharts")
	@ResponseBody
	public HdMessageCode findWorkCharts(@RequestBody Map mapPam) {	
		return workQueueService.findWorkCharts(mapPam);
	}
	@RequestMapping("/findWorkCharts1")
	@ResponseBody
	public HdMessageCode findWorkCharts1(@RequestBody Map mapPam) {	
		return workQueueService.findWorkCharts1(mapPam);
	}
			
}
