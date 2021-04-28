package controller;

import business.ClientHandler;
import business.ServerThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBoxController {
	@FXML
	private TextArea txtContent;
	@FXML
	private TextField txtMessage;

	private String userName;

	private ClientHandler ch;

	public void setUsername(String userName) {
		this.userName = userName;
		ch = ServerThread.clients.get(userName);
		ch.setTxtContent(txtContent);
	
	}

	public void btnSent(ActionEvent e) {
		try {
			ch.send(txtMessage.getText());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}

}
