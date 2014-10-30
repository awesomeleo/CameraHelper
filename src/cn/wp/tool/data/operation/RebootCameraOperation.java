package cn.wp.tool.data.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.data.service.CameraService;
import cn.wp.tool.provider.CameraListProvider;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class RebootCameraOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.rebootCamera";
	private final String TAG = RebootCameraOperation.class.getSimpleName();
	private CameraService mCameraService = null;
	
	public RebootCameraOperation(CameraService service){
		mCameraService = service;
	}
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		Log.i(TAG, "execute");
		String url = request.getString(PARAM_METHOD);
		boolean setStatus = false;
		setStatus = mCameraService.rebootCamera(url, context);
		if(setStatus){
			context.getContentResolver().delete(CameraListProvider.CONTENT_URI, 
					Defination.CameraTableColum.BASEURL.getName()+"=?", new String[]{url});
			Intent intent=new Intent();
			intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.UPDATE_ONLINE_CAMERA_NUM);
		    intent.setAction("cn.wp.tool.status");
		    context.sendBroadcast(intent);
		}
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_REBOOT_CAMERA);
        bundle.putBoolean(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_REBOOT,setStatus);
        return bundle;
	}

}
