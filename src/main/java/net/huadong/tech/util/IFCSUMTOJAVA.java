package net.huadong.tech.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.sf.json.JSONObject;
@Transactional
public class IFCSUMTOJAVA {
	public static String filenam;
	private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	@Value("${api.service.ip}")
	private static String apiServiceIp;

	public static void txtToBean(String iEId, String eShipNam, String Voyage) {

		// 文件绝对路径改成你自己的文件路径
		FileReader fr = null;
		try {
			fr = new FileReader(new File(filenam));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 可以换成工程目录下的其他文本文件
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();// 定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		try {
			while ((s = br.readLine()) != null) {
				sb.append(s + "");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = sb.toString();
		ArrayList splitTxt = new ArrayList();
		String sp = str.replaceFirst("\'\\d", "_");
		int index = sp.indexOf("_");
		int x = 0;
		while (index != -1) {
			String substr1 = str.substring(0, index);
			str = str.substring(index + 1);
			sp = str.replaceFirst("\'\\d", "_");
			index = sp.indexOf("_");
			splitTxt.add(x++, substr1);
		}
		splitTxt.add(x, str);
		System.out.println(splitTxt);
		for (int i = 0; i < splitTxt.size(); i++) {
			String firstTflag = splitTxt.get(i).toString();
			if(HdUtils.strIsNull(firstTflag)) {
				continue;
			}
			String firstT = splitTxt.get(i).toString().substring(0, 2);
			if ("10".equals(firstT)) {
				String[] substr = splitTxt.get(i).toString().split(":");
				String voyage = substr[4];
				String eshipnam = substr[2];
				if (!(Voyage.equals(voyage)&&eShipNam.replaceAll(" ", "").equals(eshipnam.replaceAll(" ", "")))){
					throw new HdRunTimeException("该舱单不属于该航次！");// 舱单与该航次不匹配
				}
				String sql = "SELECT SHIP_NO,TRADE_ID FROM SHIP WHERE REPLACE(E_SHIP_NAM,' ','') = '" + eshipnam.replaceAll(" ", "") + "'\r\n";
				if (Ship.JK.equals(iEId)){
					sql += "AND IVOYAGE = '" + voyage + "'\r\n";
				} else if (Ship.CK.equals(iEId)){
					sql += "AND EVOYAGE = '" + voyage + "'\r\n";
				}
				List<Object[]> objList = JpaUtils.getEntityManager().createNativeQuery(sql).getResultList();
				if (objList.size() == 0) {
					throw new HdRunTimeException("该舱单不属于该航次！");// 舱单与该航次不匹配
				} else {
					for (Object[] shipl : objList) {
						if ("I".equals(iEId)) {
							for (int j = 3; j < splitTxt.size() - 1; j++) {
								String billflag = splitTxt.get(j).toString();
								if(HdUtils.strIsNull(billflag)) {
									continue;
								}
								String bill = splitTxt.get(j).toString().substring(0, 2);
								if ("12".equals(bill)) {	
									String[] sbill = splitTxt.get(j).toString().split(":");
									String billNo = sbill[1];

									// 根据提单号 已入的进行update
									String shipBillJpql1 = "SELECT s FROM ShipBill s where s.shipNo =:shipNo and s.billNo =:billNo";
									QueryParamLs shipBillParams1 = new QueryParamLs();
									shipBillParams1.addParam("shipNo", shipl[0]);
									shipBillParams1.addParam("billNo", billNo);
									List<ShipBill> shipBillList1 = JpaUtils.findAll(shipBillJpql1, shipBillParams1);
									if (shipBillList1.size() > 0) {
										// 已存在的直接更新 shipBillList1.get(0)
										shipBillList1.get(0).setUpdTim(HdUtils.getDateTime());
										JpaUtils.update(shipBillList1.get(0));
										
									} else {
										ShipBill shipBill = new ShipBill();
										shipBill.setBillNo(billNo);
										shipBill.setLoadPortCod(sbill[7]);
										shipBill.setShipbillId(HdUtils.genUuid());
										shipBill.setiEId("I");
										shipBill.setTradeId(shipl[1].toString());
										shipBill.setShipNo(shipl[0].toString());

										// dockcode
										String jpqlShip = "select a from Ship a where a.shipNo=:shipNo ";
										QueryParamLs paramLShip = new QueryParamLs();
										paramLShip.addParam("shipNo", shipBill.getShipNo());
										List<Ship> dockCodelist = JpaUtils.findAll(jpqlShip, paramLShip);
										if (dockCodelist.size() > 0) {
											shipBill.setDockCod(dockCodelist.get(0).getDockCod());
										}

										String[] tranPortCod = splitTxt.get(j + 1).toString().split(":");
										shipBill.setTranPortCod(tranPortCod[1]);
										String[] consignCod = splitTxt.get(j + 2).toString().split(":");
										shipBill.setConsignCod("");
										shipBill.setConsignNam(consignCod[2]);
										String[] receiveCod = splitTxt.get(j + 3).toString().split(":");
										if (receiveCod.length < 3) {
											shipBill.setReceiveCod("");
											shipBill.setReceiveNam("");
										} else {
											shipBill.setReceiveCod("");
											shipBill.setReceiveNam(receiveCod[2]);
										}
										String[] cargo = splitTxt.get(j + 5).toString().split(":");
										if (cargo.length < 9 ) {
											int k = j + 5;
											throw new HdRunTimeException("报文格式有误，重量和件数不能为空！请检查第"+ k +"行的报文！");
										}
										shipBill.setWeights(new BigDecimal(cargo[6]));
										shipBill.setValumes(new BigDecimal(cargo[8]));
										shipBill.setCarNum(new BigDecimal(cargo[3]));
										shipBill.setPieces(new BigDecimal(cargo[3]));
										// 考虑空箱时唛头不出现的问题
										String[] D44hFirst = splitTxt.get(j + 6).toString().split(":");
										String dd44hfirst = D44hFirst[0];
										if (dd44hfirst.equals("44")) {
											if (D44hFirst.length <= 1) {
												shipBill.setMarks("");
											} else {
												shipBill.setMarks(D44hFirst[1]);
											}
											String[] carTypE = splitTxt.get(j + 7).toString().split(":");
											System.out.println(carTypE.length);
											if(carTypE.length >= 2) {
											//舱单解析不为空
											String carTypENam = carTypE[1];
											shipBill.setCargoNam(carTypENam);
											String jpql3 = "select a from CBrand a where 1=1";
											List<CBrand> cbrandList = JpaUtils.findAll(jpql3, null);
											
											for (CBrand cb : cbrandList) {
												if(cb.getBrandEname().equals("VOLK")) {
												} else {
													//处理二手
													if(carTypENam.indexOf("USED") != -1 && cb.getBrandEname().indexOf("USED") != -1) {
														//舱单
														String midStr = carTypENam.split("USED")[1];
														String cdBrandCode = "USED " + midStr.split(" ")[1];
														//数据库
														String barndCodeJpql = "select a from CBrand a where upper(a.brandEname) like :brandEname";
														QueryParamLs brandCodParams = new QueryParamLs();
														brandCodParams.addParam("brandEname", "%" + cdBrandCode + "%");
														List<CBrand> brandList = JpaUtils.findAll(barndCodeJpql, brandCodParams);
														if(brandList.size() > 0) {
															shipBill.setBrandCod(brandList.get(0).getBrandCod());
															break;
														}
													} else if (carTypENam.contains(cb.getBrandEname().toUpperCase())) {
														shipBill.setBrandCod(cb.getBrandCod());
														break;
													}
												}
											}}
											shipBill.setConfirmId("0");
											shipBill.setExitCustomId("0");
											JpaUtils.save(shipBill);
											// 向综合查询系统插入舱单
											// 追加，改为手动同步
											// insertIntoZongheCha(shipBill);
											BillSplit billSplit = new BillSplit();
											String uid = HdUtils.genUuid();
											billSplit.setBillspId(uid);
											billSplit.setShipbillId(shipBill.getShipbillId());
											String jpqla = "select a from Ship a where a.shipNo=:shipNo ";
											QueryParamLs paramLs = new QueryParamLs();
											paramLs.addParam("shipNo", shipBill.getShipNo());
											List<Ship> shiplist = JpaUtils.findAll(jpqla, paramLs);
											if (shiplist.size() > 0) {
												for (Ship ship : shiplist) {
													billSplit.setcShipNam(ship.getcShipNam());
													billSplit.setVoyage(ship.getIvoyage() + '/' + ship.getEvoyage());
													billSplit.setInPortTim(ship.getToPortTim());
													billSplit.setOutPortTim(ship.getLeavPortTim());
												}
											}
											billSplit.setTradeId(shipBill.getTradeId());
											billSplit.setiEId(shipBill.getiEId());
											billSplit.setShipNo(shipBill.getShipNo());
											billSplit.setBillNo(shipBill.getBillNo());
											billSplit.setBrandCod(shipBill.getBrandCod());
											billSplit.setCarTyp(shipBill.getCarTyp());
											billSplit.setCargoNam(shipBill.getCargoNam());
											billSplit.setPieces(shipBill.getPieces());
											billSplit.setWeights(shipBill.getWeights());
											billSplit.setVolumes(shipBill.getValumes());
											billSplit.setRecNam(HdUtils.getCurUser().getName());
											billSplit.setRecTim(HdUtils.getDateTime());
											billSplit.setUseShipworkPerson("1");
											JpaUtils.save(billSplit);
											HdUtils.genMsg();
										}
										if (dd44hfirst.equals("47")) {
											String[] carTypE = splitTxt.get(j + 6).toString().split(":");
											String carTypENam = carTypE[1];
											shipBill.setCargoNam(carTypENam);
											String jpql3 = "select a from CBrand a where 1=1";
											List<CBrand> cbrandList = JpaUtils.findAll(jpql3, null);
											for (CBrand cb : cbrandList) {
												if(cb.getBrandEname().equals("VOLK")) {
												} else {
													//处理二手
													if(carTypENam.indexOf("USED") != -1 && cb.getBrandEname().indexOf("USED") != -1) {
														//舱单
														String midStr = carTypENam.split("USED")[1];
														String cdBrandCode = "USED " + midStr.split(" ")[1];
														//数据库
														String barndCodeJpql = "select a from CBrand a where upper(a.brandEname) like :brandEname";
														QueryParamLs brandCodParams = new QueryParamLs();
														brandCodParams.addParam("brandEname", "%" + cdBrandCode + "%");
														List<CBrand> brandList = JpaUtils.findAll(barndCodeJpql, brandCodParams);
														if(brandList.size() > 0) {
															shipBill.setBrandCod(brandList.get(0).getBrandCod());
															break;
														}
													} else if (carTypENam.contains(cb.getBrandEname().toUpperCase())) {
														shipBill.setBrandCod(cb.getBrandCod());
														break;
													}
												}
//												if(HdUtils.strNotNull((String) cb.getBrandEname())) {
//													String brandCode = carTypENam.split(" ")[0];
//													if(brandCode.equals("NEW")) {
//														brandCode = carTypENam.split(" ")[1];
//													}
//													String cBrandCode = cb.getBrandEname().toUpperCase();
//													if(cBrandCode.indexOf(" ") != -1) {
//														cBrandCode = cBrandCode.split(" ")[0];
//													}
//													if(brandCode.equals(cBrandCode.toUpperCase())) {
//														shipBill.setBrandCod(cb.getBrandCod());
//													}
//												}
											}
											JpaUtils.save(shipBill);
											// 向综合查询系统插入舱单
											// 改为手动同步
											// insertIntoZongheCha(shipBill);
											BillSplit billSplit = new BillSplit();
											String uid = HdUtils.genUuid();
											billSplit.setBillspId(uid);
											billSplit.setShipbillId(shipBill.getShipbillId());
											String jpqla = "select a from Ship a where a.shipNo=:shipNo ";
											QueryParamLs paramLs = new QueryParamLs();
											paramLs.addParam("shipNo", shipBill.getShipNo());
											List<Ship> shiplist = JpaUtils.findAll(jpqla, paramLs);
											if (shiplist.size() > 0) {
												for (Ship ship : shiplist) {
													billSplit.setcShipNam(ship.getcShipNam());
													billSplit.setVoyage(ship.getIvoyage() + '/' + ship.getEvoyage());
													billSplit.setInPortTim(ship.getToPortTim());
													billSplit.setOutPortTim(ship.getLeavPortTim());
												}
											}
											billSplit.setTradeId(shipBill.getTradeId());
											billSplit.setiEId(shipBill.getiEId());
											billSplit.setShipNo(shipBill.getShipNo());
											billSplit.setBillNo(shipBill.getBillNo());
											billSplit.setBrandCod(shipBill.getBrandCod());
											billSplit.setCarTyp(shipBill.getCarTyp());
											billSplit.setCargoNam(shipBill.getCargoNam());
											billSplit.setPieces(shipBill.getPieces());
											billSplit.setWeights(shipBill.getWeights());
											billSplit.setVolumes(shipBill.getValumes());
											billSplit.setRecNam(HdUtils.getCurUser().getName());
											billSplit.setRecTim(HdUtils.getDateTime());
											billSplit.setUseShipworkPerson("1");
											JpaUtils.save(billSplit);
											HdUtils.genMsg();
										}
									}
								}
							}
						}
						if ("E".equals(iEId)) {
							for (int j = 3; j < splitTxt.size(); j++) {
								String bill = splitTxt.get(j).toString().substring(0, 2);
								if ("12".equals(bill)) {
									String[] sbill = splitTxt.get(j).toString().split(":");
									String billNo = sbill[1];
									
									if(billNo.equals("CN014733D")) {
										System.err.println(billNo);
									}

									// 根据提单号 已入的进行update
									String shipBillJpql1 = "SELECT s FROM ShipBill s where s.shipNo =:shipNo and s.billNo =:billNo";
									QueryParamLs shipBillParams1 = new QueryParamLs();
									shipBillParams1.addParam("shipNo", shipl[0]);
									shipBillParams1.addParam("billNo", billNo);
									List<ShipBill> shipBillList1 = JpaUtils.findAll(shipBillJpql1, shipBillParams1);
									if (shipBillList1.size() > 0) {
//										String[] tranPortCod = splitTxt.get(j + 1).toString().split(":");
//										shipBillList1.get(0).setTranPortCod(tranPortCod[1]);
//										String[] consignCod = splitTxt.get(j + 2).toString().split(":");
//										shipBillList1.get(0).setConsignCod("");
//										shipBillList1.get(0).setConsignNam(consignCod[2]);
//										String[] receiveCod = splitTxt.get(j + 3).toString().split(":");
//										if (receiveCod.length < 3) {
//											shipBillList1.get(0).setReceiveCod("");
//											shipBillList1.get(0).setReceiveNam("");
//										} else {
//											shipBillList1.get(0).setReceiveCod("");
//											shipBillList1.get(0).setReceiveNam(receiveCod[2]);
//										}
//										String[] cargo = splitTxt.get(j + 5).toString().split(":");
//										shipBillList1.get(0).setWeights(new BigDecimal(cargo[6]));
//										shipBillList1.get(0).setValumes(new BigDecimal(cargo[8]));
//										shipBillList1.get(0).setCarNum(new BigDecimal(cargo[3]));
//										shipBillList1.get(0).setPieces(new BigDecimal(cargo[3]));
//										String[] marks = splitTxt.get(j + 6).toString().split(":");
//										shipBillList1.get(0).setMarks(marks[1]);
//										BillSplit billSplit = new BillSplit();
//										String[] carTypE = splitTxt.get(j + 7).toString().split(":");
//										String carTypENam = carTypE[1];
//										int lastindexm = splitTxt.get(j + 7).toString().lastIndexOf(':');
//										String subs = splitTxt.get(j + 7).toString().substring(lastindexm + 1);
//										if (subs.contains(" X")) {
//											String[] length = subs.split("X");
//											String len = length[0];
//											billSplit.setLength(new BigDecimal(len.trim()));
//										}
//										shipBillList1.get(0).setCargoNam(carTypENam);
//										String jpql3 = "select a from CBrand a where 1=1";
//										List<CBrand> cbrandList = JpaUtils.findAll(jpql3, null);
//										for (CBrand cb : cbrandList) {
//											if (carTypENam.contains(cb.getBrandCod())) {
//												shipBillList1.get(0).setBrandCod(cb.getBrandCod());
//											}
//										}
//										shipBillList1.get(0).setConfirmId("0");
//										shipBillList1.get(0).setExitCustomId("0");
//										JpaUtils.update(shipBillList1.get(0));
//
//										String billSplitJpql = "SELECT b FROM BillSplit b where b.shipbillId =:shipbillId";
//										QueryParamLs billSplitParams = new QueryParamLs();
//										billSplitParams.addParam("shipbillId", shipBillList1.get(0).getShipbillId());
//										List<BillSplit> billSplitList = JpaUtils.findAll(billSplitJpql,
//												billSplitParams);
//										if (billSplitList.size() > 0) {
//											for (BillSplit billSplit1 : billSplitList) {
//												String jpqla = "select a from Ship a where a.shipNo=:shipNo ";
//												QueryParamLs paramLs = new QueryParamLs();
//												paramLs.addParam("shipNo", shipBillList1.get(0).getShipNo());
//												List<Ship> shiplist = JpaUtils.findAll(jpqla, paramLs);
//												if (shiplist.size() > 0) {
//													for (Ship ship : shiplist) {
//														billSplit1.setcShipNam(ship.getcShipNam());
//														billSplit1
//																.setVoyage(ship.getIvoyage() + '/' + ship.getEvoyage());
//														billSplit1.setInPortTim(ship.getToPortTim());
//														billSplit1.setOutPortTim(ship.getLeavPortTim());
//													}
//												}
//												billSplit1.setTradeId(shipBillList1.get(0).getTradeId());
//												billSplit1.setiEId(shipBillList1.get(0).getiEId());
//												billSplit1.setShipNo(shipBillList1.get(0).getShipNo());
//												billSplit1.setBillNo(shipBillList1.get(0).getBillNo());
//												billSplit1.setBrandCod(shipBillList1.get(0).getBrandCod());
//												billSplit1.setCarTyp(shipBillList1.get(0).getCarTyp());
//												billSplit1.setCargoNam(shipBillList1.get(0).getCargoNam());
//												billSplit1.setPieces(shipBillList1.get(0).getPieces());
//												billSplit1.setWeights(shipBillList1.get(0).getWeights());
//												billSplit1.setVolumes(shipBillList1.get(0).getValumes());
//												billSplit1.setUpdNam(HdUtils.getCurUser().getName());
//												billSplit1.setUpdTim(HdUtils.getDateTime());
//												billSplit1.setUseShipworkPerson("1");
//												JpaUtils.update(billSplit1);
//											}
//										}
									} else {

										ShipBill shipBill = new ShipBill();
										shipBill.setBillNo(billNo);
										shipBill.setLoadPortCod(sbill[7]);
										shipBill.setShipbillId(HdUtils.genUuid());
										shipBill.setiEId("E");
										shipBill.setTradeId(shipl[1].toString());
										shipBill.setShipNo(shipl[0].toString());
										String[] tranPortCod = splitTxt.get(j + 1).toString().split(":");
										shipBill.setTranPortCod(tranPortCod[1]);
										String[] consignCod = splitTxt.get(j + 2).toString().split(":");
										shipBill.setConsignCod("");
										shipBill.setConsignNam(consignCod[2]);
										String[] receiveCod = splitTxt.get(j + 3).toString().split(":");
										if (receiveCod.length < 3) {
											shipBill.setReceiveCod("");
											shipBill.setReceiveNam("");
										} else {
											shipBill.setReceiveCod("");
											shipBill.setReceiveNam(receiveCod[2]);
										}
										String[] cargo = splitTxt.get(j + 5).toString().split(":");
										shipBill.setWeights(new BigDecimal(cargo[6]));
										shipBill.setValumes(new BigDecimal(cargo[8]));
										shipBill.setCarNum(new BigDecimal(cargo[3]));
										shipBill.setPieces(new BigDecimal(cargo[3]));
										String[] marks = splitTxt.get(j + 6).toString().split(":");
										shipBill.setMarks(marks[1]);
										BillSplit billSplit = new BillSplit();
										String[] carTypE = splitTxt.get(j + 7).toString().split(":");
										String carTypENam = carTypE[1];
										int lastindexm = splitTxt.get(j + 7).toString().lastIndexOf(':');
										String subs = splitTxt.get(j + 7).toString().substring(lastindexm + 1);
										if (subs.contains(" X")) {
											String[] length = subs.split("X");
											String len = length[0];
											String testLength = "";
											
											Object[] objLength = len.split(" ");
											
											if(objLength.length > 1) {
												testLength = len.split(" ")[1].toString();
											} else {
												testLength = "0";
											}
											billSplit.setLength(new BigDecimal(testLength.trim()));
										}
										shipBill.setCargoNam(carTypENam);
										String jpql3 = "select a from CBrand a where 1=1";
										List<CBrand> cbrandList = JpaUtils.findAll(jpql3, null);
										for (CBrand cb : cbrandList) {
											if (carTypENam.contains(cb.getBrandEname().toUpperCase())) {
												shipBill.setBrandCod(cb.getBrandCod());
											}
										}
										shipBill.setConfirmId("0");
										shipBill.setExitCustomId("0");
										JpaUtils.save(shipBill);
										// 向综合查询系统插入舱单
										// 改为手动同步
										// insertIntoZongheCha(shipBill);
										String uid = HdUtils.genUuid();
										billSplit.setBillspId(uid);
										billSplit.setShipbillId(shipBill.getShipbillId());
										String jpqla = "select a from Ship a where a.shipNo=:shipNo ";
										QueryParamLs paramLs = new QueryParamLs();
										paramLs.addParam("shipNo", shipBill.getShipNo());
										List<Ship> shiplist = JpaUtils.findAll(jpqla, paramLs);
										if (shiplist.size() > 0) {
											for (Ship ship : shiplist) {
												billSplit.setcShipNam(ship.getcShipNam());
												billSplit.setVoyage(ship.getIvoyage() + '/' + ship.getEvoyage());
												billSplit.setInPortTim(ship.getToPortTim());
												billSplit.setOutPortTim(ship.getLeavPortTim());
											}
										}
										billSplit.setTradeId(shipBill.getTradeId());
										billSplit.setiEId(shipBill.getiEId());
										billSplit.setShipNo(shipBill.getShipNo());
										billSplit.setBillNo(shipBill.getBillNo());
										billSplit.setBrandCod(shipBill.getBrandCod());
										billSplit.setCarTyp(shipBill.getCarTyp());
										billSplit.setCargoNam(shipBill.getCargoNam());
										billSplit.setPieces(shipBill.getPieces());
										billSplit.setWeights(shipBill.getWeights());
										billSplit.setVolumes(shipBill.getValumes());
										billSplit.setRecNam(HdUtils.getCurUser().getName());
										billSplit.setRecTim(HdUtils.getDateTime());
										billSplit.setUseShipworkPerson("1");
										JpaUtils.save(billSplit);
										HdUtils.genMsg();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void insertIntoZongheCha(ShipBill shipBill) {
		JSONObject jsonObj = new JSONObject();
		String uuid = HdUtils.genUuid().substring(0, 8);
		jsonObj.put("id", uuid);
		String vss_no = _10_to_62(Long.parseLong(shipBill.getShipNo()), 5);
		jsonObj.put("vss_no", vss_no);
		Ship ship = JpaUtils.findById(Ship.class, shipBill.getShipNo());
		jsonObj.put("vss_name", ship.getcShipNam());
		jsonObj.put("voyage", shipBill.getVoyage());
		if ("I".equals(shipBill.getiEId())) {
			jsonObj.put("voyage", ship.getIvoyage());
			jsonObj.put("in_out_flag", "进口");
		}
		if ("E".equals(shipBill.getiEId())) {
			jsonObj.put("voyage", ship.getEvoyage());
			jsonObj.put("in_out_flag", "出口");
		}
		jsonObj.put("b_l", shipBill.getBillNo());
		jsonObj.put("quantity", shipBill.getPieces().longValue());
		jsonObj.put("true_quantity", shipBill.getPieces().longValue());
		jsonObj.put("vol", shipBill.getValumes());
		jsonObj.put("single_weight", shipBill.getWeights().divide(shipBill.getPieces(), 3, BigDecimal.ROUND_HALF_UP));
		jsonObj.put("charge_weight", shipBill.getWeights().divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP));
		jsonObj.put("charge_vol", shipBill.getValumes());
		jsonObj.put("charge_quantity", shipBill.getPieces().longValue());
		jsonObj.put("lh_check_people", HdUtils.getCurUser().getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		jsonObj.put("lh_check_tim", format.format(HdUtils.getDateTime()));
		jsonObj.put("hy_check_people", HdUtils.getCurUser().getName());
		jsonObj.put("hy_check_tim", format.format(HdUtils.getDateTime()));
		jsonObj.put("check_flag", "0");
		jsonObj.put("flag_charge", "0");
		jsonObj.put("flag_apply", "0");
		jsonObj.put("check_time", format.format(HdUtils.getDateTime()));
		jsonObj.put("check_person", HdUtils.getCurUser().getName());
		if ("03406500".equals(ship.getDockCod())) {
			jsonObj.put("token", "roroBilling");
		}
		if ("03409000".equals(ship.getDockCod())) {
			jsonObj.put("token", "globalBilling");
		}
		String url = apiServiceIp + "8081/RoroBillingSysWebApi/setShipBill";
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
				String str = '{' + "\"code\"" + ":0" + '}';
				String str2 = '{' + "\"code\"" + ":1" + '}';
				if (str.equals(response)) {
					throw new HdRunTimeException("上报舱单数据失败！");
				}
				if (str2.equals(response)) {
					throw new HdRunTimeException("上报舱单数据成功！");
				}
				// JSONObject j = JSON.parseObject(response);
			} catch (Exception e) {
				// System.out.println("上报计费数据异常！" + e);
			}
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			/**
			 * 计费连接接口问题导致上报失败！
			 */
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
			throw new HdRunTimeException("发送 POST 请求出现异常！");
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
	}

	public static String _10_to_62(long number, int length) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		int result_length = result.length();
		StringBuilder temp0 = new StringBuilder();
		for (int i = 0; i < length - result_length; i++) {
			temp0.append('0');
		}

		return temp0.toString() + result.toString();

	}
}