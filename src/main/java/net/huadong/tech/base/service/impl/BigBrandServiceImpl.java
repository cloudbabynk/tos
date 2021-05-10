package net.huadong.tech.base.service.impl;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.BigBrand;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.service.BigBrandService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Component
public class BigBrandServiceImpl implements BigBrandService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		String jpql="select a from BigBrand a where 1=1 ";
		String carKind = hdQuery.getStr("carKind");
		String carKindNam = hdQuery.getStr("carKindNam");
		String checkFlag = hdQuery.getStr("checkFlag");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(carKind)){
			jpql += "and a.carKind =:carKind ";
			paramLs.addParam("carKind", carKind);
		}
		if(HdUtils.strNotNull(carKindNam)){
			jpql += "and a.carKindNam =:carKindNam ";
			paramLs.addParam("carKindNam", carKindNam);
		}
		if(HdUtils.strNotNull(checkFlag)){
			jpql += "and a.checkFlag =:checkFlag ";
			paramLs.addParam("checkFlag", checkFlag);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs , hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<BigBrand> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String bigBrandCod) {
		List<String> bigBrandCodList = HdUtils.paraseStrs(bigBrandCod);
		for (String bigBrand : bigBrandCodList) {
			JpaUtils.remove(BigBrand.class, bigBrand);
		}
	}

	@Transactional
	public void checkAll(String bigBrandCod) {
		List<String> bigBrandCodList = HdUtils.paraseStrs(bigBrandCod);
		for (String carKind : bigBrandCodList) {
			BigBrand bigBrand = JpaUtils.findById(BigBrand.class, carKind);
			JpaUtils.update(bigBrand);
		}
	}

	@Override
	public BigBrand findone(String bigBrandCod) {
		BigBrand bigBrand = JpaUtils.findById(BigBrand.class, bigBrandCod);
		return bigBrand;

	}

	@Override
	public HdMessageCode saveone(@RequestBody BigBrand bigBrand) {
		if(HdUtils.strIsNull(bigBrand.getBigBrandCod())){
			String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			if (HdUtils.strNotNull(bigBrand.getBigBrandNam())){
				String sql = "select UPPER(substr(fn_getpy('"+bigBrand.getBigBrandNam()+"'),1,1)) as INITIALS from dual";
				List<Map>  initialsList = JpaUtils.getEntityManager().createNativeQuery(sql).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
				if (initialsList.size() > 0){
					for (Map map : initialsList) {
						bigBrand.setInitials((String) map.get("INITIALS")+"");
					}
				}
				bigBrand.getInitials();
			}
			bigBrand.setBigBrandCod(datetime.substring(4));
			JpaUtils.save(bigBrand);
		}else{
			JpaUtils.update(bigBrand);
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode findCCarKind(String bigBrandCod) {
		if(HdUtils.strNotNull(bigBrandCod)){
			BigBrand bigBrand = JpaUtils.findById(BigBrand.class, bigBrandCod);
			if(bigBrand != null){
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}
}

