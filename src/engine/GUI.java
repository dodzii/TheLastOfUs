package engine;

import scenes.BaseScene;
import scenes.GameLostScene;
import scenes.GameWonScene;
import scenes.InstructionScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application {
	public static Stage window;
	public void start(Stage primaryStage) {
		window=primaryStage;
		window.setTitle("The Last Of Us: AAA Legacy");
		window.setFullScreen(true);
		window.setScene(new BaseScene(new StackPane()));
		window.setFullScreenExitHint("");
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
