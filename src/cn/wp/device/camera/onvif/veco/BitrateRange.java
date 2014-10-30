/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.veco;

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
 * @Title BitrateRange.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��17�� ����9:41:27 
 * @version V1.0   
 */
public class BitrateRange {
	private int Min;
	private int Max;
	
	/**
	 * @return the min
	 */
	public int getMin() {
		return Min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		Min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return Max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		Max = max;
	}

	public String toString() {
		String s = "BitrateRange: "
				 + "Min:" + Min
				 + "Max:" + Max;
		return s;
	}
}
