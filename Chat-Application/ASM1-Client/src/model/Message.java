package model;

public class Message {
	private String send;
	private String receive;
	private String content;

	public Message() {

	}

	public Message(String send, String recevie, String content) {
		this.send = send;
		this.receive = recevie;
		this.content = content;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
