/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Namespace;
import cn.wp.device.camera.onvif.profile.Profile;
import cn.wp.device.camera.onvif.profile.VideoEncoderConfiguration;
import cn.wp.device.camera.utils.ClassUtil;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.device.camera.utils.XstreamUtil;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

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
 * @Title OnvifRequest.java 
 * @Package cn.ws.device.camera.utils 
 * @Description message to post
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����3:27:52 
 * @version V1.0   
 */
public class OnvifRequest {
	
	private static final String TAG = "OnvifRequest";
	private static final boolean logEnable = false;
	
	private static StringBuffer sb;
	
	/**
	 * generate a soap request for probe onvif device
	 * */
	public static String generateDeviceProbe(String uuid) {
		if(!ObjectCheck.validString(uuid)) {
			return "";
		}

		sb = new StringBuffer();
		//sb.append("<?xml version=\"1.0\"  encoding=\"UTF-8\" ?>\r\n");
		sb.append("<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"\r\n");
		sb.append("            xmlns:a=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">\r\n");
		sb.append("            <s:Header>\r\n");
		sb.append("                <a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</a:Action>\r\n");
		sb.append("                <a:MessageID>uuid:" + uuid + "</a:MessageID>\r\n");
		sb.append("                <a:ReplyTo>\r\n");
		sb.append("                    <a:Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</a:Address>\r\n");
		sb.append("                </a:ReplyTo>\r\n");
		sb.append("                <a:To s:mustUnderstand=\"1\">urn:schemas-xmlsoap-org:ws:2005:04:discovery</a:To>\r\n");
		sb.append("            </s:Header>\r\n");
		sb.append("            <s:Body>\r\n");
		sb.append("                <Probe xmlns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">\r\n");
		sb.append("                    <d:Types xmlns:d=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\"\r\n");
		sb.append("                             xmlns:dp0=\"http://www.onvif.org/ver10/network/wsdl\">dp0:NetworkVideoTransmitter</d:Types>\r\n");
		sb.append("                </Probe>\r\n");
		sb.append("            </s:Body>\r\n");
		sb.append("</s:Envelope>");
		return sb.toString();
	}
	
	/**
	 * generate a soap request for GetScopes of onvif device
	 * */
	public static String generateGetDeviceInformation() {
		sb = new StringBuffer();
		sb.append("        <GetDeviceInformation " + Onvif_Namespace.Tds + "/>");
		return simpleWrapRequest(sb.toString());
	}
	/**
	 * generate a soap request for GetScopes of onvif device
	 * */
	public static String generateGetDevicePort(){
		sb = new StringBuffer();
		sb.append("        <GetNetworkProtocols " + Onvif_Namespace.Tds + "/>");
		return simpleWrapRequest(sb.toString());
	}
	
	
	/**
	 * generate a soap request for GetScopes of onvif device
	 * */
	public static String generateGetScopes() {
		sb = new StringBuffer();
		//header is not necessary
		String pwd = "oQlkxcoLir+/pf/8G6lkB5hux5A=";
		String nonce = "+BLw/YFnWU+8bZ5Ly5EzWh0BAAAAAA==";
		String created = "2000-01-01T00:37:00.002Z";
		
		
		/*sb.append("<s:Header>");
		sb.append("<Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
		sb.append("<UsernameToken>");
		sb.append("<Username>admin</Username>");
		sb.append("<Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">"
				+ pwd + "</Password>");
		sb.append("<Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">"
				+ nonce + "</Nonce>");
		sb.append("<Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+ created + "</Created>");
		sb.append("</UsernameToken>");
		sb.append("</Security>");
		sb.append("</s:Header>");*/
		
		sb.append("        <GetScopes " + Onvif_Namespace.Tds + "/>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for Reboot onvif device
	 * */
	public static String generateReboot() {
		sb = new StringBuffer();
		sb.append("        <SystemReboot " + Onvif_Namespace.Tds + "/>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for getProfiles from onvif device
	 * */
	public static String generateGetProfiles() {
		sb = new StringBuffer();
		sb.append("        <GetProfiles " + Onvif_Namespace.Trt + "/>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for getStreamUri of onvif device
	 * */
	public static String generateStreamUri(String profileToken) {
		if(!ObjectCheck.validString(profileToken)) {
			return "";
		}
		String stream = "RTP-Unicast";
		String tProtocol = "RTSP";
		sb = new StringBuffer();
		sb.append("        <GetStreamUri " + Onvif_Namespace.Trt + ">");
		sb.append("            <StreamSetup>");
		sb.append("                <Stream xmlns=\"http://www.onvif.org/ver10/schema\">" + stream + "</Stream>");
		sb.append("                <Transport xmlns=\"http://www.onvif.org/ver10/schema\">");
		sb.append("                    <Protocol>" + tProtocol + "</Protocol>");
		sb.append("                </Transport>");
		sb.append("            </StreamSetup>");
		sb.append("            <ProfileToken>" + profileToken + "</ProfileToken>");
		sb.append("        </GetStreamUri>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for getStreamUri of onvif device
	 * */
	public static String generateGetSnapshotUri(String profileToken) {
		if(!ObjectCheck.validString(profileToken)) {
			return "";
		}
		sb = new StringBuffer();
		sb.append("        <GetSnapshotUri " + Onvif_Namespace.Trt + ">");
		sb.append("            <ProfileToken>" + profileToken + "</ProfileToken>");
		sb.append("        </GetSnapshotUri>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * /onvif/media
	 * generate a soap request for wsdlGetProfile of onvif device
	 * */
	public static String generateWsdlGetProfile(String profileToken) {//action="http://www.onvif.org/ver10/media/wsdlGetProfile/"
		if(!ObjectCheck.validString(profileToken)) {
			return "";
		}

		sb = new StringBuffer();
		sb.append("        <GetProfile " + Onvif_Namespace.Trt + ">");
		sb.append("            <ProfileToken>" + profileToken + "</ProfileToken>");
		sb.append("        </GetProfile>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for set video encoder configuration to onvif device
	 * */
	public static String generateGetVideoEncoderConfig(Profile p, VideoEncoderConfiguration veConfig) {
		if(!ObjectCheck.validObject(veConfig)) {
			return "";
		}

		sb = new StringBuffer();
		sb.append("        <GetVideoEncoderConfigurationOptions " + Onvif_Namespace.Trt + ">");
		sb.append("            <ConfigurationToken>" + veConfig.getToken() + "</ConfigurationToken>");
		sb.append("            <ProfileToken>" + p.getToken() + "</ProfileToken>");
		sb.append("        </GetVideoEncoderConfigurationOptions>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * generate a soap request for set video encoder configuration to onvif device
	 * */
	public static String generateSetVideoEncoderConfig(VideoEncoderConfiguration veConfig) {
		if(!ObjectCheck.validObject(veConfig)) {
			return "";
		}

		String veConfigXml = XstreamUtil.VideoEncoderConfig2xml(veConfig);
		veConfigXml = veConfigXml.replaceAll(VideoEncoderConfiguration.class.getName(), "Configuration");
		if (logEnable) {
			Log.i(TAG, "generateSetVideoEncoderConfig, veConfigXml:" + veConfigXml);			
		}
		
		String tt = " xmlns=\"http://www.onvif.org/ver10/schema\"";//attention the space
		Map<String, String> vemap = getSuffixFieldMap(VideoEncoderConfiguration.class, tt);
		Set<Entry<String, String>> s = vemap.entrySet();
		for (Entry<String, String> e:s) {
			//Log.i(TAG, "generateSetVideoEncoderConfig, map.key:" + e.getKey());
			if (veConfigXml.contains("<" + e.getKey() + ">")) {
				veConfigXml = veConfigXml.replaceAll("<" + e.getKey() + ">", "<" + e.getValue() + ">");
			}
		}
		if (logEnable) {
			Log.i(TAG, "generateSetVideoEncoderConfig, after suffix field, veConfigXml:" + veConfigXml);			
		}
		sb = new StringBuffer();
		sb.append("        <SetVideoEncoderConfiguration " + Onvif_Namespace.Trt + ">");
		sb.append("            " + veConfigXml + "");
		sb.append("            <ForcePersistence>true</ForcePersistence>");
		sb.append("        </SetVideoEncoderConfiguration>");
		return simpleWrapRequest(sb.toString());
	}
	
	/**
	 * POST /onvif/media
	 * generate a soap request of media
	 * */
	public static String simpleWrapRequest(String innerRequest) {
		if(!ObjectCheck.validString(innerRequest)) {
			return "";
		}

		sb = new StringBuffer();
		sb.append("<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">");
		sb.append("    <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		sb.append("            xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sb.append(innerRequest);
		sb.append("    </s:Body>");
		sb.append("</s:Envelope>");
		return sb.toString();
	}
	
	/**
	* ����Ӻ�׺����ʽ�ı�һ������ָ�����������Ʋ�ӳ�䵽ԭ����
	* @param obj ָ����
	* @param suffix ��׺�ַ���
	* @return ӳ���ϵmap
	*/
	private static Map<String, String> getSuffixFieldMap(Class<? extends Object> c, String suffix) {
		Map<String, String> m = new HashMap<String, String>();
		if (ObjectCheck.validObject(c) && ObjectCheck.validString(suffix)) {
			Field[] fields = c.getDeclaredFields();
			if (logEnable) {
				Log.i(TAG, "getSuffixFieldMap, fields:" + fields);
				Log.i(TAG, "getSuffixFieldMap, fields.l:" + fields.length);
			}
			for(Field field:fields){
				
				if (ClassUtil.isFieldAnnotationed(field, XStreamAsAttribute.class.getName())) {
					continue;
				}
				
				String oldName = (String) field.getName();
				String newName = oldName + suffix;
				//Log.i(TAG, "getSuffixFieldMap, oldName:" + oldName + ", newName:" + newName);
				m.put(oldName, newName);
			}
		}
		return m;
	}
	
}
