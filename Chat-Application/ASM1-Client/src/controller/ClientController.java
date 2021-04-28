package controller;

import java.io.IOException;
import java.util.HashMap;

import business.ClientThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Server;

public class ClientController {
	@FXML
	private TextArea txtContent;
	@FXML
	private TextField txtUsername, txtHostIP, txtPort, txtMessage;
	@FXML
	private Button btnConnect;
	@FXML
	private ChatBoxController chatboxController;
	ClientThread clientThread = null;
	public static HashMap<String, ClientThread> clients = new HashMap<String, ClientThread>();

	@SuppressWarnings("unchecked")
	public void btnConnect(ActionEvent e) {

		if (clientThread == null) {
			try {
				// connect vào server và gửi tên của mình tới server
				Client c = new Client(txtUsername.getText(), "");
				Server server = new Server(txtHostIP.getText(), Integer.parseInt(txtPort.getText()));
				clientThread = new ClientThread(server, txtContent);
				
				clientThread.send(":" + c.getUserName());
				txtContent.appendText("Connected to Server");
				btnConnect.setDisable(true);

				clients.put(c.getUserName(), clientThread);
				
				//mở danh sách các user đang online 
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ListUserOnline.fxml"));
				Parent root = loader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root, 150, 350));
				stage.show();
			
				//gán giá trị và bắt sự kiện chọn user gửi tin nhắn
				ListOnlineController listController = loader.getController();
				listController.listViewOnline.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
								chatboxController = new ChatBoxController();
								try {
									//khi click chọn hiển thị hộp thoại để chat 
									FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/ui/ChatBox.fxml"));
									Parent root2 = loader2.load();
									Stage stage = new Stage();
									stage.setScene(new Scene(root2, 400, 400));
									stage.setTitle("Chat With " + arg2);
									
									//Chatbox đó sẽ giữ giá trị người gửi người nhận và nội dung để sau này gửi tới server
									ChatBoxController chatbox = loader2.getController();
									chatbox.setSender(c.getUserName());
									chatbox.setRecevie(arg2);
									clientThread.setChatboxContent(chatbox.getTxtContent());
									
									stage.show();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		clientThread.start();
	}
	
	//hàm chat vs server tại giao diện client sau khi connect 
	public void btnSend(ActionEvent e) {
		try {
			clientThread.send(txtMessage.getText());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
