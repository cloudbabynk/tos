package net.huadong.tech.privilege.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.UuidGenerator;

import net.huadong.tech.base.bean.AuditEntityBean;
import net.huadong.tech.base.bean.EzTreeBean;

/**
 * The persistent class for the SYS_CODE database table.
 * 
 */
@Entity
@Table(name = "SYS_CODE")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "SysCode.findAll", query = "SELECT s FROM SysCode s"),
		@NamedQuery(name = "SysCode.findByCodeId", query = "SELECT s FROM SysCode s WHERE s.codeId = :codeId"), })
public class SysCode extends AuditEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static List<EzTreeBean> result = new ArrayList<EzTreeBean>();
	private static List<EzTreeBean> ship = new ArrayList<EzTreeBean>();
	private static List<EzTreeBean> codecode = new ArrayList<EzTreeBean>();
	private static List<EzTreeBean> common = new ArrayList<EzTreeBean>();
	private static List<EzTreeBean> workcode = new ArrayList<EzTreeBean>();

	public static List<EzTreeBean> genshipType() {
		if (ship.size() > 0) {
			return ship;
		}
		EzTreeBean moor_dir = new EzTreeBean();
		moor_dir.setId("MOOR_DIRECTION");
		moor_dir.setText("船舶停靠方向");
		ship.add(moor_dir);

		EzTreeBean shi_typ1 = new EzTreeBean();
		shi_typ1.setId("SHIP_TYP_COD1");
		shi_typ1.setText("船舶类型");
		ship.add(shi_typ1);
		
		

		EzTreeBean str_typ = new EzTreeBean();
		str_typ.setId("STRUCT_TYP_COD");
		str_typ.setText("泊位结构");
		ship.add(str_typ);

		EzTreeBean ser_typ_cod = new EzTreeBean();
		ser_typ_cod.setId("SERVE_TYP_COD");
		ser_typ_cod.setText("泊位服务类别");
		ship.add(ser_typ_cod);

		EzTreeBean ber_typ = new EzTreeBean();
		ber_typ.setId("BERTH_TYP_COD");
		ber_typ.setText("泊位用途分类");
		ship.add(ber_typ);

		EzTreeBean shi_sta = new EzTreeBean();
		shi_sta.setId("SHIP_STAT");
		shi_sta.setText("船舶状态");
		ship.add(shi_sta);

		EzTreeBean shi_typ = new EzTreeBean();
		shi_typ.setId("SHIP_TYP_COD");
		shi_typ.setText("船舶类型");
		ship.add(shi_typ);

		EzTreeBean sea_son = new EzTreeBean();
		sea_son.setId("SEA_SON");
		sea_son.setText("拒绝原因");
		ship.add(sea_son);

		EzTreeBean car_cod = new EzTreeBean();
		car_cod.setId("CONSIGN_COD");
		car_cod.setText("货主代码");
		ship.add(car_cod);

		EzTreeBean shipagent_cod = new EzTreeBean();
		shipagent_cod.setId("SHIPAGENT_COD");
		shipagent_cod.setText("船代代码");
		ship.add(shipagent_cod);

		EzTreeBean cargoagent_cod = new EzTreeBean();
		cargoagent_cod.setId("CARGOAGENT_COD");
		cargoagent_cod.setText("货代代码");
		ship.add(cargoagent_cod);

		return ship;
	}

	public static List<EzTreeBean> gencommonType() {
		if (common.size() > 0) {
			return common;
		}

		EzTreeBean bus_nta = new EzTreeBean();
		bus_nta.setId("BUSINESS_NATURE");
		bus_nta.setText("公司性质");
		common.add(bus_nta);

		EzTreeBean imp_deg = new EzTreeBean();
		imp_deg.setId("IMPORTANT_DEGREE");
		imp_deg.setText("重要程度");
		common.add(imp_deg);

		EzTreeBean superiority = new EzTreeBean();
		superiority.setId("SUPERIORITY");
		superiority.setText("优势程度");
		common.add(superiority);

		EzTreeBean gender = new EzTreeBean();
		gender.setId("GENDER");
		gender.setText("性别");
		common.add(gender);

		EzTreeBean cus_vis = new EzTreeBean();
		cus_vis.setId("CUSTOMER_VISIT_TYP");
		cus_vis.setText("客户拜访类别");
		common.add(cus_vis);

		return common;
	}

	public static List<EzTreeBean> gencodeType() {
		if (codecode.size() > 0) {
			return codecode;
		}
		EzTreeBean fac_typ = new EzTreeBean();
		fac_typ.setId("FACILITY_TYP_COD");
		fac_typ.setText("硬件设施类别代码");
		codecode.add(fac_typ);

		EzTreeBean mea_uni = new EzTreeBean();
		mea_uni.setId("MEASURE_UNIT_COD");
		mea_uni.setText("计量单位代码");
		codecode.add(mea_uni);

		EzTreeBean cur_sta = new EzTreeBean();
		cur_sta.setId("CURRENT_STAT");
		cur_sta.setText("箱状态代码");
		codecode.add(cur_sta);

		EzTreeBean cus_typ = new EzTreeBean();
		cus_typ.setId("CUSTOMER_TYP_COD");
		cus_typ.setText("客户类型代码");
		codecode.add(cus_typ);

		EzTreeBean loc_rea = new EzTreeBean();
		loc_rea.setId("LOCK_REASON_COD");
		loc_rea.setText("箱状态代码");
		codecode.add(loc_rea);

		EzTreeBean app_sta = new EzTreeBean();
		app_sta.setId("APPLY_STAT");
		app_sta.setText("箱状态代码");
		codecode.add(app_sta);

		EzTreeBean cy_typ = new EzTreeBean();
		cy_typ.setId("CY_TYP_COD");
		cy_typ.setText("堆场类型");
		codecode.add(cy_typ);

		EzTreeBean car_typ = new EzTreeBean();
		car_typ.setId("CARGO_TYP_COD");
		car_typ.setText("主营货类");
		codecode.add(car_typ);

		EzTreeBean loc_typ = new EzTreeBean();
		loc_typ.setId("LOCK_TYP_COD");
		loc_typ.setText("锁扣类型");
		codecode.add(loc_typ);

		EzTreeBean ord_typ = new EzTreeBean();
		ord_typ.setId("ORDER_TYP_COD");
		ord_typ.setText("委托类型");
		codecode.add(ord_typ);

		EzTreeBean com_qua = new EzTreeBean();
		com_qua.setId("COMPANY_QUALIFICATION_COD");
		com_qua.setText("企业资质编号");
		codecode.add(com_qua);

		return codecode;
	}

	public static List<EzTreeBean> genworkType() {
		if (workcode.size() > 0) {
			return workcode;
		}
		EzTreeBean work_typ = new EzTreeBean();
		work_typ.setId("workType");
		work_typ.setText("作业类型");
		workcode.add(work_typ);

		EzTreeBean unload_uni = new EzTreeBean();
		unload_uni.setId("unloadFlg");
		unload_uni.setText("装卸");
		workcode.add(unload_uni);

		EzTreeBean procCod_sta = new EzTreeBean();
		procCod_sta.setId("procCod");
		procCod_sta.setText("操作过程");
		workcode.add(procCod_sta);

		EzTreeBean ioyard_typ = new EzTreeBean();
		ioyard_typ.setId("ioyardFlg");
		ioyard_typ.setText("出入");
		workcode.add(ioyard_typ);

		EzTreeBean ioyardWay_rea = new EzTreeBean();
		ioyardWay_rea.setId("ioyardWay");
		ioyardWay_rea.setText("出入方式");
		workcode.add(ioyardWay_rea);

		EzTreeBean informTyp_sta = new EzTreeBean();
		informTyp_sta.setId("informTyp");
		informTyp_sta.setText("作业单类型");
		workcode.add(informTyp_sta);

		EzTreeBean transport_typ = new EzTreeBean();
		transport_typ.setId("transport");
		transport_typ.setText("作业工具");
		workcode.add(transport_typ);

		EzTreeBean direct_Flg = new EzTreeBean();
		direct_Flg.setId("direct_COD");
		direct_Flg.setText("直取作业");
		workcode.add(direct_Flg);

		EzTreeBean mode_Flg = new EzTreeBean();
		mode_Flg.setId("mode_COD");
		mode_Flg.setText("作业模式");
		workcode.add(mode_Flg);

		EzTreeBean main_Flg = new EzTreeBean();
		main_Flg.setId("main_COD");
		main_Flg.setText("出入数据标志");
		workcode.add(main_Flg);

		EzTreeBean return_Flg = new EzTreeBean();
		return_Flg.setId("return_COD");
		return_Flg.setText("退装卸标志");
		workcode.add(return_Flg);

		return workcode;
	}
	
	public static List<EzTreeBean> genType() {
		if (result.size() > 0) {
			return result;
		}

		EzTreeBean shipmod = new EzTreeBean();
		shipmod.setText("船舶类");
		shipmod.setChildren(genshipType());
		result.add(shipmod);

		
		
		
		
		EzTreeBean codemod = new EzTreeBean();
		codemod.setText("代码类");
		codemod.setChildren(gencodeType());
		result.add(codemod);

		EzTreeBean commod = new EzTreeBean();
		commod.setText("通用类");
		commod.setChildren(gencommonType());
		result.add(commod);
		
		EzTreeBean workmod = new EzTreeBean();
		workmod.setText("作业类");
		workmod.setChildren(genworkType());
		result.add(workmod);

		EzTreeBean custom = new EzTreeBean();
		custom.setId("CUSTOMER_CLASSIFICATION");
		custom.setText("客户级别");
		result.add(custom);

		EzTreeBean pac_typ = new EzTreeBean();
		pac_typ.setId("PACKAGE_TYP");
		pac_typ.setText("包装");
		result.add(pac_typ);
		
		EzTreeBean mat_cod = new EzTreeBean();
		mat_cod.setId("MATA_COD");
		mat_cod.setText("材质/品种");
		result.add(mat_cod);

		EzTreeBean mach_class = new EzTreeBean();
		mach_class.setId("MACH_CLASS");
		mach_class.setText("机械类型");
		result.add(mach_class);
		

		EzTreeBean mach_type = new EzTreeBean();
		mach_type.setId("MACH_TYPE");
		mach_type.setText("机械种类");
		mach_type.setType("1"); // 有下级
		result.add(mach_type);

		EzTreeBean mach_extra = new EzTreeBean();
		mach_extra.setId("MACH_EXTRA");
		mach_extra.setText("外部机械");
		result.add(mach_extra);
		
		EzTreeBean labor_team = new EzTreeBean();
		labor_team.setId("LABOR_TEAM");
		labor_team.setText("劳工队");
		result.add(labor_team);
		
		EzTreeBean work_position = new EzTreeBean();
		work_position.setId("WORK_POSITION");
		work_position.setText("作业位置");
		result.add(work_position);

		EzTreeBean mach_use = new EzTreeBean();
		mach_use.setId("MACH_USE");
		mach_use.setText("机械用途");
		result.add(mach_use);

		EzTreeBean ord_sta = new EzTreeBean();
		ord_sta.setId("ORDER_STAT");
		ord_sta.setText("委托状态");
		result.add(ord_sta);

		EzTreeBean emp_Type = new EzTreeBean();
		emp_Type.setId("EMP_TYPE");
		emp_Type.setText("委托状态");
		result.add(emp_Type);

		EzTreeBean team_id = new EzTreeBean();
		team_id.setId("TEAM_ID");
		team_id.setText("公司标识");
		result.add(team_id);

		EzTreeBean mac_sta = new EzTreeBean();
		mac_sta.setId("MACH_STAT");
		mac_sta.setText("机械状态");
		result.add(mac_sta);

		EzTreeBean project_sta = new EzTreeBean();
		project_sta.setId("PROJECT_STAT");
		project_sta.setText("项目状态");
		result.add(project_sta);

		EzTreeBean process_sta = new EzTreeBean();
		process_sta.setId("PROCESS_STAT");
		process_sta.setText("处理状态");
		result.add(process_sta);

		EzTreeBean com_sta = new EzTreeBean();
		com_sta.setId("COMPLAIN_STAT");
		com_sta.setText("投诉状态");
		result.add(com_sta);

		EzTreeBean mac_inf = new EzTreeBean();
		mac_inf.setId("MACRO_INFO_CLASS");
		mac_inf.setText("宏观信息分类");
		result.add(mac_inf);

		EzTreeBean lin_mar = new EzTreeBean();
		lin_mar.setId("LINER_MARK");
		lin_mar.setText("班轮标志");
		result.add(lin_mar);

		EzTreeBean pro_pla = new EzTreeBean();
		pro_pla.setId("PROD_PLAN_TYP");
		pro_pla.setText("生产计划类型");
		result.add(pro_pla);

		EzTreeBean bul_typ = new EzTreeBean();
		bul_typ.setId("BULK_TYP");
		bul_typ.setText("箱货类型");
		result.add(bul_typ);

		EzTreeBean app_typ = new EzTreeBean();
		app_typ.setId("APPLY_TYP");
		app_typ.setText("审批申请类别");
		result.add(app_typ);

		EzTreeBean mai_sta = new EzTreeBean();
		mai_sta.setId("MAINT_STAT");
		mai_sta.setText("维护状态");
		result.add(mai_sta);

		EzTreeBean map_cnt = new EzTreeBean();
		map_cnt.setId("MAP_CNTR_SIZE");
		map_cnt.setText("地图箱尺寸");
		result.add(map_cnt);

		EzTreeBean que_typ = new EzTreeBean();
		que_typ.setId("QUEUE_TYP");
		que_typ.setText("队列类型");
		result.add(que_typ);

		EzTreeBean getcod = new EzTreeBean();
		getcod.setId("GETCOD");
		getcod.setText("获取编号");
		result.add(getcod);

		EzTreeBean con_sta = new EzTreeBean();
		con_sta.setId("CONTRACT_STAT");
		con_sta.setText("合同状态");
		result.add(con_sta);

		EzTreeBean con_typ = new EzTreeBean();
		con_typ.setId("CONTRACT_TYP");
		con_typ.setText("合同类型");
		result.add(con_typ);

		EzTreeBean flo_fun = new EzTreeBean();
		flo_fun.setId("FLOW_FUN");
		flo_fun.setText("合同申请流转");
		result.add(flo_fun);

		EzTreeBean biz_typ = new EzTreeBean();
		biz_typ.setId("BIZ_TYP_COD");
		biz_typ.setText("经营类别");
		result.add(biz_typ);

		EzTreeBean fil_sou = new EzTreeBean();
		fil_sou.setId("FILE_SOURCE");
		fil_sou.setText("文件来源");
		result.add(fil_sou);

		EzTreeBean f_typ = new EzTreeBean();
		f_typ.setId("F_TYP");
		f_typ.setText("文件类型");
		result.add(f_typ);

		EzTreeBean cur_cod = new EzTreeBean();
		cur_cod.setId("CURRENCY_COD");
		cur_cod.setText("币别代码");
		result.add(cur_cod);

		EzTreeBean dis_typ = new EzTreeBean();
		dis_typ.setId("DISPATCH_TYP");
		dis_typ.setText("绑定类型");
		result.add(dis_typ);

		EzTreeBean ef_mar = new EzTreeBean();
		ef_mar.setId("EF_MARK");
		ef_mar.setText("空重标记");
		result.add(ef_mar);

		EzTreeBean ie_mar = new EzTreeBean();
		ie_mar.setId("IE_MARK");
		ie_mar.setText("进出口标记");
		result.add(ie_mar);

		EzTreeBean tra_mar = new EzTreeBean();
		tra_mar.setId("TRADE_MARK");
		tra_mar.setText("内外贸标记");
		result.add(tra_mar);

		EzTreeBean tra_way = new EzTreeBean();
		tra_way.setId("TRANS_WAY");
		tra_way.setText("运输类别");
		result.add(tra_way);

		EzTreeBean in_out = new EzTreeBean();
		in_out.setId("IN_OUT_MARK");
		in_out.setText("进出库标记");
		result.add(in_out);

		EzTreeBean tra_mod = new EzTreeBean();
		tra_mod.setId("TRANSPORT_MOD");
		tra_mod.setText("进出港类别");
		result.add(tra_mod);

		EzTreeBean ins_sta = new EzTreeBean();
		ins_sta.setId("INSPECTION_STAT");
		ins_sta.setText("查验状态");
		result.add(ins_sta);

		EzTreeBean io_typ = new EzTreeBean();
		io_typ.setId("IO_TYP");
		io_typ.setText("闸口进出类型");
		result.add(io_typ);

		EzTreeBean oth_wor = new EzTreeBean();
		oth_wor.setId("OTH_WORK_WAY");
		oth_wor.setText("杂作业方式");
		result.add(oth_wor);

		EzTreeBean por_cla = new EzTreeBean();
		por_cla.setId("PORT_CLASS");
		por_cla.setText("港口类别");
		result.add(por_cla);

		EzTreeBean wor_way = new EzTreeBean();
		wor_way.setId("WORK_WAY");
		wor_way.setText("作业类型");
		result.add(wor_way);

		EzTreeBean ser_typ = new EzTreeBean();
		ser_typ.setId("SERVICE_TYP");
		ser_typ.setText("航线类别");
		result.add(ser_typ);

		EzTreeBean tra_typ = new EzTreeBean();
		tra_typ.setId("TRANS_TYP");
		tra_typ.setText("中转类型");
		result.add(tra_typ);

		EzTreeBean ul_mar = new EzTreeBean();
		ul_mar.setId("UL_MARK");
		ul_mar.setText("拆装标记");
		result.add(ul_mar);

		EzTreeBean unl_mar = new EzTreeBean();
		unl_mar.setId("UNLOCK_MARK");
		unl_mar.setText("装卸标记");
		result.add(unl_mar);

		EzTreeBean cnt_sta = new EzTreeBean();
		cnt_sta.setId("CNTR_STAT_COD");
		cnt_sta.setText("箱动态代码");
		result.add(cnt_sta);

		EzTreeBean ber_sta = new EzTreeBean();
		ber_sta.setId("BERTH_STAT");
		ber_sta.setText("泊位状态");
		result.add(ber_sta);

		EzTreeBean req_uni = new EzTreeBean();
		req_uni.setId("REQ_UNIT_COD");
		req_uni.setText("申请方代码");
		result.add(req_uni);

		EzTreeBean cnt_typ = new EzTreeBean();
		cnt_typ.setId("CNTR_CLASS");
		cnt_typ.setText("流向类别");
		result.add(cnt_typ);

		EzTreeBean eq_typ = new EzTreeBean();
		eq_typ.setId("EQ_TYP");
		eq_typ.setText("应急设别");
		result.add(eq_typ);

		EzTreeBean orgn_typ = new EzTreeBean();
		orgn_typ.setId("ORGN_TYP");
		orgn_typ.setText("组织类型");
		result.add(orgn_typ);

		EzTreeBean area_cod = new EzTreeBean();
		area_cod.setId("AREA_COD");
		area_cod.setText("大洲");
		result.add(area_cod);
		
		EzTreeBean por_cod = new EzTreeBean();
		por_cod.setId("PORT_COD");
		por_cod.setText("港口");
		result.add(por_cod);

		EzTreeBean cflow_cod = new EzTreeBean();
		cflow_cod.setId("CFLOW_COD");
		cflow_cod.setText("系统流程");
		result.add(cflow_cod);

		EzTreeBean yard_cod = new EzTreeBean();
		yard_cod.setId("YARD_TYPE");
		yard_cod.setText("库场类型");
		result.add(yard_cod);

		EzTreeBean es_typ = new EzTreeBean();
		es_typ.setId("ES_TYP");
		es_typ.setText("应急资源");
		result.add(es_typ);

		EzTreeBean rent_cod = new EzTreeBean();
		rent_cod.setId("RENT_TYPE");
		rent_cod.setText("库场类型");
		result.add(rent_cod);

		EzTreeBean quick_flg = new EzTreeBean();
		quick_flg.setId("QUICK_FLG");
		quick_flg.setText("速遣标志");
		result.add(quick_flg);

		EzTreeBean ifendFlg = new EzTreeBean();
		ifendFlg.setId("IFEND_FLG");
		ifendFlg.setText("确报");
		result.add(ifendFlg);

		EzTreeBean purposeCod = new EzTreeBean();
		purposeCod.setId("PURPOSE_COD ");
		purposeCod.setText("来港目的");
		result.add(purposeCod);

		EzTreeBean src_flg = new EzTreeBean();
		src_flg.setId("SRC_FLG");
		src_flg.setText("数据来源");
		result.add(src_flg);

		EzTreeBean season_cod = new EzTreeBean();
		season_cod.setId("SEA_COD");
		season_cod.setText("拒绝申请原因");
		result.add(season_cod);

		EzTreeBean shipTyp_Cod = new EzTreeBean();
		shipTyp_Cod.setId("SHIP_KND_COD");
		shipTyp_Cod.setText("船类型");
		result.add(shipTyp_Cod);

		EzTreeBean shr_typ = new EzTreeBean();
		shr_typ.setId("SHIPR_TYPE");
		shr_typ.setText("申报状态");
		result.add(shr_typ);
		
		EzTreeBean bus_mod = new EzTreeBean();
		bus_mod.setId("BUSSINESS_MODULE");
		bus_mod.setText("业务模块");
		result.add(bus_mod);
		return result;
		
		

	}

	@Id
	@UuidGenerator(name = "UUID")
	@GeneratedValue(generator = "UUID")
	@Column(name = "CODE_ID")
	private String codeId;

	@Column(name = "ABANDONED_MARK")
	private String abandonedMark;

	@Column(name = "CODE")
	private String code;

	@Column(name = "CUSTOM_EX1")
	private String customEx1;

	@Column(name = "CUSTOM_EX2")
	private String customEx2;

	@Column(name = "CUSTOM_EX3")
	private String customEx3;

	@Column(name = "CUSTOM_EX4")
	private String customEx4;

	@Column(name = "CUSTOM_EX5")
	private String customEx5;

	@Column(name = "CUSTOM_EX6")
	private String customEx6;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FIELD_COD")
	private String fieldCod;

	@Column(name = "FIELD_NAM")
	private String fieldNam;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EN_NAME")
	private String enName;

	@Column(name = "PARENT_ID")
	private String parentId;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "SORTER")
	private BigDecimal sorter;

	@Column(name = "SYS_MARK")
	private String sysMark;

	public SysCode() {
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getAbandonedMark() {
		return this.abandonedMark;
	}

	public void setAbandonedMark(String abandonedMark) {
		this.abandonedMark = abandonedMark;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCustomEx1() {
		return this.customEx1;
	}

	public void setCustomEx1(String customEx1) {
		this.customEx1 = customEx1;
	}

	public String getCustomEx2() {
		return this.customEx2;
	}

	public void setCustomEx2(String customEx2) {
		this.customEx2 = customEx2;
	}

	public String getCustomEx3() {
		return this.customEx3;
	}

	public void setCustomEx3(String customEx3) {
		this.customEx3 = customEx3;
	}

	public String getCustomEx4() {
		return this.customEx4;
	}

	public void setCustomEx4(String customEx4) {
		this.customEx4 = customEx4;
	}

	public String getCustomEx5() {
		return this.customEx5;
	}

	public void setCustomEx5(String customEx5) {
		this.customEx5 = customEx5;
	}

	public String getCustomEx6() {
		return this.customEx6;
	}

	public void setCustomEx6(String customEx6) {
		this.customEx6 = customEx6;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFieldCod() {
		return this.fieldCod;
	}

	public void setFieldCod(String fieldCod) {
		this.fieldCod = fieldCod;
	}

	public String getFieldNam() {
		return this.fieldNam;
	}

	public void setFieldNam(String fieldNam) {
		this.fieldNam = fieldNam;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSorter() {
		return this.sorter;
	}

	public void setSorter(BigDecimal sorter) {
		this.sorter = sorter;
	}

	public String getSysMark() {
		return this.sysMark;
	}

	public void setSysMark(String sysMark) {
		this.sysMark = sysMark;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	

}