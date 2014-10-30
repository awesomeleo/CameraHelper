package cn.wp.tool.data.requestmanager;


import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.operation.ChangeOSDOperation;
import cn.wp.tool.data.operation.CtrlCameraPtzOperation;
import cn.wp.tool.data.operation.GotoPreSetOperation;
import cn.wp.tool.data.operation.PreSetCameraOperation;
import cn.wp.tool.data.operation.RebootCameraOperation;
import cn.wp.tool.data.operation.ScanCameraOperation;
import cn.wp.tool.data.operation.ScanWifiOperation;
import cn.wp.tool.data.operation.SetWifiOperation;

import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.requestmanager.Request;

/**
 * Class used to create the {@link Request}s.
 *
 * @author Foxykeep
 */
public final class ToolRequestFactory {

    // Request types
	public static final int REQUEST_TYPE_SCAN_ONLINE_CAMERA = 0;
	public static final int REQUEST_TYPE_SCAN_WIFI = 3;
	public static final int REQUEST_TYPE_SET_WIFI = 4;
	public static final int REQUEST_CTRL_PTZ = 5;
	public static final int REQUEST_PRE_SET = 6;
	public static final int REQUEST_REBOOT_CAMERA = 7;
	public static final int REQUEST_CHECK_UPDATE = 8;
	public static final int REQUEST_DOWNLOAD_APK = 9;
	public static final int REQUEST_PING_STATUS = 10;
	public static final int REQUEST_GOT_TO_PRE_SET = 11;
	public static final int REQUEST_CHANGE_OSD = 12;
    // Response data
    public static final String BUNDLE_EXTRA_CHOOSE = 
    		"cn.wp.tool.device.camera.choose";
    public static final String BUNDLE_EXTRA_CAMERA_LIST =
            "cn.wp.tool.extra.cameraList";
    public static final String BUNDLE_EXTRA_CAMERA_SCAN_WIFI =
            "cn.wp.tool.extra.scanWifi";
    public static final String BUNDLE_EXTRA_CAMERA_SET_WIFI =
            "cn.wp.tool.extra.setWifi";
    public static final String BUNDLE_EXTRA_CAMERA_CTRL_PTZ =
            "cn.wp.tool.extra.ctrlPTZ";
    public static final String BUNDLE_EXTRA_CAMERA_PRE_SET =
            "cn.wp.tool.extra.presetPTZ";
    public static final String BUNDLE_EXTRA_CAMERA_REBOOT =
            "cn.wp.tool.extra.rebootCamera";
    public static final String BUNDLE_EXTRA_CHECK_UPDATE =
            "cn.wp.tool.extra.checkUpdate";
    public static final String BUNDLE_EXTRA_DOWNLOAD_APK =
            "cn.wp.tool.extra.downloadApk";
    public static final String BUNDLE_EXTRA_PING_STATUS =
            "cn.wp.tool.extra.ping";
    public static final String BUNDLE_EXTRA_GOTO_PRESET =
            "cn.wp.tool.extra.goto";
    public static final String BUNDLE_EXTRA_CHANGE_OSD =
            "cn.wp.tool.extra.changeOsd";
    public static final String BUNDLE_EXTRA_RESULT =
            "com.foxykeep.datadroidpoc.extra.result";
    public static final String BUNDLE_EXTRA_ERROR_MESSAGE =
            "com.foxykeep.datadroidpoc.extra.errorMessage";

    private ToolRequestFactory() {
        // no public constructor
    }




    public static Request getCameraListRequest(boolean openDhcpSwitch) {
        Request request = new Request(REQUEST_TYPE_SCAN_ONLINE_CAMERA);
        request.setMemoryCacheEnabled(true);
        request.put(ScanCameraOperation.PARAM_METHOD, openDhcpSwitch);
        return request;
    }
    

    public static Request scanWifi(IPCamera camera){
        Request request = new Request(REQUEST_TYPE_SCAN_WIFI);
        request.setMemoryCacheEnabled(true);
        request.put(ScanWifiOperation.PARAM_METHOD, camera);
        return request;
    }
    
    public static Request setWifi(IPCamera camera){
        Request request = new Request(REQUEST_TYPE_SET_WIFI);
        request.setMemoryCacheEnabled(true);
        request.put(SetWifiOperation.PARAM_METHOD, camera);
        return request;
    }
    
    public static Request ctrlPtz(IPCamera camera,String CMD,int speed){
        Request request = new Request(REQUEST_CTRL_PTZ);
        request.setMemoryCacheEnabled(true);
        request.put(CtrlCameraPtzOperation.PARAM_METHOD, camera);
        request.put("CMD", CMD);
        request.put("SPEED", speed);
        return request;
    }
    
    public static Request presetPtz(IPCamera camera,String action,int status,int num){
        Request request = new Request(REQUEST_PRE_SET);
        request.setMemoryCacheEnabled(true);
        request.put(PreSetCameraOperation.PARAM_METHOD, camera);
        request.put("ACTION", action);
        request.put("STATUS", status);
        request.put("NUM", num);
        return request;
    }
    public static Request rebootCamera(String url){
        Request request = new Request(REQUEST_REBOOT_CAMERA);
        request.setMemoryCacheEnabled(true);
        request.put(RebootCameraOperation.PARAM_METHOD, url);
        return request;
    }
    public static Request checkUpdate(){
        Request request = new Request(REQUEST_CHECK_UPDATE);
        request.setMemoryCacheEnabled(true);
        return request;
    }
    
    public static Request downloadApk(){
        Request request = new Request(REQUEST_DOWNLOAD_APK);
        request.setMemoryCacheEnabled(true);
        return request;
    }
    public static Request pingNetStatus(){
        Request request = new Request(REQUEST_PING_STATUS);
        request.setMemoryCacheEnabled(true);
        return request;
    }
    public static Request gotoPreSet(String url){
        Request request = new Request(REQUEST_GOT_TO_PRE_SET);
        request.setMemoryCacheEnabled(true);
        request.put(GotoPreSetOperation.PARAM_METHOD, url);
        return request;
    }
    public static Request changeOSD(String url,String name){
        Request request = new Request(REQUEST_CHANGE_OSD);
        request.setMemoryCacheEnabled(true);
        request.put(ChangeOSDOperation.PARAM_METHOD, url);
        request.put(ChangeOSDOperation.PARAM_NAME, name);
        return request;
    }
}
