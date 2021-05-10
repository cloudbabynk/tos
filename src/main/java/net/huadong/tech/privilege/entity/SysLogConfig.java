package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.UuidGenerator;

import net.huadong.tech.base.bean.AuditEntityBean;


@Entity
@Table(name = "SYS_LOG_CONFIG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysLogConfig.findAll", query = "SELECT c FROM SysLogConfig c")
    , @NamedQuery(name = "SysLogConfig.findByLogId", query = "SELECT c FROM SysLogConfig c WHERE c.logId = :logId")
    , @NamedQuery(name = "SysLogConfig.findByEntyName", query = "SELECT c FROM SysLogConfig c WHERE c.entyName = :entyName")
    , @NamedQuery(name = "SysLogConfig.findByEntyCode", query = "SELECT c FROM SysLogConfig c WHERE c.entyCode = :entyCode")
    , @NamedQuery(name = "SysLogConfig.findByIsLog", query = "SELECT c FROM SysLogConfig c WHERE c.isLog = :isLog")
    , @NamedQuery(name = "SysLogConfig.findByRemark", query = "SELECT c FROM SysLogConfig c WHERE c.remark = :remark")
    , @NamedQuery(name = "SysLogConfig.findByRecNam", query = "SELECT c FROM SysLogConfig c WHERE c.recNam = :recNam")
    , @NamedQuery(name = "SysLogConfig.findByRecTim", query = "SELECT c FROM SysLogConfig c WHERE c.recTim = :recTim")
    , @NamedQuery(name = "SysLogConfig.findByUpdNam", query = "SELECT c FROM SysLogConfig c WHERE c.updNam = :updNam")
    , @NamedQuery(name = "SysLogConfig.findByUpdTim", query = "SELECT c FROM SysLogConfig c WHERE c.updTim = :updTim")})
public class SysLogConfig extends AuditEntityBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOG_ID")
    private String logId;
    @Column(name = "ENTY_NAME")
    private String entyName;
    @Column(name = "ENTY_CODE")
    private String entyCode;
    @Column(name = "IS_LOG")
    private String isLog;
    @Column(name = "REMARK")
    private String remark;


    public SysLogConfig() {
    }

    public SysLogConfig(String logId) {
        this.logId = logId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getEntyName() {
        return entyName;
    }

    public void setEntyName(String entyName) {
        this.entyName = entyName;
    }

    public String getEntyCode() {
        return entyCode;
    }

    public void setEntyCode(String entyCode) {
        this.entyCode = entyCode;
    }

    public String getIsLog() {
        return isLog;
    }

    public void setIsLog(String isLog) {
        this.isLog = isLog;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}