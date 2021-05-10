package net.huadong.tech.Interface.entity;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
*
* @author yuliang
* @date 2020-05-14
*/
@Entity
@Table(name="M_FEE_INTERFACE_BERTH")
public class MFeeInterfaceBerth {
	private static final long serialVersionUID = 1L;
        /**
        *泊位名称
        */
        @Id
        @Column(name = "ID")
        private String id;

        /**
        *生产系统用户ID
        */
        @Column(name = "USER_ID")
        private String userId;

        /**
        *生产系统用户名
        */
        @Column(name = "USERNAME")
        private String username;

        /**
        *生产系统用户IP
        */
        @Column(name = "USERIP")
        private String userip;

        /**
        *船名
        */
        @Column(name = "VESSELNAME")
        private String vesselname;

        /**
        *英文船名
        */
        @Column(name = "VESSELNAMEEN")
        private String vesselnameen;
        /**
         *航次
         */
         @Column(name = "VOYAGE")
         private String voyage;
        /**
        *
        */
        @Column(name = "IMPEXPID")
        private String impexpid;

        /**
        *内外贸 取值范围：内贸/外贸
        */
        @Column(name = "VESSELTRADEID")
        private String vesseltradeid;

        /**
        *净吨
        */
        @Column(name = "VESSELWEIGHT")
        private BigDecimal vesselweight;

        /**
        *船代
取值：直接用汉语名称，计费系统自动对照
        */
        @Column(name = "VESSELAGENTID")
        private String vesselagentid;

        /**
        *船公司
取值：直接用汉语名称，计费系统自动对照
        */
        @Column(name = "CARRIERID")
        private String carrierid;

        /**
        *IMO
        */
        @Column(name = "IMO")
        private String imo;

        /**
        *生产系统船期ID
        */
        @Column(name = "VESSELVISITID")
        private String vesselvisitid;

        /**
        *泊位代码
        */
        @Column(name = "BERTHCODE")
        private String berthcode;

        /**
        *
        */
        @Column(name = "BERTHNAME")
        private String berthname;

        /**
        *停泊类型，不用传值，空着就行
        */
        @Column(name = "BERTHCATEGORYID")
        private String berthcategoryid;

        /**
        *码头公司代码，滚装=GZ,环球滚装=HQGZ,固定
        */
        @Column(name = "FACILITYID")
        private String facilityid;

        /**
        *码头类型 固定值=RORO
        */
        @Column(name = "COMPLEXID")
        private String complexid;

        /**
        *
        */
        @Column(name = "NIGHTID")
        private String nightid;

        /**
        *
        */
        @Column(name = "HOLIDAYID")
        private String holidayid;

        /**
        *生产系统该船期对应的表主键（集团表）
        */
        @Column(name = "VOYAGEINTERFACEID")
        private String voyageinterfaceid;

        /**
        *生成系统该船期对应的表主键（码头公司表）
        */
        @Column(name = "VOYAGEFACILITYINTERFACEID")
        private String voyagefacilityinterfaceid;

        /**
        *
        */
        @Column(name = "ORDERNO")
        private String orderno;

        /**
        *上传人
        */
        @Column(name = "SENDNAME")
        private String sendname;

        /**
        *发送时间
        */
        @Column(name = "SENDTIME")
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp sendtime;

        /**
        *靠泊开始时间
        */
        @Column(name = "BEGINDATE")
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp begindate;

        /**
        *靠泊结束时间
        */
        @Column(name = "ENDDATE")
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp enddate;

        /**
        *停泊类型 取值: 滚装非生产性停泊/滚装锚地停泊/滚装生产性停泊
        */
        @Column(name = "MOVETYPEID")
        private String movetypeid;

        /**
        *作业系统对应的唯一一条停泊数据的主键  ？？？？怎么取值
        */
        @Column(name = "INTERFACEBERTHNO")
        private String interfaceberthno;

        /**
        *报文发送标记 0=未发送 1=已发送
        */
        @Column(name = "BW_SEND_ID")
        private String bwSendId;

        /**
        *计费系统接收报文成功 回执标记 1=已接收；0=未接收
        */
        @Column(name = "JF_OK_ID")
        private String jfOkId;

        /**
        *删除标记  1=已删除；0=未删除/默认
        */
        @Column(name = "DEL_ID")
        private String delId;

        /**
        *删除报文 发送完毕 1=已发送 0=未发送
        */
        @Column(name = "DEL_SEND_ID")
        private String delSendId;

        /**
        *计费系统接收删除报文成功 回执标记 1=已接收；0=未接收
        */
        @Column(name = "JF_DEL_OK_ID")
        private String jfDelOkId;


        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserip() {
            return this.userip;
        }

        public void setUserip(String userip) {
            this.userip = userip;
        }

        public String getVesselname() {
            return this.vesselname;
        }

        public void setVesselname(String vesselname) {
            this.vesselname = vesselname;
        }

        public String getVesselnameen() {
            return this.vesselnameen;
        }

        public void setVesselnameen(String vesselnameen) {
            this.vesselnameen = vesselnameen;
        }

        public String getImpexpid() {
            return this.impexpid;
        }

        public void setImpexpid(String impexpid) {
            this.impexpid = impexpid;
        }

        public String getVesseltradeid() {
            return this.vesseltradeid;
        }

        public void setVesseltradeid(String vesseltradeid) {
            this.vesseltradeid = vesseltradeid;
        }

        public BigDecimal getVesselweight() {
            return this.vesselweight;
        }

        public void setVesselweight(BigDecimal vesselweight) {
            this.vesselweight = vesselweight;
        }
        public String getVesselagentid() {
            return this.vesselagentid;
        }

        public void setVesselagentid(String vesselagentid) {
            this.vesselagentid = vesselagentid;
        }
        public String getVoyage() {
            return this.voyage;
        }

        public void setVoyage(String voyage) {
            this.voyage = voyage;
        }
        public String getCarrierid() {
            return this.carrierid;
        }

        public void setCarrierid(String carrierid) {
            this.carrierid = carrierid;
        }

        public String getImo() {
            return this.imo;
        }

        public void setImo(String imo) {
            this.imo = imo;
        }

        public String getVesselvisitid() {
            return this.vesselvisitid;
        }

        public void setVesselvisitid(String vesselvisitid) {
            this.vesselvisitid = vesselvisitid;
        }

        public String getBerthcode() {
            return this.berthcode;
        }

        public void setBerthcode(String berthcode) {
            this.berthcode = berthcode;
        }

        public String getBerthname() {
            return this.berthname;
        }

        public void setBerthname(String berthname) {
            this.berthname = berthname;
        }

        public String getBerthcategoryid() {
            return this.berthcategoryid;
        }

        public void setBerthcategoryid(String berthcategoryid) {
            this.berthcategoryid = berthcategoryid;
        }

        public String getFacilityid() {
            return this.facilityid;
        }

        public void setFacilityid(String facilityid) {
            this.facilityid = facilityid;
        }

        public String getComplexid() {
            return this.complexid;
        }

        public void setComplexid(String complexid) {
            this.complexid = complexid;
        }

        public String getNightid() {
            return this.nightid;
        }

        public void setNightid(String nightid) {
            this.nightid = nightid;
        }

        public String getHolidayid() {
            return this.holidayid;
        }

        public void setHolidayid(String holidayid) {
            this.holidayid = holidayid;
        }

        public String getVoyageinterfaceid() {
            return this.voyageinterfaceid;
        }

        public void setVoyageinterfaceid(String voyageinterfaceid) {
            this.voyageinterfaceid = voyageinterfaceid;
        }

        public String getVoyagefacilityinterfaceid() {
            return this.voyagefacilityinterfaceid;
        }

        public void setVoyagefacilityinterfaceid(String voyagefacilityinterfaceid) {
            this.voyagefacilityinterfaceid = voyagefacilityinterfaceid;
        }

        public String getOrderno() {
            return this.orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getSendname() {
            return this.sendname;
        }

        public void setSendname(String sendname) {
            this.sendname = sendname;
        }

        public Timestamp getSendtime() {
            return this.sendtime;
        }

        public void setSendtime(Timestamp sendtime) {
            this.sendtime = sendtime;
        }

        public Timestamp getBegindate() {
            return this.begindate;
        }

        public void setBegindate(Timestamp begindate) {
            this.begindate = begindate;
        }

        public Timestamp getEnddate() {
            return this.enddate;
        }

        public void setEnddate(Timestamp enddate) {
            this.enddate = enddate;
        }

        public String getMovetypeid() {
            return this.movetypeid;
        }

        public void setMovetypeid(String movetypeid) {
            this.movetypeid = movetypeid;
        }

        public String getInterfaceberthno() {
            return this.interfaceberthno;
        }

        public void setInterfaceberthno(String interfaceberthno) {
            this.interfaceberthno = interfaceberthno;
        }

        public String getBwSendId() {
            return this.bwSendId;
        }

        public void setBwSendId(String bwSendId) {
            this.bwSendId = bwSendId;
        }

        public String getJfOkId() {
            return this.jfOkId;
        }

        public void setJfOkId(String jfOkId) {
            this.jfOkId = jfOkId;
        }

        public String getDelId() {
            return this.delId;
        }

        public void setDelId(String delId) {
            this.delId = delId;
        }

        public String getDelSendId() {
            return this.delSendId;
        }

        public void setDelSendId(String delSendId) {
            this.delSendId = delSendId;
        }

        public String getJfDelOkId() {
            return this.jfDelOkId;
        }

        public void setJfDelOkId(String jfDelOkId) {
            this.jfDelOkId = jfDelOkId;
        }


}
