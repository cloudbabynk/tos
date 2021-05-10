package net.huadong.tech.util;

import java.util.List;

public class declaration {
	private representativePerson RepresentativePerson;
	private exitCustomsOffice ExitCustomsOffice;
	private agent Agent;
	private carrier Carrier;
	private borderTransportMeans BorderTransportMeans;
	/*private consignment Consignment;*/
	public List<consignment> consignmentList;
	
	public representativePerson getRepresentativePerson() {
		return RepresentativePerson;
	}
	public void setRepresentativePerson(representativePerson representativePerson) {
		RepresentativePerson = representativePerson;
	}
	public exitCustomsOffice getExitCustomsOffice() {
		return ExitCustomsOffice;
	}
	public void setExitCustomsOffice(exitCustomsOffice exitCustomsOffice) {
		ExitCustomsOffice = exitCustomsOffice;
	}
	public agent getAgent() {
		return Agent;
	}
	public void setAgent(agent agent) {
		Agent = agent;
	}
	public carrier getCarrier() {
		return Carrier;
	}
	public void setCarrier(carrier carrier) {
		Carrier = carrier;
	}
	public borderTransportMeans getBorderTransportMeans() {
		return BorderTransportMeans;
	}
	public void setBorderTransportMeans(borderTransportMeans borderTransportMeans) {
		BorderTransportMeans = borderTransportMeans;
	}

	
	public List<consignment> getConsignmentList() {
		return consignmentList;
	}
	public void setConsignmentList(List<consignment> consignmentList) {
		this.consignmentList = consignmentList;
	}
	/*public consignment getConsignment() {
		return Consignment;
	}
	public void setConsignment(consignment consignment) {
		Consignment = consignment;
	}*/
	

	
}
