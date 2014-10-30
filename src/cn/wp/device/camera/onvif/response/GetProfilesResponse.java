/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.response;

import cn.wp.device.camera.onvif.profile.Profiles;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
 * @Title GetProfilesResponse.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:21:52 
 * @version V1.0   
 */
public class GetProfilesResponse {
	//@XStreamAlias(Onvif_Profiles_TAG.Get_Profiles_Response)
	private Profiles Profiles;

	/**
	 * @return the profiles
	 */
	public Profiles getProfiles() {
		return Profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Profiles profiles) {
		Profiles = profiles;
	}

}
