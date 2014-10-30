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
 * @Title AudioEncoderConfiguration.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:40:42 
 * @version V1.0   
 */
public class AudioEncoderConfiguration {
	@XStreamAsAttribute
	private String token;
	private String Name;
	private int UseCount;
	private String Encoding;
	
	private int Bitrate;
	private int SampleRate;

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
	 * @return the bitrate
	 */
	public int getBitrate() {
		return Bitrate;
	}



	/**
	 * @param bitrate the bitrate to set
	 */
	public void setBitrate(int bitrate) {
		Bitrate = bitrate;
	}



	/**
	 * @return the sampleRate
	 */
	public int getSampleRate() {
		return SampleRate;
	}



	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(int sampleRate) {
		SampleRate = sampleRate;
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
		String s = "AudioEncoderConfiguration: "
				 + "token:" + token
				 + ", Name:" + Name
				 + ", UseCount:" + UseCount
				 + ", Encoding:" + Encoding
				 + ", Bitrate:" + Bitrate
				 + ", SampleRate:" + SampleRate
				 + ", Multicast:" + Multicast
				 + ", SessionTimeout:" + SessionTimeout;
		return s;
	}
	
}
