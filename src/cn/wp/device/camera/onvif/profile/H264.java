/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.profile;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
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
 * @Title H264.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:35:52 
 * @version V1.0   
 */
public class H264 {
	private int GovLength;
	private String H264Profile;
	
	/**
	 * @return the govLength
	 */
	public int getGovLength() {
		return GovLength;
	}

	/**
	 * @param govLength the govLength to set
	 */
	public void setGovLength(int govLength) {
		GovLength = govLength;
	}

	/**
	 * @return the h264Profile
	 */
	public String getH264Profile() {
		return H264Profile;
	}

	/**
	 * @param h264Profile the h264Profile to set
	 */
	public void setH264Profile(String h264Profile) {
		H264Profile = h264Profile;
	}

	public String toString() {
		String s = "H264: "
				 + "GovLength:" + GovLength
				 + ", H264Profile:" + H264Profile;
		return s;
	}
}
