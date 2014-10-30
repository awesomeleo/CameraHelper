/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;


import java.net.InetAddress; 
import java.net.UnknownHostException; 

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
 * @Title GUID.java 
 * @Package cn.ws.device.camera.utils 
 * @Description UUID generate
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����3:12:00 
 * @version V1.0   
 */
public class GUID {
	private static String TAG = "GUID";
	private static String hostAddr = "" + (int)Math.random() * 90000; 

	/** 
	 * ���UUID��Type 4 
	 * @return String 
	 */ 
	public static String getNewGUID(){ 
		return java.util.UUID.randomUUID().toString(); 
	} 

	/** 
	 * ��ȡ���UUID�� �س�nλ 
	 */ 
	public static String getNewId(int n){ 
		if(n < 1) {
			Log.e(TAG, "invalid digit");
			return "";
		}
		return java.util.UUID.randomUUID().toString().substring(n); 
	} 

	/** 
	 * ����UUID��Type 3<br> 
	 * ����������������IP + �������� + ���UUID + �����<br> 
	 * <br> 
	 * �����ص����ò���������� + ���UUID ���ص�����UUID 
	 * @return String 
	 */ 
	public static String getNameGUID() {	
		try { 
			if (hostAddr.length()<8){ 
				hostAddr += InetAddress.getLocalHost().getHostAddress() + InetAddress.getLocalHost().getCanonicalHostName(); 
			} 
			String factor = hostAddr + getNewGUID() + Math.random(); 
			return java.util.UUID.nameUUIDFromBytes(factor.getBytes()).toString(); 
		} catch (UnknownHostException e) { 
			e.printStackTrace(); 
		} 
		return java.util.UUID.nameUUIDFromBytes((Math.random() + getNewGUID()).getBytes()).toString(); 
	} 

	/** 
	 * ����UUID��Type 3<br> 
	 * ��ͬ�Ĳ������ز�ͬ��UUID������һ��<br> 
	 * ��ʵ������MD5�������ַ����� 
	 * @param nameString 
	 * @return 
	 */ 
	public static String getNameGUID(String nameString){ 
		return java.util.UUID.nameUUIDFromBytes(nameString.getBytes()).toString(); 
	} 
} 
