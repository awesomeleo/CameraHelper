package cn.wp.device.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Handler;
import cn.wp.tool.data.model.IPCamera;



public abstract class IPCameraScaner {
	
	protected List<IPCamera> mIpCameras = new ArrayList<IPCamera>();
	private int maxCameraCnt;

	/**
	 * @return the maxCameraCnt
	 */
	public int getMaxCameraCnt() {
		return maxCameraCnt;
	}


	/**
	 * @param maxCameraCnt the maxCameraCnt to set
	 */
	public void setMaxCameraCnt(int maxCameraCnt) {
		this.maxCameraCnt = maxCameraCnt;
	}


	public abstract List<IPCamera> scanOnlineCameraList(Context context,Handler mWorkHandler);
	public abstract int getCgiVersion(String url); 
	protected boolean ipMatcher(String url){
		String ipMatcher = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}"; 
		Pattern pattern = Pattern.compile(ipMatcher); 
		String ip = url.substring(url.indexOf("://") + 3, url.lastIndexOf(":"));
		Matcher matcher = pattern.matcher(ip); 
		return matcher.matches(); 
		}
}