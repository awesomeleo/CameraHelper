package cn.wp.device.camera.utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Namespace;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_Probe_TAG;
import cn.wp.device.camera.onvif.OnvifDefination.Soap_Xml_Tag;
import cn.wp.device.camera.onvif.probe.DiscoveryHeader;
import cn.wp.device.camera.onvif.probe.EndpointReference;
import cn.wp.device.camera.onvif.probe.ProbeMatch;
import cn.wp.device.camera.onvif.response.DiscoveryResponse;

public class OnvifXmlResolver extends DefaultHandler{
	
	private static final String TAG = OnvifXmlResolver.class.getSimpleName();

	public OnvifXmlResolver() {
		/*try {
			saxParser = saxParserFactory.newSAXParser();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}*/
	}

	private static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	private static SAXParser saxParser;
	private static XMLReader	xmlReader;
		static {
			try {
				saxParser = saxParserFactory.newSAXParser();
				xmlReader = saxParser.getXMLReader();
				xmlReader.setContentHandler(new OnvifXmlResolver());
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}


	private static BaseTag baseTag;
	private static DiscoveryHeader discoveryHeader = new DiscoveryHeader();
	private static ProbeMatch probeMatch = new ProbeMatch();
	private static EndpointReference endpointReference = new EndpointReference();
	
	private static StreamUriTag streamUriTag = new StreamUriTag();
	private boolean soapHeader;
	private boolean isProbeMatch;
	private boolean streamUriAnalyze;
	

	private String tempTag = "";
	private StringBuffer sb;
	private StringBuffer pN;

    private boolean finishFlag = false;

    public static final DiscoveryResponse getDiscoveryResponse(InputSource is) {
    	DiscoveryResponse wsDiscoveryRes = new DiscoveryResponse();
    	discoveryHeader = new DiscoveryHeader();
    	probeMatch = new ProbeMatch();
    	endpointReference = new EndpointReference();
    	try {
			System.out.println(is);
			xmlReader.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
    	wsDiscoveryRes.setDiscoveryHeader(discoveryHeader);
    	probeMatch.setEndpointReference(endpointReference);
    	wsDiscoveryRes.setProbeMatch(probeMatch);
    	Log.i(TAG ,"getDiscoveryResponse, ret probeMatch:" + probeMatch);
		return wsDiscoveryRes;
    }
    public static final ProbeMatch getProbeMatch(InputSource is){
    	try {
			System.out.println(is);
			xmlReader.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return probeMatch;
    }
    public static final StreamUriTag getStreamUriTag(InputSource is){
    	try {
			System.out.println(is);
			xmlReader.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return streamUriTag;
    }
    public static final BaseTag getBaseTag(InputSource is) {
    	try {
			xmlReader.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
    	System.out.println(baseTag.getResultMessage()+"---------------------");
    	return baseTag;

    }

    @Override
    public void startDocument()
    	throws SAXException {
    	super.startDocument();
    	baseTag = new BaseTag();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    	throws SAXException {
//    	if(onlyTag.equals(qName)) {
//    		tempTag =onlyTag;
//    	}
    	//Log.i(TAG ,"startElement----------localName:" + localName + ", qName:" + qName + ", uri:" + uri);
    	
    	if(Soap_Xml_Tag.Soap_Header.equals(qName)) {
        	Log.i(TAG ,"startElement----------begin discoveryHeader");
        	soapHeader = true;
    	}
    	if(Soap_Xml_Tag.Soap_Header_MessageId.equals(qName)
    			|| Soap_Xml_Tag.Soap_Header_RelatesTo.equals(qName)
    			|| Soap_Xml_Tag.Soap_Header_ReplyTo.equals(qName)
    			|| Soap_Xml_Tag.Soap_Header_Address.equals(qName)
    			|| Soap_Xml_Tag.Soap_Header_To.equals(qName)
    			|| Soap_Xml_Tag.Soap_Header_Action.equals(qName)) {
    		tempTag = qName;
    	}
    	
    	if(Soap_Xml_Tag.Soap_Body.equals(qName) || Soap_Xml_Tag.Soap_Body2.equals(qName)) {
        	Log.i(TAG ,"startElement----------begin probeMatch");
        	isProbeMatch = true;
    	}
    	if((Onvif_Namespace.Discovery + Onvif_Probe_TAG.ProbeMatches).equals(qName)
    			|| (Onvif_Namespace.Discovery + Onvif_Probe_TAG.ProbeMatch).equals(qName)
    			|| Onvif_Probe_TAG.EndPointRef.equals(qName)
    			|| Onvif_Probe_TAG.Address.equals(qName)
    			|| (Onvif_Namespace.Discovery + Onvif_Probe_TAG.Types).equals(qName)
    			|| (Onvif_Namespace.Discovery + Onvif_Probe_TAG.Scopes).equals(qName)
    			|| (Onvif_Namespace.Discovery + Onvif_Probe_TAG.Xaddrs).equals(qName)
    			|| (Onvif_Namespace.Discovery + Onvif_Probe_TAG.MetadataVersion).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.ProbeMatches).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.ProbeMatch).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Types).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Scopes).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Xaddrs).equals(qName)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.MetadataVersion).equals(qName)) {
    		tempTag = qName;
    	}
    	
    	if(Soap_Xml_Tag.Stream_Uri_Main.equals(qName)) {
        	Log.i(TAG ,"startElement----------begin StreamUri");
        	streamUriAnalyze = true;
    	}
    	if (streamUriAnalyze) {
    		if (Soap_Xml_Tag.Stream_Uri_Uri.equals(qName)
    				|| Soap_Xml_Tag.Stream_Uri_InvalidAfterConnect.equals(qName)
    				|| Soap_Xml_Tag.Stream_Uri_InvalidAfterReboot.equals(qName)
    				|| Soap_Xml_Tag.Stream_Uri_Timeout.equals(qName)) {
    			tempTag = qName;
    		}
    	}

    	//    	if("channelId".equals(qName)) {
//
//    		tempTag =qName;
//    	}
    	if("endTime".equals(qName)) {
    		tempTag =qName;
    	}
    	if("programId".equals(qName)) {
    		tempTag =qName;
    	}
    	if("programName".equals(qName)) {
    	        pN = new StringBuffer();
    		tempTag =qName;
    	}
    	if("startTime".equals(qName)) {
    		tempTag =qName;
    	}
    	if("S:Envelope".equals(qName)) {
    		baseTag.setXmlns_S(attributes.getValue("xmlns:s"));
    	}
    	if("ns2:loginResponse".equals(qName)) {
    		baseTag.setXmlns_ns2(attributes.getValue("xmlns:ns2"));
    	}
    	if("areaCode".equals(qName)) {
    		tempTag = qName;
    	}
    	if("token".equals(qName)) {
    		tempTag =qName;
    	}
    	if("userId".equals(qName)) {
    		tempTag = qName;
    	}
    	if("resultCode".equals(qName)) {
    		tempTag= qName;
    	}
    	if("resultMessage".equals(qName)) {
    		tempTag = qName;
    	}
    	if("ttvURL".equals(qName)){
    		sb = new StringBuffer();
    		tempTag = qName;
    	}

    }
    @Override
    public void characters(char[] ch, int start, int length)
    	throws SAXException {
    	super.characters(ch, start, length);
    	String value = new String(ch, start, length);
    	//System.out.println("characters----------tempTag:" + tempTag + ", value:" + value);
//    	if(tempTag.equals(onlyTag)) {
//    		System.out.println(sTag);
//    		onlyTagValue = sTag;
//    		tempTag="";
//    	}
    	
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_MessageId.equals(tempTag)) {
    		discoveryHeader.setWsaMessageId(value);
    		tempTag ="";
    	}
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_RelatesTo.equals(tempTag)) {
    		discoveryHeader.setWsaRelatesTo(value);
    		tempTag ="";
    	}
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_ReplyTo.equals(tempTag)) {
    		discoveryHeader.setWsaReplyToAddress(value);
    		tempTag ="";
    	}
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_Address.equals(tempTag)) {
    		discoveryHeader.setWsaReplyToAddress(value);
    		tempTag ="";
    	}
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_To.equals(tempTag)) {
    		discoveryHeader.setWsaTo(value);
    		tempTag ="";
    	}
    	if(soapHeader && Soap_Xml_Tag.Soap_Header_Action.equals(tempTag)) {
    		discoveryHeader.setWsaAction(value);
    		tempTag ="";
    	}
    	////////////////////////// probe body
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.ProbeMatches).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.ProbeMatches).equals(tempTag))) {
    		//isProbeMatchTag.setProbeMatch(value);
    		tempTag ="";
    	}
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.ProbeMatch).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.ProbeMatch).equals(tempTag))) {
    		//isProbeMatchTag.setProbeMatch(value);
    		tempTag ="";
    	}
    	if(isProbeMatch && Onvif_Probe_TAG.EndPointRef.equals(tempTag)) {
    		//endpointReference start
    		tempTag ="";
    	}
    	if(isProbeMatch && Onvif_Probe_TAG.Address.equals(tempTag)) {//TODO Fix me!!!
    		endpointReference.setAddress(value);//addr is not only in endpointReference 
    		tempTag ="";
    	}
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.Types).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Types).equals(tempTag))) {
    		probeMatch.setTypes(value);
    		tempTag ="";
    	}
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.Scopes).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Scopes).equals(tempTag))) {
    		probeMatch.setScopes(value);
    		tempTag ="";
    	}
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.Xaddrs).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.Xaddrs).equals(tempTag))) {
    		probeMatch.setXAddrs(value);
    		tempTag ="";
    	}
    	if(isProbeMatch && ((Onvif_Namespace.Discovery + Onvif_Probe_TAG.MetadataVersion).equals(tempTag)
    			|| (Onvif_Namespace.Discovery2 + Onvif_Probe_TAG.MetadataVersion).equals(tempTag))) {
    		probeMatch.setMetadataVersion(Integer.valueOf(value));
    		tempTag ="";
    	}
    	
    	//stream uri
    	if(streamUriAnalyze) {
    		if (Soap_Xml_Tag.Stream_Uri_Uri.equals(tempTag)) {
    			streamUriTag.setUri(value);
        		tempTag ="";
    		}
    		if (Soap_Xml_Tag.Stream_Uri_InvalidAfterConnect.equals(tempTag)) {
    			streamUriTag.setInvalidAfterConnect(Boolean.valueOf(value));
        		tempTag ="";
    		}
    		if (Soap_Xml_Tag.Stream_Uri_InvalidAfterReboot.equals(tempTag)) {
    			streamUriTag.setInvalidAfterReboot(Boolean.valueOf(value));
        		tempTag ="";
    		}
    		if (Soap_Xml_Tag.Stream_Uri_Timeout.equals(tempTag)) {
    			streamUriTag.setTimeout(value);
        		tempTag ="";
    		}
    	}
    	
    	
    	/////////////////////////////
    	if(tempTag.equals("programName")) {
    		pN.append(value);
    	}
    	if(tempTag.equals("ttvURL")){
    		if(value.equals("&")){
    			sb.append("&amp;");
    		} else {
    			sb.append(value);
    		}
    	}
    	if(tempTag.equals("resultMessage")) {
    		baseTag.setResultMessage(value);
    		tempTag="";
    	}
    }
    @Override
    public void endElement(String uri, String localName, String qName)
    	throws SAXException {
    	super.endElement(uri, localName, qName);
    	//Log.i(TAG, "endElement----------localName:" + localName + ", qName:" + qName + ", uri:" + uri);
    	
    	if(Soap_Xml_Tag.Soap_Header.equals(qName)) {
        	Log.i(TAG, "endElement----------end discoveryHeader");
        	soapHeader = false;
    	}
    	if(Soap_Xml_Tag.Soap_Body.equals(qName) || Soap_Xml_Tag.Soap_Body2.equals(qName)) {
        	Log.i(TAG, "endElement----------end isProbeMatchTag");
        	isProbeMatch = false;
    	}
    	
    	if(Soap_Xml_Tag.Stream_Uri_Main.equals(qName)) {
        	Log.i(TAG ,"endElement----------end streamUriTag StreamUri:" + streamUriTag);
        	
        	streamUriAnalyze = false;
    	}
    	
    	
    	if(baseTag != null && "ttvURL".equals(qName)) {
    		baseTag.setTtvURL(sb.toString());
    		tempTag ="";
    	}

    	if("S:Envelope".equals(qName)) {
    		finishFlag =true;
    	}
    }
}
