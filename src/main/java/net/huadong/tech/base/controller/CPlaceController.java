package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CPlace;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.service.CPlaceService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
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
@RequestMapping("webresources/login/base/CPlace")
public class CPlaceController  {
	
	@Autowired
	private CPlaceService cPlaceService; 
	
	//菜单进入
		@RequestMapping(value = "/cplace.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/base/cplace";
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
	    return cPlaceService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CPlace> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cPlaceService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String placCods) {
		cPlaceService.removeAll(placCods);
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
	public CPlace findone(String placCod) {
		if (HdUtils.strIsNull(placCod)) {
			CPlace cPlace = new CPlace();
			return cPlace;
		}
		return cPlaceService.findone(placCod);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CPlace cPlace) {

		return cPlaceService.saveone(cPlace);
	}
	/**
	 * 区域下拉
	 */
	@RequestMapping(value = "/getCAreaDrop")
	@ResponseBody
	public List<EzDropBean> getCAreaDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CArea a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CArea>  ls=JpaUtils.findAll(jpql, params);
			for(CArea cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getAreaCod());
				drop.setLabel(cc.getAreaNam());
				list.add(drop);
			}
			return list;
		}
	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCPlace")
	@ResponseBody
	public HdMessageCode findCPlace(String placCod) {
		return cPlaceService.findCPlace(placCod);
	}
}
