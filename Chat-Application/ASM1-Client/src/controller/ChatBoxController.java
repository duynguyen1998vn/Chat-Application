package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBoxController implements Initializable {
	private String sender;
	private String recevie;
	
	@FXML
	private TextArea txtContent;
	@FXML
	private TextField txtMessage;


	public ChatBoxController() {
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void send(String message) {
		try {
			ClientController.clients.get(sender).sendForUser("$"+sender+":$"+recevie+":$"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendForUser(ActionEvent e) {
		send(txtMessage.getText());
		txtContent.appendText("\n me: "+txtMessage.getText());
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecevie() {
		return recevie;
	}

	public void setRecevie(String recevie) {
		this.recevie = recevie;
	}


	public TextArea getTxtContent() {
		return txtContent;
	}


	public void setTxtContent(TextArea txtContent) {
		this.txtContent = txtContent;
	}

	
}
