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
 * @Title ResolutionsAvailable.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��15�� ����10:22:20 
 * @version V1.0   
 */
public class ResolutionsAvailable {
	private int Width;
	private int Height;
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return Width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		Width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return Height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		Height = height;
	}

	public String toString() {
		String s = "ResolutionsAvailable: "
				 + "Width:" + Width
				 + ", Height:" + Height;
		return s;
	}
}
