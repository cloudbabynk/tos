package net.huadong.tech.wechat.service;

public interface ShipAnbianService {

	StringBuffer loadBillData(String shipNo);

	String shiploader(String req);

	String shipUnloader(String req);

	StringBuffer shipBillData(String shipNo, String billNo);

	String checkRFID(String rfid);

	String checkVIN(String vin, String shipNo);
	String checkVIN(String vin, String shipNo,String tradId,String directId);
	
	String checkVINHandling(String vin, String shipNo,String tradeId,String directId,String ieId);

	String checkcyPlac(String cyPlac, String shipNo);
	
	String checkVINbillCar(String vin, String shipNo);
	
	String checkFlow(String vin, String flow, String directid);
	
	String getCarInfo(String vin);
	
	String countCar(String shipNo, String workTyp, String brandcod, String cartyp);
}
