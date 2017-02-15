package SongLib;

import java.io.IOException;

import SongLib.view.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application {

	Stage mainStage;
	
	public void start(Stage stage) {
		mainStage = stage;
		mainStage.setTitle("Music Player");
	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/Interface.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			
			Controller controller = loader.getController();
			controller.setMainStage(mainStage);
			
			Scene scene = new Scene(pane, 500, 500);
			mainStage.setResizable(false);

			mainStage.setScene(scene);
			mainStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
