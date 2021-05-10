package net.huadong.tech.privilege.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.privilege.entity.SysField;
import net.huadong.tech.privilege.service.SysFieldService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * 
 * @author ：zhoujian修改
 * @Date: 2017年7月14日add
 */
@Component
public class SysFieldServiceImpl implements SysFieldService {



	@Override
	public HdEzuiDatagridData find(HdQuery query) {
		String jpql = "select a from SysField a where 1=1";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(query.getStr("fieldName"))) {
			jpql += " and (a.fieldName like :fieldName";
			paramLs.addParam("fieldName", "%"+query.getStr("fieldName")+"%");
		}
		if (HdUtils.strNotNull(query.getStr("fieldName"))) {
			jpql += " or a.fieldCod like :fieldCod)";
			paramLs.addParam("fieldCod", "%"+query.getStr("fieldName")+"%");
		}
		HdEzuiDatagridData data = JpaUtils.findByEz(jpql, paramLs, query);
		return  data;
	}
	
	@Override
	@Transactional
	public HdMessageCode save(HdEzuiSaveDatagridData<SysField> menu) {
		for (SysField bean : menu.getInsertedRows()) {
			bean.setFieldId(HdUtils.genUuid());
		}
		return JpaUtils.save(menu);

	}
	@Override
	@Transactional
	public HdMessageCode remove(String idLs) {
		String[] fieldCod = idLs.split(",");
		for (int i = 0; i < fieldCod.length; i++) {
		String jpql = "select a from SysCode a where a.fieldCod = :fieldCod";
		QueryParamLs param = new QueryParamLs();
		param.addParam("fieldCod", fieldCod[i]);
		List<SysCode> aurhuserLs = JpaUtils.findAll(jpql, param);
			if (!aurhuserLs.isEmpty()) {
			throw new HdRunTimeException("存在下级不能删除");
		} else {
		//	JPAUtil.remove(AuthOrgn.class, fieldCod);
			QueryParamLs cntr1Param = new QueryParamLs();
			cntr1Param.addParam("fieldCod", fieldCod[i]);
			JpaUtils.execUpdate("DELETE FROM SysField c WHERE c.fieldCod = :fieldCod", cntr1Param);

		}
		}
		return HdUtils.genMsg();
	}




}
