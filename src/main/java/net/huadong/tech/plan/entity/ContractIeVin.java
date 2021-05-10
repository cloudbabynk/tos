package net.huadong.tech.plan.entity;

import java.io.Serializable;
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
 * @date 2021-04-16
 */
@Entity
@Table(name="CONTRACT_IE_VIN")
public class ContractIeVin extends AuditEntityBean {
    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Id
    @Column(name = "IE_VIN_ID")
    private String ieVinId;

    /**
     *序号
     */
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     *车架号
     */
    @Column(name = "VIN_NO")
    private String vinNo;

    /**
     *是否已打印
     */
    @Column(name = "IS_USER")
    private String isUser;

    /**
     *是否已理货
     */
    @Column(name = "IS_TALLY")
    private String isTally;

    /**
     *集港计划
     */
    @Column(name = "CONTRACT_ID_PLAN")
    private String contractIdPlan;


    public String getIeVinId() {
        return this.ieVinId;
    }

    public void setIeVinId(String ieVinId) {
        this.ieVinId = ieVinId;
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getVinNo() {
        return this.vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getIsUser() {
        return this.isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getIsTally() {
        return this.isTally;
    }

    public void setIsTally(String isTally) {
        this.isTally = isTally;
    }

    public String getContractIdPlan() {
        return this.contractIdPlan;
    }

    public void setContractIdPlan(String contractIdPlan) {
        this.contractIdPlan = contractIdPlan;
    }


}
