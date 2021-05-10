package net.huadong.tech.cargo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author 
 */
public interface PortCarService {
	HdEzuiDatagridData find(HdQuery hdQuery);
	//可以显示离港车辆的查询方法
	HdEzuiDatagridData findAll(HdQuery hdQuery);
	HdEzuiDatagridData findNull(HdQuery hdQuery);
	HdMessageCode updateInfo(String vimNo,String isAll);
	HdEzuiDatagridData findVinExcel(HdQuery hdQuery);
	HdMessageCode save(HdEzuiSaveDatagridData<PortCar> gridData,String ingateId,String gateNo);
	void removeAll(String ids);
	void changeAll(String portCarNos);
	void confrimJq(String portCarNo,String type);
	void confrimAuto(String shipNo);
	PortCar findone(String portCarNo);
	String findDchz(String cyAreaNo);
	HashMap<String,String> findDchzcl(String cyAreaNo);
	HdMessageCode saveone(PortCar portCar);
	HdEzuiDatagridData findZclh(HdQuery hdQuery);
	HdEzuiDatagridData findZC(HdQuery hdQuery);
	Map<String,Object> findZCGroup();
	HdEzuiDatagridData findHwcrk(HdQuery hdQuery);
	HashMap<String,String> getDetail(String date,String dockCod,String tradeId);
	HashMap<String,String> getJsg(String date);
	HashMap<String, String> getDuiChangDetail(String date);
	HdEzuiDatagridData findZCPL(HdQuery hdQuery);
	void statisticCount(String inoutcytim);
	String sendHwcrkDatatoJT(String shipNo, String iEId);
	HashMap<String, Long> getFtDcqk(String date);
	HashMap<String, Long> getFtJsg(String date);
	HdMessageCode statisticCountSave(String shipNo, String iEId);
	HdMessageCode statisticCountJs(String inoutcytim, String portTyp);
	String sendStatisticCountJs();
	//同步发车图位置
	HdMessageCode syncPortCatPic(@RequestBody Map map);
}
