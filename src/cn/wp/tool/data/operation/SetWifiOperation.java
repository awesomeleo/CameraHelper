package cn.wp.tool.data.operation;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.data.service.CameraService;
import cn.wp.tool.provider.CameraListProvider;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class SetWifiOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.setWifi";
	private final String TAG = SetWifiOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public SetWifiOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		Log.i(TAG, "execute");
		IPCamera camera = (IPCamera) request.getParcelable(PARAM_METHOD);
		IPCamera cameraWifiSetting = null;
		cameraWifiSetting = mCameraService.setWifi(camera, context);
		if(ObjectCheck.validObject(cameraWifiSetting))
			if(ObjectCheck.validObject(cameraWifiSetting.getNetInfo()))
				if(ObjectCheck.validObject(cameraWifiSetting.getNetInfo().getMyWifi())){
					CameraWifi myWifi = cameraWifiSetting.getNetInfo().getMyWifi();
					if(myWifi.isWifiOn()){
						ContentValues values = new ContentValues(); 
						values.put(Defination.CameraTableColum.WIFI.getName(),myWifi.isWifiOn());
						values.put(Defination.CameraTableColum.SSID.getName(),myWifi.getSsId());
						values.put(Defination.CameraTableColum.RSSI.getName(),myWifi.getRssi());
						values.put(Defination.CameraTableColum.REBOOT.getName(),true);
						context.getContentResolver().update(CameraListProvider.CONTENT_URI, 
													values, Defination.CameraTableColum.SN.getName()+"=?", new String[]{cameraWifiSetting.getName()});
					}
				}
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_TYPE_SET_WIFI);
        bundle.putParcelable(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_SET_WIFI,cameraWifiSetting);
        return bundle;
	}

}
