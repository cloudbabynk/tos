package net.huadong.tech.wechat.service;

import java.util.List;
import java.util.Map;

import net.huadong.tech.shipbill.entity.ContractIeDoc;

public interface StacksService {

	Map<String, Object> checkshgRfid(String rfid, String billNo);

	String checkcyPlacshg(String cyAreaNo, String cyRowNo, String cyBayNo, String contractNo);

	String checkVinsg(String vinNo, String billNo);

	String shiploaderzhz(String req);

	List<ContractIeDoc> billNum(String contractNo);
}
