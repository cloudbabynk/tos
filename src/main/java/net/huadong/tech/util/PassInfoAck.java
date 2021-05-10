package net.huadong.tech.util;

import java.math.BigDecimal;

public class PassInfoAck {
private String File_Type;
private String Sender_Code;
private String Sender_Name;
private String Receiver_Code;
private String transport_id;
private String  transport_name;
private String  voyage_no;
private String  bill_no;
private String  operate_date;
private String  create_date;
private String ie_flag;
private BigDecimal real_pack_no;
private BigDecimal  real_gross_wt;
private BigDecimal real_gross_measure;
private String businesstype;
private String arrivalplace;
public String getFile_Type() {
	return File_Type;
}
public void setFile_Type(String file_Type) {
	File_Type = file_Type;
}
public String getSender_Code() {
	return Sender_Code;
}
public void setSender_Code(String sender_Code) {
	Sender_Code = sender_Code;
}
public String getSender_Name() {
	return Sender_Name;
}
public void setSender_Name(String sender_Name) {
	Sender_Name = sender_Name;
}
public String getReceiver_Code() {
	return Receiver_Code;
}
public void setReceiver_Code(String receiver_Code) {
	Receiver_Code = receiver_Code;
}
public String getTransport_id() {
	return transport_id;
}
public void setTransport_id(String transport_id) {
	this.transport_id = transport_id;
}
public String getTransport_name() {
	return transport_name;
}
public void setTransport_name(String transport_name) {
	this.transport_name = transport_name;
}
public String getVoyage_no() {
	return voyage_no;
}
public void setVoyage_no(String voyage_no) {
	this.voyage_no = voyage_no;
}
public String getBill_no() {
	return bill_no;
}
public void setBill_no(String bill_no) {
	this.bill_no = bill_no;
}
public String getOperate_date() {
	return operate_date;
}
public void setOperate_date(String operate_date) {
	this.operate_date = operate_date;
}
public String getCreate_date() {
	return create_date;
}
public void setCreate_date(String create_date) {
	this.create_date = create_date;
}
public String getIe_flag() {
	return ie_flag;
}
public void setIe_flag(String ie_flag) {
	this.ie_flag = ie_flag;
}
public BigDecimal getReal_pack_no() {
	return real_pack_no;
}
public void setReal_pack_no(BigDecimal real_pack_no) {
	this.real_pack_no = real_pack_no;
}
public BigDecimal getReal_gross_wt() {
	return real_gross_wt;
}
public void setReal_gross_wt(BigDecimal real_gross_wt) {
	this.real_gross_wt = real_gross_wt;
}
public BigDecimal getReal_gross_measure() {
	return real_gross_measure;
}
public void setReal_gross_measure(BigDecimal real_gross_measure) {
	this.real_gross_measure = real_gross_measure;
}
public String getBusinesstype() {
	return businesstype;
}
public void setBusinesstype(String businesstype) {
	this.businesstype = businesstype;
}
public String getArrivalplace() {
	return arrivalplace;
}
public void setArrivalplace(String arrivalplace) {
	this.arrivalplace = arrivalplace;
}

}
