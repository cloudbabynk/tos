package net.huadong.tech.util;

public class consignmentItemPackaging {
private String QuantityQuantity;
private String TypeCode;
private String MarksNumbers;
public String getTypeCode() {
	return TypeCode;
}

public void setTypeCode(String typeCode) {
	TypeCode = typeCode;
}

public String getMarksNumbers() {
	if(MarksNumbers==null)
	return	MarksNumbers = "";
	else
	return MarksNumbers;
}

public void setMarksNumbers(String marksNumbers) {
		MarksNumbers=marksNumbers;	
}

public String getQuantityQuantity() {
	return QuantityQuantity;
}

public void setQuantityQuantity(String quantityQuantity) {
	QuantityQuantity = quantityQuantity;
}
}
