package cn.wp.tool.data.operation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.data.service.CameraService;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class ScanWifiOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.wifiList";
	private final String TAG = ScanWifiOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public ScanWifiOperation(CameraService service){
		mCameraService = service;
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		Log.i(TAG, "execute");
		IPCamera camera = (IPCamera) request.getParcelable(PARAM_METHOD);
		IPCamera cameraWifiList = null;
		cameraWifiList = mCameraService.scanWifi(camera, context);
		
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_TYPE_SCAN_WIFI);
        bundle.putParcelable(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_SCAN_WIFI,cameraWifiList);
        return bundle;
	}

}
