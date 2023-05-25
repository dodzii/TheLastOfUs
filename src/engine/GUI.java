package engine;

import scenes.BaseScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application {
	public static Stage window;
	public void start(Stage primaryStage) {
		window=primaryStage;
		window.getIcons().add(new Image("images/starfire.jpg"));
		window.setTitle("The Last Of Us: AAA Legacy");
		window.initStyle(StageStyle.UNDECORATED);
		window.setFullScreen(true);
		window.setScene(new BaseScene(new StackPane()));
		window.setFullScreenExitHint("");
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}