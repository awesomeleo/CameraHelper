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
 * @Title ProbeMatch.java 
 * @Package cn.ws.device.camera.onvif.probe 
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��17�� ����2:02:43 
 * @version V1.0   
 */
public class ProbeMatch {
	
	
	private EndpointReference EndpointReference;
	private String Types;
	private String Scopes;
	private String XAddrs;
	private int MetadataVersion;
	
	/**
	 * @return the endpointReference
	 */
	public EndpointReference getEndpointReference() {
		return EndpointReference;
	}

	/**
	 * @param endpointReference the endpointReference to set
	 */
	public void setEndpointReference(EndpointReference endpointReference) {
		EndpointReference = endpointReference;
	}

	/**
	 * @return the types
	 */
	public String getTypes() {
		return Types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String types) {
		Types = types;
	}

	/**
	 * @return the scopes
	 */
	public String getScopes() {
		return Scopes;
	}

	/**
	 * @param scopes the scopes to set
	 */
	public void setScopes(String scopes) {
		Scopes = scopes;
	}

	/**
	 * @return the xAddrs
	 */
	public String getXAddrs() {
		return XAddrs;
	}

	/**
	 * @param xAddrs the xAddrs to set
	 */
	public void setXAddrs(String xAddrs) {
		XAddrs = xAddrs;
	}

	/**
	 * @return the metadataVersion
	 */
	public int getMetadataVersion() {
		return MetadataVersion;
	}

	/**
	 * @param metadataVersion the metadataVersion to set
	 */
	public void setMetadataVersion(int metadataVersion) {
		MetadataVersion = metadataVersion;
	}

	public String toString() {
		String s = "ProbeMatche:";
		s += "EndpointReference=" + EndpointReference;
		s += ", Types=" + Types;
		s += ", Scopes=" + Scopes;
		s += ", XAddrs=" + XAddrs;
		s += ", MetadataVersion=" + MetadataVersion;
		return s;
	}
	
    @Override
    public boolean equals(Object o) {//ip may change, need compare
        if (o == null) return false;
        if (this == o) return true;
        if ((o instanceof ProbeMatch) == false) return false;
        ProbeMatch oth = (ProbeMatch)o;
        if (oth.XAddrs != null && XAddrs != null && oth.XAddrs.equals(XAddrs)) {
            return true;
        } else {
            return false;
        }
    }

}
