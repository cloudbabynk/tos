package net.huadong.tech.base.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="HANG_UP_LOG")
@NamedQuery(name="HangUpLog.findAll", query="SELECT w FROM HangUpLog w")
public class HangUpLog {
    /**
     * 车辆流水号
     */
    @Id
    @Column(name="PORT_CAR_NO")
    private BigDecimal portCarNo;

    /**
     * 旧车型
     */
    @Column(name="OLD_CAR_TYP")
    private String oldCarTyp;

    /**
     * 旧车辆品牌
     */
    @Column(name="OLD_BRAND_COD")
    private String oldBrandCod;

    /**
     * 旧车类
     */
    @Column(name="OLD_CAR_KIND")
    private String oldCarKind;

    /**
     * 新车型
     */
    @Column(name="NEW_CAR_TYP")
    private String newCarTyp;

    /**
     * 新车辆品牌
     */
    @Column(name="NEW_BRAND_COD")
    private String newBrandCod;

    /**
     * 新车类
     */
    @Column(name="NEW_CAR_KIND")
    private String newCarKind;

    /**
     * 创建人
     */
    @Column(name="REC_NAM")
    private String recNam;

    /**
     * 创建时间
     */
    @Column(name="REC_TIM")
    private Date recTim;

    /**
     * 更新人
     */
    @Column(name="UPD_NAM")
    private String updNam;

    /**
     * 更新时间
     */
    @Column(name="UPD_TIM")
    private Date updTim;

    public BigDecimal getPortCarNo() {
        return portCarNo;
    }

    public void setPortCarNo(BigDecimal portCarNo) {
        this.portCarNo = portCarNo;
    }

    public String getOldCarTyp() {
        return oldCarTyp;
    }

    public void setOldCarTyp(String oldCarTyp) {
        this.oldCarTyp = oldCarTyp;
    }

    public String getOldBrandCod() {
        return oldBrandCod;
    }

    public void setOldBrandCod(String oldBrandCod) {
        this.oldBrandCod = oldBrandCod;
    }

    public String getOldCarKind() {
        return oldCarKind;
    }

    public void setOldCarKind(String oldCarKind) {
        this.oldCarKind = oldCarKind;
    }

    public String getNewCarTyp() {
        return newCarTyp;
    }

    public void setNewCarTyp(String newCarTyp) {
        this.newCarTyp = newCarTyp;
    }

    public String getNewBrandCod() {
        return newBrandCod;
    }

    public void setNewBrandCod(String newBrandCod) {
        this.newBrandCod = newBrandCod;
    }

    public String getNewCarKind() {
        return newCarKind;
    }

    public void setNewCarKind(String newCarKind) {
        this.newCarKind = newCarKind;
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
}

