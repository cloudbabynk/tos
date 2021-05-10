package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
/**
 *
 * @author 孙璐
 * @date 2021-04-06
 */
@Entity
@Table(name="REPORT_TEU")
public class ReportTeu extends AuditEntityBean {
    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Id
    @Column(name = "TEU_ID")
    private String teuId;

    /**
     *日期
     */
    @Column(name = "TEU_TIM")
    private Timestamp teuTim;

    /**
     *日生产作业TEU
     */
    @Column(name = "TEU_DNUM")
    private BigDecimal teuDnum;

    /**
     *年生产作业量
     */
    @Column(name = "TEU_YNUM")
    private BigDecimal teuYnum;

    /**
     *月生产作业量
     */
    @Column(name = "TEU_MNUM")
    private BigDecimal teuMnum;


    public String getTeuId() {
        return this.teuId;
    }

    public void setTeuId(String teuId) {
        this.teuId = teuId;
    }

    public Timestamp getTeuTim() {
        return this.teuTim;
    }

    public void setTeuTim(Timestamp teuTim) {
        this.teuTim = teuTim;
    }

    public BigDecimal getTeuDnum() {
        return this.teuDnum;
    }

    public void setTeuDnum(BigDecimal teuDnum) {
        this.teuDnum = teuDnum;
    }
    public BigDecimal getTeuYnum() {
        return this.teuYnum;
    }

    public void setTeuYnum(BigDecimal teuYnum) {
        this.teuYnum = teuYnum;
    }
    public BigDecimal getTeuMnum() {
        return this.teuMnum;
    }

    public void setTeuMnum(BigDecimal teuMnum) {
        this.teuMnum = teuMnum;
    }

}
