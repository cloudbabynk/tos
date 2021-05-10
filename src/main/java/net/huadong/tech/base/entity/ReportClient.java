package net.huadong.tech.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author 孙璐
 * @date 2021-04-06
 */
@Entity
@Table(name="REPORT_CLIENT")
public class ReportClient extends AuditEntityBean {
    private static final long serialVersionUID = 1L;
    /**
     *主键
     */

    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Column(name = "CLIENT_ID")
    private String clientId;

    /**
     *大客户（品牌）
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     *开始时间(作废)
     */
    @Column(name = "START_TIM")
    private String startTim;

    /**
     *结束时间（作废）
     */
    @Column(name = "END_TIM")
    private Timestamp endTim;

    /**
     *周数（自然周）
     */
    @Column(name = "WEEK_NUM")
    private BigDecimal weekNum;

    /**
     *作业量
     */
    @Column(name = "TEU_NUM")
    private BigDecimal teuNum;


    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    } 
    public String getStartTim() {
		return startTim;
	}

	public void setStartTim(String startTim) {
		this.startTim = startTim;
	}

	public Timestamp getEndTim() {
        return this.endTim;
    }

    public void setEndTim(Timestamp endTim) {
        this.endTim = endTim;
    }

    public BigDecimal getWeekNum() {
        return this.weekNum;
    }

    public void setWeekNum(BigDecimal weekNum) {
        this.weekNum = weekNum;
    }
    public BigDecimal getTeuNum() {
        return this.teuNum;
    }

    public void setTeuNum(BigDecimal teuNum) {
        this.teuNum = teuNum;
    }

}
