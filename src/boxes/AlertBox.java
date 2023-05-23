package boxes;
import scenes.GameMapScene;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox extends VBox{
	public AlertBox(String message) {
		Label watch = new Label("Watch Out!");watch.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: Red; -fx-font-weight: 900;");
		Label msg = new Label(message);msg.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 20; -fx-text-fill: White; -fx-font-weight: 900;");
		Button close = new Button("Continue");close.setStyle("-fx-border-radius: 50px;-fx-background-color: transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; -fx-font-weight: 900;");
		this.getChildren().addAll(watch,msg,close);
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER);
		close.setOnMouseEntered(e ->{
			close.setStyle("-fx-border-radius: 100px;-fx-background-radius: 100px;-fx-border:3px;-fx-border-color: ghostwhite;-fx-background-color:ghostwhite;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: Black; -fx-font-weight: 900;");
		});
		close.setOnMouseExited(e ->{
			close.setStyle("-fx-border-radius: 50px;-fx-background-color: transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; -fx-font-weight: 900;");
		});
		close.setOnAction(e ->{
			GameMapScene.updateLeftUp();
		});
	}
}
