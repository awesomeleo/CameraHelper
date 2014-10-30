package cn.wp.tool.data.config;


public class Defination {
	
	/** time */
	public static class Time_Constant {
		public static long Delayed_Seconds_Two = 1000 * 2;
		public static long Delayed_Seconds_Five = 1000 * 5;
		public static long Delayed_Seconds_Ten  = 1000 * 10;
		public static long Delayed_Minites_Half = 1000 * 30;
		public static long Delayed_Minites_One  = 1000 * 60;
		public static long Delayed_Minites_Five = Delayed_Minites_One * 5;
		public static long Delayed_Minites_Ten  = Delayed_Minites_One * 10;
	}
	public static class Shared_Preferences{
		public static String SETTING = "setting";
		public static String AUTODHCP = "autoOpenDhcp";
	}
	public static class Scan_Camera_Type {
		public static final int ALL   = 0;
		public static final int Stub  = 1;
		public static final int ZAG   = 2;
		public static final int Onvif = 3;
	}
	
	public static class Stream_Index {
		public static final String Main   = "MainProfile";
		public static final String Sub    = "SubProfile";
	}
	
	public static final int IdDigit              = 18;
	
	public static class CamId_Gene_Mode{
		public static final String Name   = "Name";
		public static final String ID     = "ID";
		public static final String NameID = "NameID";
		public static final String UUID   = "UUID";
	}
	
	public static class Network_Type{
		public static final String LAN = "LAN";
		public static final String WIFI = "Wireless LAN";
	}
	
	public static class Cmd_Type{
		public static final String CMD = "CMD";
		public static final int SCAN_WIFI_FINISHED = 0;
		public static final int SET_WIFI_FINISHED = 1;
		public static final int REBOOT_CAMERA_FINISHED = 2;
		public static final int SET_WIFI_FINISHED_CAMERALIST = 3;
		public static final int SCAN_WIFI_ERROR = 4;
		public static final int GET_NET_INFO_FINISHED = 5;
		public static final int OPEN_DHCP_FINISHED = 6;
		public static final int REPORT_DOWNLOAD_PROGRESS = 7;
		public static final int DOWNLOAD_FINISHED = 8;
		public static final int DOWNLOAD_ERROR = 9;
		public static final int UPDATE_DELAY = 10;
		public static final int UPDATE_ONLINE_CAMERA_NUM = 11;
	}
	
	public static class Meun_Value{
		public static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_function";
		public static final int SCAN_CAMERA = 1;
		public static final int SCAN_BOX = 2;
		public static final int EXIT = 3;
	}
	public static enum CameraTableColum{
		SN("sn"," text "),
		NAME("name"," text "),
		IP("ipaddr"," text "),
		MAC("mac"," text "),
		BASEURL("baseurl"," text "),
		NETTYPE("nettype"," text "),
		RSSI("rssi"," text "),
		SSID("ssid"," text "),
		DHCP("dhcp"," boolean "),
		WIFI("wifi"," boolean "),
		REBOOT("reboot"," boolean "),
		RTSP("rtsp"," text "),
		DELAY("delay"," text "),
		ONLINE("online"," boolean ");

        private final String mName;
        private final String mType;

        private CameraTableColum(String name, String type) {
            mName = name;
            mType = type;
        }

        public int getIndex() {
            return ordinal() + 1;
        }

        public String getName() {
            return mName;
        }

        public String getType() {
            return mType;
        }
	}
}
