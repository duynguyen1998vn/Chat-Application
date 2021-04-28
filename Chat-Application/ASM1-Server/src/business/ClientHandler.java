package business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;

import javafx.scene.control.TextArea;
import model.Client;

//class server giao tiếp vs client
public class ClientHandler extends Thread {

	private TextArea txtContent;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Client client;

	public ClientHandler(Socket socket, Client c) throws IOException {
		this.socket = socket;
		this.client = c;
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {

			while (true) {
				// đọc dữ liệu từ client nếu có bắt đầu $ tức là user đó đang muốn giao tiếp với
				// 1 user khác
				Object line = dis.readUTF();
				if (line.toString().startsWith("$")) {
					String sender = "";
					String recevie = "";
					String message = "";
					String[] arr = line.toString().split(":");
					sender = arr[0].substring(1);
					recevie = arr[1].substring(1);
					message = arr[2].substring(1);

					// lấy clientHandler của người nhận và dán tin nhắn vào khung chat
					ServerThread.clients.get(recevie).sendForUser(sender + ": " + message);
				} else if (line != null) {
					txtContent.appendText("\n" + client.getUserName() + ": " + line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//gửi tên user vừa connect cập nhập lại tất cả listview online ở phía client
	public void sendUserConnected(Object line) throws IOException {
		dos.writeUTF(line.toString());
		dos.flush();
	}

	// gửi tin nhắn cho client từ phía server
	public void send(Object line) throws IOException {
		dos.writeUTF(line.toString());
		txtContent.appendText("\nMe: " + line.toString());
	}

	// gửi tin nhắn cho client2 theo yêu cầu client1
	public void sendForUser(Object line) throws IOException {
		System.out.println("send" + line);
		dos.writeUTF("$" + "\n" + line.toString());
		dos.flush();
	}

	public TextArea getTxtContent() {
		return txtContent;
	}

	public void setTxtContent(TextArea txtContent) {
		this.txtContent = txtContent;
	}

}
