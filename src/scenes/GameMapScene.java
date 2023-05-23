package scenes;

import java.awt.Point;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

	public GameMapScene(Parent root) {
		super(root, 1920, 1080);
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
			if (!alert) {
				if (e.getCode() == KeyCode.UP) {
					try {
						int tmpBefore = curr.getCurrentHp();
						curr.move(Direction.UP);
						int tmpAfter = curr.getCurrentHp();

						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (tmpBefore > tmpAfter) {
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
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
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
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
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
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
							alert = true;
							AlertBox a = new AlertBox(
									"You Have Stepped On A Trap Cell!!");
							leftUp.getChildren().clear();
							leftUp.getChildren().add(a);
						}
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
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

//				else

				else if (e.getCode() == KeyCode.C) {
					try {
						curr.cure();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								GUI.window
										.setScene(new GameWonScene(new VBox()));
							} else {
								GUI.window.setScene(new GameLostScene(
										new VBox()));
							}
						}
					} catch (GameActionException e1) {
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				}

				else if (e.getCode() == KeyCode.A) {
					try {
						curr.attack();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							}
						}
					} catch (GameActionException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				} else if (e.getCode() == KeyCode.U) {
					try {
						curr.useSpecial();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							}
						}
					} catch (GameActionException e1) {
						alert = true;
						AlertBox a = new AlertBox(e1.getMessage());
						leftUp.getChildren().clear();
						leftUp.getChildren().add(a);
					}
				}
			}
			 if (e.getCode() == KeyCode.E) {
					try {
						alert = false;
						Game.endTurn();
						updateMap();
						updateLeftUp();
						updateLeftDown();
						if (Game.checkGameOver()) {
							if (Game.checkWin()) {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameWonScene o = new GameWonScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							} else {
								FadeTransition fadeOut = new FadeTransition(
										Duration.seconds(0.75), GUI.window
												.getScene().getRoot());
								fadeOut.setFromValue(1.0);
								fadeOut.setToValue(0.05);
								GameLostScene o = new GameLostScene(
										new StackPane());
								o.setFill(Color.BLACK);
								FadeTransition fadeIn = new FadeTransition(
										Duration.seconds(0.75), o.getRoot());
								fadeIn.setFromValue(0.0);
								fadeIn.setToValue(1.0);
								fadeOut.setOnFinished(e3 -> {
									GUI.window.setScene(o);
									fadeIn.play();
									GUI.window.setFullScreen(true);
								});
								fadeOut.play();
							}
						}
					} catch (Exception e1) {

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
		map.getChildren().clear();
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
				} else {// if(Game.map[i][j] instanceof CharacterCell &&
						// ((CharacterCell)Game.map[i][j]).getCharacter()==null)
						// {
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
