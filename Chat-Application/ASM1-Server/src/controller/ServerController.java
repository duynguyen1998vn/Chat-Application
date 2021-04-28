package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ServerController implements Initializable {
	@SuppressWarnings("rawtypes")
	@FXML
	public ListView listView;

	public static ListView ls;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ls = listView;
		ls.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ChatBox.fxml"));
					Parent root = loader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					
					stage.setTitle("Chat with " + newValue);
					ChatBoxController controller = loader.getController();
					controller.setUsername(newValue);
					stage.show();

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
	}

}
