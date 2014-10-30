
package cn.wp.tool.data.model;

import com.google.gson.annotations.Expose;

public class AppInfo {

    @Expose
    private String appName;
    @Expose
    private String pkgName;
    @Expose
    private double versionCode;
    @Expose
    private String downloadURI;


    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }


    public double getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(double versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadURI() {
        return downloadURI;
    }

    public void setDownloadURI(String downloadURI) {
        this.downloadURI = downloadURI;
    }

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
