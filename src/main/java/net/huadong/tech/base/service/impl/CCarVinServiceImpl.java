package net.huadong.tech.base.service.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.huadong.tech.map.entity.CGisWbillCamera;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CCarVin;
import net.huadong.tech.base.service.CCarVinService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class CCarVinServiceImpl implements CCarVinService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from CCarVin a where 1=1 ";
		String typCod = hdQuery.getStr("typCod");
		String vinNo = hdQuery.getStr("vinNo");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(typCod)){
			jpql += "and a.typCod =:typCod ";
			paramLs.addParam("typCod", typCod);
		}
		if(HdUtils.strNotNull(vinNo)){
			jpql += "and a.vinNo =:vinNo ";
			paramLs.addParam("vinNo", vinNo);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}
	
	public HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<CCarVin> hdEzuiSaveDatagridData) {
		HdMessageCode hdMessageCode = new HdMessageCode();
		try {
			List<CCarVin> insertedRows = hdEzuiSaveDatagridData.getInsertedRows();
			if (insertedRows != null || insertedRows.size() > 0) {
				Iterator arg2 = hdEzuiSaveDatagridData.getInsertedRows().iterator();
				while (arg2.hasNext()) {
					CCarVin cCarVin = (CCarVin) arg2.next();
					cCarVin.setVinId(HdUtils.genUuid());
					cCarVin.setRecNam(HdUtils.getCurUser().getAccount());
					cCarVin.setRecTim(HdUtils.getDateTime());
					hdMessageCode = this.checkSameExist(cCarVin.getCarTyp(), cCarVin.getVinNo());
					if ("-1".equals(hdMessageCode.getCode())) {
						return hdMessageCode;
					}
				}
				JpaUtils.save(hdEzuiSaveDatagridData);
			}
			List<CCarVin> updateRows = hdEzuiSaveDatagridData.getUpdatedRows();
			if (updateRows != null || insertedRows.size() > 0) {
				Iterator arg3 = hdEzuiSaveDatagridData.getUpdatedRows().iterator();
				while (arg3.hasNext()) {
					CCarVin cCarVin = (CCarVin) arg3.next();
					//cCarVin.setVinId(HdUtils.genUuid());
					cCarVin.setUpdNam(HdUtils.getCurUser().getAccount());
					cCarVin.setUpdTim(HdUtils.getDateTime());
					JpaUtils.update(cCarVin);
				}
			}

			return HdUtils.genMsg();
		}catch (Exception e){
			hdMessageCode.setMessage(e.getMessage());
			hdMessageCode.setCode("-1");
			return  hdMessageCode;
		}
	}
	
	
	@Transactional
	public void removeAll(String vinIds) {
		// TODO Auto-generated method stub
		List<String> vinIdList = HdUtils.paraseStrs(vinIds);
		for (String vinId : vinIdList) {
			JpaUtils.remove(CCarVin.class, vinId);
		}	
	}

	@Override
	public CCarVin findone(String vinId) {
		// TODO Auto-generated method stub
		CCarVin cCarVin = JpaUtils.findById(CCarVin.class, vinId);
		return cCarVin;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CCarVin cCarVin) {
		// TODO Auto-generated method stub
		HdMessageCode hdMessageCode = new HdMessageCode();
		try {
			String vinId = cCarVin.getVinId();
			CCarVin ccarvin = JpaUtils.findById(CCarVin.class, vinId);
			if (ccarvin != null) {
				JpaUtils.update(cCarVin);
			} else {
				JpaUtils.save(cCarVin);
			}
			return HdUtils.genMsg();
		}catch (Exception e)
		{
			hdMessageCode.setCode("-1");
			hdMessageCode.setMessage(e.getMessage());
			return  hdMessageCode;
		}
	}
//	public HdMessageCode findCCarVin(String vinId) {
//		if(HdUtils.strNotNull(vinId)){
//			CCarVin cCarVin = JpaUtils.findById(CCarVin.class, vinId);
//			if(cCarVin != null){
//				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
//			}
//		}
//		return HdUtils.genMsg();
//	}
	
	public HdEzuiDatagridData findvinNoByCarTyp(HdQuery params, boolean showAll) {
		String jpql = "select a from CCarVin a where  1 = 1 ";
		String carTyp =params.getStr("carTyp");
		String vinNo =params.getStr("vinNo");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(carTyp)) {
			jpql+=" and a.carTyp=:carTyp ";
			paramLs.addParam("carTyp",carTyp);
		} else {
			jpql+=" and a.carTyp=:carTyp ";
			paramLs.addParam("carTyp", "0");
		}
		if (HdUtils.strNotNull(vinNo)) {
			jpql+=" and a.vinNo=:vinNo";
			paramLs.addParam("vinNo", vinNo);
		}
		return JpaUtils.findByEz(jpql, paramLs, params);
	}
	private HdMessageCode checkSameExist(String carTyp, String vinNo) {
		HdMessageCode hdMessageCode = new HdMessageCode();
		String jpql = "select count(a) from CCarVin a where a.carTyp =:carTyp and a.vinNo =:vinNo";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("carTyp", carTyp);
		paramLs.addParam("vinNo", vinNo);
		Long num = (Long) JpaUtils.single(jpql, paramLs);
		if (num.longValue() > 0L) {
			hdMessageCode.setMessage("存在相同车架号，保存失败");
			hdMessageCode.setCode("-1");
			return hdMessageCode;
		}else {
			hdMessageCode.setCode("1");
			return hdMessageCode;
		}
	}
	
    @Transactional
	@Override
	public HdMessageCode updatePortCarTyp(String carTyp, String vinNo) {
			HdMessageCode result1 = new HdMessageCode();
			String outinfo = "";
			List<Object> inParamLs = new ArrayList<Object>();
			inParamLs.add(carTyp);//过程参数值
	        inParamLs.add(vinNo);//过程参数值
			List<String> result = new ArrayList<String>();//过程返回值
			JpaUtils.executeOracleProcWithResult("p_update_port_car_typ", inParamLs, result, inParamLs.size()+1);
			outinfo = result.get(0);
			result1.setCode(outinfo);
			return result1;
	}  	
	
}

