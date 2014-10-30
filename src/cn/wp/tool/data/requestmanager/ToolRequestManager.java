package cn.wp.tool.data.requestmanager;

import cn.wp.tool.data.service.ToolRequestService;

import com.foxykeep.datadroid.requestmanager.RequestManager;

import android.content.Context;
import android.util.Log;

/**
 * This class is used as a proxy to call the Service. It provides easy-to-use methods to call the
 * service and manages the Intent creation. It also assures that a request will not be sent again if
 * an exactly identical one is already in progress.
 *
 * @author Foxykeep
 */
public final class ToolRequestManager extends RequestManager {

    // Singleton management
    private static ToolRequestManager sInstance;

    public synchronized static ToolRequestManager from(Context context) {
        if (sInstance == null) {
            sInstance = new ToolRequestManager(context);
        }

        return sInstance;
    }

    private ToolRequestManager(Context context) {
    	super(context, ToolRequestService.class);
    }
}
