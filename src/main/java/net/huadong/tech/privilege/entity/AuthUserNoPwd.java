/*
 * Copyright (C) 2015 HUADONG SOFT-TECH CO.,LTD.
 * Author: xiaojn <xiaojn@huadong.net>
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.UuidGenerator;

/**
 * 系统管理-用户实体（不带密码保存时使用的）
 *
 * @author xiaojn
 * @version 1.0.0
 * @since 2015-3-31 11:20:00
 */
@Entity
@Table(name = "AUTH_USER")
@XmlRootElement
public class AuthUserNoPwd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Column(name = "USER_ID")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ACCOUNT")
    private String account;
    @Column(name = "ACCOUNT_PASS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountPassDate;
    @Size(max = 1)
    @Column(name = "LOCK_ID")
    private String lockId;
    @Size(max = 1)
    @Column(name = "STOP_ID")
    private String stopId;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @Size(max = 1)
    @Column(name = "PW_NEVER_PASS_ID")
    private String pwNeverPassId;
    @Column(name = "PW_VALID_DAYS")
    private Short pwValidDays;
    @Column(name = "PW_MODIFY_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwModifyTim;
    @Size(max = 1)
    @Column(name = "DONOT_CHG_ID")
    private String donotChgId;
    @Size(max = 1)
    @Column(name = "NEXT_CHG_ID")
    private String nextChgId;
    @Size(max = 20)
    @Column(name = "SKIN")
    private String skin;
    @Size(max = 20)
    @Column(name = "REC_NAM")
    private String recNam;
    @Column(name = "REC_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recTim;
    @Size(max = 20)
    @Column(name = "UPD_NAM")
    private String updNam;
    @Column(name = "UPD_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updTim;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "COMPANY_COD")
    private String companyCod;
   
    @JoinColumn(name = "ORGN_ID", referencedColumnName = "ORGN_ID")
    @ManyToOne
    private AuthOrgn orgnId;
    @Transient
    private String ip;

    /**
     * 构造方法
     */
    public AuthUserNoPwd() {
        this.skin = "default";
    }

    /**
     * 构造方法
     *
     * @param userId 用户编码（主键）
     */
    public AuthUserNoPwd(String userId) {
        this.userId = userId;
    }

    /**
     * 构造方法
     *
     * @param userId 用户编码（主键）
     * @param account 用户账户（登录名）
     */
    public AuthUserNoPwd(String userId, String account) {
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
     * @param userId 用户编码
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
     * @param account 用户账户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取用户密码有效期
     *
     * @return 用户密码有效期
     */
    public Date getAccountPassDate() {
        return accountPassDate;
    }

    /**
     * 设置用户密码有效期
     *
     * @param accountPassDate 用户密码有效期
     */
    public void setAccountPassDate(Date accountPassDate) {
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
     * @param lockId 锁定标记
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
     * @param stopId 停用标记
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
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * @param pwNeverPassId 密码永不过期标记
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
     * @param pwValidDays 密码有效天数
     */
    public void setPwValidDays(Short pwValidDays) {
        this.pwValidDays = pwValidDays;
    }

    /**
     * 获取密码修改时间
     *
     * @return 密码修改时间
     */
    public Date getPwModifyTim() {
        return pwModifyTim;
    }

    /**
     * 设置密码修改时间
     *
     * @param pwModifyTim 密码修改时间
     */
    public void setPwModifyTim(Date pwModifyTim) {
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
     * @param donotChgId 用户修改密码权限
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
     * @param nextChgId 用户必须修改密码标记
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
     * @param skin 用户界面皮肤代码
     */
    public void setSkin(String skin) {
        this.skin = skin;
    }

   

    public String getRecNam() {
		return recNam;
	}

	public void setRecNam(String recNam) {
		this.recNam = recNam;
	}

	public Date getRecTim() {
		return recTim;
	}

	public void setRecTim(Date recTim) {
		this.recTim = recTim;
	}

	public String getUpdNam() {
		return updNam;
	}

	public void setUpdNam(String updNam) {
		this.updNam = updNam;
	}

	public Date getUpdTim() {
		return updTim;
	}

	public void setUpdTim(Date updTim) {
		this.updTim = updTim;
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
     * @param description 用户描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

   
    /**
     * 获取部门信息
     *
     * @return 部门信息
     */
    public AuthOrgn getOrgnId() {
        return orgnId;
    }

    /**
     * 设置部门信息
     *
     * @param orgnId 部门信息
     */
    public void setOrgnId(AuthOrgn orgnId) {
        this.orgnId = orgnId;
    }

    public String getCompanyCod() {
		return companyCod;
	}

	public void setCompanyCod(String companyCod) {
		this.companyCod = companyCod;
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

    /**
     * 获取对象哈希码
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    /**
     * 比较对象
     *
     * @param object 被比较对象
     * @return 比较结果是否相同
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AuthUserNoPwd)) {
            return false;
        }
        AuthUserNoPwd other = (AuthUserNoPwd) object;
        return this.userId.equals(other.userId);
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.AuthUser[ userId=" + userId + " ]";
    }

}
