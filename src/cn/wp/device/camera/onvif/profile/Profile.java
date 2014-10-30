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
 * @Title Profile.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��13�� ����2:14:12 
 * @version V1.0   
 */
public class Profile {
	@XStreamAsAttribute
	private boolean fixed;
	@XStreamAsAttribute
	private String token;
	//@XStreamAlias(Onvif_Profiles.Tag_Name)
	private String Name;
	
	private VideoSourceConfiguration VideoSourceConfiguration;
	private AudioSourceConfiguration AudioSourceConfiguration;
	private VideoEncoderConfiguration VideoEncoderConfiguration;
	private AudioEncoderConfiguration AudioEncoderConfiguration;
	private PTZConfiguration PTZConfiguration;
	
	private VideoAnalyticsConfiguration VideoAnalyticsConfiguration;
	
	/**
	 * @return the videoAnalyticsConfiguration
	 */
	public VideoAnalyticsConfiguration getVideoAnalyticsConfiguration() {
		return VideoAnalyticsConfiguration;
	}

	/**
	 * @param videoAnalyticsConfiguration the videoAnalyticsConfiguration to set
	 */
	public void setVideoAnalyticsConfiguration(
			VideoAnalyticsConfiguration videoAnalyticsConfiguration) {
		VideoAnalyticsConfiguration = videoAnalyticsConfiguration;
	}

	/**
	 * @return the fixed
	 */
	public boolean isFixed() {
		return fixed;
	}

	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

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
	 * @return the videoSourceConfiguration
	 */
	public VideoSourceConfiguration getVideoSourceConfiguration() {
		return VideoSourceConfiguration;
	}

	/**
	 * @param videoSourceConfiguration the videoSourceConfiguration to set
	 */
	public void setVideoSourceConfiguration(
			VideoSourceConfiguration videoSourceConfiguration) {
		VideoSourceConfiguration = videoSourceConfiguration;
	}

	/**
	 * @return the audioSourceConfiguration
	 */
	public AudioSourceConfiguration getAudioSourceConfiguration() {
		return AudioSourceConfiguration;
	}

	/**
	 * @param audioSourceConfiguration the audioSourceConfiguration to set
	 */
	public void setAudioSourceConfiguration(
			AudioSourceConfiguration audioSourceConfiguration) {
		AudioSourceConfiguration = audioSourceConfiguration;
	}

	/**
	 * @return the videoEncoderConfiguration
	 */
	public VideoEncoderConfiguration getVideoEncoderConfiguration() {
		return VideoEncoderConfiguration;
	}

	/**
	 * @param videoEncoderConfiguration the videoEncoderConfiguration to set
	 */
	public void setVideoEncoderConfiguration(
			VideoEncoderConfiguration videoEncoderConfiguration) {
		VideoEncoderConfiguration = videoEncoderConfiguration;
	}

	/**
	 * @return the audioEncoderConfiguration
	 */
	public AudioEncoderConfiguration getAudioEncoderConfiguration() {
		return AudioEncoderConfiguration;
	}

	/**
	 * @param audioEncoderConfiguration the audioEncoderConfiguration to set
	 */
	public void setAudioEncoderConfiguration(
			AudioEncoderConfiguration audioEncoderConfiguration) {
		AudioEncoderConfiguration = audioEncoderConfiguration;
	}

	/**
	 * @return the pTZConfiguration
	 */
	public PTZConfiguration getPTZConfiguration() {
		return PTZConfiguration;
	}

	/**
	 * @param pTZConfiguration the pTZConfiguration to set
	 */
	public void setPTZConfiguration(PTZConfiguration pTZConfiguration) {
		PTZConfiguration = pTZConfiguration;
	}

	public String toString() {
		String p = "Profile: fixed:" + fixed
				 + ", token:" + token
				 + ", Name:" + Name
				 + ", VideoSourceConfiguration:" + VideoSourceConfiguration
				 + ", AudioSourceConfiguration:" + AudioSourceConfiguration
				 + ", VideoEncoderConfiguration:" + VideoEncoderConfiguration
				 + ", AudioEncoderConfiguration:" + AudioEncoderConfiguration
				 + ", PTZConfiguration:" + PTZConfiguration;
		return p;
	}
}
