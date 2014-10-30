package cn.wp.device.camera.onvif.response;

import cn.wp.device.camera.onvif.probe.DiscoveryHeader;
import cn.wp.device.camera.onvif.probe.ProbeMatch;

public class DiscoveryResponse {
	
	private DiscoveryHeader discoveryHeader;
	private ProbeMatch ProbeMatch;//should be ProbeMatches, now support 1 only
	/**
	 * @return the discoveryHeader
	 */
	public DiscoveryHeader getDiscoveryHeader() {
		return discoveryHeader;
	}
	/**
	 * @param discoveryHeader the discoveryHeader to set
	 */
	public void setDiscoveryHeader(DiscoveryHeader discoveryHeader) {
		this.discoveryHeader = discoveryHeader;
	}
	/**
	 * @return the probeMatch
	 */
	public ProbeMatch getProbeMatch() {
		return ProbeMatch;
	}
	/**
	 * @param probeMatch the probeMatch to set
	 */
	public void setProbeMatch(ProbeMatch probeMatch) {
		ProbeMatch = probeMatch;
	}
	
}
