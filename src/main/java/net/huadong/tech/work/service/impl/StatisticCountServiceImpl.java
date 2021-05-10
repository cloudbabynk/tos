package net.huadong.tech.work.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.cargo.entity.StatisticCount;
import net.huadong.tech.cargo.entity.StatisticCountJs;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;

import net.huadong.tech.springboot.core.HdEzuiDatagridData;

import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.service.StatisticCountService;

/**
 * @author
 */
@Component
public class StatisticCountServiceImpl implements StatisticCountService {
	@Override
	public HdEzuiDatagridData findSc(HdQuery hdQuery) {
		String jpql = "select a from StatisticCount a where 1=1 ";
		String inoutcytim = hdQuery.getStr("inouttim");
		String shipNoParams = hdQuery.getStr("shipNo");
		String iEIdParams = hdQuery.getStr("iEId");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(inoutcytim)) {
			jpql += " and a.ioyarddate=:inouttim ";
			paramLs.addParam("inouttim", HdUtils.strToDate(inoutcytim));
		}
		if (HdUtils.strNotNull(shipNoParams)) {
			jpql += " and a.shipNo=:shipNo ";
			paramLs.addParam("shipNo", shipNoParams);
		}
		if (HdUtils.strNotNull(iEIdParams)) {
			jpql += " and a.ieflag=:ieflag ";
			paramLs.addParam("ieflag", iEIdParams);
		}
		HdEzuiDatagridData results = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<StatisticCount> statisticCountL = results.getRows();
		if (statisticCountL.size() > 0) {
			for (StatisticCount sc : statisticCountL) {
				if (HdUtils.strNotNull(sc.getIoyardflag())) {
					if ("1".equals(sc.getIoyardflag())) {
						sc.setIoyardflagNam("入库");
					} else {
						sc.setIoyardflagNam("出库");
					}
					if (HdUtils.strNotNull(sc.getBrandcode())) {
						CBrand brandEntity = JpaUtils.findById(CBrand.class, sc.getBrandcode());
						if (brandEntity != null)
						sc.setBrandNam(brandEntity.getBrandNam());
					}
					if (HdUtils.strNotNull(sc.getIeflag())) {
						if ("I".equals(sc.getIeflag())) {
							sc.setIeNam("进口");
							;
						} else {
							sc.setIeNam("出口");
						}
					}
					if (HdUtils.strNotNull(sc.getTradeflag())) {
						if ("1".equals(sc.getTradeflag())) {
							sc.setTradeFlagNam("内贸");
						} else {
							sc.setTradeFlagNam("外贸");
						}
					}
					if (HdUtils.strNotNull(sc.getIoyardflag())) {
						if ("1".equals(sc.getIoyardflag())) {
							sc.setIoyardflagNam("入");
						} else {
							sc.setIoyardflagNam("出");
						}
					}
					if (HdUtils.strNotNull(sc.getTeamorgnid())) {
						if ("03406500".equals(sc.getTeamorgnid())) {
							sc.setTeamOrgnNam("滚装码头");
						} else {
							sc.setTeamOrgnNam("环球与滚装码头");
						}
					}

				}
			}
		}
		return results;
	}

	@Override
	public HdEzuiDatagridData findScJs(HdQuery hdQuery) {
		String jpql = "select a from StatisticCountJs a where 1=1 ";
		HdEzuiDatagridData results = JpaUtils.findByEz(jpql, null, hdQuery);
		List<StatisticCountJs> statisticCountL = results.getRows();
		if (statisticCountL.size() > 0) {
			for (StatisticCountJs sc : statisticCountL) {
				if (HdUtils.strNotNull(sc.getIoyardflag())) {
					if ("1".equals(sc.getIoyardflag())) {
						sc.setIoyardflagNam("入库");
					} else {
						sc.setIoyardflagNam("出库");
					}
					if (HdUtils.strNotNull(sc.getBrandcode())) {
						CBrand brandEntity = JpaUtils.findById(CBrand.class, sc.getBrandcode());
						sc.setBrandNam(brandEntity.getBrandNam());
					}
					if (HdUtils.strNotNull(sc.getIeflag())) {
						if ("I".equals(sc.getIeflag())) {
							sc.setIeNam("进口");
							;
						} else {
							sc.setIeNam("出口");
						}
					}
					if (HdUtils.strNotNull(sc.getTradeflag())) {
						if ("1".equals(sc.getTradeflag())) {
							sc.setTradeFlagNam("内贸");
						} else {
							sc.setTradeFlagNam("外贸");
						}
					}
					if (HdUtils.strNotNull(sc.getIoyardflag())) {
						if ("1".equals(sc.getIoyardflag())) {
							sc.setIoyardflagNam("入");
						} else {
							sc.setIoyardflagNam("出");
						}
					}
					if (HdUtils.strNotNull(sc.getTeamorgnid())) {
						if ("03406500".equals(sc.getTeamorgnid())) {
							sc.setTeamOrgnNam("滚装码头");
						} else {
							sc.setTeamOrgnNam("环球与滚装码头");
						}
					}

				}
			}
		}
		return results;
	}

}
