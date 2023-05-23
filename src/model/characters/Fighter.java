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
}
