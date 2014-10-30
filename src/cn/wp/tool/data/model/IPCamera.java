package cn.wp.tool.data.model;

import android.os.Parcelable;




public abstract class IPCamera extends Camera implements Parcelable{
	
	public Camera_Type getType() {
		return Camera_Type.IP_Camera;
	}
	
	public abstract boolean isSupportWireless();
	
	public abstract boolean isSupportRtspProtocol();
	
	public abstract boolean open();

	public abstract boolean close();
	
    @Override
    public String toString() {
        return  "\n<Camera Name:" + name  
				+ ">\n<Hardware Version:"      + hardwareVersion 
				+ ">\n<Software Version:"      + softwareVersion
				+ ">\n<Mac address:"           + mac
				+ ">\n<Base Access URL:"       + mBaseAccesssURL
				+ ">\n<Rtsp Video Access URL:" + mRtspVideoAccessURL
				+ ">\n<Snapshot JPEG Uri:"     + snapshotUri
				+ ">";
    }
}
