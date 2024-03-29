package scenes;

import java.io.File;

import views.GUI;
import engine.Game;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameWonScene extends Scene {
		public static MediaPlayer m;
	public GameWonScene(Parent root) {
		super(root,1920,1080);
		this.setCursor(new ImageCursor(new Image("images/Cursor.png")));
		Media song = new Media(new File("src/sounds/WonScene.mp3").toURI().toString());
        m = new MediaPlayer(song);
        m.setCycleCount(MediaPlayer.INDEFINITE);
        m.play();
        InstructionScene.m.stop();
		VBox main = new VBox();
		main.setStyle("-fx-background-image: url('images/GameWonScene.png'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;-fx-border-style: none; -fx-focus-color: transparent;");
		Button exit = new Button("Exit");
		exit.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; -fx-font-weight: 900;");
		Button play = new Button("Play Again");
		play.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; -fx-font-weight: 900;");
		exit.setOnMouseEntered(e ->{
			exit.setStyle("-fx-border-radius: 100px;-fx-background-radius: 100px;-fx-border:3px;-fx-border-color: Red;-fx-background-color:Red;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: Black; -fx-font-weight: 900;");
		});
		exit.setOnMouseExited(e ->{
			exit.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:3px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; -fx-font-weight: 900;");
		});
		play.setOnMouseEntered(e ->{
			play.setStyle("-fx-border-radius: 100px;-fx-background-radius: 100px;-fx-border:3px;-fx-border-color: ghostwhite;-fx-background-color:ghostwhite;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: Black; -fx-font-weight: 900;");
		});
		play.setOnMouseExited(e ->{
			play.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:3px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; -fx-font-weight: 900;");
		});
		exit.setTranslateX(65);
		main.getChildren().addAll(play,exit);
		main.setAlignment(Pos.BOTTOM_LEFT);
		main.setSpacing(20);
		main.setPadding(new Insets(50));
		this.setRoot(main);
		GUI.window.setFullScreen(true);
		
		exit.setOnAction(e -> {
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
	        au.setCycleCount(1);
	        au.play();
			Game.availableHeroes.clear();
			Game.heroes.clear();
			Game.zombies.clear();

            CreditsScene o = new CreditsScene(new StackPane());o.setFill(Color.BLACK);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
      	
            	GUI.window.setScene(o);
            	fadeIn.play();
            	GUI.window.setFullScreen(true);	    			
            });

		play.setOnAction(e -> {
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
	        au.setCycleCount(1);
	        au.play();
			m.stop();
			Game.availableHeroes.clear();
			Game.heroes.clear();
			Game.zombies.clear();

            OpenScene o = new OpenScene(new StackPane());o.setFill(Color.BLACK);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
       	
            	GUI.window.setScene(o);
            	fadeIn.play();
            	GUI.window.setFullScreen(true);	    			
            });

		GUI.window.setFullScreen(true);
	}
	

}
