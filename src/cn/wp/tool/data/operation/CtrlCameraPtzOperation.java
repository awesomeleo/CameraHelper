package cn.wp.tool.data.operation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.service.CameraService;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class CtrlCameraPtzOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.ctrlPtz";
	private final String TAG = SetWifiOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public CtrlCameraPtzOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		IPCamera camera = (IPCamera) request.getParcelable(PARAM_METHOD);
		String CMD = request.getString("CMD");
		int speed = request.getInt("SPEED");
		mCameraService.ptzCtrl(camera, context, CMD, speed);
		return null;
	}
}
