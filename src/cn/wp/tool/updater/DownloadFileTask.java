package cn.wp.tool.updater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.wp.tool.data.operation.DownloadAppOperation;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadFileTask extends AsyncTask<String, Integer, Boolean> {

	private static final String TAG = DownloadFileTask.class.getSimpleName();
	
	private String mAppName;
	private String mDownloadURL;
	private String mSavePath;
	private Handler reportHandler;
    public DownloadFileTask(String savePath, String appName, String downloadURL,Handler handle) {
    	mSavePath = savePath;
    	mAppName = appName;
    	mDownloadURL = downloadURL;
    	reportHandler = handle;
    } 
    
    public String getAppName(){
    	return mAppName;
    }
    
    public String getSavePath(){
    	return mSavePath;
    }
	private void reportProgress(Handler reportHandler,int pro,int CMD){
		Log.i(TAG, "reportProgress." + pro);
		Message msg = new Message();
		msg.what = CMD;
		
		if(pro != -1){
			Bundle bundle = new Bundle();
			bundle.putInt(Updater.DOWNLOAD_PROGRESS, pro);
			msg.setData(bundle);
		}
			
		reportHandler.sendMessage(msg);
	}
	@SuppressLint("NewApi") @Override
	protected Boolean doInBackground(String... args) {
		
		try {
			Log.i(TAG, "start download file:" + mAppName + 
					"mSavePath:" + mSavePath + ", mDownloadURL:" + mDownloadURL);
			
			URL url = new URL(mDownloadURL);
			
			URLConnection connection = url.openConnection();
	    	connection.setRequestProperty("connection", "close");
	    	connection.connect();

	    	long lenghtOfFile = connection.getContentLength();
	    	
	    	File dir = new File(mSavePath);
	    	if(dir.exists() == false){
	    		dir.mkdirs();
	    	}
	    	else if(dir.isDirectory() ==false){
	    		dir.delete();
	    		dir.mkdirs();
	    	}
	    	
	    	File file = new File(mSavePath, mAppName);

	    	InputStream input = new BufferedInputStream(connection.getInputStream());
	    	OutputStream output = new FileOutputStream(file);

	    	byte data[] = new byte[1024];
	    	int count = 0;
	    	long total = 0;
	    	int report = 1;
	    	while ((count = input.read(data)) != -1) {
	    		total += count;
	    		Log.i(TAG, ">>>>>>>>>>>>>" + total + ">>>>>>>" + lenghtOfFile);
	    		if(((double)total/lenghtOfFile) > (report * 0.1)){
	    			reportProgress(reportHandler,report * 10,DownloadAppOperation.WORK_HANDLER_EVENT_REPORT_PROGRESS);
	    			report += report;
	    		}
	    		output.write(data, 0, count);
	    	}
	    	
	    	output.flush();
	    	
	    	file.setReadable(true, false);
	    	file.setWritable(true, false);
	    	file.setExecutable(true, false);
	    	
	    	output.close();
	    	input.close();
	    	
	    	Log.i(TAG, "Download Finished Size(" + total + "/" + lenghtOfFile +")");
	    	reportProgress(reportHandler,-1,DownloadAppOperation.WORK_HANDLER_EVENT_DOWNLOAD_FINISHED);
	    	return (total >= lenghtOfFile);
			
		} catch (Exception e) {
			reportProgress(reportHandler,-1,DownloadAppOperation.WORK_HANDLER_EVENT_DOWNLOAD_ERROR);
			Log.i(TAG, "Catch Exception:"+e.getMessage());
		}
		
    	return false;
	}

}
