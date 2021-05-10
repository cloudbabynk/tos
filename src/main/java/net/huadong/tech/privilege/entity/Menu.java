/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author jasoncai
 */
@Entity
@Table(name = "VJ_PRIVILEGE")
@Customizer(MenuCustomizer.class)
@XmlRootElement
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ID")
    @Id
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ACCOUNT")
    private String account;
    @Size(max = 20)
    @Column(name = "UPD_ACCOUNT")
    private String updAccount;
    @Column(name = "UPD_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updTimestamp;
    @Size(max = 20)
    @Column(name = "INS_ACCOUNT")
    private String insAccount;
    @Column(name = "INS_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insTimestamp;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 36)
    @Column(name = "PARENT_ID")
    private String parentId;
    @Size(max = 20)
    @Column(name = "TEXT")
    private String text;
    @Size(max = 255)
    @Column(name = "URL")
    private String url;
    @Column(name = "SORTER")
    private BigInteger sorter;
    @Size(max = 20)
    @Column(name = "STATE")
    private String state;
    @Size(max = 20)
    @Column(name = "ICON_CLS")
    private String iconCls;
        @Size(max = 1)
    @Column(name = "OPEN_TYPE")
    private String openType;

    public Menu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUpdAccount() {
        return updAccount;
    }

    public void setUpdAccount(String updAccount) {
        this.updAccount = updAccount;
    }

    public Date getUpdTimestamp() {
        return updTimestamp;
    }

    public void setUpdTimestamp(Date updTimestamp) {
        this.updTimestamp = updTimestamp;
    }

    public String getInsAccount() {
        return insAccount;
    }

    public void setInsAccount(String insAccount) {
        this.insAccount = insAccount;
    }

    public Date getInsTimestamp() {
        return insTimestamp;
    }

    public void setInsTimestamp(Date insTimestamp) {
        this.insTimestamp = insTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigInteger getSorter() {
        return sorter;
    }

    public void setSorter(BigInteger sorter) {
        this.sorter = sorter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }


// add by jason @ 2014-03-06
/*    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OrderBy("sorter asc")
    private Collection<Menu> children;*/
    
    
    
    @Transient
    private Attributes attributes;

/*    public Collection<Menu> getChildren() {
        return children;
    }

    public void setChildren(Collection<Menu> children) {
        this.children = children;
    }
*/
    public Attributes getAttributes() {
        if (attributes == null) {
            attributes = new Attributes();
        }
        attributes.setUrl(this.getUrl());
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
        this.setUrl(attributes.getUrl());
    }
}

class Attributes {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
