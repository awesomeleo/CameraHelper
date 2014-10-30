package cn.wp.device.camera.utils;
//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.entity.StringEntity;
import org.ksoap2.SoapFault;

import com.foxykeep.datadroid.network.NetworkConnection;

import cn.wp.device.camera.onvif.response.DiscoveryResponse;
import cn.wp.device.camera.onvif.response.ProbeMatches;
import cn.wp.tool.data.config.Defination.Time_Constant;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
/**
 * <p>
 * RequestService.java
 * </p>
 * <p>
 * http:GET and Post
 * </p>
 * @author jjj
 * @since 2014-03-31
 */
@SuppressLint("NewApi") public class NetWorkUtil {
	
	private static final String TAG = "NetWorkUtil";

	private static final String REQUEST_METHOD_GET = "GET";
	private static final String REQUEST_METHOD_POST = "POST";
	private static final String ENCODING_UTF8 = "UTF-8";
	private static final int TIMEOUT_MILLILS = 20 * 1000;

	/**
	 * get方式提交,有文件长度大小限制
	 * @param urlStr 路径
	 * @param map 参数
	 * @throws Exception
	 * @author jjj
	 * @see
	 */
	public static String sendGetRequest(String urlStr, Map<?, ?> map,Context context)
		throws Exception {
		StringBuilder sBuilder = new StringBuilder(urlStr);
		if (!urlStr.endsWith("?")) {
			Log.i(TAG, " Url don't Own ? -----------");
			sBuilder.append("?");
		}
		if (map != null && !map.isEmpty()) {
			for (Entry<?, ?> entry : map.entrySet()) {
				sBuilder.append(entry.getKey());
				sBuilder.append("=");
				String value = entry.getValue() == null?"":entry.getValue().toString();
				sBuilder.append(URLEncoder.encode(value, "UTF-8"));
				sBuilder.append("&");
			}
		}
		sBuilder.deleteCharAt(sBuilder.length() - 1);
		Log.i(TAG, "sBuilder : " + sBuilder.toString());
        NetworkConnection connection = new NetworkConnection(context, sBuilder.toString());
        connection.setMethod(NetworkConnection.Method.GET);
        NetworkConnection.ConnectionResult result = connection.execute();
		Log.i(TAG, result.body);
//		URL url = new URL(sBuilder.toString());
//		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//		conn.setConnectTimeout(TIMEOUT_MILLILS);
//		conn.setReadTimeout(TIMEOUT_MILLILS);
//		conn.setRequestMethod(REQUEST_METHOD_GET);
//		int resCode = conn.getResponseCode();
//	    Log.i(TAG, "get request resCode : " + resCode);
//		if (resCode == 200) {
//			InputStream in = conn.getInputStream();
//			result = IoUtil.inputStreamToStringByByteArrayOutputStream(in, ENCODING_UTF8);
//			Log.i(TAG, "get request go back result : " + result);
//		}
		return result.body;
	}

	/**
     * Description: 获取GMT8时间
     * @return 将当前时间转换为GMT8时区后的Date
     */
    private static Date getGMT8Time(){
        Date gmt8 = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),Locale.CHINESE);
            Calendar day = Calendar.getInstance();
            day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            day.set(Calendar.DATE, cal.get(Calendar.DATE));
            day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
            day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
            gmt8 = day.getTime();
        } catch (Exception e) {
            System.out.println("获取GMT8时间 getGMT8Time() error !");
            e.printStackTrace();
            gmt8 = null;
        }
        return gmt8;
    }

    /**
	 *
	 * post请求,无文件长度大小限制
	 * @param urlStr
	 * @param map
	 * @return
	 * @throws Exception
	 * @author jjj
	 * @see
	 */
	public static InputStream sendPostRequestForInputStream(String urlStr, String postContent) throws IOException {
		Log.i(TAG, "urlStr : " + urlStr + ", post content is:" + postContent);
		InputStream is = null;
		StringBuilder sBuilder = new StringBuilder(postContent);
		// 下面的Content-Length: 是这个URL的二进制数据长度
		byte entitydata[] = sBuilder.toString().getBytes();

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// 设定请求的方法
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setConnectTimeout(TIMEOUT_MILLILS);
			conn.setReadTimeout(TIMEOUT_MILLILS);

			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此必须设置允许对外输出数据, 默认情况下是false;
			conn.setDoOutput(true);
			//conn.setDoInput(true);
			//conn.setUseCaches(false);
			conn.setRequestProperty("SOAPAction", "\"\"");
			conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));// 长度
			conn.setRequestProperty("Content-Type", "text/xml; Charset=UTF-8");// 内容类型

			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream(),"UTF-8"));
			out.write(postContent);
			out.flush();
			out.close();

			// 状态码是不成功
		    int resCode = conn.getResponseCode();
		    Log.i(TAG, "post request resCode : " + resCode);
		    switch (resCode) {
				case 200: {
					is = conn.getInputStream();
				}
					break;
				case 404:
					Log.i(TAG, "post request go back 404 ");
					break;
				default:
					break;
			}
		}catch (IOException e) {
			Log.i(TAG, "IOException : " + e.toString());
			throw e;
		}catch (Exception e){
			Log.i(TAG, "Exception : "+e.toString());
		}
		return is;
	}
    
	/**
	 *
	 * post请求,无文件长度大小限制
	 * @param urlStr
	 * @param map
	 * @return
	 * @throws Exception
	 * @author jjj
	 * @see
	 */
	public static String sendPostRequest(String urlStr, String postContent) throws IOException {
		Log.i(TAG, "urlStr : " + urlStr + ", post content is:" + postContent);
		String result = "";
		StringBuilder sBuilder = new StringBuilder(postContent);
		// 下面的Content-Length: 是这个URL的二进制数据长度
		byte entitydata[] = sBuilder.toString().getBytes();

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// 设定请求的方法
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setConnectTimeout(TIMEOUT_MILLILS);
			conn.setReadTimeout(TIMEOUT_MILLILS);

			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此必须设置允许对外输出数据, 默认情况下是false;
			conn.setDoOutput(true);
			//conn.setDoInput(true);
			//conn.setUseCaches(false);
			conn.setRequestProperty("SOAPAction", "\"\"");
			conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));// 长度
			conn.setRequestProperty("Content-Type", "text/xml; Charset=UTF-8");// 内容类型

			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream(),"UTF-8"));
			out.write(postContent);
			out.flush();
			out.close();

			// 状态码是不成功
		    int resCode = conn.getResponseCode();
		    Log.i(TAG, "post request resCode : " + resCode);
		    switch (resCode) {
				case 200: {
					StringBuffer sendBack = new StringBuffer();
					InputStream is = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(is,"UTF-8");
					BufferedReader br = new BufferedReader(isr);
					String s = "";
					while ((s = br.readLine()) != null) {
						sendBack.append(s);
					}

					result = sendBack.toString();
				}
					break;
				case 404:
					Log.i(TAG, "post request go back 404 ");
					return "";
				default:
					break;
			}
		}catch (IOException e) {
			Log.i(TAG, "IOException : " + e.toString());
			throw e;
		}catch (Exception e){
			Log.i(TAG, "Exception : "+e.toString());
		}

		return result;
	}

	/**
	 * @param path
	 * @param params
	 * @param enc  编码方式
	 * @return response from server
	 * @throws Exception
	 * @author jjj
	 * @see
	 */
	public static String sendRequestFromHttpClient(String path, Map<String, String> params,
			String enc)
		throws Exception {
		String goBackData = "";
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params == null || params.isEmpty()) {
			goBackData = "";
		}
		for (Map.Entry<String, String> entry : params.entrySet()) {
			paramPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		// 得到经过编码过后的实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs, enc);
		HttpPost post = new HttpPost(path); // form
		post.setEntity(entitydata);
		DefaultHttpClient client = new DefaultHttpClient(); // 浏览器
		HttpResponse response = client.execute(post);// 执行请求
		int repCode = response.getStatusLine().getStatusCode();
	    Log.i(TAG, "sendRequestFromHttpClient request repCode : " + repCode);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				InputStream in = httpEntity.getContent();
				goBackData = IoUtil.inputStreamToStringByBufferedReader(in, enc);
			}
		}
		return goBackData;
	}

	/**
	 * @return -1：disable 0：mobile available 1：WiFi mode 2：unknown mode
	 */
	public static int isNetworkEnabled(Context context) {
		int status = -1;
		if(context == null) {
			return status;
		}

		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager == null) {
			return status;
		}
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			switch (networkInfo.getType()) {
				case ConnectivityManager.TYPE_MOBILE: { // mobile mode
					status = 0;
					break;
				}
				case ConnectivityManager.TYPE_WIFI: { // WiFi mode
					status = 1;
					break;
				}
				default: {
					status = 2;
				}
			}
		}
		Log.i(TAG, "isNetworkEnabled, net:" +  status);
		return status;
	}
	
	
	/*
     * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
     * 这里的path是图片的地址
     */
    public static Uri getImageURI(String path, File cache, String snapName) throws Exception {
        File file = new File(cache, snapName);
        // 如果图片存在本地缓存目录，则不去服务器下载 
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }
	
	public static ProbeMatches probeOnvifDevice(byte[] sData,Handler mWorkHandler) {
		Log.i(TAG, "probeOnvifDevice begin");
		if (sData == null) {
			Log.i(TAG, "probeOnvifDevice param is null!");
			return null;
		}
		
		ProbeMatches pms = new ProbeMatches();
		
		InetAddress group = null;
		try {
			group = InetAddress.getByName("239.255.255.250"); // 组播地址  
		} catch (UnknownHostException e1) {
			Log.i(TAG, e1.toString());
			return null;
		}
        int port = 3702; // 端口  
        MulticastSocket mss = null;  
        try {
            mss = new MulticastSocket(port); // 1.创建一个用于发送和接收的MulticastSocket组播套接字对象
            mss.setSoTimeout((int)Time_Constant.Delayed_Seconds_Ten);
            mss.joinGroup(group); // 3.使用组播套接字joinGroup(),将其加入到一个组播 
            /*while (true) {
            	Log.i(TAG, "发送组播数据包！(发送时间:" + new java.util.Date() + ")");
                Log.i(TAG, "发送数据包给" + group + ":" + port); 
                DatagramPacket dp = new DatagramPacket(sData, sData.length, group, port);  
                mss.send(dp); // 4.使用组播套接字的send()方法，将组播数据包对象放入其中，发送组播数据包  
                
            	byte[] buffer = new byte[8192];                
                DatagramPacket rData = new DatagramPacket(buffer, buffer.length); // 2.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象  
                mss.receive(rData);
                String s = new String(rData.getData(), 0, rData.getLength()); //5.解码组播数据包提取信息，并依据得到的信息作出响应  
                //Log.i(TAG, "收到数据包后转字符串, s:" + s);
            	XmlParser mXmlparser = new XmlParser();
            	DiscoveryResponse sht = mXmlparser.readResponseFromSoapXml(s);
            	//Log.i(TAG, sht.getSoapHeaderTag().toString());
            	//Log.i(TAG, sht.getSoapBodyTag().toString());
                try {
					Thread.sleep(1000 * 10);// for test
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                String deviceAccess = sht.getProbeMatch().getXAddrs();
                if (deviceAccess != null) {
                	Log.i(TAG, "deviceAcess:" + deviceAccess);
                	return deviceAccess;                	
                }
            }*/
            Log.i(TAG, "发送组播数据包！(发送时间:" + new java.util.Date() + ")");
            Log.i(TAG, "发送数据包给" + group + ":" + port); 
            DatagramPacket dp = new DatagramPacket(sData, sData.length, group, port);  
            mss.send(dp); // 4.使用组播套接字的send()方法，将组播数据包对象放入其中，发送组播数据包  
            
            Log.i(TAG, "probeOnvifDevice, prepare to receive");
			long begin = new Date().getTime();
            do {
            	byte[] buffer = new byte[8192];                
                DatagramPacket rData = new DatagramPacket(buffer, buffer.length); // 2.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象  
                mss.receive(rData);
                
                
                String s = new String(rData.getData(), 0, rData.getLength()); //5.解码组播数据包提取信息，并依据得到的信息作出响应  
                //Log.i(TAG, "收到数据包后转字符串, s:" + s);
            	XmlParser mXmlparser = new XmlParser();
            	DiscoveryResponse response = mXmlparser.readResponseFromSoapXml(s);

            	String deviceAccess = response.getProbeMatch().getXAddrs();
                if (deviceAccess != null) {
                	Log.i(TAG, "probeOnvifDevice, deviceAccess:" + deviceAccess);
                	if (!pms.getProbeMatchList().contains(response.getProbeMatch())) {
                		pms.getProbeMatchList().add(response.getProbeMatch());
                		Message msg = new Message();
                		msg.what = 1;
                		Bundle bundle = new Bundle();
                		bundle.putString("xAddress", response.getProbeMatch().getXAddrs());
                		msg.setData(bundle);
                		mWorkHandler.sendMessage(msg);
                	}
                }
			} while (new Date().getTime() - begin < Time_Constant.Delayed_Seconds_Ten);
        } catch (SocketTimeoutException e) {
			Log.i(TAG, "probeOnvifDevice, SocketTimeoutException");
			return pms;    
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (mss != null) {  
                try {  
                    mss.leaveGroup(group); // 7.使用组播套接字的leaveGroup()方法，离开组播组  
                    mss.close(); // 关闭组播套接字  
                } catch (IOException e) {  
                }  
            }  
        }
        Log.i(TAG, "probeOnvifDevice end");
        return pms;
	}
	
	public  static void deviceProbe(String addr){//disabled
		/*POST /onvif/Media HTTP/1.1
Content-Type: application/soap+xml; charset=utf-8; action="http://www.onvif.org/ver10/media/wsdl/GetStreamUri"
Host: 10.0.0.97:8899
Content-Length: 485
Accept-Encoding: gzip, deflate
Connection: Close*/
		/*HTTP/1.1 200 OK
		 * Server: gSOAP/2.7
		 * Content-Type: application/soap+xml; charset=utf-8
		 * Content-Length: 2887
		 * Connection: close*/
		String action="http://www.onvif.org/ver10/media/wsdl/GetStreamUri";
		String nameSpace = "http://www.onvif.org/ver10/media/wsdl";
		String methodName = "GetStreamUri";
		String url = "http://10.0.0.97:8899";
		
		SoapClient client = new SoapClient(action, methodName, nameSpace, url, false);//构建SoapClient客户端
		//client.addParameter("connection", "close");
		//client.addParameter("Content-Type", "application/soap+xml");
		//client.addParameter("action", "http://www.onvif.org/ver10/media/wsdl/GetStreamUri");
		client.addParameter("Stream", "RTP-Unicast");
		client.addParameter("Protocol", "RTSP");
		client.addParameter("ProfileToken", "000");
        
		try {			
			String sb= client.executeCallResponse().toString();

			Log.e("SoapFault","login, sb:" + sb);
		}catch (SoapFault e){
			Log.e("SoapFault","访问错误");
		}catch (Exception e) {
			Log.e("Exception","网络异常");
			e.printStackTrace();	
		
		} 	
		return;	
    }
	
	/**
	 * 获取本机ip地址
	 * @return 返回本机ip
	 * @author jjj
	 */
	public static String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ip
                                    .getHostAddress())) {
                        ipaddress = ip.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.e(TAG, "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;
    }

	/**
	 * 获取本机mac地址
	 * @return 返回本机mac
	 * @author jjj
	 */
	public static String getLocalHostMac() {
        String mac = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ip
                                    .getHostAddress())) {
                    	return byte2hex(nif.getHardwareAddress());
                    }
                }

            }
        } catch (SocketException e) {
            Log.e(TAG, "获取本地ip地址失败");
            e.printStackTrace();
        }
        return mac;
    }
	
    // 使用无线网络时, 得到本机Mac地址
    public static String getLocalMacByWifi(Context mContext) {
    	Log.i(TAG, "getLocalMacByWifi, param mContext:" + mContext);
		if (!ObjectCheck.validObject(mContext)) {
			return "";
		}
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        Log.i(TAG, "getLocalMacByWifi, my box mac:" + wifiInfor.getMacAddress());
        return wifiInfor.getMacAddress();
    }
    
    public static  String byte2hex(byte[] b) {
    	StringBuffer hs = new StringBuffer(b.length);
    	String stmp = "";
    	int len = b.length;
    	for (int n = 0; n < len; n++) {
    		stmp = Integer.toHexString(b[n] & 0xFF);
    		if(stmp.length() == 1) {
    			hs = hs.append("0").append(stmp);
    		}else {
    			hs = hs.append(stmp);
    		}
    	}
    	return String.valueOf(hs);
    }
}