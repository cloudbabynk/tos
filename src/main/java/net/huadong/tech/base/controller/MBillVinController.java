package net.huadong.tech.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.Interface.entity.CargoDataSpecification;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.MBillVin;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.service.MBillVinService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.controller.PrivilegeController;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.ship.entity.MNobillWorkCommand;
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
@RequestMapping("webresources/login/base/MBillVin")
public class MBillVinController {

	@Autowired
	private MBillVinService mBillVinService;

	// 菜单进入
	@RequestMapping(value = "/mbillvin.htm")
	public String pageTh(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/mbillvin";
	}


	@RequestMapping(value = "/forcetally.htm")
	public String pageTh2(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/base/forcetally";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String billVinIds) {
		HdMessageCode hdMessageCode= new HdMessageCode();
		try {
			mBillVinService.removeAll(billVinIds);
			return HdUtils.genMsg();
		}catch (Exception e){
			hdMessageCode.setMessage(e.getMessage());
			hdMessageCode.setMessage("-1");
			return  hdMessageCode;
		}
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/importAll")
	@ResponseBody
	public HdMessageCode importAll(String shipNo) {
		mBillVinService.importAll(shipNo);
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
		return mBillVinService.find(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public MBillVin findone(String billVinId) {
		if (HdUtils.strIsNull(billVinId)) {
			MBillVin cCarKind = new MBillVin();
			return cCarKind;
		}
		return mBillVinService.findone(billVinId);
	}

	/**
	 * 保存资源信息
	 *
	 * @param hdEzuiSaveDatagridData
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MBillVin> gridData) {
		// CommonUtil.initEntity(gridData);
		return mBillVinService.save(gridData);
	}

//	/*
//	 * form保存
//	 */
//	@RequestMapping("/saveone")
//	@ResponseBody
//	public HdMessageCode saveone(@RequestBody MBillVin mBillVin) {
//		return mBillVinService.saveone(mBillVin);
//	}

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
	
    /**
 *   无提单理货
 */
@RequestMapping(value = "/noBillNo",method = RequestMethod.POST)
@ResponseBody
public HdEzuiDatagridData noBillNo(@RequestBody HdQuery hdQuery) {
	HdEzuiDatagridData data = new HdEzuiDatagridData();
	QueryParamLs paramLs = new QueryParamLs();
	String shipNo = hdQuery.getStr("shipNo");
	String VinNo = hdQuery.getStr("VinNo");
	String confirmId = hdQuery.getStr("confirmId");
	String sql = " select distinct t0.vin_no,t0.QUEUE_ID, "
			+ " t1.c_ship_nam||'-'||t1.ivoyage as nam_no, "
			+ " t0.confirm_id  "
			+ " from M_NOBILL_WORK_COMMAND t0, ship t1 "
			+ " where t1.ship_no = t0.ship_no and t0.ship_no =?1 ";
	paramLs.addParam(1, shipNo);
	if (HdUtils.strNotNull(VinNo)) {
		sql += " AND t0.vin_no like ?2 ";
		paramLs.addParam(2, '%'+VinNo+'%');
	}
	if (HdUtils.strNotNull(confirmId)) {
		sql += " AND nvl(t0.confirm_id, '0') = ?3 ";
		paramLs.addParam(3,confirmId);
	}
	List<MNobillWorkCommand>  list=JpaUtils.findBySql(sql, paramLs, MNobillWorkCommand.class);
	List listRows = new ArrayList<>();
	if(hdQuery.getPage()!=null){
		int curPage=hdQuery.getPage();
    	int pageSize=hdQuery.getRows();
		int max = list.size();
		for (int i = (curPage-1)*pageSize ; i < (curPage*pageSize) ; i++) {
			if(i<max){
				listRows.add(list.get(i));
			}else{
				break;
			}
		}
	}
	if(hdQuery.getPage()!=null){
		  data.setRows(listRows);
	}else{
		data.setRows(list);
	}
	data.setTotal(list.size());
	return data;
}	

/*
* 无提单理货数据导入work_command
*/
@RequestMapping("/antiBill")
@ResponseBody
public HdMessageCode antiBill( String shipNo) {
	return mBillVinService.antiBill(shipNo);
}
	
}
