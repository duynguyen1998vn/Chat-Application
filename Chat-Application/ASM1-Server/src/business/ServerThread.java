package business;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import controller.ServerController;
import model.Client;
import model.Server;

public class ServerThread extends Thread {

	private Server chatServer;
	private ServerSocket server;
	private Socket socket;
	public static HashMap<String, ClientHandler> clients = new HashMap<String, ClientHandler>();// danh sách user
																								// connect
	public static ArrayList<String> userOnlines = new ArrayList<String>();

	public ServerThread(Server chatServer) {
		this.chatServer = chatServer;

		try {
			server = new ServerSocket(chatServer.getPort()); // khởi tạo server
			System.out.println("Da tao server có port = " + server.getLocalPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			while (true) {
				socket = server.accept(); //chấp nhân kết nối từ client tạo 1 socket để giao tiếp client đó
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				
				String userName = dis.readUTF();
				Client c = new Client();
				//nếu tên client được gửi qua tồn tại khởi tạo đối tượng client vs tên và socket ở trên
				if (userName != null) {
					String nameClient = userName.substring(userName.indexOf(":") + 1); 
					c.setUserName(nameClient);
					c.setSocket(socket);
					//ListView thêm tên user vừa connect vào danh sách online ở phía server
					userOnlines.add(nameClient);
					ServerController.ls.getItems().add(nameClient);
					//tạo 1 clienthandler để giao tiếp vs client đó
					ClientHandler ch = new ClientHandler(socket, c);
					ch.start();
					clients.put(nameClient, ch);
					
					String listOnline = "";
					for(int i=0 ;i<userOnlines.size();i++) {
						listOnline += (":"+userOnlines.get(i));
					}
				
					//cũng cần cập nhập lại listview ở phía client
					Collection<ClientHandler> clientHandlers = clients.values();
					Iterator<ClientHandler> iterator = clientHandlers.iterator();
					while(iterator.hasNext()) {
						iterator.next().sendUserConnected(listOnline);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Server getChatServer() {
		return chatServer;
	}

}
