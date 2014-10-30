package cn.wp.tool.data.service;


import cn.wp.tool.data.exception.MyCustomRequestException;
import cn.wp.tool.data.operation.ChangeOSDOperation;
import cn.wp.tool.data.operation.CheckUpdateOperation;
import cn.wp.tool.data.operation.CtrlCameraPtzOperation;
import cn.wp.tool.data.operation.DownloadAppOperation;
import cn.wp.tool.data.operation.GotoPreSetOperation;
import cn.wp.tool.data.operation.PingNetStatusOperation;
import cn.wp.tool.data.operation.PreSetCameraOperation;
import cn.wp.tool.data.operation.RebootCameraOperation;
import cn.wp.tool.data.operation.ScanCameraOperation;
import cn.wp.tool.data.operation.ScanWifiOperation;
import cn.wp.tool.data.operation.SetWifiOperation;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.updater.Updater;

import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * This class is called by the {@link PoCRequestManager} through the {@link Intent} system.
 *
 * @author Foxykeep
 */
public final class ToolRequestService extends RequestService {
	private CameraService cameraService = null;
	private Updater updater = null;
    @Override
    protected int getMaximumNumberOfThreads() {
        return 3;
    }

    @Override
    public Operation getOperationForType(int requestType) {
    	if(cameraService == null)
    	{
    		cameraService = new CameraService();
    		cameraService.initCameraManager(this);
    	}
    	if(updater == null){
    		updater = new Updater(this);
    	}
        switch (requestType) {
        case ToolRequestFactory.REQUEST_TYPE_SCAN_ONLINE_CAMERA:
        	return new ScanCameraOperation(cameraService);
        case ToolRequestFactory.REQUEST_TYPE_SCAN_WIFI:
        	return new ScanWifiOperation(cameraService);
        case ToolRequestFactory.REQUEST_TYPE_SET_WIFI:
        	return new SetWifiOperation(cameraService);
        case ToolRequestFactory.REQUEST_CTRL_PTZ:
        	return new CtrlCameraPtzOperation(cameraService);
        case ToolRequestFactory.REQUEST_PRE_SET:
        	return new PreSetCameraOperation(cameraService);
        case ToolRequestFactory.REQUEST_REBOOT_CAMERA:
        	return new RebootCameraOperation(cameraService);
        case ToolRequestFactory.REQUEST_CHECK_UPDATE:
        	return new CheckUpdateOperation(updater);
        case ToolRequestFactory.REQUEST_DOWNLOAD_APK:
        	return new DownloadAppOperation(updater);
        case ToolRequestFactory.REQUEST_PING_STATUS:
        	return new PingNetStatusOperation();
        case ToolRequestFactory.REQUEST_GOT_TO_PRE_SET:
        	return new GotoPreSetOperation(cameraService);
        case ToolRequestFactory.REQUEST_CHANGE_OSD:
        	return new ChangeOSDOperation(cameraService);
        }
        return null;
    }

    @Override
    protected Bundle onCustomRequestException(Request request, CustomRequestException exception) {
        if (exception instanceof MyCustomRequestException) {
            Bundle bundle = new Bundle();
            bundle.putString(ToolRequestFactory.BUNDLE_EXTRA_ERROR_MESSAGE,
                    "MyCustomRequestException thrown.");
            return bundle;
        }
        return super.onCustomRequestException(request, exception);
    }
}

