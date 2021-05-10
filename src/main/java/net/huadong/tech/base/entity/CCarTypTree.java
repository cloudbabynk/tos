package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;

import net.huadong.tech.base.bean.AuditEntityBean;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the C_CAR_TYP database table.
 * 车辆型号代码查询类
 *
 * @author ZHANGZY
 */
@Entity
public class CCarTypTree extends AuditEntityBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 审核状态是
     */
    public static final String Y = "1";
    /**
     * 审核状态否
     */
    public static final String N = "0";

    @Id
    private String id;

    private String name;

    private String parent_id;

    private String _parentId;

    @Column(name = "CAR_TYP")
    private String carTyp;

    @Column(name = "BRAND_NAM")
    private String brandNam;

    @Column(name = "CAR_KIND_NAM")
    private String carKindNam;

    @Column(name = "BRAND_COD")
    private String brandCod;

    @Column(name = "CAR_KIND")
    private String carKind;

    private String state;

    @Column(name = "FACTORY_NAM")
    private String factoryNam;

    @Column(name = "CAR_FEE_TYP_NAM")
    private String carFeeTypNam;

    @Column(name = "CAR_LEVEL")
    private String carLevel;

    @Column(name = "CAR_TYP_NAM")
    private String carTypNam;

    @Column(name = "CAR_TYP_E_NAM")
    private String carTypENam;

    @Column(name = "FACTORY_COD")
    private String factoryCod;

    private BigDecimal height;

    @Column(name = "LENGTH")
    private BigDecimal length;

    @Column(name = "CHECK_FLAG")
    private String checkFlag;

    @Column(name = "CAR_FEE_TYP")
    private String carFeeTyp;

    @Column(name = "SWEPT_VOLUME")
    private BigDecimal sweptVolume;

    @Column(name = "VOLUMES")
    private BigDecimal volumes;

    private BigDecimal weights;

    private BigDecimal width;

    @Column(name = "BIG_BRAND_COD")
    private String bigBrandCod;

    @Column(name = "INITIALS")
    private String initials;

    @Column(name = "BIG_BRAND_NAM")
    private String bigBrandNam;

    /**
     * 菜单级别 0为第一级菜单，1为第二级菜单……
     */
    @Transient
    private String level;

    public CCarTypTree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public String getCarTyp() {
        return carTyp;
    }

    public void setCarTyp(String carTyp) {
        this.carTyp = carTyp;
    }

    public String getBrandNam() {
        return brandNam;
    }

    public void setBrandNam(String brandNam) {
        this.brandNam = brandNam;
    }

    public String getCarKindNam() {
        return carKindNam;
    }

    public void setCarKindNam(String carKindNam) {
        this.carKindNam = carKindNam;
    }

    public String getBrandCod() {
        return this.brandCod;
    }

    public void setBrandCod(String brandCod) {
        this.brandCod = brandCod;
    }

    public String getCarKind() {
        return this.carKind;
    }

    public void setCarKind(String carKind) {
        this.carKind = carKind;
    }

    public String getBigBrandCod() {
        return bigBrandCod;
    }

    public void setBigBrandCod(String bigBrandCod) {
        this.bigBrandCod = bigBrandCod;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getBigBrandNam() {
        return bigBrandNam;
    }

    public void setBigBrandNam(String bigBrandNam) {
        this.bigBrandNam = bigBrandNam;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getCarFeeTyp() {
        return carFeeTyp;
    }

    public void setCarFeeTyp(String carFeeTyp) {
        this.carFeeTyp = carFeeTyp;
    }

    private String remarks;


    public String getCarTypENam() {
        return carTypENam;
    }

    public void setCarTypENam(String carTypENam) {
        this.carTypENam = carTypENam;
    }


    public String getFactoryNam() {
        return factoryNam;
    }

    public void setFactoryNam(String factoryNam) {
        this.factoryNam = factoryNam;
    }

    public String getCarTypNam() {
        return this.carTypNam;
    }

    public void setCarTypNam(String carTypNam) {
        this.carTypNam = carTypNam;
    }

    public String getFactoryCod() {
        return this.factoryCod;
    }

    public void setFactoryCod(String factoryCod) {
        this.factoryCod = factoryCod;
    }

    public BigDecimal getHeight() {
        return this.height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return this.length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }


    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getSweptVolume() {
        return this.sweptVolume;
    }

    public void setSweptVolume(BigDecimal sweptVolume) {
        this.sweptVolume = sweptVolume;
    }


    public BigDecimal getVolumes() {
        return this.volumes;
    }

    public void setVolumes(BigDecimal volumes) {
        this.volumes = volumes;
    }

    public BigDecimal getWeights() {
        return this.weights;
    }

    public void setWeights(BigDecimal weights) {
        this.weights = weights;
    }

    public BigDecimal getWidth() {
        return this.width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public String getCarFeeTypNam() {
        return carFeeTypNam;
    }

    public void setCarFeeTypNam(String carFeeTypNam) {
        this.carFeeTypNam = carFeeTypNam;
    }

    public String getCarLevel() {
        return carLevel;
    }

    public void setCarLevel(String carLevel) {
        this.carLevel = carLevel;
    }

}