package net.huadong.tech.base.entity;

import net.huadong.tech.base.bean.AuditEntityBean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the BIG_BRAND database table.
 *
 * @author zhangzy
 */
@Entity
@Table(name="BIG_BRAND")
@NamedQuery(name="BigBrand.findAll", query="SELECT c FROM BigBrand c")
public class BigBrand extends AuditEntityBean implements Serializable {
    private static final long serialVersionUID = -5700789863209914660L;
    /**
     * 大品牌代码
     */
    @Id
    @Column(name="BIG_BRAND_COD")
    private String bigBrandCod;

    /**
     * 大品牌名称
     */
    @Column(name="BIG_BRAND_NAM")
    private String bigBrandNam;

    /**
     * 备注
     */
    @Column(name="REMARKS")
    private String remarks;

    /**
     * 首字母
     */
    @Column(name="INITIALS")
    private String initials;

    public String getBigBrandCod() {
        return bigBrandCod;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setBigBrandCod(String bigBrandCod) {
        this.bigBrandCod = bigBrandCod;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBigBrandNam() {
        return bigBrandNam;
    }

    public void setBigBrandNam(String bigBrandNam) {
        this.bigBrandNam = bigBrandNam;
    }
}

