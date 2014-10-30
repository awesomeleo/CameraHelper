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
 * @Title RateControl.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:33:32 
 * @version V1.0   
 */
public class RateControl {
	private int FrameRateLimit;
	private int EncodingInterval;
	private int BitrateLimit;
	
	/**
	 * @return the frameRateLimit
	 */
	public int getFrameRateLimit() {
		return FrameRateLimit;
	}

	/**
	 * @param frameRateLimit the frameRateLimit to set
	 */
	public void setFrameRateLimit(int frameRateLimit) {
		FrameRateLimit = frameRateLimit;
	}

	/**
	 * @return the encodingInterval
	 */
	public int getEncodingInterval() {
		return EncodingInterval;
	}

	/**
	 * @param encodingInterval the encodingInterval to set
	 */
	public void setEncodingInterval(int encodingInterval) {
		EncodingInterval = encodingInterval;
	}

	/**
	 * @return the bitrateLimit
	 */
	public int getBitrateLimit() {
		return BitrateLimit;
	}

	/**
	 * @param bitrateLimit the bitrateLimit to set
	 */
	public void setBitrateLimit(int bitrateLimit) {
		BitrateLimit = bitrateLimit;
	}

	public String toString() {
		String s = "RateControl: "
				 + "FrameRateLimit:" + FrameRateLimit
				 + ", EncodingInterval:" + EncodingInterval
				 + ", BitrateLimit:" + BitrateLimit;
		return s;
	}
}
