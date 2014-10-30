package cn.wp.device.camera.utils;

public class BaseTag {
	private String S_Envelope = "" ;
	private String S_Body = "";
	private String ns2_loginResponse = "";
    private String _return = "";
    private String authenticationInfo = "";
    private String xmlns_S = "";
    private String xmlns_ns2 = "";
    private String version = "";
    private String userSign = "";
    public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserSign() {
		return userSign;
	}
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}
	public String getXmlns_S() {
		return xmlns_S;
	}
	public void setXmlns_S(String xmlns_S) {
		this.xmlns_S = xmlns_S;
	}
	public String getXmlns_ns2() {
		return xmlns_ns2;
	}
	public void setXmlns_ns2(String xmlns_ns2) {
		this.xmlns_ns2 = xmlns_ns2;
	}
	public String getS_Envelope() {
		return S_Envelope;
	}
	public void setS_Envelope(String s_Envelope) {
		S_Envelope = s_Envelope;
	}
	public String getS_Body() {
		return S_Body;
	}
	public void setS_Body(String s_Body) {
		S_Body = s_Body;
	}
	public String getNs2_loginResponse() {
		return ns2_loginResponse;
	}
	public void setNs2_loginResponse(String ns2_loginResponse) {
		this.ns2_loginResponse = ns2_loginResponse;
	}
	public String get_return() {
		return _return;
	}
	public void set_return(String _return) {
		this._return = _return;
	}
	public String getAuthenticationInfo() {
		return authenticationInfo;
	}
	public void setAuthenticationInfo(String authenticationInfo) {
		this.authenticationInfo = authenticationInfo;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getChannelInfoList() {
		return channelInfoList;
	}
	public void setChannelInfoList(String channelInfoList) {
		this.channelInfoList = channelInfoList;
	}
	public String getParameterInfoList() {
		return parameterInfoList;
	}
	public void setParameterInfoList(String parameterInfoList) {
		this.parameterInfoList = parameterInfoList;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getZoneFreqInfoList() {
		return zoneFreqInfoList;
	}
	public void setZoneFreqInfoList(String zoneFreqInfoList) {
		this.zoneFreqInfoList = zoneFreqInfoList;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	private String areaCode = "";
    private String channelInfoList ="";
    private String parameterInfoList ="";
    private String token = "";
    private String userId = "";
    private String zoneFreqInfoList = "";
    private String resultCode = "";
    private String resultMessage = "";
    private String ttvURL="";
	public String getTtvURL() {
		return ttvURL;
	}
	public void setTtvURL(String ttvURL) {
		this.ttvURL = ttvURL;
	}

}
