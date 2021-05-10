package net.huadong.tech.privilege.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.huadong.idev.utils.HdImageCode;
import net.huadong.idev.utils.HdRandomCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.privilege.controller.LoginResult;
import net.huadong.tech.privilege.entity.AuthPrivilege;
import net.huadong.tech.privilege.entity.AuthUser;
import net.huadong.tech.privilege.service.AuthOrgnService;
import net.huadong.tech.privilege.service.AuthPrivilegeService;
import net.huadong.tech.privilege.service.AuthRoleService;
import net.huadong.tech.privilege.service.AuthUserService;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.utils.ServletOp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
public class PrivilegeControllerNew
{
  public static final String SESSION_PRIVILLEGE = "PrivilegeController";
  public static final String SESSION_USER = "user";
  public static final String SESSION_RANDOMCOD = "randomCod";
  public static final String CAN_VISIT = "canVisit";
  public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

  @Autowired
  AuthUserService authUserService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  AuthPrivilegeService authPrivilegeService;

  @Autowired
  AuthRoleService authRoleService;

  @Autowired
  AuthOrgnService authOrgnService;

  @ResponseBody
  @RequestMapping({"PrivilegeControllerNew/sessionReset"})
  public String getValidateCode(String noUse)
    throws Exception
  {
    return "";
  }

  @RequestMapping({"PrivilegeControllerNew/getValidateCode"})
  public void getValidateCode(HttpServletResponse response)
    throws Exception
  {
    String rCod = HdRandomCode.getStringCode(4);
    SecurityUtils.getSubject().getSession().setAttribute("randomCod", rCod);
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    ImageIO.write(HdImageCode.getImage(120, 40, 255, rCod), "JPEG", response.getOutputStream());
  }

  @RequestMapping(value={"login/PrivilegeControllerNew/getLoginAccount"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public HashMap getLoginAccount(HttpServletRequest req)
  {
    HashMap hashMap = new HashMap();
    AuthUser authUser = (AuthUser)SecurityUtils.getSubject().getSession()
      .getAttribute("user");
    hashMap.put("name", authUser.getName());
    hashMap.put("account", authUser.getAccount());
    hashMap.put("deptCod", authUser.getOrgnId());
    hashMap.put("skin", authUser.getSkin());
    hashMap.put("token", authUser.getToken());
    return hashMap;
  }

  @RequestMapping(value={"login/PrivilegeControllerNew/getLoginToken"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public HashMap getLoginToken(HttpServletRequest req, @RequestParam("name") String account) {
    HashMap hashMap = new HashMap();
    AuthUser authUser = this.authUserService.findByAccount(account);
    hashMap.put("token", authUser.getToken());
    return hashMap;
  }

  @RequestMapping(value={"login/PrivilegeControllerNew/getLoginUserId"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public String getLoginUserId(HttpServletRequest req) {
    AuthUser authUser = (AuthUser)SecurityUtils.getSubject().getSession()
      .getAttribute("user");
    return authUser.getUserId();
  }

  @RequestMapping(value={"PrivilegeControllerNew/isLogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public int isLogin(@RequestParam("name") String account, @RequestParam("password") String password, @RequestParam("validateCode") String validateCode, HttpServletRequest request)
  {
    int ret = -1;

    AuthUser user = this.authUserService.findByAccount(account);
    if (user != null) {
      ret = validateUser(user,password);
      if ((ret != 1) && (ret != 2) && (ret != 3)) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), password);
        token.setRememberMe(true);
        try {
          SecurityUtils.getSubject().login(token);
        }
        catch (UnknownAccountException localUnknownAccountException)
        {
        }
        catch (IncorrectCredentialsException localIncorrectCredentialsException)
        {
        }
        catch (LockedAccountException localLockedAccountException)
        {
        }
        catch (AuthenticationException ae)
        {
          ae.printStackTrace();
        }
        if ((ret == 0) || (ret == 4) || (ret == 5))
        {
          String ip = ServletOp.getRemoteHost(request);
          user.setIp(ip);
          user.setBrowser(request.getHeader("User-Agent"));
          init(user);
          SecurityUtils.getSubject().getSession().setAttribute("user", user);
        }
      }

    }

    return ret;
  }

  private void init(AuthUser user) {
    user.setRoleIdLs(this.authRoleService.findRoleIdByUserId(user.getUserId()));
    user.setAdmin(this.authRoleService.isAdmin(user.getUserId()));
    user.setUnitId(this.authOrgnService.findUnitId(user.getOrgnId()));
  }

  @RequestMapping(value={"PrivilegeControllerNew/login"}, method = RequestMethod.POST)
  @ResponseBody
  public LoginResult login(@RequestParam("name") String account,@RequestParam("password") String password,@RequestParam("validateCode")  String validateCode, HttpServletRequest request)
  {
    String vc = SecurityUtils.getSubject().getSession().getAttribute("randomCod")
      .toString();
    if ((validateCode == null) || (validateCode.trim().length() == 0) || 
      (!(validateCode.toUpperCase().equals(vc.toUpperCase())))) {
      return LoginResult.getInstance("wrongValidateCode");
    }

    AuthUser user = this.authUserService.findByAccount(account);
   
    if (user == null) {
      return LoginResult.getInstance("unknowUser");
    }

    UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), password);
    try
    {
      SecurityUtils.getSubject().login(token);
    } catch (Exception e) {
      e.printStackTrace();
      return LoginResult.getInstance("wrongPassword");
    }

    LoginResult result = null;
    int accountState = validateUser(user,password);
    switch (accountState)
    {
    case 0:
      result = LoginResult.getInstance("success");
      break;
    case 1:
      return LoginResult.getInstance("accountOverdue");
    case 2:
      return LoginResult.getInstance("accountLocked");
    case 3:
      return LoginResult.getInstance("accountStoped");
    case 4:
      result = LoginResult.getInstance("passwordOverdue");
      break;
    case 5:
      result = LoginResult.getInstance("passwordOverdue");
    case 6:
      return LoginResult.getInstance("passwordIllegal");
    }

    String ip = ServletOp.getRemoteHost(request);
    user.setIp(ip);
    user.setBrowser(request.getHeader("User-Agent"));
    user.setPasswordTemp(password);
    init(user);
    SecurityUtils.getSubject().getSession().setAttribute("user", user);
    if ("en".equals(user.getLanguage()))
      request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
    else {
      request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.CHINESE);
    }

    HdUtils.getHttpSession().setAttribute("curUserId", user.getUserId());
    return result;
  }

  private int validateUser(AuthUser user, String password)
  {
    Date currentDate = new Date();
    Date accountPassDate = user.getAccountPassDate();
    if ((accountPassDate != null) && (accountPassDate.before(currentDate))) {
      return 1;
    }
    if ("1".equals(user.getLockId())) {
      return 2;
    }
    if ("1".equals(user.getStopId())) {
      return 3;
    }
    if ((!("1".equals(user.getPwNeverPassId()))) && (user.getPwValidDays() != null)) {
      int days = user.getPwValidDays().shortValue();
      Date pwModifyDate = user.getPwModifyTim();
      int passDays = (int)(currentDate.getTime() - pwModifyDate.getTime()) / 86400000;

      if (passDays > days) {
        return 4;
      }
    }
    if ("1".equals(user.getNextChgId())) {
      return 5;
    }
	/*
	 * if (!password.matches(this.PW_PATTERN)){ return 6; }
	 */
    return 0;
  }

  @RequestMapping(value={"login/PrivilegeControllerNew/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public String logout(HttpServletRequest req, HttpServletResponse resp)
  {
    SecurityUtils.getSubject().logout();
    SecurityUtils.getSubject().getSession().removeAttribute("user");
    try
    {
      resp.setCharacterEncoding("UTF-8");
      PrintWriter out = resp.getWriter();
      out.println("<html>");
      out.println("<script>");
      out.println("window.open('" + req.getServletContext().getContextPath() + "/index.html','_top');");
      out.println("</script>");
      out.println("</html>");
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

/*  @RequestMapping(value={"login/PrivilegeControllerNew/findMenuAllForTree"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<EzTreeBean> findAllForTree()
  {
    return toOneLevel(this.authPrivilegeService.findAllForTree(HdUtils.getCurUser()));
  }

  private List<EzTreeBean> toOneLevel(List<EzTreeBean> treeBeanLs) {
    List result = new ArrayList();
    result.addAll(treeBeanLs);
    for (EzTreeBean beanOne : treeBeanLs)
    {
      for (EzTreeBean beanTwo : beanOne.getChildren()) {
        result.add(beanTwo);

        for (EzTreeBean beanThree : beanTwo.getChildren()) {
          result.add(beanThree);
        }
      }
    }

    for (EzTreeBean bean : result) {
      bean.setText(HdUtils.i18n(bean.getText()));
    }
    return result;
  }*/

  @RequestMapping(value={"login/PrivilegeControllerNew/getNodeById"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<AuthPrivilege> getNodeById(@RequestParam("privilegeId") String privilegeId, HttpServletRequest request)
  {
    return null;
  }
}