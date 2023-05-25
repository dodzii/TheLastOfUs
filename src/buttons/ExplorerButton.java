package buttons;

import java.io.File;

import model.characters.Hero;
import scenes.GameMapScene;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class ExplorerButton extends HeroButton{
	public ExplorerButton(Hero h) {
		super();
		this.setPrefSize(80,80);
		this.setStyle("-fx-background-image: url('images/ExplorerButton.jpeg');-fx-border-color: dimgrey; -fx-border-width: 1px;");
		ProgressBar hpBar = new ProgressBar();
		hpBar.setStyle("-fx-accent: green;");
		hpBar.setProgress(((double)h.getCurrentHp()/(double)h.getMaxHp()));
		hpBar.setMinHeight(0.5);
		hpBar.setPrefSize(50, 15);
		StackPane stack = new StackPane(this,hpBar);
		StackPane.setAlignment(hpBar, Pos.BOTTOM_CENTER);
		this.setGraphic(stack);
		if(hpBar.getProgress()<(0.3)){
			hpBar.setStyle("-fx-accent: red;");
		}
		
		if(GameMapScene.curr==h){
			scaleTransition = new ScaleTransition(Duration.seconds(0.75), this);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

            
            scaleTransition.play();
		}
		
		this.setOnMouseClicked(e -> {
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
			au.setCycleCount(1);
			au.play();
			if(e.getClickCount() == 2){
				GameMapScene.alert=false;
				scaleTransition.stop();
	        	GameMapScene.curr = h;
	        	GameMapScene.updateLeftUp();
	        	scaleTransition = new ScaleTransition(Duration.seconds(0.75), this);
	            scaleTransition.setFromX(1);
	            scaleTransition.setFromY(1);
	            scaleTransition.setToX(1.1);
	            scaleTransition.setToY(1.1);
	            scaleTransition.setAutoReverse(true);
	            scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
	
	            
	            scaleTransition.play();
			}
			else if(e.getClickCount() == 1){
				GameMapScene.curr.setTarget(h);
			}
        });
	}

}
