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
 * @Title Address.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014-4-10 pm8:38:59 
 * @version V1.0   
 */
public class Address {

	private String Type;
	private String IPv4Address;
	private String IPv6Address;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}

	/**
	 * @return the iPv4Address
	 */
	public String getIPv4Address() {
		return IPv4Address;
	}

	/**
	 * @param iPv4Address the iPv4Address to set
	 */
	public void setIPv4Address(String iPv4Address) {
		IPv4Address = iPv4Address;
	}
	
	/**
	 * @return the iPv6Address
	 */
	public String getIPv6Address() {
		return IPv6Address;
	}

	/**
	 * @param iPv6Address the iPv6Address to set
	 */
	public void setIPv6Address(String iPv6Address) {
		IPv6Address = iPv6Address;
	}
	
	public String toString() {
		String s = "Address: "
				 + "Type:" + Type
				 + ", IPv4Address:" + IPv4Address
				 + ", IPv6Address:" + IPv6Address;
		return s;
	}
}
