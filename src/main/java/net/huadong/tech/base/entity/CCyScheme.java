package net.huadong.tech.base.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the C_CY_SCHEME database table.
 * 
 */
@Entity
@Table(name="C_CY_SCHEME")
@NamedQuery(name="CCyScheme.findAll", query="SELECT c FROM CCyScheme c")
public class CCyScheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String scheme;

	@Column(name="MAX_BAY_NUM")
	private BigDecimal maxBayNum;

	@Column(name="MAX_ROW_NUM")
	private BigDecimal maxRowNum;

	//bi-directional many-to-one association to CCySchemeBay
	@OneToMany(mappedBy="CCyScheme")
	private List<CCySchemeBay> CCySchemeBays;

	//bi-directional many-to-one association to CCySchemeRow
	@OneToMany(mappedBy="CCyScheme")
	private List<CCySchemeRow> CCySchemeRows;

	public CCyScheme() {
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public BigDecimal getMaxBayNum() {
		return this.maxBayNum;
	}

	public void setMaxBayNum(BigDecimal maxBayNum) {
		this.maxBayNum = maxBayNum;
	}

	public BigDecimal getMaxRowNum() {
		return this.maxRowNum;
	}

	public void setMaxRowNum(BigDecimal maxRowNum) {
		this.maxRowNum = maxRowNum;
	}

	public List<CCySchemeBay> getCCySchemeBays() {
		return this.CCySchemeBays;
	}

	public void setCCySchemeBays(List<CCySchemeBay> CCySchemeBays) {
		this.CCySchemeBays = CCySchemeBays;
	}

	public CCySchemeBay addCCySchemeBay(CCySchemeBay CCySchemeBay) {
		getCCySchemeBays().add(CCySchemeBay);
		CCySchemeBay.setCCyScheme(this);

		return CCySchemeBay;
	}

	public CCySchemeBay removeCCySchemeBay(CCySchemeBay CCySchemeBay) {
		getCCySchemeBays().remove(CCySchemeBay);
		CCySchemeBay.setCCyScheme(null);

		return CCySchemeBay;
	}

	public List<CCySchemeRow> getCCySchemeRows() {
		return this.CCySchemeRows;
	}

	public void setCCySchemeRows(List<CCySchemeRow> CCySchemeRows) {
		this.CCySchemeRows = CCySchemeRows;
	}

	public CCySchemeRow addCCySchemeRow(CCySchemeRow CCySchemeRow) {
		getCCySchemeRows().add(CCySchemeRow);
		CCySchemeRow.setCCyScheme(this);

		return CCySchemeRow;
	}

	public CCySchemeRow removeCCySchemeRow(CCySchemeRow CCySchemeRow) {
		getCCySchemeRows().remove(CCySchemeRow);
		CCySchemeRow.setCCyScheme(null);

		return CCySchemeRow;
	}

}