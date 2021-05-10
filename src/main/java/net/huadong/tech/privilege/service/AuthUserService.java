package net.huadong.tech.privilege.service;

import java.util.List;

import javax.ws.rs.core.Response;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.AuthResource;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.springboot.core.criterialquery.HdExportExcel;

/**
 * 系统管理-用户管理模块对外提供接口
 *
 * @author dengmj
 * @version 1.0.0
 * @since 2017-1-16 8:39:00
 */

public interface AuthUserService {
	public AuthUser find(String userId);

	int authUserUpdatePassword(String userId, String password);

	// String authUserGetAuthUserJson(List<AuthUser> list);

	// AuthUser findByAccount(String account);
	HdMessageCode remove(String userId);

	// void delete(String userId);

	void authUserUpdateUserStyleCss(String userId, String stylecss);

	// int authUserMove(String userId, String tenantId, String orgnId);

	// HdEzuiDatagridData ezuiFind(HdEzuiQueryParams params);

	Response exportExcelEx(HdExportExcel hdExportExcel, HdEzuiDatagridData data);

	//Response find(HdEzuiQueryParams params);

	// Response save(HdEzuiSaveDatagridData data, String string);

	AuthUser findByAccount(String account);

	boolean isAccountUse(String account, String excuserId);

	// void authUserUpdateToken(AuthUser authUser);

	public HdMessageCode ezuiSave(AuthUser user);//

	// /**
	// * @author liangjh
	// * @param user
	// */
	// public void saveSessionToMonogo(AuthUser user);
	//
	// public void removeSessionToMonogo(String userId);

	HdEzuiDatagridData findcode(HdQuery params);

	/**
	 * 
	 * @Title: editUserLanguage
	 * @Description: 修改用户language信息
	 * @param userId
	 * @param language
	 * @return HdMessageCode
	 * @Date 2017年9月15日
	 */
	void updateUserLanguage(String userId, String language);

	public List<AuthResource> getResourceByUserId(String userId);

}
