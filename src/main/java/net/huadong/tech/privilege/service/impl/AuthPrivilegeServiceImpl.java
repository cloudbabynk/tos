package net.huadong.tech.privilege.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.privilege.entity.AuthPrivilege;
import net.huadong.tech.privilege.entity.AuthPrivilegeResource;
import net.huadong.tech.privilege.entity.AuthPrivilegeResourcePK;
import net.huadong.tech.privilege.entity.AuthResource;
import net.huadong.tech.privilege.entity.AuthRoleResource;
import net.huadong.tech.privilege.entity.AuthRoleResourcePK;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.service.AuthPrivilegeService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * 
 * @author ： wangxiaoliang
 * @Date: 2017年7月14日
 */
@Component
public class AuthPrivilegeServiceImpl implements AuthPrivilegeService {

	public HdMessageCode ezuiSave(HdEzuiSaveDatagridData<AuthPrivilege> hdEzuiSaveDatagridData) {
		List<AuthPrivilege> insertList = hdEzuiSaveDatagridData.getInsertedRows();
		for (AuthPrivilege bean : insertList){
			bean.setPrivilegeId(HdUtils.genUuid());
		}
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	public List<EzTreeBean> findAllForTree(AuthUser user) {
		return this.findAllForTree(user, "0");
	}

	@Override // type=1显示其它系统菜单
	public List<EzTreeBean> findAllForTree(AuthUser user, String privilegeType) {
		List<EzTreeBean> result = new ArrayList<>();
		String jpql = "select a from AuthPrivilege a where a.state = :open";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("open", AuthPrivilege.TYPE_OPEN);
		if (!user.isAdmin()) {// 根据角色获取菜单,三级菜单
			jpql += " and a.privilegeId in(select r.authRolePrivilegePK.privilegeId from AuthRolePrivilege r where r.authRolePrivilegePK.roleId in :roleLs)";
			jpql += " or a.privilegeId in(select a2.parentId from AuthPrivilege a2 where a2.privilegeId in(select r.authRolePrivilegePK.privilegeId from AuthRolePrivilege r where r.authRolePrivilegePK.roleId in :roleLs))";
			jpql += " or a.privilegeId in(select a3.parentId from AuthPrivilege a3 where a3.privilegeId in(select a4.parentId from AuthPrivilege a4 where a4.privilegeId in(select r.authRolePrivilegePK.privilegeId from AuthRolePrivilege r where r.authRolePrivilegePK.roleId in :roleLs)))";
			if (user.getRoleIdLs().size() > 0) {
				paramLs.addParam("roleLs", user.getRoleIdLs());
			} else {
				return result;
			}
		}

		jpql += " ORDER BY a.sorter";
		List<AuthPrivilege> allLs = JpaUtils.findAll(jpql, paramLs);// TODO 之前是视图
		List<EzTreeBean> allBeanLs = new ArrayList<>();
		for (AuthPrivilege auth : allLs) {

			allBeanLs.add(chgBean(auth));
		}
		List<EzTreeBean> oneLevel = new ArrayList<>();
		for (EzTreeBean bean : allBeanLs) {
			if (AuthPrivilege.PRIVILEGE_TYPE_SYS.equals(privilegeType)) {// 系统菜单
				if (AuthPrivilege.ROOT_SYS.equals(bean.getParentId())) {// 根节点,跳过第一级
					bean.setLevel(1);// 树的层级
					result.add(bean);
					oneLevel.add(bean);

				}
			} else {// 非系统菜单
				if (privilegeType.equals(bean.getAttributes()) && AuthPrivilege.ROOT_TOP.equals(bean.getParentId())) {// 根节点
					bean.setLevel(1);// 树的层级
					result.add(bean);
					oneLevel.add(bean);

				}
			}
		}
		allBeanLs.removeAll(oneLevel);
		for (EzTreeBean bean : result) {
			addChild(bean, allBeanLs, 2);
		}
		// allLs.remove(result);
		// List<AuthPrivilege> twoLevelLs = new ArrayList<>();
		// for (AuthPrivilege oneLevel : result) {
		// for (AuthPrivilege auth : allLs) {
		// if (oneLevel.getPrivilegeId().equals(auth.getParentId())) {
		// twoLevelLs.add(auth);
		// oneLevel.getChildren().add(auth);
		// }
		// }
		//
		// }
		// allLs.remove(twoLevelLs);
		// for (AuthPrivilege twoLevel : twoLevelLs) {
		// for (AuthPrivilege auth : allLs) {
		// if (twoLevel.getPrivilegeId().equals(auth.getParentId())) {
		// // twoLevel.getChildren().add(auth);
		// }
		// }
		//
		// }
		return result;
		// return menuRepository.findAllForTree(account);
	}

	// type 2
	@Override
	public List<EzTreeBean> authPrivilegeFindAllForTree(int type) {

		List<EzTreeBean> rootResult = new ArrayList<>();
		EzTreeBean rootBean = new EzTreeBean();
		rootBean.setId(-1L);
		rootBean.setState(EzTreeBean.OPEN);
		rootBean.setText(HdUtils.i18n("菜单资源分配"));
		rootResult.add(rootBean);

		String jpql = "select a from AuthPrivilege a";
		jpql += " ORDER BY a.sorter";
		List<AuthPrivilege> allLs = JpaUtils.findAll(jpql, null);// TODO 之前是视图
		List<EzTreeBean> allBeanLs = new ArrayList<>();
		for (AuthPrivilege auth : allLs) {
			// if(auth.getText()=="船舶调度"||auth.getText()=="系统管理")
			EzTreeBean bean = chgBean(auth);
			allBeanLs.add(bean);
		}

		List<EzTreeBean> oneLevel = new ArrayList<>();
		for (EzTreeBean root : rootResult) {// 主菜单循环

			List<EzTreeBean> result = new ArrayList<>();
			for (EzTreeBean bean : allBeanLs) {
				if (type == 0) {
					if (!"0".equals(bean.getId())) {// 只展示系统菜单
						continue;
					}
				}
				if (root.getId().equals(bean.getParentId())) {// 根节点
					bean.setState(EzTreeBean.OPEN);
					result.add(bean);
					oneLevel.add(bean);
				}
			}
			allBeanLs.removeAll(oneLevel);
			for (EzTreeBean bean : result) {
				addChild(bean, allBeanLs, 0);
			}
			root.getChildren().addAll(result);
		}
		return rootResult;
	}

	// 使用实体,映射上的会增加很多查询,单独建bean合适
	private EzTreeBean chgBean(AuthPrivilege auth) {
		String language = HdUtils.getCurUser().getLanguage();

		EzTreeBean result = new EzTreeBean();
		result.setId(auth.getPrivilegeId());
		result.setUrl(auth.getUrl());
		if ("en".equals(language)) {
			result.setText(auth.getEnText());
		} else {
			result.setText(auth.getText());
		}
		result.setParentId(auth.getParentId());
		result.setType(auth.getOpenType());// iframe还是tab
		result.setAttributes(auth.getPrivilegeType());// 系统内还是系统外
		return result;
	}

	private void addChild(EzTreeBean one, List<EzTreeBean> allLs, int i) {
		if (i > 5) {// 递归5层中断
			return;
		}
		List<EzTreeBean> levelLs = new ArrayList<>();
		for (EzTreeBean bean : allLs) {
			if (one.getId().equals(bean.getParentId())) {
				bean.setLevel(i);
				one.getChildren().add(bean);
				levelLs.add(bean);
			}
		}
		allLs.removeAll(levelLs);
		for (EzTreeBean child : levelLs) {
			this.addChild(child, allLs, i + 1);// i+1超过一定层级自动停止
		}
	}

	@Override
	public HdEzuiDatagridData find(HdQuery query) {
		String jpql = "select a from AuthPrivilege a where a.parentId=:parentId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("parentId", query.getStr("parentId"));
		return JpaUtils.findByEz(jpql, paramLs, query);
	}

	@Override
	public List<AuthResource> authPrivilegeGetByPrivilegeId(String privilegeId) {
		String jpql = "select a from AuthResource a,AuthPrivilegeResource r"
				+ " where r.authPrivilegeResourcePK.resourceId=a.resourceId and r.authPrivilegeResourcePK.privilegeId=:privilegeId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("privilegeId", privilegeId);
		return JpaUtils.findAll(jpql, paramLs);

	}

	//
	@Override
	@Transactional
	public HdMessageCode remove(String privilegeIds) {
		List<String> privilegeIdLs = HdUtils.paraseStrs(privilegeIds);
		String delRolePriv = "delete from AuthRolePrivilege a where a.authRolePrivilegePK.privilegeId=:privilegeId";
		String delRrivResource = "delete from AuthPrivilegeResource a where a.authPrivilegeResourcePK.privilegeId=:privilegeId";
		for (String privilegeId : privilegeIdLs) {
			checkDelete(privilegeId);
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("privilegeId", privilegeId);
			JpaUtils.execUpdate(delRolePriv, paramLs);// 删除角色菜单关系
			JpaUtils.execUpdate(delRrivResource, paramLs);// 删除角色菜单关系
			JpaUtils.remove(AuthPrivilege.class, privilegeId);
		}
		return HdUtils.genMsg();
	}

	private void checkDelete(String privilegeId) {
		String jpql = "select count(a) from AuthPrivilege a where a.parentId=:parentId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("parentId", privilegeId);
		Long num = JpaUtils.single(jpql, paramLs);
		if (num > 0) {
			throw new HdRunTimeException(HdUtils.i18n("存在下级,删除失败"));
		}
	}

	// @Override
	// public HdMessageCode
	// authPrivilegeEzuiSave(HdEzuiSaveDatagridData<AuthPrivilege> menu,
	// AuthUser user) {
	// if (menu.getInsertedRows().size() > 0) {
	// for (int i = 0; i < menu.getInsertedRows().size(); i++) {
	// menu.getInsertedRows().get(i).setInsAccount(user.getAccount());
	// menu.getInsertedRows().get(i).setInsTimestamp(new Date());
	// menu.getInsertedRows().get(i).setUpdAccount(user.getAccount());
	// menu.getInsertedRows().get(i).setUpdTimestamp(new Date());
	// menu.getInsertedRows().get(i).setText("测试");
	// }
	// } else if (menu.getUpdatedRows().size() > 0) {
	// for (int i = 0; i < menu.getUpdatedRows().size(); i++) {
	// menu.getUpdatedRows().get(i).setUpdAccount(user.getAccount());
	// menu.getUpdatedRows().get(i).setUpdTimestamp(new Date());
	// }
	// }
	// // Response res = super.save(menu,
	// // SecurityUtils.getSubject().getPrincipal().toString());
	// HdMessageCode hdMessageCode = authPrivilegeRepository.save(menu);
	// // 同步在ROLE_PRIVILEGE增加超级管理员权限
	// List<String> insertRolePrivilege = new ArrayList();
	// List<String> deleteRolePrivilege = new ArrayList();
	// if (menu.getInsertedRows().size() > 0) {
	// for (int i = 0; i < menu.getInsertedRows().size(); i++) {
	// insertRolePrivilege.add(menu.getInsertedRows().get(i).getPrivilegeId());
	// }
	// } else if (menu.getDeletedRows().size() > 0) {
	// for (int i = 0; i < menu.getDeletedRows().size(); i++) {
	// deleteRolePrivilege.add(menu.getDeletedRows().get(i).getPrivilegeId());
	// }
	// }
	// //
	// authPrivilegeRepository.saveRolePrivilege(insertRolePrivilege,deleteRolePrivilege,user);
	// return hdMessageCode;
	// }

	@Override
	@Transactional
	public boolean authPrivilegeSavePrivilegeResource(String privilegeId, String resourceIds) {
		String[] idAry = resourceIds.split(",");
		for (String id : idAry) {
			AuthPrivilegeResourcePK authPrivilegeResourcePK = new AuthPrivilegeResourcePK(privilegeId, id);
			AuthPrivilegeResource authPrivilegeResource = new AuthPrivilegeResource(authPrivilegeResourcePK);
			JpaUtils.save(authPrivilegeResource);
		}
		return true;
	}

	@Override
	@Transactional
	public HdMessageCode authPrivilegeDeltePrivilegeResource(String privilegeId, String resouceIds) {
		String delRrivResource = "delete from AuthPrivilegeResource a where a.authPrivilegeResourcePK.privilegeId=:privilegeId";
		String delRoleResource = "delete from AuthRoleResource a where a.authRoleResourcePK.privilegeId=:privilegeId and a.authRoleResourcePK.resourceId=:resourceId";
		if (HdUtils.strIsNull(resouceIds)) {
			return HdUtils.genMsg();
		}
		List<String> resourceIdLs = HdUtils.paraseStrs(resouceIds);
		for (String resourceId : resourceIdLs) {
			QueryParamLs paramLsRriv = new QueryParamLs();
			paramLsRriv.addParam("privilegeId", privilegeId);
			QueryParamLs paramLsRole = new QueryParamLs();
			paramLsRole.addParam("privilegeId", privilegeId);
			paramLsRole.addParam("resourceId", resourceId);
			JpaUtils.execUpdate(delRrivResource, paramLsRriv);// 删除按钮菜单关系
			JpaUtils.execUpdate(delRoleResource, paramLsRole);// 删除角色按钮关系
		}
		return HdUtils.genMsg();
	}

	public List<AuthPrivilege> getCheckedPrivilege(List<AuthPrivilege> listAuthPrivilege, List<String> list) {
		for (AuthPrivilege authPrivilege : listAuthPrivilege) {
			List<AuthPrivilege> children = (List<AuthPrivilege>) authPrivilege.getChildren();
			if (children == null || children.size() == 0) {
				for (String idStr : list) {
					String id = authPrivilege.getPrivilegeId();
					if (idStr.equals(id)) {
						authPrivilege.setChecked(true);
						break;
					}
				}
			} else {
				getCheckedPrivilege(children, list);
			}
		}
		return listAuthPrivilege;
	}

	@Override
	public List<AuthResource> authPrivilegeGetByPrivilegeIdAndRoleId(String privilegeId, String roleId) {
		String jpql = "select a from AuthResource a,AuthRoleResource r"
				+ " where r.authRoleResourcePK.resourceId=a.resourceId"
				+ " and r.authRoleResourcePK.privilegeId=:privilegeId and r.authRoleResourcePK.roleId=:roleId";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		paramLs.addParam("privilegeId", privilegeId);
		return JpaUtils.findAll(jpql, paramLs);// TODO 之前是视图
	}

	@Override
	public HdMessageCode authPrivilegeSaveRoleResource(String privilegeId, String roleId, String resourceIds) {
		List<String> resourceIdLs = HdUtils.paraseStrs(resourceIds);
		for (String resourceId : resourceIdLs) {
			AuthRoleResourcePK authRoleResourcePK = new AuthRoleResourcePK(roleId, resourceId, privilegeId);
			AuthRoleResource authRoleResource = new AuthRoleResource(authRoleResourcePK);
			JpaUtils.save(authRoleResource);
		}
		return HdUtils.genMsg();
	}

	@Override
	@Transactional
	public HdMessageCode authPrivilegeDeleteRoleResource(String privilegeId, String roleId, String resourceIds) {
		List<String> resourceIdLs = HdUtils.paraseStrs(resourceIds);

		String jpql = "delete from AuthRoleResource a where a.authRoleResourcePK.roleId=:roleId"
				+ " and a.authRoleResourcePK.resourceId=:resourceId and a.authRoleResourcePK.privilegeId=:privilegeId";
		for (String resourceId : resourceIdLs) {
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("roleId", roleId);
			paramLs.addParam("resourceId", resourceId);
			paramLs.addParam("privilegeId", privilegeId);
			JpaUtils.execUpdate(jpql, paramLs);
		}
		return HdUtils.genMsg();
	}

	@Override
	public List<EzTreeBean> getPrivilegeTreeByRole(String roleId) {
		List<EzTreeBean> rootResult = new ArrayList<>();
		EzTreeBean rootBean = new EzTreeBean();
		rootBean.setId(0L);
		rootBean.setState(EzTreeBean.OPEN);
		rootBean.setText(HdUtils.i18n("系统菜单"));
		rootResult.add(rootBean);

		String jpql = "select a from AuthPrivilege a where a.privilegeId in (select p.authRolePrivilegePK.privilegeId from AuthRolePrivilege p where p.authRolePrivilegePK.roleId = :roleId)";
		jpql += " ORDER BY a.sorter";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("roleId", roleId);
		List<AuthPrivilege> allLs = JpaUtils.findAll(jpql, paramLs);// TODO 之前是视图
		List<EzTreeBean> allBeanLs = new ArrayList<>();
		for (AuthPrivilege auth : allLs) {
			// if(auth.getText()=="船舶调度"||auth.getText()=="系统管理")
			allBeanLs.add(chgBean(auth));
		}
		List<EzTreeBean> oneLevel = new ArrayList<>();
		for (EzTreeBean root : rootResult) {// 主菜单循环
			List<EzTreeBean> result = new ArrayList<>();
			for (EzTreeBean bean : allBeanLs) {
				if (root.getId().equals(bean.getParentId())) {// 根节点
					result.add(bean);
					oneLevel.add(bean);
				}
			}
			allBeanLs.removeAll(oneLevel);
			for (EzTreeBean bean : result) {
				addChild(bean, allBeanLs, 0);
			}
			root.getChildren().addAll(result);
		}
		return rootResult;

	}

	@Override
	public List<EzTreeBean> getPrivilegeTreeByCurRole() {
		// TODO Auto-generated method stub
		return null;
	}

}
