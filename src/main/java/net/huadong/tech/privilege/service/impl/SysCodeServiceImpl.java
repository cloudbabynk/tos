package net.huadong.tech.privilege.service.impl;

import java.util.List;
import java.util.Map;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdConstant;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.privilege.service.SysCodeService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class SysCodeServiceImpl implements SysCodeService {

	@Override
	public HdEzuiDatagridData ezuiFindSysCode(HdQuery query) {
		String jpql = "select a from SysCode";
		QueryParamLs paramLs = new QueryParamLs();
		return JpaUtils.findByEz(jpql, paramLs, query);
	}

	@Override
	public List<EzTreeBean> findSyscodeType() {
		return SysCode.genType();
	}

	@Override
	@CacheEvict(value = { "SysCodeOne", "SysCodeLs" }, allEntries = true) // 清除缓存
	public HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<SysCode> hdEzuiSaveDatagridData) {
		for (SysCode bean : hdEzuiSaveDatagridData.getInsertedRows()) {
			bean.setCodeId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Override
	@Cacheable(value = "SysCodeOne") // 缓存
	public String findSyscodeNam(String fieldcod, String code) {
		if (HdUtils.strIsNull(code)) {
			return "";
		}
		String jpql = "select a from SysCode a where a.fieldCod =:fieldCod and a.code =:code";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("fieldCod", fieldcod);
		paramLs.addParam("code", code);
		try {
			List<SysCode> codels = JpaUtils.findAll(jpql, paramLs);
			return codels.get(0).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "-err";
		}
	}

	@Override
	@Cacheable(value = "SysCodeLs") // 缓存
	public List<SysCode> findAll(String fieldCod) {
		String jpql = "select a from SysCode a where a.fieldCod=:fieldCod"
				+ " and (a.abandonedMark is null or a.abandonedMark!=:abandonedMark)";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("fieldCod", fieldCod);
		paramLs.addParam("abandonedMark", HdConstant.TRUE);// !=
		return JpaUtils.findAll(jpql, paramLs);
	}

	@Override
	/**
	 * 
	 * @param params
	 * @param showAll
	 *            是否展示包括废除的
	 * @return
	 */
	public HdEzuiDatagridData findbyfieldCod(HdQuery params, boolean showAll) {
		String jpql = "select a from SysCode a where a.fieldCod=:fieldCod and a.parentId is null";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(params.getStr("fieldCod"))) {
			paramLs.addParam("fieldCod", params.getStr("fieldCod"));
		} else
			paramLs.addParam("fieldCod", "0");
//		String q = params.getQ();
//		if (HdUtils.strNotNull(q)) {
//			jpql += " and (a.name like :content or a.code like :content)";
//			paramLs.addParam("content", "%" + q + "%");
//		}
		if (showAll) {
			jpql += " and (a.abandonedMark is null or a.abandonedMark!=:abandonedMark)";
			paramLs.addParam("abandonedMark", HdConstant.TRUE);// !=
		}

		return JpaUtils.findByEz(jpql, paramLs, params);
	}

	@Override
	public HdEzuiDatagridData findUnReason(HdQuery params) {
		String q = params.getQ();

		String sql = "select code,name from V_GROUP_CORP_CAUSE_CODE where 1=1";
		if(HdUtils.strNotNull(q)){
			sql += " and code like '%"+q+"%' or name like '%"+q+"%'";
		}
		List<Map<String, String>> res = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData data = new HdEzuiDatagridData();
		data.setRows(res);

		return data;
	}

	@Override
	public HdEzuiDatagridData cPort(HdQuery params) {
		String q = params.getQ();

		String sql = "select port_cod CODE, c_port_nam NAME from c_port where port_class = '3' ";
		if(HdUtils.strNotNull(q)){
			sql += " and port_cod like '%"+q+"%' or c_port_nam like '%"+q+"%'";
		}
		List<Map<String, String>> res = JpaUtils.getEntityManager().createNativeQuery(sql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		HdEzuiDatagridData data = new HdEzuiDatagridData();
		data.setRows(res);

		return data;
	}

}
