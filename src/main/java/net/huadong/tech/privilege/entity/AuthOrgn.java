
package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

import net.huadong.tech.base.bean.AuditEntityBean;

/**
 * The persistent class for the AUTH_ORGN database table.
 * 
 */
@Entity

@Table(name = "AUTH_ORGN")
@Cache(type = CacheType.SOFT, // Cache everything until the JVM decides memory
								// is low.
		size = 64000, // Use 64,000 as the initial cache size.
		expiry = 360000, // 6 minutes
		coordinationType = CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS // if
																			// cache
																			// coordination
																			// is
																			// used,
																			// only
																			// send
																			// invalidation
																			// messages.
)
@NamedQuery(name = "AuthOrgn.findAll", query = "SELECT a FROM AuthOrgn a")

public class AuthOrgn extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GRP = "0";// 集团
	public static final String UNIT = "1";// 单位(集团科室)

	@Id
	@Column(name = "ORGN_ID")
	private String orgnId;

	@Column(name = "CANCEL_ID")
	private String cancelId;
	
	@Column(name = "DEL_FLG")
	private String delFlg;

	@Column(name = "ORGN_TYP")
	private String orgnTyp;

	private String description;

	@Column(name = "INTERFACE_COD")
	private String interfaceCod;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EN_NAME")
	private String enName;
	@Column(name = "ORGN_COD")
	private String orgnCod;

	@Column(name = "PARENT_ID")
	private String parentId;

	@Column(name = "REC_CODE")
	private String recCode;

	@Column(name = "SHORT_NAM")
	private String shortNam;
	@Column(name = "SORTER")
	private BigDecimal sorter;

	public String getOrgnId() {
		return this.orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getCancelId() {
		return this.cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	public String getOrgnTyp() {
		return this.orgnTyp;
	}

	public void setOrgnTyp(String orgnTyp) {
		this.orgnTyp = orgnTyp;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInterfaceCod() {
		return this.interfaceCod;
	}

	public void setInterfaceCod(String interfaceCod) {
		this.interfaceCod = interfaceCod;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgnCod() {
		return this.orgnCod;
	}

	public void setOrgnCod(String orgnCod) {
		this.orgnCod = orgnCod;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRecCode() {
		return this.recCode;
	}

	public void setRecCode(String recCode) {
		this.recCode = recCode;
	}

	public String getShortNam() {
		return this.shortNam;
	}

	public void setShortNam(String shortNam) {
		this.shortNam = shortNam;
	}

	public BigDecimal getSorter() {
		return this.sorter;
	}

	public void setSorter(BigDecimal sorter) {
		this.sorter = sorter;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
}