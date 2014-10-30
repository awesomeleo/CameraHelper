package cn.wp.tool.ui.camera;

import java.io.IOException;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import cn.wp.device.camera.utils.PaneseCgiUtil;
import cn.wp.device.camera.utils.ObjectCheck;
import cn.wp.tool.R;
import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment;
import cn.wp.tool.dialogs.ConnectionErrorDialogFragment.ConnectionErrorDialogListener;
import cn.wp.tool.ui.DataDroidActivity;
import cn.wp.tool.ui.devicelist.DeviceListActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CameraVideoActivity extends DataDroidActivity  implements OnBufferingUpdateListener, 
																OnCompletionListener, OnPreparedListener, 
																OnVideoSizeChangedListener, SurfaceHolder.Callback,
																 RequestListener, ConnectionErrorDialogListener{
	private final String TAG = CameraVideoActivity.class.getSimpleName();
	public static final String TRANCMD = "camera";
	private Button ctrlPtzUp;
	private Button ctrlPtzDown;
	private Button ctrlPtzLeft;
	private Button ctrlPtzRight;
	private Button ctrlPtzSave;
	private Button ctrlPtzHome;
	private Button changeOsd;
	private IPCamera camera = null;
	private int mVideoWidth = 160;
	private int mVideoHeight = 90;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String videoPath = null;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
        this.setContentView(R.layout.activity_camera_video);
        this.initUI();
    }
    
    private void initData(){
    	Log.i(TAG, "initData");
    	Intent intent  = getIntent();
    	camera = intent.getParcelableExtra(TRANCMD);
    	if(!ObjectCheck.validObject(camera)){
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
			return;
    	}
    	videoPath = camera.getRtspLiveStringURL(0);
    	if(!ObjectCheck.validString(videoPath)){
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalidVideoRtsp)
									, Toast.LENGTH_LONG).show();
			return;
    	}
    	Log.i(TAG, videoPath);
    	int pathLength = videoPath.length();
    	if(!DeviceListActivity.highVideoStream){
        	videoPath = videoPath.substring(0, pathLength -1);
        	videoPath += "2";
    	}
    }
    private void initUI(){
    	Log.i(TAG, "initUI");
    	ctrlPtzHome = (Button) findViewById(R.id.ctrlPtzHome);
		ctrlPtzUp = (Button) findViewById(R.id.ctrlPtzUp);
		ctrlPtzDown = (Button) findViewById(R.id.ctrlPtzDown);
		ctrlPtzLeft = (Button) findViewById(R.id.ctrlPtzLeft);
		ctrlPtzRight = (Button) findViewById(R.id.ctrlPtzRight);
		ctrlPtzSave = (Button) findViewById(R.id.ctrlPtzSave);
		changeOsd = (Button) findViewById(R.id.osdChange);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888); 
		ctrlPtzUp.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					ctrlPtzUp.setBackgroundColor(Color.rgb(0, 255, 0));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_UP, 60);
				}else if(event.getAction() == MotionEvent.ACTION_CANCEL){
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
//					ctrlPtzUp.setBackgroundColor(Color.rgb(255, 255, 255));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				}
				return true;
			}
		});
		ctrlPtzDown.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					ctrlPtzDown.setBackgroundColor(Color.rgb(0, 255, 0));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_DOWN, 60);
				}else if(event.getAction() == MotionEvent.ACTION_CANCEL){
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
//					ctrlPtzDown.setBackgroundColor(Color.rgb(255, 255, 255));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				} 
				return true;
			}
		});
		ctrlPtzLeft.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					ctrlPtzLeft.setBackgroundColor(Color.rgb(0, 255, 0));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_LEFT, 60);
				}else if(event.getAction() == MotionEvent.ACTION_CANCEL){
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				}if(event.getAction() == MotionEvent.ACTION_UP){
//					ctrlPtzLeft.setBackgroundColor(Color.rgb(255, 255, 255));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				} 
				return true;
			}
		});
		ctrlPtzRight.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					ctrlPtzRight.setBackgroundColor(Color.rgb(0, 255, 0));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_RIGHT, 60);
				}else if(event.getAction() == MotionEvent.ACTION_CANCEL){
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
//					ctrlPtzRight.setBackgroundColor(Color.rgb(255, 255, 255));
					CameraVideoActivity.this.ctrlPtz(PaneseCgiUtil.VAR_PTZ_CTRL_STOP, 60);
				} 
				return true;
			}
		});
		ctrlPtzHome.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View arg0) {
 				CameraVideoActivity.this.gotoPreSet();
 			}
         });
		ctrlPtzSave.setOnClickListener(new View.OnClickListener(){
 			@Override
 			public void onClick(View arg0) {
 				CameraVideoActivity.this.preSetCamera(PaneseCgiUtil.VAR_PTZ_CTRL_PRESET_SET,1, 0);
 			}
         });
		changeOsd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changeOSDDialog();
			}
		});
    }

    private void preSetCamera(String action,int status,int num){
    	Log.i(TAG, "preSetCamera");
        Request request = ToolRequestFactory.presetPtz(camera, action, status, num);
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    private void gotoPreSet(){
    	Log.i(TAG, "gotoPreSet");
        Request request = ToolRequestFactory.gotoPreSet(camera.getmBaseAccesssURL());
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    private void ctrlPtz(String CMD,int speed){
    	Log.i(TAG, "ctrlPtz");
        Request request = ToolRequestFactory.ctrlPtz(camera, CMD, speed);
        mRequestManager.execute(request, this);
        mRequestList.add(request);
    }
    private void playVideo(){
    	Log.i(TAG, "playVideo");
		// Create a new media player and set the listeners
		mMediaPlayer = new MediaPlayer(this);
		try {
			mMediaPlayer.setDataSource(videoPath);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.toString());
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
		} catch (SecurityException e) {
			Log.e(TAG, e.toString());
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
		} catch (IllegalStateException e) {
			Log.e(TAG, e.toString());
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			Toast.makeText(CameraVideoActivity.this, getString(R.string.invalideInfo)
					, Toast.LENGTH_LONG).show();
		}
		mMediaPlayer.setDisplay(holder);
		mMediaPlayer.prepareAsync();
		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnVideoSizeChangedListener(this);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    
	@Override
	public void onRequestFinished(Request request, Bundle resultData) {
        if (mRequestList.contains(request)) {
            setProgressBarIndeterminateVisibility(false);
            mRequestList.remove(request);
            int choose = resultData.getInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE);
            switch(choose){
            case ToolRequestFactory.REQUEST_PRE_SET:{
            	boolean status = false;
            	status = resultData.getBoolean(ToolRequestFactory.BUNDLE_EXTRA_CAMERA_PRE_SET);
            	if(status)
        			Toast.makeText(CameraVideoActivity.this, getString(R.string.presetPtzSucceed)
							, Toast.LENGTH_LONG).show();
            	else
        			Toast.makeText(CameraVideoActivity.this, getString(R.string.presetPtzFailed)
							, Toast.LENGTH_LONG).show();
            	break;
            }
            case ToolRequestFactory.REQUEST_CHANGE_OSD:{
            	boolean status = false;
            	status = resultData.getBoolean(ToolRequestFactory.BUNDLE_EXTRA_CHANGE_OSD);
            	if(status)
        			Toast.makeText(CameraVideoActivity.this, getString(R.string.changeOSDSucceed)
							, Toast.LENGTH_LONG).show();
            	else
        			Toast.makeText(CameraVideoActivity.this, getString(R.string.changeOSDFailed)
							, Toast.LENGTH_LONG).show();
            	break;
            }
            }
            
        }
	}
    private void changeOSDDialog() {
    	LayoutInflater layoutInflater = LayoutInflater.from(CameraVideoActivity.this);
    	final View view = layoutInflater.inflate(R.layout.activity_osd_change,null);
    	final EditText inputP = (EditText)view.findViewById(R.id.inputOSD);
    	AlertDialog.Builder builder = new Builder(CameraVideoActivity.this);
    	builder.setTitle(getString(R.string.osdTitle));
    	builder.setView(view);
    	builder.setPositiveButton(getString(R.string.enterButton), new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(!ObjectCheck.validObject(camera.getmBaseAccesssURL())){
			    	Toast.makeText(getApplicationContext(), 
			    			getString(R.string.invalidInfo), Toast.LENGTH_SHORT).show();
					return;
				}
				String osd = inputP.getText().toString();
				if(!ObjectCheck.validString(osd)){
			    	Toast.makeText(getApplicationContext(), 
			    			getString(R.string.invalidInfo), Toast.LENGTH_SHORT).show();
			    	return;
				}
		        Request request = ToolRequestFactory.changeOSD(camera.getmBaseAccesssURL(), osd);
		        mRequestManager.execute(request, CameraVideoActivity.this);
		        mRequestList.add(request);
		    	Toast.makeText(getApplicationContext(), 
		    			getString(R.string.osdChanging), Toast.LENGTH_SHORT).show();
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
	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
        this.initData();
		playVideo();

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
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
