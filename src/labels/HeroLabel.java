package labels;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HeroLabel extends HBox{
	public HeroLabel(Hero h) {
		super();
		Label stats = new Label("Name: "+h.getName()+'\n'+"Type: "+ h.getClass().getSimpleName()+'\n'+"Attack Damage: "+h.getAttackDmg()+'\n'+"Actions Available: "+h.getActionsAvailable()+" / "+h.getMaxActions());
		stats.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 20; -fx-text-fill: White");
		Image image = getImage(h);
		ImageView imageView = new ImageView(image);
		ProgressBar hpBar = new ProgressBar();
		Label hp = new Label("Current HP:");hp.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 20; -fx-text-fill: White");
		hpBar.setPrefWidth(200);
		hpBar.setPrefHeight(20);
		hpBar.setStyle("-fx-accent: green;");
		hpBar.setProgress(((double)h.getCurrentHp()/(double)h.getMaxHp()));
		Label vacc = new Label("Vaccines Available: "+h.getVaccineInventory().size());vacc.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 20; -fx-text-fill: White");
		Label supp = new Label("Supplies Available: "+h.getSupplyInventory().size());supp.setStyle("-fx-font-family: Papyrus, fantasy ; -fx-font-size: 20; -fx-text-fill: White");
		VBox currhp = new VBox(5,hp,hpBar,vacc,supp);
		this.getChildren().addAll(imageView,stats,currhp);
		this.setSpacing(20);
		
		
	}

	public static Image getImage(Hero curr) {
		Image image=null;
		if(curr instanceof Fighter) image=new Image("images/FighterLabel.png");
		else if(curr instanceof Medic) image=new Image("images/MedicLabel.png");
		else if(curr instanceof Explorer) image=new Image("images/ExplorerLabel.png");
		
		
		return image;
	}
	
	
}
