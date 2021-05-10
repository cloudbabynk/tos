package net.huadong.tech.damage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.damage.entity.MDeliveryRecord;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.damage.service.MDeliveryRecordService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.shipbill.entity.ShipBill;
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
@RequestMapping("webresources/login/damage/MDeliveryRecord")
public class MDeliveryRecordController  {
	
	@Autowired
	private MDeliveryRecordService mDeliveryRecordService; 
	
	//菜单进入
		@RequestMapping(value = "/mdeliveryrecord.htm")
		public String  pageTh(Model model){
			Random random = new Random();
			model.addAttribute("radi", random.nextInt()*1000);
			return "system/damage/mdeliveryrecord";
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
	    return mDeliveryRecordService.find(hdQuery);
	}

	/**
     * 保存资源信息
     *
     * @param hdEzuiSaveDatagridData
     * @return
     */
	@RequestMapping("/save")
	@ResponseBody	
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<MDeliveryRecord> gridData) {
	 	 //CommonUtil.initEntity(gridData);
		return mDeliveryRecordService.save(gridData);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String deliveryids) {
		mDeliveryRecordService.removeAll(deliveryids);
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
	public MDeliveryRecord findone(String deliveryid) {
		if (HdUtils.strIsNull(deliveryid)) {
			MDeliveryRecord mDeliveryRecord = new MDeliveryRecord();
			return mDeliveryRecord;
		}
		return mDeliveryRecordService.findone(deliveryid);
	}
	/*
	 * form保存
	 */
	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody MDeliveryRecord mDeliveryRecord) {

		return mDeliveryRecordService.saveone(mDeliveryRecord);
	}
	
	@RequestMapping(value = "/getClassRunDrop")
	@ResponseBody
	public List<EzDropBean> getClassRunDrop() {
			List<EzDropBean> list=new ArrayList<EzDropBean>();
			String jpql= " select a  from  CWorkRun a  where 1=1 ";
			QueryParamLs params=new QueryParamLs();
			List<CWorkRun>  ls=JpaUtils.findAll(jpql, params);
			for(CWorkRun sb:ls){
				EzDropBean  drop=new EzDropBean();
				drop.setValue(sb.getWorkRun());
				drop.setLabel(sb.getWorkRunNam());
				list.add(drop);
			}
			return list;
		}
}
