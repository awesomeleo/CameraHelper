package cn.wp.tool.data.model;



public abstract class Camera{
	
	private static final String TAG = Camera.class.getSimpleName();
	/** identifier */
	protected String name;
	protected String hardwareVersion;
	protected String softwareVersion;
	protected String mac;
	protected int output_video_count;
	protected String mBaseAccesssURL;
	protected String mRtspVideoAccessURL;
	protected String snapshotUri;
	protected CameraNetInfo netInfo;
	protected int needReboot = 0;

	public enum Camera_Type {
		Analog_Camera,
		IP_Camera,
		ZAGIP_Camera,
		Stub_Camera,
	}
	
	public abstract Camera_Type getType();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	/**
	 * @return the mBaseAccesssURL
	 */
	public String getmBaseAccesssURL() {
		return mBaseAccesssURL;
	}
	/**
	 * @param mBaseAccesssURL the mBaseAccesssURL to set
	 */
	public void setmBaseAccesssURL(String mBaseAccesssURL) {
		this.mBaseAccesssURL = mBaseAccesssURL;
	}

	/**
	 * @return the snapshotUri
	 */
	public String getSnapshotUri() {
		return snapshotUri;
	}

	/**
	 * @param snapshotUri the snapshotUri to set
	 */
	public void setSnapshotUri(String snapshotUri) {
		this.snapshotUri = snapshotUri;
	}
	
	public int getSupportedVideoCnt() {
		return output_video_count;
	}
	
	public abstract String getRtspLiveStringURL(int streamIndex);
	
	public abstract void setRtspLiveStringURL(String rtspSrcAddr);
	
	public abstract boolean open();
	
	public abstract boolean close();

	public CameraNetInfo getNetInfo() {
		return netInfo;
	}

	public void setNetInfo(CameraNetInfo netInfo) {
		this.netInfo = netInfo;
	}

	public int isNeedReboot() {
		return needReboot;
	}

	public void setNeedReboot(int needReboot) {
		this.needReboot = needReboot;
	}
}
