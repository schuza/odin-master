package net.floodlightcontroller.odinmaster;

import java.net.InetAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.odinmaster.IOdinAgent;
import net.floodlightcontroller.odinmaster.OdinClient;
import net.floodlightcontroller.util.MACAddress;

/**
 * 
 * Stub OdinAgent class to be used for testing. 
 * 
 * @author Lalith Suresh <suresh.lalith@gmail.com>
 *
 */
public class StubOdinAgent implements IOdinAgent {

	private IOFSwitch sw = null;
	private InetAddress ipAddr = null;
	private long lastHeard;
	private ConcurrentSkipListSet<OdinClient> clientList = new ConcurrentSkipListSet<OdinClient>();
	
	@Override
	public void addClientLvap(OdinClient oc) {
		clientList.add(oc);
	}

	@Override
	public InetAddress getIpAddress() {
		return ipAddr;
	}

	@Override
	public Map<String, Map<String, String>> getRxStats() {
		return null;
	}

	@Override
	public IOFSwitch getSwitch() {
		return sw;
	}

	@Override
	public ConcurrentSkipListSet<OdinClient> getLvapsRemote() {
		return clientList;
	}

	@Override
	public int init(InetAddress host) {
		this.ipAddr = host;
		
		return 0;
	}

	@Override
	public void removeClientLvap(OdinClient oc) {
		clientList.remove(oc);
	}

	@Override
	public void setSwitch(IOFSwitch sw) {
		this.sw = sw;
	}


	public long getLastHeard () {
		return lastHeard;
	} 
	
	public void setLastHeard (long t) {
		this.lastHeard = t;
	}

	@Override
	public Set<OdinClient> getLvapsLocal() {
		return clientList;
	}

	@Override
	public void setSubscriptions(String subscriptionList) {
		// Do nothing.
	}

	@Override
	public void updateClientLvap(OdinClient oc) {		
	}

	@Override
	public void sendProbeResponse(MACAddress clientHwAddr, MACAddress bssid,
			Set<String> ssidLists) {
	}
}