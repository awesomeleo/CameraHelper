/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.probe;

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
 * @Title Scopes.java 
 * @Package cn.ws.device.camera.onvif.probe 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��18�� ����11:50:54 
 * @version V1.0   
 */
public class Scopes {
	
	private String ScopeDef;
	private String ScopeItem;
	/**
	 * @return the scopeDef
	 */
	public String getScopeDef() {
		return ScopeDef;
	}
	/**
	 * @param scopeDef the scopeDef to set
	 */
	public void setScopeDef(String scopeDef) {
		ScopeDef = scopeDef;
	}
	/**
	 * @return the scopeItem
	 */
	public String getScopeItem() {
		return ScopeItem;
	}
	/**
	 * @param scopeItem the scopeItem to set
	 */
	public void setScopeItem(String scopeItem) {
		ScopeItem = scopeItem;
	}
	
	public String toString() {
		String s = "Scopes:";
		s += "ScopeDef=" + ScopeDef;
		s += ", ScopeItem=" + ScopeItem;
		return s;
	}
}
