package net.huadong.tech.privilege.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import com.alibaba.dubbo.common.utils.StringUtils;
import io.swagger.annotations.Api;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.service.AuthUserService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiQueryParams;
import net.huadong.tech.springboot.core.utils.HdCipher;
import net.huadong.tech.springboot.core.utils.HdEzuiExportFile;
import net.huadong.tech.util.HdUtils;

/**
 * 系统管理-用户管理Controller
 * 
 * @author dengmj wxl 2017.7.10修改
 * @author zhangk 2017.10.10修改
 */
@Api(value = "API - AuthUserController")
@Controller
@RequestMapping("webresources/login/privilege/AuthUser")
public class AuthUserController {
	@Autowired
	AuthUserService authUserService;
	public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";


	@RequestMapping(value = "/user.htm")
	public String privilege(String userId) {
		return "system/privilege/user";
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findone", method = RequestMethod.POST)
	@ResponseBody
	public AuthUser findone(String userId, String orgnId) {
		if (HdUtils.strIsNull(userId)) {//增加时默认初值
			AuthUser auth = new AuthUser();
			auth.setOrgnId(orgnId);
			auth.setLockId("0");
			auth.setStopId("0");
			auth.setNextChgId("0");
			auth.setDonotChgId("0");
			auth.setSkin("metro-blue");
			auth.setPasswordTemp("Roro@1234.com");
			auth.setPwNeverPassId(AuthUser.PWNEVERPASS);
			return auth;
		}
		return authUserService.find(userId);
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public HdMessageCode remove(String userId) {
		return authUserService.remove(userId);
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData ezuiFind(@RequestBody HdQuery params) {
		HdEzuiDatagridData hdData = authUserService.findcode(params);
		return hdData;
	}

	/**
	 * 查询当前用户选择的语言
	 * 
	 * @param params
	 *            参数
	 * @return 查询结果
	 */
	@RequestMapping(value = "/findLanguage", method = RequestMethod.GET)
	@ResponseBody
	public String findLanguage(HdQuery params) {
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		AuthUser user = (AuthUser) SecurityUtils.getSubject().getSession()
				.getAttribute(PrivilegeController.SESSION_USER);
		String data = user.getLanguage();
		return data;
	}

	/**
	 * 保存用户选择的语言
	 * 
	 * @param params
	 *            参数
	 * @return 结果
	 */
	@RequestMapping(value = "/saveLanguage", method = RequestMethod.POST)
	@ResponseBody
	public Response saveLanguage(@RequestParam(value = "language") String language) {
		String n = "";
		switch (language) {
		case "zh":
			n = "en";
			break;
		case "en":
			n = "zh";
			break;
		default:
			n = "en";
			break;
		}
		AuthUser user = (AuthUser) SecurityUtils.getSubject().getSession()
				.getAttribute(PrivilegeController.SESSION_USER);

		try {
			authUserService.updateUserLanguage(user.getUserId(), n);
			if ("en".equals(n)) {
				HdUtils.getHttpSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						Locale.ENGLISH);
			} else {
				HdUtils.getHttpSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						Locale.CHINESE);
			}
			user.setLanguage(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * 验证用户名是否已存在 测试通过
	// *
	// * @param account
	// * @return
	// */
	// @RequestMapping(value = "/validateAccount", method = RequestMethod.GET)
	// @ResponseBody
	// public Response validateAccount(@RequestParam(value = "account") String
	// account) {
	// AuthUser user = authUserService.authUserFindByAccount(account);
	// if (user != null && StringUtils.isNotEmpty(user.getUserId())) {
	// return Response.ok("error").build();
	// } else {
	// return Response.ok().build();
	// }
	// }

	// // 序列化问题
	// @RequestMapping(value = "/findry", method = RequestMethod.POST)
	// @ResponseBody
	// public Response findry(@RequestBody HdEzuiQueryParams params) {
	// return authUserService.find(params);
	// }

	// /**
	// * 验证用户是否可以删除 测试通过
	// *
	// * @param userId
	// * @return
	// */
	// @RequestMapping(value = "/validateDelete", method = RequestMethod.GET)
	// @ResponseBody
	// public Response validateDelete(@RequestParam(value = "userId") String
	// userId) {
	// String s = authUserService.authUserValidateDelete(userId);
	// if (s == null) {
	// return Response.ok().build();
	// } else {
	// return Response.ok(s).build();
	// }
	// }

	/**
	 * 修改个人密码 测试通过
	 * 
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return 修改结果
	 */
	@RequestMapping(value = "/modifyMyPassword", method = RequestMethod.GET)
	@ResponseBody
	public HdMessageCode modifyMyPassword(@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword) {
		AuthUser sessionuser = HdUtils.getCurUser();
		AuthUser user = authUserService.find(sessionuser.getUserId());
		String oldEnPwd = HdCipher.getMD(oldPassword, "MD5");
		if (!newPassword.matches(this.PW_PATTERN)){
			throw new HdRunTimeException("密码设定规则，长度大于等于8位，必须包含大写字母、小写字母、数字、字符，请确认！");
	    }
		String newEnPwd = HdCipher.getMD(newPassword, "MD5");
		if (user.getPassword().toUpperCase().equals(oldEnPwd.toUpperCase())) {
			int i = this.updatePassword(user.getUserId(), newEnPwd);
			if (i == 1) {
				sessionuser.setPassword(newEnPwd);// 更新Session中的密码
				sessionuser.setPasswordTemp(newPassword);
				return HdUtils.genMsg();
			} else {
				throw new HdRunTimeException("修改失败");
			}
		} else {
			throw new HdRunTimeException("旧密码错误");
		}
	}
	
	@RequestMapping(value = "/valuePassword", method = RequestMethod.GET)
	@ResponseBody
	public HdMessageCode valuePassword() {
		HdMessageCode message=HdUtils.genMsg();
		AuthUser sessionuser = HdUtils.getCurUser();
		if (!sessionuser.getPasswordTemp().matches(this.PW_PATTERN)){
			message.setMessage("0");
	    }else {
	    	message.setMessage("1");
	    }
		return message;
	}

	/**
	 * 重置密码（for系统管理员） 测试 通过
	 * 
	 * @param userId
	 *            用户编码
	 * @param password
	 *            重置密码
	 * @return 修改结果
	 */
	@RequestMapping(value = "/resetPassword")
	@ResponseBody
	public HdMessageCode resetPassword(String userId) {
		String password = HdCipher.getMD("Roro@1234.com", "MD5");
		int i = this.updatePassword(userId, password);
		HdMessageCode result = new HdMessageCode();
		result.setCode("1");
		result.setMessage("保存成功！");
		return result;
	}

	/**
	 * 修改密码
	 *
	 * @param userId
	 *            用户编码
	 * @param password
	 *            新密码
	 * @return
	 */
	private int updatePassword(String userId, String password) {
		return authUserService.authUserUpdatePassword(userId, password);
	}

	// /**
	// * 调动用户（修改所属租户/组织） 测试 通过
	// *
	// * @param userId
	// * @param tenantId
	// * @param orgnId
	// * @return
	// */
	// @RequestMapping(value = "/move", method = RequestMethod.GET)
	// @ResponseBody
	// public Response modifyMyPassword(@RequestParam(value = "userId") String
	// userId,
	// @RequestParam(value = "tenantId") String tenantId, @RequestParam(value =
	// "orgnId") String orgnId) {
	// int i = authUserService.authUserMove(userId, tenantId, orgnId);
	// if (i == 1) {
	// return Response.ok().build();
	// } else {
	// return Response.ok("修改失败").build();
	// }
	// }

	/**
	 * 保存
	 *
	 * @param data
	 *            数据
	 * @return 执行结果
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody AuthUser authUser) {
		return authUserService.ezuiSave(authUser);
	}

	/*
	 * @RequestMapping(value = "save", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public HdMessageCode save(@RequestBody
	 * HdEzuiSaveDatagridData<AuthUser> data) { return
	 * authUserService.ezuiSave(data); }
	 */
	/**
	 * 导出Excel
	 *
	 * @param q
	 * @param sort
	 * @param order
	 * @param queryColumns
	 * @param showColumns
	 * @param hideColumns
	 * @param hdConditions
	 * @param others
	 * @return
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(String q, String sort, String order, String queryColumns, String showColumns,
			String hideColumns, String hdConditions, String others, HttpServletResponse response) {
		HdQuery params = new HdQuery(null, null, q == null ? null : q, sort, order, queryColumns, showColumns,
				hideColumns, hdConditions == null ? null : hdConditions, others == null ? null : others);

		HdEzuiDatagridData data = this.ezuiFind(params);
		HdEzuiExportFile.exportExcelEx(params.getHdConditions().getHdExportExcel(), response, data);

	}

	/**
	 * 换肤
	 * 
	 * @param stylecss
	 *            皮肤代码
	 * @return 执行结果
	 */
	@RequestMapping(value = "/updateUserStyleCss/{stylecss}", method = RequestMethod.GET)
	@ResponseBody
	public Response updateUserStyleCsss(@PathParam("stylecss") String stylecss) {
		AuthUser user = HdUtils.getCurUser();
		authUserService.authUserUpdateUserStyleCss(user.getUserId(), stylecss);
		user.setSkin(stylecss);//session也需要变
		SecurityUtils.getSubject().getSession().setAttribute(PrivilegeController.SESSION_USER, user);
		return Response.ok().build();
	}
}
