package net.huadong.tech.util;

import java.util.List;

public class contact {
private String Name;	
public List<communication> communicationList;

public String getName() {
	return Name;
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

}
