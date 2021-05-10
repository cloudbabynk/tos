package net.huadong.tech.base.controller;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.CargoDataSpecification;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.BigBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.service.BigBrandService;
import net.huadong.tech.base.service.CCarKindService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/base/BigBrand")
public class BigBrandController {

	@Autowired
	private BigBrandService bigBrandService;

	// 菜单进入
	@RequestMapping(value = "/bigbrand.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/bigbrand";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String bigBrandCod) {
		bigBrandService.removeAll(bigBrandCod);
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
		return bigBrandService.find(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public BigBrand findone(String bigBrandCod) {
		return bigBrandService.findone(bigBrandCod);
	}

	/**
	 * 保存资源信息
	 *
	 * @param gridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<BigBrand> gridData) {
		// CommonUtil.initEntity(gridData);
		return bigBrandService.save(gridData);
	}


	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody BigBrand bigBrand) {

		return bigBrandService.saveone(bigBrand);
	}

	/**
	 * 新增验证 防止主键冲突
	 */
	@RequestMapping(value = "/findCCarKind")
	@ResponseBody
	public HdMessageCode findCCarKind(String carKind) {
		return bigBrandService.findCCarKind(carKind);
	}

	/**
	 * 集团货类下拉
	 */
	@RequestMapping(value = "/getGroupCarKind")
	@ResponseBody
	public List<EzDropBean> getGroupCarKind(String q) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CargoDataSpecification a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(q)) {
			jpql += "and a.xFourthName like :xFourthName";
			params.addParam("xFourthName", "%" + q + "%");
		}
		List<CargoDataSpecification> ls = JpaUtils.findAll(jpql, params);
		for (CargoDataSpecification cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getxMdCargoid());
			drop.setLabel(cc.getxFourthName());
			list.add(drop);
		}
		return list;
	}
}
