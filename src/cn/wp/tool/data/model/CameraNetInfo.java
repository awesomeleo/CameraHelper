package cn.wp.tool.data.model;

import java.io.Serializable;
import java.util.List;

import cn.wp.tool.data.config.Defination;


public class CameraNetInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isDhcpOn = false;
	private String mac = "";
	private String ip = "";
	private String netType = Defination.Network_Type.LAN;
	private CameraWifi myWifi = null;
	private List<CameraWifi> wifiList = null;
	private String cameraNetName = null;
	
	
	public boolean isDhcpOn() {
		return isDhcpOn;
	}
	public void setDhcpOn(boolean isDhcpOn) {
		this.isDhcpOn = isDhcpOn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public List<CameraWifi> getWifiList() {
		return wifiList;
	}
	public void setWifiList(List<CameraWifi> wifiList) {
		this.wifiList = wifiList;
	}
	public CameraWifi getMyWifi() {
		return myWifi;
	}
	public void setMyWifi(CameraWifi myWifi) {
		this.myWifi = myWifi;
	}
	public String getCameraNetName() {
		return cameraNetName;
	}
	public void setCameraNetName(String cameraNetName) {
		this.cameraNetName = cameraNetName;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}

}

