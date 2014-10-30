/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.profile;

import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Profiles_TAG;

import com.thoughtworks.xstream.annotations.XStreamAlias;
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
 * @Title VideoSourceConfiguration.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:26:02 
 * @version V1.0   
 */
public class VideoSourceConfiguration {
	@XStreamAsAttribute
	private String token;
	private String Name;
	private int UseCount;
	private String SourceToken;
	private Bounds Bounds;
	
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
	 * @return the sourceToken
	 */
	public String getSourceToken() {
		return SourceToken;
	}

	/**
	 * @param sourceToken the sourceToken to set
	 */
	public void setSourceToken(String sourceToken) {
		SourceToken = sourceToken;
	}

	/**
	 * @return the bounds
	 */
	public Bounds getBounds() {
		return Bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Bounds bounds) {
		Bounds = bounds;
	}

	public String toString() {
		String s = "VideoSourceConfiguration: "
				 + "token:" + token
				 + ", Name:" + Name
				 + ", UseCount:" + UseCount
				 + ", SourceToken:" + SourceToken
				 + ", Bounds:" + Bounds;
		return s;
	}
}
