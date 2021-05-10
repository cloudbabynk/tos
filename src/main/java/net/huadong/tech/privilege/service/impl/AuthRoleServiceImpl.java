package net.huadong.tech.privilege.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdConstant;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthRole;
import net.huadong.tech.privilege.entity.AuthRoleOrgn;
import net.huadong.tech.privilege.entity.AuthRoleOrgnPK;
import net.huadong.tech.privilege.entity.AuthRolePrivilege;
import net.huadong.tech.privilege.entity.AuthRolePrivilegePK;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.entity.AuthUserRole;
import net.huadong.tech.privilege.entity.AuthUserRolePK;
import net.huadong.tech.privilege.service.AuthRoleService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * 
 * @author ： wangxiaoliang
 * @Date: 2017年10月25日
 */
@Component
public class AuthRoleServiceImpl implements AuthRoleService {

	// /**
	// * 查找所有角色
	// *
	// * @return
	// */
	// @Override
	// public List findAllRole(AuthRole) {
	// return authRoleRespository.findAll();
	// }
	//
	// /**
	// * 查找全部role
	// *
	// * @return
	// */
	// @Override
	// public HdEzuiDatagridData find(HdQuery hdQuery) {
	// return authRoleRespository.ezuiFind(hdQuery);
	// }

	/**
	 * 根据UserId 找 roleId
	 *
	 * @param OrgnId
	 *            组Id
	 * @return
	 */
	@Override
	public List<String> findRoleIdByUserId(String userId) {
		String jpql = "select a.authUserRolePK.roleId from AuthUserRole a where a.authUserRolePK.userId=:userId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		return JpaUtils.findAll(jpql, paramLs);
	}

	/**
	 * 根据角色删除对应的功能
	 *
	 * @param roleid
	 *            角色ID
	 */
	private void removeRolePrivilegeid(String roleId) {
		String jpql = "delete from AuthRolePrivilege a where a.authRolePrivilegePK.roleId=:roleId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		JpaUtils.execUpdate(jpql, paramLs);
	}

	/**
	 * 根据角色删除对应的资源
	 *
	 * @param roleid
	 *            角色ID
	 */
	private void removeRoleResourse(String roleId) {
		String jpql = "delete from AuthUserRole a where a.authUserRolePK.roleId=:roleId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		JpaUtils.execUpdate(jpql, paramLs);
	}

	/**
	 * 角色删除,有角色的不能删除
	 *
	 * @param roleid
	 *            角色
	 * @return
	 */
	@Override
	@Transactional
	public HdMessageCode remove(String roleIds) {
		String jpql = "select u from  AuthUser u,AuthUserRole a "
				+ " where a.authUserRolePK.userId=u.userId and a.authUserRolePK.roleId=:roleId";
		List<String> roleIdLs = HdUtils.paraseStrs(roleIds);
		for (String roleId : roleIdLs) {
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("roleId", roleId);
			List<AuthUser> userLs = JpaUtils.findAll(jpql, paramLs);
			if (userLs.size() > 0) {
				String errName = "";
				for (AuthUser u : userLs) {
					errName += u.getAccount() + ";";
				}
				throw new HdRunTimeException("角色已授权不能删除:" + errName);
			}
			this.removeRolePrivilegeid(roleId);
			this.removeRoleResourse(roleId);
			JpaUtils.remove(AuthRole.class, roleId);
		}
		return HdUtils.genMsg();
	}

	/**
	 * 删除员工与功能的映射。
	 *
	 * @param userId
	 *            员工Id
	 * @return
	 */
	@Override
	public void removeUserPrivilege(String userId) {
		String jpql = "delete from AuthUserPrivilege a where a.authUserPrivilegePK.userId = :userId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		JpaUtils.execUpdate(jpql, paramLs);

	}

	/**
	 * 删除员工与资源的映射。
	 *
	 * @param userId
	 *            员工Id
	 * @return
	 */
	@Override
	public void removeUserResourse(String userId) {
		String jpql = "delete  from AuthUserResource a  where a.authUserResourcePK.userId = :userId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		JpaUtils.execUpdate(jpql, paramLs);
	}

	/**
	 * 添加员工与角色的映射。
	 *
	 * @param roleid
	 *            角色Id
	 * @param userId
	 *            员工Id
	 * @return
	 */
	@Override
	public void addRoleUser(String roleId, String userId) {
		AuthUserRole role = new AuthUserRole();
		AuthUserRolePK pk = new AuthUserRolePK();
		pk.setRoleId(roleId);
		pk.setUserId(userId);
		AuthUserRole authrole = JpaUtils.findById(AuthUserRole.class, pk);
		if (authrole != null) {
			return;
		}
		role.setAuthUserRolePK(pk);
		JpaUtils.save(role);// 没有就添加有就更新
	}

	/**
	 * 删除员工与角色的映射。
	 *
	 * @param roleid
	 *            角色Id
	 * @param userId
	 *            员工Id
	 * @return
	 */
	@Override
	public void removeRoleUser(String roleId, String userId) {
		String jpql = "delete from AuthUserRole a where a.authUserRolePK.roleId=:roleId and a.authUserRolePK.userId=:userId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		paramLs.addParam("roleId", roleId);
		JpaUtils.execUpdate(jpql, paramLs);

	}

	/**
	 * 根据角色，修改对应的功能及资源
	 *
	 * @param roleid
	 *            角色ID
	 * @param list
	 *            数据集合
	 */
	@Override
	@Transactional
	public HdMessageCode updatePermission(String roleId, String privilegeIds) {
		List<String> privilegeIdLs = HdUtils.paraseStrs(privilegeIds);
		this.removeRolePrivilegeid(roleId);
		for (String privilegeId : privilegeIdLs) {
			AuthRolePrivilege one = new AuthRolePrivilege();
			AuthRolePrivilegePK vilPk = new AuthRolePrivilegePK(roleId, privilegeId);
			one.setAuthRolePrivilegePK(vilPk);
			JpaUtils.save(one);
		}
		return HdUtils.genMsg();
	}

	// /**
	// * 根据用户，修改对应的功能及资源
	// *
	// * @param userId
	// * 用户ID
	// * @param list
	// * 数据集合
	// */
	// @Override
	// public void updateUserPermission(String userId, List list, AuthUser
	// authUser) {
	// authRoleRespository.updateUserPermission(userId, list, authUser);
	// }

	@Override
	public HdMessageCode ezuiSave(HdEzuiSaveDatagridData<AuthRole> data) {
		List<AuthRole> authRoleList = data.getInsertedRows();
		for(AuthRole bean : authRoleList){
			bean.setRoleId(HdUtils.genUuid());
		}
		return JpaUtils.save(data);
	}

	@Override
	public HdEzuiDatagridData ezuiFind(HdQuery hdQuery) {
		String jpql = "select a from AuthRole a";
		QueryParamLs paramLs = new QueryParamLs();
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	// 是否有管理员权限
	public boolean isAdmin(String userId) {
		String jpql = "select count(r) from AuthRole r join AuthUserRole u on u.authUserRolePK.roleId=r.roleId"
				+ " where u.authUserRolePK.userId=:userId and r.isAdmin=:isAdmin";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		paramLs.addParam("isAdmin", HdConstant.TRUE);
		Long num = JpaUtils.single(jpql, paramLs);
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询用户已授权的角色
	 * 
	 * @param hdQuery
	 * @return
	 */
	public HdEzuiDatagridData findHaveAuthRole(HdQuery hdQuery) {
		String userId = hdQuery.getStr("userId");
		String text = hdQuery.getStr("text");
		String jpql = "select a from AuthRole a,AuthUserRole u"
				+ " where u.authUserRolePK.roleId=a.roleId and u.authUserRolePK.userId=:userId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("userId", userId);
		if (HdUtils.strNotNull(text)) {
			jpql += " and (a.name like :text or a.description like :text)";
			paramLs.addParam("text", "%" + text + "%");
		}
		HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		return result;
	}

	@Override
	public HdEzuiDatagridData findDataByRole(String roleId) {
		String jpql = "select o from AuthRoleOrgn o where o.authRoleOrgnPK.roleId = :roleId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		List<AuthRoleOrgn> orgnLs = JpaUtils.findAll(jpql, paramLs);
		HdEzuiDatagridData datagrid = new HdEzuiDatagridData();
		datagrid.setRows(orgnLs);
		datagrid.setTotal(orgnLs.size());
		return datagrid;
	}

	@Override
	@Transactional
	public void updateRoleOrgn(String roleId, String preId, List<String> list, AuthUser authUser) {
		this.removeRoleandOrgn(roleId, preId);
		for (String orgnId : list) {
			AuthRoleOrgn one = new AuthRoleOrgn();
			AuthRoleOrgnPK vilPk = new AuthRoleOrgnPK(roleId, preId, orgnId);
			one.setAuthRoleOrgnPK(vilPk);
			JpaUtils.save(one);
		}
	}

	private void removeRoleandOrgn(String roleId, String preId) {
		String jpql = "delete from AuthRoleOrgn a where a.authRoleOrgnPK.roleId=:roleId and a.authRoleOrgnPK.privilegeId=:privilegeId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		paramLs.addParam("privilegeId", preId);
		JpaUtils.execUpdate(jpql, paramLs);
	}

	@Override
	public List<String> findUserIdByRoleAndOrg(String roleName, String orgnId, boolean allChild) {
		String jpql = "select u.userId from AuthUserRole a,AuthRole e,AuthUser u,AuthOrgn o"
				+ " where a.authUserRolePK.roleId=e.roleId and e.name like :roleName"
				+ " and a.authUserRolePK.userId=u.userId and u.orgnId=o.orgnId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleName", roleName);
		if (allChild) {
			jpql += " and o.recCode like :orgnId";
			paramLs.addParam("orgnId", "%|" + orgnId + "|%");
		} else {
			jpql += " and o.orgnId=:orgnId";
			paramLs.addParam("orgnId", orgnId);
		}
		return JpaUtils.findAll(jpql, paramLs);
	}

	@Override
	public HdEzuiDatagridData findOrgnTypeByRole(String roleId) {
		String jpql = "select o from AuthRolePrivilege o where o.authRolePrivilegePK.roleId = :roleId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		List<AuthRolePrivilege> prLs = JpaUtils.findAll(jpql, paramLs);
		HdEzuiDatagridData datagrid = new HdEzuiDatagridData();
		datagrid.setRows(prLs);
		datagrid.setTotal(prLs.size());
		return datagrid;
	}

}
