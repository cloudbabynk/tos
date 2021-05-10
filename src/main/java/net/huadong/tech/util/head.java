package net.huadong.tech.util;

public class head {
	    private String  MessageID;//报文编号  
		private String  FunctionCode;//
	    private String  MessageType;//报文类型  
	    private String SenderID;//
	    private String ReceiverID;//
	    private String  SendTime;//发送时间  
	    private String Version;
	    public String getMessageID() {
			return MessageID;
		}
		public void setMessageID(String messageID) {
			MessageID = messageID;
		}
		public String getFunctionCode() {
			return FunctionCode;
		}
		public void setFunctionCode(String functionCode) {
			FunctionCode = functionCode;
		}
		public String getMessageType() {
			return MessageType;
		}
		public void setMessageType(String messageType) {
			MessageType = messageType;
		}
		public String getSenderID() {
			return SenderID;
		}
		public void setSenderID(String senderID) {
			SenderID = senderID;
		}
		public String getReceiverID() {
			return ReceiverID;
		}
		public void setReceiverID(String receiverID) {
			ReceiverID = receiverID;
		}
		public String getSendTime() {
			return SendTime;
		}
		public void setSendTime(String sendTime) {
			SendTime = sendTime;
		}
		public String getVersion() {
			return Version;
		}
		public void setVersion(String version) {
			Version = version;
		}
		@Override
		public String toString() {
			return "Head [MessageID=" + MessageID + ", FunctionCode=" + FunctionCode + ", MessageType=" + MessageType
					+ ", SenderID=" + SenderID + ", ReceiverID=" + ReceiverID + ", SendTime=" + SendTime + ", Version="
					+ Version + "]";
		}
	    
}
