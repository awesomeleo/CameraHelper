/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Namespace;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Profiles_TAG;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_SCOPES_TAG;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_VECO_TAG;
import cn.wp.device.camera.onvif.OnvifDefination.Soap_Xml_Tag;
import cn.wp.device.camera.onvif.probe.Scopes;
import cn.wp.device.camera.onvif.profile.Profile;
import cn.wp.device.camera.onvif.profile.Profiles;
import cn.wp.device.camera.onvif.profile.VideoEncoderConfiguration;
import cn.wp.device.camera.onvif.response.GetScopesResponse;
import cn.wp.device.camera.onvif.response.GetVideoEncoderConfigurationOptions;
import cn.wp.device.camera.onvif.veco.H264;
import cn.wp.device.camera.onvif.veco.JPEG;
import cn.wp.device.camera.onvif.veco.MPEG4;
import cn.wp.device.camera.onvif.veco.Options;
import cn.wp.device.camera.onvif.veco.ResolutionsAvailable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
 * @Title XstreamUtil.java 
 * @Package cn.ws.device.camera.utils 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����9:03:01 
 * @version V1.0   
 */
public class XstreamUtil {
	
	private static final String TAG = "XstreamUtil";
	private static final boolean logEnable = false;
	public static final String package_profile = "cn.ws.device.camera.onvif.profile";
	private static XStream xstream = new XStream(new DomDriver());
	
	private static Context mContext;
	
	/**
     * configure the context
     */
	public static void register(Context context) {
		if (context == null) {
			Log.e(TAG, "register, params context is null!");
			return;
		}
		mContext = context;
	}
	
	
	/**
     * @param xml the origin response return from ONVIF device
     * @param tag tag name of destination border
     * @return the content between tag
     */
	public static String getXmlBetweenTag(String xml, String tag) {
		if (xml == null) {
			Log.e(TAG, "getXmlBetweenTag, params xml:" + xml + ", tag:" + tag);
			return "";
		}
		String ret = "";
		String start = "<" + tag + ">";
		String end = "</" + tag + ">";
		int startIndex = xml.indexOf(start) + start.length();
		int endIndex = xml.indexOf(end);
		Log.i(TAG, "getXmlBetweenTag, startIndex:" + startIndex + ", endIndex:" + endIndex);
		if (startIndex > 0 && endIndex > startIndex) {
			ret = xml.substring(startIndex, endIndex);
		}
		//Log.i(TAG, "getXmlBetweenTag, ret:" + ret);
		return ret;
	}
	
	/**
     * @param xml the origin response return from ONVIF device
     * @param tag tag name of destination border
     * @return the content between tag
     */
	public static String getXmlWithTag(String xml, String tag) {
		if (!ObjectCheck.validString(xml) || !ObjectCheck.validString(tag)) {
			Log.e(TAG, "getXmlWithTag, params xml:" + xml + ", tag:" + tag);
			return "";
		}
		String ret = "";
		String start = "<" + tag + ">";
		String end = "</" + tag + ">";
		int startIndex = xml.indexOf(start);
		int endIndex = xml.indexOf(end) + end.length();
		Log.i(TAG, "getXmlWithTag, startIndex:" + startIndex + ", endIndex:" + endIndex);
		if (startIndex > 0 && endIndex > startIndex) {
			ret = xml.substring(startIndex, endIndex);
		}
		//Log.i(TAG, "getXmlWithTag, ret:" + ret);
		return ret;
	}
	
	/**
	 * reset the XStream instance
     * @param xStream the new XStream instance will be used
     */
	private static void resetXStream(XStream xStream) {
		if (xStream == null) {
			Log.e(TAG, "resetXStream, params xStream is null!");
			return;
		}
		xstream = xStream;
	}
	
	/**
	 * generate a GetScopesResponse class from xml
     * @param xml the origin response String of getScopes from ONVIF device
     */
	public static GetScopesResponse xml2Scopes(String xml) {
		if (!ObjectCheck.validString(xml)) {
			Log.e(TAG, "xml2Scopes, xml:" + xml);
			return null;
		}
		xml = getXmlBetweenTag(xml, Soap_Xml_Tag.Soap_Body);
		xstream.alias(Onvif_SCOPES_TAG.Get_Scopes_Response, GetScopesResponse.class);
		xstream.addImplicitCollection(GetScopesResponse.class, "ScopesList");
		xstream.alias(Onvif_SCOPES_TAG.Scopes, Scopes.class);
		XstreamUtil.aliasFieldForOnvifTag(Scopes.class, Onvif_Namespace.Ver10_Schema);
		return (GetScopesResponse)xstream.fromXML(xml);
	}
	
	/**
	 * generate a Profiles class from xml
     * @param xml the origin response String of getProfiles from ONVIF device
     */
	public static Profiles xml2Profiles(String xml) {
		if (!ObjectCheck.validString(xml)) {
			Log.e(TAG, "xml2Profiles, xml:" + xml);
			return null;
		}
		xml = getXmlBetweenTag(xml, Soap_Xml_Tag.Soap_Body);
		Profiles profiles;
		//for ����ͨ
		String regularExpression = "<tt:VideoAnalyticsConfiguration[^>]*?>.*?</tt:VideoAnalyticsConfiguration>";
		xml = xml.replaceAll(regularExpression, "");
		//Log.i(TAG, "xml2Profiles, xml after cut VideoAnalyticsConfiguration:" + xml);
		String metaRegularExpression = "<tt:MetadataConfiguration[^>]*?>.*?</tt:MetadataConfiguration>";
		xml = xml.replaceAll(metaRegularExpression, "");
		//Log.i(TAG, "xml2Profiles, xml after cut MetadataConfiguration:" + xml);
		
		xstream.alias(Onvif_Profiles_TAG.Get_Profiles_Response, Profiles.class);
		xstream.addImplicitCollection(Profiles.class, "ProfileList");
		xstream.alias(Onvif_Profiles_TAG.Profiles, Profile.class);
		XstreamUtil.aliasFieldForOnvifTag(Profile.class, Onvif_Namespace.Ver10_Schema);
		String pkgName = Profile.class.getPackage().getName();
		XstreamUtil.aliasFieldForOnvifTagPkg(Onvif_Profiles_TAG.class, pkgName, Onvif_Namespace.Ver10_Schema);
	    xstream.omitField(Profile.class, "tt:VideoAnalyticsConfiguration");
	    
	    try {
	    	profiles = (Profiles)xstream.fromXML(xml);
		} catch (XStreamException xe) {
			Log.e(TAG, "xml2Profiles, XStreamException:" + xe);
			return null;
		}
		return profiles;
	}
	
	//need to optimize tag collection
	/**
	 * generate a GetVideoEncoderConfigurationOptions class from xml
     * @param xml the origin response String of GetVideoEncoderConfigurationOptions from ONVIF device
     */
	public static GetVideoEncoderConfigurationOptions xml2GetVideoEncoderConfigurationOptions(String xml) {
		if (!ObjectCheck.validString(xml)) {
			Log.e(TAG, "xml2GetVideoEncoderConfigurationOptions, xml:" + xml);
			return null;
		}
		xml = getXmlBetweenTag(xml, Soap_Xml_Tag.Soap_Body);
		GetVideoEncoderConfigurationOptions gveco;
		//for ����ͨ
		String regularExpression = "<tt:Extension[^>]*?>.*?</tt:Extension>";
		xml = xml.replaceAll(regularExpression, "");
		//Log.i(TAG, "xml2GetVideoEncoderConfigurationOptions, xml after split:" + xml);
		
		XstreamUtil.resetXStream(new XStream(new DomDriver()));//to enable the new config(Resolutions_Available)
		
		String raTagStart = "<" + Onvif_VECO_TAG.Resolutions_Available;
		String mpeg4Content = getXmlWithTag(xml, "tt:MPEG4");
		if (hasCollectionOfTag(mpeg4Content, raTagStart)) {
			Log.i(TAG, "xml2GetVideoEncoderConfigurationOptions, Resolutions_Available in MPEG4 not one");
			xstream.alias(Onvif_VECO_TAG.Resolutions_Available, ResolutionsAvailable.class);
			XstreamUtil.aliasFieldForOnvifTagWithCollection(MPEG4.class, Onvif_Namespace.Ver10_Schema, 
					new String[]{Onvif_VECO_TAG.Resolutions_Available.substring(Onvif_Namespace.Ver10_Schema.length())});
		} else {
			XstreamUtil.aliasFieldForOnvifTag(MPEG4.class, Onvif_Namespace.Ver10_Schema);
		}
		String jpegContent = getXmlWithTag(xml, "tt:JPEG");
		if (hasCollectionOfTag(jpegContent, raTagStart)) {
			Log.i(TAG, "xml2GetVideoEncoderConfigurationOptions, Resolutions_Available in JPEG not one");
			xstream.alias(Onvif_VECO_TAG.Resolutions_Available, ResolutionsAvailable.class);
			XstreamUtil.aliasFieldForOnvifTagWithCollection(JPEG.class, Onvif_Namespace.Ver10_Schema, 
					new String[]{Onvif_VECO_TAG.Resolutions_Available.substring(Onvif_Namespace.Ver10_Schema.length())});
		} else {
			XstreamUtil.aliasFieldForOnvifTag(JPEG.class, Onvif_Namespace.Ver10_Schema);
		}
		String h264Content = getXmlWithTag(xml, "tt:H264");
		String profilebaseTagStart = "<" + "tt:H264ProfilesSupported";
		if (hasCollectionOfTag(h264Content, raTagStart) || hasCollectionOfTag(h264Content, profilebaseTagStart)) {
			Log.i(TAG, "xml2GetVideoEncoderConfigurationOptions, Resolutions_Available or H264ProfilesSupported in H264 not one");
			xstream.alias(Onvif_VECO_TAG.Resolutions_Available, ResolutionsAvailable.class);
			
			XstreamUtil.aliasFieldForOnvifTagWithCollection(H264.class, Onvif_Namespace.Ver10_Schema, 
						new String[]{Onvif_VECO_TAG.Resolutions_Available.substring(Onvif_Namespace.Ver10_Schema.length()), 
				"H264ProfilesSupported"});
			xstream.aliasField("tt:H264ProfilesSupported", H264.class, "H264ProfilesSupported");
		} else {
			XstreamUtil.aliasFieldForOnvifTag(H264.class, Onvif_Namespace.Ver10_Schema);
		}
			
		xstream.alias(Onvif_VECO_TAG.Get_VECO_Response, GetVideoEncoderConfigurationOptions.class);
		xstream.aliasField(Onvif_VECO_TAG.VE_Options, GetVideoEncoderConfigurationOptions.class, "Options");
		xstream.alias(Onvif_VECO_TAG.VE_Options, Options.class);
		XstreamUtil.aliasFieldForOnvifTag(Options.class, Onvif_Namespace.Ver10_Schema);

		//xstream.addImplicitCollection(H264.class, "ResolutionList");
		/*xstream.aliasField(Onvif_VECO_TAG.Resolutions_Available, H264.class, "ResolutionList");
		xstream.aliasField(Onvif_VECO_TAG.Encoding_Interval_Rang, H264.class, "EncodingIntervalRange");
		xstream.aliasField(Onvif_VECO_TAG.Frame_Rate_Range, H264.class, "FrameRateRange");
		xstream.aliasField(Onvif_VECO_TAG.GovLength_Range, H264.class, "GovLengthRange");
		xstream.aliasField(Onvif_VECO_TAG.Encoding_Interval_Rang, H264.class, "H264ProfilesSupported");*/
		
		String pkgName = Options.class.getPackage().getName();
		XstreamUtil.aliasFieldForOnvifTagPkg(Onvif_VECO_TAG.class, pkgName, Onvif_Namespace.Ver10_Schema);
		try {
			gveco = (GetVideoEncoderConfigurationOptions)xstream.fromXML(xml);
		} catch (XStreamException xe) {
			Log.e(TAG, "xml2GetVideoEncoderConfigurationOptions, XStreamException:" + xe);
			return null;
		}
	    return gveco;
	}
	
	/**
     * alias a class's field to get the same name with xml tag
     * @param definedIn the class whose fields need be aliased
     * @param tagPrefix add it before the field will get the alias
     */
	private static void aliasFieldForOnvifTag(Class<?> definedIn, String tagPrefix) {
		//Log.i(TAG, "aliasFieldForOnvifTag, begin definedIn:" + definedIn);
		if (ObjectCheck.validObject(definedIn) && ObjectCheck.validString(tagPrefix)) {
			//Log.i(TAG, "aliasFieldForOnvifTag, param valid, class:" + definedIn);
			Field[] fields = definedIn.getDeclaredFields();//get all field
			//Log.i(TAG, "aliasFieldForOnvifTag, param valid, fields:" + fields);
			//Log.i(TAG, "aliasFieldForOnvifTag, param valid, fields.l:" + fields.length);
			for(Field field:fields){
				//Log.i(TAG, "aliasFieldForOnvifTag, for field");
				if (field.isAnnotationPresent(XStreamAsAttribute.class)) {
					String attr = field.getName();
					//Log.i(TAG, "aliasFieldForOnvifTag, XStreamAsAttribute field:" + attr);
					xstream.aliasAttribute(definedIn, attr, attr);
					continue;
				}
				
				if (field.isAnnotationPresent(XStreamImplicit.class)) {
					String attr = field.getName();
					//Log.i(TAG, "aliasFieldForOnvifTag, omit XStreamImplicit field:" + attr);
					xstream.omitField(definedIn, attr);
					continue;
				}
				
				//if(field.getType() == String.class || field.getType() == int.class){
					//Log.i(TAG, "aliasFieldForOnvifTag, for String field");
					String sName = (String) field.getName();
					String pName = tagPrefix + sName;
					//Log.i(TAG, "aliasFieldForOnvifTag, pName:" + pName + ", sName:" + sName);
					xstream.aliasField(pName, definedIn, sName);
				//}
			}
		}
		//Log.i(TAG, "aliasFieldForOnvifTag, end");
	}
	
	/**
     * alias a class's field to get the same name with xml tag
     * @param definedIn the class whose fields need be aliased
     * @param tagPrefix add it before the field will get the alias
     * @param implicitFields the fields should be addImplicitCollection in definedIn
     */
	private static void aliasFieldForOnvifTagWithCollection(Class<?> definedIn, String tagPrefix, String[] implicitFields) {
		//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, begin definedIn:" + definedIn + ", tagPrefix:" + tagPrefix + ", implicitFields:" + implicitFields);
		if (ObjectCheck.validObject(definedIn) && ObjectCheck.validString(tagPrefix)) {
			Field[] fields = definedIn.getDeclaredFields();//get all field
			//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, param valid, fields:" + fields);
			//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, param valid, fields.l:" + fields.length);
			//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, param implicitFields.l:" + implicitFields.length);
			for(Field field:fields){
				//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, for field");
				boolean fProcessed = false;
				for (String s:implicitFields) {//����ĳ�ڵ��¿��ܴ��ڶ��ͬ����ǩ
					//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, for implicitFields, s:" + s);
					if (field.getName().equals(s)) {
						//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, omitField by implicitField");
						xstream.omitField(definedIn, s);
						fProcessed = true;
						break;
					} else if (field.getName().equals(s + "List")) {
						//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, implicitField match, field:" + field.getName());
						xstream.addImplicitCollection(definedIn, field.getName());
						fProcessed = true;
						break;
					}
				}
				if (fProcessed) {
					continue;
				}
				
				//�������Ϊ���Եı���
				if (field.isAnnotationPresent(XStreamAsAttribute.class)) {
					String attr = field.getName();
					//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, XStreamAsAttribute attr:" + attr);
					xstream.aliasAttribute(definedIn, attr, attr);
					continue;
				}
				String sName = (String) field.getName();
				String pName = tagPrefix + sName;
				//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, pName:" + pName + ", sName:" + sName);
				xstream.aliasField(pName, definedIn, sName);
				
			}
		}
		//Log.i(TAG, "aliasFieldForOnvifTagWithCollection, end");
	}
	
	/**
     * alias all fields of classes in the pkgName and tagged with aliasTag in groupName class
     * @param groupName a class contains all tags of a onvif interface's tag group
     * @param pkgName which contains all classes mapped with all tags of the group
     * @param aliasTag add it before the field will get the alias
     */
	private static void aliasFieldForOnvifTagPkg(Class<?> groupName, String pkgName, String aliasTag) {
		if (!ObjectCheck.validParams(groupName, pkgName, aliasTag)) {
			Log.e(TAG, "aliasFieldForOnvifTagPkg, groupName:" + groupName + ", pkgName:" + pkgName + ", aliasTag:" + aliasTag);
			return;
		}
		Field[] fields = groupName.getDeclaredFields();//get all field
		//Log.i(TAG, "aliasFieldForOnvifTagPkg, param valid, fields.l:" + fields.length);
		for(Field field:fields){
			//Log.i(TAG, "aliasFieldForOnvifTagPkg, for field");
			if(field.getType() == String.class){
				//Log.i(TAG, "aliasFieldForOnvifTagPkg, for String field");
				try {
					String sName = (String) field.get(groupName);
					//Log.i(TAG, "aliasFieldForOnvifTagPkg, sName:" + sName);
					if (sName.startsWith(aliasTag)) {
						sName = sName.substring(sName.indexOf(aliasTag) + aliasTag.length());
						//Log.i(TAG, "aliasFieldForOnvifTagPkg, className:" + sName);
						try {
							Class<?> c = Class.forName(pkgName + "." + sName);
							XstreamUtil.aliasFieldForOnvifTag(c, aliasTag);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * generate xml from a VideoEncoderConfiguration class
     * @param veConfig the VideoEncoderConfiguration class
     */
	public static String VideoEncoderConfig2xml(VideoEncoderConfiguration veConfig) {
		XStream xstream = new XStream(new DomDriver());
		Field[] fields = VideoEncoderConfiguration.class.getDeclaredFields();//get all field
		for(Field field:fields){
			//Log.i(TAG, "VideoEncoderConfig2xml, for field");
			Annotation[] as = field.getAnnotations();
			if (as != null) {
				int ac = as.length;
				for(int i=0;i<ac;i++) {
					//Log.i(TAG, "VideoEncoderConfig2xml, ano.name:" + as[i].annotationType().getName());
					//Log.i(TAG, "VideoEncoderConfig2xml, att.name:" + XStreamAsAttribute.class.getName());
					if (XStreamAsAttribute.class.getName().equals(as[i].annotationType().getName())) {
						String attr = field.getName();
						//Log.i(TAG, "VideoEncoderConfig2xml, for attr:" + attr);
						xstream.aliasAttribute(VideoEncoderConfiguration.class, attr, attr);
						continue;
					}
				}
			}
			String sName = (String) field.getName();
			//Log.i(TAG, "VideoEncoderConfig2xml, sName:" + sName);
		}
		return xstream.toXML(veConfig);
	}
	
	public static String printXml(InputStream xml) {
		if (!ObjectCheck.validObject(xml)) {
			Log.e(TAG, "printXml, xml:" + xml);
			return null;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				Document document = builder.parse(xml);
				
				Element element = document.getDocumentElement();
		        NodeList envelope = element.getElementsByTagName(Soap_Xml_Tag.Soap_Envelope);
		        if (envelope != null && envelope.getLength() > 0) {
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getNamespaceURI());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getBaseURI());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getLocalName());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getNodeName());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getNodeValue());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getPrefix());
						Log.e(TAG, "printXml, envelope.l:" + envelope.item(0).getTextContent());//��ȡvalue
				}
		        NodeList envelope2 = element.getElementsByTagName(Soap_Xml_Tag.Soap_Body);
		        if (envelope2 != null && envelope2.getLength() > 0) {
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getNamespaceURI());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getBaseURI());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getLocalName());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getNodeName());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getNodeValue());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getPrefix());
						Log.e(TAG, "printXml, envelope2.l:" + envelope2.item(0).getTextContent());//��ȡvalue
						//in some condition
						Element element2 = (Element)envelope2.item(0);
						NodeList nodeList2 = element2.getElementsByTagName(Onvif_Profiles_TAG.Get_Profiles_Response);
						if (nodeList2 != null && nodeList2.getLength() > 0) {
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getNamespaceURI());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getBaseURI());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getLocalName());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getNodeName());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getNodeValue());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getPrefix());
							Log.e(TAG, "printXml, nodeList2.l:" + nodeList2.item(0).getTextContent());//��ȡvalue
							//in some condition
							Element element3 = (Element)nodeList2.item(0);
							NodeList nodeList3 = element3.getElementsByTagName(Onvif_Profiles_TAG.Profiles);
							if (nodeList3 != null && nodeList3.getLength() > 0) {
								Log.e(TAG, "printXml, nodeList3.l:" + nodeList3.getLength());
							}
					}
				}
		        
				NamedNodeMap nnm = document.getAttributes();
				if (nnm != null) {
					int l = nnm.getLength();
					Log.e(TAG, "printXml, nnm.l:" + l);
					for (int i = 0;i<l;i++) {
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getNamespaceURI());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getBaseURI());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getLocalName());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getNodeName());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getNodeValue());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getPrefix());
						Log.e(TAG, "printXml, nnm.l:" + nnm.item(i).getTextContent());//��ȡvalue
					}
				} else {
					Log.e(TAG, "printXml, document.getAttributes is null");
				}
				NodeList nl = document.getChildNodes();
				if (nl != null) {
					int l = nl.getLength();
					Log.e(TAG, "printXml, nl.l:" + l);
					for (int i = 0;i<l;i++) {
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getNamespaceURI());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getBaseURI());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getLocalName());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getNodeName());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getNodeValue());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getPrefix());
						Log.e(TAG, "printXml, nl.l:" + nl.item(i).getTextContent());//��ȡvalue
						NodeList nl2 = nl.item(i).getChildNodes();
						if (nl2 != null) {
							int l2 = nl2.getLength();
							Log.e(TAG, "printXml, nl2.l2:" + l2);
							for (int j = 0;j<l2;j++) {
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getNamespaceURI());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getBaseURI());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getLocalName());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getNodeName());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getNodeValue());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getPrefix());
								Log.e(TAG, "printXml, nl2.l:" + nl2.item(j).getTextContent());//��ȡvalue
								NodeList nl3 = nl2.item(i).getChildNodes();
								if (nl3 != null) {
									int l3 = nl3.getLength();
									Log.e(TAG, "printXml, nl3.l3:" + l3);
									for (int k = 0;k<l3;k++) {
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getNamespaceURI());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getBaseURI());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getLocalName());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getNodeName());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getNodeValue());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getPrefix());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getTextContent());//��ȡvalue
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getNodeType());
										Log.e(TAG, "printXml, nl2.l:" + nl3.item(k).getOwnerDocument().toString());
										//NodeList nl3 = nl.item(i).getChildNodes();
									}
								
								}
							}
						}
					}
				}
				Node node1 = nnm.getNamedItem("SOAP-ENV");
				Log.e(TAG, "printXml, node1.l:" + node1.getNodeValue());
				Node node2 = nnm.getNamedItemNS("xmlns", "SOAP-ENV");
				Log.e(TAG, "printXml, node2.l:" + node2.getPrefix());
				
				Log.e(TAG, "printXml, getTagName:" + document.getDocumentElement().getTagName());
				Log.e(TAG, "printXml, getLocalName:" + document.getLocalName());
				String tag = "http://www.onvif.org/ver10/media/wsdl/GetProfilesResponse";
				NodeList nl1 = document.getElementsByTagName(tag);
				Log.e(TAG, "printXml, nl1.l:" + nl1.getLength());
				return nl1.item(0).getTextContent();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * @param origin the content should be scanned
     * @param tagStart the destination tag's start
     * @return true if there has more tagStart in origin
     */
	private static boolean hasCollectionOfTag(String origin, String tagStart) {
		return origin.indexOf(tagStart) != origin.lastIndexOf(tagStart);
	}
}
