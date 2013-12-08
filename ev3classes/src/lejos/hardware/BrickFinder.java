package lejos.hardware;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lejos.hardware.ev3.LocalEV3;
import lejos.internal.ev3.EV3DeviceManager;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.nxt.RemoteNXT;

public class BrickFinder {
	private static final int DISCOVERY_PORT = 3016;
	private static Brick defaultBrick, localBrick;
	
	public static Brick getLocal() {
		if (localBrick != null) return localBrick;
		// Check we are running on an EV3
		EV3DeviceManager.getLocalDeviceManager();
		localBrick = LocalEV3.get();
		return localBrick;
	}
	
	public static Brick getDefault() {
		if (defaultBrick != null) return defaultBrick;
		try {
			// See if we are running on an EV3
			EV3DeviceManager.getLocalDeviceManager();
			defaultBrick =  LocalEV3.get();
			return defaultBrick;
		} catch (UnsupportedOperationException e) {
			try {
				BrickInfo[] bricks = discover();
				if (bricks.length > 0) {
					defaultBrick = new RemoteEV3(bricks[0].getIPAddress());
					return defaultBrick;
				} else {
					// No EV3s, look for a NXT
				    bricks = discoverNXT();
				    if (bricks.length > 0) {
						defaultBrick = new RemoteNXT(bricks[0].getName(), Bluetooth.getNXTCommConnector());
						return defaultBrick;
				    } else throw new DeviceException("No brick found");
				}
			} catch (Exception e1) {
				throw new DeviceException("Error finding remote bricks", e1);
			}
		}
	}

	/**
	 * Search for available EV3s and populate table with results.
	 */
	public static BrickInfo[] discover() throws Exception {	
		
		Map<String,BrickInfo> ev3s = new HashMap<String,BrickInfo>();
		DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT);
		socket.setSoTimeout(2000);
        DatagramPacket packet = new DatagramPacket (new byte[100], 100);

        long start = System.currentTimeMillis();
        
        while ((System.currentTimeMillis() - start) < 2000) {
            socket.receive (packet);
            String message = new String(packet.getData(), "UTF-8");
            String ip = packet.getAddress().getHostAddress();
            ev3s.put(ip, new BrickInfo(message.trim(),ip,"EV3"));
        }
        
        if (socket != null) socket.close();
        
        BrickInfo[] devices = new BrickInfo[ev3s.size()];
        int i = 0;
        for(String ev3: ev3s.keySet()) {
        	BrickInfo info = ev3s.get(ev3);
        	devices[i++] = info;
        }
        
        return devices;
	}
	
	public static BrickInfo[] discoverNXT() {
		try {
			Collection<RemoteBTDevice> nxts = Bluetooth.getLocalDevice().search();
			BrickInfo[] bricks = new BrickInfo[nxts.size()];
			int i = 0;
			for(RemoteBTDevice d: nxts) {
				BrickInfo b = new BrickInfo(d.getName(), d.getAddress(), "NXT");
				bricks[i++] = b;
			}
			return bricks;
		} catch (IOException e) {
			throw new DeviceException("Error finding remote NXTs", e);
		}
	}
}
