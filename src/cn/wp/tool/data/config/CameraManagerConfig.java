/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.tool.data.config;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 *
 * <p>
 * <h1>Reviewer:</h1> 
 * <a href="mailto:jiangjunjie@1v.cn">jjj</a>
 * </p>
 * 
 * <p>
 * <h1>History Trace:</h1>
 * <li>2014-04-01    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title CameraManagerConfig.java 
 * @Package cn.ws.device.camera.data 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��24�� ����11:21:25 
 * @version V1.0   
 */
public class CameraManagerConfig {
	
	private int maxCameraCount;
	private String cameraUsr;
	private String cameraPwd;
	private String camIdMode;

	/**
	 * @return the maxCameraCount
	 */
	public int getMaxCameraCount() {
		return maxCameraCount;
	}

	/**
	 * @param maxCameraCount the maxCameraCount to set
	 */
	public void setMaxCameraCount(int maxCameraCount) {
		this.maxCameraCount = maxCameraCount;
	}
	
	/**
	 * @return the cameraUsr
	 */
	public String getCameraUsr() {
		return cameraUsr;
	}

	/**
	 * @param cameraUsr the cameraUsr to set
	 */
	public void setCameraUsr(String cameraUsr) {
		this.cameraUsr = cameraUsr;
	}

	/**
	 * @return the cameraPwd
	 */
	public String getCameraPwd() {
		return cameraPwd;
	}

	/**
	 * @param cameraPwd the cameraPwd to set
	 */
	public void setCameraPwd(String cameraPwd) {
		this.cameraPwd = cameraPwd;
	}

	/**
	 * @return the camIdMode
	 */
	public String getCamIdMode() {
		return camIdMode;
	}

	/**
	 * @param camIdMode the camIdMode to set
	 */
	public void setCamIdMode(String camIdMode) {
		this.camIdMode = camIdMode;
	}

}
