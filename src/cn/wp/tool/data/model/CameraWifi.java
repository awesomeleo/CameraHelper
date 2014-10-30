package cn.wp.tool.data.model;

import java.io.Serializable;

public class CameraWifi implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean wifiOn = false;
	private String ssId = "";
	private int rssi = 0;
	private int  conMod = -1;
	private int  wauth     = -1;
	private int  wenc       = -1;
	private String pw           = "";
	
	public String getSsId() {
		return ssId;
	}
	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getConMod() {
		return conMod;
	}
	public void setConMod(int conMod) {
		this.conMod = conMod;
	}
	public boolean isWifiOn() {
		return wifiOn;
	}
	public void setWifiOn(boolean wifiOn) {
		this.wifiOn = wifiOn;
	}
	public int getWauth() {
		return wauth;
	}
	public void setWauth(int wauth) {
		this.wauth = wauth;
	}
	public int getWenc() {
		return wenc;
	}
	public void setWenc(int wenc) {
		this.wenc = wenc;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
