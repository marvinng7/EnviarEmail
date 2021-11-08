package dad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mvc.Controller;

public class App extends Application{
	
	public Controller controller;

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new Controller();
		
		Scene scene= new Scene(controller.getView(), 640,480);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/icon.png"));
		primaryStage.setTitle("Enviar Email");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
