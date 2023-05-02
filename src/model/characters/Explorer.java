package model.characters;

import engine.*;
import exceptions.GameActionException;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CollectibleCell;

public class Explorer extends Hero {
 
	//Constructors
	public Explorer() {
		
	}
	
	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	public void useSpecial() throws GameActionException {
		super.useSpecial();
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Cell temp=Game.map[i][j];
				if(temp!=null) {
					temp.setVisible(true);
				}
			}
		}
	}

}
