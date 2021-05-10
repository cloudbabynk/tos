package net.huadong.tech.util;

import java.util.List;

public class consignee {
	private String ID;
	private address Address;
	private String Name;
	//private communication Communication;
	private List<communication> communicationList;
	private contact Contact;
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
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
//	public communication getCommunication() {
//		return Communication;
//	}
//	public void setCommunication(communication communication) {
//		Communication = communication;
//	}
	public contact getContact() {
		return Contact;
	}
	public void setContact(contact contact) {
		Contact = contact;
	}
	public void setName(String name) {
		Name = name;
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
