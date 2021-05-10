package net.huadong.tech.wechat.serviceimpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import net.huadong.tech.base.entity.CDamage;
import net.huadong.tech.base.entity.CDamgArea;
import net.huadong.tech.base.entity.CDamgLevel;
import net.huadong.tech.damage.entity.CarDamage;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.wechat.service.DamageRegistrationService;
import net.sf.json.JSONObject;

/**
 * 残损登记
 * 
 * @author hdwork
 *
 */
@Component
public class DamageRegistrationServiceImpl implements DamageRegistrationService {

	@Override
	public List<PortCar> rfidcheck(String rfid) {
		String pcJpql = "SELECT p FROM PortCar p where p.rfidCardNo =:rfid";
		QueryParamLs pcParams = new QueryParamLs();
		pcParams.addParam("rfid", rfid);
		List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParams);
		return pcList;
	}

	@Override
	public StringBuffer cardamagels(String portCarNo) {
		StringBuffer result = new StringBuffer();
		System.out.println(portCarNo);
		QueryParamLs paramLs = new QueryParamLs();
		String jpql = "select a from CarDamage a where a.portCarNo =:portCarNo";
		BigDecimal bd = new BigDecimal(portCarNo);
		paramLs.addParam("portCarNo", bd);
		List<CarDamage> cdList = JpaUtils.findAll(jpql, paramLs);
		if (cdList.size() > 0) {
			String cdJpql = "SELECT c FROM CDamage c where c.damCod =:damCod";
			QueryParamLs cdParams = new QueryParamLs();
			cdParams.addParam("damCod", cdList.get(0).getDamCod());
			List<CDamage> cddList = JpaUtils.findAll(cdJpql, cdParams);
			// 残损类型
			result.append(cddList.get(0).getDamNam() + "&");
			String cdaJpql = "SELECT c FROM CDamgArea c where c.damgAreaCod =:damgAreaCod";
			QueryParamLs cdaParams = new QueryParamLs();
			cdaParams.addParam("damgAreaCod", cdList.get(0).getDamArea());
			List<CDamgArea> cdaList = JpaUtils.findAll(cdaJpql, cdaParams);
			// 残损位置
			result.append(cdaList.get(0).getDamgArea() + "@");
			String cdlJpql = "SELECT c FROM CDamgLevel c where c.damgLevelCod =:damgLevelCod";
			QueryParamLs cdlParams = new QueryParamLs();
			cdlParams.addParam("damgLevelCod", cdList.get(0).getDamLevel());
			List<CDamgLevel> cdlList = JpaUtils.findAll(cdlJpql, cdlParams);
			// 残损程度
			result.append(cdlList.get(0).getDamgLevel());
		}
		return result;
	}

	@Override
	public String damageloader(String req) {
		System.out.println(req);
		JSONObject jobject = JSONObject.fromObject(req);
		Date sysDate = HdUtils.getDateTime();
		String Account = (String) jobject.get("account");
		String result = "true";
		if (jobject.get("damCod") == null || jobject.get("damCod").equals("")) {
			result = "false";
		} else {
			//如果残损信息不为空，更新port_car信息
			String pcJpql = "select a from PortCar a where a.rfidCardNo =:rfid";
			QueryParamLs pcParamLs = new QueryParamLs();
			pcParamLs.addParam("rfid", jobject.get("rfid").toString());
			List<PortCar> pcList = JpaUtils.findAll(pcJpql, pcParamLs);
			if(pcList.size() > 0) {
				PortCar pcu = pcList.get(0);
				pcu.setDamId("1");
				pcu.setDamArea(String.valueOf(jobject.get("damArea")));
				pcu.setDamCod(String.valueOf(jobject.get("damCod")));
				pcu.setDamLevel(String.valueOf(jobject.get("damLevel")));
				pcu.setUpdNam(Account);
				pcu.setUpdTim((Timestamp) sysDate);
				JpaUtils.getBaseDao().update(pcu);
				
				//生成残损记录
				CarDamage cdlist = new CarDamage();
				cdlist.setCardamagId(HdUtils.genUuid());
				cdlist.setDamArea((String)jobject.get("damArea"));
				BigDecimal pcn = new BigDecimal((String) jobject.get("portCarNo"));
				cdlist.setPortCarNo(pcn);
				cdlist.setDamCod((String)jobject.get("damCod"));
				cdlist.setDamLevel((String)jobject.get("damLevel"));
				cdlist.setRecNam(Account);
				cdlist.setRecTim((Timestamp) sysDate);
				cdlist.setUpdNam(Account);
				cdlist.setUpdTim((Timestamp) sysDate);
				cdlist.setVinNo((String)jobject.get("vinNo"));
				cdlist.setIncharge((String)jobject.get("incharge"));
				String orgnId = HdUtils.getCurUser().getOrgnId();
				String authOrgnJpql = "select t from AuthOrgn t where t.orgnId=:orgnId";
				QueryParamLs authOrgnParams = new QueryParamLs();
				authOrgnParams.addParam("orgnId", orgnId);
				List<AuthOrgn> authOrgnList = JpaUtils.findAll(authOrgnJpql, authOrgnParams);
				if(authOrgnList.size() > 0) {
					cdlist.setRegPost(authOrgnList.get(0).getName());
				} else {
					cdlist.setRegPost(Account);
				}
				JpaUtils.getBaseDao().save(cdlist);
			}
		}

		return result;
	}

}
