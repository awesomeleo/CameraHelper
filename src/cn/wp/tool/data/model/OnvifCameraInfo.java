/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.tool.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wp.device.camera.onvif.OnvifRequest;
import cn.wp.device.camera.onvif.OnvifUtil;
import cn.wp.device.camera.onvif.profile.Profile;
import cn.wp.device.camera.onvif.profile.Profiles;
import cn.wp.device.camera.onvif.veco.ResolutionsAvailable;


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
 * @Title OnvifCameraInfo.java 
 * @Package cn.ws.device.camera.impl 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��22�� ����10:31:21 
 * @version V1.0   
 */
public class OnvifCameraInfo {
	
	private static final String TAG = "OnvifCameraInfo";
	private OnvifCamera camera;
	private Profiles profiles;
	private Map<Profile, List<ResolutionsAvailable>> profile2ResoList = 
			new HashMap<Profile, List<ResolutionsAvailable>>();
	
	public OnvifCameraInfo(OnvifCamera camera, Profiles profiles) {
		this.camera = camera;
		this.profiles = profiles;
		if (profiles == null)
			fillData();
	}
	
	private void fillData() {
		if (camera != null)
			profiles = OnvifUtil.getProfiles(camera.getmBaseAccesssURL(), OnvifRequest.generateGetProfiles());
	}
	
	/**
	 * @return the camera
	 */
	public OnvifCamera getCamera() {
		return camera;
	}

	/**
	 * @param camera the camera to set
	 */
	public void setCamera(OnvifCamera camera) {
		this.camera = camera;
	}

	/**
	 * @return the profiles
	 */
	public Profiles getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}

	/**
	 * @return the profile2ResoList
	 */
	public Map<Profile, List<ResolutionsAvailable>> getProfile2ResoList() {
		return profile2ResoList;
	}

	/**
	 * @param profile2ResoList the profile2ResoList to set
	 */
	public void setProfile2ResoList(
			Map<Profile, List<ResolutionsAvailable>> profile2ResoList) {
		this.profile2ResoList = profile2ResoList;
	}
	
	/**
	 * @return the profiles
	 */
	public Profile getProfileByResolution(ResolutionsAvailable ra) {
		if (profiles == null || ra == null)
			return null;
		Profile profile = null;
		for (Profile p:profiles.getProfilesList()) {
			List<ResolutionsAvailable> raList = profile2ResoList.get(p);
			for (ResolutionsAvailable temp:raList) {
				if(temp.getWidth() == ra.getWidth() && temp.getHeight() == ra.getHeight()) { 
					profile = p;
				}
			}
		}
		return profile;
	}

}
