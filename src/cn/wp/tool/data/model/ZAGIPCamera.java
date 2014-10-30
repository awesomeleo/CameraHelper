package cn.wp.tool.data.model;

import android.os.Parcel;
import android.util.Log;

public class ZAGIPCamera extends IPCamera {
	private static final String TAG = ZAGIPCamera.class.getSimpleName();
	public static final int OUTPUT_VIDEO_COUNT = 4;
	
	private static final int DEVICE_NAME_LENGTH = 13;
	private static final int DEVICE_HARDWARE_VERSION_LENGTH = 18;
	private static final int DEVICE_SOFTWARE_VERSION_LENGTH = 17;
	private static final int USELESS_NET_CONFIG_LENGTH = 8 + 1 + 20;
	
	private byte[] head = new byte[]{ (byte) 0xb4, (byte) 0x9a, 0x70, 0x4d, 0x01, 0x0d};
	
	private int mAccessPort;
	private String mUserName = "admin";
	private String mPassword = "";
	
	public ZAGIPCamera(byte[] cameraInfo, String ip){
		output_video_count = OUTPUT_VIDEO_COUNT;
		if (cameraInfo!=null)
			Log.i(TAG, "ZAGIPCamera, camera.data.l:" + cameraInfo.length);
		//check for ZAG Camera Info
		for(int i = 0; i < head.length; i ++){
			if(head[i] == cameraInfo[i])
				continue;
			else {
				throw new RuntimeException("unknown data header to paser as ZAGIPCamera");
			}
		}
		
		int offset = head.length;
		
		name = new String(cameraInfo, offset, DEVICE_NAME_LENGTH);
		offset = offset + DEVICE_NAME_LENGTH + 1;
		hardwareVersion = new String(cameraInfo, offset, DEVICE_HARDWARE_VERSION_LENGTH);
		offset = offset + DEVICE_HARDWARE_VERSION_LENGTH + 1;
		softwareVersion = new String(cameraInfo, offset, DEVICE_SOFTWARE_VERSION_LENGTH);
		offset = offset + DEVICE_SOFTWARE_VERSION_LENGTH + 1;
		String name2 = new String(cameraInfo, offset, DEVICE_NAME_LENGTH);
		Log.i(TAG, "ZAGIPCamera, name2:" + name2);
		offset = offset + DEVICE_NAME_LENGTH + USELESS_NET_CONFIG_LENGTH;
		int lp = cameraInfo[offset] & 0xFF;
		int hp = (cameraInfo[offset + 1] & 0xFF) << 8;
		int mPort = hp + lp;
		Log.i(TAG, "ZAGIPCamera, mPort:" + mPort);
		
		mAccessPort = mPort;//Integer.parseInt(name.substring(name.length() - 5));
		
		mBaseAccesssURL = "http://" + ip + ":"+mAccessPort;
		mRtspVideoAccessURL = "rtsp://" + mUserName + ":"+mPassword + "@" + ip + ":" + mAccessPort
				+ "/av.sdp";

	}

	/** choose output stream */
	public String getRtspLiveStringURL(int streamIndex) {
		if(streamIndex < 0 || streamIndex >= output_video_count)
			return mRtspVideoAccessURL;
		return mRtspVideoAccessURL + "?stream="+streamIndex;
	}
	
	@Override
	public Camera_Type getType() {
		return Camera_Type.ZAGIP_Camera;
	}
	
	public boolean isSupportRtspProtocol() {
		return true;
	}

	@Override
	public boolean isSupportWireless() {
		return true;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRtspLiveStringURL(String rtspSrcAddr) {
		// TODO Auto-generated method stub
		
	}
}
