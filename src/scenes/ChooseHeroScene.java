package scenes;

import java.io.File;
import java.util.ArrayList;

import views.GUI;
import engine.Game;
import model.characters.*;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ChooseHeroScene extends Scene {
	static int i=0;
	static ImageView imageView;
	static ArrayList<Hero>herosTmp=new ArrayList<Hero>();
	static Hero curr;
	static Label stats; 
	static Label name;
	static Label maxHP;
	static Label attackDmg;
	static Label maxActions;
	static Label heroType;
	public ChooseHeroScene(Parent root) {
		super(root,1920,1080);
		this.setCursor(new ImageCursor(new Image("images/Cursor.png")));
		herosTmp.clear();
		for(int j=0;j<Game.availableHeroes.size();j++) {
			herosTmp.add(Game.availableHeroes.get(j));
		}
		curr = herosTmp.get(i);
		Label choose = new Label("When You're Lost In The Darkness Look For The Light "+ '\n' + "Believe In The FIREFLIES To Choose Your Hero!");
		choose.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		VBox north = new VBox(choose);
		HBox west = new HBox();
		Button left = new Button();
		Button right = new Button();
		left.setPrefWidth(100);
		left.setPrefHeight(100);
		right.setPrefWidth(100);
		right.setPrefHeight(100);
		
		left.setStyle("-fx-background-image: url('images/Left.jpg'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;-fx-border-style: none; -fx-focus-color: black;-fx-faint-focus-color: black;");
		right.setStyle("-fx-background-image: url('images/Right.jpg'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;-fx-border-style: none; -fx-focus-color: black;-fx-faint-focus-color: black;");
		imageView = new  ImageView(getImage(curr));
		west.getChildren().addAll(left,imageView,right);
		west.setAlignment(Pos.CENTER);
		west.setPadding(new Insets(30));
		west.setSpacing(10);
		VBox middle = new VBox();
		stats = new Label("Hero Stats:");stats.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		name = new Label("Name: " + curr.getName());name.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		heroType = new Label("Hero Type: " + curr.getClass().getSimpleName());heroType.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		maxHP = new Label("Max HP: " + curr.getMaxHp());maxHP.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		attackDmg = new Label("Attack Damage: " + curr.getAttackDmg());attackDmg.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		maxActions = new Label("Max Actions: " + curr.getMaxActions());maxActions.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 45; -fx-text-fill: White; -fx-font-weight: 900;");
		middle.getChildren().addAll(stats,name,heroType,maxHP,attackDmg,maxActions);
		middle.setSpacing(10);
		middle.setPadding(new Insets(30));
		middle.setAlignment(Pos.CENTER_LEFT);
		left.setOnAction(e -> {
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
	        au.setCycleCount(1);
	        au.play();
			int tmp = i-1;
			if(tmp<0) i = tmp + herosTmp.size();
			else i=i-1;			
			curr = herosTmp.get(i);
			imageView.setImage(getImage(curr));
			stats.setText("Hero Stats:");
			name.setText("Name: " + curr.getName());
			heroType.setText("Hero Type: " +curr.getClass().getSimpleName());
			maxHP.setText("Max HP: " + curr.getMaxHp());
			attackDmg.setText("Attack Damage: " + curr.getAttackDmg());
			maxActions.setText("Max Actions: " + curr.getMaxActions());
		});
		right.setOnAction(e -> {
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
	        au.setCycleCount(1);
	        au.play();
			i=((i+1)%(herosTmp.size()));
			curr = herosTmp.get(i);
			imageView.setImage(getImage(curr));
			imageView.setImage(getImage(curr));
			stats.setText("Hero Stats:");
			name.setText("Name: " + curr.getName());
			heroType.setText("Hero Type: " +curr.getClass().getSimpleName());
			maxHP.setText("Max HP: " + curr.getMaxHp());
			attackDmg.setText("Attack Damage: " + curr.getAttackDmg());
			maxActions.setText("Max Actions: " + curr.getMaxActions());
		});
		this.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ESCAPE){
				GUI.window.close();
				System.exit(0);
			}
			else if(e.getCode()==KeyCode.RIGHT){
				i=((i+1)%(herosTmp.size()));
				curr = herosTmp.get(i);
				imageView.setImage(getImage(curr));
				imageView.setImage(getImage(curr));
				stats.setText("Hero Stats:");
				name.setText("Name: " + curr.getName());
				heroType.setText("Hero Type: " +curr.getClass().getSimpleName());
				maxHP.setText("Max HP: " + curr.getMaxHp());
				attackDmg.setText("Attack Damage: " + curr.getAttackDmg());
				maxActions.setText("Max Actions: " + curr.getMaxActions());
			}
			else if(e.getCode()==KeyCode.LEFT){
				int tmp = i-1;
				if(tmp<0) i = tmp + herosTmp.size();
				else i=i-1;			
				curr = herosTmp.get(i);
				imageView.setImage(getImage(curr));
				stats.setText("Hero Stats:");
				name.setText("Name: " + curr.getName());
				heroType.setText("Hero Type: " +curr.getClass().getSimpleName());
				maxHP.setText("Max HP: " + curr.getMaxHp());
				attackDmg.setText("Attack Damage: " + curr.getAttackDmg());
				maxActions.setText("Max Actions: " + curr.getMaxActions());
			}
			else if(e.getCode()==KeyCode.ENTER){
				Game.startGame(curr);

	            InstructionScene o = new InstructionScene(new StackPane());o.setFill(Color.BLACK);
	            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
	            fadeIn.setFromValue(0.0);
	            fadeIn.setToValue(1.0);
            	
	            	GUI.window.setScene(o);
	            	fadeIn.play();
	            	GUI.window.setFullScreen(true);	    			

			}
		});
		HBox south = new HBox();
		Button transfer = new Button("Confirm & Start The Game");
		transfer.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; -fx-font-weight: 900;");
		south.getChildren().add(transfer);
		south.setAlignment(Pos.CENTER);
		south.setPadding(new Insets(20));
		
		transfer.setOnMouseEntered(e ->{
			transfer.setStyle("-fx-border-radius: 100px;-fx-background-radius: 100px;-fx-border:3px;-fx-border-color: ghostwhite;-fx-background-color:ghostwhite;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: Black; -fx-font-weight: 900;");
		});
		transfer.setOnMouseExited(e ->{
			transfer.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:3px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White; -fx-font-weight: 900;");
		});
		transfer.setOnAction(e -> {
			Game.startGame(curr);
			AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
			au.setCycleCount(1);
			au.play();
            InstructionScene o = new InstructionScene(new StackPane());o.setFill(Color.BLACK);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), o.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
          	
            	GUI.window.setScene(o);
            	fadeIn.play();
            	GUI.window.setFullScreen(true);	    			

		});
		
		BorderPane main = new BorderPane();
		main.setTop(north);
		main.setLeft(west);
		main.setCenter(middle);
		main.setBottom(south);
		main.setStyle("-fx-background-image: url('images/ChooseHero.png'); "+"-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;");
		this.setRoot(main);
		GUI.window.setFullScreen(true);
		
	}

	public static Image getImage(Hero curr) {
		Image image=null;
		if(curr.getName().equals("Joel Miller")) image=new Image("images/Joel.png");
		else if(curr.getName().equals("Ellie Williams")) image = new Image("images/Ellie.png");
		else if(curr.getName().equals("Tess")) image = new Image("images/Tess.png");
		else if(curr.getName().equals("Riley Abel")) image = new Image("images/Riley_Abel.png");
		else if(curr.getName().equals("Tommy Miller")) image = new Image("images/Tommy_Seattle.png");
		else if(curr.getName().equals("Bill")) image = new Image("images/bill.png");
		else if(curr.getName().equals("David")) image = new Image("images/David.png");
		else if(curr.getName().equals("Henry Burell")) image = new Image("images/henry.png");
		else if(curr instanceof Fighter) image=new Image("images/ChooseFighter.png");
		else if(curr instanceof Medic) image=new Image("images/ChooseMedic.png");
		else if(curr instanceof Explorer) image=new Image("images/ChooseExplorer.png");
		
		
		return image;
	}
}
