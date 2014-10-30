package cn.wp.device.camera.onvif.probe;

import com.google.gson.annotations.Expose;

public class DiscoveryHeader {
	
	@Expose
	private String wsaMessageId ;
	@Expose
	private String wsaRelatesTo;
	@Expose
	private String wsaReplyToAddress;
	@Expose
	private String wsaTo ;
	@Expose
	private String wsaAction ;
	
	public String getWsaMessageId() {
		return wsaMessageId;
	}
	public void setWsaMessageId(String wsaMessageId) {
		this.wsaMessageId = wsaMessageId;
	}
	public String getWsaRelatesTo() {
		return wsaRelatesTo;
	}
	public void setWsaRelatesTo(String wsaRelatesTo) {
		this.wsaRelatesTo = wsaRelatesTo;
	}
	public String getWsaReplyToAddress() {
		return wsaReplyToAddress;
	}
	public void setWsaReplyToAddress(String wsaReplyToAddress) {
		this.wsaReplyToAddress = wsaReplyToAddress;
	}
	public String getWsaTo() {
		return wsaTo;
	}
	public void setWsaTo(String wsaTo) {
		this.wsaTo = wsaTo;
	}
	public String getWsaAction() {
		return wsaAction;
	}
	public void setWsaAction(String wsaAction) {
		this.wsaAction = wsaAction;
	}
	
	public String toString() {
		return "wsaMessageId:" + wsaMessageId
				+ ", wsaRelatesTo:" + wsaRelatesTo
				+ ", wsaReplyToAddress:" + wsaReplyToAddress
				+ ", wsaTo:" + wsaTo
				+ ", wsaAction:" + wsaAction;
	}
	
}
