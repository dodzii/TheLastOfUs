package buttons;

import model.collectibles.Supply;
import javafx.scene.control.Button;

public class SupplyButton extends Button{
	public SupplyButton(boolean flag) {
		super();
		if(flag){
		this.setPrefSize(80,80);
		this.setStyle("-fx-background-image: url('images/SupplyButton.jpeg');-fx-border-color: dimgrey; -fx-border-width: 1px;");
		}
		else{
			this.setPrefSize(80,80);
			this.setStyle("-fx-background-color: rgba(50, 50, 50, 0.975);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		}
		
	}

}
