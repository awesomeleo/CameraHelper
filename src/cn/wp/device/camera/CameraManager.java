package cn.wp.device.camera;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.wp.device.camera.OnvifCameraScanner.OnvifConfig;
import cn.wp.device.camera.utils.CameraUtil;
import cn.wp.device.camera.utils.CgiUtilFactory;
import cn.wp.device.camera.utils.PaneseCgiUtil;
import cn.wp.device.camera.utils.NetWorkUtil;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.device.camera.utils.UserData;
import cn.wp.tool.data.config.CameraManagerConfig;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.config.Defination.Scan_Camera_Type;
import cn.wp.tool.data.model.AppInfo;
import cn.wp.tool.data.model.CameraNetInfo;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.RemoteData;
import cn.wp.tool.data.service.CameraService;
import cn.wp.tool.provider.CameraListProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class CameraManager {
	
	private static final String TAG = "CameraManager";
	private int scanCameraType = Scan_Camera_Type.ZAG;
	private CameraManagerConfig cm = new CameraManagerConfig();
    private ContentValues allUserDataValues = new ContentValues();
    private static Context mContext;
	private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	private IPCameraScaner ipCameraScanner;
	private IPCameraScaner stubCameraScanner = new StubCameraScaner();
	private IPCameraScaner zagCameraScanner = new ZAGCameraScaner();
	private OnvifCameraScanner onvifCameraScanner = new OnvifCameraScanner();
	private List<IPCamera> mIpCameras = new ArrayList<IPCamera>();
	//private List<IPCamera> mOnvifIpCameras = new ArrayList<IPCamera>();

	
	private static final String URL_CAMERA_GET_SECOND_NAME = "http://203.195.134.144/device/box/getCameraName.action?";
	private static final String CMD_CAMERA_SN = "cameraSn";
	
	/**
	 * configure camera manager
	 * @param cv all values can be configured
	 * */
	public void config() {
		readDefaultData();
		OnvifConfig oc = new OnvifConfig();
		oc.setSupportedWidth(Integer.valueOf((String)allUserDataValues.get(UserData.TableUsersDataColumns.SUPPORTED_WIDTH)));
		oc.setSupportedHeight(Integer.valueOf((String)allUserDataValues.get(UserData.TableUsersDataColumns.SUPPORTED_HEIGHT)));
		oc.setStreamIndex(String.valueOf(allUserDataValues.get(UserData.TableUsersDataColumns.STREAM_INDEX)));
		
		cm.setMaxCameraCount(Integer.valueOf((String)allUserDataValues.get(UserData.TableUsersDataColumns.MAX_CAMERA_COUNT)));
		cm.setCameraUsr((String)allUserDataValues.get(UserData.TableUsersDataColumns.CAMERA_USER_NAME));
		cm.setCameraPwd((String)allUserDataValues.get(UserData.TableUsersDataColumns.CAMERA_PASSWORD));
		cm.setCamIdMode((String)allUserDataValues.get(UserData.TableUsersDataColumns.CAMID_GENE_MODE));
		
		zagCameraScanner.setMaxCameraCnt(cm.getMaxCameraCount());
		onvifCameraScanner.setMaxCameraCnt(cm.getMaxCameraCount());
		onvifCameraScanner.setCameraConfig(cm);
		
		setScanCameraType(Scan_Camera_Type.ALL);
		String cameraUserName = (String) allUserDataValues.get(UserData.TableUsersDataColumns.CAMERA_USER_NAME);
		String cameraPassW = (String) allUserDataValues.get(UserData.TableUsersDataColumns.CAMERA_PASSWORD);
		PaneseCgiUtil.setUsrAndPwd(cameraUserName,cameraPassW);
		onvifCameraScanner.setOnvifConfig(oc);
		Log.i(TAG, "init the camera username and password:" + cameraUserName+ "+" + cameraPassW); 
	}
	
	/**
	 * clear the mIpCameras and rescan the camera list
	 * 
	 * @param type 0:ZAG&ONVIF, 1:Stub, 2:ZAG, 3:ONVIF
	 * */
	public void setScanCameraType(int type) {
		scanCameraType = type;
	}

	//only ZagIpCamera supported 
	public List<IPCamera> scanOnlineCamerasInner(Context context,Handler mWorkHandler){
		Log.i(TAG, "scanCameraType:" + scanCameraType); 
		List<IPCamera> ipCameras;
		if (scanCameraType == Scan_Camera_Type.ALL) {
			//ipCameraScanner = stubCameraScanner;
			//ipCameras = ipCameraScanner.scanOnlineCameraList();
			ipCameraScanner = zagCameraScanner;
			ipCameras = ipCameraScanner.scanOnlineCameraList(context,mWorkHandler);
			onvifCameraScanner.setMaxCameraCnt(cm.getMaxCameraCount() - ipCameras.size());
			onvifCameraScanner.setCameraConfig(cm);
			ipCameraScanner = onvifCameraScanner;
			ipCameras.addAll(ipCameraScanner.scanOnlineCameraList(context,mWorkHandler));
			addCamerasToManager(ipCameras);
			return this.mIpCameras;
		}
		switch(scanCameraType) {
		case Scan_Camera_Type.Stub:
			ipCameraScanner = stubCameraScanner;
			break;
		case Scan_Camera_Type.ZAG:
			ipCameraScanner = zagCameraScanner;
			break;
		case Scan_Camera_Type.Onvif:
			ipCameraScanner = onvifCameraScanner;
			break;
		default:
			ipCameraScanner = zagCameraScanner;
			break;
		}
		ipCameras = ipCameraScanner.scanOnlineCameraList(context,mWorkHandler);
		addCamerasToManager(ipCameras);
		return this.mIpCameras;
	}
	private void addCamerasToManager(List<IPCamera> ipCameras){
		boolean status = false;
		if (!ObjectCheck.validObject(ipCameras)) {
			Log.e(TAG, "addCamerasToManager,Oncline Camera is empty!");
			return;
		}
		Log.i(TAG, "addCamerasToManager,Oncline Camera info:" + ipCameras.size());
		if(ipCameras.size() > 0){
			for(IPCamera temp:ipCameras)
				Log.i(TAG, "addCamerasToManager,Oncline Camera info:" + temp.toString());
			status = true;
		}
		mIpCameras = ipCameras;
	}
	public void init(Context context) {
		mContext = context;
	}

	public List<IPCamera> getmIpCameras() {
		return mIpCameras;
	}
	public void setmIpCameras(List<IPCamera> mIpCameras) {
		this.mIpCameras = mIpCameras;
	}
    private void readDefaultData() {
    	if (mContext == null) {
        	Log.i(TAG, "readDefaultData, param is null!");
    		return;
    	}
        allUserDataValues.putNull(UserData.TableUsersDataColumns.PROJECT_NAME);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.CAMERA_TYPE);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.STREAM_INDEX);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.CAMERA_USER_NAME);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.CAMERA_PASSWORD);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.CAMID_GENE_MODE);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.GET_VIDEO_XML);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.SET_VIDEO_XML);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.SUPPORTED_WIDTH);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.SUPPORTED_HEIGHT);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.LANGUAGE_CODE);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.MAX_CAMERA_COUNT);
        allUserDataValues.putNull(UserData.TableUsersDataColumns.LIB_PROCESS_STREAM);
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            InputStream is = mContext.getAssets().open("userdata.xml");
            javax.xml.parsers.SAXParser sp = spf.newSAXParser();
            sp.parse(is, new DefaultHandler() {
                private String elementName;
                @Override
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    String value = new String(ch, start, length);
                    if (allUserDataValues.containsKey(elementName)) {
                    	Log.i(TAG, "readDefaultData, element:" + elementName + ", value:" + value);
                        allUserDataValues.put(elementName, value);
                    }
                }

                @Override
                public void startElement(String uri, String localName,
                        String qName, Attributes attributes)
                        throws SAXException {
                    elementName = localName;
                }

                @Override
                public void endElement(String arg0, String arg1, String arg2)
                        throws SAXException {
                    elementName = "";
                }
            });
        } catch (Throwable e) {
        	Log.e(TAG, e.toString());
        }
    }

    public void sleepSeconds(int sleep){
    	try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public IPCamera openCamerasDHCPAndWifi(IPCamera camera,Context context){
		Log.i(TAG, "openCamerasDHCP,  start");
		boolean status = false;
		boolean commiStatus = false;
		ContentValues values = new ContentValues(); 

		if(!camera.getNetInfo().isDhcpOn()){
			Log.i(TAG, "openCamerasDHCP,  one camera is going to open dhcp");
			String baseURL = camera.getmBaseAccesssURL();
			String ip = CameraUtil.getIpByBaseUrl(baseURL);
			status = CgiUtilFactory.getCgiUtil(ip).openDHCP(ip,context);
			if(!status){
				return null;
			}
			camera.getNetInfo().setDhcpOn(status);
			values.put(Defination.CameraTableColum.DHCP.getName(), status);
			commiStatus = true;
		}
		if(!ObjectCheck.validObject(camera.getNetInfo().getMyWifi())){
	   		CameraWifi myWifi = new CameraWifi();
    		myWifi.setWifiOn(false);
 //   		openWifi(camera.getName(),context);
    		camera.getNetInfo().setMyWifi(myWifi);		
		}
		if(commiStatus)
			context.getContentResolver().update(CameraListProvider.CONTENT_URI, 
									values, Defination.CameraTableColum.SN.getName()+"=?", new String[]{camera.getName()});
		return camera;
	}
	
	public IPCamera getNetInfo(IPCamera camera,Context context,Handler cameraHandler){
		Log.i(TAG, "getNetInfo,  start");
		if (!ObjectCheck.validObject(camera))
			return null;
		String secondName = null;
		CameraNetInfo cameraNetInfo = null;
		String baseURL = camera.getmBaseAccesssURL();
		String ip = CameraUtil.getIpByBaseUrl(baseURL);
		cameraNetInfo = CgiUtilFactory.getCgiUtil(ip).getNetworkInfo(ip,context);
		secondName = CgiUtilFactory.getCgiUtil(ip).getOSD(ip, context);
//		secondName = this.getCameraSecondName(camera.getName(), context);
		if(ObjectCheck.validObject(cameraNetInfo)){
			if(ObjectCheck.validString(secondName))
				cameraNetInfo.setCameraNetName(secondName);
		}
		else
			return null;
		camera.setNetInfo(cameraNetInfo);
		if(ObjectCheck.validObject(camera))
			if(ObjectCheck.validObject(camera.getNetInfo())){
				
				ContentValues values = new ContentValues(); 
				values.put(Defination.CameraTableColum.DHCP.getName(), camera.getNetInfo().isDhcpOn());
				values.put(Defination.CameraTableColum.NETTYPE.getName(), camera.getNetInfo().getNetType());
				values.put(Defination.CameraTableColum.NAME.getName(), camera.getNetInfo().getCameraNetName());
				CameraWifi myWifi = camera.getNetInfo().getMyWifi();
				if(ObjectCheck.validObject(myWifi)){
					values.put(Defination.CameraTableColum.WIFI.getName(),myWifi.isWifiOn());
					values.put(Defination.CameraTableColum.SSID.getName(),myWifi.getSsId());
					values.put(Defination.CameraTableColum.RSSI.getName(),myWifi.getRssi());
				}
				context.getContentResolver().update(CameraListProvider.CONTENT_URI, 
											values, Defination.CameraTableColum.SN.getName()+"=?", new String[]{camera.getName()});
        		Message msg = new Message();
        		msg.what = CameraService.WORK_HANDLER_EVENT_OPENDHCP_WIFI;
        		Bundle bundle = new Bundle();
        		bundle.putParcelable("camera", camera);
        		msg.setData(bundle);
        		cameraHandler.sendMessage(msg);
				return camera;
			}
		return null;
	}
	
	private String getCameraSecondName(String sn,Context context){
		Log.i(TAG, "getCameraSecondName,  start");
		String secondName = "";
		String res = "";
		Map<String, String> pMap = new LinkedHashMap<String, String>();
		pMap.put(CMD_CAMERA_SN, sn);
		try {
			res =  NetWorkUtil.sendGetRequest(URL_CAMERA_GET_SECOND_NAME, pMap, context);
		} catch (Exception e) {
			Log.i(TAG, "getCameraSecondName," + e);
			return null;
		}
		RemoteData data = null;
		data = gson.fromJson(res, RemoteData.class);
		if (data == null || data.getName() == null) {
			return secondName;
		}
		secondName = data.getName();
		Log.i(TAG, "getCameraSecondName,  " + secondName);
		return secondName;
	}
	public boolean openWifi(String name,Context context){
		Log.i(TAG, "openWifi,  start");
		boolean wifiStatus = false;
//		IPCamera cameraOpenWifi = null;
//		if(!ObjectCheck.validString(name)){
//			return wifiStatus;
//		}
//		for(IPCamera camera:mIpCameras){
//			if(camera.getName().equals(name)){
//				cameraOpenWifi = camera;
//				break;
//			}
//		}
		
		String baseURL = name;
		String ip = CameraUtil.getIpByBaseUrl(baseURL);
		
		wifiStatus = CgiUtilFactory.getCgiUtil(ip).openWifi(ip,context);
		return wifiStatus;
	}
	
	public IPCamera scanWifi(IPCamera camera,Context context){
		Log.i(TAG, "scanWifi,  start");
		openWifi(camera.getmBaseAccesssURL(),context);
		List<CameraWifi> wifiList = null;
		if(!ObjectCheck.validObject(camera)){
			return null;
		}
		
		String baseURL = camera.getmBaseAccesssURL();
		String ip = CameraUtil.getIpByBaseUrl(baseURL);
		wifiList = CgiUtilFactory.getCgiUtil(ip).scanWifi(ip,context);
		
		if((wifiList == null) || (wifiList.size() == 0)){
			return null;
		}
		camera.getNetInfo().setWifiList(wifiList);
		return camera;
	}
	
	
	public IPCamera setWifi(IPCamera camera,Context context){
		Log.i(TAG, "setWifi,  start");
		boolean status = false;
		CameraWifi cameraWifi = null;
		if(!ObjectCheck.validObject(camera)){
			return null;
		}
		if(!ObjectCheck.validObject(camera.getNetInfo())){
			return null;
		}
		if(!ObjectCheck.validObject(camera.getNetInfo().getMyWifi())){
			return null;
		}
		
		String wifiName = camera.getNetInfo().getMyWifi().getSsId();
		String wifiPw = camera.getNetInfo().getMyWifi().getPw();
		
		for(CameraWifi wifi:camera.getNetInfo().getWifiList()){
			if(wifi.getSsId().equals(wifiName)){
				cameraWifi = wifi;
				break;
			}
		}
		cameraWifi.setPw(wifiPw);
		String baseURL= camera.getmBaseAccesssURL();
		String ip = CameraUtil.getIpByBaseUrl(baseURL);
		status = CgiUtilFactory.getCgiUtil(ip).setWifi(ip,cameraWifi,context);
		if(status){
			cameraWifi.setWifiOn(true);
			camera.getNetInfo().setMyWifi(cameraWifi);
			return camera;
		}
		else
			return null;
	}

	public boolean rebootCamera(String url,Context context){
		Log.i(TAG, "rebootCamera,  start");
		boolean status = false;
		if(!ObjectCheck.validString(url)){
			return status;
		}
		String ip = CameraUtil.getIpByBaseUrl(url);
		status = CgiUtilFactory.getCgiUtil(ip).rebootCam(ip, context);
		return status;
	}
	
	private boolean sequencePtz = true;
	private boolean sequenceStatus = false;
	private ReentrantLock lock = new ReentrantLock();
	public  boolean ptzCtrl(IPCamera camera,Context context,String CMD,int speed){
		lock.lock();
		Log.i(TAG, "ptzCtrl,  start");
		boolean status = false;
		if(!ObjectCheck.validObject(camera))
			return status;
		if(!ObjectCheck.validString(camera.getmBaseAccesssURL()))
			return status;
		if(sequencePtz){
			sequencePtz = false;
			Log.i(TAG, "running......................................................................." + CMD);
			status = CgiUtilFactory.getCgiUtil(camera.getmBaseAccesssURL()).ptzCtrl(camera.getmBaseAccesssURL(), CMD, speed,context);
			sequencePtz = true;
			if(!status)
				CgiUtilFactory.getCgiUtil(camera.getmBaseAccesssURL()).ptzCtrl(camera.getmBaseAccesssURL(),PaneseCgiUtil.VAR_PTZ_CTRL_STOP , speed,context);
			if(sequenceStatus){
				Log.i(TAG, "running.......................................................................else in running" + CMD);
				status = CgiUtilFactory.getCgiUtil(camera.getmBaseAccesssURL()).ptzCtrl(camera.getmBaseAccesssURL(),PaneseCgiUtil.VAR_PTZ_CTRL_STOP , speed,context);
				sequenceStatus = false;
			}
		}else{
			sequencePtz = true;
			sequenceStatus = true;
			Log.i(TAG, "running.......................................................................else" + CMD);
		}
		lock.unlock();
		return status;
	}
	public boolean presetPtz(IPCamera camera,Context context,String action,int status,int num){
		Log.i(TAG, "presetPtz,  start");
		boolean setStatus = false;
		if(!ObjectCheck.validObject(camera))
			return setStatus;
		if(!ObjectCheck.validString(camera.getmBaseAccesssURL()))
			return setStatus;
		
		setStatus = CgiUtilFactory.getCgiUtil(camera.getmBaseAccesssURL()).presetPTZ(camera.getmBaseAccesssURL(), action, status, num, context);
		return setStatus;
	}
	public boolean gotoPreSet(String url,Context context){
		Log.i(TAG, "gotoPreSet,  start");
		boolean setStatus = false;
		if(!ObjectCheck.validString(url)){
			return setStatus;
		}
		setStatus = CgiUtilFactory.getCgiUtil(url).gotoPreSet(url, context);
		return setStatus;
	}
	public boolean changeOSD(String url,String name,Context context){
		Log.i(TAG,"changeOSD");
		boolean status = false;
		if(!ObjectCheck.validString(url)){
			return status;
		}
		if(!ObjectCheck.validString(name)){
			return status;
		}
		status = CgiUtilFactory.getCgiUtil(url).changeOSD(url, name, context);
		return status;
	}
}
