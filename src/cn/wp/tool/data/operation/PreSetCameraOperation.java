package cn.wp.tool.data.operation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.data.service.CameraService;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class PreSetCameraOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.presetPtz";
	private final String TAG = PreSetCameraOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public PreSetCameraOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		IPCamera camera = (IPCamera) request.getParcelable(PARAM_METHOD);
		String CMD = request.getString("ACTION");
		int status = request.getInt("STATUS");
		int num = request.getInt("NUM");
		boolean setStatus = false;
		setStatus = mCameraService.presetPtz(camera, context, CMD, status, num);
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_PRE_SET);
        bundle.putBoolean(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_PRE_SET,setStatus);
        return bundle;
	}

}
