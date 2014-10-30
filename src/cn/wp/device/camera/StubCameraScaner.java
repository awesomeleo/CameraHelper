package cn.wp.device.camera;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.StubIPCamera;


public class StubCameraScaner extends IPCameraScaner {

	@Override
	public List<IPCamera> scanOnlineCameraList(Context context,Handler mWorkHandler){
		
		mIpCameras.clear();
		
		StubIPCamera ipCamera = new StubIPCamera();
		mIpCameras.add(ipCamera);

		return mIpCameras;
	}

	@Override
	public int getCgiVersion(String url) {
		// TODO Auto-generated method stub
		return 0;
	}

}
