/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif;

import java.io.IOException;
import java.util.List;

import android.os.Handler;
import android.util.Log;
import cn.wp.device.camera.onvif.probe.Scopes;
import cn.wp.device.camera.onvif.profile.Profile;
import cn.wp.device.camera.onvif.profile.Profiles;
import cn.wp.device.camera.onvif.response.GetScopesResponse;
import cn.wp.device.camera.onvif.response.GetVideoEncoderConfigurationOptions;
import cn.wp.device.camera.onvif.response.ProbeMatches;
import cn.wp.device.camera.utils.NetWorkUtil;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.device.camera.utils.XmlParser;
import cn.wp.device.camera.utils.XstreamUtil;

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
 * @Title XmlUtil.java 
 * @Package cn.ws.device.camera.utils 
 * @Description xml util 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��8�� ����6:15:24 
 * @version V1.0   
 */
public class OnvifUtil {
	
	public static final String TAG = OnvifUtil.class.getSimpleName();
	public static final boolean logEnable = false;
	
	/**
     * probe the onvif devices
     * @param probeOnvif post to the multicast addr
     * @return all devices
     */
	public static ProbeMatches getOnvifDevices(String probeOnvif,Handler mWorkHandler) {
		if (!ObjectCheck.validString(probeOnvif)) {
			Log.e(TAG, "getBaseUri, params are valid! probeOnvif:" + probeOnvif);
			return null;
		}
		return NetWorkUtil.probeOnvifDevice(probeOnvif.getBytes(),mWorkHandler);
	}
	
	/**
     * probe the onvif devices
     * @param probeOnvif post to the multicast addr
     * @return all devices
     */
	public static String getDeviceName(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getDeviceName, params are valid! ip:" + ip + ", message:" + message);
			return "";
		}
		String name = "";
		try {
			String namePrefix = "onvif://www.onvif.org/name/";
			String scopeRes = NetWorkUtil.sendPostRequest(ip, message);
			GetScopesResponse sr = XstreamUtil.xml2Scopes(scopeRes);
			if (sr != null) {
				List<Scopes> scopesList = sr.getScopesList();
				for (Scopes s:scopesList) {
					if (s.getScopeItem().contains(namePrefix)) {
						name = s.getScopeItem().substring(namePrefix.length());
					}
				}
			}
		} catch (IOException e) {
			Log.e(TAG, "getDeviceName, IOException e:" + e);
			return "";
		}
		return name;
	}
	
	public static String getDeviceId(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getDeviceId, params are valid! ip:" + ip + ", message:" + message);
			return "";
		}
		//<tds:SerialNumber>00EB8D610159</tds:SerialNumber>
		String id = "";
		try {
			String tag = "tds:SerialNumber";
			String deviceInfoRes = NetWorkUtil.sendPostRequest(ip, message);
			if (ObjectCheck.validString(deviceInfoRes)) {
				int start = deviceInfoRes.indexOf("<tds:SerialNumber>") + "<tds:SerialNumber>".length();
				int end = deviceInfoRes.indexOf("</tds:SerialNumber>");
				id = deviceInfoRes.substring(start, end);
			}
		} catch (IOException e) {
			Log.e(TAG, "getDeviceId, IOException e:" + e);
			return "";
		}
		return id;
	}
	
	public static String getPort(String ip,String message){
		Log.e(TAG, "getPort");
		if (ip == null || message == null) {
			Log.e(TAG, "getPort, params are valid! ip:" + ip + ", message:" + message);
			return "";
		}
		String port = "";
		try {
			String deviceInfoRes = NetWorkUtil.sendPostRequest(ip, message);
			if (ObjectCheck.validString(deviceInfoRes)) {
				int start = deviceInfoRes.indexOf("<tt:Port>") + "<tt:Port>".length();
				int end = deviceInfoRes.indexOf("</tt:Port>");
				port = deviceInfoRes.substring(start, end);
			}
		} catch (IOException e) {
			Log.e(TAG, "getDeviceId, IOException e:" + e);
			return "";
		}
		return port;
	}
	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static String getRtspAddr(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getRtspAddr, params are valid! ip:" + ip + ", message:" + message);
			return null;
		}
		String addr = "";
		try {
			XmlParser xp = new XmlParser();
			addr = xp.readRtspAddrFromSoapXml(NetWorkUtil.sendPostRequest(ip, message));
		} catch (IOException e) {
			Log.e(TAG, "getRtspAddr, IOException e:" + e);
			e.printStackTrace();
		}
		return addr;
	}
	
	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static String getSnapshotUri(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getSnapshotUri, params are valid! ip:" + ip + ", message:" + message);
			return null;
		}
		String uri = "";
		try {
			String snapshotUriResponse =  NetWorkUtil.sendPostRequest(ip, message);
			if (ObjectCheck.validString(snapshotUriResponse))//tag tt:Uri
				uri = XstreamUtil.getXmlBetweenTag(snapshotUriResponse, "tt:Uri");
		} catch (IOException e) {
			Log.e(TAG, "getRtspAddr, IOException e:" + e);
			e.printStackTrace();
		}
		return uri;
	}
	
	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static Profiles getProfiles(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getProfiles, params ip:" + ip + ", message:" + message);
			return null;
		}
		Profiles profiles = null;
		try {
			String profilesResponse =  NetWorkUtil.sendPostRequest(ip, message);
			if (ObjectCheck.validString(profilesResponse))
				profiles = XstreamUtil.xml2Profiles(profilesResponse);
		} catch (IOException e) {
			Log.e(TAG, "getProfiles, IOException e:" + e);
			return null;
		}
		return profiles;
	}
	
	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static GetVideoEncoderConfigurationOptions getVideoEncoderConfigurationOptions(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "getVideoEncoderConfigurationOptions, params ip:" + ip + ", message:" + message);
			return null;
		}
		GetVideoEncoderConfigurationOptions veco = null;
		try {
			String videoEncoderConfigurationOptionsResponse =  NetWorkUtil.sendPostRequest(ip, message);
			if (ObjectCheck.validString(videoEncoderConfigurationOptionsResponse))
				veco = XstreamUtil.xml2GetVideoEncoderConfigurationOptions(videoEncoderConfigurationOptionsResponse);
		} catch (IOException e) {
			Log.e(TAG, "getVideoEncoderConfigurationOptions, IOException e:" + e);
			e.printStackTrace();
		}
		return veco;
	}
	
	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static boolean setVideoEncoderConfiguration(String ip, String message) {
		if (ip == null || message == null) {
			Log.e(TAG, "setVideoEncoderConfiguration, params ip:" + ip + ", message:" + message);
			return false;
		}
		boolean suc = false;
		try {
			String setVecResponse = NetWorkUtil.sendPostRequest(ip, message);
			suc = setVecResponse.contains("SetVideoEncoderConfigurationResponse");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return suc;
	}
	
	
	/**
	 * ����onvif camera����ѷֱ���
	 * @param ip the access port of camera
	 * */
	public static boolean setResolution(String ip, Profile profile) {
		if (ip == null || profile == null) {
			Log.e(TAG, "setResolution, params ip:" + ip + ", profile:" + profile);
			return false;
		}
		String setVeRequest = OnvifRequest.generateSetVideoEncoderConfig(profile.getVideoEncoderConfiguration());
		//Log.i(TAG, "setResolution, setVeRequest:" + setVeRequest);
		ip = ip.replaceAll("device_service", "media");
		if(setVideoEncoderConfiguration(ip, setVeRequest)) {
			String wsdlGetProfileRequest = OnvifRequest.generateWsdlGetProfile(profile.getToken());
			//Log.i(TAG, "setResolution, wsdlGetProfileRequest:" + wsdlGetProfileRequest);
			try {
				String res = NetWorkUtil.sendPostRequest(ip, wsdlGetProfileRequest);
				//Log.i(TAG, "setResolution, wsdlGetProfileRes:" + res);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	/**
     * @param ip for access the device. include port
     * @param message onvif request post to the ip
     */
	public static boolean rebootCamera(String ip) {
		Log.i(TAG, "rebootCamera, params ip:" + ip);
		if (ip == null) {
			return false;
		}
		boolean suc = false;
		try {
			String setVecResponse = NetWorkUtil.sendPostRequest(ip, OnvifRequest.generateReboot());
			suc = setVecResponse.contains("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return suc;
	}
}
