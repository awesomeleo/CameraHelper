/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.response;

import java.util.ArrayList;
import java.util.List;

import cn.wp.device.camera.onvif.probe.ProbeMatch;
import cn.wp.device.camera.onvif.profile.Profile;

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
 * @Title ProbeMatches.java 
 * @Package cn.ws.device.camera.onvif.response 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��17�� ����2:01:30 
 * @version V1.0   
 */
public class ProbeMatches {
	
	private List<ProbeMatch> ProbeMatchList = new ArrayList<ProbeMatch>();
	
	/**
	 * @return the probeMatchList
	 */
	public List<ProbeMatch> getProbeMatchList() {
		return ProbeMatchList;
	}

	/**
	 * @param probeMatchList the probeMatchList to set
	 */
	public void setProbeMatchList(List<ProbeMatch> probeMatchList) {
		ProbeMatchList = probeMatchList;
	}

	public String toString() {
		String s = "ProbeMatches:";
		for(ProbeMatch pm:ProbeMatchList) {
			s += pm.toString() + ";";
		}
		return s;
	}
}
