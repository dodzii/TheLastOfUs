package scenes;

import engine.GUI;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BaseScene extends Scene {

	public BaseScene(Parent root) {
		super(root,1920,1080);
		GUI.window.setFullScreen(true);
		Label loadingLabel = new Label("AAA Studios Presents...");
        loadingLabel.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
        loadingLabel.setAlignment(Pos.TOP_CENTER);
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(600), loadingLabel);
        tt.setAutoReverse(true);
        tt.setCycleCount(9);
        tt.setFromX(0);
        tt.setToX(5);
        tt.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(600), new KeyValue(loadingLabel.opacityProperty(), 0)));
        timeline.setAutoReverse(true);
        timeline.setCycleCount(9);
        timeline.play();
        loadingLabel.setTranslateY(-400);
        // Create a progress bar to display the loading progress
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
    	progressBar.setStyle("-fx-accent:black;-fx-pref-width: 200; -fx-pref-height: 40;");
        // Create a VBox to hold the label and progress bar
        VBox loadingBox = new VBox(progressBar);
        loadingBox.setAlignment(Pos.BOTTOM_CENTER);
        loadingBox.setSpacing(20);
        loadingBox.setTranslateY(400);
	
        // Create a stack pane to center the label
        VBox loadingPane = new VBox(loadingLabel,loadingBox);
        loadingPane.setAlignment(Pos.CENTER);
        loadingPane.setStyle("-fx-background-image: url('images/Studio.png'); "+"-fx-background-repeat: no-repeat; -fx-background-size: 100% 100%;");
		Task<Void> loadingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(30);
					final int progress = i + 1;
					Platform.runLater(() -> progressBar
							.setProgress(progress / 100.0));
				}
				return null;
			}
		};
		this.setRoot(loadingPane);
		new Thread(loadingTask).start();
		
		loadingTask.setOnSucceeded(event -> {
			this.setFill(Color.BLACK);
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.75), GUI.window.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.05);
            OpenScene o = new OpenScene(new StackPane());o.setFill(Color.BLACK);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeOut.setOnFinished(e3 -> {
            	GUI.window.setScene(o);
            	fadeIn.play();
    			GUI.window.setFullScreen(true);
            });
            
            
            fadeOut.play();
            
			});
	}


}
