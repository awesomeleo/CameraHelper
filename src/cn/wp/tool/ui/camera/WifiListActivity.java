package cn.wp.tool.ui.camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;

import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.R;
import cn.wp.tool.data.model.CameraWifi;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import cn.wp.tool.ui.DataDroidActivity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WifiListActivity extends DataDroidActivity 
														implements ActionBar.OnNavigationListener, RequestListener, 
																					ConnectionErrorDialogListener{
	private final String TAG = WifiListActivity.class.getSimpleName();
	public static final String TRANCMD = "camera";
	private IPCamera camera = null;
	private ListView wifiList = null;
	private ProgressBar scanWifiBar = null;
	private boolean settingStatus = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_wifi);
        if(this.initData()){
            this.initUI();
            this.scanWifi();
        }
    }
    private boolean initData(){
    	Log.i(TAG, "initData");
    	Intent intent  = getIntent();
    	camera = intent.getParcelableExtra(TRANCMD);
    	if(!ObjectCheck.validObject(camera)){
			Toast.makeText(WifiListActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
			return false;
    	}
    	return true;
    }
    
    private void initUI(){
		wifiList = (ListView) findViewById(R.id.wifiList);
		scanWifiBar = (ProgressBar) findViewById(R.id.scanWifiProgressBar);
		scanWifiBar.setVisibility(View.VISIBLE);
    }
    private void scanWifi(){
        Request request = ToolRequestFactory.scanWifi(camera);
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    
    private void setwifi(IPCamera cameraSetting){
    	settingStatus = true;
        Request request = ToolRequestFactory.setWifi(cameraSetting);
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    private void sortList(){
    	List<CameraWifi> sortList = camera.getNetInfo().getWifiList();
    	Collections.sort(sortList, new Comparator<CameraWifi>() {  
            public int compare(CameraWifi o1, CameraWifi o2) {  
            	if(ObjectCheck.validObject(camera.getNetInfo().getMyWifi()))
            		if(ObjectCheck.validString(camera.getNetInfo().getMyWifi().getSsId())){
            			String ssid = camera.getNetInfo().getMyWifi().getSsId();
            			if(o1.getSsId().equals(ssid))
            				return -100;
            			else if( o2.getSsId().equals(ssid))
            				return 100;
            		}
                int result = o1.getRssi() - o2.getRssi();  
                return -result;  
            }  
        });  
    	camera.getNetInfo().setWifiList(sortList);
    }
    private void initWifiList(String settingWifi){
    	List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
    	for(CameraWifi wifi:camera.getNetInfo().getWifiList()){
    		HashMap<String, Object> temp = new HashMap<String, Object>();
    		temp.put("wifiName", wifi.getSsId());
    		if(ObjectCheck.validString(settingWifi)){
    			if(wifi.getSsId().equals(settingWifi))
    				temp.put("wifiName", wifi.getSsId() +":" + getString(R.string.settingWifi));
    		}
    		if(ObjectCheck.validObject(camera.getNetInfo().getMyWifi()))
    			if(camera.getNetInfo().getMyWifi().isWifiOn())
    				if(ObjectCheck.validString(camera.getNetInfo().getMyWifi().getSsId()))
    					if(wifi.getSsId().equals(camera.getNetInfo().getMyWifi().getSsId()))
    						temp.put("wifiName", wifi.getSsId() +":" + getString(R.string.connected));
    		if(wifi.getRssi() > 90){
    			temp.put("wifiImage", R.drawable.wififull);
    		}else if(wifi.getRssi() > 70){
    			temp.put("wifiImage", R.drawable.wifihigh);
    		}else if(wifi.getRssi() > 40){
    			temp.put("wifiImage", R.drawable.wifimedia);
    		}
    		else
    			temp.put("wifiImage", R.drawable.wifilow);
    		list.add(temp);
    	}
    	SimpleAdapter wifiItemAdapter = new SimpleAdapter(this, list, R.layout.activity_wifi_item,
    			new String[]{"wifiName","wifiImage"},new int[]{R.id.wifiName,R.id.wifiImage});
    	wifiList.setAdapter(wifiItemAdapter);
		wifiList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(), 
						getString(R.string.validPassword), Toast.LENGTH_SHORT).show();
				openWifiSettingDialog(camera,arg2);
			}
		});
    }
    
    private void openWifiSettingDialog(final IPCamera camera,final int num){
		LayoutInflater layoutInflater = LayoutInflater.from(WifiListActivity.this); 
		final View view = layoutInflater.inflate(R.layout.acitivity_wifi_set,null);
		final EditText inputP = (EditText)view.findViewById(R.id.inputP);
		Button enterP = (Button)view.findViewById(R.id.enterP);
		Button cancelP = (Button)view.findViewById(R.id.cancelP);
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);  
    	builder.setInverseBackgroundForced(true);
    	builder.setTitle(getString(R.string.password))
    		.setView(view);
    	final AlertDialog dialog =  builder.show();
		
		enterP.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					String passW = inputP.getText().toString();
					CameraWifi wifiClick = camera.getNetInfo().getWifiList().get(num);
					if(!ObjectCheck.validString(passW)){
						Toast.makeText(getApplicationContext(), 
								getString(R.string.connectWifiFailed), Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						return;
					}
					wifiClick.setPw(passW);
					camera.getNetInfo().setMyWifi(wifiClick);	
					WifiListActivity.this.setwifi(camera);
					initWifiList(wifiClick.getSsId());
					Toast.makeText(getApplicationContext(), 
							getString(R.string.settingWifi), Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					scanWifiBar.setVisibility(View.VISIBLE);
				}
	        });
		cancelP.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
        });
	}
    
	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            int choose = resultData.getInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE);
            switch(choose){
            case ToolRequestFactory.REQUEST_TYPE_SCAN_WIFI:
            	IPCamera cameraNetInfo = null;
            	scanWifiBar.setVisibility(View.GONE);
            	cameraNetInfo = resultData.getParcelable(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_SCAN_WIFI);
            	if(ObjectCheck.validObject(cameraNetInfo)){
					if(cameraNetInfo.getName().equals(camera.getName())){
						camera = cameraNetInfo;
						sortList();
						initWifiList(null);
						break;
					}
            	}
				Toast.makeText(getApplicationContext(), 
						getString(R.string.scanOnlineCameraFailed), Toast.LENGTH_LONG).show();
            	break;
            case ToolRequestFactory.REQUEST_TYPE_SET_WIFI:
            	
            	scanWifiBar.setVisibility(View.GONE);
            	IPCamera cameraWifi = null;
            	cameraWifi = resultData.getParcelable(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_SET_WIFI);
            	if(ObjectCheck.validObject(cameraWifi)){
    				Toast.makeText(getApplicationContext(), getString(R.string.connected) + " :" +
    						cameraWifi.getNetInfo().getMyWifi().getSsId(), Toast.LENGTH_LONG).show();
                	camera.getNetInfo().setMyWifi(cameraWifi.getNetInfo().getMyWifi());
                	camera.setNeedReboot(1);
    				initWifiList(null);
    				settingStatus = false;
    				break;
            	}
				Toast.makeText(getApplicationContext(), 
						getString(R.string.connectWifiFailed), Toast.LENGTH_LONG).show();
            	initWifiList(null);
            	settingStatus = false;
            	break;
            }
        }
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
            if(settingStatus){
				Toast.makeText(getApplicationContext(), 
						getString(R.string.pleaseWait), Toast.LENGTH_LONG).show();
            	return false;
            }else{
            	Intent intent=new Intent();  
                intent.putExtra("camera", camera);
                setResult(RESULT_OK, intent);  
            	WifiListActivity.this.finish();
            	return true;
            }
             
         }
         return super.onKeyDown(keyCode, event);
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
