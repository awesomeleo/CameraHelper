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
 * @Title MPEG4.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��23�� ����4:34:20 
 * @version V1.0   
 */
public class MPEG4 {
	private int GovLength;
	private String Mpeg4Profile;
	
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
	 * @return the mpeg4Profile
	 */
	public String getMpeg4Profile() {
		return Mpeg4Profile;
	}

	/**
	 * @param mpeg4Profile the mpeg4Profile to set
	 */
	public void setMpeg4Profile(String mpeg4Profile) {
		Mpeg4Profile = mpeg4Profile;
	}
	
	public String toString() {
		String s = "MPEG4: "
				 + "GovLength:" + GovLength
				 + ", Mpeg4Profile:" + Mpeg4Profile;
		return s;
	}
}
