package net.huadong.tech.privilege.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.privilege.entity.AuthRole;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.service.AuthOrgnService;
import net.huadong.tech.privilege.service.AuthPrivilegeRelService;
import net.huadong.tech.privilege.service.AuthRoleService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author zhangdh 修改：qidd 2017/7/16
 * @version 1.0.0
 */
@Api(value = "API - AuthRoleController")
@Controller
@RequestMapping("/webresources/login/privilege/AuthRole")
public class AuthRoleController {
	@Autowired
	AuthRoleService authRoleService;
	@Autowired
	AuthPrivilegeRelService authPrivilegeRelService;
	@Autowired
	AuthOrgnService authOrgnService;

	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdEzuiQueryParams) {
		return authRoleService.ezuiFind(hdEzuiQueryParams);

	}

	/**
	 * grid 增加、修改、删除操作
	 *
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode ezuiSave(@RequestBody HdEzuiSaveDatagridData<AuthRole> data) {
		return authRoleService.ezuiSave(data);
	}

	/**
	 * 根据UserId 找 roleId
	 *
	 * @param OrgnId
	 *            组Id
	 * @return
	 */
	@RequestMapping(value = "/findRoleByUserId", method = RequestMethod.POST)
	@ResponseBody
	public HdEzuiDatagridData findRoleByUserId(@RequestBody HdQuery hdEzuiQueryParams) {
		HdEzuiDatagridData result = null;
		String userId = "";
		String alreadyAuth = "";
		if (hdEzuiQueryParams.getOthers() != null){
			userId = (String) hdEzuiQueryParams.getOthers().get("userId");
			alreadyAuth = (String) hdEzuiQueryParams.getOthers().get("alreadyAuth");
		}
		if ("1".equals(alreadyAuth)) {// 只显示已授权
			result = authRoleService.findHaveAuthRole(hdEzuiQueryParams);
		} else {
			result = authRoleService.ezuiFind(hdEzuiQueryParams);
		}
		List<String> list = authRoleService.findRoleIdByUserId(userId);
		initCheckedRoleByUser(result.getRows(), list);
		return result;
		// return Response.ok(listAuthRole).build();
	}

	/**
	 * 获得员工以附角色的ID数组。
	 *
	 * @return
	 */
	public void initCheckedRoleByUser(List<AuthRole> listAuthRole, List<String> list) {
		for (AuthRole authRole : listAuthRole) {
			// List<AuthRole> children = (List<AuthRole>)
			// authRole.getChildren();
			for (String idStr : list) {
				String id = authRole.getRoleId();
				if (idStr.equals(id)) {
					authRole.setChecked(true);
					break;
				}
			}

		}
	}

	/**
	 * 检测该角色是否被用户授权
	 *
	 * @param roleid
	 *            角色
	 * @return
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public HdMessageCode remove(String roleIds) {
		authRoleService.remove(roleIds);
		return HdUtils.genMsg();
	}

	/**
	 * 保存角色与功能及资源的对应关系
	 *
	 * @param roleid
	 *            角色ID
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/updatePermission/{roleId}", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode updatePermission(@PathVariable String roleId,
			@RequestBody(required = false) String privilegeIds) {
		authRoleService.updatePermission(roleId, privilegeIds);
		return HdUtils.genMsg();
	}
//
//	/**
//	 * 保存用户与功能及资源的对应关系
//	 *
//	 * @param userId
//	 *            用户ID
//	 * @param data
//	 * @return
//	 */
//	@RequestMapping(value = "/updateUserPermission/{userId}", method = RequestMethod.POST)
//	@ResponseBody
//	public HdMessageCode updateUserPermission(@PathVariable String userId, @RequestBody String data) {
//		AuthUser user = (AuthUser) SecurityUtils.getSubject().getSession()
//				.getAttribute(PrivilegeController.SESSION_USER);
//		List<String> list = HdUtils.fromJson(data, List.class);
//		authRoleService.updateUserPermission(userId, list, user);
//		return CommonUtil.genMsg();
//	}

	/**
	 * 添加员工与角色的映射。
	 *
	 * @param roleid
	 *            角色Id
	 * @param userId
	 *            员工Id
	 * @return
	 */
	@RequestMapping(value = "/addRoleUser/{roleid}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public HdMessageCode addRoleUser(@PathVariable String roleid, @PathVariable String userId) {
		authRoleService.addRoleUser(roleid, userId);
		return HdUtils.genMsg();
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
	@RequestMapping(value = "/removeRoleUser/{roleid}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public HdMessageCode removeRoleUser(@PathVariable String roleid, @PathVariable String userId) {
		authRoleService.removeRoleUser(roleid, userId);
		return HdUtils.genMsg();
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findtreeforrole")
	@ResponseBody
	public List<EzTreeBean> findSelTree(String roleId) {
		return authPrivilegeRelService.findSelTreeForRole(roleId);
	}

	/**
	 * 根据角色查询可管理部门数据
	 * 
	 * @param roleId
	 *            : 角色id
	 * 
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findOrgnByRoleAndPrivilege")
	@ResponseBody
	public List<EzTreeBean> findOrgnByRoleAndPrivilege(String roleId, String privilegeId) {
		return authPrivilegeRelService.findOrgnByRoleAndPrivilege(roleId, privilegeId);
	}

	/**
	 * 根据角色Id查询对应功能的数据权限
	 *
	 * @param roleId
	 *            : 角色id
	 * @return
	 */
	@RequestMapping(value = "/findDataByRole", method = RequestMethod.POST)
	@ResponseBody
	public HdEzuiDatagridData findDataByRole(String roleId) {

		return authRoleService.findDataByRole(roleId);
	}

	@RequestMapping(value = "/updateRoleOrgn/{roleId}/{preId}", method = RequestMethod.POST)
	@ResponseBody
	public HdMessageCode updateRoleOrgn(@PathVariable String roleId, @PathVariable String preId,
			@RequestBody(required = false) String ids) {
		AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getSession()
				.getAttribute(PrivilegeController.SESSION_USER);

		List<String> list = HdUtils.paraseStrs(ids);//部门id

		// List list = HdUtils.fromJson(data, List.class);
		authRoleService.updateRoleOrgn(roleId, preId, list, authUser);
		return HdUtils.genMsg();
	}

	/**
	 * 根据角色Id查询对应功能的数据权限
	 *
	 * @param roleId
	 *            : 角色id
	 * @return
	 */
	@RequestMapping(value = "/findOrgnTypeByRole", method = RequestMethod.POST)
	@ResponseBody
	public HdEzuiDatagridData findOrgnTypeByRole(String roleId) {

		return authRoleService.findOrgnTypeByRole(roleId);
	}
}
