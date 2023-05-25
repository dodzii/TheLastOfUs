package scenes;

import java.io.File;

import engine.GUI;
import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class InstructionScene extends Scene {
	public static MediaPlayer m;
	public InstructionScene(Parent root) {
		super(root,1920,1080);
		this.setCursor(new ImageCursor(new Image("images/Cursor.png")));
		BorderPane main = new BorderPane();
        main.setStyle("-fx-background-image: url('images/Instruction.jpg'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;");

        HBox arr = new HBox();
        Image arrow = new Image("images/arrows.PNG");
        ImageView arrimg = new ImageView(arrow);
        Label move = new Label(": Move");move.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        move.setTranslateY(55);
        arr.getChildren().addAll(arrimg,move);arr.setSpacing(20);
        
        HBox a = new HBox();
        ImageView aimg = new ImageView(new Image("images/A.PNG"));
        Label attack = new Label(": Attack");attack.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        attack.setTranslateY(10);
        a.getChildren().addAll(aimg,attack);a.setSpacing(20);
        
        HBox e = new HBox();
        ImageView eimg = new ImageView(new Image("images/E.PNG"));
        Label end = new Label(": End Turn");end.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        end.setTranslateY(10);
        e.getChildren().addAll(eimg,end);e.setSpacing(20);
        
        HBox c = new HBox();
        ImageView cimg = new ImageView(new Image("images/C.PNG"));
        Label cure = new Label(": Cure A Zombie");cure.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        cure.setTranslateY(10);
        c.getChildren().addAll(cimg,cure);c.setSpacing(20);
        
        HBox u = new HBox();
        ImageView uimg = new ImageView(new Image("images/S.png"));
        Label use = new Label(": Use Special Action");use.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        use.setTranslateY(10);
        u.getChildren().addAll(uimg,use);u.setSpacing(20);
        
        Label one = new Label("*Click On A Character Once To Set The Target");one.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        Label twice = new Label("*Click On A Hero Twice To Set The Controlled Hero");twice.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        Label inst = new Label("Instructions");inst.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; ; -fx-background-color: transparent"); 
        VBox left = new VBox(30,inst,arr,a,e,c,u,one,twice);
        left.setPadding(new Insets(60));left.setStyle("-fx-background-color: rgba(50, 50, 50, 0.75);-fx-border-color: dimgrey; -fx-border-width: 0px;");
        
        Label quote = new Label("\"You Keep Finding Something To Fight For\""+'\n'+"-Joel Miller");quote.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; ; -fx-background-color: transparent");
        quote.setTranslateY(350);
        Label cont = new Label("Press The SpaceBar To Continue");cont.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; ; -fx-background-color: transparent");
        cont.setTranslateY(650);
        ScaleTransition scaleTransition= new ScaleTransition(Duration.seconds(0.75), cont);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.play();
        VBox right = new VBox(50,quote,cont);right.setStyle("-fx-background-color: rgba(50, 50, 50, 0.75);-fx-border-color: dimgrey; -fx-border-width: 0px;");
        right.setPadding(new Insets(60));
        
        
        VBox center = new VBox();center.setStyle("-fx-background-color: rgba(50, 50, 50, 0.75);-fx-border-color: dimgrey; -fx-border-width: 0px;");
        
        main.setCenter(center);
        main.setLeft(left);
        main.setRight(right);
        main.setPadding(new Insets(25));
        BorderPane.setAlignment(left, Pos.BOTTOM_LEFT);
        this.setRoot(main);
        this.setOnKeyPressed(e1 -> {
        	if(e1.getCode() == KeyCode.SPACE){        		
        		Media song = new Media(new File("src/sounds/MapScene.mp3").toURI().toString());
                m = new MediaPlayer(song);
                m.setCycleCount(MediaPlayer.INDEFINITE);
                m.play();
                OpenScene.m.stop();

	            GameMapScene o = new GameMapScene(new StackPane());o.setFill(Color.BLACK);
	            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
	            fadeIn.setFromValue(0.0);
	            fadeIn.setToValue(1.0);
            	
	            	GUI.window.setScene(o);
	            	fadeIn.play();
	            	GUI.window.setFullScreen(true);	    			

        	}
		});
	}

}

