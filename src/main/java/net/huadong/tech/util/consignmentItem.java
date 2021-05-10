package net.huadong.tech.util;

public class consignmentItem {
private  String SequenceNumeric;
private   consignmentItemPackaging ConsignmentItemPackaging;
private commodity Commodity;
private goodsMeasure GoodsMeasure;
public String getSequenceNumeric() {
	return SequenceNumeric;
}
public void setSequenceNumeric(String sequenceNumeric) {
	SequenceNumeric = sequenceNumeric;
}
public consignmentItemPackaging getConsignmentItemPackaging() {
	return ConsignmentItemPackaging;
}
public void setConsignmentItemPackaging(consignmentItemPackaging consignmentItemPackaging) {
	ConsignmentItemPackaging = consignmentItemPackaging;
}
public commodity getCommodity() {
	return Commodity;
}
public void setCommodity(commodity commodity) {
	Commodity = commodity;
}
public goodsMeasure getGoodsMeasure() {
	return GoodsMeasure;
}
public void setGoodsMeasure(goodsMeasure goodsMeasure) {
	GoodsMeasure = goodsMeasure;
}
}
