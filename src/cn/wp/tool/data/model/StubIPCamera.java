package cn.wp.tool.data.model;

import android.os.Parcel;


public class StubIPCamera extends IPCamera {
	
	public StubIPCamera() {
		name = "StubIPCamera";
		mRtspVideoAccessURL = "rtsp://10.0.0.99:554/user=admin&password=&channel=1&stream=0.sdp";
	}

	@Override
	public Camera_Type getType() {
		return Camera_Type.Stub_Camera;
	}

	@Override
	public boolean isSupportRtspProtocol() {
		return true;
	}

	@Override
	public String getRtspLiveStringURL(int streamIndex) {
		//return "http://203.195.192.92:8080/video/test_video.mp4";
		return mRtspVideoAccessURL;
	}

	@Override
	public boolean isSupportWireless() {
		// TODO Auto-generated method stub
		return false;
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
