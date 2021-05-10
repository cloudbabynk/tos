package net.huadong.tech.util;

import java.util.List;

public class consignor {
private String ID;
private address Address;
private String Name;
private List<communication> communicationList;
private String AEO;
public address getAddress() {
	return Address;
}
public void setAddress(address address) {
	Address = address;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public List<communication> getCommunicationList() {
	return communicationList;
}
public void setCommunicationList(List<communication> communicationList) {
	this.communicationList = communicationList;
}
public String getAEO() {
	return AEO;
}
public void setAEO(String aEO) {
	AEO = aEO;
}
}
