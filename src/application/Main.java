package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent  root = FXMLLoader.load(getClass().getResource("/application/Main.fxml")) ;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Media Player");
			scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent doubleClicked) {
					// TODO Auto-generated method stub
					if(doubleClicked.getClickCount()==2)
					{
						primaryStage.setFullScreen(true);
					}
				}
				
			});
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
