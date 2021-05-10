package net.huadong.tech.util;

import java.util.List;

public class consignment {
private transportContractDocument TransportContractDocument;
private String GrossVolumeMeasure;
private loadingLocation LoadingLocation;
private  unloadingLocation UnloadingLocation;
private String CustomsStatusCode;
private freightPayment FreightPayment;
private consignmentPackaging ConsignmentPackaging;
private  List<String> routingCountryCodeList;
private goodsConsignedPlace GoodsConsignedPlace;
private transitDestination TransitDestination;
private totalGrossMassMeasure TotalGrossMassMeasure;
private deliveryDestination DeliveryDestination;
private consignee Consignee;
private address Address;
private consignor Consignor;
private notifyParty NotifyParty;
private consignmentItem ConsignmentItem;
public goodsConsignedPlace getGoodsConsignedPlace() {
	return GoodsConsignedPlace;
}
public void setGoodsConsignedPlace(goodsConsignedPlace goodsConsignedPlace) {
	GoodsConsignedPlace = goodsConsignedPlace;
}
public List<String> getRoutingCountryCodeList() {
	return routingCountryCodeList;
}
public void setRoutingCountryCodeList(List<String> routingCountryCodeList) {
	this.routingCountryCodeList = routingCountryCodeList;
}
public transportContractDocument getTransportContractDocument() {
	return TransportContractDocument;
}
public void setTransportContractDocument(transportContractDocument transportContractDocument) {
	TransportContractDocument = transportContractDocument;
}
public String getGrossVolumeMeasure() {
	return GrossVolumeMeasure;
}
public void setGrossVolumeMeasure(String grossVolumeMeasure) {
	GrossVolumeMeasure = grossVolumeMeasure;
}
public loadingLocation getLoadingLocation() {
	return LoadingLocation;
}
public void setLoadingLocation(loadingLocation loadingLocation) {
	LoadingLocation = loadingLocation;
}
public unloadingLocation getUnloadingLocation() {
	return UnloadingLocation;
}
public void setUnloadingLocation(unloadingLocation unloadingLocation) {
	UnloadingLocation = unloadingLocation;
}
public String getCustomsStatusCode() {
	return CustomsStatusCode;
}
public void setCustomsStatusCode(String customsStatusCode) {
	CustomsStatusCode = customsStatusCode;
}
public freightPayment getFreightPayment() {
	return FreightPayment;
}
public void setFreightPayment(freightPayment freightPayment) {
	FreightPayment = freightPayment;
}
public consignmentPackaging getConsignmentPackaging() {
	return ConsignmentPackaging;
}
public void setConsignmentPackaging(consignmentPackaging consignmentPackaging) {
	ConsignmentPackaging = consignmentPackaging;
}
public totalGrossMassMeasure getTotalGrossMassMeasure() {
	return TotalGrossMassMeasure;
}
public void setTotalGrossMassMeasure(totalGrossMassMeasure totalGrossMassMeasure) {
	TotalGrossMassMeasure = totalGrossMassMeasure;
}
public deliveryDestination getDeliveryDestination() {
	return DeliveryDestination;
}
public void setDeliveryDestination(deliveryDestination deliveryDestination) {
	DeliveryDestination = deliveryDestination;
}
public consignee getConsignee() {
	return Consignee;
}
public void setConsignee(consignee consignee) {
	Consignee = consignee;
}
public address getAddress() {
	return Address;
}
public void setAddress(address address) {
	Address = address;
}
public consignor getConsignor() {
	return Consignor;
}
public void setConsignor(consignor consignor) {
	Consignor = consignor;
}
public notifyParty getNotifyParty() {
	return NotifyParty;
}
public void setNotifyParty(notifyParty notifyParty) {
	NotifyParty = notifyParty;
}
public consignmentItem getConsignmentItem() {
	return ConsignmentItem;
}
public void setConsignmentItem(consignmentItem consignmentItem) {
	ConsignmentItem = consignmentItem;
}
public transitDestination getTransitDestination() {
	return TransitDestination;
}
public void setTransitDestination(transitDestination transitDestination) {
	TransitDestination = transitDestination;
}

}
