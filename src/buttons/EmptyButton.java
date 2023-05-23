package buttons;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;

public class EmptyButton extends Button{
	public EmptyButton(boolean flag) {
		super();
		if(flag){
		this.setPrefSize(80,80);
		this.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		}
		else{
			this.setPrefSize(80,80);
			this.setStyle("-fx-background-color: rgba(50, 50, 50, 0.975);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		}
	}

}

