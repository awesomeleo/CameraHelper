/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.model.CameraNetInfo;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.provider.CameraListProvider;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class PaneseCgiUtil extends CgiUtil{
	
	private static final String TAG = "CgiUtil";
	
	private static final String WEB_PATH             = "/cgi-bin/hi3510/";
	private static final String CGI_PARAM            = "param.cgi?";
	private static final String CGI_PTZ                    = "ptzctrl.cgi?";
	private static final String CGI_SNAP             = "/web/tmpfs/snap.jpg?";
	private static final String CGI_REBOOT           = "sysreboot.cgi";
	private static final String KEY_CMD              = "cmd";
	private static final String KEY_USR              = "-usr";
	private static final String KEY_PWD              = "-pwd";
	private static final String KEY_SNAP             = "-chn";
	private static final String KEY_PTZ_ACT          = "-act";
	private static final String KEY_PTZ_STEP    = "-step";
	private static final String KEY_PTZ_SPEED    = "-speed";
	private static final String KEY_PTZ_STATUS       = "-status";
	private static final String KEY_PTZ_NUMBER       = "-number";
	private static final String KEY_DHCP                   = "-dhcp";
	private static final String KEY_WIFIENABLE      = "-wf_enable";
	private static final String KEY_CHECK_WIFI           = "chkwirelessattr";
	private static final String KEY_SET_WIFI_ID      = "-wf_ssid";
	private static final String KEY_SET_WIFI_PW    = "-wf_key";
	private static final String KEY_SET_WIFI_AUTH    = "-wf_auth";
	private static final String KEY_SET_WIFI_ENC    = "-wf_enc";
	private static final String KEY_SET_WIFI_MODE    = "-wf_mode";
	private static final String KEY_CHANGE_OSD_REGION    = "-region";
	private static final String KEY_CHANGE_OSD_SHOW    = "-show";
	private static final String KEY_CHANGE_OSD_NAME    = "-name";
	
	private static final String VALUE_SET_PTZ        = "set";
	private static final String VALUE_GOTO_PTZ       = "goto";
	private static final String CMD_GETWIFI          = "getwirelessattr";
	private static final String CMD_SETWIFI          = "setwirelessattr";
	private static final String CMD_PTZ_CTRL        = "ptzctrl";
	private static final String CMD_GET_CHECKRESULT = "getchkwireless";
	private static final String CMD_SEARCHWIFI       = "searchwireless";
	private static final String CMD_GETP2P           = "getp2pattr";
	private static final String CMD_SETP2P           = "setp2pattr";
	private static final String CMD_PTZ              = "preset";
	private static final String CMD_CHANGE_OSD              = "setoverlayattr";
	private static final String CMD_GETVENC          = "getvencattr";
	private static final String CMD_GETNETINFO     = "getnetattr";
	private static final String CMD_SETNETINFO     =  "setnetattr";
	private static final String CMD_ABLEWIFI     =  "1";
	private static final String CMD_GET_OSD = "getoverlayattr";
	
	private static final String RETURN_SUCCESS      = "Succeed";
	private static final String RETURN_ERROR          = "Error";
	private static final String REBOOT_SUCCEED          = "reboot";
	
	public static final String STREAM_INDEX_FIRST   = "-chn=11";
	
	
	
	private static final String VAR_P2PID            = "p2p_uid";
	private static final String VAR_MYRSSI           = "wrssi[0]";
	private static final String VAR_FRATE1           = "fps_1";
	private static final String VAR_IP                     = "ip";
	private static final String VAR_MAC                = "macaddress";
	private static final String VAR_DHCP             = "dhcpflag";
	private static final String VAR_NET_TYPE    = "networktype";
	private static final String VAR_DHCP_ON    = "on";
	private static final String VAR_DHCP_OFF   = "off";
	public static final String VAR_PTZ_CTRL_LEFT = "left";
	public static final String VAR_PTZ_CTRL_RIGHT = "right";
	public static final String VAR_PTZ_CTRL_UP = "up";
	public static final String VAR_PTZ_CTRL_DOWN = "down";
	public static final String VAR_PTZ_CTRL_HOME = "home";
	public static final String VAR_PTZ_CTRL_STOP = "stop";
	public static final String VAR_PTZ_CTRL_ZOOM_IN = "zoomin";
	public static final String VAR_PTZ_CTRL_ZOOM_OUT = "zoomout";
	public static final String VAR_PTZ_CTRL_PRESET_GOTO = "goto";
	public static final String VAR_PTZ_CTRL_PRESET_SET = "set";
	
	private static final String VAR_WIFI_ACCESS_POINT   = "waccess_points";
	private static final String VAR_WIFI_SSID           = "wessid";
	private static final String VAR_WIFI_CONMOD = "wnet";
	private static final String VAR_WIFI_WAUTH     = "wauth";
	private static final String VAR_WIFI_RSSI              = "wrssi";
	private static final String VAR_WIFI_WENC              = "wenc";
	private static final String VAR_SET_WIFI_ID      = "wf_ssid";
	private static final String VAR_SET_WIFI_PW    = "wf_key";
	private static final String VAR_SET_WIFI_AUTH    = "wf_auth";
	private static final String VAR_SET_WIFI_ENC    = "wf_enc";
	private static final String VAR_SET_WIFI_MODE    = "wf_mode";
	
	private static final String CGI_SET_OK           = "set ok";//[Succeed]set ok.


	private static String callCgiByGet(String urlStr, Map<String, String> map,Context context) {
		Log.i(TAG, "callCgiByGet, params urlStr:" + urlStr + ", map:" + map);
		if (!ObjectCheck.validString(urlStr)) {
			return "";
		}
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		if (map != null)
			pMap.putAll(map);
		pMap.put(KEY_USR, user);
		pMap.put(KEY_PWD, password);
		Log.i(TAG, "callCgiByGet,final" + urlStr + pMap);
		try {
			return NetWorkUtil.sendGetRequest(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "callCgiByGet," + e);
			return "";
		}
	}
	

	public String getWirelessattr(String urlStr, Map<String, String> map,Context context) {
		//http://10.0.0.136/web/cgi-bin/hi3510/param.cgi?cmd=getwirelessattr&cmd=getnetattr
		Log.i(TAG, "getwirelessattr, params urlStr:" + urlStr + ", map:" + map);
		String res = "";
		if (!ObjectCheck.validString(urlStr)) {
			return res;
		}
		urlStr = urlStr + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_GETWIFI);
		if (map != null)
			pMap.putAll(map);
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "getWirelessattr," + e);
			return "";
		}
		return res;
		/* response from Panese ipcam
		 * var wf_enable="1";
		 * var wf_ssid="WePu_Public"; 
		 * var wf_auth="2"; 
		 * var wf_key="ws20130620"; 
		 * var wf_enc="0"; 
		 * var wf_mode="0"; 
		 * var dhcpflag="on"; 
		 * var ip="10.0.0.136"; 
		 * var netmask="255.255.255.0"; 
		 * var gateway="10.0.0.1"; 
		 * var dnsstat="1"; 
		 * var fdnsip="10.0.0.1"; 
		 * var sdnsip=""; 
		 * var macaddress="00:42:6A:DF:BF:BA"; 
		 * var networktype="LAN"; */
	}

	public String getWirelessName(String urlStr, Map<String, String> map,Context context) {
		Log.i(TAG, "getWirelessName, params urlStr:" + urlStr + ", map:" + map);
		String wName = "";
		if (!ObjectCheck.validString(urlStr)) {
			return wName;
		}
		try {
			String res = getWirelessattr(urlStr, map,context);
			Map<String, String> pMap = splitResponse(res);
			wName = pMap.get("wf_ssid");
			Log.i(TAG, "getWirelessName, name:" + wName);
		} catch (Exception e) {
			Log.i(TAG, "getWirelessName," + e);
			return "";
		}
		return wName;
	}
	

	public String searchWireless(String urlStr,Context context) {
		Log.i(TAG, "searchWireless, params urlStr:" + urlStr);
		String res = "";
		if (!ObjectCheck.validString(urlStr)) {
			return res;
		}
		urlStr = urlStr + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_SEARCHWIFI);
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "searchWireless," + e);
			return "";
		}
		return res;
		            
		/* response from Panese ipcam
		 * var waccess_points="1";
		 * var wchannel=new Array();
		 * var wrssi=new Array();
		 * var wessid=new Array();
		 * var wenc=new Array(); 
		 * var wauth=new Array();
		 * var wnet=new Array(); 
		 * wchannel[0]="6";
		 * wrssi[0]="100";
		 * wessid[0]="TP-LINK_FC0960";
		 * wenc[0]="AES";
		 * wauth[0]="WPA-PSK";
		 * wnet[0]="Infra"; */
	}
	

	public String getWirelessRssi(String urlStr,Context context) {
		Log.i(TAG, "getWirelessRssi, params urlStr:" + urlStr);
		String rssi = "";
		if (!ObjectCheck.validString(urlStr)) {
			return rssi;
		}
		try {
			String res = searchWireless(urlStr,context);
			Map<String, String> pMap = splitResponse(res);
			rssi = pMap.get(VAR_MYRSSI);
			Log.i(TAG, "getWirelessRssi, rssi:" + rssi);
		} catch (Exception e) {
			Log.i(TAG, "getWirelessRssi," + e);
			return "";
		}
		return rssi;
	}
	

	public Uri getSnapshotByCgi(String ip, File cache, String filename) {
		Log.i(TAG, "getSnapshotByCgi, params ip:" + ip
				+ ", cache:" + cache + ", filename:" + filename);
		if (!ObjectCheck.validString(ip)|| !ObjectCheck.validObject(cache)
				|| !ObjectCheck.validString(filename)) {
			return null;
		}
		String path = ip + CGI_SNAP + "&" + KEY_USR + "=" + user + "&" + KEY_PWD + "=" + password;
		Log.i(TAG, "getSnapshotByCgi, getImageURI path:" + path);
		try {
			return NetWorkUtil.getImageURI(path, cache, filename);
		} catch (Exception e) {
			Log.i(TAG, "getSnapshotByCgi," + e);
			return null;
		}
	}
	

	public boolean gotoPreSet(String url,Context context){
		Log.i(TAG, "gotoPreSet, params ip:" + url);
		boolean status = false;
		url =  url.substring(0, url.indexOf(":8080"));
		String urlStr = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_PTZ);
		pMap.put(KEY_PTZ_ACT, "goto");
		pMap.put(KEY_PTZ_NUMBER, "0");
		String res = "";
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "gotoPreSet," + e);
			return false;
		}
		if (res.contains(RETURN_SUCCESS)) {
			status = true;
		}
		return status;
	}
	// http://192.169.1.127/cgi-bin/hi3510/param.cgi?cmd=preset&-act=set&-status=1&-number=0&-usr=admin&-pwd=ws20130620
	public boolean presetPTZ(String ip, String action,int status, int index,Context context) {
		Log.i(TAG, "presetPTZ, params ip:" + ip
				+ ", status:" + status + ", index:" + index + ",action:" + action);
		if (!ObjectCheck.validString(ip) || status < 0 || index < 0) {
			return false;
		}
		ip = ip.substring(0, ip.indexOf(":8080"));
		String urlStr = ip + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_PTZ);
		pMap.put(KEY_PTZ_ACT, action);
		pMap.put(KEY_PTZ_STATUS, String.valueOf(status));
		pMap.put(KEY_PTZ_NUMBER, String.valueOf(index));
		String res = "";
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "presetPTZ," + e);
			return false;
		}
		if (res.contains(RETURN_SUCCESS)) {
			return true;
		}
		return false;
	}
	

	public String getP2p(String urlStr,Context context) {
		Log.i(TAG, "getP2p, params urlStr:" + urlStr);
		String res = "";
		if (!ObjectCheck.validString(urlStr)) {
			return res;
		}
		urlStr = urlStr + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_GETP2P);
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "getP2p," + e);
			return "";
		}
		return res;
		     
		/* response from Panese ipcam
		 * var p2p_enable="0";
		 * var p2p_uid="";
		 * var p2p_server1="50.19.254.134";
		 * var p2p_server2="122.248.234.207";
		 * var p2p_server3="m2.iotcplatform.com";
		 * var p2p_server4="m5.iotcplatform.com"; */
	}
	

	public String getP2pId(String urlStr,Context context) {
		Log.i(TAG, "getP2pId, params urlStr:" + urlStr);
		String id = "";
		String p2pAttr = getP2p(urlStr,context);
		if (!ObjectCheck.validString(p2pAttr)) {
			return id;
		}
		Map<String, String> pMap = splitResponse(p2pAttr);
		Set<String> keySet = pMap.keySet();
		for (String key:keySet) {
			if (key.contains(VAR_P2PID)) {
				id = pMap.get(key);
				break;
			}
		}
		Log.i(TAG, "getP2pId, id:" + id);
		return id;
		     
		/* response from Panese ipcam
		 * var p2p_enable="0";
		 * var p2p_uid="";
		 * var p2p_server1="50.19.254.134";
		 * var p2p_server2="122.248.234.207";
		 * var p2p_server3="m2.iotcplatform.com";
		 * var p2p_server4="m5.iotcplatform.com"; */
	}
	

	public boolean setP2p(String urlStr, Map<String, String> map,Context context) {
		Log.i(TAG, "setP2p, params urlStr:" + urlStr + ", map:" + map);
		if (!ObjectCheck.validString(urlStr) || !ObjectCheck.validObject(map)) {
			return false;
		}
		urlStr = urlStr + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_SETP2P);
		if (map != null)
			pMap.putAll(map);
		try {
			String res = callCgiByGet(urlStr, pMap,context);
			if (res != null && res.contains(CGI_SET_OK)) {
				return true;
			}
		} catch (Exception e) {
			Log.i(TAG, "setP2p," + e);
			return false;
		}
		return false;
	}
	
	public boolean setP2pId(String urlStr, String id,Context context) {
		Log.i(TAG, "setP2pId, params urlStr:" + urlStr + ", id:" + id);
		if (!ObjectCheck.validString(urlStr) || !ObjectCheck.validString(id)) {
			return false;
		}
		String p2pAttr = getP2p(urlStr,context);
		if (!ObjectCheck.validString(p2pAttr)) {
			return false;
		}
		Map<String, String> pMap = splitResponse(p2pAttr);
		pMap.put(VAR_P2PID, id);
		return setP2p(urlStr, formatParams(pMap),context);
	}
	
	public static String getVencAttrWithIndex(String urlStr, String index,Context context) {
		Log.i(TAG, "getVencAttr, params urlStr:" + urlStr + ", index:" + index);
		String res = "";
		if (!ObjectCheck.validString(urlStr) || !ObjectCheck.validString(index)) {
			return res;
		}
		urlStr = urlStr + WEB_PATH + CGI_PARAM + KEY_CMD + "=" + CMD_GETVENC + "&" + index;
		try {
			res = callCgiByGet(urlStr, null,context);
		} catch (Exception e) {
			Log.i(TAG, "getVencAttrWithIndex," + e);
			return "";
		}
		return res;
		/* response from Panese ipcam
		 * var bps_1="96"; 
		 * var fps_1="16"; 
		 * var gop_1="32"; 
		 * var brmode_1="1"; 
		 * var imagegrade_1="1"; 
		 * var width_1="1280"; 
		 * var height_1="720"; */
	}

	/**
	 * ��ȡ�豸��Ƶ�������
	 * @param urlStr �����ַ
	 * @return �����ַ������
	 * @author jjj
	 * @see
	 */
	public static int getFrameRateWithIndex(String urlStr, String index,Context context) {
		Log.i(TAG, "getFrameRateWithIndex, params urlStr:" + urlStr);
		int fRate = -1;
		String vencAttr = getVencAttrWithIndex(urlStr, index,context);
		if (!ObjectCheck.validString(vencAttr)) {
			return fRate;
		}
		Map<String, String> pMap = splitResponse(vencAttr);
		Set<String> keySet = pMap.keySet();
		for (String key:keySet) {
			if (key.contains(VAR_FRATE1)) {
				try {
					fRate = Integer.valueOf(pMap.get(key));
				} catch (NumberFormatException e) {
					Log.i(TAG, "getFrameRateWithIndex, NumberFormatException e:" + e);
				}
				break;
			}
		}
		Log.i(TAG, "getFrameRateWithIndex, fRate:" + fRate);
		return fRate;
	}
		
	public boolean rebootCam(String ip,Context context) {
		Log.i(TAG, "rebootCam, params ip:" + ip);
		if (!ObjectCheck.validString(ip)) {
			return false;
		}
		String urlStr = ip + WEB_PATH + CGI_REBOOT;
		String res = "";
		try {
			res = callCgiByGet(urlStr, null,context);
		} catch (Exception e) {
			Log.i(TAG, "rebootCam," + e);
			return false;
		}
		Log.i(TAG, "rebootCam, res:" + res);
		if (res.contains(REBOOT_SUCCEED)) {
			return true;
		}
		return false;
	}

	private static Map<String, String> splitResponse(String cgiRes) {
		//Log.i(TAG, "splitResponse, params cgiRes:" + cgiRes);
		Map<String, String> resMap =  new LinkedHashMap<String, String>(); 
		if (!ObjectCheck.validString(cgiRes)) {
			return resMap;
		}
		cgiRes = cgiRes.replaceAll("var", "").replaceAll(" ", "")
				.replaceAll("\n", "").replaceAll("\r", "");
		//Log.i(TAG, "splitResponse, after filt, cgiRes:" + cgiRes);
		String[] params = cgiRes.split(";");
		for(String param:params) {
			//Log.i(TAG, "splitResponse, param:" + param);
			String[] keyValues = param.split("=");
			if (keyValues != null && keyValues.length == 2) {
				String key = keyValues[0];
				String value = keyValues[1]==null?"":keyValues[1].replaceAll("\"", "");
				//Log.i(TAG, "splitResponse, key:" + key + ", value:" + value);
				resMap.put(key, value);
			}
		}
		return resMap;
	}
	
	private static Map<String, String> formatParams(Map<String, String> map) {
		Map<String, String> fMap =  new LinkedHashMap<String, String>(); 
		if (!ObjectCheck.validObject(map)) {
			return fMap;
		}
		Set<String> keySet = map.keySet();
		for (String key:keySet) {
			fMap.put("-" + key, map.get(key));
		}
		return fMap;
	}

	public CameraNetInfo getNetworkInfo(String url,Context context){
		Log.i(TAG, "getNetworkInfo,  urlStr:" + url);
		if(!ObjectCheck.validString(url))
			return null;
		String res = "";
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_GETNETINFO);
		try {
			res = callCgiByGet(url, pMap,context);
		} catch (Exception e) {
			Log.i(TAG, "getNetworkInfo," + e);
			return null;
		}
		Map<String, String> tempMap = splitResponse(res);
		if(!ObjectCheck.validObject(tempMap))
			return null;
		CameraNetInfo cameraNetInfo = new CameraNetInfo();
		Set<String> keySet = tempMap.keySet();
		for (String key:keySet) {
			if (key.equals(VAR_IP)) {
				cameraNetInfo.setIp(tempMap.get(key));
			}else if(key.contains(VAR_NET_TYPE)){
				cameraNetInfo.setNetType(tempMap.get(key));
			}else if(key.contains(VAR_DHCP)){
				boolean status = false;
				if(tempMap.get(key).equals(VAR_DHCP_ON))
					status = true;
				cameraNetInfo.setDhcpOn(status);
			}else if(key.contains(VAR_MAC)){
				cameraNetInfo.setMac(tempMap.get(key));
			}
		}
		if(ObjectCheck.validString(cameraNetInfo.getNetType()))
			if(cameraNetInfo.getNetType().equals("WirelessLAN")){
				CameraWifi myWifi = new CameraWifi();
				myWifi.setWifiOn(true);
				
				Map<String, String> pWifiMap = new LinkedHashMap<String, String>();
				pWifiMap.put(KEY_CMD, CMD_GETWIFI);
				String wifiRes = null;
				wifiRes = getWirelessattr(url,pWifiMap,context);
				Map<String, String> tempWifiMap = splitResponse(wifiRes);
				if(!ObjectCheck.validObject(tempWifiMap))
					return null;
				Set<String> wifikeySet = tempWifiMap.keySet();
				for (String key:wifikeySet) {
					if (key.contains(VAR_SET_WIFI_ID)) {
						myWifi.setSsId(tempWifiMap.get(key));
					}else if(key.contains(VAR_SET_WIFI_AUTH)){
						myWifi.setWauth(Integer.parseInt(tempWifiMap.get(key)));
					}else if(key.contains(VAR_SET_WIFI_ENC)){
						myWifi.setWenc(Integer.parseInt(tempWifiMap.get(key)));
					}else if(key.contains(VAR_SET_WIFI_MODE)){
						myWifi.setConMod(Integer.parseInt(tempWifiMap.get(key)));
					}else if(key.contains(VAR_SET_WIFI_PW)){
						myWifi.setPw(tempWifiMap.get(key));
					}
				}
				String rssi = getWirelessRssi(url,context);
				if(ObjectCheck.validString(rssi))
					myWifi.setRssi(Integer.parseInt(rssi));
				cameraNetInfo.setMyWifi(myWifi);
			}
		return cameraNetInfo;
	}
	
	
	public boolean openDHCP(String url,Context context){
		Log.i(TAG, "openDHCP,  urlStr:" + url);
		boolean status = false;
		if(!ObjectCheck.validString(url))
			return status;
		String res = "";
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_SETNETINFO);
		pMap.put(KEY_DHCP, VAR_DHCP_ON);
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS))
				status = true;
		} catch (Exception e) {
			Log.i(TAG, "openDHCP," + e);
			return false;
		}
		Log.i(TAG, "openDHCP,return infor:" + res);
		return status;
	}
	
	public boolean openWifi(String url,Context context){
		Log.i(TAG, "openWifi,  urlStr:" + url);
		boolean status = false;
		if(!ObjectCheck.validString(url))
			return status;
		String res = "";
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_SETWIFI);
		pMap.put(KEY_WIFIENABLE, CMD_ABLEWIFI);
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS))
				status = true;
		} catch (Exception e) {
			Log.i(TAG, "openWifi," + e);
			return false;
		}
		Log.i(TAG, "openWifi,return infor:" + res);
		return status;
	}
	public List<CameraWifi> scanWifi(String url,Context context){
		List<CameraWifi> wifiList = null;
		int accessPoints = 0;
		String res = 	searchWireless(url,context);
		if(!ObjectCheck.validString(res))
			return null;
		Map<String, String> pMap = splitResponse(res);
		Set<String> keySet = pMap.keySet();
		for (String key:keySet)
			if (key.contains(VAR_WIFI_ACCESS_POINT)) {
				accessPoints = Integer.parseInt(pMap.get(key));
				break;
			}
		if(accessPoints == 0)
			return null;
		wifiList = new ArrayList<CameraWifi>();
		for(int i = 0;i < accessPoints;i++){
			CameraWifi wifi = new CameraWifi();
			for(String myKey:keySet){
				if(myKey.contains(VAR_WIFI_SSID + "[" + i + "]")){
					wifi.setSsId(pMap.get(myKey));
				}else if(myKey.contains(VAR_WIFI_CONMOD + "[" + i + "]")){
					String mod = pMap.get(myKey);
					int modN = -1;
					if(mod.equals("Infra"))
						modN = 0;
					else
						modN = 1;
					wifi.setConMod(modN);
				}else if(myKey.contains(VAR_WIFI_RSSI + "[" + i + "]")){
					int rssi = Integer.parseInt(pMap.get(myKey));
					wifi.setRssi(rssi);
				}else if(myKey.contains(VAR_WIFI_WAUTH  + "[" + i + "]")){
					String auth = pMap.get(myKey);
					int authN = -1;
					if(auth.equals("WEP"))
						authN = 1;
					else if(auth.equals("WPA-PSK"))
						authN = 2;
					else if(auth.equals("WPA(2)-PSK"))
						authN = 3;
					else if(auth.equals("no"))
						authN = 0;
					wifi.setWauth(authN);
				}else if(myKey.contains(VAR_WIFI_WENC  + "[" + i + "]")){
					String enc = pMap.get(myKey);
					int encN = -1;
					if(enc.equals("TKIP"))
						encN = 0;
					else if(enc.equals("AES"))
						encN = 1;
					wifi.setWenc(encN);
				}
			}
			wifiList.add(wifi);
		}
		return wifiList;
	}
	
	public boolean checkWifi(String url,CameraWifi wifi,Context context){
		Log.i(TAG, "checkWifi,  urlStr:" + url);
		String res = "";
		boolean status = false;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD,KEY_CHECK_WIFI );
		pMap.put(KEY_SET_WIFI_ID, wifi.getSsId());
		pMap.put(KEY_SET_WIFI_PW, wifi.getPw());
		pMap.put(KEY_SET_WIFI_AUTH, String.valueOf(wifi.getWauth()));
		pMap.put(KEY_SET_WIFI_ENC, String.valueOf(wifi.getWenc()));
		pMap.put(KEY_SET_WIFI_MODE, String.valueOf(0));
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS))
				status = true;
			if(status){
				boolean firstCheck = false;
				boolean secondCheck = false;
				Map<String, String> pMapCheck = new LinkedHashMap<String, String>();
				pMapCheck.put(KEY_CMD,CMD_GET_CHECKRESULT );
				res = callCgiByGet(url, pMapCheck,context);
				String reRes = "";
				reRes = callCgiByGet(url, pMapCheck,context);
				if(res.contains("1"))
					firstCheck = true;
				if(reRes.contains("1"))
					secondCheck = true;
				if((!firstCheck) && (!secondCheck))
					status = false;
			}
		} catch (Exception e) {
			Log.i(TAG, "checkWifi," + e);
			return false;
		}
		return status;
	}
	
	public boolean setWifi(String url,CameraWifi wifi,Context context){
		Log.i(TAG, "setWifi,  urlStr:" + url);
		boolean status = false;
		if(!ObjectCheck.validString(url))
			return status;
		String res = "";
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD,CMD_SETWIFI );
		pMap.put(KEY_SET_WIFI_ID, wifi.getSsId());
		pMap.put(KEY_SET_WIFI_PW, wifi.getPw());
		pMap.put(KEY_SET_WIFI_AUTH, String.valueOf(wifi.getWauth()));
		pMap.put(KEY_SET_WIFI_ENC, String.valueOf(wifi.getWenc()));
		pMap.put(KEY_SET_WIFI_MODE, String.valueOf(0));
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS))
				status = true;
			if(status){
				status = checkWifi(url,wifi,context);
			}
		} catch (Exception e) {
			Log.i(TAG, "setWifi," + e);
			return false;
		}
		return status;
	}
	
	public boolean ptzCtrl(String url,String CMD,int speed,Context context){
		Log.i(TAG, "ptzCtrl,  urlStr:" + url);
		boolean status = false;
		String res = "";
		int last = -1;
		last = url.lastIndexOf(":");
		if(last == -1)
			return status;
		url = url.substring(0, last) + "/web";
		url = url + WEB_PATH + CGI_PTZ;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_PTZ_STEP, "0");
		pMap.put(KEY_PTZ_ACT, CMD);
		pMap.put(KEY_PTZ_SPEED, String.valueOf(speed));
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS))
				status = true;
		} catch (Exception e) {
			Log.i(TAG, "ptzCtrl : ," + CMD + e);
			return false;
		}
		return status;
	}
	public boolean changeOSD(String url,String name,Context context){
		Log.i(TAG, "changeOSD,  urlStr:" + url);
		boolean status = false;
		if(!ObjectCheck.validString(url))
			return status;
		int last = -1;
		last = url.lastIndexOf(":");
		if(last == -1)
			return status;
		url = url.substring(0, last) + "/web";
		String res = "";
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_CHANGE_OSD);
		pMap.put(KEY_CHANGE_OSD_REGION, "1");
		pMap.put(KEY_CHANGE_OSD_SHOW, "1");
		pMap.put(KEY_CHANGE_OSD_NAME, name);
		try {
			res = callCgiByGet(url, pMap,context);
			if(res.contains(RETURN_SUCCESS)){
				status = true;
				ContentValues values = new ContentValues(); 
				values.put(Defination.CameraTableColum.NAME.getName(), name);
				context.getContentResolver().update(CameraListProvider.CONTENT_URI, 
											values, Defination.CameraTableColum.IP.getName()+"=?"
											, new String[]{url.substring(7, last)});
			}
				
		} catch (Exception e) {
			Log.i(TAG, "changeOSD," + e);
			return false;
		}
		Log.i(TAG, "changeOSD,return infor:" + res);
		return status;
	}


	@Override
	public String getOSD(String url, Context context) {
		Log.i(TAG, "getOSD,  urlStr:" + url);
		String res = "";
		if(!ObjectCheck.validString(url)){
			return res;
		}
		url = url + WEB_PATH + CGI_PARAM;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_GET_OSD);
		pMap.put(KEY_CHANGE_OSD_REGION,"1");
		try {
			res = callCgiByGet(url, pMap,context);
			Map<String, String> resMap = splitResponse(res);
			// {show_1=1, place_1=0, format_1=0, x_1=0, y_1=0, name_1=智慧城堡}
			res = resMap.get("name_1");
			Log.i(TAG, "getOSD, name：" + res);
		} catch (Exception e) {
			Log.i(TAG, "getOSD," + e);
			return null;
		}
		return res;
	}
	
	@Override
	public boolean loginCam(String ip,Context context) {
		Log.i(TAG, "loginCamera,choose the cgi version:" + ip);
		// TODO Auto-generated method stub
		return false;
	}
}
