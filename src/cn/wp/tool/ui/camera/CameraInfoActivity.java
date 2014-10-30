package cn.wp.tool.ui.camera;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.R;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.model.CameraNetInfo;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.OnvifCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import cn.wp.tool.provider.CameraListProvider;
import cn.wp.tool.ui.DataDroidActivity;
import cn.wp.tool.ui.devicelist.DeviceListActivity;

public class CameraInfoActivity extends DataDroidActivity 
																	implements ActionBar.OnNavigationListener, RequestListener, 
																								ConnectionErrorDialogListener{
	private final String TAG = CameraInfoActivity.class.getSimpleName();
	public static final String TRANCMD = "cameraIp";
	private TextView sn = null;
	private TextView ip = null;
	private TextView name = null;
	private TextView mac = null;
	private TextView net = null;
	private TextView rssi = null;
	private Button scanWifi = null;
	private Button getCameraVideo = null;
	private IPCamera camera = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_camera_info);
        if(this.initData()){
        	this.initUI();
        }
    }
    
    private boolean initData(){
    	Log.i(TAG, "initData");
    	boolean initDataStatus = false;
    	Intent intent  = getIntent();
    	String ip = intent.getStringExtra(TRANCMD);
    	if(!ObjectCheck.validString(ip)){
			Toast.makeText(CameraInfoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
			return initDataStatus;
    	}
    	Cursor cursor = null;
    	cursor = this.getContentResolver().query(CameraListProvider.CONTENT_URI, null, 
    				Defination.CameraTableColum.IP.getName() + "=?", new String []{ip}, null);
    	if(!ObjectCheck.validObject(cursor)){
			Toast.makeText(CameraInfoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
			return initDataStatus;
    	}
    	if(cursor.moveToFirst() == false){
			Toast.makeText(CameraInfoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
			return initDataStatus;
    	}
    	camera = new OnvifCamera();
    	String cursorSn = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.SN.getName()));
    	String cursorName = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.NAME.getName()));
    	String cursorMac = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.MAC.getName()));
    	String cursorNetType = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.NETTYPE.getName()));
    	String cursorSsid = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.SSID.getName()));
    	String cursorRssi = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.RSSI.getName()));
    	String cursorBaseUrl = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.BASEURL.getName()));
    	int cursorWifiStatus = cursor.getInt(cursor  
                .getColumnIndex(Defination.CameraTableColum.WIFI.getName()));
    	String cursorRtsp = cursor.getString(cursor  
                .getColumnIndex(Defination.CameraTableColum.RTSP.getName()));
    	CameraNetInfo cameraNet = new CameraNetInfo();
    	CameraWifi  mywifi= new CameraWifi();
    	cameraNet.setIp(ip);
    	
    	if(ObjectCheck.validString(cursorBaseUrl))
    		camera.setmBaseAccesssURL(cursorBaseUrl);
    	
    	if(ObjectCheck.validString(cursorSn))
    		camera.setName(cursorSn);
    	
    	if(ObjectCheck.validString(cursorRtsp))
    		camera.setRtspLiveStringURL(cursorRtsp);
    	
    	if(ObjectCheck.validString(cursorMac))
    		cameraNet.setMac(cursorMac);
    	
    	if(ObjectCheck.validString(cursorName))
    		cameraNet.setCameraNetName(cursorName);
    	
    	if(ObjectCheck.validString(cursorNetType))
    		if(cursorNetType.equals("WirelessLAN") || (cursorWifiStatus == 1))
    		{
    			if(ObjectCheck.validString(cursorSsid))
    				mywifi.setSsId(cursorSsid);
    			if(ObjectCheck.validString(cursorRssi))
    				mywifi.setRssi(Integer.parseInt(cursorRssi));
    			mywifi.setWifiOn(true);
    		}
    	cameraNet.setMyWifi(mywifi);
    	camera.setNetInfo(cameraNet);
    	return true;
    }
    
    private void startVideoPage(){
    	Log.i(TAG, "startVideoPage");
		Intent intent = new Intent();
		intent.setClass(CameraInfoActivity.this, CameraVideoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(CameraVideoActivity.TRANCMD, camera);
		intent.putExtras(bundle);
		startActivity(intent);
    }
    
    private void initUI(){
    	sn = (TextView) this.findViewById(R.id.cameraSn);
    	name = (TextView) this.findViewById(R.id.cameraName);
    	ip = (TextView) this.findViewById(R.id.cameraIp);
    	mac = (TextView) this.findViewById(R.id.cameraMAC);
    	net = (TextView) this.findViewById(R.id.cameraNet);
    	rssi = (TextView) this.findViewById(R.id.cameraNetRssi);
    	scanWifi = (Button) this.findViewById(R.id.scanWifi);
    	getCameraVideo = (Button) this.findViewById(R.id.getCameraVideo);
    	
    	scanWifi.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View arg0) {
 				CameraInfoActivity.this.scanWifi();
 			}
         });
    	
    	getCameraVideo.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View arg0) {
 				CameraInfoActivity.this.startVideoPage();
 			}
         });	
    	
    	if(!ObjectCheck.validObject(camera)){
    		return;
    	}
    	if(ObjectCheck.validString(camera.getName())){
    		sn.setText(camera.getName());
    	}
    	CameraNetInfo cameraNet = camera.getNetInfo();
    	if(ObjectCheck.validObject(cameraNet)){
        	if(ObjectCheck.validString(cameraNet.getIp())){
        		ip.setText(cameraNet.getIp());
        	}
        	
        	if(ObjectCheck.validString(cameraNet.getMac())){
        		mac.setText(cameraNet.getMac());
        	}
        	
        	if(ObjectCheck.validString(cameraNet.getCameraNetName())){
        		name.setText(cameraNet.getCameraNetName());
        	}
        	
        	CameraWifi myWifi = cameraNet.getMyWifi();
        	if(ObjectCheck.validObject(myWifi)){
        		if(myWifi.isWifiOn()){
        			net.setText(myWifi.getSsId());
        			if(myWifi.getRssi() != 0){
        				rssi.setText(String.valueOf(myWifi.getRssi()));
        			}
        		}
        	}
    	}
    }
  
    private void scanWifi(){
    	Log.i(TAG, "scanWifi");
    	if(ObjectCheck.validObject(camera)){
			Intent intent = new Intent();
			intent.setClass(CameraInfoActivity.this, WifiListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable(WifiListActivity.TRANCMD, camera);
			intent.putExtras(bundle);
			startActivityForResult(intent,1);       
    	}
    }
    private void rebootCamera(){
    	rebootDialog();
    }

    
	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            int choose = resultData.getInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE);
            switch(choose){
            case ToolRequestFactory.REQUEST_REBOOT_CAMERA:
            	boolean rebootStatus = resultData.getBoolean(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_REBOOT);
            	if(rebootStatus)
            		Toast.makeText(getApplicationContext(), 
    		    			getString(R.string.reboottingSucceed), Toast.LENGTH_SHORT).show();
            	else
            		Toast.makeText(getApplicationContext(), 
    		    			getString(R.string.reboottingFailed), Toast.LENGTH_SHORT).show();
            	break;
            }
        }
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.rebootCamera:
			rebootCamera();
			break;
		}
		return super.onOptionsItemSelected(item);
    }
    private void rebootDialog() {
    	AlertDialog.Builder builder = new Builder(CameraInfoActivity.this);
    	builder.setMessage(getString(R.string.sureReboot));
    	builder.setTitle(getString(R.string.reboot_camera));
    	builder.setPositiveButton(getString(R.string.enterButton), new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(!ObjectCheck.validObject(camera.getmBaseAccesssURL())){
			    	Toast.makeText(getApplicationContext(), 
			    			getString(R.string.invalidInfo), Toast.LENGTH_SHORT).show();
					return;
				}
		        Request request = ToolRequestFactory.rebootCamera(camera.getmBaseAccesssURL());
		        mRequestManager.execute(request, CameraInfoActivity.this);
		        mRequestList.add(request);
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.rebootting), Toast.LENGTH_SHORT).show();
			}
    	});
    	builder.setNegativeButton(getString(R.string.cancelButton), new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				
			}
    	});
      	builder.create().show();
	}
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data);  
        
        if(requestCode == 1 && resultCode == RESULT_OK){
        	IPCamera cameraWifi = null;
        	cameraWifi = data.getParcelableExtra("camera");
        	if(ObjectCheck.validObject(cameraWifi)){
        		if(ObjectCheck.validObject(cameraWifi.getNetInfo().getMyWifi()))
        			if(cameraWifi.getNetInfo().getMyWifi().isWifiOn()){
        				camera = cameraWifi;
						if(cameraWifi.isNeedReboot() == 1)
							camera.setNeedReboot(1);
        				net.setText(camera.getNetInfo().getMyWifi().getSsId());
//        				rssi.setText(camera.getNetInfo().getMyWifi().getRssi());
        			}
        	}
        }
        
    }  
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	
            Intent intent=new Intent();  
            intent.putExtra("camera", camera);
            setResult(RESULT_OK, intent);  
            CameraInfoActivity.this.finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
     }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camera_info, menu);
         return super.onCreateOptionsMenu(menu); 
    }
	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		return false;
	}

	@Override
	public void onRequestConnectionError(Request request, int arg1) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            ConnectionErrorDialogFragment.show(this, request, this);
        }
	}

	@Override
	public void onRequestCustomError(Request arg0, Bundle arg1) {
		 Log.i(TAG, "onRequestCustomError," + "7777777777777777777777777777777777777777777777777777777");
	}

	@Override
	public void onRequestDataError(Request request) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            showBadDataErrorDialog();
        }
	}

	@Override
	public void connectionErrorDialogCancel(Request request) {
		Log.i(TAG, "connectionErrorDialogCancel," + "7777777777777777777777777777777777777777777777777777777");
	}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		Log.i(TAG, "connectionErrorDialogRetry," + "7777777777777777777777777777777777777777777777777777777");
	}
}
