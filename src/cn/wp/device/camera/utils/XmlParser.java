package cn.wp.device.camera.utils;

import java.io.ByteArrayInputStream;

import org.xml.sax.InputSource;

import cn.wp.device.camera.onvif.response.DiscoveryResponse;
import android.util.Log;

public class XmlParser {
	
	private static final String TAG = "XmlParser";
	private static final boolean logEnable = false;
	private BaseTag baseTag;

	private StringBuffer sb;


	public DiscoveryResponse readResponseFromSoapXml(String s)  {
		if (logEnable) {
			Log.i(TAG, "readResponseFromSoapXml s:" + s);
		}
		ByteArrayInputStream bai = new ByteArrayInputStream(s.getBytes());
		InputSource is = new InputSource(bai);
		return OnvifXmlResolver.getDiscoveryResponse(is);
	}
	public String readRtspAddrFromSoapXml(String s)  {
		if (logEnable) {
			Log.i(TAG, "readRtspAddrFromSoapXml s:" + s);
		}
		ByteArrayInputStream bai = new ByteArrayInputStream(s.getBytes());
		InputSource is = new InputSource(bai);
		return OnvifXmlResolver.getStreamUriTag(is).getUri();
	}
	public BaseTag readChannelXml_BaseTag(String s) {
		ByteArrayInputStream bai = new ByteArrayInputStream(s.getBytes());
		InputSource is = new InputSource(bai);
		baseTag = OnvifXmlResolver.getBaseTag(is);
		if(baseTag != null) {
			return baseTag;
		}
		System.out.println("baseTag is empty");
		return null;
	}
	public  String requestBaseInfoString(BaseTag baseTag1) {
		sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"  encoding=\"UTF-8\" standalone=\"yes\" ?>\r\n");
		sb.append(" <S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"> ");
		sb.append(   "<S:Header/> \r\n"+
				"<S:Body> "+
				"<ns2:login xmlns:ns2=\"http://service.authenticate.coship.com/\">");
		sb.append(        " <userId>"+baseTag1.getUserId()+"</userId> ");
		sb.append(        "<version>"+baseTag1.getVersion()+"</version> ");
		sb.append(        "<userSign>"+baseTag1.getUserSign()+"</userSign>");
		sb.append(    "</ns2:login>"+
				"</S:Body> "+
				"</S:Envelope>");
		return sb.toString();
	}

	public String requestTimestampsString(String mac, String ip) {
		sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"  encoding=\"UTF-8\" standalone=\"yes\" ?>\r\n");
		sb.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n");
		sb.append("<S:Header/>\r\n"+
				"<S:Body> "+
				"<ns2:login xmlns:ns2=\"http://service.authenticate.coship.com/\"><clientrequest>\r\n");
		sb.append(       "<requestinfo smart_card_id=\"\" password=\"\" mac=" +
				mac + " ip=" + ip + " stbid=\"\"  user_id=\"\"/></clientrequest>\r\n");
		sb.append(    "</ns2:login>\r\n"+
				"</S:Body>\r\n"+
				"</S:Envelope>");
		return sb.toString();
	}

	public String requestTVPrograms(String userid, String channelid, String ver) {
		sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"  encoding=\"UTF-8\" ?>\r\n");
		sb.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n");
		sb.append("<S:Header/>\r\n"+
				"<S:Body> "+
				"<ns2:getTVProgram xmlns:ns2=\"http://service.stb.coship.com/\"><tVProgramReqVo>\r\n");
		sb.append(       "<userId>"+userid+"</userId>");
		sb.append(       "<channelId>"+channelid+"</channelId>");
		sb.append(       "<version>"+ver+"</version>");
		sb.append(       "</tVProgramReqVo>\r\n");
		sb.append(    "</ns2:getTVProgram>\r\n"+
				"</S:Body>\r\n"+
    			"</S:Envelope>");
		return sb.toString();
	}

	public String requestTimeShiftURL(String userid, String channelid, String starttime, String ver) {
		if(starttime.equals("0")) {
			starttime = "";
		}

		sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"  encoding=\"UTF-8\" ?>\r\n");
		sb.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n");
		sb.append("<S:Header/>\r\n"+
				"<S:Body> "+
				"<ns2:getTTVURL xmlns:ns2=\"http://service.stb.coship.com/\"><tTVURLReqVo>\r\n");
		sb.append(       "<userId>"+userid+"</userId>");
		sb.append(       "<channelId>"+channelid+"</channelId>");
		sb.append(       "<startTime>"+ starttime +"</startTime>");
		sb.append(       "<version>"+ver+"</version>");
		sb.append(       "</tTVURLReqVo>\r\n");
		sb.append(    "</ns2:getTTVURL>\r\n"+
				"</S:Body>\r\n"+
    			"</S:Envelope>");
		return sb.toString();
	}

}
