/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.wp.device.camera.onvif.OnvifRequest;
import cn.wp.device.camera.onvif.OnvifUtil;
import cn.wp.device.camera.onvif.probe.ProbeMatch;
import cn.wp.device.camera.onvif.profile.Profile;
import cn.wp.device.camera.onvif.profile.Profiles;
import cn.wp.device.camera.onvif.response.GetVideoEncoderConfigurationOptions;
import cn.wp.device.camera.onvif.response.ProbeMatches;
import cn.wp.device.camera.onvif.veco.ResolutionsAvailable;
import cn.wp.device.camera.utils.CameraUtil;
import cn.wp.device.camera.utils.CgiUtilFactory;
import cn.wp.device.camera.utils.PaneseCgiUtil;
import cn.wp.device.camera.utils.GUID;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.data.config.CameraManagerConfig;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.config.Defination.CamId_Gene_Mode;
import cn.wp.tool.data.config.Defination.Stream_Index;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.OnvifCamera;
import cn.wp.tool.data.model.OnvifCameraInfo;
import cn.wp.tool.data.service.CameraService;
import cn.wp.tool.provider.CameraListProvider;
import cn.wp.tool.ui.devicelist.DeviceListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 * @Title OnvifCameraScanner.java 
 * @Package cn.ws.device.camera.impl 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��3��31�� ����11:13:09 
 * @version V1.0   
 */
public class OnvifCameraScanner extends IPCameraScaner {
	private static final String TAG = "OnvifCameraScanner";
	
	private static OnvifConfig mOnvifConfig;
	private static CameraManagerConfig cmConfig;
	private Context myContext = null;
	private HandlerThread mWorkHandlerThread = null;
	private Handler mWorkHandler = null;
	private Handler cameraServiceHandler = null;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private List<OnvifCameraInfo> cInfoList = new ArrayList<OnvifCameraInfo>(); 
	private static final int WORK_HANDLER_EVENT_GET_BASIC_INFO = 1;
	
	private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {
		
		//@Override
		public boolean handleMessage(Message msg) {
			Log.i(TAG, "handleMessage what:" + msg.what); 
			switch (msg.what) {
			case WORK_HANDLER_EVENT_GET_BASIC_INFO:
				String xAddress =  msg.getData().getString("xAddress");
				executor.execute(new Target(xAddress));
				break;
			default:
				break;
			}
			return false;
		}
	};
	/* (non-Javadoc)
	 * @see cn.ws.device.camera.impl.IPCameraScaner#scanOnlineCameraList()
	 */
	@Override
	public List<IPCamera> scanOnlineCameraList(Context context,Handler cameraHandler) {
		Log.i(TAG, "Start to Scan Online Camera.");
		cameraServiceHandler = cameraHandler;
		myContext = context;
		mWorkHandlerThread = new HandlerThread(CameraManager.class.getName());
		mWorkHandlerThread.start();
		mWorkHandler = new Handler(mWorkHandlerThread.getLooper(), mWorkHandlerCallback);
		mIpCameras.clear();
		cInfoList.clear();
		//probe devices
		OnvifUtil.getOnvifDevices(OnvifRequest.generateDeviceProbe(GUID.getNewGUID()),mWorkHandler);
		return mIpCameras;
	}
	
	private void getBasicInfo(String accesssURL){
		Log.i(TAG, "scanOnlineCameraList, for pmList pm.access:" + accesssURL);
		if (ObjectCheck.validString(accesssURL)) {
			
			if(!ipMatcher(accesssURL)){   //match the ip address
				Log.i(TAG, "scanOnlineCameraList, for pmList pm.access:lose one camera ip:" + accesssURL);
				return;
			}
			// record the cam cgi depending on different version
			int cgiVersion = this.getCgiVersion(accesssURL);
			CgiUtilFactory.add(accesssURL, cgiVersion);
			// record the cam cgi depending on different version
			OnvifCamera oCamera = new OnvifCamera();
			//set the base URI to access camera
			oCamera.setmBaseAccesssURL(accesssURL);
			//get device's name
			if (CamId_Gene_Mode.UUID.equals(cmConfig.getCamIdMode())) {
				Log.i(TAG, "scanOnlineCameraList, UUID mode");
				String ipUrl = CameraUtil.getIpByBaseUrl(accesssURL);
				String camId = CgiUtilFactory.getCgiUtil(accesssURL).getP2pId(ipUrl,myContext);
				Log.i(TAG, "scanOnlineCameraList, UUID name:" + camId);
				if (ObjectCheck.validString(camId)) {
					oCamera.setName(camId);
				}
			} else if (CamId_Gene_Mode.NameID.equals(cmConfig.getCamIdMode())) {
				Log.i(TAG, "scanOnlineCameraList, NameID mode");
				String name = OnvifUtil.getDeviceName(accesssURL, OnvifRequest.generateGetScopes());
				Log.i(TAG, "scanOnlineCameraList, NameID name:" + name);
				String id = OnvifUtil.getDeviceId(accesssURL, OnvifRequest.generateGetDeviceInformation());
				Log.i(TAG, "scanOnlineCameraList, id:" + id);
				if (ObjectCheck.validString(name) && ObjectCheck.validString(id)) {
					oCamera.setName(name + id);	
				} else {
					Log.i(TAG, "scanOnlineCameraList, camera's name is invalid!");
					return;
				}
			}
			
			String mac = OnvifUtil.getDeviceId(accesssURL, OnvifRequest.generateGetDeviceInformation());
			Log.i(TAG, "scanOnlineCameraList, mac:" + mac);
			String port = OnvifUtil.getPort(accesssURL, OnvifRequest.generateGetDevicePort());
			Log.i(TAG, "scanOnlineCameraList, port:" + port);
			if(ObjectCheck.validString(port)){
				String url = oCamera.getmBaseAccesssURL();
				int temp = url.lastIndexOf(":");
				if(temp != -1){
					String ip = url.substring(0, temp);
					String details = url.substring(temp);
					details = details.substring(details.indexOf("/"));
					String newUrl = ip + ":" + port + details;
//					oCamera.setmBaseAccesssURL(newUrl);
					Log.i(TAG, "new access url" + newUrl);
				}
			}
			if (ObjectCheck.validString(mac))
				oCamera.setMac(mac);	
				
			String rtspAddr = "";
			String snapshotUri = "";
			
			//get profiles
			Profiles ps = OnvifUtil.getProfiles(accesssURL, OnvifRequest.generateGetProfiles());
			OnvifCameraInfo cInfo = new OnvifCameraInfo(oCamera, ps);
			//Log.i(TAG, "scanOnlineCameraList, profiles:" + ps);
			if (ps != null) {
				List<Profile> profileList = ps.getProfilesList();
				if (profileList != null && !profileList.isEmpty()) {
					int profileCnt = profileList.size();//to get the last one's stream address
					String sIndex = mOnvifConfig.getStreamIndex();
					int profileIndex = 0;
					for (int i=0;i<profileCnt;i++) {
						if (profileList.get(i) != null && sIndex.equals(profileList.get(i).getName())) {
							profileIndex = i;
							break;
						}
					}
					Log.i(TAG, "scanOnlineCameraList, profileIndex:" + profileIndex);
					//get a video source address
					rtspAddr = OnvifUtil.getRtspAddr(accesssURL, OnvifRequest.generateStreamUri(profileList.get(profileIndex).getToken()));
					Log.i(TAG, "scanOnlineCameraList, rtsp:" + rtspAddr);
					if (rtspAddr == null) {
						rtspAddr = "";
					}
					snapshotUri = OnvifUtil.getSnapshotUri(accesssURL, OnvifRequest.generateGetSnapshotUri(profileList.get(profileIndex).getToken()));
					Log.i(TAG, "scanOnlineCameraList, snapshotUri:" + snapshotUri);
					if (mOnvifConfig != null && mOnvifConfig.getSupportedWidth() > 0 && mOnvifConfig.getSupportedHeight() > 0) {
						Log.i(TAG, "scanOnlineCameraList, need to find best resolution");
						int supportW = mOnvifConfig.getSupportedWidth();
						int supportH = mOnvifConfig.getSupportedHeight();
						Profile resoProfile = null;//find the profile with the best resolution video. depends on resolution we supported.
						
						for (Profile profile:profileList) {//iterate profileList for getVideoOptions in every profile 
							//Log.i(TAG, "scanOnlineCameraList, for ProfileList p:" + profile);
							
							GetVideoEncoderConfigurationOptions veco = OnvifUtil.getVideoEncoderConfigurationOptions(accesssURL, 
									OnvifRequest.generateGetVideoEncoderConfig(profile, profile.getVideoEncoderConfiguration()));
							//Log.i(TAG, "scanOnlineCameraList, GetVideoEncoderConfigurationOptions:" + veco);
							if (veco == null || veco.getOptions() == null || veco.getOptions().getH264() == null) {
								Log.i(TAG, "scanOnlineCameraList, invalid veco");
							} else {
								List<ResolutionsAvailable> rList = veco.getOptions().getH264().getResolutionList();
								cInfo.getProfile2ResoList().put(profile, rList);
								for (ResolutionsAvailable r:rList) {//iterate supported resolutions List for find the best one 
									Log.i(TAG, "scanOnlineCameraList, for rList r:" + r);
									int h = r.getHeight();
									int w = r.getWidth();
									Log.i(TAG, "scanOnlineCameraList, ResolutionsAvailable h:" + h + ", w:" + w);
									if (h <= supportH && w <= supportW) {//in the supported range
										boolean finded = false;
										if (resoProfile == null) {
											Log.i(TAG, "scanOnlineCameraList, find first resolution can be supported");
											finded = true;
										} else {
											int oH = resoProfile.getVideoEncoderConfiguration().getResolution().getHeight();
											int oW = resoProfile.getVideoEncoderConfiguration().getResolution().getWidth();
											if (h > oH || w > oW) {
												Log.i(TAG, "scanOnlineCameraList, find better resolution can be supported");
												finded = true;
											}
										}
										if (finded) {
											resoProfile = profile;
											resoProfile.getVideoEncoderConfiguration().getResolution().setHeight(h);
											resoProfile.getVideoEncoderConfiguration().getResolution().setWidth(w);
										}
									}
								}
							}
						}
						//set the profile with the best resolution
						if (resoProfile != null && OnvifUtil.setResolution(accesssURL, resoProfile)) {
							//get video source address with the best resolution
							String fixRtspAddr = OnvifUtil.getRtspAddr(accesssURL, OnvifRequest.generateStreamUri(resoProfile.getToken()));
							if (ObjectCheck.validString(fixRtspAddr) &&
									ObjectCheck.validString(oCamera.getName()) && !(oCamera.getName().startsWith("IPCAM")))
								rtspAddr = fixRtspAddr;
							Log.i(TAG, "scanOnlineCameraList, after fix rtsp:" + rtspAddr);
						}
					}
				}
			}
			/*if (oCamera.getName().startsWith("ONVIF_IPNC")) {
				rtspAddr = rtspAddr.replace("//", "//admin:123456@");
			} else if (oCamera.getName().startsWith("IPCAM")) {
				rtspAddr = rtspAddr.replace("//", "//admin:ws20130620@");
			}*/
			rtspAddr = rtspAddr.replace("//", "//" + cmConfig.getCameraUsr() + ":" + cmConfig.getCameraPwd() + "@");
			Log.i(TAG, "scanOnlineCameraList, after add user's name and pwd:" + rtspAddr);
			oCamera.setRtspLiveStringURL(rtspAddr);
			oCamera.setSnapshotUri(snapshotUri);
			if (ObjectCheck.validString(oCamera.getName())
					&& !mIpCameras.contains(oCamera)) {//TODO need to check
				mIpCameras.add(oCamera);
				cInfoList.add(cInfo);
				int end = accesssURL.lastIndexOf(":");
				String ip = accesssURL.substring(7, end);
				ContentValues values = new ContentValues(); 
				values.put(Defination.CameraTableColum.SN.getName(), oCamera.getName());
				values.put(Defination.CameraTableColum.MAC.getName(), oCamera.getMac());
				values.put(Defination.CameraTableColum.RTSP.getName(), oCamera.getRtspLiveStringURL(0));
				values.put(Defination.CameraTableColum.BASEURL.getName(), oCamera.getmBaseAccesssURL());
				myContext.getContentResolver().update(CameraListProvider.CONTENT_URI, 
											values, Defination.CameraTableColum.IP.getName()+"=?", new String[]{ip});
				
        		Message msg = new Message();
        		msg.what = CameraService.WORK_HANDLER_EVENT_GET_NET_INFO;
        		Bundle bundle = new Bundle();
        		bundle.putParcelable("camera", oCamera);
        		msg.setData(bundle);
        		cameraServiceHandler.sendMessage(msg);
			}

			Log.i(TAG, "scanOnlineCameraList, cInfoList.size:" +  cInfoList.size());
		}
	}

	
	public void setOnvifConfig(OnvifConfig oc) {
		mOnvifConfig = oc;
	}
	
	public void setCameraConfig(CameraManagerConfig cm) {
		cmConfig = cm;
	}
	
	public static class OnvifConfig {
		int supportedWidth;
		int supportedHeight;
		String streamIndex = Stream_Index.Main;
		
		/**
		 * @return the streamIndex
		 */
		public String getStreamIndex() {
			return streamIndex;
		}
		/**
		 * @param streamIndex the streamIndex to set
		 */
		public void setStreamIndex(String streamIndex) {
			this.streamIndex = streamIndex;
		}
		/**
		 * @return the supportedWidth
		 */
		public int getSupportedWidth() {
			return supportedWidth;
		}
		/**
		 * @param supportedWidth the supportedWidth to set
		 */
		public void setSupportedWidth(int supportedWidth) {
			this.supportedWidth = supportedWidth;
		}
		/**
		 * @return the supportedHeight
		 */
		public int getSupportedHeight() {
			return supportedHeight;
		}
		/**
		 * @param supportedHeight the supportedHeight to set
		 */
		public void setSupportedHeight(int supportedHeight) {
			this.supportedHeight = supportedHeight;
		}

	}
	
	/**
	 * @return the cInfoList
	 */
	public List<OnvifCameraInfo> getcInfoList() {
		return cInfoList;
	}

	/**
	 * @param cInfoList the cInfoList to set
	 */
	public void setcInfoList(List<OnvifCameraInfo> cInfoList) {
		this.cInfoList = cInfoList;
	}
	@Override
	public int getCgiVersion(String url) {
		Log.i(TAG, "getCgiVersion");
		boolean status = false;
		String ipUrl = CameraUtil.getIpByBaseUrl(url);
		status = CgiUtilFactory.getDefaultCgiUtil().loginCam(ipUrl,myContext);
		if(status){
			Log.i(TAG, "the cgi version is newest");
			return 2;
		}else{
			Log.i(TAG, "the cgi version is oldest");
			return 1;
		}
	}
	
	private class Target extends Thread {
		private String xAddress = "";
		public Target(String ip){
			xAddress = ip;
		}
        @Override
        public void run() {
        	 synchronized (this) {
     			if(ObjectCheck.validString(xAddress)){
    				//http://192.169.1.117:8080/onvif/device_service\
    				int end  = xAddress.lastIndexOf(":");
    				String ip = xAddress.substring(7, end);
    				if(ObjectCheck.validObject(myContext)){
    					ContentValues values = new ContentValues(); 
    					values.put(Defination.CameraTableColum.IP.getName(), ip);
    					values.put(Defination.CameraTableColum.ONLINE.getName(), true);
    					myContext.getContentResolver().insert(CameraListProvider.CONTENT_URI, values);
    					Intent intent=new Intent();
    					intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.UPDATE_ONLINE_CAMERA_NUM);
    				    intent.setAction("cn.wp.tool.status");
    				    myContext.sendBroadcast(intent);
    					getBasicInfo(xAddress);
    				}
    			}
        	 }
        }
	}
}
