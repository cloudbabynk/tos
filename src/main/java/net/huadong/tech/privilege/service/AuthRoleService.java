package net.huadong.tech.privilege.service;

import java.util.List;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.AuthRole;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;

/**
 * @author zhangdh wxl
 */
public interface AuthRoleService {

	public HdEzuiDatagridData ezuiFind(HdQuery hdQuery);

	HdMessageCode ezuiSave(HdEzuiSaveDatagridData<AuthRole> hdEzuiSaveDatagridData);

	/**
	 * 查询员工下所有角色id
	 * 
	 * @param userId
	 * @return
	 */
	List<String> findRoleIdByUserId(String userId);

	public HdMessageCode remove(String roleIds);

	/**
	 * 勾选时直接设置权限
	 * 
	 * @param roleid
	 * @param list
	 * @param authUser
	 */
	public HdMessageCode updatePermission(String roleId, String privilegeIds);

	void removeRoleUser(String roleid, String userId);

	void removeUserResourse(String userid);

	void removeUserPrivilege(String userid);

	void addRoleUser(String roleId, String userId);

	// void updateUserPermission(String userId, List list, AuthUser authUser);

	// List find(HdQuery hdQuery);
	//
	// List<AuthRole> findAllRole();

	// 是否有管理员权限
	public boolean isAdmin(String userId);

	// 查询用户已授权的角色
	public HdEzuiDatagridData findHaveAuthRole(HdQuery hdQuery);

	//
	public HdEzuiDatagridData findDataByRole(String roleId);

	public void updateRoleOrgn(String roleId, String preId, List<String> list, AuthUser authUser);

	public List<String> findUserIdByRoleAndOrg(String roleId, String orgnId, boolean allChild);

	public HdEzuiDatagridData findOrgnTypeByRole(String roleId);
}
