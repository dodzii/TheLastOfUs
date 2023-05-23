package scenes;

import engine.GUI;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CreditsScene extends Scene {
	private String[] teamMembers = {"AAA Studios Team Members \n \n Abdelrahman Mohamed Talaat \n \n Ali Mahmoud Shokry \n\n  Abdullah Mahmoud Mohamed","Special Thanks To: \n\nProf. Slim Abdennadher\n\nDr. Nourhan Ehab\n\nOur TAs","All Rights Reserved To Naughty Dog"};
	public CreditsScene(Parent root) {
		
		super(root,1920,1080);
		BorderPane main = new BorderPane();
		main.setStyle("-fx-background-image: url('images/CreditsScene.jpg'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;");
		VBox namesl = new VBox();
        namesl.setAlignment(Pos.CENTER);
        namesl.setPadding(new Insets(30));
        VBox namesr = new VBox();
        namesr.setAlignment(Pos.CENTER_RIGHT);
        namesr.setPadding(new Insets(30));
        VBox namesb = new VBox();
        namesb.setAlignment(Pos.BOTTOM_CENTER);
        namesb.setPadding(new Insets(30));
        SequentialTransition creditsTransition = new SequentialTransition();
        int i=0;
        for (String teamMember : teamMembers) {
            Label label = new Label(teamMember);
            label.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; -fx-font-weight: 900;");
            TranslateTransition tt = null;
            if(i==0){
				namesl.getChildren().add(label);
				tt = new TranslateTransition((Duration.seconds(3)), label);
				tt.setFromY(800);
				tt.setToY(10);
				tt.setCycleCount(1);
            }
            else if(i==1){
            	namesr.getChildren().add(label);
                tt = new TranslateTransition((Duration.seconds(3)), label);
                tt.setFromY(800);
                tt.setToY(10);
                tt.setCycleCount(1);
            }
            else {
            	namesb.getChildren().add(label);
                tt = new TranslateTransition((Duration.seconds(2)), label);
                tt.setFromY(800);
                tt.setToY(30);
                tt.setCycleCount(1);
            }
            i++;
            creditsTransition.getChildren().addAll(tt);
        }
        creditsTransition.play();
        creditsTransition.setOnFinished(e->{
        	FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GUI.window.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e3 -> GUI.window.close());
        	PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e2 -> fadeOut.play()); // Close the window after the delay
            delay.play(); // Start the delay
        });
        main.setLeft(namesl);
        main.setRight(namesr);
        main.setBottom(namesb);
        BorderPane.setAlignment(namesr, Pos.CENTER_RIGHT);
		this.setRoot(main);
		GUI.window.setFullScreen(true);
	}

}
