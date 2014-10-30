/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.tool.data.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 * @Title OnvifCamera.java 
 * @Package cn.ws.device.camera.impl 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��3��31�� ����11:21:38 
 * @version V1.0   
 */
public class OnvifCamera extends IPCamera  implements Parcelable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OnvifCamera() {
	}

	/* (non-Javadoc)
	 * @see cn.ws.device.camera.IPCamera#isSupportWireless()
	 */
	@Override
	public boolean isSupportWireless() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.ws.device.camera.IPCamera#isSupportRtspProtocol()
	 */
	@Override
	public boolean isSupportRtspProtocol() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.ws.device.camera.IPCamera#getRtspLiveStringURL(int)
	 */
	@Override
	public String getRtspLiveStringURL(int streamIndex) {
		// TODO Auto-generated method stub
		return mRtspVideoAccessURL;
	}
	public void setRtspLiveStringURL(String rtspSrcAddr) {
		mRtspVideoAccessURL = rtspSrcAddr;
	}

	/* (non-Javadoc)
	 * @see cn.ws.device.camera.IPCamera#open()
	 */
	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.ws.device.camera.IPCamera#close()
	 */
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
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		parcel.writeString(this.name);
		parcel.writeString(this.mBaseAccesssURL);
		parcel.writeInt(this.needReboot);
		parcel.writeString(this.mRtspVideoAccessURL);
		parcel.writeSerializable(this.netInfo);
	}
	
	public static final Parcelable.Creator<OnvifCamera> CREATOR = new Creator<OnvifCamera>() { 
		@Override 
		public OnvifCamera[] newArray(int size) {

		return new OnvifCamera[size]; 
		} 
		@Override 
		public OnvifCamera createFromParcel(Parcel source) { 
			OnvifCamera temp = new OnvifCamera();

		temp.name = source.readString(); 
		temp.mBaseAccesssURL = source.readString(); 
		temp.needReboot = source.readInt();
		temp.mRtspVideoAccessURL = source.readString();
		temp.netInfo = (CameraNetInfo) source.readSerializable();
		return temp; 
		} 
	};

}
