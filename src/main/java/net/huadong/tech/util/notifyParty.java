package net.huadong.tech.util;

import java.util.List;

public class notifyParty {
private String ID;
private String	Name;
private address Address;
private List<communication> communicationList;
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public address getAddress() {
	return Address;
}
public void setAddress(address address) {
	Address = address;
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


}
