package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCountry;
import net.huadong.tech.base.entity.CFactory;
import net.huadong.tech.base.service.CBrandService;
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
@RequestMapping("webresources/login/base/CBrand")
public class CBrandController  {
	
	@Autowired
	private CBrandService cBrandService; 
	
	//菜单进入
	@RequestMapping(value = "/cbrand.htm")
	public String  pageTh(Model model){
		Random random = new Random();
		model.addAttribute("radi", random.nextInt()*1000);
		return "system/base/cbrand";
	}
	/**
	 * 生产厂家下拉
	 */
	@RequestMapping(value = "/getCFactoryDrop")
	@ResponseBody
	public List<EzDropBean> getCFactoryDrop(String q) {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CFactory a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			if (HdUtils.strNotNull(q)){
				jpql += "and a.factoryNam like :factoryNam";
				params.addParam("factoryNam", "%" + q + "%");
			}
			List<CFactory>  ls=JpaUtils.findAll(jpql, params);
			for(CFactory cc:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(cc.getFactoryCod());
				drop.setLabel(cc.getFactoryNam());
				list.add(drop);
			}
			return list;
		}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String brandCods) {
		HdMessageCode hdMessageCode =new HdMessageCode();
try {
	cBrandService.removeAll(brandCods);
	return HdUtils.genMsg();
}catch (Exception e){
	hdMessageCode.setCode("-1");
	hdMessageCode.setMessage(e.getMessage());
	return  hdMessageCode;
}
	}
	
	/**
	 * 批量审核
	 */
	@RequestMapping(value = "/checkAll")
	@ResponseBody
	public HdMessageCode checkAll(String brandCods) {
		cBrandService.checkAll(brandCods);
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
	    return cBrandService.find(hdQuery);
	}
	/**
     * 实体查询
     *
     * @param params
     * @return
     */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CBrand findone(String brandCod) {
		if (HdUtils.strIsNull(brandCod)) {
			CBrand cBrand = new CBrand();
			cBrand.setCheckFlag("0");
			return cBrand;
		}
		return cBrandService.findone(brandCod);
	}
	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CBrand> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return cBrandService.save(gridData);
	}
	
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CBrand cBrand) {

		return cBrandService.saveone(cBrand);
	}
	
	
	/*
	 * form保存
	 */
	@RequestMapping("/generate")
	@ResponseBody
	public HdMessageCode generate(String shipworkTim) {

		return cBrandService.generate(shipworkTim);
	}

/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCBrand")
	@ResponseBody
	public HdMessageCode findCBrand(String brandEname) {
		return cBrandService.findCBrand(brandEname);
	}
}
