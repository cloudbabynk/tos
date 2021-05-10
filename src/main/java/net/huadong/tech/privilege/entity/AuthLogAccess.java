/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "AUTH_LOG_ACCESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthLogAccess.findAll", query = "SELECT a FROM AuthLogAccess a"),
    @NamedQuery(name = "AuthLogAccess.findByAccessId", query = "SELECT a FROM AuthLogAccess a WHERE a.accessId = :accessId"),
    @NamedQuery(name = "AuthLogAccess.findByUserId", query = "SELECT a FROM AuthLogAccess a WHERE a.user.userId = :userId"),
    @NamedQuery(name = "AuthLogAccess.findByIp", query = "SELECT a FROM AuthLogAccess a WHERE a.ip = :ip"),
    @NamedQuery(name = "AuthLogAccess.findByBrowser", query = "SELECT a FROM AuthLogAccess a WHERE a.browser = :browser"),
    @NamedQuery(name = "AuthLogAccess.findByFunction", query = "SELECT a FROM AuthLogAccess a WHERE a.function = :function"),
    @NamedQuery(name = "AuthLogAccess.findByUrlId", query = "SELECT a FROM AuthLogAccess a WHERE a.urlId = :urlId")})
public class AuthLogAccess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ACCESS_ID")
    private String accessId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "USER_ID")
    private String userId;
    @Transient
    private String userName;
    @JsonIgnore
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @ManyToOne
    private AuthUser user;
    @Size(max = 20)
    @Column(name = "IP")
    private String ip;
    @Size(max = 4000)
    @Column(name = "BROWSER")
    private String browser;
    @Size(max = 40)
    @Column(name = "FUNCTION")
    private String function;
    @Size(max = 200)
    @Column(name = "URL_ID")
    private String urlId;
    @Column(name = "REC_TIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recTim;
    @Transient
    private String resourceName;
    @JsonIgnore
    @JoinColumn(name = "URL_ID", referencedColumnName = "PERMISSIONS", insertable = false, updatable = false)
    @ManyToOne
    private AuthResource resource;
     public AuthResource getResource() {
        return resource;
    }

    public void setResource(AuthResource resource) {
        this.resource = resource;
    }
    public AuthUser getUser() {
        return user;
    }
    
    public void setUser(AuthUser user) {
        this.user = user;
    }
    
    public String getUserName() {
        return this.user.getName();  
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getResourceName() {
        if(this.resource!=null)
            return this.resource.getDescription();
        else
            return "";
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public AuthLogAccess() {
    }

    public AuthLogAccess(String accessId) {
        this.accessId = accessId;
    }

    public AuthLogAccess(String accessId, String userId) {
        this.accessId = accessId;
//        this.userId = userId;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessId != null ? accessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthLogAccess)) {
            return false;
        } 
        AuthLogAccess other = (AuthLogAccess) object;
        if ((this.accessId == null && other.accessId != null) || (this.accessId != null && !this.accessId.equals(other.accessId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "net.huadong.idev.tpl.privilege.entity.AuthLogAccess[ accessId=" + accessId + " ]";
    }
    public Date getRecTim() {
        return recTim;
    }

    public void setRecTim(Date recTim) {
        this.recTim = recTim;
    }
    
}
