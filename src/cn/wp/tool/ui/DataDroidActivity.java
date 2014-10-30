/**
 *
 */
package cn.wp.tool.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import cn.wp.tool.R;
import cn.wp.tool.data.requestmanager.ToolRequestManager;
import cn.wp.tool.dialogs.ErrorDialogFragment.ErrorDialogFragmentBuilder;

import com.foxykeep.datadroid.requestmanager.Request;

/**
 * @author Foxykeep
 *
 */
public abstract class DataDroidActivity extends FragmentActivity {

    private static final String SAVED_STATE_REQUEST_LIST = "savedStateRequestList";

    protected ToolRequestManager mRequestManager;
    protected ArrayList<Request> mRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestManager = ToolRequestManager.from(this);

        if (savedInstanceState != null) {
            mRequestList = savedInstanceState.getParcelableArrayList(SAVED_STATE_REQUEST_LIST);
        } else {
            mRequestList = new ArrayList<Request>();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(SAVED_STATE_REQUEST_LIST, mRequestList);
    }

    protected void showBadDataErrorDialog() {
        new ErrorDialogFragmentBuilder(this).setTitle(R.string.dialog_error_data_error_title)
                .setMessage(R.string.dialog_error_data_error_message).show();
    }
}
