package cn.wp.device.camera.onvif;

public class OnvifDefination {
	public static final class Soap_Xml_Tag {
		public static final String Soap_Envelope         = "SOAP-ENV:Envelope";
		public static final String Soap_Header           = "SOAP-ENV:Header";
		public static final String Soap_Header_MessageId = "wsa:MessageID";
		public static final String Soap_Header_RelatesTo = "wsa:RelatesTo";
		public static final String Soap_Header_ReplyTo   = "wsa:ReplyTo";
		public static final String Soap_Header_Address   = "wsa:Address";
		public static final String Soap_Header_To        = "wsa:To";
		public static final String Soap_Header_Action    = "wsa:Action";
		
		public static final String Soap_Body                  = "SOAP-ENV:Body";
		public static final String Soap_Body2                 = "soapenv:Body";

		/** define depend on tag */
		public static final String Stream_Uri_Main                  = "trt:GetStreamUriResponse";
		public static final String Stream_Uri_MediaUri              = "trt:MediaUri";
		public static final String Stream_Uri_Uri                   = "tt:Uri";
		public static final String Stream_Uri_InvalidAfterConnect   = "tt:InvalidAfterConnect";
		public static final String Stream_Uri_InvalidAfterReboot    = "tt:InvalidAfterReboot";
		public static final String Stream_Uri_Timeout               = "tt:Timeout";
	}
	
	public static final class Onvif_Probe_TAG {
		public static final String ProbeMatches     = "ProbeMatches";
		public static final String ProbeMatch       = "ProbeMatch";
		public static final String EndPointRef      = "wsa:EndpointReference";
		public static final String Address          = "wsa:Address";
		public static final String Types            = "Types";
		public static final String Scopes           = "Scopes";
		public static final String Xaddrs           = "XAddrs";
		public static final String MetadataVersion  = "MetadataVersion";
	}
	
	public static final class Onvif_Profiles_TAG {
		public static final String Get_Profiles_Response               = "trt:GetProfilesResponse";
		public static final String Profiles                            = "trt:Profiles";
		

		public static final String Address                             = "tt:Address";
		public static final String Audio_Encoder_Configuration         = "tt:AudioEncoderConfiguration";
		public static final String Audio_Source_Configuration          = "tt:AudioSourceConfiguration";
		public static final String Bounds                              = "tt:Bounds";
		public static final String DefaultPTZSpeed                     = "tt:DefaultPTZSpeed";
		public static final String H264                                = "tt:H264";
		public static final String MPEG4                               = "tt:MPEG4";
		public static final String Multicast                           = "tt:Multicast";
		public static final String PanTilt                             = "tt:PanTilt";
		public static final String PanTiltLimits                       = "tt:PanTiltLimits";
		
		public static final String PTZ_Configuration                   = "tt:PTZConfiguration";
		public static final String Range                               = "tt:Range";
		public static final String RateControl                         = "tt:RateControl";
		public static final String Resolution                          = "tt:Resolution";
		public static final String Video_Encoder_Configuration         = "tt:VideoEncoderConfiguration";
		public static final String Video_Source_Configuration          = "tt:VideoSourceConfiguration";
		
		public static final String XRange                              = "tt:XRange";
		public static final String YRange                              = "tt:YRange";
		public static final String Zoom                                = "tt:Zoom";
		public static final String ZoomLimits                          = "tt:ZoomLimits";
	}
	
	//VideoEncoderConfigurationOptions
	public static final class Onvif_VECO_TAG {
		public static final String Get_VECO_Response                   = "trt:GetVideoEncoderConfigurationOptionsResponse";
		public static final String VE_Options                          = "trt:Options";
		
		public static final String Encoding_Interval_Rang              = "tt:EncodingIntervalRange";
		public static final String Frame_Rate_Range                    = "tt:FrameRateRange";
		public static final String GovLength_Range                     = "tt:GovLengthRange";
		//public static final String H264                                = "tt:H264"; include collection, process alone
		//public static final String JPEG                                = "tt:JPEG";
		//public static final String MPEG4                               = "tt:MPEG4";
		public static final String Quality_Range                       = "tt:QualityRange";
		
		public static final String Resolutions_Available               = "tt:ResolutionsAvailable";
		
	}
	
	//ScopesResponse
	public static final class Onvif_SCOPES_TAG {
		public static final String Get_Scopes_Response                   = "tds:GetScopesResponse";
		public static final String Scopes                                = "tds:Scopes";
		
		public static final String ScopeDef                              = "tt:ScopeDef";
		public static final String ScopeItem                             = "tt:ScopeItem";
	}
	
	public static final class Onvif_Namespace {
		
		public static final String Ver10_Schema = "tt:";
		public static final String Discovery = "d:";
		//onvif1-wsdd=discovery and onvif2-d=discovery
		public static final String Discovery2 = "wsdd:";
		public static final String Tds = "xmlns=\"http://www.onvif.org/ver10/device/wsdl\"";
		public static final String Trt = "xmlns=\"http://www.onvif.org/ver10/media/wsdl\"";
	
		
		/*xmlns:SOAP-ENV="http://www.w3.org/2003/05/soap-envelope"
			    xmlns:SOAP-ENC="http://www.w3.org/2003/05/soap-encoding"
			    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
			    xmlns:wsa5="http://schemas.xmlsoap.org/ws/2004/08/addressing"
			    xmlns:c14n="http://www.w3.org/2001/10/xml-exc-c14n#"
			    xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
			    xmlns:xenc="http://www.w3.org/2001/04/xmlenc#"
			    xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
			    xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
			    xmlns:xmime="http://tempuri.org/xmime.xsd"
			    xmlns:xop="http://www.w3.org/2004/08/xop/include"
			    xmlns:wsbf2="http://docs.oasis-open.org/wsrf/bf-2"
			    xmlns:wsa="http://schemas.xmlsoap.org/ws/2004/08/addressing"
			    xmlns:tt="http://www.onvif.org/ver10/schema"
			    xmlns:wstop="http://docs.oasis-open.org/wsn/t-1"
			    xmlns:wsr2="http://docs.oasis-open.org/wsrf/r-2"
			    xmlns:decpp="http://www.onvif.org/ver10/events/wsdl/CreatePullPointBinding"
			    xmlns:dee="http://www.onvif.org/ver10/events/wsdl/EventBinding"
			    xmlns:denc="http://www.onvif.org/ver10/events/wsdl/NotificationConsumerBinding"
			    xmlns:denf="http://www.onvif.org/ver10/events/wsdl/NotificationProducerBinding"
			    xmlns:depp="http://www.onvif.org/ver10/events/wsdl/PullPointBinding"
			    xmlns:depps="http://www.onvif.org/ver10/events/wsdl/PullPointSubscriptionBinding"
			    xmlns:tev="http://www.onvif.org/ver10/events/wsdl"
			    xmlns:depsm="http://www.onvif.org/ver10/events/wsdl/PausableSubscriptionManagerBinding"
			    xmlns:wsnt="http://docs.oasis-open.org/wsn/b-2"
			    xmlns:desm="http://www.onvif.org/ver10/events/wsdl/SubscriptionManagerBinding"
			    xmlns:dndl="http://www.onvif.org/ver10/network/wsdl/DiscoveryLookupBinding"
			    xmlns:dnrd="http://www.onvif.org/ver10/network/wsdl/RemoteDiscoveryBinding"
			    xmlns:d="http://schemas.xmlsoap.org/ws/2005/04/discovery"
			    xmlns:dn="http://www.onvif.org/ver10/network/wsdl"
			    xmlns:tds="http://www.onvif.org/ver10/device/wsdl"
			    xmlns:timg="http://www.onvif.org/ver20/imaging/wsdl"
			    xmlns:tptz="http://www.onvif.org/ver20/ptz/wsdl"
			    xmlns:trt="http://www.onvif.org/ver10/media/wsdl" */
		
		/*xmlns:SOAP-ENV="http://www.w3.org/2003/05/soap-envelope"
			    xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
			    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
			    xmlns:wsa="http://schemas.xmlsoap.org/ws/2004/08/addressing"
			    xmlns:wsdd="http://schemas.xmlsoap.org/ws/2005/04/discovery"
			    xmlns:chan="http://schemas.microsoft.com/ws/2005/02/duplex"
			    xmlns:wsa5="http://www.w3.org/2005/08/addressing"
			    xmlns:xmime="http://tempuri.org/xmime.xsd"
			    xmlns:xop="http://www.w3.org/2004/08/xop/include"
			    xmlns:wsrfbf="http://docs.oasis-open.org/wsrf/bf-2"
			    xmlns:tt="http://www.onvif.org/ver10/schema"
			    xmlns:wstop="http://docs.oasis-open.org/wsn/t-1"
			    xmlns:wsrfr="http://docs.oasis-open.org/wsrf/r-2"
			    xmlns:ns1="http://www.onvif.org/ver10/actionengine/wsdl"
			    xmlns:tad="http://www.onvif.org/ver10/analyticsdevice/wsdl"
			    xmlns:tan="http://www.onvif.org/ver20/analytics/wsdl"
			    xmlns:tdn="http://www.onvif.org/ver10/network/wsdl"
			    xmlns:tds="http://www.onvif.org/ver10/device/wsdl"
			    xmlns:tev="http://www.onvif.org/ver10/events/wsdl"
			    xmlns:wsnt="http://docs.oasis-open.org/wsn/b-2"
			    xmlns:c14n="http://www.w3.org/2001/10/xml-exc-c14n#"
			    xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
			    xmlns:xenc="http://www.w3.org/2001/04/xmlenc#"
			    xmlns:wsc="http://schemas.xmlsoap.org/ws/2005/02/sc"
			    xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
			    xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
			    xmlns:timg="http://www.onvif.org/ver20/imaging/wsdl"
			    xmlns:tls="http://www.onvif.org/ver10/display/wsdl"
			    xmlns:tmd="http://www.onvif.org/ver10/deviceIO/wsdl"
			    xmlns:tptz="http://www.onvif.org/ver20/ptz/wsdl"
			    xmlns:trc="http://www.onvif.org/ver10/recording/wsdl"
			    xmlns:trp="http://www.onvif.org/ver10/replay/wsdl"
			    xmlns:trt="http://www.onvif.org/ver10/media/wsdl"
			    xmlns:trv="http://www.onvif.org/ver10/receiver/wsdl"
			    xmlns:ter="http://www.onvif.org/ver10/error"
			    xmlns:tse="http://www.onvif.org/ver10/search/wsdl"
			    xmlns:tns1="http://www.onvif.org/ver10/topics"*/
		
	}
}
