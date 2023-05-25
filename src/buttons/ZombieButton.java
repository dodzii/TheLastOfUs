package buttons;

import java.io.File;

import model.characters.Zombie;
import scenes.GameMapScene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;

public class ZombieButton extends Button{
	public ZombieButton(boolean flag,Zombie z) {
		super();
		if(flag){
		this.setPrefSize(80,80);
		this.setStyle("-fx-background-image: url('images/ZombieButton.jpeg');-fx-border-color: dimgrey; -fx-border-width: 1px;");
		ProgressBar hpBar = new ProgressBar();
		hpBar.setStyle("-fx-accent: green;");
		hpBar.setProgress(((double)z.getCurrentHp()/(double)z.getMaxHp()));
		hpBar.setMinHeight(0.5);
		hpBar.setPrefSize(50, 15);
		StackPane stack = new StackPane(this,hpBar);
		StackPane.setAlignment(hpBar, Pos.BOTTOM_CENTER);
		this.setGraphic(stack);
		}
		else{
			this.setPrefSize(80,80);
			this.setStyle("-fx-background-color: rgba(50, 50, 50, 0.975);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		}
		
		this.setOnAction(e->{
			AudioClip au = new AudioClip(new File("src/sounds/zombie.mp3").toURI().toString());
	        au.setCycleCount(1);
	        au.play();
			GameMapScene.curr.setTarget(z);
		});;
	}

}
