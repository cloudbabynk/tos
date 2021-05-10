package net.huadong.tech.work.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.thirdparty.service.Result;
import net.huadong.tech.work.entity.CargoInfo;
import net.huadong.tech.work.entity.MPdaWorkCommand;

/**
 * @author 
 */
public interface MPdaWorkCommandService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<MPdaWorkCommand> gridData);
	HdMessageCode saveone(MPdaWorkCommand workCommand,String type);
	HdMessageCode saveZclh(CargoInfo cargoInfo,String type,String portCarNos);
	HdMessageCode saveNmzclh(CargoInfo cargoInfo,String type);
	HdMessageCode saveZzlh(CargoInfo cargoInfo,String type);
	HdMessageCode saveLxlh(String shipNo,String type);
	HdMessageCode saveXclh(CargoInfo cargoInfo);
	HdMessageCode saveNmXclh(CargoInfo cargoInfo);
	MPdaWorkCommand findOne(String queueId);
	void jkWanChuan(String shipNo);
	void ckWanChuan(String shipNo);
	Result chooseShipNo(HdEzuiQueryParams hdEzuiQueryParams);
	MPdaWorkCommand getShipNoInfo(String shipNo);
	HdEzuiDatagridData findShipingReport(HdQuery hdQuery);
	void removeAll(String queueIds);
}
