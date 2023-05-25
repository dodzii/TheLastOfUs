package scenes;

import java.io.File;

import engine.GUI;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class BaseScene extends Scene {

	public BaseScene(Parent root) {
		super(root,1920,1080);
		this.setCursor(new ImageCursor(new Image("images/Cursor.png")));
		GUI.window.setFullScreen(true);
		
		Label loadingLabel = new Label("AAA Studios Presents...");
        loadingLabel.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White");
        loadingLabel.setAlignment(Pos.TOP_CENTER);
        AudioClip au = new AudioClip(new File("src/sounds/BaseScene.mp3").toURI().toString());
        au.setCycleCount(1);
        au.play();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), 
                    new KeyValue(loadingLabel.translateXProperty(), 0),
                    new KeyValue(loadingLabel.opacityProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(0.1),
                    new KeyValue(loadingLabel.translateXProperty(), 2),
                    new KeyValue(loadingLabel.opacityProperty(), 0.8)
                ),
                new KeyFrame(Duration.seconds(0.25),
                    new KeyValue(loadingLabel.translateXProperty(), -2),
                    new KeyValue(loadingLabel.opacityProperty(), 0.6)
                ),
                new KeyFrame(Duration.seconds(0.35),
                    new KeyValue(loadingLabel.translateXProperty(), 4),
                    new KeyValue(loadingLabel.opacityProperty(), 0.4)
                ),
                new KeyFrame(Duration.seconds(0.45),
                    new KeyValue(loadingLabel.translateXProperty(), -4),
                    new KeyValue(loadingLabel.opacityProperty(), 0.2)
                ),
                new KeyFrame(Duration.seconds(0.55),
                    new KeyValue(loadingLabel.translateXProperty(), 0),
                    new KeyValue(loadingLabel.opacityProperty(), 1)
                )
            );

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        loadingLabel.setTranslateY(-400);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
    	progressBar.setStyle("-fx-accent:black;-fx-pref-width: 200; -fx-pref-height: 40;");

        VBox loadingBox = new VBox(progressBar);
        loadingBox.setAlignment(Pos.BOTTOM_CENTER);
        loadingBox.setSpacing(20);
        loadingBox.setTranslateY(400);
        
        VBox loadingPane = new VBox(loadingLabel,loadingBox);
        loadingPane.setAlignment(Pos.CENTER);
        loadingPane.setStyle("-fx-background-image: url('images/OpenScene.jpg'); "+"-fx-background-repeat: no-repeat; -fx-background-size: 100% 100%;");
		Task<Void> loadingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(150);
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

            OpenScene o = new OpenScene(new StackPane());o.setFill(Color.BLACK);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            	GUI.window.setScene(o);
            	fadeIn.play();
    			GUI.window.setFullScreen(true);

			});
		
	}


}