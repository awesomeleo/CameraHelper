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
 * @Title Multicast.java 
 * @Package cn.ws.device.camera.onvif.profile 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��10�� ����8:37:21 
 * @version V1.0   
 */
public class Multicast {
	private Address Address;
	private int Port;
	private int TTL;
	private boolean AutoStart;
	
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return Address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		Address = address;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return Port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		Port = port;
	}

	/**
	 * @return the tTL
	 */
	public int getTTL() {
		return TTL;
	}

	/**
	 * @param tTL the tTL to set
	 */
	public void setTTL(int tTL) {
		TTL = tTL;
	}

	/**
	 * @return the autoStart
	 */
	public boolean isAutoStart() {
		return AutoStart;
	}

	/**
	 * @param autoStart the autoStart to set
	 */
	public void setAutoStart(boolean autoStart) {
		AutoStart = autoStart;
	}

	public String toString() {
		String s = "Multicast: "
				 + "Address:" + Address
				 + ", Port:" + Port
				 + ", TTL:" + TTL
				 + ", AutoStart:" + AutoStart;
		return s;
	}
}
