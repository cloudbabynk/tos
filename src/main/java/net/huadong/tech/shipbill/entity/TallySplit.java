package net.huadong.tech.shipbill.entity;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.huadong.tech.base.bean.AuditEntityBean;
/**
*
* @author yuliang
* @date 2020-07-14
*/
@Entity
@Table(name="TALLY_SPLIT")
public class TallySplit extends AuditEntityBean {
	private static final long serialVersionUID = 1L;
        /**
        *
        */
        @Id
        @Column(name = "ID")
        private String id;

        /**
        *作业类型
        */
        @Column(name = "WORK_TYP")
        private String workTyp;

        /**
        *作业时间
        */
        @Column(name = "WORK_TIM")
        private Timestamp workTim;

        /**
        *审核标记
        */
        @Column(name = "CHECK_ID")
        private String checkId;

        /**
        *审核人（货运）
        */
        @Column(name = "CHECK_NAM")
        private String checkNam;

        /**
        *审核时间
        */
        @Column(name = "CHECK_TIM")
        private Timestamp checkTim;

        /**
        *船名
        */
        @Column(name = "SHIP_NAM")
        private String shipNam;

        /**
        *航次
        */
        @Column(name = "VOYAGE")
        private String voyage;

        /**
        *船号
        */
        @Column(name = "SHIP_NO")
        private String shipNo;

        /**
        *提单号
        */
        @Column(name = "BILL_NO")
        private String billNo;

        /**
        *车型
        */
        @Column(name = "CAR_TYP_COD")
        private String carTypCod;

        /**
        *品牌
        */
        @Column(name = "BRAND_COD")
        private String brandCod;

        /**
        *件数
        */
        @Column(name = "PIECES")
        private BigDecimal pieces;

        /**
        *总重
        */
        @Column(name = "ALL_WGT")
        private BigDecimal allWgt;
        
        /**
         *单车重量
         */
         @Column(name = "PIECE_WGT")
         private BigDecimal pieceWgt; 
         
         /**
          *长度
          */
	      @Column(name = "LENGTH")
	      private BigDecimal length;
      /**
       *总体积
       */
       @Column(name = "ALL_VALUMES")
       private BigDecimal allValumes;

       /**
        *件体积
        */
        @Column(name = "PIECE_VALUME")
        private BigDecimal pieceValume;
        /**
        *超长标记 1=超长；0=非超长
        */
        @Column(name = "OVER_LENGTH_ID")
        private String overLengthId;

        /**
        *陆运机力
        */
        @Column(name = "USEFREIGHTMAC")
        private String usefreightmac;

        /**
        *港方动力
        */
        @Column(name = "USESHIPWORKMAC")
        private String useshipworkmac;

        /**
        *港方人力
        */
        @Column(name = "USESHIPWORKPERSON")
        private String useshipworkperson;

        /**
        *夜班作业
        */
        @Column(name = "ISNIGHT")
        private String isnight;

        /**
        *节假日作业
        */
        @Column(name = "ISHOLIDAY")
        private String isholiday;

        /**
        *备注
        */
        @Column(name = "REMARK_TXT")
        private String remarkTxt;

        /**
        *提交标记；0=未提交；1=已提交；2=驳回
        */
        @Column(name = "UP_ID")
        private String upId;
        
        @Column(name = "CONTRACT_NO")
        private String contractNo;
        
        @Column(name = "YW_TYP")
        private String ywTyp;
        
        @Column(name = "BILL_CHECK_ID")
        private String billCheckId;
        
        @Column(name = "FLOW")
        private String flow;
        
        @Column(name = "FLOW_NAM")
        private String flowNam;
        
        public String getFlowNam() {
			return flowNam;
		}

		public void setFlowNam(String flowNam) {
			this.flowNam = flowNam;
		}

		public String getFlow() {
			return flow;
		}

		public void setFlow(String flow) {
			this.flow = flow;
		}

		public BigDecimal getAllValumes() {
			return allValumes;
		}

		public void setAllValumes(BigDecimal allValumes) {
			this.allValumes = allValumes;
		}

		public BigDecimal getPieceValume() {
			return pieceValume;
		}

		public void setPieceValume(BigDecimal pieceValume) {
			this.pieceValume = pieceValume;
		}

		public BigDecimal getAllWgt() {
			return allWgt;
		}

		public void setAllWgt(BigDecimal allWgt) {
			this.allWgt = allWgt;
		}

		public BigDecimal getPieceWgt() {
			return pieceWgt;
		}

		public void setPieceWgt(BigDecimal pieceWgt) {
			this.pieceWgt = pieceWgt;
		}

		public String getBillCheckId() {
			return billCheckId;
		}

		public void setBillCheckId(String billCheckId) {
			this.billCheckId = billCheckId;
		}

		@Transient
        private String carTypNam;
        
        @Transient
        private String brandNam;

        @Column(name = "IN_DTE")
        private Timestamp inDte;
        
        @Column(name = "OUT_DTE")
        private Timestamp outDte;
        
        @Transient
        private String storeDays;
        

        public String getStoreDays() {
			return storeDays;
		}

		public void setStoreDays(String storeDays) {
			this.storeDays = storeDays;
		}
		
		
		public String getYwTyp() {
			return ywTyp;
		}

		public void setYwTyp(String ywTyp) {
			this.ywTyp = ywTyp;
		}

		public Timestamp getInDte() {
			return inDte;
		}

		public void setInDte(Timestamp inDte) {
			this.inDte = inDte;
		}

		public Timestamp getOutDte() {
			return outDte;
		}

		public void setOutDte(Timestamp outDte) {
			this.outDte = outDte;
		}

		public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWorkTyp() {
            return this.workTyp;
        }

        public void setWorkTyp(String workTyp) {
            this.workTyp = workTyp;
        }

        public Timestamp getWorkTim() {
            return this.workTim;
        }

        public void setWorkTim(Timestamp workTim) {
            this.workTim = workTim;
        }

        public String getCheckId() {
            return this.checkId;
        }

        public void setCheckId(String checkId) {
            this.checkId = checkId;
        }

        public String getCheckNam() {
            return this.checkNam;
        }

        public void setCheckNam(String checkNam) {
            this.checkNam = checkNam;
        }

        public Timestamp getCheckTim() {
            return this.checkTim;
        }

        public void setCheckTim(Timestamp checkTim) {
            this.checkTim = checkTim;
        }

        public String getShipNam() {
            return this.shipNam;
        }

        public void setShipNam(String shipNam) {
            this.shipNam = shipNam;
        }

        public String getVoyage() {
            return this.voyage;
        }

        public void setVoyage(String voyage) {
            this.voyage = voyage;
        }

        public String getShipNo() {
            return this.shipNo;
        }

        public void setShipNo(String shipNo) {
            this.shipNo = shipNo;
        }

        public String getBillNo() {
            return this.billNo;
        }

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public String getCarTypCod() {
            return this.carTypCod;
        }

        public void setCarTypCod(String carTypCod) {
            this.carTypCod = carTypCod;
        }

        public String getBrandCod() {
            return this.brandCod;
        }

        public void setBrandCod(String brandCod) {
            this.brandCod = brandCod;
        }

        public BigDecimal getPieces() {
            return this.pieces;
        }

        public void setPieces(BigDecimal pieces) {
            this.pieces = pieces;
        }
        public BigDecimal getLength() {
            return this.length;
        }

        public void setLength(BigDecimal length) {
            this.length = length;
        }
        public String getOverLengthId() {
            return this.overLengthId;
        }

        public void setOverLengthId(String overLengthId) {
            this.overLengthId = overLengthId;
        }

        public String getUsefreightmac() {
            return this.usefreightmac;
        }

        public void setUsefreightmac(String usefreightmac) {
            this.usefreightmac = usefreightmac;
        }

        public String getUseshipworkmac() {
            return this.useshipworkmac;
        }

        public void setUseshipworkmac(String useshipworkmac) {
            this.useshipworkmac = useshipworkmac;
        }

        public String getUseshipworkperson() {
            return this.useshipworkperson;
        }

        public void setUseshipworkperson(String useshipworkperson) {
            this.useshipworkperson = useshipworkperson;
        }

        public String getIsnight() {
            return this.isnight;
        }

        public void setIsnight(String isnight) {
            this.isnight = isnight;
        }

        public String getIsholiday() {
            return this.isholiday;
        }

        public void setIsholiday(String isholiday) {
            this.isholiday = isholiday;
        }

        public String getRemarkTxt() {
            return this.remarkTxt;
        }

        public void setRemarkTxt(String remarkTxt) {
            this.remarkTxt = remarkTxt;
        }

        public String getUpId() {
            return this.upId;
        }

        public void setUpId(String upId) {
            this.upId = upId;
        }

		public String getCarTypNam() {
			return carTypNam;
		}

		public void setCarTypNam(String carTypNam) {
			this.carTypNam = carTypNam;
		}

		public String getBrandNam() {
			return brandNam;
		}

		public void setBrandNam(String brandNam) {
			this.brandNam = brandNam;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
        

}
