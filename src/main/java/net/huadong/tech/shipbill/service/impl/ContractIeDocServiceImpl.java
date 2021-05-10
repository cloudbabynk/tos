package net.huadong.tech.shipbill.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import net.huadong.tech.shipbill.entity.ContractIeDocQuery;
import org.apache.commons.codec.binary.Base64;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.swetake.util.Qrcode;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.CClientCod;
import net.huadong.tech.base.entity.CDock;
import net.huadong.tech.base.entity.CGate;
import net.huadong.tech.cargo.entity.TruckWork;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.gate.entity.GateTruck;
import net.huadong.tech.gate.entity.GateTruckContract;
import net.huadong.tech.inter.entity.CorpInterContract;
import net.huadong.tech.inter.entity.DailyContractPlan;
import net.huadong.tech.inter.entity.RespInter;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.ContractIeDoc;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.shipbill.service.ContractIeDocService;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.CommonUtil;
import net.huadong.tech.util.HdUtils;
import net.huadong.tech.work.entity.WorkCommand;
import net.huadong.tech.work.entity.WorkCommandBak;
import net.huadong.tech.work.entity.WorkQueue;
import net.sf.json.JSONObject;

/**
 * @author
 */
@Component
public class ContractIeDocServiceImpl implements ContractIeDocService {
	private static final int QRCOLOR = 0xFF000000; // 默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色
	@Value("${tjgjt.service.ip}")
	private String tjgjtServiceIp;

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		//String jpql = "select * from CONTRACT_IE_DOC a where 1=1 ";
		String sql = "SELECT\n" +
				"\tt1.START_DTE || ':00-' || t1.END_DTE || ':00' AS from_To,\n" +
				"\tt2.DOCK_NAM,\n" +
				"\tt3.BRAND_NAM,\n" +
				"\tt4.CAR_KIND_NAM,\n" +
				"\tt5.C_CLIENT_NAM,\n" +
				//"\tt6.CAR_TYP_NAM,\n" +
				"\t( SELECT name FROM SYS_CODE WHERE code = t1.TRADE_ID AND FIELD_COD = 'TRADE_ID' ) AS TRADE_NAM,\n" +
				"\t( SELECT name FROM SYS_CODE WHERE code = t1.I_E_ID AND FIELD_COD = 'I_E_ID' ) AS I_E_NAM,\n" +
				"\t( SELECT name FROM SYS_CODE WHERE code = t1.FLOW AND FIELD_COD = 'FLOW_AREA' ) AS FLOW_NAM,\n" +
				"\t( SELECT name FROM SYS_CODE WHERE code = t1.WORK_WAY AND FIELD_COD = 'WORK_WAY' ) AS WORK_WAY_NAM,\n" +
				"\tnvl( ( SELECT count( a.queue_Id ) cnt FROM Work_Command a WHERE CONTRACT_NO = t1.CONTRACT_NO ), 0 ) AS result_Num,\n" +
				"\tt1.* \n" +
				"FROM\n" +
				"\tContract_Ie_Doc t1,\n" +
				"\tC_DOCK t2,\n" +
				"\tC_Brand t3,\n" +
				"\tC_CAR_KIND t4,\n" +
				"\tC_Client_Cod t5\n" +
				//"\tC_CAR_TYP t6 \n" +
				"WHERE\n" +
				"\tt1.DOCK_COD = t2.DOCK_COD ( + ) \n" +
				//"\tAND t1.CAR_TYP = t6.CAR_TYP \n" +
				"\tAND t1.BRAND = t3.BRAND_COD ( + ) \n" +
				"\tAND t1.CAR_KIND = t4.CAR_KIND ( + ) \n" +
				"\tAND t1.CONSIGN_COD = t5.CLIENT_COD ( + ) \n";
		String dockCod = hdQuery.getStr("dockCod");
		String tradeId = hdQuery.getStr("tradeId");
		String contractTyp = hdQuery.getStr("contractTyp");
		String billNo = hdQuery.getStr("billNo");
		String planArea = hdQuery.getStr("planArea");
		String contractDte = hdQuery.getStr("contractDte");
		String validatDte = hdQuery.getStr("validatDte");
		BigDecimal sum = new BigDecimal(0);
		String validatDte1 = hdQuery.getStr("validatDte1");
		String validatDte2 = hdQuery.getStr("validatDte2");
		String shipNam = hdQuery.getStr("shipNam");
		String voyage = hdQuery.getStr("voyage");
		String brandCod = hdQuery.getStr("brandCod");
		String carTyp = hdQuery.getStr("carTyp");
		String factoryCod = hdQuery.getStr("factoryCod");
		String timeLimit= hdQuery.getStr("timeLimit");

		QueryParamLs paramLs = new QueryParamLs();

		if(HdUtils.strNotNull(shipNam)){
			sql += " and t1.SHIP_NAM like ?2";
			//jpql += " AND t0.vin_no like ?2 ";
			paramLs.addParam(2, '%'+shipNam+'%');
			//paramLs.addParam("shipNam", "%"+shipNam+"%");
		}
		if(HdUtils.strNotNull(voyage)){
			sql += " and t1.VOYAGE like ?3";
			paramLs.addParam(3, "%"+voyage+"%");
		}
		if(HdUtils.strNotNull(factoryCod)){
			sql += " and t1.FACTORY_COD = ?4";
			paramLs.addParam(4, factoryCod);
		}
		if(HdUtils.strNotNull(brandCod)){
			sql += " and t1.BRAND = ?5";
			paramLs.addParam(5, brandCod);
		}
		if(HdUtils.strNotNull(carTyp)){
			sql += " and t1.CAR_TYP = ?6";
			paramLs.addParam(6, carTyp);
		}

		if (HdUtils.strNotNull(dockCod)) {
			sql += " and t1.DOCK_COD = ?7";
			paramLs.addParam(7, dockCod);
		}
		if (HdUtils.strNotNull(tradeId)) {
			sql += " and t1.TRADE_ID = ?8";
			paramLs.addParam(8, tradeId);
		}
		if (HdUtils.strNotNull(contractTyp)) {
			sql += " and t1.CONTRACT_TYP = ?9";
			paramLs.addParam(9, contractTyp);
		}
		if (HdUtils.strNotNull(billNo)) {
			sql += " and t1.bill_No like ?10";
			paramLs.addParam(10,"%"+billNo+"%");
		}
		if (HdUtils.strNotNull(planArea)) {
			sql += " and t1.PLAN_AREA = ?11";
			paramLs.addParam(11, planArea);
		}
		if (HdUtils.strNotNull(hdQuery.getStr("consignCod"))) {
			sql += " and t1.CONSIGN_COD = ?12";
			paramLs.addParam(12, hdQuery.getStr("consignCod"));
		}
		if (HdUtils.strNotNull(validatDte)) {
			/*jpql += "and a.validatDte >=:begDte and a.contractDte <=:endDte ";
			paramLs.addParam("begDte", HdUtils.strToDate(validatDte));
			paramLs.addParam("endDte", HdUtils.addDay( HdUtils.strToDate(validatDte), +1));*/
			sql += " and t1.VALIDAT_DTE = ?13 ";
			paramLs.addParam(13, HdUtils.strToDate(validatDte));
		}
		if (HdUtils.strNotNull(validatDte1)) {
			sql += " and t1.validatDte >=validatDte1 ";
			// jpql += "and a.validatDte =:date ";
			paramLs.addParam("validatDte1", HdUtils.strToDate(validatDte1));
		}
		if (HdUtils.strNotNull(validatDte2)) {
			sql += " and t1.validatDte <=validatDte2 ";
			// jpql += "and a.validatDte =:date ";
			//paramLs.addParam("validatDte2", HdUtils.strToDate(validatDte2));
		}

		if(HdUtils.strNotNull(timeLimit))
		{
			sql+=" AND  T1.REC_TIM > =SYSDATE -7 ";
		}

		sql += "ORDER BY t1.TRADE_ID asc,t1.I_E_ID asc,\n" +
				"\tt1.CONTRACT_NO DESC,\n" +
				"\tt1.REC_TIM DESC" ;
		// return JpaUtils.findByEz(jpql, paramLs , hdQuery);
		//HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
		List<ContractIeDocQuery> contractIeDocList=JpaUtils.findBySql(sql,paramLs,ContractIeDocQuery.class);
		if (contractIeDocList != null) {
		    for (ContractIeDocQuery contractIeDocQuery : contractIeDocList) {
		    	if(contractIeDocQuery.getCarNum() != null){

					sum = sum.add(contractIeDocQuery.getCarNum());
				}
		    }
		}
		/*Collections.sort(contractIeDocList, new Comparator<ContractIeDocQuery>() {

			public int compare(ContractIeDocQuery o1, ContractIeDocQuery o2) {
				// 按照泊位排序
				String a1 = o1.getTradeId() + o1.getiEId() + "";
				String a2 = o1.getShipNam() + "";
				String b1 = o2.getTradeId() + o2.getiEId() + "";
				String b2 = o2.getShipNam() + "";
				if (a1.compareTo(b1) == 0) {
					return a2.compareTo(b2) * (-1);
				}
				return a1.compareTo(b1);
			}
		});*/
		HdEzuiDatagridData result = new HdEzuiDatagridData();
		List<ContractIeDocQuery>  listFoo=new ArrayList<ContractIeDocQuery>();
		ContractIeDocQuery mapFoo=new ContractIeDocQuery();

		mapFoo.setCarNum(sum);
		mapFoo.setContractNo("合计");
		listFoo.add(mapFoo);
		result.setFooter(listFoo);
		result.setRows(contractIeDocList);
		return result;
	}

	@Override
	public HdEzuiDatagridData findShip(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from Ship a where 1=1 ";
		String shipNam = hdQuery.getStr("shipNam");
		String shipCod = hdQuery.getStr("shipCod");
		String ivoyage = hdQuery.getStr("ivoyage");
		String evoyage = hdQuery.getStr("evoyage");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(shipNam)) {
			jpql += "and a.shipNam =:shipNam ";
			paramLs.addParam("shipNam", shipNam);
		}
		if (HdUtils.strNotNull(shipCod)) {
			jpql += "and a.shipCod =:shipCod ";
			paramLs.addParam("shipCod", shipCod);
		}
		if (HdUtils.strNotNull(ivoyage)) {
			jpql += "and a.ivoyage =:ivoyage ";
			paramLs.addParam("ivoyage", ivoyage);
		}
		if (HdUtils.strNotNull(evoyage)) {
			jpql += "and a.evoyage =:evoyage ";
			paramLs.addParam("evoyage", evoyage);
		}
		jpql += "order by a.recTim desc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<ContractIeDoc> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String contractNos) {
		// TODO Auto-generated method stub
		List<String> contractNoList = HdUtils.paraseStrs(contractNos);
		for (String contractNo : contractNoList) {
			JpaUtils.remove(ContractIeDoc.class, contractNo);
		}
	}

	@Override
	public ContractIeDoc findone(String contractNo) {
		// TODO Auto-generated method stub
		ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
		return contractIeDoc;

	}

	public String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	public String editSaveContractIeDoc(ContractIeDoc contractIeDoc) {
		if (contractIeDoc.getCzId().equals("1")) {
			// 存栈
			contractIeDoc.setShipNo("20190311082013");
			contractIeDoc.setShipNam("存栈");
			contractIeDoc.setVoyage("");
		}
		String shipNam = contractIeDoc.getShipNam();
		String voyage = contractIeDoc.getVoyage();
		String billNo = contractIeDoc.getBillNo();
		String consignNam = contractIeDoc.getConsignNam();
		BigDecimal carNum = contractIeDoc.getCarNum();
		String planArea = contractIeDoc.getPlanArea();
		String brandCod = contractIeDoc.getBrand();
		String productName = shipNam + '-' + voyage + '-' + billNo + '-' + consignNam + '-' + brandCod + '-' + carNum
				+ '-' + planArea;
		String filepath = null;
		String qrcodePath = getLogoQRCode(contractIeDoc.getContractNo(), productName, filepath);
		contractIeDoc.setQrcodePath(qrcodePath);
		// String qrcodePath=QRCodePath(contractIeDoc.getContractNo());
		// contractIeDoc.setQrcodePath(qrcodePath);
		JpaUtils.update(contractIeDoc);
		return null;
	}

	@Override
	public HdMessageCode saveone(@RequestBody ContractIeDoc contractIeDoc) {
		// TODO Auto-generated method stub

		// 计划数和实际数校验 brandCod shipNo billNo 疏港
		// if(HdUtils.strNotNull(contractIeDoc.getWorkWay())) {
		// if(contractIeDoc.getWorkWay().equals("2")) {
		// String portCarJpql = "SELECT p FROM PortCar p where p.currentStat =
		// '2'";
		// QueryParamLs portCarParams = new QueryParamLs();
		// if(HdUtils.strNotNull(contractIeDoc.getShipNo())) {
		// portCarJpql += " and p.shipNo =:shipNo";
		// portCarParams.addParam("shipNo", contractIeDoc.getShipNo());
		// if(HdUtils.strNotNull(contractIeDoc.getBillNo())) {
		//// if(!contractIeDoc.getBillNo().equals("--")) {
		// portCarJpql += " and p.billNo =:billNo";
		// portCarParams.addParam("billNo", contractIeDoc.getBillNo());
		//// }
		// }
		// }
		// if(HdUtils.strNotNull(contractIeDoc.getBrand())) {
		// portCarJpql += " and p.brandCod =:brandCod";
		// portCarParams.addParam("brandCod", contractIeDoc.getBrand());
		// }
		// List<PortCar> portCarList = JpaUtils.findAll(portCarJpql,
		// portCarParams);
		// if(contractIeDoc.getCarNum() != null) {
		// if(portCarList.size() < contractIeDoc.getCarNum().intValue()) {
		// throw new HdRunTimeException("实际车数少于计划车数请核实!");
		// }
		// }
		// }
		// }

		String contractNo = contractIeDoc.getContractNo();
		ContractIeDoc contractiedoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
		if (contractiedoc != null) {
			// 编辑
			JpaUtils.update(contractIeDoc);
			if (contractIeDoc.getWorkWay().equals("4")) {
				contractIeDoc.setContractTyp("4");
			}

			// 校验是否确认
			String wqJpql = "SELECT w FROM WorkQueue w where w.contractNo=:contractNo";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("contractNo", contractNo);
			List<WorkQueue> workQueueList = JpaUtils.findAll(wqJpql, paramLs);
			if (workQueueList.size() > 0) {
				// 校验是否有理货数据
				String wkJpql = "SELECT w FROM WorkCommand w where w.contractNo=:contractNo";
				QueryParamLs wkParamLs = new QueryParamLs();
				wkParamLs.addParam("contractNo", contractNo);
				List<WorkCommand> workCommandList = JpaUtils.findAll(wkJpql, wkParamLs);
				if (workCommandList.size() > 0) {
					throw new HdRunTimeException("已有理货数据，请注意！");
				} else {
					// 删除作业队列
					JpaUtils.removeAll(workQueueList);
					ContractIeDoc contractIeDocRe = JpaUtils.findById(ContractIeDoc.class, contractNo);
					editSaveContractIeDoc(contractIeDoc);
					// 重新生成作业队列
					createWq(contractNo);
				}
			} else {
				editSaveContractIeDoc(contractIeDoc);
			}

		} else {
			// 增加
			if (contractIeDoc.getWorkWay().equals("4")) {
				contractIeDoc.setContractTyp("4");
			}
			String jpql = "select a from ContractIeDoc a where 1=1";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam(null);
			List<ContractIeDoc> contractiedocList = JpaUtils.findAll(jpql, null);
			List<Integer> list = new ArrayList<Integer>();
			if (contractiedocList.size() != 0) {
				for (ContractIeDoc c : contractiedocList) {
					list.add(Integer.valueOf(c.getContractNo()));
				}
				Integer maxContractNo = Collections.max(list) + 1;
				String maxN = addZeroForNum(maxContractNo.toString(), 8);
				contractIeDoc.setContractNo(maxN);
			} else {
				contractIeDoc.setContractNo("00000001");
			}
			// 默认计划确认为0
			// contractIeDoc.setConfirmId("0");
			if (contractIeDoc.getCzId().equals("1")) {
				// 存栈
				contractIeDoc.setShipNo("20190311082013");
				contractIeDoc.setShipNam("存栈");
				contractIeDoc.setVoyage("");
			}
			JpaUtils.save(contractIeDoc);
			String shipNam = contractIeDoc.getShipNam();
			String voyage = contractIeDoc.getVoyage();
			String billNo = contractIeDoc.getBillNo();
			String consignNam = contractIeDoc.getConsignNam();
			BigDecimal carNum = contractIeDoc.getCarNum();
			String planArea = contractIeDoc.getPlanArea();
			String brandCod = contractIeDoc.getBrand();
			String productName = shipNam + '-' + voyage + '-' + billNo + '-' + consignNam + '-' + brandCod + '-'
					+ carNum + '-' + planArea;
			String filepath = null;
			String qrcodePath = getLogoQRCode(contractIeDoc.getContractNo(), productName, filepath);
			contractIeDoc.setQrcodePath(qrcodePath);
			// String qrcodePath=QRCodePath(contractIeDoc.getContractNo());
			// contractIeDoc.setQrcodePath(qrcodePath);
			JpaUtils.update(contractIeDoc);
		}
		return HdUtils.genMsg();
	}

	public String getLogoQRCode(String contractNo, String productName, String filepath) {
		// String filePath =
		// (javax.servlet.http.HttpServletRequest)request.getSession().getServletContext().getRealPath("/")
		// + "resources/images/logoImages/llhlogo.png";
		// filePath是二维码logo的路径，但是实际中我们是放在项目的某个路径下面的，所以路径用上面的，把下面的注释就好
		// String filePath = "C:/Users/WSW/Desktop/IMG_2466.jpg"; //TODO
		String path = null;
		String content = contractNo;
		String pngnam = contractNo + ".png";
		// path="./src/main/resources/static/qrcodepng";
		path = "c:/tjroro/qrcodepng";
		filepath = path + "/" + pngnam;
		try {

			BufferedImage bim = getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 400, 400, getDecodeHintType());
			return addLogo_QRCode(bim, productName, filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height,
			Map<EncodeHintType, ?> hints) {
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return image;
	}

	public String addLogo_QRCode(BufferedImage bim, String productName, String filepath) {
		try {
			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = bim;

			Graphics2D g = image.createGraphics();

			// 把商品名称添加上去，商品名称不要太长哦，这里最多支持两行。太长就会自动截取啦
			if (productName != null && !productName.equals("")) {
				// 新的图片，把带logo的二维码下面加上文字
				BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D outg = outImage.createGraphics();
				outg.setBackground(Color.BLACK);
				// 画二维码到新的面板
				outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
				// 画文字到新的面板
				outg.setColor(Color.blue);
				outg.setFont(new Font("宋体", Font.BOLD, 25)); // 字体、字型、字号
				int strWidth = outg.getFontMetrics().stringWidth(productName);
				if (strWidth > 399) {
					// //长度过长就截取前面部分
					// outg.drawString(productName, 0, image.getHeight() +
					// (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
					// 长度过长就换行
					int j = productName.indexOf("-", productName.indexOf("-", productName.indexOf("-") + 1) + 1);
					String productName1 = productName.substring(0, j);
					String productName2 = productName.substring(j + 1, productName.length());
					int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
					int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
					outg.drawString(productName1, 200 - strWidth1 / 2,
							image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 + 12);
					BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D outg2 = outImage2.createGraphics();
					outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
					outg2.setColor(Color.blue);
					outg2.setFont(new Font("宋体", Font.BOLD, 25)); // 字体、字型、字号
					outg2.drawString(productName2, 200 - strWidth2 / 2,
							outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 5);
					outg2.dispose();
					outImage2.flush();
					outImage = outImage2;
				} else {
					outg.drawString(productName, 200 - strWidth / 2,
							image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 + 12); // 画文字
				}
				outg.dispose();
				outImage.flush();
				image = outImage;
			}
			// logo.flush();
			image.flush();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.flush();
			ImageIO.write(image, "png", baos);

			// 二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
			// 可以看到这个方法最终返回的是这个二维码的imageBase64字符串
			// 前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>
			// 其中${imageBase64QRCode}对应二维码的imageBase64字符串

			ImageIO.write(image, "png", new File(filepath));

			String imageBase64QRCode = Base64.encodeBase64URLSafeString(baos.toByteArray());

			baos.close();
			filepath = filepath.substring(10, filepath.length());
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<EncodeHintType, Object> getDecodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		return hints;
	}

	private String QRCodePath(String contractNo) {
//		Qrcode qrcode = new Qrcode();
//		qrcode.setQrcodeErrorCorrect('M');// 纠错等级（分为L、M、H三个等级）
//		qrcode.setQrcodeEncodeMode('B');// N代表数字，A代表a-Z，B代表其它字符
//		qrcode.setQrcodeVersion(7);// 版本
		ContractIeDoc contractIeDoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
		String shipNam = contractIeDoc.getShipNam();
		String voyage = contractIeDoc.getVoyage();
		String billNo = contractIeDoc.getBillNo();
		String brandCod = contractIeDoc.getBrand();
		BigDecimal carNum = contractIeDoc.getCarNum();
		String planArea = contractIeDoc.getPlanArea();
		// 生成二维码中要存储的信息
		String qrData = contractNo + '-' + shipNam + '-' + voyage + '-' + billNo + '-' + brandCod + '-' + carNum + '-'
				+ planArea;
		String path = null;
		// 设置一下二维码的像素
		int width = 67 + 12 * (7 - 1);
		int height = 67 + 12 * (7 - 1);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 绘图
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, width, height);// 清除下画板内容

		// 设置下偏移量,如果不加偏移量，有时会导致出错。
		int pixoff = 2;

		byte[] d = null;
		try {
			d = qrData.getBytes("gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (d.length > 0 && d.length < 120) {
//			boolean[][] s = qrcode.calQrcode(d);
//			for (int i = 0; i < s.length; i++) {
//				for (int j = 0; j < s.length; j++) {
//					if (s[j][i]) {
//						gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//					}
//				}
//			}
//		}
		gs.dispose();
		bufferedImage.flush();
		try {
			String pngnam = contractNo + ".png";
			path = "./qrcodepng";
			String filePath = path + File.separator + pngnam;
			ImageIO.write(bufferedImage, "png", new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public HdMessageCode findContractIeDoc(String contractNo) {
		if (HdUtils.strNotNull(contractNo)) {
			ContractIeDoc contractiecoc = JpaUtils.findById(ContractIeDoc.class, contractNo);
			if (contractiecoc != null) {
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			}
		}
		return HdUtils.genMsg();
	}

	public HdMessageCode backinfo(String billNo) {
		if (HdUtils.strNotNull(billNo)) {
			String jpql = "select a from ShipBill a where 1=1 and a.billNo=:billNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("billNo", billNo);
			List<ShipBill> shipBillList = JpaUtils.findAll(jpql, paramLs);
			for (ShipBill shipbill : shipBillList) {
				if (shipbill != null) {

				}
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public HdMessageCode checkBefDel(String contractNo) {
		if (HdUtils.strNotNull(contractNo)) {
			String jpql = "select a from WorkQueue a where 1=1 and a.contractNo=:contractNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("contractNo", contractNo);
			List<WorkQueue> workQueueList = JpaUtils.findAll(jpql, paramLs);
			for (WorkQueue wq : workQueueList) {
				if (wq != null) {
					throw new HdRunTimeException("正在作业！不能删除！");
				}
			}
			String jpql2 = "select a from WorkCommand a where 1=1 and a.contractNo=:contractNo ";
			QueryParamLs paramLs2 = new QueryParamLs();
			paramLs2.addParam("contractNo", contractNo);
			List<WorkCommand> workCommandList = JpaUtils.findAll(jpql2, paramLs2);
			for (WorkCommand wc : workCommandList) {
				if (wc != null) {
					throw new HdRunTimeException("该计划有作业数据！不能删除！");
				}
			}
			String jpql3 = "select a from WorkCommandBak a where 1=1 and a.contractNo=:contractNo ";
			QueryParamLs paramLs3 = new QueryParamLs();
			paramLs3.addParam("contractNo", contractNo);
			List<WorkCommandBak> workCommandBakList = JpaUtils.findAll(jpql3, paramLs3);
			for (WorkCommandBak wcb : workCommandBakList) {
				if (wcb != null) {
					throw new HdRunTimeException("该计划有历史作业数据！不能删除！");
				}
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public List<Ship> savename(HdQuery hdQuery) {
		String jpql = "select a from Ship a where  a.shipNam=:cShipNam or a.shipCod=:shipCod or a.ivoyage=:voyage or a.evoyage=:voyage ";
		String cShipNam = hdQuery.getStr("cShipNam");
		String shipCod = hdQuery.getStr("shipCod");
		String voyage = hdQuery.getStr("voyage");
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("cShipNam", cShipNam);
		paramLs.addParam("shipCod", shipCod);
		paramLs.addParam("voyage", voyage);
		List<Ship> shipList = JpaUtils.findAll(jpql, paramLs);
		return shipList;
	}

	// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
	public static String myEmailAccount = "";
	public static String myEmailPassword = "";

	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
	// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
	public static String myEmailSMTPHost = "smtp.163.com";

	// 收件人邮箱（替换为自己知道的有效邮箱）
	public static String receiveMailAccount = "";

	@Override
	public void sendEmail(String fromWho, String password, String toWho, String attach) {
		myEmailAccount = fromWho;
		myEmailPassword = password;
		receiveMailAccount = toWho;
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("userName", "zhongkuiboy@163.com");
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP
																// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		// 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message;
		try {
			message = createMimeMessage(session, myEmailAccount, receiveMailAccount, props, attach);
			// 4. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();

			// 5. 使用 邮箱账号 和 密码 连接邮件服务器
			// 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
			transport.connect(myEmailAccount, myEmailPassword);

			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
			// 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			// 7. 关闭连接
			transport.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 也可以保持到本地查看
		// message.writeTo(file_out_put_stream);

	}

	/**
	 * 创建一封复杂邮件（文本+图片+附件）
	 */
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, Properties p,
			String attach) throws Exception {
		// 1. 创建邮件对象
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, "滚装码头", "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.addRecipient(RecipientType.CC, new InternetAddress(p.getProperty("userName"), "滚装", "UTF-8"));
		message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "客户", "UTF-8"));

		// 4. Subject: 邮件主题
		message.setSubject("委托计划", "UTF-8");

		/*
		 * 下面是邮件内容的创建:
		 */

		// 5. 创建图片“节点”
		// MimeBodyPart image = new MimeBodyPart();
		// DataHandler dh = new DataHandler(new
		// FileDataSource("c:\\tjroro\\qrcodepng\\00000030.png")); // 读取本地文件
		// image.setDataHandler(dh); // 将图片数据添加到“节点”
		// image.setContentID("image_fairy_tail"); //
		// 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）

		// 6. 创建文本“节点”
		MimeBodyPart text = new MimeBodyPart();
		// 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
		text.setContent("尊敬的用户:您好，这是你的委托计划二维码图片，请注意查收!", "text/html;charset=UTF-8");

		// 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		// mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related"); // 关联关系

		// 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
		// 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
		// 上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);

		// 9. 创建附件“节点”
		MimeBodyPart attachment = new MimeBodyPart();
		String path = "c:\\tjroro\\qrcodepng\\" + attach;
		DataHandler dh2 = new DataHandler(new FileDataSource(path)); // 读取本地文件
		attachment.setDataHandler(dh2); // 将附件数据添加到“节点”
		attachment.setFileName(MimeUtility.encodeText(dh2.getName())); // 设置附件的文件名（需要编码）

		// 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_image);
		mm.addBodyPart(attachment); // 如果有多个附件，可以创建多个多次添加
		mm.setSubType("mixed"); // 混合关系

		// 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
		message.setContent(mm);

		// 12. 设置发件时间
		message.setSentDate(new Date());

		// 13. 保存上面的所有设置
		message.saveChanges();

		return message;
	}

	@Override
	public ContractIeDoc getShipBillInfo(String contractNo) {
		String jpql = "select a from ContractIeDoc a where a.contractNo=:contractNo ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("contractNo", contractNo);
		List<ContractIeDoc> contractiedocList = JpaUtils.findAll(jpql, paramLs);
		ContractIeDoc s = new ContractIeDoc();
		if (contractiedocList.size() > 0) {
			for (ContractIeDoc sb : contractiedocList) {
				s.setCarNum(sb.getCarNum());
				s.setiEId(sb.getiEId());
				s.setBillNo(sb.getBillNo());
			}
		}
		return s;
	}

	public String getMaxContractNo() {
		String jpql = "select a from ContractIeDoc a where 1=1";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam(null);
		List<ContractIeDoc> contractiedocList = JpaUtils.findAll(jpql, null);
		List<Integer> list = new ArrayList<Integer>();
		String maxN = null;
		if (contractiedocList.size() != 0) {
			for (ContractIeDoc c : contractiedocList) {
				list.add(Integer.valueOf(c.getContractNo()));
			}
			Integer maxContractNo = Collections.max(list) + 1;
			maxN = addZeroForNum(maxContractNo.toString(), 8);

		}
		return maxN;
	}

	@Override
	public HdMessageCode copy(ContractIeDoc contractIeDoc) {
		String maxContractNo = getMaxContractNo();
		contractIeDoc.setContractNo(maxContractNo);
		JpaUtils.save(contractIeDoc);
		return HdUtils.genMsg();
	}

	@Override
	public Ship getShipDockCod(String shipNo) {
		Ship ship = JpaUtils.findById(Ship.class, shipNo);
		return ship;
	}

	@Override
	public List<Map<String, Object>> getTrueCarNum(String shipNo) {
		String portCarSql = "SELECT p FROM PortCar p where p.currentStat = '2'";
		QueryParamLs portCarParams = new QueryParamLs();
		portCarSql += " and p.shipNo =:shipNo";
		List<Map<String, Object>> list = JpaUtils.getEntityManager().createNativeQuery(portCarSql)
				.setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
		return null;
	}

	// 生成作业队列
	public void createWq(String contractNo) {
		// 虚拟拖车号
		String truckNo = "";
		String jpql = "select a from ContractIeDoc a where a.contractNo=:contractNo ";
		QueryParamLs paramLs = new QueryParamLs();
		paramLs.addParam("contractNo", contractNo);
		List<ContractIeDoc> contractiedocListc = JpaUtils.findAll(jpql, paramLs);
		String gateNo = "";
		if (contractiedocListc.size() > 0) {
			if (contractiedocListc.get(0).getDockCod().equals("03406500")) {
				gateNo = "R1";
			}
			if (contractiedocListc.get(0).getDockCod().equals("03409000")) {
				gateNo = "G5";
			}
			CGate cGate = JpaUtils.findById(CGate.class, gateNo);

			if (!contractiedocListc.get(0).getDockCod().equals(cGate.getDockCod())) {
				throw new HdRunTimeException("委托作业码头与当前不符!");
			}
			GateTruckContract gateTruckContract = new GateTruckContract();
			gateTruckContract.setContractNo(contractNo);
			gateTruckContract.setPlanNum(contractiedocListc.get(0).getCarNum());
			String platNo = "";

			String dockCod = "";
			if ("03406500".equals(contractiedocListc.get(0).getDockCod())) {
				dockCod = "G";
			}
			if ("03409000".equals(contractiedocListc.get(0).getDockCod())) {
				dockCod = "H";
			}
			if ("1".equals(contractiedocListc.get(0).getContractTyp())) {
				truckNo = CommonUtil.getId().substring(0, 8) + "A" + dockCod;
				platNo = CommonUtil.getId().substring(0, 8) + "A" + dockCod;
				gateTruckContract.setCarryId("A");
			} else if ("2".equals(contractiedocListc.get(0).getContractTyp())) {
				truckNo = CommonUtil.getId().substring(0, 8) + "T" + dockCod;
				platNo = CommonUtil.getId().substring(0, 8) + "T" + dockCod;
				gateTruckContract.setCarryId("T");
			}
			String workqueueno = contractiedocListc.get(0).getContractNo() + "-" + gateTruckContract.getCarryId()
					+ platNo;
			WorkQueue wk = JpaUtils.findById(WorkQueue.class, workqueueno);
			if (wk == null) {
				if ("1".equals(contractiedocListc.get(0).getContractTyp())) {
					gateTruckContract.setCarryId("A");
					WorkQueue workQueue = new WorkQueue();
					workQueue.setWorkQueueNo(
							contractiedocListc.get(0).getContractNo() + "-" + gateTruckContract.getCarryId() + truckNo);
					String shipNoTest = (String) contractiedocListc.get(0).getShipNo();
					if (HdUtils.strNotNull(shipNoTest)) {
						workQueue.setShipNo(contractiedocListc.get(0).getShipNo());
					}
					workQueue.setWorkTyp("TI");
					workQueue.setContractNo(gateTruckContract.getContractNo());
					workQueue.setTruckNo(truckNo);
					workQueue.setRemarks("集港" + "-" + platNo + "-" + gateTruckContract.getContractNo());
					workQueue.setRecNam(HdUtils.getCurUser().getAccount());
					workQueue.setRecTim(HdUtils.getDateTime());
					JpaUtils.save(workQueue);
				} else if ("2".equals(contractiedocListc.get(0).getContractTyp())) {
					gateTruckContract.setCarryId("T");
					WorkQueue workQueue = new WorkQueue();
					workQueue.setWorkQueueNo(
							contractiedocListc.get(0).getContractNo() + "-" + gateTruckContract.getCarryId() + truckNo);

					String shipNoTest = (String) contractiedocListc.get(0).getShipNo();
					if (HdUtils.strNotNull(shipNoTest)) {
						workQueue.setShipNo(contractiedocListc.get(0).getShipNo());
					}

					workQueue.setWorkTyp("TO");
					workQueue.setContractNo(gateTruckContract.getContractNo());
					workQueue.setTruckNo(truckNo);
					workQueue.setRemarks("疏港" + "-" + platNo + "-" + gateTruckContract.getContractNo());
					workQueue.setRecNam(HdUtils.getCurUser().getAccount());
					workQueue.setRecTim(HdUtils.getDateTime());
					JpaUtils.save(workQueue);
				}
			}
		}
	}

	// 单条确认
	@Transactional
	public void confirmingateSingle(String contractNo) {
		// 虚拟拖车号
		String gateNo = "";
		String dockCod = "";
		String truckNo = "";
		String platNo = "";
		ContractIeDoc bean = JpaUtils.findById(ContractIeDoc.class, contractNo);
		
		if (bean.getDockCod().equals("03406500")) {
			gateNo = "R1";
		}
		if (bean.getDockCod().equals("03409000")) {
			gateNo = "G5";
		}
		CGate cGate = JpaUtils.findById(CGate.class, gateNo);
		if (!bean.getDockCod().equals(cGate.getDockCod())) {
			throw new HdRunTimeException("委托作业码头与当前不符!");
		}
		
		
		GateTruckContract gateTruckContract = new GateTruckContract();
		gateTruckContract.setContractNo(contractNo);
		gateTruckContract.setPlanNum(bean.getCarNum());
		

		
		if ("03406500".equals(bean.getDockCod())) {
			dockCod = "G";
		}
		if ("03409000".equals(bean.getDockCod())) {
			dockCod = "H";
		}
		if ("1".equals(bean.getContractTyp())) {
			truckNo = CommonUtil.getId().substring(0, 8) + "A" + dockCod;
			platNo = CommonUtil.getId().substring(0, 8) + "A" + dockCod;
			gateTruckContract.setCarryId("A");
		} else if ("2".equals(bean.getContractTyp())) {
			truckNo = CommonUtil.getId().substring(0, 8) + "T" + dockCod;
			platNo = CommonUtil.getId().substring(0, 8) + "T" + dockCod;
			gateTruckContract.setCarryId("T");
		}
		String workqueueno = bean.getContractNo() + "-" + gateTruckContract.getCarryId() + platNo;
		WorkQueue wk = JpaUtils.findById(WorkQueue.class, workqueueno);
		if (wk == null) {
			if ("1".equals(bean.getContractTyp())) {
				gateTruckContract.setCarryId("A");
				WorkQueue workQueue = new WorkQueue();
				workQueue.setWorkQueueNo(
						bean.getContractNo() + "-" + gateTruckContract.getCarryId() + truckNo);
				String shipNoTest = (String) bean.getShipNo();
				if (HdUtils.strNotNull(shipNoTest)) {
					workQueue.setShipNo(bean.getShipNo());
				}
				workQueue.setWorkTyp("TI");
				workQueue.setContractNo(gateTruckContract.getContractNo());
				workQueue.setTruckNo(truckNo);
				workQueue.setRemarks("集港" + "-" + platNo + "-" + gateTruckContract.getContractNo());
				workQueue.setRecNam(HdUtils.getCurUser().getAccount());
				workQueue.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(workQueue);
			} else if ("2".equals(bean.getContractTyp())) {
				gateTruckContract.setCarryId("T");
				WorkQueue workQueue = new WorkQueue();
				workQueue.setWorkQueueNo(
						bean.getContractNo() + "-" + gateTruckContract.getCarryId() + truckNo);

				String shipNoTest = (String) bean.getShipNo();
				if (HdUtils.strNotNull(shipNoTest)) {
					workQueue.setShipNo(bean.getShipNo());
				}

				workQueue.setWorkTyp("TO");
				workQueue.setContractNo(gateTruckContract.getContractNo());
				workQueue.setTruckNo(truckNo);
				workQueue.setRemarks("疏港" + "-" + platNo + "-" + gateTruckContract.getContractNo());
				workQueue.setRecNam(HdUtils.getCurUser().getAccount());
				workQueue.setRecTim(HdUtils.getDateTime());
				JpaUtils.save(workQueue);
			}
			BigDecimal num = new BigDecimal("0");
			gateTruckContract.setWorkNum(num);

			GateTruck gateTruck = new GateTruck();
			String jpqlx = "select a from GateTruck a where a.truckNo=:truckNo";
			QueryParamLs paramLsx = new QueryParamLs();
			paramLsx.addParam("truckNo", truckNo);
			List<GateTruck> gateTruckList = JpaUtils.findAll(jpqlx, paramLsx);
			if (gateTruckList.size() > 0) {
				String jpqlc = "select a from GateTruckContract a where a.ingateId=:ingateId";
				QueryParamLs paramLsc = new QueryParamLs();
				paramLsc.addParam("ingateId", gateTruckList.get(0).getIngateId());
				List<GateTruckContract> gateTruckContractList = JpaUtils.findAll(jpqlc, paramLsc);
				if (gateTruckContractList.size() > 0) {
					gateTruckContract.setIngateId(gateTruckList.get(0).getIngateId());
					gateTruckContract.setTruckNo(gateTruckList.get(0).getTruckNo());
					gateTruckContract.setBillNo(bean.getBillNo());
					JpaUtils.save(gateTruckContract);
				}
			} else {
				gateTruck.setSingleId("0");
				gateTruck.setTruckNo(truckNo);
				gateTruck.setPlatNo(platNo);
				gateTruck.setInGatNo(gateNo);
				gateTruck.setInRecNam(HdUtils.getCurUser().getAccount());
				gateTruck.setInGatTim(HdUtils.getDateTime());
				gateTruck.setDockCod(cGate.getDockCod());
				gateTruck.setFinishedId("0");
				JpaUtils.save(gateTruck);
				gateTruckContract.setIngateId(gateTruck.getIngateId());
				gateTruckContract.setTruckNo(truckNo);
				gateTruckContract.setBillNo(bean.getBillNo());
				JpaUtils.save(gateTruckContract);
			}
			bean.setConfirmId("1");
			JpaUtils.update(bean);
		} 
	}

	public void confirmingate(String contractNos) {
		List<String> contractNoList = HdUtils.paraseStrs(contractNos);
		for (String contractNo : contractNoList) {
			ContractIeDoc bean = JpaUtils.findById(ContractIeDoc.class, contractNo);
			if ("1".equals(bean.getConfirmId())) {
				continue;
			} else {
				confirmingateSingle(contractNo);
			}
		}
	}

	@Override
	public CCarTyp findBrandKind(String carTyp) {
		CCarTyp cCarTyp = JpaUtils.findById(CCarTyp.class, carTyp);
		return cCarTyp;
	}

	/**
	 * (货运计划)上报计划到局调
	 */
	public String sendData(String contractNos) {
		String message = "";
		List<String> contractIeDocList = HdUtils.paraseStrs(contractNos);
		for (String contractno : contractIeDocList) {
			ContractIeDoc contractiedoc = JpaUtils.findById(ContractIeDoc.class, contractno);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cmdId", "2018");
			jsonObj.put("coId", Ship.GZ);
			// if ("03406500".equals(contractiedoc.getDockCod())) {
			// jsonObj.put("coId", "03406500");
			// }
			// if ("03409000".equals(contractiedoc.getDockCod())) {
			// jsonObj.put("coId", "03406500");
			//// jsonObj.put("coId", "03409000");
			// }
			Map<String, String> map = new HashMap<String, String>();
			CorpInterContract corpInterContract = new CorpInterContract();
			corpInterContract.setInformId(contractiedoc.getContractNo());
			map.put("informId", corpInterContract.getInformId());
			if (contractiedoc.getContractTyp().equals("1")) {
				corpInterContract.setInformType("JG");
			}
			if (contractiedoc.getContractTyp().equals("2")) {
				corpInterContract.setInformType("SG");
			}
			map.put("informType", corpInterContract.getInformType());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			corpInterContract.setBillDate(format.format(contractiedoc.getContractDte()));
			map.put("billDate", format.format(contractiedoc.getContractDte()));
			// 长期计划，1长期，0短期
			corpInterContract.setLongPlanFlag("0");
			map.put("longPlanFlag", corpInterContract.getLongPlanFlag());
			// 说明:如果公司生产系统无‘货运单证’数据，cargoID值赋值为‘*’
			String tradeId = contractiedoc.getTradeId();
			String iEId = contractiedoc.getiEId();
			if (!contractiedoc.getBillNo().equals("--")) {
				String jpqlk = "select a from ShipBill a where a.tradeId=:tradeId and a.iEId=:iEId and a.billNo like :billNo ";
				QueryParamLs paramLsk = new QueryParamLs();
				paramLsk.addParam("tradeId", tradeId);
				paramLsk.addParam("iEId", iEId);
				paramLsk.addParam("billNo", '%' + contractiedoc.getBillNo() + '%');
				List<ShipBill> shipbillL = JpaUtils.findAll(jpqlk, paramLsk);
				if (shipbillL.size() > 0) {
					corpInterContract.setCargoId(shipbillL.get(0).getShipbillId());
					map.put("cargoID", "*");
					// map.put("cargoID", corpInterContract.getCargoId());
					corpInterContract.setShipId(shipbillL.get(0).getShipNo());
					Ship ship = JpaUtils.findById(Ship.class, contractiedoc.getShipNo());
					if ("I".equals(contractiedoc.getiEId())) {
						map.put("shipId", ship.getNewIShipId());
						map.put("svoyageId", ship.getNewGroupShipNo());
						corpInterContract.setShipName(contractiedoc.getShipNam());
						map.put("shipName", corpInterContract.getShipName());
						corpInterContract.setVoyage(contractiedoc.getVoyage());
						map.put("voyage", ship.getIvoyage());
					} else if ("E".equals(contractiedoc.getiEId())) {
						map.put("shipId", ship.getNewEShipId());
						map.put("svoyageId", ship.getNewGroupShipNo());
						corpInterContract.setShipName(contractiedoc.getShipNam());
						map.put("shipName", corpInterContract.getShipName());
						corpInterContract.setVoyage(contractiedoc.getVoyage());
						map.put("voyage", ship.getEvoyage());
					}
					// 我们的内贸外属性和集团的正好相反
					if ("1".equals(contractiedoc.getTradeId())) {
						map.put("tradeFlag", "2");
					} else if ("2".equals(contractiedoc.getTradeId())) {
						map.put("tradeFlag", "1");
					}
					corpInterContract.setIeFlag(iEId);
					map.put("ieFlag", corpInterContract.getIeFlag());
					corpInterContract.setConsignCode("");
					// 货主
					if (HdUtils.strNotNull(corpInterContract.getConsignCode())) {
						CClientCod ccc = JpaUtils.findById(CClientCod.class, corpInterContract.getConsignCode());
						map.put("consignCode", ccc.getGroupClientCod());
					} else {
						map.put("consignCode", "");
					}
					corpInterContract.setForwarderCode(contractiedoc.getConsignCod());
					// 货代
					if (HdUtils.strNotNull(corpInterContract.getForwarderCode())) {
						CClientCod ccc = JpaUtils.findById(CClientCod.class, corpInterContract.getForwarderCode());
						map.put("forwarderCode", ccc.getGroupClientCod());
					} else {
						map.put("forwarderCode", "");
					}
					corpInterContract.setCneeCode("");
					map.put("cneeCode", corpInterContract.getCneeCode());
					corpInterContract.setCnorCode("");
					map.put("conrCode", corpInterContract.getCnorCode());
					corpInterContract.setBillNo(shipbillL.get(0).getBillNo());
					map.put("billNo", corpInterContract.getBillNo());

					// 获取cargoCode
					String cardoCodeJpql = "select p from ShipBill s, BillCar b, PortCar p where s.shipbillId = b.shipbillId and b.portCarNo = p.portCarNo and s.shipbillId =:shipbillId";
					QueryParamLs cargoCodeParams = new QueryParamLs();
					cargoCodeParams.addParam("shipbillId", shipbillL.get(0).getShipbillId());
					List<PortCar> cargoCodeList = JpaUtils.findAll(cardoCodeJpql, cargoCodeParams);
					if (cargoCodeList.size() > 0) {
						String carType = "";
						for (PortCar portCar : cargoCodeList) {
							if (HdUtils.strNotNull(portCar.getCarKind())) {
								carType = portCar.getCarKind();
								break;
							}
						}
						String cCarType = "SELECT c FROM CCarKind c where c.carKind =:carTyp";
						QueryParamLs carTypParams = new QueryParamLs();
						carTypParams.addParam("carTyp", carType);
						List<CCarKind> CCarTypList = JpaUtils.findAll(cCarType, carTypParams);
						if (CCarTypList.size() > 0) {
							corpInterContract.setCargoCode(CCarTypList.get(0).getGroupCarKind());
						}
						// if(CCarTypList.size() > 0) {
						// String jpqla="select a from VGroupCorpCargo a where
						// a.cargoName=:cargoNam and a.typeFlag = '4'";
						// QueryParamLs paramLsa = new QueryParamLs();
						// paramLsa.addParam("cargoNam",
						// CCarTypList.get(0).getCarKindNam());
						// List<VGroupCorpCargo>
						// vGroupCorpCargoList=JpaUtils.findAll(jpqla,
						// paramLsa);
						// if(vGroupCorpCargoList.size() > 0) {
						// corpInterContract.setCargoCode(String.valueOf(vGroupCorpCargoList.get(0).getCargoCode()));
						// } else {
						// corpInterContract.setCargoCode("");
						// }
						// } else {
						// corpInterContract.setCargoCode("");
						// }
					} else {
						corpInterContract.setCargoCode("");
					}

					// corpInterContract.setCargoCode("9910");
					map.put("cargoCode", corpInterContract.getCargoCode());
					corpInterContract.setCargoTxt(shipbillL.get(0).getCargoNam());
					map.put("cargoTxt", corpInterContract.getCargoTxt());
					corpInterContract.setCargoMark(shipbillL.get(0).getMarks());
					map.put("cargoMark", corpInterContract.getCargoMark());
					corpInterContract.setPackageCode("");
					map.put("packageCode", corpInterContract.getPackageCode());
					corpInterContract.setFormat("");
					map.put("format", corpInterContract.getFormat());
					corpInterContract.setOriginCode(contractiedoc.getFlow());
					map.put("originCode", corpInterContract.getOriginCode());
					corpInterContract.setMataCode("");
					map.put("mataCode", corpInterContract.getMataCode());
					corpInterContract.setPlanCarNum(contractiedoc.getCarNum());
					map.put("planCarNum", corpInterContract.getPlanCarNum().toString());
					corpInterContract.setCargoWgt(new BigDecimal("0"));
					map.put("cargoWgt", corpInterContract.getCargoWgt().toString());
					corpInterContract.setCargoVol(new BigDecimal("0"));
					map.put("cargoVol", corpInterContract.getCargoVol().toString());
					corpInterContract.setCarNum(new BigDecimal("0"));
					map.put("carNum", corpInterContract.getCarNum().toString());
					corpInterContract.setWorkNum(new BigDecimal("0"));
					map.put("workNum", corpInterContract.getWorkNum().toString());
					corpInterContract.setWorkWgt(new BigDecimal("0"));
					map.put("workWgt", corpInterContract.getWorkWgt().toString());
					corpInterContract.setTransportFlag("0");
					map.put("transportFlag", corpInterContract.getTransportFlag());
					corpInterContract.setBegLocation("");
					map.put("begLocation", corpInterContract.getBegLocation());
					corpInterContract.setEndLocation("");
					map.put("endLocation", corpInterContract.getEndLocation());
					corpInterContract.setRouteTxt("");
					map.put("routeTxt", corpInterContract.getRouteTxt());
					corpInterContract.setCorpFlag("");
					map.put("corpFlag", corpInterContract.getCorpFlag());
					corpInterContract.setDirectFlag(contractiedoc.getIsTruck());
					map.put("directFlag", corpInterContract.getDirectFlag());
					corpInterContract.setBangFlag("");
					map.put("bangFlag", corpInterContract.getBangFlag());
					corpInterContract.setBangDemand("");
					map.put("bangDemand", corpInterContract.getBangDemand());
					corpInterContract.setFlowdir("");
					map.put("flowdir", corpInterContract.getFlowdir());
					corpInterContract.setFlowdirTxt("");
					map.put("flowdirTxt", corpInterContract.getFlowdirTxt());
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
					corpInterContract.setEffectDate(format2.format(contractiedoc.getContractDte()));
					map.put("effectDate", corpInterContract.getEffectDate());
					corpInterContract.setValidDate(format2.format(contractiedoc.getValidatDte()));
					map.put("valiDate", corpInterContract.getValidDate());
					corpInterContract.setIfendFlag("");
					map.put("ifendFlag", corpInterContract.getIfendFlag());
					corpInterContract.setLinkman("");
					map.put("linkMan", corpInterContract.getLinkman());
					corpInterContract.setLinktel("");
					map.put("linktel", corpInterContract.getLinktel());
					corpInterContract.setDescription("");
					map.put("description", corpInterContract.getDescription());
					corpInterContract.setTeamOrgnId("");
					map.put("teamOrgnId", corpInterContract.getTeamOrgnId());
					corpInterContract.setSubmitFlag("");
					map.put("submitFlag", corpInterContract.getSubmitFlag());
					corpInterContract.setSubmitName(HdUtils.getCurUser().getAccount());
					map.put("submitName", corpInterContract.getSubmitName());
					corpInterContract.setSubmitTime(format2.format(HdUtils.getDateTime()));
					map.put("submitTime", corpInterContract.getSubmitTime());
				} else {
					throw new HdRunTimeException("提单不存在！");
				}
			} else {
				corpInterContract.setCargoId("*");
				Ship ship = JpaUtils.findById(Ship.class, contractiedoc.getShipNo());
				if ("I".equals(contractiedoc.getiEId())) {
					map.put("shipId", ship.getNewIShipId());
					map.put("svoyageId", ship.getNewGroupShipNo());
					corpInterContract.setShipName(contractiedoc.getShipNam());
					map.put("shipName", corpInterContract.getShipName());
					corpInterContract.setVoyage(contractiedoc.getVoyage());
					map.put("voyage", ship.getIvoyage());
				} else if ("E".equals(contractiedoc.getiEId())) {
					map.put("shipId", ship.getNewEShipId());
					map.put("svoyageId", ship.getNewGroupShipNo());
					corpInterContract.setShipName(contractiedoc.getShipNam());
					map.put("shipName", corpInterContract.getShipName());
					corpInterContract.setVoyage(contractiedoc.getVoyage());
					map.put("voyage", ship.getEvoyage());
				}

				// 我们的内贸外属性和集团的正好相反
				if ("1".equals(contractiedoc.getTradeId())) {
					map.put("tradeFlag", "2");
				} else if ("2".equals(contractiedoc.getTradeId())) {
					map.put("tradeFlag", "1");
				}

				corpInterContract.setIeFlag(iEId);
				corpInterContract.setConsignCode("");
				// 货主
				if (HdUtils.strNotNull(corpInterContract.getConsignCode())) {
					CClientCod ccc = JpaUtils.findById(CClientCod.class, corpInterContract.getConsignCode());
					map.put("consignCode", ccc.getGroupClientCod());
				} else {
					map.put("consignCode", "");
				}
				corpInterContract.setForwarderCode(contractiedoc.getConsignCod());
				// 货代
				if (HdUtils.strNotNull(corpInterContract.getForwarderCode())) {
					CClientCod ccc = JpaUtils.findById(CClientCod.class, corpInterContract.getForwarderCode());
					map.put("forwarderCode", ccc.getGroupClientCod());
				} else {
					map.put("forwarderCode", "");
				}

				corpInterContract.setCneeCode("");
				map.put("cneeCode", corpInterContract.getCneeCode());
				corpInterContract.setCnorCode("");
				map.put("conrCode", corpInterContract.getCnorCode());
				corpInterContract.setBillNo("--");
				map.put("billNo", corpInterContract.getBillNo());
				// corpInterContract.setCargoCode("9910");
				map.put("cargoCode", corpInterContract.getCargoCode());
				corpInterContract.setCargoTxt("");
				map.put("cargoTxt", corpInterContract.getCargoTxt());
				corpInterContract.setCargoMark("");
				map.put("cargoMark", corpInterContract.getCargoMark());
				corpInterContract.setPackageCode("");
				map.put("packageCode", corpInterContract.getPackageCode());
				corpInterContract.setFormat("");
				map.put("format", corpInterContract.getFormat());
				corpInterContract.setOriginCode(contractiedoc.getFlow());
				map.put("originCode", corpInterContract.getOriginCode());
				corpInterContract.setMataCode("");
				map.put("mataCode", corpInterContract.getMataCode());
				corpInterContract.setPlanCarNum(contractiedoc.getCarNum());
				map.put("planCarNum", corpInterContract.getPlanCarNum().toString());
				corpInterContract.setCargoWgt(new BigDecimal("0"));
				map.put("cargoWgt", corpInterContract.getCargoWgt().toString());
				corpInterContract.setCargoVol(new BigDecimal("0"));
				map.put("cargoVol", corpInterContract.getCargoVol().toString());
				corpInterContract.setCarNum(new BigDecimal("0"));
				map.put("carNum", corpInterContract.getCarNum().toString());
				corpInterContract.setWorkNum(new BigDecimal("0"));
				map.put("workNum", corpInterContract.getWorkNum().toString());
				corpInterContract.setWorkWgt(new BigDecimal("0"));
				map.put("workWgt", corpInterContract.getWorkWgt().toString());
				corpInterContract.setTransportFlag("0");
				map.put("transportFlag", corpInterContract.getTransportFlag());
				corpInterContract.setBegLocation("");
				map.put("begLocation", corpInterContract.getBegLocation());
				corpInterContract.setEndLocation("");
				map.put("endLocation", corpInterContract.getEndLocation());
				corpInterContract.setRouteTxt("");
				map.put("routeTxt", corpInterContract.getRouteTxt());
				corpInterContract.setCorpFlag("");
				map.put("corpFlag", corpInterContract.getCorpFlag());
				corpInterContract.setDirectFlag(contractiedoc.getIsTruck());
				map.put("directFlag", corpInterContract.getDirectFlag());
				corpInterContract.setBangFlag("");
				map.put("bangFlag", corpInterContract.getBangFlag());
				corpInterContract.setBangDemand("");
				map.put("bangDemand", corpInterContract.getBangDemand());
				corpInterContract.setFlowdir("");
				map.put("flowdir", corpInterContract.getFlowdir());
				corpInterContract.setFlowdirTxt("");
				map.put("flowdirTxt", corpInterContract.getFlowdirTxt());
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				corpInterContract.setEffectDate(format2.format(contractiedoc.getContractDte()));
				map.put("effectDate", corpInterContract.getEffectDate());
				corpInterContract.setValidDate(format2.format(contractiedoc.getValidatDte()));
				corpInterContract.setIfendFlag("");
				map.put("ifendFlag", corpInterContract.getIfendFlag());
				corpInterContract.setLinkman("");
				map.put("linkMan", corpInterContract.getLinkman());
				corpInterContract.setLinktel("");
				map.put("linktel", corpInterContract.getLinktel());
				corpInterContract.setDescription("");
				map.put("description", corpInterContract.getDescription());
				corpInterContract.setTeamOrgnId(contractiedoc.getDockCod());
				map.put("teamOrgnId", corpInterContract.getTeamOrgnId());
				corpInterContract.setSubmitFlag("");
				map.put("submitFlag", corpInterContract.getSubmitFlag());
				corpInterContract.setSubmitName(HdUtils.getCurUser().getAccount());
				map.put("submitName", corpInterContract.getSubmitName());
				corpInterContract.setSubmitTime(format2.format(HdUtils.getDateTime()));
				map.put("submitTime", corpInterContract.getSubmitTime());
			}
			jsonObj.put("data", map);
			String url = tjgjtServiceIp + "8081/inface/company/upload";
			String query = jsonObj.toString();

			String response = "";
			try {
				URL httpUrl = null; // HTTP URL类 用这个类来创建连接
				// 创建URL
				httpUrl = new URL(url);
				// 建立连接
				HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.connect();
				// POST请求
				try (OutputStream os = conn.getOutputStream()) {
					os.write(query.getBytes("UTF-8"));
				}
				// out.flush();
				// 读取响应
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String lines;
					StringBuffer sbf = new StringBuffer();
					while ((lines = reader.readLine()) != null) {
						lines = new String(lines.getBytes(), "utf-8");
						sbf.append(lines);
					}
					response = sbf.toString();
					JSONObject jsonObject = JSONObject.fromObject(response);
					RespInter resp = (RespInter) JSONObject.toBean(jsonObject, RespInter.class);
					String resCode = "0000";
					String resMsg = "OK";
					if (resCode.equals(resp.getResCode()) && resMsg.equals(resp.getResMsg())) {
						message = "success";
						// throw new HdRunTimeException("上报集团成功！");
					}
					if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
						message = "error";
						// throw new HdRunTimeException("上报集团失败！");
					}
				} catch (Exception e) {
					// throw new HdRunTimeException("上报集团失败！");
					// System.out.println("上报计费数据异常！" + e);
				}
				// 断开连接
				conn.disconnect();
			} catch (Exception e) {
				// System.out.println("发送 POST 请求出现异常！" + e);
				// e.printStackTrace();
				throw new HdRunTimeException("上报集团失败！!");
			}
			// 使用finally块来关闭输出流、输入流
			finally {

			}
		}
		return message;
	}

	// (每日集疏运计划车辆安排)上报到局调
	public String sendDailyContractPlan(String contractNos) {
		List<String> contractIeDocList = HdUtils.paraseStrs(contractNos);
		String message = "";
		for (String contractno : contractIeDocList) {
			ContractIeDoc contractiedoc = JpaUtils.findById(ContractIeDoc.class, contractno);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cmdId", "2019");
			jsonObj.put("coId", Ship.GZ);
			// if ("03406500".equals(contractiedoc.getDockCod())) {
			// jsonObj.put("coId", "03406500");
			// }
			// if ("03409000".equals(contractiedoc.getDockCod())) {
			// jsonObj.put("coId", "03406500");
			//// jsonObj.put("coId", "03409000");
			// }
			Map<String, String> map = new HashMap<String, String>();
			DailyContractPlan dailyContractPlan = new DailyContractPlan();
			String jpql = "select a from TruckWork a where a.contractNo=:contractNo ";
			QueryParamLs paramLs = new QueryParamLs();
			paramLs.addParam("contractNo", contractiedoc.getContractNo());
			List<TruckWork> truckWorkList = JpaUtils.findAll(jpql, paramLs);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			if (truckWorkList.size() > 0) {
				dailyContractPlan.setTransportNo(truckWorkList.get(0).getTruckNo());
				dailyContractPlan.setItransportId(truckWorkList.get(0).getIngateId());
				map.put("itransportId", dailyContractPlan.getItransportId());
				dailyContractPlan.setInformId(contractiedoc.getContractNo());
				map.put("informId", dailyContractPlan.getInformId());
				map.put("fleetCode", "");
				map.put("transportNo", dailyContractPlan.getTransportNo());
				dailyContractPlan.setRecNam(HdUtils.getCurUser().getAccount());
				dailyContractPlan.setRecTim(format.format(HdUtils.getDateTime()));
				dailyContractPlan.setUpdNam(HdUtils.getCurUser().getAccount());
				dailyContractPlan.setUpdTim(format.format(HdUtils.getDateTime()));
				map.put("recNam", dailyContractPlan.getRecNam());
				map.put("recTim", dailyContractPlan.getRecTim());
				map.put("updNam", dailyContractPlan.getUpdNam());
				map.put("updTim", dailyContractPlan.getUpdTim());
				jsonObj.put("data", map);

				String url = tjgjtServiceIp + "8081/inface/company/upload";
				String query = jsonObj.toString();

				String response = "";
				try {
					URL httpUrl = null; // HTTP URL类 用这个类来创建连接
					// 创建URL
					httpUrl = new URL(url);
					// 建立连接
					HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.connect();
					// POST请求
					try (OutputStream os = conn.getOutputStream()) {
						os.write(query.getBytes("UTF-8"));
					}
					// out.flush();
					// 读取响应
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
						String lines;
						StringBuffer sbf = new StringBuffer();
						while ((lines = reader.readLine()) != null) {
							lines = new String(lines.getBytes(), "utf-8");
							sbf.append(lines);
						}
						response = sbf.toString();
						JSONObject jsonObject = JSONObject.fromObject(response);
						RespInter resp = (RespInter) JSONObject.toBean(jsonObject, RespInter.class);
						String resCode = "0000";
						String resMsg = "OK";
						if (resCode.equals(resp.getResCode()) && resMsg.equals(resp.getResMsg())) {
							message = "success";
							// throw new HdRunTimeException("上报集团成功！");
						}
						if (!resCode.equals(resp.getResCode()) || !resMsg.equals(resp.getResMsg())) {
							message = "error";
							// throw new HdRunTimeException("上报集团失败！");
						}
					} catch (Exception e) {
						// System.out.println("上报计费数据异常！" + e);
					}
					// 断开连接
					conn.disconnect();
				} catch (Exception e) {
					// System.out.println("发送 POST 请求出现异常！" + e);
					// e.printStackTrace();
					throw new HdRunTimeException("发送 POST 请求出现异常!");
				}
				// 使用finally块来关闭输出流、输入流
				finally {
					// try {
					// if (os != null) {
					// out.close();
					// }
					// if (reader != null) {
					// reader.close();
					// }
					// } catch (IOException ex) {
					// ex.printStackTrace();
					// }
				}
			} else {
				throw new HdRunTimeException("无拖车记录！");
			}

		}
		return message;
	}

	@Override
	public List<ContractIeDoc> findContract() {
		// TODO Auto-generated method stub
		String jpql = "select a from ContractIeDoc a where a.contractDte = :contractDte and a.shipNo !=:shipNo";
		QueryParamLs paramLs = new QueryParamLs();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -1);
		paramLs.addParam("contractDte", c.getTime());
		paramLs.addParam("shipNo", "20190311082013");
		List<ContractIeDoc> contractList = JpaUtils.findAll(jpql, paramLs);
		String dashShipMessage = "";
		for (ContractIeDoc contractiedoc : contractList) {
			if (HdUtils.strNotNull(contractiedoc.getStartDte())) {
				contractiedoc
						.setFromTo((contractiedoc.getStartDte() + ":00" + "-" + contractiedoc.getEndDte() + ":00"));
			}
			if (HdUtils.strNotNull(contractiedoc.getDockCod())) {
				CDock cd = JpaUtils.findById(CDock.class, contractiedoc.getDockCod());
				contractiedoc.setDockNam(cd.getDockNam());
			}
			if (HdUtils.strNotNull(contractiedoc.getBrand())) {
				CBrand cb = JpaUtils.findById(CBrand.class, contractiedoc.getBrand());
				contractiedoc.setBrandNam(cb.getBrandNam());
			}
			if (HdUtils.strNotNull(contractiedoc.getCarKind())) {
				CCarKind ck = JpaUtils.findById(CCarKind.class, contractiedoc.getCarKind());
				contractiedoc.setCarKindNam(ck.getCarKindNam());
				if (!HdUtils.strNotNull(contractiedoc.getBrand())) {
					contractiedoc.setBrandNam(ck.getCarKindNam());
				}
			}
			if (HdUtils.strNotNull(contractiedoc.getConsignCod())) {
				CClientCod cc = JpaUtils.findById(CClientCod.class, contractiedoc.getConsignCod());
				contractiedoc.setConsignNam(cc.getcClientNam());
			}
			contractiedoc.setTradeNam(HdUtils.getSysCodeName("TRADE_ID", contractiedoc.getTradeId()));
			contractiedoc.setIeNam(HdUtils.getSysCodeName("I_E_ID", contractiedoc.getiEId()));
			;
			contractiedoc.setFlowNam(HdUtils.getSysCodeName("FLOW_AREA", contractiedoc.getFlow()));
			contractiedoc.setWorkNam(HdUtils.getSysCodeName("WORK_WAY", contractiedoc.getWorkWay()));

			// dashShipMessage += "<div><div class='text0'>"+
			// contractiedoc.getWorkNam()
			// +"</div><div class='text0'>"+ contractiedoc.getDockNam()
			// +"</div><div
			// class='text0'>"+ contractiedoc.getBrandNam() +"</div><div
			// class='text0'>"+
			// contractiedoc.getCarNum() +"</div><div class='text0'>"+
			// contractiedoc.getIeNam() +"</div><div class='text0'>"+
			// contractiedoc.getTradeNam() +"</div></div>";
			/*
			 * dashShipMessage += "<p style=\"color: #97FFFF\">"+ "&nbsp;&nbsp;"
			 * + contractiedoc.getWorkNam() + "&nbsp;&nbsp;&nbsp;&nbsp;" +
			 * contractiedoc.getDockNam() +
			 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			 * contractiedoc.getBrandNam() + "&nbsp;&nbsp;&nbsp;&nbsp;" +
			 * contractiedoc.getCarNum() + "&nbsp;&nbsp;&nbsp;&nbsp;" +
			 * contractiedoc.getIeNam()+
			 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			 * contractiedoc.getTradeNam()+ "</p>";
			 */
		}
		return contractList;
		// return dashShipMessage;
	}

	/**
	 * 是否存在理货数据。
	 */
	@Override
	public String hasWorkCommand(String contractNo) {
		String wkJpql = "SELECT w FROM WorkCommand w where w.contractNo=:contractNo";
		QueryParamLs wkParamLs = new QueryParamLs();
		wkParamLs.addParam("contractNo", contractNo);
		List<WorkCommand> workCommandList = JpaUtils.findAll(wkJpql, wkParamLs);
		String flag = "0";
		if(workCommandList.size()>0){
			flag = "1";
		}
		
		return flag;
	}
}
