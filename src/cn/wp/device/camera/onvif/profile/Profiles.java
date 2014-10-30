/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.profile;

import java.util.ArrayList;
import java.util.List;

import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Profiles_TAG;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
 * @Title Profiles.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:22:17 
 * @version V1.0   
 */
public class Profiles {
	
	@XStreamImplicit(itemFieldName=Onvif_Profiles_TAG.Profiles)
	private List<Profile> ProfileList = new ArrayList<Profile>();
	
	public List<Profile> getProfilesList() {
		return ProfileList;
	}
	public void setProfilesList(List<Profile> Profile) {
		this.ProfileList = Profile;
	}
	
	public String toString() {
		String ps = "";
		for(Profile p:ProfileList) {
			ps += p.toString() + "\n";
		}
		return ps;
	}
}
