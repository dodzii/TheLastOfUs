package scenes;

import java.io.File;

import engine.GUI;
import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class OpenScene extends Scene {
	public static MediaPlayer m; 
	public OpenScene(Parent root) {
		super(root,1920,1080);
		GUI.window.setFullScreen(true);
		StackPane main = new StackPane();
        main.setStyle("-fx-background-image: url('images/MainScene.jpg'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;");
        main.setAlignment(Pos.BOTTOM_CENTER);
        Label click = new Label("Press SpaceBar To Continue");
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.75), click);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);


        scaleTransition.play();
        click.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
        main.getChildren().add(click);
        Media song = new Media(new File("src/sounds/OpenScene.mp3").toURI().toString());
        m = new MediaPlayer(song);
        m.setCycleCount(MediaPlayer.INDEFINITE);
        m.play();
        this.setRoot(main);
		this.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.SPACE){
				try {
					Game.loadHeroes("Heros.csv");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

	            ChooseHeroScene o = new ChooseHeroScene(new StackPane());o.setFill(Color.BLACK);
	            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
	            fadeIn.setFromValue(0.0);
	            fadeIn.setToValue(1.0);

	            	GUI.window.setScene(o);
	            	fadeIn.play();
	    			GUI.window.setFullScreen(true);

			
			}});
		}


}
