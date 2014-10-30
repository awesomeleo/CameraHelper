package cn.wp.device.camera.utils;

import java.util.ArrayList;
import java.util.List;
import cn.wp.tool.data.config.CgiVersion;
import io.vov.vitamio.utils.Log;


public class CgiUtilFactory {
	private static final String TAG = CgiUtilFactory.class.getSimpleName();
	private static PaneseBlCgiUtil pbCU = new PaneseBlCgiUtil();
	private static PaneseCgiUtil pCU = new PaneseCgiUtil();
	private static List<CgiVersion> camCgiVersion = new ArrayList<CgiVersion>();
	public static class Cgi_Type {
		public static final int Panese      = 1;
		public static final int Panese_2    = 2;
	}
	public static CgiUtil getDefaultCgiUtil(){
		return pbCU;
	}
	public static CgiUtil getCgiUtil(String ip){
		Log.i(TAG, "getCgiUtil" + ip);
		if(ObjectCheck.validString(ip)){
			for(CgiVersion temp:camCgiVersion){
				if(temp.getCamIp().contains(ip)){
					int version = 1;
					version = temp.getCgiVersion();
					switch(version){
					case Cgi_Type.Panese:{
						return pCU;
					}
					case Cgi_Type.Panese_2:{
						return pbCU;
					}
					}
				}
			}
		}
		return pCU;
	}
	
	public static void add(String ip,int version){
		Log.i(TAG, "put into version list:" + ip + " version:" + version);
		if(ObjectCheck.validString(ip)){
			for(CgiVersion temp:camCgiVersion){
				if(temp.getCamIp().equals(ip)){
					return;
				}
			}
			CgiVersion versionCode = new CgiVersion();
			versionCode.setCamIp(ip);
			versionCode.setCgiVersion(version);
			camCgiVersion.add(versionCode);
		}
	}
}
