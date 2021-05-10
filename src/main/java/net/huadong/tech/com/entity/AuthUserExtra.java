package net.huadong.tech.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.privilege.entity.AuthOrgn;
import net.huadong.tech.util.HdUtils;
import org.eclipse.persistence.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "AUTH_USER")
public class AuthUserExtra extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String PWNEVERPASS = "1";
	public static final String PWRPASS = "0";
	public static final String LOCK = "1";
	public static final String UNLOCK = "0";
	public static final String STOP = "1";
	public static final String DONOTSTOP = "0";
	public static final String NEXTCHG = "1";
	public static final String DONOTNEXTCHG = "0";
	public static final String DONOTCHG = "1";
	public static final String DOCHG = "0";
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 36)
	@Column(name = "USER_ID")
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
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
	private boolean admin;
	@Transient
	private String unitId;
	@Transient
	private List<String> roleIdLs;
	@Transient
	private List<String> roleNameLs;
	@Transient
	private String orgnName;
	@Transient
	private String ip;
	@Transient
	private String browser;
	@Transient
	private String por;
	@Column(name = "TOKEN")
	private String token;
	
	@Transient
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Timestamp getExpTimeStamp() {
		return this.expTimeStamp;
	}

	public AuthUserExtra() {
		this.skin = "default";
	}

	public AuthUserExtra(String userId) {
		this.userId = userId;
	}

	public AuthUserExtra(String userId, String account) {
		this.userId = userId;
		this.account = account;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		if ("".equals(userId)) {
			userId = null;
		}

		this.userId = userId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLanguage() {
		return HdUtils.strIsNull(this.language) ? "zh" : this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Timestamp getAccountPassDate() {
		return this.accountPassDate;
	}

	public void setAccountPassDate(Timestamp accountPassDate) {
		this.accountPassDate = accountPassDate;
	}

	public String getLockId() {
		return this.lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getStopId() {
		return this.stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwNeverPassId() {
		return this.pwNeverPassId;
	}

	public void setPwNeverPassId(String pwNeverPassId) {
		this.pwNeverPassId = pwNeverPassId;
	}

	public Short getPwValidDays() {
		return this.pwValidDays;
	}

	public void setPwValidDays(Short pwValidDays) {
		this.pwValidDays = pwValidDays;
	}

	public Timestamp getPwModifyTim() {
		return this.pwModifyTim;
	}

	public void setPwModifyTim(Timestamp pwModifyTim) {
		this.pwModifyTim = pwModifyTim;
	}

	public String getDonotChgId() {
		return this.donotChgId;
	}

	public void setDonotChgId(String donotChgId) {
		this.donotChgId = donotChgId;
	}

	public String getNextChgId() {
		return this.nextChgId;
	}

	public void setNextChgId(String nextChgId) {
		this.nextChgId = nextChgId;
	}

	public String getSkin() {
		return this.skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompanyCodNam(String companyCodNam) {
		this.companyCodNam = companyCodNam;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrgnId() {
		return this.orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	@JsonIgnore
	public AuthOrgn getAuthOrgn() {
		return this.authOrgn;
	}

	public void setAuthOrgn(AuthOrgn authOrgn) {
		this.authOrgn = authOrgn;
	}

	public String getCompanyCodNam() {
		return this.companyCodNam;
	}

	public void setExpTimeStamp(Timestamp expTimeStamp) {
		this.expTimeStamp = expTimeStamp;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@JsonIgnore
	public List<String> getRoleIdLs() {
		return this.roleIdLs;
	}

	public void setRoleIdLs(List<String> roleIdLs) {
		this.roleIdLs = roleIdLs;
	}

	@JsonIgnore
	public List<String> getRoleNameLs() {
		return this.roleNameLs;
	}

	public void setRoleNameLs(List<String> roleNameLs) {
		this.roleNameLs = roleNameLs;
	}

	public String getOrgnName() {
		return this.orgnName;
	}

	public void setOrgnName(String orgnName) {
		this.orgnName = orgnName;
	}

	public String getPasswordTemp() {
		return this.passwordTemp;
	}

	public void setPasswordTemp(String passwordTemp) {
		this.passwordTemp = passwordTemp;
	}

	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
}