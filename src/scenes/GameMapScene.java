package scenes;

import java.io.File;


import views.GUI;
import boxes.AlertBox;
import buttons.EmptyButton;
import buttons.ExplorerButton;
import buttons.FighterButton;
import buttons.MedicButton;
import buttons.SupplyButton;
import buttons.VaccineButton;
import buttons.ZombieButton;
import labels.HeroLabel;
import model.characters.*;
import model.collectibles.*;
import model.world.*;
import engine.*;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import exceptions.*;

public class GameMapScene extends Scene {
	public static boolean alert;
	public static Hero curr;
	public static VBox leftUp;
	public static VBox leftDown;
	public static GridPane map;
	public static BorderPane main;
	public static boolean flag = true;
	public static boolean cheat = true;

	public GameMapScene(Parent root) {
		super(root, 1920, 1080);
		this.setCursor(new ImageCursor(new Image("images/Cursor.png")));
		curr = Game.heroes.get(0);
		map = new GridPane();
		updateMap();
		map.setAlignment(Pos.CENTER_RIGHT);
		map.setPadding(new Insets(10));

		VBox left = new VBox();

		leftUp = new VBox();
		HeroLabel h = new HeroLabel(curr);
		Label l = new Label("Controlled Hero");
		l.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
		leftUp.getChildren().addAll(l, h);
		leftUp.setAlignment(Pos.TOP_CENTER);
		leftUp.setPadding(new Insets(20));
		leftUp.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		ScaleTransition scaleTransition = new ScaleTransition(
				Duration.seconds(0.85), leftUp);
		scaleTransition.setFromX(1);
		scaleTransition.setFromY(1);
		scaleTransition.setToX(1.05);
		scaleTransition.setToY(1.05);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
		scaleTransition.play();

		leftDown = new VBox();
		Label ld = new Label("Acquired Heroes");
		ld.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
		leftDown.getChildren().add(ld);
		for (Hero hero1 : Game.heroes) {
			HeroLabel hl = new HeroLabel(hero1);
			hl.setStyle("-fx-border-color: dimgrey; -fx-border-width: 1px;");
			leftDown.getChildren().add(hl);
		}
		leftDown.setAlignment(Pos.BOTTOM_CENTER);
		leftDown.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);-fx-border-color: dimgrey; -fx-border-width: 1px;");

		ScrollPane scrollPanel = new ScrollPane();
		scrollPanel.setContent(leftDown);
		scrollPanel.setPadding(new Insets(20));
		scrollPanel
				.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
		scrollPanel.setVbarPolicy(ScrollBarPolicy.NEVER);

		left.getChildren().addAll(leftUp, scrollPanel);
		left.setSpacing(50);
		left.setPadding(new Insets(20));

		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.E) {
				try {
					cheat = true;
					alert = false;
					Game.endTurn();
					AudioClip au = new AudioClip(new File(
							"src/sounds/endturn.mp3").toURI().toString());
					au.setCycleCount(1);
					au.play();
					updateMap();
					updateLeftUp();
					updateLeftDown();
					if (Game.checkGameOver()) {
						if (Game.checkWin()) {
							AlertBox a = new AlertBox("Congrats!  You  Won!");
							a.getChildren()
									.get(1)
									.setStyle(
											"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
							a.getChildren().remove(0);
							a.getChildren().remove(1);
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
							PauseTransition pause = new PauseTransition(
									Duration.seconds(1.5));
							pause.play();
							pause.setOnFinished(e4 -> {

								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);

								GUI.window.setScene(o);
								fadeIn.play();
								GUI.window.setFullScreen(true);
							});

						} else {
							AlertBox a = new AlertBox(
									"Unfortunately  You  Lost!");
							a.getChildren()
									.get(1)
									.setStyle(
											"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 50; -fx-text-fill: White; -fx-font-weight: 900;");
							a.getChildren().remove(2);
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
							PauseTransition pause = new PauseTransition(
									Duration.seconds(1.5));
							pause.play();
							pause.setOnFinished(e4 -> {

								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeIn.play();
								GUI.window.setScene(o);

								GUI.window.setFullScreen(true);
							});

						}
					}
				} catch (Exception e1) {

				}
			}
			if (alert) {
				if (e.getCode() == KeyCode.ESCAPE) {
					GUI.window.close();
					System.exit(0);
				} else if (e.getCode() == KeyCode.ENTER) {
					GameMapScene.alert = false;
					GameMapScene.updateLeftUp();
				}
			} else {
				if (e.getCode() == KeyCode.ESCAPE) {
					GUI.window.close();
					System.exit(0);
				} else if (e.getCode() == KeyCode.UP) {
					try {
						int tmpBefore = curr.getCurrentHp();
						curr.move(Direction.UP);
						int tmpAfter = curr.getCurrentHp();

						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (tmpBefore > tmpAfter) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/trap.mp3").toURI().toString());
							au.setCycleCount(1);
							au.play();
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox(
										"Congrats  You  Won!!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"You Have Died Because Of A Trap Cell \n Unfortunately  You  Lost!! ");
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (MovementException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						a.setAlignment(Pos.CENTER);
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
						leftUp.setAlignment(Pos.CENTER);
					} catch (NotEnoughActionsException e2) {
						alert = true;
						AlertBox a = new AlertBox(e2.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				} else if (e.getCode() == KeyCode.DOWN) {
					try {
						int tmpBefore = curr.getCurrentHp();
						curr.move(Direction.DOWN);
						int tmpAfter = curr.getCurrentHp();

						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (tmpBefore > tmpAfter) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/trap.mp3").toURI().toString());
							au.setCycleCount(1);
							au.play();
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox("Cograts  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"You Have Died Because Of A Trap Cell \n Unfortunately  You  Lost!! ");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (MovementException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						a.setAlignment(Pos.CENTER);
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
						leftUp.setAlignment(Pos.CENTER);
					} catch (NotEnoughActionsException e2) {
						alert = true;
						AlertBox a = new AlertBox(e2.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				} else if (e.getCode() == KeyCode.LEFT) {
					try {
						int tmpBefore = curr.getCurrentHp();
						curr.move(Direction.LEFT);
						int tmpAfter = curr.getCurrentHp();

						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (tmpBefore > tmpAfter) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/trap.mp3").toURI().toString());
							au.setCycleCount(1);
							au.play();
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox("Congrats  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"You Have Died Because Of A Trap Cell \n Unfortunately  You  Lost!! ");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (MovementException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						a.setAlignment(Pos.CENTER);
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
						leftUp.setAlignment(Pos.CENTER);
					} catch (NotEnoughActionsException e2) {
						alert = true;
						AlertBox a = new AlertBox(e2.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				} else if (e.getCode() == KeyCode.RIGHT) {
					try {
						int tmpBefore = curr.getCurrentHp();
						curr.move(Direction.RIGHT);
						int tmpAfter = curr.getCurrentHp();

						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (tmpBefore > tmpAfter) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/trap.mp3").toURI().toString());
							au.setCycleCount(1);
							au.play();
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox("Congrats  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"You Have Died Because Of A Trap Cell \n You Lost!! ");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (MovementException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						a.setAlignment(Pos.CENTER);
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
						leftUp.setAlignment(Pos.CENTER);
					} catch (NotEnoughActionsException e2) {
						alert = true;
						AlertBox a = new AlertBox(e2.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				}

				else if (e.getCode() == KeyCode.C) {
					try {
						curr.cure();
						AudioClip au = new AudioClip(new File(
								"src/sounds/cure.mp3").toURI().toString());
						au.setCycleCount(1);
						au.play();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox("Congrats  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"Unfortunately  You  Lost!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (GameActionException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				}

				else if (e.getCode() == KeyCode.A) {
					try {
						curr.attack();
						AudioClip au = new AudioClip(new File(
								"src/sounds/attacksound.mp3").toURI()
								.toString());
						au.setCycleCount(1);
						au.play();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox("Congrats  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"Unfortunately  You  Lost!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);
									fadeIn.play();
									GUI.window.setScene(o);

									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (GameActionException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				} else if (e.getCode() == KeyCode.S) {
					try {
						if (curr instanceof Medic) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/heal.mp3").toURI().toString());
							au.setCycleCount(1);
							au.play();
						}
						if (curr instanceof Explorer) {
							AudioClip au = new AudioClip(new File(
									"src/sounds/explorer.mp3").toURI()
									.toString());
							au.setCycleCount(1);
							au.play();
						}
						curr.useSpecial();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								AlertBox a = new AlertBox(
										"Congrats!  You  Won!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(0);
								a.getChildren().remove(1);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameWonScene o = new GameWonScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							} else {
								AlertBox a = new AlertBox(
										"Unfortunately  You  Lost!");
								a.getChildren()
										.get(1)
										.setStyle(
												"-fx-font-family: Papyrus, fantasy ; -fx-font-size: 40; -fx-text-fill: White; -fx-font-weight: 900;");
								a.getChildren().remove(2);
								leftUp.getChildren().clear();
								leftUp.getChildren().add(a);
								PauseTransition pause = new PauseTransition(
										Duration.seconds(1.5));
								pause.play();
								pause.setOnFinished(e4 -> {

									GameLostScene o = new GameLostScene(
											new StackPane());
									o.setFill(Color.BLACK);
									FadeTransition fadeIn = new FadeTransition(
											Duration.seconds(0.75), o.getRoot());
									fadeIn.setFromValue(0.0);
									fadeIn.setToValue(1.0);

									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});

							}
						}
					} catch (GameActionException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				}
				
				
				//There is nothing to find below this line 
				//---------------------------------------------------------------------
				
				
				
				//DO NOT SCROLL DOWN MORE!!!!
				
				
				
				
				
				
				
				
				else if (cheat==true&&(e.getCode()==KeyCode.NUMPAD0||e.getCode()==KeyCode.DIGIT0)){
					cheat = false;
					HBox h2 = new HBox();h2.setAlignment(Pos.CENTER);h2.setSpacing(50);h2.setTranslateY(15);
					TextArea t = new TextArea();t.setMinHeight(5);t.setPrefSize(300,5);t.setPromptText("Write Here");
					t.setStyle("-fx-prompt-text-fill: grey;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 25; -fx-control-inner-background: rgba(50, 50, 50, 0.975); -fx-highlight-fill: rgba(0, 0, 0, 0.5);-fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2; -fx-faint-focus-color: transparent;");
					
					Button b = new Button("Apply Cheat Code");
					b.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:2px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 25; -fx-text-fill: White; -fx-font-weight: 900;");
					h2.getChildren().addAll(t,b);
					leftUp.getChildren().addAll(h2);
					b.setOnMouseEntered(e2 ->{
						b.setStyle("-fx-border-radius: 100px;-fx-background-radius: 100px;-fx-border:3px;-fx-border-color: ghostwhite;-fx-background-color:ghostwhite;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 25; -fx-text-fill: Black; -fx-font-weight: 900;");
					});
					b.setOnMouseExited(e2 ->{
						b.setStyle("-fx-border-radius: 100px;-fx-background-color:transparent;-fx-border:3px;-fx-border-color: gray;-fx-font-family: Papyrus, fantasy ; -fx-font-size: 25; -fx-text-fill: White; -fx-font-weight: 900;");
					});
					b.setOnAction(e2->{
						AudioClip au = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
						au.setCycleCount(1);
						au.play();
						cheat=true;
						if(t.getText().equals("aaa")){
							curr.setActionsAvailable(999);
							curr.setCurrentHp(curr.getMaxHp());
							updateLeftDown();
							updateLeftUp();
							updateMap();
						}
						else if(t.getText().equals("slim")){
							curr.getSupplyInventory().add(new Supply());
							updateLeftDown();
							updateLeftUp();
							updateMap();
						}
						else{
							updateLeftDown();
							updateLeftUp();
							updateMap();
						}
					});
				}
			}
		});

		main = new BorderPane();
		main.setStyle("-fx-background: transparent;-fx-background-image: url('images/MapBackground.png'); "
				+ "-fx-background-repeat: no-repeat;-fx-background-size: 100% 100%;");
		main.setCenter(map);
		main.setLeft(left);
		BorderPane.setAlignment(map, Pos.CENTER_RIGHT);
		BorderPane.setAlignment(left, Pos.TOP_LEFT);
		this.setRoot(main);
		GUI.window.setFullScreen(true);

	}

	public static VBox updateLeftDown() {
		leftDown.getChildren().clear();
		Label ld = new Label("Acquired Heroes");
		ld.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
		leftDown.getChildren().add(ld);
		for (Hero h : Game.heroes) {
			HeroLabel hl = new HeroLabel(h);
			hl.setStyle("-fx-border-color: dimgrey; -fx-border-width: 1px;");
			leftDown.getChildren().add(hl);
		}
		leftDown.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);-fx-border-color: dimgrey; -fx-border-width: 1px;");
		return leftDown;
	}

	public static void updateMap() {
		cheat = true;
		map.getChildren().clear();
		if (Game.heroes.size() == 4 && flag) {
			flag = false;
			PauseTransition p = new PauseTransition(Duration.seconds(1));
			p.play();
			p.setOnFinished(e -> {
				AudioClip au = new AudioClip(new File("src/sounds/1left.mp3")
						.toURI().toString());
				au.setCycleCount(1);
				au.play();
			});
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (Game.map[j][i] instanceof CharacterCell
						&& ((CharacterCell) Game.map[j][i]).getCharacter() instanceof Fighter) {
					FighterButton cell = new FighterButton(
							((Hero) ((CharacterCell) Game.map[j][i])
									.getCharacter()));
					map.add(cell, i, 14 - j);
				} else if (Game.map[j][i] instanceof CharacterCell
						&& ((CharacterCell) Game.map[j][i]).getCharacter() instanceof Medic) {
					MedicButton cell = new MedicButton(
							((Hero) ((CharacterCell) Game.map[j][i])
									.getCharacter()));
					map.add(cell, i, 14 - j);
				} else if (Game.map[j][i] instanceof CharacterCell
						&& ((CharacterCell) Game.map[j][i]).getCharacter() instanceof Explorer) {
					ExplorerButton cell = new ExplorerButton(
							((Hero) ((CharacterCell) Game.map[j][i])
									.getCharacter()));
					map.add(cell, i, 14 - j);
				} else if (Game.map[j][i] instanceof CharacterCell
						&& ((CharacterCell) Game.map[j][i]).getCharacter() instanceof Zombie) {
					ZombieButton cell = new ZombieButton(
							Game.map[j][i].isVisible(),
							((Zombie) ((CharacterCell) Game.map[j][i])
									.getCharacter()));
					map.add(cell, i, 14 - j);
				} else if (Game.map[j][i] instanceof CollectibleCell
						&& ((CollectibleCell) Game.map[j][i]).getCollectible() instanceof Supply) {
					SupplyButton cell = new SupplyButton(
							Game.map[j][i].isVisible());
					map.add(cell, i, 14 - j);
				} else if (Game.map[j][i] instanceof CollectibleCell
						&& ((CollectibleCell) Game.map[j][i]).getCollectible() instanceof Vaccine) {
					VaccineButton cell = new VaccineButton(
							Game.map[j][i].isVisible());
					map.add(cell, i, 14 - j);
				} else {
					EmptyButton cell = new EmptyButton(
							Game.map[j][i].isVisible());
					map.add(cell, i, 14 - j);
				}
			}
		}
	}

	public static void updateLeftUp() {
		if (curr.getCurrentHp() == 0) {
			leftUp.getChildren().clear();
		} else {
			leftUp.getChildren().clear();
			HeroLabel h = new HeroLabel(curr);
			Label l = new Label("Controlled Hero");
			l.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 30; -fx-text-fill: White");
			leftUp.getChildren().addAll(l, h);
			leftUp.setAlignment(Pos.TOP_CENTER);
			leftUp.setPadding(new Insets(20));
			leftUp.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);-fx-border-color: dimgrey; -fx-border-width: 1px;");
			ScaleTransition scaleTransition = new ScaleTransition(
					Duration.seconds(0.85), leftUp);
			scaleTransition.setFromX(1);
			scaleTransition.setFromY(1);
			scaleTransition.setToX(1.05);
			scaleTransition.setToY(1.05);
			scaleTransition.setAutoReverse(true);
			scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
			scaleTransition.play();
		}
	}
}