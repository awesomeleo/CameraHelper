package cn.wp.tool.ui.devicelist;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;

import cn.wp.bl.BlManager;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.R;
import cn.wp.tool.data.config.Defination;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import cn.wp.tool.provider.CameraListProvider;
import cn.wp.tool.ui.DataDroidActivity;
import cn.wp.tool.ui.camera.CameraInfoActivity;
import cn.wp.tool.updater.Updater;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.ActionBar;

public class DeviceListActivity extends DataDroidActivity 
								implements ActionBar.OnNavigationListener, RequestListener, 
											ConnectionErrorDialogListener{

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
	private final String TAG = DeviceListActivity.class.getSimpleName();
	private ListView deviceList = null;
	private SimpleCursorAdapter cameraListAdapter = null;
	private Receiver receiver = null;
	private ProgressBar downloadP = null;
	private Dialog downloadDialog;  
	private CheckBox autoDhcp = null;
	private CheckBox HighQualityVideoStream = null;
	private SharedPreferences settingPre = null;
	private TextView onlineCameraNum = null;
	private TextView gatewayDelay = null;
	private boolean checkUpdateSwitch = true;
	private boolean openDhcpSwitch = false;
	private BlManager blManager = BlManager.getInstance();
	public static boolean highVideoStream = true;
	public static String DELAY_IP = "delay_ip";
	public static String DELAY_DATA = "delay_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device_list);
        setOverflowShowingAlways();
        this.initUI();
//        Intent intent = new Intent(getApplicationContext(),DemoService.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startService(intent);
        initPreferences();
        Request request = ToolRequestFactory.pingNetStatus();
        mRequestManager.execute(request, this);
        mRequestList.add(request);
        if(checkUpdateSwitch){
        	checkUpdate();
        	checkUpdateSwitch = false;
        }else{
        	this.scanCameras();
        }
    }
    private void refreshOnlineNum(){
    	Cursor cursor = this.getContentResolver().query(CameraListProvider.CONTENT_URI,
    			null, null, null, null);
    	if(ObjectCheck.validObject(cursor));
    	int num = cursor.getCount();
    	cursor.close();
    	String onNum = getString(R.string.online) + num;
    	onlineCameraNum.setText(onNum);
    }
    @SuppressWarnings("deprecation")
    private void initAdapter(){
    	Cursor cameraCursor = managedQuery(CameraListProvider.CONTENT_URI, null,null, null, null);
    	cameraListAdapter = new SimpleCursorAdapter(this, R.layout.activity_device_list_item,cameraCursor, 
        		new String[]{Defination.CameraTableColum.NAME.getName()
    						,Defination.CameraTableColum.IP.getName()
    						,Defination.CameraTableColum.SN.getName()
    						,Defination.CameraTableColum.DELAY.getName()}
    			,new  int []{R.id.name,R.id.ipaddr,R.id.snValue,R.id.ipDelay});
    	cameraListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

    		@Override
    		public boolean setViewValue(final View view,final Cursor cs, int columIndex) {
    			switch (view.getId()) {
    			case R.id.name:{
    				TextView cameraName  = (TextView) view;
    				String camraNetName = cs.getString(Defination.CameraTableColum.NAME.getIndex());
    				int isNeedReboot = -1;
    				String rebootStatus = "";
    				isNeedReboot = cs.getInt(Defination.CameraTableColum.REBOOT.getIndex());
    				if(isNeedReboot == 1)
    					rebootStatus = getString(R.string.waitForReboot);
    				if(!ObjectCheck.validString(camraNetName))
    					camraNetName = getString(R.string.unName);
    				cameraName.setText(camraNetName +rebootStatus );
    				
    				String online = cs.getString(Defination.CameraTableColum.ONLINE.getIndex());
    				if(ObjectCheck.validString(online)){
    					if(online.equals("0")){
    						cameraName.setTextColor(Color.rgb(255, 0, 0));
    					}else{
    						cameraName.setTextColor(Color.rgb(33, 66, 99));
    					}
    				}
    				return true;
    			}
    			case R.id.ipaddr:
    				TextView ipView  = (TextView) view;
    				String ip = cs.getString(Defination.CameraTableColum.IP.getIndex());
    				if(!ObjectCheck.validString(ip))
    					ip = getString(R.string.unrecognize);
    				ipView.setText(ip );
    				return true;
    			case R.id.ipDelay:{
    				TextView ipDelay = (TextView) view;
    				String cameraDelay = null;
    				cameraDelay = cs.getString(Defination.CameraTableColum.DELAY.getIndex());
    				if(ObjectCheck.validString(cameraDelay)){
    					ipDelay.setText(cameraDelay + getString(R.string.ms));
    				}else{
    					ipDelay.setText(getString(R.string.routeDelay));
    				}
    				
    				return true;
    			}
    			case R.id.snValue:{
    				TextView snView  = (TextView) view;
    				String sn = cs.getString(Defination.CameraTableColum.SN.getIndex());
    				if(!ObjectCheck.validString(sn))
    					sn = getString(R.string.unrecognize);
    				snView.setText(sn );
    				return true;
    			}
    			default:
    				break;
    			}
    			return false;
    		}
    		
    	});
    	deviceList.setAdapter(cameraListAdapter);
    }

    private void initUI(){
    	setProgressBarIndeterminateVisibility(false);
    	onlineCameraNum = (TextView) this.findViewById(R.id.onlineCameraNum);
    	gatewayDelay = (TextView) this.findViewById(R.id.routeDelay);
    	deviceList = (ListView) this.findViewById(R.id.deviceList);
    	 deviceList.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
 					long arg3) {
 				TextView ipView = (TextView) view.findViewById(R.id.ipaddr);
 				Intent intent = new Intent();
 				intent.setClass(DeviceListActivity.this, CameraInfoActivity.class);
 				Bundle bundle = new Bundle();
 				bundle.putString(CameraInfoActivity.TRANCMD, String.valueOf(ipView.getText()));
 				intent.putExtras(bundle);
 				startActivity(intent);
 			}
 		});
    	 initReceiver();
    	 this.initAdapter();
    }
    //0:get 1:post 2:put 3:delete
    private  void scanCameras(){
    	Toast.makeText(getApplicationContext(), 
    			getString(R.string.scanning), Toast.LENGTH_LONG).show();
        Request request = ToolRequestFactory.getCameraListRequest(openDhcpSwitch);
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    
    private void scanBox(){
    }
    private void exitApp(){
    	System.exit(0);
    }
    private void checkUpdate(){
    	Toast.makeText(getApplicationContext(), 
    			getString(R.string.checkUpdate), Toast.LENGTH_LONG).show();
    	Request request = ToolRequestFactory.checkUpdate();
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    private void downloadApk(){
        Request request = ToolRequestFactory.downloadApk();
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scanCamera:
			this.scanCameras();
			break;
		case R.id.scanBox:
			this.scanBox();
			break;
		case R.id.exitApp:
			this.exitApp();
		break;
		case R.id.setting:
			this.setting();
			break;
		}
		return super.onOptionsItemSelected(item);
    }
	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            int choose = resultData.getInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE);
            switch(choose){
            case ToolRequestFactory.REQUEST_CHECK_UPDATE:{
            	boolean hasNewVersion = false;
            	hasNewVersion = resultData.getBoolean(ToolRequestFactory.BUNDLE_EXTRA_CHECK_UPDATE);
            	if(hasNewVersion){
            		noticeUpdate();
            	}else{
            		this.scanCameras();
            	}
            	break;
            }
            
            }
        }
	}
	private void initPreferences(){
		settingPre =  getSharedPreferences(Defination.Shared_Preferences.SETTING, 0);
		if(settingPre != null){
			if(!settingPre.getBoolean(Defination.Shared_Preferences.AUTODHCP, false)){
				settingPre
				.edit()
				.putBoolean(Defination.Shared_Preferences.AUTODHCP,
						false).commit();
			}
			openDhcpSwitch = settingPre.getBoolean(Defination.Shared_Preferences.AUTODHCP, false);
		}
	}
	private void setPreferences(boolean autoDhcpOption){
		settingPre =  getSharedPreferences(Defination.Shared_Preferences.SETTING, 0);
		if(settingPre != null){
			settingPre
			.edit()
			.putBoolean(Defination.Shared_Preferences.AUTODHCP,
					autoDhcpOption).commit();
		}
	}
	private void setting(){
        AlertDialog.Builder builder = new Builder(DeviceListActivity.this);  
        builder.setTitle(R.string.setting);  
        final LayoutInflater inflater = LayoutInflater.from(DeviceListActivity.this);  
        View view = inflater.inflate(R.layout.setting, null);  
        autoDhcp = (CheckBox) view.findViewById(R.id.autoOpenDhcp);
        HighQualityVideoStream = (CheckBox) view.findViewById(R.id.highQualityVidoe);
        HighQualityVideoStream.setChecked(true);
        builder.setView(view);  
        if(openDhcpSwitch){
        	autoDhcp.setChecked(true);
        }else{
        	autoDhcp.setChecked(false);
        }
        autoDhcp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                if(isChecked){ 
                	openDhcpSwitch = true;
                	setPreferences(true);
                }else{
                	openDhcpSwitch = false;
                	setPreferences(false);
                }
            } 
        });
        HighQualityVideoStream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                if(isChecked){ 
                	highVideoStream = true;
                	Log.i(TAG, "high quality video stream");
                }else{
                	highVideoStream = false;
                	Log.i(TAG, "low quality video stream");
                }
            } 
        }); 
        builder.setNegativeButton(R.string.enterButton, new OnClickListener() {  
              
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                
            }  
        });  
        downloadDialog = builder.create();  
        downloadDialog.show();  
    	
	}
    private void noticeUpdate() {  
        AlertDialog.Builder builder = new Builder(DeviceListActivity.this);  
        builder.setTitle(R.string.appUpdate);  
        builder.setMessage(R.string.appUpdateNow);  
        builder.setPositiveButton(R.string.update, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				downloadApk();
				downloading();
				dialog.dismiss();
			}  
        });  
        builder.setNegativeButton(R.string.appUpdateLater, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
				DeviceListActivity.this.scanCameras();
			}
        });  
        Dialog noticeDialog = builder.create();  
        noticeDialog.show();  
    }  
    
    private void downloading() {  
        AlertDialog.Builder builder = new Builder(DeviceListActivity.this);  
        builder.setTitle(R.string.downloading);  
        final LayoutInflater inflater = LayoutInflater.from(DeviceListActivity.this);  
        View view = inflater.inflate(R.layout.progressbar, null);  
        downloadP = (ProgressBar) view.findViewById(R.id.downloading);    
        builder.setView(view);  
        builder.setNegativeButton(R.string.cancelButton, new OnClickListener() {  
              
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                
            }  
        });  
        downloadDialog = builder.create();  
        downloadDialog.show();  
    }  
    
    private void initReceiver(){
    	receiver = new Receiver();
    	IntentFilter filter=new IntentFilter();
    	filter.addAction("cn.wp.tool.status");
    	DeviceListActivity.this.registerReceiver(receiver,filter);
    }
	private void scanError(){
    	Toast.makeText(getApplicationContext(), 
    			getString(R.string.canNotScaned), Toast.LENGTH_LONG).show();
		
		LayoutInflater layoutInflater = LayoutInflater.from(DeviceListActivity.this); 
		final View view = layoutInflater.inflate(R.layout.unscaned_dialog,null);
		TextView unscaned = (TextView)view.findViewById(R.id.unscaned);
		Button unEnter = (Button)view.findViewById(R.id.unscanedEnter);
		unscaned.setText(getString(R.string.unscanedInfo));
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);  
    	builder.setTitle(getString(R.string.canNotScaned))
    		.setView(view);
    	try{
        	final AlertDialog dialog =  builder.show();
        	unEnter.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View arg0) {
    				dialog.dismiss();
    			}
            });
    	}catch(BadTokenException e){
    		Log.i(TAG, "openUnscanedDialog," + e);
        	Toast.makeText(getApplicationContext(), 
        			getString(R.string.scanOnlineCameraFailed), Toast.LENGTH_LONG).show();
    	}
	}
	
    private void setOverflowShowingAlways() {  
        try {  
            ViewConfiguration config = ViewConfiguration.get(this);  
            Field menuKeyField = ViewConfiguration.class  
                    .getDeclaredField("sHasPermanentMenuKey");  
            menuKeyField.setAccessible(true);  
            menuKeyField.setBoolean(config, false);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    @Override  
    public boolean onMenuOpened(int featureId, Menu menu) {  
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
                try {  
                    Method m = menu.getClass().getDeclaredMethod(  
                            "setOptionalIconsVisible", Boolean.TYPE);  
                    m.setAccessible(true);  
                    m.invoke(menu, true);  
                } catch (Exception e) {  
                }  
            }  
        }  
        return super.onMenuOpened(featureId, menu);  
    }  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.device_list, menu);
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
		 Log.i(TAG, "onRequestCustomError,");
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
		Log.i(TAG, "connectionErrorDialogCancel," );
	}

	@Override
	public void connectionErrorDialogRetry(Request request) {
		Log.i(TAG, "connectionErrorDialogRetry," );
	}
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        this.unregisterReceiver(receiver);
    }  
	public class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int CMD = intent.getIntExtra(Defination.Cmd_Type.CMD, -1);
			switch(CMD){
			case Defination.Cmd_Type.SCAN_WIFI_ERROR:{
				scanError();
				break;
			}
			case Defination.Cmd_Type.SCAN_WIFI_FINISHED:{
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.gettingDeviceInfo), Toast.LENGTH_LONG).show();
				break;
			}
			case Defination.Cmd_Type.GET_NET_INFO_FINISHED:{
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.openningDhcpWifi), Toast.LENGTH_LONG).show();
				break;
			}
			case Defination.Cmd_Type.OPEN_DHCP_FINISHED:{
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.scaned), Toast.LENGTH_LONG).show();
				break;
			}
			case Defination.Cmd_Type.REPORT_DOWNLOAD_PROGRESS:{
				int progress = intent.getIntExtra(Updater.DOWNLOAD_PROGRESS, 100);
				downloadP.setProgress(progress);
				break;
			}
			case Defination.Cmd_Type.DOWNLOAD_FINISHED:{
				downloadDialog.dismiss();
				break;
			}
			case Defination.Cmd_Type.DOWNLOAD_ERROR:{
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.download_error), Toast.LENGTH_LONG).show();
		    	downloadDialog.dismiss();
				break;
			}
			case Defination.Cmd_Type.UPDATE_DELAY:{
				int which = intent.getIntExtra(DELAY_IP,0);
				String data = intent.getStringExtra(DELAY_DATA);
				if(which == 0){
					gatewayDelay.setText(data + getString(R.string.ms));
				}
				break;
			}
			case Defination.Cmd_Type.UPDATE_ONLINE_CAMERA_NUM:{
				refreshOnlineNum();
				break;
			}
			default:
				break;
			}
		}
	}
}

