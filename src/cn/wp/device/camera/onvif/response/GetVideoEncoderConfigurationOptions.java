/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.response;

import cn.wp.device.camera.onvif.veco.Options;

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
 * @Title GetVideoEncoderConfigurationOptions.java 
 * @Package cn.ws.device.camera.onvif.response 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��14�� ����9:07:11 
 * @version V1.0   
 */
public class GetVideoEncoderConfigurationOptions {
	private Options Options;

	/**
	 * @return the options
	 */
	public Options getOptions() {
		return Options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Options options) {
		Options = options;
	}
	
	public String toString() {
		return "GetVideoEncoderConfigurationOptions: Options=" + Options;
	}
}
