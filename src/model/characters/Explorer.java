package model.characters;

import engine.*;
import exceptions.*;
import model.world.*;

public class Explorer extends Hero {
 
	//Constructors
	public Explorer() {
		
	}
	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	//Methods
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
