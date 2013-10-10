package lejos.hardware;

public class RemoteBTDevice {
	private String name;
	private byte[] address;
	
	public RemoteBTDevice(String name, byte[] address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public byte[] getDeviceAddress() {
		return address;
	}
	
	public String getAddress() {
		StringBuilder sb = new StringBuilder();
		for(int j=5;j>=0;j--) {
			String hex = Integer.toHexString(address[j] & 0xFF).toUpperCase();
			if (hex.length() == 1) sb.append('0');
			sb.append(hex);
			if (j>0) sb.append(':');
		}
		return sb.toString();
	}
}