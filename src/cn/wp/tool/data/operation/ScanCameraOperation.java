package cn.wp.tool.data.operation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.OnvifCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.data.service.CameraService;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class ScanCameraOperation implements Operation{
	public static final String PARAM_METHOD = "cn.wp.tool.extra.cameraList";
	private final String TAG = ScanCameraOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	
	public ScanCameraOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		// TODO Auto-generated method stub
        String url = "239.255.255.250";//port3702
        boolean openDhcp = request.getBoolean(PARAM_METHOD);
        mCameraService.scanOnLineCamera(context,openDhcp);
        return null;
	}
	
	
}
