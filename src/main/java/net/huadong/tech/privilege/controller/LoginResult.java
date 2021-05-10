package net.huadong.tech.privilege.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录结果类
 *
 * @author xiaojn
 */
public class LoginResult {

    private boolean success;//true时视为可以登录
    private String messageCode;//与message一一对应，为便于识别
    private String message;//null时认为正常登录

    private LoginResult() {
        this.success = true;
        this.messageCode = SUCCESS;
        this.message = null;
    }

    private LoginResult(boolean success, String messageCode, String message) {
        this.success = success;
        this.messageCode = messageCode;
        this.message = message;
    }

    public static final LoginResult getInstance(String messageCode) {
        return msg.get(messageCode);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static final String SUCCESS = "success";
    public static final String WRONG_VALIDATE_CODE = "wrongValidateCode";
    public static final String UNKNOW_ACCOUNT = "unknowUser";
    public static final String WRONG_PASSWORD = "wrongPassword";
    public static final String ACCOUNT_OVERDUE = "accountOverdue";
    public static final String ACCOUNT_STOPED = "accountStoped";
    public static final String ACCOUNT_LOCKED = "accountLocked";
    public static final String PASSWORD_OVERDUE = "passwordOverdue";
    public static final String PASSWORD_MODIFY = "passwordModify";
    public static final String PASSWORD_ILLEGAL = "passwordIllegal";

    private static final LoginResult OBJ_SUCCESS = new LoginResult();
    private static final LoginResult OBJ_WRONG_VCODE = new LoginResult(false, WRONG_VALIDATE_CODE, "验证码错误！");
    private static final LoginResult OBJ_UNKNOW_ACCOUNT = new LoginResult(false, UNKNOW_ACCOUNT, "账户不存在，请检查登录名！<br/>注意：用户名区分大小写");
    private static final LoginResult OBJ_WRONG_PASSWORD = new LoginResult(false, WRONG_PASSWORD, "密码错误，请检查！<br/>注意：密码区分大小写");
    private static final LoginResult OBJ_ACCOUNT_OVERDUE = new LoginResult(false, ACCOUNT_OVERDUE, "账户已过期！");
    private static final LoginResult OBJ_ACCOUNT_STOPED = new LoginResult(false, ACCOUNT_STOPED, "账户已停用！");
    private static final LoginResult OBJ_ACCOUNT_LOCKED = new LoginResult(false, ACCOUNT_LOCKED, "账户已锁定！");
    private static final LoginResult OBJ_PASSWORD_OVERDUE = new LoginResult(true, PASSWORD_OVERDUE, "密码过期，请及时修改！");
    private static final LoginResult OBJ_PASSWORD_MODIFY = new LoginResult(true, PASSWORD_MODIFY, "系统管理员要求修改密码。<br/>请及时修改！");
    private static final LoginResult OBJ_PASSWORD_ILLEGAL = new LoginResult(true, PASSWORD_ILLEGAL, "密码设定规则，长度大于等于8位，必须包含大写字母、小写字母、数字、字符，请及时修改！");

    private static final Map<String, LoginResult> msg = new HashMap<>();

    static {
        msg.put(SUCCESS, OBJ_SUCCESS);
        msg.put(WRONG_VALIDATE_CODE, OBJ_WRONG_VCODE);
        msg.put(UNKNOW_ACCOUNT, OBJ_UNKNOW_ACCOUNT);
        msg.put(WRONG_PASSWORD, OBJ_WRONG_PASSWORD);
        msg.put(ACCOUNT_OVERDUE, OBJ_ACCOUNT_OVERDUE);
        msg.put(ACCOUNT_STOPED, OBJ_ACCOUNT_STOPED);
        msg.put(ACCOUNT_LOCKED, OBJ_ACCOUNT_LOCKED);
        msg.put(PASSWORD_OVERDUE, OBJ_PASSWORD_OVERDUE);
        msg.put(PASSWORD_MODIFY, OBJ_PASSWORD_MODIFY);
        msg.put(PASSWORD_ILLEGAL, OBJ_PASSWORD_ILLEGAL);
    }
}
