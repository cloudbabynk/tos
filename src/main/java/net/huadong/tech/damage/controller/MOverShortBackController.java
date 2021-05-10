package net.huadong.tech.damage.controller;

import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MOverShortBack;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.damage.service.MOverShortBackService;
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
@RequestMapping("webresources/login/damage/MOverShortBack")
public class MOverShortBackController  {
	
	@Autowired
	private MOverShortBackService mOverShortBackService; 
	
	//菜单进入
		@RequestMapping(value = "/movershortback.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/damage/movershortback";
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
	    return mOverShortBackService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MOverShortBack> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return mOverShortBackService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String overshortIds) {
		mOverShortBackService.removeAll(overshortIds);
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
	public MOverShortBack findone(String overshortId) {
		if (HdUtils.strIsNull(overshortId)) {
			MOverShortBack mOverShortBack = new MOverShortBack();
			return mOverShortBack;
		}
		return mOverShortBackService.findone(overshortId);
	}
	
	@RequestMapping(value = "/findBillCar")
	@ResponseBody
	public HdEzuiDatagridData findPortCar(@RequestBody HdQuery hdQuery) {
	    return mOverShortBackService.findBillCar(hdQuery);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MOverShortBack mOverShortBack) {

		return mOverShortBackService.saveone(mOverShortBack);
	}
	//生成溢卸相关信息方法
	@RequestMapping("/genmovershort")
	@ResponseBody
	public HdMessageCode genmovershort(String shipNo,String tradeId,String billNo,String iEId,String vinNo,String missId) {
		return mOverShortBackService.genmovershort(shipNo,tradeId,billNo,iEId,vinNo,missId);
	}
}
