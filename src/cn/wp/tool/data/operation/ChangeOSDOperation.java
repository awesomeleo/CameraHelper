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

public final class ChangeOSDOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.changeosd";
	public static final String PARAM_NAME = "name";
	private final String TAG = ChangeOSDOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public ChangeOSDOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		String CMD = request.getString(PARAM_METHOD);
		String name = request.getString(PARAM_NAME);
		boolean setStatus = false;
		setStatus = mCameraService.changeOSD(CMD,name, context);
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_CHANGE_OSD);
        bundle.putBoolean(ToolRequestFactory.BUNDLE_EXTRA_CHANGE_OSD,setStatus);
        return bundle;
	}
}
