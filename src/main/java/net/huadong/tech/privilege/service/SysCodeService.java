package net.huadong.tech.privilege.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.SysCode;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * 
 * @author ： wangxiaoliang
 * @Date: 2017年6月29日
 */
public interface SysCodeService {

	public HdEzuiDatagridData ezuiFindSysCode(HdQuery hdQuery);

	public HdMessageCode ezuiSaveSysCode(HdEzuiSaveDatagridData<SysCode> hdEzuiSaveDatagridData);

	public String findSyscodeNam(String fieldcod, String code);

	public List<SysCode> findAll(String fieldCod);

	public List<EzTreeBean> findSyscodeType();

	public HdEzuiDatagridData findbyfieldCod(HdQuery params, boolean showAll);
	
	public HdEzuiDatagridData findUnReason(HdQuery params);
	public HdEzuiDatagridData cPort(HdQuery params);
	// public SysCode description(String code);
}
