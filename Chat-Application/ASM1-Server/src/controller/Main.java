package controller;

	
import business.ServerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Server;


public class Main extends Application {
	private static final String SERVER_NAME = "localhost";
	private static int PORT = 3333;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			//tại server mở listview danh sách user online và tạo 1 serverSocket 
			Parent root = FXMLLoader.load(this.getClass().getResource("/ui/ServerBox.fxml"));
			Scene scene = new Scene(root,150,350);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Client - Server");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Server server = new Server(SERVER_NAME,PORT);
			ServerThread serverThread = new ServerThread(server);
			serverThread.start();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}
