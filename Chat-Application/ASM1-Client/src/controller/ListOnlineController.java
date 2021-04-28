package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ListOnlineController implements Initializable {

	@SuppressWarnings("rawtypes")
	@FXML
	public ListView listViewOnline;
	
	public static ListView ls;
	public ListOnlineController() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ls = listViewOnline;
	}
}
