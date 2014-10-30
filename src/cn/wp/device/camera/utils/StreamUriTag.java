/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import com.google.gson.annotations.Expose;

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
 * @Title StreamUriTag.java 
 * @Package cn.ws.device.camera.utils 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����12:13:13 
 * @version V1.0   
 */
public class StreamUriTag {
	@Expose
	private String Uri ;
	@Expose
	private boolean InvalidAfterConnect;
	@Expose
	private boolean InvalidAfterReboot;
	@Expose
	private String Timeout ;
	
	/**
	 * @return the uri
	 */
	public String getUri() {
		return Uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		Uri = uri;
	}

	/**
	 * @return the invalidAfterConnect
	 */
	public boolean isInvalidAfterConnect() {
		return InvalidAfterConnect;
	}

	/**
	 * @param invalidAfterConnect the invalidAfterConnect to set
	 */
	public void setInvalidAfterConnect(boolean invalidAfterConnect) {
		InvalidAfterConnect = invalidAfterConnect;
	}

	/**
	 * @return the invalidAfterReboot
	 */
	public boolean isInvalidAfterReboot() {
		return InvalidAfterReboot;
	}

	/**
	 * @param invalidAfterReboot the invalidAfterReboot to set
	 */
	public void setInvalidAfterReboot(boolean invalidAfterReboot) {
		InvalidAfterReboot = invalidAfterReboot;
	}

	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return Timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(String timeout) {
		Timeout = timeout;
	}

	public String toString() {
		return "Uri:" + Uri
				+ ", InvalidAfterConnect:" + InvalidAfterConnect
				+ ", InvalidAfterReboot:" + InvalidAfterReboot
				+ ", Timeout:" + Timeout;
	}
	
}
