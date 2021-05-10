package net.huadong.tech.work.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzDropBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.WorkCommand;

/**
 * @author 
 */
public interface WorkCommandService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdEzuiDatagridData findCyAreaUpdate(HdQuery hdQuery);
	HdEzuiDatagridData findDclh(HdQuery hdQuery);
	HdEzuiDatagridData findFcd(HdQuery hdQuery);
	HdEzuiDatagridData findJgcs(HdQuery hdQuery,String Type);
	HdEzuiDatagridData findJgcsWm(HdQuery hdQuery);
	HdEzuiDatagridData findZxzy(HdQuery hdQuery);
	void saveZpzclh(@RequestBody CargoInfo cargoInfo, String type,String billNos);
	HdMessageCode save(HdEzuiSaveDatagridData<WorkCommand> gridData);
	HdMessageCode saveone(WorkCommand workCommand,String type);
	HdMessageCode updateCyArea(String cyArealist,String shipNo,String brandCod);
	HdMessageCode saveZclh(CargoInfo cargoInfo,String type,String portCarNos);
	HdMessageCode saveNmzclh(CargoInfo cargoInfo,String type);
	HdMessageCode saveZzlh(CargoInfo cargoInfo,String type);
	HdMessageCode saveXclh(CargoInfo cargoInfo);
	HdMessageCode saveNmXclh(CargoInfo cargoInfo);
	HdMessageCode updateLhData(CargoInfo data,String type);
	WorkCommand findOne(String queueId);
	List findWorking(String shipNo);
	void jkWanChuan(String shipNo);
	void ckWanChuan(String shipNo);
	void exportExcel(HdEzuiDatagridData data,HttpServletResponse response,HdQuery hdQuery);
	void exportZxzy(HdEzuiDatagridData data,HttpServletResponse response);
	Result chooseShipNo(HdEzuiQueryParams hdEzuiQueryParams);
	WorkCommand getShipNoInfo(String shipNo);
	HdEzuiDatagridData findShipingReport(HdQuery hdQuery);
	
	 List<EzDropBean> getBillNoByShipNo(String shipNo,String workTyp);
}
