package cn.wp.tool;


import android.app.Application;
import android.util.Log;

public class WpToolApplication extends Application {
	private final String TAG = WpToolApplication.class.getSimpleName();
    @Override
    public void onCreate() {
    	Log.i(TAG, "WpToolApplication,"  + "start");
        super.onCreate();
    }
}
