package net.huadong.tech.wechat.serviceimpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CWorkRun;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.FindCarInfoService;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;


@Component
public class FindCarInfoServiceImpl implements FindCarInfoService {

	@Override
	public List<PortCar> findByrfid(String rfid) {
		String pcJpql = "SELECT p FROM PortCar p where p.rfidCardNo =:rfid";
		QueryParamLs pcParams = new QueryParamLs();
		pcParams.addParam("rfid", rfid);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParams);
		return pcList;
	}

	@Override
	public List<PortCar> findByvinNo(String vinNo) {
		String pcJpql = "SELECT p FROM PortCar p where p.vinNo =:vinNo";
		QueryParamLs pcParams = new QueryParamLs();
		pcParams.addParam("vinNo", vinNo);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParams);
		return pcList;
	}

	@Override
	public Map<String, Object> findaAllData(String dateTime, String workRunNam) {
		String Account = HdUtils.getCurUser().getAccount();
		Map<String, Object> map = new HashMap<String, Object>();
		String cwrJpql = "SELECT c FROM CWorkRun c where c.nightId =:workRunNam";
		QueryParamLs cwrParams = new QueryParamLs();
		cwrParams.addParam("workRunNam", workRunNam);
		List<CWorkRun> cwrList = JpaUtils.findAll(cwrJpql, cwrParams);
		String begTim0 = cwrList.get(0).getBegTim();
		String endTim0 = cwrList.get(0).getEndTim();
		
		String begTim1 = dateTime + " " + begTim0.substring(0, 2) + ":" + begTim0.substring(2, 4) + ":00";
		String endTim1 = dateTime + " " + endTim0.substring(0, 2) + ":" + endTim0.substring(2, 4) + ":00";
		//开始时间
		Timestamp begTim = new Timestamp(System.currentTimeMillis());
        try {  
        	begTim = Timestamp.valueOf(begTim1);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
       
        Timestamp endTim11 = new Timestamp(System.currentTimeMillis());
        try {
        	endTim11 = Timestamp.valueOf(endTim1);  
        } catch (Exception e) {  
            e.printStackTrace();
        }
        //结束时间
        Timestamp endTim = endTim11;
        if(workRunNam.equals("1")) {
        	Date tDate = endTim;
        	Calendar c = Calendar.getInstance();  
            c.setTime(tDate);  
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date date = c.getTime();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sDate = sdf.format(date);
			endTim = Timestamp.valueOf(sDate);
		}
        
		//卸船数据
        int unloadCount = 0;
		String wcJpql = "SELECT w FROM WorkCommand w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'SI'";
		QueryParamLs wcParams = new QueryParamLs();
		wcParams.addParam(1, Account);
		wcParams.addParam(2, (Timestamp) begTim);
		wcParams.addParam(3, (Timestamp) endTim);
		List<WorkCommand> wcList = JpaUtils.findAll(wcJpql, wcParams);
		if(wcList.size() > 0) {
			unloadCount +=  wcList.size();
		}
		
		String wcbJpql = "SELECT w FROM WorkCommandBak w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'SI'";
		QueryParamLs wcbParams = new QueryParamLs();
		wcbParams.addParam(1, Account);
		wcbParams.addParam(2, (Timestamp) begTim);
		wcbParams.addParam(3, (Timestamp) endTim);
		List<WorkCommandBak> wcbList = JpaUtils.findAll(wcbJpql, wcbParams);
		if(wcbList.size() > 0) {
			unloadCount +=  wcbList.size();
		}
		map.put("unload", unloadCount);
		
		//装船数据
		int loadCount = 0;
		String wcloadJpql = "SELECT w FROM WorkCommand w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'SO'";
		QueryParamLs wcloadParams = new QueryParamLs();
		wcloadParams.addParam(1, Account);
		wcloadParams.addParam(2, (Timestamp) begTim);
		wcloadParams.addParam(3, (Timestamp) endTim);
		List<WorkCommand> wcloadList = JpaUtils.findAll(wcloadJpql, wcloadParams);
		if(wcloadList.size() > 0) {
			loadCount +=  wcloadList.size();
		}
		
		String wcbloadJpql = "SELECT w FROM WorkCommandBak w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'SO'";
		QueryParamLs wcbloadParams = new QueryParamLs();
		wcbloadParams.addParam(1, Account);
		wcbloadParams.addParam(2, (Timestamp) begTim);
		wcbloadParams.addParam(3, (Timestamp) endTim);
		List<WorkCommandBak> wcbloadList = JpaUtils.findAll(wcbloadJpql, wcbloadParams);
		if(wcbloadList.size() > 0) {
			loadCount +=  wcbloadList.size();
		}
		map.put("load", loadCount);
		
		//集港数据
		int inPortCount = 0;
		String wcinJpql = "SELECT w FROM WorkCommand w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'TI'";
		QueryParamLs wcinParams = new QueryParamLs();
		wcinParams.addParam(1, Account);
		wcinParams.addParam(2, (Timestamp) begTim);
		wcinParams.addParam(3, (Timestamp) endTim);
		List<WorkCommand> wcinList = JpaUtils.findAll(wcinJpql, wcinParams);
		if(wcinList.size() > 0) {
			inPortCount +=  wcinList.size();
		}
		
		String wcbinJpql = "SELECT w FROM WorkCommandBak w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'TI'";
		QueryParamLs wcbinParams = new QueryParamLs();
		wcbinParams.addParam(1, Account);
		wcbinParams.addParam(2, (Timestamp) begTim);
		wcbinParams.addParam(3, (Timestamp) endTim);
		List<WorkCommandBak> wcbinList = JpaUtils.findAll(wcbinJpql, wcbinParams);
		if(wcbinList.size() > 0) {
			inPortCount +=  wcbinList.size();
		}
		map.put("inPort", inPortCount);
		
		//疏港数据
		int outPortCount = 0;
		String wcoutJpql = "SELECT w FROM WorkCommand w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'TO'";
		QueryParamLs wcoutParams = new QueryParamLs();
		wcoutParams.addParam(1, Account);
		wcoutParams.addParam(2, (Timestamp) begTim);
		wcoutParams.addParam(3, (Timestamp) endTim);
		List<WorkCommand> wcoutList = JpaUtils.findAll(wcoutJpql, wcinParams);
		if(wcoutList.size() > 0) {
			outPortCount +=  wcoutList.size();
		}
		
		String wcboutJpql = "SELECT w FROM WorkCommandBak w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'TO'";
		QueryParamLs wcboutParams = new QueryParamLs();
		wcboutParams.addParam(1, Account);
		wcboutParams.addParam(2, (Timestamp) begTim);
		wcboutParams.addParam(3, (Timestamp) endTim);
		List<WorkCommandBak> wcboutList = JpaUtils.findAll(wcboutJpql, wcboutParams);
		if(wcboutList.size() > 0) {
			outPortCount +=  wcboutList.size();
		}
		map.put("outPort", outPortCount);
		
		//捣场数据
		int daoFieldCount = 0;
		String wcdcJpql = "SELECT w FROM WorkCommand w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'MV'";
		QueryParamLs wcdcParams = new QueryParamLs();
		wcdcParams.addParam(1, Account);
		wcdcParams.addParam(2, (Timestamp) begTim);
		wcdcParams.addParam(3, (Timestamp) endTim);
		List<WorkCommand> wcdctList = JpaUtils.findAll(wcdcJpql, wcdcParams);
		if(wcdctList.size() > 0) {
			daoFieldCount +=  wcdctList.size();
		}
		
		String wcbdcJpql = "SELECT w FROM WorkCommandBak w where w.inCyNam = ?1 and w.inCyTim > ?2 and w.inCyTim < ?3 and w.workTyp = 'MV'";
		QueryParamLs wcbdcParams = new QueryParamLs();
		wcbdcParams.addParam(1, Account);
		wcbdcParams.addParam(2, (Timestamp) begTim);
		wcbdcParams.addParam(3, (Timestamp) endTim);
		List<WorkCommandBak> wcbdcList = JpaUtils.findAll(wcbdcJpql, wcbdcParams);
		if(wcbdcList.size() > 0) {
			daoFieldCount +=  wcbdcList.size();
		}
		map.put("dc", daoFieldCount);
		System.out.println(map);
		return map;
	}

}
