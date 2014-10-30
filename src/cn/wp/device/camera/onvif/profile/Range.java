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
 * @Title Range.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:52:42 
 * @version V1.0   
 */
public class Range {
	private String URI;
	private XRange XRange;
	private YRange YRange;
	
	/**
	 * @return the uRI
	 */
	public String getURI() {
		return URI;
	}

	/**
	 * @param uRI the uRI to set
	 */
	public void setURI(String uRI) {
		URI = uRI;
	}

	/**
	 * @return the xRange
	 */
	public XRange getXRange() {
		return XRange;
	}

	/**
	 * @param xRange the xRange to set
	 */
	public void setXRange(XRange xRange) {
		XRange = xRange;
	}

	/**
	 * @return the yRange
	 */
	public YRange getYRange() {
		return YRange;
	}

	/**
	 * @param yRange the yRange to set
	 */
	public void setYRange(YRange yRange) {
		YRange = yRange;
	}

	public String toString() {
		String s = "Range: "
				 + "URI:" + URI
				 + "XRange:" + XRange
				 + "YRange:" + YRange;
		return s;
	}
}
