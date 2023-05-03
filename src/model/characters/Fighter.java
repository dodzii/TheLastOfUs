package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Fighter extends Hero {
	
	//Constructors
	public Fighter() {
		
	}
	
	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg,maxActions);
	}
	
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if(this.isSpecialAction()) {
			super.attack();
			this.setActionsAvailable(this.getActionsAvailable()+1);
		}
		else
			super.attack();
	}
}
