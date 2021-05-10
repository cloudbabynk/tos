package net.huadong.tech.base.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CArea;
import net.huadong.tech.base.entity.CCyArea;
import net.huadong.tech.base.entity.CCyBay;
import net.huadong.tech.base.entity.CCyRow;
import net.huadong.tech.base.service.CCyAreaService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/base/CCyArea")
public class CCyAreaController {

	@Autowired
	private CCyAreaService cCyAreaService;

	// 菜单进入
	@RequestMapping(value = "/ccyarea.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/ccyarea";
	}

	// 菜单进入
	@RequestMapping(value = "/ccyareacdxx.htm")
	public String pageCdxx(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/ccyareacdxx";
	}

	// 菜单进入
	@RequestMapping(value = "/dcclxxhz.htm")
	public String pageDccl(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/dcclxxhz";
	}
	
	// 菜单进入
		@RequestMapping(value = "/dcclxxhzcx.htm")
		public String pageDcclcx(Model model,String cyAreaNo) {
			Random random = new Random();
			model.addAttribute("radi", random.nextInt() * 1000);
			model.addAttribute("cyAreaNo", cyAreaNo);
			return "system/base/dcclxxhzcx";
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
		return cCyAreaService.find(hdQuery);
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findCdxx")
	@ResponseBody
	public HdEzuiDatagridData findCdxx(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findCdxx(hdQuery);
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDccl")
	@ResponseBody
	public HdEzuiDatagridData findDccl(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findDccl(hdQuery);
	}
	
	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findCzzc")
	@ResponseBody
	public HdEzuiDatagridData findCzzc(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findCzzc(hdQuery);
	}

	/**
	 * 堆场车辆信息汇总查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findDcclhz")
	@ResponseBody
	public HdEzuiDatagridData findDcclhz(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findDcclhz(hdQuery);
	}
	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findSglh")
	@ResponseBody
	public HdEzuiDatagridData findSglh(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findSglh(hdQuery);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<CCyArea> gridData) {
		// CommonUtil.initEntity(gridData);
		return cCyAreaService.save(gridData);
	}

	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody CCyArea cyArea) {
		return cCyAreaService.saveone(cyArea);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public HdMessageCode create() {
		String jpql = "DELETE FROM CCyRow";
		QueryParamLs paramLs = new QueryParamLs();
		JpaUtils.execUpdate(jpql, paramLs);
		String jpql1 = "DELETE FROM CCyBay";
		JpaUtils.execUpdate(jpql1, paramLs);
		String sql1 = "DELETE FROM C_CY_BAY";
		JpaUtils.getEntityManager().createNativeQuery(sql1);
		String jpql2 = "select a from CCyArea a where a.rowNum !='0'";
		List<CCyArea> cCyAreaList = JpaUtils.findAll(jpql2, paramLs);
		int rowNum;
		int bayNum;
		for(CCyArea cCyArea :cCyAreaList){
			rowNum = Integer.valueOf(cCyArea.getRowNum().toString());
			bayNum = Integer.valueOf(cCyArea.getBayNum().toString());
			for(int i=1;i<=rowNum;i++){
				CCyRow cCyRow = new CCyRow();
				cCyRow.setCyAreaNo(cCyArea.getCyAreaNo());
				cCyRow.setCyRowNo( String.format("%02d",i));
				cCyRow.setMaxBayNum(new BigDecimal(bayNum));
				JpaUtils.save(cCyRow);
				for(int j=1;j<=bayNum;j++){
					CCyBay bean = new CCyBay();
					bean.setCyAreaNo(cCyArea.getCyAreaNo());
					bean.setCyRowNo(String.format("%02d",i));
					bean.setCyBayNo(String.format("%02d",j));
					String cyPlac = bean.getCyAreaNo() + String.format("%02d", i) + String.format("%02d", j);
					bean.setCyPlac(cyPlac);
					bean.setLockId("0");
					JpaUtils.save(bean);
				}
			}
		}
		return HdUtils.genMsg();
	}

	/**
	 * 场地策划选场地
	 * 
	 * @param hdQuery
	 * @return
	 */
	@RequestMapping(value = "/findcdch")
	@ResponseBody
	public HdEzuiDatagridData findcdch(@RequestBody HdQuery hdQuery) {
		return cCyAreaService.findcdch(hdQuery);
	}

	/**
	 * 卸船批量理货 入场位置下拉
	 */
	@RequestMapping(value = "/getCCyAreaDrop")
	@ResponseBody
	public List<EzDropBean> getCCyAreaDrop(String shipNo, String dockCod) {
		List<EzDropBean> list = new ArrayList<EzDropBean>();
		String jpql = " select a  from  CCyArea a  where 1=1 ";
		QueryParamLs params = new QueryParamLs();
		if (HdUtils.strNotNull(dockCod)) {
			jpql += "and a.dockCod =:dockCod ";
			params.addParam("dockCod", dockCod);
		} else {
			if (HdUtils.strNotNull(shipNo)) {
				Ship ship = JpaUtils.findById(Ship.class, shipNo);
				if (ship != null) {
					if (HdUtils.strNotNull(ship.getDockCod())) {
						jpql += "and a.dockCod =:dockCod ";
						params.addParam("dockCod", ship.getDockCod());
					}
				}
			}
		}
		jpql+=" order by a.cyAreaNam";
		List<CCyArea> ls = JpaUtils.findAll(jpql, params);
		for (CCyArea cc : ls) {
			EzDropBean drop = new EzDropBean();
			drop.setValue(cc.getCyAreaNo());
			drop.setLabel(cc.getCyAreaNam());
			list.add(drop);
		}
		return list;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String cyAreaNos) {
		cCyAreaService.removeAll(cyAreaNos);
		return HdUtils.genMsg();
	}
	
	@RequestMapping(value = "/findone")
	@ResponseBody
	public CCyArea findone(String cyAreaNo) {
		return cCyAreaService.findone(cyAreaNo);
	}
	
	@RequestMapping(value = "/findCCyArea")
	@ResponseBody
	public CCyArea findCCyArea(String cyAreaNo) {
		if (HdUtils.strIsNull(cyAreaNo)){
			CCyArea bean = new CCyArea();
			bean.setCyTyp("1");
			return bean;
		}
		return cCyAreaService.findCCyArea(cyAreaNo);
	}
	
	@RequestMapping(value = "/sendDataJT")
	@ResponseBody
	public HdMessageCode sendDataJT() {
	    cCyAreaService.sendDataJT();
		return HdUtils.genMsg();
	}
	//String findAreaNam(String cyAreaNo);
	@RequestMapping(value = "/findAreaNam")
	@ResponseBody
	public String findAreaNam(String cyAreaNo) {
		return cCyAreaService.findAreaNam(cyAreaNo);
	}	
	
}
