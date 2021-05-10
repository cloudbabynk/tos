package net.huadong.tech.ship.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class ShipGroup {

	//船舶代码
	private String cbdm;
	
	// 船舶序号
	private String cbxh;
	
	// 海关编号
	private String hgbh;
	
	// 进口船代
	private String jkcd;
	
	// 出口船代
	private String ckcd;
		
	// 中文船名
	private String zwcm;
	
	// 英文船名
	private String ywcm;
	
	// 来港目的
	private String lgmd;
	
	// 进口航次
	private String hc;
	
	// 出口航次
	private String ckhc;

	// 呼号
	private String hh;
	
	// 当前位置
	private String dqwz;
	
	// 船公司代码
	private String cgsdm;
	
	// 船舶性质
	private String cbxz;
	
	// 船舶种类代码
	private String cbzldm;
	
	// 国家代码
	private String gjdm;
	
	// 抵口日期
	private String dkrq;
	
	// 抵口时间
	private String dksj;
	
	// 进口起运港
	private String jkqyg;
	
	// 进口第一挂靠港
	private String jkdygkg;
	
	// 出口目的港
	private String ckmdg;
	
	// 出口第一挂靠港
	private String ckdygkg;
	
	// 前吃水
	private BigDecimal qcs;
	
	// 后吃水
	private BigDecimal hcs;
	
	// 船长
	private BigDecimal cc;
	
	// 船宽
	private BigDecimal ck;
	
	// 载重吨
	private Long zzd;
	
	// 进口内外贸标志
	private String nwmbz;
	
	// 船长姓名
	private String czxm;
	
	// 进口货类
	private String jkhl;
	
	// 进口吨数
	private Long jkds;

	// 进口箱数
	private Long jkxs;
	
	// 出口货类
	private String ckhl;
	
	// 出口吨数
	private Long ckds;
	
	// 出口箱数
	private Long ckxs;
	
	// 是否使用进港拖轮
	private String jgtl;
	
	// 是否使用移泊拖轮
	private String ybtl;
	
	// 是否使用离港拖轮
	private String lgtl;
	
	// 是否使用进港引水
	private String jgys;
	
	// 是否使用移泊引水
	private String ybys;
	
	// 是否使用离港引水
	private String lgys;

	// 无用
	private String bl;
	
	// 离港日期
	private String lgrq;
	
	// 备注
	private String bz;

	// 操作人
	private String czr;
	
	// 操作单位
	private String czdw;
	
	// 操作时间
	private String czsj;
	
	// 发送标志
	private String fsbz;
	
	// 确认标志
	private String qrbz;
	
	// 分船公司（不用）
	private String fggsc;
	
	// 船舶编号
	private Long cbbh;
	
	// 无用
	private String bhbz;
	
	// 进口结吨日期
	private String jkjdrq;
	
	// 进口结吨
	private Long jkjd;
	
	// 进口结箱
	private Long jkjx;
	
	// 进口结TEU
	private Long jkjteu;
	
	// 进口预结标志
	private String jkyjf;
	
	// 出口结吨日期
	private String ckjdrq;
	
	// 出口结吨
	private Long ckjd;
	
	// 出口结箱
	private Long ckjx;
	
	// 出口结TEU
	private Long ckjteu;
	
	// 出口预结标志
	private String ckyjf;
	
	// 航线代码
	private String hxdm;
	
	// 无用
	private String xgbj;
	
	// 无用
	private String nwxtbj;
	
	// 无用
	private Long jd;
	
	// 无用
	private Long cks;
	
	// 出口内外贸标志
	private String cknwmbz;
	
	// 进口完船标志
	private String jkwcbz;
	
	// 进口完船时间
	private String jkwcsj;
	
	// 实际完船标志
	private String sjwzbz;
	
	// 船级社编号（IMO）
	private String czshbh;
	
	// 交通部编号
	private String jtbflbh;
	
	// 进口第二船代
	private String jkdrcddm;
	
	// 出口第二船代
	private String ckdrcddm;
	
	// 进口第二货类
	private String jkdrhl;
	
	// 出口第二货类
	private String ckdrhl;
	
	// 进口第二吨数
	private Long jkdrds;
	
	// 出口第二吨数
	private Long ckdrds;
	
	// 进口第二箱数
	private Long jkdrxs;
	
	// 出口第二箱数
	private Long ckdrxs;
	
	// 进口第一吨数
	private Long jkdyds;
	
	// 出口第一吨数
	private Long ckdyds;
	
	// 进口第一箱数
	private Long jkdyxs;
	
	// 出口第一箱数
	private Long ckdyxs;
	
	// 货主码头标志
	private String hzmtbz;
	
	// 引航费优惠标志
	private String ywyh;
	
	// 无用
	private String dlyxm;
	
	// 无用
	private String dlydh;
	
	// 无用
	private String dtlx;

	// 锁定标志
	private String sdbz;
	
	// 无用
	private String qtfs;
	
	// 出口航线代码
	private String ckhxdm;
	
	// 关区
	private String guanqu;
	
	// 拖轮公司
	private String tlgs;
	
	// 危品确认
	private String wpqr;
	
	// 作业公司
	private String zygs;

	public String getCbdm() {
		return cbdm;
	}

	public void setCbdm(String cbdm) {
		this.cbdm = cbdm;
	}

	public String getCbxh() {
		return cbxh;
	}

	public void setCbxh(String cbxh) {
		this.cbxh = cbxh;
	}

	public String getHgbh() {
		return hgbh;
	}

	public void setHgbh(String hgbh) {
		this.hgbh = hgbh;
	}

	public String getJkcd() {
		return jkcd;
	}

	public void setJkcd(String jkcd) {
		this.jkcd = jkcd;
	}

	public String getCkcd() {
		return ckcd;
	}

	public void setCkcd(String ckcd) {
		this.ckcd = ckcd;
	}

	public String getZwcm() {
		return zwcm;
	}

	public void setZwcm(String zwcm) {
		this.zwcm = zwcm;
	}

	public String getYwcm() {
		return ywcm;
	}

	public void setYwcm(String ywcm) {
		this.ywcm = ywcm;
	}

	public String getLgmd() {
		return lgmd;
	}

	public void setLgmd(String lgmd) {
		this.lgmd = lgmd;
	}

	public String getHc() {
		return hc;
	}

	public void setHc(String hc) {
		this.hc = hc;
	}

	public String getCkhc() {
		return ckhc;
	}

	public void setCkhc(String ckhc) {
		this.ckhc = ckhc;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getDqwz() {
		return dqwz;
	}

	public void setDqwz(String dqwz) {
		this.dqwz = dqwz;
	}

	public String getCgsdm() {
		return cgsdm;
	}

	public void setCgsdm(String cgsdm) {
		this.cgsdm = cgsdm;
	}

	public String getCbxz() {
		return cbxz;
	}

	public void setCbxz(String cbxz) {
		this.cbxz = cbxz;
	}

	public String getCbzldm() {
		return cbzldm;
	}

	public void setCbzldm(String cbzldm) {
		this.cbzldm = cbzldm;
	}

	public String getGjdm() {
		return gjdm;
	}

	public void setGjdm(String gjdm) {
		this.gjdm = gjdm;
	}

	public String getDksj() {
		return dksj;
	}

	public void setDksj(String dksj) {
		this.dksj = dksj;
	}

	public String getJkqyg() {
		return jkqyg;
	}

	public void setJkqyg(String jkqyg) {
		this.jkqyg = jkqyg;
	}

	public String getJkdygkg() {
		return jkdygkg;
	}

	public void setJkdygkg(String jkdygkg) {
		this.jkdygkg = jkdygkg;
	}

	public String getCkmdg() {
		return ckmdg;
	}

	public void setCkmdg(String ckmdg) {
		this.ckmdg = ckmdg;
	}

	public String getCkdygkg() {
		return ckdygkg;
	}

	public void setCkdygkg(String ckdygkg) {
		this.ckdygkg = ckdygkg;
	}


	public BigDecimal getQcs() {
		return qcs;
	}

	public void setQcs(BigDecimal qcs) {
		this.qcs = qcs;
	}

	public BigDecimal getHcs() {
		return hcs;
	}

	public void setHcs(BigDecimal hcs) {
		this.hcs = hcs;
	}

	public BigDecimal getCc() {
		return cc;
	}

	public void setCc(BigDecimal cc) {
		this.cc = cc;
	}

	public BigDecimal getCk() {
		return ck;
	}

	public void setCk(BigDecimal ck) {
		this.ck = ck;
	}

	public Long getZzd() {
		return zzd;
	}

	public void setZzd(Long zzd) {
		this.zzd = zzd;
	}

	public String getNwmbz() {
		return nwmbz;
	}

	public void setNwmbz(String nwmbz) {
		this.nwmbz = nwmbz;
	}

	public String getCzxm() {
		return czxm;
	}

	public void setCzxm(String czxm) {
		this.czxm = czxm;
	}

	public String getJkhl() {
		return jkhl;
	}

	public void setJkhl(String jkhl) {
		this.jkhl = jkhl;
	}

	public Long getJkds() {
		return jkds;
	}

	public void setJkds(Long jkds) {
		this.jkds = jkds;
	}

	public Long getJkxs() {
		return jkxs;
	}

	public void setJkxs(Long jkxs) {
		this.jkxs = jkxs;
	}

	public String getCkhl() {
		return ckhl;
	}

	public void setCkhl(String ckhl) {
		this.ckhl = ckhl;
	}

	public Long getCkds() {
		return ckds;
	}

	public void setCkds(Long ckds) {
		this.ckds = ckds;
	}

	public Long getCkxs() {
		return ckxs;
	}

	public void setCkxs(Long ckxs) {
		this.ckxs = ckxs;
	}

	public String getJgtl() {
		return jgtl;
	}

	public void setJgtl(String jgtl) {
		this.jgtl = jgtl;
	}

	public String getYbtl() {
		return ybtl;
	}

	public void setYbtl(String ybtl) {
		this.ybtl = ybtl;
	}

	public String getLgtl() {
		return lgtl;
	}

	public void setLgtl(String lgtl) {
		this.lgtl = lgtl;
	}

	public String getJgys() {
		return jgys;
	}

	public void setJgys(String jgys) {
		this.jgys = jgys;
	}

	public String getYbys() {
		return ybys;
	}

	public void setYbys(String ybys) {
		this.ybys = ybys;
	}

	public String getLgys() {
		return lgys;
	}

	public void setLgys(String lgys) {
		this.lgys = lgys;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}


	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public String getCzdw() {
		return czdw;
	}

	public void setCzdw(String czdw) {
		this.czdw = czdw;
	}


	public String getFsbz() {
		return fsbz;
	}

	public void setFsbz(String fsbz) {
		this.fsbz = fsbz;
	}

	public String getQrbz() {
		return qrbz;
	}

	public void setQrbz(String qrbz) {
		this.qrbz = qrbz;
	}

	public String getFggsc() {
		return fggsc;
	}

	public void setFggsc(String fggsc) {
		this.fggsc = fggsc;
	}

	public Long getCbbh() {
		return cbbh;
	}

	public void setCbbh(Long cbbh) {
		this.cbbh = cbbh;
	}

	public String getBhbz() {
		return bhbz;
	}

	public void setBhbz(String bhbz) {
		this.bhbz = bhbz;
	}


	public Long getJkjd() {
		return jkjd;
	}

	public void setJkjd(Long jkjd) {
		this.jkjd = jkjd;
	}

	public Long getJkjx() {
		return jkjx;
	}

	public void setJkjx(Long jkjx) {
		this.jkjx = jkjx;
	}

	public Long getJkjteu() {
		return jkjteu;
	}

	public void setJkjteu(Long jkjteu) {
		this.jkjteu = jkjteu;
	}

	public String getJkyjf() {
		return jkyjf;
	}

	public void setJkyjf(String jkyjf) {
		this.jkyjf = jkyjf;
	}


	public Long getCkjd() {
		return ckjd;
	}

	public void setCkjd(Long ckjd) {
		this.ckjd = ckjd;
	}

	public Long getCkjx() {
		return ckjx;
	}

	public void setCkjx(Long ckjx) {
		this.ckjx = ckjx;
	}

	public Long getCkjteu() {
		return ckjteu;
	}

	public void setCkjteu(Long ckjteu) {
		this.ckjteu = ckjteu;
	}

	public String getCkyjf() {
		return ckyjf;
	}

	public void setCkyjf(String ckyjf) {
		this.ckyjf = ckyjf;
	}

	public String getHxdm() {
		return hxdm;
	}

	public void setHxdm(String hxdm) {
		this.hxdm = hxdm;
	}

	public String getXgbj() {
		return xgbj;
	}

	public void setXgbj(String xgbj) {
		this.xgbj = xgbj;
	}

	public String getNwxtbj() {
		return nwxtbj;
	}

	public void setNwxtbj(String nwxtbj) {
		this.nwxtbj = nwxtbj;
	}

	public Long getJd() {
		return jd;
	}

	public void setJd(Long jd) {
		this.jd = jd;
	}

	public Long getCks() {
		return cks;
	}

	public void setCks(Long cks) {
		this.cks = cks;
	}

	public String getCknwmbz() {
		return cknwmbz;
	}

	public void setCknwmbz(String cknwmbz) {
		this.cknwmbz = cknwmbz;
	}

	public String getJkwcbz() {
		return jkwcbz;
	}

	public void setJkwcbz(String jkwcbz) {
		this.jkwcbz = jkwcbz;
	}


	public String getSjwzbz() {
		return sjwzbz;
	}

	public void setSjwzbz(String sjwzbz) {
		this.sjwzbz = sjwzbz;
	}

	public String getCzshbh() {
		return czshbh;
	}

	public void setCzshbh(String czshbh) {
		this.czshbh = czshbh;
	}

	public String getJtbflbh() {
		return jtbflbh;
	}

	public void setJtbflbh(String jtbflbh) {
		this.jtbflbh = jtbflbh;
	}

	public String getJkdrcddm() {
		return jkdrcddm;
	}

	public void setJkdrcddm(String jkdrcddm) {
		this.jkdrcddm = jkdrcddm;
	}

	public String getCkdrcddm() {
		return ckdrcddm;
	}

	public void setCkdrcddm(String ckdrcddm) {
		this.ckdrcddm = ckdrcddm;
	}

	public String getJkdrhl() {
		return jkdrhl;
	}

	public void setJkdrhl(String jkdrhl) {
		this.jkdrhl = jkdrhl;
	}

	public String getCkdrhl() {
		return ckdrhl;
	}

	public void setCkdrhl(String ckdrhl) {
		this.ckdrhl = ckdrhl;
	}

	public Long getJkdrds() {
		return jkdrds;
	}

	public void setJkdrds(Long jkdrds) {
		this.jkdrds = jkdrds;
	}

	public Long getCkdrds() {
		return ckdrds;
	}

	public void setCkdrds(Long ckdrds) {
		this.ckdrds = ckdrds;
	}

	public Long getJkdrxs() {
		return jkdrxs;
	}

	public void setJkdrxs(Long jkdrxs) {
		this.jkdrxs = jkdrxs;
	}

	public Long getCkdrxs() {
		return ckdrxs;
	}

	public void setCkdrxs(Long ckdrxs) {
		this.ckdrxs = ckdrxs;
	}

	public Long getJkdyds() {
		return jkdyds;
	}

	public void setJkdyds(Long jkdyds) {
		this.jkdyds = jkdyds;
	}

	public Long getCkdyds() {
		return ckdyds;
	}

	public void setCkdyds(Long ckdyds) {
		this.ckdyds = ckdyds;
	}

	public Long getJkdyxs() {
		return jkdyxs;
	}

	public void setJkdyxs(Long jkdyxs) {
		this.jkdyxs = jkdyxs;
	}

	public Long getCkdyxs() {
		return ckdyxs;
	}

	public void setCkdyxs(Long ckdyxs) {
		this.ckdyxs = ckdyxs;
	}

	public String getHzmtbz() {
		return hzmtbz;
	}

	public void setHzmtbz(String hzmtbz) {
		this.hzmtbz = hzmtbz;
	}

	public String getYwyh() {
		return ywyh;
	}

	public void setYwyh(String ywyh) {
		this.ywyh = ywyh;
	}

	public String getDlyxm() {
		return dlyxm;
	}

	public void setDlyxm(String dlyxm) {
		this.dlyxm = dlyxm;
	}

	public String getDlydh() {
		return dlydh;
	}

	public void setDlydh(String dlydh) {
		this.dlydh = dlydh;
	}

	public String getDtlx() {
		return dtlx;
	}

	public void setDtlx(String dtlx) {
		this.dtlx = dtlx;
	}

	public String getSdbz() {
		return sdbz;
	}

	public void setSdbz(String sdbz) {
		this.sdbz = sdbz;
	}

	public String getQtfs() {
		return qtfs;
	}

	public void setQtfs(String qtfs) {
		this.qtfs = qtfs;
	}

	public String getCkhxdm() {
		return ckhxdm;
	}

	public void setCkhxdm(String ckhxdm) {
		this.ckhxdm = ckhxdm;
	}

	public String getGuanqu() {
		return guanqu;
	}

	public void setGuanqu(String guanqu) {
		this.guanqu = guanqu;
	}

	public String getTlgs() {
		return tlgs;
	}

	public void setTlgs(String tlgs) {
		this.tlgs = tlgs;
	}

	public String getWpqr() {
		return wpqr;
	}

	public void setWpqr(String wpqr) {
		this.wpqr = wpqr;
	}

	public String getZygs() {
		return zygs;
	}

	public void setZygs(String zygs) {
		this.zygs = zygs;
	}

	public String getDkrq() {
		return dkrq;
	}

	public void setDkrq(String dkrq) {
		this.dkrq = dkrq;
	}

	public String getLgrq() {
		return lgrq;
	}

	public void setLgrq(String lgrq) {
		this.lgrq = lgrq;
	}

	public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}

	public String getJkjdrq() {
		return jkjdrq;
	}

	public void setJkjdrq(String jkjdrq) {
		this.jkjdrq = jkjdrq;
	}

	public String getCkjdrq() {
		return ckjdrq;
	}

	public void setCkjdrq(String ckjdrq) {
		this.ckjdrq = ckjdrq;
	}

	public String getJkwcsj() {
		return jkwcsj;
	}

	public void setJkwcsj(String jkwcsj) {
		this.jkwcsj = jkwcsj;
	}
	
	
}

