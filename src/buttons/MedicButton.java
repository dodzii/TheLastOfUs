package buttons;

import java.awt.Point;

import engine.Game;
import scenes.GameMapScene;
import model.characters.Hero;
import model.world.CharacterCell;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MedicButton extends HeroButton{
	public MedicButton(Hero h) {
		super();
		this.setPrefSize(80,80);
		this.setStyle("-fx-background-image: url('images/MedicButton.jpeg');-fx-border-color: dimgrey; -fx-border-width: 1px;");
		ProgressBar hpBar = new ProgressBar();
		hpBar.setStyle("-fx-accent: green;");
		hpBar.setProgress(((double)h.getCurrentHp()/(double)h.getMaxHp()));
		hpBar.setMinHeight(0.5);
		hpBar.setPrefSize(50, 15);
		StackPane stack = new StackPane(this,hpBar);
		StackPane.setAlignment(hpBar, Pos.BOTTOM_CENTER);
		this.setGraphic(stack);
		if(GameMapScene.curr==h){
			scaleTransition= new ScaleTransition(Duration.seconds(0.75), this);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

            
            scaleTransition.play();
		}
	this.setOnMouseClicked(e -> {
			
			if(e.getClickCount() == 2){	
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