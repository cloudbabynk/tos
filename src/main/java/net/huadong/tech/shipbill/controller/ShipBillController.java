package net.huadong.tech.shipbill.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.EzTreeBean;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.entity.ShipBillRecord;
import net.huadong.tech.shipbill.service.ShipBillService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.Axis2Util;
import net.huadong.tech.util.FtpUpload;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.util.IFCSUMTOJAVA;
import net.huadong.tech.util.PassInfoAck;
import net.huadong.tech.util.XStreamUtil;
import net.huadong.tech.work.entity.CargoInfo;

/**
 * @author
 */
@Controller
@RequestMapping("webresources/login/ship/ShipBill")
public class ShipBillController {

	@Autowired
	private ShipBillService shipBillService;

	@Value("${file.PassInfoAck-path}")
	private String uploadPath;
	public static final String FILE_TYPE = "PASSINFOACK";// 回执发送类型
	public static final String SENDER_CODE = "GGZMT";// 回执发送方代码
	public static final String SENDER_NAME = "GGZMT";// 回执发送方名称
	public static final String SENDER_CODE_HQ = "GHQGZ";// 回执发送方代码
	public static final String SENDER_NAME_HQ = "GHQGZ";// 回执发送方名称
	public static final String RECEIVER_CODE = "0200";// 接受方代码
	public static final String ARRIVAL_PLACE = "0";// 目的地默认为0
	public static final String SEND_FLAG = "1";// 回执发送成功标志
	public static final String I_ID = "I";// 进口标志
	public static final String E_ID = "E";// 出口标志
	public static final String GZMT = "03406500";// 滚装码头代码
	public static final String HQMT = "03409000";// 环球码头代码
	// 菜单进入

	@RequestMapping(value = "/shipbill.htm")
	public String pageTh(String iEId, String tradeId, Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		model.addAttribute("iEId", iEId);
		model.addAttribute("tradeId", tradeId);
		return "system/ship/shipbill";
	}

	// 菜单进入
	@RequestMapping(value = "/bill.htm")
	public String pageB(String iEId, String tradeId, Model model) {
		Random random = new Random();
		model.addAttribute("iEId", iEId);
		model.addAttribute("tradeId", tradeId);
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/bill";
	}

	// 菜单进入
	@RequestMapping(value = "/billquery.htm")
	public String pageBq(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/billquery";
	}
	
	// 海关查询菜单进入
	@RequestMapping(value = "/billquerycustoms.htm")
	public String pageHg(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/billquerycustoms";
	}
	// 边检查询菜单进入
	@RequestMapping(value = "/billqueryinspect.htm")
	public String pageBj(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/billqueryinspect";
	}

	// 进出闸查询菜单进入
	@RequestMapping(value = "/vehiclespassingthrough.htm")
	public String pageVp(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/vehiclespassingthrough";
	}
	// 进出闸查询菜单进入
	@RequestMapping(value = "/vehiclesfrequency.htm")
	public String pageVf(Model model) {
		Random random = new Random();
		model.addAttribute("radi", random.nextInt() * 1000);
		return "system/ship/vehiclesfrequency";
	}
	
	// 商务车资料菜单进入
		@RequestMapping(value = "/cbizcarshow.htm")
		public String cbizcarshow(Model model) {
			Random random = new Random();
			model.addAttribute("radi", random.nextInt() * 1000);
			return "system/ship/cbizcarshow";
		}
		
	// 商务车资料菜单进入
		@RequestMapping(value = "/ctruckshow.htm")
		public String ctruckshow(Model model) {
			Random random = new Random();
			model.addAttribute("radi", random.nextInt() * 1000);
			return "system/ship/ctruckshow";
		}
	
	@RequestMapping(value = "/findShipVoyage")
	@ResponseBody
	public HdEzuiDatagridData findShipVoyage(@RequestBody HdQuery hdQuery) {
		return shipBillService.findShipVoyage(hdQuery);
	}

	@RequestMapping(value = "/findShipBillCar")
	@ResponseBody
	public HdEzuiDatagridData findShipBillCar(@RequestBody HdQuery hdQuery) {
		return shipBillService.findShipBillCar(hdQuery);
	}

	/**
	 * 通用列表查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public HdEzuiDatagridData find(@RequestBody HdQuery hdQuery) {
		return shipBillService.find(hdQuery);
	}

	/**
	 * 舱单查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findSBQuery")
	@ResponseBody
	public HdEzuiDatagridData findSBQuery(@RequestBody HdQuery hdQuery) {
		return shipBillService.findSBQuery(hdQuery);
	}

	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findone")
	@ResponseBody
	public ShipBill findone(String shipbillId) {
		if (HdUtils.strIsNull(shipbillId)) {
			ShipBill shipBill = new ShipBill();
			return shipBill;
		}
		return shipBillService.findone(shipbillId);
	}
	
	
	
	
	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShipBill")
	@ResponseBody
	public ShipBill findShipBill(@RequestBody CargoInfo cargoInfo) {
		return shipBillService.findShipBill(cargoInfo);
	}


	/**
	 * 强制理货
	 */
	@RequestMapping(value = "/dealForce")
	@ResponseBody
	public HdMessageCode dealForce( String shipNos,String billNos) {
		return shipBillService.dealForce(shipNos,billNos);
	}

	/**
	 *确认收到下货纸
	 */
	@RequestMapping(value = "/genPaper")
	@ResponseBody
	public HdMessageCode genPaper( String shipNos,String billNos,String carNums) {
		return shipBillService.genPaper(shipNos,billNos,carNums);
	}



	/**
	 * 下货纸一集齐校验
	 *
	 */

	@RequestMapping(value = "/paperBind")
	@ResponseBody
	public HdMessageCode paperBind( String shipNos,String billNos,String carNums) {
		return shipBillService.paperBind(shipNos,billNos,carNums);
	}



	/**
	 * 实体查询
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findShipBillRecord")
	@ResponseBody
	public ShipBillRecord findShipBillRecord(@RequestBody CargoInfo cargoInfo) {
		return shipBillService.findShipBillRecord(cargoInfo);
	}
	// form保存

	@RequestMapping("/saveone")
	@ResponseBody
	public HdMessageCode saveone(@RequestBody ShipBill shipBill) {

		return shipBillService.saveone(shipBill);
	}

	// 行编辑保存
	@RequestMapping("/save")
	@ResponseBody
	public HdMessageCode save(@RequestBody HdEzuiSaveDatagridData<ShipBill> gridData) {
		// CommonUtil.initEntity(gridData);

		return shipBillService.save(gridData);
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/removeAll")
	@ResponseBody
	public HdMessageCode removeAll(String shipbillId) {
		shipBillService.removeAll(shipbillId);
		return HdUtils.genMsg();
	}

	/**
	 * 船舶状态树
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/gentree", method = RequestMethod.GET)
	@ResponseBody
	public List gentree(String iEId, String tradeId) {
		return shipBillService.findTree(iEId, tradeId);
	}

	/**
	 * 签证单船舶状态树
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/gentreec", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentree(String iEId) {
		return shipBillService.findTreec(iEId);
	}
    //内贸出口海运报告左侧树列表
	@RequestMapping(value = "/gentreerep", method = RequestMethod.GET)
	@ResponseBody
	public List<EzTreeBean> gentreerep() {
		return shipBillService.gentreerep();
	}
	/**
	 * 导入
	 */
	@RequestMapping(value = "/xmlimport")
	@ResponseBody
	public HdMessageCode xmlimport(String filenam, String iEId, String eShipnam, String Voyage) {
		List<String> filenamList = HdUtils.paraseStrs(filenam);
		for (String fnam : filenamList) {
			XStreamUtil.filenam = fnam;
			XStreamUtil.xmlToBean(iEId,eShipnam,Voyage);
		}
		return HdUtils.genMsg();
	}

	/**
	 * 获取海关放心信息getcustominfo
	 */
	@RequestMapping(value = "/getcustominfo")
	@ResponseBody
	public HdMessageCode getCustomInfo(String shipNo, String billNo) {
		Axis2Util axis2Util = new Axis2Util();
		try {
			List<String> shipbillIdList = HdUtils.paraseStrs(shipNo);
	        for (String shipbillid : shipbillIdList) {
	            ShipBill shipbill = JpaUtils.findById(ShipBill.class, shipbillid);
	            axis2Util.getCustomRelease(shipbill.getShipNo(), shipbill.getBillNo());
	            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HdUtils.genMsg();
	}

	
	/**
	 * 获取作业理货数据
	 */
	@RequestMapping(value = "/createData")
	@ResponseBody
	public HdMessageCode createData(String shipNo, String iEId, String tradeId) {
		shipBillService.createData(shipNo,iEId,tradeId);
		return HdUtils.genMsg();
	}

	/**
	 * 发送回执
	 */
	@RequestMapping("/passinfoback")
	@ResponseBody
	public HdMessageCode passinfoback(@RequestBody ShipBill shipBill) {
		PassInfoAck passInfoAck = new PassInfoAck();
		String jpqld = "select a.dockCod from Ship a,ShipBill b where a.shipNo=b.shipNo and b.billNo=:billNo";
		QueryParamLs paramLsd = new QueryParamLs();
		paramLsd.addParam("billNo", shipBill.getBillNo());
		List<String> dockc = JpaUtils.findAll(jpqld, paramLsd);
		if (dockc.size() > 0) {
			if (GZMT.equals(dockc.get(0))) {
				passInfoAck.setSender_Code(SENDER_CODE);
				passInfoAck.setSender_Name(SENDER_NAME);

				passInfoAck.setFile_Type(FILE_TYPE);

				passInfoAck.setReceiver_Code(RECEIVER_CODE);
				// 呼号
				String jpql = "select a.shipImo,a.eShipNam from CShipData a,Ship b where a.shipCodId=b.shipCodId and b.shipNo=:shipNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("shipNo", shipBill.getShipNo());
				List<Object[]> shipCallNamList = JpaUtils.findAll(jpql, paramLs);
				String shipImo = "";
				String eShipNam = "";
				if (null != shipCallNamList && shipCallNamList.size() > 0) {
					for (Object[] obj : shipCallNamList) {
						shipImo = obj[0].toString();
						eShipNam = obj[1].toString();
					}
					passInfoAck.setTransport_id(shipImo);
					passInfoAck.setTransport_name(eShipNam);
				}
				Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
				if (E_ID.equals(shipBill.getiEId())) {
					passInfoAck.setVoyage_no(ship.getEvoyage());
				}
				if (I_ID.equals(shipBill.getiEId())) {
					passInfoAck.setVoyage_no(ship.getIvoyage());
				}
				passInfoAck.setBill_no(shipBill.getBillNo());
				SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String Operatedate = formatt.format(shipBill.getCheckTim());
				String Createdate = formatt.format(shipBill.getReleaseTim());
				passInfoAck.setOperate_date(Operatedate);
				passInfoAck.setCreate_date(Createdate);
				passInfoAck.setIe_flag(shipBill.getiEId());
				passInfoAck.setReal_pack_no(shipBill.getPieces());
				passInfoAck.setReal_gross_wt(shipBill.getWeights());
				passInfoAck.setReal_gross_measure(shipBill.getValumes());
				passInfoAck.setBusinesstype(shipBill.getWorkTyp());
				passInfoAck.setArrivalplace(ARRIVAL_PLACE);
				String xml = XStreamUtil.beanToXml(passInfoAck);
				xml.replaceAll("__", "_");
				System.out.println(xml);
				// 解决XStream对出现双下划线的bug
				// XStream xStreamForRequestPostData = new XStream(new
				// DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
				String jpql2 = "select a from ShipBill a where a.billNo=:billNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("billNo", shipBill.getBillNo());
				List<ShipBill> shipBillList = JpaUtils.findAll(jpql2, paramLs2);
				if (shipBillList.size() > 0) {
					shipBillList.get(0).setSendFlag(SEND_FLAG);
					JpaUtils.update(shipBillList.get(0));
				}
				// 报文名 PassInfoAck_船号_航次_提单_yymmddhhmmss+4位序号.xml
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String tim = format.format(new Date(HdUtils.getDateTime().getTime()));
				// 产生一个四位数字做四位序列号
				int num = (int) (Math.random() * 8999) + 1000;
				String rNum = String.valueOf(num);
				String filenam = "PassInfoAck_" + shipImo + "_" + passInfoAck.getVoyage_no() + passInfoAck.getBill_no()
						+ tim + rNum + ".xml";
				String filePath = uploadPath + filenam;
				File file = new File(filePath);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
					}
				}
				OutputStream ous = null;
				try {
					ous = new FileOutputStream(file);
					ous.write(xml.getBytes());
					ous.flush();
					FtpUpload.uploadPassInfoAck_GZ(filePath, filenam);
				} catch (Exception e1) {
				} finally {
					if (ous != null)
						try {
							ous.close();
							throw new HdRunTimeException("发送成功！");
						} catch (IOException e) {
						}
				}
			}
			if (HQMT.equals(dockc.get(0))) {
				passInfoAck.setSender_Code(SENDER_CODE_HQ);
				passInfoAck.setSender_Name(SENDER_NAME_HQ);

				passInfoAck.setFile_Type(FILE_TYPE);

				passInfoAck.setReceiver_Code(RECEIVER_CODE);
				// 呼号
				String jpql = "select a.shipImo,a.eShipNam from CShipData a,Ship b where a.shipCodId=b.shipCodId and b.shipNo=:shipNo";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("shipNo", shipBill.getShipNo());
				List<Object[]> shipCallNamList = JpaUtils.findAll(jpql, paramLs);
				String shipImo = "";
				String eShipNam = "";
				if (null != shipCallNamList && shipCallNamList.size() > 0) {
					for (Object[] obj : shipCallNamList) {
						shipImo = obj[0].toString();
						eShipNam = obj[1].toString();
					}
					passInfoAck.setTransport_id(shipImo);
					passInfoAck.setTransport_name(eShipNam);
				}
				Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
				if (E_ID.equals(shipBill.getiEId())) {
					passInfoAck.setVoyage_no(ship.getEvoyage());
				}
				if (I_ID.equals(shipBill.getiEId())) {
					passInfoAck.setVoyage_no(ship.getIvoyage());
				}
				passInfoAck.setBill_no(shipBill.getBillNo());
				SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String Operatedate = formatt.format(shipBill.getCheckTim());
				String Createdate = formatt.format(shipBill.getReleaseTim());
				passInfoAck.setOperate_date(Operatedate);
				passInfoAck.setCreate_date(Createdate);
				passInfoAck.setIe_flag(shipBill.getiEId());
				passInfoAck.setReal_pack_no(shipBill.getPieces());
				passInfoAck.setReal_gross_wt(shipBill.getWeights());
				passInfoAck.setReal_gross_measure(shipBill.getValumes());
				passInfoAck.setBusinesstype(shipBill.getWorkTyp());
				passInfoAck.setArrivalplace(ARRIVAL_PLACE);
				String xml = XStreamUtil.beanToXml(passInfoAck);
				String jpql2 = "select a from ShipBill a where a.billNo=:billNo";
				QueryParamLs paramLs2 = new QueryParamLs();
				paramLs2.addParam("billNo", shipBill.getBillNo());
				List<ShipBill> shipBillList = JpaUtils.findAll(jpql2, paramLs2);
				if (shipBillList.size() > 0) {
					shipBillList.get(0).setSendFlag(SEND_FLAG);
					JpaUtils.update(shipBillList.get(0));
				}
				// 报文名 PassInfoAck_船号_航次_提单_yymmddhhmmss+4位序号.xml
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String tim = format.format(new Date(HdUtils.getDateTime().getTime()));
				// 产生一个四位数字做四位序列号
				int num = (int) (Math.random() * 8999) + 1000;
				String rNum = String.valueOf(num);
				String filenam = "PassInfoAck_" + shipImo + "_" + passInfoAck.getVoyage_no() + passInfoAck.getBill_no()
						+ tim + rNum + ".xml";
				String filePath = uploadPath + filenam;
				File file = new File(filePath);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
					}
				}
				OutputStream ous = null;
				try {
					ous = new FileOutputStream(file);
					ous.write(xml.getBytes());
					ous.flush();
					FtpUpload.uploadPassInfoAck_HQ(filePath, filenam);
				} catch (Exception e1) {
				} finally {
					if (ous != null)
						try {
							ous.close();
							throw new HdRunTimeException("发送成功！");
						} catch (IOException e) {
						}
				}
			}
		}

		return HdUtils.genMsg();
	}

	/**
	 * 导入txt(IFCSUM)舱单
	 */
	@RequestMapping(value = "/txtimport")
	@ResponseBody
	public HdMessageCode txtimport(String filenam, String iEId, String eShipNam, String Voyage) {
		List<String> filenamList = HdUtils.paraseStrs(filenam);
		for (String fnam : filenamList) {
			IFCSUMTOJAVA.filenam = fnam;
			IFCSUMTOJAVA.txtToBean(iEId,eShipNam,Voyage);
		}
		return HdUtils.genMsg();
	}

	/*
	 * 取提单相关信息
	 */
	@RequestMapping(value = "/getShipBillInfo")
	@ResponseBody
	public ShipBill getShipBillInfo(String billNo) {
		return shipBillService.getShipBillInfo(billNo);
	}

	/*
	 * 取提单相关信息
	 */
	@RequestMapping(value = "/genbillcar")
	@ResponseBody
	public HdMessageCode genbillcar(String billNo) {
		shipBillService.genbillcar(billNo);
		return HdUtils.genMsg();
	}

	// 同步舱单数据
	@RequestMapping(value = "/async")
	@ResponseBody
	public HdMessageCode async(String shipbillId, String dockCod) {
		shipBillService.async(shipbillId, dockCod);
		return HdUtils.genMsg();
	}

	// 退关
	@RequestMapping(value = "/exitCustom")
	@ResponseBody
	public HdMessageCode exitCustom(String shipbillId) {
		shipBillService.exitCustom(shipbillId);
		return HdUtils.genMsg();
	}
	
	@RequestMapping("/generatebcpc")
	@ResponseBody
	public HdMessageCode generatewq(String billNos) {
		List<String> contractNoList = HdUtils.paraseStrs(billNos);
		for(String billNo:contractNoList){
		String jpql="select a from ShipBill a where a.billNo=:billNo";
		QueryParamLs paramLb = new QueryParamLs();
		paramLb.addParam("billNo",billNo);
		List<ShipBill> shipbillL=JpaUtils.findAll(jpql, paramLb);
		if(shipbillL.size()>0){
			for(ShipBill sb:shipbillL){
				shipBillService.generatebcpc(billNo);
			}
		}
		}
		return  HdUtils.genMsg();		
	}
	@RequestMapping(value = "/searchShipBill")
	@ResponseBody
	public ShipBill searchShipBill(String billNo) {
		return shipBillService.searchShipBill(billNo);
	}
	/**
	 * copy方法
	 */
	@RequestMapping("/copy")
	@ResponseBody
	public HdMessageCode copy(@RequestBody ShipBill shipBill) {

		return shipBillService.copy(shipBill);
	}
	
	/**
	 * 上报货运单证
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/uploadshipbill")
	@ResponseBody
	public String uploadshipbill(String shipbillId,String dockCod) {
		return shipBillService.uploadshipbill(shipbillId,dockCod);
	}
	
	/**
	 * 根据船舶筛选shipbill
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getShipBillByShip")
	@ResponseBody
	public List<ShipBill> getShipBillByShip(String shipNo,String iEId) {
		return shipBillService.getShipBillByShip(shipNo, iEId);
	}
}
