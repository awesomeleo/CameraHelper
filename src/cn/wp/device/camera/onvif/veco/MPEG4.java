/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.veco;

import java.util.ArrayList;
import java.util.List;

import cn.wp.device.camera.onvif.OnvifDefination.Onvif_VECO_TAG;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import android.util.Log;

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
 * @Title MPEG4.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��23�� ����5:27:41 
 * @version V1.0   
 */
public class MPEG4 {
	private ResolutionsAvailable ResolutionsAvailable;
	@XStreamImplicit(itemFieldName=Onvif_VECO_TAG.Resolutions_Available)
	private List<ResolutionsAvailable> ResolutionsAvailableList = new ArrayList<ResolutionsAvailable>();
	
	private GovLengthRange GovLengthRange;
	private FrameRateRange FrameRateRange;
	private EncodingIntervalRange EncodingIntervalRange;
	private String Mpeg4ProfilesSupported;
	
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
	 * @return the govLengthRange
	 */
	public GovLengthRange getGovLengthRange() {
		return GovLengthRange;
	}
	/**
	 * @param govLengthRange the govLengthRange to set
	 */
	public void setGovLengthRange(GovLengthRange govLengthRange) {
		GovLengthRange = govLengthRange;
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
	/**
	 * @return the mpeg4ProfilesSupported
	 */
	public String getMpeg4ProfilesSupported() {
		return Mpeg4ProfilesSupported;
	}

	/**
	 * @param mpeg4ProfilesSupported the mpeg4ProfilesSupported to set
	 */
	public void setMpeg4ProfilesSupported(String mpeg4ProfilesSupported) {
		Mpeg4ProfilesSupported = mpeg4ProfilesSupported;
	}
	
	public String toString() {
		String s = "MPEG4:";
		String ra = "ResolutionsAvailableList=";
		Log.i("MPEG4", "ResolutionsAvailableList=" + ResolutionsAvailableList);
		Log.i("MPEG4", "ResolutionsAvailable=" + ResolutionsAvailable);
		ResolutionsAvailableList = getResolutionList();
		if (ResolutionsAvailableList != null && ResolutionsAvailableList.size() > 0) {
			for(ResolutionsAvailable p:ResolutionsAvailableList) {
				Log.i("MPEG4", p.toString());
				ra += p.toString() + ";";
			}
		} else
			ra += ResolutionsAvailable;
		s += ra;
		
		s += ", GovLengthRange=" + GovLengthRange;
		s += ", FrameRateRange=" + FrameRateRange;
		s += ", EncodingIntervalRange=" + EncodingIntervalRange;
		s += ", Mpeg4ProfilesSupported=" + Mpeg4ProfilesSupported;
		return s;
	}
}
