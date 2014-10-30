/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import android.util.Log;

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
 * @Title CameraUtil.java 
 * @Package cn.ws.device.camera.utils 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��7��2�� ����8:44:13 
 * @version V1.0   
 */
public class CameraUtil {
	
	private static final String TAG = "CameraUtil";
    
    public static String getIpByBaseUrl(String baseUrl) {
    	Log.i(TAG, "getIpByBaseUrl, param baseUrl:" + baseUrl);
    	String ip = "";
    	if (ObjectCheck.validString(baseUrl)) {
    		int index = baseUrl.lastIndexOf(":");
    		if (index == -1) {
    			ip = baseUrl;
    		} else if(index > 0) {
    			ip = baseUrl.substring(0, index);
    		} else {
    			Log.e(TAG, "getIpByBaseUrl, invalid base url!");
    		}
    	}
    	return ip;
    }

}
