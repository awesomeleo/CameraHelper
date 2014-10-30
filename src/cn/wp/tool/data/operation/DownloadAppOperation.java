package cn.wp.tool.data.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import cn.wp.device.camera.CameraManager;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.updater.Updater;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class DownloadAppOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.downloadApp";
	private final String TAG = DownloadAppOperation.class.getSimpleName();
	private HandlerThread mWorkHandlerThread = null;
	private Handler mWorkHandler = null;
	private Updater mUpdater = null;
	private Context  mContext = null;
	public static final int WORK_HANDLER_EVENT_DOWNLOAD_APK = 1;
	public static final int WORK_HANDLER_EVENT_REPORT_PROGRESS = 2;
	public static final int WORK_HANDLER_EVENT_DOWNLOAD_FINISHED = 3;
	public static final int WORK_HANDLER_EVENT_DOWNLOAD_ERROR = 4;
	private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {
		//@Override
		public boolean handleMessage(Message msg) {
			Log.i(TAG, "handleMessage what:" + msg.what); 
			switch (msg.what) {
			case WORK_HANDLER_EVENT_DOWNLOAD_APK:{
				mUpdater.downloadApk(mWorkHandler,mContext);
				break;
			}
			case WORK_HANDLER_EVENT_REPORT_PROGRESS:{
				int report = msg.getData().getInt(Updater.DOWNLOAD_PROGRESS);
				Intent intent=new Intent();
				intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.REPORT_DOWNLOAD_PROGRESS);
				intent.putExtra(Updater.DOWNLOAD_PROGRESS, report);
			    intent.setAction("cn.wp.tool.status");
			    mContext.sendBroadcast(intent);
				break;
			}
			case WORK_HANDLER_EVENT_DOWNLOAD_FINISHED:{
				Intent intent=new Intent();
				intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.DOWNLOAD_FINISHED);
			    intent.setAction("cn.wp.tool.status");
			    mContext.sendBroadcast(intent);
				mUpdater.installApk();
				break;
			}
			case WORK_HANDLER_EVENT_DOWNLOAD_ERROR:{
				Intent intent=new Intent();
				intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.DOWNLOAD_ERROR);
			    intent.setAction("cn.wp.tool.status");
			    mContext.sendBroadcast(intent);
				break;
			}
			default:
				break;
			}
			return false;
		}
	};
	public DownloadAppOperation(Updater updater){
		mUpdater = updater;
		mWorkHandlerThread = new HandlerThread(CameraManager.class.getName());
		mWorkHandlerThread.start();
		mWorkHandler = new Handler(mWorkHandlerThread.getLooper(), mWorkHandlerCallback);
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		Log.i(TAG, "execute");
		mContext = context;
		mWorkHandler.sendEmptyMessage(WORK_HANDLER_EVENT_DOWNLOAD_APK);
		
		return null;
	}

}
