
package net.huadong.tech.privilege.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.idev.utils.HdCipher;
import net.huadong.tech.base.bean.HdConstant;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdQuerySpec;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthResource;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.service.AuthUserService;
import net.huadong.tech.privilege.util.HdEzuiExportFile;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.criterialquery.HdExportExcel;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.PrivilegeUtils;

/**
 * 系统管理-用户管理业务逻辑处理Repository
 *
 * @author dengmj
 * @version 1.0.0
 * @since 2017-1-16 9:00:00 wxl 2017.7.10修改
 */
@Component
public class AuthUserServiceImpl implements AuthUserService {

	public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

	// @Autowired
	// MongodbMethod method;
	// @Autowired
	// RabbitService rabbitService;

	public AuthUser find(String userId) {
		AuthUser user = JpaUtils.findById(AuthUser.class, userId);
		return user;
	}

	@Override
	public int authUserUpdatePassword(String userId, String password) {
		AuthUser user = this.find(userId);
		if (user == null) {
			return 0;
		}
		user.setPassword(password);
		user.setPwModifyTim(HdUtils.getDateTime());
		user.setNextChgId(AuthUser.DONOTNEXTCHG);
		this.ezuiSave(user);
		return 1;
	}

	@Override
	public HdMessageCode remove(String userId) {
		AuthUser aurhuser = this.find(userId);
		if (HdConstant.TRUE.equals(aurhuser.getStopId())) {
			AuthUser user = JpaUtils.findById(AuthUser.class, userId);
			user.setDelFlg(HdConstant.TRUE);
			JpaUtils.update(user);
		} else {
			throw new HdRunTimeException("删除前,请先停用账号！");
		}
		return HdUtils.genMsg();
	}

	// @Override
	// public String authUserGetAuthUserJson(List<AuthUser> list) {
	// return authUserRepository.getAuthUserJson(list);
	// }

	// @Override
	// public AuthUser findByAccount(String account) {
	// return authUserRepository.findByAccount(account);
	// }

	// @Override
	// public void delete(String userId) {
	//
	// }

	@Override
	public void authUserUpdateUserStyleCss(String userId, String stylecss) {
		AuthUser user = JpaUtils.findById(AuthUser.class, userId);
		user.setSkin(stylecss);
		JpaUtils.update(user);
	}

	/*
	 * @Override public int authUserMove(String userId, String tenantId, String
	 * orgnId){ return authUserRepository.move(userId, tenantId, orgnId); }
	 */
	@Override
	public HdEzuiDatagridData findcode(HdQuery hdQuery) {
		String orgnId = hdQuery.getStr("orgnId");
		String name = hdQuery.getStr("name");
		String jpql = "select a from AuthUser a join fetch a.authOrgn o where (a.delFlg is null) ";
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(orgnId)) {
			paramLs.addParam("code", "%|" + orgnId + "|%");
			jpql += " and a.authOrgn.recCode like :code";
		}
		if (HdUtils.strNotNull(name)) {
			paramLs.addParam("content", "%" + name + "%");
			jpql += " and (a.name like :content or a.account like :content)";
		}
		jpql += PrivilegeUtils.addJpql("a.orgnId", paramLs);
		// 高级查询用
		hdQuery.getSpecLs().add(new HdQuerySpec("orgnName", "a.authOrgn", "orgnId"));
		HdEzuiDatagridData data = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<AuthUser> userLs = data.getRows();
		init(userLs);
		return data;
	}

	public void init(List<AuthUser> userLs) {
		for (AuthUser user : userLs) {
			try {
				user.setOrgnName(user.getAuthOrgn().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Response exportExcelEx(HdExportExcel hdExportExcel, HdEzuiDatagridData data) {
		return HdEzuiExportFile.exportExcelFile(hdExportExcel.getColumnTitles(), hdExportExcel.getColumnNames(),
				data.getRows(), hdExportExcel.getExportFileName());
	}

	// @Override
	// public Response find(HdEzuiQueryParams params) {
	// HdEzuiDatagridData data = this.ezuiFind(params);
	// return Response.ok(data).build();
	// }

	// @Override
	// public Response save(HdEzuiSaveDatagridData data, String string) {
	// return authUserRepository.save(data, string);
	// }

	@Override
	public AuthUser findByAccount(String account) {
		String jpql = "SELECT a FROM AuthUser a WHERE a.account = :account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", account);
		List<AuthUser> userLs = JpaUtils.findAll(jpql, paramLs);
		if (userLs.size() > 0) {
			return userLs.get(0);
		} else {
			return new AuthUser();
		}
	}

	// @Override
	// public int authUserMove(String userId, String tenantId, String orgnId) {
	// return authUserRepository.move(userId, tenantId, orgnId);
	// }

	@Override
	public HdMessageCode ezuiSave(AuthUser authUser) {

		if (isAccountUse(authUser.getAccount(), authUser.getUserId())) {
			throw new HdRunTimeException("账号已存在，请重新输入！");
		}
		if (HdUtils.strIsNull(authUser.getUserId())) {
			if (!authUser.getPasswordTemp().matches(this.PW_PATTERN)){
				throw new HdRunTimeException("密码设定规则，长度大于等于8位，必须包含大写字母、小写字母、数字、字符，请确认！");
		    }
			authUser.setPassword(HdCipher.getMD(authUser.getPasswordTemp(), "MD5"));
			authUser.setUserId(HdUtils.genUuid());
			JpaUtils.save(authUser);
		} else {
//			AuthUser oldUser = JpaUtils.findById(AuthUser.class, authUser.getUserId());
//			authUser.setPassword(oldUser.getPassword());
			JpaUtils.update(authUser);
		}

		return HdUtils.genMsg();
	}

	// @Override
	// public void authUserUpdateToken(AuthUser authUser) {
	// authUserRepository.modifyTokenByAccount(authUser);
	//
	// }
	//
	// @Override
	// public void saveSessionToMonogo(AuthUser user) {
	// method.connect();
	// method.saveMethod(user, "user");
	// method.destory();
	// }
	//
	// @Override
	// public void removeSessionToMonogo(String userId) {
	// System.out.println(RpcContext.getContext().getAttachments());
	// method.connect();
	// method.deleteMethod("userID", userId, "user");
	// method.destory();
	// }

	// @Override
	// public HdEzuiDatagridData ezuiFind(HdQuery params) {
	// String jpql="select a from AuthUser a";
	// HdEzuiDatagridData result = JPAUtil.findByEz(jpql, paramLs, query);
	// return result;
	// }

	@Override
	public boolean isAccountUse(String account, String excuserId) {
		String jpql = "select a from AuthUser a where a.account=:account";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("account", account);
		if (HdUtils.strNotNull(excuserId)) {// 修改时,也可能和其它账号冲突但不会和自身冲突
			jpql += " and a.userId!=:userId";
			paramLs.addParam("userId", excuserId);
		}
		List<AuthUser> userLs = JpaUtils.findAll(jpql, paramLs);
		if (userLs.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateUserLanguage(String userId, String language) {
		AuthUser authUser = JpaUtils.findById(AuthUser.class, userId);
		authUser.setLanguage(language);
		JpaUtils.update(authUser);
	}

	@Override
	public List<AuthResource> getResourceByUserId(String userId) {
		String jpql = "select r from AuthResource r where r.resourceId in(select rr.authRoleResourcePK.resourceId from AuthRoleResource rr where rr.authRoleResourcePK.roleId in (select ur.authUserRolePK.roleId from AuthUserRole ur where ur.authUserRolePK.userId = :userId))";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		List<AuthResource> resources = JpaUtils.findAll(jpql, paramLs);
		return resources;
	}

}
