/*
 * Copyright (C) 2015 HUADONG SOFT-TECH CO.,LTD.
 * Author: xiaojn <xiaojn@huadong.net>
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 * 系统管理-系统参数实体
 *
 * @author xiaojn
 * @version 1.0.0
 * @since 2015-3-27 10:39:00
 */
@Entity
@Table(name = "CT_SYS_PARAMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysParams.findAll", query = "SELECT s FROM SysParams s"),
    @NamedQuery(name = "SysParams.findByKey", query = "SELECT s FROM SysParams s WHERE s.key = :key"),
    @NamedQuery(name = "SysParams.findByKeyAndType", query = "SELECT s FROM SysParams s WHERE s.key = :key AND s.type = :type"),
    @NamedQuery(name = "SysParams.findByValue", query = "SELECT s FROM SysParams s WHERE s.value = :value"),
    @NamedQuery(name = "SysParams.findByType", query = "SELECT s FROM SysParams s WHERE s.type = :type"),
    @NamedQuery(name = "SysParams.findByParamsId", query = "SELECT s FROM SysParams s WHERE s.paramsId = :paramsId")})
public class SysParams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PARAMS_ID")
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    private String paramsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "KEY_NAME")
    private String key;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "VALUE")
    private String value;

    /**
     * 构造方法
     */
    public SysParams() {
    }

    /**
     * 构造方法
     *
     * @param paramsId 主键
     */
    public SysParams(String paramsId) {
        this.paramsId = paramsId;
    }

    /**
     * 构造方法
     *
     * @param paramsId 主键
     * @param key 项
     * @param type 类型
     * @param value 值
     */
    public SysParams(String paramsId, String key, String type, String value) {
        this.paramsId = paramsId;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    /**
     * 获取主键
     *
     * @return 主键
     */
    public String getParamsId() {
        return paramsId;
    }

    /**
     * 设置主键
     *
     * @param paramsId 主键
     */
    public void setParamsId(String paramsId) {
        this.paramsId = paramsId;
    }

    /**
     * 获取项
     *
     * @return 项
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置项
     *
     * @param key 项
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取值
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取类型
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取对象哈希码
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramsId != null ? paramsId.hashCode() : 0);
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
        if (!(object instanceof SysParams)) {
            return false;
        }
        SysParams other = (SysParams) object;
        return this.paramsId.equals(other.paramsId);
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.SysParams[ paramsId=" + paramsId + " ]";
    }

}
