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
 * @Title PTZConfiguration.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:44:24 
 * @version V1.0   
 */
public class PTZConfiguration {
	@XStreamAsAttribute
	private String token;
	private String Name;
	private int UseCount;
	private String NodeToken;
	private String DefaultAbsolutePantTiltPositionSpace;
	private String DefaultAbsoluteZoomPositionSpace;
	private String DefaultRelativePanTiltTranslationSpace;
	private String DefaultRelativeZoomTranslationSpace;
	private String DefaultContinuousPanTiltVelocitySpace;
	private String DefaultContinuousZoomVelocitySpace;

	private DefaultPTZSpeed DefaultPTZSpeed;
	private String DefaultPTZTimeout;
	private PanTiltLimits PanTiltLimits;
	private ZoomLimits ZoomLimits;
	
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
	 * @return the nodeToken
	 */
	public String getNodeToken() {
		return NodeToken;
	}

	/**
	 * @param nodeToken the nodeToken to set
	 */
	public void setNodeToken(String nodeToken) {
		NodeToken = nodeToken;
	}

	/**
	 * @return the defaultAbsolutePantTiltPositionSpace
	 */
	public String getDefaultAbsolutePantTiltPositionSpace() {
		return DefaultAbsolutePantTiltPositionSpace;
	}

	/**
	 * @param defaultAbsolutePantTiltPositionSpace the defaultAbsolutePantTiltPositionSpace to set
	 */
	public void setDefaultAbsolutePantTiltPositionSpace(
			String defaultAbsolutePantTiltPositionSpace) {
		DefaultAbsolutePantTiltPositionSpace = defaultAbsolutePantTiltPositionSpace;
	}

	/**
	 * @return the defaultAbsoluteZoomPositionSpace
	 */
	public String getDefaultAbsoluteZoomPositionSpace() {
		return DefaultAbsoluteZoomPositionSpace;
	}

	/**
	 * @param defaultAbsoluteZoomPositionSpace the defaultAbsoluteZoomPositionSpace to set
	 */
	public void setDefaultAbsoluteZoomPositionSpace(
			String defaultAbsoluteZoomPositionSpace) {
		DefaultAbsoluteZoomPositionSpace = defaultAbsoluteZoomPositionSpace;
	}

	/**
	 * @return the defaultRelativePanTiltTranslationSpace
	 */
	public String getDefaultRelativePanTiltTranslationSpace() {
		return DefaultRelativePanTiltTranslationSpace;
	}

	/**
	 * @param defaultRelativePanTiltTranslationSpace the defaultRelativePanTiltTranslationSpace to set
	 */
	public void setDefaultRelativePanTiltTranslationSpace(
			String defaultRelativePanTiltTranslationSpace) {
		DefaultRelativePanTiltTranslationSpace = defaultRelativePanTiltTranslationSpace;
	}

	/**
	 * @return the defaultRelativeZoomTranslationSpace
	 */
	public String getDefaultRelativeZoomTranslationSpace() {
		return DefaultRelativeZoomTranslationSpace;
	}

	/**
	 * @param defaultRelativeZoomTranslationSpace the defaultRelativeZoomTranslationSpace to set
	 */
	public void setDefaultRelativeZoomTranslationSpace(
			String defaultRelativeZoomTranslationSpace) {
		DefaultRelativeZoomTranslationSpace = defaultRelativeZoomTranslationSpace;
	}

	/**
	 * @return the defaultContinuousPanTiltVelocitySpace
	 */
	public String getDefaultContinuousPanTiltVelocitySpace() {
		return DefaultContinuousPanTiltVelocitySpace;
	}

	/**
	 * @param defaultContinuousPanTiltVelocitySpace the defaultContinuousPanTiltVelocitySpace to set
	 */
	public void setDefaultContinuousPanTiltVelocitySpace(
			String defaultContinuousPanTiltVelocitySpace) {
		DefaultContinuousPanTiltVelocitySpace = defaultContinuousPanTiltVelocitySpace;
	}
	
	/**
	 * @return the defaultContinuousZoomVelocitySpace
	 */
	public String getDefaultContinuousZoomVelocitySpace() {
		return DefaultContinuousZoomVelocitySpace;
	}

	/**
	 * @param defaultContinuousZoomVelocitySpace the defaultContinuousZoomVelocitySpace to set
	 */
	public void setDefaultContinuousZoomVelocitySpace(
			String defaultContinuousZoomVelocitySpace) {
		DefaultContinuousZoomVelocitySpace = defaultContinuousZoomVelocitySpace;
	}

	/**
	 * @return the defaultPTZSpeed
	 */
	public DefaultPTZSpeed getDefaultPTZSpeed() {
		return DefaultPTZSpeed;
	}

	/**
	 * @param defaultPTZSpeed the defaultPTZSpeed to set
	 */
	public void setDefaultPTZSpeed(DefaultPTZSpeed defaultPTZSpeed) {
		DefaultPTZSpeed = defaultPTZSpeed;
	}

	/**
	 * @return the defaultPTZTimeout
	 */
	public String getDefaultPTZTimeout() {
		return DefaultPTZTimeout;
	}

	/**
	 * @param defaultPTZTimeout the defaultPTZTimeout to set
	 */
	public void setDefaultPTZTimeout(String defaultPTZTimeout) {
		DefaultPTZTimeout = defaultPTZTimeout;
	}

	/**
	 * @return the panTiltLimits
	 */
	public PanTiltLimits getPanTiltLimits() {
		return PanTiltLimits;
	}

	/**
	 * @param panTiltLimits the panTiltLimits to set
	 */
	public void setPanTiltLimits(PanTiltLimits panTiltLimits) {
		PanTiltLimits = panTiltLimits;
	}

	/**
	 * @return the zoomLimits
	 */
	public ZoomLimits getZoomLimits() {
		return ZoomLimits;
	}

	/**
	 * @param zoomLimits the zoomLimits to set
	 */
	public void setZoomLimits(ZoomLimits zoomLimits) {
		ZoomLimits = zoomLimits;
	}

	public String toString() {
		String s = "PTZConfiguration: "
				 + "token:" + token
				 + ", Name:" + Name
				 + ", UseCount:" + UseCount
				 + ", NodeToken:" + NodeToken
				 + ", DefaultAbsolutePantTiltPositionSpace:" + DefaultAbsolutePantTiltPositionSpace
				 + ", DefaultAbsoluteZoomPositionSpace:" + DefaultAbsoluteZoomPositionSpace
				 + ", DefaultRelativePanTiltTranslationSpace:" + DefaultRelativePanTiltTranslationSpace
				 + ", DefaultRelativeZoomTranslationSpace:" + DefaultRelativeZoomTranslationSpace
				 + ", DefaultContinuousPanTiltVelocitySpace:" + DefaultContinuousPanTiltVelocitySpace
				 + ", DefaultPTZSpeed:" + DefaultPTZSpeed
				 + ", DefaultPTZTimeout:" + DefaultPTZTimeout
				 + ", PanTiltLimits:" + PanTiltLimits
				 + ", ZoomLimits:" + ZoomLimits;
		return s;
	}
}
