package cn.wp.tool.provider;


import cn.wp.tool.data.config.Defination;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Log;

public class CameraListProvider extends ContentProvider {
	private final String TAG = CameraListProvider.class.getSimpleName();
	public static final Uri CONTENT_URI = Uri.parse("content://cn.wp.tool.cameralist");
	private static final int DATABASE_VERSION = 1;
	private static final String CAMERATABLE = "CameraInfo";
	private static SQLiteDatabase database;
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		Log.i(TAG, "delete");
		database.delete(CAMERATABLE, arg1, arg2);
		getContext().getContentResolver().notifyChange(arg0, null);
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		Log.i(TAG, "insert");
		database.insert(CAMERATABLE, "", arg1);
		getContext().getContentResolver().notifyChange(arg0, null);
		return null;
	}

	@Override
	public boolean onCreate() {
		DBHelper helper = new DBHelper(getContext(),null,DATABASE_VERSION);
		database = helper.getWritableDatabase();
		return database != null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		Log.i(TAG, "query");
		Cursor cursor = database.query(CAMERATABLE, arg1, arg2, arg3, null, null, arg4);
		cursor.setNotificationUri(getContext().getContentResolver(), arg0);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.i(TAG, "update");
		database.update(CAMERATABLE, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return 0;
	}
	private class DBHelper extends SQLiteOpenHelper{
		private final String TAG = DBHelper.class.getSimpleName();
		private String cameraTableSql = "CREATE TABLE IF NOT EXISTS  "+ CAMERATABLE +" (_id int identity,"
							+ Defination.CameraTableColum.SN.getName() + Defination.CameraTableColum.SN.getType()+"UNIQUE,"
							+ Defination.CameraTableColum.NAME.getName() +Defination.CameraTableColum.NAME.getType() + ","
							+ Defination.CameraTableColum.IP.getName() +Defination.CameraTableColum.IP.getType()+ "UNIQUE,"
							+ Defination.CameraTableColum.MAC.getName() + Defination.CameraTableColum.MAC.getType() + "UNIQUE,"
							+ Defination.CameraTableColum.BASEURL.getName() +Defination.CameraTableColum.BASEURL.getType() +  " ,"
							+ Defination.CameraTableColum.NETTYPE.getName() +Defination.CameraTableColum.NETTYPE.getType()+ ","
							+ Defination.CameraTableColum.RSSI.getName() +Defination.CameraTableColum.SN.getType() +  ","
							+ Defination.CameraTableColum.SSID.getName() +Defination.CameraTableColum.SSID.getType() + ","
							+ Defination.CameraTableColum.DHCP.getName() +Defination.CameraTableColum.DHCP.getType() +  " ,"
							+ Defination.CameraTableColum.WIFI.getName() + Defination.CameraTableColum.WIFI.getType()+  ","
							+ Defination.CameraTableColum.REBOOT.getName() +Defination.CameraTableColum.REBOOT.getType() + ","
							+ Defination.CameraTableColum.RTSP.getName() + Defination.CameraTableColum.RTSP.getType()+ ","
							+ Defination.CameraTableColum.DELAY.getName() + Defination.CameraTableColum.DELAY.getType() + ","
							+ Defination.CameraTableColum.ONLINE.getName() + Defination.CameraTableColum.ONLINE.getType()
							+")"; 
		public DBHelper(Context context, CursorFactory factory,
				int version) {
			super(context, null, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "create table" +cameraTableSql );
			db.execSQL(cameraTableSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			
		}
	}
}
