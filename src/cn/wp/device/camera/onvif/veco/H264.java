/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.onvif.veco;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import cn.wp.device.camera.onvif.OnvifDefination.Onvif_VECO_TAG;
import cn.wp.device.camera.utils.ObjectCheck;

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
 * @Title H264VECO.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��15�� ����10:21:46 
 * @version V1.0   
 */
public class H264 {
	
	private ResolutionsAvailable ResolutionsAvailable;
	@XStreamImplicit(itemFieldName=Onvif_VECO_TAG.Resolutions_Available)
	private List<ResolutionsAvailable> ResolutionsAvailableList = new ArrayList<ResolutionsAvailable>();
	
	private GovLengthRange GovLengthRange;
	private FrameRateRange FrameRateRange;
	private EncodingIntervalRange EncodingIntervalRange;
	private String H264ProfilesSupported;
	@XStreamImplicit(itemFieldName="H264ProfilesSupported")
	private List<String> H264ProfilesSupportedList = new ArrayList<String>();
	
	private BitrateRange BitrateRange;

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
	 * @return the h264ProfilesSupported
	 */
	public String getH264ProfilesSupported() {
		return H264ProfilesSupported;
	}

	/**
	 * @param h264ProfilesSupported the h264ProfilesSupported to set
	 */
	public void setH264ProfilesSupported(String h264ProfilesSupported) {
		H264ProfilesSupported = h264ProfilesSupported;
	}

	/**
	 * @return the h264ProfilesSupportedList
	 */
	public List<String> getH264ProfilesSupportedList() {
		if (H264ProfilesSupportedList.isEmpty() && ObjectCheck.validString(H264ProfilesSupported)) {
			H264ProfilesSupportedList.add(H264ProfilesSupported);
		}
		return H264ProfilesSupportedList;
	}

	/**
	 * @param h264ProfilesSupportedList the h264ProfilesSupportedList to set
	 */
	public void setH264ProfilesSupportedList(List<String> h264ProfilesSupportedList) {
		H264ProfilesSupportedList = h264ProfilesSupportedList;
	}

	/**
	 * @return the bitrateRange
	 */
	public BitrateRange getBitrateRange() {
		return BitrateRange;
	}

	/**
	 * @param bitrateRange the bitrateRange to set
	 */
	public void setBitrateRange(BitrateRange bitrateRange) {
		BitrateRange = bitrateRange;
	}

	public String toString() {
		String s = "H264:";
		String ra = "ResolutionsAvailableList=";
		Log.i("H264", "ResolutionsAvailableList=" + ResolutionsAvailableList);
		Log.i("H264", "ResolutionsAvailable=" + ResolutionsAvailable);
		ResolutionsAvailableList = getResolutionList();
		if (ResolutionsAvailableList != null && ResolutionsAvailableList.size() > 0) {
			for(ResolutionsAvailable p:ResolutionsAvailableList) {
				Log.i("H264", p.toString());
				ra += p.toString() + ";";
			}
		} else
			ra += ResolutionsAvailable;
		s += ra;
		s += ", GovLengthRange" + GovLengthRange;
		s += ", FrameRateRange" + FrameRateRange;
		s += ", EncodingIntervalRange" + EncodingIntervalRange;
		
		String ps = "H264ProfilesSupportedList=";
		H264ProfilesSupportedList = getH264ProfilesSupportedList();
		if (H264ProfilesSupportedList != null && H264ProfilesSupportedList.size() > 0) {
			for(String p:H264ProfilesSupportedList) {
				Log.i("H264", p.toString());
				ps += p.toString() + ";";
			}
		} else
			ps += H264ProfilesSupportedList;
		s += ps;
		
		return s;
	}
}
