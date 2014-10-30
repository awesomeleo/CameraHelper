/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.profile;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 *
 * <p>
 * <h1>Reviewer:</h1> 
 * <a href="mailto:jiangjunjie@1v.cn">jjj</a>
 * </p>
 * 
 * <p>
 * <h1>History Trace:</h1>
 * <li>2014-04-01    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title VideoEncoderConfiguration.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:29:52 
 * @version V1.0   
 */
public class VideoEncoderConfiguration {
	@XStreamAsAttribute
	private String token;
	private String Name;
	private int UseCount;
	private String Encoding;
	private Resolution Resolution;
	private int Quality;
	private RateControl RateControl;
	private H264 H264;
	private MPEG4 MPEG4;
	private Multicast Multicast;
	private String SessionTimeout;
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the useCount
	 */
	public int getUseCount() {
		return UseCount;
	}

	/**
	 * @param useCount the useCount to set
	 */
	public void setUseCount(int useCount) {
		UseCount = useCount;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return Encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		Encoding = encoding;
	}

	/**
	 * @return the resolution
	 */
	public Resolution getResolution() {
		return Resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(Resolution resolution) {
		Resolution = resolution;
	}

	/**
	 * @return the quality
	 */
	public int getQuality() {
		return Quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(int quality) {
		Quality = quality;
	}

	/**
	 * @return the rateControl
	 */
	public RateControl getRateControl() {
		return RateControl;
	}

	/**
	 * @param rateControl the rateControl to set
	 */
	public void setRateControl(RateControl rateControl) {
		RateControl = rateControl;
	}

	/**
	 * @return the h264
	 */
	public H264 getH264() {
		return H264;
	}

	/**
	 * @param h264 the h264 to set
	 */
	public void setH264(H264 h264) {
		H264 = h264;
	}
	
	/**
	 * @return the mPEG4
	 */
	public MPEG4 getMPEG4() {
		return MPEG4;
	}

	/**
	 * @param mPEG4 the mPEG4 to set
	 */
	public void setMPEG4(MPEG4 mPEG4) {
		MPEG4 = mPEG4;
	}

	/**
	 * @return the multicast
	 */
	public Multicast getMulticast() {
		return Multicast;
	}

	/**
	 * @param multicast the multicast to set
	 */
	public void setMulticast(Multicast multicast) {
		Multicast = multicast;
	}

	/**
	 * @return the sessionTimeout
	 */
	public String getSessionTimeout() {
		return SessionTimeout;
	}

	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(String sessionTimeout) {
		SessionTimeout = sessionTimeout;
	}

	public String toString() {
		String s = "VideoEncoderConfiguration: "
				 + "token:" + token
				 + ", Name:" + Name
				 + ", UseCount:" + UseCount
				 + ", Encoding:" + Encoding
				 + ", Resolution:" + Resolution
				 + ", Quality:" + Quality
				 + ", RateControl:" + RateControl
				 + ", H264:" + H264
				 + ", MPEG4:" + MPEG4
				 + ", Multicast:" + Multicast
				 + ", SessionTimeout:" + SessionTimeout;
		return s;
	}
}
