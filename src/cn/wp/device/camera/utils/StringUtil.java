/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.lang.reflect.Field;

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
 * @Title StringUtil.java 
 * @Package cn.ws.device.camera.utils 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��13�� ����9:13:22 
 * @version V1.0   
 */
public class StringUtil {
	
	private static final String TAG = "StringUtil";
	/**
	* �任һ�����е�string �ַ���
	* @param obj
	*/
	public static void prefixField(Object obj, String prefix) throws Exception{
		if (ObjectCheck.validObject(obj) && ObjectCheck.validString(prefix)) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field:fields){
				if(field.getType() == String.class){
					String oldValue = (String) field.get(obj);
					String newValue = prefix + oldValue;
					field.set(obj, newValue);
				}
			}
		}
	}
	
	/**
	* ����Ӻ�׺����ʽ�ı�һ������ָ������������
	* @param obj
	*/
	public static void suffixField(Object obj, String suffix) throws Exception{
		if (ObjectCheck.validObject(obj) && ObjectCheck.validString(suffix)) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field:fields){
				String oldName = (String) field.getName();
				String newName = oldName + suffix;
				field.set(obj, newName);
			}
		}
	}
}
