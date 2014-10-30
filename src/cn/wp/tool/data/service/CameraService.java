package cn.wp.tool.data.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import cn.wp.device.camera.CameraManager;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.OnvifCamera;
import cn.wp.tool.provider.CameraListProvider;


public class CameraService {
	private final String TAG = CameraService.class.getSimpleName();
	private CameraManager cameraManager = null;
	private List<IPCamera> onlineCameras = null;
	private Context myContext = null;
	private Context serviceContext = null;
	private HandlerThread mWorkHandlerThread = null;
	private Handler mWorkHandler = null;
	private boolean getSwitch = true;
	private boolean openSwitch = true;
	private boolean scanSwitch = true;
	private boolean totalDhcpSwitch = false;
	public static final int WORK_HANDLER_EVENT_GET_NET_INFO = 1;
	public static final int WORK_HANDLER_EVENT_OPENDHCP_WIFI = 2;
	public static final int WORK_HANDLER_EVENT_SCAN_CAMERA = 3;
	private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {
		
		//@Override
		public boolean handleMessage(Message msg) {
			Log.i(TAG, "handleMessage what:" + msg.what); 
			switch (msg.what) {
			case WORK_HANDLER_EVENT_GET_NET_INFO:
				if(scanSwitch){
					scanSwitch = false;
					sendBroadcastToView(Defination.Cmd_Type.SCAN_WIFI_FINISHED);
				}
				IPCamera camera = msg.getData().getParcelable("camera");
				IPCamera error = cameraManager.getNetInfo(camera, myContext,mWorkHandler);
				if(getSwitch){
					getSwitch = false;
					sendBroadcastToView(Defination.Cmd_Type.GET_NET_INFO_FINISHED);
				}
				if(!ObjectCheck.validObject(error))
					sendBroadcastToView(Defination.Cmd_Type.SCAN_WIFI_ERROR);
				break;
			case WORK_HANDLER_EVENT_OPENDHCP_WIFI:{
				if(totalDhcpSwitch){
					IPCamera gotNetInfo = msg.getData().getParcelable("camera");
					IPCamera errorOpen  = cameraManager.openCamerasDHCPAndWifi(gotNetInfo, myContext);
					if(!ObjectCheck.validObject(errorOpen)){
						sendBroadcastToView(Defination.Cmd_Type.SCAN_WIFI_ERROR);
					}else{
						if(openSwitch){
							openSwitch = false;
							sendBroadcastToView(Defination.Cmd_Type.OPEN_DHCP_FINISHED);
						}
					}
				}else{
					sendBroadcastToView(Defination.Cmd_Type.OPEN_DHCP_FINISHED);
				}
				break;
			}
			case WORK_HANDLER_EVENT_SCAN_CAMERA:{
				
				break;
			}
			default:
				break;
			}
			return false;
		}
	};
	
	
	public void initCameraManager(Context context){
		serviceContext= context;
		mWorkHandlerThread = new HandlerThread(CameraManager.class.getName());
		mWorkHandlerThread.start();
		mWorkHandler = new Handler(mWorkHandlerThread.getLooper(), mWorkHandlerCallback);
		if(cameraManager == null)
		{
			cameraManager = new CameraManager();
	        cameraManager.init(context);
	        cameraManager.config();
		}
		if(onlineCameras == null){
			onlineCameras = new ArrayList<IPCamera>();
		}
	}
	
	private void sendBroadcastToView(int cmd){
		Intent intent=new Intent();
		intent.putExtra(Defination.Cmd_Type.CMD, cmd);
	    intent.setAction("cn.wp.tool.status");
	    serviceContext.sendBroadcast(intent);
	}
	public List<IPCamera> scanOnLineCamera(Context context,boolean openDhcp){
		totalDhcpSwitch = openDhcp;
		myContext = context;
		onlineCameras = cameraManager.scanOnlineCamerasInner(context,mWorkHandler);
		return onlineCameras;		
	}
	public IPCamera getCameraNetInfo(IPCamera camera,Context context){
		IPCamera cameraNetInfo = null;
		cameraNetInfo = cameraManager.getNetInfo(camera, context,mWorkHandler);
		return cameraNetInfo;
	}
	public IPCamera openDhcpAndWifi(IPCamera camera,Context context){
		IPCamera cameraDhcpWifi = null;
		cameraDhcpWifi = cameraManager.openCamerasDHCPAndWifi(camera, context);
		return cameraDhcpWifi;
	}
	public IPCamera scanWifi(IPCamera camera,Context context){
		IPCamera cameraWifiList = null;
		cameraWifiList = cameraManager.scanWifi(camera, context);
		return cameraWifiList;
	}
	public IPCamera setWifi(IPCamera camera,Context context){
		IPCamera cameraSetWifi = null;
		cameraSetWifi = cameraManager.setWifi(camera, context);
		return cameraSetWifi;
	}
	
	public boolean  ptzCtrl(IPCamera camera,Context context,String CMD,int speed){
		return cameraManager.ptzCtrl(camera, context, CMD, speed);
	}
	
	public boolean presetPtz(IPCamera camera,Context context,String action,int status,int num){
		return cameraManager.presetPtz(camera, context, action, status, num);
	}
	public boolean rebootCamera(String url,Context context){
		return cameraManager.rebootCamera(url, context);
	}
	public boolean gotoPreSet(String url,Context context){
		return cameraManager.gotoPreSet(url,context);
	}
	public boolean changeOSD(String url,String name,Context context){
		return cameraManager.changeOSD(url,name, context);
	}
}
