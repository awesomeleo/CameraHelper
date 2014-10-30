package cn.wp.tool.data.operation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import cn.wp.tool.data.requestmanager.ToolRequestFactory;
import cn.wp.tool.updater.Updater;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

public final class CheckUpdateOperation implements Operation {
	public static final String PARAM_METHOD = "cn.wp.tool.extra.checkUpdate";
	private final String TAG = CheckUpdateOperation.class.getSimpleName();
	private Updater mUpdater = null;
	
	public CheckUpdateOperation(Updater updater){
		mUpdater = updater;
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		Log.i(TAG, "execute");
		boolean newVersion = false;
		mUpdater.checkUpdate();
		newVersion = mUpdater.hasNewVersion();
        Bundle bundle = new Bundle();
        bundle.putInt(ToolRequestFactory.BUNDLE_EXTRA_CHOOSE, ToolRequestFactory.REQUEST_CHECK_UPDATE);
        bundle.putBoolean(ToolRequestFactory.BUNDLE_EXTRA_CHECK_UPDATE,newVersion);
        return bundle;
	}
}
