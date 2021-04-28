package model;

import java.net.Socket;

public class Client {
	private String userName;
	private String password;
	private Socket socket;
	public Client(String userName, String pass) {
		this.userName = userName;
		this.password = pass;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
}
