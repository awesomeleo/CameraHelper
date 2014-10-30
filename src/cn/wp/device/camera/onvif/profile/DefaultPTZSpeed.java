/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.profile;

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
 * @Title DefaultPTZSpeed.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:47:22 
 * @version V1.0   
 */
public class DefaultPTZSpeed {
	private PanTilt PanTilt;
	private Zoom Zoom;
	
	/**
	 * @return the panTilt
	 */
	public PanTilt getPanTilt() {
		return PanTilt;
	}

	/**
	 * @param panTilt the panTilt to set
	 */
	public void setPanTilt(PanTilt panTilt) {
		PanTilt = panTilt;
	}

	/**
	 * @return the zoom
	 */
	public Zoom getZoom() {
		return Zoom;
	}

	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(Zoom zoom) {
		Zoom = zoom;
	}

	public String toString() {
		String s = "DefaultPTZSpeed: "
				 + "PanTilt:" + PanTilt
				 + ", Zoom:" + Zoom;
		return s;
	}
}
