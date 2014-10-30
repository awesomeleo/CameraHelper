package cn.wp.device.camera.utils;

import android.net.Uri;

/**
 * <p>
 * <h1>This class is part of WsCare</h1>
 * <br>
 * list the columns of user's data<br>
 * </p>
 * 
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * Beijing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 *
 * <p>
 * <h1>Reviewer:</h1> 
 * <a href="mailto:jiangjunjie@1v.cn">jjj</a>
 * </p>
 * 
 * <p>
 * <h1>History Trace:</h1>
 * <li>2014-04-01    V1.0.0          jjj         first release</li>
 * </p>
 */
public final class UserData {

    public static final String AUTHORITY = "cn.ws.care.data.UsersDataProvider";
    
    public static final String USERS_DATA = "Table_UsersData";

    public static final class URI {
        private static final String BASE_URI        = "content://" + AUTHORITY + "/";
        public static final Uri TABLE_USERSDATA     = Uri.parse(BASE_URI + USERS_DATA);
    }

    /**
     */
    public static final class SaveStyle {
        /**  */
        public static final int Global = 0;
        /**  */
        public static final int ByProgram = 1;
    }
    
    /**
     * UsersData
     */
    public interface TableUsersDataColumns {
        public static final String ID                               = "_id";
        /** configure the name of project */
        public static final String PROJECT_NAME                     = "ProjectName";
        /** configure type of camera will be scanned */
        public static final String CAMERA_TYPE                      = "CameraType";
        /** which stream will be used */
        public static final String STREAM_INDEX                     = "StreamIndex";
        /** configure the user name of camera */
        public static final String CAMERA_USER_NAME                 = "cameraUserName";
        /** configure the password of camera */
        public static final String CAMERA_PASSWORD                  = "cameraPwd";
        /** configure the onvif request */
        public static final String CAMID_GENE_MODE                  = "camIdGeneMode";
        /** configure the onvif request */
        public static final String GET_VIDEO_XML                    = "getVideoXml";
        /** configure the onvif request */
        public static final String SET_VIDEO_XML                    = "setVideoXml";
        /** configure the supported resolution */
        public static final String SUPPORTED_WIDTH                  = "SupportedWidth";
        /** configure the supported resolution */
        public static final String SUPPORTED_HEIGHT                 = "SupportedHeight";
        /** */
        public static final String LANGUAGE_CODE                    = "LanguageCode";
        /** the max count of camera box can supported */
        public static final String MAX_CAMERA_COUNT                 = "MaxCameraCount";
        /** the library process the stream  */
        public static final String LIB_PROCESS_STREAM               = "LibProcessStream";
    }
    
    public interface TableAppDataColumns{
    	public static final String appName = "AppName";
    	public static final String pkgName = "PackageName";
    	public static final String versionCode = "VersionCode";
    }
    
}
