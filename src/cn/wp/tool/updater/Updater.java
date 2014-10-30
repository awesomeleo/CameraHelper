package cn.wp.tool.updater;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import cn.wp.device.camera.utils.NetWorkUtil;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.device.camera.utils.UserData;
import cn.wp.tool.data.model.AppInfo;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Updater {
	private static final String TAG = Updater.class.getSimpleName();
	private static final String BASE_SERVER_ADDRESS = "http://cos.myqcloud.com/1000967/update/";
	private static final String JSON_ADDRESS_APP            = BASE_SERVER_ADDRESS + "wptool_update.json";
	public static final String DOWNLOAD_PROGRESS = "DownLoadApk";
	public static final String UPDATE_CMD_INSTALL      = "install";
	public static final String UPDATE_CMD_UNINSTALL    = "uninstall";
	public static final String UPDATE_CMD_FORCE_UPDATE = "forceUpdate";
	private Context mContext = null;
	private static Gson gson = new Gson();
	private AppInfo appInfoFromServer = new AppInfo();
	private AppInfo myInfo = new AppInfo();
	private boolean newVersion = false;
	private String mSavePath = "";
	
	public Updater(Context context){
		mContext = context;
		getAppData();
	}
	private void getAppData(){
		if(!ObjectCheck.validObject(mContext)){
			Log.i(TAG, "getAppData, param is null!");
			return;
		}
		 try {
	            SAXParserFactory spf = SAXParserFactory.newInstance();
	            InputStream is = mContext.getAssets().open("appinfo.xml");
	            javax.xml.parsers.SAXParser sp = spf.newSAXParser();
	            sp.parse(is, new DefaultHandler() {
	                private String elementName;
	                @Override
	                public void characters(char[] ch, int start, int length)
	                        throws SAXException {
	                    String value = new String(ch, start, length);
	                     if(UserData.TableAppDataColumns.pkgName.equals(elementName)){
	                    	myInfo.setPkgName(value);
	                    }else if(UserData.TableAppDataColumns.versionCode.equals(elementName)){
	                    	myInfo.setVersionCode(Double.parseDouble(value));
	                    }
	                }

	                @Override
	                public void startElement(String uri, String localName,
	                        String qName, Attributes attributes)
	                        throws SAXException {
	                    elementName = localName;
	                }

	                @Override
	                public void endElement(String arg0, String arg1, String arg2)
	                        throws SAXException {
	                    elementName = "";
	                }
	            });
	        } catch (Throwable e) {
	        	Log.e(TAG, e.toString());
	        }
	}
	
	public void checkUpdate(){
		Log.i(TAG, "checkUpdate");
		String resultString = "";
		try {
			resultString = NetWorkUtil.sendGetRequest(JSON_ADDRESS_APP, null, mContext);
		} catch (Exception e) {
			Log.i(TAG, "net work exception" + e);
		}
		if(!ObjectCheck.validString(resultString)){
			Log.i(TAG, "checkUpdate resultString is invalid:" + resultString);
			return;
		}
		if(!ObjectCheck.validObject(myInfo)){
			Log.i(TAG, "checkUpdate myappinfo is invalid" );
			return;
		}else{
			Log.i(TAG, "my app info: " + 
							myInfo.getPkgName() + ":" + myInfo.getVersionCode() );
		}
		try {
			appInfoFromServer = gson.fromJson(resultString, AppInfo.class);
			
			if(!ObjectCheck.validObject(appInfoFromServer)){
				Log.i(TAG, "checkUpdate json format for AppInfo failed:" + resultString);
				return;
			}
		} catch (JsonSyntaxException e) {
			Log.w(TAG, "json format error." + e.getMessage() + "\n" + resultString);
			return;
		}
		Log.i(TAG, "app info from server:" + appInfoFromServer.getPkgName() 
						+ ":" + appInfoFromServer.getVersionCode() + ":"
						 + appInfoFromServer.getDownloadURI());	
		if(appInfoFromServer.getPkgName().equals(myInfo.getPkgName())){
			if(appInfoFromServer.getVersionCode() > myInfo.getVersionCode()){
				newVersion = true;
			}
		}
	}
	public boolean hasNewVersion(){
		return newVersion;
	}
	
	public void downloadApk(Handler reportHandler,Context context){
        mSavePath = context.getFilesDir().getAbsolutePath();
		DownloadFileTask download = new DownloadFileTask(mSavePath,
				appInfoFromServer.getAppName(),appInfoFromServer.getDownloadURI(),reportHandler);
		download.execute(new String());
	}
	

	
	public void installApk()  
    {  
        File apkfile = new File(mSavePath,appInfoFromServer.getAppName() );  
        if (!apkfile.exists())  
        {  
            return;  
        }  
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");  
        mContext.startActivity(i);  
    }  
}
