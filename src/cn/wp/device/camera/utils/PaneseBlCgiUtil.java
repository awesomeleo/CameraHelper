package cn.wp.device.camera.utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import cn.wp.tool.data.model.CameraNetInfo;
import cn.wp.tool.data.model.CameraWifi;

public class PaneseBlCgiUtil extends CgiUtil {
	private static String TAG = PaneseBlCgiUtil.class.getSimpleName();
	private static final String CGI_CGIInstance      = "/CGIInstance.fcgi?";
	private static final String CGI_NET              = "/network.fcgi?";
	private static final String CGI_VIDEO            = "/video.fcgi?";
	private static final String CGI_SYSTEM           = "/system.fcgi?";
	private static final String CGI_PTZ              = "/ptz.fcgi?";
	private static final String CGI_USER			 = "/UsrAccount.fcgi?";
	
	private static final String KEY_CMD              = "cmd";
	private static final String KEY_USR              = "usr";
	private static final String KEY_PWD              = "pwd";
	private static final String KEY_PTZ_NUMBER       = "dir";
	private static final String KEY_ISENABLE         = "isEnable";
	private static final String KEY_PORT             = "port";
	private static final String KEY_HOST             = "host";
	
	private static final String KEY_SNAP             = "-chn";

	
	
	private static final String VALUE_GOTO_PTZ       = "ptzMove";
	private static final String CMD_GETWIFI          = "getWifiConfig";
	private static final String CMD_SETWIFI          = "setWifiConfig";
	private static final String CMD_SEARCHWIFI       = "refreshWifiList";
	
	private static final String CMD_GETP2P           = "getP2PInfo";
	private static final String CMD_SNAP             = "snapPicture";
	private static final String CMD_REBOOT           = "reboot";
	
	
	private static final String CMD_PTZ_MOVE         = "ptzMove";
	private static final String CMD_PTZ_RESET        = "ptzReset";
	private static final String CMD_GETVENC          = "getvencattr";
	
	private static final String CMD_GETYCJ           = "getYCJConfig";
	private static final String CMD_SETYCJ           = "setYCJConfig";
	private static final String CMD_GETDEVSTATE      = "getDevState";
	private static final String CMD_GETPORTINFO      = "getPortInfo";
	
	private static final String CMD_LOGIN			 = "logIn";
	
	private static final String STREAM_INDEX_FIRST    = "-chn=11";
	
	private static final String VAR_P2PID            = "uid";
	private static final String VAR_MYRSSI           = "wrssi[0]";
	private static final String VAR_FRATE1           = "fps_1";
	private static final String VAR_RTSPPORT         = "rtspPort";
	
	
	private static final String CGI_SET_OK           = "set ok";//[Succeed]set ok.
	/** 0:success -1:format err -2:usr|pwd err -3:access deny */
	private static final String CGIRES_SUC           = "cgiResult";
	

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
		try {
			return NetWorkUtil.sendGetRequest(urlStr, pMap,context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	

	@Override
	public String getWirelessattr(String urlStr, Map<String, String> map,
			Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWirelessName(String urlStr, Map<String, String> map,
			Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String searchWireless(String urlStr, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWirelessRssi(String urlStr, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri getSnapshotByCgi(String ip, File cache, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gotoPreSet(String url, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean presetPTZ(String ip, String action, int status, int index,
			Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getP2p(String urlStr, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getP2pId(String urlStr, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setP2p(String urlStr, Map<String, String> map,
			Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setP2pId(String urlStr, String id, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeOSD(String url, String name, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ptzCtrl(String url, String CMD, int speed, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setWifi(String url, CameraWifi wifi, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkWifi(String url, CameraWifi wifi, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CameraWifi> scanWifi(String url, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean openWifi(String url, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openDHCP(String url, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CameraNetInfo getNetworkInfo(String url, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rebootCam(String ip, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOSD(String url, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean loginCam(String ip, Context context) {
		Log.i(TAG, "loginCamera,choose the cgi version:" + ip);
		boolean status = false;
		String urlStr = ip + CGI_USER;
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(KEY_CMD, CMD_LOGIN);
		String res = "";
		try {
			res = callCgiByGet(urlStr, pMap,context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ObjectCheck.validString(res)){
			status = true;
		}else{
			
		}
		return status;
	}
}
