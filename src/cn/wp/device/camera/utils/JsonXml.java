/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.io.InputStream;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
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
 * @Title JsonXml.java 
 * @Package cn.ws.device.camera.utils 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����6:20:19 
 * @version V1.0   
 */
public class JsonXml {
	
	private static final String TAG = "JsonXml";
	
    /**
     * ��ȡXML�ļ�׼��ΪJSON�ַ���
     * @param xmlFile  XML�ļ�
     * @return JSON�ַ���
     */
    public static String getXMLFiletoJSONString(InputStream is/*String xmlFile*/) {
    	Log.i(TAG, "getXMLFiletoJSONString, is:" + is);
    	if (is == null) {
    		return "";
    	}
        //InputStream is = JSONUtils.class.getResourceAsStream(xmlFile);
        JSON json = null;
		XMLSerializer xmlSerializer = new XMLSerializer();
		json =  xmlSerializer.readFromStream(is);
        return json.toString();
    }


}
