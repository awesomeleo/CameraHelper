/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.veco;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_VECO_TAG;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
 * @Title JPEG.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��23�� ����5:29:42 
 * @version V1.0   
 */
public class JPEG {
	private ResolutionsAvailable ResolutionsAvailable;
	@XStreamImplicit(itemFieldName=Onvif_VECO_TAG.Resolutions_Available)
	private List<ResolutionsAvailable> ResolutionsAvailableList = new ArrayList<ResolutionsAvailable>();
	
	private FrameRateRange FrameRateRange;
	private EncodingIntervalRange EncodingIntervalRange;
	
	/**
	 * @return the resolutionsAvailable
	 */
	private ResolutionsAvailable getResolutionsAvailable() {
		return ResolutionsAvailable;
	}

	/**
	 * @param resolutionsAvailable the resolutionsAvailable to set
	 */
	private void setResolutionsAvailable(ResolutionsAvailable resolutionsAvailable) {
		ResolutionsAvailable = resolutionsAvailable;
	}
	/**
	 * @return the resolutionList
	 */
	public List<ResolutionsAvailable> getResolutionList() {
		if (ResolutionsAvailableList.isEmpty() && ResolutionsAvailable != null) {
			ResolutionsAvailableList.add(ResolutionsAvailable);
		}
		return ResolutionsAvailableList;
	}

	/**
	 * @param resolutionList the resolutionList to set
	 */
	public void setResolutionList(List<ResolutionsAvailable> resolutionsAvailableList) {
		ResolutionsAvailableList = resolutionsAvailableList;
	}
	/**
	 * @return the frameRateRange
	 */
	public FrameRateRange getFrameRateRange() {
		return FrameRateRange;
	}
	/**
	 * @param frameRateRange the frameRateRange to set
	 */
	public void setFrameRateRange(FrameRateRange frameRateRange) {
		FrameRateRange = frameRateRange;
	}
	/**
	 * @return the encodingIntervalRange
	 */
	public EncodingIntervalRange getEncodingIntervalRange() {
		return EncodingIntervalRange;
	}
	/**
	 * @param encodingIntervalRange the encodingIntervalRange to set
	 */
	public void setEncodingIntervalRange(EncodingIntervalRange encodingIntervalRange) {
		EncodingIntervalRange = encodingIntervalRange;
	}
	
	public String toString() {
		String s = "JPEG:";
		String ra = "ResolutionsAvailableList=";
		Log.i("JPEG", "ResolutionsAvailableList=" + ResolutionsAvailableList);
		Log.i("JPEG", "ResolutionsAvailable=" + ResolutionsAvailable);
		ResolutionsAvailableList = getResolutionList();
		if (ResolutionsAvailableList != null && ResolutionsAvailableList.size() > 0) {
			for(ResolutionsAvailable p:ResolutionsAvailableList) {
				Log.i("JPEG", p.toString());
				ra += p.toString() + ";";
			}
		} else
			ra += ResolutionsAvailable;
		s += ra;
		s += ", FrameRateRange=" + FrameRateRange;
		s += ", EncodingIntervalRange=" + EncodingIntervalRange;
		return s;
	}
}
