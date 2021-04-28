package business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import controller.ListOnlineController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import model.Server;

//class nhận nhiệm vụ giao tiếp với server
public class ClientThread extends Thread {
	private Server server;
	private TextArea txtContent;
	private Socket socket;

	private DataOutputStream dos;
	private DataInputStream dis;
	private TextArea chatboxContent;
	
	public ClientThread(Server server, TextArea txtContent) {
		this.server = server;
		this.txtContent = txtContent;

		try {
			// connect thành công sẽ nhận dc socket dùng để giao tiếp với server
			socket = new Socket(server.getHostIP(), server.getPort());
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			while (true) {
				Object line = dis.readUTF();//đọc dữ liệu gửi về từ server
				if (line.toString().startsWith("$")) {//dòng dữ liệu có dấu $ tức là được gửi giữa client - client server là trung gian
					chatboxContent.appendText(line.toString().substring(1));
				}else if(line.toString().startsWith(":")) {
					String[] arr = line.toString().split(":");
					//update lai listView online cua tung client
					ObservableList<String> oList= FXCollections.observableArrayList(arr);
					oList.remove(0);
					ListOnlineController.ls.setItems(oList);
				
				}else{//ngược lại thì là tin nhắn của server gửi cho client
					txtContent.appendText("\n" + server.getHostIP() + ": " + line.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//hàm giao tiếp với server
	public void send(Object line) throws IOException {
		dos.writeUTF(line.toString());
		dos.flush();
		if (!line.toString().startsWith(":")) {
			txtContent.appendText("\nMe: " + line);
		}
	}
	
	//hàm giao tiếp với client
	public void sendForUser(Object line) throws IOException {
		dos.writeUTF(line.toString());
		dos.flush();
	}

	public TextArea getChatboxContent() {
		return chatboxContent;
	}

	public void setChatboxContent(TextArea chatboxContent) {
		this.chatboxContent = chatboxContent;
	}


	
	

}
