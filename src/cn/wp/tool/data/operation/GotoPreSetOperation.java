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

public final class GotoPreSetOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.goto";
	private final String TAG = GotoPreSetOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public GotoPreSetOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		String url = request.getString(PARAM_METHOD);
		boolean setStatus = false;
		setStatus = mCameraService.gotoPreSet(url, context);
        return null;
	}
}
