/*
 * Copyright (C) 2015 HUADONG SOFT-TECH CO.,LTD.
 * Author: xiaojn <xiaojn@huadong.net>
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.util.HdUtils;

/**
 * 系统管理-用户实体
 *
 * @author xiaojn
 * @version 1.0.0
 * @since 2015-3-27 10:06:00
 */
@Entity
@Table(name = "AUTH_USER")
public class AuthUser extends AuditEntityBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String PWNEVERPASS="1";
	public static final String PWRPASS="0";
	public static final String LOCK="1";
	public static final String UNLOCK="0";
	public static final String STOP="1";
	public static final String DONOTSTOP="0";
	public static final String NEXTCHG="1";
	public static final String DONOTNEXTCHG="0";
	public static final String DONOTCHG="1";
	public static final String DOCHG="0";
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 36)
	@Column(name = "USER_ID")
	private String userId;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 20)
	@Column(name = "ACCOUNT")
	private String account;
	@Column(name = "ACCOUNT_PASS_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp accountPassDate;
	@Size(max = 1)
	@Column(name = "LOCK_ID")
	private String lockId;
	@Size(max = 1)
	@Column(name = "STOP_ID")
	private String stopId;
	@Size(max = 20)
	@Column(name = "NAME")
	private String name;
	@Size(max = 10)
	@Column(name = "LANGUAGE")
	private String language;
	// @JsonIgnore//从前台向后台或反之，都会ignore
	@Size(max = 32)
	@Column(name = "PASSWORD")
	private String password;
	@Transient
	private String passwordTemp;
	
	
	@Size(max = 1)
	@Column(name = "PW_NEVER_PASS_ID")
	private String pwNeverPassId;
	@Column(name = "PW_VALID_DAYS")
	private Short pwValidDays;
	@Column(name = "PW_MODIFY_TIM")

	private Timestamp pwModifyTim;
	@Size(max = 1)
	@Column(name = "DONOT_CHG_ID")
	private String donotChgId;
	@Size(max = 1)
	@Column(name = "NEXT_CHG_ID")
	private String nextChgId;
	@Size(max = 20)
	@Column(name = "SKIN")
	private String skin;

	@Size(max = 255)
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "COMPANY_COD")
	private String companyCod;

	@Column(name = "ORGN_ID")
	private String orgnId;

	@Column(name = "EXP_TIMESTAMP")
	private Timestamp expTimeStamp;
	@Column(name = "DEL_FLG")
	private String delFlg;
	
	@Transient
	private String companyCodNam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGN_ID", insertable = false, updatable = false)
	private AuthOrgn authOrgn;

	@Transient
	private boolean admin;// 1是管理员0不是
	@Transient
	private String unitId;// 所在单位id(集团部室)
	@Transient
	private List<String> roleIdLs;// 拥有的角色
	@Transient
	private String orgnName;// 部门名称
	// @ManyToMany
	// @JoinTable(name = "AUTH_USER_ROLE", joinColumns = @JoinColumn(name =
	// "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns =
	// @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
	// protected Set<AuthRole> roles;

	@Transient
	private String ip;
	@Transient
	private String browser;
	
	@Transient
	private String por;

	@Column(name = "TOKEN")
	private String token;

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Timestamp getExpTimeStamp() {
		return expTimeStamp;
	}

	/**
	 * 构造方法
	 */
	public AuthUser() {
		this.skin = "default";
	}

	/**
	 * 构造方法
	 *
	 * @param userId
	 *            用户编码（主键）
	 */
	public AuthUser(String userId) {
		this.userId = userId;
	}

	/**
	 * 构造方法
	 *
	 * @param userId
	 *            用户编码（主键）
	 * @param account
	 *            用户账户（登录名）
	 */
	public AuthUser(String userId, String account) {
		this.userId = userId;
		this.account = account;
	}

	/**
	 * 获取用户编码
	 *
	 * @return 用户编码
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户编码
	 *
	 * @param userId
	 *            用户编码
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户账户
	 *
	 * @return 用户账户
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置用户账户
	 *
	 * @param account
	 *            用户账户
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取用户语言
	 *
	 * @return 用户账户
	 */
	public String getLanguage() {//wxl9.29 默认语言
		if(HdUtils.strIsNull(language)){
			return "zh";
		}
		return language;
	}

	/**
	 * 设置用户语言
	 *
	 * @param language
	 *            用户账户
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 获取用户密码有效期
	 *
	 * @return 用户密码有效期
	 */
	// @JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getAccountPassDate() {
		return accountPassDate;
	}

	/**
	 * 设置用户密码有效期
	 *
	 * @param accountPassDate
	 *            用户密码有效期
	 */
	public void setAccountPassDate(Timestamp accountPassDate) {
		this.accountPassDate = accountPassDate;
	}

	/**
	 * 获取锁定标记
	 *
	 * @return 锁定标记
	 */
	public String getLockId() {
		return lockId;
	}

	/**
	 * 设置锁定标记
	 *
	 * @param lockId
	 *            锁定标记
	 */
	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	/**
	 * 获取停用标记
	 *
	 * @return 停用标记
	 */
	public String getStopId() {
		return stopId;
	}

	/**
	 * 设置停用标记
	 *
	 * @param stopId
	 *            停用标记
	 */
	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	/**
	 * 获取姓名
	 *
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 *
	 * @param name
	 *            姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取密码
	 *
	 * @return 密码
	 */
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取密码永不过期标记
	 *
	 * @return 密码永不过期标记
	 */
	public String getPwNeverPassId() {
		return pwNeverPassId;
	}

	/**
	 * 设置密码永不过期标记
	 *
	 * @param pwNeverPassId
	 *            密码永不过期标记
	 */
	public void setPwNeverPassId(String pwNeverPassId) {
		this.pwNeverPassId = pwNeverPassId;
	}

	/**
	 * 获取密码有效天数
	 *
	 * @return 密码有效天数
	 */
	public Short getPwValidDays() {
		return pwValidDays;
	}

	/**
	 * 设置密码有效天数
	 *
	 * @param pwValidDays
	 *            密码有效天数
	 */
	public void setPwValidDays(Short pwValidDays) {
		this.pwValidDays = pwValidDays;
	}

	/**
	 * 获取密码修改时间
	 *
	 * @return 密码修改时间
	 */
	public Timestamp getPwModifyTim() {
		return pwModifyTim;
	}

	/**
	 * 设置密码修改时间
	 *
	 * @param pwModifyTim
	 *            密码修改时间
	 */
	public void setPwModifyTim(Timestamp pwModifyTim) {
		this.pwModifyTim = pwModifyTim;
	}

	/**
	 * 获取用户修改密码权限
	 *
	 * @return 用户修改密码权限
	 */
	public String getDonotChgId() {
		return donotChgId;
	}

	/**
	 * 设置用户修改密码权限
	 *
	 * @param donotChgId
	 *            用户修改密码权限
	 */
	public void setDonotChgId(String donotChgId) {
		this.donotChgId = donotChgId;
	}

	/**
	 * 获取用户必须修改密码标记
	 *
	 * @return 用户必须修改密码标记
	 */
	public String getNextChgId() {
		return nextChgId;
	}

	/**
	 * 设置用户必须修改密码标记
	 *
	 * @param nextChgId
	 *            用户必须修改密码标记
	 */
	public void setNextChgId(String nextChgId) {
		this.nextChgId = nextChgId;
	}

	/**
	 * 获取用户界面皮肤
	 *
	 * @return 用户界面皮肤代码
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置用户界面皮肤
	 *
	 * @param skin
	 *            用户界面皮肤代码
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取用户描述
	 *
	 * @return 用户描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置用户描述
	 *
	 * @param description
	 *            用户描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取用户所属公司
	 *
	 * @return
	 */
	public String getCompanyCod() {
		return companyCod;
	}

	public void setCompanyCod(String companyCod) {
		this.companyCod = companyCod;
	}

	// public String getCompanyCodNam() {
	// if(this.vwGroupOrgn!=null)
	// return this.vwGroupOrgn.getCompanyNam();
	// else
	// return "";
	// }

	public void setCompanyCodNam(String companyCodNam) {
		this.companyCodNam = companyCodNam;
	}

	/**
	 * 获取用户IP
	 *
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置用户IP
	 *
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrgnId() {
		return orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	@JsonIgnore
	public AuthOrgn getAuthOrgn() {
		return authOrgn;
	}

	public void setAuthOrgn(AuthOrgn authOrgn) {
		this.authOrgn = authOrgn;
	}

	public String getCompanyCodNam() {
		return companyCodNam;
	}

	public void setExpTimeStamp(Timestamp expTimeStamp) {
		this.expTimeStamp = expTimeStamp;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@JsonIgnore
	public List<String> getRoleIdLs() {
		return roleIdLs;
	}

	public void setRoleIdLs(List<String> roleIdLs) {
		this.roleIdLs = roleIdLs;
	}

	public String getOrgnName() {
		return orgnName;
	}

	public void setOrgnName(String orgnName) {
		this.orgnName = orgnName;
	}

	public String getPasswordTemp() {
		return passwordTemp;
	}

	public void setPasswordTemp(String passwordTemp) {
		this.passwordTemp = passwordTemp;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	
}
