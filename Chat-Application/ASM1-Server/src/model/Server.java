package model;

public class Server {
	private String hostIP;
	private int port;

	public Server(String ip, int port) {
		this.hostIP = ip;
		this.port = port;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
