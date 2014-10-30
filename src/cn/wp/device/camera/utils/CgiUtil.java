package cn.wp.device.camera.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.wp.tool.data.model.CameraNetInfo;
import cn.wp.tool.data.model.CameraWifi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public abstract class CgiUtil {
	private static final String TAG = CgiUtil.class.getSimpleName();
	protected static String user                       = "admin";
	protected static String password                   = "ws20130620";
	
	public static void setUsrAndPwd(String usr, String pwd) {
		Log.i(TAG, "setUsrAndPwd, params usr:" + usr + ", pwd:" + pwd);
		if (!ObjectCheck.validString(usr)) {
			return;
		}
		user = usr;
		password = pwd;
	}
	
	public abstract String getWirelessattr(String urlStr, Map<String, String> map,Context context);
	public abstract String getWirelessName(String urlStr, Map<String, String> map,Context context);
	public abstract String searchWireless(String urlStr,Context context);
	public abstract String getWirelessRssi(String urlStr,Context context);
	public abstract Uri getSnapshotByCgi(String ip, File cache, String filename);
	public abstract boolean gotoPreSet(String url,Context context);
	public abstract boolean presetPTZ(String ip, String action,int status, int index,Context context);
	public abstract String getP2p(String urlStr,Context context);
	public abstract String getP2pId(String urlStr,Context context);
	public abstract boolean setP2p(String urlStr, Map<String, String> map,Context context);
	public abstract boolean setP2pId(String urlStr, String id,Context context);
	public abstract boolean changeOSD(String url,String name,Context context);
	public abstract boolean ptzCtrl(String url,String CMD,int speed,Context context);
	public abstract boolean setWifi(String url,CameraWifi wifi,Context context);
	public abstract boolean checkWifi(String url,CameraWifi wifi,Context context);
	public abstract List<CameraWifi> scanWifi(String url,Context context);
	public abstract boolean openWifi(String url,Context context);
	public abstract boolean openDHCP(String url,Context context);
	public abstract CameraNetInfo getNetworkInfo(String url,Context context);
	public abstract boolean rebootCam(String ip,Context context);
	public abstract String getOSD(String url,Context context);
	public abstract boolean loginCam(String ip,Context context);
}
