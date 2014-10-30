package cn.wp.tool.data.operation;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import cn.wp.device.camera.CameraManager;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.device.camera.utils.PingHelper;
import cn.wp.device.camera.utils.PingHelper.PendingListener;
import cn.wp.device.camera.utils.PingHelper.PendingValue;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.provider.CameraListProvider;
import cn.wp.tool.ui.devicelist.DeviceListActivity;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class PingNetStatusOperation implements Operation{
	public static final String PARAM_METHOD = "cn.wp.tool.extra.pingStatus";
	private final String TAG = PingNetStatusOperation.class.getSimpleName();
	private Context mContext = null;
	private PingHelper pHelper = null;
	private HandlerThread mWorkHandlerThread = null;
	private Handler mWorkHandler = null;
	private String gateWay = "";
	private int DELAY_TIME = 3*1000;
	public static final int WORK_HANDLER_INIT_PING = 0;
	public static final int WORk_HANDLER_PING_GATEWAY = 1;
	public static final int WORK_HANDLER_PING_CAMEA = 2;
	private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {
		//@Override
		public boolean handleMessage(Message msg) {
			Log.i(TAG, "handleMessage what:" + msg.what); 
			switch (msg.what) {
			case WORK_HANDLER_INIT_PING:{
				initPingHelper();
			break;
			}
			case WORk_HANDLER_PING_GATEWAY:{
				if(ObjectCheck.validString(gateWay)){
					pHelper.addTask(gateWay);
				}
				mWorkHandler.sendEmptyMessageDelayed(WORk_HANDLER_PING_GATEWAY, DELAY_TIME);
				break;
			}
			case WORK_HANDLER_PING_CAMEA:{
				pingCamera();
				mWorkHandler.sendEmptyMessageDelayed(WORK_HANDLER_PING_CAMEA, DELAY_TIME);
				break;
			}
			}
			return true;
		}
	};
	public PingNetStatusOperation(){
		mWorkHandlerThread = new HandlerThread(CameraManager.class.getName());
		mWorkHandlerThread.start();
		mWorkHandler = new Handler(mWorkHandlerThread.getLooper(), mWorkHandlerCallback);
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Log.i(TAG, "execute");
		mContext = context;
		mWorkHandler.sendEmptyMessage(WORK_HANDLER_INIT_PING);
		return null;
	}
	
	private void initPingHelper(){
		pHelper = PingHelper.getInstance();
		getGateWay(mContext);
		pHelper.setListener(new PendingListener(){
			@Override
			public void PendValue(int eventType, PendingValue value) {
				if(ObjectCheck.validObject(value)){
					String host = value.host;
					double averageTime = value.lastAverageTime;
					DecimalFormat    df   = new DecimalFormat("######0.000");  
					String delay = df.format(averageTime);
					sendMessageToDeviceList(host,delay,value.isReachable);
				}
			}
		});
		mWorkHandler.sendEmptyMessage(WORk_HANDLER_PING_GATEWAY);
		mWorkHandler.sendEmptyMessage(WORK_HANDLER_PING_CAMEA);
	}

	private void pingCamera(){
		Cursor cursor = mContext.getContentResolver().query(CameraListProvider.CONTENT_URI,
    			null, null, null, null);
		if(!ObjectCheck.validObject(cursor)){
			return;
		}
		while(cursor.moveToNext()){
			String ip = cursor.getString(cursor.getColumnIndex(Defination.CameraTableColum.IP.getName()));
			if(ObjectCheck.validString(ip)){
				pHelper.addTask(ip);
			}
		}
	}
	private void getGateWay(Context context) {  
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> inet = nif.getInetAddresses();
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
            Log.e(TAG, "get local ip error" + e.toString());
        }
    	int lastDotIndex = ipaddress.lastIndexOf(".");
    	if (lastDotIndex > 0){
    		gateWay = ipaddress.substring(0, lastDotIndex) + ".1";
    	}
	}
	private void sendMessageToDeviceList(String ip,String delay,boolean isOnline){
		if(!ObjectCheck.validObject(mContext))
			return;
		if(gateWay.equals(ip)){
			Intent intent=new Intent();
			intent.putExtra(Defination.Cmd_Type.CMD, Defination.Cmd_Type.UPDATE_DELAY);
			intent.putExtra(DeviceListActivity.DELAY_IP, 0);
			intent.putExtra(DeviceListActivity.DELAY_DATA, delay);
		    intent.setAction("cn.wp.tool.status");
		    mContext.sendBroadcast(intent);
		}else{
			ContentValues values = new ContentValues(); 
			if(!isOnline){
				delay = "0";
			}
			values.put(Defination.CameraTableColum.DELAY.getName(), delay);
			values.put(Defination.CameraTableColum.ONLINE.getName(), isOnline);
			mContext.getContentResolver().update(CameraListProvider.CONTENT_URI, 
										values, Defination.CameraTableColum.IP.getName()+"=?", new String[]{ip});
		}

	}
}
