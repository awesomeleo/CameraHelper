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
 * @Title GetVecoResponse.java 
 * @Package cn.ws.device.camera.onvif.veco 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��15�� ����9:54:53 
 * @version V1.0   
 */
public class Options {
	private QualityRange QualityRange;
	private JPEG JPEG;
	private MPEG4 MPEG4;
	private H264 H264;
	/**
	 * @return the qualityRange
	 */
	public QualityRange getQualityRange() {
		return QualityRange;
	}
	/**
	 * @param qualityRange the qualityRange to set
	 */
	public void setQualityRange(QualityRange qualityRange) {
		QualityRange = qualityRange;
	}
	/**
	 * @return the jPEG
	 */
	public JPEG getJPEG() {
		return JPEG;
	}
	/**
	 * @param jPEG the jPEG to set
	 */
	public void setJPEG(JPEG jPEG) {
		JPEG = jPEG;
	}
	/**
	 * @return the mPEG4
	 */
	public MPEG4 getMPEG4() {
		return MPEG4;
	}
	/**
	 * @param mPEG4 the mPEG4 to set
	 */
	public void setMPEG4(MPEG4 mPEG4) {
		MPEG4 = mPEG4;
	}
	/**
	 * @return the h264
	 */
	public H264 getH264() {
		return H264;
	}
	/**
	 * @param h264 the h264 to set
	 */
	public void setH264(H264 h264) {
		H264 = h264;
	}
	
	public String toString() {
		String s = "Options: QualityRange=" + QualityRange
				+ ", JPEG=" + JPEG
				+ ", JPEG=" + JPEG
				+ ", H264=" + H264;
		return s;
	}
}
